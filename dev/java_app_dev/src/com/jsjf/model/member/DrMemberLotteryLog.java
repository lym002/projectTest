package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class DrMemberLotteryLog {
	
	private Integer id;//主键ID
	
	private Integer aid;//活动Id
	
	private Integer uid;//用户id
	private Integer investId;//投资ID
	private BigDecimal investAmount;//投资金额
	
	private Date addTime;//添加时间
	
	private Integer giftId;//奖品id
	
	private Date updateTime;//抽奖时间
	private String userName;//用户名
	private BigDecimal amount;//奖金
	
	private String mobilePhone;//手机号
	private String name;//奖品名称
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DrMemberLotteryLog(){}
	
	public DrMemberLotteryLog(Integer aid, Integer uid,
			Date addTime, Integer giftId) {
		super();
		this.aid = aid;
		this.uid = uid;
		this.addTime = addTime;
		this.giftId = giftId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
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

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public Integer getInvestId() {
		return investId;
	}

	public void setInvestId(Integer investId) {
		this.investId = investId;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


}

