package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class JsProductReservationLog {
	private Integer id;
	private Integer prid;//预约规则id
	private Integer uid;//用户id
	private Date addTime;//预约时间
	private BigDecimal amount;//预约金额
	private Integer joinType;//终端
	
	private String mobilePhone;//手机号码
	private String realName;//姓名
	private Date logStartTime;
	private Date logEndTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPrid() {
		return prid;
	}
	public void setPrid(Integer prid) {
		this.prid = prid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getJoinType() {
		return joinType;
	}
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getLogStartTime() {
		return logStartTime;
	}
	public void setLogStartTime(Date logStartTime) {
		this.logStartTime = logStartTime;
	}
	public Date getLogEndTime() {
		return logEndTime;
	}
	public void setLogEndTime(Date logEndTime) {
		this.logEndTime = logEndTime;
	}


}
