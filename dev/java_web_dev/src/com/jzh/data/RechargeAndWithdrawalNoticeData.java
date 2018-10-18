package com.jzh.data;

/**
 * 充值/提现通知接口数据解析
 * @author aj
 *
 */
public class RechargeAndWithdrawalNoticeData extends BaseReqdata {
	private String mchnt_txn_dt="";
	private String mobile_no="";
	private String amt="";
	private String remark="";
	public String getMchnt_txn_dt() {
		return mchnt_txn_dt;
	}
	public void setMchnt_txn_dt(String mchnt_txn_dt) {
		this.mchnt_txn_dt = mchnt_txn_dt;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

    @Override
    public String toString() {
        return "RechargeAndWithdrawalNoticeData{" +
                "mchnt_txn_dt='" + mchnt_txn_dt + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", amt='" + amt + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
