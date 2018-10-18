package com.jzh.data;


public class WebLoginData extends BaseReqdata{
	

	
	private String cust_no;//登录账户
	private String location;//成功登录后跳转页面代码
	private String amt;//跳转充值、提现页面锁定金额
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	
	
	
	
}
