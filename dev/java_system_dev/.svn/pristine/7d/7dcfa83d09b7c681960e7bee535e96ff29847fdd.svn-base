package com.jsjf.dao.claims;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.claims.DrClaimsProject;

public interface DrClaimsProjectDAO {
	
 	/**
 	 * 添加债权审核项目
 	 * @param  DrClaimsProject
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrClaimsProject(DrClaimsProject drClaimsProject) throws SQLException; 
	
	/**
	 * 根据PID拿债权审核项目
	 * @param  lid 债权ID
	 * @return List<DrClaimsProject>
	 * @throws SQLException;
	 */
	public List<DrClaimsProject> getDrClaimsProjectByLid(int lid);
	
	/**
	 * 根据pID删除债权审核项目
	 * @param lid 债权ID
	 * @return void
	 * @throws SQLException;
	 */
	public void deleteDrClaimsProjectByLid(int lid) throws SQLException; 
}