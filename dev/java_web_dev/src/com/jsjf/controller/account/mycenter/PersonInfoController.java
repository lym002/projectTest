package com.jsjf.controller.account.mycenter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.system.SysBank;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@RequestMapping("/personInfo")
@Controller
public class PersonInfoController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DrMemberFundsService drMemberFundsService;
	@Autowired
	private DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrMemberBankService drMemberBankService;
	@Autowired
	private DrMemberFavourableService drMemberFavourableService;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	
	/**
	 * 用户登陆首页信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/indexMemberInfo")
	@ResponseBody
	public BaseResult indexMemberInfo(HttpServletRequest req){
		BaseResult br = new BaseResult();
		Map<String,Object> data = new HashMap<String, Object>();
		Map<String,Object> map2 = new HashMap<String, Object>();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		//获取30天内的待收笔数
		data.put("uid", member.getUid());
		data.put("dueIn", drProductInvestRepayInfoService.selectInvestRepayInfoNumsByParam(data));
		//手机号账户余额
//		data.remove("uid");
		data.put("mobilephone", member.getMobilephone());
		data.put("realName", member.getRealName());
		data.put("balance", drMemberFundsService.selectDrMemberFundsByUid(member.getUid()).getFuiou_balance());
		//判断是否是老用户  1不是老用户  2是老用户 3是老用户登录后并且发送红包
		Integer isBypOldUser = member.getIs_byp_old_user();
		if(2==isBypOldUser){
			try {
				//送红包
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("type", 6);
				map.put("uid", member.getUid());
				redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),SerializeUtil.serialize(map));
				//修改用户状态
				member.setIs_byp_old_user(3);
				drMemberService.update(member);
			} catch (Exception e) {
				log.error("老用户送红包异常", e);
				br.setSuccess(false);
			}
			//查询未过期88元红包的状态
			BigDecimal amount = new BigDecimal(88);
			map2.put("uid",member.getUid());
			map2.put("amount",amount);
			DrMemberFavourable drMemberFavourable = drMemberFavourableService.selectNotExpiredAndNotUseByUid(map2);
			data.put("isBypOldUser",member.getIs_byp_old_user());
			if(drMemberFavourable!=null){
				//未失效
				//'状态 0：未使用  1：已使用 2：已过期'
				if(0==drMemberFavourable.getStatus()){
					//未使用,可以展示88元红包
					data.put("isShowRed", true);
				}else{
					data.put("isShowRed", false);
				}
			} else{
				//已失效
				data.put("isShowRed", false);
			}
		}else if(3==isBypOldUser||1==isBypOldUser) {
			//查询未过期88元红包的状态
			BigDecimal amount = new BigDecimal(88);
			map2.put("uid",member.getUid());
			map2.put("amount",amount);
			DrMemberFavourable drMemberFavourable = drMemberFavourableService.selectNotExpiredAndNotUseByUid(map2);
			data.put("isBypOldUser",member.getIs_byp_old_user());
			if(drMemberFavourable!=null){
				//未失效
				//'状态 0：未使用  1：已使用 2：已过期'
				if(0==drMemberFavourable.getStatus()){
					//未使用,可以展示88元红包
					data.put("isShowRed", true);
				}else{
					data.put("isShowRed", false);
				}
			} else{
				//已失效
				data.put("isShowRed", false);
			}
		}
		br.setMap(data);
		br.setSuccess(true);
		log.info(br.toString());
		return br;
	}
	

	
	@RequestMapping("/index")
	@ResponseBody
	public String toPersonInfo(Map<String,Object> model,HttpServletRequest req){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		try {
			DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
			if(null != member.getRealName()){
				model.put("realName","*"+ member.getRealName().substring(member.getRealName().length()-1,member.getRealName().length()));
			}else{
				model.put("realName","");
			}
			if(null != member.getIdCards()){
				model.put("idCards",member.getIdCards().substring(0,4)+"***********"+member.getIdCards().substring(member.getIdCards().length()-3));
			}else{
				model.put("idCards","");
			}
			if(null != member.getBirthDate()){
				model.put("birth",Utils.format(member.getBirthDate(), "yyyy-MM-dd"));
			}
			if(null != member.getSex()){
				model.put("sex", member.getSex());
			}
			if(1 == member.getRealVerify() || member.getIsFuiou() == 1){
				DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
				
				if(Utils.isObjectEmpty(drMemberBank) || 1 == member.getIsFuiou() ){
					 drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				}
				if(Utils.isObjectNotEmpty(drMemberBank)){
					String bankNum = drMemberBank.getBankNum();
					if(0 == member.getIsFuiou()){
						model.put("bankNum", bankNum);
					}else{
						model.put("bankNum", bankNum.substring(0,4)+"******"+bankNum.substring(bankNum.length()-4, bankNum.length()));
					}
					model.put("bankName", drMemberBank.getBankName());
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("bankName", drMemberBank.getBankName());
					SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
					if(Utils.isObjectNotEmpty(sysBank) ){
						model.put("bankCode", sysBank.getBankCode());
						model.put("bankId", sysBank.getId());
					}
				}
				
			}			
			
			model.put("phone",member.getMobilephone().substring(0,3)+"****"+member.getMobilephone().substring(member.getMobilephone().length()-4,member.getMobilephone().length()));
			
			br.setMap(model);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("个人中心查询错误", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
//	@RequestMapping("/updatePersonInfo")
//	@ResponseBody
//	public BaseResult updatePersonInfo(HttpServletRequest req,DrMemberBaseInfo drMemberBaseInfo){
//		BaseResult result = new BaseResult();
//		String CSRFToken = req.getParameter("CSRFToken");
//		if(CSRFToken == null || !CSRFToken.equals(req.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME).toString())){
//			result.setSuccess(false);
//			result.setErrorMsg("修改失败！请重试！");
//			return result;
//		} 
//		
//		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
//		Map<String,Object> map = new HashMap<String, Object>();
//		drMemberBaseInfo.setUid(member.getUid());
//		try {
//			drMemberBaseInfoService.updateMemberBaseInfoByUid(drMemberBaseInfo);
//			map.put("degree", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("degree")).get(drMemberBaseInfo.getDegree()));
//			map.put("gschool", drMemberBaseInfo.getGschool());
//			map.put("marry", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("marry")).get(drMemberBaseInfo.getMarry()));
//			map.put("nowAddress",drMemberBaseInfo.getNowAddress());
//			map.put("industryType",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("industrytype")).get(drMemberBaseInfo.getIndustryType().intValue()));
//			map.put("companySize",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("companysize")).get(drMemberBaseInfo.getCompanySize()));
//			map.put("job",drMemberBaseInfo.getJob());
//			map.put("salary",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("salary")).get(drMemberBaseInfo.getSalary()));
//			result.setSuccess(true);
//			result.setMap(map);
//			result.setMsg("修改成功！");
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setErrorMsg("修改失败！请重试！");
//			log.error(e);
//			e.printStackTrace();
//		}
//		return result;
//	}

}
