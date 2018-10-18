package com.jsjf.controller.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrFeedback;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrFeedbackService;
import com.jsjf.service.system.SysUsersVoService;


/**
 * 添加意见信息
 * @author tangxiangping
 *
 */
@RequestMapping("/feedBack")
@Controller
public class AppFeedbackController {
	@Autowired
	private DrFeedbackService ysFeedbackService;
	@Autowired
	private SysUsersVoService sysUsersVoService;
	/**
	 * 添加意见信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFeedback")
	@ResponseBody
	public Map<String, Object> addFeedback(HttpServletRequest request) {
		Map<String,Object> model = new HashMap<String, Object>();
		String uid=request.getParameter("uid");
		String contactInformation=request.getParameter("contactInformation");
		String content=request.getParameter("content");
		int uId=0;
		if(null!=uid&&!"".equals(uid)){
			uId=Integer.parseInt(uid);
		}
		if("".equals(contactInformation)||null==contactInformation){
			model.put("result", "error1");//联系方式为空
			return model;
		}
		if("".equals(content)||null==content){
			model.put("result", "error2");//意见信息为空
			return model;
		}
		DrFeedback ysFeedback=new DrFeedback();
		ysFeedback.setUid(uId);
		ysFeedback.setContactInformation(contactInformation);
		ysFeedback.setContent(content);
		ysFeedback.setFeedbackTime(new Date());
		ysFeedback.setStatus(0);
		
		try {
			ysFeedbackService.insertFeedback(ysFeedback);
			model.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("result", "error");
		}
		return model;
	}
	@RequestMapping("/ysFeedbackList")
	public String ysFeedbackList(Map<String,Object> model,HttpServletRequest req) {
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map<String,Object> map=new HashMap<String,Object>();
		map = sysUsersVoService.selectJsCallNull(usersVo.getUserKy());
		if(map!=null){
		model.putAll(map);
		}
		return "system/customer/drFeedbackList";
	}
	
	
	/**
	 * 查询反馈信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getYsFeedbackList")
	@ResponseBody
	public PageInfo getYsFeedbackList(DrFeedback ysFeedback,Integer page,Integer rows) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", null!=ysFeedback?ysFeedback.getStatus():"");
		map.put("mobilePhone", null!=ysFeedback?ysFeedback.getContactInformation():"");
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result=ysFeedbackService.getYsFeedbackList(map,pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 查询反馈信息详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectByPrimaryKey")
	public String selectByPrimaryKey(DrFeedback ysFeedback,Map<String,Object> model) {
		model.put("ysFeedback",ysFeedbackService.selectByPrimaryKey(ysFeedback.getId()));
		return "system/customer/getDrFeedbackById";
		
	}
	
	/**
	 * 修改反馈信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateYsFeedback",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateYsFeedback(DrFeedback ysFeedback,HttpServletRequest req) {
		BaseResult result = new BaseResult();
		try {
			if(ysFeedback.getId()==0){
				result.setSuccess(false);
				result.setMsg("保存失败，请联系管理员");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
				return jsonObject.toString();
			}
			DrFeedback dto=ysFeedbackService.selectByPrimaryKey(ysFeedback.getId());
			dto.setHandleResult(ysFeedback.getHandleResult());
			dto.setStatus(1);
			ysFeedbackService.updateYsFeedback(dto);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
}

