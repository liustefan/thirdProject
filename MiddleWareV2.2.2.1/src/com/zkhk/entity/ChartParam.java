package com.zkhk.entity;


/**
 * chart图请求参数实体
 * @author 谢美团 
 * @date 2015.10.22
 * @version V1.0
 */
public class ChartParam {

	private int memberId;//会员Id
    
	private int reportId;//报告ID
	
	private int dataType;//数据类型，1:血压,2:血糖,3:三合一,4:心电

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	

	
	
	
	
}
