package com.zkhk.entity;
/**
 * 套餐信息对象
 * @author raojunming
 *
 */
public class Opsp {

	private int packageCode;//套餐代码
	
	private int orgId;//组织id
	
	private String packageName;//名称
	
	private String packageDesc;//描述
	
	private int packageType;//类型
	
	private float price;//价格
	
	private int packageLevel;//等级
	
	private String createTime;//创建时间
	
	private int createId;//Id
	
	private String createName;//创建人
	
	private String userTag;//标记T表示使用，F表示过期不用

	public int getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(int packageCode) {
		this.packageCode = packageCode;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageDesc() {
		return packageDesc;
	}

	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(int packageLevel) {
		this.packageLevel = packageLevel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}
	
	
	
	
	
}
