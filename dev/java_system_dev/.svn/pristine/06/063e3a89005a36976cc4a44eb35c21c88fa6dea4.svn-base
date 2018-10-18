package com.jsjf.model.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.claims.DrClaimsPic;

public class DrProductInfo{

    private Integer id;//产品ID
    
    private Integer fid;//父ID
    
    private Integer sid;//标的ID

    private String code;//产品编号
    
    private String fullName;//产品全称
    
    private String simpleName;//产品简称
    
    private Integer type;//产品类型

    private Integer status;//产品状态
    
    private Integer intermediary;//居间人
    
    private Integer isSid;//是否需要关联标的
    
    private BigDecimal amount;//产品金额

    private BigDecimal alreadyRaiseAmount;//已募集金额
    
    private BigDecimal surplusAmount;//剩余金额

    private BigDecimal rate;//年利率
    
    private BigDecimal activityRate;//活动年利率  

    private Integer repayType;//还款方式
    
    private Integer deadline;//产品期限
    
    private BigDecimal leastaAmount;//起投金额
    
    private BigDecimal increasAmount;//递增金额
    
    private BigDecimal maxAmount;//最大金额
    
    private Integer raiseDeadline;//募集期限

    private Date startDate;//上架日期
    
    private Date fullDate;//满标日期
    
    private Date expireDate;//产品到期日期

    private String introduce;//产品介绍
    
    private String borrower;//借款人
    
    private String repaySource;//还款来源
    
    private String windMeasure;//风控措施
    
    private Integer isShow;//是否显示
    
    private String isShowName;//是否显示名称

    private Integer isHot;//是否热推

    private Integer isDeductible;//可否抵扣
    
    private Integer isInterest;//可否加息
    
    private Integer isCash;//可否返现
    
    private Integer isDouble;//可否加倍
    
    private Date establish;//成立日期
    
    private String accept;//承兑机构
    
    private String acceptPic;//承兑图片
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
	private String repayTypeName;//还款方式
	
	private BigDecimal shouldInterest;//平台贴息（除去基本利息，其他利息总和）
    
    public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFuiouMessageNo() {
		return fuiouMessageNo;
	}

	public void setFuiouMessageNo(String fuiouMessageNo) {
		this.fuiouMessageNo = fuiouMessageNo;
	}

	private Integer updUser;//修改人
    
    private Integer fileStatus;
    
    private String  fuiouMessageNo;
	private String failureCause;//失败原因
    
	private String statusName;//状态名称
	private String experienceName;//体验标状态名称
    private String typeName;//产品类型名称
    private Integer surplusDay;//剩余到期天数
    private Integer renewal;//判断是否续发
    private Integer bespoke;//取消预约
    
    private Integer mappingStatus;//匹配状态 0 未匹配  1：部分匹配 2：完全匹配
    private BigDecimal remainsAmount;//剩余未匹配金额
    private Date mappingEndDate;
    private BigDecimal mappingAmount;//已匹配金额
    
    private Date raiseSuccessDate;//募集成功日期
    private Integer mappingCount;//匹配标的数量
    
    private Date searchStartDate;
    private Date searchEndDate;
    
    private String sName;//标的名称
    private String parentName;//续发产品名称
    
    
    private String project_no;//存管系统项目编号
    private String project_st;//存管项目状态
    private String project_usage;//项目类型，默认00-正常项目
    
	
	private List<DrClaimsPic> drClaimsPic;
	
	private List<DrProductPic> drProductPic;
	
	private List<DrProductExtend> drProductExtend;
	private Integer loanStatus;//放款状态
	private String loanName;//借款人
	private String loanCode;//合同编号
	private Integer atid;

	private Date refundStartDate;//回款开始日期
	private Date refundEndDate;//回款结束日期
	
	private BigDecimal interest;//产品总利息

	private Date fullStartDate;//募集开始日期
	private Date fullEndDate;//募集成功结束日期
	
	private Integer remainRaiseDay;//剩余募集天数
	
	private Date actLoanTime;
	
	private Date startactLoanTime;//实际放款开始日期
	private Date endactLoanTime;//实际放款结束时间
	private BigDecimal loanAmount;//放款金额
	private String bankNO;//放款账户
	private String bankName;//放款银行
	private Integer prizeId;//奖品id
	private String prizeName;//奖品名称
	
	private String mchntTxnSsn;//流水号
	private int interestSuccessTotal = 0;//计息成功笔数
	private int interestFailTotal = 0;//计息成功笔数
	private int interestStatus = 1;//计息状态，1-未计息，2-计息成功，3-计息失败
	
	private int repaySuccessTotal = 0;//回款成功笔数
	private int repayFailTotal = 0;//回款成功笔数
	private int repayStatus = 1;//回款状态，1-未计息，2-计息成功，3-计息失败
	private Integer thaw;
	private String phone;
	private String tableName = "dr_product_info";//表名
	private String itemNo;//存管项目编号
	private String loginId;//登录名
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	public Integer getThaw() {
		return thaw;
	}

	public void setThaw(Integer thaw) {
		this.thaw = thaw;
	}
	
	private Integer principleId; //原理图id
	private String principleH5; //H5原理图
	private String principlePC; //PC原理图
	private Integer isSyn;//是否同步
	
	
	
	public Integer getIsSyn() {
		return isSyn;
	}

	public void setIsSyn(Integer isSyn) {
		this.isSyn = isSyn;
	}

	public Integer getPrincipleId() {
		return principleId;
	}

	public void setPrincipleId(Integer principleId) {
		this.principleId = principleId;
	}

	public String getPrincipleH5() {
		return principleH5;
	}

	public void setPrincipleH5(String principleH5) {
		this.principleH5 = principleH5;
	}

	public String getPrinciplePC() {
		return principlePC;
	}

	public void setPrinciplePC(String principlePC) {
		this.principlePC = principlePC;
	}

	public String getExperienceName() {
		return experienceName;
	}

	public void setExperienceName(String experienceName) {
		this.experienceName = experienceName;
	}

	public Date getFullEndDate() {
		return fullEndDate;
	}

	public void setFullEndDate(Date fullEndDate) {
		this.fullEndDate = fullEndDate;
	}

	public Date getFullStartDate() {
		return fullStartDate;
	}

	public void setFullStartDate(Date fullStartDate) {
		this.fullStartDate = fullStartDate;
	}

	private String companyName;//收款单位全称
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getHeji() {
		return heji;
	}

	public void setHeji(BigDecimal heji) {
		this.heji = heji;
	}

	private BigDecimal heji;//本息合计
	
	public Date getRefundStartDate() {
		return refundStartDate;
	}

	public void setRefundStartDate(Date refundStartDate) {
		this.refundStartDate = refundStartDate;
	}

	public Date getRefundEndDate() {
		return refundEndDate;
	}

	public void setRefundEndDate(Date refundEndDate) {
		this.refundEndDate = refundEndDate;
	}

	public Integer getAtid() {
		return atid;
	}

	public void setAtid(Integer atid) {
		this.atid = atid;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public List<DrClaimsPic> getDrClaimsPic() {
		return drClaimsPic;
	}

	public void setDrClaimsPic(List<DrClaimsPic> drClaimsPic) {
		this.drClaimsPic = drClaimsPic;
	}

	public List<DrProductPic> getDrProductPic() {
		return drProductPic;
	}

	public void setDrProductPic(List<DrProductPic> drProductPic) {
		this.drProductPic = drProductPic;
	}
	
	public List<DrProductExtend> getDrProductExtend() {
		return drProductExtend;
	}

	public void setDrProductExtend(List<DrProductExtend> drProductExtend) {
		this.drProductExtend = drProductExtend;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getIntermediary() {
		return intermediary;
	}

	public void setIntermediary(Integer intermediary) {
		this.intermediary = intermediary;
	}

	public Integer getIsSid() {
		return isSid;
	}

	public void setIsSid(Integer isSid) {
		this.isSid = isSid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAlreadyRaiseAmount() {
		return alreadyRaiseAmount;
	}

	public void setAlreadyRaiseAmount(BigDecimal alreadyRaiseAmount) {
		this.alreadyRaiseAmount = alreadyRaiseAmount;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getActivityRate() {
		return activityRate;
	}

	public void setActivityRate(BigDecimal activityRate) {
		this.activityRate = activityRate;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public BigDecimal getLeastaAmount() {
		return leastaAmount;
	}

	public void setLeastaAmount(BigDecimal leastaAmount) {
		this.leastaAmount = leastaAmount;
	}

	public BigDecimal getIncreasAmount() {
		return increasAmount;
	}

	public void setIncreasAmount(BigDecimal increasAmount) {
		this.increasAmount = increasAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getRaiseDeadline() {
		return raiseDeadline;
	}

	public void setRaiseDeadline(Integer raiseDeadline) {
		this.raiseDeadline = raiseDeadline;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getRepaySource() {
		return repaySource;
	}

	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}

	public String getWindMeasure() {
		return windMeasure;
	}

	public void setWindMeasure(String windMeasure) {
		this.windMeasure = windMeasure;
	}

	public String getIsShowName() {
		try {
			if(isShow != null){
				isShowName = ConfigUtil.dictionaryMap.get(
						PropertyUtil.getProperties("whether")).get(isShow);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isShowName;
	}

	public void setIsShowName(String isShowName) {
		this.isShowName = isShowName;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getIsDeductible() {
		return isDeductible;
	}

	public void setIsDeductible(Integer isDeductible) {
		this.isDeductible = isDeductible;
	}

	public Integer getIsInterest() {
		return isInterest;
	}

	public void setIsInterest(Integer isInterest) {
		this.isInterest = isInterest;
	}

	public Integer getIsCash() {
		return isCash;
	}

	public void setIsCash(Integer isCash) {
		this.isCash = isCash;
	}

	public Integer getIsDouble() {
		return isDouble;
	}

	public void setIsDouble(Integer isDouble) {
		this.isDouble = isDouble;
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
					PropertyUtil.getProperties("productStatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("productType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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
	 * @return the remainsAmount
	 */
	public BigDecimal getRemainsAmount() {
		return remainsAmount;
	}

	/**
	 * @param remainsAmount the remainsAmount to set
	 */
	public void setRemainsAmount(BigDecimal remainsAmount) {
		this.remainsAmount = remainsAmount;
	}

	/**
	 * @return the mappingEndDate
	 */
	public Date getMappingEndDate() {
		return mappingEndDate;
	}

	/**
	 * @param mappingEndDate the mappingEndDate to set
	 */
	public void setMappingEndDate(Date mappingEndDate) {
		this.mappingEndDate = mappingEndDate;
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
	 * @return the raiseSuccessDate
	 */
	public Date getRaiseSuccessDate() {
		return raiseSuccessDate;
	}

	/**
	 * @param raiseSuccessDate the raiseSuccessDate to set
	 */
	public void setRaiseSuccessDate(Date raiseSuccessDate) {
		this.raiseSuccessDate = raiseSuccessDate;
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

	/**
	 * @return the searchStartDate
	 */
	public Date getSearchStartDate() {
		return searchStartDate;
	}

	/**
	 * @param searchStartDate the searchStartDate to set
	 */
	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	/**
	 * @return the searchEndDate
	 */
	public Date getSearchEndDate() {
		return searchEndDate;
	}

	/**
	 * @param searchEndDate the searchEndDate to set
	 */
	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public Date getEstablish() {
		return establish;
	}

	public void setEstablish(Date establish) {
		this.establish = establish;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptPic() {
		return acceptPic;
	}
	
	public void setAcceptPic(String acceptPic) {
		this.acceptPic = acceptPic;
	}

	public Integer getSurplusDay() {
		return surplusDay;
	}

	public void setSurplusDay(Integer surplusDay) {
		this.surplusDay = surplusDay;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getRenewal() {
		return renewal;
	}

	public void setRenewal(Integer renewal) {
		this.renewal = renewal;
	}

	public Integer getBespoke() {
		return bespoke;
	}

	public void setBespoke(Integer bespoke) {
		this.bespoke = bespoke;
	}

	public Date getFullDate() {
		return fullDate;
	}

	public void setFullDate(Date fullDate) {
		this.fullDate = fullDate;
	}

	/**
	 * @return the sName
	 */
	public String getsName() {
		return sName;
	}

	/**
	 * @param sName the sName to set
	 */
	public void setsName(String sName) {
		this.sName = sName;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public Integer getRemainRaiseDay() {
		return remainRaiseDay;
	}

	public void setRemainRaiseDay(Integer remainRaiseDay) {
		this.remainRaiseDay = remainRaiseDay;
	}

	public Date getActLoanTime() {
		return actLoanTime;
	}

	public void setActLoanTime(Date actLoanTime) {
		this.actLoanTime = actLoanTime;
	}

	public Date getStartactLoanTime() {
		return startactLoanTime;
	}

	public void setStartactLoanTime(Date startactLoanTime) {
		this.startactLoanTime = startactLoanTime;
	}

	public Date getEndactLoanTime() {
		return endactLoanTime;
	}

	public void setEndactLoanTime(Date endactLoanTime) {
		this.endactLoanTime = endactLoanTime;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getBankNO() {
		return bankNO;
	}

	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getProject_no() {
		return project_no;
	}

	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}

	public String getProject_st() {
		return project_st;
	}

	public void setProject_st(String project_st) {
		this.project_st = project_st;
	}

	public String getProject_usage() {
		return project_usage;
	}

	public void setProject_usage(String project_usage) {
		this.project_usage = project_usage;
	}

	public int getInterestSuccessTotal() {
		return interestSuccessTotal;
	}

	public void setInterestSuccessTotal(int interestSuccessTotal) {
		this.interestSuccessTotal = interestSuccessTotal;
	}

	public int getInterestFailTotal() {
		return interestFailTotal;
	}

	public void setInterestFailTotal(int interestFailTotal) {
		this.interestFailTotal = interestFailTotal;
	}

	public int getInterestStatus() {
		return interestStatus;
	}

	public void setInterestStatus(int interestStatus) {
		this.interestStatus = interestStatus;
	}

	public int getRepaySuccessTotal() {
		return repaySuccessTotal;
	}

	public void setRepaySuccessTotal(int repaySuccessTotal) {
		this.repaySuccessTotal = repaySuccessTotal;
	}

	public int getRepayFailTotal() {
		return repayFailTotal;
	}

	public void setRepayFailTotal(int repayFailTotal) {
		this.repayFailTotal = repayFailTotal;
	}

	public int getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(int repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getMchntTxnSsn() {
		return mchntTxnSsn;
	}

	public void setMchntTxnSsn(String mchntTxnSsn) {
		this.mchntTxnSsn = mchntTxnSsn;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRepayTypeName() {
		try {
			repayTypeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("repayType")).get(repayType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return repayTypeName;
	}

	public void setRepayTypeName(String repayTypeName) {
		this.repayTypeName = repayTypeName;
	}

	public BigDecimal getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(BigDecimal shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	@Override
	public String toString() {
		return "DrProductInfo [id=" + id + ", fid=" + fid + ", sid=" + sid + ", code=" + code + ", fullName=" + fullName
				+ ", simpleName=" + simpleName + ", type=" + type + ", status=" + status + ", intermediary="
				+ intermediary + ", isSid=" + isSid + ", amount=" + amount + ", alreadyRaiseAmount="
				+ alreadyRaiseAmount + ", surplusAmount=" + surplusAmount + ", rate=" + rate + ", activityRate="
				+ activityRate + ", repayType=" + repayType + ", deadline=" + deadline + ", leastaAmount="
				+ leastaAmount + ", increasAmount=" + increasAmount + ", maxAmount=" + maxAmount + ", raiseDeadline="
				+ raiseDeadline + ", startDate=" + startDate + ", fullDate=" + fullDate + ", expireDate=" + expireDate
				+ ", introduce=" + introduce + ", borrower=" + borrower + ", repaySource=" + repaySource
				+ ", windMeasure=" + windMeasure + ", isShow=" + isShow + ", isShowName=" + isShowName + ", isHot="
				+ isHot + ", isDeductible=" + isDeductible + ", isInterest=" + isInterest + ", isCash=" + isCash
				+ ", isDouble=" + isDouble + ", establish=" + establish + ", accept=" + accept + ", acceptPic="
				+ acceptPic + ", addDate=" + addDate + ", addUser=" + addUser + ", updDate=" + updDate + ", updUser="
				+ updUser + ", statusName=" + statusName + ", experienceName=" + experienceName + ", typeName="
				+ typeName + ", surplusDay=" + surplusDay + ", renewal=" + renewal + ", bespoke=" + bespoke
				+ ", mappingStatus=" + mappingStatus + ", remainsAmount=" + remainsAmount + ", mappingEndDate="
				+ mappingEndDate + ", mappingAmount=" + mappingAmount + ", raiseSuccessDate=" + raiseSuccessDate
				+ ", mappingCount=" + mappingCount + ", searchStartDate=" + searchStartDate + ", searchEndDate="
				+ searchEndDate + ", sName=" + sName + ", parentName=" + parentName + ", drClaimsPic=" + drClaimsPic
				+ ", drProductPic=" + drProductPic + ", drProductExtend=" + drProductExtend + ", loanStatus="
				+ loanStatus + ", loanName=" + loanName + ", loanCode=" + loanCode + ", atid=" + atid
				+ ", refundStartDate=" + refundStartDate + ", refundEndDate=" + refundEndDate + ", interest=" + interest
				+ ", fullStartDate=" + fullStartDate + ", fullEndDate=" + fullEndDate + ", remainRaiseDay="
				+ remainRaiseDay + ", actLoanTime=" + actLoanTime + ", startactLoanTime=" + startactLoanTime
				+ ", endactLoanTime=" + endactLoanTime + ", loanAmount=" + loanAmount + ", bankNO=" + bankNO
				+ ", bankName=" + bankName + ", prizeId=" + prizeId + ", prizeName=" + prizeName + ", principleId="
				+ principleId + ", principleH5=" + principleH5 + ", principlePC=" + principlePC + ", isSyn=" + isSyn
				+ ", companyName=" + companyName + ", heji=" + heji + "]";
	}
}