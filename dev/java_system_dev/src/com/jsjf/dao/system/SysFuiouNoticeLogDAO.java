package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysFuiouNoticeLog;

public interface SysFuiouNoticeLogDAO {
    
	/**
	 * 插入富友验签记录
	 * @param sysFuiouNoticeLog
	 */
    public void insert(SysFuiouNoticeLog sysFuiouNoticeLog);
    
    /**
     * 根据流水号查询
     * @param mchnt_txn_ssn
     * @return
     */
    public SysFuiouNoticeLog selectObjectBy_txn_ssn(String mchnt_txn_ssn);
    
    /**
     * 更新
     * @param sysFuiouNoticeLog
     */
    public void update(SysFuiouNoticeLog sysFuiouNoticeLog);
    /**
     * 条件查询SysFuiouNoticeLog
     * @param param
     * @return
     */
    public SysFuiouNoticeLog getSysFuiouNoticeLogByParam(Map<String,Object> param);
    
    public List<SysFuiouNoticeLog> getSysFuiouNoticeLogByIcd();
    
    
    /**
     * 根据Map
     */
    public List<SysFuiouNoticeLog> selectFuiouNoticeLogByMap(Map<String, Object> map);
    
    /**
     * 查询更改银行卡待审核列表
     * @return
     */
    public List<SysFuiouNoticeLog> queryChangeCard();
}