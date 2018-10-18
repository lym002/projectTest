package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrGiftCardSetUpDetail;

public interface DrGiftCardSetUpDetailDAO {
	/**
	 * 插入设置详情
	 * @param drGiftCardSetUpDetail
	 * @return
	 */
	public Integer insertDrGiftCardSetUpDetail(DrGiftCardSetUpDetail drGiftCardSetUpDetail);
	
	/**
	 * 批量插入
	 * @param list
	 */
	public void batchInsert(List<DrGiftCardSetUpDetail> list);
	
	/**
	 * 获取设置详情列表
	 * @param map
	 * @return
	 */
	public List<DrGiftCardSetUpDetail> getDrGiftCardSetUpDetailList(Map<String,Object> map);
	
	/**
	 * 根据头ID获取明细
	 * @param parentId
	 * @return
	 */
	public List<DrGiftCardSetUpDetail> getDrGiftCardSetUpDetailListByParentId(Map<String,Object> map);
	
	/**
	 * 获取设置详情总数
	 * @param map
	 * @return
	 */
	public int getDrGiftCardSetUpDetailCount(Map<String,Object> map);
	
	
	/**
	 * 更新设置详情
	 * @param id
	 */
	public void updateDrGiftCardSetUpDetailStatus(Integer id);
	
	/**
	 * 根据id获取详细信息
	 * @param id
	 */
	public DrGiftCardSetUpDetail getDrGiftCardSetUpDetailById(Integer id);

}
