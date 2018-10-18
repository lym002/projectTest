package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class DrActivityParameter {
	private Integer id; // 主键ID
	private String code;// 活动编号
	private String name;// 活动名称
	private BigDecimal amount;// 金额
	private BigDecimal raisedRates;// 加息额度
	private BigDecimal enableAmount;// 启用金额
	private Integer grantQty;// 发放数量
	private Integer surplusQty;// 剩余数量
	private BigDecimal surplusAmount;// 剩余金额
	private Integer type;// 活动类型
	private String applicableScenarios;// 适用场景
	private String applicableProducts;// 适用产品
	private Integer applicableUser;// 适用用户
	private Integer status;// 状态
	private Integer deadline;// 期限
	private Integer addUser;// 添加人
	private Date addTime;// 添加时间
	private Integer updateUser;// 修改人
	private Date updateTime;// 修改时间
	private BigDecimal multiple;//倍数
	private Integer productDeadline;//产品期限

	private String addName;
	private String updateName;

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

}