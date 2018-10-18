package com.jsjf.controller.activity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsOpenDayLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.JsOpenDayLogService;
import com.jsjf.service.activity.JsOpenDayService;

@Controller
@RequestMapping("/openDay")
public class JsOpenDayController {

	@Autowired
	private JsOpenDayService jsOpenDayService;
	@Autowired
	private JsOpenDayLogService jsOpenDayLogService;

	@RequestMapping("/toJsOpenDay")
	public String toJsOpenDay(HttpServletRequest req) {
		return "system/openDay/jsOpenDayList";
	}


	@RequestMapping("/jsOpenDayList")
	@ResponseBody
	public PageInfo jsOpenDayList(JsOpenDay jsOpenDay,
			Integer page, Integer rows, HttpServletRequest request) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo info = new PageInfo(page, rows);
		BaseResult br = jsOpenDayService.getOpenDayList(jsOpenDay, info);
		return (PageInfo) br.getMap().get("page");
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping("/toaddJsOpenDay")
	public String toDrProductLoanList(Map<String,Object> model) {
		
		return "system/activity/addJsOpenDay";
	}
	
	@RequestMapping(value="/addJsOpenDay",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addJsOpenDay(HttpServletRequest request,MultipartFile img,
			JsOpenDay jsOpenDay,HttpServletResponse response) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		try {
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        if(Utils.isObjectNotEmpty(img)){
        	Matcher matcher = pattern.matcher(img.getOriginalFilename().toLowerCase());
    		if(!matcher.find()){
    			result.setSuccess(false);
    			result.setErrorMsg("请上传正确的图片格式!");
    			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
    			return jsonObject.toString();
    		}
    		long fileSize = img.getSize();
    		if(fileSize>1024*5000){
    			result.setSuccess(false);
    			result.setErrorMsg("图片不能大于5M！");
    			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
    			return jsonObject.toString();
    		}
        }
		
			jsOpenDay.setAddUser(usersVo.getUserKy().intValue());
			jsOpenDay.setAddDate(new Date());
			if(jsOpenDay.getStatus() == null){
				jsOpenDay.setStatus(0);
			}
			result = jsOpenDayService.insert(jsOpenDay, img);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/getJsOpenDayById",method=RequestMethod.GET)
	@ResponseBody
	public BaseResult getJsOpenDayById(Map<String,Object> map,Integer id){
		BaseResult result = new BaseResult();
		try {
			map.put("jsOpenDay", jsOpenDayService.selectByPrimaryKey(id));
			result.setMap(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/update",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest request,MultipartFile img,
			JsOpenDay jsOpenDay,HttpServletResponse response){
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        if(Utils.isObjectNotEmpty(img)){
        	Matcher matcher = pattern.matcher(img.getOriginalFilename().toLowerCase());
    		if(!matcher.find()){
    			result.setSuccess(false);
    			result.setErrorMsg("请上传正确的图片格式!");
    			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
    			return jsonObject.toString();
    		}
    		long fileSize = img.getSize();
    		if(fileSize>1024*5000){
    			result.setSuccess(false);
    			result.setErrorMsg("图片不能大于5M！");
    			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
    			return jsonObject.toString();
    		}
        }
        if(jsOpenDay.getStatus() == null){
			jsOpenDay.setStatus(0);
		}
        jsOpenDay.setUpdateDate(new Date());
        jsOpenDay.setUpdateUser(usersVo.getUserKy().intValue());
        result = jsOpenDayService.update(jsOpenDay, img);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	/**
	 * 置为已结束
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/soldOut",method=RequestMethod.GET)
	@ResponseBody
	public String soldOut(HttpServletRequest request,Integer id){
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		try {
			JsOpenDay vo = jsOpenDayService.selectByPrimaryKey(id);
			vo.setStatus(2);
			vo.setUpdateUser(usersVo.getUserKy().intValue());
			vo.setUpdateDate(new Date());
			result = jsOpenDayService.update(vo, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
			return jsonObject.toString();
	} 
	
	//查看预约
	@RequestMapping("/jsOpenDayLogList")
	@ResponseBody
	public PageInfo jsOpenDayLogList(JsOpenDayLog jsOpenDayLog,
			Integer page, Integer rows, HttpServletRequest request) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo info = new PageInfo(page, rows);
		BaseResult br = jsOpenDayLogService.getOpenDayLogList(jsOpenDayLog, info);
		return (PageInfo) br.getMap().get("page");
	}
	
	//导出
	@RequestMapping("/exportOpenDayLog")
	@ResponseBody
	public ModelAndView exportOpenDayLog(Integer openDayId,Integer page,Integer rows)throws Exception{
		Map<String,Object> param = new HashMap<>();
		List<JsOpenDayLog> rowsList = jsOpenDayLogService.exportOpenDayLog(openDayId);
		String[] title = new String[]{"用户姓名","手机号码","推荐码","性别","城市","预约时间"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(JsOpenDayLog vo:rowsList){
			lc = new ArrayList<Object>();
			lc.add(vo.getUserName());//用户姓名
			lc.add(vo.getMobilePhone());//手机
			lc.add(vo.getRecommCodes());//推荐码
			lc.add(vo.getGender());//性别
			lc.add(vo.getCity());
			lc.add(Utils.format(vo.getAppointmentTime(), "yyyy-MM-dd HH:ss:mm"));//预约时间
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "js_OpenDay_log_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
}
