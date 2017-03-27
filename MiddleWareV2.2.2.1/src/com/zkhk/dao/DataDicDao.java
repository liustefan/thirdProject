package com.zkhk.dao;

import java.util.List;
import java.util.Map;
/**
 * @ClassName:     DataDicDao.java 
 * @Description:   数据字典
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年03月23日 下午4:13:46
*****/
public interface DataDicDao {
    
    /** 
     * @Title: findDiseaseDictionary 
     * @Description: 查询疾病史字典
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws IOException    
     * @retrun String
     */
    public List<Map<String,Object>> findDiseaseDictionary() throws Exception;
    
}
