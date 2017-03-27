package com.zkhk.entity;

import java.util.List;

/**
 * 所有的测量信息信息
 * @author bit
 *
 */
public class Allmeasure {
	private List<Oecg> oecgs;//心电数据包括 list的异常集合
	
	private List<Oppg> oppgs;//所有在异常脉搏
	
	private List<Obsr> obsrs;//所有在异常血糖
	
	private List<Osbp> osbps;//所有在异常血压



	public List<Oecg> getOecgs() {
		return oecgs;
	}

	public void setOecgs(List<Oecg> oecgs) {
		this.oecgs = oecgs;
	}

	public List<Oppg> getOppgs() {
		return oppgs;
	}

	public void setOppgs(List<Oppg> oppgs) {
		this.oppgs = oppgs;
	}

	public List<Obsr> getObsrs() {
		return obsrs;
	}

	public void setObsrs(List<Obsr> obsrs) {
		this.obsrs = obsrs;
	}

	public List<Osbp> getOsbps() {
		return osbps;
	}

	public void setOsbps(List<Osbp> osbps) {
		this.osbps = osbps;
	}
	
	

}
