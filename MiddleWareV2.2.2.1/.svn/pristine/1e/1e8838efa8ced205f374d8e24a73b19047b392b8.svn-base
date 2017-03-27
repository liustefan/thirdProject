package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 消息管理service
 * @author liuxiaoqin
 * @2015年6月23日
 */
public interface MsgManageService
{
	
	/**
	 * @Description 根据(会员id,接收时间，最近次数)来查找会员接收的消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	String findMsgByMemIdAndDate(HttpServletRequest request) throws Exception;
   
	/**
	 * @Description 会员删除消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	String updateMsgIsInvalid(HttpServletRequest request) throws Exception;

	/**
	 * @Description 根据id来查询某条消息的具体内容
	 * @author liuxiaoqin
	 * @2015年6月25日
	 */
	String findMsgById(HttpServletRequest request) throws Exception;
	
    /**
     * 保存会员更新信息
     * @param request
     * @return
     * @throws Exception
     */
	String saveMessageByParam(HttpServletRequest request) throws Exception;
	
	/**
     * @Description 根据会员id来查找所有未读取的新信息。
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
     */
    String findNewMsgList(HttpServletRequest request) throws Exception;
   
    /**
     * @Description 根据某msgId来读取某条未读信息
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
     */
    String findNewMsgByMsgId(HttpServletRequest request) throws Exception;
    
    /**
     * @Description 根据参数获取消息列表
     * @author liuxiaoqin
     * @CreateDate 2015年10月15日
    */
    public String findMsgListByParam(HttpServletRequest request) throws Exception;
}
