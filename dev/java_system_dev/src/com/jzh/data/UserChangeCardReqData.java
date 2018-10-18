package com.jzh.data;

import java.io.File;


public class UserChangeCardReqData extends BaseReqdata{

	private String login_id;
	private String city_id;
	private String bank_cd;
	private String card_no;
	private File file1;
	private File file2;
	private String mchnt_cd;
	private String mchnt_txn_ssn;
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getBank_cd() {
		return bank_cd;
	}
	public void setBank_cd(String bank_cd) {
		this.bank_cd = bank_cd;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public File getFile1() {
		return file1;
	}
	public void setFile1(File file1) {
		this.file1 = file1;
	}
	public File getFile2() {
		return file2;
	}
	public void setFile2(File file2) {
		this.file2 = file2;
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
	/**
	 * 注册时请求的明文
	 * @return
	 */
	public String regSignVal(){
		String src = bank_cd+"|"+card_no+"|"
		+city_id+"|"+login_id+"|"+mchnt_cd+"|"+mchnt_txn_ssn;
		return src;
	}
}
