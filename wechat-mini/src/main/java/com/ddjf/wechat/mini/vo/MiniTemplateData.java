package com.ddjf.wechat.mini.vo;

import java.io.Serializable;

public class MiniTemplateData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7737973361789968533L;

	private String value;
	private String color;

	public MiniTemplateData() {
	}

	public MiniTemplateData(String value) {
		this.value = value;
	}

	public MiniTemplateData(String value, String color) {
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
