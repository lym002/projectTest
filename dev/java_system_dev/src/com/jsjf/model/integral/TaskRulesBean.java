package com.jsjf.model.integral;

import java.math.BigDecimal;
import java.util.Date;

public class TaskRulesBean {
    private Integer id;

    private String taskType;

    private BigDecimal taskIntegral;

    private Integer isFirstTask;

    private Date addTime;

    private Date updateTime;

    private Integer isOpen;
    
    private BigDecimal taskMoneyRequire;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public BigDecimal getTaskIntegral() {
        return taskIntegral;
    }

    public void setTaskIntegral(BigDecimal taskIntegral) {
        this.taskIntegral = taskIntegral;
    }

    public Integer getIsFirstTask() {
        return isFirstTask;
    }

    public void setIsFirstTask(Integer isFirstTask) {
        this.isFirstTask = isFirstTask;
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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

	public BigDecimal getTaskMoneyRequire() {
		return taskMoneyRequire;
	}

	public void setTaskMoneyRequire(BigDecimal taskMoneyRequire) {
		this.taskMoneyRequire = taskMoneyRequire;
	}
    
}