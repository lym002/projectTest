package com.jytpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class JYTSendData implements Serializable{
    private static final long serialVersionUID = 1L;
    private String            bank_name;          // 银行名称
    private String            account_no;                 // 银行账号
    private String            account_name;            // 银行账号名称
    private String            account_type;          // 账户类型00：对私 01：对公
    private BigDecimal        tran_amt;          // 交易金额
    private String            currency;           // 币种
    private String            bsn_code;           // 业务类型代码
    private String            mobile;          // 手机
    private String            tran_code;	//交易代码
    private String            tran_flowid; //交易流水号
    private String            ori_tran_flowid; //原交易流水号
    private String            remark;//摘要
    
    private String            mer_viral_acct; //虚拟账号
    
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public BigDecimal getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(BigDecimal tran_amt) {
		this.tran_amt = tran_amt;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getBsn_code() {
		return bsn_code;
	}
	public void setBsn_code(String bsn_code) {
		this.bsn_code = bsn_code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTran_code() {
		return tran_code;
	}
	public void setTran_code(String tran_code) {
		this.tran_code = tran_code;
	}
	public String getTran_flowid() {
		return tran_flowid;
	}
	public void setTran_flowid(String tran_flowid) {
		this.tran_flowid = tran_flowid;
	}
	public String getOri_tran_flowid() {
		return ori_tran_flowid;
	}
	public void setOri_tran_flowid(String ori_tran_flowid) {
		this.ori_tran_flowid = ori_tran_flowid;
	}
	public String getMer_viral_acct() {
		return mer_viral_acct;
	}
	public void setMer_viral_acct(String mer_viral_acct) {
		this.mer_viral_acct = mer_viral_acct;
	}
	@Override
	public String toString() {
		return "JYTSendData [bank_name=" + bank_name + ", account_no="
				+ account_no + ", account_name=" + account_name
				+ ", account_type=" + account_type + ", tran_amt=" + tran_amt
				+ ", currency=" + currency + ", bsn_code=" + bsn_code
				+ ", mobile=" + mobile + ", tran_code=" + tran_code
				+ ", tran_flowid=" + tran_flowid + ", ori_tran_flowid="
				+ ori_tran_flowid + ", mer_viral_acct=" + mer_viral_acct + "]";
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
