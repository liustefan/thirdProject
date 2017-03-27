package com.zkhk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 32位MD5加密
 * @author u
 *
 */
public class PasswordEncryption {
	private static MessageDigest messageDigest = null;
	private static char[] hexDigit = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	
	public static String Md532(String password) {
		byte[] plainText = null;
		try {
			plainText = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		char str[] = null;
		try {
			if (messageDigest == null) {
				messageDigest = MessageDigest.getInstance("MD5");
			}

			messageDigest.update(plainText);
			byte[] md = messageDigest.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigit[byte0 >>> 4 & 0xf];
				str[k++] = hexDigit[byte0 & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return new String(str);
	}
	
	
	public static String getMD5Password(String password){
		byte[] plainText = null;
		try {
			plainText = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		char str[] = null;
		try {
			if (messageDigest == null) {
				messageDigest = MessageDigest.getInstance("MD5");
			}
			
			messageDigest.update(plainText);
			byte[] md = messageDigest.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigit[byte0 >>> 4 & 0xf];
				str[k++] = hexDigit[byte0 & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return new String(str);
	}
	
	public static String getResult(String h){
		String result = "0";
		if (h.length()>36) {
			result = h.substring(12, 12+(h.length()-36));
		} 
//		else if(h.length()<36){
//			result = h;
//		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getMD5Password("e10adc3949ba59abbe56e057f20f883ezkhk"));
	}
}
