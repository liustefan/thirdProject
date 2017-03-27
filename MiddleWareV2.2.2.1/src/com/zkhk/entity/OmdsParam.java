package com.zkhk.entity;

/**
 * 会员档案信息查询表对象
 * @author bit
 *
 */
public class OmdsParam {
    
	
	
	private int memberId;//会员id
	
	private String timeStart;//开始时间
	
	private String timeEnd;//结束事件
	
	private int dataType;//数据类型，对应数据库表中EventType字段:1 血2 血糖3 三合一4 动态心电

	private String wheAbnTag;//0 正常 1 异常

	private int count; //本次获取多少条记录，不能超过100条
	
	/* 测量事件集合 1,2,3,4 */
	private String eventIds;
	
	/* 测量事件类型 1 血2 血糖3 三合一4 动态心电 */
	private int eventType;

	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public int getDataType() {
		return dataType;
	}
	public String getEventIds() {
		return eventIds;
	}
	public void setEventIds(String eventIds) {
		this.eventIds = eventIds;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getWheAbnTag() {
		return wheAbnTag;
	}
	public void setWheAbnTag(String wheAbnTag) {
		this.wheAbnTag = wheAbnTag;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	
}
