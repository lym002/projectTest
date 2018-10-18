package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 新手续投记录表
 * * @author DELL
 *
 */
public class JsNoviceContinueRecord {
	private Integer id;//ID，自增长
	private Integer uid;//用户uid
	private Integer newInvestId;//新手标投资记录ID
	private Date addTime;//添加时间
	private Date shouldtime;//新手标还款日期
	private Integer period;//续投产品期限
	private BigDecimal amount;//续投金额
	private BigDecimal reward;//红包金额
	private Integer status;//状态 0:待续投 1:以续投
	private Integer investId;//投资记录ID
	
	private Integer joinType;//加入方式0为PC,1为IOS,2为android,3为微信
	
	
	
	public JsNoviceContinueRecord(Integer uid, Integer newInvestId,
			Date addTime, Date shouldtime, Integer period, BigDecimal amount,
			Integer status, Integer investId, Integer joinType,BigDecimal reward) {
		super();
		this.uid = uid;
		this.newInvestId = newInvestId;
		this.addTime = addTime;
		this.shouldtime = shouldtime;
		this.period = period;
		this.amount = amount;
		this.status = status;
		this.investId = investId;
		this.joinType = joinType;
		this.reward = reward;
	}

	public JsNoviceContinueRecord() {
		super();
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
	public Integer getNewInvestId() {
		return newInvestId;
	}
	public void setNewInvestId(Integer newInvestId) {
		this.newInvestId = newInvestId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public Date getShouldtime() {
		return shouldtime;
	}

	public void setShouldtime(Date shouldtime) {
		this.shouldtime = shouldtime;
	}

	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getJoinType() {
		return joinType;
	}
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	
}
