package com.jsjf.service.product;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;

public interface JsActivityProductInvestInfoService {
	
	/**
	 * 活动页本期往期开奖结果
	 * @return
	 */
	public BaseResult getActivityPrizeResult(Map<String,Object> map);
	
	/**
	 * 查询  每期的中奖者
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>  selectJsActivityProductIsWinner(Map<String,Object> map);
	
	
	/**
	 * 活动中心
	 * @param pid
	 * @param m
	 * @return
	 */
	public BaseResult jsActivityProductCenter(Map<String,Object> map);
	
	
	/**
	 * 活动标参与人数量
	 * @param atid
	 * @param m
	 * @return
	 */
	public int selectjsActivityProductInvestInfoCount(int atid,int id);
	/**
	 * iphone7 活动总投资人数,活动中奖总人数
	 * @return
	 */
	public Map<String,Object> iPhoneCensus();
}
