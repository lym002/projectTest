package com.jsjf.dao.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.BypCommodityDetail;
import org.apache.ibatis.annotations.Param;

public interface BypCommodityDetailDAO {
	/**
	 * 双十二活动查看该用户对于的京东卡奖品
	 * 
	 * @param uid
	 * @return
	 */
	public List<BypCommodityDetail> doubleTwelve(Integer uid);

	/**
	 * 查看用户双十二列表
	 * 
	 * @param uid
	 * @return
	 */
	public Map<String, Object> selectDoubleTwelve(Integer uid);
	/**
	 * 插入数据
	 * @param record
	 * @return
	 */
	public int insertSelective(BypCommodityDetail record);
	/**
	 * 查询top10兑换记录
	 * @return
	 */
	public List<BypCommodityDetail> selectTopIntegralLog();
	/**
	 * 双旦查看我的兑换记录
	 * @param uid
	 * @return
	 */
	public List<BypCommodityDetail> selectMyIntegralLog(Integer uid);
	/**
	 * 获取用户的奖品根据类型
	 * @param uid
	 * @return
	 */
	public List<BypCommodityDetail> selectMyAward(Map<String, Object> map);

	/**根据UID查询礼品信息
	 * @param uid
	 * @return
	 */
	public BypCommodityDetail selectConvertGiftByUid(Integer uid);

    List<HashMap<String,Object>> getMyEveryoneJdCard(Map<String, Object> map);

    List<Map<String,Object>> selectToHelpFarmersTop(Map<String, Object> map);

    List<Map<String,Object>> selectToHelpFarmers(Map<String, Object> map);

	/**
	 * 查询用户是否有该物品记录
	 * @param uid
	 * @param pid
	 * @return
	 */
	BypCommodityDetail selectToHelpFarmersBypCommodity(@Param("uid") Integer uid, @Param("pid") Integer pid);
}
