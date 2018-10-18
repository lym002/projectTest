package com.jsjf.model.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrCompanyFundsLog {
	/**
	 * 主键id 自增
	 */
	private Integer logId;

	/**
	 * 资金类型
	 */
	private Integer fundsTypeId;

	/**
	 * 涉及收款人ID
	 */
	private Integer uid;

	/**
	 * 涉及标ID
	 */
	private Integer pid;

	/**
	 * 变动金额
	 */
	private BigDecimal amount;

	/**
	 * 收支 0支出 1收入
	 */
	private Integer type;
	private String typeName;// 类型名称

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 发生时间
	 */
	private Date addTime;
	/**
	 * 状态值默认为1，0:表示放款未成功前的状态  
	 */
	private Integer status;

	/**
	 * 操作人
	 */
	private Integer addUser;

	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private String realName;// 用户姓名
	private String phone;// 手机号码
	private String fundTypeName;// 交易类型
	private String pcode;// 项目编号
	private String fundTypes;// 交易类型

	public DrCompanyFundsLog() {

	}

	public DrCompanyFundsLog(Integer fundsTypeId, Integer uid, Integer pid,
			BigDecimal amount,Integer type, String remark,
			Integer addUser) {
		super();
		this.fundsTypeId = fundsTypeId;
		this.uid = uid;
		this.pid = pid;
		this.amount = amount;
		this.type = type;
		this.remark = remark;
		this.addUser = addUser;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getFundsTypeId() {
		return fundsTypeId;
	}

	public void setFundsTypeId(Integer fundsTypeId) {
		this.fundsTypeId = fundsTypeId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("balanceType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getFundTypeName() {
		try {
			fundTypeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("companyfunds")).get(
					fundsTypeId.intValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getFundTypes() {
		return fundTypes;
	}

	public void setFundTypes(String fundTypes) {
		this.fundTypes = fundTypes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}