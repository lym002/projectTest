package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberLotteryLog;

public interface DrMemberLotteryLogService {

	/**
	 * 双旦活动抽奖
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public BaseResult insertLogtteryLogDoubleEgg(DrMember member)  throws Exception;
	/**
	 * 抽奖
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public BaseResult insertLogtteryLog(DrMember member)  throws Exception;
	
	public void insertLogtteryLog(DrMemberLotteryLog drMemberLotteryLog);
	
	
	public List<DrMemberLotteryLog> selectListByParam(Map<String, Object> map);
	
	/**
	 * 获取抽奖记录
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
	 * 条件查找抽中奖品列表
	 * @param param
	 * @param pi
	 * @return
	 */
	public BaseResult selectLotteryListByParams(PageInfo pi,int uid);
	
	/**
	 * 七夕抽奖
	 * @param param
	 * @param pi
	 * @return
	 */
	public BaseResult updateLotteryQX(int uid)throws Exception;
	
	/**
	 * 获取已抽奖次数
	 * @param uid
	 * @return int
	 */
	public int getLotteryCount(int uid);
	
	/**
	 * 查询最后一次的奖品
	 * @param uid
	 * @return int
	 */
	public String selectGiftName(int uid);
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
	/**
	 * 第一次抽奖给与默认机会
	 * @param map
	 * @return
	 */
	public Integer getDoubleAGGOneLottery(Map<String, Object> map);
	/**
	 * @param uid
	 * @return
	 */
	public List<DrMemberLotteryLog> selectRecords(Integer uid);
}
