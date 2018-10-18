package com.jsjf.dao.cpa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.cpa.DrChannelKeyWords;

public interface DrChannelKeyWordsDAO {
	
	public void saveOrUpdate(DrChannelKeyWords drChannelKeyWords);
	
	List<DrChannelKeyWords> selectKeywordListByParam(Map<String, Object> param);
	Integer selectKeywordListCountByParam(Map<String, Object> param);
	
	public Integer updateKeyWordStatusById(@Param("id") Integer id, @Param("status") Integer status);
	
	public List<DrChannelKeyWords> getKeyWordUrlByCpaId(@Param("id") Integer id);
	
}
