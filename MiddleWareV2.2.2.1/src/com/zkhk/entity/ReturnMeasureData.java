package com.zkhk.entity;

import java.util.List;

/**
 * 返回测量数据对象
 * @author xiemt
 *
 */
public class ReturnMeasureData {
	
	private int dataType;//数据类型
	
	private String measureTime;//测量时间格式如：20141021143021
	
	private int isAbnormal = 0;//是否有异0 正常 1 异常
	
	private Object data; //测量数据


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}

	public int getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(int isAbnormal) {
		this.isAbnormal = isAbnormal;
	}



}
