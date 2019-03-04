package com.allmsi.dingtalk.model.po;

public class DingTalkUserDept {

	private String id;

	private String userId;

	private String deptId;

	private String udType;
	
	public DingTalkUserDept() {

	}

	public DingTalkUserDept(String id, String userId, Long deptId, String udType) {
		this.id = id;
		this.userId = userId;
		this.deptId = Long.toString(deptId);
		this.udType = udType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUdType() {
		return udType;
	}

	public void setUdType(String udType) {
		this.udType = udType;
	}
}