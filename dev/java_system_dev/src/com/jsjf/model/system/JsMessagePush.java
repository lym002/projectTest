package com.jsjf.model.system;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

import com.jsjf.common.Utils;

public class JsMessagePush {
	private Integer id;//
	private String title;//标题
	private String content;//消息内容
	private Integer status;//状态0未发送1已发送
	private Integer type;//类型0立即,1定时,2定期
	private Integer city;//城市
	private Integer isInvest;//投资情况(0=false,1=true)
	private Integer weal;//账户福利(0=没有,1=有)
	private Integer investMax;//投资峰值Max
	private Integer investMin;//投资峰值Min
	private Integer balanceMax;//账户余额max
	private Integer balanceMin;//账户余额min
	private Integer payment;//回款情况,近N天内有回款
	private Integer liveness;//活跃情况:近n天app登录,附加条件loginAppType
	private Integer livenessType;//活跃类型:0未登录过app,1登录过app
	private java.sql.Date sendStartDate;//发送区间:开始日期
	private java.sql.Date sendEndDate;//发送区间:结束日期
	private Time sendTime;//发送区间:结束日期
	private Date addTime;//发送时间
	private Date upDateTime;//添加时间
	private Integer addUser;//修改时间
	private Integer updateUser;//修改人
	private Integer ischeck;//
	private Integer audienceId;//
	private String scheduleId;//
	private String url;//推送链接
	private Integer platform;//推送平台 1ios,2android
	
	
	private Integer channel;//0极光1友盟
	
	//
	private String startDate;
	private String endDate;
	
	/**
	 * 
	 * @return
	 */
	public int isTimeBoolean (){
		Date now = new Date();
		if(this.type ==1){
			try {
				Date pushDate = Utils.parse(Utils.format(this.sendStartDate, "yyyy-MM-dd")+" "+Utils.format(this.sendTime, "HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
				if(pushDate.getTime()<now.getTime()+10*1000*60){//发送时间要大于当前时间+10分钟
					return 1;
				}	
			} catch (ParseException e) {
				e.printStackTrace();
				return -1;
			}
		}
		if(this.type ==2 && this.channel==0){
			try {				
				long ls = Utils.parse(now, "yyyy-MM-dd").getTime();
				long le = Utils.parse(now, "yyyy-MM-dd").getTime();
				if(this.sendStartDate.getTime()<ls || this.sendEndDate.getTime() < le || this.sendStartDate.getTime() > this.sendEndDate.getTime()){
					return 2;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * 判断是否按条件推送
	 * @return
	 */
	public boolean isAudience(){
		
		if(this.isInvest != null || this.weal !=null || this.balanceMax !=null || this.balanceMin != null ||
				this.payment != null || this.liveness != null || (this.investMax != null || this.investMin != null)){
			return true;
		}
		
		return false;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getIsInvest() {
		return isInvest;
	}
	public void setIsInvest(Integer isInvest) {
		this.isInvest = isInvest;
	}
	public Integer getWeal() {
		return weal;
	}
	public void setWeal(Integer weal) {
		this.weal = weal;
	}
	public Integer getInvestMax() {
		return investMax;
	}
	public void setInvestMax(Integer investMax) {
		this.investMax = investMax;
	}
	public Integer getInvestMin() {
		return investMin;
	}
	public void setInvestMin(Integer investMin) {
		this.investMin = investMin;
	}
	public Integer getBalanceMax() {
		return balanceMax;
	}
	public void setBalanceMax(Integer balanceMax) {
		this.balanceMax = balanceMax;
	}
	public Integer getBalanceMin() {
		return balanceMin;
	}
	public void setBalanceMin(Integer balanceMin) {
		this.balanceMin = balanceMin;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	
	public Integer getLiveness() {
		return liveness;
	}
	public void setLiveness(Integer liveness) {
		this.liveness = liveness;
	}
	public Integer getLivenessType() {
		return livenessType;
	}
	public void setLivenessType(Integer livenessType) {
		this.livenessType = livenessType;
	}
	public java.sql.Date getSendStartDate() {
		return sendStartDate;
	}
	public void setSendStartDate(java.sql.Date sendStartDate) {
		this.sendStartDate = sendStartDate;
	}
	public java.sql.Date getSendEndDate() {
		return sendEndDate;
	}
	public void setSendEndDate(java.sql.Date sendEndDate) {
		this.sendEndDate = sendEndDate;
	}
	public Time getSendTime() {
		return sendTime;
	}
	public void setSendTime(Time sendTime) {
		this.sendTime = sendTime;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpDateTime() {
		return upDateTime;
	}
	public void setUpDateTime(Date upDateTime) {
		this.upDateTime = upDateTime;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public Integer getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIscheck() {
		return ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public Integer getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Integer audienceId) {
		this.audienceId = audienceId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	
}
