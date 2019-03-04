package com.allmsi.dingtalk.utils;

public class ErrorProtocol extends BaseProtocol {

	private static final int DEFAULT_ERROR_CODE = 400;

	public ErrorProtocol() {
		this("");
	}

	public ErrorProtocol(int status) {
		this(status, "");
	}

	public ErrorProtocol(String msg) {
		this(DEFAULT_ERROR_CODE, msg);
	}

	public ErrorProtocol(Object data) {
		this("", data);
	}

	public ErrorProtocol(int status, String msg) {
		this(status, msg, null);
	}

	public ErrorProtocol(int status, Object data) {
		this(status, "", data);
	}

	public ErrorProtocol(String msg, Object data) {
		this(DEFAULT_ERROR_CODE, msg, data);
	}

	public ErrorProtocol(int status, String msg, Object data) {
		super(status, msg, data);
	}
}
