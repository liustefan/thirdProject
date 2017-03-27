package com.zkhk.entity;



public class FocusMem {
	private int memberId;//会员id号
	private String name;//名称
	private String address;//地址
	private String tel;//电话
	private String idCard;//身份证
	private String headImg;//头像
	private int newsLetter=1;//关注设置新消息通知 1表示接受 2表示不接受
	private String gender;//性别：F 女性M 男性
	private int educationStatus;//教育状况:0 未知1 高中及以下	2 大专	3 本科4 硕士	5 博士
    private String  marryStatus;//婚姻状况
    private String occupation;//职业婚姻状况 Y 已婚   N 未婚
    private String email;//邮箱
    private String headAddress;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getEducationStatus() {
		return educationStatus;
	}
	public void setEducationStatus(int educationStatus) {
		this.educationStatus = educationStatus;
	}
	public String getMarryStatus() {
		return marryStatus;
	}
	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNewsLetter() {
		return newsLetter;
	}
	public void setNewsLetter(int newsLetter) {
		this.newsLetter = newsLetter;
	}
	public String getHeadAddress() {
		return headAddress;
	}
	public void setHeadAddress(String headAddress) {
		this.headAddress = headAddress;
	}
	
	
	
	
}
