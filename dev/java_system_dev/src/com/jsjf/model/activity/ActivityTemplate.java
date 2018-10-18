package com.jsjf.model.activity;

import java.util.Date;

public class ActivityTemplate {
	private Integer id;
	private String name;
	private Date createTime;
	private String codeFixation;
	private Integer digit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCodeFixation() {
		return codeFixation;
	}
	public void setCodeFixation(String codeFixation) {
		this.codeFixation = codeFixation;
	}
	public Integer getDigit() {
		return digit;
	}
	public void setDigit(Integer digit) {
		this.digit = digit;
	}
	
}
