package com.zkhk.filter;

/**
 *
 * @author rjm
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.alibaba.fastjson.JSON;
import com.zkhk.constants.Constants;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.DocService;
import com.zkhk.services.MemService;
import com.zkhk.util.SystemUtils;

/**
 * 登录过滤
 * 
 */
@Component("sessionFilter")
public class SessionFilter extends OncePerRequestFilter {
	private Logger logger = Logger.getLogger(SessionFilter.class);
	@Resource(name = "memService")
	private MemService memService;

	@Resource(name = "docService")
	private DocService docService;
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws IOException,ServletException {
	  	    response.setContentType("text/html;charset=UTF-8");
	  	    ReturnValue value=new ReturnValue();
			String uri = request.getRequestURI(); // 获取地址URL
			//System.out.println(Util.getIpAddr(request));
			String prefix = request.getContextPath(); // 得到 项目上下文路径
			uri = uri.substring(prefix.length()); // 去除 项目上下文路径
			String params = request.getParameter("params");// 获取URL后跟的值
			String requestUri = uri;
			if (params != null) {
				requestUri = requestUri + "?" + params;
			}
		    if (null != requestUri && requestUri.length() > 1 && requestUri.startsWith("/")) {
				// 移除前面的"//"
				requestUri = removePrex(requestUri);
			}
			
		    /* 验证用户账号禁用状态 begin */
			if(params != null && 
					(null != requestUri) && 
					!"/".equals(requestUri) && 
					!"index.jsp".equals(requestUri) && 
					!"login.jsp".equals(requestUri) && 
					!requestUri.startsWith("doc/docLogin") && 
					!requestUri.startsWith("doc/docLogout") &&
					!requestUri.startsWith("pc/docLogin") &&
					!requestUri.startsWith("pc/addPcMiniRecord") &&
					!requestUri.startsWith("pc/deletePcMiniRecord") &&
					!requestUri.startsWith("pc/updatePcMiniRecord") &&
					!requestUri.startsWith("pc/findPcMiniRecordByParam") &&
					!requestUri.startsWith("pc/findOmemByUserId") &&
					!requestUri.startsWith("docHealthExam/findTcmAndAgedQuestionnaire") &&
					!requestUri.startsWith("docVisit/findSomeDatasByMemberId") &&
					!requestUri.startsWith("docHealthExam/findMemHealthFile") &&
					!requestUri.startsWith("docVisit/findMemHypertensionVisitList") &&
					!requestUri.startsWith("doc/getMyMemberList"))
			{
				try{
					CallValue callValue = JSON.parseObject(params, CallValue.class);
					boolean isUse = memService.checkAcountStatus(callValue);
					if(!isUse){
						value.setState(8);
						value.setMessage(SystemUtils.getValue(Constants.ACCOUNT_FREEZE));
						PrintWriter out;
					    out = response.getWriter();
						out.println(JSON.toJSONString(value));						
						return;
					}
				}
				catch(Exception e){
					value.setState(2);
					value.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
					PrintWriter out;
				    out = response.getWriter();
					out.println(JSON.toJSONString(value));
					logger.error("验证账户冻结状态异常。");
					return;
				}
			}
			/* 验证用户账号禁用状态 end */
			
			/* 验证session begin */
			if (null == requestUri || "/".equals(requestUri)
					|| "index.jsp".equals(requestUri)
					|| "login.jsp".equals(requestUri)
					|| requestUri.startsWith("mem/login")
				    || requestUri.startsWith("js")
				    || requestUri.startsWith("images")
				    || requestUri.startsWith("measure/uploadEcg")
				    || requestUri.startsWith("measure/uploadEcgPpg")
					|| requestUri.startsWith("measure/getPage")
					|| requestUri.startsWith("measure/uploadObsr")
					|| requestUri.startsWith("measure/uploadOsbp")
					|| requestUri.startsWith("measure/measureLine")
				    || requestUri.startsWith("focus/getFocusMessage")
			        || requestUri.startsWith("message/saveMessage")
			        || requestUri.startsWith("doc/docLogin")
			        || requestUri.startsWith("doc/memLogin")
			        || requestUri.startsWith("doc/findMemMeasureRecordImgs")
			        || requestUri.startsWith("doc/getMyMemberList")
			        || requestUri.startsWith("pc/docLogin")
			        || requestUri.startsWith("dd/findAllDictionary")
			        || requestUri.startsWith("dd/findDiseaseDictionary")
			        || requestUri.startsWith("dd/findHealthExamDictionary")
			        || requestUri.startsWith("docHealthExam/findTcmAndAgedQuestionnaire")
			        || requestUri.startsWith("docVisit/findSomeDatasByMemberId")
			        || requestUri.startsWith("docHealthExam/findMemHealthFile")
			        || requestUri.startsWith("docVisit/findMemHypertensionVisitList")
					){
				chain.doFilter(request, response);
			}
			else if (params != null){
				try{
				    CallValue callValue = JSON.parseObject(params, CallValue.class);	
				    // 根据session和id查询会员
				    String returnJson;
    				if(requestUri.startsWith("doc")){//医生模块
    					returnJson = docService.findDocBySessionAndId(callValue.getSession(), callValue.getMemberId());
    				}else if(requestUri.startsWith("pc")){//pc模块
                        returnJson = docService.findDocBySessionAndId(callValue.getSession(), callValue.getMemberId());
                    }
    				else{//会员模块
    					returnJson = memService.findUserBySessionAndId(callValue.getSession(), callValue.getMemberId());
    				}
				 
    				ReturnValue returnValue = JSON.parseObject(returnJson,	ReturnValue.class);
    				//System.out.println(returnValue.getState()+"-----------------------");
    				// 40f054c*b6244f63d=386c*c*c*3e96d=f4b01
    				if (returnValue.getState() != 0){
    				    PrintWriter out = response.getWriter();
    				    out.println(returnJson);
    				    return;
    				}
				} 
				catch (Exception e){		
					value.setState(10);
					value.setMessage(SystemUtils.getValue(Constants.INVALID_PARAM));
					PrintWriter out;
				    out = response.getWriter();
					out.println(JSON.toJSONString(value));
					return;
				}
				chain.doFilter(request, response);
			}
			else{	
				value.setState(10);
				value.setMessage(SystemUtils.getValue(Constants.INVALID_PARAM));
				PrintWriter out;
			    out = response.getWriter();
				out.println(JSON.toJSONString(value));
				return;
			}
			/* 验证session end */
	}

	public String removePrex(String s) {
		String patternStr = "([a-zA-Z]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(s);
		String replaceStr = null;
		if (matcher.find()) {
			replaceStr = matcher.group();
		}
		int i = s.indexOf(replaceStr) - 1;
		if (s.length() > i) {
			s = s.substring(i + 1, s.length());
		}
		return s;
	}
	
	
	

}
