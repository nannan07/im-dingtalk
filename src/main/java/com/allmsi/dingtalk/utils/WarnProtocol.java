package com.allmsi.dingtalk.utils;

public class WarnProtocol extends BaseProtocol {

	private static final int DEFAULT_WARN_CODE = 1;

	public WarnProtocol() {
		this("");
	}

	public WarnProtocol(int status) {
		this(status, "");
	}

	public WarnProtocol(String msg) {
		this(DEFAULT_WARN_CODE, msg);
	}

	public WarnProtocol(Object data) {
		this("", data);
	}

	public WarnProtocol(int status, String msg) {
		this(status, msg, null);
	}

	public WarnProtocol(int status, Object data) {
		this(status, "", data);
	}

	public WarnProtocol(String msg, Object data) {
		this(DEFAULT_WARN_CODE, msg, data);
	}

	public WarnProtocol(int status, String msg, Object data) {
		super(status, msg, data);
	}
}
