package com.jsjf.controller.account.funds;

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
import com.jsjf.service.member.DrMemberFundsRecordService;

/**
 * 我的资产-资产记录
 * @author DELL
 *
 */
@RequestMapping("/assetRecord")
@Controller
public class AssetRecordController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrMemberFundsRecordService drMemberFundsRecordService;
	
	/**
	 * 领取奖金
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping("/getTheRewards")
	@ResponseBody
	public String getTheRewards(HttpServletRequest req,@RequestBody Map<String, Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = drMemberFundsRecordService.getTheRewards(m, param);
		return JSON.toJSONString(br);
	}
	
		
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest req,@RequestBody Map<String, Object> param, PageInfo pi){
		BaseResult br = new BaseResult();
		try {
			DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			param.put("uid", m.getUid());
			Utils.getObjectFromMap(pi, param);
			br = drMemberFundsRecordService.selectMemberFundsRecordByParam(param, pi);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("资产记录查询失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
		
	}
	
}
