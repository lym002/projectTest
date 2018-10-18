package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class JsProductReservationLog {
	private Integer id;//ID，自增长	　	
	private Integer prid;//	预约规则id	　	
	private Integer uid;//用户uid	　	
	private Date addTime;//预约时间	　	
	private BigDecimal amount;//	预约金额	　	
	private Integer joinType;//	加入方式0为PC,1为IOS,2为android,3为微信
	public JsProductReservationLog() {
		super();
	}
	public JsProductReservationLog(Integer prid, Integer uid,
			BigDecimal amount, Integer joinType) {	
		this.prid = prid;
		this.uid = uid;	
		this.amount = amount;
		this.joinType = joinType;
	}
	Integer getId() {
		return id;
	}
	void setId(Integer id) {
		this.id = id;
	}
	Integer getPrid() {
		return prid;
	}
	void setPrid(Integer prid) {
		this.prid = prid;
	}
	Integer getUid() {
		return uid;
	}
	void setUid(Integer uid) {
		this.uid = uid;
	}
	Date getAddTime() {
		return addTime;
	}
	void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	BigDecimal getAmount() {
		return amount;
	}
	void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	Integer getJoinType() {
		return joinType;
	}
	void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	
}
