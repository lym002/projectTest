package com.jsjf.service.member.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.JsMemberInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.service.member.JsMemberInfoService;

@Service
public class JsMemberInfoServiceImpl implements JsMemberInfoService {

	@Autowired
	private JsMemberInfoDAO jsMemberInfoDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	
	@Override
	public void updateJsMemberInfo(JsMemberInfo jsMemberInfo) {
		jsMemberInfoDAO.updateJsMemberInfo(jsMemberInfo);
	}

	@Override
	public JsMemberInfo selectMemberInfoByUid(Integer uid) {
		return jsMemberInfoDAO.selectMemberInfoByUid(uid);
	}
	
	@Override
	public BaseResult insertJsMemberInfo(JsMemberInfo jsMemberInfo) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			if(Utils.isObjectEmpty(jsMemberInfo.getUid())){
				br.setErrorCode("9998");
				br.setSuccess(false);
				br.setErrorMsg("uid不能为空");
				return br;
			}
			/*Integer prizeType = drProductInvestDAO.selectPrizeTypeByInvestId(jsMemberInfo.getInvestId());
			//1：虚拟  0：实物
			if(prizeType == 1){
				if(StringUtils.isBlank(jsMemberInfo.getPhone())){
					br.setErrorCode("1002");
					br.setSuccess(false);
					br.setErrorMsg("充值手机号码不能为空");
					return br;
				}
			}
			else if(prizeType == 0){
				if (StringUtils.isBlank(jsMemberInfo.getPhone()) || StringUtils.isBlank(jsMemberInfo.getName())
						|| StringUtils.isBlank(jsMemberInfo.getAddress())) {
					br.setErrorCode("1003");
					br.setSuccess(false);
					br.setErrorMsg("收货信息填写不完整");
					return br;
				}
			}*/
			param.clear();
			param.put("uid", jsMemberInfo.getUid());
			//param.put("investId", jsMemberInfo.getInvestId());
			JsMemberInfo info = jsMemberInfoDAO.selectMemberInfoByMap(param);
			if(info!=null){
				br.setErrorCode("1004");
				br.setSuccess(false);
				br.setErrorMsg("收货信息已存在");
				return br;
			}
			jsMemberInfoDAO.insertJsMemberInfo(jsMemberInfo);
			br.setSuccess(true);
		} catch (NumberFormatException e) {
			br.setErrorCode("9999");// 系统错误
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public JsMemberInfo selectMemberInfoByMap(Map<String, Object> map) {
		return jsMemberInfoDAO.selectMemberInfoByMap(map);
	}

}