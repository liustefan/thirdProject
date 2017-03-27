package com.zkhk.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



import com.alibaba.fastjson.JSON;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Focus;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.OmdsParam;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.OsmrParam;
import com.zkhk.entity.Ouai;
import com.zkhk.entity.AnswerParam;
import com.zkhk.entity.RecordParam;
import com.zkhk.entity.Uai21;
import com.zkhk.entity.ViewParam;



/**
 * HTTP请求工具类
 * @author bit
 *
 */
public class HttpRequest  implements Serializable{
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
          
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", "text/html");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            // 建立实际的连接
            conn.connect();
            Map headers = conn.getHeaderFields();
            Set<String> keys = headers.keySet();
            for( String key : keys ){
                String val = conn.getHeaderField(key);
                System.out.println(key+"    "+val);
               }       
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader( conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
               // System.out.println(line);
            }
         // System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            Map headers = conn.getHeaderFields();
            Set<String> keys = headers.keySet();
            for( String key : keys ){
                String val = conn.getHeaderField(key);
                System.out.println(key+"    "+val);
               }           

            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
             conn.setRequestProperty("Content-type", "text/html");
			 conn.setRequestProperty("Accept-Charset", "UTF-8");
			 conn.setRequestProperty("contentType", "UTF-8");
			 conn.setRequestProperty("params", params);
	 

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            
            out = new PrintWriter(conn.getOutputStream());
            
            // 发送请求参数
             out.print(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            //	System.out.println(line);
                result += line;
            }
        } catch (Exception e) {
           // System.out.println("发送 POST 请求出现异常！"+e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    public static void main(String[] args) {
    	//String params="?params={\"memberId\":1,\"session\":\"40f054c*b6244f63d=386c*c*c*3e96d=f4b01\",\"version\":\"V1.0\",\"loginLog\":\"2014-03-0212:12:12……\"}" ;
    	MemLog log=new MemLog();
		log.setPassWord(PasswordEncryption.getMD5Password("123456"));
		log.setDevice("ios");
		log.setUserAccount("15915450821");
    	/**---------------查询会员测量信息1参数--------------- **/
//    	OmdsParam param=new OmdsParam();
//    	param.setCount(5);
//    	param.setDataType(2);
//    	param.setEndTime("20151021143021");
//    	param.setEventId(90);
//    	param.setMemberId(1);
//    	param.setTimeStart("20141021143021");
//    	param.setWheAbnTag("0");
       	/**---------------查询会员测量信息2参数--------------- **/
// 	RecordParam param=new RecordParam();
//    	param.setMeasureEnd("20151021143021");
//    	param.setMeasureStart("20141021143021");
//    	//测量类型:ECG,PPG,BP,BS
//    	param.setMeasureType("PPG");
//    	param.setMemberId(1);
    	
     	/**---------------查询会员汇总测量信息参数--------------- **/
//         OsmrParam param=new OsmrParam();
//         param.setCount(5);
//         param.setTimeStart("20141021143021");
//         param.setTimeEnd("20151021143021");
//         param.setMemberId(95);
    	/**---------------查询会员答卷信息参数--------------- **/
//      AnswerParam param=new AnswerParam();
//      param.setCount(5);
//      param.setTimeStart("20141021143021");
//      param.setTimeEnd("20151021143021");
//      param.setMemberId(95);
  /**-------------血压上传数据-------------------------**/    
//		Osbp osbp=new Osbp();
//		osbp.setMemberId(95);
//		osbp.setTestTime("20141021143021");
//		osbp.setTimePeriod(1);
//		osbp.setSbp(120);
//		osbp.setDbp(79);
//        osbp.setPulseRate(80);
//        osbp.setBluetoothMacAddr("0F:6D:3D:3E:9C:00");
//        osbp.setDeviceCode("中兴血压计");
/**-----------------查询图片-------------------------**/
      ViewParam param=new ViewParam();
       param.setFs(250);
     //ecg
       //param.setRawImage("556ed28f9172e46e517facac");
      //  556961d0988dfeea9a850eb0
   //   ppg
          param.setRawImage("555409da67ff072ac5465e37");
//       
     //  hr_ecg
                           
        param.setRawImage("5570ff1b98dd7c6dd554c390");
       param.setType("ecg");
//       
       //hr_ppg
//       param.setRawImage("556ecafd91724e3876d48a26");
//       param.setType("hr_ppg");
//       param.setPage(6);
        param.setMeasureTime("20150416121212");
       
       //ab_ecg
      
      // param.setRawImage("552dcaf8a82ddd81c34c6f6c");
    // param.setType("ppg");
    param.setPage(1);
       
   /**-------------上传答案--------------------------**/   
//		List<Uai21> list=new ArrayList<Uai21>();
//		Uai21 uai211=new Uai21();
//		uai211.setAnsId(1);
//		uai211.setAnsNumber(2);
//		uai211.setFillblank("11111111");
//		uai211.setProblemId(2);
//		list.add(uai211);
//		Uai21 uai2=new Uai21();
//		uai2.setAnsId(1);
//		uai2.setAnsNumber(3);
//		uai2.setProblemId(3);
//		list.add(uai211);
		CallValue callValue=new CallValue();
//       callValue.setParam(JSON.toJSONString(param));
//		callValue.setMemberId(26017);
	    callValue.setParam("{\"membeId\":\"102\",\"messageType\":\"102\"}");
		//callValue.setParam("");
//		callValue.setSession("acf58d5e-6b9a-49b7-8d5d-112a235ceea9");
//		callValue.setMemberId(25914);
//	    callValue.setVersion("V1.0");
//	    callValue.setLoginLog("2032323232");
/**-----------修改关注信息---------------**/
//	    Focus mem=new Focus();
//	    mem.setFocusPed("1");
//	    mem.setRelation("FRIENT");
//	    mem.setId(1);
//	    mem.setFocusStatus(2);
//	    mem.setTag("N");
//	    mem.setFocusP("1,2");
	   // callValue.setParam("{\"focusId\":1}");
	    
	    String params=JSON.toJSONString(callValue);
	    System.out.println(params);
	   //  System.out.println(params);
    	// String params="?params={\"version\":\"v1.0\",\"loginLog\":\"20140302121212\",\"param\":\"{\"userAccount\":\"15915450821\",\"passWord\":\"123456\",\"device\":\"IOS\"}\"}";
//    	 params =params .replace("\"", "%22")  
//        	     .replace("{", "%7b").replace("}", "%7d");   //特殊字符进行转义  
	    try {
			  params   =   URLDecoder.decode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}   
       // String  testUrl="http://localhost:8081/MiddleWare/measure/measureLine?params="+params;
       String  testUrl="http://localhost:8081/MiddleWare/message/saveMessage?params="+params;
      // String  testUrl="http://localhost:8081/MiddleWare/answer/uploadResult?params="+params;
       System.out.println(testUrl);
//		 // String baidu_url=  "http://www.yg1999.com:82/mea/gps!saveMeaGpsInitiative.do?history.mgiGid=12345678901&history.mgiPoint=106.50247,29.61336&history.mgiSpeed=50&history.mgiDate=20120510060657&history.mgiDirectuin=230&history.mgiStatus=1,2,3,4&history.mgiTotalDistance=1200&history.mgiAlarm=1&history.mgiOilPercent=30";
//         System.out.println(params);
    System.out.println(HttpRequest.sendGet(testUrl));  
	}
}
