package com.jsjf.controller.cpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.cpa.DrCpaInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jsjf.service.cpa.DrChannelKeyWordsService;
import com.jsjf.service.cpa.DrCpaInfoService;

@RequestMapping("/cpa")
@Controller
public class DrCpaInfoController {
	private static Logger log = Logger.getLogger(DrCpaInfoController.class);
	
	@Autowired
	private DrCpaInfoService drCpaInfoService;
	@Autowired
	private DrChannelInfoService drChannelInfoService;
	@Autowired
	private DrChannelKeyWordsService drChannelKeyWordsService;
	
	/**
	 * 跳转到CPA地址列表页面
	 */
	@RequestMapping("/toDrCpaInfoList")
	public String toDrCpaInfoList(Map<String,Object> model) {
		return "system/cpa/drCpaInfoList";
	}
	
	/**
	 * 显示CPA地址列表数据
	 * @param DrCpaInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drCpaInfoList")
	@ResponseBody
	public PageInfo drCpaInfoList(DrCpaInfo drCpaInfo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drCpaInfoService.getDrCpaInfoList(drCpaInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 添加CPA地址信息
	 * @param DrCpaInfo
	 * @return
	 */
	@RequestMapping(value= "/addDrCpaInfo")
	@ResponseBody
	public BaseResult addDrCpaInfoInfo(DrCpaInfo drCpaInfo,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			String[] cid = drCpaInfo.getCid().split(",");
			for(int i=0;i<cid.length;i++){
				DrChannelInfo drChannelInfo = drChannelInfoService.getDrChannelInfoByid(Integer.valueOf(cid[i]));
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("code",drChannelInfo.getCode());
				map.put("activityCode",drCpaInfo.getActivityCode());
				List<DrCpaInfo> list = drCpaInfoService.getDrCpaInfoListForMap(map);
				if(list.size()>0){
					br.setErrorMsg("该活动号已生成过该渠道【"+drChannelInfo.getName()+"】!");
					br.setSuccess(false);
					return br;
				}
			}
			
			for(int i=0;i<cid.length;i++){
				drCpaInfo.setAddUser(usersVo.getUserKy().intValue());
				DrChannelInfo drChannelInfo = drChannelInfoService.getDrChannelInfoByid(Integer.valueOf(cid[i]));

				drCpaInfo.setCid(drChannelInfo.getId().toString());
				StringBuffer url = new StringBuffer();
//				url.append(ConfigUtil.getDomainname()).append("/").append(drCpaInfo.getPageUrl()).append("?")
//				.append("toFrom=")
//				.append(drChannelInfo.getCode())
//				.append(drCpaInfo.getActivityCode());
				if(drCpaInfo.getParameters()!=null && !drCpaInfo.getParameters().trim().equals("")){
					url.append(drCpaInfo.getPageUrl()).append("?").append(drCpaInfo.getParameters()).append("&toFrom=").append(drChannelInfo.getCode()).append(drCpaInfo.getActivityCode());
				}else{
					url.append(drCpaInfo.getPageUrl()).append("?toFrom=").append(drChannelInfo.getCode()).append(drCpaInfo.getActivityCode());					
				}
				
				drCpaInfo.setUrl(url.toString());
				drCpaInfoService.insertDrCpaInfo(drCpaInfo);
			}
			br.setSuccess(true);
			br.setMsg("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("添加失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	@RequestMapping("/exportChannelKeyWord")
	@ResponseBody
	public ModelAndView exportChannelKeyWord(HttpServletRequest request, HttpServletResponse response,int id){
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		map = drChannelKeyWordsService.getKeyWordUrlByCpaId(id);
		return new ModelAndView(new JXLExcelView(), map);

	}
	
}
