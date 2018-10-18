package com.jsjf.service.product;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.JsNoviceContinueRecord;

public interface JsNoviceContinueRecordService {

	/**
	 * 获取新手续投记录
	 * @param jsNoviceContinueRecord
	 * @param pi
	 * @return
	 */
	public BaseResult getJsNoviceContinueRecord(JsNoviceContinueRecord jsNoviceContinueRecord,PageInfo pi);
	
	/**
	 * 新手标续投
	 */
	public void insertInvestNewHandContinue();
}
