package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:     HealthCheckService.java
 * @Description:   健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月14日 上午10:15:02
*****/
public interface HealthExamService {
    
    /** 
     * @Title: findMyHealthExamReportList 
     * @Description: 查询我的健康体检报告列表
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun String
     */
    public String findMyHealthExamReportList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMyHealthExamDetail 
     * @Description: 查询我的健康体检报告明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyHealthExamDetail(HttpServletRequest request) throws Exception;
    
}
