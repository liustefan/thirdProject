package com.zkhk.entity;

/**
 * 获取图片数据
 * @author bit
 *
 */
public class ViewParam {

	
	private String type;//设备类型 ecg表示心电 ppg表示脉搏
	
	private String rawImage;//源文件id
	
	private int fs;//采样频率
    
	private int page=1;//图片页数
	
	private String measureTime;//测量时间
	
	private int width=1000;//图片宽度
	
	private int height=300;//图片高度
	
	private String deviceCode;//该数据的上次设备
	
	

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRawImage() {
		return rawImage;
	}

	public void setRawImage(String rawImage) {
		this.rawImage = rawImage;
	}

	public int getFs() {
		return fs;
	}

	public void setFs(int fs) {
		this.fs = fs;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}



	
	
}
