package com.zkhk.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import com.zkhk.dao.DataDicDao;
import com.zkhk.jdbc.JdbcService;

/**
 * @ClassName:     DataDicDaoImpl.java 
 * @Description:   数据字典
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午4:13:46
*****/
@Repository(value="dataDicDao")
public class DataDicDaoImpl implements DataDicDao{
    
    @Resource
    private JdbcService jdbcService;
    
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    /** 
     * @Title: findDiseaseDictionary 
     * @Description: 查询疾病史字典
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws IOException    
     * @retrun String
     */
    public List<Map<String,Object>> findDiseaseDictionary() throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String sql = " SELECT disease_id,disease_name FROM d_disease_dictionary ORDER BY disease_id ";
        SqlRowSet rowSet = jdbcService.query(sql);
        while(rowSet.next()){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("diseaseId", rowSet.getInt("disease_id"));
            map.put("diseaseName",rowSet.getString("disease_name"));
            list.add(map);
        }
        return list;
    }
    
}
