package com.jsjf.dao.activity;

import com.jsjf.model.activity.BypCommodityDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BypCommodityDetailDAO {

	public List<BypCommodityDetail>  doubleTwelve(Integer uid);

	public void insertSelective(BypCommodityDetail bcdl);

	public List<BypCommodityDetail> selectTopIntegralLog();

	/**根据UID查询礼品信息
	 * @param uid
	 * @return
	 */
	public BypCommodityDetail selectConvertGiftByUid(Integer uid);

    List<HashMap<String,Object>> getMyEveryoneJdCard(Map<String, Object> param);

	List<Map<String,Object>> selectToHelpFarmersTop(Map<String, Object> map);

	List<Map<String,Object>> selectToHelpFarmers(Map<String, Object> map);

	/**
	 * 查询用户是否有该物品记录
	 * @param uid
	 * @param pid
	 * @return
	 */
	BypCommodityDetail selectToHelpFarmersBypCommodity(@Param("uid") Integer uid, @Param("pid") Integer pid);

    BypCommodityDetail selectLastCommodityDetail(Map<String, Object> param);

	void updateSelectiveByUid(BypCommodityDetail detail);

    List<BypCommodityDetail> selectMyIntegralLog(Integer uid);
}