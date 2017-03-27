
package com.zkhk.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hkbithealth.bean.ph.PHHealthExam;
import com.hkbithealth.bean.ph.PHHealthExamDetailMedicine;
import com.hkbithealth.bean.ph.PHHealthExamDetailNonimmune;
import com.hkbithealth.bean.ph.PHHealthExamDetailinPatient;
import com.hkbithealth.bean.ph.PHHealthExamdetailFamilyBed;
import com.hkbithealth.bean.ph.PHOmem;
import com.hkbithealth.bean.ph.PhHealthExamDetail;
import com.hkbithealth.enmu.Terminal;
import com.hkbithealth.service.ph.PublicHealthService;
import com.zkhk.constants.Constants;
import com.zkhk.dao.AnswerDao;
import com.zkhk.dao.DocHealthExamDao;
import com.zkhk.dao.MemDao;
import com.zkhk.dao.VisitDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.HealthExam;
import com.zkhk.entity.MemDisease;
import com.zkhk.entity.Omem;
import com.zkhk.entity.Omfq;
import com.zkhk.entity.Oopt;
import com.zkhk.entity.Ouai;
import com.zkhk.entity.PHHealthExamParam;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.Uai21;
import com.zkhk.util.HealthCheckUtil;
import com.zkhk.util.SystemUtils;
import com.zkhk.util.TimeUtil;

@Service("docHealthExamService")
public class DocHealthExamServiceImpl implements DocHealthExamService {

	private Logger logger = Logger.getLogger(DocHealthExamServiceImpl.class);
	
	private PublicHealthService publicHealthService = PublicHealthService.getInstance();
	
	@Resource(name = "docHealthExamDao")
    private DocHealthExamDao docHealthExamDao;
	
	@Resource(name = "visitDao")
    private VisitDao visitDao;
	
	@Resource(name = "answerDao")
	private AnswerDao answerDao;
    
	@Resource(name = "memDao")
    private MemDao memDao;
	
	/** 
     * @Title: findMemHealthExamReportList 
     * @Description: 医生查询会员的健康体检报告列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findMemHealthExamReportList(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        PHHealthExamParam param = JSON.parseObject(callValue.getParam(), PHHealthExamParam.class);
        int doctorId = callValue.getMemberId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数医生id【"+doctorId+"】应为正整数！");
            logger.info("参数医生id【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int memberId = param.getMemberId();
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int pageNow = param.getPageNow();
        int pageSize = param.getPageSize();
        PHOmem omem = new PHOmem();
        omem.setMemberid(memberId);
        omem.setDocid(doctorId);
        Terminal phone = Terminal.PHONE;
        List<PHHealthExam> healthExamReportList = (List<PHHealthExam>) publicHealthService.queryPhysicalPage(omem, null, null, pageNow, pageSize,phone).getList();
        if(healthExamReportList != null && healthExamReportList.size() > 0){
            List<Map<String,Object>> examMapList = new ArrayList<Map<String,Object>>();
            for(PHHealthExam health: healthExamReportList){
                Map<String,Object> examMap = new HashMap<String, Object>();
                String date = "";
                Date examDate = health.getExamDate();
                if(examDate != null){
                    date = TimeUtil.formatDate(examDate);
                }
                examMap.put("examDate", date);
                examMap.put("hExamID", health.getHExamID());
                Integer healthEvaluate = health.getHealthEvaluate();
                String evaluateName = "";
                if(healthEvaluate != null && healthEvaluate == 2){
                    evaluateName = "体检有异常";
                }else if(healthEvaluate != null && healthEvaluate == 1){
                    evaluateName = "体检无异常";
                }
                examMap.put("healthEvaluate",evaluateName);
                examMapList.add(examMap);
            }
            result.setState(0);
            result.setMessage("医生查询会员的健康体检报告列表成功");
            result.setContent(examMapList);
            logger.info("医生查询会员的健康体检报告列表成功");
        }else{
            result.setState(3);
            result.setMessage("没有该会员的健康体检报告列表");
            logger.info("没有该会员的健康体检报告列表");
        }
        return JSON.toJSONString(result);
    }
    /** 
     * @Title: findMemHealthExamReportList 
     * @Description: 医生查询会员的健康体检信息列表
     * @param request
     * @author hx
     * @createDate 2016-05-20
     * @throws Exception    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findHealthExamList(HttpServletRequest request)throws Exception{
		ReturnResult result = new ReturnResult();
	    String params = request.getParameter("params");
	    CallValue callValue = JSON.parseObject(params, CallValue.class);
	    JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
	    int docId = callValue.getMemberId();
	    int pageNow = Integer.parseInt(StringUtils.isBlank(jsonObject.getString("pageNow"))?SystemUtils.getValue(Constants.PAGE_NOW):jsonObject.getString("pageNow"));
	    int pageSize = Integer.parseInt(StringUtils.isBlank(jsonObject.getString("pageSize"))?SystemUtils.getValue(Constants.PAGE_SIZE):jsonObject.getString("pageSize"));
	    String searchParam = jsonObject.getString("searchParam");
	    
	    PHOmem omem = new PHOmem();
        omem.setDocid(docId);
        omem.setMemName(searchParam);
        Terminal phone = Terminal.PHONE;
        List<PHHealthExam> healthExamReportList = (List<PHHealthExam>) publicHealthService.queryPhysicalPage(omem, null, null, pageNow, pageSize,phone).getList();
        if(healthExamReportList != null && healthExamReportList.size() > 0){
            List<HealthExam> healthList = new ArrayList<HealthExam>();
            for(PHHealthExam health: healthExamReportList){
            	HealthExam exam = new HealthExam();
            	PHOmem omem2 = health.getOmem();
            	Date date = omem2.getBirthDate();
                if(date != null){
                    String birthDate = TimeUtil.formatDate(date);
                    exam.setAge(TimeUtil.getCurrentAgeByBirthdate(birthDate));
                }else{
                    exam.setAge(0);
                }
            	exam.setMemberID(omem2.getMemberid());
            	exam.setMemName(omem2.getMemName());
            	exam.setGender(omem2.getGender());
            	exam.setIdCard(omem2.getIdCard());
                Date examDate = health.getExamDate();
                if(examDate != null){
                	exam.setExamDate(TimeUtil.formatDate(examDate));
                }
                exam.setHExamID(health.getHExamID());
                healthList.add(exam);
            }
            result.setState(0);
            result.setMessage("查询会员的健康体检列表成功");
            result.setContent(healthList);
            logger.info("查询会员的健康体检列表成功");
        }else{
            result.setState(3);
            result.setMessage("没有该会员的健康体检列表");
            logger.info("没有该会员的健康体检列表");
        }
        return JSON.toJSONString(result);
    } 
    
    /** 
     * @Title: findMemHealthExamDetail 
     * @Description: 医生查询会员的健康体检报告明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHealthExamDetail(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        int doctorId = callValue.getMemberId();
        long hExamID = jsonObject.getLong("hExamID");
        if(hExamID <= 0){
            result.setState(1);
            result.setMessage("参数hExamID【"+hExamID+"】应为正整数！");
            logger.info("参数hExamID【"+hExamID+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        PHHealthExam memHealthExamDetail = publicHealthService.queryPhysicalById(hExamID);
        if(memHealthExamDetail.getHealthExamDetail() != null){
            Map<String,Object> examMap = new HashMap<String, Object>();
            examMap.put("hExamID", memHealthExamDetail.getHExamID());
            String uniqueId = memHealthExamDetail.getUnique_ID();
            if(!StringUtils.isEmpty(uniqueId)){
                examMap.put("uniqueId", uniqueId);
            }
            Integer refCompany = memHealthExamDetail.getRefCompany();
            if(refCompany != null){
                examMap.put("refCompany", refCompany);
            }
            String refDataPk = memHealthExamDetail.getRefDataPK();
            if(!StringUtils.isEmpty(refDataPk)){
                examMap.put("refDataPk", refDataPk);
            }
            Date examDate = memHealthExamDetail.getExamDate();
            if(examDate!= null){
                examMap.put("examDate", TimeUtil.formatDate(examDate));
            }
            String responsibleDrName = memHealthExamDetail.getResponsibleDrName();
            if(!StringUtils.isEmpty(responsibleDrName)){
                examMap.put("responsibleDrName", responsibleDrName);
            }
            Integer createDrID = memHealthExamDetail.getCreateDrID();
            if(createDrID != null){
                examMap.put("createDrID", createDrID);
            }
            String createDrName = memHealthExamDetail.getCreateDrName();
            if(!StringUtils.isEmpty(createDrName)){
                examMap.put("createDrName", createDrName);
            }
            Date createTime = memHealthExamDetail.getCreateTime();
            if(createTime != null){
                examMap.put("createTime", TimeUtil.formatDatetime2(createTime));
            }
            Date getTime = memHealthExamDetail.getGetTime();
            if(getTime != null){
                examMap.put("getTime", TimeUtil.formatDatetime2(getTime));
            }
            Integer updateDrID = memHealthExamDetail.getUpdateDrID();
            if(updateDrID != null ){
                examMap.put("updateDrID", updateDrID);
            }
            String updateDrName = memHealthExamDetail.getUpdateDrName();
            if(!StringUtils.isEmpty(updateDrName)){
                examMap.put("updateDrName", updateDrName);
            }
            Map<String, Object> basicInfoMap = HealthCheckUtil.getMemBasicInfo(memHealthExamDetail);
            int memberId = (int)basicInfoMap.get("memberId");
            String headAddress = memDao.findMemberHeadImg(memberId);
            if(!StringUtils.isEmpty(headAddress)){
                basicInfoMap.put("headAddress", headAddress);
            }
            examMap.put("basicInfo", basicInfoMap);
            examMap.put("symptoms", HealthCheckUtil.getSymptoms(memHealthExamDetail));
            String str  = JSON.toJSONString(examMap.get("basicInfo"));
            JSONObject strObject = JSON.parseObject(str);
            Integer age = strObject.getInteger("age");
            examMap.put("generalSituation", HealthCheckUtil.getGeneralSituation(memHealthExamDetail,age));
            examMap.put("lifeStyle", HealthCheckUtil.getLifeStyle(memHealthExamDetail));
            examMap.put("organFunction", HealthCheckUtil.getOrganFunction(memHealthExamDetail));
            examMap.put("examination", HealthCheckUtil.getExamination(memHealthExamDetail));
            examMap.put("accessoryExamination", HealthCheckUtil.getAccessoryExamination(memHealthExamDetail));
            examMap.put("tcmConstitutionIdentification", HealthCheckUtil.getTcm(memHealthExamDetail));
            examMap.put("majorHealthProblems", HealthCheckUtil.getProblems(memHealthExamDetail));
            examMap.put("hospitalCourse", HealthCheckUtil.getHospitalCourse(memHealthExamDetail));
            examMap.put("medication", HealthCheckUtil.getMedication(memHealthExamDetail));
            examMap.put("vaccination", HealthCheckUtil.getVaccination(memHealthExamDetail));
            examMap.put("healthEvaluation", HealthCheckUtil.getHealthEvaluation(memHealthExamDetail));
            examMap.put("healthGuidance", HealthCheckUtil.getHealthGuidance(memHealthExamDetail));
            examMap.put("riskFactorControl", HealthCheckUtil.getRiskFactorControl(memHealthExamDetail));
            String userType = jsonObject.getString("userType");
            if(!StringUtils.isEmpty(userType) && userType.equals("doctor")){
            	Map<String,Boolean> answerMap = docHealthExamDao.checkHasSubmitAnswer(hExamID,doctorId);
            	if(!answerMap.isEmpty()){
            		examMap.put("hasSubmitAged", answerMap.get("hasSubmitAged"));
            		examMap.put("hasSubmitTcm", answerMap.get("hasSubmitTcm"));
            	}
            }
            result.setState(0);
            result.setState(0);
            result.setMessage("医生查询会员的健康体检报告明细成功");
            result.setContent(examMap);
            logger.info("医生查询会员的健康体检报告明细成功");
        }else{
            result.setState(3);
            result.setMessage("没有该会员的健康体检报告明细");
            logger.info("没有该会员的健康体检报告明细");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: addOrModifyMemHealthExam 
     * @Description: 医生新增或者修改会员的健康体检
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemHealthExam(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        Integer doctorId = callValue.getMemberId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数医生memberId【"+doctorId+"】应为正整数！");
            logger.info("参数医生memberId【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        String doctorName = visitDao.findDoctorBasicInfo(doctorId).getDoctorName();
        Long hExamID = jsonObject.getLong("hExamID");
        String basicInfo = jsonObject.getString("basicInfo");
        Integer memberID = JSON.parseObject(basicInfo).getInteger("memberId");
        ReturnResult resultFinal = new ReturnResult();
        if(hExamID == null || hExamID <= 0){
            resultFinal = validAddExamParam(doctorId,doctorName,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHHealthExam exam = (PHHealthExam)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyPhysical(exam);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生新增会员的体检报告成功");
                logger.info("医生新增会员的体检报告成功");
            }else{
                result.setState(1);
                result.setMessage("医生新增会员的体检报告失败");
                logger.info("医生新增会员的体检报告");
            }
            Date nowTime = exam.getGetTime();
            hExamID = docHealthExamDao.findLastestHealthExamId(nowTime);
        }else{
            resultFinal = validModifyExamParam(doctorId,doctorName,hExamID,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHHealthExam exam = (PHHealthExam)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyPhysical(exam);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生修改会员的体检报告成功");
                logger.info("医生修改会员的体检报告成功");
            }else{
                result.setState(1);
                result.setMessage("医生修改会员的体检报告失败");
                logger.info("医生修改会员的体检报告失败");
            }
        }
        /* 开始保存老年人生活自理答卷   begin */
        String agedlifeEvaAnsListStr = jsonObject.getString("agedlifeEvaAnsList");
        if(!StringUtils.isEmpty(agedlifeEvaAnsListStr)){
        	submitAnswer(doctorId,doctorName,hExamID,memberID,agedlifeEvaAnsListStr,"aged",request);
        }
        /* 开始保存老年人生活自理答卷   end */
        /* 开始中医体质答卷   begin */
        String tcmAnsListStr = jsonObject.getString("tcmAnsList");
        if(!StringUtils.isEmpty(tcmAnsListStr)){
        	submitAnswer(doctorId,doctorName,hExamID,memberID,tcmAnsListStr,"tcm",request);
        }
        /* 开始中医体质答卷    end */
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: addMemDiseases 
     * @Description: 医生新增会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addMemDiseases(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        MemDisease param = JSON.parseObject(callValue.getParam(), MemDisease.class);
        int memberId = param.getMemberId();
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int diseaseId = param.getDiseaseId();
        if(diseaseId <= 0){
            result.setState(1);
            result.setMessage("参数diseaseId【"+diseaseId+"】应为正整数！");
            logger.info("参数diseaseId【"+diseaseId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int lineNum = docHealthExamDao.findLineNum(memberId,diseaseId);
        param.setLineNum(lineNum);
        int hasTheDisease = docHealthExamDao.memHasTheDisease(param);
        if(hasTheDisease == 1){
            result.setState(4);
            result.setMessage("该会员的这条疾病史已经存在,请重新选择！");
            logger.info("该会员的这条疾病史已经存在,请重新选择！");
            return JSON.toJSONString(result);
        }else{
            int realLineNum = docHealthExamDao.findMaxLineNum(param.getMemberId()) + 1;
            param.setLineNum(realLineNum);
            int count = docHealthExamDao.addMemDiseases(param);
            if(count > 0){
                result.setState(0);
                result.setContent(param);
                result.setMessage("医生新增会员的疾病史成功");
                logger.info("医生新增会员的疾病史成功");
            }else{
                result.setState(1);
                result.setMessage("医生新增会员的疾病史失败");
                logger.info("医生新增会员的疾病史失败");
            }
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: deleteMemDiseases 
     * @Description: 医生删除会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String deleteMemDiseases(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        MemDisease param = JSON.parseObject(callValue.getParam(), MemDisease.class);
        List<MemDisease> memDiseaseList = param.getMemDiseaseList();
        if(memDiseaseList.size() > 0){
            for(MemDisease disease:memDiseaseList){
                int lineNum = disease.getLineNum();
                if(lineNum <= 0){
                    result.setState(1);
                    result.setMessage("参数lineNum【"+lineNum+"】应为正整数！");
                    logger.info("参数lineNum【"+lineNum+"】应为正整数！");
                    return JSON.toJSONString(result);
                }
                int memberId = disease.getMemberId();
                if(memberId <= 0){
                    result.setState(1);
                    result.setMessage("参数memberId【"+memberId+"】应为正整数！");
                    logger.info("参数memberId【"+memberId+"】应为正整数！");
                    return JSON.toJSONString(result);
                }
                int diseaseId = disease.getDiseaseId();
                if(diseaseId <= 0){
                    result.setState(1);
                    result.setMessage("参数diseaseId【"+diseaseId+"】应为正整数！");
                    logger.info("参数diseaseId【"+diseaseId+"】应为正整数！");
                    return JSON.toJSONString(result);
                }
                if(diseaseId == 1 || diseaseId == 2){
                    int hasVisitRecord = docHealthExamDao.findVisitRecord(memberId,diseaseId);
                    if(hasVisitRecord > 0){
                        result.setState(4);
                        result.setContent(diseaseId);
                        result.setMessage("该会员的"+disease.getDiseaseName()+"有随访记录不能删除！");
                        logger.info("该会员该疾病【"+diseaseId+"】有随访记录不能删除！");
                        return JSON.toJSONString(result);
                    }
                }
                int count = docHealthExamDao.deleteMemDiseases(disease);
                if(count > 0){
                    result.setState(0);
                    result.setMessage("医生删除会员的疾病史成功");
                    logger.info("医生删除会员的疾病史成功");
                }else{
                    result.setState(1);
                    result.setMessage("医生删除会员的疾病史失败");
                    logger.info("医生删除会员的疾病史失败");
                }
            }
        }else{
            result.setState(1);
            result.setMessage("参数memDiseaseList为空");
            logger.info("参数memDiseaseList为空");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemHealthFile 
     * @Description: 医生查询会员的健康档案
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHealthFile(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        int memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(3);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("basicInfo", getMemBasicInfo(memberId));
        map.put("emergencyContact", getMemEmergencyContact(memberId));
        map.put("disease", getMemDiseases(memberId));
        map.put("physicalExamination", getMemPhysicalExamination(memberId));
        map.put("lifeStyle", getMemLifeStyle(memberId));
        map.put("familyHistory", getMemFamilyHistory(memberId));
        map.put("group", getMemGroup(memberId));
        if(map.size() > 0){
            result.setState(0);
            result.setContent(map);
            result.setMessage("医生查询会员的健康档案成功");
            logger.info("医生查询会员的健康档案成功");
        }else{
            result.setState(3);
            result.setMessage("该会员没有健康档案信息");
            logger.info("该会员没有健康档案信息");
        }
        return JSON.toJSONString(result);
    }
    
     /** 
     * @Title: getMemDiseases 
     * @Description: 医生查询会员的疾病史 
     * @param memberId
     * @return
     * @throws Exception    
     * @retrun List<MemDisease>
     */
    public List<MemDisease> getMemDiseases(Integer memberId)throws Exception{
        List<MemDisease> list = docHealthExamDao.findMemDiseases(memberId);
        if(list.size() <= 0){
            logger.info("该会员没有疾病史");
        }else{
            logger.info("医生查询会员的疾病史成功！");
        }
        return list;
    }
    
    /** 
     * @Title: getMemFamilyHistory 
     * @Description: 医生查询会员的家族史 
     * @param memberId
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @return
     * @throws Exception    
     * @retrun List<MemDisease>
     */
    public List<Map<String,Object>> getMemFamilyHistory(Integer memberId)throws Exception{
        List<Map<String,Object>> list = docHealthExamDao.getMemFamilyHistory(memberId);
        if(list.size() <= 0){
            logger.info("该会员没有家族史");
        }else{
            logger.info("医生查询会员的家族史成功！");
        }
        return list;
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
        List<Map<String,Object>> map = docHealthExamDao.getMemGroup(memberId);
        if(map.size() <= 0){
            logger.info("该会员没有分组");
        }else{
            logger.info("医生查询会员的分组成功！");
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
    	List<Map<String,Object>> list = docHealthExamDao.getMemEmergencyContact(memberId);
        if(list.size() <= 0){
            logger.info("该会员没有紧急联系人");
        }else{
            logger.info("医生查询会员的紧急联系人成功！");
        }
        return list;
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
        Map<String,Object> map = docHealthExamDao.getMemLifeStyle(memberId);
        if(map.size() <= 0){
            logger.info("该会员没有生活习惯");
        }else{
            logger.info("医生查询会员的生活习惯成功！");
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
        Map<String,Object> map = docHealthExamDao.getMemPhysicalExamination(memberId);
        if(map.size() <= 0){
            logger.info("该会员没有体格检查!");
        }else{
            logger.info("医生查询会员的体格检查成功！");
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
        Map<String,Object> map = docHealthExamDao.getMemBasicInfo(memberId);
        if(map.size() <= 0){
            logger.info("该会员没有基本信息!");
        }else{
            logger.info("医生查询会员的基本信息成功！");
        }
        return map;
    }
    
    /** 
     * @Title: validAddExamParam 
     * @Description: 验证新增体检报告参数 
     * @param doctorId
     * @param doctorName
     * @author liuxiaoqin
     * @createDate 2016-05-24
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validAddExamParam(Integer doctorId,String doctorName,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();
        PHHealthExam exam = new PHHealthExam();
        /* 基本资料   begin  */
        String basicInfo = jsonObject.getString("basicInfo");
        JSONObject memInfo = JSON.parseObject(basicInfo);
        Integer memberID = memInfo.getInteger("memberId");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberID+"】应为正整数！");
            logger.info("参数memberId【"+memberID+"】应为正整数！");
            return result;
        }
        String name = memInfo.getString("memName");
        exam.setName(name);
        exam.setMemberID(memberID);
        String idCard = memInfo.getString("idcard");
        exam.setiDCard(idCard);
        /* 基本资料   end  */
        
        String uniqueID = jsonObject.getString("uniqueID");
        exam.setUnique_ID(uniqueID);
        Integer refCompany = jsonObject.getInteger("refCompany");
        if(refCompany == null){
            refCompany = 0;
        }
        exam.setRefCompany(refCompany);
        String refDataPK = jsonObject.getString("refDataPK");
        exam.setRefDataPK(refDataPK);
        String date = jsonObject.getString("examDate");
        if(!StringUtils.isEmpty(date)){
            Date examDate = TimeUtil.parseDate(date);
            exam.setExamDate(examDate);
        }else{
        	String nowStr = TimeUtil.currentDate();
        	Date now = TimeUtil.parseDate(nowStr);
        	exam.setExamDate(now);
        }
        String responsibleDrName = jsonObject.getString("responsibleDrName");
        if(StringUtils.isEmpty(responsibleDrName)){
        	exam.setResponsibleDrName(doctorName);
        }else{
        	exam.setResponsibleDrName(responsibleDrName);
        }
        exam.setCreateDrID(doctorId);
        exam.setIsDeleted(0);
        exam.setCreateDrName(doctorName);
        exam.setGetTime(new Date());
        exam.setCreateTime(new Date());
        /* 获取healthExamDetail  begin */
        PhHealthExamDetail examDetail = getExamDetail(jsonObject);
        examDetail = formatDetail(examDetail);
        exam.setHealthExamDetail(examDetail);
        /* 获取healthExamDetail  end */
        
        /* 获取healthExamDetailFamilyBed  begin */
        List<PHHealthExamdetailFamilyBed> familyBedList = getFamilyBed(jsonObject);
        exam.setHealthExamDetailFamilyBed(familyBedList);
        /* 获取healthExamDetailFamilyBed  end */
        
        /* 获取healthExamDetailInPatient  begin */
        List<PHHealthExamDetailinPatient> patientStrList = getPatient(jsonObject);
        exam.setHealthExamDetailInPatient(patientStrList);
        /* 获取healthExamDetailInPatient  end */
        
        /* 获取healthExamDetailMedicine  begin */
        List<PHHealthExamDetailMedicine> medicineList = getMedicine(jsonObject);
        if(medicineList != null && medicineList.size() >0){
            exam.setHealthExamDetailMedicine(medicineList);
        }
        /* 获取healthExamDetailMedicine  end */
        
        /* 获取healthExamDetailNonimmune  begin */
        List<PHHealthExamDetailNonimmune> nonimmuneList = getNonimmune(jsonObject);
        if(nonimmuneList != null && nonimmuneList.size() >0){
            exam.setHealthExamDetailNonimmune(nonimmuneList);
        }
        /* 获取healthExamDetailNonimmune  end */
        result.setState(0);
        result.setContent(exam);
        return result;
    }
    
     /** 
     * @Title: validModifyExamParam 
     * @Description: 验证修改体检报告参数
     * @author liuxiaoqin
     * @createDate 2016-05-24 
     * @param doctorId
     * @param doctorName
     * @param hExamID
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validModifyExamParam(Integer doctorId,String doctorName,Long hExamID,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();

        PHHealthExam exam = new PHHealthExam();
        /* 基本资料   begin  */
        String basicInfo = jsonObject.getString("basicInfo");
        JSONObject memInfo = JSON.parseObject(basicInfo);
        Integer memberID = memInfo.getInteger("memberId");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberID+"】应为正整数！");
            logger.info("参数memberId【"+memberID+"】应为正整数！");
            return result;
        }
        String name = memInfo.getString("memName");
        exam.setName(name);
        exam.setMemberID(memberID);
        String idCard = memInfo.getString("idcard");
        exam.setiDCard(idCard);
        /* 基本资料   end  */
        if(hExamID <= 0){
            result.setState(1);
            result.setMessage("参数hExamID【"+hExamID+"】应为正整数！");
            logger.info("参数hExamID【"+hExamID+"】应为正整数！");
            return result;
        }
        exam.setHExamID(hExamID);
        exam.setMemberID(memberID);
        String uniqueID = jsonObject.getString("uniqueID");
        exam.setUnique_ID(uniqueID);
        Integer refCompany = jsonObject.getInteger("refCompany");
        if(refCompany == null){
            refCompany = 0;
        }
        exam.setRefCompany(refCompany);
        String refDataPK = jsonObject.getString("refDataPK");
        exam.setRefDataPK(refDataPK);
        String date = jsonObject.getString("examDate");
        if(!StringUtils.isEmpty(date)){
            Date examDate = TimeUtil.parseDate(date);
            exam.setExamDate(examDate);
        }else{
        	String nowStr = TimeUtil.currentDate();
        	Date now = TimeUtil.parseDate(nowStr);
        	exam.setExamDate(now);
        }
        String responsibleDrName = jsonObject.getString("responsibleDrName");
        if(StringUtils.isEmpty(responsibleDrName)){
        	exam.setResponsibleDrName(doctorName);
        }else{
        	exam.setResponsibleDrName(responsibleDrName);
        }
        Integer createDrID = jsonObject.getInteger("createDrID");
        if(createDrID == null || createDrID <=0){
        	result.setState(1);
            result.setMessage("参数createDrID【"+createDrID+"】应为正整数！");
            logger.info("参数createDrID【"+createDrID+"】应为正整数！");
            return result;
        }
        exam.setCreateDrID(createDrID);
        String createDrName = jsonObject.getString("createDrName");
        if(StringUtils.isEmpty(createDrName)){
        	result.setState(1);
            result.setMessage("参数createDrName【"+createDrName+"】不能为空！");
            logger.info("参数createDrName【"+createDrName+"】不能为空！");
            return result;
        }
        exam.setCreateDrName(createDrName);
        String createTime = jsonObject.getString("createTime");
        if(StringUtils.isEmpty(createTime)){
        	result.setState(1);
            result.setMessage("参数createTime【"+createTime+"】不能为空！");
            logger.info("参数createTime【"+createTime+"】不能为空！");
            return result;
        }
        Date newCreateTime = TimeUtil.parseDatetime(createTime);
        exam.setCreateTime(newCreateTime);
        String getTime = jsonObject.getString("getTime");
        if(StringUtils.isEmpty(getTime)){
        	result.setState(1);
            result.setMessage("参数getTime【"+getTime+"】不能为空！");
            logger.info("参数getTime【"+getTime+"】不能为空！");
            return result;
        }
        Date newGetTime = TimeUtil.parseDatetime(getTime);
        exam.setGetTime(newGetTime);
        exam.setUpdateDrID(doctorId);
        exam.setUpdateDrName(doctorName);
        exam.setUpdateTime(new Date());
        /* 获取healthExamDetail  begin */
        PhHealthExamDetail examDetail = getExamDetail(jsonObject);
        examDetail = formatDetail(examDetail);
        exam.setHealthExamDetail(examDetail);
        /* 获取healthExamDetail  end */
        
        /* 获取healthExamDetailFamilyBed  begin */
        List<PHHealthExamdetailFamilyBed> familyBedList = getFamilyBed(jsonObject);
        exam.setHealthExamDetailFamilyBed(familyBedList);
        /* 获取healthExamDetailFamilyBed  end */
        
        /* 获取healthExamDetailInPatient  begin */
        List<PHHealthExamDetailinPatient> patientStrList = getPatient(jsonObject);
        exam.setHealthExamDetailInPatient(patientStrList);
        /* 获取healthExamDetailInPatient  end */
        
        /* 获取healthExamDetailMedicine  begin */
        List<PHHealthExamDetailMedicine> medicineList = getMedicine(jsonObject);
        if(medicineList != null && medicineList.size() >0){
            exam.setHealthExamDetailMedicine(medicineList);
        }
        /* 获取healthExamDetailMedicine  end */
        
        /* 获取healthExamDetailNonimmune  begin */
        List<PHHealthExamDetailNonimmune> nonimmuneList = getNonimmune(jsonObject);
        if(nonimmuneList != null && nonimmuneList.size() >0){
            exam.setHealthExamDetailNonimmune(nonimmuneList);
        }
        /* 获取healthExamDetailNonimmune  end */
        result.setState(0);
        result.setContent(exam);
        return result;
    }
    
    /** 
     * @Title: formatDetail 
     * @Description: 转化某些值
     * @author liuxiaoqin
     * @createDate 2016-05-25 
     * @param examDetail
     * @throws Exception    
     * @retrun PhHealthExamDetail
     */
    public PhHealthExamDetail formatDetail(PhHealthExamDetail examDetail) throws Exception{
    	String str = examDetail.getSymptom();
        String symptom = StringUtils.replace(str, ",", "@#");
        examDetail.setSymptom(symptom);
        String eatingHabitsStr = examDetail.getEatingHabits();
        String eatingHabits = StringUtils.replace(eatingHabitsStr, ",", "@#");
        examDetail.setEatingHabits(eatingHabits);
        String drinkTypeStr = examDetail.getDrinkType();
        String drinkType = StringUtils.replace(drinkTypeStr, ",", "@#");
        examDetail.setDrinkType(drinkType);
        String breastStr = examDetail.getBreast();
        String breast = StringUtils.replace(breastStr, ",", "@#");
        examDetail.setBreast(breast);
        String tCM_PHZ_GuideStr = examDetail.getTCM_PHZ_Guide();
        String tCM_PHZ_Guide = StringUtils.replace(tCM_PHZ_GuideStr, ",", "@#");
        examDetail.setTCM_PHZ_Guide(tCM_PHZ_Guide);
        String tCM_YXZ_GuideStr = examDetail.getTCM_YXZ_Guide();
        String tCM_YXZ_Guide = StringUtils.replace(tCM_YXZ_GuideStr, ",", "@#");
        examDetail.setTCM_YXZ_Guide(tCM_YXZ_Guide);
        String tCM_QXZ_GuideStr = examDetail.getTCM_QXZ_Guide();
        String tCM_QXZ_Guide = StringUtils.replace(tCM_QXZ_GuideStr, ",", "@#");
        examDetail.setTCM_QXZ_Guide(tCM_QXZ_Guide);
        String tCM_YIXZ_GuideStr = examDetail.getTCM_YIXZ_Guide();
        String tCM_YIXZ_Guide = StringUtils.replace(tCM_YIXZ_GuideStr, ",", "@#");
        examDetail.setTCM_YIXZ_Guide(tCM_YIXZ_Guide);
        String tCM_TSZ_GuideStr = examDetail.getTCM_TSZ_Guide();
        String tCM_TSZ_Guide = StringUtils.replace(tCM_TSZ_GuideStr, ",", "@#");
        examDetail.setTCM_TSZ_Guide(tCM_TSZ_Guide);
        String tCM_SRZ_GuideStr = examDetail.getTCM_SRZ_Guide();
        String tCM_SRZ_Guide = StringUtils.replace(tCM_SRZ_GuideStr, ",", "@#");
        examDetail.setTCM_SRZ_Guide(tCM_SRZ_Guide);
        String tCM_XTZ_GuideStr = examDetail.getTCM_XTZ_Guide();
        String tCM_XTZ_Guide = StringUtils.replace(tCM_XTZ_GuideStr, ",", "@#");
        examDetail.setTCM_XTZ_Guide(tCM_XTZ_Guide);
        String tCM_QYZ_GuideStr = examDetail.getTCM_QYZ_Guide();
        String tCM_QYZ_Guide = StringUtils.replace(tCM_QYZ_GuideStr, ",", "@#");
        examDetail.setTCM_QYZ_Guide(tCM_QYZ_Guide);
        String tCM_TBZ_GuideStr = examDetail.getTCM_TBZ_Guide();
        String tCM_TBZ_Guide = StringUtils.replace(tCM_TBZ_GuideStr, ",", "@#");
        examDetail.setTCM_TBZ_Guide(tCM_TBZ_Guide);
        String cerebralVesselStr = examDetail.getCerebralVessel();
        String cerebralVessel = StringUtils.replace(cerebralVesselStr, ",", "@#");
        examDetail.setCerebralVessel(cerebralVessel);
        String kidneyStr = examDetail.getKidney();
        String kidney = StringUtils.replace(kidneyStr, ",", "@#");
        examDetail.setKidney(kidney);
        String heartStr = examDetail.getHeart();
        String heart = StringUtils.replace(heartStr, ",", "@#");
        examDetail.setHeart(heart);
        String bloodPipeStr = examDetail.getBloodPipe();
        String bloodPipe = StringUtils.replace(bloodPipeStr, ",", "@#");
        examDetail.setBloodPipe(bloodPipe);
        String eyePartStr = examDetail.getEyePart();
        String eyePart = StringUtils.replace(eyePartStr, ",", "@#");
        examDetail.setEyePart(eyePart);
        String healthEvaluate_DescStr = examDetail.getHealthEvaluate_Desc();
        String healthEvaluate_Desc = StringUtils.replace(healthEvaluate_DescStr, ",", "@#");
        examDetail.setHealthEvaluate_Desc(healthEvaluate_Desc);
        String healthGuideStr = examDetail.getHealthGuide();
        String healthGuide = StringUtils.replace(healthGuideStr, ",", "@#");
        examDetail.setHealthGuide(healthGuide);
        String riskFactorStr = examDetail.getRiskFactor();
        String riskFactor = StringUtils.replace(riskFactorStr, ",", "@#");
        examDetail.setRiskFactor(riskFactor);
    	return examDetail;
    }
    
    
    /** 
     * @Title: getOtherEightTCMResult 
     * @Description: 获取除平和质以外8种体质的结果
     * @author liuxiaoqin
     * @createDate 2016-05-25 
     * @param everyTCMScore
     * @throws Exception    
     * @retrun Integer
     */
    public Integer getOtherEightTCMResult(Double everyTCMScore)throws Exception{
    	Integer result = null;
    	if(everyTCMScore >= 11){
    		result = 1;
    	}else if(everyTCMScore >= 9 && everyTCMScore <= 10){
    		result = 2;
    	}else if(everyTCMScore <=8 ){
    		result = 3;
    	}
    	return result;
    }
    
    /** 
     * @Title: submitAnswer 
     * @Description: 提交答卷
     * @author liuxiaoqin
     * @createDate 2016-05-26 
     * @param everyTCMScore
     * @throws Exception    
     * @retrun Integer
     */
    public void submitAnswer(Integer doctorId,String doctorName,Long hExamID,Integer memberId,String ansList,String type,HttpServletRequest request)throws Exception{
    	List<Uai21> answerOptionList = JSON.parseArray(ansList, Uai21.class);
    	String qustTag = "C";
    	Uai21 uai21 = answerOptionList.get(0);
    	Integer qustId = uai21.getQustId();
    	Integer ansNumber = uai21.getAnsNumber();
    	Omfq omfq = answerDao.findOmfqByParam2(qustId);
    	Ouai ouaiNew = new Ouai();
		ouaiNew.setQustId(qustId);
		ouaiNew.setMemberid(memberId);
		ouaiNew.setDoctorId(doctorId);
		ouaiNew.setDocName(doctorName);
		ouaiNew.sethExamID(hExamID);
		ouaiNew.setFunId(omfq.getFunId());
		ouaiNew.setOptId(omfq.getOptId());
		ansNumber = answerDao.addNewAnswer(ouaiNew);
		for(Uai21 answer :answerOptionList){
			answer.setAnsNumber(ansNumber);
		}
    	answerDao.saveUai21ByParam(answerOptionList,qustTag);
     	//获取总分数
        double totalScore = answerDao.getScore(answerOptionList);
        //获取系统得出结论
        String result = "";
        if(type.equals("aged")){
        	result = getExamAnswerConclusion("aged",request);
        }else{
        	result = getExamAnswerConclusion("tcm",request);
        }
        //保存总分和分析结果
        answerDao.saveOuai4(ansNumber,totalScore,result);
        Oopt oopt = answerDao.findOoptBySingleQustId(qustId);
        //保存到oasr表中
        answerDao.submitSingleAnswer(ansNumber, oopt.getId(), oopt.getOptName(), memberId,"exam");
        if(type.equals("aged")){
        	logger.info("保存老年人生活自理答卷信息成功！");
        }else{
        	logger.info("保存中医体质答卷信息成功！");
        }
    }
    
    /** 
     * @Title: findAgedLifeEvaluate 
     * @Description: 通过老年人生活自理能力问卷获取老年人生活自理能力值
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun String
     */
    public String findAgedLifeEvaluate(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
    	String params = request.getParameter("params");
	    CallValue callValue = JSON.parseObject(params, CallValue.class);
	    JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
	    String agedlifeEvaAnsListStr = jsonObject.getString("agedlifeEvaAnsList");
	    if(StringUtils.isEmpty(agedlifeEvaAnsListStr)){
	    	result.setState(1);
	        result.setMessage("老年人生活自理能力答卷【"+agedlifeEvaAnsListStr +"】不能为空");
	        logger.info("老年人生活自理能力答卷【"+agedlifeEvaAnsListStr +"】不能为空");
	        return JSON.toJSONString(result);
	    }
    	Map<String,Object> map = new HashMap<String, Object>();
    	Integer agedLifeEvaluate = null;
    	List<Uai21> answerOptionList = JSON.parseArray(agedlifeEvaAnsListStr, Uai21.class);
    	double totalScore = 0;
    	for(Uai21 answerOption : answerOptionList){
    		int problemId = answerOption.getProblemId();
    		int answerId = answerOption.getAnsId();
    		double score = 0;
    		if(problemId == 1){
    			if(answerId == 1){
    				score = 0;
    			}else if(answerId == 2){
    				score = 3;
    			}else if(answerId == 3){
    				score = 5;
    			}
    		}else if(problemId == 2){
    			if(answerId == 1){
    				score = 0;
    			}else if(answerId == 2){
    				score = 1;
    			}else if(answerId == 3){
    				score = 3;
    			}else if(answerId == 4){
    				score = 7;
    			}
    		}else if(problemId == 3){
    			if(answerId == 1){
    				score = 0;
    			}else if(answerId == 2){
    				score = 3;
    			}else if(answerId == 3){
    				score = 5;
    			}
    		}else if(problemId == 4){
    			if(answerId == 1){
    				score = 0;
    			}else if(answerId == 2){
    				score = 1;
    			}else if(answerId == 3){
    				score = 5;
    			}else if(answerId == 4){
    				score = 10;
    			}
    		}else if(problemId == 5){
    			if(answerId == 1){
    				score = 0;
    			}else if(answerId == 2){
    				score = 1;
    			}else if(answerId == 3){
    				score = 5;
    			}else if(answerId == 4){
    				score = 10;
    			}
    		}
    		answerOption.setScore(score);
    		totalScore += score;
    	}
    	if(totalScore <= 3){
    		agedLifeEvaluate = 1;
    	}else if(totalScore >= 4 && totalScore <= 8){
    		agedLifeEvaluate = 2;
    	}else if(totalScore >= 9 && totalScore <= 18){
    		agedLifeEvaluate = 3;
    	}else{
    		agedLifeEvaluate = 4;
    	}
    	map.put("agedLifeEvaluate", agedLifeEvaluate);
    	result.setState(0);
        result.setMessage("通过老年人生活自理能力问卷获取老年人生活自理能力值成功");
        result.setContent(map);
        logger.info("通过老年人生活自理能力问卷获取老年人生活自理能力值成功");
    	return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findTcmValue 
     * @Description: 通过中医体质问卷获取中医体质结果
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun String
     */
    public String findTcmValue(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
    	String params = request.getParameter("params");
	    CallValue callValue = JSON.parseObject(params, CallValue.class);
	    JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
	    String tcmAnsListStr = jsonObject.getString("tcmAnsList");
	    if(StringUtils.isEmpty(tcmAnsListStr)){
	    	result.setState(1);
	        result.setMessage("中医体质答卷【"+tcmAnsListStr +"】不能为空");
	        logger.info("中医体质答卷【"+tcmAnsListStr +"】不能为空");
	        return JSON.toJSONString(result);
	    }
	    List<Uai21> answerOptionList = JSON.parseArray(tcmAnsListStr, Uai21.class);
	    Map<String,Object> map = new HashMap<String, Object>();
    	double tcm__QXZ_Score = 0;
    	double tcm__YXZ_Score = 0;
    	double tcm__YIXZ_Score = 0;
    	double tcm__TSZ_Score = 0;
    	double tcm__SRZ_Score = 0;
    	double tcm__XTZ_Score = 0;
    	double tcm__QYZ_Score = 0;
    	double tcm__TBZ_Score = 0;
    	double tcm__PHZ_Score = 0;
    	for(Uai21 answerOption : answerOptionList){
    		int problemId = answerOption.getProblemId();
    		int answerId = answerOption.getAnsId();
    		double score = 0;
    		double phzScore = 0;
    		if(answerId == 1){
    			score = 1;
    			phzScore = 5;
    		}else if(answerId == 2){
    			score = 2;
    			phzScore = 4;
    		}
    		else if(answerId == 3){
    			score = 3;
    			phzScore = 3;
    		}
    		else if(answerId == 4){
    			score = 4;
    			phzScore = 2;
    		}
    		else if(answerId == 5){
    			score = 5;
    			phzScore = 1;
    		}
    		answerOption.setScore(score);
    		//气虚质总分
    		if(problemId == 2 || problemId == 3 || problemId == 4 || problemId == 14){
    			tcm__QXZ_Score += score;
    		}
    		//阳虚质总分
    		else if(problemId == 11 || problemId == 12 || problemId == 13 || problemId == 29){
    			tcm__YXZ_Score += score;
    		}
    		//阴虚质总分
    		else if(problemId == 10 || problemId == 21 || problemId == 26 || problemId == 31){
    			tcm__YIXZ_Score += score;
    		}
    		//痰湿质总分
    		else if(problemId == 9 || problemId == 16 || problemId == 28 || problemId == 32){
    			tcm__TSZ_Score += score;
    		}
    		//湿热质总分
    		else if(problemId == 23 || problemId == 25 || problemId == 27 || problemId == 30){
    			tcm__SRZ_Score += score;
    		}
    		//血瘀质总分
    		else if(problemId == 19 || problemId == 22 || problemId == 24 || problemId == 33){
    			tcm__XTZ_Score += score;
    		}
    		//气郁质总分
    		else if(problemId == 5 || problemId == 6 || problemId == 7 || problemId == 8){
    			tcm__QYZ_Score += score;
    		}
    		//特禀质总分
    		else if(problemId == 15 || problemId == 17 || problemId == 18 || problemId == 20){
    			tcm__TBZ_Score += score;
    		}
    		//平和质总分
    		if(problemId == 1){
    			tcm__PHZ_Score += score; 
    		}else if(problemId == 2 || problemId == 4 || problemId == 5 || problemId == 13){
    			tcm__PHZ_Score += phzScore;
    		}
    	}
    	Integer tcmPHZ = null;
    	if(tcm__PHZ_Score >= 17 && tcm__QXZ_Score < 8 && tcm__YXZ_Score < 8 && tcm__YIXZ_Score < 8 && tcm__TSZ_Score < 8 
    			&& tcm__SRZ_Score < 8 && tcm__XTZ_Score < 8 && tcm__QYZ_Score < 8 && tcm__TBZ_Score < 8 ){
    		tcmPHZ = 1;
    	}else if(tcm__PHZ_Score >= 17 && (tcm__QXZ_Score >= 8 && tcm__QXZ_Score < 10) && (tcm__YXZ_Score >= 8 && tcm__YXZ_Score < 10) && (tcm__YIXZ_Score >= 8 && tcm__YIXZ_Score < 10)
    			&& (tcm__TSZ_Score >= 8 && tcm__TSZ_Score < 10) && (tcm__SRZ_Score >= 8 && tcm__SRZ_Score < 10) && (tcm__XTZ_Score >= 8 && tcm__XTZ_Score < 10) 
    			&& (tcm__QYZ_Score >= 8 && tcm__QYZ_Score < 10) && (tcm__TBZ_Score >= 8 && tcm__TBZ_Score < 10) ){
    		tcmPHZ = 2;
    	}else{
    		tcmPHZ = 3;
    	}
    	map.put("tCM_PHZ", tcmPHZ);
    	map.put("tCM_QXZ", getOtherEightTCMResult(tcm__QXZ_Score));
    	map.put("tCM_YXZ", getOtherEightTCMResult(tcm__YIXZ_Score));
    	map.put("tCM_YIXZ", getOtherEightTCMResult(tcm__YIXZ_Score));
    	map.put("tCM_TSZ", getOtherEightTCMResult(tcm__TSZ_Score));
    	map.put("tCM_SRZ", getOtherEightTCMResult(tcm__SRZ_Score));
    	map.put("tCM_XTZ", getOtherEightTCMResult(tcm__XTZ_Score));
    	map.put("tCM_QYZ", getOtherEightTCMResult(tcm__QYZ_Score));
    	map.put("tCM_TBZ", getOtherEightTCMResult(tcm__TBZ_Score));
    	result.setState(0);
        result.setMessage("通过中医体质问卷获取中医体质结果成功");
        result.setContent(map);
        logger.info("通过中医体质问卷获取中医体质结果成功");
	    return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findTcmAndAgedQuestionnaire 
     * @Description: 获取中医体质和老年人生活能力评估问卷
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws Exception    
     * @retrun void
     */
    public String findTcmAndAgedQuestionnaire(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
	    String params = request.getParameter("params");
	    CallValue callValue = JSON.parseObject(params, CallValue.class);
	    JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
	    int doctorId = callValue.getMemberId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数医生id【"+doctorId+"】应为正整数！");
            logger.info("参数医生id【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
	    int memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        long hExamID = jsonObject.getLong("hExamID");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("aged", docHealthExamDao.findTcmOrAgedQuestionnaire(doctorId,memberId,hExamID,"aged"));
        map.put("tcm", docHealthExamDao.findTcmOrAgedQuestionnaire(doctorId,memberId,hExamID,"tcm"));
        result.setState(0);
        result.setMessage("获取中医体质和老年人生活能力评估问卷成功");
        result.setContent(map);
        logger.info("获取中医体质和老年人生活能力评估问卷成功");
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: getFamilyBed 
     * @Description: 获取建床病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public PhHealthExamDetail getExamDetail(JSONObject jsonObject)throws Exception{
        PhHealthExamDetail examDetail = new PhHealthExamDetail();
        String symptoms = jsonObject.getString("symptoms");
        if(!StringUtils.isEmpty(symptoms)){
            PhHealthExamDetail examSymptoms = JSON.parseObject(symptoms, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examSymptoms, examDetail, getNullPropertyNames(examSymptoms));
        }
        String generalSituation = jsonObject.getString("generalSituation");
        if(!StringUtils.isEmpty(generalSituation)){
            PhHealthExamDetail examGeneral = JSON.parseObject(generalSituation, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examGeneral, examDetail, getNullPropertyNames(examGeneral));
        }
        String lifeStyle = jsonObject.getString("lifeStyle");
        if(!StringUtils.isEmpty(lifeStyle)){
            PhHealthExamDetail examLifeStyle = JSON.parseObject(lifeStyle, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examLifeStyle, examDetail, getNullPropertyNames(examLifeStyle));
        }
        String organFunction = jsonObject.getString("organFunction");
        if(!StringUtils.isEmpty(organFunction)){
            PhHealthExamDetail examOrganFunction = JSON.parseObject(organFunction, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examOrganFunction, examDetail, getNullPropertyNames(examOrganFunction));
        }
        String examination = jsonObject.getString("examination");
        if(!StringUtils.isEmpty(examination)){
            PhHealthExamDetail examExamination = JSON.parseObject(examination, PhHealthExamDetail.class);
            String breast = examExamination.getBreast();
            examExamination.setBreast("");
            BeanUtils.copyProperties(examExamination, examDetail, getNullPropertyNames(examExamination));
            if(!StringUtils.isEmpty(breast)){
            	examDetail.setBreast(breast);
            }
        }
        String accessoryExamination = jsonObject.getString("accessoryExamination");
        if(!StringUtils.isEmpty(accessoryExamination)){
            PhHealthExamDetail examAccessoryExamination = JSON.parseObject(accessoryExamination, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examAccessoryExamination, examDetail, getNullPropertyNames(examAccessoryExamination));
        }
        String tcmConstitutionIdentification = jsonObject.getString("tcmConstitutionIdentification");
        if(!StringUtils.isEmpty(tcmConstitutionIdentification)){
            PhHealthExamDetail examTcmConstitution = JSON.parseObject(tcmConstitutionIdentification, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examTcmConstitution, examDetail, getNullPropertyNames(examTcmConstitution));
        }
        String majorHealthProblems = jsonObject.getString("majorHealthProblems");
        if(!StringUtils.isEmpty(majorHealthProblems)){
            PhHealthExamDetail examMajorHealthProblems = JSON.parseObject(majorHealthProblems, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examMajorHealthProblems, examDetail, getNullPropertyNames(examMajorHealthProblems));
        }
        String healthEvaluation = jsonObject.getString("healthEvaluation");
        if(!StringUtils.isEmpty(healthEvaluation)){
            PhHealthExamDetail examHealthEvaluation = JSON.parseObject(healthEvaluation, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examHealthEvaluation, examDetail, getNullPropertyNames(examHealthEvaluation));
        }
        String healthGuidance = jsonObject.getString("healthGuidance");
        if(!StringUtils.isEmpty(healthGuidance)){
            PhHealthExamDetail examHealthGuidance = JSON.parseObject(healthGuidance, PhHealthExamDetail.class);
            BeanUtils.copyProperties(examHealthGuidance, examDetail, getNullPropertyNames(examHealthGuidance));
        }
        String riskFactorControl = jsonObject.getString("riskFactorControl");
        if(!StringUtils.isEmpty(riskFactorControl)){
        	JSONObject risk = JSON.parseObject(riskFactorControl);
        	String riskFactor = risk.getString("riskFactor");
        	if(!StringUtils.isEmpty(riskFactor)){
        		examDetail.setRiskFactor(riskFactor);
        	}
        	String riskFactor_Target = risk.getString("riskFactorTarget");
        	if(!StringUtils.isEmpty(riskFactor_Target)){
        		examDetail.setRiskFactor_Target(riskFactor_Target);
        	}
        	String riskFactor_Vaccine = risk.getString("riskFactorVaccine");
        	if(!StringUtils.isEmpty(riskFactor_Vaccine)){
        		examDetail.setRiskFactor_Vaccine(riskFactor_Vaccine);
        	}
        	String riskFactor_Other = risk.getString("riskFactorOther");
        	if(!StringUtils.isEmpty(riskFactor_Other)){
        		examDetail.setRiskFactor_Other(riskFactor_Other);
        	}
        }
        return examDetail;
    }
    
    /** 
     * @Title: getFamilyBed 
     * @Description: 获取建床病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public List<PHHealthExamdetailFamilyBed> getFamilyBed(JSONObject jsonObject)throws Exception{
        List<PHHealthExamdetailFamilyBed> list = new ArrayList<PHHealthExamdetailFamilyBed>();
        String hospitalCourse = jsonObject.getString("hospitalCourse");
        if(!StringUtils.isEmpty(hospitalCourse)){
            JSONObject hospitalCourseNew = JSON.parseObject(hospitalCourse);
            String familyBed = hospitalCourseNew.getString("familyBedHistory");
            if(!StringUtils.isEmpty(familyBed)){
                list = JSON.parseArray(familyBed, PHHealthExamdetailFamilyBed.class);
            }
        }
        return list;
    }
    
    /** 
     * @Title: getPatient 
     * @Description: 获取住院史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public List<PHHealthExamDetailinPatient> getPatient(JSONObject jsonObject)throws Exception{
        List<PHHealthExamDetailinPatient> list = new ArrayList<PHHealthExamDetailinPatient>();
        String hospitalCourse = jsonObject.getString("hospitalCourse");
        if(!StringUtils.isEmpty(hospitalCourse)){
            JSONObject hospitalCourseNew = JSON.parseObject(hospitalCourse);
            String patient = hospitalCourseNew.getString("hospitalization");
            if(!StringUtils.isEmpty(patient)){
                list = JSON.parseArray(patient, PHHealthExamDetailinPatient.class);
            }
        }
        return list;
    }
    
    /** 
     * @Title: getMedicine 
     * @Description: 获取用药情况
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public List<PHHealthExamDetailMedicine> getMedicine(JSONObject jsonObject)throws Exception{
        List<PHHealthExamDetailMedicine> list = new ArrayList<PHHealthExamDetailMedicine>();
        String medication = jsonObject.getString("medication");
        if(!StringUtils.isEmpty(medication)){
            list = JSON.parseArray(medication, PHHealthExamDetailMedicine.class);
        }
        return list;
    }
    
    /** 
     * @Title: getNonimmune 
     * @Description: 获取免疫接种病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public List<PHHealthExamDetailNonimmune> getNonimmune(JSONObject jsonObject)throws Exception{
        List<PHHealthExamDetailNonimmune> list = new ArrayList<PHHealthExamDetailNonimmune>();
        String nonimmune = jsonObject.getString("vaccination");
        if(!StringUtils.isEmpty(nonimmune)){
            list = JSON.parseArray(nonimmune, PHHealthExamDetailNonimmune.class);
        }
        return list;
    }
    
    /** 
     * @Title: getNullPropertyNames 
     * @Description: 获取为空的属性
     * @param Object
     * @author liuxiaoqin
     * @createDate 2016-06-02
     * @throws Exception    
     * @retrun void
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /** 
     * @Title: getExamAnswerConclusion 
     * @Description: 获取答卷结论
     * @param Object
     * @author liuxiaoqin
     * @createDate 2016-06-27
     * @throws Exception    
     * @retrun String
     */
    public String getExamAnswerConclusion (String answerType,HttpServletRequest request)throws Exception{
    	String conclusion = "";
    	if(answerType.equals("tcm")){
    		String returnStr = findTcmValue(request);
    		JSONObject returnString = JSON.parseObject(returnStr);
    		String tcmResult = returnString.getString("content");
    		JSONObject tcm = JSON.parseObject(tcmResult);
    		Integer tCM_PHZ = tcm.getInteger("tCM_PHZ");
    		String tCM_PHZ_DESC = getTCMDescription(tCM_PHZ,"phz");
    		Integer tCM_QXZ = tcm.getInteger("tCM_QXZ");
    		String tCM_QXZ_DESC = getTCMDescription(tCM_QXZ,null);
    		Integer tCM_YXZ = tcm.getInteger("tCM_YXZ");
    		String tCM_YXZ_DESC = getTCMDescription(tCM_YXZ,null);
    		Integer tCM_YIXZ = tcm.getInteger("tCM_YIXZ");
    		String tCM_YIXZ_DESC = getTCMDescription(tCM_YIXZ,null);
    		Integer tCM_TSZ = tcm.getInteger("tCM_TSZ");
    		String tCM_TSZ_DESC = getTCMDescription(tCM_TSZ,null);
    		Integer tCM_SRZ = tcm.getInteger("tCM_SRZ");
    		String tCM_SRZ_DESC = getTCMDescription(tCM_SRZ,null);
    		Integer tCM_XTZ = tcm.getInteger("tCM_XTZ");
    		String tCM_XTZ_DESC = getTCMDescription(tCM_XTZ,null);
    		Integer tCM_QYZ = tcm.getInteger("tCM_QYZ");
    		String tCM_QYZ_DESC = getTCMDescription(tCM_QYZ,null);
    		Integer tCM_TBZ = tcm.getInteger("tCM_TBZ");
    		String tCM_TBZ_DESC = getTCMDescription(tCM_TBZ,null);
    		conclusion = "平和质" + Constants.LEFT_BRACKET + tCM_PHZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "气虚质" + Constants.LEFT_BRACKET + tCM_QXZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "阳虚质" + Constants.LEFT_BRACKET + tCM_YXZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "阴虚质" + Constants.LEFT_BRACKET + tCM_YIXZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "痰湿质" + Constants.LEFT_BRACKET + tCM_TSZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "湿热质" + Constants.LEFT_BRACKET + tCM_SRZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "血瘀质" + Constants.LEFT_BRACKET + tCM_XTZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "气郁质" + Constants.LEFT_BRACKET + tCM_QYZ_DESC + Constants.RIGHT_BRACKET + ";"
    				   + "特禀质" + Constants.LEFT_BRACKET + tCM_TBZ_DESC + Constants.RIGHT_BRACKET;
    		
    	}else{
    		String returnStr = findAgedLifeEvaluate(request);
    		JSONObject returnString = JSON.parseObject(returnStr);
    		String agedLifeResult = returnString.getString("content");
    		JSONObject aged = JSON.parseObject(agedLifeResult);
    		Integer agedValue = aged.getInteger("agedLifeEvaluate");
    		if(agedValue != null && agedValue == 1){
    			conclusion = Constants.AGED_HANDLE;
    		}else if(agedValue != null && agedValue == 2){
    			conclusion = Constants.AGED_LIGHT_DEPENDENT;
    		}else if(agedValue != null && agedValue == 3){
    			conclusion = Constants.AGED_MIDDLE_DEPENDENT;
    		}else if(agedValue != null && agedValue == 4){
    			conclusion = Constants.AGED_NOT_HANDLE;
    		}
    	}
    	return conclusion;
    }
    
    /** 
     * @Title: getTCMDescription 
     * @Description: 获取中医体质的描述
     * @author liuxiaoqin
     * @createDate 2016-06-27 
     * @param everyTCMScore
     * @throws Exception    
     * @retrun Integer
     */
    public String getTCMDescription(Integer tcmValue,String type)throws Exception{
    	String result = "";
    	if(tcmValue == 1){
    		result = Constants.TCM_RESULT_YES;
    	}else if(tcmValue == 2){
    		if(type != null && type.equals("phz")){
    			result = "基本是";
    		}else{
    			result = Constants.TCM_RESULT_TEND_YES;
    		}
    	}else if(tcmValue == 3 ){
    		result = Constants.TCM_RESULT_NO;
    	}
    	return result;
    }
    
}
