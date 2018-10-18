package com.jsjf.dao.activity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.apache.ibatis.annotations.Param;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrMemberFavourable;

public interface DrMemberFavourableDAO {
	/**
	 * 插入用户优惠券
	 * @param drMemberFavourable
	 * @throws SQLException
	 */
	public void insertIntoInfo(DrMemberFavourable drMemberFavourable) throws SQLException;
	/**
	 * 获取用户优惠券列表
	 * @param map
	 * @return
	 */
	public List<DrMemberFavourable> getMemberFavourableByParam(Map<String,Object> map); 
	/**
	 * 获取用户优惠券统计
	 * @param map
	 * @return
	 */
	public Integer getMemberFavourableTotal(Map<String,Object> map); 
	
	public void updateFavourableStatus(DrMemberFavourable drMemberFavourable);
	
	/**
	 * 批量插入优惠券
	 * @param list
	 */
	public void batchInsert(List<DrMemberFavourable> list);
	
	/**
	 * 获取活动大礼包
	 * @param map
	 */
	public List<DrMemberFavourable> getMemberFavourableByValentine(Map<String,Object> map);
	
	/**
	 * 查询优惠券信息
	 * @param id 优惠券主键ID
	 * @return
	 */
	public DrMemberFavourable selectByPrimaryKey(Integer id);
	
	/**
	 * 查询用户  pid 不为空 未过期利率最高的加息券
	 */
	public List<Map<String,Object>> selectRaisedRatesAndPid(int uid);
	
	
	/**
	 * 查询用户在次产品上获得的加息券
	 */
	public List<DrMemberFavourable> selectDrMemberFavourableByPid(Map<String,Object> map);
	
	/**
	 * 跟据map修改
	 * @param map
	 */
	public void updateFavourableStatusByMap(Map<String,Object> map);
	
	/**
	 * 查询两条额度最高的红包,amount 降 expireDate 升序
	 */
	public List<DrMemberFavourable> selectFavourableOrderByAmountExpireDate(int uid);
	
	/**
	 * 查询用户的体验金
	 */
	public List<DrMemberFavourable> selectDrMemberFavourableByUid(@Param("uid")  int uid);
	
	public List<DrMemberFavourable> selectRedMsg(Integer amount);
	
	/**
	 * 查询用户的体验金总额
	 * @param uid
	 * @return
	 */
	public BigDecimal selectExperienceSumByUid(@Param("uid")  int uid);
	/**
	 * 查询用户未使用的体验金总额和id
	 * @return
	 */
	public Map<String,Object> selectExperSumAmountId(@Param("uid")  int uid);
	/**
	 * 查询用户未使用的体验金总额和id
	 * @return
	 */
	public Map<String,Object> selectExperSumAmountIdByMap(Map<String,Object> map);
	
	/**
	 * 根据uid查询红包记录
	 * @param uid
	 * @return
	 */
	public int selectRedCountByUid(Integer uid);
	/**
	 * 查询优惠券累计获取
	 * @param map
	 * @return
	 */
	public BigDecimal selectByParamSum(Map<String,Object> map);
	/**
	 * 查询可用体验金
	 * @param uid
	 * @return
	 */
	public BigDecimal getMemberFavourableSumByUid(Integer uid);
	
	/**
	 * 查询是否有未激活的体验金
	 * @param uid
	 * @return
	 */
	public Integer selectIsShowCountByUid(Integer uid);
	/**
	 * 查询注册送体验金
	 * @return
	 */
	public BigDecimal selectRegSendExperienceGold(Integer uid);
	/**
	 * 查看是否使用了老客户红包
	 * @param uid
	 * @return
	 */
	public List<DrMemberFavourable> selectOldIsUse(@Param("uid")Integer uid);

    Integer selectUserCount(Map<String, Object> map);

    Integer selectByParameterId(Map<String, Object> map);

    DrMemberFavourable getMemberFavourableByCode(Map<String, Object> map);

    List<Map<String,Object>> selectByParam(Map<String, Object> param);

    Map<String,Object> selectUserByAid(Map<String, Object> param);

    BigDecimal selectByParamSums(Map<String, Object> param);

    BigDecimal selectByAddRateParamSum(Map<String, Object> param);
}
