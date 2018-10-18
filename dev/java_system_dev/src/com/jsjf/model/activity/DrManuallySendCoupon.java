package com.jsjf.model.activity;

import java.util.Date;


public class DrManuallySendCoupon {
	private Integer id;
	private Integer cirId;
	private String coupons;
	private String couponNames;
	private String mobilePhone;
	private Integer status;
	private Integer addUser;
	private String addName;
	private Date addTime;
	private Integer sendUser;
	private String sendName;
	private Date sendTime;
	private String message;//站内信内容
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCoupons() {
		return coupons;
	}
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getSendUser() {
		return sendUser;
	}
	public void setSendUser(Integer sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getCirId() {
		return cirId;
	}
	public void setCirId(Integer cirId) {
		this.cirId = cirId;
	}
	public String getCouponNames() {
		return couponNames;
	}
	public void setCouponNames(String couponNames) {
		this.couponNames = couponNames;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
