package com.jsjf.controller.account;

import java.util.HashMap;
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
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
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
	 * 消息列表
	 * @param req
	 * @param pi
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getMessage", method = RequestMethod.POST)
	@ResponseBody
	public String getMessage(HttpServletRequest req, PageInfo pi,Integer uid , Integer type){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("uid", uid);
			map.put("type", type==0?null:type);//0为查询查询全部消息
			pi = drMemberMsgService.getDrMemberParam(pi, map);
			drMemberMsgService.updateMsgToRead(type, uid);
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
	/**
	 * 修改未读成已读
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateUnReadMsg",method = RequestMethod.POST)
	@ResponseBody
	public String updateUnReadMsg(HttpServletRequest req,Integer[] ids, Integer uid){
		BaseResult br = new BaseResult();
		try {
			drMemberMsgService.updateMsgToReadByIds(ids,uid);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("修改未读消息失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 删除选中消息
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delMsg",method = RequestMethod.POST)
	@ResponseBody
	public String delMsg(HttpServletRequest req,Integer[] ids, Integer uid){
		BaseResult br = new BaseResult();
		try {
			if(Utils.isObjectEmpty(ids)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			drMemberMsgService.updateMsgToDelByIds(ids,uid);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("删除消息失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	

}