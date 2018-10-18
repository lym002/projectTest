package com.jsjf.controller.orderManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;

@Controller
@RequestMapping("/investOrder")
public class InvestOrderController {
	
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrActivityParameterService drActivityParameterService;
	@Autowired
	private DrMemberService drMemberService;
	
	@RequestMapping("/toInvestOrder")
	public String toInvestOrder(Map<String,Object> model) throws IOException{
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/orderManager/investOrderList";
	}
	
	//投资订单列表查询
	@RequestMapping(value= "/investOrderList")
	@ResponseBody
	public PageInfo investOrderList(
			DrProductInvest drProductInvest,
			@RequestParam(value="cids", required=false )Integer[] cids, 
			Integer page,Integer rows,Integer deadline) {
		
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		param.put("fullName", drProductInvest.getFullName());
		/*param.put("realname", drProductInvest.getRealname());*/
		param.put("mobilephone", drProductInvest.getMobilephone());
		param.put("startDate", drProductInvest.getStartDate());
		param.put("endDate", drProductInvest.getEndDate());
		param.put("cids", cids);
		param.put("startShouldTime",drProductInvest.getStartShouldTime());
		param.put("endShouldTime",drProductInvest.getEndShouldTime());
		param.put("deadline",deadline);
		param.put("startRegDate", drProductInvest.getStartRegDate());
		param.put("endRegDate", drProductInvest.getEndRegDate());
		if(drProductInvest.getRepayType()!=null){
			param.put("repayType", drProductInvest.getRepayType());
		}
		PageInfo pi = new PageInfo(page,rows);
		pi = drProductInvestService.selectInvestMemberInfoListByParam(param, pi);
		return pi;
	}
	
	@RequestMapping("/exportInvestOrderInfo")
	public ModelAndView exportInvestOrderInfo(DrProductInvest drProductInvest,
			@RequestParam(value="cids", required=false )Integer[] cids, 
			Integer page,Integer rows) throws Exception{
		
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fullName", java.net.URLDecoder.decode(drProductInvest.getFullName(),"utf-8"));
		/*param.put("realname", java.net.URLDecoder.decode(drProductInvest.getRealname(),"utf-8"));*/
		param.put("mobilephone", drProductInvest.getMobilephone());
		param.put("startDate", drProductInvest.getStartDate());
		param.put("endDate", drProductInvest.getEndDate());
		param.put("cids", cids);
		param.put("startShouldTime",drProductInvest.getStartShouldTime());
		param.put("endShouldTime",drProductInvest.getEndShouldTime());
		param.put("startRegDate", drProductInvest.getStartRegDate());
		param.put("endRegDate", drProductInvest.getEndRegDate());
		param.put("deadline", drProductInvest.getDeadline());
		param.put("repayType", drProductInvest.getRepayType());
		
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		pi = drProductInvestService.selectInvestMemberInfoListByParam(param, pi);
		List<Map<String, Object>> rowsList = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","推荐号","推荐人姓名","推荐人号码","产品名称","项目周期(天)","订单金额","预计收益","还款方式","红包返现",
				"加息利率","翻倍倍数","订单时间","计息日期","还款日期","投资终端","投资状态","活动兑换日期","活动金额","活动类型","活动状态","专属理财师","注册日期","注册渠道"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("recommCodes"));
			lc.add(map.get("recomRealName")==null?"":map.get("recomRealName"));
			lc.add(map.get("recomMobilePhone")==null?"":map.get("recomMobilePhone"));
			lc.add(map.get("fullName"));
			lc.add(map.get("deadline"));
			lc.add(map.get("amount"));
			lc.add(map.get("interest"));
			lc.add(map.get("repayTypeName"));
			lc.add(map.get("faAmount1")==null?"":map.get("faAmount1"));
			lc.add(map.get("raisedRates")==null?"":map.get("raisedRates"));
			lc.add(map.get("multiple")==null?"":map.get("multiple"));
			lc.add(map.get("investTime"));
			lc.add(map.get("resultDate")==null?"":map.get("resultDate"));
			lc.add(map.get("shouldTime")==null?"":map.get("shouldTime"));
			lc.add(map.get("joinType"));
			lc.add(map.get("statusName"));
			lc.add(map.get("usedTime")==null?"":map.get("usedTime"));
			lc.add(map.get("faAmount2")==null?"":map.get("faAmount2"));
			lc.add(map.get("typeName")==null?"":map.get("typeName"));
			lc.add(map.get("faStatus")==null?"":map.get("faStatus"));
			lc.add(map.get("userKey")==null?"":map.get("userKey"));
			lc.add(map.get("regdate"));
			lc.add(map.get("chanelName"));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "invest_order_record_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
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
    //TODO
    /**
     * 跳转优惠投资订单页面
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/toActivityInvestOrder")
	public String toActivityInvestOrder(Map<String,Object> model,Integer id) throws Exception{
    	try {
			model.put("id", id);
			model.put("drActivityParameter", drActivityParameterService.getActivityParameterById(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/orderManager/activityInvestOrderList";
	}
  //优惠投资订单列表查询
  @RequestMapping(value= "/activityInvestOrderList")
  	@ResponseBody
  	public PageInfo activityInvestOrderList(Integer id,Integer page,Integer rows) {
  		Map<String, Object> param = new HashMap<String, Object>();
  		if(page == null){
  			page = PageInfo.DEFAULT_PAGE_ON;
  		}
  		if(rows == null){
  			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
  		}
  		PageInfo pi = new PageInfo(page,rows);
  		param.put("id",id);
  		pi = drProductInvestService.selectActivityInvestListByParam(param, pi);
  		return pi;
  	}
  	/**
  	 * 优惠活动对应的投资订单导出
  	 */
    @RequestMapping("/exporActivitytInvestOrderInfo")
	public ModelAndView exporActivitytInvestOrderInfo(Integer id,Integer page,Integer rows) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id",id);
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		pi = drProductInvestService.selectActivityInvestListByParam(param, pi);
		List<Map<String, Object>> rowsList = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","产品名称","项目周期(天)","订单金额",
				"订单时间","注册日期","注册渠道"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("fullName"));
			lc.add(map.get("deadline"));
			lc.add(map.get("amount"));
			lc.add(map.get("investTime"));
			lc.add(map.get("regdate"));
			lc.add(map.get("chanelName"));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "invest_order_record_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
    
    /**
     * 体验金投资订单管理
     */
	@RequestMapping("/toExperienceInvestOrder")
	public String toExperienceInvestOrder(){
		return "system/orderManager/experienceinvestOrderList";
	}
	
	//体验标投资订单列表查询
	@RequestMapping(value= "/experienceInvestOrderList")
	@ResponseBody
	public PageInfo experienceInvestOrderList(
			DrProductInvest drProductInvest,
			@RequestParam(value="cids", required=false )Integer[] cids, 
			Integer page,Integer rows) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		param.put("fullName", drProductInvest.getFullName());
		param.put("realname", drProductInvest.getRealname());
		param.put("mobilephone", drProductInvest.getMobilephone());
		param.put("startDate", drProductInvest.getStartDate());
		param.put("endDate", drProductInvest.getEndDate());
		param.put("cids", cids);
		param.put("startShouldTime",drProductInvest.getStartShouldTime());
		param.put("endShouldTime",drProductInvest.getEndShouldTime());
		PageInfo pi = new PageInfo(page,rows);
		pi = drProductInvestService.selectExperienceInvestMemberInfoListByParam(param, pi);
		return pi;
	}
	
	//体验标投资订单列表导出
	@RequestMapping("/exportExperienceInvestOrderInfo")
	public ModelAndView exportExperienceInvestOrderInfo(DrProductInvest drProductInvest,
			@RequestParam(value="cids", required=false )Integer[] cids, 
			Integer page,Integer rows) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fullName", java.net.URLDecoder.decode(drProductInvest.getFullName(),"utf-8"));
		param.put("realname", java.net.URLDecoder.decode(drProductInvest.getRealname(),"utf-8"));
		param.put("mobilephone", drProductInvest.getMobilephone());
		param.put("startDate", drProductInvest.getStartDate());
		param.put("endDate", drProductInvest.getEndDate());
		param.put("cids", cids);
		param.put("startShouldTime",drProductInvest.getStartShouldTime());
		param.put("endShouldTime",drProductInvest.getEndShouldTime());
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		pi = drProductInvestService.selectExperienceInvestMemberInfoListByParam(param, pi);
		List<Map<String, Object>> rowsList = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","推荐人姓名","推荐人号码","产品名称","项目周期(天)","体验金金额","预计收益",
				"订单时间","投资终端","投资状态","注册日期","注册渠道"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("recomRealName")==null?"":map.get("recomRealName"));
			lc.add(map.get("recomMobilePhone")==null?"":map.get("recomMobilePhone"));
			lc.add(map.get("fullName"));
			lc.add(map.get("deadline"));
			lc.add(map.get("amount"));
			lc.add(map.get("interest"));
			lc.add(map.get("investTime"));
			lc.add(map.get("joinType"));
			lc.add(map.get("statusName"));
			lc.add(map.get("regdate"));
			lc.add(map.get("chanelName"));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "experienceinvest_order_record_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
    
	/*
	 * 复投订单查询
	 */
	@RequestMapping("/toInvestListForFuTou")
	public String toInvestListForFuTou(){
		return "system/orderManager/investOrderFuTouList";
	}
	
	@RequestMapping("/getInvestListForFuTou")
	@ResponseBody
	public PageInfo getInvestListForFuTou(DrProductInvest drProductInvest,Integer page,Integer rows){
		
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		/*map.put("realname", drProductInvest.getRealname());*/
		map.put("mobilePhone", drProductInvest.getMobilephone());
		map.put("startRegDate", drProductInvest.getStartRegDate());
		map.put("endRegDate", drProductInvest.getEndRegDate());
		map.put("cids", drProductInvest.getCids());
		map.put("count_allStart",drProductInvest.getCount_allStart());
		map.put("count_allEnd", drProductInvest.getCount_allEnd());
		PageInfo pi = new PageInfo(page,rows);
		pi = drProductInvestService.getInvestListForFuTou(map, pi);
		return pi;
	}
	
	/*
	 * 用户投资列表
	 */
	@RequestMapping("/toProductInvestListByUid")
	public String toProductInvestListByUid(Integer uid,Map<String,Object> model){
		DrMember m = drMemberService.selectByPrimaryKey(uid);
		model.put("uid", uid);
		model.put("m", m);
		return "system/orderManager/productInvestOrderList";
	}
	
	@RequestMapping("/getProductInvestListByUid")
	@ResponseBody
	public PageInfo getProductInvestListByUid(DrProductInvest drProductInvest,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		map.put("fullName", drProductInvest.getFullName());
		map.put("status", drProductInvest.getStatus());
		map.put("startDate", drProductInvest.getStartDate());
		map.put("endDate", drProductInvest.getEndDate());
		map.put("deadline", drProductInvest.getDeadline());
		map.put("typeName", drProductInvest.getTypeName());
		map.put("uid", drProductInvest.getUid());
		PageInfo pi = new PageInfo(page,rows);
		pi = drProductInvestService.getProductInvestListByUid(map, pi);
		return pi;
	}
	
	@RequestMapping("/exportInvestListForFuTou")
	@ResponseBody
	public ModelAndView exportInvestOrderInfo(DrProductInvest drProductInvest,
			Integer page,Integer rows) throws Exception{
		
		
		
		Map<String,Object> param  = new HashMap<String,Object>();
		/*param.put("realname", java.net.URLDecoder.decode(drProductInvest.getRealname(),"utf-8"));*/
		param.put("mobilePhone", drProductInvest.getMobilephone());
		param.put("startRegDate", drProductInvest.getStartRegDate());
		param.put("endRegDate", drProductInvest.getEndRegDate());
		param.put("cids", drProductInvest.getCids());
		param.put("count_allStart",drProductInvest.getCount_allStart());
		param.put("count_allEnd", drProductInvest.getCount_allEnd());
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		pi = drProductInvestService.getInvestListForFuTou(param, pi);
		List<Map<String, Object>> rowsList = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","注册日期","注册渠道","30-40","60","90/150","180","投资总次数","投资总金额"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("regdate"));
			lc.add(map.get("chanelName"));
			lc.add(map.get("count_30"));
			lc.add(map.get("count_60"));
			lc.add(map.get("count_90"));
			lc.add(map.get("count_180"));
			lc.add(map.get("count_all"));
			lc.add(map.get("investAmountSUM"));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "invest_order_FuTou_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
}
