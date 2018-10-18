package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统活动
 *
 */
public class JsActivityMemberAccount {
	
	private Integer id;
	
	private Integer piid;//产品投资记录表ID
	
	private Integer uid;//会员ID
	
	private Integer afid;//活动id
	
	private BigDecimal amount;//返现金额
	
	private Integer status;//领取状态，默认为0，0-未领取，1-已领取		
	
	private Date addDate;//添加时间	
	private BigDecimal amountSum;//返现总和
	private String userName;//用户名
	
	public JsActivityMemberAccount(){}

	public JsActivityMemberAccount(Integer piid, Integer uid, Integer afid, BigDecimal amount, Integer status,
			Date addDate) {
		super();
		this.piid = piid;
		this.uid = uid;
		this.afid = afid;
		this.amount = amount;
		this.status = status;
		this.addDate = addDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPiid() {
		return piid;
	}

	public void setPiid(Integer piid) {
		this.piid = piid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getAfid() {
		return afid;
	}

	public void setAfid(Integer afid) {
		this.afid = afid;
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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public BigDecimal getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
