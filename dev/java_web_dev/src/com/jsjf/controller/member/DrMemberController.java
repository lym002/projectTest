package com.jsjf.controller.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.SensorsAnalytics.SensorsAnalytics;
import com.SensorsAnalytics.exceptions.InvalidArgumentException;
import com.jsjf.common.SensorsAnalyticsUtil;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.model.member.DrMemberRecommended;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.member.DrMemberBaseInfoService;
import com.jsjf.service.member.DrMemberCpsFavourableRuleService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.member.JsMemberInfoService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@RequestMapping("/member")
@Controller
public class DrMemberController {
	@Autowired
	DrMemberFavourableService drMemberFavourableService;
	
	@Autowired
	DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	DrProductInfoService drProductInfoService;
	@Autowired
	DrMemberCpsFavourableRuleService drMemberCpsFavourableRuleService;
	@Autowired
	DrActivityParameterService drActivityParameterService;
	@Autowired
	DrMemberService drMemberService;
	@Autowired
	JsMemberInfoService jsMemberInfoService;
	@Autowired
	private RedisClientTemplate redis;
	@Autowired
	private DrMemberBaseInfoService drMemberBaseInfoService;
    @Autowired
    private DrMemberRecommendedDAO drMemberRecommendedDAO;
	/**
	 * 存管开户直连
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/openAccount", method = RequestMethod.POST)
	@ResponseBody
	public String openAccount(HttpServletRequest req,@RequestBody Map<String,Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//实名
        Map<String, Object> properties = new HashMap<String, Object>();
        SensorsAnalytics instance1 = SensorsAnalyticsUtil.getInstance();//绑卡
        Map<String, Object> properties1 = new HashMap<String, Object>();
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
        try {
            //神策埋点
            String bankName = drMemberService.selectBankName(param.get("parent_bank_id").toString());
            properties.put("CertificateType","身份证");
            properties1.put("BankCard",param.get("capAcntNo"));
            properties1.put("BankName",bankName);
			//		
			if(Utils.isObjectNotEmpty(m) && 0 == m.getIsFuiou()){				
				Map<String,String> map = new HashMap<String, String>();	
				if(Utils.isObjectNotEmpty(m.getRealName())){
					param.put("cust_nm", m.getRealName());
					param.put("certif_id", m.getIdCards());
				}
				Map<String,Object> cardMap = new HashMap<String, Object> ();
				cardMap.put("idCards", param.get("certif_id").toString());
				Integer count = drMemberBaseInfoService.queryMemberBaseInfoCountByMap(cardMap);
				if(count!=null && m.getRealVerify() ==0 && count > 0){
					br.setErrorCode("1003");
					br.setErrorMsg("身份证已存在");
					br.setSuccess(false);
                    properties.put("IsSuccess",false);
                    properties1.put("IsSuccess",false);

                    return JSON.toJSONString(br);
				}				
				
				if(Utils.isObjectNotEmpty(param.get("cust_nm")) 
						&& Utils.isObjectNotEmpty(param.get("certif_id"))						
						&& Utils.isObjectNotEmpty(param.get("city_id"))
						&& Utils.isObjectNotEmpty(param.get("parent_bank_id"))
						&& Utils.isObjectNotEmpty(param.get("capAcntNo"))
						&& Utils.isObjectNotEmpty(param.get("password"))
						&& param.get("password").equals(param.get("rpassword"))){
					
					
					map.put("cust_nm", param.get("cust_nm").toString());//客户姓名
					map.put("certif_id", param.get("certif_id").toString());//身份证号码/证件
					map.put("mobile_no", m.getMobilephone());//手机号码
					map.put("city_id", param.get("city_id").toString());//开户行行别
					map.put("parent_bank_id", param.get("parent_bank_id").toString());//开户行行别
					map.put("capAcntNo", param.get("capAcntNo").toString());//帐号
					map.put("mchnt_txn_ssn", Utils.createOrderNo(6, m.getUid(), null));//流水号
					map.put("password", param.get("password").toString());//交易密码
					
					br = FuiouConfig.reg(map);
					
					if(br.isSuccess()){
						String resp_code = (String) br.getMap().get("resp_code");
						if(resp_code.equals("0000")){
							
							drMemberService.openAccount(JSONObject.fromObject(map));
							m = drMemberService.selectByPrimaryKey(m.getUid());
							if(m != null)
								req.getSession().setAttribute(ConfigUtil.FRONT_LOGIN_USER, m);
                            properties.put("IsSuccess",true);
							br.setMsg("开户成功");
                            properties.put("IsSuccess",true);
                            properties1.put("IsSuccess",true);
						}else{
							br.setSuccess(false);
							br.setErrorMsg(resp_code+":"+(String) br.getMap().get("resp_desc")+("5344".equals(resp_code) || "5343".equals(resp_code)?"-->请咨询客服":""));
                            properties.put("IsSuccess",false);
                            properties1.put("IsSuccess",false);
						}
					}else{
						br.setErrorMsg("系统错误:"+br.getErrorMsg());
                        properties.put("IsSuccess",false);
                        properties1.put("IsSuccess",false);
					}
				}else{
					br.setErrorCode("1002");
					br.setErrorMsg("参数错误");
                    properties.put("IsSuccess",false);
                    properties1.put("IsSuccess",false);
				}
			}else{
				br.setErrorCode("1001");
				br.setErrorMsg("用户已开通");
                properties.put("IsSuccess",false);
                properties1.put("IsSuccess",false);
			}
            //配置用户信息
            DrMember drMember = drMemberService.selectForUpDateByPrimaryKey(m.getUid());
            properties2.put("uid",drMember.getUid());
            DrMemberRecommended drMemberRecommended = drMemberRecommendedDAO.selectByUid(properties2);
			if (drMemberRecommended == null) {
				drMemberRecommended = new DrMemberRecommended();
			}
				properties2.clear();
				String sex = "";
				if (drMember.getSex() == 1)
					sex = "男";
				if (drMember.getSex() == 2)
					sex = "女";
				if (drMember.getSex() == 0)
					sex = "保密";
				properties2.put("name", drMember.getRealName());      // 姓名
				properties2.put("sex", sex);  // 性别
				properties2.put("IDnumber", drMember.getIdCards());   // 身份证号
				properties2.put("bank", bankName);     // 银行名称
				properties2.put("yearOfBirth", Integer.parseInt(Utils.format(drMember.getBirthDate(), "yyyy")));// 用户的出生日期，特别注意，这个地方填入年龄是不合适的，因为年龄会随着时间而变化
				properties2.put("inviter", drMemberRecommended.getReferrerId() == null ? 0 : drMemberRecommended.getReferrerId());//邀请人 如果没有为0
				properties2.put("Utype", "个人");//用户类型
				properties2.put("Ulevel", "VIP0");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorCode("9999");//系统错误
		}finally {
            try {
                instance.track(String.valueOf(m.getUid()), true, "RealnameAuthentication" ,properties);
                instance1.track(String.valueOf(m.getUid()), true, "BindingCards" ,properties1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance.flush();
            instance1.flush();
        }
        br.setMap(null);
		return JSON.toJSONString(br);
	}
	/**
	 * 存管开户
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/openAccountSignature", method = RequestMethod.POST)
	@ResponseBody
	public String openAccountSignature(HttpServletRequest req,@RequestBody Map<String,Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		try {
			
			if(Utils.isObjectNotEmpty(m) && 0 == m.getIsFuiou()){
				DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
				Map<String, Object> map = new HashMap<String, Object>();
				if(Utils.isObjectEmpty(member.getRealName()))
					member.setRealName((String)param.get("realName"));
				if(Utils.isObjectEmpty(member.getIdCards())){
					member.setIdCards((String)param.get("idCards"));
					map.put("idCards", member.getIdCards());
					Integer count = drMemberBaseInfoService.queryMemberBaseInfoCountByMap(map);
					if(count!=null && count>0){
						br.setErrorCode("1003");
						br.setErrorMsg("身份证已存在");
						return JSON.toJSONString(br);
					}
				}
				
				map.clear();
				map.put("signature", "");
				map.put("fuiouUrl", FuiouConfig.WEBREGURL);
				
				if(Utils.isObjectNotEmpty(member.getRealName()) && Utils.isObjectNotEmpty(member.getIdCards())){
					param.put("mobile_no", member.getMobilephone());
					param.put("cust_nm", member.getRealName());
					param.put("certif_id", member.getIdCards());
					param.put("id", member.getUid());
					
					
					map.put("signature", FuiouConfig.webReg(param));
					
					br.setMap(map);
					br.setSuccess(true);
				}else{
					br.setErrorCode("1002");
					br.setErrorMsg("身份证,用户名不能为空");
				}
			}else{
				br.setErrorCode("1001");
				br.setErrorMsg("用户已开通");
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorCode("9999");//系统错误
			e.printStackTrace();
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	/**
	 * 立即变现
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getUse", method = RequestMethod.POST)
	@ResponseBody
	public String getUse (HttpServletRequest req,@RequestBody Map<String,Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		Integer deadline = null;
		try {
			if(param.containsKey("deadline") && Utils.isObjectNotEmpty(param.get("deadline"))){
				deadline = Integer.parseInt(param.get("deadline").toString());
			}else{				
				if(Utils.isObjectNotEmpty(m)){				
					if(param.containsKey("fid") && Utils.isObjectNotEmpty(param.get("fid"))){
						DrMemberFavourable fav = drMemberFavourableService.selectByPrimaryKey(Integer.parseInt(param.get("fid").toString()));
						deadline = fav.getProductDeadline();
					}else{
						br.setMsg("没有参数");
						br.setErrorCode("1003");
					}
				}else{
					br.setMsg("未登录");
					br.setErrorCode("1001");
				}
			}
			//红包				
			if(!Utils.isBlank(deadline)){
				//根据规则获得产品
				Map<String,Object> map = new HashMap<>();
				map.put("orderStr", " order by deadline,status,id desc");
				map.put("offset", 0);
				map.put("limit", 1);
				map.put("type", 2);
				map.put("atid", " and atid is null");
				if(param.containsKey("fid")){
					map.put("deadlines", deadline);
					map.put("status", 5);
				}else{
					map.put("deadline", deadline);
					map.put("statuses", new int[]{5,6,8,9});
				}
				List<DrProductInfo> list = drProductInfoService.selectProductbyMap(map);
				if(!Utils.isEmptyList(list)){
					param.put("pid", list.get(0).getId());
					param.put("surplusAmount", list.get(0).getSurplusAmount());
					br.setMap(param);
					br.setSuccess(true);
				}else{
					param.put("pid", -1);
					br.setMap(param);
					br.setSuccess(true);
				}
			}else{
				br.setMsg("没有参数");
				br.setErrorCode("1002");
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorCode("9999");//系统错误
			e.printStackTrace();
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 促复投接口
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getPromoteRedelivery", method = RequestMethod.POST)
	@ResponseBody
	public String getPromoteRedelivery(HttpServletRequest req){
		Map<String,Object> param = new HashMap<>();
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		param.put("isRedPacket", false);
		try {
			if(Utils.isObjectNotEmpty(m)){//登录
				List<DrMemberFavourable> redPacketList = drMemberFavourableService
						.selectFavourableOrderByAmountExpireDate(m.getUid());
				if(!Utils.isEmptyList(redPacketList)){//是否有红包
					param.put("isRedPacket", true);//有红包
					param.put("redPacketList",redPacketList);	
					//未来7天回款笔数	
					param.put("returnedCount",drProductInvestRepayInfoService.selectReturnedCount(m.getUid()));
					br.setSuccess(true);
					br.setMap(param);
					
				}else{
					br.setMsg("没有红包");
					br.setErrorCode("1002");
				}
			}else{
				br.setMsg("未登录");
				br.setErrorCode("1001");
			}
		} catch (Exception e) {
			br.setErrorCode("9999");//系统错误
			e.printStackTrace();
			br.setSuccess(false);
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}
	
	
	@RequestMapping(value = "/getOpenRed", method = RequestMethod.POST)
	@ResponseBody
	public String getOpenRed(HttpServletRequest req){
		//拆红包统计
		redis.incr("chaiHongBaoTongJi");
		Map<String,Object> param = new HashMap<>();
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		br.setErrorCode("9999");
		return JSON.toJSONString(br);
//		try {
//			if (Utils.isObjectNotEmpty(m)) {// 登录
//				boolean lockFlag = redis.tryLock("fuTou.uid."+m.getUid(), 3, TimeUnit.SECONDS,true);
//				if(lockFlag){
//					// 前五天后五天回款笔数
//					List<DrProductInvestRepayInfo> drProductInvestRepayInfo = drProductInvestRepayInfoService
//							.selectPayment(m.getUid());
//					// 前五天后五天回款总和
//					if (drProductInvestRepayInfo.size() > 0) {
//						BigDecimal shouldPrincipalCount = BigDecimal.ZERO;
//						for (DrProductInvestRepayInfo drProductInvestRepayInfo2 : drProductInvestRepayInfo) {
//							shouldPrincipalCount = shouldPrincipalCount.add(drProductInvestRepayInfo2.getShouldPrincipal());
//						}
//						// 满足条件，发红包
//						if (shouldPrincipalCount.compareTo(new BigDecimal(0)) == 1) {
//							Map<String,Object> paramMap = new HashMap<String,Object>();
//							//查询用户是否首投
//							int isFirst = drMemberService.selectFirstPayMent(m.getUid());
//							paramMap.put("uid", m.getUid());
//							paramMap.put("shouldPrincipalCount", shouldPrincipalCount.intValue());
//							paramMap.put("isFirst", isFirst ==0?1:0);
//							List<DrMemberCpsFavourableRule> drMemberCpsFavourableRuleList = drMemberCpsFavourableRuleService.selectActivityByCps(paramMap);
//							drActivityParameterService.insertActivityParameter(drMemberCpsFavourableRuleList, m.getUid(), shouldPrincipalCount,m.getMobilephone());
//							for (DrProductInvestRepayInfo info : drProductInvestRepayInfo) {
//								info.setIsgrant(1);
//								drProductInvestRepayInfoService.update(info);
//							}
//							List<DrMemberFavourable> redPacketList = drMemberFavourableService
//									.selectFavourableOrderByAmountExpireDate(m.getUid());
//							param.put("redPacketList", redPacketList);
//							//查询红包记录
//							List<DrMemberFavourable> redMsg = drMemberFavourableService.selectRedMsg(redPacketList.get(0).getAmount().intValue());
//							param.put("redMsg", redMsg);
//							//拆红包统成功计
//							redis.incr("chaiHongBaoTongJi_success");
//							//复投红包机会统计次数
//							redis.srem("fuTouSet", m.getUid().toString());
//							redis.lpush("fuTouFLHBList", m.getUid().toString()+","+Utils.format(new Date(), "yyyy-MM-dd"));
//							
//							br.setSuccess(true);
//							br.setMap(param);
//						}
//					}
//				}
//			} else {
//				br.setMsg("未登录");
//				br.setErrorCode("1001");
//			}
//		} catch (Exception e) {
//			br.setErrorCode("9999");// 系统错误
//			e.printStackTrace();
//			br.setSuccess(false);
//			e.printStackTrace();
//		}
//		return JSON.toJSONString(br);
	}

	private BigDecimal BigDecimal(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取收获地址信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getReceiptAddress", method = RequestMethod.POST)
	@ResponseBody
	public String getReceiptAddress(HttpServletRequest req,@RequestBody Map<String,Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(Utils.isObjectEmpty(m.getUid())){
				br.setErrorCode("9998");
				br.setSuccess(false);
				br.setErrorMsg("未登录");
				return JSON.toJSONString(br);
			}
			map.put("uid", m.getUid());
			map.put("investId", param.get("investId"));
			map.put("prizeType", 1);
			JsMemberInfo jsMemberInfo = jsMemberInfoService.selectMemberInfoByMap(map);
			map.put("prizeType", 0);
			JsMemberInfo jsMemberInfo2 = jsMemberInfoService.selectMemberInfoByMap(map);
			map.clear();
			map.put("fictitious", jsMemberInfo);
			map.put("materialObject", jsMemberInfo2);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}
	/**
	 * 修改用户收货地址
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateReceiptAddress", method = RequestMethod.POST)
	@ResponseBody
	public String updateReceiptAddress (HttpServletRequest req,@RequestBody Map<String,Object> param){
		BaseResult br = new BaseResult();
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		if(Utils.isObjectEmpty(m)){
			br.setMsg("未登录");
			br.setErrorCode("9998");
			br.setSuccess(false);
			return JSON.toJSONString(br);
		}
		try {
			JsMemberInfo jsMemberInfo = new JsMemberInfo();
			if (Utils.isObjectNotEmpty(param.get("name"))) {
				jsMemberInfo.setName((String) param.get("name"));
			}
			if (Utils.isObjectNotEmpty(param.get("phone"))) {
				jsMemberInfo.setPhone((String) param.get("phone"));
			}
			if (Utils.isObjectNotEmpty(param.get("address"))) {
				jsMemberInfo.setAddress((String) param.get("address"));
			}
			jsMemberInfo.setUpdateTime(new Date());
			jsMemberInfo.setUid(m.getUid());
			//jsMemberInfo.setInvestId(Integer.parseInt(param.get("investId").toString()));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uid", m.getUid());
		//	map.put("investId", param.get("investId"));
			JsMemberInfo memberInfo = jsMemberInfoService.selectMemberInfoByMap(map);
			if(Utils.isObjectNotEmpty(memberInfo)){
				jsMemberInfoService.updateJsMemberInfo(jsMemberInfo);
			}
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");// 系统错误
			br.setSuccess(false);
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 添加用户收货地址
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/insertReceiptAddress", method = RequestMethod.POST)
	@ResponseBody
	public String insertReceiptAddress (HttpServletRequest req,@RequestBody Map<String,Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		if(Utils.isObjectEmpty(m)){
			br.setMsg("未登录");
			br.setErrorCode("9998");
			br.setSuccess(false);
			return JSON.toJSONString(br);
		}
		try {
			JsMemberInfo jsMemberInfo = new JsMemberInfo();
			jsMemberInfo.setUid(m.getUid());
			jsMemberInfo.setName((String)param.get("name"));
			jsMemberInfo.setPhone((String)param.get("phone"));
			jsMemberInfo.setAddress((String)param.get("address"));
			//jsMemberInfo.setInvestId(Integer.parseInt(param.get("investId").toString()));
			br = jsMemberInfoService.insertJsMemberInfo(jsMemberInfo);
		} catch (Exception e) {
			br.setErrorCode("9999");// 系统错误
			br.setSuccess(false);
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}
	/**
	 * 查询总注册人数
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/selectDrmembercount", method = RequestMethod.POST)
	@ResponseBody
	public String selectDrmembercount(HttpServletRequest req){
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			int count = drMemberService.selectDrmembercount();
			
			map.put("count", count);
			br.setSuccess(true);
			br.setMap(map);
			
		} catch (Exception e) {
			br.setErrorCode("9999");// 系统错误
			br.setSuccess(false);
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}
}