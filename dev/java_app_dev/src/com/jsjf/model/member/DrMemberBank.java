package com.jsjf.model.member;

import java.util.Date;

public class DrMemberBank {
    /**
     * 自动编号  
     */
    private Integer id;

    /**
     * 会员ID  
     */
    private Integer uid;

    /**
     * 银行卡号  
     */
    private String bankNum;

    /**
     * 银行名称
     */
    private String bankName;

    private String mobilePhone;//预留手机号码
    /**
     * 银行卡类型0-网银 1认证
     */
    private Integer type;

    /**
     * 状态1通过 9删除  
     */
    private Integer status;

    /**
     * 冲值渠道 0 PC 1iOS 2android 3微信 4 后台
     */
    private Integer channel;
    
    /**
     * 入库时间  
     */
    private Date addTime;

    
    private Integer cardFlag;//0-对私 1-对公
    
    private Integer addUser;//添加人
    
    private String agreementNo;//协议号


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


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getChannel() {
		return channel;
	}


	public void setChannel(Integer channel) {
		this.channel = channel;
	}


	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public Integer getCardFlag() {
		return cardFlag;
	}


	public void setCardFlag(Integer cardFlag) {
		this.cardFlag = cardFlag;
	}


	public Integer getAddUser() {
		return addUser;
	}


	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}


	public String getAgreementNo() {
		return agreementNo;
	}


	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
    
}