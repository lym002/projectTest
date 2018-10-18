package com.jsjf.controller.system;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.system.SysMessageLog;


@Controller
@RequestMapping("/sysmessagelog")
public class SysMessageLogController{
	
	@Autowired
	com.jsjf.service.system.SysMessageLogService  sysMessageLogService;
	
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
    
    /**
	 * @Description 显示注册日志列表
	 */
	@RequestMapping(value= "/logList")
	@ResponseBody
	public PageInfo logList(Integer page,Integer rows,SysMessageLog log) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		//log.setType(1);
		BaseResult result = sysMessageLogService.selectByType(log, pi);
		
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * @Description 跳转验证码查询页面
	 * @param 
	 * @return
	 */
	@RequestMapping("/toSysLog")
	public String toSysLog(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("smsType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/system/sys/sysMessageLog/selMessageLog";
	}
	
}
