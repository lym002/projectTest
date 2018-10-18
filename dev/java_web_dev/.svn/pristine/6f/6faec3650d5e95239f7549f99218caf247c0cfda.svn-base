package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户投资回款信息
 */
public class DrProductInvestRepayInfo {
	
	private Integer id;//
	
	private Integer uid;//用户ID
	
	private Integer investId;
	
	private Integer pid;
	
	private BigDecimal shouldPrincipal;//应收本金	
	
	private BigDecimal factPrincipal;//实收本金
	
	private BigDecimal shouldInterest;//应收利息
	
	private BigDecimal factInterest;//实收利息
	
	private BigDecimal penalty;//罚息
	
	private Integer status;//状态 0未还款，1已还款，2逾期
	
	private Integer shouldPrincipalCount;//7天回款总和
	
	private Date shouldTime;//
	
	private Date factTime;//
	
	private Integer isgrant; //是否已發紅包
	
	

	public Integer getIsgrant() {
		return isgrant;
	}

	public void setIsgrant(Integer isgrant) {
		this.isgrant = isgrant;
	}

	public DrProductInvestRepayInfo(Integer uid, Integer investId, Integer pid, BigDecimal shouldPrincipal,
			BigDecimal factPrincipal, BigDecimal shouldInterest,
			BigDecimal factInterest, BigDecimal penalty, Integer status ,Date shouldTime) {
		super();
		this.uid = uid;
		this.setInvestId(investId);
		this.pid = pid;
		this.shouldPrincipal = shouldPrincipal;
		this.factPrincipal = factPrincipal;
		this.shouldInterest = shouldInterest;
		this.factInterest = factInterest;
		this.penalty = penalty;
		this.status = status;
		this.shouldTime = shouldTime;
	}
	public DrProductInvestRepayInfo(){
		super();
	}

	public Integer getShouldPrincipalCount() {
		return shouldPrincipalCount;
	}

	public void setShouldPrincipalCount(Integer shouldPrincipalCount) {
		this.shouldPrincipalCount = shouldPrincipalCount;
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

	public BigDecimal getShouldPrincipal() {
		return shouldPrincipal;
	}

	public void setShouldPrincipal(BigDecimal shouldPrincipal) {
		this.shouldPrincipal = shouldPrincipal;
	}

	public BigDecimal getFactPrincipal() {
		return factPrincipal;
	}

	public void setFactPrincipal(BigDecimal factPrincipal) {
		this.factPrincipal = factPrincipal;
	}

	public BigDecimal getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(BigDecimal shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public BigDecimal getFactInterest() {
		return factInterest;
	}

	public void setFactInterest(BigDecimal factInterest) {
		this.factInterest = factInterest;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getShouldTime() {
		return shouldTime;
	}

	public void setShouldTime(Date shouldTime) {
		this.shouldTime = shouldTime;
	}

	public Date getFactTime() {
		return factTime;
	}

	public void setFactTime(Date factTime) {
		this.factTime = factTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getInvestId() {
		return investId;
	}

	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	
}
