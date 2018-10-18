package com.sftpay.config;

public class ExpressGlobalConfig {
	/**
	 * 商户信息配置
	 */
	//测试
	// 商户号：商户在盛付通开通的商户号
	public final static String merchantNo = "540506";
	// 商户私钥：商户自己生成
	public final static String merchantPrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDbODPX+TrymksM5+aRF3K5b/cDT+fDq5GKfgGlZO8MlgEG4UpOEh097UYIYrWEzBQs53MDn3dIwlMt4X+4FuKcECG3+drZ3Ehzh68qDak+q/2pIiEb0QfdCULY8Xz3TIoN0EKnCCVoHkHo3+Vel3uaUtlU74bnB0CCw1jGWdJOSeEpUiU5MjujjyzYbOVsz4AdzbmEfZFB8e+mSsM7zjcriyUponkMSXBKIiDQehKn0YLp39N2sJrpG/2SOcZnPh3wc/k6OtJP52CHVMDjrB4wTdf6njiqntGub+nuiIJDDGl+4HzahVfQ5IruMbWThRQzUC+KF0Q2tIFIMc0XuXfJAgMBAAECggEBALUXFmyg67shDkJZBzRwREs/XLQVvAT9o7reIIn6eSbSe4KtdO5NNG7FpQakVAKhe0Ek6PPjNWybao8KKrFt5kC5asFJ2yoBOLCHM4HvyxGEjoS7NtJ9uJs1XU1NH8hCKAEFOyo0JoJ+DEBNRHMBfA+dxP7O54fNi9L5gEpKRNp50ePm20H5m2rHRu6nhOloAJZA+jXog3Yc2283Bnjx1PwpkP8LZypC+TodIOx3aZMRoXYSz6BRia8ylar1tr2Y8oJoOV8ENWGp6z0tj/YH/Cwc9jYHlHJWcMdzkTmlohwAiJmsFjRAsn8ognhmHoDFplaWM7Zlof5AhNSDXRwdHYkCgYEA7/dL9f3M0ArdfutBmx67O1l48HsAUG/O8dX49oVoCZI+91C/NyQ0r1A6iu9qjSvmIPBmbIOeGH0kRbl4BwRGF1j1wVB2rgl5FFf5oYs6TPvgGR9losvd4Bk3UOsua9nUkpmHOE/6ONZYGo2PhpYRhTc5/pOMW7jEW0UgicJd2e8CgYEA6d4HvU4nx8jWhFo4IDmb9lE6ccoG+6gUGSxkzQZUtfa+H/EsFlQv3GNGvXNQ/oQ3oAQQrFxfvOSQ51MXED2MOKluGqy7DSSkp2ocRfFU7wEbordU3rhfKMROsj1NYMj4anUWT4AnsVc8JezatKViWbXmwuyk1plovjaNWEFm4ccCgYA6nDUybP4EZlL5N+67O4NRmKXgXrqR6u0pxjBbzfO+OrkkYNWDW1V+6GKUIqvstSctLmpl4LPRmWctnIJDfHi+JR1JTSTflzK6lE5FdaMUwIRYvoFthMu5e482NWsOLpMsB1GuoGImVbwJKEBBCBBZcEa69kDW+kcDX9v1qcKQgwKBgQDkzNE7lpv6rHWa1P02IcaBDGUmcCW2zXCkVDdmEnyL7ZOCgpvEWKbecc4CiTBDYS1egruhNVqA3gkaF8Nnox9tS+2pcTYrHJ9uHrT8hKe4kJft8Hi60RxgMPZhEPKD7vqChHzIWLP8n0D8RaaOt4LqC7lxGL4IKdw8w/gy0QGLfwKBgQDN4POsfAgXieHkFZdOoc9++memQFgYQHVgfrepAw2MZxV+YX/75FkymHxKrONCAqFs7bS1u2GfPRpkpiYbyHmboAkBgK3AiUYExpBUs27ZjwP3FSNLYxT9Pe4f/tOXpmSpZnaiXvUJ16OPCBLRHf77+C8DQ8NLnVCkSGdW4/nd/w==";
	// 商户系统支付成功回调地址, 可以运行test下的server.Jetty. java启动服务来测试，商户通过绑定域名映射到公网就可以完成回调
	public final static String paymentNotifyUrl = "http://180.168.54.30:8888/recharge/receiveNotifySFT.do";

	
	// 盛付通RSA公钥，场景：当盛付通响应报文给商户，商户可以拿此参数对盛付通响应内容验证签名，确保商户收到的内容是盛付通响应，保证安全
	public final static String sftRsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC69veKW1X9GETEFr49gu9PN8w7H6alWec8wmF8SoP3tqQLAflZp8g83UZPX2UWhClnm53P5ZwesaeSTHkXkSI0iSjwd27N07bc8puNgB5BAGhJ80KYqTv3Zovl04C8AepVmxy9iFniJutJSYYtsRcnHYyUNoJai4VXhJsp5ZRMqwIDAQAB";
	// 快捷服务地址：盛付通线上请求地址
	public final static String expressServiceUrl = "http://mgw.shengpay.com/mas/api-acquire-channel/services/express";
	// 查询订单服务地址
	public final static String queryOrderServiceWSDL = "https://mas.shengpay.com/api-acquire-channel/services/queryOrderService?wsdl";

	// 借记卡/信用卡信息
	public final static String bankCardType = "DR"; // 银行卡类型, 例如：CR/DR
	// 身份证信息
	public final static String idType = "IC"; // 证件类型, 例如身份证证件类型：IC
	// 产品信息
	public final static String productName = "理财投资"; // 商品名称
	public final static String currency = "CNY"; // 货币类型
	// 为保证快捷支付的安全性，快捷服务统一使用RSA机制
	public final static String rsaSignType = "RSA";
	// 报文编码，统一为UTF-8
	public final static String utf8 = "UTF-8";

	/**
	 * 快捷服务所有接口
	 */
	// 创建支付 订单
	public final static String expressCreatePaymentOrderUrl = expressServiceUrl + "/createPaymentOrder";
	// 签约预校验(发送短信验证码)
	public final static String expressPrecheckForSignUrl = expressServiceUrl + "/precheckForSign";
	// 签约确认(绑卡)
	public final static String expressSignUrl = expressServiceUrl + "/sign";
	// 支付预校验(发送短信验证码)
	public final static String expressPrecheckForPaymentUrl = expressServiceUrl + "/precheckForPayment";
	// 支付
	public final static String expressPaymentUrl = expressServiceUrl + "/payment";
	// 查询支持的银行卡列表
	public final static String expressGetInstListUrl = expressServiceUrl + "/getInstList";
	// 查询协议
	public final static String expressQueryAgreementUrl = expressServiceUrl + "/queryAgreement";
	// 解约(取消绑卡)
	public final static String expressUnsignUrl = expressServiceUrl + "/unsign";
}
