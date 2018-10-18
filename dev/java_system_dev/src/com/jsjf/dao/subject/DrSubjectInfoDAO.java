package com.jsjf.dao.subject;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.subject.DrSubjectInfo;

public interface DrSubjectInfoDAO {
	
	/**
	 * 得到标的信息
	 * @param Map
	 * @return List<DrSubjectInfo>
	 */
    public List<DrSubjectInfo> getDrSubjectInfoList(Map<String,Object> map); 

 	/**
 	 * 得到标的信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrSubjectInfoCounts(Map<String,Object> map); 
     
 	/**
 	 * 添加标的信息
 	 * @param  DrSubjectInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrSubjectInfo(DrSubjectInfo drSubjectInfo) throws SQLException; 
 	
	/**
	 * 根据id得到标的信息 拿到的金额和剩余金额单位是万元
	 * @param id
	 * @return DrSubjectInfo
	 */
    public DrSubjectInfo getDrSubjectInfoByid(Integer id); 
    
	/**
	 * 根据id得到标的信息流标专用查询
	 * @param id
	 * @return DrSubjectInfo
	 */
    public DrSubjectInfo getDrSubjectInfoByid1(Integer id); 
    
	/**
	 * 根据MAP得到标的信息
	 * @param map
	 * @return List<DrSubjectInfo>
	 */
    public List<DrSubjectInfo> getDrSubjectInfoByMap(Map<String,Object> map); 
    
 	/**
 	 * 修改标的信息
 	 * @param  DrSubjectInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrSubjectInfo(DrSubjectInfo drSubjectInfo) throws SQLException; 
    
 	/**
 	 * 把到期标的状态修改为已到期
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrSubjectInfoByExpire() throws SQLException; 	
 	
 	
 	
 	/**
	 * 得到标的信息
	 * @param Map
	 * @return List<DrSubjectInfo>
	 */
    public List<DrSubjectInfo> getSubjectPoolList(Map<String,Object> map); 

 	/**
 	 * 得到标的信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getSubjectPoolTotal(Map<String,Object> map);
     /**
      * 导出标的查询信息
      * @param param
      * @return
      */
	public List<DrSubjectInfo> exportDrSubjectInfo(Map<String, Object> param); 
}