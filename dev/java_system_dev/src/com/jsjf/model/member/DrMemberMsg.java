package com.jsjf.model.member;

import java.util.Date;

public class DrMemberMsg {
    /**
     * 主键  
     */
    private Integer id;

    /**
     * 接收用户ID  
     */
    private Integer ruId;

    /**
     * 发送人ID，0默认为系统发送 
     */
    private Integer puId;

    /**
     * 类型，1满标, 2还款, 3收款 ，7提现 8充值 9申请成为理财经纪人, 10注册成功 11注册返利 12推荐返利  
     */
    private Integer type;

    /**
     * 消息标题  
     */
    private String title;

    /**
     * 发送时间
     */
    private Date addTime;

    /**
     * 是否阅读0未读，1已读
     */
    private Integer isRead;

    /**
     * 状态，0正常，1删除
     */
    private Integer status;

    /**
     * 消息内容  
     */
    private String content;
    
    public DrMemberMsg(){};
	public DrMemberMsg(Integer ruId, Integer puId, Integer type, String title,
			Date addTime, Integer isRead, Integer status, String content) {
		super();
		this.ruId = ruId;
		this.puId = puId;
		this.type = type;
		this.title = title;
		this.addTime = addTime;
		this.isRead = isRead;
		this.status = status;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuId() {
		return ruId;
	}

	public void setRuId(Integer ruId) {
		this.ruId = ruId;
	}

	public Integer getPuId() {
		return puId;
	}

	public void setPuId(Integer puId) {
		this.puId = puId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    
}