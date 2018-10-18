package com.jsjf.model.vip;

import java.util.Date;

public class MemberGrowthDetail {
    private Integer id;

    private Integer uid;

    private Long growthValue;

    private Date createdTime;

    private Integer growthValueType;

    private String growthValueDetail;

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

    public Long getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(Long growthValue) {
        this.growthValue = growthValue;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getGrowthValueType() {
        return growthValueType;
    }

    public void setGrowthValueType(Integer growthValueType) {
        this.growthValueType = growthValueType;
    }

    public String getGrowthValueDetail() {
        return growthValueDetail;
    }

    public void setGrowthValueDetail(String growthValueDetail) {
        this.growthValueDetail = growthValueDetail == null ? null : growthValueDetail.trim();
    }
}