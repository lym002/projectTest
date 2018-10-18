package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class JsProductPrizeLog {
	private Integer id;
	private Integer uid;//用户id
	private Integer investId;//投资id
	private Integer ppid;//奖品id
	private Date addTime;
	private Integer type;//定单类型,0投资定单,1预约定单  
	
	private String realname;
	private String mobilePhone;
	private Date addTimeStart;
	private Date addTimeEnd;
	private String name;//用户name
	private String phone;//用户手机 
	private String address;//用户 地址
	private int deadline;//募集期
	private BigDecimal rate;
	private Date investTime;
	private BigDecimal investAmount;//投资金额
	private String code;
	private String prizeName;
	private String fullName;//标的名称
	private String recommCodes;//推荐码
	
	private String userName;//所属电销
	private String allotName;//所属部门
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public JsProductPrizeLog() {
		super();
	}
	public JsProductPrizeLog(Integer id, Integer uid, Integer investId,
			Integer ppid, Date addTime, Integer type) {
		super();
		this.id = id;
		this.uid = uid;
		this.investId = investId;
		this.ppid = ppid;
		this.addTime = addTime;
		this.type = type;
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
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getPpid() {
		return ppid;
	}
	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Date getAddTimeStart() {
		return addTimeStart;
	}
	public void setAddTimeStart(Date addTimeStart) {
		this.addTimeStart = addTimeStart;
	}
	public Date getAddTimeEnd() {
		return addTimeEnd;
	}
	public void setAddTimeEnd(Date addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getRecommCodes() {
		return recommCodes;
	}
	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAllotName() {
		return allotName;
	}
	public void setAllotName(String allotName) {
		this.allotName = allotName;
	}
	
	
}
