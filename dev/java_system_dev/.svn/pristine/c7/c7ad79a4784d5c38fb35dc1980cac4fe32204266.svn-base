package com.jsjf.model.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrProductInvest {

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 会员ID
	 */
	private Integer uid;

	/**
	 * 产品ID
	 */
	private Integer pid;

	/**
	 * 投资总金额
	 */
	private BigDecimal amount;

	/**
	 * 投资时间
	 */
	private Date investTime;

	/**
	 * 状态(0-失败 1-成功)
	 */
	private Integer status;

	/**
	 * 实收本金
	 */
	private BigDecimal factAmount;
	/**
	 * 利息
	 */
	private BigDecimal interest;
	/**
	 * 实收利息
	 */
	private BigDecimal factInterest;

	/**
	 * 加入方式(0-PC端 1-IOS 2-安卓)
	 */
	private Integer joinType;
	
	/**
	 * 优惠券ID
	 */
	private Integer fid;
	
	/**
	 * 协议编号
	 */
	private String agreementNo;
	
	private Integer type;//类型
	private String code;//产品编号
	private String typeName;//类型名称
	private Integer deadline;//项目周期
	private BigDecimal rate;//年利率
	private String realname;//真实姓名
	private String mobilephone;//账号
	private String recommCodes;//账号
	
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String joinTypeName;//加入方式
	private String fullName;//产品名称
	private BigDecimal productAmount;//产品总额
	private BigDecimal receivedAmount;//已收本息
	private BigDecimal collectAmount;//待收本息
	private String scode;//标的编号
	private Date startShouldTime;//还款日期
	private Date endShouldTime;//还款日期
	private Date expireDate;//回款日期
	
	private Date startRegDate;//注册日期
	private Date endRegDate;//注册日期
	
	private BigDecimal transferSurplusAmount;//匹配后剩余金额
	private Integer productStatus;//客服页面需要的产品状态
	
	private String remark;//投资说明
	
	private BigDecimal specialRate;//双旦活动利率
	private BigDecimal activityRate;//活动利率
	
	private String experience;//体验金id集合{id1,id2,id3}
	
	private Date regDateStart;
	private Date regDateEnd;
	private Integer count_allStart;
	private Integer count_allEnd;
	private Integer[] cids;
	
	private String message;
	private String remitMchntTxnSsn;
	private String contract_no; //预授权合同号
	private String seqNo;//文件名称
	private Integer fileStatus;//状态
	private String fuiouMessageNo;//报文流水号
	private String failureCause;//失败原因
	private String tableName = "dr_product_invest";//表名
	
	private Integer repayType;//还款方式
	private String repayTypeName;//还款方式

	private Integer fullFileStatus;//满标报备状态
	private String fullFuiouMessageNo;//满标报备报文流水号
	private String fullFailureCause;//满标报备失败原因

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFuiouMessageNo() {
		return fuiouMessageNo;
	}

	public void setFuiouMessageNo(String fuiouMessageNo) {
		this.fuiouMessageNo = fuiouMessageNo;
	}

	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
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

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getFactAmount() {
		return factAmount;
	}

	public void setFactAmount(BigDecimal factAmount) {
		this.factAmount = factAmount;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getFactInterest() {
		return factInterest;
	}

	public void setFactInterest(BigDecimal factInterest) {
		this.factInterest = factInterest;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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


	/**
	 * @return the joinTypeName
	 */
	public String getJoinTypeName() {
		try {
			joinTypeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("channel")).get(joinType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return joinTypeName;
	}

	/**
	 * @param joinTypeName the joinTypeName to set
	 */
	public void setJoinTypeName(String joinTypeName) {
		this.joinTypeName = joinTypeName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the productAmount
	 */
	public BigDecimal getProductAmount() {
		return productAmount;
	}

	/**
	 * @param productAmount the productAmount to set
	 */
	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	/**
	 * @return the receivedAmount
	 */
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	/**
	 * @param receivedAmount the receivedAmount to set
	 */
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	/**
	 * @return the collectAmount
	 */
	public BigDecimal getCollectAmount() {
		return collectAmount;
	}

	/**
	 * @param collectAmount the collectAmount to set
	 */
	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}

	/**
	 * @return the scode
	 */
	public String getScode() {
		return scode;
	}

	/**
	 * @param scode the scode to set
	 */
	public void setScode(String scode) {
		this.scode = scode;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	/**
	 * @return the mobilephone
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * @param mobilephone the mobilephone to set
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}


	/**
	 * @return the startShouldTime
	 */
	public Date getStartShouldTime() {
		return startShouldTime;
	}

	/**
	 * @param startShouldTime the startShouldTime to set
	 */
	public void setStartShouldTime(Date startShouldTime) {
		this.startShouldTime = startShouldTime;
	}

	/**
	 * @return the endShouldTime
	 */
	public Date getEndShouldTime() {
		return endShouldTime;
	}

	/**
	 * @param endShouldTime the endShouldTime to set
	 */
	public void setEndShouldTime(Date endShouldTime) {
		this.endShouldTime = endShouldTime;
	}

	public BigDecimal getTransferSurplusAmount() {
		if(transferSurplusAmount == null){
			return factAmount;
		}else{
			return transferSurplusAmount;
		}
	}

	public void setTransferSurplusAmount(BigDecimal transferSurplusAmount) {
		this.transferSurplusAmount = transferSurplusAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(BigDecimal specialRate) {
		this.specialRate = specialRate;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public BigDecimal getActivityRate() {
		return activityRate;
	}

	public void setActivityRate(BigDecimal activityRate) {
		this.activityRate = activityRate;
	}

	public Date getStartRegDate() {
		return startRegDate;
	}

	public void setStartRegDate(Date startRegDate) {
		this.startRegDate = startRegDate;
	}

	public Date getEndRegDate() {
		return endRegDate;
	}

	public void setEndRegDate(Date endRegDate) {
		this.endRegDate = endRegDate;
	}

	public Date getRegDateStart() {
		return regDateStart;
	}

	public void setRegDateStart(Date regDateStart) {
		this.regDateStart = regDateStart;
	}

	public Date getRegDateEnd() {
		return regDateEnd;
	}

	public void setRegDateEnd(Date regDateEnd) {
		this.regDateEnd = regDateEnd;
	}

	public Integer getCount_allStart() {
		return count_allStart;
	}

	public void setCount_allStart(Integer count_allStart) {
		this.count_allStart = count_allStart;
	}

	public Integer getCount_allEnd() {
		return count_allEnd;
	}

	public void setCount_allEnd(Integer count_allEnd) {
		this.count_allEnd = count_allEnd;
	}

	public Integer[] getCids() {
		return cids;
	}

	public void setCids(Integer[] cids) {
		this.cids = cids;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemitMchntTxnSsn() {
		return remitMchntTxnSsn;
	}

	public void setRemitMchntTxnSsn(String remitMchntTxnSsn) {
		this.remitMchntTxnSsn = remitMchntTxnSsn;
	}

	

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
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

	public Integer getFullFileStatus() {
		return fullFileStatus;
	}

	public void setFullFileStatus(Integer fullFileStatus) {
		this.fullFileStatus = fullFileStatus;
	}

	public String getFullFuiouMessageNo() {
		return fullFuiouMessageNo;
	}

	public void setFullFuiouMessageNo(String fullFuiouMessageNo) {
		this.fullFuiouMessageNo = fullFuiouMessageNo;
	}

	public String getFullFailureCause() {
		return fullFailureCause;
	}

	public void setFullFailureCause(String fullFailureCause) {
		this.fullFailureCause = fullFailureCause;
	}

	public String getRecommCodes() {
		return recommCodes;
	}

	public void setRecommCodes(String recommCodes) {
		this.recommCodes = recommCodes;
	}
	

}
