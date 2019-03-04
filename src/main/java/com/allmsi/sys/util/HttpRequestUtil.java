package com.allmsi.sys.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if ((ip != null && !ip.equals("")) && !"unKnown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if ((ip != null && !ip.equals("")) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}

		String ipAddress = request.getRemoteAddr();
		if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ipAddress = inet.getHostAddress();
		}
		return ipAddress;
	}

	private static String[] getUA(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		String[] ua = agent.trim().split("\\)\\s");
		String[] u = ua[ua.length - 1].split(" ")[0].split("\\/");
		return u;
	}

	public static String getBrower(HttpServletRequest request) {
		String[] ua = getUA(request);
		return ua[0];
	}

	public static String getBrowserVersion(HttpServletRequest request) {
		String[] ua = getUA(request);
		return ua[1];
	}
}
