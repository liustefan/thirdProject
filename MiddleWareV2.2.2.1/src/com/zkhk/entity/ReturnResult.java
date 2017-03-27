package com.zkhk.entity;

/**
 * @author xiemt
 *
 */
public class ReturnResult {

	
	
	//状态值
	private int state = 0;
	
	//具体内容
	private Object content = new Object();
	
	//描述信息
	private String message ;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
