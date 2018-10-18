package com.jsjf.model.system;

import java.util.Date;

public class JsPushDevice {
	private Integer id;//
	private Integer uid;//用户id
	private Integer appId;//appId
	private String device;//设备唯一标识 极光的是registrationId,友盟的是device_token
	private Date updateTime;//修改时间
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	
}
