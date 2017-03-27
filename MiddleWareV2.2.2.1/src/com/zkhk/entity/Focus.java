package com.zkhk.entity;




//import com.zkhk.util.ImgHelper;

/**
 * 关注会员信息对象
 * @author bit
 *
 */
public class Focus {
	private long id; //关注的编号
	private String  relation; //关系（亲人朋友。。。）
	private int focusType;//关注类型（1，主动添加，2我要邀请）
	private String remark;//备注
	private int memberId;//关注方会员id
	private int focusId;//被关注方id
	private int focusStatus=1;//处理状态 1表示未处理 ，2表示接受 ，3表示拒绝
	private String focusP;//允许关注项，1,2,3（关注项的标志位）
	private String focusPed;//已经关注的项
	private String tag= "N";//是否已经取消关注 N表示未取消，Y表示已经关注方取消，Z表示被关注方取消
	private String name;//名称
	private String address;//地址
	private String tel;//电话
	private String idCard;//身份证
	private String headImg;//头像
	private String createTime;//申请时间
	private String gender;//性别：F 女性M 男性
	private int educationStatus;//教育状况:0 未知1 高中及以下	2 大专	3 本科4 硕士	5 博士
    private String  marryStatus;//婚姻状况
    private String occupation;//职业婚姻状况 Y 已婚   N 未婚
    private String email;//邮箱
    private int newsLetter=1;//关注设置新消息通知 1表示接受 2表示不接受 
    public String getHeadAddress() {
		return headAddress;
	}
	public void setHeadAddress(String headAddress) {
		this.headAddress = headAddress;
	}
	private String headAddress;//头像id

	public int getNewsLetter() {
		return newsLetter;
	}
	public void setNewsLetter(int newsLetter) {
		this.newsLetter = newsLetter;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public int getFocusType() {
		return focusType;
	}
	public void setFocusType(int focusType) {
		this.focusType = focusType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getFocusId() {
		return focusId;
	}
	public void setFocusId(int focusId) {
		this.focusId = focusId;
	}
	public int getFocusStatus() {
		return focusStatus;
	}
	public void setFocusStatus(int focusStatus) {
		this.focusStatus = focusStatus;
	}
	public String getFocusP() {
		return focusP;
	}
	public void setFocusP(String focusP) {
		this.focusP = focusP;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
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
	public String getFocusPed() {
		return focusPed;
	}
	public void setFocusPed(String focusPed) {
		this.focusPed = focusPed;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	
	

	
}
