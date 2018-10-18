package com.jsjf.service.product;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.DrproductLoanRecord;

public interface DrProductLoanRecodService {
	/**
	 * 新手标放回款管理
	 * @param drproductLoanRecord
	 * @param pi
	 * @return
	 */
	BaseResult drProductLoanRecordList(DrproductLoanRecord drproductLoanRecord, PageInfo pi);

	public void insert();
	/**
	 * 新手标放款导出
	 * @param drproductLoanRecord
	 * @return
	 */
	List<DrproductLoanRecord> getDrProductLoanRecordListByParam(DrproductLoanRecord drproductLoanRecord);
	/**
	 * 新手标放款状态修改
	 * @param map
	 */
	void updateDrProductLoanStatus(Map<String, Object> map);

	/**
	 * 新手表回款状态修改
	 * @param id
	 */
	void updateReFundRecordDrProductLoan(Integer id);
}
