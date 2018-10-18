package com.jsjf.model.cpa;

import java.io.Serializable;
import java.util.Date;

public class DrChannelKeyWords implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3232797868381083694L;

	private Integer id;
	
	private String keyword;//关键字
	
	private String kCode;//关键字编号
	
	private Integer cid;//渠道ID
	
	private String code;//渠道编号
	
	private Integer status;//状态
	
	private Integer addUserKey;
	
	private Date addTime;
	
	private String name;//渠道名称
	
	private String activityName;//活动名称
	private String activityCode;//活动编号
	private String url;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAddUserKey() {
		return addUserKey;
	}

	public void setAddUserKey(Integer addUserKey) {
		this.addUserKey = addUserKey;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the kCode
	 */
	public String getkCode() {
		return kCode;
	}

	/**
	 * @param kCode the kCode to set
	 */
	public void setkCode(String kCode) {
		this.kCode = kCode;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the activityCode
	 */
	public String getActivityCode() {
		return activityCode;
	}

	/**
	 * @param activityCode the activityCode to set
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
