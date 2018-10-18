package com.jsjf.service.subject;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysUsersVo;

public interface DrSubjectInfoService {

	/**
	 * 拿到标的信息列表数据
	 * @param order 
	 * @param sort 
	 * 
	 * @param DrSubjectInfo
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrSubjectInfoList(DrSubjectInfo drSubjectInfo, PageInfo pi, String sort, String order);
	
	/**
	 * 根据id得到标的信息
	 * @param id
	 * @return DrSubjectInfo
	 */
    public DrSubjectInfo getDrSubjectInfoByid(Integer id); 
    
 	/**
 	 * 修改标的信息
 	 * @param  id
 	 * @param  SysUsersVo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrSubjectInfo(Integer id,SysUsersVo usersVo) throws SQLException; 
 	
	/**
	 * 根据MAP得到标的信息
	 * @param map
	 * @return List<DrSubjectInfo>
	 */
    public List<DrSubjectInfo> getDrSubjectInfoByMap(Map<String,Object> map); 
    
 	/**
 	 * 把到期标的状态修改为已到期
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrSubjectInfoByExpire() throws SQLException; 	
 	
 	/**
	 * 拿到标的池信息列表数据
	 * 
	 * @param DrSubjectInfo
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrSubjectPoolList(DrSubjectInfo drSubjectInfo, PageInfo pi);

	public BaseResult getSubjectProductInfoList(DrProductInfo drProductInfo,PageInfo pi);
	
	/**
	 * 导出标的查询信息
	 * @param param
	 * @return
	 */
	public List<DrSubjectInfo> exportDrSubjectInfo(Map<String, Object> param);
}
