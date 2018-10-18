package com.jsjf.controller.finance;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SensorsAnalyticsUtils;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrCustomerAllot;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jsjf.service.member.DrMemberFundsRecordService;
import com.jsjf.service.member.DrMemberRecommendedService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.member.DrcustomerAllotService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.JsErrorService;
import com.jsjf.service.system.SysChooseOptionService;
import com.jsjf.service.system.SysUsersVoService;
import com.jzh.FuiouConfig;
import com.sensorsdata.SensorsAnalytics;

@RequestMapping("/member")
@Controller
public class DrMemberController {
	private Logger log = Logger.getLogger(DrMemberController.class);
	
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrActivityParameterService drActivityParameterService;
	@Autowired
	private DrMemberFavourableService drMemberFavourableService;
	@Autowired
	private DrMemberFundsRecordService drMemberFundsRecordService;
	@Autowired
	private DrMemberRecommendedService drMemberRecommendedService;
	@Autowired
	private SysUsersVoService sysUsersVoService;
	@Autowired
	private DrcustomerAllotService drcustomerAllotService;
	@Autowired
	private SysChooseOptionService sysChooseOptionService;
	@Autowired
	private DrChannelInfoService drChannelInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private JsErrorService jsErrorService;
	@Autowired
	private DrProductInfoService drProductInfoService;

	
	/**
	 * 查询fuiou
	 * @return
	 */
	@RequestMapping("/selectFuiouByPrimaryKey")
	@ResponseBody
	public BaseResult selectFuiouByPrimaryKey(HttpServletRequest req,Integer uid){
		BaseResult result = new BaseResult();
		
		try {
			
			if(!Utils.isBlank(uid)){
				
				DrMember member = drMemberService.selectByPrimaryKey(uid);
				
				if(Utils.isObjectNotEmpty(member)){
					if(!Utils.isBlank(member.getIsFuiou()) && member.getIsFuiou() ==0){
						result = drMemberService.selectFuiouByPrimaryKey(member);
					}else{
						result.setMsg("已开通!");
						result.setSuccess(true);
					}
				}else{
					result.setErrorMsg("参数错误");
				}
				
			}else{
				result.setErrorMsg("参数错误");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMsg("系统错误");
		}
		
		return result;
	}
	
	@RequestMapping("/toMemberList")
	public String toMemberList(){
		return "system/finance/drMemberList";
	}
	
	@RequestMapping("/memberList")	
	@ResponseBody
	public PageInfo memberList(HttpServletRequest req,Integer rows,Integer page,DrMember member){
		PageInfo pi = new PageInfo(page, rows);
		pi = drMemberService.selectDrMemberList(member, pi,true);
		return pi;
	}
	
	@RequestMapping("toCustomerService")
	public String toCustomerService(@RequestParam(value="caller",required=false) String caller,@RequestParam(value="type",required=false) String type,String isdirector,Map<String,Object> model) throws UnsupportedEncodingException{
		if(caller==null){
			model.put("recommCodes", "");
		}else{
			if(caller.length()>6){//caller可能是手机号，也可能是推荐码
				//手机号
				DrMember drMember=new DrMember();
				drMember.setMobilephone(caller);
				
				drMember=drMemberService.selectByMobilephone(drMember);
				if(drMember!=null){
					caller=drMember.getRecommCodes();
				}else{
					caller="该手机号未注册";
				}
			}
			model.put("recommCodes",caller);
		}
		model.put("isdirector", isdirector);
		return "system/activity/customerService";
	}
	
	@RequestMapping("/qeuryMemberList")	
	@ResponseBody
	public PageInfo qeuryMemberList(HttpServletRequest req,Integer rows,Integer page,DrMember member,@RequestParam(value="type",required=false) String type,
			@RequestParam(value="mobilephone",required=false) String mobilephone,String recommCodes){
		PageInfo pi = new PageInfo(page, rows);
		if((mobilephone!=null && !mobilephone.equals("")) || (recommCodes!=null && !recommCodes.equals(""))){
			member.setMobilephone(mobilephone);
			member.setRecommCodes(recommCodes);
			pi = drMemberService.selectDrMemberList(member, pi, false);
		}
		
		/*if(type!=null && type.equals("1")){//外呼
			List<DrMember> mList =(List<DrMember>) pi.getRows();
			List<DrMember> list=new ArrayList<DrMember>();			
			for (DrMember drMember : mList) {
				 String phone=drMember.getMobilephone().substring(0,3) + "****" + drMember.getMobilephone().substring(7, drMember.getMobilephone().length());
				 drMember.setMobilephone(phone);
				 list.add(drMember);
			}
			pi.setRows(list);
		}*/
		return pi;
	}
	
	/**
	 * 投资记录
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/productInvestList")	
	@ResponseBody
	public PageInfo productInvestList(HttpServletRequest req,Integer rows,Integer page,String mobilephone,String recommCodes){
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = new BaseResult();
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("statuses", new Integer[]{0,1,3});
		map.put("mobilephone", mobilephone);
		map.put("recommCodes", recommCodes);
		result = drProductInvestService.selectInvestLogByParam(map, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	
	/**
	 * 优惠券
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/favourableList")	
	@ResponseBody
	public PageInfo favourableList(HttpServletRequest req,Integer rows,Integer page,String mobilephone,Integer fid,String recommCodes){
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = new BaseResult();
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobilephone", mobilephone);
		map.put("recommCodes", recommCodes);
		map.put("fid", fid);
		result = drMemberFavourableService.selectFavourableByParam(map, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	/**
	 * 用户资金记录
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/memberFundsRecordList")	
	@ResponseBody
	public PageInfo memberFundsRecordList(HttpServletRequest req,Integer rows,Integer page,String mobilephone,String recommCodes){
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = new BaseResult();
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobilephone", mobilephone);
		map.put("recommCodes", recommCodes);
		result = drMemberFundsRecordService.getDrMemberFundsRecordList(map,pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 推荐信息
	 */
	@RequestMapping("/recommendInfoList")	
	@ResponseBody
	public Map<String,Object> recommendInfoList(HttpServletRequest req,@RequestParam(value="mobilephone",required=false) String mobilephone,String recommCodes){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String, Object> param=new HashMap<>();
			if(Utils.strIsNull(mobilephone) && Utils.strIsNull(recommCodes))
				return null;
			param.put("mobilephone", mobilephone);
			param.put("recommCodes", recommCodes);
			map.put("member", drMemberService.selectReferrerMemberByMobilePhone(param));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
		}
		return map;
	}
	
	/**
	 * 被邀请人信息
	 */
	@RequestMapping("/beInvited")	
	@ResponseBody
	public PageInfo beInvited(HttpServletRequest req,Integer rows,Integer page,DrMember member,@RequestParam(value="type",required=false) String type,
			@RequestParam(value="mobilephone",required=false) String mobilephone,String recommCodes){
		PageInfo pi = new PageInfo(page, rows);
		if(mobilephone!=null && !mobilephone.equals("")){
			member.setMobilephone(mobilephone);
		}
		if(recommCodes!=null && !recommCodes.equals("")){
			member.setRecommCodes(recommCodes);
		}
		pi = drMemberService.selectdrMemberByMobilePhone(member, pi);
		return pi;
	}
	
	/**
	 * 进入放发页面
	 * @param req
	 * @param type
	 * @return
	 */
	@RequestMapping("/giveOut") 
	public String giveOut(HttpServletRequest req,Integer type,Integer uid,Map<String,Object> model){
		model.put("uid", uid);
		model.put("type", type);
		return "system/activity/giveOut";
	}
	
	/**
	 * 获取可放发的红包
	 * @param req
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/giveOutInfo")
	@ResponseBody
	public PageInfo giveOutInfo(HttpServletRequest req,Integer rows,Integer page,Integer type,
			DrActivityParameter drActivityParameter){
		PageInfo info = new PageInfo(page, rows);
		try {
			drActivityParameter.setType(type);
			drActivityParameter.setStatuses(new Integer[]{0,1});
			drActivityParameter.setSurplusQty(0);
			drActivityParameter.setApplicableScenarios("0");//0：电销使用  1：活动使用
			info = drActivityParameterService.getGiveOutAPList(info,drActivityParameter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 发放操作
	 * @param req
	 * @param id
	 * @param uid
	 * @return
	 */
	@RequestMapping("/selectBatchActivity")
	@ResponseBody
	public BaseResult selectBatchActivity(HttpServletRequest req,Integer[] ids,Integer uid){
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult result = new BaseResult();
		SensorsAnalytics sau = SensorsAnalyticsUtils.getInstance();
		Map<String, Object> properties = new HashMap<String, Object>();
		for(Integer id : ids){
			try {
				DrActivityParameter drActivityParameter = drActivityParameterService.getActivityParameterById(id);
				int surplusQty = drActivityParameter.getSurplusQty()-1;
				if(surplusQty>=0){
					drMemberFavourableService.selectActivity(uid, drActivityParameter, usersVo);
					result.setSuccess(true);
					result.setMsg("操作成功");
					if(drActivityParameter.getType().equals(1)){
						properties.put("CouponType", "红包券");
					}else if(drActivityParameter.getType().equals(2)){
						properties.put("CouponType", "加息券");
					}else if(drActivityParameter.getType().equals(3)){
						properties.put("CouponType", "体验金");
					}else{
						properties.put("CouponType", "翻倍券");
						properties.put("InterestRateHike", drActivityParameter.getMultiple());
					}
					properties.put("CouponId", drActivityParameter.getId());
					if(Utils.isObjectNotEmpty(drActivityParameter.getAmount())){
						properties.put("CouponAmount", drActivityParameter.getAmount());
					}
					if(Utils.isObjectNotEmpty(drActivityParameter.getRaisedRates())){
						properties.put("InterestRateHike", drActivityParameter.getRaisedRates());
					}
					if(Utils.isObjectNotEmpty(drActivityParameter.getProductDeadline())){
						properties.put("RateHikeDays", drActivityParameter.getProductDeadline());
					}
					properties.put("Source", "客服发放");
					if(Utils.isObjectNotEmpty(drActivityParameter.getApplicableProducts())){
						properties.put("UsedProductName", drActivityParameter.getApplicableProducts()+"");
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = sdf.format(new Date());
					properties.put("UsedStartTime", sdf.parse(time));
					Calendar c = Calendar.getInstance();  
	                c.setTime(sdf.parse(time));  
	                c.add(Calendar.DATE, 7);
					properties.put("UsedEndTime", c.getTime());
					properties.put("UsedAmountLimit", drActivityParameter.getEnableAmount());
					sau.track(String.valueOf(uid), true, "ReceiveCoupons" ,properties);	
				}else{
					result.setSuccess(false);
					result.setErrorMsg("【"+drActivityParameter.getName()+"】已经发放完，请选择其他红包/加息券发放");
				}
			} catch (Exception e) {
				result.setSuccess(false);
				result.setErrorMsg("操作失败");
				log.error(e.getMessage(), e);
			}finally{
				sau.flush();
			}
		}
		return result;
	}
	
	@RequestMapping("/toCustomerAllot")
	public String toCustomerAllot(Map<String,Object> model,HttpServletRequest req){//跳转客户分配页面
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		try {
			model.put("wincallType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("wincallType")));
			model.put("deptCode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("deptCode")));
			SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			model.put("deptId", vo.getDeptId());
			model.put("roleName", vo.getRoleName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.put("channel", list);
		return "system/customer/customerAllotList";
	}
	
	@RequestMapping("/toFreezeCustomerList")
	public String toFreezeCustomerList(Map<String,Object> model,HttpServletRequest req){//跳转客户分配页面
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		try {
			model.put("wincallType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("wincallType")));
			SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			model.put("roleName", vo.getRoleName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.put("channel", list);
		return "system/customer/freezeCustomerList";
	}
	/**
	 * 批量发放加息劵操作
	 * @param req
	 * @param id
	 * @param uid
	 * @return
	 */
	
	@RequestMapping("/updateBatchGiveOutMember")
	@ResponseBody
	public BaseResult updateBatchGiveOutMember(HttpServletRequest req,Integer id,Integer[] uids){
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult result = new BaseResult();
		try {
			DrActivityParameter drActivityParameter = drActivityParameterService.getActivityParameterById(id);
			int surplusQty = drActivityParameter.getSurplusQty()-1;
			if(surplusQty>=0){
				drMemberFavourableService.batchSelectActivity(uids, drActivityParameter, usersVo);
				result.setSuccess(true);
				result.setMsg("操作成功");
			}else{
				result.setSuccess(false);
				result.setErrorMsg("【"+drActivityParameter.getName()+"】已经发放完。");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("操作失败");
			log.error(e.getMessage(), e);
			
		}
		return result;
	}
	/**
	 * 查询角色为电销的用户
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryUser")
	@ResponseBody
	public String queryUser(SysUsersVo vo,HttpServletRequest req) {
		Map<String,Object> map=new HashMap();
		if(vo.getName()!=null && !vo.getName().equals("")){
			map.put("name", vo.getName());
		}
		if(vo.getMobile()!=null && !vo.getMobile().equals("")){
			map.put("mobile", vo.getMobile());
		}
		if(vo.getPhone()!=null && !vo.getPhone().equals("")){
			map.put("phone", vo.getName());
		}
		if(vo.getStatus()!=null){
			map.put("status", vo.getStatus());
		}
		if(Utils.isObjectNotEmpty(vo.getDeptId())){
			map.put("deptId", vo.getDeptId());
		}
		
		SysUsersVo user = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		if(user.getRoleName().equals("电销经理") || user.getRoleName().equals("电销主管")){
			map.put("deptId", user.getDeptId());
		}
		
		List<SysUsersVo> list = sysUsersVoService.queryUserByRole(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 查询客户
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryCustomer")
	@ResponseBody
	public String queryUserByRole(HttpServletRequest req,Integer page,Integer rows,
			/*@RequestParam(value="customerName",required=false) String customerName,*/
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="customerstatus",required=false) String customerstatus,
			@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="startAppointDate",required=false) String startAppointDate,
			@RequestParam(value="endAppointDate",required=false) String endAppointDate,
			@RequestParam(value="startCallDate",required=false) String startCallDate,
			@RequestParam(value="endCallDate",required=false) String endCallDate,
			@RequestParam(value="type",required=false) String type,
			@RequestParam(value="isbank",required=false) String isbank,
			@RequestParam(value="startMoney",required=false) String startMoney,
			@RequestParam(value="endMoney",required=false) String endMoney,
			@RequestParam(value="lastcall",required=false) String lastcall,
			@RequestParam(value="mobilePhone",required=false) String mobilePhone,
			@RequestParam(value="channelName",required=false) String channelName,
			@RequestParam(value="channelType",required=false) Integer channelType,
			@RequestParam(value="customerType",required=false) String customerType,
			@RequestParam(value="callNumAgo",required=false) Integer callNumAgo,
			@RequestParam(value="callNumAfter",required=false) Integer callNumAfter,
			@RequestParam(value="notFollowNumAgo",required=false) Integer notFollowNumAgo,
			@RequestParam(value="notFollowNumAfter",required=false) Integer notFollowNumAfter,
			@RequestParam(value="regDateStart",required=false) String regDateStart,
			@RequestParam(value="regDateEnd",required=false) String regDateEnd,
			@RequestParam(value="startLoginTime",required=false) String startLoginTime,
			@RequestParam(value="endLoginTime",required=false) String endLoginTime,
			@RequestParam(value="opUserKy",required=false) Integer opUserKy,
			@RequestParam(value="deptId",required=false) Integer deptId,
			@RequestParam(value="recommCodes",required=false) String recommCodes) {
		
		Map<String,Object> map=new HashMap();
		/*if(customerName!=null && !customerName.equals("")){ 
			map.put("customerName",customerName);
		}*/
		if(userName!=null && !userName.equals("")){
			map.put("userName",userName);
		}
		if(status!=null && !status.equals("")){
			map.put("namestatus",status);
		}
		if(isbank!=null && !isbank.equals("")){
			map.put("isbank", isbank);
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		if(customerstatus!=null && !customerstatus.equals("")){//客户状态
			map.put("customerstatus", customerstatus);
		}
		if(startTime!=null && !startTime.equals("")){
			map.put("startTime",startTime);
		}
		if(endTime!=null && !endTime.equals("")){
			map.put("endTime",endTime);
		}
		if(startAppointDate!=null && !startAppointDate.equals("")){
			map.put("startAppointDate",startAppointDate);
		}
		if(endAppointDate!=null && !endAppointDate.equals("")){
			map.put("endAppointDate",endAppointDate);
		}
		if(startCallDate!=null && !startCallDate.equals("")){
			map.put("startCallDate",startCallDate);
		}
		if(endCallDate!=null && !endCallDate.equals("")){
			map.put("endCallDate",endCallDate);
		}
		if(type!=null && !type.equals("")){
			map.put("type",type);
		}
		if(startMoney!=null && !startMoney.equals("")){
			map.put("startMoney",Integer.valueOf(startMoney));
		}
		if(endMoney!=null && !endMoney.equals("")){
			map.put("endMoney",Integer.valueOf(endMoney));
		}
		if(lastcall!=null && !lastcall.equals("")){
			map.put("lastcall",lastcall);
		}
		if(Utils.isObjectNotEmpty(mobilePhone)){
			map.put("mobilePhone", mobilePhone);
		}
		if(Utils.isObjectNotEmpty(recommCodes)){
			map.put("recommCodes", recommCodes);
		}
		
		SysUsersVo usersVo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		if(usersVo.getRoleName().equals("电销总监") || usersVo.getRoleName().equals("系统管理员")){
			if(!Utils.isBlank(deptId)){
				map.put("deptIds", new Integer[]{deptId});
			}else{
				map.put("deptIds", new Integer[]{0,1,2});
			}
		}else if(Utils.isObjectNotEmpty(usersVo.getDeptId())){
			if(!Utils.isBlank(deptId)){
				map.put("deptIds", new Integer[]{deptId});
			}else{
				map.put("deptIds", new String[]{usersVo.getDeptId()});
			}
		}else{
			map.put("deptIds", new Integer[]{0,1,2});
		}

		if(startLoginTime!=null && !startLoginTime.equals("")){
			map.put("startLoginTime",startLoginTime);
		}
		if(endLoginTime!=null && !endLoginTime.equals("")){
			map.put("endLoginTime",endLoginTime);
		}
		
		if(Utils.isObjectNotEmpty(channelName)) map.put("channelName", channelName.split(","));
		if(null != channelType) map.put("channelType", channelType);
		if(customerType !=null && !customerType.equals("")) map.put("customerType", customerType.split(","));
		if(callNumAgo != null) map.put("callNumAgo", callNumAgo);
		if(null != callNumAfter) map.put("callNumAfter", callNumAfter);
		if(notFollowNumAgo != null) map.put("notFollowNumAgo", notFollowNumAgo);
		if(null != notFollowNumAfter) map.put("notFollowNumAfter", notFollowNumAfter);
		if(Utils.isObjectNotEmpty(regDateStart)) map.put("regDateStart", regDateStart);
		if(Utils.isObjectNotEmpty(regDateEnd)) map.put("regDateEnd", regDateEnd);
		if(null != opUserKy) map.put("opUserKy", opUserKy);
		
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= drMemberService.queryMember(map);
		int count=drMemberService.queryMemberCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	 /**
	  * 导出客户
	  * @return
	 * @throws UnsupportedEncodingException 
	  */
	 @RequestMapping("/exportQueryCustomer")
	 public ModelAndView exportQueryCustomer(HttpServletRequest reg,Integer page,Integer rows,
			 @RequestParam(value="customerName",required=false) String customerName,
				@RequestParam(value="userName",required=false) String userName,
				@RequestParam(value="status",required=false) String status,
				@RequestParam(value="customerstatus",required=false) String customerstatus,
				@RequestParam(value="startTime",required=false) String startTime,
				@RequestParam(value="endTime",required=false) String endTime,
				@RequestParam(value="startAppointDate",required=false) String startAppointDate,
				@RequestParam(value="endAppointDate",required=false) String endAppointDate,
				@RequestParam(value="startCallDate",required=false) String startCallDate,
				@RequestParam(value="endCallDate",required=false) String endCallDate,
				@RequestParam(value="type",required=false) String type,
				@RequestParam(value="isbank",required=false) String isbank,
				@RequestParam(value="startMoney",required=false) String startMoney,
				@RequestParam(value="endMoney",required=false) String endMoney,
				@RequestParam(value="lastcall",required=false) String lastcall,
				@RequestParam(value="mobilePhone",required=false) String mobilePhone,
				@RequestParam(value="channelName",required=false) String channelName,
				@RequestParam(value="channelType",required=false) Integer channelType,
				@RequestParam(value="customerType",required=false) String customerType,
				@RequestParam(value="callNumAgo",required=false) Integer callNumAgo,
				@RequestParam(value="callNumAfter",required=false) Integer callNumAfter,
				@RequestParam(value="notFollowNumAgo",required=false) Integer notFollowNumAgo,
				@RequestParam(value="notFollowNumAfter",required=false) Integer notFollowNumAfter,
				@RequestParam(value="regDateStart",required=false) String regDateStart,
				@RequestParam(value="regDateEnd",required=false) String regDateEnd,
				@RequestParam(value="startLoginTime",required=false) String startLoginTime,
				@RequestParam(value="endLoginTime",required=false) String endLoginTime,
				@RequestParam(value="opUserKy",required=false) Integer opUserKy,
				@RequestParam(value="deptId",required=false) Integer deptId,
				@RequestParam(value="recommCodes",required=false) String recommCodes) throws UnsupportedEncodingException {
			
			Map<String,Object> map=new HashMap();
			try {
				if(customerName!=null && !customerName.equals("")) map.put("customerName",new String(customerName.getBytes("ISO-8859-1"),"utf-8"));
				if(userName!=null && !userName.equals(""))	map.put("userName",new String(userName.getBytes("ISO-8859-1"),"utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(status!=null && !status.equals("")){
				map.put("namestatus",status);
			}
			if(isbank!=null && !isbank.equals("")){
				map.put("isbank", isbank);
			}
			if(page == null){
				page = PageInfo.DEFAULT_PAGE_ON;
			}
			if(rows == null){
				rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
			}
			if(customerstatus!=null && !customerstatus.equals("")){//客户状态
				map.put("customerstatus", customerstatus);
			}
			if(startTime!=null && !startTime.equals("")){
				map.put("startTime",startTime);
			}
			if(endTime!=null && !endTime.equals("")){
				map.put("endTime",endTime);
			}
			if(startAppointDate!=null && !startAppointDate.equals("")){
				map.put("startAppointDate",startAppointDate);
			}
			if(endAppointDate!=null && !endAppointDate.equals("")){
				map.put("endAppointDate",endAppointDate);
			}
			if(startCallDate!=null && !startCallDate.equals("")){
				map.put("startCallDate",startCallDate);
			}
			if(endCallDate!=null && !endCallDate.equals("")){
				map.put("endCallDate",endCallDate);
			}
			if(type!=null && !type.equals("")){
				map.put("type",type);
			}
			if(startMoney!=null && !startMoney.equals("")){
				map.put("startMoney",Integer.valueOf(startMoney));
			}
			if(endMoney!=null && !endMoney.equals("")){
				map.put("endMoney",Integer.valueOf(endMoney));
			}
			if(lastcall!=null && !lastcall.equals("")){
				map.put("lastcall",lastcall);
			}
			if(Utils.isObjectNotEmpty(mobilePhone)){
				map.put("mobilePhone", mobilePhone);
			}
			if(Utils.isObjectNotEmpty(recommCodes)){
				map.put("recommCodes", recommCodes);
			}
			if(startLoginTime!=null && !startLoginTime.equals("")){
				map.put("startLoginTime",startLoginTime);
			}
			if(endLoginTime!=null && !endLoginTime.equals("")){
				map.put("endLoginTime",endLoginTime);
			}
			
			SysUsersVo usersVo = (SysUsersVo)reg.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			if(usersVo.getRoleName().equals("电销总监") || usersVo.getRoleName().equals("系统管理员")){
				if(!Utils.isBlank(deptId)){
					map.put("deptIds", new Integer[]{deptId});
				}else{
					map.put("deptIds", new Integer[]{0,1,2});
				}
			}else if(Utils.isObjectNotEmpty(usersVo.getDeptId())){
				if(!Utils.isBlank(deptId)){
					map.put("deptIds", new Integer[]{deptId});
				}else{
					map.put("deptIds", new String[]{usersVo.getDeptId()});
				}
			}else{
				map.put("deptIds", new Integer[]{0,1,2});
			}
			
			if(Utils.isObjectNotEmpty(channelName)) map.put("channelName", new String(channelName.getBytes("ISO-8859-1"),"utf-8").split(","));
			if(null != channelType) map.put("channelType", channelType);
			if(customerType !=null && !customerType.equals("")) map.put("customerType", customerType.split(","));
			if(callNumAgo != null) map.put("callNumAgo", callNumAgo);
			if(null != callNumAfter) map.put("callNumAfter", callNumAfter);
			if(notFollowNumAgo != null) map.put("notFollowNumAgo", notFollowNumAgo);
			if(null != notFollowNumAfter) map.put("notFollowNumAfter", notFollowNumAfter);
			if(Utils.isObjectNotEmpty(regDateStart)) map.put("regDateStart", regDateStart);
			if(Utils.isObjectNotEmpty(regDateEnd)) map.put("regDateEnd", regDateEnd);
			if(null != opUserKy) map.put("opUserKy", opUserKy);
			
		/*	PageInfo pi = new PageInfo(page,rows);
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit()); */
			List<Map<String,Object>> list= drMemberService.queryMember(map);
	
		String[] title = new String[]{"客户姓名","客户手机","推荐码","是否绑卡","是否充值","是否投资","累计投资金额","注册来源","注册时间","渠道类型","渠道名称","沟通次数","最近登录时间","最后沟通时间","*天未跟进","客户类型","电销人员","操作人","操作时间"};
		Integer[] columnWidth = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String,Object> maps : list){
			lc = new ArrayList<Object>();
			lc.add(maps.get("realname"));
			lc.add(maps.get("mobilePhone"));
			lc.add(maps.get("recommCodes"));
			lc.add(maps.get("isbank"));
			lc.add(maps.get("isRecharge"));
			lc.add(maps.get("isInvestment"));
			lc.add(maps.get("moneysum"));
			lc.add(maps.get("regfrom"));
			lc.add(maps.get("regdate"));
			lc.add(maps.get("channelType"));
			lc.add(maps.get("channelName"));
			lc.add(maps.get("meets"));
			lc.add(maps.get("lastlogintime"));
			lc.add(maps.get("lastCallTime"));
			lc.add(maps.get("notFollowNum"));
			lc.add(maps.get("customerType"));
			lc.add(maps.get("username"));
			lc.add(maps.get("createUsername"));
			lc.add(maps.get("createDate"));
			tableList.add(lc);
		}
		map.clear();
		map.put("excelName", "Claims_info_"+System.currentTimeMillis()+".xls");
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	
	 /**
	  * 冻结客户
	  * @param map
	  * @return
	  * @throws IOException 
	  * @throws JsonMappingException 
	  * @throws JsonParseException 
	  */
	 /*@RequestMapping("/freezeCustomer")
	 @ResponseBody
	 public String freezeCustomer(HttpServletRequest req,
			 @RequestParam(value="userKy",required=false)Integer userKy,
			 @RequestParam("conData") String conData) throws JsonParseException, JsonMappingException, IOException {
		 DrCustomerAllot[] list = new ObjectMapper().readValue(conData,DrCustomerAllot[].class);
		 List<Integer> uids = new ArrayList<Integer>();
		 for (DrCustomerAllot drCustomerAllot : list) {
			 if(drCustomerAllot.getDeptId() !=0){
				 uids.add(drCustomerAllot.getUid());
			 }else{
				 return "0";
			 }
		 }
		 if(!Utils.isEmptyList(uids)){
			 Map<String,Object> map = new HashMap<String, Object>();
			 map.put("allot", 100);
			 map.put("uids", uids);
			 drcustomerAllotService.freezeCustomerByUid(map);
		 }
		 
		
		 return "success";
	 }*/
	/**
	 * 分配客户
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/saveCustomerAllot")
	@ResponseBody
	public String saveCustomerAllot(HttpServletRequest req,
			@RequestParam(value="userKy",required=false)Integer userKy,
			@RequestParam("conData") String conData) throws JsonParseException, JsonMappingException, IOException {
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map [] list = new ObjectMapper().readValue(conData,Map[].class);
		List<Integer> uids = new ArrayList<Integer>();
		for (Map map : list) {
			if(!map.get("deptId").equals("0")){
				//判断用户是否分配过
				DrCustomerAllot allot=drcustomerAllotService.selectCustomerByUid(map);
				if(allot!=null) return "1";
				
				DrCustomerAllot drCustomerAllot=new DrCustomerAllot();
				drCustomerAllot.setUid(Integer.parseInt(map.get("uid").toString()));
				drCustomerAllot.setCreateUserKy(usersVo.getUserKy().intValue());
				drCustomerAllot.setUserKy(userKy);
				drcustomerAllotService.insert(drCustomerAllot);
				uids.add(drCustomerAllot.getUid());
			}else{
				return "0";
			}
		}
		 if(!Utils.isEmptyList(uids)){
			 Map<String,Object> map = new HashMap<String, Object>();
			 SysUsersVo vo = sysUsersVoService.selectByPrimaryKey(userKy);
			 map.put("allot", vo.getDeptId());
			 map.put("uids", uids);
			 drMemberService.updateMemberAllot(map);
			 drcustomerAllotService.freezeCustomer(map);
			 
		 }	
		return "success";
	}
	
	/**
	 * 取消分配客户
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/deleteCustomerAllot")
	@ResponseBody
	public String deleteCustomerAllot(HttpServletRequest req,
			@RequestParam("conData") String conData) throws JsonParseException, JsonMappingException, IOException {
		Map [] list = new ObjectMapper().readValue(conData,Map[].class);
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		for (Map map : list) {
			if(map.get("id")!=null){
				DrCustomerAllot drCustomerAllot=new DrCustomerAllot();
				drCustomerAllot.setId(Integer.parseInt(map.get("id").toString()));
				drCustomerAllot.setCreateUserKy(Integer.valueOf(usersVo.getUserKy().toString()));
				drCustomerAllot.setUid(Integer.parseInt(map.get("uid").toString()));
				drcustomerAllotService.insertHistroy(drCustomerAllot);
				drcustomerAllotService.delete(drCustomerAllot);
			}else{
				return "0";
			}
		}
		return "success";
	}
	
	/**
	 * 跳转客户管理页面
	 */
	@RequestMapping("/toCustomerManagement")
	public String toCustomerManagement(HttpServletRequest req,Map<String,Object> model){//跳转客户分配页面
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map<String,Object> map=new HashMap<String,Object>();
		if(Utils.isObjectNotEmpty(usersVo)){
			try {
				model.put("wincallType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("wincallType")));
				model.put("deptCode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("deptCode")));
				model.put("deptId", usersVo.getDeptId());
				model.put("roleName", usersVo.getRoleName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			map = sysUsersVoService.selectJsCallNull(usersVo.getUserKy());
			if(map!=null){
			model.putAll(map);
			}
		}
		return "system/customer/customerManagement";
	}
	

	/**
	 * 查询属于自己的客户
	 * @param map
	 * @return
	 */
	@RequestMapping("/selCustomerManagement")
	@ResponseBody
	public String selCustomerManagement(HttpServletRequest req,Integer page,Integer rows,
			/*@RequestParam(value="customerName",required=false) String customerName,*/
			@RequestParam(value="mobilePhone",required=false) String mobilePhone,
			@RequestParam(value="recommCodes",required=false) String recommCodes,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="startAppointDate",required=false) String startAppointDate,
			@RequestParam(value="endAppointDate",required=false) String endAppointDate,
			@RequestParam(value="startLoginTime",required=false) String startLoginTime,
			@RequestParam(value="endLoginTime",required=false) String endLoginTime,
			@RequestParam(value="startCallDate",required=false) String startCallDate,
			@RequestParam(value="endCallDate",required=false) String endCallDate,
			@RequestParam(value="startRegDate",required=false) String startRegDate,
			@RequestParam(value="endRegDate",required=false) String endRegDate,
			@RequestParam(value="startshouldTime",required=false) String startshouldTime,
			@RequestParam(value="endshouldTime",required=false) String endshouldTime,
			@RequestParam(value="investAmountSumFirst",required=false) String investAmountSumFirst,
			@RequestParam(value="investAmountSumEnd",required=false) String investAmountSumEnd,
			@RequestParam(value="balanceFirst",required=false) String balanceFirst,
			@RequestParam(value="balanceEnd",required=false) String balanceEnd,
			@RequestParam(value="investCountFirst",required=false) String investCountFirst,
			@RequestParam(value="investCountEnd",required=false) String investCountEnd,
			@RequestParam(value="channelName",required=false) String channelName,
			@RequestParam(value="channelType",required=false) String channelType,
			@RequestParam(value="debarExperience",required=false) String debarExperience,
			@RequestParam(value="deptId",required=false) Integer deptId,
			@RequestParam(value="callNumAgo",required=false) Integer callNumAgo,
			@RequestParam(value="notFollowNumAgo",required=false) Integer notFollowNumAgo,
			@RequestParam(value="notFollowNumAfter",required=false) Integer notFollowNumAfter,
			@RequestParam(value="customerType",required=false) String customerType,
			@RequestParam(value="callNumAfter",required=false) Integer callNumAfter
			) {
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map<String,Object> map=new HashMap<String,Object>();
		if(usersVo.getRoleName().equals("电销") 
				|| !(usersVo.getRoleName().equals("系统管理员") ||
					usersVo.getRoleName().equals("电销总监") ||
					usersVo.getRoleName().equals("电销经理") ||
					usersVo.getRoleName().equals("电销主管"))){
			
			map.put("userKy",usersVo.getUserKy());
		}
		/*if(customerName!=null && !customerName.equals("")){
			map.put("customerName",customerName);
		}*/
		if(mobilePhone!=null && !mobilePhone.equals("")){
			map.put("mobilePhone",mobilePhone);
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		if(startTime!=null && !startTime.equals("")){
			map.put("startTime",startTime);
		}
		if(endTime!=null && !endTime.equals("")){
			map.put("endTime",endTime);
		}
		if(recommCodes!=null && !recommCodes.equals("")){
			map.put("recommCodes",recommCodes);
		}
		if(startRegDate!=null && !startRegDate.equals("")){
			map.put("startRegDate",startRegDate);
		}
		if(endRegDate!=null && !endRegDate.equals("")){
			map.put("endRegDate",endRegDate);
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		
		if(status!=null && !status.equals("")){//客户状态
			map.put("status", status);
		}
		if(startAppointDate!=null && !startAppointDate.equals("")){
			map.put("startAppointDate",startAppointDate);
		}
		if(endAppointDate!=null && !endAppointDate.equals("")){
			map.put("endAppointDate",endAppointDate);
		}
	
		if(startLoginTime!=null && !startLoginTime.equals("")){
			map.put("startLoginTime",startLoginTime);
		}
		if(endLoginTime!=null && !endLoginTime.equals("")){
			map.put("endLoginTime",endLoginTime);
		}
		if(startCallDate!=null && !startCallDate.equals("")){
			map.put("startCallDate",startCallDate);
		}
		if(endCallDate!=null && !endCallDate.equals("")){
			map.put("endCallDate",endCallDate);
		}
		if(startshouldTime!=null && !startshouldTime.equals("")){
			map.put("startshouldTime",startshouldTime);
		}
		if(endshouldTime!=null && !endshouldTime.equals("")){
			map.put("endshouldTime",endshouldTime);
		}
		if(investAmountSumFirst!=null && !investAmountSumFirst.equals("")){
			map.put("investAmountSumFirst",investAmountSumFirst);
		}
		if(investAmountSumEnd!=null && !investAmountSumEnd.equals("")){
			map.put("investAmountSumEnd",investAmountSumEnd);
		}
		if(balanceFirst!=null && !balanceFirst.equals("")){
			map.put("balanceFirst",balanceFirst);
		}
		if(balanceEnd!=null && !balanceEnd.equals("")){
			map.put("balanceEnd",balanceEnd);
		}
		if(Utils.isObjectNotEmpty(investCountFirst)){
			map.put("investCountFirst", investCountFirst);
		}
		if(Utils.isObjectNotEmpty(investCountEnd)){
			map.put("investCountEnd", investCountEnd);
		}
		if(Utils.isObjectNotEmpty(channelName)){
			map.put("channelName", channelName);
		}
		if(Utils.isObjectNotEmpty(channelType)){
			map.put("channelType", channelType);
		}
		if(Utils.isObjectNotEmpty(debarExperience)){
			map.put("debarExperience", debarExperience);
		}
		if(usersVo.getRoleName().equals("电销总监") || usersVo.getRoleName().equals("系统管理员")){
			if(!Utils.isBlank(deptId)){
				map.put("deptIds", new Integer[]{deptId});
			}else{
				map.put("deptIds", new Integer[]{0,1,2});
			}
		}else if(Utils.isObjectNotEmpty(usersVo.getDeptId())){
			if(!Utils.isBlank(deptId)){
				map.put("deptIds", new Integer[]{deptId});
			}else{
				map.put("deptIds", new String[]{usersVo.getDeptId()});
			}
		}else{
			map.put("deptIds", new Integer[]{0,1,2});
		}

		if(callNumAgo!=null){
			map.put("callNumAgo",callNumAgo);
		}
		if(callNumAfter!=null ){
			map.put("callNumAfter",callNumAfter);
		}
		if(customerType !=null && !customerType.equals("")) map.put("customerType", customerType.split(","));
		if(notFollowNumAgo != null) map.put("notFollowNumAgo", notFollowNumAgo);
		if(null != notFollowNumAfter) map.put("notFollowNumAfter", notFollowNumAfter);
		List<Map<String,Object>> list= drMemberService.selCustomerManagement(map);
		int count=drMemberService.selCustomerManagementCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	
	/**
	 * 根据id查询推荐码
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/selectByPrimaryKey")
	@ResponseBody
	public String selectByPrimaryKey(
			@RequestParam(value="uid",required=false) Integer uid) {
		DrMember drMember=drMemberService.selectByPrimaryKey(uid);
		return drMember.getRecommCodes();
	}
	
	/**
	 * 根据id查询手机号
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/selectByUid")
	@ResponseBody
	public String selectByUid(
			@RequestParam(value="uid",required=false) Integer uid) {
		DrMember drMember=drMemberService.selectByPrimaryKey(uid);
		return drMember.getMobilephone();
	}
	
	/**
	 * 查询字典
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryByCategory")
	@ResponseBody
	public String queryByCategory(String category) {
		List<SysChooseOption> list=sysChooseOptionService.queryByCategory(category);
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	 /**
	  * 冻结客户
	  * @param map
	  * @return
	  * @throws IOException 
	  * @throws JsonMappingException 
	  * @throws JsonParseException 
	  */
	 @RequestMapping("/freezeCustomerByUid")
	 @ResponseBody
	 public String freezeCustomerByUid(HttpServletRequest req,
			 @RequestParam("conData") String conData
			) throws JsonParseException, JsonMappingException, IOException{
		 SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		 Map [] list = new ObjectMapper().readValue(conData,Map[].class);
		 String str=drcustomerAllotService.freezeCustomerByUid(list,Integer.valueOf(usersVo.getUserKy().toString()));

		 return str;
	 }
	 
	/**
	 * 投资客户账户查询
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/getFundsRecord")	
	@ResponseBody
	public String getFundsRecord(HttpServletRequest req,Integer rows,Integer page,String mobilePhone ,
			String realname,String tradeType,String startaddTime,String endaddTime,String type,String recommCodes,Integer isFuiou){
		PageInfo pi = new PageInfo(page, rows);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("isFuiou",isFuiou);
		if(realname!=null && !realname.equals("")){
			map.put("realname",realname);
		}
		if(mobilePhone!=null && !mobilePhone.equals("")){
			map.put("mobilephone",mobilePhone);
		}
		if(tradeType!=null && !tradeType.equals("")){
			map.put("tradeType",tradeType);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		if(recommCodes !=null && !recommCodes.equals("")){
			map.put("recommCodes",recommCodes);
		}
		if(type!=null && !type.equals("")){
			map.put("type",type);
		}
		List<Map<String, Object>> list= drMemberFundsRecordService.getFundsRecord(map);
		int count=drMemberFundsRecordService.getFundsRecordCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 统计收入支出
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/fundsRecordSum")
	@ResponseBody
	public Map<String,Object> fundsRecordSum(HttpServletRequest req,String mobilePhone ,
			String realname,String tradeType,String startaddTime,String endaddTime,String type,String recommCodes) {
		
		
		
		BigDecimal sr=new BigDecimal(0);
		BigDecimal zc=new BigDecimal(0);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		if(realname!=null && !realname.equals("")){
			map.put("realname",realname);
		}
		if(mobilePhone!=null && !mobilePhone.equals("")){
			map.put("mobilephone",mobilePhone);
		}
		if(tradeType!=null && !tradeType.equals("")){
			map.put("tradeType",tradeType);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		
		if(recommCodes !=null && !recommCodes.equals("")){
			map.put("recommCodes",recommCodes);
		}
		if(type!=null && !type.equals("")){
			if(type.equals("1")){
				sr=drMemberFundsRecordService.getFundsRecordSRSum(map);
			}else{
				zc=drMemberFundsRecordService.getFundsRecordZCSum(map);
			}
			map.put("type",type);
		}else{
			sr=drMemberFundsRecordService.getFundsRecordSRSum(map);
			zc=drMemberFundsRecordService.getFundsRecordZCSum(map);
		}	
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("sr", sr);
		result.put("zc", zc);
		return result;
	}

	
	/**
	 * 投资客户账户查询
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/toFundsRecordList")	
	public String toFundsRecordList(HttpServletRequest req){
		return "system/finance/fundsRecordList";
	}
	
	
	/**
	 * 投资客户账户查询导出
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/exportFundsRecord")	
	@ResponseBody
	public ModelAndView exportFundsRecord(HttpServletRequest req,String mobilePhone,
			String realname,String tradeType,String startaddTime,String endaddTime,String type,String recommCodes) throws UnsupportedEncodingException{
		
		
		
		BigDecimal sr=new BigDecimal(0);
		BigDecimal zc=new BigDecimal(0);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		if(realname!=null && !realname.equals("")){
			map.put("realname",new String(realname.getBytes("iso-8859-1"),"utf-8"));
		}
		if(mobilePhone!=null && !mobilePhone.equals("")){
			map.put("mobilephone",mobilePhone);
		}
		if(tradeType!=null && !tradeType.equals("")){
			map.put("tradeType",tradeType);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		if(type!=null && !type.equals("")){
			map.put("type",type);
		}
		if(recommCodes !=null && !recommCodes.equals("")){
			map.put("recommCodes",recommCodes);
		}
		map.put("offset",0); 
		map.put("limit",100000000);
		List<Map<String, Object>> list= drMemberFundsRecordService.getFundsRecord(map);
		
		
		String[] title = new String[]{"客户姓名","手机号","时间","交易类型","收入","支出","余额","备注"};
		Integer[] columnWidth = new Integer[]{20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map m : list){
			lc = new ArrayList<Object>();
			lc.add(m.get("realname")==null?"":m.get("realname"));//客户姓名
			lc.add(m.get("mobilePhone")==null?"":m.get("mobilePhone"));//项目产品编号
			lc.add(m.get("addTime")==null?"":m.get("addTime"));//时间
			lc.add(m.get("tradeType")==null?"":m.get("tradeType"));//交易类型
			lc.add(m.get("sr")==null?"":m.get("sr"));//收入
			lc.add(m.get("zc")==null?"":m.get("zc"));//支出
			lc.add(m.get("balance")==null?"":m.get("balance"));//余额
			lc.add(m.get("remark")==null?"":m.get("remark"));//备注	
			tableList.add(lc);
			if(Utils.isObjectNotEmpty(m.get("sr"))){
				sr=sr.add(new BigDecimal(m.get("sr").toString()));
			}
			if(Utils.isObjectNotEmpty(m.get("zc"))){
				zc=zc.add(new BigDecimal(m.get("zc").toString()));
			}	
		}
		lc = new ArrayList<Object>();
		lc.add("小计");//客户姓名
		lc.add("");//项目产品编号
		lc.add("");//时间
		lc.add("");//交易类型
		lc.add(sr);//收入
		lc.add(zc);//支出
		lc.add("");//余额
		lc.add("");//备注	
		tableList.add(lc);
		Map<String,Object> param=new HashMap();
		param.put("excelName", "fundsRecord_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
		}
	
	/**
	 * 回款明细
	 * @param req
	 * @param rows
	 * @param page
	 * @param id
	 * @return
	 */
	@RequestMapping("/getInvestRepayinfo")	
	@ResponseBody
	public String getInvestRepayinfo(HttpServletRequest req,Integer id){
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("investId", id);
		List<Map<String, Object>> list= drProductInvestService.getInvestRepayinfo(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 剩余回款 已回款合计
	 * @return
	 */
	@RequestMapping(value= "/getInvestRepayinfoResidueSum")
	@ResponseBody
	public Map<String,Object> getInvestRepayinfoResidueSum(HttpServletRequest req,Integer id
		) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("investId", id);
		Map<String, Object> residueresult=drProductInvestService.getInvestRepayinfoResidueSum(map);
		Map<String, Object> alreadyresult=drProductInvestService.getInvestRepayinfoAlreadySum(map);
		
		Map<String, Object> result=new HashMap<>();
		result.put("residueprincipal", residueresult.get("residueprincipal"));
		result.put("residueinterest", residueresult.get("residueinterest"));
		result.put("factPrincipal", alreadyresult.get("factPrincipal"));
		result.put("factInterest", alreadyresult.get("factInterest"));
		return result;
	}
	 
	 /**
	  * 工具箱页面
	  * @return
	  */
	@RequestMapping("/toToolList")
	public String toToolList(){
		return "system/customer/toolList";
	}
	
	/**
	 * 工具箱错误列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/getJsError")
	@ResponseBody
	public String getJsError(HttpServletRequest req,
			@RequestParam(value="code",required=false) String code
			) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(code!=null && code!=""){
			map.put("code", code);
		}
		List<Map<String,Object>> list= jsErrorService.getJsError(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	
	/**
	 * 回款计算器
	 * @param map
	 * @return
	 */
	@RequestMapping("/getcalculator")
	@ResponseBody
	public String getcalculator(HttpServletRequest req,
			@RequestParam(value="type",required=false) String type,
			@RequestParam(value="manner",required=false) String manner,
			@RequestParam(value="amount",required=false) BigDecimal amount,
			@RequestParam(value="rate",required=false) BigDecimal rate,
			@RequestParam(value="deadline",required=false) BigDecimal deadline
			) {
		List<Map<String, Object>> list=new ArrayList<>();
		if(type!=null && type.equals("1") && deadline!=null && amount!=null && rate!=null){//到期付息还本
			BigDecimal interest=amount.multiply(rate).multiply(deadline).divide(new BigDecimal(360),2,BigDecimal.ROUND_DOWN).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
			Map<String, Object> m1=new HashMap<>();
			m1.put("periods", 1);
			m1.put("principal", amount);
			m1.put("interest", interest);
			m1.put("sum", amount.add(interest));
			list.add(m1);
			Map<String, Object> m2=new HashMap<>();
			m2.put("periods", "合计");
			m2.put("principal", amount);
			m2.put("interest", interest);
			m2.put("sum", amount.add(interest));
			list.add(m2);
		}else if(type!=null && type.equals("2") && deadline!=null && amount!=null && rate!=null){//等本等息
			BigDecimal sumprincipal=new BigDecimal(0);
			BigDecimal suminterest=new BigDecimal(0);
			BigDecimal sum=new BigDecimal(0);
			BigDecimal interest=new BigDecimal(0);
			BigDecimal money=new BigDecimal(0);
			BigDecimal countinterest=amount.multiply(rate).multiply(deadline).divide(new BigDecimal(360),2,BigDecimal.ROUND_DOWN).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);//当期利息

			int month=0;

			if(manner!=null && manner.equals("1")){//按月
				month=deadline.intValue()/30;			
			}else if(manner!=null && manner.equals("2")){//按周
				month=deadline.intValue()/7;	
			}
			for (int i = 0; i <month; i++) {
				Map<String, Object> m3=new HashMap<>();
				interest=countinterest.divide(new BigDecimal(month),2,BigDecimal.ROUND_DOWN);//当期利息
				money=amount.divide(new BigDecimal(month),2,BigDecimal.ROUND_DOWN);//当期本金
				if(i==month-1){//第一期
					money=amount.subtract(money.multiply(new BigDecimal(month-1)));
					interest=countinterest.subtract(interest.multiply(new BigDecimal(month-1)));
					m3.put("principal", money);
					m3.put("interest", interest);
				}else{
					m3.put("principal", money);
					m3.put("interest", interest);
				}
				m3.put("periods", i+1);
				m3.put("sum", money.add(interest));
				list.add(m3);
				
				sumprincipal=sumprincipal.add(money);//累计本金
				suminterest=suminterest.add(interest);//累计利息
				sum=sum.add(money).add(interest);
			}
			Map<String, Object> m4=new HashMap<>();
			m4.put("periods", "合计");
			m4.put("principal", sumprincipal);
			m4.put("interest", suminterest);
			m4.put("sum", sum);
			list.add(m4);
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 提交更改银行卡信息
	 * @param req
	 * @param DrCarryParameter
	 * @param name
	 * @param claimsFiles
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addCardNo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addCardNo(HttpServletRequest request,
			MultipartFile file1,MultipartFile file2,String card_no,String mobile,String bank_cd,String city_id,String mobilephone,String remark,Integer uid) throws Exception{
			BaseResult br = new BaseResult();	
			String reg = ".+(.jpg)$";
			Pattern pattern = Pattern.compile(reg);
			if(Utils.isObjectNotEmpty(file1)){
				Matcher matcher = pattern.matcher(file1.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确图片1的格式!");
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = file1.getSize();
				if(fileSize>1024*3000){
					br.setSuccess(false);
					br.setErrorMsg("图片1不能大于3M！");
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}else{
				br.setErrorMsg("人物、身份证、新银行卡合照不能为空!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			if(Utils.isObjectNotEmpty(file2)){
				Matcher matcher = pattern.matcher(file2.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确图片2的格式!");
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = file2.getSize();
				if(fileSize>1024*3000){
					br.setSuccess(false);
					br.setErrorMsg("图片2不能大于3M！");
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}else{
				//查询用户是否投资过  cece
				int total = drProductInfoService.getDealRecord(uid);
				if(total != 0){
					br.setErrorMsg("人物、身份证、旧银行卡合照不能为空!");
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			Map<String, Object> map=new HashMap<>();
			map.put("card_no", card_no);
			map.put("mobile", mobile);
			map.put("bank_cd", bank_cd);
			map.put("city_id", city_id);
			map.put("login_id", mobilephone);
			map.put("mchnt_txn_ssn", Utils.createOrderNo(6, 1, null));
			map.put("remark", remark);
			
			CommonsMultipartFile cf1= (CommonsMultipartFile)file1; //这个myfile是MultipartFile的
	        DiskFileItem fi1 = (DiskFileItem)cf1.getFileItem(); 
	        File file3 = fi1.getStoreLocation(); 
	        file1.transferTo(file3);
	        
	        File file4 = null;
	        if(Utils.isObjectNotEmpty(file2)){
		        CommonsMultipartFile cf2= (CommonsMultipartFile)file2; //这个myfile是MultipartFile的
		        DiskFileItem fi2 = (DiskFileItem)cf2.getFileItem(); 
		        file4 = fi2.getStoreLocation(); 
		        file2.transferTo(file4);
	        }
	        br=FuiouConfig.userChangeCard(map,file3,file4);
			if(!br.isSuccess()){
				return "操作失败:"+br.getErrorMsg();
			}
			return "操作成功，待审核";
	}
	
	
	/**
	 *查询银行卡审核进度
	 */
	@RequestMapping(value="/selectCardNo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String selectCardNo(HttpServletRequest request,
			String mobilephone,String bank_no_mchnt_txn_ssn){
		Map<String, Object> map=new HashMap<>();
		map.put("login_id", mobilephone);
		map.put("txn_ssn", bank_no_mchnt_txn_ssn);
		map.put("mchnt_txn_ssn", Utils.createOrderNo(6, 1, null));
		BaseResult br=FuiouConfig.queryChangeCard(map);
		if(br.isSuccess()){
			if(br.getMap().get("examine_st").equals("1")){//审核成功
				return "审核成功";
			}else if(br.getMap().get("examine_st").equals("2")){//审核失败
				return "审核失败";
			}else{
				return "待审核";
			}
		}else{
			return "查询失败";
		}
	}
	
	/**
	 * 根据手机号查询推荐码
	 * @param request
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping(value="/selectByMobilephone")
	@ResponseBody
	public String selectByMobilephone(HttpServletRequest request,String mobilephone){
		DrMember drMember=new DrMember();
		drMember.setMobilephone(mobilephone);
		drMember= drMemberService.selectByMobilephone(drMember);
		if(drMember!=null){
			return drMember.getRecommCodes();//推荐码
		}
		return "该手机号未注册";
		
	}
}

