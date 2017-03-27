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
import com.zkhk.entity.CallValue;
import com.zkhk.entity.OsmrParam;
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.ReportService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * 会员测量报表接口
 * 
 * @author bit
 * 
 */
@Controller
@RequestMapping("report")
public class ReportController {
	private Logger logger = Logger.getLogger(ReportController.class);
	@Resource(name = "reportService")
	private ReportService reportService;

	/**
	 * 查询会员测量信息的汇总报表信息 state 0表示成功 1表示查询不到数据 2表示异常 9表示没权限登入
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("summaryReport")
	public void summaryReport(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			OsmrParam  param=JSON.parseObject(callValue.getParam(),OsmrParam .class);
			String resPString = reportService.findSummaryReportbyParam(param,callValue);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员汇总报表信息查询运行异常:" + e);
			response.getWriter().write(JSON.toJSONString(re));
		
		}

	}

	/**
	 * 查询会员的关联报表信息 state 0表示成功 1表示查询不到数据 2表示异常 9表示没权限登入
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("relatedReport")
	public void relatedReport(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = reportService.findReportbyParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员关联报表信息查询运行异常:" + e);
			response.getWriter().write(JSON.toJSONString(re));
			
		}

	}

	/**
	 * 查询会员关联测量信息 state 0表示成功 1表示查询不到数据 2表示异常 3表示参数错误 9表示没权限登入
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("relationRecord")
	public void relationRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = reportService.findReportRecordParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("订购套餐信息查询运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
			
		}

	}
	
	/**
	  * @Description 更新会员测量报告状态为已读(0:已读;1：未读)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月14日
	*/
	@RequestMapping(value="updateReportReadStatus", method = RequestMethod.POST)
	public void updateReportReadStatus(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
	    try 
	    {
            String resPString = reportService.updateReportReadStatus(request);
            GzipUtil.write(response, resPString);
        }
	    catch (Exception e)
	    {
            ReturnValue re = new ReturnValue();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
            logger.error("更新会员测量报告状态为已读运行异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
	}
	
	
	/**
	 * 根据时间段获取会员的未读汇总测量报告数
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getUnreadReportAmount")
	public void getUnreadReportAmount(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			OsmrParam  param=JSON.parseObject(callValue.getParam(),OsmrParam .class);
			String resPString = reportService.getUnreadReportAmount(param);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("根据时间段获取会员的未读汇总测量报告数异常:" + e);
			response.getWriter().write(JSON.toJSONString(re));
		
		}

	}

}
