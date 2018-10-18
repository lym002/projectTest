package com.jsjf.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.JsActivityProductDAO;
import com.jsjf.dao.product.JsActivityProductInvestInfoDAO;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.product.JsActivityProductInvestInfoService;
import com.mysql.jdbc.Util;

@Service
@Transactional
public class JsActivityProductInvestInfoServiceImpl implements JsActivityProductInvestInfoService {
	@Autowired
	JsActivityProductInvestInfoDAO jsActivityProductInvestInfoDAO;
	@Autowired
	DrProductInfoDAO drProductInfoDAO;
	@Autowired
	JsActivityProductDAO jsActivityProductDAO;
	

	@Override
	public List<Map<String, Object>> selectJsActivityProductIsWinner(
			Map<String, Object> map) {
		
		return jsActivityProductInvestInfoDAO.selectJsActivityProductIsWinner(map);
	}


	@Override
	public BaseResult jsActivityProductCenter(Map<String,Object> map) {
		BaseResult br = new BaseResult();
		PageInfo pageInfo = new PageInfo();
		try {
			List<Map<String,Object>> list = jsActivityProductInvestInfoDAO.selectjsActivityProductInvestInfoList(map);
			int total = jsActivityProductInvestInfoDAO.selectjsActivityProductInvestInfoCount(map);
			pageInfo.setRows(list);
			pageInfo.setTotal(total);
			map.clear();
			map.put("pageInfo", pageInfo);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return br;
	}

	@Override
	public BaseResult getActivityPrizeResult(Integer pageOn,Integer pageSize) {
		BaseResult br = new BaseResult();
		Map<String,Object> result = new HashMap<String, Object>(); 
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			//当期的中奖者
			DrProductInfo dpi = drProductInfoDAO.selectJSProductActive();//活动产品
			if(Utils.isObjectNotEmpty(dpi)){
				map.put("pid", dpi.getId());
				result.put("current", jsActivityProductDAO.selectActivityProductByPid(map));
				//查询往期的中奖者
				map.clear();
				map.put("noPid", dpi.getId());//排除本期的				
			}
			PageInfo page = new PageInfo();
			if(!Utils.isBlank(pageSize) && !Utils.isBlank(pageOn)){
				page.setPageOn(pageOn);
				page.setPageSize(pageSize);
				map.remove("noPid");
			}else{
				page.setPageOn(1);
				page.setPageSize(5);
			}
			
			map.put("offset", page.getPageInfo().getOffset());
			map.put("limit", page.getPageInfo().getLimit());
			List<Map<String,Object>> history = jsActivityProductInvestInfoDAO.selectJsActivityProductIsWinner(map);		
			int total = jsActivityProductInvestInfoDAO.selectJsActivityProductIsWinnerCount(map);
			page.setTotal(total);
			result.put("pageOn", page.getPageOn());
			result.put("TotalPage", page.getTotalPage());
			result.put("total", page.getTotal());
			result.put("history", history);
			br.setMap(result);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return br;
	}


	@Override
	public int selectjsActivityProductInvestInfoCount(int atid) {
		Map<String,Object> map = new HashMap<>();
		map.put("atid", atid);	
		return jsActivityProductInvestInfoDAO.selectjsActivityProductInvestInfoCount(map);
	}
	@Override
	public Map<String, Object> iPhoneCensus() {
		
		return jsActivityProductInvestInfoDAO.iPhoneCensus();
	}

}
