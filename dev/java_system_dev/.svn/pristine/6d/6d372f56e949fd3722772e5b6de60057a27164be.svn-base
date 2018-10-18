package com.jzh.data;

import net.sf.json.JSONObject;

public class claimsRspData extends BaseRspdata{

	private String cust_nm;//企业名称
	private String artif_nm;//法人姓名
	private String mobile_no;//手机号码
	private String certif_id;//身份证号码
	private String email;//邮箱地址
	private String city_id;//开户行地区代码
	private String parent_bank_id;//开户行行别
	private String bank_nm;//开户行支行名称
	private String user_id_from;//用户在商户系统的
    private String capAcntNo;//标志
    
    
    
	
	public String getCust_nm() {
		return cust_nm;
	}




	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}




	public String getArtif_nm() {
		return artif_nm;
	}




	public void setArtif_nm(String artif_nm) {
		this.artif_nm = artif_nm;
	}




	public String getMobile_no() {
		return mobile_no;
	}




	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}




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




	public String getUser_id_from() {
		return user_id_from;
	}




	public void setUser_id_from(String user_id_from) {
		this.user_id_from = user_id_from;
	}




	public String getCapAcntNo() {
		return capAcntNo;
	}




	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}




	public claimsRspData(JSONObject message) {
		super();
		if (message.containsKey("cust_nm")) {this.cust_nm = (String)message.get("cust_nm");}
		if (message.containsKey("artif_nm")) {this.artif_nm = (String)message.get("artif_nm");}
		if (message.containsKey("mobile_no")) {this.mobile_no = (String)message.get("mobile_no");}
		if (message.containsKey("certif_id")) {this.certif_id = (String)message.get("certif_id");}
		if (message.containsKey("email")) {this.email = (String)message.get("email");}
		if (message.containsKey("city_id")) {this.city_id = (String)message.get("city_id");}
		if (message.containsKey("parent_bank_id")) {this.parent_bank_id = (String)message.get("parent_bank_id");}
		if (message.containsKey("bank_nm")) {this.bank_nm = (String)message.get("bank_nm");}
		if (message.containsKey("user_id_from")) {this.user_id_from = (String)message.get("user_id_from");}
		if (message.containsKey("capAcntNo")) {this.capAcntNo = (String)message.get("capAcntNo");}
		
		
		if (message.containsKey("signature")) {this.setSignature((String)message.get("signature"));}		
		if (message.containsKey("mchnt_cd")) {super.setMchnt_cd((String)message.get("mchnt_cd"));}
		if (message.containsKey("mchnt_txn_ssn")) {super.setMchnt_txn_ssn((String)message.get("mchnt_txn_ssn")) ;}
		if (message.containsKey("resp_code")) {super.setResp_code((String)message.get("resp_code"));}
		
	}
}
