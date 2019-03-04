package com.allmsi.dingtalk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.dingtalk.helper.DepartmentHelper;
import com.allmsi.dingtalk.model.po.DingTalkDept;
import com.allmsi.dingtalk.service.DingUserService;
import com.allmsi.dingtalk.utils.SuccessProtocol;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

@Controller
public class DingTalkcontroller {

	@Resource
	private DingUserService userService;

	@RequestMapping(value = "/deptId/list", method = RequestMethod.GET)
	public Object listDepartmentsId(Model model) {
		List<Long> deptIds = DepartmentHelper.listDepartmentsId();
		model.addAttribute("deptIds", deptIds);
		return "hello";
	}

	@RequestMapping(value = "/deptDetail/List", method = RequestMethod.GET)
	public Object listDepartmentDetail(Model model) {
		List<DepartmentDetail> list = DepartmentHelper.listDepartmentDetail();
		List<DingTalkDept> newList = new ArrayList<DingTalkDept>();
		for (DepartmentDetail departmentDetail : list) {
			if (departmentDetail != null) {
				newList.add(new DingTalkDept(departmentDetail));
			}
		}
		model.addAttribute("deptList", newList);
		return "deptList";
	}

	@RequestMapping(value = "/dept/info", method = RequestMethod.GET)
	@ResponseBody
	public Object getDepartmentInfo(Long id) {
		return new SuccessProtocol(DepartmentHelper.getDepartmentInfo(id));
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	@ResponseBody
	// 获取部门下成员
	public Object getuserbydepart(String deptId) {
		return new SuccessProtocol(DepartmentHelper.getUserList(deptId));
	}

	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	@ResponseBody
	public Object userInfo(String userid) {
		return new SuccessProtocol(userService.userInfo(userid));
	}
}
