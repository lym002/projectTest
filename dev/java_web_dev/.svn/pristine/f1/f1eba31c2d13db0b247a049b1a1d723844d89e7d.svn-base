package com.jsjf.service.member.impl;

import com.jsjf.common.BaseResult;
import com.jsjf.common.LotteryUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrLotteryParamDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.member.*;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrLotteryParam;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.*;
import com.jsjf.model.system.DrCompanyFundsLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberLotteryLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class DrMemberLotteryLogServiceImpl implements DrMemberLotteryLogService {

	@Autowired
	private DrActivityDAO drActivityDAO;
	@Autowired
	private DrLotteryParamDAO drLotteryParamDAO;
	@Autowired
	private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;

	@Override
	public BaseResult insertLogtteryLog(DrMember member) throws Exception {
		BaseResult br = new BaseResult();
		Integer activityId = Integer.parseInt(redisClientTemplate
				.getProperties("online.activity.id"));
		DrActivity activity = drActivityDAO.selectByPrimaryKey(activityId);

		Date now = new Date();
		String lotteryActivitySms = redisClientTemplate
				.getProperties("lotteryActivitySms");// 短信模板
		String lotteryActivityMsg = redisClientTemplate
				.getProperties("lotteryActivityMsg");// 站内信模板
		if (now.after(activity.getStartTime())
				&& now.before(activity.getEndTime())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", member.getUid());
			map.put("aid", activity.getId());
			map.put("addtime", now);
			List<DrMemberLotteryLog> lotteryLogList = drMemberLotteryLogDAO
					.selectListByParam(map);
			DrMemberLotteryLog lotteryLog = Utils.isEmptyList(lotteryLogList) ? null
					: lotteryLogList.get(0);
			Integer num = new Random().nextInt(10);
			if (!Utils.isEmptyList(lotteryLogList)
					&& lotteryLogList.size() >= 3) {
				br.setErrorCode("1002");
				br.setErrorMsg("今日机会已用完，请明日再来");
				br.setSuccess(false);
			}
			switch (lotteryLogList.size()) {
			case 0:// 第一次抽奖
				if (num % 2 == 0) {// 偶数为中奖
					List<DrLotteryParam> lotteryParamList = drLotteryParamDAO
							.selectByAid(activity.getId());
					List<Double> rates = new ArrayList<Double>();
					for (int i = 0; i < lotteryParamList.size(); i++) {
						rates.add(lotteryParamList.get(i).getProbability());
					}
					Integer index = LotteryUtil.lottery(rates);
					DrLotteryParam lotteryParam = lotteryParamList.get(index);// 抽中的奖品
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(lotteryParam.getGiftId());// 获取奖品信息

					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), member.getUid(), dap.getType(),
							dap.getCode(), dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"端午活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);
					// 活动不对奖品已发数量做统计
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, dap.getId());
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(true);
					br.setMsg("恭喜您，获得" + dmf.getName());
					lotteryActivitySms = lotteryActivitySms.replace("${1}",
							dmf.getName());
					lotteryActivityMsg = lotteryActivityMsg.replace("${1}",
							dmf.getName());
				} else {
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, 0);
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(false);
					br.setErrorCode("1003");
					br.setMsg("很抱歉，您没有收到奖品");
				}
				break;
			case 1:// 第二次抽奖
				if (lotteryLog.getGiftId() == 0 && num % 2 == 0) {// 未中奖过奖，并本次抽中
					List<DrLotteryParam> lotteryParamList = drLotteryParamDAO
							.selectByAid(activity.getId());
					List<Double> rates = new ArrayList<Double>();
					for (int i = 0; i < lotteryParamList.size(); i++) {
						rates.add(lotteryParamList.get(i).getProbability());
					}
					Integer index = LotteryUtil.lottery(rates);
					DrLotteryParam lotteryParam = lotteryParamList.get(index);// 抽中的奖品
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(lotteryParam.getGiftId());// 获取奖品信息

					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), member.getUid(), dap.getType(),
							dap.getCode(), dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"端午活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);
					// 活动不对奖品已发数量做统计
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, dap.getId());
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(true);
					br.setMsg("恭喜您，获得" + dmf.getName());
					lotteryActivitySms = lotteryActivitySms.replace("${1}",
							dmf.getName());
					lotteryActivityMsg = lotteryActivityMsg.replace("${1}",
							dmf.getName());
				} else {
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, 0);
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(false);
					br.setErrorCode("1003");
					br.setMsg("很抱歉，您没有收到奖品");
				}
				break;
			case 2:// 第三次抽奖
				if (lotteryLog.getGiftId() == 0) {
					List<DrLotteryParam> lotteryParamList = drLotteryParamDAO
							.selectByAid(activity.getId());
					List<Double> rates = new ArrayList<Double>();
					for (int i = 0; i < lotteryParamList.size(); i++) {
						rates.add(lotteryParamList.get(i).getProbability());
					}
					Integer index = LotteryUtil.lottery(rates);
					DrLotteryParam lotteryParam = lotteryParamList.get(index);// 抽中的奖品
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(lotteryParam.getGiftId());// 获取奖品信息

					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), member.getUid(), dap.getType(),
							dap.getCode(), dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"端午活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);
					// 活动不对奖品已发数量做统计
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, dap.getId());
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(true);
					br.setMsg("恭喜您，获得" + dmf.getName());
					lotteryActivitySms = lotteryActivitySms.replace("${1}",
							dmf.getName());
					lotteryActivityMsg = lotteryActivityMsg.replace("${1}",
							dmf.getName());
				} else {
					// 插入抽奖记录
					lotteryLog = new DrMemberLotteryLog(activity.getId(),
							member.getUid(), now, 0);
					drMemberLotteryLogDAO.insert(lotteryLog);
					br.setSuccess(false);
					br.setErrorCode("1003");
					br.setMsg("很抱歉，您没有收到奖品");
				}
				break;
			}

		} else {
			br.setErrorCode("1001");
			br.setErrorMsg("请在活动期间进行抽奖");
			br.setSuccess(false);
		}
		if (br.isSuccess()) {
			SysMessageLog sysLog = new SysMessageLog(member.getUid(),
					lotteryActivitySms, 18, null, member.getMobilephone());
			sysMessageLogService.sendMsg(sysLog, 1);
			DrMemberMsg msg = new DrMemberMsg(member.getUid(), 0, 2, "端午节活动",
					now, 0, 0, lotteryActivityMsg);
			drMemberMsgDAO.insertDrMemberMsg(msg);
		}
		return br;
	}

	@Override
	public List<DrMemberLotteryLog> selectListByParam(Map<String, Object> map) {
		return drMemberLotteryLogDAO.selectListByParam(map);
	}

	@Override
	public List<DrMemberLotteryLog> selectDrMemberLotteryLogByMap() {
		return drMemberLotteryLogDAO.selectDrMemberLotteryLogByMap();
	}

	@Override
	public int getLotteryCountByUid(int uid) {
		return drMemberLotteryLogDAO.getLotteryCountByUid(uid);
	}

	@Override
	public BaseResult selectLotteryListByParams(PageInfo pi, int uid) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrMemberLotteryLog> list = drMemberLotteryLogDAO
				.selectDrMemberLotteryLogList(map);
		Integer total = drMemberLotteryLogDAO
				.selectDrMemberLotteryLogListCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		map.clear();
		map.put("page", pi);
		br.setMap(map);
		return br;
	}

	@Override
	public BaseResult updateLotteryQX(int uid) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, String> map = new HashMap<String, String>();

		List<String> list = redisClientTemplate.hmget("investTotal", uid + "");
		if (StringUtils.isBlank(list.get(0))) {// >=41名
			List<DrLotteryParam> colourList41 = drLotteryParamDAO
					.selectByAid(7);
			DrLotteryParam colourParam41 = lottery(colourList41);
			if ("green".equals(colourParam41.getGiftName())) {
				List<DrLotteryParam> greenList41 = drLotteryParamDAO
						.selectByAid(3);
				DrLotteryParam greenParam41 = lottery(greenList41);
				String colourCount = redisClientTemplate.get("greenCount");
				if (Integer.valueOf(colourCount) > 0) {
					String count = redisClientTemplate.get(greenParam41
							.getGiftName() + "Count");
					if (Integer.valueOf(count) <= 0) {
						DrActivityParameter dap = drActivityParameterDAO
								.getActivityParameterById(358);// 获取奖品信息
																// ----------358
						DrMemberFavourable dmf = new DrMemberFavourable(
								dap.getId(), uid, dap.getType(), dap.getCode(),
								dap.getName(), dap.getAmount(),
								dap.getRaisedRates(), dap.getEnableAmount(), 0,
								Utils.getDayNumOfDate(1 - dap.getDeadline()),
								"七夕活动奖品", 0, 3, dap.getProductDeadline(),
								dap.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								13, uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						br.setSuccess(true);
						map.put("id", "13");
						map.put("name", "4%加息券");
						br.setMap(map);
						return br;
					} else {
						if (Utils.isObjectNotEmpty(greenParam41.getGiftId())) {
							DrActivityParameter dap = drActivityParameterDAO
									.getActivityParameterById(greenParam41
											.getGiftId());// 获取奖品信息
							DrMemberFavourable dmf = new DrMemberFavourable(
									dap.getId(),
									uid,
									dap.getType(),
									dap.getCode(),
									dap.getName(),
									dap.getAmount(),
									dap.getRaisedRates(),
									dap.getEnableAmount(),
									0,
									Utils.getDayNumOfDate(1 - dap.getDeadline()),
									"七夕活动奖品", 0, 3, dap.getProductDeadline(),
									dap.getMultiple());
							drMemberFavourableDAO.insertIntoInfo(dmf);
						}

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								greenParam41.getId(), uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						redisClientTemplate.set(
								greenParam41.getGiftName() + "Count",
								Integer.valueOf(redisClientTemplate
										.get(greenParam41.getGiftName()
												+ "Count"))
										- 1 + "");
						redisClientTemplate.set(
								"greenCount",
								Integer.valueOf(redisClientTemplate
										.get("greenCount")) - 1 + "");

						br.setSuccess(true);
						map.put("id", greenParam41.getId() + "");
						map.put("name", greenParam41.getGiftName());
						br.setMap(map);
						return br;
					}
				} else {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);

					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13,
							uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				}
			} else {
				DrActivityParameter dap = drActivityParameterDAO
						.getActivityParameterById(colourParam41.getGiftId());// 获取奖品信息
				DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(),
						uid, dap.getType(), dap.getCode(), dap.getName(),
						dap.getAmount(), dap.getRaisedRates(),
						dap.getEnableAmount(), 0, Utils.getDayNumOfDate(1 - dap
								.getDeadline()), "七夕活动奖品", 0, 3,
						dap.getProductDeadline(), dap.getMultiple());
				drMemberFavourableDAO.insertIntoInfo(dmf);

				DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13, uid,
						new Date(), null);
				drMemberLotteryLogDAO.insert(lotteryLog);

				br.setSuccess(true);
				map.put("id", "13");
				map.put("name", "4%加息券");
				br.setMap(map);
				return br;
			}
		} else if (Integer.valueOf(list.get(0)) >= 9
				&& Integer.valueOf(list.get(0)) <= 40) {// 9-40名
			List<DrLotteryParam> colourList9 = drLotteryParamDAO.selectByAid(6);
			DrLotteryParam colourParam9 = lottery(colourList9);
			if ("blue".equals(colourParam9.getGiftName())) {
				List<DrLotteryParam> blueList9 = drLotteryParamDAO
						.selectByAid(2);
				DrLotteryParam blueParam9 = lottery(blueList9);
				String colourCount = redisClientTemplate.get("blueCount");
				if (Integer.valueOf(colourCount) > 0) {
					String count = redisClientTemplate.get(blueParam9
							.getGiftName() + "Count");
					if (Integer.valueOf(count) <= 0) {
						DrActivityParameter dap = drActivityParameterDAO
								.getActivityParameterById(358);// 获取奖品信息
																// ----------358
						DrMemberFavourable dmf = new DrMemberFavourable(
								dap.getId(), uid, dap.getType(), dap.getCode(),
								dap.getName(), dap.getAmount(),
								dap.getRaisedRates(), dap.getEnableAmount(), 0,
								Utils.getDayNumOfDate(1 - dap.getDeadline()),
								"七夕活动奖品", 0, 3, dap.getProductDeadline(),
								dap.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								13, uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						br.setSuccess(true);
						map.put("id", "13");
						map.put("name", "4%加息券");
						br.setMap(map);
						return br;
					} else {
						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								blueParam9.getId(), uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						redisClientTemplate
								.set(blueParam9.getGiftName() + "Count",
										Integer.valueOf(redisClientTemplate
												.get(blueParam9.getGiftName()
														+ "Count"))
												- 1 + "");
						redisClientTemplate.set(
								"blueCount",
								Integer.valueOf(redisClientTemplate
										.get("blueCount")) - 1 + "");

						br.setSuccess(true);
						map.put("id", blueParam9.getId() + "");
						map.put("name", blueParam9.getGiftName());
						br.setMap(map);
						return br;
					}
				} else {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);

					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13,
							uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				}
			} else if ("green".equals(colourParam9.getGiftName())) {
				List<DrLotteryParam> greenList9 = drLotteryParamDAO
						.selectByAid(3);
				DrLotteryParam greenParam9 = lottery(greenList9);
				String colourCount = redisClientTemplate.get("greenCount");
				if (Integer.valueOf(colourCount) > 0) {
					String count = redisClientTemplate.get(greenParam9
							.getGiftName() + "Count");
					if (Integer.valueOf(count) <= 0) {
						DrActivityParameter dap = drActivityParameterDAO
								.getActivityParameterById(358);// 获取奖品信息
																// ----------358
						DrMemberFavourable dmf = new DrMemberFavourable(
								dap.getId(), uid, dap.getType(), dap.getCode(),
								dap.getName(), dap.getAmount(),
								dap.getRaisedRates(), dap.getEnableAmount(), 0,
								Utils.getDayNumOfDate(1 - dap.getDeadline()),
								"七夕活动奖品", 0, 3, dap.getProductDeadline(),
								dap.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								13, uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						br.setSuccess(true);
						map.put("id", "13");
						map.put("name", "4%加息券");
						br.setMap(map);
						return br;
					} else {
						if (Utils.isObjectNotEmpty(greenParam9.getGiftId())) {
							DrActivityParameter dap = drActivityParameterDAO
									.getActivityParameterById(greenParam9
											.getGiftId());// 获取奖品信息
							DrMemberFavourable dmf = new DrMemberFavourable(
									dap.getId(),
									uid,
									dap.getType(),
									dap.getCode(),
									dap.getName(),
									dap.getAmount(),
									dap.getRaisedRates(),
									dap.getEnableAmount(),
									0,
									Utils.getDayNumOfDate(1 - dap.getDeadline()),
									"七夕活动奖品", 0, 3, dap.getProductDeadline(),
									dap.getMultiple());
							drMemberFavourableDAO.insertIntoInfo(dmf);
						}

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								greenParam9.getId(), uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						redisClientTemplate.set(
								greenParam9.getGiftName() + "Count",
								Integer.valueOf(redisClientTemplate
										.get(greenParam9.getGiftName()
												+ "Count"))
										- 1 + "");
						redisClientTemplate.set(
								"greenCount",
								Integer.valueOf(redisClientTemplate
										.get("greenCount")) - 1 + "");

						br.setSuccess(true);
						map.put("id", greenParam9.getId() + "");
						map.put("name", greenParam9.getGiftName());
						br.setMap(map);
						return br;
					}
				} else {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);

					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13,
							uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				}
			} else {
				DrActivityParameter dap = drActivityParameterDAO
						.getActivityParameterById(colourParam9.getGiftId());// 获取奖品信息
				DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(),
						uid, dap.getType(), dap.getCode(), dap.getName(),
						dap.getAmount(), dap.getRaisedRates(),
						dap.getEnableAmount(), 0, Utils.getDayNumOfDate(1 - dap
								.getDeadline()), "七夕活动奖品", 0, 3,
						dap.getProductDeadline(), dap.getMultiple());
				drMemberFavourableDAO.insertIntoInfo(dmf);

				DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13, uid,
						new Date(), null);
				drMemberLotteryLogDAO.insert(lotteryLog);

				br.setSuccess(true);
				map.put("id", "13");
				map.put("name", "4%加息券");
				br.setMap(map);
				return br;
			}
		} else {
			List<DrLotteryParam> colourList1 = drLotteryParamDAO.selectByAid(5);
			DrLotteryParam colourParam1 = lottery(colourList1);
			if ("red".equals(colourParam1.getGiftName())) {
				List<DrLotteryParam> redList1 = drLotteryParamDAO
						.selectByAid(1);
				DrLotteryParam redParam1 = lottery(redList1);
				String count = redisClientTemplate.get(redParam1.getGiftName()
						+ "Count");
				if (Integer.valueOf(count) <= 0) {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);
					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				} else {
					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
							redParam1.getId(), uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					redisClientTemplate.set(
							redParam1.getGiftName() + "Count",
							Integer.valueOf(redisClientTemplate.get(redParam1
									.getGiftName() + "Count"))
									- 1 + "");
					redisClientTemplate
							.set("redCount",
									Integer.valueOf(redisClientTemplate
											.get("redCount")) - 1 + "");

					br.setSuccess(true);
					map.put("id", redParam1.getId() + "");
					map.put("name", redParam1.getGiftName());
					br.setMap(map);
					return br;
				}
			} else if ("green".equals(colourParam1.getGiftName())) {
				List<DrLotteryParam> greenList1 = drLotteryParamDAO
						.selectByAid(3);
				DrLotteryParam greenParam1 = lottery(greenList1);
				String colourCount = redisClientTemplate.get("greenCount");
				if (Integer.valueOf(colourCount) > 0) {
					String count = redisClientTemplate.get(greenParam1
							.getGiftName() + "Count");
					if (Integer.valueOf(count) <= 0) {
						DrActivityParameter dap = drActivityParameterDAO
								.getActivityParameterById(358);// 获取奖品信息
																// ----------358
						DrMemberFavourable dmf = new DrMemberFavourable(
								dap.getId(), uid, dap.getType(), dap.getCode(),
								dap.getName(), dap.getAmount(),
								dap.getRaisedRates(), dap.getEnableAmount(), 0,
								Utils.getDayNumOfDate(1 - dap.getDeadline()),
								"七夕活动奖品", 0, 3, dap.getProductDeadline(),
								dap.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								13, uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						br.setSuccess(true);
						map.put("id", "13");
						map.put("name", "4%加息券");
						br.setMap(map);
						return br;
					} else {
						if (Utils.isObjectNotEmpty(greenParam1.getGiftId())) {
							DrActivityParameter dap = drActivityParameterDAO
									.getActivityParameterById(greenParam1
											.getGiftId());// 获取奖品信息
							DrMemberFavourable dmf = new DrMemberFavourable(
									dap.getId(),
									uid,
									dap.getType(),
									dap.getCode(),
									dap.getName(),
									dap.getAmount(),
									dap.getRaisedRates(),
									dap.getEnableAmount(),
									0,
									Utils.getDayNumOfDate(1 - dap.getDeadline()),
									"七夕活动奖品", 0, 3, dap.getProductDeadline(),
									dap.getMultiple());
							drMemberFavourableDAO.insertIntoInfo(dmf);
						}

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								greenParam1.getId(), uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						redisClientTemplate.set(
								greenParam1.getGiftName() + "Count",
								Integer.valueOf(redisClientTemplate
										.get(greenParam1.getGiftName()
												+ "Count"))
										- 1 + "");
						redisClientTemplate.set(
								"greenCount",
								Integer.valueOf(redisClientTemplate
										.get("greenCount")) - 1 + "");

						br.setSuccess(true);
						map.put("id", greenParam1.getId() + "");
						map.put("name", greenParam1.getGiftName());
						br.setMap(map);
						return br;
					}
				} else {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);

					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13,
							uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				}
			} else if ("blue".equals(colourParam1.getGiftName())) {
				List<DrLotteryParam> blueList1 = drLotteryParamDAO
						.selectByAid(2);
				DrLotteryParam blueParam1 = lottery(blueList1);
				String colourCount = redisClientTemplate.get("blueCount");
				if (Integer.valueOf(colourCount) > 0) {
					String count = redisClientTemplate.get(blueParam1
							.getGiftName() + "Count");
					if (Integer.valueOf(count) <= 0) {
						DrActivityParameter dap = drActivityParameterDAO
								.getActivityParameterById(358);// 获取奖品信息
																// ----------358
						DrMemberFavourable dmf = new DrMemberFavourable(
								dap.getId(), uid, dap.getType(), dap.getCode(),
								dap.getName(), dap.getAmount(),
								dap.getRaisedRates(), dap.getEnableAmount(), 0,
								Utils.getDayNumOfDate(1 - dap.getDeadline()),
								"七夕活动奖品", 0, 3, dap.getProductDeadline(),
								dap.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);

						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								13, uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						br.setSuccess(true);
						map.put("id", "13");
						map.put("name", "4%加息券");
						br.setMap(map);
						return br;
					} else {
						DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(
								blueParam1.getId(), uid, new Date(), null);
						drMemberLotteryLogDAO.insert(lotteryLog);

						redisClientTemplate
								.set(blueParam1.getGiftName() + "Count",
										Integer.valueOf(redisClientTemplate
												.get(blueParam1.getGiftName()
														+ "Count"))
												- 1 + "");
						redisClientTemplate.set(
								"blueCount",
								Integer.valueOf(redisClientTemplate
										.get("blueCount")) - 1 + "");

						br.setSuccess(true);
						map.put("id", blueParam1.getId() + "");
						map.put("name", blueParam1.getGiftName());
						br.setMap(map);
						return br;
					}
				} else {
					DrActivityParameter dap = drActivityParameterDAO
							.getActivityParameterById(358);// 获取奖品信息
															// ----------358
					DrMemberFavourable dmf = new DrMemberFavourable(
							dap.getId(), uid, dap.getType(), dap.getCode(),
							dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()),
							"七夕活动奖品", 0, 3, dap.getProductDeadline(),
							dap.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf);

					DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13,
							uid, new Date(), null);
					drMemberLotteryLogDAO.insert(lotteryLog);

					br.setSuccess(true);
					map.put("id", "13");
					map.put("name", "4%加息券");
					br.setMap(map);
					return br;
				}
			} else {
				DrActivityParameter dap = drActivityParameterDAO
						.getActivityParameterById(colourParam1.getGiftId());// 获取奖品信息
				DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(),
						uid, dap.getType(), dap.getCode(), dap.getName(),
						dap.getAmount(), dap.getRaisedRates(),
						dap.getEnableAmount(), 0, Utils.getDayNumOfDate(1 - dap
								.getDeadline()), "七夕活动奖品", 0, 3,
						dap.getProductDeadline(), dap.getMultiple());
				drMemberFavourableDAO.insertIntoInfo(dmf);

				DrMemberLotteryLog lotteryLog = new DrMemberLotteryLog(13, uid,
						new Date(), null);
				drMemberLotteryLogDAO.insert(lotteryLog);

				br.setSuccess(true);
				map.put("id", "13");
				map.put("name", "4%加息券");
				br.setMap(map);
				return br;
			}
		}
	}

	public DrLotteryParam lottery(List<DrLotteryParam> orignalRates) {
		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (int i = 0; i < orignalRates.size(); i++) {
			sumRate += orignalRates.get(i).getProbability();
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (int i = 0; i < orignalRates.size(); i++) {
			tempSumRate += orignalRates.get(i).getProbability();
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		DrLotteryParam drLotteryParam = orignalRates.get(sortOrignalRates
				.indexOf(nextDouble));
		if (Integer.valueOf(redisClientTemplate.get(drLotteryParam
				.getGiftName() + "Count")) <= 0) {
			lottery(orignalRates);
		} else {
			return drLotteryParam;
		}
		return drLotteryParam;
	}

	@Override
	public int getLotteryCount(int uid) {
		return drMemberLotteryLogDAO.getLotteryCount(uid);
	}

	@Override
	public BaseResult insertLogtteryLogDoubleEgg(DrMember m) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		if (Utils.parse(redisClientTemplate.getProperties("activityStartDate"),
				"yyyy-MM-dd HH:mm:ss").before(new Date())
				&& Utils.parse(
						redisClientTemplate.getProperties("activityEndDate"),
						"yyyy-MM-dd HH:mm:ss").after(new Date())) {
			map.put("uid", m.getUid());
			map.put("giftId", -1);
			map.put("orders", " id asc ");
			boolean lockFlag = redisClientTemplate.tryLock(
					"tearOpen." + m.getUid(), 3, TimeUnit.SECONDS, true);
			if (lockFlag) {
				List<DrMemberLotteryLog> looteryList = drMemberLotteryLogDAO
						.selectListByParam(map);
				if (!Utils.isEmptyList(looteryList)) {
					List<DrLotteryParam> paramList = drLotteryParamDAO
							.selectByAid(looteryList.get(0).getAid());
					double lotterFirst = 0;
					double lotterEnd = 0;
					double lotter = 0;
					lotter = Math.random();// 随机数
					for (int i = 0; i < paramList.size(); i++) {
						if (i != 0)
							lotterFirst += paramList.get(i - 1)
									.getProbability();// 概率区间1
						lotterEnd += paramList.get(i).getProbability();// 概率区间2
						if (lotter > lotterFirst && lotter <= lotterEnd) {// 概率判断
							looteryList.get(0).setGiftId(
									paramList.get(i).getId());
							drMemberLotteryLogDAO.update(looteryList.get(0));
							map.clear();
							BigDecimal amount = new BigDecimal(paramList.get(i)
									.getGiftName().toString());
							String prizeName = "三等奖";
							if (paramList.get(i).getGiftId() == 1) {
								prizeName = "一等奖";
							} else if (paramList.get(i).getGiftId() == 2) {
								prizeName = "二等奖";
							}

							map.put("pullCount", looteryList.size() - 1);// 剩余次数
							map.put("amount", paramList.get(i).getGiftName());// 奖品金额
							map.put("prizeName", prizeName);// 奖品金额
							// 资金
							DrMemberFunds fund = drMemberFundsDAO
									.queryDrMemberFundsByUid(m.getUid());
							fund.setBalance(fund.getBalance().add(amount));// 余额
							fund.setSpreadProfit(fund.getSpreadProfit().add(
									amount));// 其他收益
							drMemberFundsDAO.updateDrMemberFunds(fund);
							// 资金记录recode
							DrMemberFundsRecord record = new DrMemberFundsRecord(
									null, looteryList.get(0).getInvestId(),
									m.getUid(), 4, 1, amount,
									fund.getBalance(), 3, "捕鱼达人活动-捕鱼中大奖:" + prizeName,
									null);
							drMemberFundsRecordDAO.insert(record);
							// 资金记录log
							DrMemberFundsLog fundslog = new DrMemberFundsLog(
									m.getUid(), record.getId(), amount, 20, 1,
									"捕鱼达人活动-捕鱼中大奖:" + prizeName);
							drMemberFundsLogDAO
									.insertDrMemberFundsLog(fundslog);
							// 公司资金交易记录表
							DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(
									11, m.getUid(), null, amount, 0, "捕鱼达人活动-捕鱼中大奖", 0);
							drCompanyFundsLogDAO
									.insertDrCompanyFundsLog(cfundsLog);

                            String msgContext = "恭喜您在“捕鱼达人活动-捕鱼中大奖”活动抽中"+amount+"，已发送至【我的账户】，快去使用吧！再来一次，还能中大奖！无人机、熊本熊相机，将伴你一起出游！";
							DrMemberMsg msg = new DrMemberMsg(m.getUid(), 0, 2,
									"捕鱼达人活动-捕鱼中大奖", new Date(), 0, 0, msgContext);
							drMemberMsgDAO.insertDrMemberMsg(msg);

							br.setMap(map);
							br.setSuccess(true);
							break;
						}
					}
				} else {
					br.setErrorCode("1003");
					br.setErrorMsg("没有抽奖次数");
				}
			} else {
				br.setErrorCode("1002");
				br.setErrorMsg("系统繁忙稍后重试");
			}
		} else {
			br.setErrorCode("1001");
			br.setErrorMsg("活动已经结束");
		}
		return br;
	}

	@Override
	public String selectGiftName(int uid) {
		return drMemberLotteryLogDAO.selectGiftName(uid);
	}

	@Override
	public Integer getDoubleAGGLotteryCountByUid(Map<String, Object> map) {
		return drMemberLotteryLogDAO.getDoubleAGGLotteryCountByUid(map);
	}

	@Override
	public List<DrMemberLotteryLog> getDoubleAGGListCountByUid() {
		return drMemberLotteryLogDAO.getDoubleAGGListCountByUid();
	}

	@Override
	public void insertLogtteryLog(DrMemberLotteryLog drMemberLotteryLog) {
		drMemberLotteryLogDAO.insert(drMemberLotteryLog);
	}

	@Override
	public List<DrMemberLotteryLog> selectLotteryLogByAid() {
		return drMemberLotteryLogDAO.selectLotteryLogByAid();
	}

	@Override
	public Integer getDoubleAGGOneLottery(Map<String, Object> map) {
		return drMemberLotteryLogDAO.getDoubleAGGOneLottery(map);
	}

	@Override
	public List<DrMemberLotteryLog> selectRecords(Integer uid) {
		return drMemberLotteryLogDAO.selectRecords(uid);
	}
}
