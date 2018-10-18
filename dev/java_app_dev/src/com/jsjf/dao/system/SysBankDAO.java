package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.system.SysBank;

public interface SysBankDAO {
    
	/**
	 * 根据map查询银行信息
	 * @param map
	 * @return SysBank
	 */
    public SysBank selectSysBank(Map<String,Object> map);
    
    /**
     * 获取银行单笔和每日限额列表
     * @return
     */
    public List<Map<String,Object>> selectSysBankQuotaList();
    
    
    public String selectBankByCode(@Param("bankCode") String bankCode);

}