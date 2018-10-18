package com.jsjf.controller.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberCpsFavourableRuleService;

@Controller
@RequestMapping("/cps")
public class CpsFavourableRuleController {
	private Logger log = Logger.getLogger(CpsFavourableRuleController.class);
	
	@Autowired
	private DrMemberCpsFavourableRuleService drMemberCpsFavourableRuleService;
	
	/**
	 * 进入回款复投红包发送页面
	 * @return
	 */
	@RequestMapping(value="cpsFavourableRule")
	public String cpsFavourableRule(){
		return "system/activity/cpsFavourableRuleList";
	}
	
	@RequestMapping(value="cpsFavourableRuleList")
	@ResponseBody
	public PageInfo cpsFavourableRuleList(DrMemberCpsFavourableRule rule, Integer rows, Integer page){
		PageInfo pi = new PageInfo(page, rows);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isFirst", rule.getIsFirst());
		param.put("isCps", rule.getIsCps());
		param.put("status", rule.getStatus());
		param.put("offset", pi.getPageInfo().getOffset());
		param.put("limit", pi.getPageInfo().getLimit());
		List<DrMemberCpsFavourableRule> list = drMemberCpsFavourableRuleService.selectByParam(param);
		Integer total = drMemberCpsFavourableRuleService.selectCountByParam(param);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}
	
	@RequestMapping(value="addMemberCpsRule")
	@ResponseBody
	public BaseResult addMemberCpsRule(DrMemberCpsFavourableRule rule, HttpServletRequest req){
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		rule.setStatus(1);
		rule.setAddTime(new Date());
		rule.setAddUserKey(Integer.parseInt(user.getUserKy().toString()));
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("str", rule.getMinAmount()+" BETWEEN minAmount and maxAmount or "+rule.getMaxAmount()+" BETWEEN minAmount and maxAmount");
			param.put("isFirst", rule.getIsFirst());
			param.put("isCps", rule.getIsCps());
			param.put("status", 1);
			Integer total = drMemberCpsFavourableRuleService.selectCountByParam(param);
			if(total>0){
				br.setSuccess(false);
				br.setErrorMsg("投资金额区间有重复");
				return br;
			}
			drMemberCpsFavourableRuleService.insert(rule);
			br.setSuccess(true);
			br.setMsg("添加成功");
		} catch (Exception e) {
			log.error("添加促复投红包发送规则失败", e);
			br.setErrorMsg("添加失败");
		}
		return br;
		
	}
	
	/**
	 * 修改红包发放规则
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateStatus")
	@ResponseBody
	public BaseResult updateStatus(HttpServletRequest req,Integer id){
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		DrMemberCpsFavourableRule cpsRule = new DrMemberCpsFavourableRule();
		cpsRule.setId(id);
		cpsRule.setStatus(0);
		cpsRule.setUpdUserKey(Integer.parseInt(user.getUserKy().toString()));
		cpsRule.setUpdTime(new Date());
		drMemberCpsFavourableRuleService.updateBySelective(cpsRule);
		br.setSuccess(true);
		br.setMsg("修改成功");
		return br;
	}

}
