package com.jytpay.vo;

import java.io.Serializable;
/**
 * 四要素验证实体类
 * @author Administrator
 *
 */
public class JYTFourElementsSendDate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8460811386699862602L;
    
	private String bank_card_no;//银行卡号
	private String bank_code;//银行编码
	private String id_num;//身份证号
	private String id_name;//姓名
	private String terminal_type;//请求终端类型商户使用验证接口的终端类型：01 APP，02 WAP，03 WEB，04 SIM卡，05 VI-POS，06 SD卡
	private String bank_card_type;//银行卡类型
	private String phone_no;//手机号码
	private String tran_code;//类型TR4003
	private String tran_flowid;//唯一标识符
	public String getBank_card_no() {
		return bank_card_no;
	}
	public void setBank_card_no(String bank_card_no) {
		this.bank_card_no = bank_card_no;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	public String getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getBank_card_type() {
		return bank_card_type;
	}
	public void setBank_card_type(String bank_card_type) {
		this.bank_card_type = bank_card_type;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getTran_code() {
		return tran_code;
	}
	public void setTran_code(String tran_code) {
		this.tran_code = tran_code;
	}
	public String getTran_flowid() {
		return tran_flowid;
	}
	public void setTran_flowid(String tran_flowid) {
		this.tran_flowid = tran_flowid;
	}
	
	
}
