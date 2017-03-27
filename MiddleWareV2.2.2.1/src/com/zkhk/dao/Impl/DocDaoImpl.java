package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.gridfs.GridFSDBFile;
import com.zkhk.dao.DocDao;
import com.zkhk.entity.AnomalyEcg;
import com.zkhk.entity.Ecg2;
import com.zkhk.entity.MeasureRecordParam;
import com.zkhk.entity.MemBasicInfo;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.MemMeasureRecord;
import com.zkhk.entity.MemSearch;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omds;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.OsbpFile;
import com.zkhk.entity.ReturnMeasureData;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.util.ByteToInputStream;
import com.zkhk.util.ImageUtils;
import com.zkhk.util.TimeUtil;


/**
 * @author xiemt
 *
 */
@Repository(value="docDao")
public class DocDaoImpl implements DocDao {
    @Resource
	private JdbcService jdbcService;
    
    @Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;
    
	public MemLog findDocbyNameAndPassWord(MemLog log) throws Exception {
	    String sql ="select a.Docid as docId ,a.Tag as tag,b.DocName as doctorName,b.DocGUID,b.HeadAddress AS doctorHeadImg from doc1 a ,odoc b "+
	    			 "where a.DocPass = ? "+
	    			   "and a.Docid = b.Docid and a.Tag = 'T'"+
	    			   "and (a.DocAcc = ? or b.Email = ? or b.Tel= ? ) order by a.Docid desc";
	    SqlRowSet rowSet=jdbcService.query(sql, new Object[]{log.getPassWord(),log.getUserAccount(),log.getUserAccount(),log.getUserAccount()});
	    while(rowSet.next()){
	    	MemLog log2=new MemLog();
	    	log2.setMemberId(rowSet.getInt("docId"));
	    	log2.setCurTag(rowSet.getString("Tag"));
	    	log2.setDoctorName(rowSet.getString("doctorName"));
	    	log2.setDoctorGUID(rowSet.getString("DocGUID"));
	    	log2.setDoctorHeadImg(rowSet.getString("doctorHeadImg"));
	    	return log2;
	    }
		return null;
	}

	public int updateDocLogByMemberid(MemLog loginLog) throws Exception {
		String sql="UPDATE doclog SET `session`=?,device=? WHERE doctor_id=?";
		return jdbcService.doExecuteSQL(sql, new Object[]{loginLog.getSession(),loginLog.getDevice(),loginLog.getMemberId()} );
	}

	public void saveDoctorLog(MemLog loginLog) throws Exception {
		String sql="insert into doclog (doctor_id,`password`,`session`,tag,login_time,device)VALUES(?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(sql, new Object[]{loginLog.getMemberId(),loginLog.getPassWord(),loginLog.getSession(),loginLog.getCurTag(),loginLog.getLoginTime(),loginLog.getDevice()} );
	}
	
	public void queryDocByDoctorId(MemLog loginLog) throws Exception {
		
	}
	
	public boolean findDocBySessionAndId(String session, int memberId)
			throws Exception {
	    String sql ="SELECT 1 FROM doclog WHERE `session`=? AND doctor_id=?;";
	    SqlRowSet rowSet=jdbcService.query(sql, new Object[]{session,memberId});
	    while(rowSet.next()){
	    	return true;
	    }
		return false;
	}

	/** 
	 * <p>Title: queryMemberListByParams</p>  
	 * <p>Description: 获取医生管理下的会员列表</p>
	 * @author liuxiaoqin  
	 * @param memSearch
	 * @return
	 * @throws Exception 
	 * @see com.zkhk.dao.DocDao#queryMemberListByParams(com.zkhk.entity.MemSearch)  
	*/
	public List<MemBasicInfo> queryMemberListByParams(MemSearch memSearch) throws Exception{
	    List<MemBasicInfo> memberList = new ArrayList<MemBasicInfo>();
	    String diseaseIds = memSearch.getDiseaseIds();
	    StringBuffer sqlAll = new StringBuffer();
	    StringBuffer sqlDisease = new StringBuffer();
	    String sql = "";
	    sqlAll.append(" SELECT o.Tel, o.Email, o.BirthDate AS birthDate, o.Docid AS doctorId, o.Gender AS gender, o.IdCard AS idCard, o.memberGUID,o.Memberid AS memberId, o.MemName AS memberName,b.PassWord, ");
	    sqlAll.append(" GROUP_CONCAT(DISTINCT CAST(od.DiseaseID AS CHAR) ORDER BY od.DiseaseID) AS diseaseIds, ");
	    sqlAll.append(" GROUP_CONCAT(DISTINCT od.DiseaseName ORDER BY od.DiseaseID) AS diseaseNames ");
	    sqlAll.append(" FROM ( SELECT o.Memberid FROM omem o, ompb m, odmt d, dgp1 g ");
	    sqlAll.append(" WHERE o.useTag = 'T' AND o.orgId = g.OrgId AND g.Docid = ? AND m.memberid = o.memberid AND m.memGrpid = d.MemGrpid AND d.OdgpId = g.OdgpId ");
	    
	    sqlDisease.append(" SELECT o.Tel, o.Email, o.BirthDate AS birthDate, o.Docid AS doctorId, o.Gender AS gender, o.IdCard AS idCard,o.memberGUID, o.Memberid AS memberId, o.MemName AS memberName,b.PassWord, ");
	    sqlDisease.append(" GROUP_CONCAT(DISTINCT CAST(od.DiseaseID AS CHAR) ORDER BY od.DiseaseID) AS diseaseIds, ");
	    sqlDisease.append(" GROUP_CONCAT(DISTINCT od.DiseaseName ORDER BY od.DiseaseID) AS diseaseNames ");
	    sqlDisease.append(" FROM ( SELECT o.Memberid FROM omem o, ompb m, odmt d, dgp1 g, mem3 od ");
	    sqlDisease.append(" WHERE o.useTag = 'T' AND o.orgId = g.OrgId AND g.Docid = ? AND m.memberid = o.memberid AND m.memGrpid = d.MemGrpid AND d.OdgpId = g.OdgpId  AND o.Memberid = od.Memberid ");
	    
	    if(!StringUtils.isEmpty(memSearch.getSearchParam())){
	        sqlAll.append(" AND  (o.MemName like '%"+memSearch.getSearchParam()+"%' or o.IdCard like '%"+memSearch.getSearchParam()+"%' or o.Tel like '%"+memSearch.getSearchParam()+"%' ) ");
	        sqlDisease.append(" AND  (o.MemName like '%"+memSearch.getSearchParam()+"%' or o.IdCard like '%"+memSearch.getSearchParam()+"%' or o.Tel like '%"+memSearch.getSearchParam()+"%' ) ");
	    }
	    sqlAll.append(" GROUP BY o.Memberid ORDER BY o.MemName, o.Memberid LIMIT ?,? ) t ");
	    if(!StringUtils.isEmpty(diseaseIds)){
	    	/*  解析字符串diseaseIds begin */
	        String str[] = diseaseIds.split(",");  
	        String array[] = new String[str.length];  
	        for(int i=0;i<str.length;i++){  
	            array[i] = str[i];  
	        }
	        Arrays.sort(array);
	        String finalString = StringUtils.arrayToCommaDelimitedString(array);
	        int count = array.length;
	        /*  解析字符串diseaseIds end */
	        
	        sqlDisease.append(" AND o.Memberid = od.Memberid AND od.DiseaseID IN ( "+finalString+" ) ");
	        sqlDisease.append(" GROUP BY o.Memberid HAVING COUNT(DISTINCT od.DiseaseID) = "+count+" ORDER BY o.MemName, o.Memberid LIMIT ?,? ) t ");
	    }
	    sqlAll.append(" LEFT JOIN omem o ON t.Memberid = o.Memberid LEFT JOIN memlog b ON o.Memberid = b.Memberid ");
	    sqlDisease.append(" LEFT JOIN omem o ON t.Memberid = o.Memberid LEFT JOIN memlog b ON o.Memberid = b.Memberid ");
	    String memberType = memSearch.getMemberType();
	    if(!StringUtils.isEmpty(memberType) && "exam".equals(memberType)){
	    	sqlAll.append(" LEFT JOIN mem3 od ON o.Memberid = od.Memberid WHERE o.source = 0 GROUP BY o.MemName, o.Memberid ");
	    	sqlDisease.append(" LEFT JOIN mem3 od ON o.Memberid = od.Memberid WHERE o.source = 0 GROUP BY o.MemName, o.Memberid ");
	    }else{
	    	sqlAll.append(" LEFT JOIN mem3 od ON o.Memberid = od.Memberid GROUP BY o.MemName, o.Memberid ");
	    	sqlDisease.append(" LEFT JOIN mem3 od ON o.Memberid = od.Memberid GROUP BY o.MemName, o.Memberid ");
	    }
	    
	    if(!StringUtils.isEmpty(diseaseIds)){
	    	sql = sqlDisease.toString();
	    }else{
	    	sql = sqlAll.toString();
	    }
	    SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memSearch.getDoctorId(),memSearch.getFirstResult(),memSearch.getPageSize()} ); 
        while(rowSet.next()){
            MemBasicInfo info = new MemBasicInfo();
            info.setAge(TimeUtil.getCurrentAgeByBirthdate(rowSet.getString("birthDate")));
            info.setDoctorId(rowSet.getString("doctorId"));
            info.setGender(rowSet.getString("gender"));
            info.setIdCard(rowSet.getString("idCard"));
            int memberId = rowSet.getInt("memberId");
            info.setMemberId(memberId);
            info.setMemberName(rowSet.getString("memberName"));
            info.setPassword(rowSet.getString("password"));
            info.setMemberGUID(rowSet.getString("memberGUID"));
            String tel = rowSet.getString("Tel");
            String email = rowSet.getString("Email");
            if(!StringUtils.isEmpty(tel)){
                info.setUserAccount(tel);
                info.setTel(tel);
            }else if(!StringUtils.isEmpty(email)){
                info.setUserAccount(email);
                info.setEmail(email);
            }else{
                info.setUserAccount(rowSet.getString("IdCard"));
            }
            String newDiseaseIds = rowSet.getString("diseaseIds");
            String newDiseaseNames = rowSet.getString("diseaseNames");
            if(!StringUtils.isEmpty(newDiseaseIds)){
                info.setDiseaseIds(newDiseaseIds);
            }
            if(!StringUtils.isEmpty(newDiseaseNames)){
                info.setDiseaseNames(newDiseaseNames);
            }
            if(!StringUtils.isEmpty(memberType) && "exam".equals(memberType)){
            	String newSql = " SELECT ExamDate FROM ph_healthexam WHERE MemberID = ? AND IsDeleted = 0 ORDER BY ExamDate DESC,GetTime DESC LIMIT 1 ";
            	SqlRowSet newSet = jdbcService.query(newSql, new Object[]{memberId});
            	while(newSet.next()){
            		Date examDate = newSet.getDate("ExamDate");
            		if(examDate != null){
            			String lastExamDate = TimeUtil.formatDate(examDate);
            			info.setLastExamDate(lastExamDate);
            		}
            	}
            }
            memberList.add(info);
        }
	    return memberList;
	}
	

	public int findmemByMemberIdAndDoctorId(int doctorId, int memberId) throws Exception {
		String sql = "SELECT distinct o.Memberid as memberId FROM omem o,ompb m,odmt d,dgp1 g WHERE o.useTag='T' and g.Docid = ? and o.Memberid= ?"
				+ " and o.orgId=g.OrgId and m.memberid=o.memberid and m.memGrpid = d.MemGrpid and d.OdgpId = g.OdgpId ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[]{doctorId,memberId} );
		while(rowSet.next()){
			return 1;
		}
		return 0;
	}

	/** 
     * @Title: findMemHeadImg 
     * @Description: 根据会员id和密码验证会员(0707版去掉密码的验证) 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-07-07
     * @throws Exception    
     * @retrun Integer
     */
	public MemLog findUserbyMemberIdAndPassWord(MemSearch memSearch)throws Exception {
	    String sql = " SELECT a.MEMBERID,a.curTag,a.Session FROM memlog a left join omem b on a.memberId = b.Memberid "
	    		   + "  WHERE b.Memberid = ? AND b.useTag = 'T' ";
	    SqlRowSet rowSet=jdbcService.query(sql, new Object[]{memSearch.getMemberId()});
	    while(rowSet.next()){
	    	MemLog log2=new MemLog();
	    	log2.setMemberId(rowSet.getInt("MEMBERID"));
	    	log2.setCurTag(rowSet.getString("curTag"));
	    	log2.setSession(rowSet.getString("Session"));
	    	return log2;
	    }
		return null;
	}

	public int updateMemFile(OsbpFile osbpFile) throws Exception {
		StringBuffer sqlBuf = new StringBuffer(); 
		sqlBuf.append("update mem2 set ");
		int count = 0;
		if(osbpFile.getHeight() != 0){
			sqlBuf.append("Height = "+osbpFile.getHeight());
			count = count+1;
		}
		
		if(Float.parseFloat(osbpFile.getWeight()) > 0){
			if(count>0){
				sqlBuf.append(",Weight = "+osbpFile.getWeight());
			}else{
				sqlBuf.append("Weight = "+osbpFile.getWeight());
			}
			count = count+1;
		}
		
		if(osbpFile.getBloodH() != 0){
			if(count>0){
				sqlBuf.append(",BloodH = "+osbpFile.getBloodH());
			}else{
				sqlBuf.append("BloodH = "+osbpFile.getBloodH());
			}
			count = count+1;
		}
		
		if(osbpFile.getBloodL() != 0){
			if(count>0){
				sqlBuf.append(",BloodL = "+osbpFile.getBloodL());
			}else{
				sqlBuf.append("BloodL = "+osbpFile.getBloodL());
			}
		}
		
		sqlBuf.append(" where Memberid = ?");
		
		
		return jdbcService.doExecuteSQL(sqlBuf.toString(),new Object[]{osbpFile.getMemberId()});
	}

    /** 
     * <p>Title: queryMemFile</p>  
     * <p>Description:检查会员的健康档案：身高，体重，脉搏，舒张压，收缩压 </p>  
     * @author liuxiaoqin
     * @param memberId
     * @return
     * @throws Exception 
     * @see com.zkhk.dao.DocDao#queryMemFile(java.lang.String)  
    */
    public MemFile queryMemFile(String memberId) throws Exception {
        MemFile memFile  = new MemFile();
        memFile.setMemberId(Integer.valueOf(memberId));
        /* 获取身高,体重from mem2 begin */
        String memSql =" SELECT a.Memberid, a.Weight,a.Height FROM mem2 a  WHERE  a.Memberid = ? ";
        SqlRowSet memRowSet=jdbcService.query(memSql, new Object[]{memberId});
        while(memRowSet.next()){
            memFile.setHeight(memRowSet.getInt("Height"));
            memFile.setWeight(memRowSet.getFloat("Weight"));
        }
        /* 获取身高,体重from mem2 end */
        
        /* 脉搏，舒张压，收缩压from osbp begin */
        String obspSql =" SELECT p.SBP,p.DBP,p.PulseRate FROM  osbp p WHERE  p.Memberid = ? AND p.DelTag = 0 ORDER BY p.TestTime desc limit 1";
        SqlRowSet rowSet=jdbcService.query(obspSql, new Object[]{memberId});
        while(rowSet.next()){
            memFile.setBloodH(rowSet.getInt("SBP"));
            memFile.setBloodL(rowSet.getInt("DBP"));
            memFile.setPulse(rowSet.getInt("PulseRate"));
        }
        /* 脉搏，舒张压，收缩压from osbp end */
        return memFile;
    }

	public int saveMemFile(OsbpFile osbpFile) throws Exception {
		String sql = "insert into mem2 (Memberid,BloodH,BloodL,Height,Weight) VALUES(?,?,?,?,?)";
		return jdbcService.doExecuteSQL(sql,new Object[]{osbpFile.getMemberId(),osbpFile.getBloodH(),osbpFile.getBloodL(),osbpFile.getHeight(),osbpFile.getWeight()});
	}

	public List<MemBasicInfo> queryDiseaseListBymemeberId(String memberIds)throws Exception {
	
		List<MemBasicInfo> memberList = new ArrayList<MemBasicInfo>();
		StringBuffer  sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT group_concat(CAST(d.disease_id AS char) separator ',') as disease_id ,group_concat(d.disease_name separator ',') as disease_name ");  
		sqlBuffer.append("from d_omem_disease d where d.member_id in (?) GROUP BY  d.member_id " );    
		SqlRowSet rowSet = jdbcService.query(sqlBuffer.toString(), new Object[]{memberIds} );
		while(rowSet.next()){
	    	MemBasicInfo info = new MemBasicInfo();
	    	info.setDiseaseIds(rowSet.getString("disease_id"));
	    	info.setDiseaseNames(rowSet.getString("disease_name"));
	    	memberList.add(info);
	    }
		return memberList;
	}
	
	/** 
     * @Title: getEventIdList 
     * @Description: 医生获取所属下的会员的测量记录id集合 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
	public List<Integer> getEventIdList(Integer doctorId)  throws Exception{
	    List<Integer> list = new ArrayList<Integer>();
	    String sql = " SELECT DISTINCT bb.eventid FROM (SELECT DISTINCT o.Memberid FROM omem o LEFT JOIN ompb m ON m.memberid = o.memberid "
	               + " LEFT JOIN odmt d ON m.memGrpid = d.MemGrpid LEFT JOIN dgp1 g ON d.OdgpId = g.OdgpId WHERE g.Docid = ? "
	               + " AND o.useTag = 'T')aa LEFT JOIN omds bb ON aa.Memberid = bb.Memberid where bb.eventid IS NOT NULL ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{doctorId});
        while (rowSet.next()){
            int eventId = rowSet.getInt("eventid");
            list.add(eventId);
        }
	    return list;
	}
	
	/** 
     * @Title: findMemMeasureRecordList 
     * @Description: 医生查看所属下的会员的测量记录 
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-01-27
     * @throws IOException    
     * @retrun void
     */
    public List<MemMeasureRecord> findMemMeasureRecordList(MeasureRecordParam param)throws Exception
    {
    	List<MemMeasureRecord> memMeasureRecordList = new ArrayList<MemMeasureRecord>();
        List<Integer> list = getEventIdList(param.getDoctorId());
        if(list.size() <= 0){
        	return memMeasureRecordList;
        }
        String ids = StringUtils.collectionToCommaDelimitedString(list);
        String sql = " SELECT DISTINCT t1.event_id,t1.measTime,t2.EventType AS event_type,t3.Memberid AS member_id,t3.MemName AS member_name, "
                   + " t3.Tel AS tel,t3.IdCard AS idCard,t3.Gender AS gender,t3.BirthDate  AS birthDate "
                   + " FROM ( SELECT aa1.EventId AS event_id,aa1.TestTime AS measTime FROM "
                   + " (SELECT b1.EventId,b1.TestTime FROM obsr b1 WHERE b1.DelTag = 0 AND b1.EventId IN ("+ids+"))aa1  "
                   + " UNION SELECT bb1.EventId AS event_id,bb1.TestTime AS measTime FROM "
                   + " (SELECT c1.EventId,c1.TestTime FROM osbp c1 WHERE c1.DelTag = 0 AND c1.EventId IN ("+ids+"))bb1 "
                   + " UNION SELECT cc1.EventId AS event_id,cc1.TestTime AS measTime FROM  "
                   + " (SELECT d1.EventId,d1.MeasTime AS TestTime FROM oecg d1 WHERE d1.DelTag = 0 AND d1.EventId IN ("+ids+"))cc1 "
                   + " )t1 LEFT JOIN omds t2 ON t1.event_id = t2.eventid LEFT JOIN omem t3 ON t2.Memberid = t3.Memberid "
                   + " WHERE t2.EventType IN ("+param.getMeasureTypeIds()+") ";
        StringBuffer  sqlBuffer = new StringBuffer();
        sqlBuffer.append(sql);
        if(!StringUtils.isEmpty(param.getParamName())){
            sqlBuffer.append(" AND  (t3.MemName like '%"+param.getParamName()+"%' or t3.idCard like '%"+param.getParamName()+"%' or t3.Tel like '%"+param.getParamName()+"%' ) ");
        }
        sqlBuffer.append(" ORDER BY measTime DESC LIMIT ?,? ");
        String finalStr = sqlBuffer.toString();
        int size = param.getPageSize();
        int beginRecord = (param.getPageNow()-1)*size;
        SqlRowSet rowSet = jdbcService.query(finalStr, new Object[]{beginRecord,size} );
        while(rowSet.next()){
            MemMeasureRecord record = new MemMeasureRecord();
            record.setMeasureRecordId(rowSet.getInt("event_id"));
            String birthDate = rowSet.getString("birthDate");
            if(!StringUtils.isEmpty(birthDate)){
                record.setAge(TimeUtil.getCurrentAgeByBirthdate(birthDate));
            }
            record.setMemberId(rowSet.getInt("member_id"));
            record.setIdCard(rowSet.getString("idCard"));
            record.setGender(rowSet.getString("gender"));
            record.setMemberName(rowSet.getString("member_name"));
            record.setTel(rowSet.getString("tel"));
            record.setMeasureType(rowSet.getInt("event_type"));
            String measTime = rowSet.getString("measTime");
            if(!StringUtils.isEmpty(measTime)){
                record.setMeasureTime(TimeUtil.paserDatetime2(measTime));
            }
            memMeasureRecordList.add(record);
        }
        return memMeasureRecordList;
    }
	
	/** 
     * @Title: findObsrRecordOne 
     * @Description: 根据事件id(eventId)查询一条血糖数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param eventId
     * @return
     * @throws Exception    
     * @retrun Obsr
     */
    public Obsr findObsrRecordOne(Integer eventId) throws Exception{
        String sql = " SELECT Docentry,EventId,Memberid,UploadTime,TestTime,BsValue,timePeriod,AnalysisResult,DeviceCode,BluetoothMacAddr FROM obsr WHERE EventId = ? AND DelTag = '0' order by TestTime desc limit 1 ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{eventId});
        while (rowSet.next()) {
            Obsr osbr = new Obsr();
            osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
            osbr.setBsValue(rowSet.getFloat("BsValue"));
            osbr.setEventId(rowSet.getLong("EventId"));
            osbr.setId(rowSet.getLong("Docentry"));
            osbr.setMemberId(rowSet.getInt("Memberid"));
            osbr.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
            osbr.setTimePeriod(rowSet.getInt("timePeriod"));
            osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            String bluetoothMacAddr = rowSet.getString("BluetoothMacAddr");
            if(!StringUtils.isEmpty(bluetoothMacAddr)){
                osbr.setBluetoothMacAddr(bluetoothMacAddr);
            }
            String deviceCode = rowSet.getString("DeviceCode");
            if(!StringUtils.isEmpty(deviceCode)){
                osbr.setBluetoothMacAddr(deviceCode);
            }
            return osbr;
        }
        return null;
    }
    
     /** 
     * @Title: findOsbpRecordOne 
     * @Description:  根据事件id(eventId)查询一条血压数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param eventId
     * @return
     * @throws Exception    
     * @retrun Osbp
     */
    public Osbp findOsbpRecordOne(Integer eventId) throws Exception{
        String sql = " SELECT Docentry,EventId,Memberid,UploadTime,TestTime,Abnormal,timePeriod,SBP,DBP,PulseRate,BluetoothMacAddr,DeviceCode FROM osbp WHERE EventId = ? AND DelTag = '0' order by TestTime desc limit 1 ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{eventId});
        while (rowSet.next()) {
            Osbp osbp = new Osbp();
            osbp.setAbnormal(rowSet.getString("Abnormal"));
            osbp.setDbp(rowSet.getInt("DBP"));
            osbp.setEventId(rowSet.getLong("EventId"));
            osbp.setId(rowSet.getLong("Docentry"));
            osbp.setPulseRate(rowSet.getInt("PulseRate"));
            osbp.setSbp(rowSet.getInt("SBP"));
            osbp.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
            osbp.setTimePeriod(rowSet.getInt("timePeriod"));
            osbp.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            osbp.setMemberId(rowSet.getInt("Memberid"));
            String bluetoothMacAddr = rowSet.getString("BluetoothMacAddr");
            if(!StringUtils.isEmpty(bluetoothMacAddr)){
                osbp.setBluetoothMacAddr(bluetoothMacAddr);
            }
            String deviceCode = rowSet.getString("DeviceCode");
            if(!StringUtils.isEmpty(deviceCode)){
                osbp.setBluetoothMacAddr(deviceCode);
            }
            return osbp;
        }
        return null;
    }
    
     /** 
     * @Title: findOecgRecordOne 
     * @Description: 根据事件id(eventId)查询一条心电(mini)数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param eventId
     * @return
     * @throws Exception    
     * @retrun Oecg
     */
    public Oecg findOecgRecordOne(Integer eventId) throws Exception{
        String sql = " select b.Memberid,b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
                + " from oecg b  where b.EventId = ? and b.delTag ='0' order by b.measTime desc limit 1 ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[] {eventId});
        while (rowSet.next()) {
            Oecg oecg = new Oecg();
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setEcgResult(rowSet.getInt("ECGResult"));
            List<Ecg2> ecg2s = new ArrayList<Ecg2> ();
            if(oecg.getEcgResult() != 0){
                ecg2s = findEcg2ById(oecg.getId() + "");
            }
            oecg.setEcg2s(ecg2s);
            oecg.setFs(rowSet.getInt("fs"));
            oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
            oecg.setEventId(rowSet.getLong("EventId"));
            oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
            oecg.setFastestTime(rowSet.getInt("FastestTime"));
            oecg.setFrepPsd(rowSet.getString("FreqPSD"));
            oecg.setHeartNum(rowSet.getInt("HeartNum"));
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
            oecg.setRawEcg(rowSet.getString("RawECG"));
            oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
            oecg.setRrInterval(rowSet.getString("RRInterval"));
            oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
            oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
            oecg.setStatusTag(rowSet.getInt("StatusTag"));
            oecg.setTimeLength(rowSet.getInt("TimeLength"));
            oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            oecg.setMemberId(rowSet.getInt("Memberid"));
            oecg.setSdnnLevel(rowSet.getInt("sdnnLevel"));
            oecg.setPnn50Level(rowSet.getInt("pnn50Level"));
            oecg.setHrviLevel(rowSet.getInt("hrviLevel"));
            oecg.setRmssdLevel(rowSet.getInt("rmssdLevel"));
            oecg.setTpLevel(rowSet.getInt("tplevel"));
            oecg.setVlfLevel(rowSet.getInt("vlfLevel"));
            oecg.setLfLevel(rowSet.getInt("lfLevel"));
            oecg.setHfLevel(rowSet.getInt("hfLevel"));
            oecg.setLhrLevel(rowSet.getInt("lhrLevel"));
            oecg.setHrLevel(rowSet.getInt("hrLevel"));
            oecg.setDeviceCode(rowSet.getString("DeviceCode"));
            oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            oecg.setPnn50(rowSet.getFloat("pnn50"));
            oecg.setSdnn(rowSet.getFloat("sdnn"));
            oecg.setHrvi(rowSet.getFloat("hrvi"));
            return oecg;
        }
        return null;
    }
    
     /** 
     * @Title: findThreeJoinOneRecordOne 
     * @Description: 根据事件id(eventId)查询一条三合一数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param eventId
     * @return
     * @throws Exception    
     * @retrun Oppg
     */
    public ReturnMeasureData findThreeJoinOneRecordOne(Integer eventId) throws Exception{
        String sql = "select b.Memberid,b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,"
                     +" b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel,"
                     +" c.BluetoothMacAddr as oppgBluetoothMacAddr, c.fs as oppgfs, c.Docentry as oppgDocentry,c.TimeLength as oppgTimeLength,c.UploadTime as oppgUploadTime,c.MeasureTime,c.PulsebeatCount,c.SlowPulse,"
                     +" c.SlowTime,c.QuickPulse,c.QuickTime,c.PulseRate,c.Spo,c.SPO1,c.CO,c.SI,c.SV,c.CI,c.SPI,c.K,c.K1,c.V,c.TPR,c.PWTT,c.Pm,c.AC, c.ImageNum,c.AbnormalData,c.Ppgrr,c.RawPPG,c.PPGResult,c.StatusTag as oppgStatusTag,"
                     +" c.kLevel,c.svLevel,c.coLevel,c.siLevel,c.vLevel,c.tprLevel,c.spoLevel,c.ciLevel,c.spiLevel,c.pwttLevel,c.acLevel,c.prLevel"
                     +" from oecg b LEFT JOIN oppg c on b.eventid = c.EventId"
                     +" where b.EventId = ?  and b.delTag='0'  and c.DelTag='0' order by b.measTime desc limit 1 ";
        
        SqlRowSet rowSet = jdbcService.query(sql,new Object[] {eventId});
        while (rowSet.next()) {
            ReturnMeasureData measureData =  new ReturnMeasureData();
            //封装心电对象
            Oecg oecg = new Oecg();
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setEcgResult(rowSet.getInt("ECGResult"));
            List<Ecg2> ecg2s = new ArrayList<Ecg2>();
            if(oecg.getEcgResult() == 1){
                ecg2s = findEcg2ById(oecg.getId() + "");
            }
            oecg.setEcg2s(ecg2s);
            oecg.setFs(rowSet.getInt("fs"));
            oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
            
            oecg.setEventId(rowSet.getLong("EventId"));
            oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
            oecg.setFastestTime(rowSet.getInt("FastestTime"));
            oecg.setFrepPsd(rowSet.getString("FreqPSD"));
            oecg.setHeartNum(rowSet.getInt("HeartNum"));
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
            oecg.setRawEcg(rowSet.getString("RawECG"));
            oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
            oecg.setRrInterval(rowSet.getString("RRInterval"));
            oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
            oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
            oecg.setStatusTag(rowSet.getInt("StatusTag"));
            oecg.setTimeLength(rowSet.getInt("TimeLength"));
            oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            oecg.setMemberId(rowSet.getInt("Memberid"));
            oecg.setSdnnLevel(rowSet.getInt("sdnnLevel"));
            oecg.setPnn50Level(rowSet.getInt("pnn50Level"));
            oecg.setHrviLevel(rowSet.getInt("hrviLevel"));
            oecg.setRmssdLevel(rowSet.getInt("rmssdLevel"));
            oecg.setTpLevel(rowSet.getInt("tplevel"));
            oecg.setVlfLevel(rowSet.getInt("vlfLevel"));
            oecg.setLfLevel(rowSet.getInt("lfLevel"));
            oecg.setHfLevel(rowSet.getInt("hfLevel"));
            oecg.setLhrLevel(rowSet.getInt("lhrLevel"));
            oecg.setHrLevel(rowSet.getInt("hrLevel"));
            oecg.setDeviceCode(rowSet.getString("DeviceCode"));
            oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            oecg.setPnn50(rowSet.getFloat("pnn50"));
            oecg.setSdnn(rowSet.getFloat("sdnn"));
            oecg.setHrvi(rowSet.getFloat("hrvi"));
            oecg.setEventType(3);
            
            //封装脉搏对象
            Oppg oppg = new Oppg();
            oppg.setFs(rowSet.getInt("oppgfs"));
            oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
            oppg.setAc(rowSet.getFloat("AC"));
            oppg.setCi(rowSet.getFloat("CI"));
            oppg.setCo(rowSet.getFloat("CO"));
            oppg.setEventId(rowSet.getLong("EventId"));
            oppg.setId(rowSet.getLong("oppgDocentry"));
            oppg.setImageNum(rowSet.getInt("ImageNum"));
            oppg.setK(rowSet.getFloat("K"));
            oppg.setK1(rowSet.getFloat("K1"));
            oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("MeasureTime")));
            oppg.setPm(rowSet.getFloat("Pm"));
            oppg.setPpgResult(rowSet.getInt("PPGResult"));
            oppg.setPpgrr(rowSet.getString("Ppgrr"));
            oppg.setPulsebeatCount(rowSet.getInt("PulsebeatCount"));
            oppg.setPulseRate(rowSet.getInt("PulseRate"));
            oppg.setPwtt(rowSet.getFloat("PWTT"));
            oppg.setQuickPulse(rowSet.getInt("QuickPulse"));
            oppg.setQuickTime(rowSet.getInt("QuickTime"));
            oppg.setRawPpg(rowSet.getString("RawPPG"));
            oppg.setSi(rowSet.getFloat("SI"));
            oppg.setSlowPulse(rowSet.getInt("SlowPulse"));
            oppg.setSlowTime(rowSet.getInt("SlowTime"));
            oppg.setSpi(rowSet.getFloat("SPI"));
            oppg.setSpo(rowSet.getInt("Spo"));
            oppg.setSpo1(rowSet.getInt("SPO1"));
            oppg.setStatusTag(rowSet.getInt("oppgStatusTag"));
            oppg.setSv(rowSet.getFloat("SV"));
            oppg.setTimeLength(rowSet.getInt("oppgTimeLength"));
            oppg.setTpr(rowSet.getFloat("TPR"));
            oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("oppgUploadTime")));
            oppg.setV(rowSet.getFloat("V"));
            oppg.setMemberId(rowSet.getInt("Memberid"));
            oppg.setK1Level(rowSet.getInt("kLevel"));
            oppg.setSvLevel(rowSet.getInt("svLevel"));
            oppg.setCoLevel(rowSet.getInt("coLevel"));
            oppg.setAcLevel(rowSet.getInt("acLevel"));
            oppg.setSiLevel(rowSet.getInt("siLevel"));
            oppg.setV1Level(rowSet.getInt("vLevel"));
            oppg.setTprLevel(rowSet.getInt("tprLevel"));
            oppg.setSpoLevel(rowSet.getInt("spoLevel"));
            oppg.setCiLevel(rowSet.getInt("ciLevel"));
            oppg.setSpiLevel(rowSet.getInt("spiLevel"));
            oppg.setPwttLevel(rowSet.getInt("pwttLevel"));
            oppg.setPrLevel(rowSet.getInt("prLevel"));
            oppg.setDeviceCode(rowSet.getString("DeviceCode"));
            oppg.setBluetoothMacAddr(rowSet.getString("oppgBluetoothMacAddr"));
            
            if(oecg.getEcgResult() == 1 || oppg.getPpgResult() == 1){
                measureData.setIsAbnormal(1);
            }
            Map<String,Object> datamap = new HashMap<String,Object>();
            datamap.put("oecg", oecg);
            datamap.put("oppg", oppg);
            measureData.setData(datamap);
            return measureData;
        }
        return null;
    }
    
     /** 
     * @Title: findEcg2ById 
     * @Description: 心电12种异常（ECG2） 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param ids
     * @return
     * @throws Exception    
     * @retrun List<Ecg2>
     */
    public List<Ecg2> findEcg2ById(String ids) throws Exception {
        List<Ecg2> ecgs = new ArrayList<Ecg2>();
        String sql = "select Docentry, abnName ,abnNum from ecg2 where Docentry in ("
                + ids + ")";
        SqlRowSet rowSet = jdbcService.query(sql);
        while (rowSet.next()) {
            Ecg2 ecg = new Ecg2();
            ecg.setAbnName(rowSet.getString("abnName"));
            ecg.setAbnNum(rowSet.getInt("abnNum"));
            ecg.setId(rowSet.getLong("Docentry"));
            ecg.setEcgs(findAnomalyEcgbyId(ecg.getId(), ecg.getAbnName()));
            ecgs.add(ecg);
        }
        return ecgs;
    }
	
     /** 
     * @Title: findAnomalyEcgbyId 
     * @Description: 获取异常心电
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param id
     * @param name
     * @return
     * @throws Exception    
     * @retrun List<AnomalyEcg>
     */
    public List<AnomalyEcg> findAnomalyEcgbyId(long id, String name)throws Exception {
        List<AnomalyEcg> ecgs = new ArrayList<AnomalyEcg>();
        String useNameString = getName(name);
        String sql = "select ObjectId,AbECGType,AbECGTime from ecg1 where Docentry=? and AbECGType like '%"
                + useNameString + "%'";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
        while (rowSet.next()) {
            AnomalyEcg ecg = new AnomalyEcg();
            ecg.setAbEcgTime(rowSet.getString("AbECGTime"));
            ecg.setAbEcgType(rowSet.getString("AbECGType"));
            ecg.setObjectId(rowSet.getString("ObjectId"));
            ecgs.add(ecg);
        }
        return ecgs;
    }
    
    private String getName(String name) {
        if ("Polycardia".equals(name)) {
            name = "ST";
        } else if ("Bradycardia".equals(name)) {
            name = "SB";
        } else if ("Arrest".equals(name)) {
            name = "SA";
        } else if ("Missed".equals(name)) {
            name = "MB";
        } else if ("Wide".equals(name)) {
            name = "WS";
        } else if ("PVB".equals(name)) {
            name = "VPB";
        } else if ("PAB".equals(name)) {
            name = "APB";
        } else if ("Insert_PVB".equals(name)) {
            name = "IVBP";
        } else if ("VT".equals(name)) {
            name = "VT";
        } else if ("Bigeminy".equals(name)) {
            name = "BG";
        } else if ("Trigeminy".equals(name)) {
            name = "TRG";
        } else if ("Arrhythmia".equals(name)) {
            name = "AR";
        }
        return name;
    }
    
    /** 
     * @Title: findMemOecgById 
     * @Description: 医生根据心电oecg的id获取会员的某条心电测量数据(不分三合一和mini)
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public Oecg findMemOecgById(long id) throws Exception {
        String sql = "select b.sdnn,b.pnn50,b.hrvi, b.BluetoothMacAddr, b.DeviceCode, a.eventType ,b.fs, b.memberId, b.measTime,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
                + " from  oecg b left join omds a on b.EventId=a.EventId  where b.Docentry=?";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
        if (rowSet.next()) {
            Oecg oecg = new Oecg();
            oecg.setFs(rowSet.getInt("fs"));
            oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
            oecg.setEcgResult(rowSet.getInt("ECGResult"));
            oecg.setEventId(rowSet.getLong("EventId"));
            oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
            oecg.setFastestTime(rowSet.getInt("FastestTime"));
            oecg.setFrepPsd(rowSet.getString("FreqPSD"));
            oecg.setHeartNum(rowSet.getInt("HeartNum"));
            oecg.setId(id);
            oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
                    .getString("measTime")));
            oecg.setRawEcg(rowSet.getString("RawECG"));
            oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
            oecg.setRrInterval(rowSet.getString("RRInterval"));
            oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
            oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
            oecg.setStatusTag(rowSet.getInt("StatusTag"));
            oecg.setTimeLength(rowSet.getInt("TimeLength"));
            oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
                    .getString("UploadTime")));
            oecg.setMemberId(rowSet.getInt("memberId"));
            oecg.setSdnnLevel(rowSet.getInt("sdnnLevel"));
            oecg.setPnn50Level(rowSet.getInt("pnn50Level"));
            oecg.setHrviLevel(rowSet.getInt("hrviLevel"));
            oecg.setRmssdLevel(rowSet.getInt("rmssdLevel"));
            oecg.setTpLevel(rowSet.getInt("tplevel"));
            oecg.setVlfLevel(rowSet.getInt("vlfLevel"));
            oecg.setLfLevel(rowSet.getInt("lfLevel"));
            oecg.setHfLevel(rowSet.getInt("hfLevel"));
            oecg.setLhrLevel(rowSet.getInt("lhrLevel"));
            oecg.setHrLevel(rowSet.getInt("hrLevel"));
            oecg.setDeviceCode(rowSet.getString("DeviceCode"));
            oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            oecg.setPnn50(rowSet.getFloat("pnn50"));
            oecg.setSdnn(rowSet.getFloat("sdnn"));
            oecg.setHrvi(rowSet.getFloat("hrvi"));
            return oecg;

        }
        return null;
    }

    /** 
     * @Title: findMemOppgById 
     * @Description: 医生根据脉搏oppg的id获取会员的某条脉搏数据
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-22
     * @throws Exception    
     * @retrun String
     */
    public Oppg findMemOppgById(long id) throws Exception {
        String sql = "select b.BluetoothMacAddr, b.DeviceCode, b.fs, b.memberId, b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
                + "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel "
                + " from oppg b  where b.Docentry=? ";

        SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
        if (rowSet.next()) {
            Oppg oppg = new Oppg();
            oppg.setFs(rowSet.getInt("fs"));
            oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
            oppg.setAc(rowSet.getFloat("AC"));
            oppg.setCi(rowSet.getFloat("CI"));
            oppg.setCo(rowSet.getFloat("CO"));
            oppg.setEventId(rowSet.getLong("EventId"));
            oppg.setId(id);
            oppg.setImageNum(rowSet.getInt("ImageNum"));
            oppg.setK(rowSet.getFloat("K"));
            oppg.setK1(rowSet.getFloat("K1"));
            oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
                    .getString("MeasureTime")));
            oppg.setPm(rowSet.getFloat("Pm"));
            oppg.setPpgResult(rowSet.getInt("PPGResult"));
            oppg.setPpgrr(rowSet.getString("Ppgrr"));
            oppg.setPulsebeatCount(rowSet.getInt("PulsebeatCount"));
            oppg.setPulseRate(rowSet.getInt("PulseRate"));
            oppg.setPwtt(rowSet.getFloat("PWTT"));
            oppg.setQuickPulse(rowSet.getInt("QuickPulse"));
            oppg.setQuickTime(rowSet.getInt("QuickTime"));
            oppg.setRawPpg(rowSet.getString("RawPPG"));
            oppg.setSi(rowSet.getFloat("SI"));
            oppg.setSlowPulse(rowSet.getInt("SlowPulse"));
            oppg.setSlowTime(rowSet.getInt("SlowTime"));
            oppg.setSpi(rowSet.getFloat("SPI"));
            oppg.setSpo(rowSet.getInt("Spo"));
            oppg.setSpo1(rowSet.getInt("SPO1"));
            oppg.setStatusTag(rowSet.getInt("StatusTag"));
            oppg.setSv(rowSet.getFloat("SV"));
            oppg.setTimeLength(rowSet.getInt("TimeLength"));
            oppg.setTpr(rowSet.getFloat("TPR"));
            oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
                    .getString("UploadTime")));
            oppg.setV(rowSet.getFloat("V"));
            oppg.setMemberId(rowSet.getInt("memberId"));
            oppg.setK1Level(rowSet.getInt("kLevel"));
            oppg.setSvLevel(rowSet.getInt("svLevel"));
            oppg.setCoLevel(rowSet.getInt("coLevel"));
            oppg.setAcLevel(rowSet.getInt("acLevel"));
            oppg.setSiLevel(rowSet.getInt("siLevel"));
            oppg.setV1Level(rowSet.getInt("vLevel"));
            oppg.setTprLevel(rowSet.getInt("tprLevel"));
            oppg.setSpoLevel(rowSet.getInt("spoLevel"));
            oppg.setCiLevel(rowSet.getInt("ciLevel"));
            oppg.setSpiLevel(rowSet.getInt("spiLevel"));
            oppg.setPwttLevel(rowSet.getInt("pwttLevel"));
            oppg.setDeviceCode(rowSet.getString("DeviceCode"));
            oppg.setPrLevel(rowSet.getInt("prLevel"));
            oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            return oppg;
        }
        return null;
    }
    
    /** 
     * @Title: findDocByAccount 
     * @Description: 根据医生账号查询该账号是否有效
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun String
     */
    public Odoc findDocByAccount(String account) throws Exception{
        String sql = " SELECT DISTINCT a.Docid FROM odoc a,doc1 b where a.Docid = b.Docid AND a.Tag = 'T' AND (b.DocAcc = ? or a.Email = ? or a.Tel= ? ) ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{account,account,account});
        while(rowSet.next()){
            Odoc doctor = new Odoc();
            doctor.setDoctorId(rowSet.getInt("Docid"));
            return doctor;
       }
       return null;
    }
    
    /** 
     * @Title: insertEventId 
     * @Description: 获取事件id
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun long
     */
    public synchronized long insertEventId() throws Exception{
        String sql = " INSERT INTO omds_eventid VALUES() ";
        jdbcService.doExecute2(sql);
        long id = jdbcService.getMaxId("omds_eventid", "eventId");
        return id;
    }
    
    /** 
     * @Title: insertId 
     * @Description: 生成表(oecg,obsr,osbp.oppg)的主键
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun long
     */
    public synchronized long insertId(String tableName) throws Exception {
        String sql = " INSERT INTO " + tableName + " VALUES () ";
        long id =  jdbcService.addId(sql);
        return id;
    }
    
     /** 
     * @Title: saveOmdsData 
     * @Description: 医生保存会员测量事件到事件分表中 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun Integer
     */
    public Integer saveMemOmdsData(Omds omds) throws Exception {
        int count = 0;
        int tableNum = (int) (omds.getEventId() % 100);
        String tableName = "omds_" + tableNum;
        String sql = "INSERT INTO "
                + tableName
                + " ( eventid ,Memberid ,UploadTime ,EventType ,WheAbnTag ,StatusTag,TimeLength ) VALUES (?,?,?,?,?,?,?)";
        count = jdbcService.doExecuteSQL(sql,new Object[] { omds.getEventId(), omds.getMemberId(),omds.getUploadTime(), omds.getDataType(),
                        omds.getWheAbnTag(), omds.getStatusTag(),omds.getTimeLength() });
        return count;

    }
    
    /** 
     * @Title: uploadMemBloodGlucose 
     * @Description: 医生保存会员血糖记录到血糖分表中 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun Integer
     */
    public Integer uploadMemBloodGlucose(Obsr obsr) throws Exception {
        int count = 0;
        int tableNum = (int) (obsr.getId() % 100);
        String tableName = "obsr_" + tableNum;
        String sql = " INSERT INTO "
                + tableName
                + " (Docentry,EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,BsValue ,timePeriod ,AnalysisResult ,DeviceCode ,BluetoothMacAddr ) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        count = jdbcService.doExecuteSQL(sql, new Object[] { obsr.getId(), obsr.getEventId(), obsr.getMemberId(), obsr.getUploadTime(),
                        obsr.getTestTime(), 0, obsr.getBsValue(),obsr.getTimePeriod(), obsr.getAnalysisResult(),obsr.getDeviceCode(), obsr.getBluetoothMacAddr() });
        return count;
    }

    /** 
     * @Title: uploadMemBloodPressure 
     * @Description: 医生保存会员血压记录到血压分表中 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun Integer
     */
    public Integer uploadMemBloodPressure(Osbp osbp) throws Exception {
        int count = 0;
        int tableNum = (int) (osbp.getId() % 100);
        String tableName = "osbp_" + tableNum;
        String sql = "INSERT INTO "
                + tableName
                + " (Docentry,EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,Abnormal ,timePeriod ,SBP ,DBP ,PulseRate ,BluetoothMacAddr ,DeviceCode ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        count = jdbcService.doExecuteSQL(sql,new Object[] { osbp.getId(), osbp.getEventId(),osbp.getMemberId(), osbp.getUploadTime(),osbp.getTestTime(), 0, osbp.getAbnormal(),
                        osbp.getTimePeriod(), osbp.getSbp(), osbp.getDbp(),osbp.getPulseRate(), osbp.getBluetoothMacAddr(),osbp.getDeviceCode() });
        return count;
    }

    /** 
     * @Title: uploadMemMini 
     * @Description: 医生保存会员心电记录到心电分表中  
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun Integer
     */
    public Integer uploadMemMini(Oecg oecg) throws Exception {
        int count = 0;
        int tableNum = (int) (oecg.getId() % 100);
        String tableName = "oecg_" + tableNum;
        String sql = "INSERT INTO "
                + tableName
                + " ( Docentry ,EventId ,Memberid ,UploadTime ,DeviceCode ,BluetoothMacAddr ,RawECG ,DelTag ,StatusTag,fs,TimeLength,MeasTime,addValue) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        count = jdbcService.doExecuteSQL(sql,new Object[] { oecg.getId(), oecg.getEventId(),oecg.getMemberId(), oecg.getUploadTime(),oecg.getDeviceCode(), oecg.getBluetoothMacAddr(),
                        oecg.getRawEcg(), 0, 0, oecg.getFs(),oecg.getTimeLength(), oecg.getMeasureTime(),oecg.getAddValue() });
        return count;
    }

    /** 
     * @Title: uploadMemPulse 
     * @Description: 医生保存会员脉搏记录到脉搏分表中 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-02-24
     * @throws Exception    
     * @retrun Integer
     */
    public Integer uploadMemPulse(Oppg oppg) throws Exception {
        int count = 0;
        int tableNum = (int) (oppg.getId() % 100);
        String tableName = "oppg_" + tableNum;
        String sql = "INSERT INTO "
                + tableName
                + "( Docentry ,EventId ,Memberid ,UploadTime ,RawPPG ,BluetoothMacAddr ,DeviceCode ,StatusTag ,DelTag,FS,TimeLength,MeasureTime,addValue ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        count = jdbcService.doExecuteSQL(sql,new Object[] { oppg.getId(), oppg.getEventId(),oppg.getMemberId(), oppg.getUploadTime(),oppg.getRawPpg(), oppg.getBluetoothMacAddr(),oppg.getDeviceCode(),
                               0, 0, oppg.getFs(),oppg.getTimeLength(), oppg.getMeasureTime(),oppg.getAddValue() });
        return count;
    }
    
    /** 
     * @Title: findMemHeadImg 
     * @Description: 医生获取会员头像 
     * @param omds
     * @author liuxiaoqin
     * @createDate 2016-04-08
     * @throws Exception    
     * @retrun Integer
     */
    public String findMemHeadImg(Integer memberId) throws Exception{
        String headImg = "";
        String sql = " SELECT Memberid,HeadAddress FROM omem WHERE Memberid = ?  ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId});
        while(rowSet.next()){
        	String headAddress = rowSet.getString("HeadAddress");
        	if(!StringUtils.isEmpty(headAddress)){
        	    headImg = headAddress;
        	}
        }
        return headImg;
    }
    
}
