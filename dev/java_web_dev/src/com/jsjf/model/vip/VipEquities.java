package com.jsjf.model.vip;

import java.util.Date;

public class VipEquities {
    private Integer id;

    private String equitiesName;

    private Integer status;

    private String equitiesExplain;

    private String openLevel;

    private Date updateTime;

    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquitiesName() {
        return equitiesName;
    }

    public void setEquitiesName(String equitiesName) {
        this.equitiesName = equitiesName == null ? null : equitiesName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEquitiesExplain() {
        return equitiesExplain;
    }

    public void setEquitiesExplain(String equitiesExplain) {
        this.equitiesExplain = equitiesExplain == null ? null : equitiesExplain.trim();
    }

    public String getOpenLevel() {
        return openLevel;
    }

    public void setOpenLevel(String openLevel) {
        this.openLevel = openLevel == null ? null : openLevel.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}