package com.jzh.data;

import com.jzh.data.BaseReqdata;

public class FuiouOnlineBankingReq extends BaseReqdata{
	private String login_id;//用户登录名
	private String amt;//充值金额
	private String page_notify_url;//商户返回地址
	private String back_notify_url;//商户后台调用地址
	
	public FuiouOnlineBankingReq() {
		super();
	}
	
	public FuiouOnlineBankingReq(String login_id, String amt, String page_notify_url, String back_notify_url) {
		super();
		this.login_id = login_id;
		this.amt = amt;
		this.page_notify_url = page_notify_url;
		this.back_notify_url = back_notify_url;
	}


	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPage_notify_url() {
		return page_notify_url;
	}

	public void setPage_notify_url(String page_notify_url) {
		this.page_notify_url = page_notify_url;
	}

	public String getBack_notify_url() {
		return back_notify_url;
	}

	public void setBack_notify_url(String back_notify_url) {
		this.back_notify_url = back_notify_url;
	}
	
}
