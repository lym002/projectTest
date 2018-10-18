package com.jsjf.model.vip;

import java.util.Date;

public class VipEquitiesMember {
    private Integer id;

    private Integer uid;

    private String mobilePhone;

    private Integer vipLevel;

    private Integer vipEquitiesId;

    private Date addTime;
    
    private Date startAddTime;
    
    private Date endAddTime;
    
    private String equitiesName;
    
    private String VipName;

    public VipEquitiesMember() {
    }

    public VipEquitiesMember(Integer id, Integer uid, String mobilePhone, Integer vipLevel, Integer vipEquitiesId, Date addTime) {
        this.id = id;
        this.uid = uid;
        this.mobilePhone = mobilePhone;
        this.vipLevel = vipLevel;
        this.vipEquitiesId = vipEquitiesId;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Integer getVipEquitiesId() {
        return vipEquitiesId;
    }

    public void setVipEquitiesId(Integer vipEquitiesId) {
        this.vipEquitiesId = vipEquitiesId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public Date getStartAddTime() {
		return startAddTime;
	}

	public void setStartAddTime(Date startAddTime) {
		this.startAddTime = startAddTime;
	}

	public Date getEndAddTime() {
		return endAddTime;
	}

	public void setEndAddTime(Date endAddTime) {
		this.endAddTime = endAddTime;
	}

	public String getEquitiesName() {
		return equitiesName;
	}

	public void setEquitiesName(String equitiesName) {
		this.equitiesName = equitiesName;
	}

	public String getVipName() {
		return VipName;
	}

	public void setVipName(String vipName) {
		VipName = vipName;
	}
	
    
}