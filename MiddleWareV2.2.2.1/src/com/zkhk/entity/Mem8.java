package com.zkhk.entity;
/**
 * 活力积分
 * @author liuxiaoqin
 *
 */
public class Mem8 {
	
	//id
    private int id;
    
    //会员id
    private  int memberId;
    
    //每次得分
    private int score;
    
    //上传时间
    private String uploadTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

}
