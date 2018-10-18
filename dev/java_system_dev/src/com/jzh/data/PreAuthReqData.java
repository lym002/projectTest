package com.jzh.data;
/**
 * 预授权接口
 * @author Ernes
 *
 */
public class PreAuthReqData extends BaseReqdata {
	private String out_cust_no;//出账账户
	private String in_cust_no;//入账账户
	private String amt;//预授权金额
	private String rem;//备注
	private String ver; //接口版本号
	
	
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
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
