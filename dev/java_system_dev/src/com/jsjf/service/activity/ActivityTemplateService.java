package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.ActivityPrize;
import com.jsjf.model.activity.ActivityTemplate;
import com.jsjf.model.product.DrProductInfo;

public interface ActivityTemplateService {
	
	/**
	 * 查询活动模板
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityTemplate(Map<String, Object> param);
	
	/**
	 * 查询活动模板总条数
	 * @param param
	 * @return
	 */
	public int selActivityTemplateCount(Map<String, Object> param);
	
	/**
	 * 新增活动模板
	 */
	public void insert(ActivityTemplate activityTemplate);
	
	/**
	 * 新增奖品明细表
	 */
	public void insertPrize(ActivityPrize activityPrize);
	
	/**
	 * 根据atid查询奖品明细表
	 */
	public List<Map<String, Object>> selActivityPrize(Map<String, Object> param);
	
	/**
	 * 根据atid删除奖品明细表
	 */
	public void delActivityPrize(Map<String, Object> param);
	
	/**
	 * 根据id修改活动模板表
	 */
	public void updateActivityTemplate(ActivityTemplate activityTemplate);
	
	/**
	 * 查询所有活动模板
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityTemplateAll();
	
	/**
	 * 查询活动产品关联数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityProductAll(Map<String, Object> param);
	
	/**
	 * 查询活动产品关联数据总条数
	 * @param param
	 * @return
	 */
	public int selActivityProductAllCount(Map<String, Object> param);
	
	/**
	 * 发布中奖信息
	 * @param acceptPicFile
	 * @param prizeCode
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BaseResult addActivityProduct(@RequestParam MultipartFile acceptPicFile,String prizeCode,String id,String prizeUrl)throws Exception;
	
	/**
	 * 查询活动详细数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityProductById(Map<String, Object> param);
	
	/**
	 * 根据活动id查询对应奖品明细
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityPrizeById(Map<String, Object> param);
	
	/**
	 * 根据活动产品关联表id查询出当前活动的投资人信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selProductInvestById(Map<String, Object> param);
	
	/**
	 * 根据活动产品关联表id查询出当前活动的投资人信息总条数
	 * @param param
	 * @return
	 */
	public int selProductInvestByIdCount(Map<String, Object> param);
	
	/**
	 * 上传中奖信息
	 * @param acceptPicFile
	 * @param prizeCode
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BaseResult addInvest(MultipartFile acceptPicFile,String prizeContent,String id,String prizeMobile,String prizeVideoLink,String prizeVideoUrl,MultipartFile headFile)
			throws Exception;
	
	/**
	 * 根据活动产品查询投资总人数和总额
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selSumCount(Map<String, Object> param);
	
	/**
	 * 查询幸运码是否存在
	 * @param param
	 * @return
	 */
	public int selInvestByCode(Map<String, Object> param);
	
	/**
	 * 查询要导出的数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> exportData(Map<String, Object> param);
	
	/**
	 * 更新APP短标题
	 * @param appTitle
	 * @param productId
	 */
	public void updateActivityProductAppTitle(String appTitle,String productId);
	
	/**
	 * 更新活动标-活动详情页面
	 * @param detailFile
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public BaseResult updateActivityProductDetailImg(MultipartFile detailFile,String productId) throws Exception;
	
	/**
	 * 更新PC及H5 banner图片
	 * @param pcBannerFile
	 * @param h5BannerFile
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public BaseResult updateActivityDetailBanner(MultipartFile pcBannerFile, MultipartFile h5BannerFile, String productId) throws Exception;
}


