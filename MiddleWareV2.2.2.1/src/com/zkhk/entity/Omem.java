package com.zkhk.entity;



/**
 * 会员信息
 * @author raojunming
 *
 */
public class Omem {

	private int memberId; //会员id
	
	private int orgId; //组织代码id
	
	private int memId; //会员类型id 关联OMES
	
	private String memName; //会员姓名
	
	/* 新版-性别： 1-男;2-女;3-未知   */
	private String gender; //性别 F女性 ,M男性
	
	private String birthDate; //生日 格式: 1985-10-01
	
	private String tel; //手机号码
	
	private String email;//邮箱地址
	
	private String idCard;//身份证
	
	private String nativeAddr;//籍贯
	
	private String address;//地址
	
	/* 新版-婚姻状况：1未婚；2已婚；3初婚；4再婚；5复婚；6丧偶；7离异；8未说明的婚姻状况  */
	private String marryStatus;//婚姻状况 Y 已婚 N 未婚
	
	/* 新版-教育状况：1研究生及以上;2大学本科;3大学专科和专科学校;4中等专业学校;5技工学校;6高中;7初中;8小学;9文盲或半文盲;10学历不详;11无;  */
	private int educationStatus; //教育情况 教育状况:0 未知1 高中及以下2 大专3 本科4 硕士5 博士
	
	/* 新版-职业：1.农林牧渔水利业生产人员;2.生产运输设备操作人员及有关人员;3.专业技术人员;4.办事人员和有关人员;5.商业、服务业人员;
	 * 6.国家机关、党群组织、企事业单位负责人;7.在校学生;8.家务;9.待业;10.离退休人员;11.婴幼、学龄前儿童;12.军人;13.其他劳动者 */
	private String occupation; //职业
	
	private int docId;//医生id
	
	private String docName;//医生名称
	
	private String createTime;//创建时间
	
	/*  用户是否有效： T：有效;F:无效  */
	private String useTag;
	
    private String headImg;//会员头像信息

    private float  vitality;//活力指数 
    
    private String headAddress;
    
    private String integrity ; //资料完整度
    
    private String contactMobPhone ; //联系电话
    
    private String contactName ; //联系人姓名
    
    private String bloodType ; //血型
    
    private String height ; //身高
    
    private String weight ; //体重
    
    /*  PC端医生分页查询符合条件的会员总记录数  */
    private int totalRecord;
    
	public String getContactMobPhone() {
		return contactMobPhone;
	}

	public void setContactMobPhone(String contactMobPhone) {
		this.contactMobPhone = contactMobPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getIntegrity() {
		return integrity;
	}

	public void setIntegrity(String integrity) {
		this.integrity = integrity;
	}

	public String getHeadAddress() {
		return headAddress;
	}

	public void setHeadAddress(String headAddress) {
		this.headAddress = headAddress;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getMemId() {
		return memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNativeAddr() {
		return nativeAddr;
	}

	public void setNativeAddr(String nativeAddr) {
		this.nativeAddr = nativeAddr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}


	public int getEducationStatus() {
		return educationStatus;
	}

	public void setEducationStatus(int educationStatus) {
		this.educationStatus = educationStatus;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUseTag() {
		return useTag;
	}

	public void setUseTag(String useTag) {
		this.useTag = useTag;
	}

	public float getVitality() {
		return vitality;
	}

	public void setVitality(float vitality) {
		this.vitality = vitality;
	}

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

	
	
	
	
	
	
}
