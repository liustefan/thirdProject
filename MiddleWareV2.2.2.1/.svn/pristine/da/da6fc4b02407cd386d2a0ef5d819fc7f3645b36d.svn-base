package com.zkhk.services;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.zkhk.entity.ReturnResult;
import com.zkhk.util.FileOperateUtil;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	private Logger logger = Logger.getLogger(ConfigServiceImpl.class);

	@Override
	public ReturnResult getConfigFileByName(String fileName) throws Exception {
		ReturnResult result = new ReturnResult();
		String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/file/"+fileName;
		String fileStr = FileOperateUtil.feadFileToString(filePath);
		result.setContent(fileStr);
		result.setMessage("获取配置文件成功。");
		return result;
	}


	

}
