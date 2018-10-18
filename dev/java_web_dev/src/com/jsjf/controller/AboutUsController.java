package com.jsjf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysArticle;
import com.jsjf.service.system.SysArticleService;

@RequestMapping("/aboutus")
@Controller
public class AboutUsController {
	private Logger log = Logger.getLogger(AboutUsController.class);
	@Autowired
	private SysArticleService sysArticleService;
	
	/**
	 * 新闻动态列表
	 * @param req
	 * @param params
	 * @return
	 */
	@RequestMapping("/newsInformationList")
	@ResponseBody
	public String newsInformationList(HttpServletRequest req,@RequestBody Map<String, Object> params, PageInfo pi){
		BaseResult br = null;
		try {
			Utils.getObjectFromMap(pi, params);
			br = sysArticleService.getArticleByParam(params, pi);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("新闻动态列表获取失败", e);
			br.setErrorMsg("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	

	/**
	 * 新闻详情(按id,且没上下页)
	 * @param proId
	 * @param offset
	 * @param model
	 * @return
	 */
	@RequestMapping("/newsDetails")
	@ResponseBody
	public String newDetails(HttpServletRequest req, @RequestBody Map<String, Object> param){
		BaseResult br = new BaseResult();
		Integer aid = Integer.parseInt(String.valueOf(param.get("id")));//新闻ID
		SysArticle sysArticle;
		try {
			sysArticle = sysArticleService.selectSysArticleById(aid);
			param.put("sysArticle", sysArticle);
			br.setMap(param);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("详情获取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	
	/**
	 * 给静态页面ajax刷新header
	 * @param req
	 * @return
	 */
	@RequestMapping("/toHeader")
	public String toHeader(HttpServletRequest req){
		return "common/header";
	}
	/**
	 * 给静态页面ajax刷新header
	 * @param req
	 * @return
	 */
	@RequestMapping("/toFooter")
	public String toFooter(HttpServletRequest req){
		return "common/footer";
	}
	
}
