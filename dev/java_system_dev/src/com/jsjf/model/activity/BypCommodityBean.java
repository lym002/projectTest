package com.jsjf.model.activity;

import java.math.BigDecimal;

public class BypCommodityBean {
	private Integer prId;
	private String prizeName;
	private BigDecimal price;
	private String details;
	private Integer status;
	private Integer type;
	private String productUrl;
	private BigDecimal needPoints;
	private String activityId;//活动id
	
	private String hbCode;//红包编号
	
	private String hbName;
	
	private BigDecimal repertory;//库存
	public Integer getPrId() {
		return prId;
	}
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public BigDecimal getNeedPoints() {
		return needPoints;
	}
	public void setNeedPoints(BigDecimal needPoints) {
		this.needPoints = needPoints;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getHbCode() {
		return hbCode;
	}
	public void setHbCode(String hbCode) {
		this.hbCode = hbCode;
	}
	public BigDecimal getRepertory() {
		return repertory;
	}
	public void setRepertory(BigDecimal repertory) {
		this.repertory = repertory;
	}
	public String getHbName() {
		return hbName;
	}
	public void setHbName(String hbName) {
		this.hbName = hbName;
	}
	
	
}