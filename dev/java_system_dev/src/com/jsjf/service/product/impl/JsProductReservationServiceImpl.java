package com.jsjf.service.product.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.product.JsProductReservationDAO;
import com.jsjf.dao.product.JsProductReservationLogDAO;
import com.jsjf.model.cpa.DrChannelKeyWords;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.JsProductReservation;
import com.jsjf.model.product.JsProductReservationLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.product.JsProductReservationService;

@Service
public class JsProductReservationServiceImpl implements
		JsProductReservationService {
	@Autowired
	private JsProductReservationDAO jsProductReservationDAO;
	@Autowired
	private JsProductReservationLogDAO jsProductReservationLogDAO;

	@Override
	public BaseResult getJsProductReservationList(
			JsProductReservation jsProductReservation, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", jsProductReservation.getName());
		map.put("startTime", jsProductReservation.getStartTime());
		map.put("endTime", jsProductReservation.getEndTime());
		map.put("status", jsProductReservation.getStatus());
		
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<JsProductReservation> list = jsProductReservationDAO.selectJsProductReservationList(map);
		Integer total = jsProductReservationDAO.selectJsProductReservationCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public BaseResult insertJsProductReservation(JsProductReservation jsProductReservation,SysUsersVo usersVo){
		BaseResult br = new BaseResult();
		
		try {
			Integer periods = jsProductReservation.getPeriods();
			Map<String, Object> map = new HashMap<>();
			map.put("periods", periods);
			map.put("notStatus", 2);
			//根据期限获取是否有开启 或待开启的规则
			Integer total = jsProductReservationDAO.selectJsProductReservationCount(map);
			if(total>0){
				br.setSuccess(false);
				br.setErrorMsg("不能新建相同期限的规则");
			}else{
				jsProductReservation.setAddUser(usersVo.getUserKy().intValue());
				jsProductReservationDAO.insertProductReservation(jsProductReservation);
				br.setSuccess(true);
				br.setMsg("保存成功!");
			}
		} catch (SQLException e) {
			br.setSuccess(false);
			br.setErrorMsg("保存失败！");
			e.printStackTrace();
		}
		return br;
		
	}

	@Override
	public JsProductReservation getJsProductReservationById(Integer id) {
		return jsProductReservationDAO.getJsProductReservationById(id);
	}

	@Override
	public void updateJsProductReservation(
			JsProductReservation jsProductReservation, SysUsersVo usersVo) {

		try {
			jsProductReservation.setUpdateUser(usersVo.getUserKy().intValue());
			jsProductReservationDAO.updateProductReservation(jsProductReservation);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PageInfo getJsPReservationLogByPrid(Map<String, Object> map,PageInfo pi) {
		map.put("limit", pi.getPageInfo().getLimit());
		map.put("offset", pi.getPageInfo().getOffset());
		List<JsProductReservationLog> list = jsProductReservationLogDAO.selectJsProductReservationLogList(map);
		Integer total = jsProductReservationLogDAO.selectJsProductReservationLogCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}


}
