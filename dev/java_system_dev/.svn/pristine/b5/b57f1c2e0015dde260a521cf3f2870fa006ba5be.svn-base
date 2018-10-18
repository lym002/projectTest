package com.jsjf.controller.orderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.member.DrMemberService;

@Controller
@RequestMapping("/regMemberInfo")
public class RegMemberInfoController {
	
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrActivityParameterService drActivityParameterService;
	
	/**
	 * 跳转到用户注册页面
	 * @return
	 */
	@RequestMapping("/toRegMemberInfo")
	public String toRegMemberInfo(){
		return "system/orderManager/regMemberInfoList";
	}
	/**
	 * 显示用户注册列表
	 * @return
	 */
	@RequestMapping("/regMemberInfoList")
	@ResponseBody
	public PageInfo regMemberInfoList(Integer page, Integer rows,
			String startDate, String endDate, String mobilephone,
			@RequestParam(value="cids", required=false) Integer[] cids,String isInvest,String keyWord,String isCrush){
		
		
		
		PageInfo pi = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("mobilephone", mobilephone);
		/*map.put("realname", realname);*/
		map.put("cids", cids);
		map.put("isInvest", "0".equals(isInvest)?"否":"1".equals(isInvest)?"是":null);
		map.put("isCrush","0".equals(isCrush)?"否":"1".equals(isCrush)?"是":null);
		pi = drMemberService.selectRegMemberInfoByParam(map, pi);
		return pi;
	}
	
	@RequestMapping("/selectInvestMemberInfoDataSum")
	@ResponseBody
	public BaseResult selectInvestMemberInfoDataSum(String startDate, String endDate, String mobilephone,String realname,
			@RequestParam(value="cids", required=false) Integer[] cids,String isInvest,String keyWord,String isCrush){
		BaseResult result = new BaseResult();
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			param.put("mobilephone", mobilephone);
			param.put("realname", realname);
			param.put("cids", cids);
//			param.put("isInvest", isInvest==null?null:isInvest==1?"是":"否");
//			param.put("isCrush",isCrush ==null?null:isCrush==1?"是":"否");
			param.put("isInvest", "0".equals(isInvest)?"否":"1".equals(isInvest)?"是":null);
			param.put("isCrush","0".equals(isCrush)?"否":"1".equals(isCrush)?"是":null);

			
			Map<String,Object> map = drMemberService.selectMemberInfoDataSum(param);
			if(map != null){
				result.setMap(map);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/exportRegMemberInfo")
	public ModelAndView exportRegMemberInfo(
			String startDate, String endDate, String mobilephone,
			@RequestParam(value="cids", required=false) Integer[] cids,String isInvest,String keyWord,String isCrush)throws Exception{
		PageInfo pi = new PageInfo(1, Integer.MAX_VALUE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("mobilephone", mobilephone);
		/*map.put("realname", java.net.URLDecoder.decode(realname,"utf-8"));*/
		map.put("cids", cids);
		map.put("isInvest", "0".equals(isInvest)?"否":"1".equals(isInvest)?"是":null);
		map.put("isCrush","0".equals(isCrush)?"否":"1".equals(isCrush)?"是":null);
		pi = drMemberService.selectRegMemberInfoByParam(map, pi);
		List<Map<String, Object>> list = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户姓名","手机号码","推荐码","推荐人姓名","推荐人号码","注册日期","绑卡银行","存管","注册渠道","注册终端","是否充值","是否投资"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for (Map<String, Object> data : list) {
			lc = new ArrayList<Object>();
			lc.add(data.get("realname")==null?"":data.get("realname"));
			lc.add(data.get("mobilePhone"));
			lc.add(data.get("recommCodes"));
			lc.add(data.get("recomRealName")==null?"":data.get("recomRealName"));
			lc.add(data.get("recomMobilePhone")==null?"":data.get("recomMobilePhone"));
			lc.add(data.get("regdate"));
			lc.add(data.get("bankName")==null?"":data.get("bankName"));
			lc.add(data.get("isFuiou")==null?"":data.get("isFuiou").equals(0)?"未开通":data.get("isFuiou").equals(1)?"已开通":null);
			lc.add(data.get("channelName"));
			lc.add(data.get("regfrom"));
			lc.add(data.get("isCrush"));
			lc.add(data.get("isInvest"));
			tableList.add(lc);
		}
		map.clear();
		map.put("titles", title);
		map.put("list", tableList);
		map.put("excelName", "reg_member_info_"+System.currentTimeMillis()+".xls");
		return new ModelAndView(new JXLExcelView(), map);
	}
	/**
	 * 跳转到选择发放的用户列表页面
	 */
	@RequestMapping("/toSelectRegMemberInfoList")
	public String toSelectRegMemberInfoList(Map<String,Object> model,Integer id){
		try {
			model.put("id", id);
			model.put("drActivityParameter", drActivityParameterService.getActivityParameterById(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "system/orderManager/selectRegMemberInfoList";
	}
	/**
	 * 显示选择发放的用户
	 */
	@RequestMapping("/selectRegMemberInfoList")
	@ResponseBody
	public PageInfo selectRegMemberInfoList(Integer page, Integer rows,
			String startDate, String endDate, String mobilephone,String realname,String refundStartDate,String refundEndDate,
			@RequestParam(value="cids", required=false) Integer[] cids,String isInvest,String keyWord,String isCrush,String isFirst){
		PageInfo pi = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("refundStartDate", refundStartDate);
		map.put("refundEndDate", refundEndDate);
		map.put("mobilephone", mobilephone);
		map.put("realname", realname);
		map.put("cids", cids);
		map.put("isInvest", "0".equals(isInvest)?"否":"1".equals(isInvest)?"是":null);
		map.put("isCrush","0".equals(isCrush)?"否":"1".equals(isCrush)?"是":null);
		map.put("isFirst","1".equals(isFirst)?"是":null);
		map.put("keyWord", keyWord);
		pi = drMemberService.selectGiveRegMemberInfoByParam(map, pi);
		return pi;
	}
	
}
