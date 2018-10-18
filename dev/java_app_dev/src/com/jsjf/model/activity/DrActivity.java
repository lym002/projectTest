package com.jsjf.model.activity;

import java.util.Date;

/**
 * 系统活动
 *
 */
public class DrActivity {
	
	private Integer id;
	
	private String activityName;//活动名称
	
	private Date startTime; //活动开始时间
	
	private Date endTime; //活动结束时间
	
	private Integer updateUser;//修改用户
	
	private Integer addUser;//添加用户	
	
	private Date addTime;//添加时间
	
	private Date lastUpdateTime;//最后修改时间
	
	private Integer status;//状态：1-开始  2-结束

	public DrActivity(){}
	
	public DrActivity(Integer id, String activityName, Date startTime,
			Date endTime, Integer updateUser, Integer addUser, Date addTime,
			Date lastUpdateTime) {
		super();
		this.id = id;
		this.activityName = activityName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.updateUser = updateUser;
		this.addUser = addUser;
		this.addTime = addTime;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
