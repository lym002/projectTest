package com.jsjf.model.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

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
	private Integer status;//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
	private String statusName;//状态名称

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
	private String bankName;// 银行卡名

	/**
	 * 提现渠道 0 线上 1 后台
	 */
	private Integer channel;
	private String channelName;// 冲值渠道名称
	private String paymentNum;// 商户唯一订单号
	private String reason;// 失败原因

	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private Date audStartDate;// 开始时间
	private Date audEndDate;// 结束时间
	private String realName;// 用户姓名
	private String phone;// 手机号码
	
	private Integer type;//1.连连2金运通
	
	private String remitMchntTxnSsn;//手续费划拨流水号
	
	/**
	 * 开始金额
	 */
	private BigDecimal startamount;
	
	/**
	 * 结束金额
	 */
	private BigDecimal endamount;
	
	
	private BigDecimal earningSum;//收入总额
	
	private BigDecimal paySum;//支出总额
	
	private BigDecimal balance;//余额
	
	private String recommCodes;//余额
	

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

	public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("carrystatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getChannelName() {
		try {
			channelName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("channel")).get(
					channel.intValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getAudStartDate() {
		return audStartDate;
	}

	public void setAudStartDate(Date audStartDate) {
		this.audStartDate = audStartDate;
	}

	public Date getAudEndDate() {
		return audEndDate;
	}

	public void setAudEndDate(Date audEndDate) {
		this.audEndDate = audEndDate;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		if (realName != null) {
			this.realName = realName.trim();
		} else {
			this.realName = realName;
		}

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public BigDecimal getStartamount() {
		return startamount;
	}

	public void setStartamount(BigDecimal startamount) {
		this.startamount = startamount;
	}

	public BigDecimal getEndamount() {
		return endamount;
	}

	public void setEndamount(BigDecimal endamount) {
		this.endamount = endamount;
	}

	public BigDecimal getEarningSum() {
		return earningSum;
	}

	public void setEarningSum(BigDecimal earningSum) {
		this.earningSum = earningSum;
	}

	public BigDecimal getPaySum() {
		return paySum;
	}

	public void setPaySum(BigDecimal paySum) {
		this.paySum = paySum;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemitMchntTxnSsn() {
		return remitMchntTxnSsn;
	}

	public void setRemitMchntTxnSsn(String remitMchntTxnSsn) {
		this.remitMchntTxnSsn = remitMchntTxnSsn;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	
}