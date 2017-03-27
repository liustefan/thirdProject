package com.zkhk.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkhk.constants.Constants;
import com.zkhk.dao.AnswerDao;
import com.zkhk.dao.MeasureDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Mem8;
import com.zkhk.entity.Message;
import com.zkhk.entity.Mfq1;
import com.zkhk.entity.Mfq11;
import com.zkhk.entity.Ocam;
import com.zkhk.entity.Omfq;
import com.zkhk.entity.Oopt;
import com.zkhk.entity.Ouai;
import com.zkhk.entity.AnswerParam;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.ReturnValue;
import com.zkhk.entity.Uai21;
import com.zkhk.entity.UploadAnswer;
import com.zkhk.util.TimeUtil;


@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    private Logger logger=Logger.getLogger(AnswerServiceImpl.class);
    
    @Resource(name="answerDao")
    private AnswerDao answerDao;
      
    @Resource(name = "measureDao")
  	private MeasureDao measureDao;
    
    @Resource(name = "pushMessageDao")
	private PushMessageDao messageDao;

	public String findOuaiByParam(HttpServletRequest request) throws Exception{
		    ReturnResult re = new ReturnResult();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            AnswerParam param=JSON.parseObject(callValue.getParam(), AnswerParam.class);
			// 更新同行状态信息
			List<Ouai> ouais=answerDao.findOuaiByParam(param);
			if (ouais.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(ouais);
				logger.info("查询会员答卷信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员的答卷信息");
				logger.info("查询不到该会员的答卷信息");
			}
			if(callValue.getMemberId()!=param.getMemberId()){
				//标记读取了关注用户的单份答卷信息
				messageDao.updateMarkTagByCreateId(param.getMemberId(), callValue.getMemberId(), 3);
			}
			
		    return JSON.toJSONString(re);
	}

	public String findOcamByParam(HttpServletRequest request)throws Exception {
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            AnswerParam param=JSON.parseObject(callValue.getParam(), AnswerParam.class);
			// 更新同行状态信息
			List<Ocam> ocams=answerDao.findOcamByParam(param);
			if (ocams.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(ocams));
				logger.info("查询会员组合答卷信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员的组合答卷信息");
				logger.info("查询不到该会员的组合答卷信息");
			}
			if(callValue.getMemberId()!=param.getMemberId()){
				//标记读取了关注用户的单份答卷信息
				messageDao.updateMarkTagByCreateId(param.getMemberId(), callValue.getMemberId(), 4);
			}
			
		return JSON.toJSONString(re);
	}

	public String findOuaiByParam2(HttpServletRequest request) throws Exception{
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            if(callValue.getParam()==null||callValue.getParam().equals("")){
            	re.setState(3);
				re.setMessage("传递参数不合规定");
				return JSON.toJSONString(re);
            }
            JSONObject object=JSON.parseObject(callValue.getParam());
            String param=object.getString("relation");
			// 更新同行状态信息
			List<Ouai> ouais = answerDao.findOuaiByParam2(param);
			List<Ouai> ouaisList = new ArrayList<Ouai>();
			if (ouais.size()>0) {
			    for(Ouai newOuai : ouais)
	            {
	                String hasCheck = newOuai.getQustTag();
	                String ansMode = newOuai.getAnsMode();
	                if((hasCheck.equals("T") && ansMode.equals("1")) || (hasCheck.equals("C") && ansMode.equals("2")) || (hasCheck.equals("C") && ansMode.equals("1"))
	                        || (hasCheck.equals("F") && ansMode.equals("1")) || (hasCheck.equals("B") && ansMode.equals("1"))){
	                    ouaisList.add(newOuai);
	                }
	            }
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(ouaisList));
				logger.info("查询会员答卷信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员的答卷信息");
				logger.info("查询不到该会员的答卷信息");
			}

	
		return JSON.toJSONString(re);
	}

	public String findOmfqByParam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();

			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            if(callValue.getParam()==null||callValue.getParam().equals("")){
            	re.setState(3);
				re.setMessage("传递参数不合规定");
				return JSON.toJSONString(re);
            }
            JSONObject object=JSON.parseObject(callValue.getParam());
            int param=object.getIntValue("qustId");
			// 获取问卷信息
			Omfq omfq=answerDao.findOmfqByParam(param);
			if (omfq!=null) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(omfq));
				logger.info("查询问卷信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到问卷信息");
				logger.info("查询不到问卷信息");
			}
		return JSON.toJSONString(re);
	}

	public String findResultByParam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
		try {
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
           if(callValue.getParam()==null||callValue.getParam().equals("")){
        		re.setState(3);
				re.setMessage("传递参数有错误");
				return JSON.toJSONString(re);
           }
           JSONObject object=JSON.parseObject(callValue.getParam());
           int ansNumber=object.getIntValue("ansNumber");
			// 查询问卷答案
			List<Mfq11> mfq11s=answerDao.findResultByParam(ansNumber);
			if (mfq11s.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(mfq11s));
				logger.info("查询问卷答案信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该问卷答案信息");
				logger.info("查询不到该问卷答案信息");
			}

		} catch (Exception e) {
			re.setState(2);
			re.setMessage("问卷答案信息查询运行异常");
			logger.error("问卷答案信息查询运行异常");
		}
		return JSON.toJSONString(re);
	}

	
	public String saveResultByParam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
            if(callValue.getParam() == null || callValue.getParam().equals("")){
            	re.setState(3);
				re.setMessage("传递参数不合规定");
				return JSON.toJSONString(re);
            }
            List<Uai21> list = new ArrayList<Uai21>(); 
            String  qustTag = "T";
            
            if("v1.0".equals(callValue.getVersion())){
            	list = (List<Uai21>) JSON.parseArray(callValue.getParam(), Uai21.class);          	
            }else{
            	UploadAnswer uploadAnswer = (UploadAnswer) JSON.parseObject(callValue.getParam(), UploadAnswer.class);
            	list = (List<Uai21>) uploadAnswer.getUai21List();
            	qustTag = uploadAnswer.getQustTag();
            }
            
            /*  会员单份答卷先清除原来暂存答案  begin  */
            int ansNumber = list.get(0).getAnsNumber();
            List<Uai21> oldUai21List = answerDao.findMemSingleAnswerDetail(ansNumber);
            if(oldUai21List.size() > 0){
                answerDao.deleteMemSingleQustFristDraft(ansNumber);
            }
            /*  会员单份答卷先清除原来暂存答案  end  */
            
            //保存会员和答卷 问卷的对应关系
         	answerDao.saveUai21ByParam(list,qustTag);
         	
         	Uai21 uai21 = list.get(0);
         	//默认为单份答卷
         	int type = 3;
         	
         	//判断是否为组合答卷
         	int combAnsId = uai21.getCombAnsId();
         	//如果是提交
         	if(qustTag.equals("T")){
                
                if(combAnsId > 0){
                    //获取组合答卷总分数
                    double combTotalScore = answerDao.getScore(list);
                    //获取系统得出结论
                    String combResult = answerDao.getResultByQustIdAndScore(uai21.getQustId(),combTotalScore);
         
                    //保存总分和分析结果
                    answerDao.saveOuai4(uai21.getAnsNumber(),combTotalScore,combResult);
                    
                    //查看该组合答卷是否已经完成
                    boolean isAllAnswer = answerDao.findisFinish(uai21.getRelation());
                    //3代表作答中
                    String combTag = "3";
                    if(isAllAnswer){
                        combTag = "1";
                        answerDao.updateOcamStatusById(combAnsId,combTag);
                        //获取组合问卷问卷信息
                        int combQustId = uai21.getCombQustId();
                        if(combQustId <= 0)
                        {
                            re.setState(1);
                            re.setMessage("组合答卷的参数组合问卷id为空！");        
                            logger.info("组合答卷的参数组合问卷id为空！！");
                            return JSON.toJSONString(re);
                        }
                        Oopt oopt = answerDao.findOoptByCombQustId(combQustId);
                        //保存到oasr表中
                        answerDao.submitCombAnswer(combAnsId, oopt.getId(), oopt.getOptName(), callValue.getMemberId());
                        //answerDao.pro_fromOCAMinsOASR(combAnsId,callValue.getMemberId(),createDate,combQust.getOrgId(),combQust.getOptId());
                        re.setState(0);
                        re.setMessage("成功");        
                        logger.info("保存组合答卷信息成功！");
                    }else{
                        answerDao.updateOcamStatusById(combAnsId,combTag);
                    }
                    type = 4;
                }else{
                    //获取总分数
                    double totalScore = answerDao.getScore(list);
                    //获取系统得出结论
                    String result = answerDao.getResultByQustIdAndScore(uai21.getQustId(),totalScore);
                    //保存总分和分析结果
                    answerDao.saveOuai4(list.get(0).getAnsNumber(),totalScore,result);
                    int qustId = uai21.getQustId();
                    Oopt oopt = answerDao.findOoptBySingleQustId(qustId);
                    //保存到oasr表中
                    answerDao.submitSingleAnswer(ansNumber, oopt.getId(), oopt.getOptName(), callValue.getMemberId(),"normal");
                    
                    //获取单份问卷信息
                    //Omfq omfq = answerDao.findOmfqByParam2(uai21.getQustId());
                    //获取单份问卷创建时间
                    //Date date = answerDao.findQustCreateTimeByQustId(uai21.getQustId());
                    //answerDao.pro_fromOUAIinsOASR(omfq.getOrgId(),uai21.getAnsNumber(),callValue.getMemberId(),omfq.getOptId(),date);
                    re.setState(0);
                    re.setMessage("成功");        
                    logger.info("保存单份答卷信息成功！");
                } 
         	}
         	//暂存数据
         	else{
         	    if(combAnsId > 0){
         	       //3代表作答中
                   String newCombTag = "3";
         	       answerDao.updateOcamStatusById(combAnsId,newCombTag);
         	    }
         	    re.setState(0);
                re.setMessage("成功");        
                logger.info("保存答卷为暂存状态成功！");
         	}
         	
			//会员回答一次问卷得3 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(callValue.getMemberId());
			mem8.setScore(Constants.ONCE_ANSWER_QUESTION_SCORE);
			String createTime= TimeUtil.formatDatetime2(new Date(System.currentTimeMillis()));
			mem8.setUploadTime(createTime);
			measureDao.addMemActivityScore(mem8);
			/**推送消息----------------------开始**/
			Message pushMs = messageDao.getPushMessageByMemberId(callValue.getMemberId(),type);	
			pushMs.setCreateTime(createTime);
			messageDao.add(pushMs);
			/**推送消息----------------------结束**/
		    return JSON.toJSONString(re);
	}
	
	/**
	  * @Description 查询会员的单份答卷：(已答和未答)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	public String findMemSingleAnswer(HttpServletRequest request)throws Exception
	{
	    ReturnValue re = new ReturnValue();
	    String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        AnswerParam param=JSON.parseObject(callValue.getParam(), AnswerParam.class);
        int memberId = param.getMemberId();
        if(memberId <= 0 )
        {
            re.setState(1);
            re.setMessage("会员id为空！");
            logger.info("会员id为空！");
            return JSON.toJSONString(re);
        }
        String hasAnswerd = param.getHasAnswerd();
        if(StringUtils.isEmpty(hasAnswerd))
        {
            re.setState(1);
            re.setMessage("参数是否审核(hasAnswerd)为空!");
            logger.info("参数是否审核(hasAnswerd)为空!");
            return JSON.toJSONString(re);
        }
        int page = param.getPage();
        if(page <= 0)
        {
            re.setState(1);
            re.setMessage("参数page为0!");
            logger.info("参数page为0!");
            return JSON.toJSONString(re);
        }
        int count = param.getCount();
        if(count <= 0)
        {
            re.setState(1);
            re.setMessage("参数count为空!");
            logger.info("参数count为空!");
            return JSON.toJSONString(re);
        }
        List<Ouai> ouais = answerDao.findMemSingleAnswer(param);
        List<Ouai> ouaisList = new ArrayList<Ouai>();
        if (ouais.size()>0) {
            for(Ouai newOuai : ouais)
            {
                String hasCheck = newOuai.getQustTag();
                String ansMode = newOuai.getAnsMode();
                if((hasCheck.equals("T") && ansMode.equals("1")) || (hasCheck.equals("C") && ansMode.equals("2")) || (hasCheck.equals("C") && ansMode.equals("1"))
                        || (hasCheck.equals("F") && ansMode.equals("1")) || (hasCheck.equals("B") && ansMode.equals("1"))){
                    ouaisList.add(newOuai);
                }
            }
            re.setState(0);
            re.setMessage("成功");
            re.setContent(JSON.toJSONString(ouaisList));
            logger.info("查询会员单份答卷信息成功");
        }
        else 
        {
            re.setState(1);
            re.setMessage("查询不到该会员的单份答卷信息！");
            logger.info("查询不到该会员的单份答卷信息！");
        }
	    return JSON.toJSONString(re);
	}
	
	/**
     * @Description 获取会员单份问卷详细信息(会员未作答)
     * @author liuxiaoqin
     * @CreateDate 2015年10月12日
   */
	public String findMemSingleQuestionInfo(HttpServletRequest request)throws Exception
	{
	    ReturnValue re = new ReturnValue();
	    String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        AnswerParam param = JSON.parseObject(callValue.getParam(), AnswerParam.class);
        //问卷id
        int qustId = param.getQustId();
        if(qustId <= 0)
        {
            re.setState(1);
            re.setMessage("问卷参数问卷id为空！");
            logger.info("问卷参数问卷id为空！");
            return JSON.toJSONString(re);
        }
        //获取问卷所有题目(list)
        List<Mfq1> titleList = answerDao.findMemSingleQustAllTitle(qustId);
        if(titleList.size() > 0)
        {
            for(Mfq1 newMfq1 :titleList)
            {
                //每个题目的选项 
                int problemId = newMfq1.getId();
                List<Mfq11> optionList = answerDao.findMemSingleQustTitleOption(qustId,problemId);
                newMfq1.setAnserList(optionList);
            }
            re.setState(0);
            re.setMessage("获取会员问卷成功！");
            re.setContent(JSON.toJSONString(titleList));
            logger.info("获取会员问卷成功！");
        }
        
	    return JSON.toJSONString(re);
	}
	
	/**
	  * @Description 获取会员单份答卷总得分，结论和审核建议(已审核)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	public String findMemSingleAnswerSummary(HttpServletRequest request)throws Exception
	{
	    ReturnResult re = new ReturnResult();
        String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        AnswerParam param = JSON.parseObject(callValue.getParam(), AnswerParam.class);
        //获取审核结果以及建议
        //会员已审核答卷id
        int ouaiId = param.getAnsNumber();
        if(ouaiId <= 0)
        {
            re.setState(1);
            re.setMessage("答卷参数答卷id为空！");
            logger.info("答卷参数答卷id为空！");
            return JSON.toJSONString(re);
        }
        Ouai ouai = answerDao.findMemSingleAnswerSummary(ouaiId);
        if(ouai != null)
        {
            //获取会员答卷总分与系统结论
            Ouai newOuai = answerDao.getSingleAnsScoreAndConclusion(ouaiId);
            if(newOuai != null)
            {
                ouai.setScore(newOuai.getScore());
                ouai.setConclusion(newOuai.getConclusion());
            }
            String qustTag = ouai.getQustTag();
            //获取医生诊断结果，建议，审核时间。
            if(qustTag.equals("C")){
                Ouai adviceOuai = answerDao.getSingleAnsDocAdviceAndTime(ouaiId,param);
                if(adviceOuai != null)
                {
                    ouai.setAuditTime(adviceOuai.getAuditTime());
                    ouai.setAuditDesc(adviceOuai.getAuditDesc());
                    ouai.setDiagnosis(adviceOuai.getDiagnosis());
                    ouai.setAuditDocName(adviceOuai.getAuditDocName());
                    ouai.setAuditDocSignature(adviceOuai.getAuditDocSignature());
                }
            }
            
            //更新状态为已读("0")
            int readStatus = ouai.getReadStatus();
            if(readStatus == 1 && qustTag.equals("C")){
                int count = answerDao.updateMemSingleAnswerHasApproved(ouaiId);
                if(count <= 0){
                    re.setState(1);
                    re.setMessage("更新已审核的会员单份答卷为已读失败！");
                    logger.info("更新已审核的会员单份答卷为已读失败！");
                }
            }
            re.setState(0);
            re.setMessage("成功");
            re.setContent(ouai);
            logger.info("获取会员单份答卷总得分，结论和审核建议(已审核)成功！");
        }else{
            re.setState(1);
            re.setMessage("获取会员单份答卷总得分，结论和审核建议(已审核)失败！");
            logger.info("获取会员单份答卷总得分，结论和审核建议(已审核)失败！");
        }
	    return JSON.toJSONString(re);
	}
	
	/**
	  * @Description 获取会员已答单份答卷的答案
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	public String findSingleAnswerHasSubmit(HttpServletRequest request)throws Exception
	{
	    ReturnValue re = new ReturnValue();
	    String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        AnswerParam param = JSON.parseObject(callValue.getParam(), AnswerParam.class);
        int ouaiId = param.getAnsNumber();
        if(ouaiId <= 0)
        {
            re.setState(1);
            re.setMessage("单份答卷参数答卷id为空！");
            logger.info("单份答卷参数答卷id为空！");
            return JSON.toJSONString(re);
        }
        //获取答卷内容(list)
        List<Uai21> list = answerDao.findMemSingleAnswerDetail(ouaiId);
        re.setState(0);
        re.setMessage("成功！");
        re.setContent(JSON.toJSONString(list));
        logger.info("获取会员已答单份答卷的答案成功！");
        return JSON.toJSONString(re);
	}
	
	/**
     * @Description 更新已审核的会员答卷为已读("0")
     * @author liuxiaoqin
     * @CreateDate 2015年10月13日
   */
   public String updateMemSingleAnswerHasApproved(HttpServletRequest request) throws Exception{
       ReturnValue re = new ReturnValue();
       String params = request.getParameter("params");
       CallValue callValue = JSON.parseObject(params, CallValue.class);
       JSONObject param = JSON.parseObject(callValue.getParam());
       String ansNumber = param.getString("ansNumber");
       if(StringUtils.isEmpty(ansNumber)){
           re.setState(1);
           re.setMessage("参数答卷id：ansNumber为空！");
           logger.info("参数答卷id：ansNumber为空！");
           return JSON.toJSONString(re);
       }
       Integer newAnsNumber = Integer.valueOf(ansNumber);
       if(newAnsNumber <= 0){
           re.setState(1);
           re.setMessage("参数答卷ansNumber应为正整数！");
           logger.info("参数答卷ansNumber应为正整数！");
           return JSON.toJSONString(re);
       }
       int count = answerDao.updateMemSingleAnswerHasApproved(newAnsNumber);
       if(count <= 0){
           re.setState(1);
           re.setMessage("更新已审核的会员单份答卷为已读失败！");
           logger.info("更新已审核的会员单份答卷为已读失败！");
           return JSON.toJSONString(re);
       }else{
           re.setState(0);
           re.setMessage("成功！");
           logger.info("更新已审核的会员单份答卷为已读成功！");
       }
       return JSON.toJSONString(re);
   }
   
}
