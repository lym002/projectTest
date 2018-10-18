package com.jsjf.model.member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    /**
     * 来源.0:本站 1:新手标DSP 2:
     */
    private String mchnt_txn_ssn;
    
    
    //fuio
    private Integer isFuiou;//是否开通存管账户,0否1是
    private String user_id;//存管系统用户ID'
    private String fuiou_acnt;//存管系统虚拟账户号
    private String auth_st;//授权状态
    //fuio
    
    private String mobilephone;
    private String email;
    private Integer msgCount;//未读消息数
    
	private String realName;//真实姓名
	private String idCards;//身份证
	private Date birthDate;
	private BigDecimal balance;
	private BigDecimal freeze;
	private BigDecimal crushCount;
	private BigDecimal carryCount;
	private BigDecimal investAmount;
	/**
	 * 易瑞特追加参数，作为易瑞特用户唯一标识,值由易瑞特传递,每个用户注册tid值都不同,一般为32位字符串。
	 */
	private String tid;
	
	private Integer sex;
	/**
	 * 1不是老用户  2是老用户 3是老用户登录后并且发送红包
	 */
	private Integer is_byp_old_user;
	
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

	public Integer getIs_byp_old_user() {
		return is_byp_old_user;
	}

	public void setIs_byp_old_user(Integer is_byp_old_user) {
		this.is_byp_old_user = is_byp_old_user;
	}

	public DrMember(){};
    
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

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

	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}

	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}

	@Override
	public String toString() {
		return "DrMember [uid=" + uid + ", passWord=" + passWord
				+ ", tpassWord=" + tpassWord + ", photo=" + photo
				+ ", isBlack=" + isBlack + ", regIp=" + regIp + ", regDate="
				+ regDate + ", lastLoginIp=" + lastLoginIp + ", lastLoginTime="
				+ lastLoginTime + ", type=" + type + ", userKy=" + userKy
				+ ", status=" + status + ", regFrom=" + regFrom
				+ ", mobileVerify=" + mobileVerify + ", realVerify="
				+ realVerify + ", loginVerify=" + loginVerify
				+ ", emailVerify=" + emailVerify + ", recommCodes="
				+ recommCodes + ", level=" + level + ", toFrom=" + toFrom
				+ ", mchnt_txn_ssn=" + mchnt_txn_ssn + ", isFuiou=" + isFuiou
				+ ", user_id=" + user_id + ", fuiou_acnt=" + fuiou_acnt
				+ ", auth_st=" + auth_st + ", mobilephone=" + mobilephone
				+ ", email=" + email + ", msgCount=" + msgCount + ", realName="
				+ realName + ", idCards=" + idCards + ", birthDate="
				+ birthDate + ", balance=" + balance + ", freeze=" + freeze
				+ ", crushCount=" + crushCount + ", carryCount=" + carryCount
				+ ", investAmount=" + investAmount + ", tid=" + tid + ", sex="
				+ sex + ", is_byp_old_user=" + is_byp_old_user + "]";
	}
 
}