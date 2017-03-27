
package com.zkhk.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkhk.dao.MsgManageDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Message;
import com.zkhk.entity.MsgManage;
import com.zkhk.entity.ReturnValue;
import com.zkhk.util.TimeUtil;

@Service("msgManageService")
public class MsgManageServiceImpl implements MsgManageService {

	private Logger logger = Logger.getLogger(MsgManageServiceImpl.class);
	
	@Resource(name = "msgManageDao")
	private MsgManageDao msgManageDao;
	@Resource(name = "pushMessageDao")
	private PushMessageDao messageDao;
	/**
	 * @Description 根据(会员id,接收时间，最近次数)来查找会员接收的消息
	 * @author liuxiaoqin
	 * @2015年6月23日
	 */
	public String findMsgByMemIdAndDate(HttpServletRequest request) throws Exception 
	{
		ReturnValue re = new ReturnValue();
		
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
		
		List<MsgManage> msgList = new ArrayList<MsgManage>();
		int memberId = callValue.getMemberId();
		String beginDate = jsonObject.getString("beginDate");
		String endDate = jsonObject.getString("endDate");
		int recentTimes = 0;
		String time = jsonObject.getString("recentTimes");
		if(time != null && !"".equals(time))
		{
			recentTimes = Integer.valueOf(time);
		}
				
		if(beginDate != null && !"".equals(beginDate))
		{
			beginDate = beginDate + " 00:00:00";
		}
		if(endDate != null && !"".equals(endDate))
		{
			endDate = endDate + " 23:59:59";
		}
		msgList = msgManageDao.findMsgByMemIdAndDate(memberId, beginDate, endDate, recentTimes);
		if(msgList.size() > 0)
		{
			re.setState(0);
			re.setMessage("成功");
			re.setContent(JSON.toJSONString(msgList));
			logger.info("查询会员历史接收消息成功！");
		}
		else
		{
			re.setState(1);
			re.setMessage("查询不到该会员历史接收消息！");
			logger.info("查询不到该会员历史接收消息！");
		}
		return JSON.toJSONString(re);
	}

	/**
	 * @Description 会员删除消息
	 * @author liuxiaoqin
	 * @2015年6月23日
	 */
	public String updateMsgIsInvalid(HttpServletRequest request) throws Exception 
	{
		ReturnValue re = new ReturnValue();
		try 
		{
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
			
			// 更新消息状态为已删除。
            String msgIds = jsonObject.getString("msgIds");
            String[] ids = msgIds.split(",");
            for(int i = 0; i <ids.length ; i++)
            {
            	int msgId = Integer.valueOf(ids[i]);
            	msgManageDao.updateMsgIsInvalid(msgId);
            }
            re.setState(0);
            re.setMessage("删除成功");
            logger.info("删除成功");
            
		}
		catch (Exception e)
		{
			re.setState(1);
			re.setMessage("删除失败");
			logger.error("删除失败");
		}
		return JSON.toJSONString(re);
	}

	/**
	 * @Description 根据id来查询某条消息的具体内容
	 * @author liuxiaoqin
	 * @2015年6月25日
	 */
	public String findMsgById(HttpServletRequest request) throws Exception
	{
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
		
		MsgManage msg = new MsgManage();
		String msgId = jsonObject.getString("msgId");
        if(StringUtils.isEmpty(msgId))
        {
            re.setState(1);
            re.setMessage("查询参数msgId为空！");
            logger.info("查询参数msgId为空！");
            return JSON.toJSONString(re);
        }
        int newMsgId = Integer.valueOf(msgId);
        if(newMsgId <= 0)
        {
            re.setState(1);
            re.setMessage("查询参数msgId:" + newMsgId + "不是正整数！");
            logger.info("查询参数msgId:" + newMsgId + "不是正整数！");
            return JSON.toJSONString(re);
        }
		msg = msgManageDao.findMsgById(newMsgId);
		if(msg != null)
		{
			re.setState(0);
			re.setMessage("成功");
			re.setContent(JSON.toJSONString(msg));
			logger.info("查询消息成功！");
			//更新消息为已读（T）
			String hasRead = msg.getHasRead();
			if("F".equals(hasRead))
			{
			    Integer count = msgManageDao.updateMsgHasRead(newMsgId);
	            if(count <= 0)
	            {
	                re.setState(1);
	                re.setMessage("修改信息为已读状态失败！");
	                logger.info("修改信息为已读状态失败！");
	            }
			}
		}
		else
		{
			re.setState(1);
			re.setMessage("查询不到消息！");
			logger.info("查询不到消息！");
		}
		return JSON.toJSONString(re);
	}

	public String saveMessageByParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
		int msgType = jsonObject.getIntValue("messageType");
	    int memberId=jsonObject.getIntValue("memberId");
		/**推送消息----------------------开始**/
		Message pushMs=messageDao.getPushMessageByMemberId(memberId,msgType);
		pushMs.setCreateTime(TimeUtil.formatDatetime2(new Date()));
		messageDao.add(pushMs);
		/**推送消息----------------------结束**/
		re.setState(0);
		re.setMessage("推送信息保存成功");
		logger.info("查询消息成功！");
	
		return JSON.toJSONString(re);
	}
	
	/**
     * @Description 根据会员id来查找所有未读取的新信息。
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
   */
    public String findNewMsgList(HttpServletRequest request) throws Exception
    {
        ReturnValue re = new ReturnValue();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        List<MsgManage> msgList = new ArrayList<MsgManage>();
        int memberId = Integer.valueOf(jsonObject.getString("memberId"));
        msgList = msgManageDao.findNewMsgList(memberId);
        if(msgList.size() > 0)
        {
            re.setState(0);
            re.setMessage("成功");
            re.setContent(JSON.toJSONString(msgList));
            logger.info("查询会员未读消息成功！");
        }
        else
        {
            re.setState(1);
            re.setMessage("查询不到该会员未读消息！");
            logger.info("查询不到该会员未读消息！");
        }
        return JSON.toJSONString(re);
    }
   
   /**
     * @Description 根据某msgId来读取某条未读信息
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
   */
    public String findNewMsgByMsgId(HttpServletRequest request) throws Exception
    {
        ReturnValue re = new ReturnValue();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        
        MsgManage msg = new MsgManage();
        int msgId = Integer.valueOf(jsonObject.getString("msgId"));
        msg = msgManageDao.findNewMsgByMsgId(msgId);
        if(msg != null)
        {
            re.setState(0);
            re.setMessage("成功");
            re.setContent(JSON.toJSONString(msg));
            logger.info("查询未读消息成功！");
            Integer count = msgManageDao.updateMsgHasRead(msgId);
            if(count <= 0)
            {
                re.setState(2);
                re.setMessage("修改信息为已读状态失败！");
                logger.info("修改信息为已读状态失败！");
            }
        }
        else
        {
            re.setState(1);
            re.setMessage("查询不到消息！");
            logger.info("查询不到消息！");
        }
        return JSON.toJSONString(re);
    }
    
    /**
      * @Description 根据参数获取消息列表
      * @author liuxiaoqin
      * @CreateDate 2015年10月15日
    */
    public String findMsgListByParam(HttpServletRequest request) throws Exception
    {
        ReturnValue re = new ReturnValue();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        String memberId = jsonObject.getString("memberId");
        if(StringUtils.isEmpty(memberId)){
            re.setState(1);
            re.setMessage("查询参数memberId为空！");
            logger.info("查询参数memberId为空！");
            return JSON.toJSONString(re);
        }
        int newMemberId = Integer.valueOf(memberId);
        if(newMemberId <= 0){
            re.setState(1);
            re.setMessage("查询参数memberId:" + newMemberId + "不是正整数！");
            logger.info("查询参数memberId:" + newMemberId + "不是正整数！");
            return JSON.toJSONString(re);
        }
        
        /* 兼容老版本begin */
        String time = jsonObject.getString("recentTimes");
        if(!StringUtils.isEmpty(time)){
            return findMsgByMemIdAndDate(request);
        }
        /* 兼容老版本end */
        
        String pageNo = jsonObject.getString("pageNo");
        if(StringUtils.isEmpty(pageNo)){
            re.setState(1);
            re.setMessage("查询参数pageNo为空！");
            logger.info("查询参数pageNo为空！");
            return JSON.toJSONString(re);
        }
        int newPageNo = Integer.valueOf(pageNo);
        if(newPageNo <= 0){
            re.setState(1);
            re.setMessage("查询参数pageNo:" + newPageNo + "不是正整数！");
            logger.info("查询参数pageNo:" + newPageNo + "不是正整数！");
            return JSON.toJSONString(re);
        }
        String pageSize = jsonObject.getString("pageSize");
        if(StringUtils.isEmpty(pageSize)){
            re.setState(1);
            re.setMessage("查询参数pageSize为空！");
            logger.info("查询参数pageSize为空！");
            return JSON.toJSONString(re);
        }
        int newPageSize = Integer.valueOf(pageSize);
        if(newPageSize <= 0){
            re.setState(1);
            re.setMessage("查询参数pageSize:" + newPageSize + "不是正整数！");
            logger.info("查询参数pageSize:" + newPageSize + "不是正整数！");
            return JSON.toJSONString(re);
        }
        List<MsgManage> msgList = msgManageDao.findMsgListByParam(newMemberId, newPageNo, newPageSize);
        re.setState(0);
        re.setMessage("查询消息列表成功");
        re.setContent(JSON.toJSONString(msgList));
        logger.info("查询消息列表成功！");
        return JSON.toJSONString(re);
    }
	
}
