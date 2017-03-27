package com.zkhk.entity;

/**
 * @ClassName:     PHHealthExamParam.java 
 * @Description:   健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午4:13:46
*****/
public class PHHealthExamParam extends Pagination{
    
    /*  会员id  */
    private int memberId;
    
    /*  医生id   */
    private int doctorId;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
	/* 查询会员列表需要的参数 end */

   
	
    
}
