package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;

import com.zkhk.entity.CallValue;
import com.zkhk.entity.OsmrParam;

public interface ReportService {

	/**
	 * 查询会员汇总测量报告信息
	 * @param request
	 * @return
	 */
	String findSummaryReportbyParam(OsmrParam osmrParam,CallValue callValue)throws Exception;
	/**
	 * 查询会员的关联报表信息
	 * @param request
	 * @return
	 */
	String findReportbyParam(HttpServletRequest request)throws Exception;
	/**
	 * 查询报表关联的测量数据
	 * @param request
	 * @return
	 */
	String findReportRecordParam(HttpServletRequest request)throws Exception;
	
	/**
	  * @Description 更新会员测量报告状态为已读(0:已读;1：未读)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月14日
	*/
	String updateReportReadStatus(HttpServletRequest request)throws Exception;
	
	
	/**
	 * 获取会员的未读汇总测量报告数
	 * @param osmrParam
	 * @return
	 * @throws Exception
	 */
	String getUnreadReportAmount(OsmrParam osmrParam)throws Exception;
	
	
}
