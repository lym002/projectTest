package com.jsjf.controller.account;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.service.member.DrMemberMsgService;

/**
 * 消息中心
 * @author
 *
 */
@RequestMapping("/messageCenter")
@Controller
public class MessageCenterController {
	private Logger log = Logger.getLogger(MessageCenterController.class);
	@Autowired
	private DrMemberMsgService drMemberMsgService;
	
	/**
	 * 获取各类消息未读条数
	 * @param req
	 * @return
	 */
	@RequestMapping("/myMessage")
	@ResponseBody
	public String myMessage(HttpServletRequest req){
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("uid",member.getUid());
			map.put("isRead", 0);
			//系统消息
			map.put("type", 1);
			int sysTotal = drMemberMsgService.getUnReadCountByMap(map);
			
			//活动消息未读数量
			map.put("type", 2);
			int actTotal = drMemberMsgService.getUnReadCountByMap(map);
			//交易未读
			map.put("type", 3);
			int fundsTotal = drMemberMsgService.getUnReadCountByMap(map);
			//查询后消息置为已读
			DrMemberMsg msg = new DrMemberMsg();
			msg.setRuId(member.getUid());
			drMemberMsgService.updateMsgToRead(msg);
			map.clear();
			map.put("sysTotal", sysTotal);
			map.put("actTotal", actTotal);
			map.put("fundsTotal", fundsTotal);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取未读消息失败",e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);		
	}
	
	/**
	 * 消息列表
	 * @param req
	 * @param pi
	 * @param param
	 * @return
	 */
	@RequestMapping("/getMessage")
	@ResponseBody
	public String getMessage(HttpServletRequest req, PageInfo pi,@RequestBody Map<String,Object> param){
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("uid", member.getUid());
			map.put("type", param.get("type"));
			Utils.getObjectFromMap(pi, param);
			pi = drMemberMsgService.getDrMemberParam(pi, map);
			map.clear();
			map.put("page", pi);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("消息列表读取失败",e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);		
	}
	
	@RequestMapping("/queryUnReadMsg")
	@ResponseBody
	public Map<String,Object> queryUnReadMsg(HttpServletRequest req){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("uid", m.getUid());
			map.put("isRead", 0);
			Integer count = drMemberMsgService.getUnReadCountByMap(map);
			map.put("count", count);
		} catch (Exception e) {
			log.error("读取未读消息失败", e);
		}
		return map;
	}
}