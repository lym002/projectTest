package com.jsjf.dao.article;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.article.SysArticle;

public interface SysArticleDAO {
	
	/**
	 * 拿到文章列表数据
	 * @param Map
	 * @return List<SysArticle>
	 */
    public List<SysArticle> getSysArticleList(Map<String,Object> map); 
    
	/**
	 * 拿到文章列表数据总数
	 * @param Map
	 * @return Integer
	 */
    public Integer getSysArticleCounts(Map<String,Object> map); 
    
	/**
	 * 添加文章列表数据
	 * @param SysArticle
	 * @return void
	 */
    public void insertSysArticle(SysArticle sysArticle);
    
	/**
	 * 根据ID查询文章
	 * @param SysArticle
	 * @return SysArticle
	 */
    public SysArticle selectSysArticleById(SysArticle sysArticle);
    
	/**
	 * 根据ID修改文章
	 * @param SysArticle
	 * @return void
	 * @throws SQLException
	 */
    public void updateSysArticleById(SysArticle sysArticle)throws SQLException;
    
}