package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.Map;

import com.jsjf.model.system.SysActivityParameter;


public interface SysActivityParameterDAO {
	/**
	 * @Description 根据ID查询活动
	 * @param Integer
	 * @return SysActivityParameter
	 * @throws SQLException
	 * @author 
	 */
    public SysActivityParameter selectSysActivityParameterById(String code) throws SQLException; 
    
    /**
     * @Description 根据ID修改活动
     * @param Integer
     * @throws SQLException
     * @author 
     */
    public void updateSysActivityParameterById(SysActivityParameter sysActivityParameter) throws SQLException;
    
    /**
     * 修改资金 ( 传入要减去资金变量 :subAmount -->set amount = (amount -subAmount),...) 及修改状态   
     * @param map{subAmount:'',....}
     * @throws SQLException
     */
    public void updateSysActivityParameterByMap(Map<String,Object> map) throws SQLException; 
    
}