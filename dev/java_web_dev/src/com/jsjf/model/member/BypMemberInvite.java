package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

public class BypMemberInvite {
    private Integer id;

    private Integer investId;

    private Integer referrerid;

    private Integer userId;

    private BigDecimal inviteBonus;

    private Date addTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public Integer getReferrerid() {
        return referrerid;
    }

    public void setReferrerid(Integer referrerid) {
        this.referrerid = referrerid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getInviteBonus() {
        return inviteBonus;
    }

    public void setInviteBonus(BigDecimal inviteBonus) {
        this.inviteBonus = inviteBonus;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}