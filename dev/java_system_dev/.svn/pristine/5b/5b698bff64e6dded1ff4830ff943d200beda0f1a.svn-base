package com.jzh.data;
/**
 * 充值/提现通知接口数据解析
 * @author aj
 *
 */
public class WtRechargeAndWtWithdrawalRspData {
	private String mchnt_cd="";
	private String mchnt_txn_ssn="";
	private String mchnt_txn_dt="";
	private String amt="";
	private String resp_code="";
	private String signature="";
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
	public String getMchnt_txn_dt() {
		return mchnt_txn_dt;
	}
	public void setMchnt_txn_dt(String mchnt_txn_dt) {
		this.mchnt_txn_dt = mchnt_txn_dt;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String createSignValue(){
		String src =amt+ "|"+mchnt_cd + "|"+ mchnt_txn_dt + "|"+ mchnt_txn_ssn+ "|"+resp_code;
		System.out.println("委托充值委托提现签名返回明文>>>>"+src);
		return src;
	}
}
