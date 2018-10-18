package com.jsjf.model.system;

import java.util.Date;

public class SysUsersVo implements java.io.Serializable {
	private static final long serialVersionUID = 315131335290121714L;

	private Long userKy;

	private String loginId;

	private String password;

	private Date registertime;

	private String lastLoginIp;

	private Short failLoginNum;

	private Short status;

	private String name;

	private String deptId;

	private String gongsiId;

	private String mobile;

	private String phone;

	private String email;

	private String address;

	private String qq;

	private Long jobId;

	private Long csid;

	private String recommCodes;// 会员专属ID(推荐�?

	private Integer manageFlag;// �?否是管理�?0 �?1 �?

	private Long manageParents;// 上级管理�?

	private Integer roleKy;// 用户角色ID
	
	private String roleName;//用户角色名称

	private String realName;// 真实姓名
	private String loginName;
	private Integer level;// 理财代理的级�?
	private String roleCode;//用户角色编码

	public Long getUserKy() {
		return userKy;
	}

	public void setUserKy(Long userKy) {
		this.userKy = userKy;
	}

	public String getGongsiId() {
		return gongsiId;
	}

	public void setGongsiId(String gongsiId) {
		this.gongsiId = gongsiId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Short getFailLoginNum() {
		return failLoginNum;
	}

	public void setFailLoginNum(Short failLoginNum) {
		this.failLoginNum = failLoginNum;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Long getCsid() {
		return csid;
	}

	public void setCsid(Long csid) {
		this.csid = csid;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}

	public Long getManageParents() {
		return manageParents;
	}

	public void setManageParents(Long manageParents) {
		this.manageParents = manageParents;
	}

	public Integer getManageFlag() {
		return manageFlag;
	}

	public void setManageFlag(Integer manageFlag) {
		this.manageFlag = manageFlag;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public Integer getRoleKy() {
		return roleKy;
	}

	public void setRoleKy(Integer roleKy) {
		this.roleKy = roleKy;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}