package com.allmsi.dingtalk.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.dingtalk.service.DingTalkService;
import com.allmsi.dingtalk.utils.SuccessProtocol;

@Controller
public class SysController {

	@Resource
	DingTalkService deptService;
	
	@RequestMapping(value = "/dept/insert", method = RequestMethod.GET)
	@ResponseBody
	public Object insertDept() {
		return new SuccessProtocol("成功",deptService.insertDept());
	}
	
	@RequestMapping(value = "/user/insert", method = RequestMethod.GET)
	@ResponseBody
	public Object insertUser() {
		return new SuccessProtocol("成功",deptService.insertUser());
	}

}
