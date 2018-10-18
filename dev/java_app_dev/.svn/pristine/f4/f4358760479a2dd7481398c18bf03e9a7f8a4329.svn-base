package com.jsjf.model.system;

import java.util.Date;

import com.jzh.data.BaseReqdata;
import com.jzh.service.JZHService;

public class SysFuiouNoticeLog {
	
	private Integer id;
	
	private Integer type;//通知类型
	
	private Integer status;//通知状态状态，1-未处理，2-处理成功，3-处理失败
	
	private Date addtime;//添加时间
	
	private String icd; //业务代码
	private String mchnt_cd; //商户号
	private String mchnt_txn_ssn; //商户流水
	private String resp_code; //返回码
	private String resp_desc; //返回消息
	
	private String 	icd_name;//业务类型名称
	private String 	req_message;//请求报文
	private String 	user_id;//用户ID
	private String 	fuiou_acnt;//存管系统虚拟账户
	private String 	project_no;//项目编号
	private String 	amt;//金额
	private String 	message;//响应报文
	private Date 	update_time;//更新时间
	private Integer mf_id;//dr_member_favourable优惠券ID
	private Integer join_type;//投资来源
	private Integer invest_id;//投资id
	private String contract_no;//预授权合同号
	
	private Integer channel;//预授权合同号
	
	
	public Integer getInvest_id() {
		return invest_id;
	}
	public void setInvest_id(Integer invest_id) {
		this.invest_id = invest_id;
	}
	public SysFuiouNoticeLog() {
		super();
	}
	public SysFuiouNoticeLog(BaseReqdata data) {
		super();
		this.setReq_message(JZHService.encaJSONstring(data));
		this.setMchnt_cd(data.getMchnt_cd());
		this.setMchnt_txn_ssn(data.getMchnt_txn_ssn());
	}
	public SysFuiouNoticeLog(Integer id, Integer type, Integer status, Date addtime, String icd, String mchnt_cd,
			String mchnt_txn_ssn, String resp_code, String resp_desc, String message) {
		super();
		this.id = id;
		this.type = type;
		this.status = status;
		this.addtime = addtime;
		this.icd = icd;
		this.mchnt_cd = mchnt_cd;
		this.mchnt_txn_ssn = mchnt_txn_ssn;
		this.resp_code = resp_code;
		this.resp_desc = resp_desc;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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
	public String getIcd_name() {
		return icd_name;
	}
	public void setIcd_name(String icd_name) {
		this.icd_name = icd_name;
	}
	public String getReq_message() {
		return req_message;
	}
	public void setReq_message(String req_message) {
		this.req_message = req_message;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFuiou_acnt() {
		return fuiou_acnt;
	}
	public void setFuiou_acnt(String fuiou_acnt) {
		this.fuiou_acnt = fuiou_acnt;
	}
	public String getProject_no() {
		return project_no;
	}
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Integer getMf_id() {
		return mf_id;
	}
	public void setMf_id(Integer mf_id) {
		this.mf_id = mf_id;
	}
	public Integer getJoin_type() {
		return join_type;
	}
	public void setJoin_type(Integer join_type) {
		this.join_type = join_type;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
}
