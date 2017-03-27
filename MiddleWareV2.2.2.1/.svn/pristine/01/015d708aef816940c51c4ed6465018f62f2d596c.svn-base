package com.zkhk.dao;

import java.util.List;
import com.zkhk.entity.Omem;
import com.zkhk.entity.PcMiniParam;
import com.zkhk.entity.PcMiniRecord;

/**
 * @ClassName:     PcMiniRecord.java 
 * @Description:   pc端发送mini记录
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午4:13:46
*****/
public interface PcDao {
    
    /** 
     * @Title: addPcMiniRecord 
     * @Description: 医生在pc端发放一条mini记录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer addPcMiniRecord(PcMiniRecord pcMiniRecord) throws Exception;
    
    /** 
     * @Title: deletePcMiniRecord 
     * @Description: 删除未上传的mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer deletePcMiniRecord(String bluetoothMacAddr) throws Exception;
    
    /** 
     * @Title: updatePcMiniRecord 
     * @Description: 修改mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer updatePcMiniRecord(String userId,String bluetoothMacAddr,String uploadTime) throws Exception;
    
    /** 
     * @Title: findPcMiniRecordByParam 
     * @Description: 根据参数分页查询mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public List<PcMiniRecord> findPcMiniRecordByParam(PcMiniParam pcMiniParam) throws Exception;
    
    /** 
     * @Title: findOmemByUserId 
     * @Description: 根据用户id获取用户基本资料 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Omem findOmemByUserId(String userId) throws Exception;
    
    /** 
     * @Title: findPcMiniRecord 
     * @Description: 根据用户id,设备地址查询一条mini记录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public PcMiniRecord findPcMiniRecord(String bluetoothMacAddr) throws Exception;
    
    /** 
     * @Title: findMemListByParam 
     * @Description: 根据医生id和参数分页查询会员列表 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws Exception    
     */
    public List<Omem> findMemListByParam(PcMiniParam pcMiniParam)throws Exception;
    
     /** 
     * @Title: checkDocOwnMem 
     * @Description: 根据医生id和会员id，检查会员是否属于医生管理 
     * @author liuxiaoqin
     * @createDate 2016-02-01
     * @param userId
     * @param doctorId
     * @return
     * @throws Exception    
     * @retrun Integer
     */
    public Integer checkDocOwnMem(String userId,String doctorId) throws Exception;

}
