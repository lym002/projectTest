package com.jsjf.model.system;

import java.util.Date;

public class SysFuiouMessageLog {
	private Integer id;
	private String type;//报文类型，P2P_PW10：个人开户，P2P_PW11：法人开户，P2P_PWJY：交易明细，P2P_PWXM：项目信息
	private String checkFileName;//上传报文文件名
	private Date uploadTime;//上传时间
	private String overcheckFileName;//返回报文文件名
	private Date downloadTime;//返回报文下载时间
	private Integer status;//状态，1-报文已上传，2-返回报文成功，3-返回报文中有失败数据
	private String remark;//备注
	private String businessType;//业务类型： 0.投标 1.满标放款 2.转让 3.回款 4.其他 5.流标 6平台手续费 7.风险保证金
	private Integer reportCount;// 报备的条数 
	
	public Integer getReportCount() {
		return reportCount;
	}
	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCheckFileName() {
		return checkFileName;
	}
	public void setCheckFileName(String checkFileName) {
		this.checkFileName = checkFileName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getOvercheckFileName() {
		return overcheckFileName;
	}
	public void setOvercheckFileName(String overcheckFileName) {
		this.overcheckFileName = overcheckFileName;
	}
	public Date getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	@Override
	public String toString() {
		return "SysFuiouMessageLog [id=" + id + ", type=" + type + ", checkFileName=" + checkFileName + ", uploadTime="
				+ uploadTime + ", overcheckFileName=" + overcheckFileName + ", downloadTime=" + downloadTime
				+ ", status=" + status + ", remark=" + remark + ", businessType=" + businessType + ", reportCount="
				+ reportCount + "]";
	}

	
}
