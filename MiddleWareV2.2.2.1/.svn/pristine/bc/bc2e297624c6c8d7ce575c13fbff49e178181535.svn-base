package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName:     PcController.java 
 * @Description:   pc端操作(上传mini等)
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午2:39:50
*****/
public interface PcService {
    
    /** 
     * @Title: addPcMiniRecord 
     * @Description: 医生在pc端发放一条mini记录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun String
     */
    public String addPcMiniRecord(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: deletePcMiniRecord 
     * @Description: 删除未上传的mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String deletePcMiniRecord(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: updatePcMiniRecord 
     * @Description: 修改mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String updatePcMiniRecord(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findPcMiniRecordByParam 
     * @Description: 根据参数分页查询mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String findPcMiniRecordByParam(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findOmemByUserId 
     * @Description: 根据用户id获取用户基本资料 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String findOmemByUserId(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemListByParam 
     * @Description: 根据医生id和参数分页查询会员列表 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String findMemListByParam(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: uploadMiniRecord 
     * @Description: 医生上传mini记录 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String uploadMiniRecord (HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: checkDocOwnMem 
     * @Description: 根据医生id和会员id，检查会员是否属于医生管理 
     * @author liuxiaoqin
     * @createDate 2016-02-01
     * @param userId
     * @param doctorId
     * @return
     * @throws Exception    
     */
    public String checkDocOwnMem (HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: docLogin 
     * @Description: 医生在pc端登录
     * @author liuxiaoqin 
     * @createDate 2016-02-24
     * @param request
     * @param response
     * @throws IOException    
     * @retrun String
     */
    public String docLogin(HttpServletRequest request) throws Exception;
    
}
