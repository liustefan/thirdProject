package com.zkhk.contoller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.zkhk.constants.Constants;
import com.zkhk.entity.ReturnResult;
import com.zkhk.services.DocHealthExamService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * @ClassName:     DocHealthExamController.java 
 * @Description:   医生操作健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月17日 上午9:49:44
*****/
@Controller
@RequestMapping("docHealthExam")
public class DocHealthExamController {

	private Logger logger = Logger.getLogger(DocHealthExamController.class);
	
	@Resource(name = "docHealthExamService")
	private DocHealthExamService docHealthExamService;

    
    /** 
     * @Title: findMemHealthExamReportList 
     * @Description: 医生查询会员的健康体检报告列表
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMemHealthExamReportList",method=RequestMethod.POST)
    public void findMemHealthExamReportList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findMemHealthExamReportList(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("医生查询会员的健康体检报告列表异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    /** 
     * @Title: findMemHealthExamReportList 
     * @Description: 根据参数获取医生所属会员的健康体验列表。
     * @param request
     * @param response
     * @author hx
     * @createDate 2016-05-19
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findHealthExamList",method=RequestMethod.POST)
    public void findHealthExamList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findHealthExamList(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("医生查询会员的健康体检报告列表异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMemHealthExamDetail 
     * @Description: 医生查询会员的健康体检报告明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMemHealthExamDetail",method=RequestMethod.POST)
    public void findMemHealthExamDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findMemHealthExamDetail(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("医生查询会员的健康体检报告明细异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: addMemDiseases 
     * @Description: 医生新增会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="addMemDiseases",method=RequestMethod.POST)
    public void addMemDiseases(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.addMemDiseases(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
            logger.error("医生新增会员的疾病史异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: deleteMemDiseases 
     * @Description: 医生删除会员的疾病史
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="deleteMemDiseases",method=RequestMethod.POST)
    public void deleteMemDiseases(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.deleteMemDiseases(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.DELETE_DATA_EXCEPTION));
            logger.error("医生修改会员的疾病史异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMemHealthFile 
     * @Description: 医生查询会员的健康档案
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMemHealthFile",method=RequestMethod.POST)
    public void findMemHealthFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findMemHealthFile(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("医生查询会员的健康档案异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: addOrModifyHealthExam 
     * @Description: 医生新增或者修改会员的体检报告
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-17
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="addOrModifyHealthExam",method=RequestMethod.POST)
    public void addOrModifyHealthExam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.addOrModifyMemHealthExam(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
            logger.error("医生新增或者修改会员的体检报告异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findAgedLifeEvaluate 
     * @Description: 通过老年人生活自理能力问卷获取老年人生活自理能力值
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findAgedLifeEvaluate",method=RequestMethod.POST)
    public void findAgedLifeEvaluate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findAgedLifeEvaluate(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("通过老年人生活自理能力问卷获取老年人生活自理能力值异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findTcmValue 
     * @Description: 通过中医体质问卷获取中医体质结果
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findTcmValue",method=RequestMethod.POST)
    public void findTcmValue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findTcmValue(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("通过中医体质问卷获取中医体质结果异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findTcmAndAgedQuestionnaire 
     * @Description: 获取中医体质和老年人生活能力评估问卷
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-05-27
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findTcmAndAgedQuestionnaire",method=RequestMethod.POST)
    public void findTcmAndAgedQuestionnaire(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = docHealthExamService.findTcmAndAgedQuestionnaire(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("获取中医体质和老年人生活能力评估问卷异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
}
