package com.zkhk.entity;

import java.io.Serializable;

/**
 * @ClassName:     MemMeasureRecord.java 
 * @Description:   医生查看所属下的会员测量记录list
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月27日 下午4:13:46
*****/
public class MemMeasureRecord implements Serializable{
	
    /**
    * 
    */
    private static final long serialVersionUID = 3524066215881224891L;

    /*  测量记录id  */
    private int measureRecordId;
    
    /*  会员id  */
    private int memberId;
    
    /*  会员年龄   */
    private int age;
	
	/*  手机号码  */
	private String tel;
	
	/*  性别  */
    private String gender;
	
	/*  身份证  */
	private String idCard;
	
	/*  会员名字  */
	private String memberName;
	
	/*  测量时间('yyyy-mm-dd hh:mm:ss')  */
	private String measureTime;
	
	/* 1 血压 2 血糖 3 三合一 4 动态心电  */
	private int measureType;

    public int getMeasureRecordId() {
        return measureRecordId;
    }

    public void setMeasureRecordId(int measureRecordId) {
        this.measureRecordId = measureRecordId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
	
}
