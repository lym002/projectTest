package com.jzh.data;

import net.sf.json.JSONObject;

public class PwdFrozenReqData extends BaseReqdata {
	private String user_id;//冻结用户ID
	private JSONObject[] acnts_amt;//冻结账户及金额
	private String project_no;//存管系统项目编号
	private String busi_tp;//业务类型，A：投标，B：还款	C：债权转让
	private String in_user_id;//入账账户ID，若填了则与对应项目的借款人做比对，须与项目的借款人保持一致
	private String page_notify_url;//商户前台地址
	private String back_notify_url;//商户后台地址
	private String rem;//备注
	private String client_tp;//网页类型
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public JSONObject[] getAcnts_amt() {
		return acnts_amt;
	}
	public void setAcnts_amt(JSONObject[] acnts_amt) {
		this.acnts_amt = acnts_amt;
	}
	public String getProject_no() {
		return project_no;
	}
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	public String getBusi_tp() {
		return busi_tp;
	}
	public void setBusi_tp(String busi_tp) {
		this.busi_tp = busi_tp;
	}
	public String getIn_user_id() {
		return in_user_id;
	}
	public void setIn_user_id(String in_user_id) {
		this.in_user_id = in_user_id;
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
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getClient_tp() {
		return client_tp;
	}
	public void setClient_tp(String client_tp) {
		this.client_tp = client_tp;
	}
}
