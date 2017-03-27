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
import com.zkhk.services.HealthExamService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * @ClassName:     HealthExamController.java 
 * @Description:   健康体检
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月14日 上午9:49:44
*****/
@Controller
@RequestMapping("healthExam")
public class HealthExamController {

	private Logger logger = Logger.getLogger(HealthExamController.class);
	
	@Resource(name = "healthExamService")
	private HealthExamService healthExamService;

    
    /** 
     * @Title: findMyHealthExamReportList 
     * @Description: 查询我的健康体检报告列表
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyHealthExamReportList",method=RequestMethod.POST)
    public void findMyHealthExamReportList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = healthExamService.findMyHealthExamReportList(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的健康体检报告列表异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMyHealthExamDetail 
     * @Description: 查询我的健康体检报告明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyHealthExamDetail",method=RequestMethod.POST)
    public void findMyHealthExamDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String resPString = healthExamService.findMyHealthExamDetail(request);
            GzipUtil.write(response, resPString);
        } catch (Exception e) {
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的健康体检报告明细异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
}
