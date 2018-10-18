package com.jsjf.controller.operate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.operate.DrUserAnalysis;
import com.jsjf.service.operate.DrUserAnalysisService;

@Controller
@RequestMapping("/userAnalysis")
public class DrUserAnalysisController {

	@Autowired
	public DrUserAnalysisService drUserAnalysisService;
	
	/**
	 * 跳转到用户分析列表
	 * @return
	 */
	@RequestMapping("/toUserAnalysisList")
	public String toUserAnalysisList() {
		return "system/operate/drUserAnalysisList";
	}
	
	/**
	 * 拿到用户分析列表数据
	 * @param DrUserAnalysis
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/userAnalysisList")
	@ResponseBody
	public PageInfo userAnalysisList(DrUserAnalysis drUserAnalysis, Integer page,
			Integer rows) {
		if (page == null) {
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if (rows == null) {
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = drUserAnalysisService.getDrUserAnalysisList(drUserAnalysis, pi);
		return (PageInfo) result.getMap().get("page");
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
