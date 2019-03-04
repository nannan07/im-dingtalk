package com.allmsi.dingtalk.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allmsi.dingtalk.utils.Env;
import com.allmsi.dingtalk.utils.HttpHelper;
import com.allmsi.dingtalk.utils.OApiException;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserDetailList;
import com.dingtalk.open.client.api.model.corp.Department;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

public class DepartmentDingApi {

	private static final String DEPT_LIST = "/department/list?access_token=";

	private static final String DEPT_INFO = "/department/get?access_token=";

	private static final String DEPT_USER = "/user/list?access_token=";
	
	private static final String USER_INFO="/user/get?access_token=";
	
	/**
	 * 获取部门ID
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<Long> listDepartmentsId(String accessToken){
		String url = Env.OAPI_HOST + DEPT_LIST + accessToken;
		JSONObject response = null;
		try {
			response = HttpHelper.httpGet(url);
		} catch (OApiException e) {
		}
		String json = response.getString("department").toString();
		List<Department> list = JSON.parseArray(json, Department.class);
		List<Long> listDepartmentsId = new ArrayList<Long>();
		for (Department department : list) {
			listDepartmentsId.add(department.getId());
		}
		return listDepartmentsId;
	}

	/**
	 * 获取部门列表
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<DepartmentDetail> listDepartmentDetail(String accessToken) {
		List<DepartmentDetail> departmentDetailList = new ArrayList<DepartmentDetail>();
		 List<Long> listDeptId = listDepartmentsId(accessToken);
		for (Long id : listDeptId) {
			departmentDetailList.add(getDepartmentInfo(accessToken, id));
		}
		return departmentDetailList;
	}

	/**
	 * 获取但各部门信息
	 * 
	 * @param accessToken
	 * @param id
	 * @return
	 */
	public static DepartmentDetail getDepartmentInfo(String accessToken, Long id) {
		String url = Env.OAPI_HOST + DEPT_INFO + accessToken + "&id=" + id;
		JSONObject response = null;
		try {
			response = HttpHelper.httpGet(url);
		} catch (OApiException e) {
		}
		String json = response.toJSONString();
		DepartmentDetail departmentDetail = JSON.parseObject(json, DepartmentDetail.class);
		return departmentDetail;
	}

	/**
	 * 获取部门成员（详情）
	 */
	public static List<CorpUserDetail> getUserList(String accessToken, String deptId) {
		String url = Env.OAPI_HOST + DEPT_USER + accessToken + "&department_id=" + deptId;
		JSONObject response = null;
		try {
			response = HttpHelper.httpGet(url);
		} catch (OApiException e) {
			
		}
		String json = response.toJSONString();
		CorpUserDetailList corpUserDetailList = JSON.parseObject(json, CorpUserDetailList.class);
		List<CorpUserDetail> list = corpUserDetailList.getUserlist();
		return list;
	}

	public static CorpUserDetail getUserInfo(String accessToken, String userid) {
		String url = Env.OAPI_HOST + USER_INFO + accessToken + "&userid=" + userid;
		JSONObject response = null;
		try {
			response = HttpHelper.httpGet(url);
		} catch (OApiException e) {
			
		}
		CorpUserDetail adminUserInfo = null;
		if(response != null) {
			String json = response.toJSONString();
			adminUserInfo = JSON.parseObject(json, CorpUserDetail.class);
		}
		return adminUserInfo;
	}

	@SuppressWarnings("unused")
	public static List<String> listdimission(String accessToken) {
		String url = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/querydimission?access_token="+accessToken+"&offset=0&size=50";
		JSONObject response = null;
		try {
			response = HttpHelper.httpPost(url, null);
		} catch (OApiException e) {
			
		}
		if(response != null) {
			String json = response.getString("result");
		}
		return null;
	}

}
