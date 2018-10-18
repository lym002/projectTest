package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityLuckyMoney;

public interface JsActivityLuckyMoneyDAO {

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JsActivityLuckyMoney selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 根据所传条件查询列表
	 * @param map
	 * @return
	 */
	public List<JsActivityLuckyMoney> selectJsActivityLuckyMoneyByMap(Map<String,Object> map);
	/**
	 * 修改
	 */
	public void update (JsActivityLuckyMoney JsActivityLuckyMoney);
	/**
	 * 添加
	 * @param jsActivityLuckyMoney
	 */
	public void insert(JsActivityLuckyMoney jsActivityLuckyMoney);
	/**
	 * 根据uid查询是否分享过
	 * @param shaerUid
	 * @return
	 */
	public List<JsActivityLuckyMoney> selectByshaerUid(Integer uid);
	/**
	 * 根据mobilePhone查询是否分享过
	 * @param mobilePhone
	 * @return
	 */
	public List<JsActivityLuckyMoney> selectByshaerPhone(Map<String,Object> map);
}
