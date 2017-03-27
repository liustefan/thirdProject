package com.zkhk.contoller;

/**
* @author rjm
*/

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
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.FocusService;
import com.zkhk.services.FocusServiceImpl;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;



@Controller
@RequestMapping(value = "focus")
public class FocusController {
	
	private Logger logger=Logger.getLogger(FocusServiceImpl.class);
	@Resource(name = "focusService")
	private FocusService focusService;
	

	
	/**
	 * 获取我的关注信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("myFocus")
	public void myFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.getMyFocusByParam(request);
		GzipUtil.write(response, resPString);		
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("我的关注查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}

	
	/**
	 * 查找所有的可以添加的好友信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getFocusMem")
	public void getFocusMem(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.getFocusMemsByParam(request);
		GzipUtil.write(response, resPString);	
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("查询会员信息运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}
	
	
	/**
	 * 查找所有的可以的想邀请的好友信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getInviteFocusMem")
	public void getInviteFocuseMem(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=  focusService.getInviteFocuseMem(request);
		GzipUtil.write(response, resPString);	
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("查询会员信息运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
	
 }
	
	/**
	 * 查找我已经关注的好友的信息记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getMyFocused")
	public void getMyFocused(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.getMyFocusedByParam(request);
		GzipUtil.write(response, resPString);	
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("已关注信息查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}	
   }
	/**
	 * 查找我已经关注的好友的信息记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getFocusedMe")
	public void getFocusedMe(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.getFocusedMeByParam(request);
		GzipUtil.write(response, resPString);	
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("已关注信我的会员息查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}
	
	/**
	 * 查找我已经关注的好友的信息记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("focusMemFile")
	public void focusMemFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.focusMemFile(request);
		GzipUtil.write(response, resPString);	
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("会员健康档案信息查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
}
	
	
	
	/**
	 * 修改关注信息记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="updateFocus",method=RequestMethod.POST)
	public void updateFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.updateFocusByParam(request);
		GzipUtil.write(response, resPString);
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(1);
		re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
		logger.error("修改信息失败:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}
	
	/**
	 * 添加关注信息(根据协议由客服端来传递相应的值)成功放回id号
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="addFocus",method=RequestMethod.POST)
	public void addFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.addFocusByParam(request);
		GzipUtil.write(response, resPString);		
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(1);
		re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
		logger.error("添加关注信息失败:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}
	
	/**
	 * 获取监护医生
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getCustodyDoc")
	public void getCustodyDoc(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = focusService.getCustodyDoc(request);
			GzipUtil.write(response, resPString);
		} 
		catch (Exception e)
		{
			ReturnValue re=new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员的监护医生基本信息查询运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		
		}
	}
	
	/**
	 * 修改关注信息记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="updateFocusRemark",method=RequestMethod.POST)
	public void updateFocusRemark(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=   focusService.updateFocusRemarkByParam(request);
		GzipUtil.write(response, resPString);
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(1);
		re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
		logger.error("修改备注信息失败:"+e);
		response.getWriter().write(JSON.toJSONString(re));
	}
		
	}

//	@RequestMapping(value = "getHeadImg")
//	public void getHeadImg(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		//User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466
//		
//		String resPString=   focusService.getHeadImg(request);;
//		try {
//			GzipUtil.write(response, resPString);			
//		} catch (IOException e) {
//			logger.info("运行出现异常情况");			
//	     }
//	}
	/**
	 * 获取关注会员测量信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getFocusMeasure")
	public void getFocusMeasure(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466
		
		try {
			String resPString=   focusService.getFocusMeasure(request);
			GzipUtil.write(response, resPString);			
		} catch (Exception e) {
			ReturnValue re=new ReturnValue();
			 re.setState(2);
	         re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			 logger.info("查询关注会员信息异常:"+e);	
			 response.getWriter().write(JSON.toJSONString(re));
	     }
	}
	
	/**
	 * 获取关注会员测量信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getFocusCount")
	public void getFocusCount(HttpServletRequest request,HttpServletResponse response) throws Exception {		
		try {
			String resPString=   focusService.getFocusCount(request);;
			GzipUtil.write(response, resPString);			
		} catch (Exception e) {
			ReturnValue re=new ReturnValue();
			re.setState(2);
	        re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
	        logger.error("查询更新关注会员信息异常:"+e);
	        response.getWriter().write(JSON.toJSONString(re));
	     }
	}
	
	/**
	 * 获取关注会员测量信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getFocusMessage")
	public void getFocusMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {		
		try {
			String resPString= focusService.getFocusMessage(request);;
			GzipUtil.write(response, resPString);			
		} catch (Exception e) {
			ReturnValue re=new ReturnValue();
			re.setState(2);
	        re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
	        logger.error("查询更新关注会员信息异常:"+e);
	        response.getWriter().write(JSON.toJSONString(re));
	     }
	}
	
}