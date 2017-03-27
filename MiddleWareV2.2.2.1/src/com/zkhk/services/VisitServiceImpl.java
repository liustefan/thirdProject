package com.zkhk.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hkbithealth.bean.ph.PHDiabetes;
import com.hkbithealth.bean.ph.PHDiabetesDetail;
import com.hkbithealth.bean.ph.PHHypertension;
import com.hkbithealth.bean.ph.PHHypertensionDetail;
import com.hkbithealth.bean.ph.PHOmem;
import com.hkbithealth.enmu.Terminal;
import com.hkbithealth.service.ph.PublicHealthService;
import com.zkhk.constants.Constants;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.VisitParam;
import com.zkhk.util.TimeUtil;
import com.zkhk.util.VisitUtil;

@Service("visitService")
public class VisitServiceImpl implements VisitService {

	private Logger logger = Logger.getLogger(VisitServiceImpl.class);
	
	private PublicHealthService publicHealthService = PublicHealthService.getInstance();
	
	/** 
     * @Title: findMyDiabetesVisitList 
     * @Description: 查询我的糖尿病随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findMyDiabetesVisitList(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        VisitParam param = JSON.parseObject(callValue.getParam(), VisitParam.class);
        int memberId = callValue.getMemberId();
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
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
        omem.setMemberid(memberId);
        Terminal phone = Terminal.PHONE;
        List<PHDiabetes> diabetesList = (List<PHDiabetes>) publicHealthService.queryDiabetesPage(omem,null, null, pageNow, pageSize, null, followUp,phone).getList();
        if(diabetesList != null && diabetesList.size() > 0){
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            for(PHDiabetes diabetes : diabetesList){
                Map<String,Object> map = new HashMap<String, Object>();
                String date = "";
                Date visitDate = diabetes.getVisitDate();
                if(visitDate != null){
                    date = TimeUtil.formatDate(visitDate);
                }
                String nextDate = "";
                Date nextVisitDate = diabetes.getVisitDate_Next();
                if(nextVisitDate != null){
                    nextDate = TimeUtil.formatDate(nextVisitDate);
                }
                map.put("visitDate", date);
                map.put("basicInfo", VisitUtil.getDiabeBasicInfo(diabetes));
                map.put("diabetesId", diabetes.getDiabetesId());
                map.put("nextVisitDate", nextDate);
                map.put("visitResult", diabetes.getVisitClassStr());
                mapList.add(map);
            }
            result.setState(0);
            result.setMessage("查询我的糖尿病随访列表成功");
            result.setContent(mapList);
            logger.info("查询我的糖尿病随访列表成功");
        }else{
            result.setState(3);
            result.setMessage("查询不到我的糖尿病随访列表");
            logger.info("查询不到我的糖尿病随访列表");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMyDiabetesVisitDetail 
     * @Description: 查询我的糖尿病随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyDiabetesVisitDetail(HttpServletRequest request) throws Exception{
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
            PHDiabetesDetail detail = visit.getDiabetesDetail();
            if(detail != null){
                map.put("visitWayStr",detail.getVisitWayStr());
                String symptom = detail.getSymptom();
                String symptomStr = "";
                if(!StringUtils.isEmpty(symptom) && !"null".equals(symptom)){
                	symptomStr = detail.getSymptomStr();
                }
//                String symptomDesc = detail.getSymptomDesc();
//                if(!StringUtils.isEmpty(symptomDesc)){
//                    symptomStr += Constants.ZN_STRING_SEPARATE + symptomDesc;
//                }
                map.put("symptomStr", symptomStr);
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
                map.put("drugAdverseReactionStr", drugAdverseReactionStr);
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
                result.setMessage("查询我的糖尿病随访明细成功");
                result.setContent(map);
                logger.info("查询我的糖尿病随访明细成功");
            }else{
                result.setState(3);
                result.setMessage("查询不到我的糖尿病随访明细");
                logger.info("查询不到我的糖尿病随访明细");
            }
        }else{
            result.setState(3);
            result.setMessage("查询不到我的糖尿病随访明细");
            logger.info("查询不到我的糖尿病随访明细");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMyHypertensionVisitList 
     * @Description: 查询我的高血压随访列表
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    @SuppressWarnings("unchecked")
    public String findMyHypertensionVisitList(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        VisitParam param = JSON.parseObject(callValue.getParam(), VisitParam.class);
        int memberId = callValue.getMemberId();
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
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
        omem.setMemberid(memberId);
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
                String nextDate = "";
                Date nextVisitDate = hypertension.getVisitDate_Next();
                if(nextVisitDate != null){
                    nextDate = TimeUtil.formatDate(nextVisitDate);
                }
                map.put("visitDate", date);
                map.put("basicInfo", VisitUtil.getHyperBasicInfo(hypertension));
                map.put("hypertensionID", hypertension.getHypertensionID());
                map.put("nextVisitDate", nextDate);
                map.put("visitResult", hypertension.getVisitClassStr());
                mapList.add(map);
            }
            result.setState(0);
            result.setMessage("查询我的高血压随访列表成功");
            result.setContent(mapList);
            logger.info("查询我的高血压随访列表成功");
        }else{
            result.setState(3);
            result.setMessage("查询不到我的高血压随访列表");
            logger.info("查询不到我的高血压随访列表");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findMyHypertensionVisitDetail 
     * @Description: 查询我的高血压随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws Exception    
     * @retrun String
     */
    public String findMyHypertensionVisitDetail(HttpServletRequest request) throws Exception{
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
            PHHypertensionDetail detail = visit.getHypertensionDetail();
            if(detail != null){
                map.put("visitWayStr",detail.getVisitWayStr());
                String symptom = detail.getSymptom();
                String symptomStr = "";
                if(!StringUtils.isEmpty(symptom) && !"null".equals(symptom)){
                	symptomStr = detail.getSymptomStr();
                }
//                String symptomDesc = detail.getSymptomDesc();
//                if(!StringUtils.isEmpty(symptomDesc)){
//                    symptomStr += Constants.ZN_STRING_SEPARATE + symptomDesc;
//                }
                map.put("symptomStr", symptomStr);
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
                result.setMessage("查询我的高血压随访明细成功");
                result.setContent(map);
                logger.info("查询我的高血压随访明细成功");
            }else{
                result.setState(3);
                result.setMessage("查询不到我的高血压随访明细");
                logger.info("查询不到我的高血压随访明细");
            }
        }else{
        }
        return JSON.toJSONString(result);
    }
    
}
