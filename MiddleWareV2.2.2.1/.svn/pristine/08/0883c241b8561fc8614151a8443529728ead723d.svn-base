package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkhk.entity.ChartParam;
import com.zkhk.entity.OmdsParam;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.ReturnValue;


public interface MeasureService {

	/**
	 * 查询会员的测量信息
	 * @param request
	 * @return
	 */
	String findMemRecordbyParam(HttpServletRequest request) throws Exception;
	/**
	 * 查询会员的测量信息2
	 * @param request
	 * @return
	 */
	String findMemRecord2byParam(HttpServletRequest request)throws Exception;
	/**
	 * 查询会员的异常心电信息
	 * @param request
	 * @return
	 */
	String findAnomalyEcgbyParam(HttpServletRequest request)throws Exception;
	/**
	 * 上传会员血糖测量数据
	 * @param request
	 * @return
	 */
	String saveObsrData(HttpServletRequest request)throws Exception;
	/**
	 * 上传血压测量数据
	 * @param request
	 * @return
	 */
	ReturnValue saveOsbpData(Osbp osbp,int memberId)throws Exception;
	/**
	 * 上传心电测量数据
	 * @param request
	 * @return
	 */
	String saveEcgData(HttpServletRequest request)throws Exception;
	/**
	 * 上传三合一测量数据
	 * @param request
	 * @return
	 */
	String saveEcgPpgData(HttpServletRequest request)throws Exception;
	/**
	 * 通过id获取心电数据
	 * @param request
	 * @return
	 */
	String getOecgById(HttpServletRequest request)throws Exception;
	
	/**
	 * 通过id获取脉搏数据
	 * @param request
	 * @return
	 */
	String getOppgById(HttpServletRequest request)throws Exception;


	/**
	 * 获取文件路径
	 * @param request
	 * @param response
	 * @param chart
	 * @param i
	 * @param j
	 * @return
	 */
	void getJfreeChartFileName(HttpServletRequest request,HttpServletResponse response)throws Exception;
	/**
	 * 获取页数
	 * @param request
	 * @return
	 */
	String getPageByparam(HttpServletRequest request)throws Exception;
	
	/**
	 * 获取所有在异常信息
	 * @param request
	 * @return
	 */
	String getAllAbnormal(HttpServletRequest request)throws Exception;
	/**
	 * 查询会员是否存在血压测试数据
	 * @param request
	 * @return
	 */
	String isExitOsbp(HttpServletRequest request)throws Exception;
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void download(HttpServletRequest request, HttpServletResponse response)throws Exception;
	/**
	 * 根据事件id查询测量信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String getMeasureByEventId(HttpServletRequest request)throws Exception;
	
	
	/**
	 * 根据数据类型和数据id删除测量数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String deleteMeasureRecord(HttpServletRequest request)throws Exception;
	
	/**
	 * 根据参数获取chart图数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String getMeasueChartData(ChartParam param)throws Exception;
	
	/** 
     * @Title: getMeasureRecordList 
     * @Description: 获取测量记录list(返回新增事件类型)
     * @liuxaioqin
     * @createDate 2016-01-28 
     * @param param
     * @throws IOException    
     * @retrun void
     */
	public String getMeasureRecordList(OmdsParam param) throws Exception;
	
	/** 
     * @Title: getAllMeasureRecord 
     * @Description: 获取各种测量类型记录
     * @liuxaioqin
     * @createDate 2016-05-18 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String getAllMeasureRecord(HttpServletRequest request)throws Exception;
	
	/** 
     * @Title: findLastestMonthMeasureCount 
     * @Description: 获取最近一个月内四种类型的测量条数
     * @liuxaioqin
     * @createDate 2016-05-18 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findLastestMonthMeasureCount(HttpServletRequest request)throws Exception;
	
	/** 
     * @Title: findAllMeasureRecordByParam 
     * @Description: 根据不同条件查询测量数据
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findAllMeasureRecordByParam(HttpServletRequest request)throws Exception;
	
	/** 
     * @Title: findMeasRecordByEventIdAndType 
     * @Description: 根据测量事件id和type获取测量数据
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findMeasRecordByEventIdAndType(HttpServletRequest request)throws Exception;
	
}
