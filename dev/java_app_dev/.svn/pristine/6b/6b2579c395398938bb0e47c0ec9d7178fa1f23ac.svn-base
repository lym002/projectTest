package com.jsjf.service.product;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;

public interface DrProductInfoService {
	
	/**
	 * 我的幸运码
	 * @param map
	 * @return
	 */
	public BaseResult getMyLuckCodes(Map<String,Object> map);
	/**
	 * js活动首页
	 * @param map
	 * @return
	 */
	public BaseResult  getNewActivityProduct();

	/**
	 * 条件查找产品列表
	 * @param type 产品类型
	 * @param pi 分页实体
	 * @return
	 */
	public BaseResult selectProductInfoByParams(Integer type, PageInfo pi,Integer uid,Integer status);
	
	/**
	 * 条件查找产品列表
	 * @param type 产品类型
	 * @param pi 分页实体
	 * @return
	 */
	public BaseResult selectProductInfoByParams(Integer type, PageInfo pi,Integer uid,Integer status,Integer isActivity);

	/**
	 * 查找产品详情
	 * @param param
	 * @return
	 */
	public DrProductInfo selectProductDetailByPid(Integer pid);
	/**
	 * 查找产品详情
	 * @param param
	 * @return
	 */
	public DrProductInfo selectProductDetailById(Integer pid);
	
	/**
	 * 通过产品ID查看产品信息
	 * @param id
	 * @return
	 */
	public DrProductInfo selectProductByPrimaryKey(Integer id);
	
	/**
	 * 保存投资
	 * @param loginMember
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BaseResult saveInvest(DrMember loginMember, DrProductInfo info, JSONObject json) throws Exception;
	/**
	 * 查询推荐的产品，三个类型最早发布正在募集中的产品
	 * @return
	 */
	public List<DrProductInfo>  selectProductInfoRecommend();
	
	public List<DrProductInfo> selectHotProductInfo(); 
	
	public DrProductInfo selectNewHandInfo(Map<String,Object> map);
	
	public DrProductInfo selectJSProductActive();
	
	/**
	 * 砸金蛋产品过滤
	 * @param list
	 * @param listMap
	 * @param m
	 */
	public void eggActivityRuleFilter(List<DrProductInfo> list,List<Map<String,Object>> listMap, Integer uid);
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	public List<DrProductInfo> selectProductbyMap(Map<String,Object> map);
	
	/**
	 * 查找体验标详情
	 * @param param
	 * @return
	 */
	public DrProductInfo selectExperienceDetail();
	
	/**
	 * 体验标投资
	 * @param param
	 * @return
	 */
	public BaseResult experienceInvest(DrProductInfo info,Integer uid, Map<String,Object> map,Integer channel);
	
	/**
	 * 获取双蛋活动页产品
	 * @param param
	 * @return
	 */
	public List<DrProductInfo> doubleEggList(Map<String,Object> param);
	/**
	 * 条件查询产品信息
	 * @param param
	 * @return
	 */
	public List<DrProductInfo> selectProductInfo(Map<String,Object> param);
	/**
	 * 查询首页优选产品信息
	 * @param param
	 * @return
	 */
	public DrProductInfo selectPreferredInvest();
	/**
	 * 查询低于30天的标地信息
	 * @param param
	 * @return
	 */
	public DrProductInfo selectLessThirtyInvest();
	/**
	 * 根据 Map查询投资抽奖码和是否中奖
	 * @param map
	 * @return
	 */
	public BaseResult selectjsActivityProductLuckCodesAndStatus(Map<String,Object> map);
	/**
	 * 投资成功后其他后续业务处理, 发红包,自动发标,满标提醒 等等
	 * @param loginMember
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void investSuccessAfter(DrProductInfo info, Map<String,Object> param,DrMember loginMember);
	/**
	 * 等本等息产品
	 */
	public Map<String, Object> selectPeriodProductList(Map<String,Object> param, PageInfo pi) throws Exception;
	/**
	 * 查询端午节产品
	 * @return
	 */
	public BaseResult selectProductInfoByDragonBoat();
	/**
	 * 查询端午节产品
	 * @return
	 */
	public Map<String,Object> getProductRateInterval(Map<String,Object> map);
	
	/**
	 * 查询30,60,180 天的产品各一个
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectProductInfoByDeadLine () throws Exception;
	
	/**
	 * 按期限分类取产品 
	 * @return
	 */
	public List<DrProductInfo> selectPorductClassifyByDeadline (Map<String,Object> map);

	/**
	 * 查询小于30天标地的单独产品信息
	 * @return
	 */
    DrProductInfo selectMaxRateInvestByLess();
	/**
	 * 查询30天标地信息
	 * @return
	 */
	DrProductInfo selectThirtyInvest();
	/**
	 * 查询60天标地信息
	 * @return
	 */
	DrProductInfo selectSixtyInvest();
	/**
	 * 查询30天标地的单独产品信息
	 * @return
	 * @param i
	 */
	DrProductInfo selectMaxRateInvest(int i);

    BaseResult getZeroBuy();

	Integer selectToHelpFarmersProduct();
}
