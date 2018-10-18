package com.jsjf.dao.article;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.article.SysBanner;

public interface SysBannerDAO {
	/**
	 * 拿到广告列表数据
	 * @param Map
	 * @return List<SysArticle>
	 */
    public List<SysBanner> getSysBannerList(Map<String,Object> map); 
    
	/**
	 * 拿到广告列表数据总数
	 * @param Map
	 * @return Integer
	 */
    public Integer getSysBannerCounts(Map<String,Object> map); 
    
	/**
	 * 添加广告
	 * @param SysBanner
	 * @return void
	 * @throws SQLException
	 */
    public void insertSysBanner(SysBanner sysBanner)throws SQLException;
	/**
	 * 修改广告
	 * @param SysBanner
	 * @return void
	 * @throws SQLException
	 */
    public void updateSysBannerById(SysBanner sysBanner)throws SQLException;

	/**
	 * 根据ID查询广告
	 * @param SysBanner
	 * @return SysBanner
	 * @throws SQLException
	 */
    public SysBanner selectSysBannerById(SysBanner sysBanner);
    
}