package com.zkhk.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hkbithealth.bean.ph.PHDictitem;
import com.hkbithealth.service.ph.PhDictionaryService;
import com.hkbithealth.util.CacheUtil;
import com.zkhk.constants.Constants;
import com.zkhk.dao.DataDicDao;
import com.zkhk.entity.ReturnResult;

@Service("dataDicService")
public class DataDicServiceImpl implements DataDicService {

	private Logger logger = Logger.getLogger(DataDicServiceImpl.class);
	
	private PhDictionaryService dictionaryService = PhDictionaryService.getInstance();
	
	@Resource(name = "dataDicDao")
	private DataDicDao dataDicDao;
	
	/** 
     * @Title: findAllDictionary 
     * @Description: 查询所有字典【健康体检和随访】
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun String
     */
    public String findAllDictionary(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        Map<String, List<PHDictitem>> map = dictionaryService.findAllSecond();
        Map<String, List<PHDictitem>> newMap = new HashMap<String, List<PHDictitem>>();
        if(map.size() > 0){
            newMap.put("hyperSymptom", map.get("高血压随访JOINT症状"));
            newMap.put("hyperPsychologicalAdjust", map.get("高血压随访_生活方式指导JOINT心理调整"));
            newMap.put("hyperVisitType", map.get("高血压随访JOINT随访方式"));
            newMap.put("hyperComplianceBehavior", map.get("高血压随访_生活方式指导JOINT遵医行为"));
            newMap.put("hyperSaltIntake", map.get("高血压随访JOINT摄盐情况(咸淡)"));
            newMap.put("hyperMedicationCompliance", map.get("高血压随访JOINT服药依从性"));
            newMap.put("hyperAdverseDrugReactions", map.get("高血压随访JOINT药物不良反应"));
            newMap.put("hyperVisitClass", map.get("高血压随访JOINT此次随访分类"));
            newMap.put("diabetesVisitType", map.get("2型糖尿病随访JOINT随访方式"));
            newMap.put("diabetesHypoglycemicReaction", map.get("2型糖尿病随访JOINT低血糖反应"));
            newMap.put("diabetesSymptom", map.get("2型糖尿病随访JOINT症状"));
            newMap.put("diabetesPsychologicalAdjust", map.get("2型糖尿病随访_生活方式指导JOINT心理调整"));
            newMap.put("diabetesVisitClass", map.get("2型糖尿病随访JOINT此次随访分类"));
            newMap.put("diabetesMedicationCompliance", map.get("2型糖尿病随访JOINT服药依从性"));
            newMap.put("diabetesAdverseDrugReactions", map.get("2型糖尿病随访JOINT药物不良反应"));
            newMap.put("diabetesDorsalArterialPulse", map.get("2型糖尿病随访_体征JOINT足背动脉搏动"));
            newMap.put("diabetesComplianceBehavior", map.get("2型糖尿病随访_生活方式指导JOINT遵医行为"));
            result.setState(0);
            result.setMessage("查询所有字典【随访】成功");
            result.setContent(newMap);
            logger.info("查询所有字典【随访】成功");
        }else{
            result.setState(3);
            result.setMessage("查询不到所有字典【随访】");
            logger.info("查询不到所有字典【随访】");
        }
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findDiseaseDictionary 
     * @Description: 查询疾病史字典
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-22
     * @throws IOException    
     * @retrun String
     */
    public String findDiseaseDictionary(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        Object dictionary = CacheUtil.getCache(Constants.DIESASE_DICTIONARY_CACHE, Constants.DIESASE_DICTIONARY_KEY);
        if(dictionary == null){
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String,Object>> list = dataDicDao.findDiseaseDictionary();
            if(list.size() > 0){
                map.put("diseaseDictionary", list);
                CacheUtil.setCache(Constants.DIESASE_DICTIONARY_CACHE, Constants.DIESASE_DICTIONARY_KEY, map);
                result.setState(0);
                result.setMessage("查询疾病史字典成功");
                result.setContent(map);
                logger.info("查询疾病史字典成功");
            }else{
                result.setState(3);
                result.setMessage("查询不到疾病史字典");
                logger.info("查询不到疾病史字典");
            }
        }else{
            result.setState(0);
            result.setMessage("查询疾病史字典成功");
            result.setContent(dictionary);
            logger.info("查询疾病史字典成功");
        }
        
        return JSON.toJSONString(result);
    }
    
    /** 
     * @Title: findHealthExamDictionary 
     * @Description: 查询所有字典【健康体检】
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-23
     * @throws IOException    
     * @retrun String
     */
    public String findHealthExamDictionary(HttpServletRequest request) throws Exception{
    	ReturnResult result = new ReturnResult();
        Map<String, List<PHDictitem>> map = dictionaryService.findAllSecond();
        Map<String, List<PHDictitem>> newMap = new HashMap<String, List<PHDictitem>>();
        if(map.size() > 0){
            newMap.put("hcGynaeUterine", map.get("健康体检_查体_妇科JOINT宫体"));
            newMap.put("hcGynaeCervical", map.get("健康体检_查体_妇科JOINT宫颈"));
            newMap.put("hcGynaeVagina", map.get("健康体检_查体_妇科JOINT阴道"));
            newMap.put("hcGynaeEnclosure", map.get("健康体检_查体_妇科JOINT附件"));
            newMap.put("hcGynaeVulva", map.get("健康体检_查体_妇科JOINT外阴"));
            newMap.put("hcAnusDre", map.get("健康体检_查体JOINT肛门指诊"));
            newMap.put("hcDorsalArterialPulse", map.get("健康体检_查体JOINT足背动脉搏动"));
            newMap.put("hcBreast", map.get("健康体检_查体JOINT乳腺"));
            newMap.put("hcSkin", map.get("健康体检_查体JOINT皮肤"));
            newMap.put("hcSclera", map.get("健康体检_查体JOINT巩膜"));
            newMap.put("hcEyeground", map.get("健康体检_查体JOINT眼底"));
            newMap.put("hcLymph", map.get("健康体检_查体JOINT淋巴结"));
            newMap.put("hcLowLimbEdema", map.get("健康体检_查体JOINT下肢水肿"));
            newMap.put("hcAbdomenBlock", map.get("健康体检_查体_腹部JOINT包块"));
            newMap.put("hcAbdomenMoveSonant", map.get("健康体检_查体_腹部JOINT移动浊音"));
            newMap.put("hcAbdomenPain", map.get("健康体检_查体_腹部JOINT压痛"));
            newMap.put("hcAbdomenSplenomegaly", map.get("健康体检_查体_腹部JOINT脾大"));
            newMap.put("hcAbdomenHepatomegaly", map.get("健康体检_查体_腹部JOINT肝大"));
            newMap.put("hcLungRales", map.get("健康体检_查体_肺JOINT罗音"));
            newMap.put("hcLungBarrelChest", map.get("健康体检_查体_肺JOINT桶状胸"));
            newMap.put("hcLungBreathSounds", map.get("健康体检_查体_肺JOINT呼吸音"));
            newMap.put("hcHeartRhythm", map.get("健康体检_查体_心脏JOINT心律"));
            newMap.put("hcHeartMurmur", map.get("健康体检_查体_心脏JOINT杂音"));
            
            newMap.put("hcDiseaseHeart", map.get("健康体检_现存主要健康问题JOINT心脏疾病"));
            newMap.put("hcDiseaseEyePart", map.get("健康体检_现存主要健康问题JOINT眼部疾病"));
            newMap.put("hcDiseaseCerebralVessel", map.get("健康体检_现存主要健康问题JOINT脑血管疾病"));
            newMap.put("hcDiseaseNervousSystem", map.get("健康体检_现存主要健康问题JOINT神经系统疾病"));
            newMap.put("hcDiseaseBloodPipe", map.get("健康体检_现存主要健康问题JOINT血管疾病"));
            newMap.put("hcDiseaseKidney", map.get("健康体检_现存主要健康问题JOINT肾脏疾病"));
            newMap.put("hcDiseaseOtherSystem", map.get("健康体检_现存主要健康问题JOINT其他系统疾病"));
            
            newMap.put("hcLifedDustGuard", map.get("健康体检_生活方式_职业病危害因素接触史JOINT粉尘防护措施"));
            newMap.put("hcLifePhysicalGuard", map.get("健康体检_生活方式_职业病危害因素接触史JOINT物理因素防护措施"));
            newMap.put("hcLifeRadiogenGuard", map.get("健康体检_生活方式_职业病危害因素接触史JOINT放射物质防护措施"));
            newMap.put("hcLifeChemicalGuard", map.get("健康体检_生活方式_职业病危害因素接触史JOINT化学物质防护措施"));
            newMap.put("hcLifeToxicOtherGuard", map.get("健康体检_生活方式_职业病危害因素接触史JOINT其他防护措施"));
            newMap.put("hcLifeOccupation", map.get("健康体检_生活方式JOINT职业病危害因素接触史"));
            newMap.put("hcLifeSmokingState", map.get("健康体检_生活方式_吸烟情况JOINT吸烟状况"));
            newMap.put("hcLifeSportFrequency", map.get("健康体检_生活方式_体育锻炼JOINT锻炼频率"));
            newMap.put("hcLifeEatingHabits", map.get("健康体检_生活方式JOINT饮食习惯"));
            newMap.put("hcLifeDrinkType", map.get("健康体检_生活方式_饮酒情况JOINT饮酒种类"));
            newMap.put("hcLifeIsAbstinence", map.get("健康体检_生活方式_饮酒情况JOINT是否戒酒"));
            newMap.put("hcLifeIsTemulenceLastYear", map.get("健康体检_生活方式_饮酒情况JOINT近一年内是否曾醉酒"));
            newMap.put("hcLifeDrinkFrequency", map.get("健康体检_生活方式_饮酒情况JOINT饮酒频率"));
            
            newMap.put("hcAssistFecalOccultBlood", map.get("健康体检_辅助检查JOINT大便潜血"));
            newMap.put("hcAssistBUltrasonic", map.get("健康体检_辅助检查JOINTB超"));
            newMap.put("hcAssistCardiogram", map.get("健康体检_辅助检查JOINT心电图"));
            newMap.put("hcAssistCervicalSmear", map.get("健康体检_辅助检查JOINT宫颈涂片"));
            newMap.put("hcAssistX_RAY", map.get("健康体检_辅助检查JOINT胸部X线片"));
            newMap.put("hcAssistHBSAG", map.get("健康体检_辅助检查JOINT乙肝表面抗原"));
            
            newMap.put("hcOrganLips", map.get("健康体检_脏器功能_口腔JOINT口唇"));
            newMap.put("hcOrganDentition", map.get("健康体检_脏器功能_口腔JOINT齿列"));
            newMap.put("hcOrganThroat", map.get("健康体检_脏器功能_口腔JOINT咽部"));
            newMap.put("hcOrganHearing", map.get("健康体检_脏器功能JOINT听力"));
            newMap.put("hcOrganMovement", map.get("健康体检_脏器功能JOINT运动功能"));
            
            newMap.put("hcBasicAgedHealthEvaluate", map.get("健康体检_一般状况JOINT老年人健康状态自我评估"));
            newMap.put("hcBasicAgedLifeEvaluate", map.get("健康体检_一般状况JOINT老年人生活自理能力自我评估"));
            newMap.put("hcBasicAgedFeeling", map.get("健康体检_一般状况JOINT老年人情感状态"));
            newMap.put("hcBasicAgedCognition", map.get("健康体检_一般状况JOINT老年人认知功能"));
            
            newMap.put("hcHealthEvaluate", map.get("健康体检JOINT健康评价"));
            newMap.put("hcSymptom", map.get("健康体检JOINT症状"));
            newMap.put("hcHealthGuide", map.get("健康体检JOINT健康指导"));
            newMap.put("hcRiskFactor", map.get("健康体检JOINT危险因素控制"));
            newMap.put("hcDrugCompliance", map.get("健康体检_主要用药情况JOINT服药依从性"));
            
            newMap.put("hc_tCM_XTZ_Guide", map.get("健康体检_中医体质辨识_血瘀质JOINT健康指导"));
            newMap.put("hc_tCM_QXZ_Guide", map.get("健康体检_中医体质辨识_气虚质JOINT健康指导"));
            newMap.put("hc_tCM_TSZ_Guide", map.get("健康体检_中医体质辨识_痰湿质JOINT健康指导"));
            newMap.put("hc_tCM_YXZ_Guide", map.get("健康体检_中医体质辨识_阳虚质JOINT健康指导"));
            newMap.put("hc_tCM_TBZ_Guide", map.get("健康体检_中医体质辨识_特秉质JOINT健康指导"));
            newMap.put("hc_tCM_QYZ_Guide", map.get("健康体检_中医体质辨识_气郁质JOINT健康指导"));
            newMap.put("hc_tCM_SRZ_Guide", map.get("健康体检_中医体质辨识_湿热质JOINT健康指导"));
            newMap.put("hc_tCM_PHZ_Guide", map.get("健康体检_中医体质辨识_平和质JOINT健康指导"));
            newMap.put("hc_tCM_YIXZ_Guide", map.get("健康体检_中医体质辨识_阴虚质JOINT健康指导"));
            
            newMap.put("hc_tCM_TBZ", map.get("健康体检_中医体质辨识_特秉质JOINT辨识结果"));
            newMap.put("hc_tCM_SRZ", map.get("健康体检_中医体质辨识_湿热质JOINT辨识结果"));
            newMap.put("hc_tCM_TSZ", map.get("健康体检_中医体质辨识_痰湿质JOINT辨识结果"));
            newMap.put("hc_tCM_YIXZ", map.get("健康体检_中医体质辨识_阴虚质JOINT辨识结果"));
            newMap.put("hc_tCM_XTZ", map.get("健康体检_中医体质辨识_血瘀质JOINT辨识结果"));
            newMap.put("hc_tCM_QYZ", map.get("健康体检_中医体质辨识_气郁质JOINT辨识结果"));
            newMap.put("hc_tCM_YXZ", map.get("健康体检_中医体质辨识_阳虚质JOINT辨识结果"));
            newMap.put("hc_tCM_PHZ", map.get("健康体检_中医体质辨识_平和质JOINT辨识结果"));
            newMap.put("hc_tCM_QXZ", map.get("健康体检_中医体质辨识_气虚质JOINT辨识结果"));
            
            result.setState(0);
            result.setMessage("查询所有字典【健康体检】成功");
            result.setContent(newMap);
            logger.info("查询所有字典【健康体检】成功");
        }else{
            result.setState(3);
            result.setMessage("查询不到所有字典【健康体检】");
            logger.info("查询不到所有字典【健康体检】");
        }
        return JSON.toJSONString(result);
    }
    
}
