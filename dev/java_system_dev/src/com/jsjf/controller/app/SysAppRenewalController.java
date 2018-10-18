package com.jsjf.controller.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.app.SysAppRenewal;
import com.jsjf.model.system.JsMessagePush;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.app.SysAppRenewalService;
import com.jsjf.service.system.JsMessagePushService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/app")
public class SysAppRenewalController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private SysAppRenewalService sysAppRenewalService;
	@Autowired
	private JsMessagePushService jsMessagePushService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@RequestMapping("/deletePush")
	@ResponseBody
	public BaseResult deletePush(HttpServletRequest req,Integer id){
		BaseResult result = new BaseResult();
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(id != null){				
				Map<String,Object> map  = new HashMap<String, Object>();
				map.put("status", 3);
				map.put("id", id);
				map.put("updateUser", vo.getUserKy().intValue());
				map.put("upDateTime", new Date());
				jsMessagePushService.updateByMap(map);
				result.setSuccess(true);
				result.setMsg("成功");
			}else{
				result.setErrorMsg("失败,刷新页面");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	/**
	 * 启用
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("/executePushBtn")
	@ResponseBody
	public BaseResult executePushBtn(HttpServletRequest req,Integer id){
		BaseResult result = new BaseResult();
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		boolean lockFlag = false;
		JsMessagePush obj = null;
		try {
			obj = jsMessagePushService.selectObjectById(id);
			if(Utils.isObjectNotEmpty(obj) && obj.getStatus() == 0){
				int isTimeBoolean = obj.isTimeBoolean();
				if(isTimeBoolean ==0){
					obj.setUpDateTime(new Date());
					obj.setUpdateUser(vo.getUserKy().intValue());
					lockFlag = redisClientTemplate.tryLock("jsMessagePush."+obj.getId(), 3, TimeUnit.SECONDS,false);//产品全局锁
					if(lockFlag){						
						result = jsMessagePushService.executePush(obj);
					}else{
						result.setErrorMsg("系统繁忙");
					}
				}else if(isTimeBoolean == 1){
					result.setErrorMsg("发送时间要大于当前时间+10分钟");
				}else if(isTimeBoolean == 2){
					result.setErrorMsg("时间区间有误或发送时间要大于当前时间+10分钟");
				}else{
					result.setErrorMsg("系统错误");
				}
			}else{
				result.setErrorMsg("没有对象或已启用,刷新页面");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			result.setErrorMsg("系统错误");
		}finally{
			if(lockFlag && Utils.isObjectNotEmpty(obj)){
				redisClientTemplate.del("jsMessagePush."+obj.getId());
			}
		}	
		return result;
	}
	/**
	 * 查看
	 * @param req
	 * @param id
	 * @param model
	 * @param isEdit
	 * @return
	 */
	@RequestMapping("/selectAppPush")
	public String selectAppPush(HttpServletRequest req,Integer id,Map<String,Object> model,Integer isEdit){
		if(!Utils.isBlank(id)){
			JsMessagePush result = jsMessagePushService.selectObjectById(id);
			model.put("result", result);
			model.put("isEdit", isEdit);
			if(Utils.isObjectNotEmpty(result)){
				Map<String,Object> checkbox = new HashMap<String,Object>();
				if(!Utils.isBlank(result.getInvestMax()) || !Utils.isBlank(result.getInvestMax()))
					checkbox.put("invest", 1);
				if(!Utils.isBlank(result.getBalanceMax()) || !Utils.isBlank(result.getBalanceMin()))
					checkbox.put("balance", 1);
				if(!Utils.isBlank(result.getPayment()))
					checkbox.put("payment", 1);
				if(!Utils.isBlank(result.getLiveness()))
					checkbox.put("liveness", 1);
				
				model.put("checkbox", checkbox);
			}
		}
		return "system/app/editAppPush";
	}
	
	/**
	 * 添加推送
	 * @return
	 */
	@RequestMapping("/addAppPush")
	@ResponseBody
	public BaseResult addAppPush(HttpServletRequest req,JsMessagePush jsMessagePush,Integer isEdit){
		BaseResult result = new BaseResult();
		try {			
			SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			if(Utils.isObjectNotEmpty(vo) && Utils.isObjectNotEmpty(jsMessagePush)){
				int isTimeBoolean = jsMessagePush.isTimeBoolean();
				if(isTimeBoolean ==0){
					if(!Utils.isBlank(isEdit)){//编辑
						jsMessagePush.setUpDateTime(new Date());
						jsMessagePush.setIscheck(0);
						jsMessagePush.setUpdateUser(vo.getUserKy().intValue());
						jsMessagePushService.update(jsMessagePush);
						result.setMsg("修改成功");
					}else{
						jsMessagePush.setAddUser(vo.getUserKy().intValue());
						jsMessagePushService.insert(jsMessagePush);
						result.setMsg("添加成功");
					}	
					result.setSuccess(true);
				}else if(isTimeBoolean == 1){
					result.setErrorMsg("发送时间要大于当前时间+10分钟");
				}else if(isTimeBoolean == 2){
					result.setErrorMsg("时间区间有误或发送时间要大于当前时间+10分钟");
				}else{
					result.setErrorMsg("系统错误");
				}
			}else{
				result.setErrorCode("未登录");
			}
		} catch (Exception e) {
			result.setErrorMsg("系统错误");
			e.printStackTrace();
		}
		return result;		
	}
	/**
	 * 进入推送
	 * @param req
	 * @return
	 */
	@RequestMapping("toAppPush")
	public String toAppPush(HttpServletRequest req){
		return "system/app/appPushList";
	}
	/**
	 * 获取推送list
	 * @param req
	 * @param page
	 * @param rows
	 * @param jsMessagePush
	 * @return
	 */
	@RequestMapping("/appAppPushList")
	@ResponseBody
	public PageInfo appAppPushList(HttpServletRequest req,Integer page, Integer rows,JsMessagePush jsMessagePush){
		PageInfo info = new PageInfo(page, rows);
		try {
			info = jsMessagePushService.selectParamList(info, jsMessagePush);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	/**
	 * 跳转到列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAppRenewalList")
	public String toAppRenewalList(HttpServletRequest req) {
		return "system/app/appRenewalList";
	}
	
	@RequestMapping("/appRenewalList")
	@ResponseBody
	public PageInfo appRenewalList(HttpServletRequest req,Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResult br = new BaseResult();
		try {
			br = sysAppRenewalService.getSysAppRenewal(map, info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (PageInfo)br.getMap().get("page");
	}
	
	@RequestMapping("/addAppRenewal")
	@ResponseBody
	public BaseResult addAppRenewal(HttpServletRequest req,SysAppRenewal appRenewal){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			SysAppRenewal sar = new SysAppRenewal();
			sar.setContainers(appRenewal.getContainers());
			sar.setStatus(1);
			sysAppRenewalService.updateStatus(sar);
			sysAppRenewalService.insertAppRenewal(appRenewal,usersVo);
			br.setSuccess(true);
			br.setMsg("新增成功！");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("新增失败！");
			e.printStackTrace();
		}
		return br;
	}
	
	@RequestMapping("/toAppRenewalShow")
	public String toAppRenewalShow(HttpServletRequest req,Integer id,Map<String, Object> model) {
		SysAppRenewal sar = sysAppRenewalService.getSysAppRenewalById(id);
		model.put("sar", sar);
		return "system/app/showAppRenewal";
	} 
	
	@RequestMapping("/toAppRenewalModify")
	public String toAppRenewalModify(HttpServletRequest req,Integer id,Map<String, Object> model) {
		SysAppRenewal sar = sysAppRenewalService.getSysAppRenewalById(id);
		model.put("sar", sar);
		return "system/app/updateAppRenewal";
	} 
	
	@RequestMapping("/updateAppRenewal")
	@ResponseBody
	public BaseResult updateAppRenewal(HttpServletRequest req,SysAppRenewal appRenewal){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			appRenewal.setUpdateTime(new Date());
			appRenewal.setUpdateUser(usersVo.getUserKy().intValue());
			appRenewal.setStatus(1);
			sysAppRenewalService.updateSysAppRenewal(appRenewal);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		return br;	
	}
	
	

}
