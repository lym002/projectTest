package com.jsjf.controller.finance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.jsjf.model.system.SysUsersVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.service.member.DrMemberFundsLogService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/memberFundsLog")
public class DrMemberFundsLogController{
	@Autowired
	private DrMemberFundsLogService drMemberFundsLogService;
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 跳转到客户收支记录列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMemberFundsLogList")
	public String toMemberCarryList(Map<String,Object> model,String mobilePhone,String recommCodes) {
		try {
			model.put("memberFundsType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("fundType")));
			model.put("mobilePhone",  mobilePhone);
			model.put("recommCodes",  recommCodes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/finance/drMemberFundsLogList";
	}
	
	/**
	 * 显示客户收支记录列表数据
	 * @param drMemberFundsLog
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/memberFundsLogList")
	@ResponseBody
	public PageInfo memberFundsLogList(HttpServletRequest request, DrMemberFundsLog drMemberFundsLog, Integer page, Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		
		BaseResult result = drMemberFundsLogService.getMemberFundsLogList(drMemberFundsLog, pi);
		SysUsersVo vo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		log.info("系统审计：查询客户收支记录列表——【"+vo.getName()+"】 rows："+rows+"   pages：" + page);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 统计收入支出
	 * @param drMemberFundsLog
	 * @return
	 */
	@RequestMapping(value= "/memberFundsLogSum")
	@ResponseBody
	public Map<String,Object> memberFundsLogSum(DrMemberFundsLog drMemberFundsLog) {
		
		
		
		return drMemberFundsLogService.getDrMemberFundsLogSum(drMemberFundsLog);
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
