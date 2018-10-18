package com.jsjf.dao.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;


public interface DrProductInvestDAO {
	/**
	 * 得到投资记录列表
	 * @param Map
	 * @return List<DrProductInvest>
	 */
    public List<DrProductInvest> getDrProductInvestList(Map<String,Object> map); 

 	/**
 	 * 得到投资记录总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getDrProductInvestCounts(Map<String,Object> map); 
     
     
     
     public Integer selectInvestLogByParamCounts(Map<String,Object> map); 
     
     /**
      * 根据产品ID查询未处理的投资记录
      * @param pid
      * @return
      */
     public List<DrProductInvest> getDrProductInvestListByPid(@Param("pid")Integer pid);
     
     /**
      * 通过ID批量修改实际投资金额以及状态
      */
     public void batchUpdate(List<DrProductInvest> list);
     
     /**
      * 通过ID批量修改投资记录状态
      */
     public void updateStatusByIds(@Param("status")String status,@Param("ids")String[] ids);
     
     public BigDecimal getDrProductInvestByTime(Map<String,Object> map);
     
     /**
 	 * 得到投资记录
 	 * @param Map
 	 * @return List<DrProductInvest>
 	 */
     public List<DrProductInvest> selectInvestLogByParam(Map<String,Object> map);  
     
     /**
      * 获取未开始计息的新手标记录
      * @return
      */
     public List<DrProductInvest> selectNewhandNormalInvestLog();
     
     /**
      * 获取首投用户UID
      * @return
      */
     public List<Integer> getForFirstTimeInvestmentMember();
     
     /**
      * 根据投资人UID获取首投
      * @param uid
      * @return
      */
     public DrProductInvest getForFirstTimeInvestmentByUid(@Param("uid")Integer uid);
     
     /**
      * 查询投资用户信息
      * @param map
      * @return
      */
     public List<Map<String, Object>> selectInvestMemberInfoListByParam(Map<String, Object> map);
     public Map<String,Object> selectInvestMemberInfoListCountByParam(Map<String, Object> map);
     
     public List<DrProductInvest> selectProductInvestByPid(@Param("pid")Integer pid);
     
     /**
      * 每页统计
      * @param map
      * @return
      */
     public List<Map<String,Object>> selectInvestPageCountByParam(Map<String,Object> map);
     
     /**
      * 查询渠道投资记录
      * @param map
      * @return
      */
     public List<Map<String,Object>> QueryChannelInvestList(Map<String,Object> map);
     /**
      * 查询渠道投资记录
      * @param map
      * @return
      */
     public List<Map<String,Object>> QueryChannelYRT_InvestList(Map<String,Object> map);
     /**
      * 根据优惠活动列表查询相对应投资订单列表
      * @param map
      * @return
      */
	public List<Map<String, Object>> selectActivityInvestListByParam(Map<String, Object> map);
	/**
	 * 根据优惠活动列表查询相对应投资订单分页数
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectActivityInvestListCountByParam(Map<String, Object> map);
	
	/**
	 * 根据优惠活动列表查询相对应投资订单分页数汇总
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectActivityInvestListByParamCensus(Map<String, Object> map);
	
	
	
	/**
	 * 查询未来7天回款的投资记录（默认查询首笔投资）
	 * @return
	 */
	public List<Map<String, Object>> selectWillSevenDayRapyInvest(Map<String, Object> map);
	
	public void insertSelective(DrProductInvest invest);

	
	/**
	 * 注册第n天-未投资type=2 and if(atid or prizeId,0,1)
	 * @return
	 */
	public String[] selectRegNotInvest(@Param("day")Integer day);
	/**
	 * 注册第n天-未投资type=2 and if(atid or prizeId,0,1)
	 * @return
	 */
	public List<Map<String,Object>> selectRegNotInvests(@Param("day")Integer day);
	/**
	 * 首投(type=2 and if(atid or prizeId,0,1))之后第n天是今天
	 * @return
	 */
	public String[] selectfirstInvest(@Param("day")Integer day);

	
	/**
	 * 体验标
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectExperienceInvestMemberInfoListByParam(Map<String, Object> map);
	/**
	 * 体验标总计
	 * @param map
	 * @return
	 */
    public Map<String,Object> selectExperienceInvestMemberInfoListCountByParam(Map<String, Object> map);
    /**
     * 体验标每页统计
     * @param map
     * @return
     */
    public List<Map<String,Object>> selectExperienceInvestPageCountByParam(Map<String,Object> map);
    /**
     * 修改为已发送
     */
    public void updateRapyInvestIsgrant();
    
    /**
     * 复投订单查询
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInvestListForFuTou(Map<String,Object> map);
    /**
     * 复投订单查询总数
     * @param map
     * @return
     */
    public Map<String, Object> getInvestListForFuTouCount(Map<String,Object> map);
    
    /**
     * 根据uid订单查询
     * @param map
     * @return
     */
    public List<Map<String,Object>> getProductInvestListByUid(Map<String,Object> map);
    /**
     * 根据uid订单查询总数
     * @param map
     * @return
     */
    public Map<String, Object> getProductInvestCountByUid(Map<String,Object> map);
    
    /**
     * 根据uid来获取个人投资的总计
     * @param map
     * @return
     */
    public List<Map<String,Object>> getProductInvestSumByUid(Map<String,Object> map);
    /**
     * 复投订单查询本页总计
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInvestListForFuTouSumPage(Map<String,Object> map);
    /**
     * 复投订单查询总计
     * @param map
     * @return
     */
    public Map<String,Object> getInvestListForFuTouSumAll(Map<String,Object> map);
    /**
     * 修改存管标识状态
     * @param drProductInvest
     */
    public void updateFuiouProductInvest(DrProductInvest drProductInvest);
    /**
     * 批量修改存管标识状态
     * @param drProductInvest
     */
    public void updateFuiouProductInvestBatch(List<DrProductInvest> list);
    /**
     * 根据产品ID查询未处理的投资记录
     * @param pid
     * @return
     */
    public List<DrProductInvest> getFuiouDrProductInvestListByPid(@Param("pid")Integer pid);
    /**
     * 根据条件获取投资记录
     * @param map
     * @return
     */
    public DrProductInvest getProductInfoByParam(Map<String,Object> map);
    /**
	 * 根据id查询非新手标和体验标的投资记录
	 * @param uid
	 * @return
	 */
	public int selectIsOldUserById(Integer uid);
	
	/**
	 * 通过investId获得正常收益  =投资金额*rate*deadline/360/100
	 * @param investId
	 * @return
	 */
	public BigDecimal selectInvestNormalInterest(Integer investId);
	
	/**
	 * 通过id信息
	 * @param uid
	 * @return
	 */
	public DrProductInvest selectByPrimaryKey(Integer id);
	/**
	 * 获取最近投资的产品 存管编号 
	 * @param uid
	 * @return
	 */
	public String selectFuiouProject_no(Integer uid);
	
	
	public void updateFileStatus(List<DrProductInvest> param);

	/**根据投资id更新存正编号
	 * @param productList
	 */
	public void updateEvid(List<Map<String, String>> productList);

    List<Map<String,Object>> selectEveryoneTopFive(Map<String, Object> param);
}
