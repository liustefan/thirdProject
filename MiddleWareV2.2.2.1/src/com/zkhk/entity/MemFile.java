package com.zkhk.entity;
/**
 * 会员档案 ，表mem2
 * @author raojunming
 *
 */
public class MemFile {

	
	private int memberId; //会员id
	
	private String bloodType;//血型
	
	private String allergicHis;//是否有过敏史 N没有 Y 有
	
	private String  allergicHisName;//过敏名称
	
	private int  height;//身高 150到220 cm
	
	private float  weight;//40到300 kg
	
	private int  waist;//腰围
	
	private int  hip;//臀围
	
	private int  pulse;//脉搏
	
	private int  heartRate;//心率
	
	private int bloodH; //收缩压
	
	private  int bloodL;//舒张压
	
	private float fastingGlucose; //空腹血糖
	
	private int uricAcid; //尿酸
	
	private float  totalCholesterol;//总胆固醇
	
	private float  triglyceride;//甘油三酯
	
	private float  densityLipop;//高密度脂蛋白
	
	private float ldLip;//低密度脂蛋白
	
	/* 过敏史："有(青霉素)或者无" */
	private String allergic;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	

	public String getAllergicHis() {
		return allergicHis;
	}

	public void setAllergicHis(String allergicHis) {
		this.allergicHis = allergicHis;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getWaist() {
		return waist;
	}

	public void setWaist(int waist) {
		this.waist = waist;
	}

	public int getHip() {
		return hip;
	}

	public void setHip(int hip) {
		this.hip = hip;
	}

	public int getPulse() {
		return pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public int getBloodH() {
		return bloodH;
	}

	public void setBloodH(int bloodH) {
		this.bloodH = bloodH;
	}

	public int getBloodL() {
		return bloodL;
	}

	public void setBloodL(int bloodL) {
		this.bloodL = bloodL;
	}

	public float getFastingGlucose() {
		return fastingGlucose;
	}

	public void setFastingGlucose(float fastingGlucose) {
		this.fastingGlucose = fastingGlucose;
	}

	public int getUricAcid() {
		return uricAcid;
	}

	public void setUricAcid(int uricAcid) {
		this.uricAcid = uricAcid;
	}


	public float getTotalCholesterol() {
		return totalCholesterol;
	}

	public void setTotalCholesterol(float totalCholesterol) {
		this.totalCholesterol = totalCholesterol;
	}

	public float getTriglyceride() {
		return triglyceride;
	}

	public void setTriglyceride(float triglyceride) {
		this.triglyceride = triglyceride;
	}

	public float getDensityLipop() {
		return densityLipop;
	}

	public void setDensityLipop(float densityLipop) {
		this.densityLipop = densityLipop;
	}

	public float getLdLip() {
		return ldLip;
	}

	public void setLdLip(float ldLip) {
		this.ldLip = ldLip;
	}

	public void setLdLip(int ldLip) {
		this.ldLip = ldLip;
	}

	public String getAllergicHisName() {
		return allergicHisName;
	}

	public void setAllergicHisName(String allergicHisName) {
		this.allergicHisName = allergicHisName;
	}

	public String getAllergic() {
		return allergic;
	}

	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}
	
	
	
	
}
