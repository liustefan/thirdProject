package com.zkhk.entity;

/**
 * 会员登入的信息
 * @author raojunming
 *
 */

public class MemLog {

	private String userAccount;//用户账号
	
	private String passWord;   //用户密码
	
	private String device;     //用户的登入的终端设备   

	private int memberId;      //会员id
	
	private String session;    //令牌
	
	private String loginTime;   //登入时间
	
	private String curTag;   //
	
	/* 当前登录医生的名字 */
	private String doctorName;
	
	/* 当前登录医生的GUID */
    private String doctorGUID;
    
    /* 当前登录会员的GUID */
    private String memberGUID;
    
    /* 当前登录医生的头像id */
    private String doctorHeadImg;
	
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}


	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getCurTag() {
		return curTag;
	}

	public void setCurTag(String curTag) {
		this.curTag = curTag;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

    public String getDoctorGUID() {
        return doctorGUID;
    }

    public void setDoctorGUID(String doctorGUID) {
        this.doctorGUID = doctorGUID;
    }

    public String getMemberGUID() {
        return memberGUID;
    }

    public void setMemberGUID(String memberGUID) {
        this.memberGUID = memberGUID;
    }

    public String getDoctorHeadImg() {
        return doctorHeadImg;
    }

    public void setDoctorHeadImg(String doctorHeadImg) {
        this.doctorHeadImg = doctorHeadImg;
    }
	
	
	
	
	
}
