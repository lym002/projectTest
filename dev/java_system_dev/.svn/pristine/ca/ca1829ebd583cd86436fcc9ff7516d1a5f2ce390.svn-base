package com.jsjf.service.jzh.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.product.DrProductInvestRepayInfoDAO;
import com.jsjf.dao.system.SysFuiouMessageLogDAO;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.model.system.SysFuiouMessageLog;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.jzh.SysFuiouMessageService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
@Service
@Transactional
public class SysFuiouMessageServiceImpl implements SysFuiouMessageService {

	@Autowired
	private SysFuiouMessageLogDAO sysFuiouMessageLogDAO;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;

	@Override
	public void updateMessageData(Map<String, Object>map){
		List<DrMember>updateMemberList = (List<DrMember>)map.get("updateMemberList");//个人用户
		List<DrClaimsCustomer>updateClaimsCustomerList = (List<DrClaimsCustomer>)map.get("updateClaimsCustomerList");//企业用户
		List<DrProductInfo>updateProductInfoList = (List<DrProductInfo>)map.get("updateProductInfoList");//项目用户
		List<SysFuiouMessageLog>updateLogList = (List<SysFuiouMessageLog>)map.get("updateLogList");//更新报文日志
		List<DrProductInvest>updateInvestList = (List<DrProductInvest>) map.get("updateInvestList");//投资
		List<DrProductInvestRepayInfo>updateInvestRepayInfoList = (List<DrProductInvestRepayInfo>) map.get("updateInvestRepayInfoList");//回款
		
		if(updateMemberList != null && !updateMemberList.isEmpty()){
			sysFuiouMessageLogDAO.batchUpdateJzhMessageResult(updateMemberList);
		}
		if(updateClaimsCustomerList != null && !updateClaimsCustomerList.isEmpty()){
			sysFuiouMessageLogDAO.batchUpdateJzhMessageResult(updateClaimsCustomerList);
		}
		if(updateProductInfoList != null && !updateProductInfoList.isEmpty()){
			sysFuiouMessageLogDAO.batchUpdateJzhMessageResult(updateProductInfoList);
			drClaimsInfoService.updateCompanyStatus();
		}
		if(updateInvestList != null && !updateInvestList.isEmpty()){
			sysFuiouMessageLogDAO.batchUpdateInvestJzhMessageResult(updateInvestList);
		}
		if(updateInvestRepayInfoList != null && !updateInvestRepayInfoList.isEmpty()){
			sysFuiouMessageLogDAO.batchUpdateJzhMessageResult(updateInvestRepayInfoList);
		}
		
		if(updateLogList != null && !updateLogList.isEmpty()){
			sysFuiouMessageLogDAO.updateOvercheck(updateLogList);
		}
		
	}

	@Override
	public void updateMemberMessageUpload(SysFuiouMessageLog log, List<DrMember>batchUpdateList){
		sysFuiouMessageLogDAO.insert(log);
		if(!batchUpdateList.isEmpty()){
			drMemberService.updateFileStatus(batchUpdateList);
		}
	}

	@Override
	public void updateProjectInventUpload(SysFuiouMessageLog log, List<DrProductInvest> batchUpdateList) {
		sysFuiouMessageLogDAO.insert(log);
		if(!batchUpdateList.isEmpty()){
			drProductInvestService.updateFileStatus(batchUpdateList);
		}
		
	}

	@Override
	public void updateProjectInventRepayInfoUpload(SysFuiouMessageLog log,
			List<DrProductInvestRepayInfo> batchUpdateList) {
		sysFuiouMessageLogDAO.insert(log);
		if(!batchUpdateList.isEmpty()){
			drProductInvestRepayInfoDAO.updateFileStatus(batchUpdateList);
		}
	}
	
	@Override
	public void updateCustomerMessageUpload(SysFuiouMessageLog log, List<DrClaimsCustomer>batchUpdateList){
		sysFuiouMessageLogDAO.insert(log);
		if(!batchUpdateList.isEmpty()){
			drClaimsInfoService.updateFileStatus(batchUpdateList);
		}
	}
	
	@Override
	public void updateProductInfoUpload(SysFuiouMessageLog log, List<DrProductInfo>batchUpdateList){
		sysFuiouMessageLogDAO.insert(log);
		if(!batchUpdateList.isEmpty()){
			drProductInfoService.updateFileStatus(batchUpdateList);
		}
	}
	
}
