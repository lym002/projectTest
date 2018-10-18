package com.jsjf.service.article;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.article.SysArticle;
import com.jsjf.model.article.SysProgram;
import com.jsjf.model.system.SysUsersVo;


public interface SysArticleService {

	/**
	 * 拿到文章列表数据
	 * @param SysArticle
	 * @param  PageInfo
	 * @return BaseResult  
	 */
	public BaseResult getArticleList(SysArticle sysArticle,PageInfo pi);
	
	/**
	 * 添加文章
	 * @param SysArticle
	 * @param  usersVo
	 * @param  articleAddPicFile
	 * @return void  
	 */
	public void addArticle(SysArticle sysArticle,SysUsersVo usersVo,MultipartFile articleAddPicFile)throws Exception;
	
	/**
	 * 根据ID查询文章
	 * @param SysArticle
	 * @return SysArticle  
	 */
	public SysArticle selectSysArticleById(SysArticle sysArticle);
	
	/**
	 * 根据ID修改文章
	 * @param SysArticle
	 * @param usersVo
	 * @param articleUpdPicFile
	 * @return void  
	 */
	public void updateArticle(SysArticle sysArticle,SysUsersVo usersVo,MultipartFile articleUpdPicFile)throws Exception;
	
	/**
	 * 删除文章
	 * @param SysArticle
	 * @return BaseResult  
	 */
	public BaseResult deleteArticle(SysArticle sysArticle);
	
	/**
	 * 是否推荐
	 * @param SysArticle
	 * @return BaseResult  
	 */
	public BaseResult isrecommendArticle(SysArticle sysArticle);
	
	/**
	 * 恢复文章及审核通过
	 * @param SysArticle
	 * @return BaseResult  
	 */
	public BaseResult recoverAtricle(SysArticle sysArticle);
	
	/**
	 * 拒绝文章
	 * @param SysArticle
	 * @return BaseResult  
	 */
	public BaseResult refuseAtricle(SysArticle sysArticle);
	
	/**
	 * 拒绝文章
	 * @param SysArticle
	 * @return List<SysProgram>  
	 */
	public List<SysProgram> getAllSysProgram();

}
