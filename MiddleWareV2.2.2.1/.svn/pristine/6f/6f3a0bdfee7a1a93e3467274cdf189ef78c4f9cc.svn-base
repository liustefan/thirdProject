package com.zkhk.dao;

import java.util.List;
import java.util.Map;

import com.zkhk.entity.Message;

/**
 * 消息推送接口
 * @author bit
 *
 */
public interface PushMessageDao {
	/**
	 * 添加推送的消息
	 * @param 
	 * @throws Exception
	 */
	public void add(Message m) throws Exception;
    
	/***
	 * 获取测量信息的推送信息对象
	 * @param memberId
	 * @return
	 */
	public Message getPushMessageByMemberId(int memberId,int type);
	/**
	 * 关注模块的消息推送对象
	 * @param memberId 事件产生的人id
	 * @param notifier 被通知的id
	 * @param type
	 * @return
	 */
	public Message getPushMessageByMemberId(int memberId,int notifier,int type);

	
	/**
	 * 查询会员当前的信息通知
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	Map<Integer, Map<Integer, List<Long>>>  getFocusMessage(int memberId)throws Exception;
    /**
     * 标记用户已经读取了该用户的全部事件
     * @param createId 事件产生方的id 
     * @param memberId 事件接听方的id
     * @throws Exception
     */
	public void updateMarkTagByCreateId(int createId, int memberId)throws Exception;
    /**
     * 标记该用户去了该用户的某一类事件
     * @param createId
     * @param memberId
     * @param type 消息类型
     */
	public void updateMarkTagByCreateId(int createId, int memberId, int type)throws Exception;

	/**
	 * 标记用户查看自己关注的消息记录
	 * @param notifierId
	 * @param type
	 */
	public void updateMarkTagByNotifierAndType(int notifierId, int type)throws Exception;
    /***
     * 
     * @param tagTime
     */
	public void deletePushMessageByTagTime(String tagTime)throws Exception;

	/**
	 * 被关注修改信息是更新已经禁用的消息
	 * @param focusId
	 * @param memberId
	 * @param focusPed
	 * @throws Exception
	 */
	public void updateMarkTagByCreateId(int createId, int notifierId,String focusPed)throws Exception;
	
}
