package com.jsjf.controller.account.funds;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;

/**
 * 我的资产-我的投资
 * @author He
 *
 */
@RequestMapping("/investCenter")
@Controller
public class InvestCenterController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrMemberFundsService drMemberFundsService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberFavourableService drMemberFavourableService;
	
	
	@RequestMapping("/repayInfoDetail")
	@ResponseBody
	public Map<String,Object> repayInfoDetail(HttpServletRequest req,@RequestBody Map<String, Object> param){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			DrMember member =  (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			if(param.get("id") == null && "".equals(param.get("id"))){
				map.put("msg", "系统错误");
				map.put("success", false);
				return map;
			}
			//获取投资
			map.put("id", param.get("id"));
			map.put("uid", member.getUid());
			DrProductInvest invest = drProductInvestService.selectByParam(map);
			//获取产品
			DrProductInfo info = drProductInfoService.selectProductDetailById(invest.getPid());
			//获取回款列表
			List<Map<String,Object>> list = drProductInvestRepayInfoService.selectRepayInfoDetail(invest.getId()); 
			
			map.put("result", list);
			map.put("productName", info.getFullName());//产品名称
			map.put("rate", info.getRate());//年化率
			map.put("activityRate", info.getActivityRate());//年化率
			map.put("type", info.getType());//产品类型
			map.put("repayType", info.getRepayType());//回款类型
			
			int period = info.getRepayType() ==2 || info.getRepayType() ==4 ? 30 : (info.getRepayType() == 3 ? 7 : info.getDeadline());
			int deadline = info.getDeadline()/period;//期限数	
			map.put("deadLine", info.getDeadline());//期限
			
			map.put("amount", invest.getFactAmount());//实投
			map.put("status", invest.getStatus());//投资状态
			map.put("pid", info.getId());//产品id
			map.put("uid", member.getUid());//uid
			
			int i = 0;
			BigDecimal residualPri = BigDecimal.ZERO;
			for (Map<String, Object> m : list) { //计算周期， 应收总计，剩余
				m.put("index", ++i);
				residualPri = residualPri.add( (BigDecimal)m.get("shouldPrincipal"));
				m.put("shouldSum", ((BigDecimal)m.get("shouldPrincipal")).add((BigDecimal)m.get("shouldInterest")));
				m.put("residualPrincipal", invest.getAmount().subtract(residualPri) );
			}
			map.put("interest", invest.getFactInterest());//实际利息
			map.put("shouldAmount", invest.getFactAmount().add(invest.getFactInterest()));//息实际本
			if(Utils.isObjectNotEmpty(invest.getExperience())){//体验金
				Map<String,Object> maps = new HashMap<String, Object>();
				maps.put("uid", invest.getUid());
				maps.put("ids", invest.getExperience().split(","));
				maps = drMemberFavourableService.selectExperSumAmountIdByMap(maps);
				if(Utils.isObjectNotEmpty(maps) && Utils.isObjectNotEmpty(maps.get("experAmount")))
				map.put("amount", maps.get("experAmount"));//体验金
			}
			map.put("success", true);
		
		/**
		 * 添加E签宝业务逻辑start
		 */
		String url = ConfigUtil.getEsignUrl();
		String projectId=ConfigUtil.getProjectId();
		long timestamp=System.currentTimeMillis();
		DrProductInvest drpi=drProductInvestService.getEvidByInvestId(param.get("id").toString());//获取e签id
		if (Utils.isObjectNotEmpty(drpi)) {
			String signature = Utils.getXtimevaleSignature("id="+drpi.getEvid()+"&projectId="+projectId+"&timestamp="+timestamp+"&reverse=false&type=ID_CARD&number="+member.getIdCards(),
					ConfigUtil.getProjectSecret(), ConfigUtil.getAlgorithm(), ConfigUtil.getEncoding());
			//id=O918397270204903425&projectId=1111563517&timestamp=1510987348003&reverse=false&type=CODE_USC&number=591101009HEC6BFG3T
			map.put("signature",url+"?id="+drpi.getEvid()+"&projectId="+projectId+"&timestamp="+timestamp+"&reverse=false&type=ID_CARD&number="+member.getIdCards()+"&signature="+signature);
		}else {
			map.put("signature","");
		}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			map.put("msg", "系统错误");
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/productList")
	@ResponseBody
	public String productList(HttpServletRequest req,PageInfo pi,@RequestBody Map<String, Object> param){
		BaseResult br = new BaseResult();
		try {
			DrMember member = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			param.put("status", param.get("status"));
			param.put("uid", member.getUid());
			Utils.getObjectFromMap(pi, param);
			br = drProductInvestService.selectInvestLogByParam(param, pi);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取我的投资失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("/investStat")
	@ResponseBody
	public String investStat(HttpServletRequest req){
		BaseResult br = new BaseResult();
		try {
			DrMember drMember = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			DrMemberFunds funds = drMemberFundsService.selectDrMemberFundsByUid(drMember.getUid());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("investAmount", funds.getInvestAmount().add(funds.getFuiou_investAmount()));
			result.put("wprincipal", funds.getWprincipal().add(funds.getFuiou_wprincipal()));
			BigDecimal collectAmount = funds.getWinterest()
					.add(funds.getWpenalty()).add(funds.getWprincipal()).add(funds.getFuiou_winterest())
					.add(funds.getFuiou_wpenalty()).add(funds.getFuiou_wprincipal());
			result.put("collectAmount", collectAmount);
			result.put("profit", funds.getInvestProfit().add(funds.getSpreadProfit())
					.add(funds.getFuiou_investProfit().add(funds.getFuiou_spreadProfit())));
			br.setSuccess(true);
			br.setMap(result);
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("获取我的投资统计失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		
		return JSON.toJSONString(br);
	}
}
