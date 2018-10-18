package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysArticle;

public interface SysArticleDAO {
	/**
	 * 修改文章
	 * @param sysArticle
	 */
    public void updateSysArticleById(SysArticle sysArticle);
	/**
	 * 根据ID查询文章
	 * @param SysArticle
	 * @return SysArticle
	 */
    public SysArticle selectSysArticleById(int artiId);
    /**
	 * 获取首页新闻和公告
	 * @param map
	 * @return List<SysArticle>
	 */
    public List<Map<String,Object>> getIndexArticle(Map<String,Object> map);
    /**
     * 根据条件分页查询文章
     * @param map
     * @return
     */
    public List<SysArticle> getArticleByParam(Map<String,Object> map);
    /**
     * 根据条件取得文章总数
     * @param map
     * @return int
     */
    
    public int getSysArticleCount(Map<String,Object> map);
    /**
     * 根据点击条件取得文章总数
     * @param map
     * @return List<SysArticle>
     */
    
    
    /**
	 * 根据uid查询绑定银行的公告
	 * @param map
	 * @return
	 */
	public List<SysArticle> getArticleByUid(Map<String,Object> map);
}