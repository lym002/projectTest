package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.ActivityPrize;
import com.jsjf.model.activity.ActivityTemplate;

public interface ActivityTemplateDAO {
	
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
	 * 新增活动产品关联数据
	 */
	public void insertActivityProduct(Map<String, Object> param);
	
	/**
	 * 修改活动产品关联数据
	 */
	public void updateActivityProduct(Map<String, Object> param);
	
	/**
	 * 查询活动产品关联数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selActivityProduct(Map<String, Object> param);
	
	/**
	 * 删除活动产品关联数据
	 */
	public void deleteActivityProduct(Map<String, Object> param);
	
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
	 * 未中奖
	 */
	public void updateActivityStatusOne(Map<String, Object> param);
	
	/**
	 * 中奖啦
	 */
	public void updateActivityStatusTwo(Map<String, Object> param);

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
	public void addInvest(Map<String, Object> param)
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
	 * 查询要导出的数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPrizeMember(Map<String, Object> param);
	
	public void updateActivityProductAppTitle(Map<String, Object> param);
	
	public void updateActivityProductDetailImg(Map<String, Object> param);
	
	public void updateActivityDetailBanner(Map<String, Object> param);
	
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> getActivityProductByPid(Integer id);
}


