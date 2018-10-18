package com.jsjf.service.article;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.article.SysBanner;


public interface SysBannerService {

	/**
	 * 拿到广告列表数据
	 * @param SysBanner
	 * @param  PageInfo
	 * @return BaseResult  
	 */
	public BaseResult getSysBannerList(SysBanner sysBanner,PageInfo pi);
	
	/**
	 * 添加广告
	 * @param SysBanner
	 * @param bannerAddPicFile
	 */
	public void addSysBanner(SysBanner sysBanner,MultipartFile bannerAddPicFile)throws Exception;
	
	/**
	 * 修改广告
	 * @param SysBanner
	 * @param bannerUpdPicFile
	 */
	public void updateSysBanner(SysBanner sysBanner,MultipartFile bannerUpdPicFile)throws Exception;
	
	/**
	 * 删除广告
	 * @param SysBanner
	 * @return BaseResult  
	 */
	public BaseResult deleteSysBanner(SysBanner sysBanner);
	/**
	 * 恢复广告
	 * @param SysBanner
	 * @return BaseResult  
	 */
	public BaseResult recoverSysBanner(SysBanner sysBanner);
	
	/**
	 * 查询广告
	 * @param SysBanner
	 * @return SysBanner  
	 */
	public SysBanner selectSysBannerById(SysBanner sysBanner);
	
}
