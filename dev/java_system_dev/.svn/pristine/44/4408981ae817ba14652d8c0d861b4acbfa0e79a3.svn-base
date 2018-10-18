package com.jsjf.model.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrMemberCrush {
	private Integer id;// 主键
	private Integer uid;// 会员ID
	private BigDecimal amount;// 金额
	private BigDecimal poundFee;// 手续费
	private Integer channel;// 冲值渠道 0 PC 2iOS 3WeChat 4 android 1 后台
	private String channelName;// 冲值渠道名称
	private Integer submitUserKy;// 后台充值提交人
	private String remark;// 备注
	private Integer status;// 冲值状态，0 未处理 1 成功 2 失败
	private String statusName;// 充值状态名称
	private Date addTime;// 充值时间
	private Integer auditId;// 审核人ID
	private Date auditTime;// 审核时间（不论失败或成功）

	private String name; // 操作人员
	private String phone;// 手机号码
	private String realName; // 真实姓名
	private String idCards; // 身份证号码

	private Date startDate;// 开始时间
	private Date endDate;// 结束时间

	private String payNum;// 商户订单号
	private String sftOrderNo;// 盛付通订单号
	private String sessionToken;// 支付token，用于后续的支付预校验和支付确认
    private Integer type;//1.金运通认证 2金运通网银 3盛付通认证
    private String typeName;
    
    private String bankName;//充值银行名称
    
    private String recommCodes;//推荐码
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

	public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("crushstatus")).get(status);
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

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIdCards() {
		return idCards;
	}

	public void setIdCards(String idCards) {
		this.idCards = idCards;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null) {
			this.phone = phone.trim();
		} else {
			this.phone = phone;
		}
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("payType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	
}