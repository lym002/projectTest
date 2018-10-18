package com.jsjf.controller.cpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.SM;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.cpa.DrChannelInfoAndroidTailBean;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.cpa.DrChannelInfoAndroidInvestTailService;
import com.jsjf.service.cpa.DrChannelInfoService;

@RequestMapping("/androidInvestTail")
@Controller
public class DrChannelAndroidInvestTailController {
	private static Logger log = Logger.getLogger(DrChannelAndroidInvestTailController.class);
	
	@Autowired
	private DrChannelInfoAndroidInvestTailService androidInvestTailService;
	
	@Autowired
	private DrChannelInfoService drChannelInfoService;
	
	@RequestMapping("/androidInvestTailView")
	public String toDrChannelInfoList(Map<String,Object> model) {
		return "system/cpa/drChannelInfoAndroidInvestTailList";
	}
	
	
	@RequestMapping(value= "/queryAndroidInvestTailList")
	@ResponseBody
	public PageInfo drChannelInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = androidInvestTailService.queryAndroidInvestTailList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/downloadDetail")
	@ResponseBody
	public ModelAndView downloadDetail(@RequestParam Map<String,Object> param){
		PageInfo pi = new PageInfo(1,1000000);
		androidInvestTailService.queryAndroidInvestTailList(param,pi);
		List<DrChannelInfoAndroidTailBean> list = (List<DrChannelInfoAndroidTailBean>) pi.getRows();
		String[] title = new String[]{"用户手机号","渠道来源","是否注册","是否绑卡","投资额","投资时间","注册时间"};
		Integer[] columnWidth = new Integer[]{30,20,20,20,20,20,40};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(DrChannelInfoAndroidTailBean bean : list){
			lc = new ArrayList<Object>();
			if(bean.getMobilephone()!=null){
				lc.add(bean.getMobilephone());
			}else{
				lc.add("");
			}
			if(bean.getToFrom()!=null){
				lc.add(bean.getToFrom());
			}else{
				lc.add("");
			}
			if(bean.getIsReg()!=null){
				lc.add(bean.getIsReg());
			}else{
				lc.add("");
			}
			if(bean.getIsBuildCard()!=null){
				lc.add(bean.getIsBuildCard());
			}else{
				lc.add("");
			}
			if(bean.getSumAmount()!=null){
				lc.add(bean.getSumAmount());
			}else{
				lc.add("");
			}
			if(bean.getInvestTime()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				lc.add(sdf.format(bean.getInvestTime()));
			}else{
				lc.add("");
			}
			if(bean.getRegDate()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				lc.add(sdf.format(bean.getRegDate()));
			}else{
				lc.add("");
			}
			tableList.add(lc);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("excelName", "bypCommodityDetail_"+System.currentTimeMillis()+".xls");
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 显示首投渠道订单列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/searchFirst")
	@ResponseBody
	public PageInfo searchFirst(DrChannelInfoAndroidTailBean bean,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				bean.setCode(usersVo.getLoginId());
			}
		}
		DrChannelInfo info = new DrChannelInfo();
		info.setMobilephone(bean.getMobilephone());
		info.setCode(bean.getToFrom());
		if(Utils.isObjectNotEmpty(bean.getIsBuildCard())){
			info.setIsbank(Integer.parseInt(bean.getIsBuildCard()));
		}
		//注册时间
		info.setStartDate(bean.getSearchDrChannelInfoOrderStartDate());
		info.setEndDate(bean.getSearchDrChannelInfoOrderEndDate());
		//投资时间
		info.setStartInvestDate(bean.getStartInvestMoneyDate());
		info.setEndInvestDate(bean.getEndInvestMoneyDate());
		BaseResult result = drChannelInfoService.getFirstDrChannelInfoPhoneList(info, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 显示复投渠道订单列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/searchAgain")
	@ResponseBody
	public PageInfo searchAgain(DrChannelInfoAndroidTailBean bean,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				bean.setCode(usersVo.getLoginId());
			}
		}
		DrChannelInfo info = new DrChannelInfo();
		info.setMobilephone(bean.getMobilephone());
		info.setCode(bean.getToFrom());
		if(Utils.isObjectNotEmpty(bean.getIsBuildCard())){
			info.setIsbank(Integer.parseInt(bean.getIsBuildCard()));
		}
		//注册时间
		info.setStartDate(bean.getSearchDrChannelInfoOrderStartDate());
		info.setEndDate(bean.getSearchDrChannelInfoOrderEndDate());
		//投资时间
		info.setStartInvestDate(bean.getStartInvestMoneyDate());
		info.setEndInvestDate(bean.getEndInvestMoneyDate());
		BaseResult result = drChannelInfoService.getAgainChannelInfoPhoneList(info, pi);
		return (PageInfo)result.getMap().get("page");
	}
}
