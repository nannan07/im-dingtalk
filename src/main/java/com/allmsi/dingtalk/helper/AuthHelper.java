package com.allmsi.dingtalk.helper;

import com.alibaba.fastjson.JSONObject;
import com.allmsi.dingtalk.utils.Env;
import com.allmsi.dingtalk.utils.FileUtils;
import com.allmsi.dingtalk.utils.HttpHelper;
import com.allmsi.dingtalk.utils.OApiException;

/**
 * AccessToken和jsticket的获取封装
 */
public class AuthHelper {

	// 调整到1小时50分钟
	public static final long cacheTime = 1000 * 60 * 55 * 2;

	private static final String ACCESS_TOKEN = "https://oapi.dingtalk.com/gettoken?corpid=";

	/*
	 * 在此方法中，为了避免频繁获取access_token， 在距离上一次获取access_token时间在两个小时之内的情况，
	 * 将直接从持久化存储中读取access_token
	 *
	 * 因为access_token和jsapi_ticket的过期时间都是7200秒 所以在获取access_token的同时也去获取了jsapi_ticket
	 * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的 具体信息请查看开发者文档--权限验证配置
	 */
	public static String getAccessToken() throws OApiException {
		long curTime = System.currentTimeMillis();
		JSONObject accessTokenValue = (JSONObject) FileUtils.getValue("access_token", Env.CORP_ID);
		String accToken = "";
		JSONObject jsontemp = new JSONObject();
		if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
			try {
				String url = ACCESS_TOKEN + Env.CORP_ID + "&corpsecret=" + Env.CORP_SECRET;
				JSONObject response = null;
				try {
					response = HttpHelper.httpGet(url);
				} catch (OApiException e) {
					e.printStackTrace();
				}
				accToken = response.getString("access_token").toString();
				// save accessToken
				JSONObject jsonAccess = new JSONObject();
				jsontemp.clear();
				jsontemp.put("access_token", accToken);
				jsontemp.put("begin_time", curTime);
				jsonAccess.put(Env.CORP_ID, jsontemp);
				// 真实项目中最好保存到数据库中
				FileUtils.write2File(jsonAccess, "accesstoken");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return accessTokenValue.getString("access_token");
		}
		return accToken;
	}
}
