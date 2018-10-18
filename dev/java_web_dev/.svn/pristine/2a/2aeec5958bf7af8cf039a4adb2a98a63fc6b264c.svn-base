package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberLotteryLog;

public interface DrMemberLotteryLogDAO {
	
	/**
	 * 获取抽奖记录
	 * @param map
	 * @return
	 */
	public List<DrMemberLotteryLog> selectListByParam(Map<String, Object> map);
	
	
	public void insert(DrMemberLotteryLog lotteryLog);
	
	/**
	 * 获取抽奖记录【七夕】
	 * @return
	 */
	public List<DrMemberLotteryLog> selectDrMemberLotteryLogByMap();
	
	/**
	 * 获取抽奖次数
	 * @param uid
	 * @return int
	 */
	public int getLotteryCountByUid(int uid);
	
	/**
	 * 获取已抽奖次数
	 * @param uid
	 * @return int
	 */
	public int getLotteryCount(int uid);
	
	/**
	 * 获取抽中奖励列表
	 * @param map
	 * @return
	 */
	public List<DrMemberLotteryLog> selectDrMemberLotteryLogList(Map<String, Object> map);
	
	/**
	 * 获取抽中奖励列表总数
	 * @param map
	 * @return Integer
	 */
	public Integer selectDrMemberLotteryLogListCount(Map<String, Object> map);
	
	/**
	 * 修改
	 * @param log
	 */
	public void update(DrMemberLotteryLog log);
	/**
	 * 查询最后一次的奖品
	 * @param log
	 */
	public String selectGiftName(Integer uid);
	
	/**
	 * 获取双蛋抽奖次数
	 * @param uid
	 * @return
	 */
	public Integer getDoubleAGGLotteryCountByUid(Map<String, Object> map);
	
	/**
	 * 双蛋假数据
	 * @param map
	 * @return
	 */
	public List<DrMemberLotteryLog> getDoubleAGGListCountByUid();
	/**
	 * 查询元宵领取记录
	 * @return
	 */
	public List<DrMemberLotteryLog> selectLotteryLogByAid();


	public DrMemberLotteryLog getDoubleEGGByUid(Map<String, Object> map);

	/**
	 * 获取第一次抽奖机会
	 * @param map
	 * @return
	 */
	public Integer getDoubleAGGOneLottery(Map<String, Object> map);


	public List<DrMemberLotteryLog> selectRecords(Integer uid);




	
}
