package com.zkhk.entity;

import java.io.Serializable;

/**
  * @Description 组合问卷对象
  * @author liuxiaoqin
  * @CreateDate 2015年11月19日
*/
public class Ocqt implements Serializable{
    
	/**
     * 
     */
    private static final long serialVersionUID = -4442230091453763650L;

    /*  组合问卷id  */
    private int combQustId;

    /*  组合问卷编号  */
	private String combQustName;
	
	/*  组合问卷编号  */
	private int combQustCode;
	
	/*  组合问卷描述  */
	private String combQustDesc;

	/*  创建时间  */
	private String createDate;

	/*  是否审核  */
	private String chTag;

	/*  问卷状态 */
	private String qustTag;

	/*  创建人id */
	private int createId;
	
	/*  创建人姓名  */
	private String createName;
	
	/*  选项id  */
	private int optId;
	
	/*  组织机构id  */
	private int orgId;
	
	/*  问卷版本号  */
	private String qustVer;

    public int getCombQustId() {
        return combQustId;
    }

    public void setCombQustId(int combQustId) {
        this.combQustId = combQustId;
    }

    public String getCombQustName() {
        return combQustName;
    }

    public void setCombQustName(String combQustName) {
        this.combQustName = combQustName;
    }

    public int getCombQustCode() {
        return combQustCode;
    }

    public void setCombQustCode(int combQustCode) {
        this.combQustCode = combQustCode;
    }

    public String getCombQustDesc() {
        return combQustDesc;
    }

    public void setCombQustDesc(String combQustDesc) {
        this.combQustDesc = combQustDesc;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getChTag() {
        return chTag;
    }

    public void setChTag(String chTag) {
        this.chTag = chTag;
    }

    public String getQustTag() {
        return qustTag;
    }

    public void setQustTag(String qustTag) {
        this.qustTag = qustTag;
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

    public int getOptId() {
        return optId;
    }

    public void setOptId(int optId) {
        this.optId = optId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getQustVer() {
        return qustVer;
    }

    public void setQustVer(String qustVer) {
        this.qustVer = qustVer;
    }

	
}
