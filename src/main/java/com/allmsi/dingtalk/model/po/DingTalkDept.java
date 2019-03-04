package com.allmsi.dingtalk.model.po;

import java.util.Date;

import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

public class DingTalkDept implements Cloneable {

	private String id;

	private String deptCode;

	private String deptName;

	private String parentId;

	private String phone;

	private String corporation;

	private String description;

	private String address;

	private String bLicenceId;

	private Integer deptType;

	private Integer sort;

	private String cUserId;

	private String uUserId;

	private Date cTime;

	private Date uTime;

	private int del;

	private String udType;

	private String dingDeptId;

	private String systemId;

	public DingTalkDept() {

	}

	public DingTalkDept(DepartmentDetail departmentDetail) {
		if (departmentDetail != null) {
			this.id = Long.toString(departmentDetail.getId());
			this.deptName = departmentDetail.getName();
			if (departmentDetail.getParentid() != null) {
				this.parentId = Long.toString(departmentDetail.getParentid());
			}
			this.sort = departmentDetail.getOrder().intValue();
			this.dingDeptId = Long.toString(departmentDetail.getId());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getbLicenceId() {
		return bLicenceId;
	}

	public void setbLicenceId(String bLicenceId) {
		this.bLicenceId = bLicenceId;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getUdType() {
		return udType;
	}

	public void setUdType(String udType) {
		this.udType = udType;
	}

	public String getDingDeptId() {
		return dingDeptId;
	}

	public void setDingDeptId(String dingDeptId) {
		this.dingDeptId = dingDeptId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		DingTalkDept dingTalkDept = (DingTalkDept) obj;
		
		if (this.deptName != null) {
			if (!this.deptName.equals(dingTalkDept.getDeptName())) {
				return false;
			}
		} else {
			if (dingTalkDept.getDeptName() != null) {
				return false;
			}
		}
		
		if (this.parentId != null) {
			if (!this.parentId.equals(dingTalkDept.getParentId())) {
				return false;
			}
		} else {
			if (dingTalkDept.getParentId() != null) {
				return false;
			}
		}
		
		if (this.dingDeptId != null) {
			if (!this.dingDeptId.equals(dingTalkDept.getDingDeptId())) {
				return false;
			}
		} else {
			if (dingTalkDept.getDingDeptId() != null) {
				return false;
			}
		}
		
		if (this.sort != null) {
			if (!this.sort.equals(dingTalkDept.getSort())) {
				return false;
			}
		} else {
			if (dingTalkDept.getSort() != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "DingTalkDept [id=" + id + ", deptCode=" + deptCode + ", deptName=" + deptName + ", parentId=" + parentId
				+ ", phone=" + phone + ", corporation=" + corporation + ", description=" + description + ", address="
				+ address + ", bLicenceId=" + bLicenceId + ", deptType=" + deptType + ", sort=" + sort + ", cUserId="
				+ cUserId + ", uUserId=" + uUserId + ", cTime=" + cTime + ", uTime=" + uTime + ", del=" + del
				+ ", udType=" + udType + ", dingDeptId=" + dingDeptId + ", systemId=" + systemId + "]";
	}
}