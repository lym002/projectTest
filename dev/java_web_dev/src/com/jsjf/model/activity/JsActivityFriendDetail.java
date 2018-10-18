package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统活动
 *
 */
public class JsActivityFriendDetail {
	
	private Integer id;
	
	private Integer fid;//活动id
	
	private Integer days; //产品期限
	
	private Integer awardType; //配置奖励，1-固定比例，2-固定面值
	
	private Integer investLimit;//投资限制，0-首投返现，1-投资返现
	
	private Date addDate;//添加时间	
	
	private BigDecimal amount;//奖励额度	
	private BigDecimal appReward;//app奖励额度	
	
	public JsActivityFriendDetail(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public Integer getInvestLimit() {
		return investLimit;
	}

	public void setInvestLimit(Integer investLimit) {
		this.investLimit = investLimit;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAppReward() {
		return appReward;
	}

	public void setAppReward(BigDecimal appReward) {
		this.appReward = appReward;
	}

	
}
