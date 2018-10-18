package com.jsjf.service.subject.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.subject.DrSubjectInfoService;

@Service
@Transactional
public class DrSubjectInfoServiceImpl implements DrSubjectInfoService {
	@Autowired
	private DrSubjectInfoDAO drSubjectInfoDAO;
	@Autowired 
	private DrProductInfoDAO drProductInfoDAO;
	
	@Override
	public BaseResult getDrSubjectInfoList(DrSubjectInfo drSubjectInfo,PageInfo pi,String sort,String order) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("no", drSubjectInfo.getNo());
		map.put("code", drSubjectInfo.getCode());
		map.put("amount", drSubjectInfo.getAmount());
		map.put("surplusAmount", drSubjectInfo.getSurplusAmount());
		map.put("startDate", Utils.format(drSubjectInfo.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd"));
		map.put("status", drSubjectInfo.getStatus());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("user_id", 1);//查询开过户的企业
		if(sort != null && sort !=""){
			map.put("sort", sort);
		}
		if(order != null && order !=""){
			map.put("order", order);
		}
		List<DrSubjectInfo> list = drSubjectInfoDAO.getDrSubjectInfoList(map);
		Integer total = drSubjectInfoDAO.getDrSubjectInfoCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public DrSubjectInfo getDrSubjectInfoByid(Integer id) {
		return drSubjectInfoDAO.getDrSubjectInfoByid(id);
	}

	@Override
	public void updateDrSubjectInfo(Integer id, SysUsersVo usersVo)
			throws SQLException {
		DrSubjectInfo drSubjectInfo = new DrSubjectInfo();
		drSubjectInfo.setId(id);
		drSubjectInfo.setStatus(3);//状态：表的池使用
		drSubjectInfo.setIspool(1);//是否入池   1：表示此标的入池
		drSubjectInfo.setUpdUser(usersVo.getUserKy().intValue());
		drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);		
	}

	@Override
	public List<DrSubjectInfo> getDrSubjectInfoByMap(Map<String, Object> map) {
		return drSubjectInfoDAO.getDrSubjectInfoByMap(map);
	}

	@Override
	public void updateDrSubjectInfoByExpire() throws SQLException {
		drSubjectInfoDAO.updateDrSubjectInfoByExpire();
	}

	@Override
	public BaseResult getDrSubjectPoolList(DrSubjectInfo drSubjectInfo,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("scode", drSubjectInfo.getCode());
		map.put("no", drSubjectInfo.getNo());
		map.put("pcode", drSubjectInfo.getpCode());
		map.put("startDate", drSubjectInfo.getStartDate());
		map.put("endDate", drSubjectInfo.getEndDate());

		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrSubjectInfo> list = drSubjectInfoDAO.getSubjectPoolList(map);
		Integer total = drSubjectInfoDAO.getSubjectPoolTotal(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	//获取标的产品的列表
	@Override
	public BaseResult getSubjectProductInfoList(DrProductInfo drProductInfo,
			PageInfo pi) {
		
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("simpleName", drProductInfo.getSimpleName());
		map.put("code", drProductInfo.getCode());
		if(drProductInfo.getStatus() == null){
			map.put("status", drProductInfo.getStatus());
		}else{
			if(drProductInfo.getStatus() == 100){//显示审核页面
				map.put("status", new Integer[]{1,3});
			}else{
				map.put("status", new Integer[]{drProductInfo.getStatus()});
			}
		}
		map.put("offset",pi.getPageInfo().getOffset());  
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("sid", drProductInfo.getSid());
		List<DrProductInfo> list = drProductInfoDAO.getSubjecDrProductInfoList(map);
		Map<String,Object> param = drProductInfoDAO.getSubjectDrProductInfoCounts(map);
		List<Map<String,Object>> footer = drProductInfoDAO.getSubjectPageCountByParam(map);
		footer.add(param);
		pi.setTotal(Integer.parseInt(param.get("total").toString()));
		pi.setFooter(footer);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<DrSubjectInfo> exportDrSubjectInfo(Map<String, Object> param) {
		return drSubjectInfoDAO.exportDrSubjectInfo(param);
	}


}
