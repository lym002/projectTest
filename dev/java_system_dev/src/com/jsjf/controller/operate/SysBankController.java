package com.jsjf.controller.operate;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.system.SysBankService;

@Controller
@RequestMapping ( "/bank" )
public class SysBankController {
	
	@Autowired
	SysBankService sysBankService;
	
	/**
	 * 跳转到银行信息列表
	 * @return
	 */
	@RequestMapping("/toSysBankList")
	public String toSysBankList(Map<String,Object> model) {
		return "system/operate/sysBankList";
	}
	
	/**
	 * 显示银行信息列表数据
	 */
	@RequestMapping(value= "/sysBankList")
	@ResponseBody
	public PageInfo sysBankList(SysBank sysBank,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = sysBankService.getSysBankList(sysBank, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 根据ID查询银行信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/querySysBank")
	@ResponseBody
	public BaseResult querySysBank(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		SysBank sysBank = sysBankService.getSysBankByid(id);
		map.put("sysBank",sysBank);
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 根据ID查询银行信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/updateSysBank")
	@ResponseBody
	public BaseResult updateSysBank(SysBank sysBank,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		try {
			if(Utils.isObjectEmpty(usersVo)){
				result.setSuccess(false);
				result.setErrorMsg("请重新登录！");
			}
			sysBank.setUpdUser(usersVo.getUserKy().intValue());
			sysBankService.updateSysBank(sysBank);
			result.setSuccess(true);
			result.setMsg("操作成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("操作失败！");
			e.getStackTrace();
		}
		return result;
	}
}
