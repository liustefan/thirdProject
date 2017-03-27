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
import com.zkhk.services.VisitService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * @ClassName:     VisitController.java 
 * @Description:   随访
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年3月14日 上午9:49:44
*****/
@Controller
@RequestMapping("visit")
public class VisitController {

	private Logger logger = Logger.getLogger(VisitController.class);
	
	@Resource(name = "visitService")
	private VisitService visitService;
    
    /** 
     * @Title: findMyDiabetesVisitList 
     * @Description: 查询我的糖尿病随访列表
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyDiabetesVisitList",method=RequestMethod.POST)
    public void findMyDiabetesVisitList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String resPString = visitService.findMyDiabetesVisitList(request);
            GzipUtil.write(response, resPString);
        }catch(Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的糖尿病随访列表:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMyDiabetesVisitDetail 
     * @Description: 查询我的糖尿病随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyDiabetesVisitDetail",method=RequestMethod.POST)
    public void findMyDiabetesVisitDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String resPString = visitService.findMyDiabetesVisitDetail(request);
            GzipUtil.write(response, resPString);
        }catch(Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的糖尿病随访明细异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMyHypertensionVisitList 
     * @Description: 查询我的高血压随访列表
     * @param request
     * @param response
     * @author liuxiaoqin
     * @createDate 2016-03-14
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyHypertensionVisitList",method=RequestMethod.POST)
    public void findMyHypertensionVisitList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String resPString = visitService.findMyHypertensionVisitList(request);
            GzipUtil.write(response, resPString);
        }catch(Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的高血压随访列表:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    /** 
     * @Title: findMyHypertensionVisitDetail 
     * @Description: 查询我的高血压随访明细
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-03-15
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findMyHypertensionVisitDetail",method=RequestMethod.POST)
    public void findMyHypertensionVisitDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String resPString = visitService.findMyHypertensionVisitDetail(request);
            GzipUtil.write(response, resPString);
        }catch(Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("查询我的高血压随访明细异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
}
