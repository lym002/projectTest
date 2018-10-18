package com.jsjf.model.system;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户营销交易流水
 * @author DELL
 *
 */
public class JsMerchantMarketing {
	
	private Integer id;//主键
	private BigDecimal amount;//营销 支持 金额
	private Integer pid;//产品id
	private Integer investId;//投资id
	private Integer fid;//红包id
	private Integer uid;//用户id
	private Integer type;//类型: 0,投资返现红包,1:好友返现,2:回款收益 
	private Date addtime;//添加时间
	private String mchnt_txn_ssn;//交易流水
	private String fuiouMessageNo;//报文流水号
	private Integer fileStatus;//报备状态：1-报文已上传，2-报备成功，3-报备失败
	private String remark;//备注
	
	public JsMerchantMarketing() {
	}
	
	public JsMerchantMarketing(BigDecimal amount, Integer investId,Integer pid,
			Integer fid, Integer uid, Integer type, Date addtime,
			String mchnt_txn_ssn, 
			String remark) {
		super();
		this.amount = amount;
		this.investId = investId;
		this.pid = pid;
		this.fid = fid;
		this.uid = uid;
		this.type = type;
		this.addtime = addtime;
		this.mchnt_txn_ssn = mchnt_txn_ssn;
		this.remark = remark;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}
	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}
	public String getFuiouMessageNo() {
		return fuiouMessageNo;
	}
	public void setFuiouMessageNo(String fuiouMessageNo) {
		this.fuiouMessageNo = fuiouMessageNo;
	}
	public Integer getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
