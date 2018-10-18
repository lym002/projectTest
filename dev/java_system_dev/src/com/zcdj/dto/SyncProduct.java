package com.zcdj.dto;

public class SyncProduct {

	private String productNo;
	
	private String platformProductName;
	
	private String contractNo;

	private String repaymentDate;

	private String interest;

	private String loanAmount;

	private Integer loanDeadline;

	private String loanRate;

	private String loanDate;

	private String establishDate;

    public SyncProduct() {
    }

    public SyncProduct(String productNo, String platformProductName, String contractNo, String repaymentDate, String interest, String loanAmount, Integer loanDeadline, String loanRate, String loanDate, String establishDate) {
        this.productNo = productNo;
        this.platformProductName = platformProductName;
        this.contractNo = contractNo;
        this.repaymentDate = repaymentDate;
        this.interest = interest;
        this.loanAmount = loanAmount;
        this.loanDeadline = loanDeadline;
        this.loanRate = loanRate;
        this.loanDate = loanDate;
        this.establishDate = establishDate;
    }

    public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getPlatformProductName() {
		return platformProductName;
	}

	public void setPlatformProductName(String platformProductName) {
		this.platformProductName = platformProductName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanDeadline() {
        return loanDeadline;
    }

    public void setLoanDeadline(Integer loanDeadline) {
        this.loanDeadline = loanDeadline;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }
}
