package com.jsjf.controller.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.JsActivityLuckyMoney;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.activity.JsActivityLuckyMoneyService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/jsActivityLuckMoney")
public class JsActivityLuckMoneyController {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private JsActivityLuckyMoneyService jsActivityLuckyMoneyService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	
	@RequestMapping(value="/getJsActivityLuckyMoney",method = RequestMethod.POST)
	@ResponseBody
	public String getJsActivityLuckyMoney(HttpServletRequest req,Integer shaerUid,String mobilePhone){
		BaseResult br = new BaseResult();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (Utils.isObjectEmpty(shaerUid)) {
				br.setErrorMsg("shaerUid不能为空");
				br.setSuccess(false);
				return JSON.toJSONString(br);
			}
			DrMember m = drMemberService.selectByPrimaryKey(shaerUid);
			if (Utils.isObjectEmpty(m)) {
				br.setErrorMsg(shaerUid + "的用户不存在");
				br.setSuccess(false);
				br.setErrorCode("10003");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(mobilePhone)){
				br.setErrorMsg("领取者手机号不能为空");
				br.setSuccess(false);
				br.setErrorCode("10002");
				return JSON.toJSONString(br);
			}
			List<JsActivityLuckyMoney> ownlist = jsActivityLuckyMoneyService.selectByshaerUid(shaerUid);
			
			if(ownlist.size() == 0){
				br.setErrorMsg("当日用户无分享记录");
				br.setSuccess(false);
				br.setErrorCode("10001");
				return JSON.toJSONString(br);
			}else{
				JsActivityLuckyMoney activityLuckyMoney = ownlist.get(0);
				if(Utils.getQuot(Utils.format(activityLuckyMoney.getAddTime(), "yyyy-MM-dd"),Utils.format(new Date(), "yyyy-MM-dd"))==0){
					if(activityLuckyMoney.getShareCount() == 0){
						map.put("isOverCount", true);//true压岁钱已抢完
					}else{
						param.clear();
						param.put("shaerUid", shaerUid);
						param.put("time", new Date());
						param.put("uid", -1);
						List<JsActivityLuckyMoney> unlist = jsActivityLuckyMoneyService.selectJsActivityLuckyMoneyByMap(param);//未使用的list
						map.put("luckMoney", drMemberService.isExistsMobilphone(mobilePhone)?ownlist.get(0).getAmount():unlist.get(0).getAmount());//压岁钱金额
					}
					param.clear();
					param.put("shaerUid", shaerUid);
					param.put("order", "updateTime desc");
					param.put("time", new Date());
					param.put("uid", -2);//-2的时候，uid is not null
					List<JsActivityLuckyMoney> list = jsActivityLuckyMoneyService.selectJsActivityLuckyMoneyByMap(param);
					map.put("luckMoneyList", list);//压岁钱领取列表
				}else{
					param.clear();
					param.put("shaerUid", shaerUid);
					param.put("order", "updateTime desc");
					param.put("time", activityLuckyMoney.getAddTime());
					param.put("uid", -2);//-2的时候，uid is not null
					map.put("isOverdue", true);//true 压岁钱已过期 
					List<JsActivityLuckyMoney> list = jsActivityLuckyMoneyService.selectJsActivityLuckyMoneyByMap(param);
					map.put("luckMoneyList", list);//压岁钱领取列表
				}
				map.put("mobilePhone", drMemberService.isExistsMobilphone(mobilePhone)?m.getMobilephone():mobilePhone);//显示手机号
			}
			br.setSuccess(true);
			br.setMap(map);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
			return JSON.toJSONString(br);
		}
		
		return JSON.toJSONString(br);
	}
	
	/**
	 * 确认领钱
	 */
	@RequestMapping(value="/getshaerUserName",method=RequestMethod.POST)
	@ResponseBody
	public String getshaerUserName(HttpServletRequest req,Integer shaerUid){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			DrMember m =drMemberService.selectByPrimaryKey(shaerUid);
			if(Utils.isObjectEmpty(m)){
				br.setErrorMsg(shaerUid+"用户找不到");
				br.setSuccess(false);
				return JSON.toJSONString(br);
			}
			String realName = m.getRealName();
			StringBuffer sb = new StringBuffer();
			sb.append(realName.substring(0, 1));
			for (int i = 0; i < realName.length()-1; i++) {
				sb.append("*");
			}
			map.put("userName", sb.toString());
			br.setSuccess(true);
			br.setMap(map);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}
	
}
