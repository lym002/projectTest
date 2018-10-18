package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;

import net.sf.json.JSONObject;

import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.weixin.BindWeixinBean;

public interface DrMemberService {
	
	
	public Integer insertMember(DrMemberBaseInfo baseinfo,DrMember member,Integer rid,String deviceJson) throws Exception;
	
	/**
	 * 获取要登录的用户
	 * @return
	 */
	public DrMember selectDrMemberByMobilephone(String mobilephone);
	
	/**
	 * 判断手机号码是否存在
	 * @param mobilephone
	 * @return 
	 * 		true 存在 false不存在
	 */
	public boolean isExistsMobilphone(String mobilephone);
	
	
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
	 * 获取用户待还和待收笔数
	 * @param map
	 * @return
	 */
	public Map<String,Integer> queryCollectCountAndStayCount(Map<String,Object> map);
	
	/**
	 * 查询推荐人
	 * @param str
	 * 		  推荐码或者手机号码查询
	 * @return
	 */
	public DrMember selectDrMemberByMobileOrRecomm(String str);
	
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
	public Map<String,Object> selectIndexSummaryData();
	/**
	 * 查询是不是老用户且没有发过红包
	 * @param uid
	 * @param ids 
	 * @return 
	 */
	public DrMember selectIsBypOldUser(Integer uid, int[] ids);
	/**
	 * 查询是不是老用户且没有发过红包
	 * @param uid
	 * @param ids 
	 * @return 
	 */
	public DrMember selectForUpDateByPrimaryKey(Integer uid);

	/**
	 * 持久化用户设备信息
	 * @param json
	 * @param uid
	 */
	public void insertDevice(com.alibaba.fastjson.JSONObject json, Integer uid);

	/**
	 * 安卓渠道用户会在用户注册之前获取用户手机识别码
	 * @param androidUuid
	 */
	public void insertMemberAndroidUuio(String androidUuid,String toFrom);

    /**
     * 查询bank名字
     * @param parent_bank_id
     * @return
     */
    String selectBankName(String parent_bank_id);

    BaseResult selectDrmemberSignDays(Map<String, Object> param);

	public String queryOpenId(Integer uid);

	public void insertopenId(BindWeixinBean wxBean);
}