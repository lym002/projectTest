package com.jsjf.model.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;


public class DrMemberFundsLog {

	/**
     * 主键 
     */
    private Integer id;


    /**
     * 变动会员ID  
     */
    private Integer uid;

    /**
     * 记录ID  
     */
    private Integer rid;
    
    private Integer fundType;
    private String fundTypeName;

    /**
     * 变动金额  
     */
    private BigDecimal amount;


    /**
     * 收支 0支出 1收入  
     */
    private Integer type;
    
    private String typeName;

    /**
     * 发生时间  
     */
    private Date addTime;
    
    /**
     * 备注
     */
    private String remark;
    
	private Date startDate;
	private Date endDate;
	private String realName;
	private String mobilephone; 
	private String idCards;// 
	private String fundTypes;
	private String recommCodes;

    public DrMemberFundsLog(){}
    
	public DrMemberFundsLog(Integer uid, Integer rid, BigDecimal amount,
			Integer fundType, Integer type,String remark) {
		super();
		this.uid = uid;
		this.rid = rid;
		this.setFundType(fundType);
		this.amount = amount;
		this.type = type;
		this.remark = remark;
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

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getIdCards() {
		return idCards;
	}

	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}
	
	public String getFundTypeName() {
		try {
			fundTypeName = ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("fundType")).get(fundType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}
	
	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("balanceType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFundTypes() {
		return fundTypes;
	}

	public void setFundTypes(String fundTypes) {
		this.fundTypes = fundTypes;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	
}