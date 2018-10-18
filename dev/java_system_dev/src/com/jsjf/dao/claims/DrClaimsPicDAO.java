package com.jsjf.dao.claims;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.claims.DrClaimsPic;

public interface DrClaimsPicDAO {
	
	/**
	 * 添加债权图片
	 * @param DrClaimsPic
	 * @return void
	 * @throws SQLException;
	 */
	public void insertDrClaimsPic(DrClaimsPic drClaimsPic) throws SQLException; 
	
	/**
	 * 修改债权图片
	 * @param DrClaimsPic
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrClaimsPic(DrClaimsPic drClaimsPic) throws SQLException; 
	
	/**
	 * 根据LID删除债权图片
	 * @param lid
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrClaimsPicByLid(Integer lid) throws SQLException; 
	
	/**
	 * 根据ID删除债权图片
	 * @param id
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrClaimsPicById(Integer id) throws SQLException; 
	
 	/**
 	 * 根据条件得到债权图片
 	 * @param lid
 	 * @return List<DrClaimsPic>
 	 */
     public List<DrClaimsPic> getDrClaimsPicByLid(Integer lid); 
     
  	/**
  	 * 根据id得到债权图片
  	 * @param id
  	 * @return DrClaimsPic
  	 */
      public DrClaimsPic getDrClaimsPicById(Integer id); 
}