package com.jsjf.controller.finance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrCarryParamService;

@Controller
@RequestMapping("/carryParam")
public class DrCarryParamController{
	
	@Autowired
	private DrCarryParamService drCarryParamService;
	
	/**
	 * 跳转到提现设置页面
	 */
	@RequestMapping("/toDrCarryParamList")
	public String toDrCarryParamList() {
		return "system/finance/drCarryParamList";
	}
	
	/**
	 * 显示提现设置列表数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drCarryParamList")
	@ResponseBody
	public PageInfo drCarryParamList(Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drCarryParamService.getDrCarryParamList(pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 根据ID查询提现设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/queryDrCarryParam")
	@ResponseBody
	public BaseResult queryDrCarryParam(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrCarryParam drCarryParam = drCarryParamService.getDrCarryParamByid(id);
		map.put("drCarryParam",drCarryParam);
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 修改提现设置信息
	 * @param DrCarryParam
	 * @return BaseResult
	 */
	@RequestMapping(value= "/updateDrCarryParam")
	@ResponseBody
	public BaseResult updateDrCarryParam(DrCarryParam drCarryParam,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			drCarryParamService.updateDrCarryParam(drCarryParam, usersVo);
			result.setSuccess(true);
			result.setMsg("修改成功！");
		}catch(Exception e){
			result.setSuccess(false);
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		return result;
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