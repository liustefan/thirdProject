package com.zkhk.entity;
/**
 * 关注备注信息
 * @author bit
 *
 */
public class FocusRemark {
   private int id;
   private int memberId;//备注方id
   private int remarkId;//被备注方id
   private String  relation; //关系（亲人朋友。。。）
   private String remark;//备注
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
public int getRemarkId() {
	return remarkId;
}
public void setRemarkId(int remarkId) {
	this.remarkId = remarkId;
}
public String getRelation() {
	return relation;
}
public void setRelation(String relation) {
	this.relation = relation;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
	
	
}
