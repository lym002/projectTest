package com.jsjf.controller.filing;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.filing.DrFiling;
import com.jsjf.service.filing.FilingService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/filing")
public class DrFilingController {
	@Autowired
	private FilingService filingService;

	/**
	 * 跳转至交易报备页面
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/dealFiling")
	public String toDealFiling(Map<String, Object> model, HttpServletRequest req) {
		return "system/filing/dealFilingList";
	}

	/**
	 * 交易报备查询
	 * 
	 * @param drFiling
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/dealFilingList")
	@ResponseBody
	public PageInfo investFilingList(DrFiling drFiling, Integer page, Integer rows) {
		if (page == null) {
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if (rows == null) {
			rows = PageInfo.DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = filingService.getInvestFilingList(drFiling, pi);
		return (PageInfo) result.getMap().get("page");
	}

	/**
	 * 单个或多个补报操作
	 */

	@RequestMapping(value = "/batchDealFilingAudit")
	@ResponseBody
	public BaseResult batchDealFilingAudit(String carryListData, String businessType,String failureCause,String repairTpe) {
		JSONArray jsonArray = JSONArray.fromObject(carryListData);
		BaseResult b = new BaseResult();
		if ("0".equals(businessType) && (!jsonArray.isEmpty())) {
			filingService.getInvestFiling(jsonArray,repairTpe);
		}else if("1".equals(businessType) && (!jsonArray.isEmpty())){
			filingService.getFullCreditFiling(jsonArray,repairTpe);
		}else if("3".equals(businessType) && (!jsonArray.isEmpty())){
			filingService.getInvestReturnedMoneyFiling(jsonArray,repairTpe);
		}else{
			b.setSuccess(false);
			return b;
		}
		b.setSuccess(true);
		return b;
	}

}
