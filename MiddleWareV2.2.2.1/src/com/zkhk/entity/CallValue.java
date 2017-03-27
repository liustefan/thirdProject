package com.zkhk.entity;

public class CallValue {

	//版本号
	private String version;
	 

	
	//登入令牌
	private String session;
	
	//具体参数
	private String param;
	
    //会员id
	private  int memberId;
	
	//登入日志信息（用于行为记录）
	private String loginLog;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}






	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}


	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(String loginLog) {
		this.loginLog = loginLog;
	}
	
	
	
	
	
}
