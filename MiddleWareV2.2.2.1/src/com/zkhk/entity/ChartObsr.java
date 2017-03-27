package com.zkhk.entity;

import java.sql.Timestamp;



/**
 * 血糖chart图数据实体
 * @author 谢美团
 *
 */
public class ChartObsr implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long docentry;
	private Long eventId;
	private Integer memberid;
	private Timestamp testTime;
	private Timestamp uploadTime;
	private String delTag;
	private Double bsValue;
	private String timePeriod;
	private String analysisResult;
	private String deviceCode;
	private String bluetoothMacAddr;

	private String TestTimes;
	private String days;
	private double max;
	private double min;
	private double last;
	private String NE;
	private int num;
	
	/** ��ֵ��Χ*/
	private NormalValues nv;
	// Constructors

	/** default constructor */
	public ChartObsr() {
	}

	/** minimal constructor */
	public ChartObsr(Long docentry) {
		this.docentry = docentry;
	}

	/** full constructor */
	public ChartObsr(Long docentry, Long eventId, Integer memberid, Timestamp uploadTime, String delTag,
			Double bsValue, String timePeriod, String analysisResult, String deviceCode,
			String bluetoothMacAddr) {
		this.docentry = docentry;
		this.eventId = eventId;
		this.memberid = memberid;
		this.uploadTime = uploadTime;
		this.delTag = delTag;
		this.bsValue = bsValue;
		this.timePeriod = timePeriod;
		this.analysisResult = analysisResult;
		this.deviceCode = deviceCode;
		this.bluetoothMacAddr = bluetoothMacAddr;
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

	public String getDelTag() {
		return this.delTag;
	}

	public void setDelTag(String delTag) {
		this.delTag = delTag;
	}

	public Double getBsValue() {
		return this.bsValue;
	}

	public void setBsValue(Double bsValue) {
		this.bsValue = bsValue;
	}

	public String getTimePeriod() {
		return this.timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getAnalysisResult() {
		return this.analysisResult;
	}

	public void setAnalysisResult(String analysisResult) {
		this.analysisResult = analysisResult;
	}

	public String getDeviceCode() {
		return this.deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getBluetoothMacAddr() {
		return this.bluetoothMacAddr;
	}

	public void setBluetoothMacAddr(String bluetoothMacAddr) {
		this.bluetoothMacAddr = bluetoothMacAddr;
	}

	public String getTestTimes() {
		return TestTimes;
	}

	public void setTestTimes(String testTimes) {
		TestTimes = testTimes;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public String getNE() {
		return NE;
	}

	public void setNE(String nE) {
		NE = nE;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Obsr [docentry=" + docentry + ", eventId=" + eventId
				+ ", memberid=" + memberid + ", uploadTime=" + uploadTime
				+ ", delTag=" + delTag + ", bsValue=" + bsValue
				+ ", timePeriod=" + timePeriod + ", analysisResult="
				+ analysisResult + ", deviceCode=" + deviceCode
				+ ", bluetoothMacAddr=" + bluetoothMacAddr 
				+ ", TestTimes=" + TestTimes
				+ ", days=" + days
				+ ", max=" + max 
				+ ", min=" + min 
				+ ", last=" + min 
				+ ", NE=" + NE
				+ ", num=" + num
				+ "]";
	}

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}
	
	public NormalValues getNv() {
		return nv;
	}

	public void setNv(String timePeriod) {
		switch(timePeriod){
			case "0":
				this.nv = NormalValues.KEY0;
				break;
			case "1":
				this.nv = NormalValues.KEY1;
				break;
			case "2":
				this.nv = NormalValues.KEY2;
				break;
			case "3":
				this.nv = NormalValues.KEY3;
				break;
			case "4":
				this.nv = NormalValues.KEY4;
				break;
			case "5":
				this.nv = NormalValues.KEY5;
				break;
			case "6":
				this.nv = NormalValues.KEY6;
				break;
			case "7":
				this.nv = NormalValues.KEY7;
				break;
			case "8":
				this.nv = NormalValues.KEY8;
				break;
			default:
				this.nv = NormalValues.KEY0;
		}
	}

	public String getTimePeriodName(){
		String [] strs=new String [9];
		strs[0]="其他（其他随机测量）";
		strs[1]="早晨空腹血糖";
		strs[2]="早餐后2小时";
		strs[3]="午餐前";
		strs[4]="午餐后2小时";
		strs[5]="晚餐前";
		strs[6]="晚餐后2小时";
		strs[7]="睡前";
		strs[8]="夜间（下半夜）";
		return strs[Integer.parseInt(this.timePeriod)];
	}

	public enum NormalValues{
		KEY0(4.4, 7.8),//其他（其他随机测量）
		KEY1(3.9, 6.1),//早晨空腹血溏
		KEY2(4.4, 7.8),//早餐后2小时
		KEY3(4.4, 7.8),//午餐前
		KEY4(4.4, 7.8),//午餐后2小时
		KEY5(4.4, 7.8),//晚餐前
		KEY6(4.4, 7.8),//晚餐后2小时
		KEY7(4.4, 7.8),//睡前
		KEY8(4.4, 7.8);//夜间（下半夜）
		private double min;
		private double max;
		private NormalValues(double min, double max){
			this.min = min;
			this.max = max;
		}
		public double getMin() {
			return min;
		}
		public void setMin(double min) {
			this.min = min;
		}
		public double getMax() {
			return max;
		}
		public void setMax(double max) {
			this.max = max;
		}
		
	}
}