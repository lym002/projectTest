package com.jsjf.dao.article;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.article.SysProgram;

public interface SysProgramDAO {
	
	/**
	 * 拿到栏目列表数据
	 * @param Map
	 * @return List<SysProgram>
	 */
    public List<SysProgram> getSysProgramList(Map<String,Object> map); 
    
	/**
	 * 拿到栏目列表数据总数
	 * @param Map
	 * @return Integer
	 */
    public Integer getSysProgramCounts(Map<String,Object> map); 
    
 	/**
 	 * 添加栏目
 	 * @param SysProgram
 	 * @return void
 	 * @throws SQLException
 	 */
    
    public void insertSysProgram(SysProgram sysProgram)throws SQLException;
    
 	/**
 	 * 修改栏目
 	 * @param SysProgram
 	 * @return void
 	 * @throws SQLException
 	 */
    public void updateSysProgramById(SysProgram sysProgram)throws SQLException;
    
 	/**
 	 * 查询栏目
 	 * @param Map
 	 * @return List<SysProgram>
 	 */
    public List<SysProgram> selectSysProgramById(Map<String,Object> map);


}