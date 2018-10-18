package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class DrMemberCrush {
	private Integer id;// 主键
	private Integer uid;// 会员ID
	private BigDecimal amount;// 金额
	private BigDecimal poundFee;// 手续费
	private Integer channel;// 冲值渠道 0 系统 1 后台
	private Integer submitUserKy;// 后台充值提交人
	private String remark;// 备注
	private Integer status;// 冲值状态，0 未处理 1 成功 2 失败
	private Date addTime;// 充值时间
	private Integer auditId;// 审核人ID
	private Date auditTime;// 审核时间（不论失败或成功）

	private String payNum;// 商户订单号
	private String sftOrderNo;// 盛付通订单号
	private String sessionToken;// 支付token，用于后续的支付预校验和支付确认
    private Integer type;//1.金运通认证 2金运通网银 3盛付通认证
	/**
	 * 绑定银行（当前）
	 */
	private Integer bankId;
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
	public BigDecimal getPoundFee() {
		return poundFee;
	}
	public void setPoundFee(BigDecimal poundFee) {
		this.poundFee = poundFee;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public Integer getSubmitUserKy() {
		return submitUserKy;
	}
	public void setSubmitUserKy(Integer submitUserKy) {
		this.submitUserKy = submitUserKy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getAuditId() {
		return auditId;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getPayNum() {
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getSftOrderNo() {
		return sftOrderNo;
	}
	public void setSftOrderNo(String sftOrderNo) {
		this.sftOrderNo = sftOrderNo;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
}