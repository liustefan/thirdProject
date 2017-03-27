package com.zkhk.entity;
/**
 * 推送消息对象
 * @author bit
 *
 */
public class MessageData {

	private int memberId;//事件产生人
	
	private String memName;//发送人姓名
	
	private int type;//事件类型

	public int getMemberId() {
		return memberId;
	}

	
	public MessageData() {
		
	}


	public MessageData(int memberId, String memName, int type) {
		super();
		this.memberId = memberId;
		this.memName = memName;
		this.type = type;
	}


	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}
