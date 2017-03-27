package com.zkhk.entity;

/**
 * 组合问卷对象
 * 
 * @author bit
 * 
 */
public class Ocam {
	private int id;//答卷ID

	//组合答卷状态: 0未作答;1 已作答 ;2 已审核;3作答中;4审核中
	private String combTag;

	private int CombQustId;//组合问卷ID

	private String combQustName;//组合问卷名

	private String publisherTime;//答卷发布时间，格式如：20141110143021

	private String docName;//发放问卷医生名

	private String relation;//关联答卷ID，多份以","分开，如:"12,23"(CAM1中存放级联关系)

	/* 组合答卷终审医生名建议参考CAM2表  */
	private String auditDesc;
	
	/* 组合答卷终审医生名字 */
	private String auditDoc;
	
	/* 组合答卷终审医生签名 */
    private String auditDocSignature;
	
	/* 组合答卷审核时间 */
	private String combAuditTime;
	
	/*  组合已审核,显示最终审医生的诊断结果  */
    private String combDiagnosis;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCombTag() {
        return combTag;
    }

    public void setCombTag(String combTag) {
        this.combTag = combTag;
    }

    public int getCombQustId() {
		return CombQustId;
	}

	public void setCombQustId(int combQustId) {
		CombQustId = combQustId;
	}

	public String getCombQustName() {
		return combQustName;
	}

	public void setCombQustName(String combQustName) {
		this.combQustName = combQustName;
	}

	public String getPublisherTime() {
		return publisherTime;
	}

	public void setPublisherTime(String publisherTime) {
		this.publisherTime = publisherTime;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getAuditDoc() {
		return auditDoc;
	}

	public void setAuditDoc(String auditDoc) {
		this.auditDoc = auditDoc;
	}

    public String getCombAuditTime() {
        return combAuditTime;
    }

    public void setCombAuditTime(String combAuditTime) {
        this.combAuditTime = combAuditTime;
    }

    public String getCombDiagnosis() {
        return combDiagnosis;
    }

    public void setCombDiagnosis(String combDiagnosis) {
        this.combDiagnosis = combDiagnosis;
    }

    public String getAuditDocSignature() {
        return auditDocSignature;
    }

    public void setAuditDocSignature(String auditDocSignature) {
        this.auditDocSignature = auditDocSignature;
    }
	
	
}
