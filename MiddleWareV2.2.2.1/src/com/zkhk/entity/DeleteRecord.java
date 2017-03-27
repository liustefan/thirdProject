package com.zkhk.entity;

/**
 * 所有的异常信息
 * @author bit
 *
 */
public class DeleteRecord {
	private int id ;
	
	private int memberId;
	
	private int dataType;
	
	/*  记录eventIds(批量删除) */
	private String eventIds;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
    public String getEventIds() {
        return eventIds;
    }
    public void setEventIds(String eventIds) {
        this.eventIds = eventIds;
    }
    @Override
	public String toString() {
		return "DeleteRecord [id=" + id + ", memberId=" + memberId
				+ ", dataType=" + dataType + "]";
	}
	
}
