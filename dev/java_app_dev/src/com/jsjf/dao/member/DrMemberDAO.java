package com.jsjf.dao.member;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMember;
import com.jsjf.model.weixin.BindWeixinBean;

public interface DrMemberDAO {
	public void insertDrMember(DrMember DrMember)throws SQLException;

    public int updateByPrimaryKey(DrMember record);

    public DrMember selectByPrimaryKey(Integer uid);

    public DrMember selectDrMemberByMobilephone(@Param("loginId") String loginId);
    
    Integer selectRecommRegSum(Integer uid);
    
    /**
	 * 获取用户待还和待收笔数
	 * @param map
	 * @return
	 */
	public Map<String,Integer> queryCollectCountAndStayCount(Map<String,Object> map);
	
	/**
	 * 通过推荐码或者手机号获取用户
	 * @param str
	 * @return
	 */
	public DrMember selectDrMemberByMobileOrRecomm(@Param("str") String str);
	
	public List<DrMember> selectMemberIsJoinActivity(@Param("uid") Integer uid);
	
	public Integer isExistsMobilphone(@Param("mobilephone")String mobilephone);
	/**
	 * 通过推荐码获取用户脱敏手机号
	 * @param str
	 * @return
	 */
	public String selectMobilePhoneByRecommCode(@Param("recommCode") String recommCode);
	
	/**
	 * 用户是否首投
	 * @param uid
	 * @return
	 */
	public int selectFirstPayMent(Integer uid);
	/**
	 * 查询 最近注册100条用户,手机号,注册时间
	 * @return
	 */
	public List<Map<String,Object>> lastRegMember();
	public Map<String,Object> selectIndexSummaryData();
	
	/**
	 * 查询总注册人数
	 * @return
	 */
	public int selectDrmembercount();
	/**
	 * 查询用户是不是当月注册的
	 * @return
	 */
	public DrMember selectDrMemberByMonth(Integer uid);
	/**
	 * 查询是不是老byp客户
	 * @param uid
	 * @param ids 
	 * @return
	 */
	public DrMember selectIsBypOldUser(@Param("uid")Integer uid, @Param("ids")int[] ids);
	
	/**
	 * 悲观锁修改
	 * @param uid
	 * @return
	 */
	public DrMember selectForUpDateByPrimaryKey(Integer uid);

	public void insertDevice(DrMember member);

	/**
	 * 如果安卓uuid已存在则修改
	 * @param member
	 */
	public void updateDrMember(DrMember member);

	public int queryMemberUuid(String androidUuid);

	public String queryMemberUid(String androidUuid);

    Integer selectDrmemberSignDays(Map<String, Object> param);

	public String queryOpenId(Integer uid);

	public void insertopenId(BindWeixinBean wxBean);
}