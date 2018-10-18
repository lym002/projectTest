package com.jsjf.controller.partner.yrt;

import java.math.BigDecimal;
import java.util.Date;

public class YrtMemberInfo {
	private String tid;
	private String uid;//手机号
	private Date registerTime;//注册时间
	private boolean isValidateIdentity = false;//是否实名认证
	private Date firstInvestTime;//首次投资时间
	private BigDecimal amount;//首次投资金额
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public boolean isValidateIdentity() {
		return isValidateIdentity;
	}
	public void setValidateIdentity(boolean isValidateIdentity) {
		this.isValidateIdentity = isValidateIdentity;
	}
	public Date getFirstInvestTime() {
		return firstInvestTime;
	}
	public void setFirstInvestTime(Date firstInvestTime) {
		this.firstInvestTime = firstInvestTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
