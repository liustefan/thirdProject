package com.zkhk.entity;
/**
 * 会员订购的套餐
 * @author raojunming
 *
 */
public class Mem5 {
    private int memberId;//会员ID
    
    private  Opsp packAge;//套餐信息
    
    private  int lineNum;
    
    private String tag;
    
    private String createTime;//创建时间：2015-03-02 00:00:00
    
    private int createId;//Id
    
    private String createName;//创建名称

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Opsp getPackAge() {
		return packAge;
	}

	public void setPackAge(Opsp packAge) {
		this.packAge = packAge;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}


	
}
