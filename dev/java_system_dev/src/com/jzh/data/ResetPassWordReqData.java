package com.jzh.data;

import com.jzh.data.BaseReqdata;

public class ResetPassWordReqData extends BaseReqdata{
	
	private String user_id; //用户ID
	private String login_id; //用户登录ID
	private String busi_tp; //业务类型
	private String back_url; //返回地址
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getBusi_tp() {
		return busi_tp;
	}
	public void setBusi_tp(String busi_tp) {
		this.busi_tp = busi_tp;
	}
	public String getBack_url() {
		return back_url;
	}
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}
	
	
	

}
