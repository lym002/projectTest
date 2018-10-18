package com.jsjf.model.vip;

import java.math.BigDecimal;
import java.util.Date;

public class MemberVipInfo {
    private Integer id;

    private Integer uid;

    private BigDecimal growthValue;

    private Integer vipLevel;

    private Date expirationTime;

    private Date addTime;

    private Date updateTime;

    private Integer isForbidden;

    public MemberVipInfo() {
    }

    public MemberVipInfo(Integer id, Integer uid, BigDecimal growthValue, Integer vipLevel, Date expirationTime) {
        this.id = id;
        this.uid = uid;
        this.growthValue = growthValue;
        this.vipLevel = vipLevel;
        this.addTime = new Date();
        this.expirationTime = expirationTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
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

    public BigDecimal getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(BigDecimal growthValue) {
        this.growthValue = growthValue;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Integer getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(Integer isForbidden) {
        this.isForbidden = isForbidden;
    }
}