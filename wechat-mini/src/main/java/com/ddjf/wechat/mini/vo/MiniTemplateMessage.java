package com.ddjf.wechat.mini.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class MiniTemplateMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8652275828769911311L;
	
	private String toUser;
	private String templateId;
	private String page;
	private String formId;
	private Map<String, MiniTemplateData> data = new HashMap<>();
	@JSONField(name="touser")
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	@JSONField(name="template_id")
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	@JSONField(name="form_id")
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public Map<String, MiniTemplateData> getData() {
		return data;
	}
	public void setData(Map<String, MiniTemplateData> data) {
		this.data = data;
	}
}
