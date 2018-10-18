package com.jsjf.model.claims;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;


public class DrClaimsLoan{

    private Integer id;//贷款项目基本信息

    private String name;//产品名称
    
    private String no;//借款合同编号
    
    private String companyNameProtocolShow;
    
    private Integer status;//债权状态
    
    private String loanUse;//贷款用途

    private BigDecimal loanAmount;//贷款金额
    
    private BigDecimal receiveInterest;//应收利息
    
    private BigDecimal serviceCharge;//服务费

    private String currency;//币种
    
    private String loanDeadline;//贷款期限(天)

    private BigDecimal rate;//年利率

    private Integer repayType;//还款方式
    
    private Integer repayDeadline;//还款周期
    
    private Date startDate;//放款日期
    
    private Date endDate;//还款日期
    
    private Integer dateType;//日期模式
    
    private String loanName;//贷款户名

    private String bankName;//开户行

    private String bankNo;//账号
    
    private BigDecimal advisoryRate;//管理咨询费率

    private BigDecimal serviceRate;//服务费率

    private BigDecimal defaultersRate;//违约金比例
    
    private BigDecimal overdueRate;//逾期利率
    
    private BigDecimal overduePenaltyRate;//逾期罚息比率
    
    private BigDecimal riskRate;//风险保证金比例
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
    private Integer updUser;//修改人

    private Integer cardFlag;//0-对私 1-对公
    
    private Integer isAuditEdit;//是否要审核修改:0:否,1:是
    
    //查询条件
    private String companyName;//公司名称
    private Date dueStartDate;//到期日期
    private Date dueEndDate;//到期日期
    private BigDecimal pendRepayAmount;//待还款金额
    private Integer productCounts;//对应产品数量
    private BigDecimal surplusAmount;//可用金额
    private String code;//标的编号
    
    
	private DrClaimsBill drClaimsBill;//商业承兑汇票信息
	private DrClaimsCustomer drClaimsCustomer;//企业客户基本信息
	private DrClaimsFinanc drClaimsFinanc;//融资性担保企业信息
	private DrClaimsGuarantee[] drClaimsGuarantee;//担保情况
	private DrClaimsShareholder[] drClaimsShareholder;//股东情况'
	private List<DrClaimsPic> drClaimsPic;//票据图片
	private List<DrClaimsProject> drClaimsProject;//债权产品
	private String bankAddress;//开户行地址
	
	private String statusName;//状态名称
	
	private String customerId;//企业基本信息id
	private String user_id; //恒丰借款人id
	private String fuiou_acnt;//恒丰虚拟账户
	private String mchnt_txn_ssn;//流水号
	
	public String getPayLoanNo() {
		return payLoanNo;
	}
	private String payLoanNo;//融资申请单编号（代收付系统债权唯一编号）
	private Integer claimsOriginate; //债权来源（1-币优铺理财，2-代收付）

	public void setPayLoanNo(String payLoanNo) {
		this.payLoanNo = payLoanNo;
	}

	public Integer getClaimsOriginate() {
		return claimsOriginate;
	}

	public void setClaimsOriginate(Integer claimsOriginate) {
		this.claimsOriginate = claimsOriginate;
	}

	public String getCompanyNameProtocolShow() {
		return companyNameProtocolShow;
	}

	public void setCompanyNameProtocolShow(String companyNameProtocolShow) {
		this.companyNameProtocolShow = companyNameProtocolShow;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	private Integer showStatus;

	public DrClaimsBill getDrClaimsBill() {
		return drClaimsBill;
	}

	public void setDrClaimsBill(DrClaimsBill drClaimsBill) {
		this.drClaimsBill = drClaimsBill;
	}

	public DrClaimsCustomer getDrClaimsCustomer() {
		return drClaimsCustomer;
	}

	public void setDrClaimsCustomer(DrClaimsCustomer drClaimsCustomer) {
		this.drClaimsCustomer = drClaimsCustomer;
	}

	public DrClaimsFinanc getDrClaimsFinanc() {
		return drClaimsFinanc;
	}

	public void setDrClaimsFinanc(DrClaimsFinanc drClaimsFinanc) {
		this.drClaimsFinanc = drClaimsFinanc;
	}

	public DrClaimsGuarantee[] getDrClaimsGuarantee() {
		return drClaimsGuarantee;
	}

	public void setDrClaimsGuarantee(DrClaimsGuarantee[] drClaimsGuarantee) {
		this.drClaimsGuarantee = drClaimsGuarantee;
	}

	public DrClaimsShareholder[] getDrClaimsShareholder() {
		return drClaimsShareholder;
	}

	public void setDrClaimsShareholder(DrClaimsShareholder[] drClaimsShareholder) {
		this.drClaimsShareholder = drClaimsShareholder;
	}

	public List<DrClaimsPic> getDrClaimsPic() {
		return drClaimsPic;
	}

	public void setDrClaimsPic(List<DrClaimsPic> drClaimsPic) {
		this.drClaimsPic = drClaimsPic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public BigDecimal getReceiveInterest() {
		return receiveInterest;
	}

	public void setReceiveInterest(BigDecimal receiveInterest) {
		this.receiveInterest = receiveInterest;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Integer getRepayDeadline() {
		return repayDeadline;
	}

	public void setRepayDeadline(Integer repayDeadline) {
		this.repayDeadline = repayDeadline;
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

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public BigDecimal getAdvisoryRate() {
		return advisoryRate;
	}

	public void setAdvisoryRate(BigDecimal advisoryRate) {
		this.advisoryRate = advisoryRate;
	}

	public BigDecimal getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}

	public BigDecimal getDefaultersRate() {
		return defaultersRate;
	}

	public void setDefaultersRate(BigDecimal defaultersRate) {
		this.defaultersRate = defaultersRate;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public BigDecimal getOverduePenaltyRate() {
		return overduePenaltyRate;
	}

	public void setOverduePenaltyRate(BigDecimal overduePenaltyRate) {
		this.overduePenaltyRate = overduePenaltyRate;
	}

	public BigDecimal getRiskRate() {
		return riskRate;
	}

	public void setRiskRate(BigDecimal riskRate) {
		this.riskRate = riskRate;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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
	
	public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("claimsStatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDueStartDate() {
		return dueStartDate;
	}

	public void setDueStartDate(Date dueStartDate) {
		this.dueStartDate = dueStartDate;
	}

	public Date getDueEndDate() {
		return dueEndDate;
	}

	public void setDueEndDate(Date dueEndDate) {
		this.dueEndDate = dueEndDate;
	}

	public BigDecimal getPendRepayAmount() {
		return pendRepayAmount;
	}

	public void setPendRepayAmount(BigDecimal pendRepayAmount) {
		this.pendRepayAmount = pendRepayAmount;
	}

	public Integer getProductCounts() {
		return productCounts;
	}

	public void setProductCounts(Integer productCounts) {
		this.productCounts = productCounts;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<DrClaimsProject> getDrClaimsProject() {
		return drClaimsProject;
	}

	public void setDrClaimsProject(List<DrClaimsProject> drClaimsProject) {
		this.drClaimsProject = drClaimsProject;
	}

	public Integer getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(Integer cardFlag) {
		this.cardFlag = cardFlag;
	}

	public Integer getIsAuditEdit() {
		return isAuditEdit;
	}

	public void setIsAuditEdit(Integer isAuditEdit) {
		this.isAuditEdit = isAuditEdit;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}

	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}
	
}