package com.allmsi.dingtalk.helper;

import java.util.List;

import com.allmsi.dingtalk.api.DepartmentDingApi;
import com.allmsi.dingtalk.utils.OApiException;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

/**
 * 部门相关API
 *
 * https://open-doc.dingtalk.com/docs/doc.htm?treeId=371&articleId=106817&docType=1
 */
public class DepartmentHelper {

	/**
	 * 获取部门ID
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<Long> listDepartmentsId() {
		try {
			return DepartmentDingApi.listDepartmentsId(AuthHelper.getAccessToken());
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取部门列表
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<DepartmentDetail> listDepartmentDetail() {
		try {
			return DepartmentDingApi.listDepartmentDetail(AuthHelper.getAccessToken());
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取但各部门信息
	 * 
	 * @param accessToken
	 * @param id
	 * @return
	 */
	public static DepartmentDetail getDepartmentInfo(Long id) {
		try {
			return DepartmentDingApi.getDepartmentInfo(AuthHelper.getAccessToken(), id);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取部门成员（详情）
	 */
	public static List<CorpUserDetail> getUserList(String deptId) {
			try {
				return DepartmentDingApi.getUserList(AuthHelper.getAccessToken(), deptId);
			} catch (OApiException e) {
				e.printStackTrace();
			}
			return null;
	}

	public static CorpUserDetail getUserInfo(String userid) {
		try {
			return DepartmentDingApi.getUserInfo(AuthHelper.getAccessToken(), userid);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询企业离职员工列表
	 * @return
	 */
	public static List<String> listdimission() {
		try {
			return DepartmentDingApi.listdimission(AuthHelper.getAccessToken());
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		listdimission();
	}
}
