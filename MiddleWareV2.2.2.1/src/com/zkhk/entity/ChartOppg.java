package com.zkhk.entity;

import java.sql.Timestamp;

/**
 * ChartOppg entity. @author MyEclipse Persistence Tools
 */

public class ChartOppg implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Long docentry;
	private Long eventId;
	private Integer memberid;
	private Integer timeLength;
	private Timestamp uploadTime;
	private Timestamp measureTime;
	private Short pulsebeatCount;
	private Short slowPulse;
	private Short slowTime;
	private Short quickPulse;
	private Short quickTime;
	private Short pulseRate;
	private Short spo;
	private Short spo1;
	private Double co;
	private Double sI;
	private Double sv;
	private Double ci;
	private Double spi;
	private Double k;
	private Double k1;
	private Double v;
	private Double tpr;
	private Double pWTT;
	private Double pm;
	private Double ac;
	private Short imageNum;
	private Short prlevel;
	private Short klevel;
	private Short svlevel;
	private Short colevel;
	private Short aclevel;
	private Short silevel;
	private Short vlevel;
	private Short tprlevel;
	private Short spolevel;
	private Short cilevel;
	private Short spilevel;
	private Short pwttlevel;
	private Integer abnormalData;
	private String ppgrr;
	private String rawPpg;
	private String bluetoothMacAddr;
	private String deviceCode;
	private Short icount;
	private Integer addValue;
	private Short fs;
	private Short ppgresult;
	private Short statusTag;
	private String delTag;
	
	private Integer averageHeart;
	
	private String MeasureTimes;
	private int prNum;
	private int kNum;
	private int svNum;
	private int coNum;
	private int acNum;
	private int siNum;
	private int vNum;
	private int tprnum;
	private int spoNum;
	private int ciNum;
	private int spiNum;
	private int pmNum;
	
	// Constructors

	public Integer getAverageHeart() {
		return averageHeart;
	}

	public void setAverageHeart(Integer averageHeart) {
		this.averageHeart = averageHeart;
	}

	/** default constructor */
	public ChartOppg() {
	}

	/** minimal constructor */
	public ChartOppg(Long docentry) {
		this.docentry = docentry;
	}

	/** full constructor */
	public ChartOppg(Long docentry, Long eventId, Integer memberid, Integer timeLength,Timestamp uploadTime, Timestamp measureTime,
			Short pulsebeatCount, Short slowPulse, Short slowTime, Short quickPulse, Short quickTime, Short pulseRate,
			Short spo, Short spo1, Double co,Double sI, Double sv, Double ci, Double spi, Double k,Double k1, Double v, Double tpr,
			Double pWTT,Double pm,Double ac, Short imageNum, Short prlevel, Short klevel, Short svlevel, Short colevel, Short aclevel,
			Short silevel, Short vlevel, Short tprlevel, Short spolevel, Short cilevel, Short spilevel,
			Short pwttlevel, Integer abnormalData, String ppgrr, String rawPpg, String bluetoothMacAddr,
			String deviceCode, Short icount, Integer addValue, Short fs, Short ppgresult, Short statusTag, String delTag) {
		this.docentry = docentry;
		this.eventId = eventId;
		this.memberid = memberid;
		this.timeLength = timeLength;
		this.uploadTime = uploadTime;
		this.measureTime = measureTime;
		this.pulsebeatCount = pulsebeatCount;
		this.slowPulse = slowPulse;
		this.slowTime = slowTime;
		this.quickPulse = quickPulse;
		this.quickTime = quickTime;
		this.pulseRate = pulseRate;
		this.spo = spo;
		this.spo1 = spo1;
		this.co = co;
		this.sI = sI;
		this.sv = sv;
		this.ci = ci;
		this.spi = spi;
		this.k = k;
		this.k1 = k1;
		this.v = v;
		this.tpr = tpr;
		this.pWTT = pWTT;
		this.pm = pm;
		this.ac = ac;
		this.imageNum = imageNum;
		this.prlevel = prlevel;
		this.klevel = klevel;
		this.svlevel = svlevel;
		this.colevel = colevel;
		this.aclevel = aclevel;
		this.silevel = silevel;
		this.vlevel = vlevel;
		this.tprlevel = tprlevel;
		this.spolevel = spolevel;
		this.cilevel = cilevel;
		this.spilevel = spilevel;
		this.pwttlevel = pwttlevel;
		this.abnormalData = abnormalData;
		this.ppgrr = ppgrr;
		this.rawPpg = rawPpg;
		this.bluetoothMacAddr = bluetoothMacAddr;
		this.deviceCode = deviceCode;
		this.icount = icount;
		this.addValue = addValue;
		this.fs = fs;
		this.ppgresult = ppgresult;
		this.statusTag = statusTag;
		this.delTag = delTag;
	}

	// Property accessors

	public Long getDocentry() {
		return this.docentry;
	}

	public void setDocentry(Long docentry) {
		this.docentry = docentry;
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getTimeLength() {
		return this.timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	public Timestamp getMeasureTime() {
		return this.measureTime;
	}

	public void setMeasureTime(Timestamp measureTime) {
		this.measureTime = measureTime;
	}

	public Short getPulsebeatCount() {
		return this.pulsebeatCount;
	}

	public void setPulsebeatCount(Short pulsebeatCount) {
		this.pulsebeatCount = pulsebeatCount;
	}

	public Short getSlowPulse() {
		return this.slowPulse;
	}

	public void setSlowPulse(Short slowPulse) {
		this.slowPulse = slowPulse;
	}

	public Short getSlowTime() {
		return this.slowTime;
	}

	public void setSlowTime(Short slowTime) {
		this.slowTime = slowTime;
	}

	public Short getQuickPulse() {
		return this.quickPulse;
	}

	public void setQuickPulse(Short quickPulse) {
		this.quickPulse = quickPulse;
	}

	public Short getQuickTime() {
		return this.quickTime;
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Double getsI() {
		return sI;
	}

	public void setsI(Double sI) {
		this.sI = sI;
	}

	public Double getK1() {
		return k1;
	}

	public void setK1(Double k1) {
		this.k1 = k1;
	}

	public Double getpWTT() {
		return pWTT;
	}

	public void setpWTT(Double pWTT) {
		this.pWTT = pWTT;
	}

	public Double getPm() {
		return pm;
	}

	public void setPm(Double pm) {
		this.pm = pm;
	}

	public void setQuickTime(Short quickTime) {
		this.quickTime = quickTime;
	}

	public Short getPulseRate() {
		return this.pulseRate;
	}

	public void setPulseRate(Short pulseRate) {
		this.pulseRate = pulseRate;
	}

	public Short getSpo() {
		return this.spo;
	}

	public void setSpo(Short spo) {
		this.spo = spo;
	}

	public Short getSpo1() {
		return this.spo1;
	}

	public void setSpo1(Short spo1) {
		this.spo1 = spo1;
	}

	public Double getCo() {
		return this.co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getSv() {
		return this.sv;
	}

	public void setSv(Double sv) {
		this.sv = sv;
	}

	public Double getCi() {
		return this.ci;
	}

	public void setCi(Double ci) {
		this.ci = ci;
	}

	public Double getSpi() {
		return this.spi;
	}

	public void setSpi(Double spi) {
		this.spi = spi;
	}

	public Double getK() {
		return this.k;
	}

	public void setK(Double k) {
		this.k = k;
	}

	public Double getV() {
		return this.v;
	}

	public void setV(Double v) {
		this.v = v;
	}

	public Double getTpr() {
		return this.tpr;
	}

	public void setTpr(Double tpr) {
		this.tpr = tpr;
	}

	public Double getAc() {
		return this.ac;
	}

	public void setAc(Double ac) {
		this.ac = ac;
	}

	public Short getImageNum() {
		return this.imageNum;
	}

	public void setImageNum(Short imageNum) {
		this.imageNum = imageNum;
	}

	public Short getPrlevel() {
		return this.prlevel;
	}

	public void setPrlevel(Short prlevel) {
		this.prlevel = prlevel;
	}

	public Short getKlevel() {
		return this.klevel;
	}

	public void setKlevel(Short klevel) {
		this.klevel = klevel;
	}

	public Short getSvlevel() {
		return this.svlevel;
	}

	public void setSvlevel(Short svlevel) {
		this.svlevel = svlevel;
	}

	public Short getColevel() {
		return this.colevel;
	}

	public void setColevel(Short colevel) {
		this.colevel = colevel;
	}

	public Short getAclevel() {
		return this.aclevel;
	}

	public void setAclevel(Short aclevel) {
		this.aclevel = aclevel;
	}

	public Short getSilevel() {
		return this.silevel;
	}

	public void setSilevel(Short silevel) {
		this.silevel = silevel;
	}

	public Short getVlevel() {
		return this.vlevel;
	}

	public void setVlevel(Short vlevel) {
		this.vlevel = vlevel;
	}

	public Short getTprlevel() {
		return this.tprlevel;
	}

	public void setTprlevel(Short tprlevel) {
		this.tprlevel = tprlevel;
	}

	public Short getSpolevel() {
		return this.spolevel;
	}

	public void setSpolevel(Short spolevel) {
		this.spolevel = spolevel;
	}

	public Short getCilevel() {
		return this.cilevel;
	}

	public void setCilevel(Short cilevel) {
		this.cilevel = cilevel;
	}

	public Short getSpilevel() {
		return this.spilevel;
	}

	public void setSpilevel(Short spilevel) {
		this.spilevel = spilevel;
	}

	public Short getPwttlevel() {
		return this.pwttlevel;
	}

	public void setPwttlevel(Short pwttlevel) {
		this.pwttlevel = pwttlevel;
	}

	public Integer getAbnormalData() {
		return this.abnormalData;
	}

	public void setAbnormalData(Integer abnormalData) {
		this.abnormalData = abnormalData;
	}

	public String getPpgrr() {
		return this.ppgrr;
	}

	public void setPpgrr(String ppgrr) {
		this.ppgrr = ppgrr;
	}

	public String getRawPpg() {
		return this.rawPpg;
	}

	public void setRawPpg(String rawPpg) {
		this.rawPpg = rawPpg;
	}

	public String getBluetoothMacAddr() {
		return this.bluetoothMacAddr;
	}

	public void setBluetoothMacAddr(String bluetoothMacAddr) {
		this.bluetoothMacAddr = bluetoothMacAddr;
	}

	public String getDeviceCode() {
		return this.deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Short getIcount() {
		return this.icount;
	}

	public void setIcount(Short icount) {
		this.icount = icount;
	}

	public Integer getAddValue() {
		return this.addValue;
	}

	public void setAddValue(Integer addValue) {
		this.addValue = addValue;
	}

	public Short getFs() {
		return this.fs;
	}

	public void setFs(Short fs) {
		this.fs = fs;
	}

	public Short getPpgresult() {
		return this.ppgresult;
	}

	public void setPpgresult(Short ppgresult) {
		this.ppgresult = ppgresult;
	}

	public Short getStatusTag() {
		return this.statusTag;
	}

	public void setStatusTag(Short statusTag) {
		this.statusTag = statusTag;
	}

	public String getDelTag() {
		return this.delTag;
	}

	public void setDelTag(String delTag) {
		this.delTag = delTag;
	}

	public String getMeasureTimes() {
		return MeasureTimes;
	}

	public void setMeasureTimes(String measureTimes) {
		MeasureTimes = measureTimes;
	}

	public int getPrNum() {
		return prNum;
	}

	public void setPrNum(int prNum) {
		this.prNum = prNum;
	}

	public int getSpoNum() {
		return spoNum;
	}

	public void setSpoNum(int spoNum) {
		this.spoNum = spoNum;
	}

	public int getAcNum() {
		return acNum;
	}

	public void setAcNum(int acNum) {
		this.acNum = acNum;
	}

	public int getvNum() {
		return vNum;
	}

	public void setvNum(int vNum) {
		this.vNum = vNum;
	}

	public int getkNum() {
		return kNum;
	}

	public void setkNum(int kNum) {
		this.kNum = kNum;
	}

	public int getSvNum() {
		return svNum;
	}

	public void setSvNum(int svNum) {
		this.svNum = svNum;
	}

	public int getCoNum() {
		return coNum;
	}

	public void setCoNum(int coNum) {
		this.coNum = coNum;
	}

	public int getSiNum() {
		return siNum;
	}

	public void setSiNum(int siNum) {
		this.siNum = siNum;
	}

	public int getTprnum() {
		return tprnum;
	}

	public void setTprnum(int tprnum) {
		this.tprnum = tprnum;
	}

	public int getCiNum() {
		return ciNum;
	}

	public void setCiNum(int ciNum) {
		this.ciNum = ciNum;
	}

	public int getSpiNum() {
		return spiNum;
	}

	public void setSpiNum(int spiNum) {
		this.spiNum = spiNum;
	}

	public int getPmNum() {
		return pmNum;
	}

	public void setPmNum(int pmNum) {
		this.pmNum = pmNum;
	}
	
  	public enum Item{
  		PR("pulseRate","prlevel","平均心率"),//
  		K("k","klevel","波形特征量"),	//
  		SV("sv","svlevel","心脏每搏射血量"),	//
  		CO("co","colevel","平均每分射血量"),	//
  		AC("ac","aclevel","血管顺应度"),	//
  		SI("sI","silevel","血管硬化指数"),	//
  		V("v","vlevel","血液粘度"),	//
  		TPR("tpr","tprlevel","外周阻力"),	//
  		SPO("spo","spolevel","血氧饱和度"),	//
  		CI("ci","cilevel","心指数"),	//
  		SPI("spi","spilevel","心搏指数"),	//
  		PM("pm","pm","平均动脉压");	//	70-105
  		private String code;
  		private String title;
  		private String level;
  		private Item(String code, String level, String title){
  			this.code = code;
  			this.title = title;
  			this.level = level;
  		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
  		
  	}
	
	@Override
	public String toString() {
		return "Oppg [docentry=" + docentry + ", eventId=" + eventId
				+ ", memberid=" + memberid + ", timeLength=" + timeLength
				+ ", measureTime=" + measureTime + ", pulsebeatCount="
				+ pulsebeatCount + ", slowPulse=" + slowPulse + ", slowTime="
				+ slowTime + ", quickPulse=" + quickPulse + ", quickTime="
				+ quickTime + ", pulseRate=" + pulseRate + "]";
	}
}