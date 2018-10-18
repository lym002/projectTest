package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.system.SysBank;

public interface SysBankDAO {
    
	/**
	 * 查询银行信息
	 * @return List<SysBank>
	 */
    public List<SysBank> selectSysBank();
    
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
    
	/**
	 * 得到银行信息数据
	 * @param Map
	 * @return List<SysBank>
	 * @throws SQLException
	 */
    public List<SysBank> getSysBankList(Map<String,Object> map); 

    
 	/**
 	 * 得到银行信息总数
 	 * @param Map
 	 * @return Integer
 	 * @throws SQLException
 	 */
     public Integer getSysBankCounts(Map<String,Object> map); 
     
     public String selectBankByCode(@Param("bankCode") String bankCode);
}