/**
 * 
 */
package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.DrRecommendedSettingsDAO;
import com.jsjf.dao.activity.DrRecommendedSettingsDetailDAO;
import com.jsjf.model.activity.DrRecommendedSettings;
import com.jsjf.model.activity.DrRecommendedSettingsDetail;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrRecommendedSettingsService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;

/**
 * @author Gerald
 *
 */
@Service
public class DrRecommendedSettingsServiceImpl implements
		DrRecommendedSettingsService {
	@Autowired
	private DrRecommendedSettingsDAO drRecommendedSettingsDAO;
	
	@Autowired
	private DrRecommendedSettingsDetailDAO drSettingDetailsDAO;
	
	@Autowired
	private DrProductInfoService drProductInfoService;
	
	@Autowired
	private DrProductInvestService  drProductInvestService;
	

	@Override
	public PageInfo getDrRecommendedSettingsList(PageInfo info,
			DrRecommendedSettings recommendedSettings) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		List<DrRecommendedSettings> list = drRecommendedSettingsDAO.getReCommendedSettingsList(map);
		int total = drRecommendedSettingsDAO.getReCommendedSettingsCount(map);
		info.setTotal(total);
		info.setRows(list);
		return info;
	}


	@Override
	public PageInfo getSettingDetailsList(PageInfo info, Integer rid) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rid", rid);
		List<DrRecommendedSettingsDetail> list = drSettingDetailsDAO.getDetailByRid(map);
		int total = drSettingDetailsDAO.getDetailByRidTotal(map);
		info.setTotal(total);
		info.setRows(list);
		return info;
	}


	@Override
	public DrRecommendedSettings getRecommendedSettingsById(Integer id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		DrRecommendedSettings settings = drRecommendedSettingsDAO.getReCommendedSettingsById(map);
		map.clear();
		map.put("rid", id);
		List<DrRecommendedSettingsDetail> detailList = drSettingDetailsDAO.getDetailByRid(map);
		settings.setDetailList(detailList);
		return settings;
	}


	@Override
	public BigDecimal FriendRecommendedRebate(DrProductInvest invest) {
		BigDecimal rebateAmount = BigDecimal.ZERO;
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status", 0);//好友推荐返利 0：生效中
		map.put("investTime", invest.getInvestTime());//用户投资时间
		//获取产品
		DrProductInfo productInfo = drProductInfoService.getDrProductInfoByid(invest.getPid());
		//判断用户投资时间是否在生效活动的有效范围内
		DrRecommendedSettings settings = drRecommendedSettingsDAO.getReCommendedSettingsById(map);
		//规则存在 产品存在且产品在规则允许范围内
		if(settings != null && productInfo!=null && settings.getProducts().contains(productInfo.getType()+"")){
			BigDecimal investAmount = BigDecimal.ZERO;//投资用户总投资额
			if(settings.getNorm()==2){//按累计金额返佣
				investAmount =drProductInvestService.getDrProductInvestByTime(invest.getUid(), settings.getPeriod());
			}else{//按单笔投资额返佣
				investAmount = invest.getFactAmount();
			}
			map.clear();
			map.put("rid", settings.getId()==null?0:settings.getId());
			map.put("amount", investAmount);
			DrRecommendedSettingsDetail detail = drSettingDetailsDAO.getDetailByRidAndAmount(map);
			if(detail != null){
				if(settings.getModality()==2){
					//按奖励比例
					if(settings.getStandard()==1){//按本金比例返佣
						rebateAmount = invest.getFactAmount().multiply(detail.getReward()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
					}else if(settings.getStandard()==2){//按利息比例返佣
					}  
				}else{
					//按固定金额
					rebateAmount = detail.getReward();
				}
			}
		}
		return rebateAmount;
	}


	@Override
	public void updateStatus(Integer id) {
		drRecommendedSettingsDAO.updateStatus(id);
	}


	@Override
	public void insertDrRecommendedSetting(DrRecommendedSettings settings,SysUsersVo vo) {
		settings.setAddUser(vo.getUserKy().intValue());
		settings.setAddTime(new Date());
		settings.setStatus(0);
		drRecommendedSettingsDAO.insertReCommendedSettings(settings);
		List<DrRecommendedSettingsDetail> list = settings.getDetailList();
		for(DrRecommendedSettingsDetail detail:list){
			DrRecommendedSettingsDetail settingsDetail = new DrRecommendedSettingsDetail();
			settingsDetail.setRid(settings.getId());
			settingsDetail.setAmount(detail.getAmount());
			settingsDetail.setReward(detail.getReward());
			drSettingDetailsDAO.insertDetail(settingsDetail);
		}
		
	}

}
