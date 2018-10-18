package com.jsjf.controller.partner.rrl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.SecurityUtils;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberBaseInfoService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/api/rrl")
public class RrlController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public DrMemberBaseInfoService drMemberBaseInfoService;
	@Autowired
	public SysMessageLogService sysMessageLogService;
	@Autowired
	public DrMemberService drMemberService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	
	@RequestMapping(value="/register",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String register(HttpServletRequest req, HttpServletResponse response){
		log.info("请求注册数据："+JSON.toJSONString(Utils.getParameterMap(req)));
		JSONObject json = Utils.getParameterMap(req);
		String CustId = json.getString("Cust_id");
		String phone = json.getString("Phone");
		String sign = json.getString("Sign");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DrMember member = new DrMember();
			if(RrlBase.getInstance().validCustId(CustId)){//验证商户号
				if(RrlBase.getInstance().validSign(CustId, phone, sign)){//验证签名
					//如果注册用户名为空则用户名为手机号码
					member.setMobilephone(phone);
					member.setPassWord(SecurityUtils.MD5AndSHA256(member.getMobilephone()));
					member.setRegIp(json.getString("ipfrom"));
					member.setMobileVerify(1);//手机号码已认证
					member.setRegDate(new Date());
					member.setLoginVerify(0);
					member.setEmailVerify(0);
					member.setRealVerify(0);
					member.setIsBlack(0);
					member.setStatus(1);
					member.setRegFrom(json.getString("cfrom").equals("pc")?0:9);//注册来源分PC和手机（未知手机类型）
					member.setToFrom("rrl");
					
					Boolean flag = redisClientTemplate.tryLock("reg.mobilephone."+member.getMobilephone(), 3, TimeUnit.SECONDS,true);
					if(flag){
						if(drMemberService.isExistsMobilphone(phone)){
							map.clear();
							map.put("Code", "100");
							map.put("Tip", "错误提示");
							map.put("Data", "该手机号已注册");
							log.info("人人利注册返回数据："+JSON.toJSONString(map));
							return JSON.toJSONString(map);
						}
						DrMemberBaseInfo baseinfo = new DrMemberBaseInfo();
						drMemberService.insertMember(baseinfo,member,null);
						
						SysMessageLog sms = new SysMessageLog(member.getUid(), "您已成功注册币优铺理财账号，登录用户名："+phone+"，临时密码："+phone+"，请登录币优铺理财修改密码。",
								2, null, member.getMobilephone());
						sysMessageLogService.sendMsg(sms, 1);
						
						JSONObject obj = new JSONObject();
						obj.put("Cust_key", member.getUid());
						map.clear();
						map.put("Code", "101");
						map.put("Tip", "操作成功");
						map.put("Data", obj.toJSONString());
					}else{
						map.clear();
						map.put("Code", "103");
						map.put("Tip", "错误提示");
						map.put("Data", "系统繁忙，请稍后再试");
					}
				}else{
					map.clear();
					map.put("Code", "108");
					map.put("Tip", "错误提示");
					map.put("Data", "签名校验失败");
				}
			}else{
				map.clear();
				map.put("Code", "");
				map.put("Tip", "错误提示");
				map.put("Data", "商户号校验失败");
			}
			
		} catch (Exception e) {
			log.error("人人利用户["+phone+"]注册失败："+JSON.toJSONString(Utils.getParameterMap(req)), e);
		}
		log.info("人人利注册返回数据："+JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="/queryInvest",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryInvest(HttpServletRequest req, HttpServletResponse response){
		log.info("人人利查询请求数据："+JSON.toJSONString(Utils.getParameterMap(req)));
		JSONObject json = Utils.getParameterMap(req);
		Map<String, Object> result = new HashMap<String, Object>();
		String CustId = json.getString("Cust_id");//商户号
		String CustKey = StringUtils.isBlank(json.getString("Cust_key"))?"":json.getString("Cust_key");//用户ID（uid）
		String Order_no = StringUtils.isBlank(json.getString("Order_no"))?"":json.getString("Order_no");
		String sign = json.getString("Sign");//签名
		try {
			if(RrlBase.getInstance().validCustId(CustId)){//验证商户号
				if(RrlBase.getInstance().validSign(CustId, Order_no+CustKey, sign)){
					result.put("id", Order_no);
					result.put("uid", CustKey);
					if(StringUtils.isNotBlank(json.getString("Start_date"))){
						result.put("startDate", Utils.format(new Date(json.getLong("Start_date")),"yyyy-MM-dd HH:mm:ss"));
					}
					if(StringUtils.isNotBlank(json.getString("End_date"))){
						result.put("endDate", Utils.format(new Date(json.getLong("End_date")),"yyyy-MM-dd HH:mm:ss"));
					}
					List<Map<String, Object>> list = drProductInvestService.rrlQueryInvestList(result);
					result.clear();
					result.put("Code", "101");
					result.put("Data", list);
				}else{
					result.clear();
					result.put("Code", "108");
					result.put("Tip", "错误提示");
					result.put("Data", "签名校验失败");
				}
			}else{
				result.clear();
				result.put("Code", "");
				result.put("Tip", "错误提示");
				result.put("Data", "商户号校验失败");
			}
		} catch (Exception e) {
			log.error("人人利查询投资记录失败"+JSON.toJSONString(Utils.getParameterMap(req)),e);
			result.clear();
			result.put("Code", "");
			result.put("Tip", "错误提示");
			result.put("Data", "查询失败，请稍后再试");
		}
		log.info("人人利查询返回数据："+JSON.toJSONString(result));
		return JSON.toJSONString(result);
		
	}

}
