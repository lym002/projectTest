package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class DrActivityParameter {
	private Integer id; // 主键ID
	private Integer coupons; //主键ID的别名
	private String code;// 活动编号
	private String name;// 活动名称
	private BigDecimal amount;// 金额
	private BigDecimal raisedRates;// 加息额度
	private BigDecimal enableAmount;// 启用金额
	private Integer grantQty;// 发放数量
	private Integer surplusQty;// 剩余数量
	private BigDecimal surplusAmount;// 剩余金额
	private Integer type;// 活动类型 1:返现券   2：加息券  3：体验金 4：比例红包
	private Integer[] types;//优惠券类型集合

	private String applicableScenarios;// 适用场景
	private String applicableProducts;// 适用产品
	private Integer applicableUser;// 适用用户
	private Integer status;// 状态
	private Integer deadline;// 期限
	private Integer addUser;// 添加人
	private Date addTime;// 添加时间
	private Integer updateUser;// 修改人
	private Date updateTime;// 修改时间
	private Integer productDeadline;//产品期限
	private BigDecimal multiple;//倍数

	private String addName;
	private Integer usedQty;//已使用的数量
	private Integer sendQty;//已发送数量
	private String startTime; //开始时间
	private String endTime;//结束时间
	private BigDecimal orderAmount;//订单金额=投资金额*使用数量
	
	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	private String updateName;
	
	
	private Integer[] statuses;
	
	

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRaisedRates() {
		return raisedRates;
	}

	public void setRaisedRates(BigDecimal raisedRates) {
		this.raisedRates = raisedRates;
	}

	public BigDecimal getEnableAmount() {
		return enableAmount;
	}

	public void setEnableAmount(BigDecimal enableAmount) {
		this.enableAmount = enableAmount;
	}

	public Integer getGrantQty() {
		return grantQty;
	}

	public void setGrantQty(Integer grantQty) {
		this.grantQty = grantQty;
	}

	public Integer getSurplusQty() {
		return surplusQty;
	}

	public void setSurplusQty(Integer surplusQty) {
		this.surplusQty = surplusQty;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getApplicableScenarios() {
		return applicableScenarios;
	}

	public void setApplicableScenarios(String applicableScenarios) {
		this.applicableScenarios = applicableScenarios;
	}

	public String getApplicableProducts() {
		return applicableProducts;
	}

	public void setApplicableProducts(String applicableProducts) {
		this.applicableProducts = applicableProducts;
	}

	public Integer getApplicableUser() {
		return applicableUser;
	}

	public void setApplicableUser(Integer applicableUser) {
		this.applicableUser = applicableUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the usedQty
	 */
	public Integer getUsedQty() {
		return usedQty;
	}

	/**
	 * @param usedQty the usedQty to set
	 */
	public void setUsedQty(Integer usedQty) {
		this.usedQty = usedQty;
	}

	/**
	 * @return the productDeadline
	 */
	public Integer getProductDeadline() {
		return productDeadline;
	}

	/**
	 * @param productDeadline the productDeadline to set
	 */
	public void setProductDeadline(Integer productDeadline) {
		this.productDeadline = productDeadline;
	}

	/**
	 * @return the multiple
	 */
	public BigDecimal getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}
	public Integer[] getTypes() {
		return types;
	}
	
	public void setTypes(Integer[] types) {
		this.types = types;
	}

	public Integer getCoupons() {
		return coupons;
	}

	public void setCoupons(Integer coupons) {
		this.coupons = coupons;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	
	
}