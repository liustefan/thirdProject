package com.zkhk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * GZIP压缩解压类
 */
public class GzipUtil{
    
    private static String encode = "utf-8";//"ISO-8859-1"
    
    public String getEncode() {
        return encode;
    }

    /*
     * 设置 编码，默认编码：UTF-8
     */
    public void setEncode(String encode) {
    	GzipUtil.encode = encode;
    }

    /*
     * 字符串压缩为字节数组
     */
    public static byte[] compressToByte(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encode));
            gzip.close();
        } catch (IOException e) {
        }
        return out.toByteArray();
    }

    /*
     * 字符串压缩为字节数组
     */
    public static byte[] compressToByte(String str,String encoding){
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
        }
        return out.toByteArray();
    }

    /*
     * 字节数组解压缩后返回字符串
     */
    public static String uncompressToString(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);

        try {
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        }
        return out.toString();
    }

    /*
     * 字节数组解压缩后返回字符串
     */
    public static String uncompressToString(byte[] b, String encoding) {
        if (b == null || b.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);

        try {
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
        }
        return null;
    }
   /* 
    * 字节流解压缩后返回字符串
    */
   public static String uncompressToString(InputStream in, String encoding) {
     
       ByteArrayOutputStream out = new ByteArrayOutputStream();
     
       try {
           GZIPInputStream gunzip = new GZIPInputStream(in);
           byte[] buffer = new byte[1024];
           int n;
           while ((n = gunzip.read(buffer)) >= 0) {
               out.write(buffer, 0, n);
           }
           return out.toString(encoding);
       } catch (IOException e) {
       }
       return null;
   }
  
   
   /**
    * 压缩文件
    * @param val
    * @return
    * @throws IOException
    */
    public static byte[] gzip(byte[] val) throws IOException {  
     ByteArrayOutputStream bos = new ByteArrayOutputStream(val.length);  
     GZIPOutputStream gos = null;  
     try {  
      gos = new GZIPOutputStream(bos);  
      gos.write(val, 0, val.length);  
      gos.finish();  
      gos.flush();  
      bos.flush();  
      val = bos.toByteArray();  
    } finally {  
     if (gos != null)  
       gos.close();  
      if (bos != null)  
       bos.close();  
     }  
    return val;  
    }

     /**
      * 解压缩文件
      * @param buf
      * @return
      * @throws IOException
      */
     public static byte[] unGzip(byte[] buf) throws IOException {  
      GZIPInputStream gzi = null;  
      ByteArrayOutputStream bos = null;  
      try {  
       gzi = new GZIPInputStream(new ByteArrayInputStream(buf));  
       bos = new ByteArrayOutputStream(buf.length);  
       int count = 0;  
       byte[] tmp = new byte[2048];  
       while ((count = gzi.read(tmp)) != -1) {  
       bos.write(tmp, 0, count);  
      }  
       buf = bos.toByteArray();  
      } finally {  
      if (bos != null) {  
        bos.flush();  
        bos.close();  
       }  
      if (gzi != null)  
        gzi.close();  
      }  
      return buf;  
     }  
    /**
     * 传输数据设置判断该数据是否要压缩
     * @param response
     * @param message
     * @throws IOException 
     */
    public static void write(HttpServletResponse response,String message) throws IOException{
    	byte[]  result;
 
    		if(message!=null&&message.length()>5000){
    			   result = GzipUtil.compressToByte(message);  
    			    response.setHeader("Content-Encoding","gzip");  
    			    response.getOutputStream().write(result);
    		}else {
    			response.getWriter().write(message);
			}
    	
    }
}


