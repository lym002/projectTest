package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsjf.dao.member.BypMemberInviteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsActivityFriendDAO;
import com.jsjf.dao.activity.JsActivityFriendDetailDAO;
import com.jsjf.dao.activity.JsActivityMemberAccountDAO;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.model.activity.JsActivityFriend;
import com.jsjf.model.activity.JsActivityFriendDetail;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.activity.JsActivityFriendService;
import com.jsjf.service.system.impl.RedisClientTemplate;
@Service
@Transactional
public class JsActivityFriendServiceImpl implements JsActivityFriendService {
	@Autowired
	JsActivityFriendDAO jsActivityFriendDAO;
	@Autowired
	JsActivityFriendDetailDAO jsActivityFriendDetailDAO;
	@Autowired
	DrMemberRecommendedDAO drMemberRecommendedDAO;
	@Autowired
	JsActivityMemberAccountDAO jsActivityMemberAccountDAO;
	@Autowired
	BypMemberInviteDao bypMemberInviteDao;
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Override
	public JsActivityFriend selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult selectNewJsActivityFriend(DrMember m,Map<String,Object> map) {
		BaseResult br = new BaseResult();
		Map<String,Object> param = new HashMap<>();
		if(Utils.isObjectNotEmpty(m)){//获得推荐吗
			param.put("recommCode", m.getRecommCodes());	
		}
		JsActivityFriend jaf = null;
		if(Utils.isObjectNotEmpty(map.get("id"))){
			jaf = jsActivityFriendDAO.selectByPrimaryKey(Integer.parseInt(map.get("id").toString()));
			if(Utils.isObjectEmpty(jaf)){
				br.setErrorMsg("没有活动");
				br.setSuccess(false);
				return br;
			}
		}else{
			//按条件获得最新活动
			map.put("offset", 0);
			map.put("limit", 1);
//			map.put("status", 1);
			map.put("type",0);
			List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(map); 
			if(Utils.isEmptyList(list)){
				br.setErrorMsg("没有活动");
				br.setSuccess(false);
				return br;
			}
			jaf = list.get(0);
		}
		//活动奖励列表
		param.put("jsActivityFriend", jaf);
		List<JsActivityFriendDetail> detailList =  jsActivityFriendDetailDAO.selectJsActivityFriendDetail(jaf.getId());
		
		param.put("detailList", detailList);
		br.setMap(param);
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult selectJsActivityFriend(Map<String, Object> param) {
		BaseResult br = new BaseResult();
		PageInfo pageInfo = new PageInfo();
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			
			if(!param.containsKey("status") || Utils.isObjectEmpty( param.get("status"))){
				map.put("status", null);
			}else if((Integer)param.get("status") == 0 ){//全部
				map.put("status", null); //全部
			}else if((Integer)param.get("status") == 1 ){//进行中
				map.put("status", 1); 
			}else if((Integer)param.get("status") == 2){//已结束
				map.put("status", 2);
			}
			
			if(param.containsKey("pageOn")&&param.containsKey("pageSize")){
				pageInfo.setPageOn((Integer)param.get("pageOn"));
				pageInfo.setPageSize((Integer)param.get("pageSize"));
			}
			
			map.put("offset", pageInfo.getPageInfo().getOffset());
			map.put("limit", pageInfo.getPageInfo().getLimit());
			map.put("type", 0);
			List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(map);
			
			int total = jsActivityFriendDAO.selectJsActivityFriendCount(map);
			pageInfo.setRows(list);
			pageInfo.setTotal(total);
			map.clear();
			map.put("pageInfo", pageInfo);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult getActivityFriendStatistics(DrMember m,Map<String, Object> map) {
		BaseResult br = new BaseResult();
		Map<String,Object> param = new HashMap<>();
		PageInfo pageInfo = new PageInfo();
		try {
			if(Utils.isObjectEmpty(m)){
				br.setErrorMsg("未登录");
				br.setSuccess(false);
				return br;
			}
			JsActivityFriend activity = null;
			Integer friendTotal = null;
			if(!map.containsKey("afid") || Utils.isObjectEmpty(map.get("afid"))){
				//按条件获得最新活动
				map.put("offset", 0);
				map.put("limit", 1);
//				map.put("status", 1);
				map.put("type", 1);
				List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(map); 
				if(Utils.isEmptyList(list)){
					br.setErrorMsg("没有活动");
					br.setSuccess(false);
					return br;
				}
				map.put("afid", list.get(0).getId());
				friendTotal = drMemberRecommendedDAO.selectRecommendedCount(m.getUid(),list.get(0).getId());
				activity = list.get(0);
			}else{
				activity = jsActivityFriendDAO.selectByPrimaryKey(Integer.parseInt(map.get("afid").toString()));
			}
//			//好友总数
//			param.put("referrerId", m.getUid());
//			int friendTotal = drMemberRecommendedDAO.getDrMemberRecommendedCount(param);
		
			//完成首投好友数
			param.put("afid", map.get("afid"));
			param.put("uid", m.getUid());
			int firstTotal = jsActivityMemberAccountDAO.selectFirstInvestCount(param);
		
			//活动奖励
			BigDecimal rewards = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);	
			
			//活动未领奖励
			param.put("status", 0);
			BigDecimal unclaimed = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);
			
			
		
			if(Utils.isObjectNotEmpty(map.get("pageOn"))&&Utils.isObjectNotEmpty(map.get("pageSize"))){
				pageInfo.setPageOn(Integer.parseInt(map.get("pageOn").toString()));
				pageInfo.setPageSize(Integer.parseInt(map.get("pageSize").toString()));
			}
			param.put("offset", pageInfo.getPageInfo().getOffset());
			param.put("limit", pageInfo.getPageInfo().getLimit());
			
			//好友详情
			List<Map<String,Object>> friendList = jsActivityMemberAccountDAO.selectFriendInvestList(param);
			//hui 如果List中有amount为0 并且isInvest为是,将amount数据删去
			List<Map<String, Object>> newFriendList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map2 : friendList) {
				if(map2.get("isInvest").equals("是")){
					if(map2.containsKey("amount")){
					}else{
						map2.put("amount", "0");
					}
				}
				newFriendList.add(map2);
			}
				
			int total = jsActivityMemberAccountDAO.selectFriendInvestListCount(param);
			
			pageInfo.setTotal(total);
			pageInfo.setRows(newFriendList);
			
			map.clear();
			map.put("friendTotal", friendTotal==null?0:friendTotal);
			map.put("firstTotal", firstTotal);
			map.put("unclaimed", unclaimed);
			map.put("rewards", rewards);
			map.put("page", pageInfo);
			map.put("activity", activity);
			br.setSuccess(true);
			br.setMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
			return br;
		}
		
		return br;
	}

	@Override
	public BaseResult myInvitation(DrMember m,Map<String, Object> map) {
		BaseResult br = new BaseResult();
		Map<String,Object> param = new HashMap<>();
		PageInfo pageInfo = new PageInfo();
		try {
			if(Utils.isObjectEmpty(m)){
				br.setErrorMsg("未登录");
				br.setSuccess(false);
				return br;
			}
			//活动推送:获取最近活动
			param.put("offset", 0);
			param.put("limit", 1);
//			map.put("status", 1);
			List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(param); 
			
			//完成首投好友数
			param.clear();
			param.put("referrerId", m.getUid());
			int investors = drMemberRecommendedDAO.selectRecommendedInvestCount(param);
			//好友总数
			param.clear();
			param.put("referrerId", m.getUid());
			int total = drMemberRecommendedDAO.getDrMemberRecommendedCount(param);
				
			if(Utils.isObjectNotEmpty(map.get("pageOn"))
					&&Utils.isObjectNotEmpty(map.get("pageSize"))){
				pageInfo.setPageOn(Integer.valueOf(map.get("pageOn").toString()));
				pageInfo.setPageSize(Integer.valueOf(map.get("pageSize").toString()));
			}
			param.put("offset", pageInfo.getPageInfo().getOffset());
			param.put("limit", pageInfo.getPageInfo().getLimit());
			
			//累计好友首投资详情
			List<Map<String,Object>> friendList = drMemberRecommendedDAO.selectDrMemberRecommendedInvest(param);
			
			//hui 如果List中有amount为0 并且isInvest为是,将amount数据删去
			List<Map<String, Object>> newFriendList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map2 : friendList) {
				if(map2.get("isInvest").equals("是")){
					if(map2.containsKey("amount")){
					}else{
						map2.put("amount", "0");
					}
				}
				newFriendList.add(map2);
				
			}
			int friendListTotal = drMemberRecommendedDAO.getDrMemberRecommendedCount(param);
			
			pageInfo.setTotal(friendListTotal);//列表总数
			pageInfo.setRows(newFriendList);//列表
			
			map.clear();
			map.put("recommCodes",m.getRecommCodes());//推荐码
			map.put("total", total);//好友总数
			map.put("investors", investors);//已投资好友数	
			map.put("page", pageInfo);
			if(!Utils.isEmptyList(list)){			
				map.put("isPut", list.get(0).getIsPut());//是否推送
				map.put("banner", list.get(0).getPcPutImg());//banner
				map.put("jumpUrl", list.get(0).getPcPutUrl());//banner链接地址
				map.put("startTime", list.get(0).getStartDate());//时间
				map.put("endTime", list.get(0).getEndDate());//时间
				map.put("content", list.get(0).getPutContent());//推送类容
			}
			br.setSuccess(true);
			br.setMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
			return br;
		}
		return  br;
	}
	
	@Override
	public BaseResult firstInvestList(int uid) {
		BaseResult br = new BaseResult();
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			param.put("offset", 0);
			param.put("limit", 1);
			param.put("type", 1);
			List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(param); 
			if(Utils.isEmptyList(list)){
				br.setErrorMsg("没有活动");
				br.setSuccess(false);
				return br;
			}
//			Date startDate = list.get(0).getStartDate();
//			Date endDate = list.get(0).getEndDate();
//			Date nowTime = new Date();
//			if(nowTime.after(startDate) && nowTime.before(endDate)){
				Integer afid = list.get(0).getId();
				param.clear();
				param.put("afid",afid);
				param.put("uid",uid);
				//活动奖励
				BigDecimal rewards = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);	
				//活动未领奖励
				param.put("status", 0);
				BigDecimal unclaimed = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);
				String nowRanking = null;
				BigDecimal amount = null;
				Map<String,Object> threePresentMap = null;
				List<Map<String,Object>> top = null;
				//当前排行 threePresentMap
				byte[] threePresent = redisClientTemplate.get("threePresentMap".getBytes());
				threePresentMap = (Map<String,Object>) SerializeUtil.unserialize(threePresent);
				if(threePresentMap !=null){
					top = (List<Map<String, Object>>)threePresentMap.get("top");
					//排行第一累计年华 
					if(top != null){
						amount = (BigDecimal) top.get(0).get("amount");
					}
					//排行信息
					Map<String,Object> nowRankingInfo = (Map<String, Object>) threePresentMap.get(uid+"");
					//当前排名
					if(nowRankingInfo != null && nowRankingInfo.size()>0){
						nowRanking = nowRankingInfo.get("rownum").toString();
					}
				}
				//首投邀请列表
				param.clear();
				param.put("referrerId", uid);
				param.put("afid", afid);
				param.put("investOrder", 1);
				List<Map<String, Object>> firstInvestList = drMemberRecommendedDAO.selectFirstInvestList(param);
				//首投好友人数
				Integer firstInvestCount = drMemberRecommendedDAO.selectReInvestCount(param);
				//复投邀请列表
				param.put("investOrder", 2);
				List<Map<String, Object>> repeatInvestList = drMemberRecommendedDAO.selectFirstInvestList(param);
				//复投好友人数
				Integer reInvestCount = drMemberRecommendedDAO.selectReInvestCount(param);
				//邀请人数总计
				Integer recommendedCount = drMemberRecommendedDAO.selectRecommendedCount(uid,afid);
				map.put("firstInvestCount", firstInvestCount == null?0:firstInvestCount);
				map.put("reInvestCount", reInvestCount == null?0:reInvestCount);
				map.put("firstInvestList", firstInvestList);
				map.put("repeatInvestList", repeatInvestList);
				map.put("recommendedCount", recommendedCount);
				map.put("threePresentRewards", rewards);
				map.put("threePresentUnclaimed", unclaimed);
				map.put("nowRanking", nowRanking);
				map.put("firstAmount", amount);
				map.put("threePresentAfid", afid);
				map.put("activity", list.get(0));
				br.setSuccess(true);
				br.setMap(map);
//			}else{
//				br.setSuccess(false);
//				br.setErrorMsg("不在活动时间内");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
			return br;
		}
		return  br;
	}
	
	@Override
	public BaseResult threePresent(Integer uid) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("offset", 0);
			param.put("limit", 1);
			param.put("type", 1);
			List<JsActivityFriend> list = jsActivityFriendDAO.selectJsActivityFriend(param); 
			if(Utils.isEmptyList(list)){
				br.setErrorMsg("没有活动");
				br.setSuccess(false);
				return br;
			}
			Date startDate = list.get(0).getStartDate();
			Date endDate = list.get(0).getEndDate();
			Date nowTime = new Date();
			if(nowTime.after(startDate) && nowTime.before(endDate)){
				Integer afid = list.get(0).getId();
				param.clear();
				param.put("afid",afid);
				param.put("uid",uid);
				//活动奖励
				BigDecimal rewards = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);
				//活动未领奖励
				param.put("status", 0);
				BigDecimal unclaimed = jsActivityMemberAccountDAO.selectActivityRewardsSum(param);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("unclaimed", unclaimed);
				map.put("rewards", rewards);
				map.put("afid", afid);
				br.setSuccess(true);
				br.setMap(map);
			}else{
				br.setSuccess(false);
			}
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public List<JsActivityFriend> selectJsActivityFriendByParam(Map<String, Object> map) {
		return jsActivityFriendDAO.selectJsActivityFriend(map); 
	}

	@Override
	public BaseResult getActivityFriendBonus(Integer uid) {
		BaseResult br = new BaseResult();
		BigDecimal award = new BigDecimal(0);
		int total = 0;
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> resultMap = new ArrayList<>();
		try {
			if (uid != null && uid != 0) {
				param.put("referrerId", uid);
				//获取当前用户邀请的人数
				total = drMemberRecommendedDAO.getDrMemberRecommendedCount(param);
				//获取推荐好友首投奖励总和
				BigDecimal bigDecimal = drMemberRecommendedDAO.getRewardByReferrerFirstInvest(uid);
				if (bigDecimal == null) bigDecimal = new BigDecimal(0);
				award = award.add(bigDecimal);
				BigDecimal bigDecimal1 = bypMemberInviteDao.getRewardByReferrerIdCount(uid);
				if (bigDecimal1 == null) bigDecimal1 = new BigDecimal(0);
				award = award.add(bigDecimal1);
			}
			//获取前十邀请
			resultMap = drMemberRecommendedDAO.getDrMemberTopTen();
			param.clear();
			param.put("total", total);//当前用户邀请的人数
			param.put("award", award);//当前用户获得的奖励
			param.put("inviteTop", resultMap);
			br.setSuccess(true);
			br.setMap(param);
		}catch (Exception e){
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.getMessage();
		}
		return br;
	}

	@Override
	public BaseResult getMyFriendBonus(Integer uid) {
		BaseResult br = new BaseResult();
		BigDecimal award = new BigDecimal(0);
		BigDecimal investSum = new BigDecimal(0);
		int total = 0;
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> resultMap = new ArrayList<>();
		try {
			param.put("referrerId", uid);
			//获取当前用户邀请的人数
			total = drMemberRecommendedDAO.getDrMemberRecommendedCount(param);
			investSum = drMemberRecommendedDAO.getDrMemberInvestAll(param);
			//获取推荐好友首投奖励总和
			BigDecimal bigDecimal = drMemberRecommendedDAO.getRewardByReferrerFirstInvest(uid);
			if (bigDecimal == null) bigDecimal = new BigDecimal(0);
			award = award.add(bigDecimal);
			BigDecimal bigDecimal1 = bypMemberInviteDao.getRewardByReferrerIdCount(uid);
			if (bigDecimal1 == null) bigDecimal1 = new BigDecimal(0);
			award = award.add(bigDecimal1);
			//获取我的邀请
			resultMap = drMemberRecommendedDAO.getMyRecommended(uid);
			param.clear();
			param.put("total", total);//当前用户邀请的人数
			param.put("award", award);//当前用户获得的奖励
			param.put("myFriends", resultMap);
			param.put("investSum", investSum);
			br.setSuccess(true);
			br.setMap(param);
		}catch (Exception e){
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.getMessage();
		}
		return br;
	}
}
