package com.zkhk.constants;
/**
 * 常量信息
 * @author bit
 *
 */
public class Constants {
	
	public static final String SUCCESS="success";
	
	public static final String ERROR="error";
	
	//心电文件上传参数名称
	public static final String ECG_PARAM_FILE="ecg_file";
	
	//脉搏文件上传参数名称
	public static final String PPG_PARAM_FILE="ppg_file";
	
	//会员头像
	public static final String MEMBER_HENDIMG="headImg";
	
	//mongdb文件集合
	public static final String MDB_FILE="fs";
	
	//每测量一次血压(blood pressure)得1 分
    public static final int ONCE_MEASURE_BP_SCORE = 1;
    
    //每测量一次血糖(Blood glucose)得3 分
    public static final int ONCE_MEASURE_BG_SCORE = 3;
    
    //每测量一次三合一(three in one)得2 分
    public static final int ONCE_MEASURE_TIN_SCORE = 2;
    
    //每测量一次miniHolter得5 分
    public static final int ONCE_MEASURE_MNH_SCORE = 5;
    
    //回答一次问卷得3 分
    public static final int ONCE_ANSWER_QUESTION_SCORE = 3;
    
    //查看一次报告得2 分
    public static final int ONCE_CHECK_REPORT_SCORE = 2;
    
    //消息发送次数
    public static final int PUSH_COUNT = 3;
    
    //消息推送接口
    public static final String PUSH_URL="http://192.168.10.112:8082/Push/pushByMemberIds";
    
    //编码
    public static final String  PUSH_CHARSET="UTF-8";

    //账号被冻结提示
    public static final String  ACCOUNT_FREEZE="accountFreeze";
    
    //无效的参数
    public static final String  INVALID_PARAM="invalidparam";
        
    //seesion失效
    public static final String  INVALID_SESSION="invalidSession";
    
    /* 健康档案缓存名 */
    public static final String  HEALTH_FILE_CACHE = "healthFileCache";
    
    /* 健康档案缓存KEY */
    public static final String  HEALTH_FILE_KEY = "healthFileKey";
    
    /* 疾病字典缓存名 */
    public static final String  DIESASE_DICTIONARY_CACHE = "diseaseDictionaryCache";
    
    /* 疾病字典缓存KEY */
    public static final String  DIESASE_DICTIONARY_KEY = "diseaseDictionaryKey";
    
    /* 体检老年人认知功能--粗筛阳性值(CognitionScreenPositive)  */
    public static final int  EXAM_COGNITION_SCREEN_POSITIVE = 2;
    
    /* 体检老年人认知功能--粗筛阳性值描述(CognitionScreenPositiveDesc)  */
    public static final String  EXAM_COGNITION_SCREEN_POSITIVE_DESC = " ,简易智力状态检查, 总分";
    
    /* 体检老年人情感状态--粗筛阳性值(FeelingScreenPositive)  */
    public static final int  EXAM_FEELING_SCREEN_POSITIVE = 2;
    
    /* 体检老年人情感状态--粗筛阳性值描述(FeelingScreenPositiveDesc)  */
    public static final String  EXAM_FEELING_SCREEN_POSITIVE_DESC = " ,老年人抑郁评分检查, 总分";
    
    /* 中文字符串分割标志 znStringSeparate */
    public static final String  ZN_STRING_SEPARATE = " ，";
    
    /* 用户有效的session  */
    public static final String  USER_VALID_SESSION = "userValidSession";
    
    /* 用户无效的session  */
    public static final String  USER_INVALID_SESSION = "userInvalidSession";
    
    /* 用户session异常  */
    public static final String  USER_EXCEPTION_SESSION = "userExceptionSession";
    
    /* 左括号  leftBracket*/
    public static final String  LEFT_BRACKET = "(";
    
    /* 右括号 rightBracket */
    public static final String  RIGHT_BRACKET = ")";
    
    /* 登录异常提示loginException */
    public static final String  LOGIN_EXCEPTION = "loginException";
    
    /* 获取数据异常提示 getDataException*/
    public static final String  GET_DATA_EXCEPTION = "getDataException";
    
    /* 删除数据异常提示 deleteDataException*/
    public static final String  DELETE_DATA_EXCEPTION = "deleteDataException";
    
    /* 修改数据异常提示 modifyDataException*/
    public static final String  MODIFY_DATA_EXCEPTION = "modifyDataException";
    
    /* 添加数据异常提示 addDataException*/
    public static final String  ADD_DATA_EXCEPTION = "addDataException";
    
    /* 家族疾病史名称-高血压 hypertension*/
    public static final String  DISEASE_HYPERTENSION_1 = "高血压";
    
    /* 家族疾病史名称-糖尿病 DiabMell*/
    public static final String  DISEASE_DIABMELL_2 = "糖尿病";
    
    /* 家族疾病史名称-冠心病 CoronaryHeart*/
    public static final String  DISEASE_CORONARY_HEART_3 = "冠心病";
    
    /* 家族疾病史名称-脑卒中 cerebralApoplexy*/
    public static final String  DISEASE_CEREBRALAPOPLEXY_4 = "脑卒中";
    
    /* 家族疾病史名称-恶性肿瘤 therioma*/
    public static final String  DISEASE_THERIOMA_5 = "恶性肿瘤";
    
    /* 家族疾病史名称-慢性阻塞性肺疾病 LungDisease*/
    public static final String  DISEASE_LUNG_DISEASE_6 = "慢性阻塞性肺疾病";
    
    /* 家族疾病史名称-重型精神病majorPsychosis*/
    public static final String  DISEASE_MAJORPSYCHOSIS_7 = "重型精神病";
    
    /* 家族疾病史名称-肝炎 Hepatitis*/
    public static final String  DISEASE_HEPATITIS_8 = "肝炎";

    /* 家族疾病史名称-结核病 tuberculosis;*/
    public static final String  DISEASE_TUBERCULOSIS_9 = "结核病";

    /* 家族疾病史名称-其它 Tag*/
    public static final String  DISEASE_OTHER_10 = "其它";
    
    /* 运动时间段-sportPeriod0*/
    public static final String  SPORT_PERIOD_0 = "5:30-7:30";
    
    /* 运动时间段-sportPeriod1*/
    public static final String  SPORT_PERIOD_1 = "7:30-9:30";
    
    /* 运动时间段-sportPeriod2*/
    public static final String  SPORT_PERIOD_2 = "9:30-11:30";
    
    /* 运动时间段-sportPeriod3*/
    public static final String  SPORT_PERIOD_3 = "11:30-13:30";
    
    /* 运动时间段-sportPeriod4*/
    public static final String  SPORT_PERIOD_4 = "13:30-15:30";
    
    /* 运动时间段-sportPeriod5*/
    public static final String  SPORT_PERIOD_5 = "15:30-17:30";
    
    /* 运动时间段-sportPeriod6*/
    public static final String  SPORT_PERIOD_6 = "17:30-19:30";
    
    /* 运动时间段-sportPeriod7*/
    public static final String  SPORT_PERIOD_7 = "19:30-21:30";
    
    /* 运动时间段-sportPeriod8*/
    public static final String  SPORT_PERIOD_8 = "21:30-23:30";
    
    /* 当前页码    */
    public static final String PAGE_NOW = "pageNow";
    /* 分页数  */
    public static final String PAGE_SIZE = "pageSize";
    
    /* 中医体质辨识问卷-tcmQuestionnaire*/
    public static final String  TCM_QUESTIONNAIRE = "中医体质辨识问卷-1";
    
    /* 老年人生活自理能力问卷-agedLifeQuestionnaire*/

    public static final String  AGED_QUESTIONNAIRE = "老年人生活自理能力评估问卷-1";
    
    /* 老年人生活自理能力-可自理-agedHandle*/
    public static final String  AGED_HANDLE = "可自理";
    
    /* 老年人生活自理能力-轻度依赖-agedLightDependent*/
    public static final String  AGED_LIGHT_DEPENDENT = "轻度依赖";
    
    /* 老年人生活自理能力-中度依赖-agedMiddleDependent*/
    public static final String  AGED_MIDDLE_DEPENDENT = "中度依赖";
    
    /* 老年人生活自理能力-不能自理-agedNotHandle*/
    public static final String  AGED_NOT_HANDLE = "不能自理";
    
    /* 中医体质结果-是-tcmResultYes*/
    public static final String  TCM_RESULT_YES = "是";
    
    /* 中医体质结果-倾向是-tcmResultTendYes*/
    public static final String  TCM_RESULT_TEND_YES = "倾向是";
    
    /* 中医体质结果-否-tcmResultNo*/
    public static final String  TCM_RESULT_NO = "否";
    
    /* 会员血型-未知-bloodTypeUnknown*/
    public static final String  BLOOD_TYPE_UNKNOWN = "不详";

    /* 所有疾病ids*/
    public static final String  ALL_DISEASE_IDS = "1,2,3,4,5,6,7,8,9,10";
    
}
