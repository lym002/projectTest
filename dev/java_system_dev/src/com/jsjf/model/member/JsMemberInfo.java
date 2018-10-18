package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class JsMemberInfo {
	private Integer id;
	private Integer uid;//用户id
	private String name;//用户name
	private String phone;//用户手机 
	private String address;//用户 地址
	private Date addTime;
	private Date updateTime;
	private String productCode;//产品编号
	private Date orderFormStart;
	private Date orderFormEnd;
	private BigDecimal investAmount;//投资金额
	private int deadline;//募集期
	private BigDecimal rate;
	private String mobilephone;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public JsMemberInfo(Integer id, Integer uid, String name, String phone,
			String address, Date addTime, Date updateTime) {
		super();
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.addTime = addTime;
		this.updateTime = updateTime;
	}
	public JsMemberInfo() {
		super();
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getOrderFormStart() {
		return orderFormStart;
	}
	public void setOrderFormStart(Date orderFormStart) {
		this.orderFormStart = orderFormStart;
	}
	public Date getOrderFormEnd() {
		return orderFormEnd;
	}
	public void setOrderFormEnd(Date orderFormEnd) {
		this.orderFormEnd = orderFormEnd;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	
}
