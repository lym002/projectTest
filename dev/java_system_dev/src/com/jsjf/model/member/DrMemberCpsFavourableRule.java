package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class DrMemberCpsFavourableRule {

	private  Integer id;
	
	private BigDecimal minAmount;//最小起投金额
	
	private BigDecimal maxAmount;//最大投资金额
	
	private Integer activityId_1;//红包1
	
	private Integer activityId_2;//红包2
	
	private Integer activityId_3;//红包3
	
	private Integer isFirst;//是否首笔投资 0=否，1=是
	
	private Integer status;//状态 0=无效，1=有效
	
	private Integer isCps; //0=非cps渠道，1=cps渠道
	
	private Date addTime;//添加时间
	
	private Integer addUserKey;//添加人
	
	private Date updTime;//修改时间
	
	private Integer updUserKey;//修改人
	
	private String code_1;//红包1编号
	private String code_2;//红包2编号
	private String code_3;//红包3编号
	
	private String addUserName;
	
	private String updUserName;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getActivityId_1() {
		return activityId_1;
	}

	public void setActivityId_1(Integer activityId_1) {
		this.activityId_1 = activityId_1;
	}

	public Integer getActivityId_2() {
		return activityId_2;
	}

	public void setActivityId_2(Integer activityId_2) {
		this.activityId_2 = activityId_2;
	}

	public Integer getActivityId_3() {
		return activityId_3;
	}

	public void setActivityId_3(Integer activityId_3) {
		this.activityId_3 = activityId_3;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getAddUserKey() {
		return addUserKey;
	}

	public void setAddUserKey(Integer addUserKey) {
		this.addUserKey = addUserKey;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Integer getUpdUserKey() {
		return updUserKey;
	}

	public void setUpdUserKey(Integer updUserKey) {
		this.updUserKey = updUserKey;
	}

	public String getCode_1() {
		return code_1;
	}

	public void setCode_1(String code_1) {
		this.code_1 = code_1;
	}

	public String getCode_2() {
		return code_2;
	}

	public void setCode_2(String code_2) {
		this.code_2 = code_2;
	}

	public String getCode_3() {
		return code_3;
	}

	public void setCode_3(String code_3) {
		this.code_3 = code_3;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getUpdUserName() {
		return updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	public Integer getIsCps() {
		return isCps;
	}

	public void setIsCps(Integer isCps) {
		this.isCps = isCps;
	}
	
}
