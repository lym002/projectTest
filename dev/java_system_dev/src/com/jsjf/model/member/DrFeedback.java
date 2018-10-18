package com.jsjf.model.member;

import java.io.Serializable;
import java.util.Date;


public class DrFeedback  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8258260781870314633L;
	/**
     * 主键id  
     */
    private Integer id;
    /**
     * 意见提交人id
     */
    private Integer uid;
    /**
     * 联系方式
     */
    private String contactInformation;
    /**
     * 意见内容
     */
    private String content;
    /**
     * 意见提交时间
     */
    private Date feedbackTime;
    /**
     * 处理结果
     */
    private String handleResult;
    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 处理人id
     */
    private Integer handleId;
    /**
     * 状态（0未处理，1已处理）
     */
    private Integer status;
    
    private String statusName;
    
    private String realName;
    
    private String mobilePhone;
    
    private String recommCodes;
    
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public Integer getHandleId() {
		return handleId;
	}
	public void setHandleId(Integer handleId) {
		this.handleId = handleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRecommCodes() {
		return recommCodes;
	}
	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
    
    

}