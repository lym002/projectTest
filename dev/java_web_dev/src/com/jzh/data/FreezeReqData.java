package com.jzh.data;

import com.jzh.data.BaseReqdata;

public class FreezeReqData  extends BaseReqdata{

	private String ver = ""; //M文档版本号
	private String cust_no = ""; //M冻结目标登录账户
	private String amt = ""; //M冻结金额
	private String rem = ""; //O备注
	
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
}
