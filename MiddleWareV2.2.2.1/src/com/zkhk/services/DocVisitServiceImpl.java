package com.zkhk.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hkbithealth.bean.ph.PHDiabetes;
import com.hkbithealth.bean.ph.PHDiabetesDetail;
import com.hkbithealth.bean.ph.PHDiabetesDetailMedicine;
import com.hkbithealth.bean.ph.PHHealthExam;
import com.hkbithealth.bean.ph.PHHealthExamDetailMedicine;
import com.hkbithealth.bean.ph.PHHypertension;
import com.hkbithealth.bean.ph.PHHypertensionDetail;
import com.hkbithealth.bean.ph.PHHypertensionDetailMedicine;
import com.hkbithealth.bean.ph.PHOmem;
import com.hkbithealth.enmu.Terminal;
import com.hkbithealth.service.ph.PublicHealthService;
import com.zkhk.constants.Constants;
import com.zkhk.dao.MemDao;
import com.zkhk.dao.VisitDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.MemBasicInfo;
import com.zkhk.entity.MemSearch;
import com.zkhk.entity.Omem;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.VisitParam;
import com.zkhk.util.TimeUtil;
import com.zkhk.util.VisitUtil;

@Service("docVisitService")
public class DocVisitServiceImpl implements DocVisitService {

	private Logger logger = Logger.getLogger(DocVisitServiceImpl.class);
	
	private PublicHealthService publicHealthService = PublicHealthService.getInstance();
	
	@Resource(name = "visitDao")
    private VisitDao visitDao;
	
	@Resource(name = "memDao")
    private MemDao memDao;
	
	 /** 
     * @Title: findMemDiabetesVisitList 
     * @Description: 医生查询会员的糖尿病随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findMemDiabetesVisitList(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        VisitParam param = JSON.parseObject(callValue.getParam(), VisitParam.class);
        int doctorId = callValue.getMemberId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数医生id【"+doctorId+"】应为正整数！");
            logger.info("参数医生id【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        boolean followUp = false;
        int hasVisited = param.getHasVisited();
        if(hasVisited > 0){
            followUp = true;
        }
        int pageNow = param.getPageNow();
        int pageSize = param.getPageSize();
        PHOmem omem = new PHOmem();
        omem.setDocid(doctorId);
        String searchName = param.getSearchName();
        if(!StringUtils.isEmpty(searchName)){
            omem.setMemName(searchName);
        }
        Terminal phone = Terminal.PHONE;
        List<PHDiabetes> diabetesList = (List<PHDiabetes>) publicHealthService.queryDiabetesPage(omem, null, null, pageNow, pageSize, null, followUp,phone).getList();
        if(diabetesList != null && diabetesList.size() > 0){
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            for(PHDiabetes diabetes : diabetesList){
                Map<String,Object> map = new HashMap<String, Object>();
                String date = "";
                Date visitDate = diabetes.getVisitDate();
                if(visitDate != null){
                    date = TimeUtil.formatDate(visitDate);
                }
                map.put("visitDate", date);
                Long diabetesId =  diabetes.getDiabetesId();
                /*判断明细表中的id是否为空  begin*/
                Integer count = visitDao.hasDiabeteDetailRecord(diabetesId);
                if(count == null || count <= 0){
                    //明细表中插入新的id
                    visitDao.insertDiabeteDetailRecord(diabetesId);
                }
                /*判断明细表中的id是否为空  end*/
                map.put("diabetesId", diabetesId);
                map.put("visitResult", diabetes.getVisitClassStr());
                Map<String, Object> basicInfoMap = VisitUtil.getDiabeBasicInfo(diabetes);
                int memberId = (int)basicInfoMap.get("memberId");
                String headAddress = memDao.findMemberHeadImg(memberId);
                if(!StringUtils.isEmpty(headAddress)){
                    basicInfoMap.put("headAddress", headAddress);
                }
                map.put("basicInfo", basicInfoMap);
                mapList.add(map);
            }
            result.setState(0);
            result.setMessage("医生查询会员的糖尿病随访列表成功");
            result.setContent(mapList);
            logger.info("医生查询会员的糖尿病随访列表成功");
        }else{
            result.setState(3);
            result.setMessage("没有该医生所属下的会员的糖尿病随访列表");
            logger.info("没有该医生所属下的会员的糖尿病随访列表");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemDiabetesVisitDetail 
     * @Description: 医生查询会员的糖尿病随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemDiabetesVisitDetail(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        long logId = jsonObject.getLong("logId");
        if(logId <= 0){
            result.setState(1);
            result.setMessage("参数logId【"+logId+"】应为正整数！");
            logger.info("参数logId【"+logId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        PHDiabetes visit = publicHealthService.queryDiabetesDetail(logId);
        if(visit != null){
            Map<String,Object> map = new HashMap<String, Object>();
            String date = "";
            Date visitDate = visit.getVisitDate();
            if(visitDate != null){
                date = TimeUtil.formatDate(visitDate);
            }
            map.put("visitDate",date);
            Integer createDrID = visit.getCreateDrID();
            if(createDrID !=null &&createDrID >0){
            	 map.put("createDrID",createDrID);
            }
            String createDrName = visit.getCreateDrName();
            if(!StringUtils.isEmpty(createDrName)){
            	map.put("createDrName",createDrName);
            }
            Date createTime = visit.getCreateTime();
            if(createTime != null){
            	String newCreateTime = TimeUtil.formatDatetime2(createTime);
                map.put("createTime",newCreateTime);
            }
            Date getTime = visit.getGetTime();
            if(getTime != null){
            	String newGetTime = TimeUtil.formatDatetime2(getTime);
                map.put("getTime",newGetTime);
            }
            PHDiabetesDetail detail = visit.getDiabetesDetail();
            if(detail != null){
                map.put("visitWayStr",detail.getVisitWayStr());
                String symptom = detail.getSymptom();
                String symptomStr = "";
                String symptomIds = "";
                if(!StringUtils.isEmpty(symptom) && !"null".equals(symptom)){
                	symptomStr = detail.getSymptomStr();
                	symptomIds = StringUtils.replace(symptom, "@#", ",");
                }
//                String symptomDesc = detail.getSymptomDesc();
//                if(!StringUtils.isEmpty(symptomDesc)){
//                    symptomStr += Constants.ZN_STRING_SEPARATE + symptomDesc;
//                }
                map.put("symptomStr", symptomStr);
                map.put("symptomIds", symptomIds);
                map.put("rhgStr", detail.getRhgStr());
                map.put("drugComplianceStr",detail.getDrugCompliStr());
                Integer drugAdverseReaction = detail.getDrugAdverseReaction();
                String drugAdverseReactionStr = detail.getDrugAdverReaStr();
                if(drugAdverseReaction != null && drugAdverseReaction == 2){
                    String drugAdverseReactionDesc = detail.getDrugAdverseReactionDesc();
                    if(!StringUtils.isEmpty(drugAdverseReactionDesc)){
                        drugAdverseReactionStr += Constants.LEFT_BRACKET + drugAdverseReactionDesc + Constants.RIGHT_BRACKET;
                    }
                }
                map.put("drugAdverseReactionStr",drugAdverseReactionStr);
                map.put("transferReason",detail.getTransferReason());
                map.put("transferOrgAndDept",detail.getTransferOrgAndDept());
                String nextDate = "";
                Date newDate = detail.getVisitDateNext();
                if(newDate != null){
                    nextDate = TimeUtil.formatDate(newDate);
                }
                map.put("nextVistDate",nextDate);
                map.put("visitDrName",visit.getVisitDrName());
                map.put("accessoryExamination",VisitUtil.getDiabeAccessoryExamination(visit));
                map.put("physicalSigns",VisitUtil.getDiabePhysicalSigns(visit));
                map.put("lifeStyle", VisitUtil.getDiabeLifeStyle(visit));
                map.put("medication",VisitUtil.getDiabeMedication(visit));
                result.setState(0);
                result.setMessage("医生查询会员的糖尿病随访明细成功");
                result.setContent(map);
                logger.info("医生查询会员的糖尿病随访明细成功");
            }else{
                result.setState(3);
                result.setMessage("没有该会员的糖尿病随访明细");
                logger.info("没有该会员的糖尿病随访明细");
            }
        }else{
            result.setState(3);
            result.setMessage("没有该会员的糖尿病随访明细");
            logger.info("没有该会员的糖尿病随访明细");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemHypertensionVisitList 
     * @Description: 医生查询会员的高血压随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findMemHypertensionVisitList(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        VisitParam param = JSON.parseObject(callValue.getParam(), VisitParam.class);
        int doctorId = callValue.getMemberId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数医生id【"+doctorId+"】应为正整数！");
            logger.info("参数医生id【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        boolean followUp = false;
        int hasVisited = param.getHasVisited();
        if(hasVisited > 0){
            followUp = true;
        }
        int pageNow = param.getPageNow();
        int pageSize = param.getPageSize();
        PHOmem omem = new PHOmem();
        omem.setDocid(doctorId);
        String searchName = param.getSearchName();
        if(!StringUtils.isEmpty(searchName)){
            omem.setMemName(searchName);
        }
        Terminal phone = Terminal.PHONE;
        List<PHHypertension> hypertensionList = (List<PHHypertension>) publicHealthService.queryHypertensionPage(omem, null,null, null, pageNow, pageSize, followUp,phone).getList();
        if(hypertensionList != null && hypertensionList.size() > 0){
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            for(PHHypertension hypertension : hypertensionList){
                Map<String,Object> map = new HashMap<String, Object>();
                String date = "";
                Date visitDate = hypertension.getVisitDate();
                if(visitDate != null){
                    date = TimeUtil.formatDate(visitDate);
                }
                map.put("visitDate", date);
                Long hypertensionID =  hypertension.getHypertensionID();
                /*判断明细表中的id是否为空  begin*/
                Integer count = visitDao.hasHyperDetailRecord(hypertensionID);
                if(count == null || count <= 0){
                    //明细表中插入新的id
                    visitDao.insertHyperDetailRecord(hypertensionID);
                }
                /*判断明细表中的id是否为空  end*/
                map.put("hypertensionID", hypertensionID);
                map.put("visitResult", hypertension.getVisitClassStr());
                Map<String, Object> basicInfoMap = VisitUtil.getHyperBasicInfo(hypertension);
                int memberId = (int)basicInfoMap.get("memberId");
                String headAddress = memDao.findMemberHeadImg(memberId);
                if(!StringUtils.isEmpty(headAddress)){
                    basicInfoMap.put("headAddress", headAddress);
                }
                map.put("basicInfo", basicInfoMap);
                mapList.add(map);
            }
            result.setState(0);
            result.setMessage("医生查询会员的高血压随访列表成功");
            result.setContent(mapList);
            logger.info("医生查询会员的高血压随访列表成功");
        }else{
            result.setState(3);
            result.setMessage("没有该医生所属下的会员的高血压随访列表");
            logger.info("没有该医生所属下的会员的高血压随访列表");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMemHypertensionVisitDetail 
     * @Description: 医生查询会员的高血压随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findMemHypertensionVisitDetail(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        long logId = jsonObject.getLong("logId");
        if(logId <= 0){
            result.setState(1);
            result.setMessage("参数logId【"+logId+"】应为正整数！");
            logger.info("参数logId【"+logId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        PHHypertension visit = publicHealthService.queryHypertensionDetail(logId);
        if(visit != null){
            Map<String,Object> map = new HashMap<String, Object>();
            String date = "";
            Date visitDate = visit.getVisitDate();
            if(visitDate != null){
                date = TimeUtil.formatDate(visitDate);
            }
            map.put("visitDate",date);
            Integer createDrID = visit.getCreateDrID();
            if(createDrID !=null &&createDrID >0){
            	 map.put("createDrID",createDrID);
            }
            String createDrName = visit.getCreateDrName();
            if(!StringUtils.isEmpty(createDrName)){
            	map.put("createDrName",createDrName);
            }
            Date createTime = visit.getCreateTime();
            if(createTime != null){
            	String newCreateTime = TimeUtil.formatDatetime2(createTime);
                map.put("createTime",newCreateTime);
            }
            Date getTime = visit.getGetTime();
            if(getTime != null){
            	String newGetTime = TimeUtil.formatDatetime2(getTime);
                map.put("getTime",newGetTime);
            }
            PHHypertensionDetail detail = visit.getHypertensionDetail();
            if(detail != null){
                map.put("visitWayStr",detail.getVisitWayStr());
                String symptom = detail.getSymptom();
                String symptomStr = "";
                String symptomIds = "";
                if(!StringUtils.isEmpty(symptom) && !"null".equals(symptom)){
                	symptomStr = detail.getSymptomStr();
                	symptomIds = StringUtils.replace(symptom, "@#", ",");
                }
//                String symptomDesc = detail.getSymptomDesc();
//                if(!StringUtils.isEmpty(symptomDesc)){
//                    symptomStr += Constants.ZN_STRING_SEPARATE + symptomDesc;
//                }
                map.put("symptomStr", symptomStr);
                map.put("symptomIds", symptomIds);
                map.put("drugComplianceStr",detail.getDrugCompliStr() );
                Integer drugAdverseReaction = detail.getDrugAdverseReaction();
                String drugAdverseReactionStr = detail.getDrugAdverReaStr();
                if(drugAdverseReaction != null && drugAdverseReaction == 2){
                    String drugAdverseReactionDesc = detail.getDrugAdverseReactionDesc();
                    if(!StringUtils.isEmpty(drugAdverseReactionDesc)){
                        drugAdverseReactionStr += Constants.LEFT_BRACKET + drugAdverseReactionDesc + Constants.RIGHT_BRACKET;
                    }
                }
                map.put("drugAdverseReactionStr",drugAdverseReactionStr);
                map.put("checkResult",detail.getCheckResult() );
                map.put("transferReason",detail.getTransferReason() );
                map.put("transferOrgAndDept",detail.getTransferOrgAndDept());
                String nextDate = "";
                Date newDate = detail.getVisitDateNext();
                if(newDate != null){
                    nextDate = TimeUtil.formatDate(newDate);
                }
                map.put("nextVistDate",nextDate);
                map.put("visitDrName",visit.getVisitDrName());
                map.put("physicalSigns",VisitUtil.getHyperPhysicalSigns(visit));
                map.put("lifeStyle", VisitUtil.getHyperLifeStyle(visit));
                map.put("medication",VisitUtil.getHyperMedication(visit));
                result.setState(0);
                result.setMessage("医生查询会员的高血压随访明细成功");
                result.setContent(map);
                logger.info("医生查询会员的高血压随访明细成功");
            }else{
                result.setState(3);
                result.setMessage("没有该会员的高血压随访明细");
                logger.info("没有该会员的高血压随访明细");
            }
        }else{
            result.setState(3);
            result.setMessage("没有该会员的高血压随访明细");
            logger.info("没有该会员的高血压随访明细");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: addOrModifyMemHypertensionVisit 
     * @Description: 医生新增或者修改会员的高血压随访
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemHypertensionVisit(HttpServletRequest request) throws Exception{
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
        Long hypertensionID = jsonObject.getLong("hypertensionID");
        ReturnResult resultFinal = new ReturnResult();
        if(hypertensionID <= 0){
            resultFinal = validAddHyperParam(doctorId,doctorName,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHHypertension visit = (PHHypertension)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyHypertension(visit);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生新增会员的高血压随访成功");
                logger.info("医生新增会员的高血压随访成功");
            }else{
                result.setState(1);
                result.setMessage("医生新增会员的高血压随访失败");
                logger.info("医生新增会员的高血压随访失败");
            }
        }else{
            resultFinal = validModifyHyperParam(doctorId,doctorName,hypertensionID,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHHypertension visit = (PHHypertension)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyHypertension(visit);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生修改会员的高血压随访成功");
                logger.info("医生修改会员的高血压随访成功");
            }else{
                result.setState(1);
                result.setMessage("医生修改会员的高血压随访失败");
                logger.info("医生修改会员的高血压随访失败");
            }
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: addOrModifyMemDiabetesVisit 
     * @Description: 医生新增或者修改会员的糖尿病随访
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String addOrModifyMemDiabetesVisit(HttpServletRequest request) throws Exception{
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
        Long diabetesId = jsonObject.getLong("diabetesId");
        ReturnResult resultFinal = new ReturnResult();
        if(diabetesId <= 0){
            resultFinal = validAddDiabetesParam(doctorId,doctorName,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHDiabetes visit = (PHDiabetes)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyPhdiabetes(visit);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生新增会员的糖尿病随访成功");
                logger.info("医生新增会员的糖尿病随访成功");
            }else{
                result.setState(1);
                result.setMessage("医生新增会员的糖尿病随访失败");
                logger.info("医生新增会员的糖尿病随访失败");
            }
        }else{
            resultFinal = validModifyDiabetesParam(doctorId,doctorName,diabetesId,jsonObject);
            if(resultFinal.getState() != 0){
                result.setState(resultFinal.getState());
                result.setMessage(resultFinal.getMessage());
                logger.info(resultFinal.getMessage());
                return JSON.toJSONString(result);
            }
            PHDiabetes visit = (PHDiabetes)resultFinal.getContent();
            boolean isSuccess = publicHealthService.addOrModifyPhdiabetes(visit);
            if(isSuccess){
                result.setState(0);
                result.setMessage("医生修改会员的糖尿病随访成功");
                logger.info("医生修改会员的糖尿病随访成功");
            }else{
                result.setState(1);
                result.setMessage("医生修改会员的糖尿病随访失败");
                logger.info("医生修改会员的糖尿病随访失败");
            }
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findCanVisitDiabeteMem 
     * @Description: 医生获取可进行糖尿病随访的会员
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findCanVisitDiabeteMem(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        MemSearch memSearch = JSON.parseObject(callValue.getParam(), MemSearch.class);
        int diseaseId = memSearch.getDiseaseId();
        if(diseaseId <= 0){
            result.setState(1);
            result.setMessage("参数diseaseId【"+diseaseId+"】应为正整数！");
            logger.info("参数diseaseId【"+diseaseId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int doctorId = memSearch.getDoctorId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数doctorId【"+doctorId+"】应为正整数！");
            logger.info("参数doctorId【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        List<MemBasicInfo> memberList = visitDao.findCanVisitDiabeteMem(memSearch);
        if(memberList.size() > 0){
            result.setState(0);
            result.setContent(memberList);
            result.setMessage("医生获取可进行糖尿病随访的会员成功");
            logger.info("医生获取可进行糖尿病随访的会员成功");
        }else{
            result.setState(3);
            result.setMessage("医生查询不到可进行糖尿病随访的会员");
            logger.info("医生查询不到可进行糖尿病随访的会员");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findCanVisitHyperMem 
     * @Description: 医生获取可进行高血压随访的会员
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findCanVisitHyperMem(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        MemSearch memSearch = JSON.parseObject(callValue.getParam(), MemSearch.class);
        int diseaseId = memSearch.getDiseaseId();
        if(diseaseId <= 0){
            result.setState(1);
            result.setMessage("参数diseaseId【"+diseaseId+"】应为正整数！");
            logger.info("参数diseaseId【"+diseaseId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        int doctorId = memSearch.getDoctorId();
        if(doctorId <= 0){
            result.setState(1);
            result.setMessage("参数doctorId【"+doctorId+"】应为正整数！");
            logger.info("参数doctorId【"+doctorId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        List<MemBasicInfo> memberList = visitDao.findCanVisitHyperMem(memSearch);
        if(memberList.size() > 0){
            result.setState(0);
            result.setContent(memberList);
            result.setMessage("医生获取可进行高血压随访的会员成功");
            logger.info("医生获取可进行高血压随访的会员成功");
        }else{
            result.setState(3);
            result.setMessage("医生查询不到可进行高血压随访的会员");
            logger.info("医生查询不到可进行高血压随访的会员");
        }
        return JSON.toJSONString(result);
    }
    
     /** 
     * @Title: validAddHyperParam 
     * @Description: 验证新增参数 
     * @param doctorId
     * @param doctorName
     * @author liuxiaoqin
     * @createDate 2016-04-04
     * @param hypertensionID
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validAddHyperParam(Integer doctorId,String doctorName,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();
        PHHypertension visit = new PHHypertension();
        Integer memberID = jsonObject.getInteger("memberID");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberID【"+memberID+"】应为正整数！");
            logger.info("参数memberID【"+memberID+"】应为正整数！");
            return result;
        }
        String name = jsonObject.getString("name");
        visit.setName(name);
        int hasRecord = visitDao.hasVisitingHyperRecord(memberID);
        if(hasRecord > 0){
            result.setState(4);
            result.setMessage("会员【"+name+"】已有待随访的高血压记录,请勿重复添加！");
            logger.info("会员【"+name+"】已有待随访的高血压记录,请勿重复添加！");
            return result;
        }
        visit.setMemberID(memberID);
        String uniqueID = jsonObject.getString("uniqueID");
        visit.setUniqueID(uniqueID);
        Integer refCompany = jsonObject.getInteger("refCompany");
        visit.setRefCompany(refCompany);
        String refDataPK = jsonObject.getString("refDataPK");
        visit.setRefDataPK(refDataPK);
        String idCard = jsonObject.getString("idCard");
        visit.setIdCard(idCard);
        String date = jsonObject.getString("visitDate");
        if(!StringUtils.isEmpty(date)){
            Date visitDate = TimeUtil.parseDate(date);
            visit.setVisitDate(visitDate);
        }else{
        	String nowStr = TimeUtil.currentDate();
        	Date now = TimeUtil.parseDate(nowStr);
        	visit.setVisitDate(now);
        }
        String visitDrName = jsonObject.getString("visitDrName");
        if(StringUtils.isEmpty(visitDrName)){
        	visit.setVisitDrName(doctorName);
        }else{
        	visit.setVisitDrName(visitDrName);
        }
        //随访分类默认为0--待随访
        visit.setVisitClass(jsonObject.getInteger("visitClass"));
        visit.setCreateDrID(doctorId);
        visit.setIsDeleted(0);
        visit.setCreateDrName(doctorName);
        visit.setGetTime(new Date());
        String detailStr = jsonObject.getString("hypertensionDetail");
        if(!StringUtils.isEmpty(detailStr)){
            PHHypertensionDetail hypertensionDetail = JSON.parseObject(detailStr, PHHypertensionDetail.class);
            String str = hypertensionDetail.getSymptom();
            String symptom = StringUtils.replace(str, ",", "@#");
            hypertensionDetail.setSymptom(symptom);
            visit.setHypertensionDetail(hypertensionDetail);
            Date nextVisitDate = hypertensionDetail.getVisitDateNext();
            if(nextVisitDate != null){
            	visit.setUpdateDrID(doctorId);
            	visit.setUpdateDrName(doctorName);
            }
        }
        String medicineStr = jsonObject.getString("hyperDetailMedicines");
        if(!StringUtils.isEmpty(medicineStr)){
            List<PHHypertensionDetailMedicine> hyperDetailMedicines = JSON.parseArray(medicineStr, PHHypertensionDetailMedicine.class);
            visit.setHyperDetailMedicines(hyperDetailMedicines);
        }
        result.setState(0);
        result.setContent(visit);
        return result;
    }
    
     /** 
     * @Title: validModifyHyperParam 
     * @Description: 验证修改参数
     * @author liuxiaoqin
     * @createDate 2016-04-04 
     * @param doctorId
     * @param doctorName
     * @param hypertensionID
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validModifyHyperParam(Integer doctorId,String doctorName,Long hypertensionID,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();
        PHHypertension visit = new PHHypertension();
        Integer memberID = jsonObject.getInteger("memberID");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberID【"+memberID+"】应为正整数！");
            logger.info("参数memberID【"+memberID+"】应为正整数！");
            return result;
        }
        if(hypertensionID <= 0){
            result.setState(1);
            result.setMessage("参数hypertensionID【"+hypertensionID+"】应为正整数！");
            logger.info("参数hypertensionID【"+hypertensionID+"】应为正整数！");
            return result;
        }
        visit.setHypertensionID(hypertensionID);
        Integer nowVisitClass = visitDao.isOneVisitingHyperRecord(hypertensionID);
        if(nowVisitClass == null){
        	result.setState(1);
            result.setMessage("该条高血压随访记录不存在，请重新核对。");
            logger.info("该条高血压随访记录不存在，请重新核对。");
            return result;
        }
        if(nowVisitClass > 0){
        	result.setState(1);
            result.setMessage("该条高血压随访已为【已随访】状态，不能修改，请重新选择。");
            logger.info("该条高血压随访已为【已随访】状态，不能修改，请重新选择。");
            return result;
        }
        visit.setMemberID(memberID);
        String uniqueID = jsonObject.getString("uniqueID");
        visit.setUniqueID(uniqueID);
        Integer refCompany = jsonObject.getInteger("refCompany");
        visit.setRefCompany(refCompany);
        String refDataPK = jsonObject.getString("refDataPK");
        visit.setRefDataPK(refDataPK);
        String idCard = jsonObject.getString("idCard");
        visit.setIdCard(idCard);
        String name = jsonObject.getString("name");
        visit.setName(name);
        String date = jsonObject.getString("visitDate");
        if(!StringUtils.isEmpty(date)){
            Date visitDate = TimeUtil.parseDate(date);
            visit.setVisitDate(visitDate);
        }
        //visit.setVisitDrName(doctorName);
        String visitDrName = jsonObject.getString("visitDrName");
        if(StringUtils.isEmpty(visitDrName)){
        	visit.setVisitDrName(doctorName);
        }else{
        	visit.setVisitDrName(visitDrName);
        }
        Integer visitClass = jsonObject.getInteger("visitClass");
        visit.setVisitClass(visitClass);
        Integer createDrID = jsonObject.getInteger("createDrID");
        if(createDrID == null || createDrID <=0){
        	result.setState(1);
            result.setMessage("参数createDrID【"+createDrID+"】应为正整数！");
            logger.info("参数createDrID【"+createDrID+"】应为正整数！");
            return result;
        }
        visit.setCreateDrID(createDrID);
        String createDrName = jsonObject.getString("createDrName");
        if(StringUtils.isEmpty(createDrName)){
        	result.setState(1);
            result.setMessage("参数createDrName【"+createDrName+"】不能为空！");
            logger.info("参数createDrName【"+createDrName+"】不能为空！");
            return result;
        }
        visit.setCreateDrName(createDrName);
        String createTime = jsonObject.getString("createTime");
        if(StringUtils.isEmpty(createTime)){
        	result.setState(1);
            result.setMessage("参数createTime【"+createTime+"】不能为空！");
            logger.info("参数createTime【"+createTime+"】不能为空！");
            return result;
        }
        Date newCreateTime = TimeUtil.parseDatetime(createTime);
        visit.setCreateTime(newCreateTime);
        String getTime = jsonObject.getString("getTime");
        if(StringUtils.isEmpty(getTime)){
        	result.setState(1);
            result.setMessage("参数getTime【"+getTime+"】不能为空！");
            logger.info("参数getTime【"+getTime+"】不能为空！");
            return result;
        }
        Date newGetTime = TimeUtil.parseDatetime(getTime);
        visit.setGetTime(newGetTime);
        visit.setUpdateDrID(doctorId);
        visit.setUpdateDrName(doctorName);
        visit.setUpdateTime(new Date());
        String detailStr = jsonObject.getString("hypertensionDetail");
        if(!StringUtils.isEmpty(detailStr)){
            PHHypertensionDetail hypertensionDetail = JSON.parseObject(detailStr, PHHypertensionDetail.class);
            String str = hypertensionDetail.getSymptom();
            String symptom = StringUtils.replace(str, ",", "@#");
            hypertensionDetail.setSymptom(symptom);
            visit.setHypertensionDetail(hypertensionDetail);
        }
        Date nextVistDate = visit.getHypertensionDetail().getVisitDateNext();
        if(visitClass >0 && nextVistDate != null){
        	visit.setOperate("do");
        }
        String medicineStr = jsonObject.getString("hyperDetailMedicines");
        if(!StringUtils.isEmpty(medicineStr)){
            List<PHHypertensionDetailMedicine> hyperDetailMedicines = JSON.parseArray(medicineStr, PHHypertensionDetailMedicine.class);
            visit.setHyperDetailMedicines(hyperDetailMedicines);
        }
        result.setState(0);
        result.setContent(visit);
        return result;
    }
    
    /** 
     * @Title: validAddDiabetesParam 
     * @Description: 验证新增糖尿病参数 
     * @param doctorId
     * @param doctorName
     * @author liuxiaoqin
     * @createDate 2016-04-04
     * @param hypertensionID
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validAddDiabetesParam(Integer doctorId,String doctorName,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();
        PHDiabetes visit = new PHDiabetes();
        Integer memberID = jsonObject.getInteger("memberID");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberID【"+memberID+"】应为正整数！");
            logger.info("参数memberID【"+memberID+"】应为正整数！");
            return result;
        }
        String name = jsonObject.getString("name");
        visit.setName(name);
        int hasRecord = visitDao.hasVisitingDiabetesRecord(memberID);
        if(hasRecord > 0){
            result.setState(4);
            result.setMessage("会员【"+name+"】已有待随访的糖尿病记录,请勿重复添加！");
            logger.info("会员【"+name+"】已有待随访的糖尿病记录,请勿重复添加！");
            return result;
        }
        visit.setMemberID(memberID);
        String uniqueId = jsonObject.getString("uniqueId");
        visit.setUniqueId(uniqueId);
        Integer refCompany = jsonObject.getInteger("refCompany");
        visit.setRefCompany(refCompany);
        String refDataPk = jsonObject.getString("refDataPk");
        visit.setRefDataPk(refDataPk);
        String idCard = jsonObject.getString("idCard");
        visit.setIdCard(idCard);
        String date = jsonObject.getString("visitDate");
        if(!StringUtils.isEmpty(date)){
            Date visitDate = TimeUtil.parseDate(date);
            visit.setVisitDate(visitDate);
        }else{
        	String nowStr = TimeUtil.currentDate();
        	Date now = TimeUtil.parseDate(nowStr);
        	visit.setVisitDate(now);
        }
        String visitDrName = jsonObject.getString("visitDrName");
        if(StringUtils.isEmpty(visitDrName)){
        	visit.setVisitDrName(doctorName);
        }else{
        	visit.setVisitDrName(visitDrName);
        }
        //随访分类默认为0--待随访
        visit.setVisitClass(jsonObject.getInteger("visitClass"));
        visit.setCreateDrID(doctorId);
        visit.setIsDeleted(0);
        visit.setCreateDrName(doctorName);
        visit.setGetTime(new Date());
        String detailStr = jsonObject.getString("diabetesDetail");
        if(!StringUtils.isEmpty(detailStr)){
            PHDiabetesDetail diabetesDetail = JSON.parseObject(detailStr, PHDiabetesDetail.class);
            String str = diabetesDetail.getSymptom();
            String symptom = StringUtils.replace(str, ",", "@#");
            diabetesDetail.setSymptom(symptom);
            visit.setDiabetesDetail(diabetesDetail);
            Date nextVisitDate = diabetesDetail.getVisitDateNext();
            if(nextVisitDate != null){
            	visit.setUpdateDrID(doctorId);
            	visit.setUpdateDrName(doctorName);
            }
        }
        String medicineStr = jsonObject.getString("diabetesDetailMedicines");
        if(!StringUtils.isEmpty(medicineStr)){
            List<PHDiabetesDetailMedicine> diabetesDetailMedicines = JSON.parseArray(medicineStr, PHDiabetesDetailMedicine.class);
            visit.setDiabetesDetailMedicines(diabetesDetailMedicines);
        }
        result.setState(0);
        result.setContent(visit);
        return result;
    }
    
     /** 
     * @Title: validModifyDiabetesParam 
     * @Description: 验证修改糖尿病参数
     * @author liuxiaoqin
     * @createDate 2016-04-04 
     * @param doctorId
     * @param doctorName
     * @param hypertensionID
     * @param jsonObject
     * @return
     * @throws Exception    
     * @retrun ReturnResult
     */
    public ReturnResult validModifyDiabetesParam(Integer doctorId,String doctorName,Long diabetesId,JSONObject jsonObject)throws Exception{
        ReturnResult result = new ReturnResult();
        PHDiabetes visit = new PHDiabetes();
        Integer memberID = jsonObject.getInteger("memberID");
        if(memberID <= 0){
            result.setState(1);
            result.setMessage("参数memberID【"+memberID+"】应为正整数！");
            logger.info("参数memberID【"+memberID+"】应为正整数！");
            return result;
        }
        if(diabetesId <= 0){
            result.setState(1);
            result.setMessage("参数diabetesId【"+diabetesId+"】应为正整数！");
            logger.info("参数diabetesId【"+diabetesId+"】应为正整数！");
            return result;
        }
        visit.setDiabetesId(diabetesId);
        Integer nowVisitClass = visitDao.isOneVisitingDiabeteRecord(diabetesId);
        if(nowVisitClass == null){
        	result.setState(1);
            result.setMessage("该条糖尿病随访记录不存在，请重新核对。");
            logger.info("该条糖尿病随访记录不存在，请重新核对。");
            return result;
        }
        if(nowVisitClass > 0){
        	result.setState(1);
            result.setMessage("该条糖尿病随访已为【已随访】状态，不能修改，请重新选择。");
            logger.info("该条糖尿病随访已为【已随访】状态，不能修改，请重新选择。");
            return result;
        }
        visit.setMemberID(memberID);
        String uniqueId = jsonObject.getString("uniqueId");
        visit.setUniqueId(uniqueId);
        Integer refCompany = jsonObject.getInteger("refCompany");
        visit.setRefCompany(refCompany);
        String refDataPk = jsonObject.getString("refDataPk");
        visit.setRefDataPk(refDataPk);
        String idCard = jsonObject.getString("idCard");
        visit.setIdCard(idCard);
        String name = jsonObject.getString("name");
        visit.setName(name);
        String date = jsonObject.getString("visitDate");
        if(!StringUtils.isEmpty(date)){
            Date visitDate = TimeUtil.parseDate(date);
            visit.setVisitDate(visitDate);
        }
        // visit.setVisitDrName(doctorName);
        //TODO 修改医生之后？
        String visitDrName = jsonObject.getString("visitDrName");
        if(StringUtils.isEmpty(visitDrName)){
        	visit.setVisitDrName(doctorName);
        }else{
        	visit.setVisitDrName(visitDrName);
        }
        Integer visitClass = jsonObject.getInteger("visitClass");
        visit.setVisitClass(visitClass);
        Integer createDrID = jsonObject.getInteger("createDrID");
        if(createDrID == null || createDrID <=0){
        	result.setState(1);
            result.setMessage("参数createDrID【"+createDrID+"】应为正整数！");
            logger.info("参数createDrID【"+createDrID+"】应为正整数！");
            return result;
        }
        visit.setCreateDrID(createDrID);
        String createDrName = jsonObject.getString("createDrName");
        if(StringUtils.isEmpty(createDrName)){
        	result.setState(1);
            result.setMessage("参数createDrName【"+createDrName+"】不能为空！");
            logger.info("参数createDrName【"+createDrName+"】不能为空！");
            return result;
        }
        visit.setCreateDrName(createDrName);
        String createTime = jsonObject.getString("createTime");
        if(StringUtils.isEmpty(createTime)){
        	result.setState(1);
            result.setMessage("参数createTime【"+createTime+"】不能为空！");
            logger.info("参数createTime【"+createTime+"】不能为空！");
            return result;
        }
        Date newCreateTime = TimeUtil.parseDatetime(createTime);
        visit.setCreateTime(newCreateTime);
        String getTime = jsonObject.getString("getTime");
        if(StringUtils.isEmpty(getTime)){
        	result.setState(1);
            result.setMessage("参数getTime【"+getTime+"】不能为空！");
            logger.info("参数getTime【"+getTime+"】不能为空！");
            return result;
        }
        Date newGetTime = TimeUtil.parseDatetime(getTime);
        visit.setGetTime(newGetTime);
        visit.setUpdateDrID(doctorId);
        visit.setUpdateDrName(doctorName);
        visit.setUpdateTime(new Date());
        String detailStr = jsonObject.getString("diabetesDetail");
        if(!StringUtils.isEmpty(detailStr)){
            PHDiabetesDetail diabetesDetail = JSON.parseObject(detailStr, PHDiabetesDetail.class);
            String str = diabetesDetail.getSymptom();
            String symptom = StringUtils.replace(str, ",", "@#");
            diabetesDetail.setSymptom(symptom);
            visit.setDiabetesDetail(diabetesDetail);
        }
        Date nextVistDate = visit.getDiabetesDetail().getVisitDateNext();
        if(visitClass >0 && nextVistDate != null){
        	visit.setOperate("do");
        }
        String medicineStr = jsonObject.getString("diabetesDetailMedicines");
        if(!StringUtils.isEmpty(medicineStr)){
            List<PHDiabetesDetailMedicine> diabetesDetailMedicines = JSON.parseArray(medicineStr, PHDiabetesDetailMedicine.class);
            visit.setDiabetesDetailMedicines(diabetesDetailMedicines);
        }
        result.setState(0);
        result.setContent(visit);
        return result;
    }
    
    /** 
     * @Title: findSomeDatasByMemberId 
     * @Description: 根据会员id自动获取最新的体征，血糖，用药等数据
     * @param request
     * @author 2016-04-28
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public String findSomeDatasByMemberId(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
    	String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        Integer memberId = jsonObject.getInteger("memberId");
        String visitType = jsonObject.getString("visitType");
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        if(StringUtils.isEmpty(visitType)){
        	result.setState(1);
            result.setMessage("参数visitType【"+visitType+"】不能为空！");
            logger.info("参数visitType【"+visitType+"】不能为空！");
            return JSON.toJSONString(result);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("info", getInfos(memberId,visitType));
        map.put("medication", getMedication(memberId,visitType).get("medication"));
        if(map.isEmpty()){
        	 result.setState(3);
             result.setMessage("没有该会员的最新的体征，血糖，用药等数据");
             logger.info("没有该会员的最新的体征，血糖，用药等数据");
        }else{
        	 result.setState(0);
        	 result.setContent(map);
             result.setMessage("自动获取最新的体征，血糖，用药等数据成功");
             logger.info("自动获取最新的体征，血糖，用药等数据成功");
        }
    	return JSON.toJSONString(result);	
    }
    
    /** 
     * @Title: getInfos 
     * @Description: 根据会员id自动获取最新的体格，血糖
     * @param request
     * @author 2016-04-28
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public Map<String,Object> getInfos(Integer memberId,String visitType) throws Exception{
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 map = visitDao.findLifeAndPhysicalAndExamination(memberId,visitType);
    	 return map;
    }
    
    /** 
     * @Title: getMedication 
     * @Description: 根据会员id自动获取最新的用药等数据
     * @param request
     * @author 2016-04-28
     * @createDate 2016-03-17
     * @throws Exception    
     * @retrun String
     */
    public Map<String,Object> getMedication(Integer memberId,String visitType) throws Exception{
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 Map<String,Object> newMap = new HashMap<String, Object>();
    	 map = visitDao.findRecentMedication(memberId,visitType);
    	 if(!map.isEmpty()){
    		 String recent = (String)map.get("recent");
        	 Long id = (Long)map.get("id");
        	 if(recent.equals("E") && id != null){
        		 PHHealthExam exam = publicHealthService.queryPhysicalById(id);
        		 if(exam != null){
        			 List<PHHealthExamDetailMedicine> listE = exam.getHealthExamDetailMedicine();
        			 if(listE != null && listE.size()>0){
        				 newMap.put("medication", listE);
        			 }
        		 }
        	 }else if(recent.equals("H") && id != null){
        		 PHHypertension hypertension = publicHealthService.queryHypertensionDetail(id);
        		 if(hypertension != null){
        			 List<PHHypertensionDetailMedicine> listH = hypertension.getHyperDetailMedicines();
        			 if(listH != null && listH.size()>0){
        				 newMap.put("medication", listH);
        			 }
        		 }
        	 }else if(recent.equals("D") && id != null){
        		 PHDiabetes diabete = publicHealthService.queryDiabetesDetail(id);
        		 if(diabete != null){
        			 List<PHDiabetesDetailMedicine> listD = diabete.getDiabetesDetailMedicines();
        			 if(listD != null && listD.size()>0){
        				 newMap.put("medication", listD);
        			 }
        		 }
        	 }
    	 }
    	 return newMap;
    }
    
}
