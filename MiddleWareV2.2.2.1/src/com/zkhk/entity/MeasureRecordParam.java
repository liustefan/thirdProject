package com.zkhk.entity;

/**
 * @ClassName:     MeasureRecordParam.java 
 * @Description:   测量记录参数
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午4:13:46
*****/
public class MeasureRecordParam extends Pagination{
    
    /*  参数名(手机，名字，身份证)   */
	private String paramName;
	
	/*  测量记录类型集合"1,2,3,4" 1 血压 2 血糖 3 三合一 4 动态心电 */
	private String measureTypeIds;
	
	/*  医生id  */
    private int doctorId;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getMeasureTypeIds() {
        return measureTypeIds;
    }

    public void setMeasureTypeIds(String measureTypeIds) {
        this.measureTypeIds = measureTypeIds;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
	
}
