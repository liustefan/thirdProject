package com.zkhk.entity;

import java.util.List;

/**
 * @ClassName:     MemDisease.java 
 * @Description:   会员疾病
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月23日 上午11:07:16
*****/
public class MemDisease {
    
    /*  会员ID  */
	private int memberId;
	
	/* 行号  */
	private int lineNum;

	/*  疾病id  */
	private int diseaseId;
	
	/*  疾病名称  */
	private String diseaseName;

	/*  确诊时间  */
	private String diagTime;
	
	/*  既往标记 (N : 现病史  ,Y : 既往病史)  */
	private int proTag;
	
	/*  是否有该病的随访记录-- 1有；0无  */
    private int hasVisitRecord;
	
	private List<MemDisease> memDiseaseList;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiagTime() {
        return diagTime;
    }

    public void setDiagTime(String diagTime) {
        this.diagTime = diagTime;
    }

    public int getProTag() {
        return proTag;
    }

    public void setProTag(int proTag) {
        this.proTag = proTag;
    }

    public List<MemDisease> getMemDiseaseList() {
        return memDiseaseList;
    }

    public void setMemDiseaseList(List<MemDisease> memDiseaseList) {
        this.memDiseaseList = memDiseaseList;
    }

    public int getHasVisitRecord() {
        return hasVisitRecord;
    }

    public void setHasVisitRecord(int hasVisitRecord) {
        this.hasVisitRecord = hasVisitRecord;
    } 

}
