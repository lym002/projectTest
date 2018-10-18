package com.jsjf.model.system;

import java.util.Date;

public class SysMessageLog {
    /**
     * 主键id 自增  
     */
    private Integer msgId;

    /**
     * 会员ID  
     */
    private Integer uid;

    /**
     * 短信验证码
     */
    private String message;

    /**
     * 短信类型:0修改手机号验证, 1忘记密码,找回登录密码, 2找回交易密码, 3线上充值, 4后台充值, 
     *          5注册, 6提现, 7提现拒绝, 8登录时需手机验证 ，9还款 ，10添加银行卡,11 签约 ，12 还款提醒，13 逾期提醒
     */
    private Integer type;

    /**
     * 短信发送时间
     */
    private Date sendTime;

    /**
     * 发送结果  对应短信接口文档（大于零发送成功）
     */
    private Integer results;

    /**
     * 手机号码  
     */
    private String phone;
    
    public SysMessageLog(){}
    
    

	public SysMessageLog(Integer uid, String message, Integer type,
			Date sendTime, String phone) {
		super();
		this.uid = uid;
		this.message = message;
		this.type = type;
		this.sendTime = sendTime;
		this.phone = phone;
	}



	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}