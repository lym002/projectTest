package com.jsjf.service.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCpsFavourableRule;

public interface DrActivityParameterService {
	/**
	 * 情人节活动
	 * @return 
	 */
	public boolean valentineActivity(DrMember member)throws Exception;
	/**
	 *根据规则送红包
	 * @return 
	 */
	public void valentineActivitys(int uid, int type,int status,String toFrom)throws Exception;
	
	public DrCouponsIssuedRules getcouponsIssuedRules(Map<String, Object> map)throws Exception;
	
	/**
	 * 砸金蛋,随机加息券
	 * @param m
	 * @param pid
	 * @return
	 */
	public BaseResult getRandomCouponByProductId(DrMember m,int pid);
	
	/**
	 * 7天回款添加红包
	 * @return
	 */
	public void insertActivityParameter(List<DrMemberCpsFavourableRule> list,Integer uId,BigDecimal ShouldPrincipalCount,String mobilephone)throws Exception;

	/**
	 * 根据规则查询红包
	 * @return
	 */
	public List<Map<String,Object>> getActivityParameterByRules();

	/**
	 * 查询banner页数据
	 * @param map
	 * @return
	 */
	BaseResult selectRedEnvelope(Map<String, Object> map);

	/**
	 * 开始抢红包
	 * @param map
	 * @return
	 */
	BaseResult lootRedEnvelope(Map<String, Object> map);

	/**
	 * 查询主页banner
	 * @param map
	 * @return
	 */
	Map<String, Object> getEnvelope(Map<String, Object> map);


	/**
	 * 查询top10 关于抢红包
	 * @param map
	 * @return
	 */
	BaseResult getRedEnvelopeTop(Map<String, Object> map);
}
