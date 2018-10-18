package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
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
	
	public void insertLogtteryLog(DrMemberLotteryLog drMemberLotteryLog);
	
	/**
	 * 查询最后一次的奖品
	 * @param uid
	 * @return int
	 */
	public String selectGiftName(int uid);
	
	/**
	 * 获取是否已经领取元宵奖品
	 * @param map
	 * @return
	 */
	public boolean selectListByParam(Map<String, Object> map);
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
	 * 查询个人抽奖纪录
	 * @return
	 */
	public List<DrMemberLotteryLog> selectRecords(Integer uid);
}
