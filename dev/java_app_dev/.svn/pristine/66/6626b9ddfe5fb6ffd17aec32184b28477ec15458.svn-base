package com.jsjf.controller.account.index;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.JsMemberInfoDAO;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.activity.JsActivityFriendService;
import com.jsjf.service.member.*;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/accountIndex")
@Controller
public class AccountIndexController {
	private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private JsMemberInfoDAO jsMemberInfoDAO;
	@Autowired
	private RedisClientTemplate redis;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrMemberFundsService DrMemberFundsService;
	@Autowired
	private DrMemberMsgService drMemberMsgService;
	@Autowired
	private DrMemberFavourableService drMemberFavourableService;
	@Autowired
	private JsActivityFriendService jsActivityFriendService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	DrMemberCpsFavourableRuleService drMemberCpsFavourableRuleService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	JsMemberInfoService jsMemberInfoService;

	@RequestMapping("/info")
	@ResponseBody
	public String info(HttpServletRequest req, Integer uid, Integer channel) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 未读消息条数
			map.put("uid", uid);
			map.put("isRead", 0);
			Integer unReadCount = drMemberMsgService.getUnReadCountByMap(map);
			// 获取累计收益
			BigDecimal accumulatedIncome = drProductInvestService
					.selectAccumulatedIncomeSumByUid(map);
			// 判断用户是否投资了新手标
			map.put("isNewHand", 5);
			Integer isNewHand = drProductInvestService.checkProductType(map) == true ? 0
					: 1;
			/*// 判断用户收货信息是否完善
			boolean isPerfect = true;
			List<DrProductInvest> dpiList = drProductInvestService
					.selectInvestSendNotAddress(uid);
			if (dpiList.size() > 0) {
				isPerfect = false;
			}*/
            boolean isPerfect = true;
            // 查询客户是否有地址
            JsMemberInfo selectMemberInfoByUid = jsMemberInfoDAO
                    .selectMemberInfoByUid(uid);
            if (Utils.isObjectEmpty(selectMemberInfoByUid)) {
                isPerfect = false;
            }
			// 可用优惠券数量
			map.clear();
			map.put("uid", uid);
			map.put("status", 0);
			map.put("type", new Integer[] { 1, 2, 4 });
			List<DrMemberFavourable> list = drMemberFavourableService
					.selectByParam(map);
			map.clear();
			// 体验金可用金额
			map.put("availableExperience",
					drMemberFavourableService.getMemberFavourableSumByUid(uid));
			map.put("unReadMsg", unReadCount);
			map.put("unUseFavourable", list.size());

			DrMember m = drMemberService.selectByPrimaryKey(uid);
			map.put("realVerify", m.getRealVerify());
			map.put("sex", m.getSex());
			map.put("realName", m.getRealName());

			// 账户资金
			DrMemberFunds funds = DrMemberFundsService
					.selectDrMemberFundsByUid(uid);
			map.put("balance", funds.getBalance());
			map.put("free", funds.getFreeze());// 冻结资金
			map.put("winterest", funds.getWinterest());// 待收收益
			map.put("wprincipal", funds.getWprincipal());// 待收本金
            map.put("newWprincipal",funds.getFuiou_winterest());//等收利息
			// 存管
			map.put("balanceFuiou", funds.getFuiou_balance());
			map.put("freeFuiou", funds.getFuiou_freeze());
			// 待收本金+罚息+待收利息
			BigDecimal collectAmountFuiou = funds.getFuiou_winterest()
					.add(funds.getFuiou_wpenalty())
					.add(funds.getFuiou_wprincipal());
			/**
			 * TODO 2017-06-15 安卓取错值 总资产计算错误 修改：如果安卓请求 本息合计取待收本金
			 */
			/*map.put("collectAmountFuiou",
					channel == 2 ? funds.getFuiou_wprincipal()
							: collectAmountFuiou);*/
            //map.put("collectAmountFuiou",collectAmountFuiou);
            map.put("fuiou_wprincipal",funds.getFuiou_wprincipal());
			map.put("wprincipalFuiou", funds.getFuiou_wprincipal());// 待收本金
			map.put("winterestFuiou", funds.getFuiou_winterest());// 待收收益

			map.put("profitFuiou",
					funds.getFuiou_investProfit().add(
							funds.getFuiou_spreadProfit()));// 已收投资收益+推广收益
			map.put("winterestFuiou", funds.getFuiou_winterest());// 待收收益

			// 好友推荐奖励未领取
			BaseResult afBr = jsActivityFriendService
					.getActivityFriendStatistics(null, uid, null, null);
			if (afBr.isSuccess()) {
				map.put("afid", afBr.getMap().get("afid"));
				map.put("unclaimed", afBr.getMap().get("unclaimed"));
			}
			List<DrMemberFavourable> redPacketList = drMemberFavourableService
					.selectFavourableOrderByAmountExpireDate(uid);
			if (!Utils.isEmptyList(redPacketList)) {// 是否有红包
				map.put("isRedPacket", true);
			} else {
				map.put("isRedPacket", false);
			}
			/**
			 * 根据渠道是否为应用宝并且客户端是否为Android来返回体验金
			 */
			List<DrMemberFavourable> redList = drMemberFavourableService
					.selectDrMemberFavourableByUid(uid);// 根据uid查询出来的体验金
			if (!Utils.isEmptyList(redList)) {
				map.put("isExperience", true);
			} else {
				map.put("isExperience", false);
			}
			map.put("isPayment", false);// 没回款
			// 未来7天有无回款
			// List<DrProductInvestRepayInfo> drProductInvestRepayInfo =
			// drProductInvestRepayInfoService.selectPayment(m.getUid());
			// 7日汇款总和
			// if(drProductInvestRepayInfo.size()>0){
			// BigDecimal shouldPrincipalCount = BigDecimal.ZERO;
			// for (DrProductInvestRepayInfo drProductInvestRepayInfo2 :
			// drProductInvestRepayInfo) {
			// shouldPrincipalCount =
			// shouldPrincipalCount.add(drProductInvestRepayInfo2.getShouldPrincipal());
			// }
			// if(shouldPrincipalCount.compareTo(new BigDecimal(0))==1){
			// Map<String,Object> paramMap = new HashMap<String,Object>();
			// //查询用户是否首投
			// int isFirst = drMemberService.selectFirstPayMent(uid);
			// paramMap.put("uid", uid);
			// paramMap.put("shouldPrincipalCount",
			// shouldPrincipalCount.intValue());
			// paramMap.put("isFirst", isFirst ==0?1:0);
			// List<DrMemberCpsFavourableRule> list2 =
			// drMemberCpsFavourableRuleService.selectActivityByCps(paramMap);
			// if(list2.size()>0){
			map.put("isPayment", false);// 有回款
			// //复投红包机会统计次数
			// long flag =redis.sadd("fuTouSet", m.getUid().toString());
			// if(flag==1){
			// redis.incr("fuTouHongBaoJiHuiTongJi");
			// }
			// }
			// }
			// }

			map.put("investSendUrl",
					redisClientTemplate.getProperties("investSendUrl"));
			map.put("accumulatedIncome", accumulatedIncome);
			map.put("isNewHand", isNewHand);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", 1);
			DrProductInfo productInfo = drProductInfoService
					.selectNewHandInfo(param);
			map.put("newHandId", productInfo != null ? productInfo.getId()
					: null);
			map.put("unTiedCardTitle",
					redisClientTemplate.getProperties("unTiedCardTitle"));
			map.put("isPerfect", isPerfect);

			List<DrProductInvest> investList = drProductInvestService
					.selectInvestSendDrProductInfoByUid(uid);
			map.put("prizeType", investList.size() > 0 ? investList.get(0)
					.getPrizeType() : null);
			map.put("pid", investList.size() > 0 ? investList.get(0).getPid()
					: null);
			map.put("investId", investList.size() > 0 ? investList.get(0)
					.getId() : null);

			if (Utils.isObjectNotEmpty(m)) {
				map.put("isFuiou", m.getIsFuiou());
			}
			// 体验金
			BigDecimal experienceAmount = drMemberFavourableService
					.selectRegSendExperienceGold(uid);
			map.put("experienceAmount",
					experienceAmount != null ? experienceAmount : 0);
			/*// 收货信息不完善返回投资投即送标最新一个pid
			if (!isPerfect) {
				map.put("investSendId", dpiList.size() > 0 ? dpiList.get(0)
						.getPid() : null);
			}*/
			// 邀请好友三重礼
			BaseResult threePresent = jsActivityFriendService.threePresent(uid);
			if (threePresent.isSuccess() && threePresent.getMap() != null) {
				((Map<String, Object>) threePresent.getMap()).putAll(map);
				map.put("afid", threePresent.getMap().get("afid"));
				map.put("unclaimed", threePresent.getMap().get("unclaimed"));
			}
			/**
			 * 扩展新版老客户提示 需求:老客户使用新版且还未投资的弹出提示框且发送站内信和新版平台券
			 * date是传入时间在某时间之后投资算作新版投资 start
			 */
			int[] ids={2,3};
			DrMember dm = drMemberService.selectIsBypOldUser(uid,ids);
			Integer result = dm != null ? dm.getIs_Byp_Old_User() : 0;
			Integer res = drProductInvestService.selectNewInvest(uid);// 新版本没有投资
			// 插入站内信
			String str = "尊敬的币优铺老用户,因版本更新,旧版资产与投资记录需至币优铺旧版查看,给您带来不便敬请谅解.";
			String title = "老版客户通知";
			Integer selectMsg = drMemberMsgService.selectMsg(title, uid);
			map.put("Push", "");
			map.put("favourable", false);
			if (2 == result && Utils.isObjectEmpty(res)) {
				map.put("Push", str);
				// 满足条件且没有发送过的给与信件发送
				if (Utils.isObjectEmpty(selectMsg)) {
					DrMemberMsg msg = new DrMemberMsg(uid, 0, 1, title,
							new Date(), 0, 0, str);
					drMemberMsgService.insert(msg);
					// ***********************老客户送新手礼包***************************
					Boolean flag = redisClientTemplate.tryLock("info.user"
							+ uid, 3, TimeUnit.SECONDS, true);// 加锁防止并发重复发红包
					if (flag) {
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("type", 6);
						map2.put("uid", uid);
						redisClientTemplate.lpush(
								"regAndVerifySendRedUidList".getBytes(),
								SerializeUtil.serialize(map2));
						DrMember m1 = new DrMember();
						m1.setUid(uid);
						m1.setIs_Byp_Old_User(3);
						map.put("favourable", true);
						drMemberService.update(m1);
					} else {
						br.setErrorCode("9999");
						br.setSuccess(false);
						log.info(uid + "获取红包失败：可能是redis锁定");
						map.put("favourable", false);
					}
				}else {
					log.info("用户已经领取过红包了");
					map.put("favourable", false);
				}
			}
			if (3 == result && Utils.isObjectEmpty(res)) {
				List<DrMemberFavourable> sf = drMemberFavourableService
						.selectOldIsUse(uid);
				//如果为true说明是可用状态
				boolean emptyList = Utils.isEmptyList(sf);
				if (emptyList) {
					map.put("Push", str);
					map.put("URL", "等待添加");
				}else {
					map.put("Push", "");
					log.info("用户已经领取过红包了");
					map.put("favourable", false);
				}
			}
			// end
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("用户【" + uid + "】的账户信息读取错误", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}

	@RequestMapping("/myFunds")
	@ResponseBody
	public String myFunds(HttpServletRequest req, Integer uid) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DrMemberFunds drMemberFunds = DrMemberFundsService
					.selectDrMemberFundsByUid(uid);
			drMemberFunds.getWinterestAddFuiou();
			drMemberFunds.getWprincipalAddFuiou();
			DrMember m = drMemberService.selectByPrimaryKey(uid);
			map.put("isFuiou", m.getIsFuiou());
			map.put("funds", drMemberFunds);
            // 待收本金+罚息+待收利息
            BigDecimal collectAmountFuiou = drMemberFunds.getFuiou_winterest()
                    .add(drMemberFunds.getFuiou_wpenalty())
                    .add(drMemberFunds.getFuiou_wprincipal());
            map.put("collectAmountFuiou",collectAmountFuiou);
            //冻结金额
            map.put("freeFuiou", drMemberFunds.getFuiou_freeze());
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("我【" + uid + "】的资产读取失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}

}
