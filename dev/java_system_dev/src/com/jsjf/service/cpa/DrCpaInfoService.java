package com.jsjf.service.cpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.cpa.DrCpaInfo;

public interface DrCpaInfoService {
	/**
	 * 得到CPA地址信息列表
	 * @param Map
	 * @return List<DrCpaInfo>
	 */
	public BaseResult getDrCpaInfoList(DrCpaInfo drCpaInfo,PageInfo pi);
     
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
