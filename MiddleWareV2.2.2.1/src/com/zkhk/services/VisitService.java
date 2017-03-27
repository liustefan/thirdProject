package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:     VisitService.java
 * @Description:   随访
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月14日 上午10:15:02
*****/
public interface VisitService {
    
    /** 
     * @Title: findMyDiabetesVisitList 
     * @Description: 查询我的糖尿病随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun String
     */
    public String findMyDiabetesVisitList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMyDiabetesVisitDetail 
     * @Description: 查询我的糖尿病随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyDiabetesVisitDetail(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMyHypertensionVisitList 
     * @Description: 查询我的高血压随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyHypertensionVisitList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMyHypertensionVisitDetail 
     * @Description: 查询我的高血压随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyHypertensionVisitDetail(HttpServletRequest request) throws Exception;
    
}
