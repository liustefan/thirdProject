package com.withub.test;

import com.alibaba.fastjson.JSON;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.ReturnValue;

public class JsonTest {

	public static void main(String[] args) {
	    int[] arr = new int[]{8,2,1,0,3};
	    int[] index = new int[]{2,0,3,2,4,0,1,3,2,3,3};
	    String tel = "";
	    for(int i : index){
	        System.out.println("i="+ i);
	        System.out.println("idex="+index[i]);
	        tel += arr[i];
	    }
	    System.out.println("tel++++" + tel);
		MemLog log=new MemLog();
		log.setPassWord("123456");
		log.setDevice("ios");
		log.setUserAccount("15915450821");
		CallValue callValue=new CallValue();
	 
	    callValue.setParam(JSON.toJSONString(log));
	    callValue.setVersion("V1.0");
	    callValue.setLoginLog("2032323232");
	    String params=JSON.toJSONString(callValue);
	    System.out.println(params);
	    
	    
//	   ReturnValue returnValue=new ReturnValue(); 
//	   returnValue.setState(0);
//	   returnValue.setContent("{\"memberId\": 5,\"session\": \" ad=8829e2711ec*c*6b6f665046c*373a370\"}");
//	   returnValue.setMessage( "18028721432登入成功");
//	   System.out.println(JSON.toJSONString(returnValue));
//	   returnValue=JSON.parseObject(JSON.toJSONString(returnValue), ReturnValue.class);
//	   System.out.println(returnValue.getContent());
	}
}
