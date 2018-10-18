package com.jsjf.controller.article;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.model.article.SysProgram;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.article.SysProgramService;

@Controller
@RequestMapping("/program")
public class SysProgramController {
	
	@Autowired
	public SysProgramService sysProgramService;
	
	/**
	 * 跳转到栏目列表
	 * @return
	 */
	@RequestMapping("/toSysProgramList")
	public String toSysProgramList() {
		return "system/article/sysProgramList";
	}
	
	/**
	 * 拿到栏目列表数据
	 * @param sysProgram
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/sysProgramList")
	@ResponseBody
	public PageInfo sysProgramList(SysProgram sysProgram,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = sysProgramService.getSysProgramList(sysProgram, pi);
		
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 添加栏目
	 * @param request
	 * @param sysProgram
	 * @return
	 */
	@RequestMapping("/addSysProgram")
	@ResponseBody
	public BaseResult addSysProgram(HttpServletRequest request,SysProgram sysProgram){
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		result = sysProgramService.addSysProgram(sysProgram,usersVo);
		return result;
	}
	
	/**
	 * 修改栏目
	 * @param request
	 * @param sysProgram
	 * @return
	 */
	@RequestMapping("/updateSysProgram")
	@ResponseBody
	public BaseResult updateSysProgram(HttpServletRequest request,SysProgram sysProgram){
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		result = sysProgramService.updateSysProgram(sysProgram,usersVo);
		return result;
	}
	
	/**
	 * 删除栏目
	 * @param sysProgram
	 * @return
	 */
	@RequestMapping("/deleteSysProgram")
	@ResponseBody
	public BaseResult deleteSysProgram(SysProgram sysProgram){
		BaseResult result = new BaseResult();
		result = sysProgramService.deleteSysProgram(sysProgram);
		return result;
	}
	
	/**
	 * 恢复栏目
	 * @param sysProgram
	 * @return
	 */
	@RequestMapping("/recoverSysProgram")
	@ResponseBody
	public BaseResult recoverSysProgram(SysProgram sysProgram){
		BaseResult result = new BaseResult();
		result = sysProgramService.recoverSysProgram(sysProgram);
		return result;
	}
}
