package com.jsjf.controller.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.product.JsProductReservation;
import com.jsjf.model.product.JsProductReservationLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.product.JsProductReservationService;

@Controller
@RequestMapping("/preservation")
public class JsProductReservationController{
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private JsProductReservationService jsProductReservationService;

	
	/**
	 * 跳转到预约管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toJsProductReservationList")
	public String toJsProductReservationList(Map<String,Object> model){
		return "system/product/jsProductReservationList";
	}
	
	
	
	/**
	 * 显示预约管理信息
	 */
	@RequestMapping("/jsProductReservationList")
	@ResponseBody
	public PageInfo jsProductReservationList(JsProductReservation jsProductReservation,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsProductReservationService.getJsProductReservationList(jsProductReservation, pi);
		return (PageInfo)result.getMap().get("page");
	}  
	
	@RequestMapping("/addReservation")
	@ResponseBody
	public BaseResult addReservation(HttpServletRequest request,JsProductReservation jsProductReservation){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		if(usersVo!= null){
			try {
				br = jsProductReservationService.insertJsProductReservation(jsProductReservation, usersVo);
			} catch (Exception e) {
				br.setErrorMsg("保存失败！");
				e.printStackTrace();
			}
		}
		return br;
	}
	
	
	/**
	 * 进入修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toReservationModify")
	public String toReservationModify(Integer id, Map<String, Object> model) {
		JsProductReservation jsProductReservation;
		try {
			jsProductReservation = jsProductReservationService.getJsProductReservationById(id);
			model.put("jsPReservation", jsProductReservation);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/product/jsProductReservationModify";
	}

	
	@RequestMapping("/modifyJsPReservation")
	@ResponseBody
	public BaseResult modifyJsPReservation(HttpServletRequest request,JsProductReservation jsProductReservation){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(usersVo!= null){
			try {
				jsProductReservationService.updateJsProductReservation(jsProductReservation, usersVo);
				br.setSuccess(true);
				br.setMsg("修改成功");
			} catch (Exception e) {
				br.setErrorMsg("修改失败！");
				e.printStackTrace();
			}
		}
		return br;
	}
	
	
	@RequestMapping("/queryJsPReservationLog")
	@ResponseBody
	public PageInfo queryJsPReservationLog(HttpServletRequest request, JsProductReservationLog jprlog,
			Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prid",jprlog.getPrid());
		map.put("realName", jprlog.getRealName());
		map.put("mobilePhone",jprlog.getMobilePhone());
		map.put("logStartTime", jprlog.getLogStartTime());
		map.put("logEndTime", jprlog.getLogEndTime());
		pi = jsProductReservationService.getJsPReservationLogByPrid(map, pi);
		return pi;
	}
	
	
	@RequestMapping("/exportJsPReservationLog")
	public ModelAndView exportJsPReservationLog(JsProductReservationLog jprlog,
			Integer page,Integer rows) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prid", jprlog.getPrid());
		param.put("realName", java.net.URLDecoder.decode(jprlog.getRealName(),"utf-8"));
		param.put("mobilePhone", jprlog.getMobilePhone());
		param.put("logStartTime", jprlog.getLogStartTime());
		param.put("logEndTime", jprlog.getLogEndTime());
		
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		pi = jsProductReservationService.getJsPReservationLogByPrid(param, pi);
		List<JsProductReservationLog> rowsList = (List<JsProductReservationLog>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","预约时间","预约金额"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(JsProductReservationLog jprl:rowsList){
			lc = new ArrayList<Object>();
			
			lc.add(jprl.getRealName());
			lc.add(jprl.getMobilePhone());
			lc.add(Utils.format(jprl.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(jprl.getAmount());
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "Reservation_Log"+System.currentTimeMillis()+".xls");
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}