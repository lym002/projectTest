package com.jsjf.model.activity;

import java.math.BigDecimal;

/**
 * 系统礼品发放(京东卡双十二活动) 奖品类型表
 * 
 * @author huzhenya
 */
public class BypCommodity {
	private Integer prid;// '主键、标识列自动增长 奖品ID'
	private String prizename;// '奖品名称'
	private Integer price;// '金额',
	private String details;// '奖品详情',
	private Integer status;// '0 不可用 1 可用',
	private Integer type;// '1 京东卡 2实物奖品 3虚拟货物',
	
	private BigDecimal needPoints;

    private String activityId;
    private String productUrl;

    private String code;		//红包id
    private BigDecimal repertory;  //库存

	public BigDecimal getNeedPoints() {
		return needPoints;
	}

	public void setNeedPoints(BigDecimal needPoints) {
		this.needPoints = needPoints;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public BypCommodity() {
	}

	public BypCommodity(Integer prid, String prizeName, Integer price,
			String details, Integer status, Integer type) {
		super();
		this.prid = prid;
		this.prizename = prizeName;
		this.price = price;
		this.details = details;
		this.status = status;
		this.type = type;
	}

	public Integer getPrid() {
		return prid;
	}

	public void setPrid(Integer prid) {
		this.prid = prid;
	}

	public String getPrizeName() {
		return prizename;
	}

	public void setPrizeName(String prizeName) {
		this.prizename = prizeName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getRepertory() {
		return repertory;
	}

	public void setRepertory(BigDecimal repertory) {
		this.repertory = repertory;
	}
}