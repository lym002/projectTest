package com.jsjf.dao.claims;

import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.JsLoanRecord;

public interface JsLoanRecordDAO {
	
	/**
	 * 查数据
	 * @param map
	 * @return
	 */
	public List<JsLoanRecord> selectJsLoanRecordByMap(Map<String,Object> map);
	/**
	 * 查总数
	 */
	public int selectJsLoanRecordCountByMap(Map<String,Object> map);
	/**
	 * 批量添加
	 * @param list
	 */
	public void insertBatch(List<JsLoanRecord> list);
	/**
	 * 添加
	 * @param jsLoanRecord
	 */
	public void insert(JsLoanRecord jsLoanRecord);
	/**
	 * 添加
	 * @param map{useKy,pid}
	 */
	public void insertByPid(Map<String,Object> map);
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	public JsLoanRecord selectByPrimaryKey(Integer pid);
	
	public void deleteLoanRecord(Map<String,Object> map);

}
