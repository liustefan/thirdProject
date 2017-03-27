package com.zkhk.entity;

import java.util.List;

/**
 * 异常心电描述对象
 * @author bit
 *
 */
public class Ecg2 {

	private long id;
	 
	private String abnName;
	
	private int abnNum;
	
	private List<AnomalyEcg> ecgs;

	public String getAbnName() {
		return abnName;
	}

	public void setAbnName(String abnName) {
		this.abnName = abnName;
	}

	public int getAbnNum() {
		return abnNum;
	}

	public void setAbnNum(int abnNum) {
		this.abnNum = abnNum;
	}

	public List<AnomalyEcg> getEcgs() {
		return ecgs;
	}

	public void setEcgs(List<AnomalyEcg> ecgs) {
		this.ecgs = ecgs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	
	
}
