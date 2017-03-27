package com.zkhk.services;


import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:     DocVisitService.java
 * @Description:   医生随访
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月17日 上午10:15:02
*****/
public interface DocVisitService {
    
    /** 
     * @Title: findMemDiabetesVisitList 
     * @Description: 医生查询会员的糖尿病随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemDiabetesVisitList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemDiabetesVisitDetail 
     * @Description: 医生查询会员的糖尿病随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemDiabetesVisitDetail(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemHypertensionVisitList 
     * @Description: 医生查询会员的高血压随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHypertensionVisitList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemHypertensionVisitDetail 
     * @Description: 医生查询会员的高血压随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHypertensionVisitDetail(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: addOrModifyMemHypertensionVisit 
     * @Description: 医生新增或者修改会员的高血压随访
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemHypertensionVisit(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: addOrModifyMemDiabetesVisit 
     * @Description: 医生新增或者修改会员的糖尿病随访
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemDiabetesVisit(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findCanVisitDiabeteMem 
     * @Description: 医生获取可进行糖尿病随访的会员
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findCanVisitDiabeteMem(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findCanVisitHyperMem 
     * @Description: 医生获取可进行高血压随访的会员
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findCanVisitHyperMem(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findSomeDatasByMemberId 
     * @Description: 根据会员id自动获取最新的体格，血糖，用药等数据
     * @param request
     * @author 2016-04-28
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findSomeDatasByMemberId(HttpServletRequest request) throws Exception;
    
}
