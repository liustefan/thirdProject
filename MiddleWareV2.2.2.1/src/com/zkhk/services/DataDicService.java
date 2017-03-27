package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:     DataDicService.java
 * @Description:   数据字典
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月22日 上午10:15:02
*****/
public interface DataDicService {
    
    /** 
     * @Title: findAllDictionary 
     * @Description: 查询所有字典【随访】
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws IOException    
     * @retrun String
     */
    public String findAllDictionary(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findDiseaseDictionary 
     * @Description: 查询疾病史字典
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws IOException    
     * @retrun String
     */
    public String findDiseaseDictionary(HttpServletRequest request) throws Exception;
    
    /** 
     * @Title: findHealthExamDictionary 
     * @Description: 查询所有字典【健康体检】
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-23
     * @throws IOException    
     * @retrun String
     */
    public String findHealthExamDictionary(HttpServletRequest request) throws Exception;
    
}
