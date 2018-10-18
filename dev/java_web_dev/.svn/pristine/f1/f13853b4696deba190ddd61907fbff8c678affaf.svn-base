package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;

public interface DrMemberService {
	
	
	public Integer insertMember(DrMemberBaseInfo baseinfo,DrMember member, Integer rid) throws Exception;
	
	/**
	 * 获取要登录的用户
	 * @return
	 */
	public DrMember selectDrMemberByMobilephone(String mobilephone);
	
	
	/**
	 * 修改用户信息
	 * @param DrMember
	 * @throws Exception
	 */
	public void update(DrMember DrMember) throws Exception;
	
	/**
	 * 根据用户ID获取用户
	 * @param uid
	 * @return
	 */
	public DrMember selectByPrimaryKey(Integer uid);
	
	/**
	 * 查询推荐人
	 * @param str
	 * 		  推荐码或者手机号码查询
	 * @return
	 */
	public DrMember selectDrMemberByMobileOrRecomm(String str);
	
	/**
	 * 查询用户是否参加活动
	 * @param uid
	 * @return List等于0表示用户可以参加活动
	 */
	public List<DrMember> selectMemberIsJoinActivity(@Param("uid") Integer uid);
	
	/**
	 * 
	 */
	public Map<String,Object> selectIndexSummaryData();
	
	/**
	 * 判断手机号码是否存在
	 * @param mobilephone
	 * @return 
	 * 		true 存在 false不存在
	 */
	public boolean isExistsMobilphone(String mobilephone);
	
	/**
	 * 根据推荐码获取脱敏手机号
	 * @param recommCode
	 * @return
	 */
	public String selectMobilePhoneByRecommCode(String recommCode);
	/**
	 * 根据id判断是否首投
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
	 * 个人开户
	 * @param message
	 * @throws Exception
	 */
	public void openAccountRes(JSONObject message,HttpServletRequest req) throws Exception;
	/**
	 * 个人开户直连
	 * @param message
	 * @throws Exception
	 */
	public void openAccount(JSONObject message) throws Exception;
	
	
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

    String selectBankName(String parent_bank_id);

	public String queryOpenId(Integer uid);
}