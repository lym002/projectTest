package com.jsjf.controller.store;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.store.CommodityClass;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.store.CommodityClassService;

/**
 * 商品类别管理
 * @author cece
 *
 */
@Controller
@RequestMapping("/commodityClass")
public class CommodityClassController {
	
	@Autowired
	private CommodityClassService commodityClassService;

	@RequestMapping("/commodityClassView")
	public String commodityClassView(Map<String,Object> model){
		return "system/store/commodityClassView";
	}
	
	@RequestMapping(value="/commodityClassList",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo queryCommodityClassList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = commodityClassService.queryCommodityClassList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping(value="/commodityClassList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResult addCommodityClass(HttpServletRequest request,CommodityClass bean){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		bean.setTypeCode(Utils.createOrderNo(4, usersVo.getUserKy().intValue(), "CP-"));
		br = commodityClassService.addCommodityClass(bean);
		return br;
	}
	
	@RequestMapping(value="/commodityClassList",method=RequestMethod.PUT)
	@ResponseBody
	public BaseResult updateCommodityClass(CommodityClass bean){
		BaseResult br = new BaseResult();
		br = commodityClassService.updateCommodityClass(bean);
		return br;
	}
	
	@RequestMapping(value="/commodityClassList",method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResult deleteCommodityClass(HttpServletRequest req,int id) {
		BaseResult br = new BaseResult();
		br = commodityClassService.deleteCommodityClass(id);
		return br;
	}
}
