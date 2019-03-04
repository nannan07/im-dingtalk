package com.allmsi.dingtalk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allmsi.auth.dao.UserMapper;
import com.allmsi.auth.model.po.DingTalkSnsUser;
import com.allmsi.auth.model.po.User;
import com.allmsi.auth.service.LoginService;
import com.allmsi.dingtalk.helper.AuthHelper;
import com.allmsi.dingtalk.service.DingLoginService;
import com.allmsi.dingtalk.utils.Env;
import com.allmsi.dingtalk.utils.HttpHelper;
import com.allmsi.dingtalk.utils.OApiException;

@Service
public class DingLoginServiceImpl implements DingLoginService {

	@Resource
	private UserMapper userDao;

	@Resource
	private LoginService loginService;

	@Override
	public Object dingLogin(String code, String state) {
		String url1 = "https://oapi.dingtalk.com/sns/gettoken?appid=" + Env.APP_Id + "&appsecret=" + Env.APP_SECRET;
		JSONObject response = null;
		try {
			response = HttpHelper.httpGet(url1);
		} catch (OApiException e1) {
			e1.printStackTrace();
		}

		String accesstoken = response.getString("access_token").toString();
		String url3 = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token=" + accesstoken;
		// 封装临时授权码persistent_code，openid
		JSONObject jsonObject3_1 = new JSONObject();
		jsonObject3_1.put("tmp_auth_code", code);
		JSONObject response1 = null;
		try {
			response1 = HttpHelper.httpPost(url3, jsonObject3_1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String persistent_code = response1.getString("persistent_code").toString();
		String openid = response1.getString("openid").toString();
		System.out.println("openid---" + openid);
		// 获取用户授权的SNS_TOKEN
		String snsTokenUrl = "https://oapi.dingtalk.com/sns/get_sns_token?access_token=" + accesstoken;
		JSONObject snsTokenJson = new JSONObject();
		snsTokenJson.put("openid", openid);
		snsTokenJson.put("persistent_code", persistent_code);
		JSONObject snsResponse = null;
		try {
			snsResponse = HttpHelper.httpPost(snsTokenUrl, snsTokenJson);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String snsToken = snsResponse.getString("sns_token").toString();
		// 获取unionid
		String unionidUrl = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token=" + snsToken;
		JSONObject unionidResponse = null;
		try {
			unionidResponse = HttpHelper.httpGet(unionidUrl);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String userInfo = unionidResponse.getString("user_info").toString();
		DingTalkSnsUser user = JSON.parseObject(userInfo, DingTalkSnsUser.class);

		// 根据unionid获取成员的userid
		String acc_token = null;
		try {
			acc_token = AuthHelper.getAccessToken();
		} catch (OApiException e1) {
			e1.printStackTrace();
		}
		String userUrl = "https://oapi.dingtalk.com/user/getUseridByUnionid?access_token=" + acc_token + "&unionid="
				+ user.getUnionid();
		JSONObject userResponse = null;
		try {
			userResponse = HttpHelper.httpGet(userUrl);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String userid = userResponse.getString("userid").toString();
		User userInfoIndex = userDao.selectByPrimaryKey(userid);
		if (userInfoIndex == null) {
			return null;
		}
		return loginService.getLoginInfo("mis", userInfoIndex);
	}
}
