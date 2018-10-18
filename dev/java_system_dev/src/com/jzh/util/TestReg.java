package com.jzh.util;

import java.util.Set;

import com.jzh.http.WebUtils;

public class TestReg {
	
	private String mchnt_cd="";     //商户代码                  
	private String mchnt_txn_ssn="";//流水号                    
	private String cust_nm="";      //注册企业名称              
	private String certif_id="";    //法人身份证号              
	private String mobile_no="";    //手机号                    
	private String email="";        //邮箱地址                  
	private String rem="";          //备注信息（企业号）        
	private String city_id="";      //开户区县代码  
	private String  parent_bank_id="";//开户银行总行号
	private String capAcntNo="";    //账号                      
	private String capAcntNm="";    //账号户名                  
	private String lpassword="";    //登录密码                  
	private String password="";     //支付密码                  
	private String signature="";    //签名数据       
	
	private String bank_nm="";
	
	public String getBank_nm() {
		return bank_nm;
	}

	public void setBank_nm(String bank_nm) {
		this.bank_nm = bank_nm;
	}

	//其他get\set方法省略
	//.......
	public void setSignature(String signature) {
		this.signature = signature;
	}

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

	public String getCust_nm() {
		return cust_nm;
	}

	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}

	public String getCertif_id() {
		return certif_id;
	}

	public void setCertif_id(String certif_id) {
		this.certif_id = certif_id;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRem() {
		return rem;
	}

	public void setRem(String rem) {
		this.rem = rem;
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

	public String getCapAcntNo() {
		return capAcntNo;
	}

	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}

	public String getCapAcntNm() {
		return capAcntNm;
	}

	public void setCapAcntNm(String capAcntNm) {
		this.capAcntNm = capAcntNm;
	}

	public String getLpassword() {
		return lpassword;
	}

	public void setLpassword(String lpassword) {
		this.lpassword = lpassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return signature;
	}

	/**
	 * 注册时请求的明文
	 * @return
	 */
	public String regSignVal(){
		String src = bank_nm+"|"+capAcntNm+"|"
		+capAcntNo+"|"+certif_id+"|"+city_id+"|"+cust_nm+"|"
		+email+"|"+lpassword+"|"+mchnt_cd+"|"+mchnt_txn_ssn+"|"
		+mobile_no+"|"+parent_bank_id+"|"+password+"|"+rem;
		return src;
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub、
		//注册请求
		TestReg regData=new TestReg();
		//设值
//		密码需md5加密，如：MD5Util.encode("密码明文,如888888", "UTF-8");
//		regData.setCapAcntNm("xxx");
//		regData.setCapAcntNo("xxx");
//		....
		regData.setBank_nm("中国农业银行");
		regData.setCapAcntNm("");
		regData.setCapAcntNo("62284803111111111111");
		regData.setCertif_id("330382111111010110");
		regData.setCity_id("3333");
		regData.setCust_nm("猪");
		regData.setEmail("");
		regData.setLpassword("97b149a269795ef98a7e31b66d1f105e");
		regData.setMchnt_cd("0002900F0041077");
		regData.setMchnt_txn_ssn("96f14200a794dbcc91cad69b50ef05");
		regData.setMobile_no("11111111111");
		regData.setParent_bank_id("0103");
		regData.setPassword("97b149a269795ef98a7e31b66d1f105e");
		regData.setRem("");
		//请求明文
		String inputStr=regData.regSignVal();
		//密文(私钥加密)
		String signatureStr=SecurityUtils.sign(inputStr);
//		String signatureStr=SecurityUtils.sign("0002900F0338384|20110519|201503021425261790937|13980566277|18521084585");
		regData.setSignature(signatureStr);
		System.out.println(signatureStr);
		
		//http请求富友开户接口并获取返回数据
		//如返回：<?xml version="1.0" encoding="UTF-8"?><ap><plain><resp_code>返回码</resp_code><mchnt_cd>商户代码</mchnt_cd><mchnt_txn_ssn>商户流水号</mchnt_txn_ssn></plain><signature>签名数据</signature></ap>
		String backStr=WebUtils.sendHttp("https://jzh-test.fuiou.com/jzh/reg.action",regData);
	//	System.out.println(backStr);
		//验签
		//验签明文plain为 <plain><resp_code>返回码</resp_code><mchnt_cd>商户代码</mchnt_cd><mchnt_txn_ssn>商户流水号</mchnt_txn_ssn></plain>
		boolean b=SecurityUtils.verifySign("<plain><resp_code>5019</resp_code><mchnt_cd>0002900F0041077</mchnt_cd><mchnt_txn_ssn>96f14200a794dbcc91cad69b50ef05</mchnt_txn_ssn></plain>", 
				"auK8wfMTwG8ObQ2HC0J3KnSzgSGAplpYhIzU0pksT1Zzhb22hmVllM+dgjfXy5OriA6+0xzlr0ByfFcv6EOmcxduZ0Aa84Ouui9G1zYbkiGEv/AG+0VwqYYqZUVCEbEEnIvyyqtyKB4RyBZxg8HPGaBPRls6pTH8Lc5i5m1aaEA=");//验签结果
		
		System.out.println(b);
	}

}
