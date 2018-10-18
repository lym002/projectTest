package com.jsjf.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.ActivityFriendService;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@Controller
@RequestMapping("/gukaimingTest")
public class TestController {
	private static Logger log = Logger.getLogger(TestController.class);

	@Autowired
	private DrActivityParameterService drActivityParameterService;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private  ActivityFriendService activityFriendService;
	@Autowired
	public  RedisClientTemplate redisClientTemplate;
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	public DrMemberCarryService drMemberCarryService;
	
		
	//进入查看redis值
	@RequestMapping("/toSelectRedis")
	public String toSelectRedis(HttpServletRequest req){
		if(!authIsTrue(req)) {
			return "没有权限";
		}
		return "system/sys/user/selectRedis";
	}
	
	/**
	 * 通过json 设置redis
	 */
	@RequestMapping("/selectRedis")
	@ResponseBody
	public PageInfo selectRedis(HttpServletRequest req,String redisKey,Integer type,Integer valueType){
		PageInfo pi = new PageInfo();
		List<Map<String,Object>> valList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		if(!authIsTrue(req)) {
			map = new HashMap<String, Object>();
			map.put("value", "你没有权限");
			valList.add(map);
		}else{
			if(!Utils.strIsNull(redisKey)){
				if(0 == type){
					if(valueType ==0){
						byte[] b = redisClientTemplate.get(redisKey.getBytes());
						map = new HashMap<String, Object>();
						map.put("value", JSONObject.fromObject(SerializeUtil.unserialize(b)).toString());
						valList.add(map);
					}else if(valueType ==1){
						String b = redisClientTemplate.get(redisKey);
						map = new HashMap<String, Object>();
						map.put("value", b);
						valList.add(map);
					}
					
				}else if(1 == type){
					if(valueType ==0){
						List<byte[]> list = redisClientTemplate.lrange(redisKey.getBytes(), 0, -1);
						if(!Utils.isEmptyList(list)){
							for(byte[] b : list){
								map = new HashMap<String, Object>();
								map.put("value", JSONObject.fromObject(SerializeUtil.unserialize(b)).toString());
								valList.add(map);
							}
						}
					}else if(valueType ==1){
						List<String> list = redisClientTemplate.lrange(redisKey, 0, -1);
						if(!Utils.isEmptyList(list)){
							for(String b : list){
								map = new HashMap<String, Object>();
								map.put("value", b);
								valList.add(map);
							}
						}
					}
				}else if(2 == type ){
					if(valueType ==0){
						Set<byte[]> set = redisClientTemplate.hkeys(redisKey.getBytes());
						if(Utils.isObjectNotEmpty(set)){
							for(byte[] b : set){
								byte[] value = redisClientTemplate.hget(redisKey.getBytes(), b);
								map = new HashMap<String, Object>();
								map.put("key", JSONObject.fromObject(SerializeUtil.unserialize(b)).toString());
								map.put("value", JSONObject.fromObject(SerializeUtil.unserialize(value)).toString());
								valList.add(map);
							}
						}
					}else if(valueType ==1){
						Set<String> set = redisClientTemplate.hkeys(redisKey);
						if(Utils.isObjectNotEmpty(set)){
							for(String b : set){
								String value = redisClientTemplate.hget(redisKey, b);
								map = new HashMap<String, Object>();
								map.put("key", b);
								map.put("value", value);
								valList.add(map);
							}
						}
					}
				}else if(3 == type ){
					if(valueType ==0){
						Set<byte[]> set = redisClientTemplate.smembers(redisKey.getBytes());
						if(Utils.isObjectNotEmpty(set)){
							for(byte[] b : set){
								map = new HashMap<String, Object>();
								map.put("value", JSONObject.fromObject(SerializeUtil.unserialize(b)).toString());
								valList.add(map);
							}
						}
					}else if(valueType ==1){
						Set<String> set = redisClientTemplate.smembers(redisKey);
						if(Utils.isObjectNotEmpty(set)){
							for(String b : set){
								map = new HashMap<String, Object>();
								map.put("value", b);
								valList.add(map);
							}
						}
					}
				}
			}
		}
		pi.setRows(valList);
		pi.setTotal(valList.size());
		return pi;
	}
	
	/**
	 * 通过json 设置redis
	 */
	@RequestMapping("/setRedisType53Pid")
	public void setRedisType53Pid(HttpServletRequest req,Integer[] pid){
		if(!authIsTrue(req)) {
			return ;
		}
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		if(usersVo.getRoleKy() == 1 && Utils.isObjectNotEmpty(pid) && pid.length > 0){			
			Map<String, Object> mapFreeze = new HashMap<String, Object>();
			mapFreeze.put("type", 53);
			
			for (Integer i : pid) {
				if(Utils.isBlank(i))
					continue;
				mapFreeze.put("pid",i);
				redisClientTemplate.lpush("regAndVerifySendRedUidList_bak53".getBytes(), SerializeUtil.serialize(mapFreeze));
			}
			
		}
	
		
	}
	
	
	//进入解除预授权页面
	@RequestMapping("/toPreAuthCancel")
	public String toPreAuthCancel(HttpServletRequest req){
		if(!authIsTrue(req)) {
			return "没有权限";
		}
		return "system/sys/user/preAuthCancel";
	}
	
	//撤销预授权
	@RequestMapping("preAuthCancel")
	@ResponseBody
	public BaseResult preAuthCancel(HttpServletRequest req,String str){
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)) {
			result.setErrorMsg("没有权限");
		}
		try {
			Map<String,Object> paramsPreAuth = new HashMap<String,Object>();
			
			JSONObject json = JSONObject.fromObject(str);
//			str = "{\"contract_no\":\"001411300323\",\"amt\":\"10000\",\"in_cust_no\":\"13736700443\",\"mchnt_cd\":\"0002900F0388471\",\"mchnt_txn_ssn\":\"2017051816172694471620\",\"out_cust_no\":\"18621521684\",\"rem\":\"\",\"signature\":\"\",\"ver\":\"0.44\"}";
			
			paramsPreAuth.put("uid", 0);
			paramsPreAuth.put("memberPhone", json.getString("out_cust_no"));
			paramsPreAuth.put("loanPhones", json.getString("in_cust_no"));
			paramsPreAuth.put("contract_no", json.getString("contract_no"));
			result = FuiouConfig.preAuthCancel(paramsPreAuth);
			if(result.isSuccess()){
				result.setMsg("成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMsg("系统错误");
		}
		return result;
		
	}
	
	/**
	 * 进入执行 
	 * @param req
	 * @return
	 */
	@RequestMapping("/toQuery")
	public String toQuery(HttpServletRequest req){
		if(!authIsTrue(req)) {
			return "没有权限";
		}
		return "system/sys/user/queryController";
	}
	
	/**
	 * 进入黑名单操作
	 * @param req
	 * @return
	 */
	@RequestMapping("queryRedis")
	@ResponseBody
	public BaseResult queryRedis(HttpServletRequest req,String mobile,Integer type) {
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)) {
			result.setErrorMsg("没有权限");
		}
		try {
			
			if(Utils.strIsNull(mobile) || Utils.isBlank(type)){
				result.setErrorMsg("参数错误");
			}else if(type == 1){
				if(redisClientTemplate.sismember("DenyMobile", mobile)){
					result.setMsg("手机存在!");
				}else{
					result.setMsg("手机不存在!");
				}
				result.setSuccess(true);
			}else if(type ==2){
				long l = redisClientTemplate.srem("DenyMobile", mobile);
				if(l==1){
					result.setMsg("手机号已删除");
				}else{
					result.setMsg("手机号不在黑名单中");
				}
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	/**
	 * 进入黑名单操作
	 * @param req
	 * @return
	 */
	@RequestMapping("redisOperate")
	public String redisOperate(HttpServletRequest req) {
		if(!authIsTrue(req)) 
			return "没有权限";
		return "system/sys/user/denyMobile";
	}
	/**
	 * 当日计息失败 ,处理
	 */
	@RequestMapping("queryProductRaise")
	@ResponseBody
	public BaseResult queryProductRaise(HttpServletRequest req) {
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)){
			result.setSuccess(false);
			result.setMsg("没有权限");
		}
		Map<String,Object>  map = new HashMap<String, Object>();
		
		log.info("执行计息任务");
		List<DrProductInfo> list = drProductInfoService.selectRaiseSuccesProductInfo();
		char[] ary2 = {'0','0','0','0'};
		int nums = 0;//记录计息次数
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DrProductInfo drProductInfo = (DrProductInfo) iterator
					.next();
			try {
				int count = drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				if(drProductInfo.getProject_no() != null && !"".equals(drProductInfo.getProject_no())){ 
					//恒丰存管项目报备产品计息
					drProductInfoService.updateFuiouProductToEnd(drProductInfo, ary2, nums);
				}else{
					//其他情况产品计息
					drProductInfoService.updateProductToEnd(drProductInfo, ary2, nums);
				}
				nums = nums + count;
				
			} catch (Exception e) {
				nums += drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				log.error(drProductInfo.getFullName()+"==>计息任务执行失败,下一个产品开始编号为："+nums,e);
				try {
					SmsSendUtil.sendMsg("15221219118,15221219118", drProductInfo.getFullName()+"==>计息任务执行失败");
				} catch (Exception e1) {
					log.error(e1);
				}
			}
		}
		log.info("计息任务完成");
		result.setSuccess(true);
		return result;
	}
	/**
	 * 激活回款
	 * @return
	 */
	@RequestMapping("queryProductRepay")
	@ResponseBody
	public BaseResult queryProductRepay(HttpServletRequest req) {
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)){
			result.setSuccess(false);
			result.setMsg("没有权限");
		}
		log.info("执行回款任务开始");
		List<DrProductInfo> infoList = drProductInvestService.selectExpireProductInfo();
		for(int i = 0; i < infoList.size(); i++){
			DrProductInfo info = infoList.get(i);
			try {
				String d = "2017-05-07 23:23:23";//指定时间之后体验标回款走 存管回款
				boolean f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
				
				//注意:发布凌晨之前,体验标有问题
				if((info.getType() ==5 && f) || (info.getProject_no() != null && !"".equals(info.getProject_no()))){
					//恒丰存管项目报备产品回款
					drProductInvestService.saveInvestRepayByFuiou(info);
				}else{
					//其他情况产品回款
					drProductInvestService.saveInvestRepay(info);
				}
			} catch (Exception e) {
				log.error(info.getFullName()+"==>执行回款任务失败", e);
				try {
					SmsSendUtil.sendMsg("15221219118,15221219118", info.getFullName()+"==>回款任务执行失败");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				result.setSuccess(false);
				result.setErrorMsg("失败");
				return result;
			}
		}
		log.info("执行回款任务结束");
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 激活转账
	 */
	@RequestMapping("queryTransferBmu")
	@ResponseBody
	public BaseResult queryTransferBmu(HttpServletRequest req) {
		BaseResult br = new BaseResult();
		if(!authIsTrue(req)){
			br.setSuccess(false);
			br.setMsg("没有权限");
		}
			
		try {
			br = drProductInfoService.queryTransferBmu();
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg("激活转账失败");
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 好友邀请三重礼返现任务
	 */
	@RequestMapping("insertSendThreePresentToAccount")//定义每天0点触发一次
	@ResponseBody
	public String insertSendThreePresentToAccount(HttpServletRequest req) {
		if(!authIsTrue(req)) 
			return "false";
		
		String n = "";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		List<ActivityFriend> list = activityFriendService.selectObjectByMap(map);
		
		if(!Utils.isObjectEmpty(list)){
			for(ActivityFriend af:list){
				List<Integer> uidList =  activityFriendService.selectIsSend(af.getId());
				if(!Utils.isObjectEmpty(uidList)){
					for(Integer uid:uidList){
						try {
							drActivityParameterService.insertSendThreePresentToAccount(uid, af);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println(n="----好友邀请三重礼返现任务,活动:"+af.getName()+"发放完成");
			}
		}
		try {
			return n==""?"true":new String(n.getBytes("utf-8"),"gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	/**
	 * 优选产品计息
	 * @throws  
	 */
	@RequestMapping("/productRaiseEnd")
	@ResponseBody
	public void productRaiseEnd(HttpServletRequest req,Integer pid) {
		log.info("执行计息任务");
		List<DrProductInfo> list = drProductInfoService.selectRaiseSuccesProductInfo();
		char[] ary2 = {'0','0','0','0'};
		Integer nums = 0;//记录计息次数
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DrProductInfo drProductInfo = (DrProductInfo) iterator
					.next();
			try {
				if(pid !=null &&  drProductInfo.getId().intValue() == pid){
					int count = drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
					if(drProductInfo.getProject_no() != null && !"".equals(drProductInfo.getProject_no())){ 
						//恒丰存管项目报备产品计息
						drProductInfoService.updateFuiouProductToEnd(drProductInfo, ary2, nums);
					}else{
						//其他情况产品计息
						drProductInfoService.updateProductToEnd(drProductInfo, ary2, nums);
					}
					nums = nums + count;
				}
				
			} catch (Exception e) {
				nums += drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				log.error(drProductInfo.getFullName()+"==>计息任务执行失败,下一个产品开始编号为："+nums,e);
				try {
					SmsSendUtil.sendMsg("15221219118,15221219118", drProductInfo.getFullName()+"==>计息任务执行失败");
				} catch (Exception e1) {
					log.error(e1);
				}
			}
		}
		log.info("计息任务完成");	
	}
	/**
	 * 体验标计息  
	 * @throws  
	 */
	@RequestMapping("/productExperience")
	@ResponseBody
	public void productExperience(HttpServletRequest req) {
		if(!authIsTrue(req)) 
			return ;
		log.info("执行体验标计息任务"); 
		
		//这段代码 时间过了就可以删除
		String d = "2017-05-07 23:23:23";//指定时间之后体验标计息走 存管回款
		boolean f = false;
		try {
			f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e2) {
			System.out.println("计息失败...........");
			return ;
		}
		
		List<DrProductInfo> infoList = drProductInfoService.getDrProductInfoExperience();
		if(!Utils.isEmptyList(infoList)){
			char[] ary2 = {'0','0','0','0'};
			int nums = 0;
			for(DrProductInfo pInfo:infoList){
				List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestListByPid(pInfo.getId());
				if(!Utils.isEmptyList(list)){
					try {
						List<DrProductInvest> investList = new ArrayList<>();
						for(int i = 0 ; i < list.size() ; i++){
							investList.add(list.get(i));
							if(i != 0 && i%20 == 0){
								if(f){//存管
									drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
								}else{//平台
									drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
									
								}
								nums = i;
								investList.clear();
							}
						}
						if(!Utils.isEmptyList(investList)){
							if(f){//存管
								drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
							}else{//平台
								drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
								
							}
						}
					} catch (Exception e) {
						log.error("体验标==>计息任务执行失败,下一个产品开始编号为",e);
						try {
							SmsSendUtil.sendMsg("15221219118", "体验标==>计息任务执行失败");
						} catch (Exception e1) {
							log.error(e1);
						}
					}
				}
			}
		}
		log.info("体验标计息任务完成");
	}
	/**
	 * 产品回款
	 */
	@RequestMapping("/productRepay")//凌晨5点执行回款
	@ResponseBody
	public String productRepay(HttpServletRequest req,Integer pid) {
		if(!authIsTrue(req)) 
			return "false";
		try {
			
			log.info("执行回款任务开始");
			List<DrProductInfo> infoList = drProductInvestService.selectExpireProductInfo();
			for(int i = 0; i < infoList.size(); i++){
				DrProductInfo info = infoList.get(i);
				if(pid != null && pid.intValue() == info.getId().intValue()){
					try {
						String d = "2017-05-07 23:23:23";//指定时间之后体验标回款走 存管回款
						boolean f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
						
						//注意:发布凌晨之前,体验标有问题
						if((info.getType() ==5 && f) || (info.getProject_no() != null && !"".equals(info.getProject_no()))){
							//恒丰存管项目报备产品回款
							drProductInvestService.saveInvestRepayByFuiou(info);
						}else{
							//其他情况产品回款
							drProductInvestService.saveInvestRepay(info);
						}
					} catch (Exception e) {
						log.error(info.getFullName()+"==>执行回款任务失败", e);
						try {
							SmsSendUtil.sendMsg("15800784479,15221219118,15221219118", info.getFullName()+"==>回款任务执行失败");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			log.info("执行回款任务结束");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	
	/**
	 * 定义每半小时执行一次好友邀请返现任务
	 */
	@RequestMapping("/insertFriendMemberActivityAmount") 
	@ResponseBody
   	public String insertFriendMemberActivityAmount(HttpServletRequest req) {
		if(!authIsTrue(req)) 
			return "false";
    	try {
//    		drActivityParameterService.insertFriendMemberActivityAmount();
    		
    		
    		//插入好友邀请三重好礼 返现到活动账户
    		drActivityParameterService.insertThreePresent();
    		
    		//三重好礼
    		drActivityParameterService.selectThreePresentTop();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
    	return "success";
   	}
	
	/**
	 * 更新redis里的存管银行卡返回码
	 */
	@RequestMapping("/setFuiouRspCode")
	@ResponseBody
	public String setFuiouRspCode(HttpServletRequest req) {
		if(!authIsTrue(req)) 
			return "false";
		try {
			sysMessageLogService.getFuiouRspCode();
					
				
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	/**
	 * 判断 当前用户登录的ip 是否指定的
	 * @param req
	 * @return
	 */
	private boolean authIsTrue(HttpServletRequest req){
		try {
			String ip = Utils.getIpAddr(req);
			String ips = PropertyUtil.getProperties("nativeIP");
			
			if(Utils.strIsNull(ips)){
				return true;
			}
			String [] ipArray = ips.split(",");
			for (String str : ipArray) {
				if(str.equals(ip))
					return true;
				if(str.equals("*"))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
		
	}
	
	//进入提现同步页面
	@RequestMapping("/toWithdrawals")
	public String toWithdrawals(HttpServletRequest req){
		if(!authIsTrue(req)) {
			return "没有权限";
		}
		return "system/sys/user/withdrawals";
	}

	//提现同步
	@RequestMapping("withdrawals")
	@ResponseBody
	public BaseResult withdrawals(HttpServletRequest req,String str) throws SQLException{
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)) {
			result.setErrorMsg("没有权限");
			result.setSuccess(false);
			return result;
		}
		result=drMemberCarryService.withdrawals(str);
		return result;
		
	}
	
	//进入企业网银充值（回款同步页面
	@RequestMapping("/toQueryRecharge")
	public String toQueryRecharge(HttpServletRequest req){
		if(!authIsTrue(req)) {
			return "没有权限";
		}
		return "system/sys/user/enterpriseRecharge";
	}
	
	//企业网银充值（回款）同步
	@RequestMapping("queryRecharge")
	@ResponseBody
	public BaseResult queryRecharge(HttpServletRequest req,String str) throws SQLException{
		BaseResult result = new BaseResult();
		if(!authIsTrue(req)) {
			result.setErrorMsg("没有权限");
			result.setSuccess(false);
			return result;
		}
		result=drProductInfoService.queryRecharge(str);
		return result;
		
	}
	/**
	 * 促复投
	 * 用来做补发的,必须有权限才可以
	 */
	@RequestMapping("/insertCpsFavourable")
	@ResponseBody
	public String insertCpsFavourable(HttpServletRequest req){
		try {
			System.out.println("补发促复投红包");
			SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(
					ConfigUtil.ADMIN_LOGIN_USER);
			if(!authIsTrue(req) && usersVo.getRoleKy() == 1) {
				drActivityParameterService.insertCpsFavourables();
				System.out.println("补发促复投红包结束");
			}else{
				return "No permissions";
			}
		} catch (Exception e) {
			log.error("促复投红包发放失败", e);
			return "1111";
		}
		return "0000";
	}
}
