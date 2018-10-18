package com.jsjf.dao.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityMemberAccount;

public interface JsActivityMemberAccountDAO {
	
	/**
	 * 添加
	 * @param id
	 * @return
	 */
	public void insert(JsActivityMemberAccount jama);
	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public JsActivityMemberAccount selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 获得返利活动
	 * @param map
	 * @return
	 */
	public List<JsActivityMemberAccount> selectJsActivityMemberAccount(Map<String,Object> map);
	
	/**
	 * 修改
	 */
	public void update(Map<String,Object> map);
	/**
	 * 完成当期首投好友数
	 * @param uid
	 * @return
	 */
	public int selectFirstInvestCount(Map<String,Object> map);
	
	/**
	 * 活动奖励sum
	 * @param uid
	 * @return
	 */
	public BigDecimal selectActivityRewardsSum(Map<String,Object> map);
	
	/**
	 * 好友详情
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectFriendInvestList(Map<String,Object> map);
	/**
	 * 好友详情
	 * @param map
	 * @return
	 */
	public int selectFriendInvestListCount(Map<String,Object> map);
	
	/**
	 * 根据活动获取返现总额前10名的
	 * @param afid
	 * @return
	 */
	public List<JsActivityMemberAccount> selectFriendAmountTopTen(@Param(value="afid") Integer afid);
}
