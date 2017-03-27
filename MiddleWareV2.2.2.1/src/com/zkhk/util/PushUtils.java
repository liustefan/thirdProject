package com.zkhk.util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author 
 *
 */
public class PushUtils {
	
	
    private static JsonConfig  jsonConfig = new JsonConfig();
    private static PropertyFilter propertyFilter = new PropertyFilter(){

		public boolean apply(Object source, String name, Object value) {
			if(null == value){
				return true;
			}
			return false;
		}
    };
	
	
	
	/**
	 * 
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
    
    /**
     * 生成数据的MD5值
     * @param bytes
     * @return
     */
    public static String MD5(final byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }
    

  
    /**
     * 将bean转换成jsonobject,bean中为null的属性将忽略
     * @param bean
     * @return
     */
    
    public static JSONObject  BeanToJsonObject(Object bean){
    	jsonConfig.setJsonPropertyFilter(propertyFilter);
		return JSONObject.fromObject(bean, jsonConfig);
    }

    

	
	
	
    
}