package com.zkhk.dao;

import java.util.List;
import com.zkhk.entity.MsgManage;
/**
 * @Description 消息管理接口
 * @author liuxiaoqin
 * @2015年6月19日
 */
public interface MsgManageDao {
	
	/**
	 * @Description 根据(会员id,接收时间，最近次数)来查找会员接收的消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	List<MsgManage> findMsgByMemIdAndDate(int memberId,String beginDate,String endDate, int recentTimes)  throws Exception;
	
	/**
	 * @Description 会员删除消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	void updateMsgIsInvalid(int msgId) throws Exception;
	
	/**
	 * @Description 根据id来查询某条消息的具体内容
	 * @author liuxiaoqin
	 * @2015年6月25日
	 */
	MsgManage findMsgById(int msgId) throws Exception;
	
	/**
     * @Description 根据会员id来查找所有未读取的新信息。
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
     */
    List<MsgManage> findNewMsgList(int memberId)  throws Exception;
   
   /**
    * @Description 根据某msgId来读取某条未读信息
    * @author liuxiaoqin
    * @CreateDate 2015年9月16日
    */
    public MsgManage findNewMsgByMsgId(int msgId) throws Exception;
  
    /**
    * @Description 修改该条信息为已读
    * @author liuxiaoqin
    * @2015年6月19日
    */
    public Integer updateMsgHasRead(int msgId) throws Exception;
    
    /**
     * @Description 根据参数获取消息列表
     * @author liuxiaoqin
     * @CreateDate 2015年10月15日
    */
    public List<MsgManage> findMsgListByParam(int memberId, int pageNo, int pageSize) throws Exception;
	
}
