package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.DrproductLoanRecord;

public interface DrProductLoanRecordDAO {
	

	/**
	 * 查询新手标放回款列表
	 * @param map
	 * @return
	 */
	List<DrproductLoanRecord> drProductLoanRecordList(Map<String, Object> map);
	/**
	 * 统计新手标放回款总数
	 * @param map
	 * @return
	 */
	Integer drProductLoanRecordListCount(Map<String, Object> map);
	
 	

	public void insert();
	/**
	 * 新手标放款状态修改
	 * @param map
	 */
	void updateDrProductLoanStatus(Map<String, Object> map);
	/**
	 * 新手标回款状态修改
	 * @param map
	 */
	void updateReFundRecordDrProductLoan(Integer id);

}