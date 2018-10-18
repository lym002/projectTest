package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class JsActivityLuckyMoney {
	
	private Integer id;
	private Integer uid;
	private BigDecimal amount;
	private Integer shaerUid;
	private Date addTime ;
	private Date updateTime;
	private String mobilePhone;
	private Integer shareCount;//剩余分享次数
	private Integer luckDrawSum;//可抽奖次数
	private Integer aid ; //活动id
	
	
	
	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getLuckDrawSum() {
		return luckDrawSum;
	}

	public void setLuckDrawSum(Integer luckDrawSum) {
		this.luckDrawSum = luckDrawSum;
	}

	public JsActivityLuckyMoney(){
		
	}
	
	public JsActivityLuckyMoney(Integer uid, BigDecimal amount,
			Integer shaerUid, Date addTime, Date updateTime) {
		super();
		this.uid = uid;
		this.amount = amount;
		this.shaerUid = shaerUid;
		this.addTime = addTime;
		this.updateTime = updateTime;
	}

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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getShaerUid() {
		return shaerUid;
	}
	public void setShaerUid(Integer shaerUid) {
		this.shaerUid = shaerUid;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	
}
