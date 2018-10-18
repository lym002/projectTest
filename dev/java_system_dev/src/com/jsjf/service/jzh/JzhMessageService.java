package com.jsjf.service.jzh;

import java.util.List;
import java.util.Map;

public interface JzhMessageService {
	/**
	 * 个人开户报文报备
	 * 每天早上的1点钟报备昨日的个人开户
	 */
	public void selectPersonRegBatchUpload();
	/**
	 * 法人开户报文报备
	 * 每天早上的1点钟报备昨日的法人开户
	 */
	public void companyRegBatchUpload();
	/**
     * P2P商户交易（冻结，动账）
  	 * 获取投标记录
  	 * 每天早上的3:00进行投标交易报备
  	 */
	public void getProductByProjectNo();
	/**
	 * 投标补报
	 * @param list
	 * @param businessType
	 */
	void getInvestFiling(List<Map<String, Object>> list,String businessType);
	/**
	 * 满标放款
	 * @param drProductInfoService
	 * @param seqService
	 * @param sysFuiouMessageService
	 * 每天早上3:15报备满标放款
	 */
	public void getProductFullCreditByProjectNo();
	/**
	 * 满标补报
	 * @param list
	 * @param businessType
	 */
	public void getFullCreditFiling(List<Map<String, Object>> list,String businessType);
	/**
	 * P2P商户交易（冻结，动账）
	 * 获取回款记录
	 * 每天早上的3:00报回款
	 */
	public void getProductInvestRepayInfoByProjectNo();
	
	/**
	 * 回款补报
	 * @param list
	 * @param businessType
	 */
	public void getInvestReturnedMoneyFiling(List<Map<String, Object>> list,String businessType);
	/**
	 * P2P 项目信息
	 * 定义每5分钟执行一次项目信息报备
	 * 
	 */
	public void projectInfoUpload();
	/**
	 * 报文回调
	 * 定义每半小时执行一次改掉overcheck中匹配成功的数据状态
	 */
	public void overcheck();
}
