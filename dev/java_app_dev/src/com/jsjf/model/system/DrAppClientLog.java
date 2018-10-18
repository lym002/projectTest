package com.jsjf.model.system;

import java.util.Date;

public class DrAppClientLog {
	
	private Integer id;
	
	private String url;//请求地址
	
	private String param;//请求参数
	
	private String version;//app版本号
	
	private String channel;//通道 1=ios，2=android，3=wechat，4=后台，0=PC

	private Date addTime;//添加时间
	
	private String phone ;//请求手机号
	
	private String reqIp; //请求IP
	
	private String uid;//用户id
	
	public DrAppClientLog(){}
	
	
	public DrAppClientLog(String url, String param, String version,
			String channel,String phone,String reqIp,String uid) {
		super();
		this.url = url;
		this.param = param;
		this.version = version;
		this.channel = channel;
		this.phone = phone;
		this.reqIp = reqIp;
		this.uid = uid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getReqIp() {
		return reqIp;
	}


	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


}
