package com.reapal.config;
/* *
 *功能：设置帐户有关信息及返回路径（基础配置页面）
 *版本：3.1.2
 *日期：2015-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究融宝支付接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.访问融宝支付商户后台，然后用您的签约融宝支付账号登陆(注册邮箱号).
 *2.点击导航栏中的“商家服务”，即可查看
	
 * */


public class ReapalConfig {
	//测试环境
	// 商户ID，由纯数字组成的字符串
	public static String merchant_id = "100000000000147";
	//交易安全检验码，由数字和字母组成的64位字符串
	public static String key = "g0be2385657fa355af68b74e9913a1320af82gb7ae5f580g79bffd04a402ba8f";
	//商户私钥
	public static String privateKey = "/usr/local/tomcat/config/rb-key/itrus001.pfx";
//	public static String privateKey = "D:\\cert\\itrus001.pfx";
    //融宝公钥
	public static String pubKeyUrl = "/usr/local/tomcat/config/rb-key/itrus001.cer";
//	public static String pubKeyUrl = "D:\\cert\\itrus001.cer";
	public static String rongpay_api = "http://testapi.reapal.com";
//	// 私钥密码
	public static String password = "123456";

	public static String version = "3.1.3";

	public static String getMerchant_id() {
		return merchant_id;
	}

	public static String getKey() {
		return key;
	}

	public static String getVersion() {
		return version;
	}

	public static String getPrivateKey() {
		return privateKey;
	}

	public static String getRongpay_api() {
		return rongpay_api;
	}

	public static String getPubKeyUrl() {
		return pubKeyUrl;
	}

	public static String getPassword() {
		return password;
	}
	
}
