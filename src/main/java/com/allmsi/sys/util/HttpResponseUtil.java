package com.allmsi.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * Http 响应辅助类
 * 
 * @version 2016-09-02
 */
public class HttpResponseUtil {
	/**
	 * 发送xml文档。使用UTF-8编码
	 * 
	 * @param res
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse res, String text) {
		renderContent(res, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送普通文本。使用UTF-8编码
	 * 
	 * @param res
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse res, String text) {
		renderContent(res, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码
	 * 
	 * @param res
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse res, String text) {
		renderContent(res, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送任意的二进制数据。使用UTF-8编码
	 * 
	 * @param response
	 *            HttpServletResponse
	 */
	public static void renderStream(HttpServletResponse response) {
		response.setContentType("application/octet-stream; charset=UTF-8");
	}

	/**
	 * 发送内容。使用UTF-8编码
	 * 
	 * @param res
	 *            HttpServletResponse
	 * @param contentType
	 *            上下文类型
	 * @param text
	 */
	public static void renderContent(HttpServletResponse res, String contentType, String text) {
		res.setContentType(contentType);
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		try {
			res.getWriter().write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 读取二进制文件字节流（已做缓冲处理）
	 * 
	 * @param in
	 *            InputStream
	 * @param out
	 *            OutputStream
	 * @return out BufferedOutputStream
	 */
	public static OutputStream readStream(InputStream in, OutputStream out) throws IOException {
		int len = 0;
		byte[] buffer = new byte[2048];
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.flush();
		out.close();
		return out;
	}
}
