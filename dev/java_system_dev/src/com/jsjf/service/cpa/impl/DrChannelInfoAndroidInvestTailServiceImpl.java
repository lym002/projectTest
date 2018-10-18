package com.jsjf.service.cpa.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.cpa.DrChannelInfoAndroidInvestTailDao;
import com.jsjf.model.cpa.DrChannelInfoAndroidTailBean;
import com.jsjf.service.cpa.DrChannelInfoAndroidInvestTailService;

@Service
@Transactional
public class DrChannelInfoAndroidInvestTailServiceImpl implements DrChannelInfoAndroidInvestTailService{
	
	@Autowired
	private DrChannelInfoAndroidInvestTailDao investTailDao;

	@Override
	public BaseResult queryAndroidInvestTailList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("mobilePhone",param.get("mobilePhone"));
		map.put("toFrom",param.get("toFrom"));
		map.put("isBuildCard",param.get("isBuildCard"));
		map.put("isReg",param.get("isReg"));
		map.put("startInvestMoney",param.get("startInvestMoney"));
		map.put("endInvestMoney",param.get("endInvestMoney"));
		map.put("searchDrChannelInfoOrderStartDate",param.get("searchDrChannelInfoOrderStartDate"));
		map.put("searchDrChannelInfoOrderEndDate",param.get("searchDrChannelInfoOrderEndDate"));
		map.put("startInvestMoneyDate",param.get("startInvestMoneyDate"));
		map.put("endInvestMoneyDate",param.get("endInvestMoneyDate"));
		if(Utils.isObjectNotEmpty(param.get("cid"))){
			String[] cidi = param.get("cid").toString().split(",");
			Integer cid[] = new Integer[cidi.length];
			for (int i = 0; i < cidi.length; i++) {
				cid[i] = Integer.parseInt(cidi[i]);
			}
			map.put("cid",cid); 
		}
		List<DrChannelInfoAndroidTailBean> list = investTailDao.queryAndroidInvestTailList(map);
		Integer total = investTailDao.queryAndroidInvestTailListCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}


}