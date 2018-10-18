package com.jsjf.service.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrMemberFavourable;

public interface DrMemberFavourableService {
	
	public List<DrMemberFavourable> selectByParam(Map<String, Object> map);
	
	/**
	 * 查询优惠券累计获取
	 * @param map
	 * @return
	 */
	public BigDecimal selectByParamSum(Map<String,Object> map);
	
	/**
	 * 贈送優惠券
	 * @param uid 接收人
	 * @param faId 优惠券ID
	 */
	public void sendFavourable(Integer uid ,Integer faId);
	
	/**
	 * 查询优惠券信息
	 * @param id 优惠券主键ID
	 * @return
	 */
	public DrMemberFavourable selectByPrimaryKey(Integer id);
	/**
	 * 查询两条额度最高的红包,amount 降 expireDate 升序
	 * @param uid
	 * @return
	 */
	public List<DrMemberFavourable> selectFavourableOrderByAmountExpireDate(int uid);
	
	/**
	 * 查询用户的体验金
	 */
	public List<DrMemberFavourable> selectDrMemberFavourableByUid(int uid);
	
	public List<DrMemberFavourable> selectRedMsg(Integer amount);
	/**
	 * 查询用户未使用的体验金总额和id
	 * @return
	 */
	public Map<String,Object> selectExperSumAmountId(int uid);
	/**
	 * 查询用户未使用的体验金总额和id
	 * @return
	 */
	public Map<String,Object> selectExperSumAmountIdByMap(Map<String,Object> map);
	
	/**
	 * 查询用户的体验金总额
	 * @param uid
	 * @return
	 */
	public BigDecimal selectExperienceSumByUid(int uid);
	
	/**
	 * 根据uid查询红包记录
	 * @param uid
	 * @return
	 */
	public int selectRedCountByUid(Integer uid);
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
	
	public BigDecimal selectRegSendExperienceGold(Integer uid);
	/**
	 * 获取用户优惠券统计
	 * @param map
	 * @return
	 */
	public Integer getMemberFavourableTotal(Integer uid);
	
	/**
	 * 查询老客户红包是否使用
	 * @param uid
	 * @return
	 */
	public List<DrMemberFavourable> selectOldIsUse(Integer uid);

}
