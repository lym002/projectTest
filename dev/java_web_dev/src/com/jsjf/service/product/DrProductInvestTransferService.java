package com.jsjf.service.product;

import java.util.List;
import java.util.Map;


public interface DrProductInvestTransferService {
	
	/**
	 * 查找出让人信息
	 * @param pid 产品ID
	 * @param assigneeUid 受让人ID
	 * @param investId 投资记录ID
	 * @return
	 */
	public List<Map<String, String>> selectTransferInfoByAssigneeUid(Integer pid, Integer assigneeUid, Integer investId);

}
