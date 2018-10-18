package com.jsjf.dao.product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.common.BaseResult;
import com.jsjf.model.product.DrProductInfo;

public interface DrProductInfoDAO {
	
	/**
	 * 得到产品信息列表
	 * @param Map
	 * @return List<DrProductInfo>
	 */
    public List<DrProductInfo> getDrProductInfoList(Map<String,Object> map); 

 	/**
 	 * 得到产品信息总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrProductInfoCounts(Map<String,Object> map); 
     
 	/**
 	 * 添加产品信息
 	 * @param  DrProductInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrProductInfo(DrProductInfo drProductInfo) throws SQLException; 
    
	/**
	 * 修改产品信息
	 * @param  DrProductInfo
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfo(DrProductInfo drProductInfo) throws SQLException; 
	
	/**
	 * 取消预约
	 * @param  DrProductInfo
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductCancelBespoke(DrProductInfo drProductInfo) throws SQLException; 
	
 	/**
 	 * 根据id得到产品信息
 	 * @param id
 	 * @return DrProductInfo
 	 */
     public DrProductInfo getDrProductInfoByid(Integer id); 
     
     /**
      * 获取体验标
      * @param id
      * @return DrProductInfo
      */
     public List<DrProductInfo> getDrProductInfoExperience(); 
     
  	/**
  	 * 根据MAP得到产品信息
  	 * @param Map<String,Object>
  	 * @return DrProductInfo
  	 */
      public DrProductInfo getDrProductInfoByMap(Map<String,Object> map); 
      
      /**
       * 查询募集完成的产品 以及募集中的新手产品
       * @return
       */
      public List<DrProductInfo> selectRaiseSuccesProductInfo();
      
	/**
	 * 根据MAP得到产品信息List
	 * @param Map<String,Object>
	 * @return List<DrProductInfo>
	 */
	public List<DrProductInfo> getDrProductInfoListByMap(Map<String,Object> map); 
	public Integer getDrProductInfoListCountByMap(Map<String,Object> map); 
	
	/**
	 * 获取所有需要匹配债权的产品
	 * @return
	 */
	public List<DrProductInfo> getNeedMatchingProductList(Map<String,Object> map);
	
	/**
	 * 查询要到期的产品
	 * @return
	 */
	public List<DrProductInfo> selectExpireProductInfo();
	
	/**
	 * 通过PID修改产品状态
	 * @param  DrProductInfo
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfoStatusById(@Param("status") Integer status, @Param("pid") Integer pid) throws SQLException; 
	
	/**
	 * 查询未做债权匹配的续发产品
	 * @return
	 */
	public List<DrProductInfo> selectTransferProductInfo();
    /**
     * 修改产品债权匹配状态
     * @param id
     */
	public void updateMappingStatusByPid(Integer id);
	
	public List<Map<String,Object>> selectDrProductInfoList(Map<String,Object> map);
	
	/**
	 * 获取标的产品信息总数和总计
	 * @param map
	 * @return
	 */
	public Map<String, Object> getSubjectDrProductInfoCounts(Map<String, Object> map);
	
	/**
	 * 获取标的产品信息总数列表
	 * @param map
	 * @return
	 */
	public List<DrProductInfo> getSubjecDrProductInfoList(
			Map<String, Object> map);
	/**
	 * 获取标的产品的分页总计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getSubjectPageCountByParam(
			Map<String, Object> map);
	/**
	 * 获取放款清单总数列表 查询存管新手标type传3 否则可以不传
	 * @param map
	 * @return
	 */
	public List<DrProductInfo> getDrProductLoanList(Map<String, Object> map);
	/**
	 * 获取放款清单的分页总计
	 * @param map
	 * @return
	 */
	public Integer getDrProductLoanCounts(Map<String, Object> map);
	/**
	 * 更新产品的放款状态
	 * @param id
	 * @return
	 */
	public void updateDrProductLoanStatus(Map<String, Object> map);
	/**
	 * 更新产品的回款状态
	 * @param id
	 * @return
	 */
	public void updateReFundDrProductLoanStatus(Integer id);
	/**
	 * 根据id查询还款
	 * @return
	 */
	public List<Map<String, Object>> getReturnNoticeList(Integer id);
	/**
	 * 根据map查询新手标回款明细
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getReturnNoticeRecordList(Map<String, Object> map);
	
	public List<Map<String, Object>> selectProjectInformation();
	
	public void updateFileStatus(List<DrProductInfo> param);
	
	public List<DrProductInfo> getDrProductInfoByPeriodList(Map<String,Object> map);
	
	/**
	 * 查询 同类型的待上架产品
	 * @return
	 */
	public List<DrProductInfo> selectProductInfo(Map<String,Object> param);
	
	/**
	 * 只修改已募集金额，剩余金额，产品状态
	 * 通过主键修改
	 * @param info DrProductInfo
	 */
	public void updateProductSelective(DrProductInfo info);
	
	
	/**
	 * 申请产品的放款
	 * @param id
	 * @return
	 */
	public void updateDrProductLoanStatusSQ(Map<String, Object> map);

	/**
	 * 获取放款清单总数列表
	 * @param map
	 * @return
	 */
	public List<DrProductInfo> getDrProductLoanListSQ(Map<String, Object> map);
	/**
	 * 获取放款清单的分页总计
	 * @param map
	 * @return
	 */
	public Integer getDrProductLoanCountsSQ(Map<String, Object> map);
	
	/**
 	 * 根据id得到放款账户信息等
 	 * @param id
 	 * @return DrProductInfo
 	 */
     public DrProductInfo getDrProductInfoByidSQ(Integer id); 
     
     /**
      * 根据Prizeid获取产品
      * @param id
      * @return
      */
     public DrProductInfo getDrProductInfoByPrizeId(Integer PrizeId); 
     
     /**
      * 查询当前时间大于等于成立时间，并且以募集金额等于0并且type=2的产品list
      * @param type
      * @return
      */
     public List<DrProductInfo> getDrProductInfoByType(Integer type);
     
     public List<DrProductInfo> productFullRemind();

 	/**
 	 * 更新产品回款状态
 	 * @param  DrProductInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrProductInfoInterestRepay(Map<String, Object> map); 
 	
    /**
     * 查询存管计息失败的产品
     * @return
     */
    public List<DrProductInfo> selectDepositsRepayFail(Map<String,Object> map);
    
    /**
     * 查询待解冻的产品
     */
    public List<Map<String, Object>> getProductByThaw(Map<String,Object> map);
    
    public List<Map<String, Object>> getProductByProjectNo(Map<String, Object>map);
    
    public List<Map<String, Object>> getProductInvestRepayInfoByProjectNo(); 
    
    
	 /**
	   * 根据产品id获得  In_cust_no (借款人或企业的账户)
	   * @param id
	   * @return
	  */
	public String getIn_cust_noByProductId(Integer id);
    
	public void updateDrProductLoanByMchntTxnSsn(Map<String,Object> map);
	
	public DrProductInfo getDrProductLoanByMchntTxnSsn(Map<String,Object> map);
     /**
      * 项目数据情况列表
      * @param map
      * @return
      */
     public List<Map<String, Object>>getProductInfoDetail(Map<String, Object> map);
     
     /**
 	 * 获取所有存管回款列表
 	 * @param map
 	 * @return
 	 */
 	public List<DrProductInfo> getDepositReturnLoanList(Map<String, Object> map);
 	/**
 	 * 获取所有存管回款的分页总计
 	 * @param map
 	 * @return
 	 */
 	public Integer getDepositReturnLoanCounts(Map<String, Object> map); 

	/**
	 * 查询未来三天企业不足的企业
	 * @param dayNum
	 * @return
	 */
	public List<Map<String, Object>> getReimbursementInfo(Map<String, Object> map);
   
     
     public int getProductInfoDetailCount(Map<String, Object> map);
     
     /**
      * 服务费管理列表
      * @param map
      * @return
      */
     public List<Map<String, Object>>getProductServiceManagement(Map<String, Object> map);
     
     public int getProductServiceManagementCount(Map<String, Object> map);
     
     /**
      * 查询已募集完成的产品 排除新手标
      * @param map
      * @return
      */
     public DrProductInfo getProductInfoByCode(Map<String,Object> map); 
     
     /**
      * 获取放回款管理的放款金额总数
      * @param map
      * @return
      */
     public BigDecimal selectDrProductLoanAmountSum(Map<String,Object> map);

	/**
	 * 官网显示新手标满标的数量
	 * @return
	 */
	public int getNewFinancingEnd();
	
	/**
	 * 根据商品ID查询募集中的产品条数
	 * @param prizeId
	 * @return
	 */
	public int getProductPrize(Integer prizeId);

	public int getDealRecord(Integer uid);
	/**
	 * 查询产品待还款状态
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectProductInfoStatus();

    List<Map<String,Object>> selectSyncProductInfo();
}