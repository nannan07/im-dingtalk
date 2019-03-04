package com.allmsi.dingtalk.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.dingtalk.service.DingLoginService;
import com.allmsi.dingtalk.utils.Env;
import com.allmsi.dingtalk.utils.SuccessProtocol;
import com.allmsi.dingtalk.utils.WarnProtocol;
import com.allmsi.sys.util.StrUtil;

import ch.qos.logback.core.util.TimeUtil;

@Controller
public class DingLoginController {

	@Resource
	DingLoginService dingLoginService;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/login/v")
	public void toALiDingDing(HttpServletResponse response) {
		// 这是我自己写的一个获取时间戳的Util，前期大家可以不管这个STATE
		TimeUtil timeUtil = new TimeUtil();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://oapi.dingtalk.com/connect/qrconnect?appid=" + Env.APP_Id + "&")
				.append("response_type=code&scope=snsapi_login&state=").append(timeUtil.computeStartOfNextDay(1000))
				.append("&redirect_uri=").append("http://localhost:8601/ding/login");
		try {
			response.sendRedirect(stringBuilder.toString());
			System.out.println(response);
		} catch (IOException e1) {
		}
	}

	@SuppressWarnings("static-access")
	public void toALiDingDing1(HttpServletResponse response) {
		// 这是我自己写的一个获取时间戳的Util，前期大家可以不管这个STATE
		TimeUtil timeUtil = new TimeUtil();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=" + Env.APP_Id + "&")
				.append("response_type=code&scope=snsapi_login&state=").append(timeUtil.computeStartOfNextDay(1000))
				.append("&redirect_uri=").append("http://localhost:8082/mis/login/test");
		try {
			response.sendRedirect(stringBuilder.toString());
		} catch (IOException e1) {
		}
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public Object toALiDingDing(String code, String state) {
		if (StrUtil.isEmpty(state) || StrUtil.isEmpty(code)) {
			return new WarnProtocol();
		}
		return new SuccessProtocol("", dingLoginService.dingLogin(code, state));
	}
}