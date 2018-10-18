package com.jsjf.model.claims;


public class DrClaimsCustomer{

    private Integer id;//企业客户基本信息主键ID  

    private Integer lid;//贷款项目基本信息ID
    
    private String companyName;//企业名称

    private String name;//法人姓名

    private Integer sex;//性别

    private String phone;//手机号码
    
    private Integer certificateType;//证件类型

    private String certificateNo;//证件号码

    private String mechanismNo;//组织机构代码证号码
    
    private String industryType;//行业类别
    
    private String businessNo;//营业执照号码
    
    private String companyPhone;//企业联系号码
    
    private String companyMail;//企业邮箱
    
    private String address;//通讯地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getMechanismNo() {
		return mechanismNo;
	}

	public void setMechanismNo(String mechanismNo) {
		this.mechanismNo = mechanismNo;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyMail() {
		return companyMail;
	}

	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}