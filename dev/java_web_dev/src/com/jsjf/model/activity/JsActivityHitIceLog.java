package com.jsjf.model.activity;

import java.util.Date;

/**
 * 砸冰活动记录
 * @author DELL
 *
 */
public class JsActivityHitIceLog {
	private Integer id ;//
	private Integer uid ;//
	private Integer type ;//
	private Integer goldNum ;//
	private Integer investId ;//
	private Date addTime ;//
	private Date updateTime ;//
	
	
	public JsActivityHitIceLog() {
	}
	
	public JsActivityHitIceLog( Integer uid, Integer type,
			Integer goldNum, Integer investId, Date addTime) {
		super();
		this.uid = uid;
		this.type = type;
		this.goldNum = goldNum;
		this.investId = investId;
		this.addTime = addTime;		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getGoldNum() {
		return goldNum;
	}
	public void setGoldNum(Integer goldNum) {
		this.goldNum = goldNum;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
