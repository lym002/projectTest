package com.jzh.data;


public class WebReqData extends BaseReqdata{
	
	private String ser_id_from;//用户在商户系统的标志
	private String ver; //接口版本号
	private String mobile_no;//手机号码
	private String cust_nm;//客户姓名
//	private String certif_tp;//证件类型
	private String certif_id;//证件号码
	private String email;//邮箱地址
	private String city_id;//开户行地区代码
	private String parent_bank_id;//开户行行别
	private String bank_nm;//开户行支行名称
	private String capAcntNo;//帐号
	private String back_notify_url; //商户后台通知地址 （根据具体接口判断非空）
	private String page_notify_url; //商户前端返回地址 （根据具体接口判断非空）
	public String getSer_id_from() {
		return ser_id_from;
	}
	public void setSer_id_from(String ser_id_from) {
		this.ser_id_from = ser_id_from;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
//	public String getCertif_tp() {
//		return certif_tp;
//	}
//	public void setCertif_tp(String certif_tp) {
//		this.certif_tp = certif_tp;
//	}
	public String getCertif_id() {
		return certif_id;
	}
	public void setCertif_id(String certif_id) {
		this.certif_id = certif_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getParent_bank_id() {
		return parent_bank_id;
	}
	public void setParent_bank_id(String parent_bank_id) {
		this.parent_bank_id = parent_bank_id;
	}
	public String getBank_nm() {
		return bank_nm;
	}
	public void setBank_nm(String bank_nm) {
		this.bank_nm = bank_nm;
	}
	public String getCapAcntNo() {
		return capAcntNo;
	}
	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}
	public String getBack_notify_url() {
		return back_notify_url;
	}
	public void setBack_notify_url(String back_notify_url) {
		this.back_notify_url = back_notify_url;
	}
	public String getPage_notify_url() {
		return page_notify_url;
	}
	public void setPage_notify_url(String page_notify_url) {
		this.page_notify_url = page_notify_url;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	
	
	
}
