package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.member.DrMemberCpsFavourableRuleDAO;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.service.member.DrMemberCpsFavourableRuleService;

@Service
public class DrMemberCpsFavourableRuleServiceImpl implements
		DrMemberCpsFavourableRuleService {

	@Autowired
	private DrMemberCpsFavourableRuleDAO drMemberCpsFavourableRuleDAO;
	
	@Override
	public List<DrMemberCpsFavourableRule> selectActivityByCps(Map<String,Object> paramMap) {
		return drMemberCpsFavourableRuleDAO.selectActivityByCps(paramMap);
	}

}
