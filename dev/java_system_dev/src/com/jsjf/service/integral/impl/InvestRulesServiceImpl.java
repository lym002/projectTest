package com.jsjf.service.integral.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.integral.InvestRulesDao;
import com.jsjf.model.integral.InvestRulesBean;
import com.jsjf.service.integral.InvestRulesService;

@Service
@Transactional
public class InvestRulesServiceImpl implements InvestRulesService {
	private Logger log = Logger.getLogger(InvestRulesServiceImpl.class);
	
	@Autowired
	private InvestRulesDao investRulesDao;

	@Override
	public PageInfo queryInvestrulesListList(PageInfo info,
			InvestRulesBean investRulesBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		
		//map.put("sourceType", integralSourceBean.getSourceType());
		map.put("orders", "add_time desc");
		
		List<InvestRulesBean> rows = investRulesDao.queryInvestrulesListList(map);
		int total = investRulesDao.queryInvestrulesListListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
		
	}

	@Override
	public BaseResult addInvestRules(InvestRulesBean investRulesBean) {
		BaseResult br = new BaseResult();
		try{
			investRulesBean.setAddTime(new Date());
			investRulesDao.addInvestRules(investRulesBean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			log.error("添加失败", e);
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateInvestRules(InvestRulesBean investRulesBean) {
		BaseResult br = new BaseResult();
		try{
			investRulesBean.setUpdateTime(new Date());
			investRulesDao.updateInvestRules(investRulesBean);
			br.setSuccess(true);
			br.setMsg("修改成功");
		}catch(Exception e){
			log.error("修改失败", e);
			br.setSuccess(false);
			br.setMsg("修改失败");
		}
		return br;
	}

}