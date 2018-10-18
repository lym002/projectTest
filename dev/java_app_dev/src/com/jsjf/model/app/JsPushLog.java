package com.jsjf.model.app;

import java.util.Date;

public class JsPushLog {
	private Integer id;//
	private Integer pushId;//推送任务id
	private Integer deviceId;//设备表的id
	private Integer uid;//推送uid, 推送uid不一定等于 接受的uid
	private Integer ruid;//接受的uid
	private Date update;//
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPushId() {
		return pushId;
	}
	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getRuid() {
		return ruid;
	}
	public void setRuid(Integer ruid) {
		this.ruid = ruid;
	}
	public Date getUpdate() {
		return update;
	}
	public void setUpdate(Date update) {
		this.update = update;
	}
	
}
