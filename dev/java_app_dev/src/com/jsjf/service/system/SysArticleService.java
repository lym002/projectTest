package com.jsjf.service.system;


import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysArticle;


public interface SysArticleService {
	
	/**
	 * 根据pro_ID查询文章
	 * @param SysArticle
	 * @return SysArticle  
	 */
	public SysArticle selectSysArticleById(Integer id);
	
	/**
	 * 条件分页查询文章(包括文章符合条件总数)
	 * @param map
	 * @param info
	 * @return
	 */
	public BaseResult getArticleByParam(Map<String,Object> map,PageInfo info);
	/**
	 * 查询文章列表
	 * @param map
	 * @return
	 */
	public List<SysArticle> getArticleByParam(Map<String,Object> map);
	
	public List<Map<String,Object>> getIndexArticle(Map<String,Object> map);
	
	/**
	 * 根据uid查询绑定银行的公告
	 * @param map
	 * @return
	 */
	public List<SysArticle> getArticleByUid(Map<String,Object> map);
}
