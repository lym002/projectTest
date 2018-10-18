package com.jsjf.controller.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberFourElementsLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberFourElementsLogService;
import com.jsjf.service.member.DrMemberService;

@Controller
@RequestMapping("/fourElements")
public class DrMemberFourElementsLogController {
	@Resource
	private DrMemberFourElementsLogService drMemberFourElementsLogService;
	@Autowired
	private DrMemberService drMemberService;

	@RequestMapping("toDrMemberFourElementsLogList")
	public String toDrMemberFourElementsLogList(HttpServletRequest req,Map<String,Object> model) {
		model.put("sysBank", drMemberFourElementsLogService.selectSysBank());
		return "system/customer/drMemberFourElementsLogList";
	}

	@RequestMapping("/drMemberFourElementsLogList")
	@ResponseBody
	public PageInfo drMemberFourElementsLogList(Integer rows, Integer page,
			DrMemberFourElementsLog drMemberFourElementsLog) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drMemberFourElementsLogService.getMemberFourElementsLogList(drMemberFourElementsLog, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	@RequestMapping("/updateDrMemberFourElementsLog")
	@ResponseBody
	public BaseResult updateDrMemberFourElementsLog(HttpServletRequest request,DrMemberFourElementsLog drMemberFourElementsLog) {
		BaseResult result = new BaseResult();
		try {
			DrMember drMember = drMemberService.queryDrMemberByUid(drMemberFourElementsLog.getUid());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idCards", drMemberFourElementsLog.getIdCards());
			List<DrMemberBaseInfo> baseInfo = drMemberService.selectByCard(map);
			if(0 == drMember.getRealVerify()){
				if(baseInfo.size()==0){//身份证不存在DrMemberBaseInfo表
					SysUsersVo vo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
					drMemberFourElementsLogService.updateDrMemberFourElementsLog(drMemberFourElementsLog,vo);
					result.setSuccess(true);
					result.setMsg("认证成功！");
				}else if(baseInfo.get(0).getUid()==drMemberFourElementsLog.getUid()){//身份存在但与当前修改人为同一人
					SysUsersVo vo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
					drMemberFourElementsLogService.updateDrMemberFourElementsLog(drMemberFourElementsLog,vo);
					result.setSuccess(true);
					result.setMsg("认证成功！");
				}else{
					result.setSuccess(false);
					result.setErrorMsg("认证失败，该身份证已经存在！");
				}
			}else{
				result.setSuccess(true);
				result.setMsg("认证成功！");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("认证失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/updateDrMemberFourElementsLogAgain")
	@ResponseBody
	public BaseResult updateDrMemberFourElementsLogAgain(HttpServletRequest request,Integer uid) {
		BaseResult result = new BaseResult();
		try {
			drMemberFourElementsLogService.updateDrMemberFourElementsLogAgain(uid);
			result.setSuccess(true);
			result.setMsg("操作成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("操作失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 跳转到银行失败认证页面
	 * @return
	 */
	
	@RequestMapping("toDrMemberIdentificationLogList")
	public String toDrMemberIdentificationLogList() {
		
		return "system/customer/drMemberIdentificationLogList";
	}
	
	/**toDrMemberIdentificationLogList
	 * 显示银行认证失败日志列表
	 */
	@RequestMapping("/drMemberIdentificationLogList")
	@ResponseBody
	public PageInfo drMemberIdentificationLogList(Integer rows, Integer page,
			DrMemberFourElementsLog drMemberFourElementsLog) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
 			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drMemberFourElementsLogService.drMemberIdentificationLogList(drMemberFourElementsLog, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 重新一次银行认证
	 */
	@RequestMapping("/updateDrMemberIdentificationLogAgain")
	@ResponseBody
	public BaseResult updateDrMemberIdentificationLogAgain(HttpServletRequest request,String mobilePhone) {
		BaseResult result = new BaseResult();
		if (StringUtils.isNotEmpty(mobilePhone)) {
			try {
				return drMemberFourElementsLogService.updateDrMemberIdentificationLogAgain(mobilePhone);
			} catch (Exception e) {
				result.setSuccess(false);
				result.setErrorMsg("操作失败！");
				e.printStackTrace();
			}
			return result;
		}else if(StringUtils.isEmpty(mobilePhone)){
			result.setSuccess(false);
			result.setErrorMsg("请输入需查询的手机号！");
		}
		return result;
	}
}
