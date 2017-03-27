package com.zkhk.entity;

/**
 * 
 * @author xiemt
 *
 */

public class MemSearch extends Pagination {

	private int memberId;     

	private String idCard;
	
	private int doctorId; 
	
	private String memberName;
	
	private String password;
	
	private String searchParam;
	
	private String diseaseIds;
	
	/* 疾病id */
	private int diseaseId;
	
	/* 会员类型："exam":"体检会员" */
	private String memberType;


	public String getDiseaseIds() {
		return diseaseIds;
	}

	public void setDiseaseIds(String diseaseIds) {
		this.diseaseIds = diseaseIds;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Override
	public String toString() {
		return "MemSearch [memberId=" + memberId + ", idCard=" + idCard
				+ ", doctorId=" + doctorId + ", memberName=" + memberName
				+ ", password=" + password + ", searchParam=" + searchParam
				+ ", diseaseIds=" + diseaseIds + "]";
	}


	
}
