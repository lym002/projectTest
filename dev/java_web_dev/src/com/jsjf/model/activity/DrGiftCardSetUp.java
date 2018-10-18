 package com.jsjf.model.activity;

import java.util.Date;
import java.util.List;

public class DrGiftCardSetUp {
	private Integer id;
	private String name;
	private String code;
	private Integer channelId;
	private String channelName;//渠道名称
	private String channelCode;//渠道编号
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Integer onceQty;
	private String remark;
	private Date addTime;
	private Integer addUser;
	private String addName;
	private Date updateTime;
	private Integer updateUser;
	private String updateName;
	private Integer totalQty;//总数量
	private Integer validQty;//有效数量
	private Integer useQty;//已发放数量
	private Integer failureQty;//失效数量
	private List<DrGiftCardSetUpDetail> detailList;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
	 * @return the onceQty
	 */
	public Integer getOnceQty() {
		return onceQty;
	}
	/**
	 * @param onceQty the onceQty to set
	 */
	public void setOnceQty(Integer onceQty) {
		this.onceQty = onceQty;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @return the detailList
	 */
	public List<DrGiftCardSetUpDetail> getDetailList() {
		return detailList;
	}
	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<DrGiftCardSetUpDetail> detailList) {
		this.detailList = detailList;
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
	 * @return the totalQty
	 */
	public Integer getTotalQty() {
		return totalQty;
	}
	/**
	 * @param totalQty the totalQty to set
	 */
	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}
	/**
	 * @return the useQty
	 */
	public Integer getUseQty() {
		return useQty;
	}
	/**
	 * @param useQty the useQty to set
	 */
	public void setUseQty(Integer useQty) {
		this.useQty = useQty;
	}
	/**
	 * @return the failureQty
	 */
	public Integer getFailureQty() {
		return failureQty;
	}
	/**
	 * @param failureQty the failureQty to set
	 */
	public void setFailureQty(Integer failureQty) {
		this.failureQty = failureQty;
	}
	/**
	 * @return the validQty
	 */
	public Integer getValidQty() {
		return validQty;
	}
	/**
	 * @param validQty the validQty to set
	 */
	public void setValidQty(Integer validQty) {
		this.validQty = validQty;
	}
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}
	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}
