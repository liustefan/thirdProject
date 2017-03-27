package com.zkhk.services;

import javax.servlet.http.HttpServletRequest;

public interface AnswerService {
    /**
     * 查询会员问卷列表
     * @param request
     * @return
     */
	String findOuaiByParam(HttpServletRequest request) throws Exception;
   /**
    * 查询会员组合问卷列表
    * @param request
    * @return
    */
	String findOcamByParam(HttpServletRequest request)throws Exception;
	/**
	 * 获取组合答卷的级联答卷信息
	 * @param request
	 * @return
	 */
    String findOuaiByParam2(HttpServletRequest request)throws Exception;
    /**
     * 获取问卷信息
     * @param request
     * @return
     */
	String findOmfqByParam(HttpServletRequest request)throws Exception;
	/**
	 * 获取已答答案列表
	 * @param request
	 * @return
	 */
	String findResultByParam(HttpServletRequest request)throws Exception;
	/**
	 * 保存会员答案
	 * @param request
	 * @return
	 */
	String saveResultByParam(HttpServletRequest request)throws Exception;
	
	
	/**
	  * @Description 查询会员的单份答卷：(已答和未答)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	String findMemSingleAnswer(HttpServletRequest request)throws Exception;
	
	/**
	  * @Description 获取会员单份问卷详细信息(会员未作答)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	String findMemSingleQuestionInfo(HttpServletRequest request)throws Exception;
	
	/**
	  * @Description 获取会员单份答卷总得分，结论和审核建议(已审核)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	String findMemSingleAnswerSummary(HttpServletRequest request)throws Exception;
	
	/**
	  * @Description 获取会员已答单份答卷的答案
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月13日
	*/
	String findSingleAnswerHasSubmit(HttpServletRequest request)throws Exception;
	
	/**
     * @Description 更新已审核的会员答卷为已读("0")
     * @author liuxiaoqin
     * @CreateDate 2015年10月13日
    */
    public String updateMemSingleAnswerHasApproved(HttpServletRequest request) throws Exception;

}
