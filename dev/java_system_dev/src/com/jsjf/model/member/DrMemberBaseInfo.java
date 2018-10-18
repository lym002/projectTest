package com.jsjf.model.member;

import java.text.ParseException;
import java.util.Date;

import com.jsjf.common.Utils;

public class DrMemberBaseInfo {
    /**
     * 主键id  
     */
    private Integer id;

    /**
     * 会员ID  
     */
    private Integer uid;

    /**
     * 真实姓名  
     */
    private String realName;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别，值为：1男，2女，0保密
     */
    private Integer sex;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 出生日期  
     */
    private Date birthDate;

    /**
     * 邮箱地址  
     */
    private String email;

    /**
     * 身份证号码 
     */
    private String idCards;

    /**
     * 身份证详细住址  
     */
    private String idAddress;

    /**
     * 居住地详细地址 
     */
    private String nowAddress;

    /**
     * 婚姻状况  
     */
    private Integer marry;

    /**
     * 行业类别  
     */
    private Short industryType;

    /**
     * 职位  
     */
    private String job;

    /**
     * 添加时间  
     */
    private Date addTime;

    /**
     * 修改时间  
     */
    private Date updTime;
    private Integer salary;//月收入
    private Integer companySize;//公司规模
    /**
     * 学历  
     */
    private String degree;

    /**
     * 毕业学校  
     */
    private String gschool;
    
    public DrMemberBaseInfo(){}
    
	public DrMemberBaseInfo(Integer uid, String realName, Integer sex,
			Date birthDate, String email, String idCards,
			String idAddress, String nowAddress, Integer marry, String degree) {
		super();
		this.uid = uid;
		this.realName = realName;
		this.sex = sex;
		this.birthDate = birthDate;
		this.email = email;
		this.idCards = idCards;
		this.idAddress = idAddress;
		this.nowAddress = nowAddress;
		this.marry = marry;
		this.degree = degree;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCards() {
		return idCards;
	}

	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public Integer getMarry() {
		return marry;
	}

	public void setMarry(Integer marry) {
		this.marry = marry;
	}

	public Short getIndustryType() {
		return industryType;
	}

	public void setIndustryType(Short industryType) {
		this.industryType = industryType;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getCompanySize() {
		return companySize;
	}

	public void setCompanySize(Integer companySize) {
		this.companySize = companySize;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGschool() {
		return gschool;
	}

	public void setGschool(String gschool) {
		this.gschool = gschool;
	}

	public Integer getAge() {
		if(this.age == null){
			try {
				this.age = Utils.getQuotAge(new Date(), this.getBirthDate() == null ? new Date():this.getBirthDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return age;
	}

	@Override
	public String toString() {
		return "DrMemberBaseInfo [id=" + id + ", uid=" + uid + ", realName="
				+ realName + ", userName=" + userName + ", sex=" + sex
				+ ", age=" + age + ", birthDate=" + birthDate
				+ ", email=" + email
				+ ", idCards=" + idCards + ", idAddress=" + idAddress
				+ ", nowAddress=" + nowAddress + ", marry=" + marry
				+ ", industryType=" + industryType + ", job=" + job
				+ ", addTime=" + addTime + ", updTime=" + updTime + ", salary="
				+ salary + ", companySize=" + companySize + ", degree="
				+ degree + ", gschool=" + gschool + "]";
	}
	
}