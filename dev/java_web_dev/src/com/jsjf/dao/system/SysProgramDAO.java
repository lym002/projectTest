package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysProgram;

public interface SysProgramDAO {
	
	/**
	 * @Description 拿到栏目列表数据
	 * @param Map
	 * @return List<SysProgram>
	 * @throws SQLException
	 */
    public List<SysProgram> getSysProgramList(Map<String,Object> map); 
    
	/**
	 * @Description 拿到栏目列表数据总数
	 * @param Map
	 * @return Integer
	 * @throws SQLException
	 */
    public Integer getSysProgramCounts(Map<String,Object> map); 
    
 	/**
 	 * @Description 添加栏目
 	 * @param SysProgram
 	 * @return void
 	 * @throws SQLException
 	 */
    public void insertSysProgram(SysProgram sysProgram)throws SQLException;
 	/**
 	 * @Description 修改栏目
 	 * @param SysProgram
 	 * @return void
 	 * @throws SQLException
 	 */
    public void updateSysProgramById(SysProgram sysProgram)throws SQLException;
 	/**
 	 * @Description 查询栏目
 	 * @param Map
 	 * @return List<SysProgram>
 	 * @throws SQLException
 	 */
    public List<SysProgram> selectSysProgramById(Map<String,Object> map);


}