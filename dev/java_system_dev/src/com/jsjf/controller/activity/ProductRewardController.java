package com.jsjf.controller.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.ProductReward;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.ProductRewardService;


@Controller
@RequestMapping("productReward/")
public class ProductRewardController {
	
	@Autowired 
	private ProductRewardService productRewardService;
	
	/**
	 * 跳转奖品关联管理页面
	 * @return
	 */
	@RequestMapping("/toProductRewardList")
	public String toProductRewardList(Map<String,Object> model){
		List<DrProductInfo> productlist=productRewardService.selProductList();  //产品信息
		model.put("product", productlist);
		return "system/activity/productRewardList";
	}
	
	/**
	 * 查询奖励关联数据
	 * @param map
	 * @return
	 */
	@RequestMapping("/selProductReward")
	@ResponseBody
	public String selProductReward(HttpServletRequest req,Integer page,Integer rows) {
		Map<String,Object> map=new HashMap();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= productRewardService.selProductReward(map);
		int count =productRewardService.selProductRewardCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/selParameterList")
	@ResponseBody
	public String selParameterList(HttpServletRequest req,String type) {
		Map<String,Object> map=new HashMap();
		if(type!=null && !type.equals("")){
			map.put("type", type);
		}
		List<Map<String,Object>> list= productRewardService.selParameterList(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	//生成关联
	@RequestMapping("/insertProductReward")
	@ResponseBody
	public String insertProductReward(HttpServletRequest req,int id,
			@RequestParam("conData") String conData) throws JsonParseException, JsonMappingException, IOException {
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		ProductReward[] list = new ObjectMapper().readValue(conData,ProductReward[].class);
		for (ProductReward productReward : list) {
			ProductReward reward=new ProductReward();
			reward.setPid(id);
			reward.setApid(productReward.getApid());
			reward.setUser_ky(usersVo.getUserKy());
			productRewardService.insert(reward);
		}
		return "success";
	}
	
	//删除关联
	@RequestMapping("/deleteProductReward")
	@ResponseBody
	public String deleteProductReward(HttpServletRequest req,int id) {
		Map<String,Object> map=new HashMap();
		map.put("id", id);
		productRewardService.delete(map);
		return "success";
	}
	
	/**
	 * 跳转奖品关联管理页面
	 * @return
	 */
	@RequestMapping("/SelProductList")
	@ResponseBody
	public String SelProductList(Map<String,Object> model){
		List<DrProductInfo> productlist=productRewardService.selProductList();  //产品信息
		JSONArray jsonArray=JSONArray.fromObject(productlist);
		return jsonArray.toString();
	}
}
