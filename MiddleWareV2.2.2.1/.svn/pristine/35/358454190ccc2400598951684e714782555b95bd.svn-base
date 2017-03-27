package com.zkhk.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkhk.dao.FocusDao;
import com.zkhk.dao.MemDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.Allmeasure;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Focus;
import com.zkhk.entity.FocusMem;
import com.zkhk.entity.FocusRemark;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.Message;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.ReturnValue;
import com.zkhk.util.TimeUtil;

@Service("focusService")
public class FocusServiceImpl implements FocusService {
	  private Logger logger=Logger.getLogger(FocusServiceImpl.class);
      @Resource(name="focusDao")
      private FocusDao focusDao;
      @Resource(name = "memDao")
      private MemDao memDao;
      @Resource(name = "pushMessageDao")
  	  private PushMessageDao messageDao;
  
	public String getMyFocusByParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();

			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			// 更新同行状态信息
			List<Focus>  focusMems= focusDao.findMyFocusByMemberId(callValue.getMemberId());
			if ( focusMems.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString( focusMems));
				logger.info("查询我的关注信息列表成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到我的关注信息");
				logger.info("查询不到我的关注信息");
			}
			//更新或者保存会员查看关注的世界
			 //标记该会员已经查看过该关注会员的信息
	     	messageDao.updateMarkTagByNotifierAndType(callValue.getMemberId(),9);
			//focusDao.saveUpdateTag(callValue.getMemberId(),TimeUtil.formatDatetime2(new Date()));
	     	String messge = JSON.toJSONString(re);
	     	//System.out.println("输出信息为：" + messge);
		    return messge;
	}
	public String updateFocusByParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
	
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			Focus mem=JSON.parseObject(callValue.getParam(), Focus.class);
			String createTime=TimeUtil.formatDatetime2(new Date());
			mem.setCreateTime(createTime);
			// 更新同行状态信息
			    focusDao.updateFocusByParam(mem);
				re.setState(0);
				re.setMessage("成功");
				logger.info("修改成功");	
				/**推送消息----------------------开始**/
				Message pushMs=null;
				if("Y".equals(mem.getTag())){
					//关注方取消关注
					pushMs=messageDao.getPushMessageByMemberId(mem.getMemberId(),mem.getFocusId(),9);
					pushMs.setCreateTime(createTime);
					messageDao.add(pushMs);
				    //设置该用户已经接不收被关注方的最新消息
					messageDao.updateMarkTagByCreateId(mem.getFocusId(),mem.getMemberId());
					
				}else if("Z".equals(mem.getTag())) {
				    //被关注方取消
					pushMs=messageDao.getPushMessageByMemberId(mem.getFocusId(),mem.getMemberId(),9);
					pushMs.setCreateTime(createTime);
					messageDao.add(pushMs);
					messageDao.updateMarkTagByCreateId(mem.getFocusId(),mem.getMemberId());
				}else if (callValue.getMemberId()==mem.getFocusId())  {
					//被关注方修改接受状态
					pushMs=messageDao.getPushMessageByMemberId(mem.getFocusId(),mem.getMemberId(),9);
					pushMs.setCreateTime(createTime);
					messageDao.add(pushMs);
					messageDao.updateMarkTagByCreateId(mem.getFocusId(),mem.getMemberId(),mem.getFocusPed());
				}else {
					//被邀请方修改接受状态
					pushMs=messageDao.getPushMessageByMemberId(mem.getMemberId(),mem.getFocusId(),9);
					pushMs.setCreateTime(createTime);
					messageDao.add(pushMs);
					if(mem.getNewsLetter()==2){
						messageDao.updateMarkTagByCreateId(mem.getFocusId(),mem.getMemberId());
					}else {
						messageDao.updateMarkTagByCreateId(mem.getFocusId(),mem.getMemberId(),mem.getFocusPed());
					}
				}
				/**推送消息----------------------结束**/
				
		    return JSON.toJSONString(re);
	}
	public String getFocusMemsByParam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject object=JSON.parseObject(callValue.getParam());
			String ifWhere=object.getString("ifWhere");
			int page =object.getIntValue("page");
			// 更新同行状态信息
			List<FocusMem> omems= focusDao.getFocusMemsByParam(ifWhere,callValue.getMemberId(),page);
			if (omems.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(omems));
				logger.info("查询会员信息列表成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到相应的会员信息");
				logger.info("查询不到相应的会员信息");
			}
		   return JSON.toJSONString(re);
	}
	public String getInviteFocuseMem(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject object=JSON.parseObject(callValue.getParam());
			String ifWhere=object.getString("ifWhere");
			int page=object.getIntValue("page");
			// 更新同行状态信息
			List<FocusMem> omems= focusDao.getInviteFocuseMem(ifWhere,callValue.getMemberId(),page);
			if (omems.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(omems));
				logger.info("查询会员信息列表成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到相应的会员信息");
				logger.info("查询不到相应的会员信息");
			}
		return JSON.toJSONString(re);
	}
	public String getMyFocusedByParam(HttpServletRequest request) throws Exception{
		  ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			// 更新同行状态信息
			List<Focus>  focusMems= focusDao.getMyFocusedByParam(callValue.getMemberId());
			if ( focusMems.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString( focusMems));
				logger.info("查询已关注的会员信息列表成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到已关注的会员信息");
				logger.info("查询不到已关注的会员信息");
			}
		 return JSON.toJSONString(re);
	}
	public String getFocusedMeByParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			// 更新同行状态信息
			List<Focus>  focusMems= focusDao. getFocusedMeByParam(callValue.getMemberId());
			if ( focusMems.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString( focusMems));
				logger.info("查询关注我的会员信息列表成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到关注我的会员信息");
				logger.info("查询不到已关注我的会员信息");
			}
		return JSON.toJSONString(re);
	}
	
	public String addFocusByParam(HttpServletRequest request) throws Exception{
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			Focus mem=JSON.parseObject(callValue.getParam(), Focus.class);
			String createTime=TimeUtil.formatDatetime2(new Date());
			mem.setCreateTime(createTime);
			
			/** 会员已关注亲友数量限制   begin **/
			//获取会员现在已经关注的亲友(限制为最多关注20个人)
			//主动关注
			int focusType = mem.getFocusType();
			if(focusType == 1)
			{
			    int memberId = callValue.getMemberId();
	            List<Focus> myHasFocusedList = focusDao.getMyFocusedByParam(memberId);
	            int myHasFocusedPerson = myHasFocusedList.size();
	            if(myHasFocusedPerson > 20)
	            {
	                re.setState(2);
	                re.setMessage("该会员关注的亲友已经达到上限20个！");
	                logger.info("关注失败，该会员"+memberId +"关注的好友已经达到上限20个！" );
	                return JSON.toJSONString(re);
	            }
			}
			
			/** 会员已关注亲友数量限制   end **/
			
			// 更新同行状态信息
			int id = focusDao.addFocusByParam(mem);
			re.setState(0);
			re.setMessage("成功");
			re.setContent("{\"id\":"+id+"}");
			logger.info("添加成功");	
			
			/**推送消息----------------------开始**/
			Message pushMs=null;
			if(mem.getMemberId()==callValue.getMemberId()){
				pushMs=messageDao.getPushMessageByMemberId(mem.getMemberId(),mem.getFocusId(),9);
			}else {
				pushMs=messageDao.getPushMessageByMemberId(mem.getFocusId(),mem.getMemberId(),9);
			}
			pushMs.setCreateTime(createTime);
			messageDao.add(pushMs);
			/**推送消息----------------------结束**/
		    return JSON.toJSONString(re);
	}
	
	public String focusMemFile(HttpServletRequest request)throws Exception {
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            JSONObject object=JSON.parseObject(callValue.getParam());
            int focusId=object.getIntValue("focusId");
			MemFile memFile = memDao.findMemFilebyId(focusId);
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
	 * 获取所有的监护医生
	 * @param request
	 * @return
	 */
	public String getCustodyDoc(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
		     JSONObject object=JSON.parseObject(callValue.getParam());
	           int focusId=object.getIntValue("focusId");
			// 更新同行状态信息
			Set<Odoc>  odocs= focusDao.getCustodyDoc(focusId);
			if (odocs.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(odocs));
				logger.info("查询会员的监护医生基本信息成功");
				messageDao.updateMarkTagByCreateId(focusId, callValue.getMemberId(), 7);
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员的监护医生基本信息");
				logger.info("查询不到该会员的监护医生基本信息");
			}
		return JSON.toJSONString(re);
	}
	public String updateFocusRemarkByParam(HttpServletRequest request)throws Exception{
		
		ReturnValue re = new ReturnValue();
		try {
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			FocusRemark remark=JSON.parseObject(callValue.getParam(), FocusRemark.class);
			// 更新同行状态信息
			    focusDao.updateFocusRemarkByParam(remark);
				re.setState(0);
				re.setMessage("成功");
				logger.info("修改成功");		
		} catch (Exception e) {
			re.setState(1);
			re.setMessage("修改信息失败");
			logger.error("修改信息失败");
		}
		return JSON.toJSONString(re);
	}
	public String  getHeadImg(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
	     JSONObject object=JSON.parseObject(callValue.getParam());
          int memberId=object.getIntValue("memberId");
		 try {
			 
			focusDao.getHeadImgByMemberId(memberId);
			
		} catch (Exception e) {
			logger.error(e);
		}
		 return JSON.toJSONString(re);
		
	}
	public String getFocusMeasure(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
	     JSONObject object=JSON.parseObject(callValue.getParam());
          int focusId=object.getIntValue("focusId");
          String startTime=object.getString("startTime");
          String endTime=object.getString("endTime");
          int page=object.getIntValue("page");
			 String eventIds=focusDao.getEventsByParam(focusId,startTime,endTime,page);
			// System.out.println("--------------"+eventIds);
			 if("".equals(eventIds)){
				
				 re.setState(1);
		         re.setMessage("查询不到关注会员的测量信息");
			 }else{
				 eventIds=eventIds.substring(0, eventIds.length()-1);
				 Allmeasure allmeasure=new Allmeasure();
				 List<Oecg> oecgs=focusDao.findEcgbyMemberIdAndTime(eventIds);
		         List<Oppg> oppgs=focusDao.findPpgbyMemberIdAndTime(eventIds);
		         List<Obsr> obsrs=focusDao.findBsrbyMemberIdAndTime(eventIds);
		         List<Osbp> osbps=focusDao.findSbpbyMemberIdAndTime(eventIds);
		         allmeasure.setOecgs(oecgs);
		         allmeasure.setObsrs(obsrs);
		         allmeasure.setOppgs(oppgs);
		         allmeasure.setOsbps(osbps);
		         re.setState(0);
		         re.setContent(JSON.toJSONString(allmeasure));
		         re.setMessage("查询关注会员的测量信息成功");
		        //标记该会员已经查看过该关注会员的信息
		     	messageDao.updateMarkTagByCreateId(focusId,callValue.getMemberId(),1);
		         
			 }
		 return JSON.toJSONString(re);
	}
	public String getFocusCount(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);     
			 int count=focusDao.getFocusCount(callValue.getMemberId());
			 if(count==0){
				 re.setState(1);
		         re.setMessage("没有新的关注信息");
			 }else{
		         re.setState(0);
		         re.setContent("{\"focusCount\":"+count+"}");
		         re.setMessage("查询验证信息成功");
			 }
		 return JSON.toJSONString(re);
	}
	public String getFocusMessage(HttpServletRequest request) throws Exception {
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);     
		Map<Integer, Map<Integer, List<Long>>>  ms=messageDao.getFocusMessage(callValue.getMemberId());
			 if(ms.size()==0){
				 re.setState(1);
		         re.setMessage("没有新的关注信息");
			 }else{
		         re.setState(0);
		         re.setContent(JSON.toJSONString(ms));
		         re.setMessage("查询验证信息成功");
			 }
		 return JSON.toJSONString(re);
	}
	

	
}
