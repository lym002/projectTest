package com.jsjf.dao.claims;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.DrClaimsCustomer;

public interface DrClaimsCustomerDAO {
	
 	/**
 	 * 添加企业客户基本信息
 	 * @param  DrClaimsCustomer
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrClaimsCustomer(DrClaimsCustomer drClaimsCustomer) throws SQLException; 
 	
 	/**
 	 * 修改企业客户基本信息
 	 * @param  DrClaimsCustomer
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrClaimsCustomer(DrClaimsCustomer drClaimsCustomer) throws SQLException; 
 	
 	/**
 	 * 根据条件得到企业客户基本信息
 	 * @param lid
 	 * @return DrClaimsCustomer
 	 */
     public DrClaimsCustomer getDrClaimsCustomerByLid(Integer lid); 
     
     /**
      * 根据id得到企业客户基本信息
      * @param id
      * @return DrClaimsCustomer
      */
     public DrClaimsCustomer getDrClaimsCustomerById(Integer id); 
  	/**
  	 * 删除企业客户基本信息
  	 * @param  lid
  	 * @return void
  	 * @throws SQLException;
  	 */
  	public void deleteDrClaimsCustomer(Integer lid) throws SQLException; 
  	
  	public List<Map<String, Object>> selectCustomerFuiou(Map<String,Object> param);
  	
  	public int selectCustomerFuiouCount(Map<String,Object> param);

	public List<Map<String, Object>> selectGenerateAndupload();
	
	public void updateFileStatus(List<DrClaimsCustomer> param);
    /**
     * 根据流水号查询
     * @param mchnt_txn_ssn 
     * @return
     */
	public DrClaimsCustomer selectCustomerByMchnt_txn_ssn(String mchnt_txn_ssn);
	
	/**
	 * 根据手机号获取企业客户基本信息
	 * @param phone
	 * @return
	 */
	public DrClaimsCustomer selectCustomerByPhone(int id); 
	/**
	 * 根据存管企业登录名查询一条包含流水号的企业信息
	 * @param userId
	 * @return
	 */
	public DrClaimsCustomer selectCustmoerByUserId(String userId);

	/**
	 * 将多笔债权对应无流水号的法人信息fileStatus更新
	 */
	public void updateCompanyStatus();
}