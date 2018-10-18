package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;


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
    
    /**
     * 变动金额  
     */
    private BigDecimal amount;


    /**
     * 收支 0支出 1收入  
     */
    private Integer type;
    
    /**
     * 发生时间  
     */
    private Date addTime;
    
    /**
     * 备注
     */
    private String remark;

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
	
}