package com.jsjf.controller.account.funds;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.product.DrProductInvestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping("/repayInfoDetail")
	@ResponseBody
	public String repayInfoDetail(HttpServletRequest req, Integer uid,Integer id){
		BaseResult br = new BaseResult(); 
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(uid == null || id == null){
				br.setSuccess(false);
				br.setErrorMsg("系统错误");
				return JSON.toJSONString(map);
			}
			//获取投资
			map.put("id", id);
			map.put("uid", uid);
			DrProductInvest invest = drProductInvestService.selectByPrimaryKey(map);
			map.clear();
			//获取产品
			DrProductInfo info = drProductInfoService.selectProductDetailById(invest.getPid());
			//获取回款列表
			List<Map<String,Object>> list = drProductInvestRepayInfoService.selectRepayInfoDetail(invest.getId()); 
			
			map.put("result", list);
			map.put("productName", info.getFullName());//产品名称
			map.put("rate", info.getRate());//年化率
			map.put("repayType", info.getRepayType());//回款类型
			map.put("type", info.getType());//产品类型
			int period = info.getRepayType() ==2 || info.getRepayType() ==4 ? 30 : (info.getRepayType() == 3 ? 7 : info.getDeadline());
			int deadline = info.getDeadline()/period;//期限数	
			map.put("deadLine", info.getDeadline());//期限
			map.put("amount", invest.getFactAmount());//实投
			map.put("status", invest.getStatus());//投资状态
			map.put("pid", info.getId());//产品id
			map.put("uid", uid);//uid
			int i = 0;
			BigDecimal residualPri = BigDecimal.ZERO;
			for (Map<String, Object> m : list) { //计算周期， 应收总计，剩余
				m.put("index", ++i);
				residualPri = residualPri.add( (BigDecimal)m.get("shouldPrincipal"));
				m.put("shouldSum", ((BigDecimal)m.get("shouldPrincipal")).add((BigDecimal)m.get("shouldInterest")));
				m.put("residualPrincipal", invest.getAmount().subtract(residualPri) );
			}
			map.put("interest", invest.getFactInterest());//实际利息
			map.put("shouldAmount", invest.getFactAmount().add(invest.getFactInterest()));//实际本息
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			br.setSuccess(false);
			br.setErrorMsg("系统错误");
		}
		
		return JSON.toJSONString(br);
	}
	
	/**
	 * 我的投资产品
	 * @param req
	 * @param pi
	 * @param uid
	 * @param status
	 * @return
	 */
	@RequestMapping("/productList")
	@ResponseBody
	public String productList(HttpServletRequest req,PageInfo pi,Integer uid, Integer status){
		BaseResult br = new BaseResult();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			if(uid == null ){
				br.setErrorCode("1000");
				br.setSuccess(false);
				br.setErrorMsg("uid不能为空");
				return JSON.toJSONString(br);
			}
			if(status == null){
				br.setErrorCode("1000");
				br.setSuccess(false);
				br.setErrorMsg("status不能为空");
				return JSON.toJSONString(br);
			}
			if(req.getParameter("channel") == null){
				param.put("channel", 1);
			}else{
				Integer channel = Integer.valueOf(req.getParameter("channel").toString());
				param.put("channel", channel);
			}
			//查询在投资产
			if(status == 4){
				param.put("statuses", new Integer[]{0,1});
			}else{
				param.put("status",status);
			}
			param.put("uid", uid);
			pi = drProductInvestService.selectInvestLogByParam(param, pi);
			BigDecimal principal = drProductInvestService.selectUserSumPrincipalByStatus(param);
			BigDecimal interest = drProductInvestService.selectUserSumInterestByStatus(param);
			BigDecimal accumulatedIncome =  drProductInvestService.selectAccumulatedIncomeSumByUid(param);
			param.clear();
			param.put("principal", principal);
			param.put("interest", interest);
			param.put("accumulatedIncome", accumulatedIncome);
			param.put("page", pi);
			br.setMap(param);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取我的投资产品失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 奖品详情
	 * @param req
	 * @param investId
	 * @return
	 */
	@RequestMapping("/prizeInfo")
	@ResponseBody
	public String prizeInfo(HttpServletRequest req, Integer investId,Integer pid){
		BaseResult br = new BaseResult(); 
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("pid", pid);
			map.put("investId", investId);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = drProductInvestService.selectprizeInfoByInvestId(map);
			map.clear();
			map.putAll(list.size()>0?list.get(0):null);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			br.setSuccess(false);
			br.setErrorMsg("系统错误");
		}
		return JSON.toJSONString(br);
	}
    /**
     * 获取投资id标地详情
     * @return
     */
    @RequestMapping("/getInvestInfo")
    @ResponseBody
    public String getInvestInfo(HttpServletRequest req, Integer investId){
        BaseResult br = new BaseResult();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            map.put("investId", investId);
            map = drProductInvestService.selectInvestInfo(map);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setSuccess(false);
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }
}
