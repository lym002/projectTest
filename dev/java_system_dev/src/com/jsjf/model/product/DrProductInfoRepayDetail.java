package com.jsjf.model.product;

import java.util.Date;
/**
 * 按月付息产品回款明细信息
 * @author DELL
 *
 */
public class DrProductInfoRepayDetail {
	private Integer id;
	private Integer pid;//产品ID
	private Integer periods;//期数
	private Integer status = 1;//还款状态，1-未还款，2-已还款，3-逾期
	private Date shouldTime;//每期还款日期
	private Date addDate;//创建时间
	private Date updDate;//更新时间
	
	public DrProductInfoRepayDetail(){
		
	}
	public DrProductInfoRepayDetail(Integer pid, Integer periods,
			Date shouldTime) {
		super();
		this.pid = pid;
		this.periods = periods;
		this.shouldTime = shouldTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
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
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
}
