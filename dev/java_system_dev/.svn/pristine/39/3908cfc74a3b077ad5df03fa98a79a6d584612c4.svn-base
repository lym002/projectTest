package com.jsjf.model.activity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrAgentParameter {
	private Integer id;
	private Integer agentLevel;//代理人等级
	private BigDecimal rebateRate;//代理人返利比率
	private Integer type;//返利类型
	private Date startTime;//有效开始时间
	private Date endTime;//有效结束时间
	private Integer status;//状态
	private Integer addUser;//添加人ID
	private Date addTime;//添加时间
	private Integer updateUser;//修改人ID
	private Date updateTime;//修改时间
	
	private String addName;//添加人姓名
	private String updateName;//修改人姓名
	
	private String agentName;//代理等级名称
	private String typeName;//返利类型名称
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the agentLevel
	 */
	public Integer getAgentLevel() {
		return agentLevel;
	}
	/**
	 * @param agentLevel the agentLevel to set
	 */
	public void setAgentLevel(Integer agentLevel) {
		this.agentLevel = agentLevel;
	}
	/**
	 * @return the rebateRate
	 */
	public BigDecimal getRebateRate() {
		return rebateRate;
	}
	/**
	 * @param rebateRate the rebateRate to set
	 */
	public void setRebateRate(BigDecimal rebateRate) {
		this.rebateRate = rebateRate;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the addUser
	 */
	public Integer getAddUser() {
		return addUser;
	}
	/**
	 * @param addUser the addUser to set
	 */
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return the updateUser
	 */
	public Integer getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the addName
	 */
	public String getAddName() {
		return addName;
	}
	/**
	 * @param addName the addName to set
	 */
	public void setAddName(String addName) {
		this.addName = addName;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		try {
			agentName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("agentLevel")).get(agentLevel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("agentType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
