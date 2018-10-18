package com.jsjf.controller.integral;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.integral.InvestRulesBean;
import com.jsjf.service.integral.InvestRulesService;

@Controller
@RequestMapping("/investrules")
public class InvestRulesController {
	
	private Logger log = Logger.getLogger(InvestRulesController.class);
	
	@Autowired
	private InvestRulesService investRulesService;
	
	@RequestMapping("investrulesView")
	public String investrules(){
		return "/system/integral/investRulesList";
	}
	
	@RequestMapping("investrulesList")
	@ResponseBody
	public PageInfo investrulesList(HttpServletRequest req,InvestRulesBean investRulesBean,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			investRulesService.queryInvestrulesListList(info, investRulesBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping("addInvestRules")
	@ResponseBody
	public BaseResult addInvestRules(HttpServletRequest request,InvestRulesBean investRulesBean){
		BaseResult br = new BaseResult();
		br = investRulesService.addInvestRules(investRulesBean);
		return br;
	}
	
	@RequestMapping("/updateInvestRules")
	@ResponseBody
	public BaseResult updateInvestRules(InvestRulesBean investRulesBean){
		BaseResult br = new BaseResult();
		br = investRulesService.updateInvestRules(investRulesBean);
		return br;
	}
	
}
