package com.jsjf.model.vip;

import java.math.BigDecimal;
import java.util.Date;

public class MemberGrowthValueDetail {

    private Integer id;

    private Integer uid;

    private BigDecimal growthValue;

    private Date createdTime;

    private Integer growthValueType;

    private String growthValueDetail;

    public MemberGrowthValueDetail() {
    }

    public MemberGrowthValueDetail(Integer id, Integer uid, BigDecimal growthValue, Date createdTime, Integer growthValueType, String growthValueDetail) {
        this.id = id;
        this.uid = uid;
        this.growthValue = growthValue;
        this.createdTime = createdTime;
        this.growthValueType = growthValueType;
        this.growthValueDetail = growthValueDetail;
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