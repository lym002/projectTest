package com.jsjf.dao.claims;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.claims.DrClaimsLoan;

public interface DrClaimsLoanDAO {
	
	/**
	 * 得到贷款项目基本信息
	 * @param Map
	 * @return List<DrClaimsLoan>
	 */
    public List<DrClaimsLoan> getDrClaimsLoanList(Map<String,Object> map); 

 	/**
 	 * 得到贷款项目基本信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrClaimsLoanCounts(Map<String,Object> map); 
     
 	/**
 	 * 添加贷款项目基本信息
 	 * @param  DrSubjectInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrClaimsLoan(DrClaimsLoan drClaimsLoan) throws SQLException; 
    
	/**
	 * 修改贷款项目基本信息
	 * @param  DrSubjectInfo
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrClaimsLoan(DrClaimsLoan drClaimsLoan) throws SQLException; 
	
 	/**
 	 * 根据id得到贷款项目基本信息
 	 * @param id
 	 * @return DrClaimsLoan
 	 */
     public DrClaimsLoan getDrClaimsLoanByid(Integer id); 
     
 	/**
 	 * 根据MAP得到贷款项目基本信息
 	 * @param Map
 	 * @return List<DrClaimsLoan>
 	 */
     public List<DrClaimsLoan> getDrClaimsLoanByMap(Map<String,Object> map); 
     
  	/**
  	 * 得到贷款项目基本信息总金额
  	 * @param Map
  	 * @return BigDecimal
  	 */
 	public BigDecimal getDrClaimsLoanSum(Map<String,Object> map);
 	
 	
	/**
	 * 得到债权统计信息
	 * @param Map
	 * @return List<DrClaimsLoan>
	 */
    public List<DrClaimsLoan> getDrClaimsStatisList(Map<String,Object> map); 

 	/**
 	 * 得到债权统计信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrClaimsStatisCounts(Map<String,Object> map); 
     
    public void updateClaimsLoanStatusById(@Param("status")Integer status, @Param("id")Integer id);
    
    public DrClaimsLoan getDrClaimsLoanBySid(@Param("sid")Integer sid);
    /**
     * 资产关闭状态
     * @param id
     */
	public void updateShowOffStatusBtn(Integer id);
	/**
     * 资产开启状态
     * @param id
     */
	public void updateShowOnStatusBtn(Integer id);
	/**
	 * 导出投资记录
	 * @param param
	 * @return
	 */
	public List<DrClaimsLoan> exportClaimsLoanList(Map<String, Object> param);
	
	public List<Map<String, Object>>getFuiouAreaCity();
	
	public List<Map<String, Object>>getFuiouAreaByCity(Integer cityCode);
	
	public List<Map<String, Object>>getFuiouBankCode();

	public DrClaimsLoan selectByPrimaryKey1(Integer id);
}