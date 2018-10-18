package com.jsjf.model.app;

import java.util.Date;

public class SysAppRenewal {
	private Integer id;
	private String version;
	private Integer isForceUpdate;
	private Integer containers;
	private String content;
	private Integer status;
	private String remark;
	private Integer addUser;
	private Date addTime;
	private Integer updateUser;
	private Date updateTime;
	private String addName;
	private String updateName;
	private String releaseVersion;
	
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the isForceUpdate
	 */
	public Integer getIsForceUpdate() {
		return isForceUpdate;
	}
	/**
	 * @param isForceUpdate the isForceUpdate to set
	 */
	public void setIsForceUpdate(Integer isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}
	/**
	 * @return the containers
	 */
	public Integer getContainers() {
		return containers;
	}
	/**
	 * @param containers the containers to set
	 */
	public void setContainers(Integer containers) {
		this.containers = containers;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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

	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
}
