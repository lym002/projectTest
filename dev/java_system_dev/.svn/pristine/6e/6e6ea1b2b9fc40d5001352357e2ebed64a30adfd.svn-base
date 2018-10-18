package com.jsjf.dao.product;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.product.DrProductPic;

public interface DrProductPicDAO {
	/**
	 * 添加产品图片
	 * @param DrProductPic
	 * @return void
	 * @throws SQLException;
	 */
	public void insertDrProductPic(DrProductPic drProductPic) throws SQLException; 
	
	/**
	 * 修改产品图片
	 * @param DrProductPic
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductPic(DrProductPic drProductPic) throws SQLException; 
	
	/**
	 * 根据PID删除产品图片
	 * @param pid
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrProductPicByPid(Integer pid) throws SQLException; 
	
	/**
	 * 根据ID删除产品图片
	 * @param id
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrProductPicById(Integer id) throws SQLException; 
	
 	/**
 	 * 根据条件得到产品图片
 	 * @param pid
 	 * @return List<DrProductPic>
 	 */
     public List<DrProductPic> getDrProductPicByPid(Integer pid); 
     
  	/**
  	 * 根据id得到产品图片
  	 * @param id
  	 * @return DrProductPic
  	 */
      public DrProductPic getDrProductPicById(Integer id);
}
