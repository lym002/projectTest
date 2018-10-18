package com.jsjf.model.activity;

import java.util.Date;


public class JsOpenDayLog {
	private Integer id;
	private Integer uid;
	private Integer openDayId;
	private String userName;
	private int sex;
	private String city;
	private Date appointmentTime;
	private String mobilePhone;
	private String gender;
	private String recommCodes;
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
	public Integer getOpenDayId() {
		return openDayId;
	}
	public void setOpenDayId(Integer openDayId) {
		this.openDayId = openDayId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Date getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRecommCodes() {
		return recommCodes;
	}
	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	
}
