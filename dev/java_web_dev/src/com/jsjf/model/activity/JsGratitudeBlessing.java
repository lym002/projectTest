package com.jsjf.model.activity;

import java.util.Date;

/**
 * 感恩活动
 *
 */
public class JsGratitudeBlessing {
	
	private Integer id;
	private Integer uid; //用户ID
	private String blessing; //祝福语
	private Integer status; //0-待审核，1-显示，2-不显示
	private Date addtime; //祝福提交时间
	private Date updatetime; //审核时间
	private Integer split; //0-未拆 1-已拆
	private Date splitaddtime; //拆红包时间
	private String mobilePhone; //手机号
	
	
	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public JsGratitudeBlessing() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public JsGratitudeBlessing(Integer id, Integer uid, String blessing,
			Integer status, Date addtime, Date updatetime, Integer split,
			Date splitaddtime) {
		super();
		this.id = id;
		this.uid = uid;
		this.blessing = blessing;
		this.status = status;
		this.addtime = addtime;
		this.updatetime = updatetime;
		this.split = split;
		this.splitaddtime = splitaddtime;
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

	public String getBlessing() {
		return blessing;
	}

	public void setBlessing(String blessing) {
		this.blessing = blessing;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getSplit() {
		return split;
	}

	public void setSplit(Integer split) {
		this.split = split;
	}

	public Date getSplitaddtime() {
		return splitaddtime;
	}

	public void setSplitaddtime(Date splitaddtime) {
		this.splitaddtime = splitaddtime;
	}
	
	

}
