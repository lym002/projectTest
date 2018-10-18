/**
 * 
 */
package com.jsjf.model.member;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gerald
 *
 */
public class DrMemberRecommended {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户UID
	 */
	private Integer uid;
	/**
	 * 推荐人UID
	 */
	private Integer referrerId;
	/**
	 * 首投时间
	 */
	private Date investTime;
	/**
	 * 首投金额
	 */
	private BigDecimal amount;
	/**
	 * 推荐人获取奖励
	 */
	private BigDecimal referrerReward;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 好友手机
	 */
	private String mobilePhone;
	/**
	 * 好友实名
	 */
	private String realName;
	
	private Integer investStatus;
	

	
	public DrMemberRecommended() {
		super();
	}
	
	public DrMemberRecommended(Integer uid, Integer referrerId,
			Date investTime, BigDecimal amount, BigDecimal referrerReward,
			Date addTime) {
		super();
		this.uid = uid;
		this.referrerId = referrerId;
		this.investTime = investTime;
		this.amount = amount;
		this.referrerReward = referrerReward;
		this.addTime = addTime;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the uid
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * @return the referrerId
	 */
	public Integer getReferrerId() {
		return referrerId;
	}
	/**
	 * @param referrerId the referrerId to set
	 */
	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}
	/**
	 * @return the investTime
	 */
	public Date getInvestTime() {
		return investTime;
	}
	/**
	 * @param investTime the investTime to set
	 */
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the referrerReward
	 */
	public BigDecimal getReferrerReward() {
		return referrerReward;
	}
	/**
	 * @param referrerReward the referrerReward to set
	 */
	public void setReferrerReward(BigDecimal referrerReward) {
		this.referrerReward = referrerReward;
	}
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getInvestStatus() {
		if(this.investTime!=null){
			return 1;
		}else{
			return 0;
		}
	}

	public void setInvestStatus(Integer investStatus) {
		this.investStatus = investStatus;
	}
}
