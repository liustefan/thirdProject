package com.zkhk.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;



public class SystemUtils {
	private static Logger logger = Logger.getLogger(SystemUtils.class);
	private static Properties prop = null;	
	static{
        try {   
        	prop = new Properties();
    		InputStream in = SystemUtils.class.getResourceAsStream("/config.properties");  
    		BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));  
    		prop.load(bf); 
            if(in != null){
            	in.close();
            }
        } catch (Exception e) {   
        	logger.error("读取初始配置文件时出现异常：",e);   
        }
	}

	public static String getValue(String key){
		
		return prop.getProperty(key);
	}
	
	
	public static void main(String args[]){
		
		Enumeration en = prop.propertyNames(); //得到配置文件的名字
		         
        while(en.hasMoreElements()) {
	        String strKey = (String) en.nextElement();
	        String strValue = SystemUtils.getValue(strKey);
	        System.out.println(strKey + "=" + strValue);

        }
	}
}
