package com.jsjf.model.member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jsjf.common.Utils;

public class DrMember implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8379814537665266917L;

	/**
     * 主键、标识列自动增长 会员ID  
     */
    private Integer uid;

    /**
     * 用户密码（32 位双层MD5 salt 加密）
     */
    private String passWord;

    /**
     * 交易密码（初始化为登录密码）  
     */
    private String tpassWord;

    /**
     * 头像  
     */
    private String photo;

    /**
     * 是否为黑名单 0否1是
     */
    private Integer isBlack;

    /**
     * 注册IP  
     */
    private String regIp;

    /**
     * 注册日期  
     */
    private Date regDate;

    /**
     * 最后登录IP  
     */
    private String lastLoginIp;

    /**
     * 最后登录日期
     */
    private Date lastLoginTime;


    /**
     * 账户类型  
     */
    private Integer type;

    /**
     * 所属员工
     */
    private Integer userKy;

    /**
     * 账户状态（0 未激活1 正常2 冻结） 
     */
    private Integer status;

    /**
     * 注册来源  
     */
    private Integer regFrom;

    /**
     * 手机验证(0未激活1登录是不需要认证2需要验证3ip地址更换4三次密码不对)
     */
    private Integer mobileVerify;

    /**
     * 实名认证 1:已认证 0:未认证
     */
    private Integer realVerify;

    /**
     * 0 默认不验证,1登录手机验证,2更换ip手机验证,3密码错误3次手机验证--banby add 
     */
    private Integer loginVerify;

    /**
     * 邮箱验证0未认证1已认证 
     */
    private Integer emailVerify;

    /**
     * 推荐码-penkee
     */
    private String recommCodes;

    /**
     * 理财经济人级别
     */
    private Integer level;
    /**
     * 来源.0:本站 1:新手标DSP 2:
     */
    private String toFrom;
    private String tid;
    
    private String mobilephone;
    private String email;
    private Integer msgCount;//未读消息数
    
	private String realName;//真实姓名
	private String idCards;//身份证
	private Date birthdate;
	private BigDecimal balance;
	private BigDecimal freeze;
	private BigDecimal crushCount;
	private BigDecimal carryCount;
	private BigDecimal investAmount;
	private Integer tpwdSetting;//交易密码设置
	
	private Integer sex;
	
	
	
	//fuio
    private Integer isFuiou;//是否开通存管账户,0否1是
    private String user_id;//存管系统用户ID'
    private String fuiou_acnt;//存管系统虚拟账户号
    private String auth_st;//授权状态
    //fuio
    private String mchnt_txn_ssn;//授权状态
    
    private String androidUuid;
    
    /**
     * 1不是老用户  2是老用户 3是老用户登录后并且发送红包
     */
    private Integer is_Byp_Old_User;
    private BigDecimal user_integral;
	/**
	 * 2018/1/5 用户积分，上次签到时间，签到次数
	 * @return
	 */
	private BigDecimal userIntegralUse;

	private Date lastSignInTime;

	private Integer signInNumberDays;

	public BigDecimal getUserIntegralUse() {
		return userIntegralUse;
	}

	public void setUserIntegralUse(BigDecimal userIntegralUse) {
		this.userIntegralUse = userIntegralUse;
	}

	public Date getLastSignInTime() {
		return lastSignInTime;
	}

	public void setLastSignInTime(Date lastSignInTime) {
		this.lastSignInTime = lastSignInTime;
	}

	public Integer getSignInNumberDays() {
		return signInNumberDays;
	}

	public void setSignInNumberDays(Integer signInNumberDays) {
		this.signInNumberDays = signInNumberDays;
	}



	public BigDecimal getUser_integral() {
		return user_integral;
	}

	public void setUser_integral(BigDecimal user_integral) {
		this.user_integral = user_integral;
	}

	public Integer getIs_Byp_Old_User() {
		return is_Byp_Old_User;
	}

	public void setIs_Byp_Old_User(Integer is_Byp_Old_User) {
		this.is_Byp_Old_User = is_Byp_Old_User;
	}

	public DrMember(){};
    
	public Integer getIsFuiou() {
		return isFuiou;
	}

	public void setIsFuiou(Integer isFuiou) {
		this.isFuiou = isFuiou;
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

	public String getAuth_st() {
		return auth_st;
	}

	public void setAuth_st(String auth_st) {
		this.auth_st = auth_st;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getTpassWord() {
		return tpassWord;
	}

	public void setTpassWord(String tpassWord) {
		this.tpassWord = tpassWord;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserKy() {
		return userKy;
	}

	public void setUserKy(Integer userKy) {
		this.userKy = userKy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(Integer regFrom) {
		this.regFrom = regFrom;
	}

	public Integer getMobileVerify() {
		return mobileVerify;
	}

	public void setMobileVerify(Integer mobileVerify) {
		this.mobileVerify = mobileVerify;
	}

	public Integer getRealVerify() {
		return realVerify;
	}

	public void setRealVerify(Integer realVerify) {
		this.realVerify = realVerify;
	}

	public Integer getLoginVerify() {
		return loginVerify;
	}

	public void setLoginVerify(Integer loginVerify) {
		this.loginVerify = loginVerify;
	}

	public Integer getEmailVerify() {
		return emailVerify;
	}

	public void setEmailVerify(Integer emailVerify) {
		this.emailVerify = emailVerify;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getToFrom() {
		return toFrom;
	}

	public void setToFrom(String toFrom) {
		if(toFrom==null||toFrom.trim().length()==0){
			this.toFrom = "byp";
		}else{
			this.toFrom = toFrom;
		}
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCards() {
		return idCards;
	}

	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreeze() {
		return freeze;
	}

	public void setFreeze(BigDecimal freeze) {
		this.freeze = freeze;
	}

	public BigDecimal getCrushCount() {
		return crushCount;
	}

	public void setCrushCount(BigDecimal crushCount) {
		this.crushCount = crushCount;
	}

	public BigDecimal getCarryCount() {
		return carryCount;
	}

	public void setCarryCount(BigDecimal carryCount) {
		this.carryCount = carryCount;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getTpwdSetting() {
		return tpwdSetting;
	}

	public void setTpwdSetting(Integer tpwdSetting) {
		this.tpwdSetting = tpwdSetting;
	}

	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}

	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}

	public String getAndroidUuid() {
		return androidUuid;
	}

	public void setAndroidUuid(String androidUuid) {
		this.androidUuid = androidUuid;
	}

	
	
 
}