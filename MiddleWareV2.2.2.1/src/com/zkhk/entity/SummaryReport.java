package com.zkhk.entity;

/**
 * 会员汇总报告
 * @author bit
 *
 */
public class SummaryReport {
	private int id;//汇总测量报告ID
	
	private String sumRepCode;//汇总测量报告编号
	
	private String tempName;//汇总测量报告名称，取osms汇总报告审核模板TempName字段
	
	private String grenerTime;//产生时间，格式如：20141110143021

	private Object chkDesc;//医生审核意见
	
	private String relations;//关联单项测量报告ID，如果存在多个以","分开，如："12,13,16"
	
	/* 是否已读报告：0:已读;1:未读  */
	private int readStatus;
	
	private String auditTiem; //审核时间
	
	private String doctorSignature;//医生签名
	
	/* 审核医生名字 */
	private String auditDocName; 
	
	public String getAuditTiem() {
		return auditTiem;
	}

	public void setAuditTiem(String auditTiem) {
		this.auditTiem = auditTiem;
	}

	public String getDoctorSignature() {
		return doctorSignature;
	}

	public void setDoctorSignature(String doctorSignature) {
		this.doctorSignature = doctorSignature;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSumRepCode() {
		return sumRepCode;
	}

	public void setSumRepCode(String sumRepCode) {
		this.sumRepCode = sumRepCode;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getGrenerTime() {
		return grenerTime;
	}

	public void setGrenerTime(String grenerTime) {
		this.grenerTime = grenerTime;
	}



	public Object getChkDesc() {
		return chkDesc;
	}

	public void setChkDesc(Object chkDesc) {
		this.chkDesc = chkDesc;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

	public String getAuditDocName() {
		return auditDocName;
	}

	public void setAuditDocName(String auditDocName) {
		this.auditDocName = auditDocName;
	}


}
