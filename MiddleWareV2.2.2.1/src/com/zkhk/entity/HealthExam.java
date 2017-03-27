package com.zkhk.entity;
/**
 * 会员体检信息表
 * @author hx
 * 2016年5月19日
 */
public class HealthExam {
	
	private long hExamID; //体检ID
	private int memberID; //会员ID
	private String idCard; //身份证号
	private String memName; //会员姓名
	private String gender; //性别
	private int age; //年龄
	private String examDate;
	
	public long getHExamID() {
		return hExamID;
	}
	public void setHExamID(long hExamID) {
		this.hExamID = hExamID;
	}
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	
	
}
