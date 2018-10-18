package com.jsjf.model.vip;

import java.math.BigDecimal;
import java.util.Date;

public class VipInfo {
    private Integer id;

    private Integer vipLevel;

    private String vipName;

    private String levelUpdateRedPacket;

    private BigDecimal growthValueMin;

    private BigDecimal growthValueMax;

    private Double integralMultiply;

    private String rightsAndInterestsId;

    private Integer freeWithdrawDeposit;

    private Date createdTime;

    private Date updateTime;
    
    private String growthSection;

    private String equitiesName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName == null ? null : vipName.trim();
    }

    public String getLevelUpdateRedPacket() {
        return levelUpdateRedPacket;
    }

    public void setLevelUpdateRedPacket(String levelUpdateRedPacket) {
        this.levelUpdateRedPacket = levelUpdateRedPacket == null ? null : levelUpdateRedPacket.trim();
    }

    public BigDecimal getGrowthValueMin() {
        return growthValueMin;
    }

    public void setGrowthValueMin(BigDecimal growthValueMin) {
        this.growthValueMin = growthValueMin;
    }

    public BigDecimal getGrowthValueMax() {
        return growthValueMax;
    }

    public void setGrowthValueMax(BigDecimal growthValueMax) {
        this.growthValueMax = growthValueMax;
    }

    public Double getIntegralMultiply() {
        return integralMultiply;
    }

    public void setIntegralMultiply(Double integralMultiply) {
        this.integralMultiply = integralMultiply;
    }

    public String getRightsAndInterestsId() {
        return rightsAndInterestsId;
    }

    public void setRightsAndInterestsId(String rightsAndInterestsId) {
        this.rightsAndInterestsId = rightsAndInterestsId == null ? null : rightsAndInterestsId.trim();
    }

    public Integer getFreeWithdrawDeposit() {
        return freeWithdrawDeposit;
    }

    public void setFreeWithdrawDeposit(Integer freeWithdrawDeposit) {
        this.freeWithdrawDeposit = freeWithdrawDeposit;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getGrowthSection() {
		return growthSection;
	}

	public void setGrowthSection(String growthSection) {
		this.growthSection = growthSection;
	}

	public String getEquitiesName() {
		return equitiesName;
	}

	public void setEquitiesName(String equitiesName) {
		this.equitiesName = equitiesName;
	}
	
    
}