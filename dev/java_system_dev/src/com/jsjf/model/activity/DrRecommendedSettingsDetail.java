package com.jsjf.model.activity;

import java.math.BigDecimal;

public class DrRecommendedSettingsDetail {
	private Integer id;
	private Integer rid;
	private BigDecimal amount;
	private BigDecimal reward;
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
	 * @return the sid
	 */
	public Integer getRid() {
		return rid;
	}
	/**
	 * @param sid the sid to set
	 */
	public void setRid(Integer rid) {
		this.rid = rid;
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
	 * @return the reward
	 */
	public BigDecimal getReward() {
		return reward;
	}
	/**
	 * @param reward the reward to set
	 */
	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
}
