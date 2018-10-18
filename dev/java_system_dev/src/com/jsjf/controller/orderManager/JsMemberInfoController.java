package com.jsjf.controller.orderManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.member.JsMemberInfoService;
import com.jsjf.service.product.JsProductPrizeLogService;


@Controller
@RequestMapping("/jsMemberInfo")
public class JsMemberInfoController{
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private JsProductPrizeLogService jsProductPrizeLogService;
	
	/**
	 * 跳转到订单管理页面
	 */
	@RequestMapping("/toJsMemberInfoList")
	public String toJsMemberInfoList(Map<String,Object> model) {
		return "system/orderManager/jsMemberInfoList";
	}
	/**
	 * 显示列表
	 */
	@RequestMapping("/JsMemberInfoList")
	@ResponseBody
	public PageInfo JsMemberInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		
		
		
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsProductPrizeLogService.getJsproductPrizeLogOrderList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/getInvestAmountSum")
	@ResponseBody
	public BigDecimal getInvestAmountSum(@RequestParam Map<String,Object> param){
		
		
		
		BigDecimal result = jsProductPrizeLogService.getInvestAmountSum(param);
		return result;
	}
	/**
	 * 导出
	 * @param drProductInfo
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportJsMemberInfo")
	@ResponseBody
	public ModelAndView exportJsMemberInfo(@RequestParam Map<String,Object> param,Integer page,Integer rows)throws Exception{
		param.put("type",0);
		List<Map<String, Object>> rowsList = jsProductPrizeLogService.getJsproductPrizeLogOrderListForExl(param);
		String[] title = new String[]{"客户姓名","账户","推荐码","所属部门","电销人员","订单金额","产品编号","项目周期","项目预期年化率","订单日期","收货人电话","收货人姓名","奖品名称","收货地址","标的名称"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("name")==null?"":map.get("name"));//用户姓名
			lc.add(map.get("mobilePhone")==null?"":map.get("mobilePhone"));//账号
			lc.add(map.get("recommCodes")==null?"":map.get("recommCodes"));//推荐码
			lc.add(map.get("allotName")==null?"":map.get("allotName"));//所属部门
			lc.add(map.get("userName")==null?"":map.get("userName"));//所属电销
			lc.add(map.get("investAmount")==null?"":map.get("investAmount"));//投资金额
			lc.add(map.get("code")==null?"":map.get("code"));//产品编号
			lc.add(map.get("deadline")==null?"":map.get("deadline"));//项目周期
			lc.add(map.get("rate")==null?"":map.get("rate"));//年化率
			lc.add(map.get("addTime")==null?"":map.get("addTime"));//上架时间
			lc.add(map.get("phone")==null?"":map.get("phone"));//联系电话
			lc.add(map.get("realname")==null?"":map.get("realname"));//收货人姓名
			lc.add(map.get("prizeName")==null?"":map.get("prizeName"));//奖品名称
			lc.add(map.get("address")==null?"":map.get("address"));//收货地址
			lc.add(map.get("fullName")==null?"":map.get("fullName"));//标的名称
			tableList.add(lc);
		}
		BigDecimal result = jsProductPrizeLogService.getInvestAmountSum(param);
		lc =new ArrayList<Object>();
		lc.add("订单金额总计");
		lc.add(result);
		tableList.add(lc);
		param.clear();
		param.put("excelName", "js_product_info_prize_log_order"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
}