package com.reapal.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.reapal.config.ReapalConfig;



public class RongpayService {
	private static String gateway=ReapalConfig.getRongpay_api()+"/web/portal";
	/**
	 * 功能：构造表单提交HTML
	 * @param merchant_ID 合作身份者ID
	 * @param seller_email 签约融宝支付账号或卖家融宝支付帐户
	 * @param return_url 付完款后跳转的页面 要用 以http开头格式的完整路径，不允许加?id=123这类自定义参数
	 * @param notify_url 交易过程中服务器通知的页面 要用 以http开格式的完整路径，不允许加?id=123这类自定义参数
	 * @param order_no 请与贵网站订单系统中的唯一订单号匹配
	 * @param title 订单名称，显示在融宝支付收银台里的“商品名称”里，显示在融宝支付的交易管理的“商品名称”的列表里。
	 * @param body 订单描述、订单详细、订单备注，显示在融宝支付收银台里的“商品描述”里
	 * @param total_fee 订单总金额，显示在融宝支付收银台里的“交易金额”里
	 * @param buyer_email 默认买家融宝支付账号
	 * @param charset 字符编码格式 目前支持 GBK 或 utf-8
	 * @param key 安全校验码
	 * @param sign_type 签名方式 不需修改
	 * @return 表单提交HTML文本
	 */
	public static String BuildFormWeb(String seller_email, String merchant_id, String notify_url, String return_url, String transtime, String currency,
			String member_id, String member_ip, String terminal_info, String key, String sign_type,
			String order_no, String title, String body, String default_bank, String total_fee,String pay_method) throws Exception{
		
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("seller_email",seller_email);
		sPara.put("merchant_id",merchant_id);
		sPara.put("notify_url", notify_url);
		sPara.put("return_url", return_url);
		sPara.put("transtime", transtime);
		sPara.put("currency", currency);
		sPara.put("member_id", member_id);
		sPara.put("member_ip", member_ip);
		//sPara.put("terminal_type", terminal_type);
		sPara.put("terminal_info", terminal_info);
		sPara.put("sign_type", sign_type);
		sPara.put("order_no", order_no);
		sPara.put("title", title);
		sPara.put("body", body);
		sPara.put("total_fee", total_fee);
		sPara.put("payment_type", "1");
		sPara.put("default_bank", default_bank);
		sPara.put("pay_method", pay_method);
		
		String mysign = Md5Utils.BuildMysign(sPara, key);//生成签名结果
		
		sPara.put("sign", mysign);
	      
	    String json = JSON.toJSONString(sPara);

	    Map<String, String> maps = DecipherWeb.encryptData(json);
		
		StringBuffer sbHtml = new StringBuffer();
		
		
		//post方式传递
		sbHtml.append("<form id=\"rongpaysubmit\" name=\"rongpaysubmit\" action=\"").append(gateway).append("\" method=\"post\">");
		
		 sbHtml.append("<input type=\"hidden\" name=\"merchant_id\" value=\"").append(merchant_id).append("\"/>");
        sbHtml.append("<input type=\"hidden\" name=\"data\" value=\"").append(maps.get("data")).append("\"/>");
        sbHtml.append("<input type=\"hidden\" name=\"encryptkey\" value=\"").append(maps.get("encryptkey")).append("\"/>");
        
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" class=\"button_p2p\" value=\"融宝支付确认付款\"></form>");
		return sbHtml.toString();
		
	}
	
	

}
