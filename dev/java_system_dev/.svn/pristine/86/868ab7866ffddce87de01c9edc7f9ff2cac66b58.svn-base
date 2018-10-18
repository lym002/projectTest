package com.jsjf.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.service.product.JsProductPrizeLogService;


@Controller
@RequestMapping("/productPrizeLog")
public class JsProductPrizeLogController{
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private JsProductPrizeLogService jsProductPrizeLogService;
	/**
	 * 跳转到放款管理页面
	 */
	@RequestMapping("/toJsProductPrizeList")
	public String toDrProductLoanList(Map<String,Object> model) {
		return "system/product/jsProductPrizeLogList";
	}
	/**
	 * 显示列表
	 */
	@RequestMapping("/jsProductPrizeLogList")
	@ResponseBody
	public PageInfo jsProductPrizeLogList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsProductPrizeLogService.getJsProductPrizeLogList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 导出
	 * @param drProductInfo
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportJsProductPrizeLog")
	@ResponseBody
	public ModelAndView exportJsMemberInfo(@RequestParam Map<String,Object> param,Integer page,Integer rows)throws Exception{
		List<Map<String, Object>> rowsList = jsProductPrizeLogService.getJsProductPrizeLogListForExl(param);
		String[] title = new String[]{"用户姓名","手机号码","推荐码","预约时间"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));//用户姓名
			lc.add(map.get("mobilePhone"));//手机
			lc.add(map.get("recommCodes"));//推荐码
			lc.add(map.get("addTime")==null?"":map.get("addTime"));//预约时间
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "js_productPrize_log_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	/**
	 * 统计
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/prizeLogCount")
	@ResponseBody
	public Map<String,Integer> prizeLogCount(@RequestParam Map<String,Object> param) {
		param.put("type", 1);
		int count = jsProductPrizeLogService.getJsProductPrizeLogCountByPPid(param);
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		resultMap.put("count", count);
		return resultMap;
	}
}