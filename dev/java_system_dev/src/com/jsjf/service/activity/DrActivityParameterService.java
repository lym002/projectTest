package com.jsjf.service.activity;


import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.system.SysUsersVo;

public interface DrActivityParameterService {
	/**
	 * 获得活动列表
	 * @param info
	 * @param sysActivityParameter
	 * @return
	 * @throws Exception
	 */
	public List<DrActivityParameter> getActivityList(DrActivityParameter drActivityParameter) throws Exception;
	
	/**
	 * 获得活动列表
	 * @param info
	 * @param sysActivityParameter
	 * @return
	 * @throws Exception
	 */
	public PageInfo getActivityList(PageInfo info,DrActivityParameter drActivityParameter) throws Exception;
	/**
	 * 修改活动详情
	 * @param sysActivityParameter
	 * @throws Exception
	 */
	public void toModifyActivity(DrActivityParameter drActivityParameter,SysUsersVo usersVo) throws Exception;
	/**
	 * 获得活动详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DrActivityParameter getActivityParameterById(Integer id) throws Exception;
	/**
	 * 添加活动
	 * @param sysActivityParameter
	 * @throws Exception
	 */
	public BaseResult insertActivity(DrActivityParameter drActivityParameter,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 关闭活动
	 * @param sysActivityParameter
	 * @param usersVo
	 * @throws Exception
	 */
	public void updateStatus(DrActivityParameter drActivityParameter,SysUsersVo usersVo) throws Exception;
	
	public List<DrActivityParameter> getActivityParameterList(Map<String, Object> map) throws Exception;
	
	/**
	 * 关闭券发放规则  关闭相关联的券
	 * @param coupons
	 * @throws Exception
	 */
	public void updateStatusByRules(String coupons);

	/**
	 * 插入好友邀请返现到活动账户
	 */
	public void insertFriendMemberActivityAmount();
	
	/**
	 * 插入好友邀请三重好礼 记录
	 */
	public void insertThreePresent();
	/**
	 * 发放好友邀请三重好礼 返现到活动账户
	 */
	public void insertSendThreePresentToAccount(int uid,ActivityFriend jaf) throws Exception;
	
	/**
	 * 发放促复投红包
	 */
	public void insertCpsFavourable() throws Exception;

	/**
	 *根据规则送红包
	 * @return 
	 */
	public void valentineActivitys(int uid,Integer type)throws Exception;
	
	/**
	 * 根据规则发放感恩回馈
	 * @param uid
	 * @param type
	 * @throws Exception
	 */
	public void gratitudeBlessing(int uid)throws Exception;
	
	
	public void annualBonus(Map<String,Object> param);
	
	
	/**
	 * 首投用户，投资后第5天 送券, type = 2 and if(dp.atid or dp.prizeId,0,1)
	 */
	public void marketFirstInvestLaterdays(Integer day,String key,String name,int type);
	
	
	/**
	 * 发放促复投红包
	 */
	public void insertCpsFavourables() throws Exception;
	
	/**
	 * 三重好礼排行榜
	 * @throws Exception
	 */
	public void selectThreePresentTop() throws Exception;
	/**
	 * 三重好礼发加息券
	 * @throws Exception
	 */
	public void threePresentMultipleCoupon(Integer uid) throws Exception;
	
	/**
	 * 发券--获取券列表
	 * @param info
	 * @param drActivityParameter
	 * @return
	 * @throws Exception
	 */
	public PageInfo getGiveOutAPList(PageInfo info, DrActivityParameter drActivityParameter) throws Exception;
	
}
