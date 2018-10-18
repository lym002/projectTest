package com.jsjf.controller.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.JsNoviceContinueRecord;
import com.jsjf.service.product.JsNoviceContinueRecordService;

@Controller
@RequestMapping("/noviceContinueRecord")
public class JsNoviceContinueRecordController {
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private JsNoviceContinueRecordService jsNoviceContinueRecordService;

	/**
	 * 跳转到预约管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toJsNoviceContinueRecordList")
	public String toJsNoviceContinueRecordList(Map<String,Object> model){
		return "system/product/jsNoviceContinueRecordList";
	} 
	
	@RequestMapping("/jsNoviceContinueRecordList")
	@ResponseBody
	public PageInfo jsNoviceContinueRecordList(JsNoviceContinueRecord jsNoviceContinueRecord,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsNoviceContinueRecordService.getJsNoviceContinueRecord(jsNoviceContinueRecord, pi);
		return (PageInfo)result.getMap().get("page");
	} 
	
	@RequestMapping("/exportJsNCR")
	public ModelAndView exportJsNCR(JsNoviceContinueRecord jsNCR,Integer page,Integer rows) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		jsNCR.setRealName(jsNCR.getRealName()!=null?java.net.URLDecoder.decode(jsNCR.getRealName().trim(),"utf-8"):null);
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		BaseResult br = jsNoviceContinueRecordService.getJsNoviceContinueRecord(jsNCR, pi);
		pi = (PageInfo)br.getMap().get("page");
		List<JsNoviceContinueRecord> rowsList = (List<JsNoviceContinueRecord>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","订单日期","还款日期","续投产品","产品编号","续投金额","续投奖励","状态"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(JsNoviceContinueRecord jncr:rowsList){
			lc = new ArrayList<Object>();
			lc.add(jncr.getRealName());
			lc.add(jncr.getMobilePhone());
			lc.add(Utils.format(jncr.getAddTime(),"yyyy-MM-dd HH:mm:ss"));
			lc.add(Utils.format(jncr.getShouldTime(),"yyyy-MM-dd"));
			lc.add(jncr.getPeriod()+"天产品");
			lc.add(jncr.getpCode()==null?"-":jncr.getpCode());
			lc.add(jncr.getAmount());
			lc.add(jncr.getReward());
			lc.add(jncr.getStatus()==0?"待续投":"已续投");
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "novice_continue_record"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	/**
	 * 表单提交日期绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
