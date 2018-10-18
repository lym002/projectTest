package com.jsjf.controller.account.funds;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.service.member.DrMemberFundsRecordService;

/**
 * 我的资产-资产记录
 * @author DELL
 *
 */
@RequestMapping("/assetRecord")
@Controller
public class AssetRecordController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrMemberFundsRecordService drMemberFundsRecordService;
	
	/**
	 * 领取奖金
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping("/getTheRewards")
	@ResponseBody
	public String getTheRewards(HttpServletRequest req,Integer uid,Integer afid){
		BaseResult br = drMemberFundsRecordService.getTheRewards(uid,afid);
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest req,PageInfo pi ,Integer uid,Integer tradeType){
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("uid", uid);
			param.put("tradeType", tradeType==0?null:tradeType);
			br = drMemberFundsRecordService.selectMemberFundsRecordByParam(param, pi);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("资产记录查询失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
		
	}
	
	/**
	 * 累计收益
	 * @param req
	 * @param pi
	 * @param uid
	 * @return
	 */
	@RequestMapping("/getAccumulatedIncome")
	@ResponseBody
	public String getAccumulatedIncome(HttpServletRequest req,PageInfo pi ,Integer uid){
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(Utils.isObjectEmpty(uid)){
				br.setErrorMsg("uid不能为空");
				br.setErrorCode("9998");
				br.setSuccess(false);
			}
			param.put("uid", uid);
			br = drMemberFundsRecordService.selectAccumulatedIncomeByUid(param, pi);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("累计收益查询失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	@RequestMapping("/getAccumulatedClassification")
    @ResponseBody
    public String getAccumulatedClassification(HttpServletRequest req,Integer uid){
        BaseResult br = new BaseResult();
        try{
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("uid", uid);
            br = drMemberFundsRecordService.selectAccumulatedClassification(param);
        }catch (Exception e){
            log.error("累计分类查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }
}
