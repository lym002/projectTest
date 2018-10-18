package com.wechat.util;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.Http;
import com.jsjf.service.system.impl.RedisClientTemplate;

/**
 * 被动模板消息工具类
 * @author cece
 *
 */
@Lazy(false)
@Component
public class ModelPassivityMessageSendUtil {
	
	private Logger log = Logger.getLogger(ModelPassivityMessageSendUtil.class);
	
	private String sendModelUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	private String investModelId = "wOiX2VOykmuwRK0W8BaNRISokP24SQ60nJoT4N-Z6C4";//产品交易认购成功模板ID
	private String payModelId = "1A0FciQ2bJDA8kmHbDZuv6udWJj5AipV42MnMVorTD8";//充值成功模板ID
	
	//测试
	//private String payModelId = "c0SD--K_GxC8YWA50xaTVjB0FQ18r6SkIGIOcHefJLo";//充值成功模板ID
	//private String investModelId = "EOWp3xqe_DAEvYrUAT3jKYOLjOJM2CZwM8NCuLieyKU";
	
	private String remark = "感谢使用！";
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private static ModelPassivityMessageSendUtil modelMessage;
	
	 @PostConstruct
	 private void init() {    
		 modelMessage = this;
     } 
	 
	
	//测试模板
	//private String tid = "c0SD--K_GxC8YWA50xaTVjB0FQ18r6SkIGIOcHefJLo";
	
	private String modelJson = "{\"touser\": \"\","
			+"\"template_id\": \"\","
			+"\"url\": \"\","
			+"\"topcolor\": \"#FF0000\","
			+"\"data\": {"
				+"\"first\": {"
				+"}}}";  
	
	/**
	 * 被动模板消息发送
	 * @param json
	 * @return
	 */
	public void sendModel(String json){
		try{
			String accessToken = modelMessage.redisClientTemplate.get("wxAccessToken");
			Http.sendPost(sendModelUrl+accessToken, json);
		}catch(Exception e){
			log.error("accessToken为null",e);
		}
	}
	
	/**
	 * 充值模板数据填充
	 * @param openId 用户开放id
	 * @param payMoney 充值金额
	 * @param payChannel 充值渠道
	 * @param bank 充值银行
	 * @param balance 账户余额
	 */
	public void payJson(String openId,String date, String payMoney,String balance){
		JSONObject json = JSONObject.parseObject(modelJson);
		JSONObject jsonDate = json.getJSONObject("data");
		JSONObject jsonFirst = jsonDate.getJSONObject("first");
		jsonFirst.put("value", "充值成功!");
		jsonFirst.put("color", "#173177");
		jsonDate.put("keyword1", JSONObject.parse("{\"value\":\""+date+"\",\"color\":\"#173177\"}"));
		jsonDate.put("keyword2", JSONObject.parse("{\"value\":\""+payMoney+"\",\"color\":\"#173177\"}"));
		jsonDate.put("keyword3", JSONObject.parse("{\"value\":\""+balance+"\",\"color\":\"#173177\"}"));
		jsonDate.put("remark", JSONObject.parse("{\"value\":\""+remark+"\",\"color\":\"#173177\"}"));
		jsonDate.put("first", jsonFirst);
		json.put("data", jsonDate);
		json.put("touser", openId);
		json.put("template_id", payModelId);
		sendModel(json.toString());
	}

	/**
	 * 产品认购投资模板填充
	 * @param openId
	 * @param investMoney 投资金额
	 * @param productName 产品名称
	 * @param productDay 产品期限
	 * @param yxsy 预期收益
	 */
	public void investJson(String openId,
			String investMoney, String productName,String productDay,String yxsy){
		JSONObject json = JSONObject.parseObject(modelJson);
		JSONObject jsonDate = json.getJSONObject("data");
		JSONObject jsonFirst = jsonDate.getJSONObject("first");
		jsonFirst.put("value", "产品认购成功!");
		jsonFirst.put("color", "#173177");
		jsonDate.put("keyword1", JSONObject.parse("{\"value\":\""+investMoney+"\",\"color\":\"#173177\"}"));
		jsonDate.put("keyword2", JSONObject.parse("{\"value\":\""+productName+"\",\"color\":\"#173177\"}"));
		jsonDate.put("keyword3", JSONObject.parse("{\"value\":\""+productDay+"\",\"color\":\"#173177\"}"));
		jsonDate.put("keyword4", JSONObject.parse("{\"value\":\""+yxsy+"\",\"color\":\"#173177\"}"));
		jsonDate.put("remark", JSONObject.parse("{\"value\":\""+remark+"\",\"color\":\"#173177\"}"));
		jsonDate.put("first", jsonFirst);
		json.put("data", jsonDate);
		json.put("touser", openId);
		json.put("template_id", investModelId);
		sendModel(json.toString());
	}
	
/*	public static void main(String[] args) {
		ModelPassivityMessageSendUtil m = new ModelPassivityMessageSendUtil();
		m.investJson("ovUGf0m_yinNTKIxmJxoCACjObcs","123.11","水电费","30","12.01");
	}*/

}