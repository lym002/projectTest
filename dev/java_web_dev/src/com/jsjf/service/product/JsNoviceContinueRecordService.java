package com.jsjf.service.product;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.JsNoviceContinueRecord;

public interface JsNoviceContinueRecordService {
	/**
	 * 新手续投红包处理
	 * @param dpi
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getContinueReward(DrProductInvest dpi) throws Exception;
	
	
	public List<JsNoviceContinueRecord> selectJsNoviceContinueRecord(Map<String,Object> map);
	
	
	public BaseResult addContinueReward(DrProductInvest dpi,Integer period)throws Exception;
}
