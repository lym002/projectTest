package com.jsjf.service.system;

import java.sql.SQLException;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysBank;

public interface SysBankService {
	
	/**
	 * 得到银行列表数据
	 * @param SysBank
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getSysBankList(SysBank sysBank,PageInfo pi);
	
	/**
	 * 根据ID银行信息
	 * @param id
	 * @return SysBank
	 */
    public SysBank getSysBankByid(int id);
    
	/**
	 * 修改银行信息
	 * @param sysBank
	 */
    public void updateSysBank(SysBank sysBank);
}
