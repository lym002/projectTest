package com.jsjf.dao.claims;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.claims.DrClaimsShareholder;

public interface DrClaimsShareholderDAO {
	
 	/**
 	 * 添加股东情况
 	 * @param  DrClaimsShareholder
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrClaimsShareholder(DrClaimsShareholder drClaimsShareholder) throws SQLException; 
 	
 	/**
 	 * 修改股东情况
 	 * @param  DrClaimsShareholder
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrClaimsShareholder(DrClaimsShareholder drClaimsShareholder) throws SQLException; 
 	
 	/**
 	 * 删除股东情况
 	 * @param  lid
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void deleteDrClaimsShareholder(Integer lid) throws SQLException; 
 	
 	/**
 	 * 根据条件得到股东情况
 	 * @param lid
 	 * @return List<DrClaimsShareholder>
 	 */
     public List<DrClaimsShareholder> getDrClaimsShareholderByLid(Integer lid); 
}