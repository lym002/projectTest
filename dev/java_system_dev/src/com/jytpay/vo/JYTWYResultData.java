package com.jytpay.vo;

import java.io.Serializable;

public class JYTWYResultData implements Serializable{
    private static final long serialVersionUID = 1L;
    private String tranCode;//交易代码
    private String version;//版本号 
    private String charset;//编码方式
    private String merchantId;//商户号    	
    private String oriMerOrderId;//原商户订单号
    private String orderType;//订单类型   
    private String oriPayOrderId;//原平台支付订单号
    private String tranState;// 交易状态    00-初始 01-支付中，02-支付成功，03-支付失败，04-过期订单 ,05-撤销成功,06-作废订单	
    private String tranAmt;//订单金额
    private String respCode;//交易响应码
    private String respDesc;//交易响应码描述
    private String signType;//签名类型 	
    private String sign;//签名
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOriMerOrderId() {
		return oriMerOrderId;
	}
	public void setOriMerOrderId(String oriMerOrderId) {
		this.oriMerOrderId = oriMerOrderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOriPayOrderId() {
		return oriPayOrderId;
	}
	public void setOriPayOrderId(String oriPayOrderId) {
		this.oriPayOrderId = oriPayOrderId;
	}
	public String getTranState() {
		return tranState;
	}
	public void setTranState(String tranState) {
		this.tranState = tranState;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
