package com.jsjf.model.filing;

import java.math.BigDecimal;

public class DrFiling {
	private Integer id;
	private String mchnt_cd;//商户号
	private String dealType;//交易类型
	private String mchntTxnSsn;//在存管系统的对应交易流水
	private String mchTime;//20150603存管系统的交易日期
	private String type;//交易类型
	private String itemNo;//项目报备成功后的有效项目编号
	private String code;//合同号
	private String out_cust_no;//出账人存管账户系统用户名
	private String realname;//存管系统姓名法人用户就是公司名
	private BigDecimal amount;//金额
	private String in_cust_no;//入账人存管账户系统用户名
	private String name;//入账人平台用户名
	private String businessType;//业务类型
	private String paycompanyid;//第三方支付公司ID
	private String failureCause;//失败原因
	private String repairTpe;//补报类型
	private String fileStatus;//补报状态  1正常报备处理中 2报备成功 3 失败 4已补报
	private String fullFileStatus;//放款补报状态  1正常报备处理中 2报备成功 3 失败 4已补报
	private String failureCauseType;//根据失败原因查询
	
	
	
	
	public String getFailureCauseType() {
		return failureCauseType;
	}
	public void setFailureCauseType(String failureCauseType) {
		this.failureCauseType = failureCauseType;
	}
	public String getFullFileStatus() {
		return fullFileStatus;
	}
	public void setFullFileStatus(String fullFileStatus) {
		this.fullFileStatus = fullFileStatus;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getOut_cust_no() {
		return out_cust_no;
	}
	public void setOut_cust_no(String out_cust_no) {
		this.out_cust_no = out_cust_no;
	}
	public String getIn_cust_no() {
		return in_cust_no;
	}
	public void setIn_cust_no(String in_cust_no) {
		this.in_cust_no = in_cust_no;
	}
	public String getRepairTpe() {
		return repairTpe;
	}
	public void setRepairTpe(String repairTpe) {
		this.repairTpe = repairTpe;
	}
	public String getFailureCause() {
		return failureCause;
	}
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getMchnt_cd() {
		return mchnt_cd;
	}
	public void setMchnt_cd(String mchnt_cd) {
		this.mchnt_cd = mchnt_cd;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMchntTxnSsn() {
		return mchntTxnSsn;
	}
	public void setMchntTxnSsn(String mchntTxnSsn) {
		this.mchntTxnSsn = mchntTxnSsn;
	}
	public String getMchTime() {
		return mchTime;
	}
	public void setMchTime(String mchTime) {
		this.mchTime = mchTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPaycompanyid() {
		return paycompanyid;
	}
	public void setPaycompanyid(String paycompanyid) {
		this.paycompanyid = paycompanyid;
	}
	@Override
	public String toString() {
		return "DrFiling [id=" + id + ", mchnt_cd=" + mchnt_cd + ", dealType=" + dealType + ", mchntTxnSsn="
				+ mchntTxnSsn + ", mchTime=" + mchTime + ", type=" + type + ", itemNo=" + itemNo + ", code=" + code
				+ ", out_cust_no=" + out_cust_no + ", realname=" + realname + ", amount=" + amount + ", in_cust_no="
				+ in_cust_no + ", name=" + name + ", businessType=" + businessType + ", paycompanyid=" + paycompanyid
				+ ", failureCause=" + failureCause + ", repairTpe=" + repairTpe + ", fileStatus=" + fileStatus
				+ ", fullFileStatus=" + fullFileStatus + ", failureCauseType=" + failureCauseType + "]";
	}
	
	
}

