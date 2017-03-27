package com.zkhk.entity;
/**
 * 疾病
 * @author liuxiaoqin
 *
 */
public class Disease {
    
   //会员ID
    private int memberId;//会员ID
    
    //疾病ID
    private  int diseaseId;
    
    //疾病名字
    private String diseaseName;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
    
	
}
