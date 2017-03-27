package com.zkhk.services;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.zkhk.constants.Constants;
import com.zkhk.dao.MemDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.MemberStatus;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Omem;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.ReturnValue;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.util.ByteToInputStream;
import com.zkhk.util.ImageUtils;
import com.zkhk.util.PasswordEncryption;
import com.zkhk.util.SystemUtils;
import com.zkhk.util.TimeUtil;
import com.zkhk.util.Util;

@Service("memService")
public class MemServiceImpl implements MemService {

	private Logger logger = Logger.getLogger(MemServiceImpl.class);
	@Resource(name = "memDao")
	private MemDao memDao;
    @Resource(name = "pushMessageDao")
 	private PushMessageDao messageDao;
	
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;

	public String findUserbyNameAndPassWord(HttpServletRequest request) throws Exception{
		// 返回函数
		ReturnValue re = new ReturnValue();
		String param = request.getParameter("params");
		// 接收函数
		CallValue call = JSON.parseObject(param, CallValue.class);
		MemLog log = JSON.parseObject(call.getParam(), MemLog.class);
		// 对用户密码进行加密
		// String
		// pasString=PasswordEncryption.getMD5Password(log.getPassWord()+log.getUserAccount()+"zkhk");
		log.setPassWord(PasswordEncryption.getMD5Password(log.getPassWord()+ "zkhk"));
		
		//以下别扭的登录逻辑，是为了解决web医生端注册流程没有事务控制，不进行回滚生成错误数据，从而导致账号不能登录的问题
		MemLog loginLog = memDao.findUserbyNameAndPassWord(log);
		if (loginLog != null) {
			String session = UUID.randomUUID().toString();
			loginLog.setSession(session);
			loginLog.setLoginTime(TimeUtil.formatDatetime(new Date(),"yyyy-MM-dd HH:mm:ss"));
			loginLog.setDevice(log.getDevice());
			String defaultPass = PasswordEncryption.getMD5Password(PasswordEncryption.getMD5Password("123456")+ "zkhk");
			if (StringUtils.isEmpty(loginLog.getPassWord()) && defaultPass.equals(log.getPassWord())){ //表中没有该会员数据,并且登陆密码等于初始密码
				// 插入用户的登入信息
				loginLog.setCurTag("Y");
				loginLog.setPassWord(defaultPass);
				
				memDao.insertMemLogByMemberid(loginLog);
				
				re.setState(0);
				String content = JSON.toJSONString(loginLog);
				re.setContent(content);
				logger.info("账号："+log.getUserAccount() + "，登入成功");
			}else if(loginLog.getPassWord().equals(log.getPassWord())){
				// 更新用户的登入信息
				memDao.updateMemLogByMemberid(loginLog);
				
				re.setState(0);
				String content = JSON.toJSONString(loginLog);
				re.setContent(content);
				logger.info("账号："+log.getUserAccount() + "，登入成功");
			}else{
				re.setState(1);
				re.setMessage("账号密码错误。");
				logger.info("账号:"+log.getUserAccount() + "，密码错误，登入失败");
			}
		} else {
			re.setState(1);
			re.setMessage("账号不存在。");
			logger.info("账号:"+log.getUserAccount() + "，不存在，登入失败");
		}
		return JSON.toJSONString(re);
	}

	/** 
	 * <p>Title: findUserBySessionAndId</p>  
	 * <p>Description: 验证会员的session是否有效</p>
	 * @author liuxiaoqin 
	 * @createDate 2016-04-13
	 * @return
	 * @throws Exception 
	*/
	public String findUserBySessionAndId(String session, int memberId) throws Exception{
		// 返回函数
		ReturnValue re = new ReturnValue();
//		try {
//			boolean isLogin = memDao.findUserBySessionAndId(session, memberId);
//			if (isLogin) {
//				re.setState(0);
//				re.setMessage(SystemUtils.getValue(Constants.USER_VALID_SESSION));
//				logger.info("验证会员session通过！");
//			} else {
//				re.setState(9);
//				re.setMessage(SystemUtils.getValue(Constants.USER_INVALID_SESSION));
//				logger.info("验证会员session失败,没有权限登入，请重新登入");
//			}
//		} catch (Exception e) {
//			re.setState(2);
//			re.setMessage(SystemUtils.getValue(Constants.USER_EXCEPTION_SESSION));
//			logger.error("验证会员session异常"+e);
//		}
		re.setState(0);
		return JSON.toJSONString(re);
	}

	public String updateUserSessionByMemId(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
		try {
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			MemLog log = new MemLog();
			log.setDevice("");
			log.setLoginTime("");
			log.setSession("");
			log.setMemberId(callValue.getMemberId());
			// 更新同行状态信息
			memDao.updateMemLogByMemberid(log);
			re.setState(0);
			re.setMessage("注销成功");
			logger.info(callValue.getMemberId() + "注销成功");
		} catch (Exception e) {
			re.setState(1);
			re.setMessage("注销失败");
			logger.error("注销失败");
		}
		return JSON.toJSONString(re);
	}

	public String findOmembyId(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);

			// 更新同行状态信息
			Omem omem = memDao.findOmembyId(callValue.getMemberId());
		
		
			if (omem != null) {
				if(omem.getHeadAddress()!=null&&!omem.getHeadAddress().equals("")){
					try {
						GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(omem.getHeadAddress()));
						omem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
					} catch (Exception e) {
						logger.info("查询会员头像信息异常成功");
					}
				}
				
				//根据算法计算会员资料的完整度
				omem.setIntegrity(caculateIntegrity(omem)+"%");
				
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(omem));
				logger.info("查询会员基本信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员基本信息");
				logger.info("查询不到该会员基本信息");
			}
		return JSON.toJSONString(re);
	}

	public String findMemFilebyId(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			String param=callValue.getParam();
			MemFile memFile=null;
			if(param!=null){
				JSONObject object=JSON.parseObject(param);
				 int id=object.getIntValue("memberId");
				 memFile = memDao.findMemFilebyId(id);
				 if(id!=callValue.getMemberId()){
					 messageDao.updateMarkTagByCreateId(id, callValue.getMemberId(), 8);
				 }
			}else{
				
				memFile = memDao.findMemFilebyId(callValue.getMemberId());
			}
			if (memFile != null) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(memFile));
				logger.info("查询会员健康档案信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员的健康档案信息");
				logger.info("查询不到该会员的健康档案信息");
			}
		return JSON.toJSONString(re);
	}

	/** 
	 * <p>Title: updateOmembyId</p>  
     * <p>Description: 更新会员基本信息</p>
     * @author liuxiaoqin
     * @createDate 2016-04-12  
	 * @param request
	 * @return
	 * @throws Exception 
	 * @see com.zkhk.services.MemService#updateOmembyId(javax.servlet.http.HttpServletRequest)  
	*/
	public String updateOmembyId(HttpServletRequest request)throws Exception {
	    ReturnResult re = new ReturnResult();
	    String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        String omemJson = callValue.getParam();
        Integer memberId = callValue.getMemberId();
        Omem omem = JSON.parseObject(omemJson, Omem.class);
        String imageId;
        try{
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            //获取会员头像
            MultipartFile headImg = mRequest.getFile(Constants.MEMBER_HENDIMG); 
            if(headImg != null){
                imageId = mongoEntityDao.saveFile("headImage", headImg);
                omem.setHeadAddress(imageId);
            }
        }catch (Exception e) {
            logger.error(e);
            logger.info("没有上传头像");
        }
        String birthDate = omem.getBirthDate();
        if(StringUtils.isEmpty(birthDate)){
            omem.setBirthDate(null);
        }
        Omem member = memDao.findOmembyId(memberId);
        String idcard = omem.getIdCard();
        String idcardOld = member.getIdCard();
        if(!StringUtils.isEmpty(idcard) && idcard.equals(idcardOld) == false){
        	int countIdCard = memDao.vaildIdCard(idcard);
        	if(countIdCard > 0){
        		re.setState(1);
                re.setMessage("身份证号【"+idcard+"】已被使用，请重新输入！");
                logger.info("身份证号【"+idcard+"】已被使用，请重新输入！");
                return JSON.toJSONString(re);
        	}
        }
        String tel = omem.getTel();
        String telOld = member.getTel();
        if(!StringUtils.isEmpty(tel) && tel.equals(telOld) == false){
        	int countTel = memDao.vaildTel(tel);
        	if(countTel > 0){
        		re.setState(1);
                re.setMessage("手机号码【"+tel+"】已被使用，请重新输入！");
                logger.info("手机号码【"+tel+"】已被使用，请重新输入！");
                return JSON.toJSONString(re);
        	}
        }
        String email = omem.getEmail();
        String emailOld = member.getEmail();
        if(!StringUtils.isEmpty(email) && email.equals(emailOld) == false){
        	int countEmail = memDao.vaildEmail(email);
        	if(countEmail > 0){
        		re.setState(1);
                re.setMessage("邮箱号【"+email+"】已被使用，请重新输入！");
                logger.info("邮箱号【"+email+"】已被使用，请重新输入！");
                return JSON.toJSONString(re);
        	}
        }
        int count = memDao.updateOmembyId(omem);
        if(count > 0){
            re.setState(0);
            re.setMessage("修改会员基本资料成功！");
            logger.info("修改会员基本资料成功！");
            return JSON.toJSONString(re);
        }else{
            re.setState(1);
            re.setMessage("修改会员基本资料失败！");
            logger.info("修改会员基本资料失败！");
            return JSON.toJSONString(re);
        }
	}

	public String updateOmemPassWordByParam(HttpServletRequest request) {
		ReturnValue re = new ReturnValue();
		try {
			String params=request.getParameter("params");
			CallValue callValue=JSON.parseObject(params, CallValue.class);
			String param=callValue.getParam();
		   JSONObject object=JSON.parseObject(param);
		   String oldPW=object.getString("oldPassWord");
		   String newPW=object.getString("newPassWord");
		   int result =	memDao.updateOmemPassWordByParam(callValue.getMemberId(),oldPW,newPW);
		   if(result>0){
				re.setState(0);
				re.setMessage("修改成功"); 
		   }else{
				re.setState(1);
				re.setMessage("原始密码错误"); 
		   }
	       }catch (Exception e) {
		      re.setState(2);
		      re.setMessage("运行异常，请联系管理人员");
	       }
	   return JSON.toJSONString(re);
	}
	
	

	/**
	 * @throws Exception 
	 * 计算资料完整度
	 * @param omem
	 * @return
	 * @throws  
	 */
	private long caculateIntegrity(Omem omem) throws Exception{
		//统计的属性字段
		String[] caculateFileds ={"memName","gender","birthDate","tel","idCard","marryStatus","headImg","contactMobPhone","contactName","bloodType","height","weight"};
		int count = 0;
		int total = caculateFileds.length;
		try{
			String[] totalFiles = Util.getFiledName(omem);
			
			for(String filed:caculateFileds){
				for(String temp:totalFiles){
					if(filed.equals(temp)){
						Object o = Util.getFieldValueByName(temp,omem);
						if(null != o && !"".equals(o.toString().trim())){
							count=count+1;
						}
						
					}
				}
			}
		}catch(Exception e){
			logger.error("计算资料完整度异常",e);
			throw new Exception("计算资料完整度异常");
		}
		return Math.round(((double)count/total)*100);
	}

	
	public boolean checkAcountStatus(CallValue callValue)throws Exception {
		boolean isUse = true;
		String account = "";
		if(callValue.getMemberId() == 0){	
			MemLog log = JSON.parseObject(callValue.getParam(), MemLog.class);
			account = log.getUserAccount();
		}else{
			account = String.valueOf(callValue.getMemberId());
		}

		if(!StringUtils.isEmpty(account)){
		    MemberStatus  status = memDao.queryMemberStatus(account); 
	        if("F".equals(status.getStatus())){
	            logger.error("用户账号【"+account+"】 被冻结。");
	            isUse = false;
	        }else{
	            isUse = true;
	        }
		}
		return isUse;
	}
	
	/** 
     * @Title: findSameOrgDoctors 
     * @Description: 会员查询同组织下的所有医生。
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-30
     * @throws IOException    
     * @retrun String
     */
    public String findSameOrgDoctors(HttpServletRequest request)throws Exception{
    	ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        int memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(3);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        Integer pageNow = 1;
        Integer pageNowGet = jsonObject.getInteger("pageNow");
        if(pageNowGet != null && pageNowGet >= 0){
        	pageNow = pageNowGet;
        }
        Integer pageSize = 10;
        Integer pageSizeGet = jsonObject.getInteger("pageSize");
        if(pageSizeGet != null && pageSizeGet >= 0){
        	pageSize = pageSizeGet;
        }
        String searchName = jsonObject.getString("searchName");
        List<Odoc> doctorList = memDao.findSameOrgDoctors(memberId,searchName,pageNow,pageSize);
        if(doctorList != null && doctorList.size() > 0){
            result.setState(0);
            result.setContent(doctorList);
            result.setMessage("会员查询同组织下的所有医生成功");
            logger.info("会员查询同组织下的所有医生成功");
        }else{
            result.setState(3);
            result.setMessage("没有该会员同组织下的医生");
            logger.info("没有该会员同组织下的医生");
        }
        return JSON.toJSONString(result);
    }
	
}
