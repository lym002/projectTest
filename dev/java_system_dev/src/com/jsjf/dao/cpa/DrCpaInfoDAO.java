package com.jsjf.dao.cpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.cpa.DrCpaInfo;

public interface DrCpaInfoDAO {
	/**
	 * 得到CPA地址信息列表
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
    public List<DrCpaInfo> getDrCpaInfoList(Map<String,Object> map); 

 	/**
 	 * 得到CPA地址信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrCpaInfoCounts(Map<String,Object> map); 
     
 	/**
 	 * 添加CPA地址信息
 	 * @param  DrCpaInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrCpaInfo(DrCpaInfo drCpaInfo) throws SQLException; 
 	
 	/**
 	 * 根据Map得到CPA地址信息
 	 * @param  Map
 	 * @return List<DrCpaInfo>
 	 * @throws SQLException;
 	 */
 	public List<DrCpaInfo> getDrCpaInfoListForMap(Map<String,Object> map) throws SQLException; 
}
