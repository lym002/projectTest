package com.jsjf.model.integral;

import java.math.BigDecimal;
import java.util.Date;

public class UserDetailIntegralBean {
    private Integer id;

    private Integer uid;

    private Integer integralSourceId;

    private BigDecimal userDetailIntegral;

    private Date expirationTime;

    private Date addTime;

    private Date updateTime;
    
    private String mobilePhone;
    
    private String taskType;

    private Date startaddTime;
    
    private Date endaddTime;
     
    private String realName;

    private BigDecimal needPoints;

    private String prizeName;

    private Integer taskIntegralId;

    private BigDecimal grandIntegral;//累计积分
    private BigDecimal userIntegralUse;//剩余积分
    private BigDecimal useIntegral;//使用积分
    private BigDecimal loseIntegral;//过期积分
    
    public UserDetailIntegralBean(){}
    
    public UserDetailIntegralBean(Integer id, Integer uid, Integer integralSourceId, BigDecimal userDetailIntegral, Date expirationTime, Date addTime, Date updateTime, Integer taskIntegralId) {
        this.id = id;
        this.uid = uid;
        this.integralSourceId = integralSourceId;
        this.userDetailIntegral = userDetailIntegral;
        this.expirationTime = expirationTime;
        this.addTime = addTime;
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

    public Integer getIntegralSourceId() {
        return integralSourceId;
    }

    public void setIntegralSourceId(Integer integralSourceId) {
        this.integralSourceId = integralSourceId;
    }

    public BigDecimal getUserDetailIntegral() {
        return userDetailIntegral;
    }

    public void setUserDetailIntegral(BigDecimal userDetailIntegral) {
        this.userDetailIntegral = userDetailIntegral;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getStartaddTime() {
		return startaddTime;
	}

	public void setStartaddTime(Date startaddTime) {
		this.startaddTime = startaddTime;
	}

	public Date getEndaddTime() {
		return endaddTime;
	}

	public void setEndaddTime(Date endaddTime) {
		this.endaddTime = endaddTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getGrandIntegral() {
		return grandIntegral;
	}

	public void setGrandIntegral(BigDecimal grandIntegral) {
		this.grandIntegral = grandIntegral;
	}

	public BigDecimal getUserIntegralUse() {
		return userIntegralUse;
	}

	public void setUserIntegralUse(BigDecimal userIntegralUse) {
		this.userIntegralUse = userIntegralUse;
	}

	public BigDecimal getUseIntegral() {
		return useIntegral;
	}

	public void setUseIntegral(BigDecimal useIntegral) {
		this.useIntegral = useIntegral;
	}

	public BigDecimal getLoseIntegral() {
		return loseIntegral;
	}

	public void setLoseIntegral(BigDecimal loseIntegral) {
		this.loseIntegral = loseIntegral;
	}


    public BigDecimal getNeedPoints() {
        return needPoints;
    }

    public void setNeedPoints(BigDecimal needPoints) {
        this.needPoints = needPoints;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getTaskIntegralId() {
        return taskIntegralId;
    }

    public void setTaskIntegralId(Integer taskIntegralId) {
        this.taskIntegralId = taskIntegralId;
    }
}