package com.jsjf.service.cpa;

import java.util.Map;

import com.jsjf.common.PageInfo;
import com.jsjf.model.cpa.DrChannelKeyWords;

public interface DrChannelKeyWordsService {
	
	/**
	 * 保存或修改渠道关键字
	 * @param drChannelKeyWords
	 */
	public void saveOrUpdate(DrChannelKeyWords drChannelKeyWords);
	
	/**
	 * 通过渠道号和关键字查找渠道关键字
	 * @param param
	 * @param pi
	 * @return
	 */
	public PageInfo selectKeywordListByParam(Map<String, Object> param, PageInfo pi);
	
	/**
	 * 修改渠道关键字状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Integer updateKeyWordStatusById(Integer id, Integer status);
	
	/**
	 * 根据CPA ID获取需要下载数据
	 */
	public Map<String, Object> getKeyWordUrlByCpaId(Integer id);
	
}
