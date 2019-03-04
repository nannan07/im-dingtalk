package com.allmsi.dingtalk.utils;

public class SuccessProtocol extends BaseProtocol {

	private static final int DEFAULT_SUCCESS_CODE = 200;

	public SuccessProtocol() {
		this("");
	}

	public SuccessProtocol(int status) {
		this(status, "");
	}

	public SuccessProtocol(String msg) {
		this(DEFAULT_SUCCESS_CODE, msg);
	}

	public SuccessProtocol(Object data) {
		this("", data);
	}

	public SuccessProtocol(int status, String msg) {
		this(status, msg, null);
	}

	public SuccessProtocol(int status, Object data) {
		this(status, "", data);
	}

	public SuccessProtocol(String msg, Object data) {
		this(DEFAULT_SUCCESS_CODE, msg, data);
	}

	public SuccessProtocol(int status, String msg, Object data) {
		super(status, msg, data);
	}
}
