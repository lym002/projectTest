package com.jsjf.model.activity;

import java.util.Date;

public class DrCouponsIssuedRules {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 赠送规则类型  0 ：注册赠送  1：投资赠送
	 */
	private Integer type;
	/**
	 * 活动开始时间
	 */
	private Date startTime;
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	/**
	 * 赠送优惠券类型
	 */
	private Integer couponType;
	/**
	 * 优惠券
	 */
	private String coupons;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 添加人
	 */
	private Integer addUser;
	private String addName;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改人
	 */
	private Integer updateUser;
	private String updateName;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * cps使用 0否1是else全部
	 */
	private Integer isCps;


	private String mobilePhone;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}




	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the couponType
	 */
	public Integer getCouponType() {
		return couponType;
	}
	/**
	 * @param couponType the couponType to set
	 */
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the addUser
	 */
	public Integer getAddUser() {
		return addUser;
	}
	/**
	 * @param addUser the addUser to set
	 */
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	/**
	 * @return the addName
	 */
	public String getAddName() {
		return addName;
	}
	/**
	 * @param addName the addName to set
	 */
	public void setAddName(String addName) {
		this.addName = addName;
	}
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return the updateUser
	 */
	public Integer getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the coupons
	 */
	public String getCoupons() {
		return coupons;
	}
	/**
	 * @param coupons the coupons to set
	 */
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsCps() {
		return isCps;
	}
	public void setIsCps(Integer isCps) {
		this.isCps = isCps;
	}
	
	
	
}
