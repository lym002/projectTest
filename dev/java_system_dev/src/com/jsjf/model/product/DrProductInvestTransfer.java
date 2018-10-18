package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class DrProductInvestTransfer {
	
	private Integer id;
	
	private Integer transferorUid;
	
	private Integer assigneeUid;
	
	private Integer pid;
	
	private Integer transferorInvestId;
	
	private Integer assigneeInvestId;
	
	private Date addTime;
	
	private BigDecimal amount;
	
	public DrProductInvestTransfer(){}

	public DrProductInvestTransfer(Integer transferorUid, Integer assigneeUid,
			Integer pid, Integer transferorInvestId, Integer assigneeInvestId,
			Date addTime, BigDecimal amount) {
		super();
		this.transferorUid = transferorUid;
		this.assigneeUid = assigneeUid;
		this.pid = pid;
		this.transferorInvestId = transferorInvestId;
		this.assigneeInvestId = assigneeInvestId;
		this.addTime = addTime;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTransferorUid() {
		return transferorUid;
	}

	public void setTransferorUid(Integer transferorUid) {
		this.transferorUid = transferorUid;
	}

	public Integer getAssigneeUid() {
		return assigneeUid;
	}

	public void setAssigneeUid(Integer assigneeUid) {
		this.assigneeUid = assigneeUid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getTransferorInvestId() {
		return transferorInvestId;
	}

	public void setTransferorInvestId(Integer transferorInvestId) {
		this.transferorInvestId = transferorInvestId;
	}

	public Integer getAssigneeInvestId() {
		return assigneeInvestId;
	}

	public void setAssigneeInvestId(Integer assigneeInvestId) {
		this.assigneeInvestId = assigneeInvestId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
