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
	 * 
	 * @param map
	 * @return
	 */
	public List<JsActivityLuckyMoney> selectJsActivityLuckyMoneyByMap(Map<String,Object> map);
	/**
	 * 修改
	 */
	public void update (JsActivityLuckyMoney JsActivityLuckyMoney);
}
