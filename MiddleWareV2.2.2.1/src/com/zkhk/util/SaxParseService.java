package com.zkhk.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public  class  SaxParseService extends DefaultHandler{
	private String values ;
	private String tagname=null;
	private String  key ;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public  static String getXmlValue(InputStream xmlStream) throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		SaxParseService handler = new SaxParseService();
		parser.parse(xmlStream, handler);
		return handler.getValues();
	}
	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTagname() {
		return tagname;
	}







	public void setTagname(String tagname) {
		this.tagname = tagname;
	}







	@Override
	public void startDocument() throws SAXException {
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.tagname=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	
	
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
			String content = new String(ch,start,length);
			if(key.equals(tagname)){
				values=content;
			}
		
	}
	
public static void main(String[] args) throws Exception {
String itemInfo="<ItemInfo><工单编号>BP-20130808-162252</工单编号><申请部门>系统管理部</申请部门><申请人>艾迪安</申请人><手机号码>15989896122</手机号码><短号>null</短号><申请日期>2013-08-08 16:23:05</申请日期><故障类型>个人固定资产故障</故障类型><资产编码>CMSZ001891</资产编码><资产类别>投影仪</资产类别><资产品牌>HP</资产品牌><资产型号>TLP-X2500A</资产型号><存放地点>1102</存放地点><故障类别>办公设备故障</故障类别><故障情况>投影仪异常</故障情况><故障地点>请选择</故障地点><卡座端口号>null</卡座端口号><申请原因及具体需求>null</申请原因及具体需求><采购编号>null</采购编号><采购状态>null</采购状态><受理时间>2013-10-31</受理时间></ItemInfo>";

	System.out.println(SaxParseService.getKeyValue(itemInfo, "工单编号"));
	System.out.println(SaxParseService.getKeyValue(itemInfo, "申请部门"));
}

public  static String getKeyValue(String itemInfo,String key) throws Exception{
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser parser = factory.newSAXParser();
	SaxParseService handler = new SaxParseService();
	InputStream inputStream= new  ByteArrayInputStream(itemInfo.getBytes("utf-8"));  
	handler.setKey(key);
	parser.parse(inputStream, handler);
	return handler.getValues();	
}
}
