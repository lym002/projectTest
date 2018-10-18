package com.jsjf.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.Utils;

public class DrProductInfo implements Serializable{

	private static final long serialVersionUID = -656107006645107145L;

	private Integer id;//产品ID
    
    private Integer sid;//标的ID
    
    private Integer fid;//续发产品的上一个产品ID

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
    
    private BigDecimal activityRate;//活动利率
    
    private Integer repayType;//还款方式
    
    private Integer deadline;//产品期限
    
    private BigDecimal leastaAmount;//起投金额
    
    private BigDecimal increasAmount;//递增金额
    
    private BigDecimal maxAmount;//最大金额
    
    private Integer raiseDeadline;//募集期限

    private Date startDate;//上架日期
    
    private Date fullDate;//满标时间
    
    private Date expireDate;//产品回款日期

    private String introduce;//产品介绍
    
    private String borrower;//借款人
    
    private String repaySource;//还款来源
    
    private String windMeasure;//风控措施
    
    private Integer isShow;//是否显示

    private Integer isHot;//是否热推

    private Integer isDeductible;//可否抵扣
    
    private Integer isInterest;//可否加息
    
    private Integer isCash;//可否返现
    
    private Integer isDouble;//可否加倍
    
    private Integer interestType;//计息方式 0 = 次日计息
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
    private Integer updUser;//修改人
    
    private BigDecimal pert; //募集完成百分比
    
    private Integer billType; //票据类型 1=商票，2=银票
    private String acceptor;//票据承兑人
    private Date endDate;//募集结束时间
    
    private Date establish;//成立日期
    
    private String accept;//承兑机构
    
    private String acceptPic;//承兑机构图片
    
    private Integer atid;//活动产品id
    
    private BigDecimal maxActivityCoupon;//绑定的加息券最高利率
    
    private Integer isEgg;//是否已砸
    
    private Integer prizeId;//产品奖品Id
    private Integer activityPeriods;//期数
    
    private String project_no; //存管项目编号
    private Integer principleId; //原理图id
	private String principleH5; //H5原理图
	private String principlePC; //PC原理图
	
	private Integer loanStatus;//放款状态
    
	
	public String getProject_no() {
		return project_no;
	}

	public void setProject_no(String project_no) {
		this.project_no = project_no;
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

	public Integer getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(Integer activityPeriods) {
		this.activityPeriods = activityPeriods;
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

	public BigDecimal getPert() {
		return pert;
	}

	public void setPert(BigDecimal pert) {
		this.pert = pert;
	}

	public Integer getInterestType() {
		return interestType;
	}

	public void setInterestType(Integer interestType) {
		this.interestType = interestType;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public Date getEndDate() {
		if(this.startDate != null){
			return Utils.getDayNumOfAppointDate(this.getStartDate(), -this.getRaiseDeadline());
		}else{
			return null;
		}
		
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the isDouble
	 */
	public Integer getIsDouble() {
		return isDouble;
	}

	/**
	 * @param isDouble the isDouble to set
	 */
	public void setIsDouble(Integer isDouble) {
		this.isDouble = isDouble;
	}

	/**
	 * @return the establish
	 */
	public Date getEstablish() {
		return establish;
	}

	/**
	 * @param establish the establish to set
	 */
	public void setEstablish(Date establish) {
		this.establish = establish;
	}

	/**
	 * @return the accept
	 */
	public String getAccept() {
		return accept;
	}

	/**
	 * @param accept the accept to set
	 */
	public void setAccept(String accept) {
		this.accept = accept;
	}

	/**
	 * @return the acceptPic
	 */
	public String getAcceptPic() {
		return acceptPic;
	}

	/**
	 * @param acceptPic the acceptPic to set
	 */
	public void setAcceptPic(String acceptPic) {
		this.acceptPic = acceptPic;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Date getFullDate() {
		return fullDate;
	}

	public void setFullDate(Date fullDate) {
		this.fullDate = fullDate;
	}

	public Integer getAtid() {
		return atid;
	}

	public void setAtid(Integer atid) {
		this.atid = atid;
	}

	public BigDecimal getMaxActivityCoupon() {
		return maxActivityCoupon;
	}

	public void setMaxActivityCoupon(BigDecimal maxActivityCoupon) {
		this.maxActivityCoupon = maxActivityCoupon;
	}

	public Integer getIsEgg() {
		return isEgg;
	}

	public void setIsEgg(Integer isEgg) {
		this.isEgg = isEgg;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	
	
}