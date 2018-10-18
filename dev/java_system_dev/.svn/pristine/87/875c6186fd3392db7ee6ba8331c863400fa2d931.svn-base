package com.jzh.data;

public class WtwithdrawRspData{
	private String mobile_no;
	private String amt;
	private String remark;
	private String mchnt_txn_dt;
	private String mchnt_cd;
	private String mchnt_txn_ssn;
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMchnt_txn_dt() {
		return mchnt_txn_dt;
	}
	public void setMchnt_txn_dt(String mchnt_txn_dt) {
		this.mchnt_txn_dt = mchnt_txn_dt;
	}
	
	public String getMchnt_cd() {
		return mchnt_cd;
	}
	public void setMchnt_cd(String mchnt_cd) {
		this.mchnt_cd = mchnt_cd;
	}
	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}
	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}
	public String createSignValue(){
		String src =amt+"|"+mchnt_cd+"|"+mchnt_txn_dt+"|"+mchnt_txn_ssn+"|"+mobile_no+"|"+remark;
		System.out.println("返回签名明文>>>>"+src);
		return src;
	}
	
}
