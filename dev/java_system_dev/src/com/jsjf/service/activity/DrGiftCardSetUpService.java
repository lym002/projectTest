package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.DrGiftCardSetUp;
import com.jsjf.model.activity.DrGiftCardSetUpDetail;

public interface DrGiftCardSetUpService {
	/**
	 * 插入
	 * @param drGiftCardSetUp
	 */
	public void insertDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp)throws Exception;
	
	/**
	 * 根据条件获取设置列表
	 * @param drGiftCardSetUp
	 * @param pi
	 * @return
	 */
	public PageInfo getDrGiftCardSetUpList(DrGiftCardSetUp drGiftCardSetUp,PageInfo pi)throws Exception;
	
	/**
	 * 根据ID获取设置
	 * @param id
	 * @return
	 */
	public DrGiftCardSetUp getDrGiftCardSetUpById(Integer id)throws Exception;
	
	/**
	 * 更新
	 * @param drGiftCardSetUp
	 */
	public void updateDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp)throws Exception;
	
	/**
	 * 插入兑换券
	 * @param drGiftCardSetUpDetail
	 * @throws Exception
	 */
	public void insertDrGifCardDetail(DrGiftCardSetUpDetail drGiftCardSetUpDetail)throws Exception;
	
	/**
	 * 批量插入兑换券
	 * @param list
	 * @throws Exception
	 */
	public void batchInsert(List<DrGiftCardSetUpDetail> list)throws Exception;
	
	/**
	 * 获取兑换券使用情况列表
	 * @param parentId
	 * @return
	 */
	public PageInfo getDrGiftCardSetUpDetailList(DrGiftCardSetUpDetail detail,PageInfo pi)throws Exception;
	
	/**
	 * 根据头ID获取明细列表
	 * @param parentId
	 * @return
	 */
	public PageInfo getDrGiftCardSetUpDetailListByParentId(Integer parentId,PageInfo pi)throws Exception;
	
	/**
	 * 根据ID获取详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DrGiftCardSetUpDetail getDrGiftCardSetUpDetailById(Integer id)throws Exception;
	
	/**
	 * 兑换券失效
	 * @param detail
	 * @throws Exception
	 */
	public void updateDrGiftCardSetUpDetailStatus(Integer id)throws Exception;
	

}
