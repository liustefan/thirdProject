package com.zkhk.entity;

import java.util.List;



/**
 * 单个问题对象
 * @author bit
 *
 */
public class Mfq1 {

	private int id;//问题ID
	
	private String qustClass;//问题归类
	
	private String ansType;//答案类型：1 单选2 多选3 填空4 单选+填空5 多选+填空 6 比较值

	private String proDes;//问题标题
	
	private int ansNum;//答案个数
	
	private int uproblemId;//关联问题ID
	
	private int uansid;//关联问题答案ID
	
	private List<Mfq11> anserList;//所有答案

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQustClass() {
		return qustClass;
	}

	public void setQustClass(String qustClass) {
		this.qustClass = qustClass;
	}

	public String getAnsType() {
		return ansType;
	}

	public void setAnsType(String ansType) {
		this.ansType = ansType;
	}

	public String getProDes() {
		return proDes;
	}

	public void setProDes(String proDes) {
		this.proDes = proDes;
	}

	public int getAnsNum() {
		return ansNum;
	}

	public void setAnsNum(int ansNum) {
		this.ansNum = ansNum;
	}

	public int getUproblemId() {
		return uproblemId;
	}

	public void setUproblemId(int uproblemId) {
		this.uproblemId = uproblemId;
	}

	public int getUansid() {
		return uansid;
	}

	public void setUansid(int uansid) {
		this.uansid = uansid;
	}

	public List<Mfq11> getAnserList() {
		return anserList;
	}

	public void setAnserList(List<Mfq11> anserList) {
		this.anserList = anserList;
	}
	
	
	
}
