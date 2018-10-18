package com.jsjf.controller.product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.product.DrProductInvestService;

@Controller
@RequestMapping("/invest")
public class DrProductInvestController{
	
	@Autowired
	private DrProductInvestService drProductInvestService;
	
	/**
	 * 跳转到投资记录列表页面
	 * @throws IOException 
	 */
	@RequestMapping("/toDrProductInvestList")
	public String toDrProductInvestList(Map<String,Object> model,
			@RequestParam(value="code",required=false)String code,
			@RequestParam(value="scode",required=false)String scode) throws IOException {
				
		model.put("scode", scode);
		model.put("code", code);
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/product/drProductInvestList";
	}
	
	
	/**
	 * 显示投资记录列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drProductInvestList")
	@ResponseBody
	public PageInfo drProductInvestList(DrProductInvest drProductInvest,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		
		
		
		BaseResult result = drProductInvestService.getDrProductInvestList(drProductInvest, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	
	/**
	 * 标的池跳转到产品列表页面
	 */
	@RequestMapping("/toInvestList")
	public String toInvestList(Map<String,Object> model,
			@RequestParam(value="code",required=false)String code,
			@RequestParam(value="scode",required=false)String scode) {
		model.put("scode", scode);
		model.put("code", code);
		return "system/product/drInvestList";
	}
	
	
	/**
	 * 显示投资列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drInvestList")
	@ResponseBody
	public PageInfo drInvestList(DrProductInvest drProductInvest,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInvestService.getDrProductInvestList(drProductInvest, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	
	
	
	/**
	 * 表单提交日期绑定
	 * @param binder
	 */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  
	
}