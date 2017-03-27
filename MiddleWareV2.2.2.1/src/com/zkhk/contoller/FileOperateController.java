package com.zkhk.contoller;

/**
* @author rjm
*/


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zkhk.constants.Constants;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.util.FileOperateUtil;




//@Controller
//@RequestMapping(value = "app")
public class FileOperateController {
	
	
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;
	/**
	 * 上传文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "test",method = RequestMethod.POST)
//	public void test(HttpServletRequest request) throws Exception {
//		
//		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
//		MultipartFile mFile = mRequest.getFile(Constants.ECG_PARAM_FILE);
//        mongoEntityDao.saveFile("fs.files", mFile);
//	}

	/**
	 * 上传文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "upload",method = RequestMethod.POST)
//	public ModelAndView upload(HttpServletRequest request) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		// 别名
//		String[] alaises = ServletRequestUtils.getStringParameters(request,"alais");
//		String[] params = new String[] { "alais" };
//		Map<String, Object[]> values = new HashMap<String, Object[]>();
//		values.put("alais", alaises);
//		List<Map<String, Object>> result = FileOperateUtil.upload(request,params, values);
//		map.put("result", result);
//		return new ModelAndView("/list", map);
//
//	}

	/**
	 * 下载
	 * @param attachment
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "download")
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466
		String storeName = "201205051340364510870879724.zip";
		String realName = "Java设计模式.zip";
		String contentType = "application/octet-stream";
		request.getHeader("User-Agent");
	//	FileOperateUtil.download(request, response, storeName, contentType,realName);

		return null;
	}
}
