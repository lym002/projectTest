package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsNoviceContinueRecord;


public interface JsNoviceContinueRecordDAO {
	/**
	 * 插入
	 * @param jsActivityProductInvestInfo
	 */
	public void insert(JsNoviceContinueRecord jsNoviceContinueRecord);
	
	
	public List<Map<String,Object>> selectResultByMap(Map<String,Object> map);
	
	
	public List<JsNoviceContinueRecord> selectJsNoviceContinueRecordByMap(Map<String,Object> map);
}
