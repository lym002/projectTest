package com.jsjf.model.activity;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统活动
 *
 */
public class JsSignIn implements Serializable{
	
	private Integer id; //
	private Integer type; //0:叱咤风云签到
	private Integer signNu; //签到次数
	private Integer uid; 
	private Date addtime; //添加时间
	private Date updateTime; //签到时间
	private String mobilePhone;//手机号
	
	public JsSignIn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}



	public Date getAddtime() {
		return addtime;
	}


	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSignNu() {
		return signNu;
	}
	public void setSignNu(Integer signNu) {
		this.signNu = signNu;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}
