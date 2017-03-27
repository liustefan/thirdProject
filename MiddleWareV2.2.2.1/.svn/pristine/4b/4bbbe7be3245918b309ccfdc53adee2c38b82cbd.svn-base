package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:     DocHealthExamService.java
 * @Description:   医生操作健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016-03-17 上午10:15:02
*****/
public interface DocHealthExamService {
    
    /** 
     * @Title: findMemHealthExamReportList 
     * @Description: 医生查询会员的健康体检报告列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHealthExamReportList(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemHealthExamDetail 
     * @Description: 医生查询会员的健康体检报告明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHealthExamDetail(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: addOrModifyMemHealthExam 
     * @Description: 医生新增或者修改会员的健康体检
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemHealthExam(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: addMemDiseases 
     * @Description: 医生新增会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addMemDiseases(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: deleteMemDiseases 
     * @Description: 医生删除会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String deleteMemDiseases(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemHealthFile 
     * @Description: 医生查询会员的健康档案
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHealthFile(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findMemHealthExamReportList 
     * @Description: 根据参数获取医生所属会员的健康体验列表。
     * @param request
     * @param response
     * @author hx
     * @createDate 2016-05-19
     * @throws IOException    
     * @retrun void
     */
	public String findHealthExamList(HttpServletRequest request) throws Exception;
	
	/** 
     * @Title: findAgedLifeEvaluate 
     * @Description: 通过老年人生活自理能力问卷获取老年人生活自理能力值
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun String
     */
    public String findAgedLifeEvaluate(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findTcmValue 
     * @Description: 通过中医体质问卷获取中医体质结果
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun String
     */
    public String findTcmValue(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findTcmAndAgedQuestionnaire 
     * @Description: 获取中医体质和老年人生活能力评估问卷
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun void
     */
    public String findTcmAndAgedQuestionnaire(HttpServletRequest request) throws Exception;
    
}
