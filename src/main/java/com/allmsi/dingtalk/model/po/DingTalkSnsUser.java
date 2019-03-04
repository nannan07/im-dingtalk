package com.allmsi.dingtalk.model.po;

public class DingTalkSnsUser {

	private String maskedMobile;
	
	private String nick;
	
	private String openid;
	
	private String unionid;
	
	private String dingId;

	public String getMaskedMobile() {
		return maskedMobile;
	}

	public void setMaskedMobile(String maskedMobile) {
		this.maskedMobile = maskedMobile;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getDingId() {
		return dingId;
	}

	public void setDingId(String dingId) {
		this.dingId = dingId;
	}
	
}
