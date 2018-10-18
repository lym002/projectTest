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
	 * 根据map查询银行信息List
	 * @return List<SysBank>
	 */
    public List<SysBank> selectSysBankList();
    /**
     * 查询银行单笔限额、单日限额
     * @return
     */
    public List<SysBank> selectBank();
    
    public String selectBankByCode(@Param("bankCode") String bankCode);
}