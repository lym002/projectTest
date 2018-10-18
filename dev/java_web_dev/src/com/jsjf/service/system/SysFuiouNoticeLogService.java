package com.jsjf.service.system;

import java.sql.SQLException;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysFuiouNoticeLog;

public interface SysFuiouNoticeLogService {

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
}
