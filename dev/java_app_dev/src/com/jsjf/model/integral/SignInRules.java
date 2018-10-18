package com.jsjf.model.integral;

import java.math.BigDecimal;
import java.util.Date;

public class SignInRules {
    private Integer id;

    private Integer signinDay;

    private BigDecimal signinIntegral;

    private Date addTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSigninDay() {
        return signinDay;
    }

    public void setSigninDay(Integer signinDay) {
        this.signinDay = signinDay;
    }

    public BigDecimal getSigninIntegral() {
        return signinIntegral;
    }

    public void setSigninIntegral(BigDecimal signinIntegral) {
        this.signinIntegral = signinIntegral;
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