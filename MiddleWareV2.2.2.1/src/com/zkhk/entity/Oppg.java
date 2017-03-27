package com.zkhk.entity;



/**
 * 脉搏
 * @author bit
 *
 */
public class Oppg {

	private long id;//数据ID
	
	private String uploadTime;//上传时间格式如：20141021143021
	
	private String measureTime;//测量时间格式如：20141021143021
	
	private long eventId;//测量记录ID
	
	private int timeLength;//测量时长
	
	private int pulsebeatCount;//总脉搏数
	
	private int slowPulse;//最小脉搏值
	
	private int slowTime;//最小脉搏发生时间
	
	private int quickPulse;//最大脉搏值
	
	private int quickTime;//最大脉搏发生时间
	
	private int  pulseRate;//平均脉搏
	
	private int spo;//血样饱和度
	
	private int  spo1;//SPO1
	
	private float co;//平均每分射血量 CO
	
	private float si;//SI
	
	private float sv;//心脏每搏射血量 SV
	
	private float ci;//心指数 CI
	
	private float spi;//心搏指数 SPI
	
	private float k;//波形特征量 K
	
	private float k1;//K1
	
	private float v;//血液粘度 V
	
	private float tpr;//外周阻力 TPR
	
	private float pwtt;//PWTT
	
	private float pm;//Pm
	
	private float ac;//血管顺应度 AC
	
	private int imageNum;//图片数量
	
	private int abnormalData;//是否异常
	
	private String ppgrr;//瞬时脉率图文件ID
	
	private String rawPpg;//原始测量数据文件ID
	
	private int ppgResult;//是否有异常0 正常1 异常
	
	private int StatusTag;//分析状态 0 未分析 1 分析失败 2 分析完成
	
	private int memberId;//会员id
	
	private String deviceCode;//上传方式：Hand 手动或如果是蓝牙设备名称

	private String bluetoothMacAddr;//设备蓝牙地址
	
	private int fs;//采样频率
	
	private int k1Level;
	private int svLevel;
	private int coLevel;
	private int siLevel;
	private int v1Level;
	private int tprLevel;
	private int spoLevel;
	private int ciLevel;
	private int spiLevel;
	private int pwttLevel;
	private int acLevel;
	private int prLevel;
	
	

    public int getPrLevel() {
		return prLevel;
	}

	public void setPrLevel(int prLevel) {
		this.prLevel = prLevel;
	}

	private int addValue=1;
	
	

	public int getAddValue() {
		return addValue;
	}

	public void setAddValue(int addValue) {
		this.addValue = addValue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}

	

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public int getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(int timeLength) {
		this.timeLength = timeLength;
	}

	public int getPulsebeatCount() {
		return pulsebeatCount;
	}

	public void setPulsebeatCount(int pulsebeatCount) {
		this.pulsebeatCount = pulsebeatCount;
	}

	public int getSlowPulse() {
		return slowPulse;
	}

	public void setSlowPulse(int slowPulse) {
		this.slowPulse = slowPulse;
	}

	public int getSlowTime() {
		return slowTime;
	}

	public void setSlowTime(int slowTime) {
		this.slowTime = slowTime;
	}

	public int getQuickPulse() {
		return quickPulse;
	}

	public void setQuickPulse(int quickPulse) {
		this.quickPulse = quickPulse;
	}

	public int getQuickTime() {
		return quickTime;
	}

	public void setQuickTime(int quickTime) {
		this.quickTime = quickTime;
	}

	public int getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(int pulseRate) {
		this.pulseRate = pulseRate;
	}

	public int getSpo() {
		return spo;
	}

	public void setSpo(int spo) {
		this.spo = spo;
	}

	public int getSpo1() {
		return spo1;
	}

	public void setSpo1(int spo1) {
		this.spo1 = spo1;
	}

	public float getCo() {
		return co;
	}

	public void setCo(float co) {
		this.co = co;
	}

	public float getSi() {
		return si;
	}

	public void setSi(float si) {
		this.si = si;
	}

	public float getSv() {
		return sv;
	}

	public void setSv(float sv) {
		this.sv = sv;
	}

	public float getCi() {
		return ci;
	}

	public void setCi(float ci) {
		this.ci = ci;
	}

	public float getSpi() {
		return spi;
	}

	public void setSpi(float spi) {
		this.spi = spi;
	}

	public float getK() {
		return k;
	}

	public void setK(float k) {
		this.k = k;
	}

	public float getK1() {
		return k1;
	}

	public void setK1(float k1) {
		this.k1 = k1;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}

	public float getTpr() {
		return tpr;
	}

	public void setTpr(float tpr) {
		this.tpr = tpr;
	}

	public float getPwtt() {
		return pwtt;
	}

	public void setPwtt(float pwtt) {
		this.pwtt = pwtt;
	}

	public float getPm() {
		return pm;
	}

	public void setPm(float pm) {
		this.pm = pm;
	}

	public float getAc() {
		return ac;
	}

	public void setAc(float ac) {
		this.ac = ac;
	}

	public int getImageNum() {
		return imageNum;
	}

	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public int getAbnormalData() {
		return abnormalData;
	}

	public void setAbnormalData(int abnormalData) {
		this.abnormalData = abnormalData;
	}

	public String getPpgrr() {
		return ppgrr;
	}

	public void setPpgrr(String ppgrr) {
		this.ppgrr = ppgrr;
	}

	public String getRawPpg() {
		return rawPpg;
	}

	public void setRawPpg(String rawPpg) {
		this.rawPpg = rawPpg;
	}

	public int getPpgResult() {
		return ppgResult;
	}

	public void setPpgResult(int ppgResult) {
		this.ppgResult = ppgResult;
	}

	public int getStatusTag() {
		return StatusTag;
	}

	public void setStatusTag(int statusTag) {
		StatusTag = statusTag;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getBluetoothMacAddr() {
		return bluetoothMacAddr;
	}

	public void setBluetoothMacAddr(String bluetoothMacAddr) {
		this.bluetoothMacAddr = bluetoothMacAddr;
	}

	public int getFs() {
		return fs;
	}

	public void setFs(int fs) {
		this.fs = fs;
	}



	public int getK1Level() {
		return k1Level;
	}

	public void setK1Level(int k1Level) {
		this.k1Level = k1Level;
	}

	public int getV1Level() {
		return v1Level;
	}

	public void setV1Level(int v1Level) {
		this.v1Level = v1Level;
	}

	public int getSvLevel() {
		return svLevel;
	}

	public void setSvLevel(int svLevel) {
		this.svLevel = svLevel;
	}

	public int getCoLevel() {
		return coLevel;
	}

	public void setCoLevel(int coLevel) {
		this.coLevel = coLevel;
	}

	public int getSiLevel() {
		return siLevel;
	}

	public void setSiLevel(int siLevel) {
		this.siLevel = siLevel;
	}



	public int getTprLevel() {
		return tprLevel;
	}

	public void setTprLevel(int tprLevel) {
		this.tprLevel = tprLevel;
	}

	public int getSpoLevel() {
		return spoLevel;
	}

	public void setSpoLevel(int spoLevel) {
		this.spoLevel = spoLevel;
	}

	public int getCiLevel() {
		return ciLevel;
	}

	public void setCiLevel(int ciLevel) {
		this.ciLevel = ciLevel;
	}

	public int getSpiLevel() {
		return spiLevel;
	}

	public void setSpiLevel(int spiLevel) {
		this.spiLevel = spiLevel;
	}

	public int getPwttLevel() {
		return pwttLevel;
	}

	public void setPwttLevel(int pwttLevel) {
		this.pwttLevel = pwttLevel;
	}

	public int getAcLevel() {
		return acLevel;
	}

	public void setAcLevel(int acLevel) {
		this.acLevel = acLevel;
	}

	
	
	
	
	
}
