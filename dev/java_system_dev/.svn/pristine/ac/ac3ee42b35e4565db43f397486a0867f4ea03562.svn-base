package com.jsjf.model.product;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户投资回款信息
 */
public class DrProductInvestRepayInfo {
	
	private Integer id;//
	
	private Integer uid;//用户ID
	
	private Integer investId;
	
	private Integer pid;
	
	private BigDecimal shouldPrincipal;//应收本金	
	
	private BigDecimal factPrincipal;//实收本金
	
	private BigDecimal shouldInterest;//应收利息
	
	private BigDecimal factInterest;//实收利息
	
	private BigDecimal penalty;//罚息
	
	private Integer status;//状态 0未还款，1已还款，2逾期
	
	private Date shouldTime;//
	
	private Date factTime;//
	private Integer pdId;//按月付息产品，每期回款ID
	
	private String remitMchntTxnSsn;//划拨还款流水号
	private int remitStatus;//划拨还款状态，1-未划拨，2-成功，3-失败
	private String remitFailReson;//划拨还款失败原因
	private Integer fileStatus;//状态
	private String fuiouMessageNo;//报文流水号
	private String failureCause;//失败原因
	private String tableName = "dr_product_invest_repayinfo";//表名
	
	private Integer transfer_status; //转账状态 1-未转账 2-成功，3-失败
	private String transferMchntTxnSsn;//转账流水号
	private String transfer_fail_reson;//转账失败原因
	
	private Integer productType;//产品type
	private String project_no;//产品type
	
	private BigDecimal basicprofit;//基本利息
	private Integer mchntPay;//基本利息
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public DrProductInvestRepayInfo(){}
	public DrProductInvestRepayInfo(Integer uid, Integer investId, Integer pid, BigDecimal shouldPrincipal,
			BigDecimal factPrincipal, BigDecimal shouldInterest,
			BigDecimal factInterest, BigDecimal penalty, Integer status ,Date shouldTime,BigDecimal basicprofit) {
		super();
		this.uid = uid;
		this.investId = investId;
		this.pid = pid;
		this.shouldPrincipal = shouldPrincipal;
		this.factPrincipal = factPrincipal;
		this.shouldInterest = shouldInterest;
		this.factInterest = factInterest;
		this.penalty = penalty;
		this.status = status;
		this.shouldTime = shouldTime;
		this.basicprofit=basicprofit;
	}

	
	
	public String getProject_no() {
		return project_no;
	}
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Integer getTransfer_status() {
		return transfer_status;
	}
	public void setTransfer_status(Integer transfer_status) {
		this.transfer_status = transfer_status;
	}
	public String getTransferMchntTxnSsn() {
		return transferMchntTxnSsn;
	}
	public void setTransferMchntTxnSsn(String transferMchntTxnSsn) {
		this.transferMchntTxnSsn = transferMchntTxnSsn;
	}
	public String getTransfer_fail_reson() {
		return transfer_fail_reson;
	}
	public void setTransfer_fail_reson(String transfer_fail_reson) {
		this.transfer_fail_reson = transfer_fail_reson;
	}
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

	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
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

	public BigDecimal getShouldPrincipal() {
		return shouldPrincipal;
	}

	public void setShouldPrincipal(BigDecimal shouldPrincipal) {
		this.shouldPrincipal = shouldPrincipal;
	}

	public BigDecimal getFactPrincipal() {
		return factPrincipal;
	}

	public void setFactPrincipal(BigDecimal factPrincipal) {
		this.factPrincipal = factPrincipal;
	}

	public BigDecimal getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(BigDecimal shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public BigDecimal getFactInterest() {
		return factInterest;
	}

	public void setFactInterest(BigDecimal factInterest) {
		this.factInterest = factInterest;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getShouldTime() {
		return shouldTime;
	}

	public void setShouldTime(Date shouldTime) {
		this.shouldTime = shouldTime;
	}

	public Date getFactTime() {
		return factTime;
	}

	public void setFactTime(Date factTime) {
		this.factTime = factTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getInvestId() {
		return investId;
	}

	public void setInvestId(Integer investId) {
		this.investId = investId;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getRemitMchntTxnSsn() {
		return remitMchntTxnSsn;
	}

	public void setRemitMchntTxnSsn(String remitMchntTxnSsn) {
		this.remitMchntTxnSsn = remitMchntTxnSsn;
	}

	public int getRemitStatus() {
		return remitStatus;
	}

	public void setRemitStatus(int remitStatus) {
		this.remitStatus = remitStatus;
	}

	public String getRemitFailReson() {
		return remitFailReson;
	}

	public void setRemitFailReson(String remitFailReson) {
		this.remitFailReson = remitFailReson;
	}
	public BigDecimal getBasicprofit() {
		return basicprofit;
	}
	public void setBasicprofit(BigDecimal basicprofit) {
		this.basicprofit = basicprofit;
	}
	public Integer getMchntPay() {
		return mchntPay;
	}
	public void setMchntPay(Integer mchntPay) {
		this.mchntPay = mchntPay;
	}
	
}
