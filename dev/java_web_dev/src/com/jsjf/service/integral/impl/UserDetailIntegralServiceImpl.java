package com.jsjf.service.integral.impl;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SystemConstant;
import com.jsjf.dao.integral.BypCommodityExchangeDao;
import com.jsjf.dao.integral.BypCommodityRepertoryDao;
import com.jsjf.dao.integral.UserDetailIntegralDao;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.dao.vip.VipInfoMapper;
import com.jsjf.model.integral.BypCommodityExchange;
import com.jsjf.model.integral.BypCommodityRepertory;
import com.jsjf.model.integral.TaskIntegralRules;
import com.jsjf.model.integral.UserDetailIntegralBean;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.integral.UserDetailIntegralService;
import com.jsjf.service.member.DrMemberService;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserDetailIntegralServiceImpl implements UserDetailIntegralService {

	private static transient Logger log = Logger.getLogger(UserDetailIntegralServiceImpl.class);
	
	@Autowired
	private UserDetailIntegralDao userDetailIntegralDao;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private TaskIntegralRulesService taskIntegralRulesService;
	@Autowired
	private MemberVipInfoMapper memberVipInfoMapper;
	@Autowired
	private VipInfoMapper vipInfoMapper;
    @Autowired
    private BypCommodityRepertoryDao bypCommodityRepertoryDao;
    @Autowired
    private BypCommodityExchangeDao bypCommodityExchangeDao;

	@Override
	public PageInfo queryDetailintegralList(PageInfo info,
			UserDetailIntegralBean userDetailIntegralBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		
		map.put("orders", "id desc");
		
		List<UserDetailIntegralBean> rows = userDetailIntegralDao.queryDetailintegralList(map);
		int total = userDetailIntegralDao.queryDetailintegralListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;

	}

	/**
	 * 查看获取积分
	 * @param uid
	 * @return
	 */
	@Override
	public BaseResult queryEarnPoint(int uid) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<>();
		map.put("order"," add_time desc");
		map.put("uid", uid);
		try {
			List<UserDetailIntegralBean> resultList = userDetailIntegralDao.queryEarnPoint(map);
			String name = "";
			map.clear();
			List<JSONObject> list = new ArrayList<>();
			for (UserDetailIntegralBean bean : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("addTime", DateUtil.formatDate(bean.getAddTime(),"yyyy-MM-dd"));
				jsonObject.put("integral", bean.getUserDetailIntegral());
				if (bean.getIntegralSourceId() == 1) {
					name = SystemConstant.INVEST_TYPE;//投资
				} else if (bean.getIntegralSourceId() == 2) {
					name = SystemConstant.SIGN_IN;//签到
				} else if (bean.getIntegralSourceId() == 3) {
					name = SystemConstant.TASK_TYPE;//任务
				}
				TaskIntegralRules rules = null;
				if (bean.getTaskIntegralId() != null) {
					rules = taskIntegralRulesService.selectByPrimaryKey(bean.getTaskIntegralId());
				}
				jsonObject.put("integralName", rules == null ? name : rules.getTaskType());
				list.add(jsonObject);
			}
			map.put("earnPoints", list);
			br.setMap(map);
			br.setSuccess(true);
		}catch (Exception e){
			log.error("获取积分列表失败！",e);
		}
		return br;
	}

	/**
	 * 查询使用积分列表
	 * @param uid
	 * @return
	 */
	@Override
	public BaseResult queryConsumptionPoint(Integer uid) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<>();
		map.put("order"," bcd.addTime desc");
		map.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
		map.put("activityId", "jf");
		map.put("uid", uid);
		try {
			List<UserDetailIntegralBean> resultList = userDetailIntegralDao.queryConsumptionPoint(map);
			map.clear();
			List<JSONObject> list = new ArrayList<>();
			for (UserDetailIntegralBean bean : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("addTime", DateUtil.formatDate(bean.getAddTime(),"yyyy-MM-dd"));
				jsonObject.put("integral", bean.getNeedPoints());
				jsonObject.put("prizeName", bean.getPrizeName());
				list.add(jsonObject);
			}
			map.put("consumptionPoint", list);
			br.setMap(map);
			br.setSuccess(true);
		}catch (Exception e){
			log.error("用户积分使用查询错误！",e);
		}
		return br;
	}

	@Override
	public UserDetailIntegralBean queryExpirationDate(String expirationDate) {
		return userDetailIntegralDao.queryExpirationDate(expirationDate);
	}

	/**
	 * 查询top10商品兑换
	 * @return
	 */
	@Override
	public BaseResult queryTopTenCommodity() {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<>();
		List<JSONObject> objects = new ArrayList<>();
		map.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
		map.put("order", " bcd.addTime DESC");
		map.put("activityId", "jf");
		map.put("limit",10);
		String sex = "XX";
		try {
			List<UserDetailIntegralBean> list = userDetailIntegralDao.queryConsumptionPoint(map);
			map.clear();
			if (list == null) list = new ArrayList<>();
			for (UserDetailIntegralBean bean : list) {
				JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
				if (bean.getSex() == null || bean.getSex() == 1) {
					sex = "先生";
				} else if (bean.getSex() == 2) {
					sex = "女士";
				}
				jsonObject.put("name", bean.getRealname() == null ? "":bean.getRealname().substring(0, 1) + sex);
				jsonObject.put("commodity", bean.getPrizeName() == null ?"":bean.getPrizeName());
				objects.add(jsonObject);
			}
			map.put("topTen", objects);
			br.setMap(map);
			br.setSuccess(true);
		}catch (Exception e){
			log.error("兑换top10查询错误!" , e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			br.setErrorMsg("系统查询错误！");
		}
		return br;
	}

	//用户id，积分，日志，获取积分路径类型

	/**
	 *
	 * @param uid 用户id
	 * @param points 获得的积分数
	 * @param source 积分来源（1：投资，3：任务）
	 * @param taskId 任务id
	 */
	@Override
	public void eranPoints(Integer uid, BigDecimal points, Integer source, Integer taskId){
		BigDecimal integrals = new BigDecimal(1);
		try {
			//根据用户id，获取优币翻倍系数
//			MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(uid);
//			if (memberVipInfo == null){
//				memberVipInfo = new MemberVipInfo(null, uid, new BigDecimal(0),0);
//				memberVipInfoMapper.insertSelective(memberVipInfo);
//				memberVipInfo = memberVipInfoMapper.selectByUid(uid);
//			}
//			//获取优币翻倍系数
//			VipInfo vipinfo = vipInfoMapper.selectByVipLevel(memberVipInfo.getVipLevel());
//			if (vipinfo != null){
//				integrals = new BigDecimal(vipinfo.getIntegralMultiply()).setScale(2,BigDecimal.ROUND_FLOOR);
//			}
			//获取用户可用的总积分
			DrMember dm = drMemberService.selectByPrimaryKey(uid);
				//添加用户积分
				dm.setUserIntegralUse(dm.getUserIntegralUse().add(points.multiply(integrals)));
				drMemberService.update(dm);
				//添加积分日志
				UserDetailIntegralBean bmi = new UserDetailIntegralBean(
						null,uid,source,points, getExpirationTime(),new Date(),null,taskId);
				userDetailIntegralDao.insert(bmi);

		}catch (Exception e){
			log.error("用户积分数据添加失败！" + e);
		}
	}

	@Override
	public Integer queryTodayInvestTask(Integer uid) {
		return userDetailIntegralDao.queryTodayInvestTask(uid);
	}

    @Override
    public BaseResult integralJDCard(HashMap<String, Object> param) throws Exception {
        BaseResult br = new BaseResult();
        Integer uid = (Integer) param.get("uid");
        Integer pid = (Integer) param.get("pid");
        DrMember member = drMemberService.selectByPrimaryKey(uid);
        BypCommodityRepertory bypCommodityRepertory= bypCommodityRepertoryDao.selectByPrimaryKey(pid);
        UserDetailIntegralBean bmi = null;
        List<BypCommodityExchange> bypCommodityExchanges = bypCommodityExchangeDao.selectByExchangeId(param);
        if (member.getUserIntegralUse().compareTo(bypCommodityRepertory.getCommodityWorth())>=0&&bypCommodityRepertory.getRepertoryInto().compareTo(bypCommodityRepertory.getRepertoryOut())>0&&bypCommodityExchanges.size()>0){
            //给用户发放jd卡
            BypCommodityExchange bypCommodityExchange = bypCommodityExchanges.get(0);//给过期时间最早的SQL控制
            bypCommodityExchange.setUserMobilephone(member.getMobilephone());
            bypCommodityExchange.setExchangeTime(new Date());
            bypCommodityExchangeDao.updateByPrimaryKey(bypCommodityExchange);
            //修改积分
            Integer repertoryOut = bypCommodityRepertory.getRepertoryOut()+1;
            bypCommodityRepertory.setRepertoryOut(repertoryOut);
            bypCommodityRepertoryDao.updateByPrimaryKey(bypCommodityRepertory);
            member.setUserIntegralUse(member.getUserIntegralUse().subtract(bypCommodityRepertory.getCommodityWorth()));
            drMemberService.update(member);
            //积分记录
            bmi = new UserDetailIntegralBean(
                    null, uid, 4, new BigDecimal("-" + bypCommodityRepertory.getCommodityWorth()), new Date(), new Date(), null, null);
            userDetailIntegralDao.insert(bmi);
            //发送站内信
            br.setSuccess(true);
        }else {
            br.setSuccess(false);
            br.setErrorMsg("奖品数量不足,或积分不足,请联系客服!");
            log.error("奖品数量不足,或积分不足!!!兑换用户id:"+uid);
        }
        return br;
    }
//
//	/**
//	 * 计算累计投资额积分数
//	 * @param uid		用户id
//	 * @param invest
//	 * @param pid
//	 */
//	@Override
//	public void getInvestIntegral(Integer pid, Integer uid, BigDecimal invest){
//		BigDecimal points = new BigDecimal(0);
//		try {
//
//		}catch (Exception e){
//			log.error("计算用户投资积分Error！",e);
//		}
//		return ;
//	}

	/**
	 * 获取过期时间
	 * @return
	 */
	public Date getExpirationTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateString = "";
		try {
			calendar.setTime(date);
			if (calendar.get(Calendar.MONTH)+1>6) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
				calendar.set(Calendar.MONTH, 5);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			}else{
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			dateString = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime());
			date = sdf.parse(dateString);
		}catch (Exception e){
			log.error("过期时间获取错误！",e);
		}
		return date;
	}

}
