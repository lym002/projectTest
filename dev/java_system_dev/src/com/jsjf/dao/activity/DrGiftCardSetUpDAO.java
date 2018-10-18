package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrGiftCardSetUp;

public interface DrGiftCardSetUpDAO {
	/**
	 * 插入
	 * @param drGiftCardSetUp
	 * @return
	 */
	public Integer insertDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp);
	
	/**
	 * 获取设置列表
	 * @param map
	 * @return
	 */
	public List<DrGiftCardSetUp> getDrGiftCardSetUpList(Map<String,Object> map);
	
	/**
	 * 获取设置列表总数
	 * @param map
	 * @return
	 */
	public int getDrGiftCardSetUpCount(Map<String,Object> map);
	
	/**
	 * 根据ID获取设置详情
	 * @param id
	 * @return
	 */
	public DrGiftCardSetUp getDrGiftCardSetUpById(Integer id);
	
	/**
	 * 更新设置
	 * @param drGiftCardSetUp
	 */
	public void updateDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp);
	
	 

}
