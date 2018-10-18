package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysMessageLog;

public interface SysMessageLogDAO {
	
	/**
	 * @Description 插入短信日志
	 * @param SysMessageLog
	 * @return void
	 * @throws SQLException
	 */
    public void insertSysMessageLog(SysMessageLog sysMessageLog) throws SQLException; 
    
    
    /**
	 * 根据短信属性查找最后一条记录
	 * @param sysMessageLog
	 * @return
	 */
    public SysMessageLog selectByProperty(SysMessageLog sysMessageLog);
    
    /**
  	 * 查询注册短信日志
  	 * @param sysMessageLog
  	 * @return
  	 */
      public List<SysMessageLog> selectByType(Map map);
      /**
       * 查询注册短信日志count
       * @param sysMessageLog
       * @return
       */
      public int selectByTypeCounts(Map<String,Object> map);
      
    /**
	 * 查询注册短信日志总条数
	 * @param sysMessageLog
	 * @return
	 */
    public int selectByTypecount(SysMessageLog sysMessageLog);
    /**
     * 
     * @param sysMessageLog
     * @return
     */
    public void update(Map<String,Object> map);
    
    
	/**
	 * 查询fuiou 返回码描述
	 */
	public List<Map<String,Object>> getFuiouRspCode();
    
}