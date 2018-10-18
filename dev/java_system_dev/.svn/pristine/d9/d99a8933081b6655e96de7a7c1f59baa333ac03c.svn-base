package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsNoviceContinueRecord;

public interface JsNoviceContinueRecordDAO {
	/**
	 * 获取续投记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JsNoviceContinueRecord> getJsNoviceContinueRecordList(Map<String, Object> map)throws Exception;
	
	/**
	 * 续投记录总条数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getJsNoviceContinueRecordCount(Map<String, Object> map)throws Exception;
	
	/**
	 * 根据uid查询
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public JsNoviceContinueRecord selectJsNoviceContinueRecord(int uid)throws Exception;
	
	/**
	 * 获取续投记录
	 * @param map
	 * @return
	 */
	public List<JsNoviceContinueRecord> getNewHandContinueList(Map<String,Object> map);
	
	/**
	 * 获取待续投标期
	 * @return
	 */
	public List<Integer> getContinuePeriodList();
	
	public void updateJsNoviceContinueRecord(JsNoviceContinueRecord jsNCR);

}
