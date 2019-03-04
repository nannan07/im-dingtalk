package com.allmsi.sys.util;

import java.io.Serializable;
import java.util.List;

public class BaseTree<T extends BaseTree<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String pId;

	private String text;

	private List<T> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}
}