package com.jsjf.model.subject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrSubjectInfo{

    private Integer id;//标的信息ID

    private String code;//标的编号
    
    private Integer lid;//债权ID
    
    private Integer status;//标的状态
    
    private Integer type;//标的类型

    private BigDecimal amount;//标的金额
    
    private BigDecimal surplusAmount;//可用金额

    private Date startDate;//起始日期
    
    private Date endDate;//到期日期
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
    private Integer updUser;//修改人
    
	private String statusName;//状态名称
	
	private Integer ispool;//是否标的池
	
    private String loanUse;//贷款用途

    private BigDecimal loanAmount;//贷款金额
    
    private String loanDeadline;//贷款期限(天)

    private BigDecimal rate;//年利率
    
    private Integer repayType;//还款方式
    
    private Integer dateType;//日期模式
    
    private BigDecimal remainsAmount;//剩余未匹配债权金额
    
    private BigDecimal mappingAmount;//已匹配金额
    
    private Integer mappingStatus;//匹配状态 0：未匹配 1：部分匹配  2：完全匹配
    
    private Integer  mappingCount;
    
    private String pCode;//产品编号
    
	//查询条件
	private String no;//债权编号
	private String name;//债权名称
	private String simpleName;//产品名称
	private String companyName;//公司名字
    private String companyNameProtocolShow;

	public String getCompanyNameProtocolShow() {
		return companyNameProtocolShow;
	}

	public void setCompanyNameProtocolShow(String companyNameProtocolShow) {
		this.companyNameProtocolShow = companyNameProtocolShow;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("subjectStatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Integer getUpdUser() {
		return updUser;
	}

	public void setUpdUser(Integer updUser) {
		this.updUser = updUser;
	}

	public String getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanDeadline() {
		return loanDeadline;
	}

	public void setLoanDeadline(String loanDeadline) {
		this.loanDeadline = loanDeadline;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public BigDecimal getRemainsAmount() {
		return remainsAmount;
	}

	public void setRemainsAmount(BigDecimal remainsAmount) {
		this.remainsAmount = remainsAmount;
	}

	/**
	 * @return the mappingStatus
	 */
	public Integer getMappingStatus() {
		return mappingStatus;
	}

	/**
	 * @param mappingStatus the mappingStatus to set
	 */
	public void setMappingStatus(Integer mappingStatus) {
		this.mappingStatus = mappingStatus;
	}

	/**
	 * @return the pCode
	 */
	public String getpCode() {
		return pCode;
	}

	/**
	 * @param pCode the pCode to set
	 */
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	/**
	 * @return the ispool
	 */
	public Integer getIspool() {
		return ispool;
	}

	/**
	 * @param ispool the ispool to set
	 */
	public void setIspool(Integer ispool) {
		this.ispool = ispool;
	}

	/**
	 * @return the mappingAmount
	 */
	public BigDecimal getMappingAmount() {
		return mappingAmount;
	}

	/**
	 * @param mappingAmount the mappingAmount to set
	 */
	public void setMappingAmount(BigDecimal mappingAmount) {
		this.mappingAmount = mappingAmount;
	}


	/**
	 * @return the mappingCount
	 */
	public Integer getMappingCount() {
		return mappingCount;
	}

	/**
	 * @param mappingCount the mappingCount to set
	 */
	public void setMappingCount(Integer mappingCount) {
		this.mappingCount = mappingCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}