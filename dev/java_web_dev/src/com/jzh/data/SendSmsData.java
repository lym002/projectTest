package com.jzh.data;

/**
 * 短信验证
 * @author DELL
 *
 */
public class SendSmsData extends BaseReqdata{
	
	private String login_id ="";//M交易用户
	private String amt ="";//M交易金额
	private String bank_mobile ="";//O银行预留手机号
	private String card_no ="";//O银行卡号
	
	
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
	public String getBank_mobile() {
		return bank_mobile;
	}
	public void setBank_mobile(String bank_mobile) {
		this.bank_mobile = bank_mobile;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
}
