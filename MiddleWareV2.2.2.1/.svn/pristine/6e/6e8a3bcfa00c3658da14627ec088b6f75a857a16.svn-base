package com.zkhk.contoller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.zkhk.constants.Constants;
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.PackageService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * 套餐接口
 * @author raojunming
 *
 */
@Controller
@RequestMapping("package")
public class PackageController {
	private Logger logger=Logger.getLogger(PackageController.class);
	@Resource(name="packageService")
	private PackageService packageService;
	
	/**
	 * 获取所有的套餐信息
	 * @param request
	 * @param response
	 * Return 的json格式数据  state成功0，1表示查询不到数据，2表示出现异常 9表示异常
	 * content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("packages")
	public void packages(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=  packageService.findPackageByParam(request);
		GzipUtil.write(response, resPString);;
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("套餐信息查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));
		
	}
		
	}
	
	/**
	 * 获取会员的套餐信息
	 * @param request
	 * @param response
	 * Return 的json格式数据  state成功0，1表示查询不到数据，2表示出现异常 9表示异常
	 * content表示成功以后的MemLog对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("memPackage")
	public void memPackage(HttpServletRequest request,HttpServletResponse response) throws IOException {
	try {
		String resPString=  packageService.findMemPackageByParam(request);
		GzipUtil.write(response, resPString);;
	} catch (Exception e) {
		ReturnValue re=new ReturnValue();
		re.setState(2);
		re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
		logger.error("订购套餐信息查询运行异常:"+e);
		response.getWriter().write(JSON.toJSONString(re));

	}
		
	}
}
