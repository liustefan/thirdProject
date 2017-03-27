package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;


public interface FocusService {
	/**
	 * 获取我的关注信息
	 * 
	 * @param request
	 * @return
	 */
	String getMyFocusByParam(HttpServletRequest request)throws Exception;

	/**
	 * 修改关注的信息
	 * @param request
	 * @return
	 */
	String updateFocusByParam(HttpServletRequest request)throws Exception;
    /**
     * 获取会员可以被关注信息列表
     * @param request
     * @return
     */
	String getFocusMemsByParam(HttpServletRequest request)throws Exception;
    /**
     * 获取所有的符合条件的想邀请的好友信息
     * @param request
     * @return
     */
	String getInviteFocuseMem(HttpServletRequest request)throws Exception;
    /**
     * 获取所有的我已经关注的好友信息
     * @param request
     * @return
     */
	String getMyFocusedByParam(HttpServletRequest request)throws Exception;
    /**
     * 获取已经关注我的好友信息
     * @param request
     * @return
     */
	String getFocusedMeByParam(HttpServletRequest request)throws Exception;
     /**
      * 添加关注信息
      * @param request
      * @return
      */
	String addFocusByParam(HttpServletRequest request)throws Exception;
     /**
      * 获取关注方的健康档案
      * @param request
      * @return
      */
	String focusMemFile(HttpServletRequest request)throws Exception;
		
	/**
	 * 根据会员id查询监控医生的信息
	 * @param request
	 * @return
	 */
	String getCustodyDoc(HttpServletRequest request)throws Exception;
    /**
     * 修改会员备注信息
     * @param request
     * @return
     */
	String updateFocusRemarkByParam(HttpServletRequest request)throws Exception;
    /**
     * 获取会员头像
     * @param request
     * @param response
     */
	String getHeadImg(HttpServletRequest request)throws Exception;
   /**
    * 获取关注会员 测量信息
    * @param request
    * @return
    */
	String getFocusMeasure(HttpServletRequest request)throws Exception;
   /**
    * 获取更新信息记录
    * @param request
    * @return
    */
    String getFocusCount(HttpServletRequest request)throws Exception;
   /**
    * 查询所有的最新消息
    * @param request
    * @return
    * @throws Exception
    */
    String getFocusMessage(HttpServletRequest request)throws Exception;
	


}
