package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class DrMemberCarry {
    /**
     * 主键id 自增  
     */
    private Integer id;

    /**
     * 会员ID  
     */
    private Integer uid;

    /**
     * 绑定银行（当前）  
     */
    private Integer bankId;

    /**
     * 金额  
     */
    private BigDecimal amount;

    /**
     * 提现手续费 
     */
    private BigDecimal poundage;

    /**
     * 提现状态，1 成功 2失败 3 冻结
     */
    private Integer status;

    /**
     * 提交时间  
     */
    private Date addTime;

    /**
     * 审核人 
     */
    private Integer auditId;

    /**
     * 审核时间（不论失败或成功）
     */
    private Date auditTime;

    /**
     * 银行卡号  
     */
    private String bankNum;
    private String bankName;//银行卡名

    /**
     * 提现渠道 0 线上 1 后台  
     */
    private Integer channel;
    private String paymentNum;//商户唯一订单号
    private String reason;//失败原因
    
    private Integer type;//1.连连2金运通
    
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

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
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

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
}