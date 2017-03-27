package com.zkhk.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;








import com.mongodb.gridfs.GridFSDBFile;

/**
 * @author
 */

public class Util {
	private static Logger logger = Logger.getLogger(Util.class);

	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s.trim())) {
			return true;
		}
		return false;
	}

	public static String trim(String s) {
		if (null != s) {
			s = s.trim();
		} else {
			s = "";
		}
		return s;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isNumber(String id) {
		try {
			if (!isEmpty(id)) {
				Long.valueOf(id);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static List<String> getSqlTypeNameList(String string) {
		String[] nameRights = string.split("\\@\\[");
		String name = null;
		List<String> sqlTypeNameList = null;
		if (nameRights != null && nameRights.length > 1) {
			sqlTypeNameList = new ArrayList<String>();
			for (int i = 1; i < nameRights.length; i++) {
				String nameRight = nameRights[i];
				name = nameRight.split("\\]")[0];
				if (!sqlTypeNameList.contains(name)) {
					sqlTypeNameList.add(name);
				}
			}
		}
		return sqlTypeNameList;
	}

	public static List<String> getVariableNameList(String string) {
		String[] nameRights = string.split("\\$\\{");
		String name = null;
		List<String> variableNameList = null;
		if (nameRights != null && nameRights.length > 1) {
			variableNameList = new ArrayList<String>();
			for (int i = 1; i < nameRights.length; i++) {
				String nameRight = nameRights[i];
				name = nameRight.split("\\}")[0];
				if (!variableNameList.contains(name)) {
					variableNameList.add(name);
				}
			}
		}
		return variableNameList;
	}

	public static List<String> getVariableNameListDulipSame(String string) {
		String[] nameRights = string.split("\\$\\{");
		String name = null;
		List<String> variableNameList = null;
		if (nameRights != null && nameRights.length > 1) {
			variableNameList = new ArrayList<String>();
			for (int i = 1; i < nameRights.length; i++) {
				String nameRight = nameRights[i];
				name = nameRight.split("\\}")[0];
				variableNameList.add(name);
			}
		}
		return variableNameList;
	}

	public static String getRequestScopeVariableName(String string) {
		String tempNames[] = string.split("requestScope.");
		if (tempNames != null && tempNames.length > 1) {
			return tempNames[1];
		}
		return null;
	}

	public static String getSessionScopeVariableName(String string) {
		String tempNames[] = string.split("sessionScope.");
		if (tempNames != null && tempNames.length > 1) {
			return tempNames[1];
		}
		return null;
	}

	/**
	 * 把null字符串转换成空
	 * 
	 * @param tel
	 * @return
	 */
	public static String nullToEmpty(String s) {
		if (null == s || "null".equals(s.trim())) {
			return "";
		}
		return s;
	}

	/**
	 * 通过HttpServletRequest返回IP地址
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = null;
		try {
			request.getHeader("x-forwarded-for");
			if ((ip == null) || (ip.length() == 0)
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if ((ip == null) || (ip.length() == 0)
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return  ip;
	}

	public static boolean isEmpty(Object s) {
		String h = (String) s;
		if (null == h || "".equals(h.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 读取指定位字节流
	 * @param file
	 * @param start
	 *            开始读取位
	 * @param end
	 *            结束读取
	 * @return
	 * @throws IOException
	 */
	public static final byte[] input2byte(GridFSDBFile file, int start,
			int end) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		InputStream  stream;
		// 解压文件
		if ("gzip".equals(file.getContentType())) {
			 stream=new GZIPInputStream(file.getInputStream());
		}else {
			stream=file.getInputStream();
		}
		
		//int length=stream.available();
		//System.out.println(length);
		//放弃指定在字节数
		stream.skip(start);
		byte[] buff = new byte[1024];
		int rc = 0;
		while ((rc = stream.read(buff)) > 0) {
			//System.out.println(rc+"------------------------");
			swapStream.write(buff, 0, rc);
			if(swapStream.toByteArray().length>=(end-start)){
				//System.out.println(rc+"==========================");
				return swapStream.toByteArray();
			}
		}
		byte[] in2b = swapStream.toByteArray();
//		 FileOutputStream out1 = new
//		 FileOutputStream("E://"+TimeUtil.currentDatetime()+"test.txt");
//		 FileCopyUtils.copy(new ByteArrayInputStream(in2b)  , out1);
//	
//		 out1.flush();
//
//		 out1.close();

		return in2b;
	}
	
	/**
	 * 读取字节流
	 * @param inStream
	
	 * @return
	 * @throws IOException
	 */
	public static final byte[] input3byte(GridFSDBFile file) throws IOException {
		
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		
		InputStream  stream;
		// 解压文件
		if ("gzip".equals(file.getContentType())) {
			 stream=new GZIPInputStream(file.getInputStream());
		}else {
			stream=file.getInputStream();
		}
		byte[] buff = new byte[1014];
		int rc = 0;
		while ((rc = stream.read(buff)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/**
	 * 读取字节流
	 * @param inStream
	
	 * @return
	 * @throws IOException
	 */
	public static final int getFileLength(GridFSDBFile file) throws IOException {
		
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		
		InputStream  stream;
		// 解压文件
		if ("gzip".equals(file.getContentType())) {
			 stream=new GZIPInputStream(file.getInputStream());
		}else {
			stream=file.getInputStream();
		}
		byte[] buff = new byte[1014];
		int rc = 0;
		while ((rc = stream.read(buff)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		return swapStream.toByteArray().length;
	}
	
	public final static int getInt(byte[] buf, boolean asc)

	{

		if (buf == null)

		{

			throw new IllegalArgumentException("byte array is null!");

		}

		if (buf.length > 4)

		{

			throw new IllegalArgumentException("byte array size > 4 !");

		}

		int r = 0;

		if (asc)

			for (int i = buf.length - 1; i >= 0; i--)

			{

				r <<= 8;

				r |= (buf[i] & 0x000000ff);

			}

		else

			for (int i = 0; i < buf.length; i++)

			{

				r <<= 8;

				r |= (buf[i] & 0x000000ff);

			}

		return r;

	}

	public final static short getShort(byte[] buf, boolean asc)

	{

		if (buf == null)

		{

			throw new IllegalArgumentException("byte array is null!");

		}

		if (buf.length > 2)

		{

			throw new IllegalArgumentException("byte array size > 2 !");

		}

		short r = 0;

		if (asc)

			for (int i = buf.length - 1; i >= 0; i--)

			{

				r <<= 8;

				r |= (buf[i] & 0x00ff);

			}

		else

			for (int i = 0; i < buf.length; i++)

			{

				r <<= 8;

				r |= (buf[i] & 0x00ff);

			}

		return r;

	}
	/** 
     * 通过byte数组取到short 
     *  
     * @param b 
     * @param index 
     *            第几位开始取 
     * @return 
     */  
    public static short getShort(byte[] b, int index) {  
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));  
    }  

	public static void getSeries() throws IOException {
		XYSeries series = new XYSeries("ecg");
		 FileInputStream stream = new FileInputStream("E:/20150427161440ecg.txt");
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			
		
			byte[] buff = new byte[1014];
			int rc = 0;
			while ((rc = stream.read(buff)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in2b = swapStream.toByteArray();
			
			byte[] b = new byte[2];
			int j=0;
			for (int i = 0; i < 250*12; i += 2) {
				if (i < in2b.length-1) {
					j += 2;
					b[0] = in2b[i];
					b[1] = in2b[i + 1];
					series.add(j, Util.getShort(b, false));
					System.out.println(Util.getShort(b, false));;
				} else {
					break;
				}

			}
			final XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			JFreeChart chart=JfreeChartUtil.createXYChart(dataset, "20140607111111",
					null, null, false, 250);
			 File file2 = new File("E:/test.png");  
             ChartUtilities.saveChartAsPNG(file2, chart, 1000,400);   
			
			
		
	}
	


	public static Object getFieldValueByName(String fieldName, Object o) throws Exception {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
	}

	public static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}
	
	
	
	public static String stringFormatForJson(String s) {    
        StringBuffer sb = new StringBuffer ();     
        for (int i=0; i<s.length(); i++) {     
      
            char c = s.charAt(i);     
            switch (c) {     
            case '\"':     
                sb.append("\\\"");     
                break;     
//          case '\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
//              sb.append("\\\\");     
//               break;     
            case '/':     
                sb.append("\\/");     
                break;     
            case '\b':      //退格
                sb.append("\\b");     
                break;     
            case '\f':      //走纸换页
                sb.append("\\f");     
                break;     
            case '\n':     
                sb.append("\\n"); //换行    
                break;     
            case '\r':      //回车
                sb.append("\\r");     
                break;     
            case '\t':      //横向跳格
                sb.append("\\t");     
                break;     
            default:     
                sb.append(c);    
            }}
        return sb.toString();     
     }
	
	
	public static boolean isEquals(Object obj1,Object obj2){
		if(obj1 != null && obj1.equals(obj2)){
			return true;
		}else{
			return false;
		}
		
	}


	public static void main(String[] args) throws IOException {
		String a = "";
		getSeries();
	}

}
