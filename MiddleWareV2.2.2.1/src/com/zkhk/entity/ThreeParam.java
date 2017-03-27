package com.zkhk.entity;



/**
 * 三合一上传参数
 * @author bit
 *
 */
public class ThreeParam {

	private String deviceCode;//上传方式：Hand 手动或如果是蓝牙设备名称

	private String bluetoothMacAddr;//设备蓝牙地址
	
	private int ecgFs;//采样频率
	
	private int ppgFs;//采样频率

	private  int spo;//血氧饱和度
	 
	private String measureTime ;//测量时间
	
	private int timeLength ;//测量时长
	
	/* 会员ID  */
	private int memberId;
	
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

	public int getEcgFs() {
		return ecgFs;
	}

	public void setEcgFs(int ecgFs) {
		this.ecgFs = ecgFs;
	}

	public int getPpgFs() {
		return ppgFs;
	}

	public void setPpgFs(int ppgFs) {
		this.ppgFs = ppgFs;
	}

	public int getSpo() {
		return spo;
	}

	public void setSpo(int spo) {
		this.spo = spo;
	}

	public String getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}

	public int getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(int timeLength) {
		this.timeLength = timeLength;
	}

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

	
	
	
	
}
