package com.jsjf.model.article;

import java.util.Date;

public class SysProgram {
    /**
     * 主键id 自增  
     */
    private Integer proId;
    /**
     * 栏目名称  
     */
    private String proName;

    /**
     * 栏目描述  
     */
    private String description;

    /**
     * 是否活动  
     */
    private Short active;

    /**
     * 备注  
     */
    private String remark;

    /**
     * 创建时间  
     */
    private Date createTime;

    /**
     * 创建�?  
     */
    private Short createUser;

    /**
     * 更新时间  
     */
    private Date updateTime;

    /**
     * 更改�?  
     */
    private Short updateUser;

    /**
     * 0表示删除 1表示有效  
     */
    private Short status;
    
    private String createName;
    private String updateName;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Short createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Short updateUser) {
        this.updateUser = updateUser;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
    
    
}