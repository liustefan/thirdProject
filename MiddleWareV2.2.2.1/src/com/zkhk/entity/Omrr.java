package com.zkhk.entity;
/**
 * 单项测量报告
 * @author bit
 *
 */
public class Omrr {
  private int id; //单项测量报告id
  
  private String reportCode;//单项测量报告编号
  
  private String tempName;//汇总测量报告名称，取orts单项报告审核模板TempName字段
  
  private String measureType;//单项测量报告测量类型： ECG 心电 PPG 脉搏 BP  血压BS  血糖根据选项代码 OptId字段确定类型

  private String grenerTime;//产生时间，格式如： 20141110143021

  private String measTime;//测量记录起始时间，格式如：20141110143021

  private String measTermTime;//测量记录终止时间，格式如： 20141110143021

  private int measNum;//测量记录数量
  
  private long measCorrNo;//测量记录初始ID
  
  private long measCorrTermNo;//测量记录终止ID
  
  private String chkDesc;//医生审核意见
  
  

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getReportCode() {
	return reportCode;
}

public void setReportCode(String reportCode) {
	this.reportCode = reportCode;
}

public String getTempName() {
	return tempName;
}

public void setTempName(String tempName) {
	this.tempName = tempName;
}

public String getMeasureType() {
	return measureType;
}

public void setMeasureType(String measureType) {
	this.measureType = measureType;
}

public String getGrenerTime() {
	return grenerTime;
}

public void setGrenerTime(String grenerTime) {
	this.grenerTime = grenerTime;
}

public String getMeasTime() {
	return measTime;
}

public void setMeasTime(String measTime) {
	this.measTime = measTime;
}

public String getMeasTermTime() {
	return measTermTime;
}

public void setMeasTermTime(String measTermTime) {
	this.measTermTime = measTermTime;
}

public int getMeasNum() {
	return measNum;
}

public void setMeasNum(int measNum) {
	this.measNum = measNum;
}

public long getMeasCorrNo() {
	return measCorrNo;
}

public void setMeasCorrNo(long measCorrNo) {
	this.measCorrNo = measCorrNo;
}

public long getMeasCorrTermNo() {
	return measCorrTermNo;
}

public void setMeasCorrTermNo(long measCorrTermNo) {
	this.measCorrTermNo = measCorrTermNo;
}

public String getChkDesc() {
	return chkDesc;
}

public void setChkDesc(String chkDesc) {
	this.chkDesc = chkDesc;
}
	
}
