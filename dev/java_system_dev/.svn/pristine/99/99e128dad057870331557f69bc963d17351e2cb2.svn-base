package com.jsjf.service.jzh;

import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.model.system.SysFuiouMessageLog;

public interface SysFuiouMessageService {
	/**
	 * 更新返回报文
	 * @param map
	 */
	public void updateMessageData(Map<String, Object>map);
	/**
	 * 更新个人开户上传报文
	 * @param log
	 * @param batchUpdateList
	 */
	public void updateMemberMessageUpload(SysFuiouMessageLog log, List<DrMember>batchUpdateList);
	
	/**
	 * 更新投资记录上传报文
	 * @param log
	 * @param batchUpdateList
	 */
	public void updateProjectInventUpload(SysFuiouMessageLog log, List<DrProductInvest>batchUpdateList);
	
	/**
	 * 更新回款记录上传报文
	 * @param log
	 * @param batchUpdateList
	 */
	
	public void updateProjectInventRepayInfoUpload(SysFuiouMessageLog log, List<DrProductInvestRepayInfo>batchUpdateList);
	
	/**
	 * 更新企业开户上传报文
	 * @param log
	 * @param batchUpdateList
	 */
	public void updateCustomerMessageUpload(SysFuiouMessageLog log, List<DrClaimsCustomer>batchUpdateList);
	/**
	 * 更新项目上传报文
	 * @param log
	 * @param batchUpdateList
	 */
	public void updateProductInfoUpload(SysFuiouMessageLog log, List<DrProductInfo>batchUpdateList);

}
