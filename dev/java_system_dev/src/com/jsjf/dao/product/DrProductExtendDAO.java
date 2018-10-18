package com.jsjf.dao.product;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.product.DrProductExtend;

public interface DrProductExtendDAO {
	
 	/**
 	 * 添加产品扩展信息
 	 * @param  DrProductExtend
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrProductExtend(DrProductExtend drProductExtend) throws SQLException; 
	
	/**
	 * 根据PID拿产品扩展信息
	 * @param  pid 产品ID
	 * @return List<DrProductExtend>
	 * @throws SQLException;
	 */
	public List<DrProductExtend> getDrProductExtendByPid(int pid);
	/**
	 * 根据sid拿产品扩展信息
	 * @param  pid 产品ID
	 * @return List<DrProductExtend>
	 * @throws SQLException;
	 */
	public List<DrProductExtend> getDrProductExtendBySid(int sid);
	
	/**
	 * 根据pID删除产品扩展信息
	 * @param pid
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrProductExtendByPid(int pid) throws SQLException; 
}