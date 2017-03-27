package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zkhk.constants.Constants;
import com.zkhk.dao.DocHealthExamDao;
import com.zkhk.entity.MemDisease;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.util.TimeUtil;

/**
 * @ClassName:     DocHealthExamDaoImpl.java 
 * @Description:   健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年03月23日 下午4:13:46
*****/
@Repository(value="docHealthExamDao")
public class DocHealthExamDaoImpl implements DocHealthExamDao{
    
    @Resource
    private JdbcService jdbcService;
    
    /** 
     * @Title: findLineNum 
     * @Description: 获取行号，新增疾病的时候需要
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer findLineNum(Integer memberId,Integer diseaseId) throws Exception{
        int count = 0;
        String sql = " SELECT LineNum FROM mem3 where DiseaseID = ? AND Memberid = ? ORDER BY LineNum DESC LIMIT 1 ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{diseaseId,memberId});
        while(rowSet.next()) {
            count = rowSet.getInt("LineNum");
        }
        return count;
    }
    
    /** 
     * @Title: findMaxLineNum 
     * @Description: 获取最大行号
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer findMaxLineNum(Integer memberId) throws Exception{
        int count = 0;
        String sql = " SELECT LineNum FROM mem3 where  Memberid = ? ORDER BY LineNum DESC LIMIT 1 ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            count = rowSet.getInt("LineNum");
        }
        return count;
    }
    
    /** 
     * @Title: memHasTheDisease 
     * @Description: 医生查询该会员是否已经有这个疾病。
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer memHasTheDisease(MemDisease param) throws Exception{
        int count = 0;
        String sql = " SELECT COUNT(Memberid) FROM mem3 where DiseaseID = ? AND Memberid = ? AND LineNum = ? ";
        count = jdbcService.queryForInt(sql, new Object[]{param.getDiseaseId(),param.getMemberId(),param.getLineNum()});
        return count;
    }
    
    /** 
     * @Title: addMemDiseases 
     * @Description: 医生新增会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer addMemDiseases(MemDisease param) throws Exception{
        int count = 0;
        String sql = "";
        String digTime = param.getDiagTime();
        if(!StringUtils.isEmpty(digTime)){
        	sql = " INSERT INTO mem3 (Memberid,LineNum,DiseaseID,DiseaseName,DiagTime,CreateTime) VALUES(?,?,?,?,?,?) ";
            count = jdbcService.doExecuteSQL(sql, new Object[]{param.getMemberId(),param.getLineNum(),param.getDiseaseId(),param.getDiseaseName(),param.getDiagTime(),new Date()});
        }else{
        	sql = " INSERT INTO mem3 (Memberid,LineNum,DiseaseID,DiseaseName,CreateTime) VALUES(?,?,?,?,?) ";
            count = jdbcService.doExecuteSQL(sql, new Object[]{param.getMemberId(),param.getLineNum(),param.getDiseaseId(),param.getDiseaseName(),new Date()});
        }
        return count;
    }
    
    /** 
     * @Title: findVisitRecord 
     * @Description: 查询会员是否有随访记录
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer findVisitRecord(Integer memberId,Integer diseaseId) throws Exception{
        int count = 0;
        String sql = "";
        if(diseaseId == 2){
            sql = " SELECT COUNT(DiabetesID) FROM ph_diabetes where MemberID = ? ";
        }
        if(diseaseId == 1){
            sql = " SELECT COUNT(HypertensionID) FROM ph_hypertension where  MemberID = ? ";
        }
        count = jdbcService.queryForInt(sql, new Object[]{memberId});
        return count;
    }
    
    /** 
     * @Title: deleteMemDiseases 
     * @Description: 医生删除会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun Integer
     */
    public Integer deleteMemDiseases(MemDisease param) throws Exception{
        int count = 0;
        String sql = " DELETE FROM mem3 WHERE Memberid = ? AND LineNum = ? AND DiseaseID = ? ";
        count = jdbcService.doExecuteSQL(sql, new Object[]{param.getMemberId(),param.getLineNum(),param.getDiseaseId()});
        return count;
    }
    
    /** 
     * @Title: findMemDiseases 
     * @Description: 医生查询该会员是否已经有这个疾病。
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws Exception    
     * @retrun List<MemDiseaser>
     */
    public List<MemDisease> findMemDiseases(Integer memberId) throws Exception{
        List<MemDisease> list = new ArrayList<MemDisease>();
        String sql = " SELECT Memberid,LineNum,DiseaseID,DiseaseName,DiagTime FROM mem3 WHERE Memberid = ? ORDER BY DiagTime DESC ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            MemDisease disease = new MemDisease();
            disease.setMemberId(memberId);
            disease.setLineNum(rowSet.getInt("LineNum"));
            disease.setDiseaseId(rowSet.getInt("DiseaseID"));
            disease.setDiseaseName(rowSet.getString("DiseaseName"));
            Date date = rowSet.getDate("DiagTime");
            if(date != null){
                String diagTime = TimeUtil.formatDate(date);
                disease.setDiagTime(diagTime);
            }
            int diseaseId = disease.getDiseaseId();
            if(diseaseId == 1 || diseaseId == 2){
                int count = findVisitRecord(disease.getMemberId(),disease.getDiseaseId());
                if(count > 0){
                    disease.setHasVisitRecord(1);
                }else{
                    disease.setHasVisitRecord(0);
                }
            }else{
                disease.setHasVisitRecord(0);
            }
            list.add(disease);
            
        }
        return list;
    }
    
    /** 
     * @Title: getMemFamilyHistory 
     * @Description: 医生查询会员的家族史 
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun List<Map<String,Object>>
     */
    public List<Map<String,Object>> getMemFamilyHistory(Integer memberId) throws Exception{
        List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
        String sql = " SELECT m.Relation FROM mem4 m WHERE m.memberID = ? GROUP BY m.Relation ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            Map<String,Object> newMap = new HashMap<String, Object>();
            newMap.put("memberId", memberId);
            Integer relation = rowSet.getInt("Relation");
            if(relation != null && relation >0){
                /*获取每个家庭成员的疾病id  begin*/
                List<String> diseaseNamesStr = new ArrayList<String>();
                String diseaseNames = "";
                String diseaseName = "";
                String sqlNew = " SELECT DiseaseID,DiseaseName FROM mem4 WHERE memberID = ? AND Relation = ? order by DiseaseID ";
                String allDiseaseIds = Constants.ALL_DISEASE_IDS;
                SqlRowSet rowSetNew = jdbcService.query(sqlNew, new Object[]{memberId,relation});
                while(rowSetNew.next()){
                    String diseaseId = rowSetNew.getString("DiseaseID");
                    if(allDiseaseIds.contains(diseaseId)){
                        diseaseNamesStr.add(conventDiseaseName(diseaseId));
                    }
                    diseaseName = rowSetNew.getString("DiseaseName");
                }
                if(diseaseNamesStr != null && diseaseNamesStr.size() >0){
                    diseaseNames = StringUtils.collectionToDelimitedString(diseaseNamesStr, ",");
                    if(!StringUtils.isEmpty(diseaseName)){
                        diseaseNames += Constants.LEFT_BRACKET + diseaseName + Constants.RIGHT_BRACKET;
                    }
                }
                /*获取每个家庭成员的疾病id  end*/
                newMap.put("relation", conventRelationName(relation));
                newMap.put("diseaseName", diseaseNames);
            }
            map.add(newMap);
        }
        return map;
    }
    
    /** 
     * @Title: getMemGroup 
     * @Description: 医生查询会员的分组 
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun List<Map<String,Object>>
     */
    public List<Map<String,Object>> getMemGroup(Integer memberId) throws Exception{
        List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
        String sql = " SELECT e.Memberid,f.Path,f.OrgId,g.OrgName AS orgName FROM ompb e LEFT JOIN omgs f ON e.MemGrpid = f.MemGrpid "
        		   + " LEFT JOIN orgs g ON f.OrgId = g.OrgId where e.Memberid = ? ORDER BY e.MemGrpid ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        List<String> list = new ArrayList<String>();
        while(rowSet.next()) {
            String path = rowSet.getString("Path");
            String memGrpName = "";
            String orgName = rowSet.getString("orgName");
            String groupName = "";
            if(!StringUtils.isEmpty(path)){
                String memGrpids = path.substring(1, path.length()-1);
                String newSql = " SELECT GROUP_CONCAT(MemGrpName) as memGrpName FROM omgs WHERE MemGrpid IN ("+ memGrpids +") ";
                SqlRowSet newRowSet = jdbcService.query(newSql);
                while(newRowSet.next()){
                    memGrpName = newRowSet.getString("memGrpName");
                }
            }
            groupName = orgName +","+ memGrpName;
            if(!StringUtils.isEmpty(path)){
            	list.add(groupName);
            }
        }
        if(list.size() > 0){
            for(int i= 0;i <list.size();i++){
            	Map<String,Object> newMap = new HashMap<String, Object>();
                int id = i+1;
                String memGrpId = "分组" + id;
                String name = StringUtils.replace(list.get(i), ",", "->> ");
                newMap.put("memGrpId", memGrpId);
                newMap.put("memGrpName",name);
                map.add(newMap);
            }
        }
        return map;
    }
    
    /** 
     * @Title: getMemEmergencyContact 
     * @Description: 医生查询会员的紧急联系人
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun Map<String,Object>
     */
    public List<Map<String,Object>> getMemEmergencyContact(Integer memberId) throws Exception{
    	List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        String sql = " SELECT DISTINCT Memberid,Relation,ContactName,ContactMobPhone,MessageTag,ReceiveTag FROM mem1 WHERE Memberid = ? ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
        	Map<String,Object> map = new HashMap<String,Object>();
            map.put("memberId", rowSet.getInt("Memberid"));
            map.put("contactName", rowSet.getString("ContactName"));
            map.put("contactMobPhone", rowSet.getString("ContactMobPhone"));
            map.put("relation", rowSet.getString("Relation"));
            String messageTag = rowSet.getString("MessageTag");
            if(StringUtils.isEmpty(messageTag)){
            	messageTag = "N";
            }
            map.put("messageTag",messageTag );
            String receiveTag = rowSet.getString("ReceiveTag");
            if(StringUtils.isEmpty(receiveTag)){
            	receiveTag = "N";
            }
            map.put("receiveTag",receiveTag);
            mapList.add(map);
        }
        return mapList;
    }
    
    /** 
     * @Title: getMemLifeStyle 
     * @Description: 医生查询会员的生活习惯
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun Map<String,Object>
     */
    public Map<String,Object> getMemLifeStyle(Integer memberId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT DISTINCT Memberid,Smoking,DoDrink,DoNtFood,StapleFood,SleepCond,LikeSports,MoveLong,TimeSeg,WeekNumTimes FROM mem7 WHERE Memberid = ? ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            map.put("memberId", rowSet.getInt("Memberid"));
            String smoking = rowSet.getString("Smoking");
            if(StringUtils.isEmpty(smoking)){
            	smoking = "N";
            }
            map.put("smoking",smoking);
            String doDrink = rowSet.getString("DoDrink");
            if(StringUtils.isEmpty(doDrink)){
            	doDrink = "N";
            }
            map.put("doDrink", doDrink);
            map.put("doNtFood", rowSet.getString("DoNtFood"));
            map.put("stapleFood", rowSet.getString("StapleFood"));
            map.put("sleepCond", rowSet.getString("SleepCond"));
            map.put("likeSports", rowSet.getString("LikeSports"));
            map.put("moveLong", rowSet.getString("MoveLong"));
            String timeSeg = rowSet.getString("TimeSeg");
            String timePeriod = "";
            if(!StringUtils.isEmpty(timeSeg)){
            	if(timeSeg.equals("0")){
            		timePeriod = Constants.SPORT_PERIOD_0;
            	}else if(timeSeg.equals("1")){
            		timePeriod = Constants.SPORT_PERIOD_1;
            	}else if(timeSeg.equals("2")){
            		timePeriod = Constants.SPORT_PERIOD_2;
            	}else if(timeSeg.equals("3")){
            		timePeriod = Constants.SPORT_PERIOD_3;
            	}else if(timeSeg.equals("4")){
            		timePeriod = Constants.SPORT_PERIOD_4;
            	}else if(timeSeg.equals("5")){
            		timePeriod = Constants.SPORT_PERIOD_5;
            	}else if(timeSeg.equals("6")){
            		timePeriod = Constants.SPORT_PERIOD_6;
            	}else if(timeSeg.equals("7")){
            		timePeriod = Constants.SPORT_PERIOD_7;
            	}else if(timeSeg.equals("8")){
            		timePeriod = Constants.SPORT_PERIOD_8;
            	}
            }
            map.put("timeSeg",timePeriod);
            map.put("weekNumTimes", rowSet.getInt("WeekNumTimes"));
        }
        return map;
    }
    
    /** 
     * @Title: getMemPhysicalExamination 
     * @Description: 医生查询会员的体格检查
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun Map<String,Object>
     */
    public Map<String,Object> getMemPhysicalExamination(Integer memberId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT DISTINCT Memberid,BloodType,AllergicHis,AllergicHisName,Height,Weight,Waist,Hip,Pulse,HeartRate,BloodH,BloodL,FastingGlucose,"
                   + " UricAcid,TotalCholesterol,Triglyceride,DensityLipop,LDLip FROM mem2 WHERE Memberid = ? ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            map.put("memberId", rowSet.getInt("Memberid"));
            String bloodType = rowSet.getString("BloodType");
            if(StringUtils.isEmpty(bloodType)){
            	bloodType = Constants.BLOOD_TYPE_UNKNOWN;
            }
            map.put("bloodType", bloodType);
            map.put("allergicHis", rowSet.getString("AllergicHis"));
            map.put("allergicHisName", rowSet.getString("AllergicHisName"));
            map.put("height", rowSet.getInt("Height"));
            map.put("weight", rowSet.getDouble("Weight"));
            map.put("waist", rowSet.getInt("Waist"));
            map.put("hip", rowSet.getInt("Hip"));
            map.put("pulse", rowSet.getInt("Pulse"));
            map.put("heartRate", rowSet.getInt("HeartRate"));
            map.put("bloodH", rowSet.getInt("BloodH"));
            map.put("bloodL", rowSet.getInt("BloodL"));
            map.put("fastingGlucose", rowSet.getDouble("FastingGlucose"));
            map.put("uricAcid", rowSet.getInt("UricAcid"));
            map.put("totalCholesterol", rowSet.getDouble("TotalCholesterol"));
            map.put("triglyceride", rowSet.getDouble("Triglyceride"));
            map.put("densityLipop", rowSet.getDouble("DensityLipop"));
            map.put("lDLip", rowSet.getDouble("LDLip"));
        }
        return map;
    }
    
    /** 
     * @Title: getMemBasicInfo 
     * @Description: 医生查询会员的基本信息
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun Map<String,Object>
     */
    public Map<String,Object> getMemBasicInfo(Integer memberId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT DISTINCT a.Memberid,a.LogName,a.MemName,a.Gender,a.BirthDate,a.Tel,a.Email,a.IdCard,a.NativeAddr,a.Address, "
                   + " a.MarryStatus,a.EducationStatus,a.Occupation,a.DocName,b.unique_id,b.relation,b.company,b.neighborhood_committee,b.province,b.city,b.area,b.village, "
                   + " b.live_status,b.nation,b.pay_type,b.other_pay,b.medical_account,b.agro_account,b.fetation_status,b.certificate_type,"
                   + " b.file_type,b.file_status,b.family_code,c.MemName AS memType,c.MemDesc AS memTypeDesc "
                   + " FROM omem a LEFT JOIN d_omem_relation b ON a.unique_id = b.unique_id LEFT JOIN omes c ON a.MemId = c.MemId WHERE a.Memberid = ? AND a.UseTag = 'T' ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()) {
            map.put("memberId", rowSet.getInt("Memberid"));
            map.put("logName", rowSet.getString("LogName"));
            map.put("memName", rowSet.getString("MemName"));
            map.put("birthDate", rowSet.getString("BirthDate"));
            map.put("gender", rowSet.getString("Gender"));
            map.put("email", rowSet.getString("Email"));
            map.put("tel", rowSet.getString("Tel"));
            map.put("idcard", rowSet.getString("IdCard"));
            map.put("nativeAddr", rowSet.getString("NativeAddr"));
            map.put("address", rowSet.getString("Address"));
            map.put("marryStatus", rowSet.getString("MarryStatus"));
            map.put("educationStatus", rowSet.getString("EducationStatus"));
            map.put("occupation", rowSet.getString("Occupation"));
            map.put("docName", rowSet.getString("DocName"));
            map.put("uniqueId", rowSet.getString("unique_id"));
            map.put("relation", rowSet.getInt("relation"));
            map.put("company", rowSet.getString("company"));
            String neighborhood = rowSet.getString("neighborhood_committee");
            map.put("administrativeArea", neighborhood);
            map.put("liveStatus", rowSet.getInt("live_status"));
            map.put("nation", rowSet.getString("nation"));
            map.put("payType", rowSet.getInt("pay_type"));
            map.put("otherPay", rowSet.getString("other_pay"));
            map.put("medicalAccount", rowSet.getString("medical_account"));
            map.put("agroAccount", rowSet.getString("agro_account"));
            map.put("fetationStatus", rowSet.getInt("fetation_status"));
            Integer certificateType = rowSet.getInt("certificate_type");
            if(certificateType == null || certificateType == 0){
            	certificateType = 1;
            }
            map.put("certificateType",certificateType);
            map.put("fileType", rowSet.getInt("file_type"));
            map.put("fileStatus", rowSet.getInt("file_status"));
            map.put("familyCode", rowSet.getString("family_code"));
            map.put("memType", rowSet.getString("memType"));
            map.put("memTypeDesc", rowSet.getString("memTypeDesc"));
        }
        return map;
    }
    
    /** 
     * @Title: findTcmOrAgedQuestionnaire 
     * @Description: 获取中医体质和老年人生活能力评估问卷
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun Map<String,Object>
     */
    public Map<String,Object> findTcmOrAgedQuestionnaire(Integer doctorId,Integer memberId,Long hExamID,String QuestionType) throws Exception{
    	 Map<String,Object> map = new HashMap<String,Object>();
    	 String questName = "";
    	 if(QuestionType.equals("aged")){
    		 questName = Constants.AGED_QUESTIONNAIRE;
    	 }else{
    		 questName = Constants.TCM_QUESTIONNAIRE;
    	 }
    	 String sql = "";
    	 /* 获取问卷id begin*/
    	 Integer qustId = 0;
		 String publisherTime = "";
		 String qustSql = "";
		 if(hExamID != null && hExamID >0){
			 qustSql = " SELECT b.Qustid,b.Qustname,b.CreateDate FROM odoc a LEFT JOIN omfq b ON a.OrgId = b.OrgId LEFT JOIN ouai c ON b.Qustid = c.Qustid "
		 		        + "  WHERE a.Docid = ? AND b.Qustname = ? AND b.QustTag = 'T' AND c.HExamID = "+hExamID+" ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
		 }else{
			 qustSql = " SELECT b.Qustid,b.Qustname,b.CreateDate FROM odoc a LEFT JOIN omfq b ON a.OrgId = b.OrgId "
					 + " WHERE a.Docid = ? AND b.Qustname = ? AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
		 }
		 SqlRowSet qustSet = jdbcService.query(qustSql,new Object[]{doctorId,questName});
		 boolean qustTag = qustSet.next();
		 if(qustTag == true){
			 qustId = qustSet.getInt("Qustid");
			 Date date = qustSet.getDate("CreateDate");
    		 if(date != null){
    			 publisherTime = TimeUtil.formatDatetime2(date);
    		 }
		 }else{
			 String newQustSql = "";
			 if(hExamID != null && hExamID >0){
				 newQustSql = " SELECT b.Qustid,b.Qustname,b.CreateDate FROM omfq b LEFT JOIN ouai c ON b.Qustid = c.Qustid WHERE b.Qustname = ? "
						    + " AND c.HExamID = "+hExamID+" AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
			 }else{
				 newQustSql = " SELECT b.Qustid,b.Qustname,b.CreateDate FROM omfq b WHERE b.Qustname = ? AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
			 }
			 SqlRowSet newQustSet = jdbcService.query(newQustSql,new Object[]{questName});
			 boolean newQustTag = newQustSet.next();
			 if(newQustTag == true){
				 qustId = newQustSet.getInt("Qustid");
				 Date date = newQustSet.getDate("CreateDate");
	    		 if(date != null){
	    			 publisherTime = TimeUtil.formatDatetime2(date);
	    		 }
			 }else{
				 String finalQustSql = " SELECT b.Qustid,b.Qustname,b.CreateDate FROM omfq b WHERE b.Qustname = ? AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
				 SqlRowSet finalQustSet = jdbcService.query(finalQustSql,new Object[]{questName});
				 while(finalQustSet.next()){
					 qustId = finalQustSet.getInt("Qustid");
					 Date date = finalQustSet.getDate("CreateDate");
		    		 if(date != null){
		    			 publisherTime = TimeUtil.formatDatetime2(date);
		    		 }
				 }
			 }
		 }
    	 /* 获取问卷id end*/
		 Integer ansNumber = 0;
		 
    	 if(hExamID != null && hExamID >0){
    		 sql = " SELECT AnsNumber FROM ouai WHERE HExamID = ? AND Memberid = ? AND Qustid = ? ORDER BY AnsNumber DESC LIMIT 1";
    		 SqlRowSet rowSet = jdbcService.query(sql,new Object[]{hExamID,memberId,qustId});
    		 while(rowSet.next()){
    			 Integer newAnsNumber = rowSet.getInt("AnsNumber");
    			 if(newAnsNumber != null){
    				 ansNumber = rowSet.getInt("AnsNumber");
    			 }
    		 }
    	 }
    	 List<Map<String,Object>> problemList = new ArrayList<Map<String,Object>>();
		 /* 查询问卷的每个题目 begin */
		 String sqlProblem = " SELECT Qustid,Problemid,ProDesc,LineNum,AnsType FROM mfq1 WHERE Qustid = ? ORDER BY Problemid ";
		 SqlRowSet problemSet = jdbcService.query(sqlProblem,new Object[]{qustId});
		 while(problemSet.next()){
			 Map<String,Object> problemMap = new HashMap<String,Object>();
			 Integer proQustId = problemSet.getInt("Qustid");
			 Integer problemId = problemSet.getInt("Problemid");
			 problemMap.put("id",problemId);
			 problemMap.put("proDes",problemSet.getString("ProDesc"));
			 problemMap.put("ansNum", problemSet.getInt("LineNum"));
			 problemMap.put("ansType", problemSet.getString("AnsType"));
			 List<Map<String,Object>> optionList = new ArrayList<Map<String,Object>>();
			 if(proQustId != null && proQustId >0){
				 /* 查询每个问题的选项 begin */
				 String optionSql = " SELECT Qustid,Problemid,Ansid,Description,Mark,Score FROM mfq11 WHERE Qustid = ? AND Problemid = ? ORDER BY Ansid ";
	    		 SqlRowSet optionSet = jdbcService.query(optionSql,new Object[]{proQustId,problemId});
	    		 while(optionSet.next()){
	    			 Map<String,Object> optionMap = new HashMap<String,Object>();
	    			 optionMap.put("ansId",optionSet.getInt("Ansid"));
	    			 optionMap.put("description",optionSet.getString("Description"));
	    			 optionMap.put("mark",optionSet.getString("Mark"));
	    			 optionMap.put("score",optionSet.getDouble("Score"));
	    			 optionList.add(optionMap);
	    		 }
				 /* 查询每个问题的选项 end */
			 }
			 problemMap.put("anserList", optionList);
			 if(ansNumber != null && ansNumber > 0){
			     Integer answerId = 0;
			     String answerSql = " SELECT Ansid FROM uai21 WHERE AnsNumber = ? AND Problemid = ? ";
			     SqlRowSet answerSet = jdbcService.query(answerSql,new Object[]{ansNumber,problemId});
			     while(answerSet.next()){
			         answerId = answerSet.getInt("Ansid");
			     }
			     problemMap.put("answerId", answerId);
			 }
			 problemList.add(problemMap);
		 }
		 /* 查询问卷的每个题目 end */
		 map.put("ansNumber", ansNumber);
		 map.put("qustId", qustId);
		 map.put("qustName", questName);
		 map.put("publisherTime", publisherTime);
		 map.put("qustList", problemList);
    	 return map;
    }
    
    /** 
     * @Title: findLastestHealthExamId 
     * @Description: 获取最新入库的健康体检的id
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-03
     * @throws Exception    
     * @retrun Long
     */
    public Long findLastestHealthExamId(Date now) throws Exception{
        Long hExamID = 0l;
        String answerSql = " SELECT HExamID FROM ph_healthexam WHERE GetTime = ? ";
        SqlRowSet answerSet = jdbcService.query(answerSql,new Object[]{now});
        while(answerSet.next()){
            hExamID = answerSet.getLong("HExamID");
        }
        return hExamID;
    }
    
    /** 
     * @Title: checkHasSubmitAnswer 
     * @Description: 判断体检的问卷是否已经答过。 
     * @author liuxiaoqin
     * @createDate 2016-06-28
     * @param hExamID
     * @return
     * @throws Exception    
     * @retrun Map<String,Boolean>
     */
    public Map<String,Boolean> checkHasSubmitAnswer(Long hExamID,Integer doctorId) throws Exception{
    	Map<String,Boolean> map = new HashMap<String, Boolean>();
    	Boolean hasAged = true;
    	Boolean hasTcm = true;
    	String agedSql = " SELECT COUNT(AnsNumber) FROM ouai WHERE HExamID = ? AND Qustid = ? ";
    	int agedQustId = findQustId(doctorId,"aged",hExamID);
    	int countAged = jdbcService.queryForInt(agedSql, new Object[]{hExamID,agedQustId});
    	if(countAged <= 0){
    		hasAged = false;
    	}
    	String tcmSql = " SELECT COUNT(AnsNumber) FROM ouai WHERE HExamID = ? AND Qustid = ? ";
    	int tcmQustId = findQustId(doctorId,"tcm",hExamID);
    	int countTcm = jdbcService.queryForInt(tcmSql, new Object[]{hExamID,tcmQustId});
    	if(countTcm <= 0){
    		hasTcm = false;
    	}
    	map.put("hasSubmitAged", hasAged);
    	map.put("hasSubmitTcm", hasTcm);
    	return map;
    }
    
    /** 
     * @Title: findQustId 
     * @Description: 获取问卷id 
     * @author liuxiaoqin
     * @createDate 2016-06-28
     * @param hExamID
     * @return
     * @throws Exception    
     * @retrun Integer
     */
    public Integer findQustId(Integer doctorId,String qustName,Long hExamID) throws Exception{
    	String questName = "";
	   	if(qustName.equals("aged")){
	   		questName = Constants.AGED_QUESTIONNAIRE;
	   	}else{
	   		questName = Constants.TCM_QUESTIONNAIRE;
	   	}
    	Integer qustId = 0;
    	String qustSql = "";
		if(hExamID != null && hExamID >0){
			qustSql = " SELECT b.Qustid FROM odoc a LEFT JOIN omfq b ON a.OrgId = b.OrgId LEFT JOIN ouai c ON b.Qustid = c.Qustid "
		 		    + "  WHERE a.Docid = ? AND b.Qustname = ? AND b.QustTag = 'T' AND c.HExamID = "+hExamID+" ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
		}else{
			qustSql = " SELECT b.Qustid FROM odoc a LEFT JOIN omfq b ON a.OrgId = b.OrgId "
					+ " WHERE a.Docid = ? AND b.Qustname = ? AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
		}
		SqlRowSet qustSet = jdbcService.query(qustSql,new Object[]{doctorId,questName});
		boolean qustTag = qustSet.next();
		if(qustTag == true){
			qustId = qustSet.getInt("Qustid");
		}else{
			String newQustSql = "";
			if(hExamID != null && hExamID >0){
				newQustSql = " SELECT b.Qustid FROM omfq b LEFT JOIN ouai c ON b.Qustid = c.Qustid WHERE b.Qustname = ? "
						   + " AND c.HExamID = "+hExamID+" AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
			}else{
				newQustSql = " SELECT b.Qustid FROM omfq b WHERE b.Qustname = ? AND b.QustTag = 'T' ORDER BY b.QustVer DESC,b.CreateDate DESC LIMIT 1 ";
			}
			SqlRowSet newQustSet = jdbcService.query(newQustSql,new Object[]{questName});
			while(newQustSet.next()){
				qustId = newQustSet.getInt("Qustid");
			}
		}
    	return qustId;
    }
    
    /** 
     * @Title: conventDiseaseName 
     * @Description: 转化疾病中文名
     * @author liuxiaoqin
     * @createDate 2016-10-18
     * @param diseaseId
     * @return
     * @throws Exception    
     * @retrun Map<String,Boolean>
     */
    public String conventDiseaseName(String diseaseId) throws Exception{
        String diseaseName = "";
        if(!StringUtils.isEmpty(diseaseId)){
            if(diseaseId.equals("1")){
                diseaseName = Constants.DISEASE_HYPERTENSION_1;
            }else if(diseaseId.equals("2")){
                diseaseName = Constants.DISEASE_DIABMELL_2;
            }else if(diseaseId.equals("3")){
                diseaseName = Constants.DISEASE_CORONARY_HEART_3;
            }else if(diseaseId.equals("4")){
                diseaseName = Constants.DISEASE_CEREBRALAPOPLEXY_4;
            }else if(diseaseId.equals("5")){
                diseaseName = Constants.DISEASE_THERIOMA_5;
            }else if(diseaseId.equals("6")){
                diseaseName = Constants.DISEASE_LUNG_DISEASE_6;
            }else if(diseaseId.equals("7")){
                diseaseName = Constants.DISEASE_MAJORPSYCHOSIS_7;
            }else if(diseaseId.equals("8")){
                diseaseName = Constants.DISEASE_HEPATITIS_8;
            }else if(diseaseId.equals("9")){
                diseaseName = Constants.DISEASE_TUBERCULOSIS_9;
            }else if(diseaseId.equals("10")){
                diseaseName = Constants.DISEASE_OTHER_10;
            }
        }
        return diseaseName;
    }
    
    /** 
     * @Title: conventRelationName 
     * @Description: 转化关系中文名
     * @author liuxiaoqin
     * @createDate 2016-10-18
     * @param relation
     * @return
     * @throws Exception    
     * @retrun Map<String,Boolean>
     */
    public String conventRelationName(Integer relation) throws Exception{
        //关系：1-父亲，2-母亲，3-子女，4-兄弟姐妹
        String relationName = "";
        if(relation != null && relation == 1){
            relationName = "父亲";
        }else if(relation != null && relation == 2){
            relationName = "母亲";
        }else if(relation != null && relation == 3){
            relationName = "子女";
        }else if(relation != null && relation == 4){
            relationName = "兄弟姐妹";
        }
        return relationName;
    }
    
}
