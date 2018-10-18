package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrCouponsIssuedRulesDAO;
import com.jsjf.dao.activity.JsGratitudeBlessingDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.JsGratitudeBlessing;
import com.jsjf.service.activity.JsGratitudeBlessingService;

@Service
@Transactional
public class JsGratitudeBlessingServiceImpl implements JsGratitudeBlessingService {
	
	@Autowired
	private JsGratitudeBlessingDAO jsGratitudeBlessingDAO;
	@Autowired
	private DrCouponsIssuedRulesDAO drCouponsIssuedRulesDAO;
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	

	@Override
	public List<JsGratitudeBlessing> selectGratitudeBlessing() {
		Map<String,Object> param = new HashMap<>();
		List<JsGratitudeBlessing> list = new ArrayList<JsGratitudeBlessing>();
		try {
			param.put("status", 1);
			param.put("offset", 0);
			param.put("limit", 100);
			list = jsGratitudeBlessingDAO.selectGratitudeBlessing(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public BaseResult insertGratitudeBlessing(JsGratitudeBlessing vo) {
		BaseResult br = new BaseResult();
		try {
			jsGratitudeBlessingDAO.insertGratitudeBlessing(vo);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setErrorMsg("系统错误");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult updateGratitudeBlessing(JsGratitudeBlessing vo) {
		BaseResult br = new BaseResult();
		try {
			jsGratitudeBlessingDAO.updateGratitudeBlessing(vo);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setErrorMsg("系统错误");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public JsGratitudeBlessing selectGratitudeBlessingByUid(Integer uid) {
		// TODO Auto-generated method stub
		return jsGratitudeBlessingDAO.selectGratitudeBlessingByUid(uid);
	}

	/**
	 * 先查询出来规则，然后根据规则查询出来优惠券返回
	 */
	@Override
	public List<DrActivityParameter> selectGratitudeBlessingFavourable() {
		List<DrActivityParameter> list = new ArrayList<>();
		try {
			Map<String,Object> map = new HashMap<>();
//			JsGratitudeBlessing vo = jsGratitudeBlessingDAO.selectGratitudeBlessingByUid();
//			if(Utils.isObjectNotEmpty(vo) && vo.getSplit()==1){
				map.put("type", 5);
				map.put("status", 1);
				List<DrCouponsIssuedRules> ruleList = drCouponsIssuedRulesDAO.getCouponsIssuedRulesList(map);
				if(Utils.isObjectNotEmpty(ruleList)){
					for(DrCouponsIssuedRules rule:ruleList){
						String coupons = rule.getCoupons();
						String[] str = coupons.split(",");
						map.clear();
						map.put("ids", str);
						list =  drActivityParameterDAO.selectActivityParamterByIds(map);
					}
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
