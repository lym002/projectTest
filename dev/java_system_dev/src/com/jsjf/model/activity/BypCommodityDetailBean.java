package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class BypCommodityDetailBean {
	private Integer id;
	private String code;//兑换码
	private Integer prid;
	private Integer uid;
	private Integer status;
	private Date addtime;
	private Date starttime;
	private Date endtime;
	private Date provide;
	private String address;
	private Integer type;
	private String mobilePhone;
	private Integer price;
	
	
	//统计奖品记录时使用
	private Date tjStartTime;
	private Date tjEndTime;
	private Integer deadline;
	private BigDecimal amount;
	private Integer tjType;
	
	private BigDecimal thirtyAmount;
	private BigDecimal sixtyAmount;
	private BigDecimal oheightyAmount;
	
	private String realName;
	
	private Date xnEndTime;
	
	private String prizeName;
	
	private BigDecimal needPoints;
	
	private String otherAward;
	
	private BigDecimal yearAmount;//年化投资额
	

	
	private String toFrom;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getPrid() {
		return prid;
	}
	public void setPrid(Integer prid) {
		this.prid = prid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Date getProvide() {
		return provide;
	}
	public void setProvide(Date provide) {
		this.provide = provide;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getTjStartTime() {
		return tjStartTime;
	}
	public void setTjStartTime(Date tjStartTime) {
		this.tjStartTime = tjStartTime;
	}
	public Date getTjEndTime() {
		return tjEndTime;
	}
	public void setTjEndTime(Date tjEndTime) {
		this.tjEndTime = tjEndTime;
	}
	public Integer getTjType() {
		return tjType;
	}
	public void setTjType(Integer tjType) {
		this.tjType = tjType;
	}
	public BigDecimal getThirtyAmount() {
		return thirtyAmount;
	}
	public void setThirtyAmount(BigDecimal thirtyAmount) {
		this.thirtyAmount = thirtyAmount;
	}
	public BigDecimal getSixtyAmount() {
		return sixtyAmount;
	}
	public void setSixtyAmount(BigDecimal sixtyAmount) {
		this.sixtyAmount = sixtyAmount;
	}
	public BigDecimal getOheightyAmount() {
		return oheightyAmount;
	}
	public void setOheightyAmount(BigDecimal oheightyAmount) {
		this.oheightyAmount = oheightyAmount;
	}
	public Date getXnEndTime() {
		return xnEndTime;
	}
	public void setXnEndTime(Date xnEndTime) {
		this.xnEndTime = xnEndTime;
	}
	public String getToFrom() {
		return toFrom;
	}
	public void setToFrom(String toFrom) {
		this.toFrom = toFrom;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public BigDecimal getNeedPoints() {
		return needPoints;
	}
	public void setNeedPoints(BigDecimal needPoints) {
		this.needPoints = needPoints;
	}
	public String getOtherAward() {
		return otherAward;
	}
	public void setOtherAward(String otherAward) {
		this.otherAward = otherAward;
	}
	public BigDecimal getYearAmount() {
		return yearAmount;
	}
	public void setYearAmount(BigDecimal yearAmount) {
		this.yearAmount = yearAmount;
	}
	
	
	
}
