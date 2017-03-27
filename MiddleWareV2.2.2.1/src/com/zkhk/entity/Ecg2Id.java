package com.zkhk.entity;

/**
 * Ecg2Id entity. @author MyEclipse Persistence Tools
 */

public class Ecg2Id implements java.io.Serializable {

	// Fields

	private Long docentry;
	private String abnName;

	// Constructors

	/** default constructor */
	public Ecg2Id() {
	}

	/** full constructor */
	public Ecg2Id(Long docentry, String abnName) {
		this.docentry = docentry;
		this.abnName = abnName;
	}

	// Property accessors

	public Long getDocentry() {
		return this.docentry;
	}

	public void setDocentry(Long docentry) {
		this.docentry = docentry;
	}

	public String getAbnName() {
		return this.abnName;
	}

	public void setAbnName(String abnName) {
		this.abnName = abnName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Ecg2Id))
			return false;
		Ecg2Id castOther = (Ecg2Id) other;

		return ((this.getDocentry() == castOther.getDocentry()) || (this.getDocentry() != null
				&& castOther.getDocentry() != null && this.getDocentry().equals(
				castOther.getDocentry())))
				&& ((this.getAbnName() == castOther.getAbnName()) || (this.getAbnName() != null
						&& castOther.getAbnName() != null && this.getAbnName().equals(
						castOther.getAbnName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getDocentry() == null ? 0 : this.getDocentry().hashCode());
		result = 37 * result + (getAbnName() == null ? 0 : this.getAbnName().hashCode());
		return result;
	}

}