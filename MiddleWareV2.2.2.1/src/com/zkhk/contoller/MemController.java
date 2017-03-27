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
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.MemService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

@Controller
@RequestMapping("mem")
public class MemController {

	private Logger logger = Logger.getLogger(MemController.class);
	@Resource(name = "memService")
	private MemService memService;

	/**
	 * 会员登入
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0，1表示用户名或密码不存在，2表示出现异常
	 *            content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String resPString = memService.findUserbyNameAndPassWord(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("用户登入出现异常:" + e);
	        response.getWriter().write(JSON.toJSONString(re));
		}

	}

	/**
	 * 会员注销
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0，1表示查询不到该，2标示出现异常信息
	 *            content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String resPString = memService.findOmembyId(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员基本信息查询运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}

	}

	/**
	 * 获取会员信息
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0，1表示注销失败，2标示出现异常信息
	 *            content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("info")
	public void info(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			String resPString = memService.findOmembyId(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("查询会员基本信息异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}

	}

	/**
	 * 查询会员健康档案信息 state 0表示成功 1表示查询不到数据 2表示异常 9表示没权限登入
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("memFile")
	public void memFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			String resPString = memService.findMemFilebyId(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员健康档案信息查询运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}

	}

	/**
	 * 会员信息编辑
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0 2出现异常 9没有操作权限，
	 *            content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String resPString = memService.updateOmembyId(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			 ReturnValue re = new ReturnValue();
			 re.setState(1);
		     re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
		     logger.error("修改会员信息失败:"+e);
			 response.getWriter().write(JSON.toJSONString(re));
		}
	}

	/**
	 * 会员注销
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0 2出现异常 9没有操作权限 ,1老密码错误，
	 *            content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping(value = "updatePassWord", method = RequestMethod.POST)
	public void updatePassWord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = memService.updateOmemPassWordByParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
		    re.setState(2);
		    re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
			logger.info("会员注销失败:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	/** 
     * @Title: findSameOrgDoctors 
     * @Description: 会员查询同组织下的所有医生。
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-30
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="findSameOrgDoctors",method=RequestMethod.POST)
	public void findSameOrgDoctors(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = memService.findSameOrgDoctors(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
		    re.setState(2);
		    re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.info("会员查询同组织下的所有医生异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}

}
