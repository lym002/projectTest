package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.dao.activity.JsActivityMemberAccountDAO;
import com.jsjf.model.activity.JsActivityMemberAccount;
import com.jsjf.service.activity.JsActivityMemberAccountService;
@Service
@Transactional
public class JsActivityMemberAccountServiceImpl implements JsActivityMemberAccountService {

	@Autowired
	private JsActivityMemberAccountDAO jsActivityMemberAccountDAO;
	@Override
	public BaseResult selectFriendAmountTopFive(Integer afid) {
		BaseResult br = new BaseResult();
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			List<JsActivityMemberAccount> list =  jsActivityMemberAccountDAO.selectFriendAmountTopFive(afid);
			result.put("topFiveList", list);
			br.setMap(result);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("获取失败");
			e.printStackTrace();
		}
		return br;
	}

	

}
