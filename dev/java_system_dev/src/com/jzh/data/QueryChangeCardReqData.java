package com.jzh.data;

public class QueryChangeCardReqData extends BaseReqdata{
	private String login_id;
	private String txn_ssn;
	
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getTxn_ssn() {
		return txn_ssn;
	}
	public void setTxn_ssn(String txn_ssn) {
		this.txn_ssn = txn_ssn;
	}
	
}
