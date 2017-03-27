package com.zkhk.entity;



/**
 * 
 * @author 谢美团
 * @version v1.0
 * @date 2015.10.23
 */
public class ChartEcg2 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ecg2Id id;
	private Integer abnNum;
	private Long docentry;
	private String abnName;
	
	private String aName;
	private String measTimes;
	private int num;

	// Constructors
	public ChartEcg2(Ecg2Id id, Integer abnNum, Long docentry, String abnName,
			String aName, String measTimes, int num) {
		super();
		this.id = id;
		this.abnNum = abnNum;
		this.docentry = docentry;
		this.abnName = abnName;
		this.aName = aName;
		this.measTimes = measTimes;
		this.num = num;
	}
	
	/** default constructor */
	public ChartEcg2() {
	}

	/** minimal constructor */
	public ChartEcg2(Ecg2Id id) {
		this.id = id;
	}

	/** full constructor */
	public ChartEcg2(Ecg2Id id, String abnName, Integer abnNum) {
		this.id = id;
		this.abnName = abnName;
		this.abnNum = abnNum;
	}

	// Property accessors

	public Ecg2Id getId() {
		return this.id;
	}

	public void setId(Ecg2Id id) {
		this.id = id;
	}

	public Integer getAbnNum() {
		return this.abnNum;
	}

	public void setAbnNum(Integer abnNum) {
		this.abnNum = abnNum;
	}

	
	public Long getDocentry() {
		return docentry;
	}

	public void setDocentry(Long docentry) {
		this.docentry = docentry;
	}

	public String getAbnName() {
		return abnName;
	}

	public void setAbnName(String abnName) {
		this.abnName = abnName;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getMeasTimes() {
		return measTimes;
	}

	public void setMeasTimes(String measTimes) {
		this.measTimes = measTimes;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Ecg2 [id=" + id + ", abnName=" +abnName+ ", abnNum=" + abnNum + "]";
	}

	/**�������*/
	public String getAbnNameCN() {
		if ("Polycardia".equals(this.abnName)) {
			return "心动过速";
		} else if ("Bradycardia".equals(this.abnName)) {
			return "心动过缓";
		} else if ("Arrest".equals(this.abnName)) {
			return "停搏";
		} else if ("Missed".equals(this.abnName)) {
			return "漏搏";
		} else if ("Wide".equals(this.abnName)) {
			return "宽搏";
		} else if ("PVB".equals(this.abnName)) {
			return "室性早搏";
		} else if ("PAB".equals(this.abnName)) {
			return "房性早搏";
		} else if ("Insert_PVB".equals(this.abnName)) {
			return "插入性室早搏";
		} else if ("VT".equals(this.abnName)) {
			return "阵发性心动过速";
		} else if ("Bigeminy".equals(this.abnName)) {
			return "二联律";
		} else if ("Trigeminy".equals(this.abnName)) {
			return "三联律";
		} else if ("Arrhythmia".equals(this.abnName)) {
			return "心律不齐";
		}
		return "";
	}
}