package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.List;

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
    
    /**
     * 查询扣除手续费失败的数据
     * @return
     */
    public List<SysFuiouNoticeLog> getSysFuiouNoticeLogByIcd();

    /**
     * 查询更改银行卡待审核列表
     * @return
     */
    public List<SysFuiouNoticeLog> queryChangeCard();
}
