package com.zkhk.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zkhk.dao.PcDao;
import com.zkhk.entity.Omem;
import com.zkhk.entity.PcMiniParam;
import com.zkhk.entity.PcMiniRecord;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.util.TimeUtil;

/**
 * @ClassName:     PcMiniRecord.java 
 * @Description:   pc端发送mini记录
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午4:13:46
*****/
@Repository(value="pcDao")
public class PcDaoImpl implements PcDao{
    
    @Resource
    private JdbcService jdbcService;
    
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    /** 
     * @Title: addPcMiniRecord 
     * @Description: 医生在pc端发放一条mini记录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer addPcMiniRecord(PcMiniRecord pcMiniRecord) throws Exception
    {
        int count = 0;
        String sql = " INSERT INTO pc_mini_record(user_id,mem_name,gender,tel,idCard,bluetooth_mac_addr,send_down_time,has_uploaded,has_deleted,send_doc_id) VALUES (?,?,?,?,?,?,?,0,0,?) ";
        Date date = datetimeFormat.parse(pcMiniRecord.getSendDownTime());
        count = jdbcService.doExecuteSQL(sql, new Object[]{pcMiniRecord.getUserId(),pcMiniRecord.getMemName(),pcMiniRecord.getGender(),pcMiniRecord.getTel(),
                pcMiniRecord.getIdCard(),pcMiniRecord.getBluetoothMacAddr(),date,pcMiniRecord.getDoctorId()});
        return count;
    }
    
    /** 
     * @Title: deletePcMiniRecord 
     * @Description: 删除未上传的mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer deletePcMiniRecord(String bluetoothMacAddr) throws Exception
    {
        int count = 0;
        String sql = " UPDATE pc_mini_record SET has_deleted = 1 WHERE bluetooth_mac_addr = ? AND has_uploaded = 0 ";
        count = jdbcService.doExecuteSQL(sql, new Object[]{bluetoothMacAddr});
        return count;
    }
    
    /** 
     * @Title: updatePcMiniRecord 
     * @Description: 修改mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Integer updatePcMiniRecord(String userId,String bluetoothMacAddr,String uploadTime) throws Exception
    {
        int count = 0;
        String sql = " UPDATE pc_mini_record SET upload_time = ?, has_uploaded = 1 WHERE user_id = ? AND bluetooth_mac_addr = ? AND has_deleted = 0 AND has_uploaded = 0 ";
        Date date = datetimeFormat.parse(uploadTime);
        count = jdbcService.doExecuteSQL(sql, new Object[]{date,userId,bluetoothMacAddr});
        return count;
    }
    
    /** 
     * @Title: findPcMiniRecordByParam 
     * @Description: 根据参数分页查询mini记录 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public List<PcMiniRecord> findPcMiniRecordByParam(PcMiniParam pcMiniParam) throws Exception
    {
        List<PcMiniRecord> pcMiniRecordList = new ArrayList<PcMiniRecord>();
        StringBuffer  sql = new StringBuffer();
        StringBuffer  sqlCount = new StringBuffer();
        sql.append(" select user_id,mem_name,gender,tel,idCard,bluetooth_mac_addr,send_down_time,upload_time,has_uploaded from pc_mini_record where has_deleted = 0 and send_doc_id = ? ");
        sqlCount.append(" select count(user_id) from pc_mini_record where has_deleted = 0 and send_doc_id = ? ");
        String memName = pcMiniParam.getMemName();
        String timeStart = pcMiniParam.getTimeStart();
        String timeEnd = pcMiniParam.getTimeEnd();
        int state = pcMiniParam.getStatus();
        if(!StringUtils.isEmpty(memName)){
            sql.append(" and mem_name like '%"+memName+"%' ");
            sqlCount.append(" and mem_name like '%"+memName+"%' ");
        }
        if(state >= 0){
            sql.append(" and has_uploaded =" +state+" " );
            sqlCount.append(" and has_uploaded =" +state+" " );
        }
        if(!StringUtils.isEmpty(timeStart) && !StringUtils.isEmpty(timeEnd)){
            sql.append(" and ( send_down_time BETWEEN '" +timeStart+"' AND '"+timeEnd+"' ) ");
            sqlCount.append(" and ( send_down_time BETWEEN '" +timeStart+"' AND '"+timeEnd+"' ) ");
        }
        if(!StringUtils.isEmpty(timeStart) && StringUtils.isEmpty(timeEnd)){
            sql.append(" and ( send_down_time BETWEEN '" +timeStart+"' AND now() ) ");
            sqlCount.append(" and ( send_down_time BETWEEN '" +timeStart+"' AND now() ) ");
        }
        if(StringUtils.isEmpty(timeStart) && !StringUtils.isEmpty(timeEnd)){
            sql.append(" and ( send_down_time <= '" +timeEnd+"' ) ");
            sqlCount.append(" and ( send_down_time <= '" +timeEnd+"' ) ");
        }
        //总数
        int count = countPcMiniRecord(sqlCount,pcMiniParam.getDoctorId());
        int endRecord = pcMiniParam.getPageSize();
        int beginRecord = (pcMiniParam.getPageNow()-1)*endRecord;
        sql.append(" order by send_down_time desc limit "+beginRecord+" ," +endRecord +" ");
        String finalSql = sql.toString();
        SqlRowSet rowSet = jdbcService.query(finalSql,new Object[]{pcMiniParam.getDoctorId()});
        while(rowSet.next()){
            PcMiniRecord pcMiniRecord = new PcMiniRecord();
            pcMiniRecord.setUserId(rowSet.getString("user_id"));
            String memberName = rowSet.getString("mem_name");
            if(!StringUtils.isEmpty(memberName)){
                pcMiniRecord.setMemName(memberName);
            }
            String gender = rowSet.getString("gender");
            if(!StringUtils.isEmpty(gender)){
                pcMiniRecord.setGender(gender);
            }
            String tel = rowSet.getString("tel");
            if(!StringUtils.isEmpty(tel)){
                pcMiniRecord.setTel(tel);
            }
            String idCard = rowSet.getString("idCard");
            if(!StringUtils.isEmpty(idCard)){
                pcMiniRecord.setIdCard(idCard);
            }
            pcMiniRecord.setBluetoothMacAddr(rowSet.getString("bluetooth_mac_addr"));
            pcMiniRecord.setSendDownTime(TimeUtil.formatDatetime2(rowSet.getDate("send_down_time")));
            String uploadTime = rowSet.getString("upload_time");
            if(!StringUtils.isEmpty(uploadTime)){
                pcMiniRecord.setUploadTime(TimeUtil.formatDatetime2(rowSet.getDate("upload_time")));
            }
            pcMiniRecord.setHasUploaded(rowSet.getInt("has_uploaded"));
            pcMiniRecord.setTotalRecord(count);
            pcMiniRecordList.add(pcMiniRecord);
        }
        return pcMiniRecordList;
    }
    
    /** 
     * @Title: findOmemByUserId 
     * @Description: 根据用户id获取用户基本资料 
     * @author liuxiaoqin
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public Omem findOmemByUserId(String userId) throws Exception
    {
        Integer memberId = Integer.valueOf(userId);
        String sql = " select * from omem where memberid = ? ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()){
            Omem omem=new Omem();
            omem.setAddress(rowSet.getString("Address"));
            String birthDate = rowSet.getString("BirthDate");
            if(!StringUtils.isEmpty(birthDate)){
                omem.setBirthDate(rowSet.getString("BirthDate"));
            }
            omem.setEducationStatus(rowSet.getInt("EducationStatus"));
            omem.setEmail(rowSet.getString("Email"));
            omem.setGender(rowSet.getString("Gender"));
            omem.setIdCard(rowSet.getString("IdCard"));
            omem.setMarryStatus(rowSet.getString("MarryStatus"));
            omem.setMemberId(memberId);
            omem.setMemId(rowSet.getInt("MemId"));
            omem.setMemName(rowSet.getString("MemName"));
            omem.setNativeAddr(rowSet.getString("NativeAddr"));
            omem.setOccupation(rowSet.getString("Occupation"));
            omem.setOrgId(rowSet.getInt("OrgId"));
            omem.setTel(rowSet.getString("Tel"));
            omem.setHeadAddress(rowSet.getString("HeadAddress"));
            omem.setVitality(findMemActiveIndexByMemberId(memberId));
            return omem;
        }
        return null;
    }

     /** 
     * @Title: findMemActiveIndexByMemberId 
     * @Description: 获取会员活力指数 
     * @param memberId
     * @author liuxiaoqin
     * @return
     * @throws Exception    
     * @retrun float
     */
    public float findMemActiveIndexByMemberId(int memberId) throws Exception {
        //获取会员最近30 天活力指数activeIndex
        float activeIndex = 0;
        String sql = "SELECT sum(score) FROM mem8 WHERE memberId = ? AND uploadTime > DATE_ADD(curdate(),INTERVAL -30 DAY) ";
        //会员最近30 天活力累计分数(monthActiveIndex)
        int monthActiveIndex=  jdbcService.queryForInt(sql, new Object[]{memberId});
        //取所有用户
        int allMem = getAllMem();
        
        //取所有用户前1/4的用户数量为
        int quarterMem = Math.round((float)allMem/4);
        
        String sqlQuarter = " SELECT avg(mm.totalScore) avgTotal FROM ( SELECT sum(score)totalScore FROM mem8 WHERE uploadTime > DATE_ADD(curdate(), INTERVAL -30 DAY) GROUP BY memberId ORDER BY totalScore DESC LIMIT ? )mm ";
        //所有会员累计积分从高到低排列，取前1/4所有用户的总积分平均值(avgActiveIndex)
        int avgActiveIndex = jdbcService.queryForInt(sqlQuarter, new Object[]{quarterMem});
        activeIndex = Math.round((float)monthActiveIndex*100/(float)avgActiveIndex);
        if (activeIndex>100) {
            activeIndex=100;
        }
        return activeIndex;
    }
    
     /** 
     * @Title: getAllMem 
     * @Description: 获取最近30 天所有会员 
     * @return
     * @throws Exception    
     * @retrun int
     */
    public int getAllMem() throws Exception{
        int allMem = 0;
        String sqlAll = " ( SELECT 1 FROM mem8 WHERE uploadTime > DATE_ADD(curdate(), INTERVAL -30 DAY) GROUP BY memberId ) mm ";
        //所有会员的最近30 天活力积分由高到低进行排序的总会员数
        allMem = jdbcService.getRecordCount(sqlAll);    
        return allMem;
    }
    
    /** 
     * @Title: findPcMiniRecord 
     * @Description: 根据用户id,设备地址查询一条mini记录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public PcMiniRecord findPcMiniRecord(String bluetoothMacAddr) throws Exception{
        String sql = " select user_id,mem_name,gender,tel,idCard,bluetooth_mac_addr,send_down_time,upload_time,has_uploaded from pc_mini_record where has_deleted = 0 AND bluetooth_mac_addr = ? AND has_uploaded = 0";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{bluetoothMacAddr});
        while(rowSet.next()){
            PcMiniRecord record = new PcMiniRecord();
            record.setUserId(rowSet.getString("user_id"));
            String memberName = rowSet.getString("mem_name");
            if(!StringUtils.isEmpty(memberName)){
                record.setMemName(memberName);
            }
            String gender = rowSet.getString("gender");
            if(!StringUtils.isEmpty(gender)){
                record.setGender(gender);
            }
            String tel = rowSet.getString("tel");
            if(!StringUtils.isEmpty(tel)){
                record.setTel(tel);
            }
            String idCard = rowSet.getString("idCard");
            if(!StringUtils.isEmpty(idCard)){
                record.setIdCard(idCard);
            }
            record.setBluetoothMacAddr(rowSet.getString("bluetooth_mac_addr"));
            record.setSendDownTime(TimeUtil.formatDatetime2(rowSet.getDate("send_down_time")));
            record.setHasUploaded(rowSet.getInt("has_uploaded"));
            return record;
        }
        return null;
    }
    
     /** 
     * @Title: countPcMiniRecord 
     * @Description: 获取符合条件的记录总数 
     * @param sqlCount
     * @return
     * @throws Exception    
     * @retrun Integer
     */
    public Integer countPcMiniRecord(StringBuffer sqlCount,Integer doctorId) throws Exception{
        int count = 0;
        String sql = sqlCount.toString();
        count = jdbcService.queryForInt(sql, new Object[]{doctorId});
        return count;
    }
    
    /** 
     * @Title: findMemListByParam 
     * @Description: 根据医生id和参数分页查询会员列表 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws Exception    
     */
    public List<Omem> findMemListByParam(PcMiniParam pcMiniParam)throws Exception{
        List<Omem> memList = new ArrayList<Omem>();
        StringBuffer  sql = new StringBuffer();
        StringBuffer  sqlCount = new StringBuffer();
        sql.append(" SELECT DISTINCT o.* FROM omem o, ompb m, odmt d, dgp1 g  ");
        sql.append(" WHERE o.useTag = 'T' AND o.orgId = g.OrgId AND g.Docid = ? AND m.memberid = o.memberid AND m.memGrpid = d.MemGrpid AND d.OdgpId = g.OdgpId  ");
        sqlCount.append(" SELECT COUNT(1) FROM (SELECT DISTINCT o.Memberid FROM omem o, ompb m, odmt d, dgp1 g  ");
        sqlCount.append(" WHERE o.useTag = 'T' AND o.orgId = g.OrgId AND g.Docid = ? AND m.memberid = o.memberid AND m.memGrpid = d.MemGrpid AND d.OdgpId = g.OdgpId ");
        String memName = pcMiniParam.getMemName();
        String tel = pcMiniParam.getTel();
        String idcard = pcMiniParam.getIdCard();
        if(!StringUtils.isEmpty(memName)){
            sql.append(" and o.MemName like '%"+memName+"%' ");
            sqlCount.append(" and o.MemName like '%"+memName+"%' ");
        }
        if(!StringUtils.isEmpty(tel)){
            sql.append(" and o.Tel like '%"+tel+"%' ");
            sqlCount.append(" and o.Tel like '%"+tel+"%' ");
        }
        if(!StringUtils.isEmpty(idcard)){
            sql.append(" and o.IdCard like '%"+idcard+"%' ");
            sqlCount.append(" and o.IdCard like '%"+idcard+"%' ");
        }
        sqlCount.append(" )aa ");
        //总数
        int count = countMemList(sqlCount,pcMiniParam.getDoctorId());
        int endRecord = pcMiniParam.getPageSize();
        int beginRecord = (pcMiniParam.getPageNow()-1)*endRecord;
        sql.append(" order by o.CreateTime desc limit "+beginRecord+" ," +endRecord +" ");
        String finalSql = sql.toString();
        SqlRowSet rowSet = jdbcService.query(finalSql, new Object[]{pcMiniParam.getDoctorId()});
        while(rowSet.next()){
            Omem omem=new Omem();
            omem.setAddress(rowSet.getString("Address"));
            String birthDate = rowSet.getString("BirthDate");
            if(!StringUtils.isEmpty(birthDate)){
                omem.setBirthDate(rowSet.getString("BirthDate"));
            }
            omem.setEducationStatus(rowSet.getInt("EducationStatus"));
            omem.setEmail(rowSet.getString("Email"));
            omem.setGender(rowSet.getString("Gender"));
            omem.setIdCard(rowSet.getString("IdCard"));
            omem.setMarryStatus(rowSet.getString("MarryStatus"));
            int memberId = rowSet.getInt("Memberid");
            omem.setMemberId(memberId);
            omem.setMemId(rowSet.getInt("MemId"));
            omem.setMemName(rowSet.getString("MemName"));
            omem.setNativeAddr(rowSet.getString("NativeAddr"));
            omem.setOccupation(rowSet.getString("Occupation"));
            omem.setOrgId(rowSet.getInt("OrgId"));
            omem.setTel(rowSet.getString("Tel"));
            omem.setHeadAddress(rowSet.getString("HeadAddress"));
            omem.setVitality(findMemActiveIndexByMemberId(memberId));
            omem.setTotalRecord(count);
            memList.add(omem);
        }
        return memList;
    }
    
    /** 
     * @Title: countMemList 
     * @Description: 获取符合条件的会员记录总数 
     * @param sqlCount
     * @return
     * @throws Exception    
     * @retrun Integer
     */
    public Integer countMemList(StringBuffer sqlCount,int doctorId) throws Exception{
        int count = 0;
        String sql = sqlCount.toString();
        count = jdbcService.queryForInt(sql,new Object[]{doctorId});
        return count;
    }
    
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
    public Integer checkDocOwnMem(String userId,String doctorId) throws Exception{
        int newUserId = Integer.valueOf(userId);
        int newDoctorId = Integer.valueOf(doctorId);
        int count = 0;
        String sql = " SELECT COUNT(1) FROM (SELECT DISTINCT o.Memberid FROM omem o, ompb m, odmt d, dgp1 g "
                    + " WHERE o.useTag = 'T' AND o.orgId = g.OrgId AND g.Docid = ? AND m.memberid = o.memberid AND m.memGrpid = d.MemGrpid AND d.OdgpId = g.OdgpId AND o.Memberid = ? )aa ";
        count = jdbcService.queryForInt(sql,new Object[]{newDoctorId,newUserId});
        return count;
    }
    
}
