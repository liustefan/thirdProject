package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkhk.entity.ReturnResult;
public interface DocService {
    /**
     * 通过用户名和密码查询医生登入信息
     * @param request
     * @return
     */
	ReturnResult findDocbyNameAndPassWord(HttpServletRequest request) throws Exception;
    /**
     * 验证用户是否有权限登入 。有返回true没有返回
     * @param session
     * @param menberId
     * @return
     */
	String findDocBySessionAndId(String session, int menberId)throws Exception;
	/**
	 * 注销用户信息
	 * @param request
	 * @return
	 */
	String updateDocSessionByMemId(HttpServletRequest request)throws Exception;

	/**
	 * 获取我的会员列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String getMyMemberList(HttpServletRequest request)throws Exception;
	
	/**
	 * 会员自动登录接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String memLogin(HttpServletRequest request)throws Exception;
	
	/**
	 * 保存会员健康档案
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String saveMemFile(HttpServletRequest request)throws Exception;
	
	/**
	 * 获取用户健康档案信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String checkMemFile(HttpServletRequest request)throws Exception;
	
	/** 
     * @Title: findMemMeasureRecordList 
     * @Description: 医生查看所属下的会员的测量记录 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
	public String findMemMeasureRecordList(HttpServletRequest request) throws Exception;
	
	/** 
     * @Title: findMemMeasureRecordOne 
     * @Description: 医生根据事件id(eventId)和事件类型(eventType)获取会员的某条测量记录 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
	public String findMemMeasureRecordOne(HttpServletRequest request) throws Exception;
	
	/** 
     * @Title: findMemOecgById 
     * @Description: 医生根据心电oecg的id获取会员的某条心电测量数据(不分三合一和mini)
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public String findMemOecgById(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemOppgById 
     * @Description: 医生根据脉搏oppg的id获取会员的某条脉搏数据
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public String findMemOppgById(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: downloadFile 
     * @Description: 医生根据文件的id下载会员的测量文件信息
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws IOException    
     * @retrun void
     */
    public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws Exception;
    
    /** 
     * @Title: uploadMemBloodPressure 
     * @Description: 医生上传会员的血压数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemBloodPressure(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: uploadMemBloodGlucose 
     * @Description: 医生上传会员的血糖数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemBloodGlucose(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: uploadMemMini 
     * @Description: 医生上传会员的心电mini数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemMini(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: uploadMemThreeInOne 
     * @Description: 医生上传会员的三合一数据
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws IOException    
     * @retrun void
     */
    public String uploadMemThreeInOne(HttpServletRequest request) throws Exception;
    
     /** 
     * @Title: findMemMeasureRecordImgs 
     * @Description: 医生获取会员测量数据的图片 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-02-29
     * @throws Exception    
     * @retrun void
     */
    public void findMemMeasureRecordImgs(HttpServletRequest request,HttpServletResponse response) throws Exception;
    
    /** 
     * @Title: findMemHeadImg 
     * @Description: 医生获取会员头像
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-04-08
     * @throws Exception    
     * @retrun String
     */
    public String findMemHeadImg(HttpServletRequest request) throws Exception;
	
}
