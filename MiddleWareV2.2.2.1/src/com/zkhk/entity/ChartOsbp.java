package com.zkhk.entity;

import java.sql.Timestamp;

/**
 *  血压Osbp chart图数据实体 
 *  @author 谢美团
 *  @date 2015.10.22
 *  @version v1.0
 */
public class ChartOsbp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long docentry;
	private Long eventId;
	private Integer memberid;
	private Timestamp uploadTime;
	private Timestamp testTime;
	private String delTag;
	private String abnormal;
	private String timePeriod;
	private Integer sbp;  //收缩压
	private Integer dbp;  //舒张压
	private Integer pulseRate;
	private String bluetoothMacAddr;
	private String deviceCode;
	
	private int num;		//附加计算用途
	private String TimeQ;	//时间区域
	private String XY;		//血压  正常、偏高、偏低
	private String TestTimes;

	// Constructors

	/** default constructor */
	public ChartOsbp() {
	}

	/** minimal constructor */
	public ChartOsbp(Long docentry) {
		this.docentry = docentry;
	}

	/** full constructor */
	public ChartOsbp(Long docentry, Long eventId, Integer memberid, Timestamp uploadTime,
			Timestamp testTime, String delTag, String abnormal, String timePeriod, Integer sbp,
			Integer dbp, Integer pulseRate, String bluetoothMacAddr, String deviceCode) {
		this.docentry = docentry;
		this.eventId = eventId;
		this.memberid = memberid;
		this.uploadTime = uploadTime;
		this.testTime = testTime;
		this.delTag = delTag;
		this.abnormal = abnormal;
		this.timePeriod = timePeriod;
		this.sbp = sbp;
		this.dbp = dbp;
		this.pulseRate = pulseRate;
		this.bluetoothMacAddr = bluetoothMacAddr;
		this.deviceCode = deviceCode;
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

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Timestamp getTestTime() {
		return this.testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public String getDelTag() {
		return this.delTag;
	}

	public void setDelTag(String delTag) {
		this.delTag = delTag;
	}

	public String getAbnormal() {
		return this.abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

	public String getTimePeriod() {
		return this.timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Integer getSbp() {
		return this.sbp;
	}

	public void setSbp(Integer sbp) {
		this.sbp = sbp;
	}

	public Integer getDbp() {
		return this.dbp;
	}

	public void setDbp(Integer dbp) {
		this.dbp = dbp;
	}

	public Integer getPulseRate() {
		return this.pulseRate;
	}

	public void setPulseRate(Integer pulseRate) {
		this.pulseRate = pulseRate;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTimeQ() {
		return TimeQ;
	}

	public void setTimeQ(String timeQ) {
		TimeQ = timeQ;
	}

	public String getXY() {
		return XY;
	}

	public void setXY(String xY) {
		XY = xY;
	}

	public String getTestTimes() {
		return TestTimes;
	}

	public void setTestTimes(String testTimes) {
		TestTimes = testTimes;
	}

	@Override
	public String toString() {
		return "Osbp [docentry=" + docentry + ", eventId=" + eventId
				+ ", memberid=" + memberid + ", uploadTime=" + uploadTime
				+ ", testTime=" + testTime + ", delTag=" + delTag
				+ ", abnormal=" + abnormal + ", timePeriod=" + timePeriod
				+ ", sbp=" + sbp + ", dbp=" + dbp + ", pulseRate=" + pulseRate
				+ ", bluetoothMacAddr=" + bluetoothMacAddr + ", deviceCode="
				+ deviceCode + "]";
	}

}