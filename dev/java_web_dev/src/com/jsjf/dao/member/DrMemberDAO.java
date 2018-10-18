package com.jsjf.dao.member;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMember;

public interface DrMemberDAO {
	public void insertDrMember(DrMember DrMember)throws SQLException;

    public int updateByPrimaryKey(DrMember record);

    public DrMember selectByPrimaryKey(Integer uid);

    public DrMember selectDrMemberByMobilephone(@Param("mobilephone") String mobilephone);
    
	
	/**
	 * 通过推荐码或者手机号获取用户
	 * @param str
	 * @return
	 */
	public DrMember selectDrMemberByMobileOrRecomm(@Param("str") String str);
	
	public List<DrMember> selectMemberIsJoinActivity(@Param("uid") Integer uid);
	
	public Map<String,Object> selectIndexSummaryData();
	
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
	/**
	 * 根据恒丰user_id查询用户
	 * @param mobilePhone
	 * @return
	 */
	public DrMember selectDrMemberByUserId(String user_id);
	
	/**
	 * 根据手机号查用户
	 * @param mobilePhone
	 * @return
	 */
	public DrMember selectDrMemberByMobilePhone(String mobilePhone);
	
	/**
	 * 查询总注册人数
	 * @return
	 */
	public int selectDrmembercount();


	/**
	 * @param uid
	 * @return
	 */
	public DrMember selectForUpDateByPrimaryKey(Integer uid);

	public String queryOpenId(Integer uid);

}