package com.zkhk.services;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zkhk.dao.DocDao;
import com.zkhk.dao.MeasureDao;
import com.zkhk.dao.MemDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.MeasureRecordParam;
import com.zkhk.entity.MemBasicInfo;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.MemMeasureRecord;
import com.zkhk.entity.MemSearch;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omds;
import com.zkhk.entity.Omem;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.OsbpFile;
import com.zkhk.entity.ReturnMeasureData;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.ReturnValue;
import com.zkhk.entity.ThreeParam;
import com.zkhk.entity.ViewParam;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.rabBit.ClientSender;
import com.zkhk.util.FileOperateUtil;
import com.zkhk.util.SystemUtils;
import com.zkhk.util.TimeUtil;
import com.zkhk.util.Util;

@Service("docService")
public class DocServiceImpl implements DocService {

	private Logger logger = Logger.getLogger(DocServiceImpl.class);
	
	@Resource(name = "docDao")
	private DocDao docDao;
	
	@Resource(name = "memDao")
	private MemDao memDao;
	
	@Resource(name = "measureDao")
	private MeasureDao measureDao;
	
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;
	
	@Resource(name = "measureService")
	private MeasureService measureService;

	public ReturnResult findDocbyNameAndPassWord(HttpServletRequest request) throws Exception{
		ReturnResult re = new ReturnResult();
		String param = request.getParameter("params");
		CallValue call = JSON.parseObject(param, CallValue.class);
		MemLog log = JSON.parseObject(call.getParam(), MemLog.class);
		// 对用户密码进行加密
		//log.setPassWord(PasswordEncryption.getMD5Password(log.getPassWord()));
		MemLog loginLog = docDao.findDocbyNameAndPassWord(log);
		if (loginLog != null) {
			String session = UUID.randomUUID().toString();
			loginLog.setSession(session);
			loginLog.setLoginTime(TimeUtil.formatDatetime(new Date(),"yyyy-MM-dd HH:mm:ss"));
			loginLog.setDevice(log.getDevice());
			// 更新用户的登入信息
			int row = docDao.updateDocLogByMemberid(loginLog);
			//用户从未为登录过，保存用户登录信息
			if(row == 0){
				loginLog.setPassWord(log.getPassWord());
				docDao.saveDoctorLog(loginLog);
			}
			Map<Object, Object> result = new HashMap<Object, Object>();
			result.put("memberId", loginLog.getMemberId());
			result.put("session", loginLog.getSession());
			result.put("doctorName", loginLog.getDoctorName());
			result.put("doctorGUID", loginLog.getDoctorGUID());
			result.put("doctorHeadImg", loginLog.getDoctorHeadImg());
			re.setContent(result);
			re.setMessage(log.getUserAccount() + ":登入成功");
			logger.info(log.getUserAccount() + ":登入成功");
		} else {
			re.setState(1);
			re.setMessage("用户名或密码错误");
			logger.info(log.getUserAccount() + ":登入失败");
		}
		return re;
	}

	public String updateDocSessionByMemId(HttpServletRequest request)throws Exception {
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
			docDao.updateDocLogByMemberid(log);
			re.setState(0);
			re.setMessage("注销成功");
			logger.info(callValue.getMemberId() + "注销成功");
		} catch (Exception e) {
			re.setState(1);
			re.setMessage("注销失败.");
			logger.error("注销失败."+e);
		}
		return JSON.toJSONString(re);
	}
	
	/** 
	 * <p>Title: findDocBySessionAndId</p>  
	 * <p>Description: 验证医生的session是否有效</p>
	 * @author liuxiaoqin 
	 * @createDate 2016-04-13
	 * @return
	 * @throws Exception 
	*/
	public String findDocBySessionAndId(String session, int doctorId) throws Exception{
		ReturnValue re = new ReturnValue();
		try{
			boolean isLogin = docDao.findDocBySessionAndId(session, doctorId);
			if(isLogin){
				re.setState(0);
				re.setMessage(SystemUtils.getValue(Constants.USER_VALID_SESSION));
				logger.info("验证医生session通过！");
			}else{
				re.setState(9);
				re.setMessage(SystemUtils.getValue(Constants.USER_INVALID_SESSION));
				logger.info("验证医生session失败,没有权限登入，请重新登入");
			}
		}catch(Exception e){
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.USER_EXCEPTION_SESSION));
			logger.error("验证医生session异常"+e);
		}
		return JSON.toJSONString(re);
	}

	public String getMyMemberList(HttpServletRequest request) throws Exception {
		ReturnResult result = new ReturnResult();
		try{
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			MemSearch memSearch = JSON.parseObject(callValue.getParam(), MemSearch.class);
			List<MemBasicInfo> memberList = docDao.queryMemberListByParams(memSearch);
			if(memberList != null && memberList.size() >0){
			    String memberType = memSearch.getMemberType();
			    result.setState(0);
			    result.setContent(memberList);
			    if(!StringUtils.isEmpty(memberType)){
			        result.setMessage("医生查询可进行体检的会员列表成功");
	                logger.info("医生查询可进行体检的会员列表成功"+memSearch.toString());
			    }else{
			        result.setMessage("医生查询会员列表成功");
			        logger.info("医生查询会员列表成功."+memSearch.toString());
			    }
			}else{
			    result.setState(3);
	            result.setMessage("没有符合条件的会员");
	            logger.error("没有符合条件的会员");
			}
		}catch(Exception e){
			result.setState(1);
			result.setMessage("医生查询会员列表失败");
			logger.error("医生查询会员列表失败."+e);
		}
		return JSON.toJSONString(result);
	}

	public String memLogin(HttpServletRequest request) throws Exception {
		ReturnResult result = new ReturnResult();
		try{
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			MemSearch memSearch = JSON.parseObject(callValue.getParam(), MemSearch.class);
			logger.info(memSearch.toString());
			//判断会员是否还在该医生的管辖之下
			int rows = docDao.findmemByMemberIdAndDoctorId(memSearch.getDoctorId(), memSearch.getMemberId());
			if (rows == 0){
				result.setState(8);
				result.setMessage("您无权限管理该会员");
			}else{
				MemLog loginLog = docDao.findUserbyMemberIdAndPassWord(memSearch);
				if (loginLog != null) {
				    String oldSession = loginLog.getSession();
				    if(!StringUtils.isEmpty(oldSession)){
				        loginLog.setSession(oldSession);
				    }else{
				        String session = UUID.randomUUID().toString();
				        loginLog.setSession(session);
				    }
					loginLog.setLoginTime(TimeUtil.formatDatetime(new Date(),"yyyy-MM-dd HH:mm:ss"));
					loginLog.setDevice("Android");
					// 更新用户的登入信息
					memDao.updateMemLogByMemberid(loginLog);
					result.setState(0);
					result.setContent(loginLog);
					logger.info(memSearch.getMemberId() + ":登入成功");
				} else {
					result.setState(1);
					result.setMessage("用户名或密码错误");
					logger.info(memSearch.getMemberId() + ":登入失败");
				}
			}
		}catch(Exception e){
			result.setState(1);
			result.setMessage("会员自动登录失败");
			logger.error("会员自动登录失败."+e);
		}
		return JSON.toJSONString(result);
	}

	public String saveMemFile(HttpServletRequest request) throws Exception {
		ReturnResult result = new ReturnResult();
		try{
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			OsbpFile osbp = JSON.parseObject(callValue.getParam(), OsbpFile.class);
			Osbp pramOsbp = new Osbp();
			pramOsbp.setSbp(osbp.getBloodH());
			pramOsbp.setDbp(osbp.getBloodL());
			pramOsbp.setDeviceCode("");
			pramOsbp.setPulseRate(osbp.getPulseRate());
			pramOsbp.setTimePeriod(0);
			pramOsbp.setTestTime(callValue.getLoginLog());
			pramOsbp.setBluetoothMacAddr("");
			pramOsbp.setMemberId(osbp.getMemberId());
			
			ReturnValue re = measureService.saveOsbpData(pramOsbp,osbp.getMemberId());
			
			if(re.getState() == 0){
				int rows = docDao.updateMemFile(osbp);
				if(rows == 0){
					docDao.saveMemFile(osbp);
				}
				result.setState(0);
				result.setMessage("保存健康档案成功");
				logger.info("保存健康档案成功");
			}else{
				result.setState(1);
				result.setMessage("保存健康档案失败");
				logger.error("保存健康档案失败:");
			}
		}catch(Exception e){
			result.setState(1);
			result.setMessage("保存健康档案异常");
			logger.error("保存健康档案异常:"+e);
		}
		return JSON.toJSONString(result);
	}

	public String checkMemFile(HttpServletRequest request) throws Exception {
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject param = JSON.parseObject(callValue.getParam());
		
		MemFile memFile = docDao.queryMemFile(param.getString("memberId"));
		
		if(memFile !=null){
			result.setState(0);
			result.setContent(memFile);
			logger.info("获取用户健康档案成功");
		}else{
			result.setState(2);
			result.setMessage("找不到相应的会员健康档案");
			logger.info("找不到相应的会员健康档案");
		}
		
		return JSON.toJSONString(result);
	}
	
	/** 
     * @Title: findMemMeasureRecordList 
     * @Description: 医生查看所属下的会员的测量记录 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
	public String findMemMeasureRecordList(HttpServletRequest request) throws Exception
	{
	    /* 1 血压 2 血糖 3 三合一 4 动态心电  */
	    ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        MeasureRecordParam param = JSON.parseObject(callValue.getParam(),MeasureRecordParam.class);
        if(param.getDoctorId() <= 0){
            result.setState(1);
            result.setMessage("参数医生doctorId为空！");
            logger.info("参数医生doctorId为空！");
            return JSON.toJSONString(result);
        }
        if(StringUtils.isEmpty(param.getMeasureTypeIds())){
            result.setState(1);
            result.setMessage("医生查看所属下的会员的测量记录为空！");
            logger.info("医生查看所属下的会员的测量记录为空！");
            return JSON.toJSONString(result);
        }
        List<MemMeasureRecord> recordList = docDao.findMemMeasureRecordList(param);
        if(recordList != null && recordList.size() > 0){
            result.setState(0);
            result.setContent(recordList);
            result.setMessage("获取医生查看所属下的会员的测量记录成功!");
            logger.info("获取医生查看所属下的会员的测量记录成功!");
        }else{
            result.setState(3);
            result.setMessage("该医生所属下的会员没有测量记录！");
            logger.info("该医生所属下的会员没有测量记录！");
        }
        return JSON.toJSONString(result);
	}
	
	/** 
     * @Title: findMemMeasureRecordOne 
     * @Description: 医生根据事件id(eventId)和事件类型(eventType)获取会员的某条测量记录 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
    public String findMemMeasureRecordOne(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject param = JSON.parseObject(callValue.getParam());
        String eventId = param.getString("eventId");
        if(StringUtils.isEmpty(eventId)){
            result.setState(1);
            result.setMessage("参数eventId为空！");
            logger.info("参数eventId为空！");
            return JSON.toJSONString(result);
        }
        String eventType = param.getString("eventType");
        if(StringUtils.isEmpty(eventType)){
            result.setState(1);
            result.setMessage("参数eventType为空！");
            logger.info("参数eventType为空！");
            return JSON.toJSONString(result);
        }
        int newEventId = Integer.valueOf(eventId);
        int newEventType = Integer.valueOf(eventType);
        if(newEventType == 1){
            Osbp osbp = docDao.findOsbpRecordOne(newEventId);
            if(osbp == null){
                result.setState(1);
                result.setMessage("没有血压数据！");
                logger.info("没有血压数据！");
                return JSON.toJSONString(result);
            }else{
                result.setState(0);
                result.setMessage("查询血压数据成功！");
                result.setContent(osbp);
                logger.info("没有血压数据！");
            }
        }else if(newEventType == 2){
            Obsr obsr = docDao.findObsrRecordOne(newEventId);
            if(obsr == null){
                result.setState(1);
                result.setMessage("没有血糖数据！");
                logger.info("没有血糖数据！");
                return JSON.toJSONString(result);
            }else{
                result.setState(0);
                result.setMessage("查询血糖数据成功！");
                result.setContent(obsr);
                logger.info("没有血糖数据！");
            }
        }else if(newEventType == 3){
            ReturnMeasureData data = docDao.findThreeJoinOneRecordOne(newEventId);
            if(data == null){
                result.setState(1);
                result.setMessage("没有三合一数据！");
                logger.info("没有三合一数据！");
                return JSON.toJSONString(result);
            }else{
                result.setState(0);
                result.setMessage("查询血糖数据成功！");
                result.setContent(data);
                logger.info("没有血糖数据！");
            }
        }else{
            Oecg oecg = docDao.findOecgRecordOne(newEventId);
            if(oecg == null){
                result.setState(1);
                result.setMessage("没有mini心电数据！");
                logger.info("没有mini心电数据！");
                return JSON.toJSONString(result);
            }else{
                result.setState(0);
                result.setMessage("查询mini心电数据成功！");
                result.setContent(oecg);
                logger.info("没有mini心电数据！");
            }
        }
        
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemOecgById 
     * @Description: 医生根据心电oecg的id获取会员的某条心电测量数据(不分三合一和mini)
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public String findMemOecgById(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject param = JSON.parseObject(callValue.getParam());
        String oecgId = param.getString("oecgId");
        if(StringUtils.isEmpty(oecgId)){
            result.setState(1);
            result.setMessage("参数oecgId为空！");
            logger.info("参数oecgId为空！");
            return JSON.toJSONString(result);
        }
        long newOecgId = Integer.valueOf(oecgId);
        Oecg oecg = docDao.findMemOecgById(newOecgId);
        if(oecg != null){
            result.setState(0);
            result.setMessage("成功");
            result.setContent(oecg);
            logger.info("查询会员心电信息成功");
        }else{
            result.setState(1);
            result.setMessage("查询不到该心电信息");
            logger.info("查询不到该心电信息");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemOppgById 
     * @Description: 医生根据脉搏oppg的id获取会员的某条脉搏数据
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public String findMemOppgById(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject param = JSON.parseObject(callValue.getParam());
        String oppgId = param.getString("oppgId");
        if(StringUtils.isEmpty(oppgId)){
            result.setState(1);
            result.setMessage("参数oppgId为空！");
            logger.info("参数oppgId为空！");
            return JSON.toJSONString(result);
        }
        long newOppgId = Integer.valueOf(oppgId);
        Oppg oppg = docDao.findMemOppgById(newOppgId);
        if(oppg != null){
            result.setState(0);
            result.setMessage("成功");
            result.setContent(oppg);
            logger.info("查询会员脉搏信息成功");
        }else{
            result.setState(1);
            result.setMessage("查询不到该脉搏信息");
            logger.info("查询不到该脉搏信息");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: downloadFile 
     * @Description: 医生根据文件的id下载会员的测量文件信息
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws IOException    
     * @retrun void
     */
    public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject object=JSON.parseObject(callValue.getParam());
        GridFSDBFile file = mongoEntityDao.retrieveFileOne("fs",new ObjectId( object.getString("rawImg")));
        FileOperateUtil.download(request, response, file);
    }
	
    /** 
     * @Title: uploadMemBloodPressure 
     * @Description: 医生上传会员的血压数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemBloodPressure(HttpServletRequest request) throws Exception{
        ReturnResult re = new ReturnResult();
        String uploadTime = TimeUtil.currentDatetime();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        Osbp osbp = JSON.parseObject(callValue.getParam(), Osbp.class);
        int memberId = osbp.getMemberId();
        if(memberId <= 0){
            re.setState(1);
            re.setMessage("参数memberId："+memberId+"应为大于0的正整数");
            logger.info("参数memberId："+memberId+"应为大于0的正整数");
            return JSON.toJSONString(re);
        }
        /*验证血压数据是否重复  begin */
        int countOldOsbp = measureDao.checkHasOsbpRecord(memberId, osbp.getTestTime());
        if(countOldOsbp > 0){
            re.setState(11);
            re.setMessage("该血压数据已存在，请勿重复上传");
            logger.info("该血压数据已存在，请勿重复上传");
            return JSON.toJSONString(re);
        }
        /*验证血压数据是否重复  end */
        
        // 分析血压数据
        int osbpResult = getBloodPressureResult(osbp);
        osbp.setAbnormal(osbpResult + "");
//        // 获取事件id
//        long event_id = docDao.insertEventId();
//        osbp.setEventId(event_id);
//        osbp.setUploadTime(uploadTime);
//        osbp.setMemberId(memberId);
//        /** 保存omds表开始 **/
//        Omds omds = new Omds();
//        omds.setDataType(1);
//        omds.setEventId(event_id);
//        omds.setMemberId(memberId);
//        omds.setStatusTag(2);
//        omds.setUploadTime(osbp.getUploadTime());
//        // 结果不为零标示异常
//        if (osbpResult != 0) {
//            omds.setWheAbnTag(1);
//        }
//        int count = docDao.saveMemOmdsData(omds);
//        if(count > 0){
//            long osbpId = docDao.insertId("osbp_docentry");
//            osbp.setId(osbpId);
//            int countOsbp = docDao.uploadMemBloodPressure(osbp);
//            if(countOsbp > 0){
//                re.setState(0);
//                re.setMessage("医生上传会员的血压数据成功");
//                re.setContent(osbp);
//                logger.info("医生上传会员的血压数据成功");
//            }else{
//                re.setState(1);
//                re.setMessage("医生上传会员的血压数据失败");
//                logger.info("医生上传会员的血压数据失败");
//            }
//        }else{
//            re.setState(1);
//            re.setMessage("医生上传会员的血压数据失败！");
//            logger.info("医生上传会员的血压数据到事件主表失败！");
//            return JSON.toJSONString(re);
//        }
        
        /* V3.0版本开始去掉分表，主键自动生成  begin */
        /* 保存omds表  begin*/
        Omds omds = new Omds();
        omds.setDataType(1);
        omds.setMemberId(memberId);
        omds.setStatusTag(2);
        omds.setUploadTime(osbp.getUploadTime());
        // 结果不为零标示异常
        if (osbpResult != 0) {
            omds.setWheAbnTag(1);
        }
        long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
        /* 保存omds表  end*/
        
        /* 保存血压osbp表  begin*/
        osbp.setEventId(eventId);
        osbp.setUploadTime(uploadTime);
        osbp.setMemberId(memberId);
        long docentry = measureDao.insertOsbpReturnPrimaryKey(osbp);
        osbp.setId(docentry);
        /* 保存血压osbp表  end*/
        /* V3.0版本开始去掉分表，主键自动生成  end */
        re.setState(0);
        re.setMessage("医生上传会员的血压数据成功");
        re.setContent(osbp);
        logger.info("医生上传会员的血压数据成功");
        return JSON.toJSONString(re);
    }
    
    /** 
     * @Title: uploadMemBloodGlucose 
     * @Description: 医生上传会员的血糖数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemBloodGlucose(HttpServletRequest request) throws Exception{
        ReturnResult re = new ReturnResult();
        String uploadTime = TimeUtil.currentDatetime();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        Obsr obsr = JSON.parseObject(callValue.getParam(), Obsr.class);
        int memberId = obsr.getMemberId();
        if(memberId <= 0){
            re.setState(1);
            re.setMessage("参数memberId："+memberId+"应为大于0的正整数");
            logger.info("参数memberId："+memberId+"应为大于0的正整数");
            return JSON.toJSONString(re);
        }
        /*验证血糖数据是否重复  begin */
        int countOldObsr = measureDao.checkHasObsrRecord(memberId, obsr.getTestTime());
        if(countOldObsr > 0){
            re.setState(11);
            re.setMessage("该血糖数据已存在，请勿重复上传");
            logger.info("该血糖数据已存在，请勿重复上传");
            return JSON.toJSONString(re);
        }
        /*验证血糖数据是否重复  end */
        
        // 分析血糖数据
        int obsrResult = getBloodGlucoseResult(obsr);
        obsr.setAnalysisResult(obsrResult);
//        // 获取事件id
//        long event_id = docDao.insertEventId();
//        obsr.setEventId(event_id);
//        obsr.setUploadTime(uploadTime);
//        obsr.setMemberId(memberId);
//        /** 保存omds表开始 **/
//        Omds omds = new Omds();
//        omds.setDataType(2);
//        omds.setEventId(event_id);
//        omds.setMemberId(memberId);
//        omds.setStatusTag(2);
//        omds.setUploadTime(obsr.getUploadTime());
//        // 结果不为零标示异常
//        if (obsrResult != 0) {
//            omds.setWheAbnTag(1);
//        }
//        int count = docDao.saveMemOmdsData(omds);
//        if(count > 0){
//            long obsrId = docDao.insertId("obsr_docentry");
//            obsr.setId(obsrId);
//            int countObsr = docDao.uploadMemBloodGlucose(obsr);
//            if(countObsr > 0){
//                re.setState(0);
//                re.setMessage("医生上传会员的血糖数据成功");
//                re.setContent(obsr);
//                logger.info("医生上传会员的血糖数据成功");
//            }else{
//                re.setState(1);
//                re.setMessage("医生上传会员的血糖数据失败");
//                logger.info("医生上传会员的血糖数据失败");
//            }
//        }else{
//            re.setState(1);
//            re.setMessage("医生上传会员的血糖数据失败！");
//            logger.info("医生上传会员的血糖数据到事件主表失败！");
//            return JSON.toJSONString(re);
//        }
        
        /* V3.0版本开始去掉分表，主键自动生成  begin */
        /* 保存omds表  begin*/
        Omds omds = new Omds();
        omds.setDataType(2);
        omds.setMemberId(memberId);
        omds.setStatusTag(2);
        omds.setUploadTime(obsr.getUploadTime());
        // 结果不为零标示异常
        if (obsrResult != 0) {
            omds.setWheAbnTag(1);
        }
        long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
        /* 保存omds表  end*/
        
        /* 保存血糖obsr表  begin*/
        obsr.setEventId(eventId);
        obsr.setUploadTime(uploadTime);
        obsr.setMemberId(memberId);
        long docentry = measureDao.insertObsrReturnPrimaryKey(obsr);
        obsr.setId(docentry);
        /* 保存血糖obsr表  end*/
        /* V3.0版本开始去掉分表，主键自动生成  end */
        re.setState(0);
        re.setMessage("医生上传会员的血糖数据成功");
        re.setContent(obsr);
        logger.info("医生上传会员的血糖数据成功");
        return JSON.toJSONString(re);
    }
    
    /** 
     * @Title: uploadMemMini 
     * @Description: 医生上传会员的心电mini数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemMini(HttpServletRequest request) throws Exception{
        ReturnResult re = new ReturnResult();
        String uploadTime = TimeUtil.currentDatetime();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        Oecg oecg = JSON.parseObject(callValue.getParam(), Oecg.class);
        
        int memberId = oecg.getMemberId();
        if(memberId <= 0){
            re.setState(1);
            re.setMessage("参数memberId："+memberId+"应为大于0的正整数");
            logger.info("参数memberId："+memberId+"应为大于0的正整数");
            return JSON.toJSONString(re);
        }
        /*验证mini心电数据是否重复  begin */
        int countOldOecg = measureDao.checkHasOecgRecord(callValue.getMemberId(), oecg.getMeasureTime());
        if(countOldOecg > 0){
            re.setState(11);
            re.setMessage("该MINI心电数据已存在，请勿重复上传");
            logger.info("该MINI心电数据已存在，请勿重复上传");
            return JSON.toJSONString(re);
        }
        /*验证mini心电数据是否重复  end */
        
        String rawEcg = null;

        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile ecg_file = mRequest.getFile(Constants.ECG_PARAM_FILE);                
        // 获取保存文件id
        rawEcg = mongoEntityDao.saveFile(Constants.MDB_FILE, ecg_file,oecg.getInComing());
        oecg.setRawEcg(rawEcg);
        oecg.setMemberId(memberId);

//        // 获取事件id
//        long event_id = docDao.insertEventId();
//        oecg.setEventId(event_id);
//        oecg.setUploadTime(uploadTime);
//        /** 保存omds表开始 **/
//        Omds omds = new Omds();
//        omds.setDataType(4);
//        omds.setEventId(event_id);
//        omds.setMemberId(memberId);
//        omds.setStatusTag(2);
//        omds.setUploadTime(oecg.getUploadTime());
//        int count = docDao.saveMemOmdsData(omds);
//        if(count > 0){
//            long oecgId = docDao.insertId("oecg_docentry");
//            oecg.setId(oecgId);
//            int countOecg = docDao.uploadMemMini(oecg);
//            if(countOecg > 0){
//                String message = sendMemMiniMessage(oecg);
//                ClientSender.sender(message);
//                re.setState(0);
//                re.setMessage("医生上传会员的心电mini数据成功");
//                re.setContent("{\"id\":" + oecgId + "}");
//                logger.info("医生上传会员的心电mini数据成功");
//            }else{
//                re.setState(1);
//                re.setMessage("医生上传会员的心电mini数据失败");
//                logger.info("医生上传会员的心电mini数据失败");
//            }
//        }else{
//            re.setState(1);
//            re.setMessage("医生上传会员的心电mini数据失败！");
//            logger.info("医生上传会员的心电mini数据到事件主表失败！");
//            return JSON.toJSONString(re);
//        }
        
        /* V3.0版本开始去掉分表，主键自动生成  begin */
        /* 保存omds表  begin*/
        Omds omds = new Omds();
        omds.setDataType(4);
        omds.setMemberId(memberId);
        omds.setStatusTag(2);
        omds.setUploadTime(oecg.getUploadTime());
        long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
        /* 保存omds表  end*/
        
        /* 保存心电oecg表  begin*/
        oecg.setRawEcg(rawEcg);
        oecg.setMemberId(memberId);
        oecg.setEventId(eventId);
        oecg.setUploadTime(uploadTime);
        long docentry = measureDao.insertOecgReturnPrimaryKey(oecg);
        oecg.setId(docentry);
        /* 保存心电oecg表  end*/
        /* V3.0版本开始去掉分表，主键自动生成  end */
        
        String message = sendMemMiniMessage(oecg);
        ClientSender.sender(message);
        re.setState(0);
        re.setMessage("医生上传会员的心电mini数据成功");
        re.setContent("{\"id\":" + docentry + "}");
        logger.info("医生上传会员的心电mini数据成功");
        return JSON.toJSONString(re);
    }
    
    /** 
     * @Title: uploadMemThreeInOne 
     * @Description: 医生上传会员的三合一数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemThreeInOne(HttpServletRequest request) throws Exception{
        ReturnValue re = new ReturnValue();
        String uploadTime = TimeUtil.currentDatetime();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        ThreeParam param = JSON.parseObject(callValue.getParam(),ThreeParam.class);
        int memberId = param.getMemberId();
        if(memberId <= 0){
            re.setState(1);
            re.setMessage("参数memberId："+memberId+"应为大于0的正整数");
            logger.info("参数memberId："+memberId+"应为大于0的正整数");
            return JSON.toJSONString(re);
        }
        /*验证三合一数据是否重复  begin */
        int countOldOppg = measureDao.checkHasThreeInOneRecord(memberId, param.getMeasureTime());
        if(countOldOppg > 0){
            re.setState(11);
            re.setMessage("该三合一数据已存在，请勿重复上传");
            logger.info("该三合一数据已存在，请勿重复上传");
            return JSON.toJSONString(re);
        }
        /*验证三合一数据是否重复  end */
        
        Osbp osbp = measureDao.findObsrByMemberId(memberId);
        if (osbp == null) {
            re.setState(4);
            re.setMessage("没有相应的血压测量信息");
            logger.info("没有相应的血压测量信息");
            return JSON.toJSONString(re);
        }

        Omem omem = memDao.findOmembyId(memberId);
        if (omem.getBirthDate() == null || "".equals(omem.getBirthDate())) {
            re.setState(5);
            re.setMessage("无法获取该会员的年龄");
            return JSON.toJSONString(re);
        }
        Oecg oecg = new Oecg();

        oecg.setMemberId(memberId);
//        oecg.setDeviceCode("SIAT3IN1_E");
//        oecg.setBluetoothMacAddr(param.getBluetoothMacAddr());
//        oecg.setFs(param.getEcgFs());
//        oecg.setTimeLength(param.getTimeLength());
//        oecg.setMeasureTime(param.getMeasureTime());
//        Oppg oppg = new Oppg();
//        oppg.setMemberId(memberId);
//        oppg.setDeviceCode(param.getDeviceCode());
//        oppg.setBluetoothMacAddr(param.getBluetoothMacAddr());
//        oppg.setFs(param.getPpgFs());
//        oppg.setTimeLength(param.getTimeLength());
//        oppg.setMeasureTime(param.getMeasureTime());
//        oppg.setSpo(param.getSpo());

        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile ecg_file = mRequest.getFile(Constants.ECG_PARAM_FILE);
        MultipartFile ppg_file = mRequest.getFile(Constants.PPG_PARAM_FILE);
         
        
        
        // 获取保存文件id
        String rawEcg_ecg = mongoEntityDao.saveFile(Constants.MDB_FILE,ecg_file,oecg.getInComing());
        String rawEcg_ppg = mongoEntityDao.saveFile(Constants.MDB_FILE,ppg_file,null);
//        oecg.setRawEcg(rawEcg_ecg);
//        oppg.setRawPpg(rawEcg_ppg);
//
//        // 获取事件id
//        long event_id = docDao.insertEventId();
//        oecg.setEventId(event_id);
//        oppg.setEventId(event_id);
//        oecg.setUploadTime(uploadTime);
//        oppg.setUploadTime(uploadTime);
//        /** 保存omds表开始 **/
//        Omds omds = new Omds();
//        omds.setDataType(3);
//        omds.setEventId(event_id);
//        omds.setMemberId(memberId);
//        omds.setStatusTag(2);
//        omds.setUploadTime(oecg.getUploadTime());
//        int count = docDao.saveMemOmdsData(omds);
//        if(count > 0){
//            long ecgId = docDao.insertId("oecg_docentry");
//            long ppgId = docDao.insertId("oppg_docentry");
//            oecg.setId(ecgId);
//            oppg.setId(ppgId);
//            int countOecg = docDao.uploadMemMini(oecg);
//            int countOppg = docDao.uploadMemPulse(oppg);
//            if(countOecg > 0 && countOppg > 0){
//                String message = sendMemThreeInOneMessage(oecg, oppg, osbp, omem);
//                ClientSender.sender(message);
//                re.setState(0);
//                re.setMessage("医生上传会员的三合一数据成功");
//                re.setContent("{\"ecg_id\":" + ecgId + ",\"ppg_id\":" + ppgId + "}");
//                logger.info("医生上传会员的三合一数据成功");
//            }else{
//                re.setState(1);
//                re.setMessage("医生上传会员的三合一数据失败");
//                logger.info("医生上传会员的三合一数据失败");
//            }
//        }else{
//            re.setState(1);
//            re.setMessage("医生上传会员的三合一数据失败！");
//            logger.info("医生上传会员的三合一数据到事件主表失败！");
//            return JSON.toJSONString(re);
//        }
        
        /* V3.0版本开始去掉分表，主键自动生成  begin */
        /* 保存omds表  begin*/
        Omds omds = new Omds();
        omds.setDataType(3);
        omds.setMemberId(memberId);
        omds.setStatusTag(2);
        omds.setUploadTime(oecg.getUploadTime());
        long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
        /* 保存omds表  end*/
        
        /* 保存心电oecg表  begin*/
        oecg.setMemberId(memberId);
        oecg.setDeviceCode("SIAT3IN1_E");
        oecg.setBluetoothMacAddr(param.getBluetoothMacAddr());
        oecg.setFs(param.getEcgFs());
        oecg.setTimeLength(param.getTimeLength());
        oecg.setMeasureTime(param.getMeasureTime());
        oecg.setRawEcg(rawEcg_ecg);
        oecg.setEventId(eventId);
        oecg.setUploadTime(uploadTime);
        long ecgid = measureDao.insertOecgReturnPrimaryKey(oecg);
        oecg.setId(ecgid);
        /* 保存心电oecg表  end*/
        
        /* 保存脉搏oppg表  begin*/
        Oppg oppg = new Oppg();
        oppg.setMemberId(memberId);
        oppg.setDeviceCode(param.getDeviceCode());
        oppg.setBluetoothMacAddr(param.getBluetoothMacAddr());
        oppg.setFs(param.getPpgFs());
        oppg.setTimeLength(param.getTimeLength());
        oppg.setMeasureTime(param.getMeasureTime());
        oppg.setSpo(param.getSpo());
        oppg.setRawPpg(rawEcg_ppg);
        oppg.setEventId(eventId);
        oppg.setUploadTime(uploadTime);
        long ppgid = measureDao.insertOppgReturnPrimaryKey(oppg);
        oecg.setId(ppgid);
        /* 保存脉搏oppg表  end*/
        /* V3.0版本开始去掉分表，主键自动生成  end */
        
        String message = sendMemThreeInOneMessage(oecg, oppg, osbp, omem);
        ClientSender.sender(message);
        re.setState(0);
        re.setMessage("医生上传会员的三合一数据成功");
        re.setContent("{\"ecg_id\":" + ecgid + ",\"ppg_id\":" + ppgid + "}");
        logger.info("医生上传会员的三合一数据成功");
        return JSON.toJSONString(re);
    }
    
    /** 
     * @Title: sendMemMiniMessage 
     * @Description: 发送心电mini消息到mq解析
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    private String sendMemMiniMessage(Oecg oecg)throws Exception {
        // ECG_FILTER|Debug,ecg,omds表ID,文件ID,数据类型，采样频率,设备DeviceCode,用户ID
        StringBuffer sb = new StringBuffer();
        sb.append("ECGHOLTER_ANALYZE|debug,");
        sb.append("ecg,");
        sb.append(oecg.getEventId()).append(",");
        sb.append(oecg.getRawEcg()).append(",");
        int dataType = oecg.getDataType();
        int newDataType = 1;
        if(dataType != 0){
            newDataType = dataType;
        }
        sb.append(newDataType).append(",");
        sb.append(oecg.getFs()).append(",");
        String deviceCode = oecg.getDeviceCode();
        int addValue = oecg.getAddValue();
        //老mini设备
        if(!StringUtils.isEmpty(deviceCode) && "SIAT_ELECECG".equals(deviceCode)){
            //数据类型为双字节()时 addValue的值为384;单字节的时 addValue的值为47
            if(newDataType == 1 || newDataType == 2){
                addValue = 47;
            }else{
                addValue = 384;
            }
        }
        //新mini设备
        else if(!StringUtils.isEmpty(deviceCode) && "ZKHK_ELECECG".equals(deviceCode)){
            addValue = 200;
        }
        sb.append(addValue).append(",");
        sb.append(oecg.getMemberId());
        return sb.toString();
    }

    /** 
     * @Title: sendMemThreeInOneMessage 
     * @Description: 发送三合一消息到mq解析
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    private String sendMemThreeInOneMessage(Oecg oecg, Oppg oppg, Osbp osbp,Omem omem) throws Exception {
        // EP_ANALYZE|Debug,ecg,omds表ID,ecg文件ID,Ecg采样频率,设备DeviceCode,ppg,omds表ID,ppg文件ID,ppg采样频率,血压高压,血压低压,身高,年龄,血氧饱和度,体重,用户ID"
        StringBuffer sb = new StringBuffer();
        sb.append("EP_ANALYZE|debug,");
        sb.append("ecg").append(",");
        sb.append(oecg.getEventId()).append(",");
        sb.append(oecg.getRawEcg()).append(",");
        sb.append(4).append(",");
        sb.append(oecg.getFs()).append(",");
        sb.append(200).append(",");
        sb.append("ppg").append(",");
        sb.append(oppg.getEventId()).append(",");
        sb.append(oppg.getRawPpg()).append(",");
        sb.append(1).append(",");
        sb.append(oppg.getFs()).append(",");
        sb.append(osbp.getSbp()).append(",");
        sb.append(osbp.getDbp()).append(",");
        MemFile mem = memDao.findMemFilebyId(oecg.getMemberId());
        sb.append(mem.getHeight()).append(",");
        sb.append(getAgeByBirthday(omem.getBirthDate())).append(",");
        sb.append(oppg.getSpo()).append(",");
        sb.append((int) mem.getWeight()).append(",");
        sb.append(oecg.getMemberId());
        return sb.toString();
    }
    
    /** 
     * @Title: getAgeByBirthday 
     * @Description: 通过生日算年龄
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun int
     */
    @SuppressWarnings("deprecation")
    private static int getAgeByBirthday(String birthDate)throws Exception {
        try{
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatDate = new SimpleDateFormat("YYYYMMdd");
            String currentTime = formatDate.format(calendar.getTime());
            Date today = formatDate.parse(currentTime);
            Date brithDay = formatDate.parse(birthDate);
            return today.getYear() - brithDay.getYear();
        }catch (Exception e) {
            return 0;
        }
    }
    
    /** 
     * @Title: getBloodPressureResult 
     * @Description: 分析血压数据异常状态 0 正常1 低血压2 高度高血压3 中度高血压4 轻度高血压5 单纯收缩高血压
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun int
     */
    private int getBloodPressureResult(Osbp osbp)throws Exception {
        int sbp = osbp.getSbp();
        int dbp = osbp.getDbp();
        if((sbp >= 140 && sbp < 150) && (dbp >= 60 && dbp < 90)){
            return 5; // "单纯收缩期高血压";
        }else if ((sbp >= 140 && sbp < 160) || (dbp >= 90 && dbp < 100)) {
            return 4; // "轻度高血压";
        }else if ((sbp >= 160 && sbp < 180) || (dbp >= 100 && dbp < 110)) {
            return 3; // "中度高血压";
        }else if (sbp >= 180 || dbp >= 110) {
            return 2; // "高度高血压";
        }else if (sbp < 90 || dbp < 60) {
            return 1; // "低血压";
        }else if ((sbp >= 90 && sbp <= 130) && (dbp >= 60 && dbp <= 85)) {
            return 0; // "正常";
        }else if ((sbp > 130 && sbp < 140) && (dbp > 85 && dbp < 90)) {
            return 6; // "正常偏高";
        }
        return 0;
    }

    /** 
     * @Title: getBloodGlucoseResult 
     * @Description: 分析血糖结果
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun int
     */
    private int getBloodGlucoseResult(Obsr obsr)throws Exception 
    {
        float value = obsr.getBsValue();
        int timePeriod = obsr.getTimePeriod();
        //分析结果值(1:血糖偏低;2:血糖偏高;0：正常)
        int analysisResult = 0; 
        //早晨空腹(1),午餐前(3),晚餐前(5),睡前(7),夜间(8)  值为：3.9-6.1mmol/L(正常值：包括前者，不包括后者)
        if(timePeriod == 1 || timePeriod == 3 || timePeriod == 5 || timePeriod == 7 || timePeriod == 8)
        {
            if(value < 3.9f)
            {
                analysisResult = 1; 
            }
            else if(value >= 6.1f)
            {
                analysisResult = 2;
            }
        }
        //早餐后2小时(2),午餐后2小时(4),晚餐后2小时(6)  值为：3.9-7.8mmol/L(正常值：包括前者，不包括后者)
        else if(timePeriod == 2 || timePeriod == 4 || timePeriod == 6)
        {
            if(value < 3.9f)
            {
                analysisResult = 1;
            }
            else if(value >= 7.8f)
            {
                analysisResult = 2;
            }
        }
        //随机血糖(0) 值为：3.9-11.1mmol/L(正常值：包括前者，不包括后者)
        else
        {
            if(value < 3.9f)
            {
                analysisResult = 1;
            }
            else if(value >= 11.1f)
            {
                analysisResult = 2;
            }
        }
        return analysisResult;
    }
    
    /** 
     * @Title: findMemMeasureRecordImgs 
     * @Description: 医生获取会员测量数据的图片 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-29
     * @throws Exception    
     * @retrun void
     */
    public void findMemMeasureRecordImgs(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try{
            String params = request.getParameter("params");
            CallValue value = JSON.parseObject(params, CallValue.class);
            ViewParam param = JSON.parseObject(value.getParam(),ViewParam.class);
            GridFSDBFile file = mongoEntityDao.retrieveFileOne("fs",new ObjectId(param.getRawImage()));
            if("ecg".equals(param.getType())){
                String data = JSON.toJSONString(makeEcgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
                request.setAttribute("highChareData", data);
            }else if("mi_ecg".equals(param.getType())){
                boolean isWeb = measureDao.findOecgByRawImg(param.getRawImage());
                String data =JSON.toJSONString  (makeMiEcgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight(),isWeb));
                request.setAttribute("highChareData", data);
            }else if("ppg".equals(param.getType())){
                String data =JSON.toJSONString  (makePpgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
                request.setAttribute("highChareData", data);
            }else if("hr_ecg".equals(param.getType())){
                String data =JSON.toJSONString  (makeHrecgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
                request.setAttribute("highChareData", data);
            }else if("hr_ppg".equals(param.getType())){
                String data =JSON.toJSONString  (makeHrppgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
                request.setAttribute("highChareData", data);
            }else if("ab_ecg".equals(param.getType())){
                String data =JSON.toJSONString(makeAbEcgDataForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight(),param.getDeviceCode()));
                request.setAttribute("highChareData", data);
            }
            logger.info("医生获取会员测量数据的图片成功！");
        }catch(Exception e1){
            logger.error("医生获取会员测量数据的图片异常！");
        }
    }
    
     /** 
     * @Title: makeEcgDataForJs 
     * @Description: 为js提供ceg心电数据 
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makeEcgDataForJs(GridFSDBFile file, int fs,int page,String measureTime ,int width,int height) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if(file == null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        map.put("type", "ecg");
        List<Integer> list=new ArrayList<Integer>();
        byte[] ubs  = Util.input2byte(file,(page-1)*12*fs,page*12*fs);
        for(int i = 0; i <  fs * 6*2; i += 2){
            if (i < ubs.length-1) {
                list.add((int) Util.getShort(ubs,i ));
            }else{
                break;
            }
        }
        if(ubs.length<fs*6*2){
            int num=(fs*6*2-ubs.length)/2;
            for (int i = 0; i < num; i++) {
                list.add( (int) 0);
            }
        }
        map.put("data", list);
        return map;
    }
    
    /** 
     * @Title: makePpgDataForJs 
     * @Description: 为js提供脉搏ppg数据
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makePpgDataForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height) throws Exception {
        
        Map<String, Object> map=new HashMap<String, Object>();
        if(file==null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        map.put("type", "ppg");
        List<Integer> list=new ArrayList<Integer>();
        byte[] bs = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
        for (int i = 0; i < fs * 6; i++) {
            if (i <bs.length) {
            list.add((int) bs[i]);  
            } else {
                break;
            }
        }
        if(bs.length<fs*6){
            int num=(fs*6-bs.length);
            for (int i = 0; i < num; i++) {
            list.add(0);
            }
        }
        map.put("data", list);
        return map;
    }
    
    /** 
     * @Title: createAbEcgDataForJs 
     * @Description: 为js提供异常心电abEcg数据
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makeAbEcgDataForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height,String deviceCode) throws Exception {
        Map< String, Object> map = new HashMap<String, Object>();
        if(file == null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime", TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        if(deviceCode!= null && "SIAT3IN1_E".equals(deviceCode)){  
            map.put("type", "ab_ecg");
        }else if(deviceCode != null && "MINIHOLTER_E".equals(deviceCode)) {
            map.put("type", "ab_ecg1");
        }else if(deviceCode != null && "SIAT_ELECECG".equals(deviceCode)) {
            map.put("type", "ab_ecg1");
        }else if(deviceCode != null && "ZKHK_ELECECG".equals(deviceCode)) {
            map.put("type", "ab_ecg1");
        }else {
            map.put("type", "ab_ecg2");
        }
        List<Float> list=new ArrayList<Float>();
        try{
            byte[] bs = Util.input3byte(file);  
            String message = new String(bs);
            String[] hr = message.split("\n{1,}");
            for(String i : hr){
                list.add(Float.parseFloat(i));
            } 
            map.put("data", list);
        }catch (Exception e){
            logger.error("获取坐标数据异常");
        }
        return map;
    }
    
    /** 
     * @Title: createMiEcgDateForJs 
     * @Description: 为js提供mini心电MiEcg数据
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makeMiEcgDataForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height,boolean isWeb) throws Exception {
        Map< String, Object> map = new HashMap<String, Object>();
        if(file==null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime", TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        List<Integer> list=new ArrayList<Integer>();
        try{
            byte[] ubs  = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
            for (int i = 0; i <  fs * 6; i ++){
                if (i < ubs.length){
                    //有符合行单字节
                    if(isWeb){      
                        list.add((int) ubs[i]);
                        map.put("type", "mi_ecg");
                    }else{
                        //无符号型单字节
                        list.add((ubs[i]&0x0FF)-128);
                        map.put("type", "mi_ecg");
                    }
                }else{
                    break;
                }
            }
            if(ubs.length<fs*6){
                int num=(fs*6-ubs.length);
                for (int i = 0; i < num; i++) {
                    list.add(0);    
                }
            }
            map.put("data", list);
        }catch (IOException e) {
            logger.error("获取坐标数据异常");
        }
        return map;
    }
    
    /** 
     * @Title: makeHrppgDataForJs 
     * @Description: 为js提供Hrppg数据
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makeHrppgDataForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if(file == null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        map.put("type", "hr_ppg");
        String startTime = TimeUtil.formatDatetime2(TimeUtil.datetimeFormat.parse(measureTime));
        String endTime = "";
    
        byte[] ubs = Util.input3byte(file);
        float time= 0;
        String message = new String(ubs);
        String[] hr = message.split("\n{1,}");
        for (int i = 0; i < hr.length; i++) {
            time+=(float)Float.parseFloat(hr[i])/1000;  
        }
        endTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<Float> list=new ArrayList<Float>();        
        for (int i = 0; i < hr.length-1; i++) {
          list.add(60 * 1000 /Float.parseFloat(hr[i])) ;    
        }
        map.put("data", list);
        return map;
    }
    
    /** 
     * @Title: makeHrecgDataForJs 
     * @Description: 为js提供Hrecg数据
     * @author liuxiaoqin
     * @createDate 2016-02-29
     */
    private Map<String, Object> makeHrecgDataForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if(file == null){
            map.put("msg", false);
            return map;
        }
        map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
        map.put("page", page);
        map.put("fs", fs);
        map.put("width", width);
        map.put("height", height);
        map.put("type", "hr_ecg");
        String startTime = null;
        String endTime = null;
    
        byte[] ubs = Util.input2byte(file,0,page*684*2);
        byte[] b = new byte[2];
    
        float time=0;
        for(int i = 0; i <ubs.length; i += 2){
            if (i <=(ubs.length-1)){
                b[0] = ubs[i];
                b[1] = ubs[i + 1];
                short value=Util.getShort(b, false);
                time+= (float)value/1000;
                if(i==((page-1)*684*2-1)||i==((page-1)*684*2+1)||i==((page-1)*684*2)){
                    startTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),6 * (page - 1)));
                }                   
            }else{
                break;
            }
        }

        endTime=TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<Float> list=new ArrayList<Float>();
        for (int i = (page-1)*684*2; i <ubs.length; i += 2){
            if (i <=(ubs.length-1)) {
                b[0] = ubs[i];
                b[1] = ubs[i + 1];
                list.add( 60*1000/(float)Util.getShort(b, false));      
            }else{
                break;
            }
        }
        map.put("data", list);
        return map;
    }
    
    /** 
     * @Title: findMemHeadImg 
     * @Description: 医生获取会员头像
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-04-08
     * @throws Exception    
     * @retrun String
     */
    public String findMemHeadImg(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject param = JSON.parseObject(callValue.getParam());
        Integer memberId = param.getInteger("memberId");
        if(memberId < 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        String headImg = docDao.findMemHeadImg(memberId);
        if(!StringUtils.isEmpty(headImg)){
            result.setState(0);
            result.setMessage("医生获取会员头像成功");
            result.setContent(headImg);
            logger.info("医生获取会员头像成功");
        }else{
            result.setState(3);
            result.setMessage("查询不到该会员头像");
            logger.info("查询不到该会员头像");
        }
        return JSON.toJSONString(result);
    }
    
}
