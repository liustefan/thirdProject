package com.zkhk.dao;

import java.util.List;

import com.zkhk.entity.Obsr;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omrr;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.OsmrParam;
import com.zkhk.entity.RecordParam;
import com.zkhk.entity.SummaryReport;

public interface ReportDao {
	/**
	 * 查询会员汇总报表信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<SummaryReport> findSummaryReportbyParam(OsmrParam param)throws Exception;
	/**
	 * 查询相关联的报表信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Omrr> findReportbyParam(String param)throws Exception;
	List<Osbp> findOsbpByRecordValue(RecordParam param)throws Exception;
	List<Obsr> findObsrByRecordValue(RecordParam param)throws Exception;
	List<Oppg> findOppgByRecordValue(RecordParam param)throws Exception;
	List<Oecg> findOecgByRecordValue(RecordParam param)throws Exception;
	
	/**
	  * @Description 更新会员测量报告状态为已读(0:已读;1：未读)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月14日
	*/
	Integer updateReportReadStatus(Integer reportId) throws Exception;
	
	
	/**
	 * 查询未读汇总报告数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int queryUnreadReport(OsmrParam param)throws Exception;
	
}
