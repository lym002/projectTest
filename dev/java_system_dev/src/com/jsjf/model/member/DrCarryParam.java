package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class DrCarryParam{

    private Integer id;//提现设置ID

    private BigDecimal amount;//提现金额
    
    private Integer dayCounts;//每天免费提现次数
    
    private Integer monthCounts;//每月免费提现次数
    
    private Integer fourElementCount;//四要素次数上限
    
    private Integer smsChanel = 1;//短信通道，1-希奥，2-企信通
    
    private Integer status;//状态
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
    private Integer updUser;//修改人
    private BigDecimal autoReleaseProductLimit;
    private String autoReleaseProduct;
    
	//修改人姓名
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getDayCounts() {
		return dayCounts;
	}

	public void setDayCounts(Integer dayCounts) {
		this.dayCounts = dayCounts;
	}

	public Integer getMonthCounts() {
		return monthCounts;
	}

	public void setMonthCounts(Integer monthCounts) {
		this.monthCounts = monthCounts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFourElementCount() {
		return fourElementCount;
	}

	public void setFourElementCount(Integer fourElementCount) {
		this.fourElementCount = fourElementCount;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Integer getUpdUser() {
		return updUser;
	}

	public void setUpdUser(Integer updUser) {
		this.updUser = updUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSmsChanel() {
		return smsChanel;
	}

	public void setSmsChanel(Integer smsChanel) {
		this.smsChanel = smsChanel;
	}

	public BigDecimal getAutoReleaseProductLimit() {
		return autoReleaseProductLimit;
	}

	public void setAutoReleaseProductLimit(BigDecimal autoReleaseProductLimit) {
		this.autoReleaseProductLimit = autoReleaseProductLimit;
	}

	public String getAutoReleaseProduct() {
		return autoReleaseProduct;
	}

	public void setAutoReleaseProduct(String autoReleaseProduct) {
		this.autoReleaseProduct = autoReleaseProduct;
	}
}