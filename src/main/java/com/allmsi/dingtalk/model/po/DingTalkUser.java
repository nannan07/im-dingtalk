package com.allmsi.dingtalk.model.po;

import java.util.Date;

import com.allmsi.sys.util.MD5Util;
import com.allmsi.sys.util.StrUtil;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;

public class DingTalkUser {

	private String id;

	private String loginName;

	private String userName;

	private String password;

	private String phone;

	private String email;

	private Integer sex;

	private Integer sort;

	private String cUserId;

	private String uUserId;

	private Date cTime;

	private Date uTime;

	private int del;

	private String dingUserId;

	private String orgEmail;

	public DingTalkUser() {

	}

	public DingTalkUser(CorpUserDetail corpUserDetail) {
		if (corpUserDetail != null) {
			this.id = corpUserDetail.getUserid();
			this.password = MD5Util.encode("lssj123");
			this.loginName = corpUserDetail.getName();
			this.userName = corpUserDetail.getName();
			this.phone = corpUserDetail.getMobile();
			// this.email = corpUserDetail.getEmail();
			this.dingUserId = corpUserDetail.getUserid();
			String jobnumber = corpUserDetail.getJobnumber().substring(3);
			if (StrUtil.notEmpty(jobnumber)) {
				this.sort = Integer.valueOf(jobnumber);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getcUserId() {
		return cUserId;
	}

	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}

	public String getuUserId() {
		return uUserId;
	}

	public void setuUserId(String uUserId) {
		this.uUserId = uUserId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getDingUserId() {
		return dingUserId;
	}

	public void setDingUserId(String dingUserId) {
		this.dingUserId = dingUserId;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		DingTalkUser dingTalkUser = (DingTalkUser) obj;
		if (this.loginName != null) {
			if (!this.loginName.equals(dingTalkUser.getLoginName())) {
				return false;
			}
		} else {
			if (dingTalkUser.getLoginName() != null) {
				return false;
			}
		}

		if (this.userName != null) {
			if (!this.userName.equals(dingTalkUser.getUserName())) {
				return false;
			}
		} else {
			if (dingTalkUser.getUserName() != null) {
				return false;
			}
		}

		if (this.phone != null) {
			if (!this.phone.equals(dingTalkUser.getPhone())) {
				return false;
			}
		} else {
			if (dingTalkUser.getPhone() != null) {
				return false;
			}
		}

		if (this.dingUserId != null) {
			if (!this.dingUserId.equals(dingTalkUser.getDingUserId())) {
				return false;
			}
		} else {
			if (dingTalkUser.getDingUserId() != null) {
				return false;
			}
		}

		if (this.sort != null) {
			if (!this.sort.equals(dingTalkUser.getSort())) {
				return false;
			}
		} else {
			if (dingTalkUser.getSort() != null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "DingTalkUser [id=" + id + ", loginName=" + loginName + ", userName=" + userName + ", password="
				+ password + ", phone=" + phone + ", email=" + email + ", sex=" + sex + ", sort=" + sort + ", cUserId="
				+ cUserId + ", uUserId=" + uUserId + ", cTime=" + cTime + ", uTime=" + uTime + ", del=" + del
				+ ", dingUserId=" + dingUserId + ", orgEmail=" + orgEmail + "]";
	}
	
}