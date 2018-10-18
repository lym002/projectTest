package com.jzh.data;

/**
 * 响应基础数据对象
 * @Desc BaseRspdata
 * @Author xuelin.Wang
 * @Version 1.0.1
 * @Date 2016年3月30日
 */
public class BaseRspdata2 {
	private String ver; //接口版本号
	private String icd; //业务代码
	private String mchnt_cd; //商户号
	private String mchnt_txn_ssn; //商户流水
	private String resp_code; //返回码
	private String resp_desc; //返回消息
	
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getIcd() {
		return icd;
	}

	public void setIcd(String icd) {
		this.icd = icd;
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

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_desc() {
		return resp_desc;
	}

	public void setResp_desc(String resp_desc) {
		this.resp_desc = resp_desc;
	}
}