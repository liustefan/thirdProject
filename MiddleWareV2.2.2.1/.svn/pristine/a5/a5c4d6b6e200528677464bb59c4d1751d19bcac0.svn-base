package com.zkhk.services;

import java.sql.Date;
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
import com.zkhk.constants.Constants;
import com.zkhk.dao.MeasureDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.dao.ReportDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Mem8;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omrr;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.OsmrParam;
import com.zkhk.entity.RecordParam;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.ReturnValue;
import com.zkhk.entity.SummaryReport;
import com.zkhk.util.TimeUtil;
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	private Logger logger=Logger.getLogger(ReportServiceImpl.class);
	
	@Resource(name = "reportDao")
	private ReportDao reportDao;
	
	@Resource(name = "measureDao")
	private MeasureDao measureDao;
	
	@Resource(name = "pushMessageDao")
	private PushMessageDao messageDao;
	public String findSummaryReportbyParam(OsmrParam param,CallValue callValue)throws Exception {
		ReturnResult re = new ReturnResult();
		// 查询汇总信息
		List<SummaryReport> reports= reportDao.findSummaryReportbyParam(param);
		if (reports.size()>0) {
			re.setState(0);
			re.setMessage("成功");
			re.setContent(reports);
			logger.info("查询会员汇总报表信息成功");
			 if(callValue.getMemberId()!=param.getMemberId()){
				 messageDao.updateMarkTagByCreateId(param.getMemberId(), callValue.getMemberId(), 2);
			 }
			//会员查看一次报告得2 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(callValue.getMemberId());
			mem8.setScore(Constants.ONCE_CHECK_REPORT_SCORE);
			String measureTime = TimeUtil.formatDatetime2(new Date(System.currentTimeMillis()));
			mem8.setUploadTime(measureTime);
			measureDao.addMemActivityScore(mem8);
			
		} else {
			re.setState(3);
			re.setMessage("查询不到该会员汇总报告信息");
			logger.info("查询不到该会员汇总报告信息");
		}
		return JSON.toJSONString(re);
	}

	public String findReportbyParam(HttpServletRequest request)throws Exception {
		   ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			if(callValue.getParam()==null||callValue.getParam().trim().equals("")){
				re.setState(1);
				re.setMessage("传递的参数不合规定");
				return JSON.toJSONString(re);
			}
			JSONObject object=JSON.parseObject(callValue.getParam());
			String param=object.getString("reports");
			
			// 更新同行状态信息
			List<Omrr> reports= reportDao.findReportbyParam(param);
			if (reports.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(reports));
				logger.info("查询会员关联报表信息成功");
			} else {
				re.setState(3);
				re.setMessage("查询不到该会员关联报告信息");
				logger.info("查询不到该会员关联报告信息");
			}
		 return JSON.toJSONString(re);
	}

	public String findReportRecordParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			RecordParam param = JSON.parseObject(callValue.getParam(),
					RecordParam.class);
			// 测量类型:ECG,PPG,BP,BS

			if ("BP".equals(param.getMeasureType())) {
				// 查询血压信息
				List<Osbp> osbps = reportDao.findOsbpByRecordValue(param);
				if (osbps.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员血压测量数据成功");
					String content = JSON.toJSONString(osbps);
					re.setContent(content);
				} else {
					re.setState(3);
					re.setMessage("该会员没有匹配的血压测量数据");
				}
			} else if ("BS".equals(param.getMeasureType())) {
				// 查询血糖信息
				List<Obsr> osbps = reportDao.findObsrByRecordValue(param);
				if (osbps.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员血糖测量数据成功");
					String content = JSON.toJSONString(osbps);
					re.setContent(content);
				} else {
					re.setState(3);
					re.setMessage("该会员没有匹配的血糖测量数据");
				}
			} else if ("PPG".equals(param.getMeasureType())) {
				// 脉搏
				List<Oppg> oppgs = reportDao.findOppgByRecordValue(param);
				if (oppgs.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员脉搏测量数据成功");
					String content = JSON.toJSONString(oppgs);
					re.setContent(content);
				} else {
					re.setState(3);
					re.setMessage("该会员没有匹配的脉搏测量数据");
				}
			} else if ("ECG".equals(param.getMeasureType())) {
				List<Oecg> oecgs = reportDao.findOecgByRecordValue(param);
				if (oecgs.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员心电测量数据成功");
					String content = JSON.toJSONString(oecgs);
					re.setContent(content);
				} else {
					re.setState(3);
					re.setMessage("该会员没有匹配的心电测量数据");
				}
			} else {
				re.setState(1);
				re.setMessage("传递参数有错误");
			}
		return JSON.toJSONString(re);
	}

	/**
	  * @Description 更新会员汇总测量报告状态为已读(0:已读;1：未读)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月14日
	*/
	public String updateReportReadStatus(HttpServletRequest request)throws Exception
	{
	    ReturnValue re = new ReturnValue();
	    String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject object = JSON.parseObject(callValue.getParam());
        String reportId = object.getString("reportId");
        if(StringUtils.isEmpty(reportId))
        {
            re.setState(1);
            re.setMessage("汇总测量报告的参数reportId为空！");
            logger.info("汇总测量报告的参数reportId为空！");
            return JSON.toJSONString(re);
        }
        Integer newReportId = Integer.valueOf(reportId);
        if(newReportId < 0)
        {
            re.setState(1);
            re.setMessage("汇总测量报告的参数reportId应该为正整数！");
            logger.info("汇总测量报告的参数reportId应该为正整数！");
            return JSON.toJSONString(re);
        }
        int count = reportDao.updateReportReadStatus(newReportId);
        if(count <= 0)
        {
            re.setState(3);
            re.setMessage("更新会员汇总测量报告id为"+ newReportId +"状态为已读失败！");
            logger.info("更新会员汇总测量报告id为"+ newReportId +"状态为已读失败！");
        }
        else
        {
            re.setState(0);
            re.setMessage("更新会员汇总测量报告状态为已读成功！");
            logger.info("更新会员汇总测量报告状态为已读成功！");
        }
	    return JSON.toJSONString(re);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getUnreadReportAmount(OsmrParam param)throws Exception {
		ReturnResult re = new ReturnResult();
		int amount = reportDao.queryUnreadReport(param);
		re.setState(0);
		re.setMessage("查询会员未读报告数成功。");
		Map returnMap = new HashMap();
		returnMap.put("amount", amount);
		re.setContent(returnMap);
		logger.info("获取会员的未读汇总测量报告数成功！");
		return JSON.toJSONString(re);
	}
	
}
