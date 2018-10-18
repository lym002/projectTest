package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.activity.JsActivityLuckyMoneyDAO;
import com.jsjf.dao.activity.JsActivityRewardDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberLotteryLogDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.activity.JsActivityLuckyMoney;
import com.jsjf.model.activity.JsActivityReward;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberLotteryLog;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class DrActivityServiceImpl implements DrActivityService {
	private final static String tearOpen = "tearOpen";
	@Autowired
	private DrActivityDAO drActivityDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
	@Autowired
	private JsActivityLuckyMoneyDAO jsActivityLuckyMoneyDAO;
	@Autowired
	private JsActivityRewardDAO jsActivityRewardDAO; 
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;

	@Override
	public DrActivity selectByPrimaryKey(Integer id) {
		return drActivityDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<DrActivity> selectByTime(Map<String, Object> param) {
			return drActivityDAO.selectByTime(param);
	}

	@Override
	public List<DrActivity> selectDrActivityList(Map<String, Object> map) {
		return drActivityDAO.selectDrActivityList(map);
	}
	
	

	@Override
	public BaseResult flop(Map<String, Object> param) {
		BaseResult br = new BaseResult();
		boolean lockFlag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			lockFlag = redisClientTemplate.tryLock("tearOpen." + param.get("uid"), 3, TimeUnit.SECONDS, false);
			if (lockFlag) {
				if (Utils.isObjectEmpty(param.get("uid")) || Utils.isObjectEmpty(param.get("version"))
						|| Utils.isObjectEmpty(param.get("channel"))) {
					br.setSuccess(false);
					br.setErrorCode("9998");
					br.setErrorMsg("未登录");
					return br;
				}
				DrMember m = drMemberDAO.selectByPrimaryKey(Integer.parseInt(param.get("uid").toString()));
				if (Utils.isObjectEmpty(m)) {
					br.setSuccess(false);
					br.setErrorCode("9997");
					br.setErrorMsg("该用户不存在");
					return br;
				}
				Date nowDate = new Date();
				map.put("activityName", "翻牌活动");
				List<DrActivity> drActivity = drActivityDAO.selectDrActivityList(map);
				if (drActivity.size() == 0) {
					br.setSuccess(false);
					br.setErrorCode("1004");
					br.setErrorMsg("未找到翻牌活动");
					return br;
				}
				Date flopStartTime = drActivity.get(0).getStartTime();
				Date flopEndTime = drActivity.get(0).getEndTime();
				if (!(nowDate.after(flopStartTime) && nowDate.before(flopEndTime))) {
					br.setSuccess(false);
					br.setErrorCode("1003");
					br.setErrorMsg("不在活动时间内");
					return br;
				}
				Integer flag = null;
				String isAPP2 = redisClientTemplate.getProperties("isAPP2");
				if (1 == (Integer) param.get("channel") || 2 == (Integer) param.get("channel")) {
					flag = Utils.compareVersion(param.get("version").toString(), isAPP2);
				} else {
					br.setSuccess(false);
					br.setErrorCode("1001");
					br.setErrorMsg("翻牌活动仅限APP端");
					return br;
				}
				if (flag < 0) {
					br.setSuccess(false);
					br.setErrorCode("1002");
					br.setErrorMsg("请先下载APP2.0");
					return br;
				}
				map.clear();
				map.put("uid", param.get("uid"));
				map.put("aid", drActivity.get(0).getId());
				map.put("addtime", nowDate);
				System.out.println(System.currentTimeMillis());
				System.out.println("sync:" + System.currentTimeMillis());
				boolean isFlop = false;
				// 查询今天是否已翻牌
				List<DrMemberLotteryLog> list = drMemberLotteryLogDAO.selectListByParam(map);
				isFlop = list.size() > 0 ? false : true;
				// 查询是否有分享获得的机会
				map.clear();
				map.put("uid", param.get("uid"));
				map.put("aid", drActivity.get(0).getId());
				Integer luckDrawSum = jsActivityLuckyMoneyDAO.getLuckDrawSum(map);
				luckDrawSum = luckDrawSum == null ? 0 : luckDrawSum;
				// 判断是否可翻牌
				if (!isFlop && luckDrawSum <= 0) {
					br.setSuccess(false);
					br.setErrorCode("1005");
					br.setErrorMsg("没有翻牌机会");
					return br;
				}
				// 抽奖
				map.clear();
				map.put("atid", drActivity.get(0).getId());
				List<JsActivityReward> rewardList = jsActivityRewardDAO.getJsActivityRewardByAid(map);
				// 是否中奖
				boolean isWinning = false;
				double lotterFirst = 0;
				double lotterEnd = 0;
				double lotter = 0;
				lotter = Math.random();
				for (int i = 0; i < rewardList.size(); i++) {
					if (i != 0) {
						lotterFirst += rewardList.get(i - 1).getProbability();
					}
					lotterEnd += rewardList.get(i).getProbability();
					if (lotter > lotterFirst && lotter <= lotterEnd) {
						JsActivityReward jsActivityReward = rewardList.get(i);
						// 根据奖品id判断是否中奖
						isWinning = jsActivityReward.getCouponId() == null ? false : true;
						// 如果中奖发奖品
						if (isWinning) {
							DrActivityParameter dap = drActivityParameterDAO
									.getActivityParameterById(jsActivityReward.getCouponId());
							DrMemberFavourable dmf = new DrMemberFavourable(jsActivityReward.getCouponId(),
									(Integer) param.get("uid"), dap.getType(), dap.getCode(), dap.getName(),
									dap.getAmount(), dap.getRaisedRates(), dap.getEnableAmount(), 0,
									Utils.getDayNumOfDate(1 - dap.getDeadline()), "APP2.0翻牌活动", 0, 3,
									dap.getProductDeadline(), dap.getMultiple(), null);
							drMemberFavourableDAO.insertIntoInfo(dmf);
							map.clear();
							map.put("type", dap.getType());
							if (dap.getType() == 1 || dap.getType() == 3) {
								map.put("num", dap.getAmount());
							}
							if (dap.getType() == 2) {
								map.put("num", dap.getRaisedRates());
							}
							if (dap.getType() == 4) {
								map.put("num", dap.getMultiple());
							}
						}
						// 中奖未中奖插抽奖记录
						Date date = new Date();
						DrMemberLotteryLog dmfLog = new DrMemberLotteryLog(drActivity.get(0).getId(),
								(Integer) param.get("uid"), date, jsActivityReward.getId());
						drMemberLotteryLogDAO.insert(dmfLog);
						// 如果有每日免费一次抽奖机会，则不更新分享获得的机会
						if (!isFlop) {
							JsActivityLuckyMoney jsActivityLuckyMoney = new JsActivityLuckyMoney();
							jsActivityLuckyMoney.setShaerUid((Integer) param.get("uid"));
							jsActivityLuckyMoney.setAid(drActivity.get(0).getId());
							jsActivityLuckyMoney.setLuckDrawSum(luckDrawSum - 1);
							jsActivityLuckyMoneyDAO.update(jsActivityLuckyMoney);
						}
						map.put("isWinning", isWinning);
						br.setSuccess(true);
						br.setMap(map);
						break;
					}
				}
			} else {
				br.setErrorCode("1006");
				br.setErrorMsg("抽奖获取锁失败");
				br.setSuccess(false);
				return br;
			}

		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		} finally {
			if (lockFlag) {
				redisClientTemplate.del("tearOpen." + param.get("uid"));
			}
		}
		return br;
	}

	@Override
	public BaseResult flopIndex() {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activityName", "翻牌活动");
		List<DrActivity> drActivity = drActivityDAO.selectDrActivityList(map);
		if(drActivity.size()==0){
			br.setSuccess(false);
			br.setErrorCode("1004");
			br.setErrorMsg("未找到翻牌活动");
			return br;
		}
		List<DrMemberLotteryLog> list = drMemberLotteryLogDAO.selectLotteryLogListByAid(drActivity.get(0).getId());
		map.clear();
		map.put("luckDrawList", list);
		map.put("aid", drActivity.get(0).getId());
		br.setMap(map);
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult flopShare(Map<String,Object> map) {
		BaseResult br = new BaseResult();
		if(Utils.isObjectEmpty(map.get("uid"))){
			br.setSuccess(false);
			br.setErrorCode("9998");
			br.setErrorMsg("uid不能为空");
			return br;
		}
		map.put("activityName", "翻牌活动");
		List<DrActivity> drActivity = drActivityDAO.selectDrActivityList(map);
		if(drActivity.size()==0){
			br.setSuccess(false);
			br.setErrorCode("1004");
			br.setErrorMsg("未找到翻牌活动");
			return br;
		}
		try {
			//翻牌活动只有首次分享获得抽奖机会一次
			map.put("aid", drActivity.get(0).getId());
			Integer luckDrawSum = jsActivityLuckyMoneyDAO.getLuckDrawSum(map);
			if(luckDrawSum == null){
				JsActivityLuckyMoney jsActivityLuckyMoney = new JsActivityLuckyMoney();
				jsActivityLuckyMoney.setShaerUid((Integer)map.get("uid"));
				jsActivityLuckyMoney.setAddTime(new Date());
				jsActivityLuckyMoney.setLuckDrawSum(1);
				jsActivityLuckyMoney.setAid((Integer)map.get("aid"));
				jsActivityLuckyMoneyDAO.insert(jsActivityLuckyMoney);
				br.setSuccess(true);
			}else{
				br.setSuccess(false);
				br.setErrorCode("1001");
				br.setErrorMsg("已经分享过");
			}
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}
	
	
	//测试抽奖概率
	private void liyutest(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("atid", 7);
		List<JsActivityReward> rewardList = jsActivityRewardDAO.getJsActivityRewardByAid(map);
		Map<String,Integer> prizeCount = new HashMap<String,Integer>();
		for (int j = 0; j < 100; j++) {
			double lotterFirst = 0;
			double lotterEnd = 0;
			double lotter = 0;
			lotter = Math.random();
			for(int i=0;i<rewardList.size();i++){
				if(i!=0){
					lotterFirst += rewardList.get(i-1).getProbability(); 
				}
				lotterEnd += rewardList.get(i).getProbability();
				if(lotter >lotterFirst && lotter <=lotterEnd){
					JsActivityReward jsActivityReward = rewardList.get(i);
					if(prizeCount.get(jsActivityReward.getName()) == null){
						prizeCount.put(jsActivityReward.getName(), 1);
					}else{
						String name = jsActivityReward.getName();
						Integer value = prizeCount.get(jsActivityReward.getName());
						Integer count = value+1;
						prizeCount.put(name, count);
					}
					break;
				}
			}
		}
		
		System.out.println(prizeCount.toString());
	}
	@Override
	public DrActivity selectObjectByName(String name) {
		
		return drActivityDAO.selectObjectByName(name);
	}


}
