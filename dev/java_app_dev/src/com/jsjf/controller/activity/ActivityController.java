package com.jsjf.controller.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.dao.activity.BypActivityIntegralDAO;
import com.jsjf.dao.activity.BypCommodityDetailDAO;
import com.jsjf.dao.activity.BypMemberIntegralDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.integral.BypCommodityExchangeDao;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.model.activity.*;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.integral.BypCommodityExchange;
import com.jsjf.model.member.*;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.model.system.SysArticle;
import com.jsjf.service.activity.*;
import com.jsjf.service.member.*;
import com.jsjf.service.product.*;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private JsProductReservationLogService jsProductReservationLogService;
    @Autowired
    private JsCompanyAccountLogService jsCompanyAccountLogService;
    @Autowired
    private DrMemberFavourableService drMemberFavourableService;
    @Autowired
    private DrProductInfoService drProductInfoService;
    @Autowired
    private DrMemberRecommendedService drMemberRecommendedService;
    @Autowired
    private DrMemberService drMemberService;
    @Autowired
    private JsActivityProductInvestInfoService jsActivityProductInvestInfoService;
    @Autowired
    private DrActivityParameterService drActivityParameterService;
    @Autowired
    private JsActivityFriendService jsActivityFriendService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrMemberLotteryLogService drMemberLotteryLogService;
    @Autowired
    private DrProductInvestService drProductInvestService;
    @Autowired
    private JsproductPrizeService jsproductPrizeService;
    @Autowired
    private JsProductPrizeLogService jsProductPrizeLogService;
    @Autowired
    private JsActivityAggregationPageService jsActivityAggregatioPageService;
    @Autowired
    private DrMemberFundsService drMemberFundsService;
    @Autowired
    private JsActivityMemberAccountService jsActivityMemberAccountService;
    @Autowired
    private DrActivityService drActivityService;
    @Autowired
    private JsActivityLuckyMoneyService jsActivityLuckyMoneyService;
    @Autowired
    private JsActivityRewardService jsActivityRewardService;
    @Autowired
    private JsOpenDayService jsOpenDayService;
    @Autowired
    private JsOpenDayLogService jsOpenDayLogService;
    @Autowired
    private SysArticleService sysArticleService;
    @Autowired
    private JsProductPrizeOrderShareService jsProductPrizeOrderShareService;
    @Autowired
    private JsProductPrizeWishService jsProductPrizeWishService;
    @Autowired
    private JsActivityOfflineService jsActivityOfflineService;
    @Autowired
    private JsActivityHitIceLogService jsActivityHitIceLogService;
    @Autowired
    private JsGratitudeBlessingService jsGratitudeBlessingService;
    @Autowired
    private JsSignInService jsSignInService;
    @Autowired
    private BypCommodityDetailService bypCommodityDetailService;
    @Autowired
    private BypActivityIntegralDAO bypActivityIntegralMapper;
    @Autowired
    private BypCommodityService bypCommodityService;
    @Autowired
    private BypMemberIntegralDAO bypMemberIntegralMapper;
    @Autowired
    private JsMemberInfoService jsMemberInfoService;
    @Autowired
    private BypCommodityDetailDAO bypCommodityDetailDAO;
    @Autowired
    public DrChannelInfoDAO drChannelInfoDAO;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private BypCommodityExchangeDao bypCommodityExchangeDao;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    /**
     * 砸冰块
     *
     * @param req
     * @return
     */
    @RequestMapping("/hitIce")
    @ResponseBody
    public String hitIce(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        DrMember member = drMemberService.selectByPrimaryKey(uid);
        try {
            if (Utils.isObjectNotEmpty(member)) {
                DrActivity activity = drActivityService
                        .selectObjectByName("砸冰活动");
                if (Utils.isObjectNotEmpty(activity)) {
                    if (new Date().before(activity.getEndTime())
                            && new Date().after(activity.getStartTime())) {
                        br = jsActivityHitIceLogService.hitIce(member,
                                activity.getStartTime(), activity.getEndTime());
                    } else {
                        br.setErrorMsg("活动已结束");
                    }
                } else {
                    br.setErrorMsg("活动不存在");
                }
            } else {
                br.setErrorMsg("未登录");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 砸冰块活动首页
     *
     * @param req
     * @return
     */
    @RequestMapping("/activityHitIceIndex")
    @ResponseBody
    public String activityHitIceIndex(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        DrMember member = drMemberService.selectByPrimaryKey(uid);
        try {
            DrActivity activity = drActivityService.selectObjectByName("砸冰活动");
            if (Utils.isObjectNotEmpty(activity)) {
                // 初始化 用户机会
                if (Utils.isObjectNotEmpty(member) && member.getIsFuiou() == 1
                        && new Date().after(activity.getStartTime())
                        && new Date().before(activity.getEndTime())) {
                    long time1 = System.currentTimeMillis();
                    redisClientTemplate.lock("hitIceChance_" + member.getUid());

                    jsActivityHitIceLogService.insertHitIceChanceByRule(
                            member.getUid(), activity.getStartTime(),
                            activity.getEndTime());

                    long time2 = System.currentTimeMillis();

                    if ((time2 - time1) < 2500) {
                        redisClientTemplate.del("hitIceChance_"
                                + member.getUid());
                    }
                }
                // 查询数据
                br = jsActivityHitIceLogService.selectHitIceParam(member,
                        activity.getStartTime(), activity.getEndTime());
            } else {
                br.setErrorMsg("活动不存在");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 新手福利-体验金推广页 :查询 最近注册100条用户,手机号,注册时间
     *
     * @return
     */
    @RequestMapping("/lastRegMember")
    @ResponseBody
    public String lastRegMember() {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", drMemberService.lastRegMember());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * iphone7 SEM 推广
     *
     * @param req
     * @return
     */
    @RequestMapping("/iPhoneSEM")
    @ResponseBody
    public String iPhoneSEM(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            DrProductInfo dpi = drProductInfoService.selectJSProductActive();
            Map<String, Object> map = jsActivityProductInvestInfoService
                    .iPhoneCensus();
            if (Utils.isObjectEmpty(map)) {
                map = new HashMap<String, Object>();
                map.put("investCount", 0);
                map.put("prizeCount", 0);
            }
            map.put("info", dpi);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 查询产品绑定的奖品
     *
     * @param req
     * @return
     */
    @RequestMapping("/selectProductPrize")
    @ResponseBody
    public String selectProductPrize(HttpServletRequest req, Integer pid,
                                     Integer id) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("pid", pid);
            map.put("id", id);
            JsProductPrize prize = jsproductPrizeService
                    .selectJsPorudctPrize(map);
            map.clear();
            map.put("prize", prize);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 年末活动投即送
     *
     * @param req
     * @return
     */
    @RequestMapping("/investSendPrizeIndex")
    @ResponseBody
    public String investSendPrizeIndex(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            // 奖品
            List<JsProductPrize> prizeList = jsproductPrizeService
                    .selectActivityIndexJsproductPrize();
            // map.put("status", 1);//商品上架
            map.put("type", 0);// 0商品投资订单 1商品预约订单
            map.put("offset", 0);
            map.put("limit", 30);
            // 投资记录
            List<Map<String, Object>> investList = jsProductPrizeLogService
                    .selectListMap(map);
            String investCount = redisClientTemplate.get("investCount");
            if (!StringUtils.isNotEmpty(investCount)) {
                int total = jsProductPrizeLogService.selectListMapCount(map);
                redisClientTemplate.setex("investCount", 7200,
                        String.valueOf(total));
                param.put("investCount", String.valueOf(total));
            } else {
                param.put("investCount", investCount);
            }
            // 用户晒单列表
            map.clear();
            map.put("isShow", 1);
            map.put("order", " ORDER BY a.sort DESC");
            map.put("top5", 5);
            param.put("orderShareList",
                    jsProductPrizeOrderShareService.selectByMap(map));
            param.put("prizeList", prizeList);
            param.put("investList", investList);
            br.setMap(param);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双旦活动是否开启
     *
     * @param req
     * @return
     */
    @RequestMapping("/isInDoubleEggActivity")
    @ResponseBody
    public String isInDoubleEggActivity(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("isInDoubleEggActivity", false);
            if (Utils.parse(
                    redisClientTemplate.getProperties("activityIconStartDate"),
                    "yyyy-MM-dd HH:mm:ss").before(new Date())
                    && Utils.parse(
                    redisClientTemplate
                            .getProperties("activityIconEndDate"),
                    "yyyy-MM-dd HH:mm:ss").after(new Date())) {
                map.put("isInDoubleEggActivity", true);
            }
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            log.error(e.getMessage(), e);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 分享双旦活动
     *
     * @param req
     * @return
     */
    @RequestMapping("/dobuleEggShare")
    @ResponseBody
    public String dobuleEggShare(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String amount = drMemberLotteryLogService.selectGiftName(uid);
            if (!Utils.strIsNull(amount)) {
                DrMember m = drMemberService.selectByPrimaryKey(uid);
                map.clear();
                map.put("amount", amount);
                map.put("name", m.getRealName());
                map.put("phone", m.getMobilephone());
                br.setMap(map);
                br.setSuccess(true);
            } else {
                br.setErrorCode("1001");
                br.setErrorMsg("没有拆过红包");
            }
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            log.error(e.getMessage(), e);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 拆双旦红包
     *
     * @param req
     * @return
     */
    @RequestMapping("/tearOpen")
    @ResponseBody
    public String tearOpen(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        DrMember m = drMemberService.selectByPrimaryKey(uid);
        try {
            br = drMemberLotteryLogService.insertLogtteryLogDoubleEgg(m);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            log.error(e.getMessage(), e);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 邀请好友统计2
     *
     * @param req
     * @return
     */
    @RequestMapping("/myInvitation")
    @ResponseBody
    public String getActivityFriendStatisticsTwo(HttpServletRequest req,
                                                 Integer uid, Integer pageOn, Integer pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageOn", pageOn);
        param.put("pageSize", pageSize);
        BaseResult br = jsActivityFriendService.myInvitation(uid, param);
        // 好友推荐奖励未领取
        Map<String, Object> map = new HashMap<String, Object>();
        BaseResult afBr = jsActivityFriendService.getActivityFriendStatistics(
                null, uid, null, null);
        if (afBr.isSuccess()) {
            map.put("afid", afBr.getMap().get("afid"));
            map.put("unclaimed", afBr.getMap().get("unclaimed"));
        }
        ((Map<String, Object>) br.getMap()).putAll(map);
        return JSON.toJSONString(br);
    }

    /**
     * 好友邀请活动统计
     *
     * @param req
     * @return
     */
    @RequestMapping("/getActivityFriendStatistics")
    @ResponseBody
    public String getActivityFriendStatistics(HttpServletRequest req,
                                              Integer afid, Integer uid, Integer pageOn, Integer pageSize) {
        BaseResult br = jsActivityFriendService.getActivityFriendStatistics(
                afid, uid, pageOn, pageSize);
        return JSON.toJSONString(br);
    }

    /**
     * 活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping("/getActivityFriendConfigAll")
    @ResponseBody
    public String getActivityFriendConfigAll(HttpServletRequest req,
                                             Integer pageOn, Integer pageSize, Integer status) {
        BaseResult br = jsActivityFriendService.selectJsActivityFriend(pageOn,
                pageSize, status); // 邀请好有返利列表
        return JSON.toJSONString(br);
    }

    /**
     * 邀请好有返利
     *
     * @param req
     * @return
     */
    @RequestMapping("/getActivityFriendConfig")
    @ResponseBody
    public String getActivityFriendConfig(HttpServletRequest req, Integer id,
                                          Integer uid) {
        BaseResult br = jsActivityFriendService.selectNewJsActivityFriend(id,
                uid); // 邀请好有返利
        return JSON.toJSONString(br);
    }

    /**
     * 砸金蛋,随机加息券
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRandomCouponByProductId")
    @ResponseBody
    public String getRandomCouponByProductId(HttpServletRequest req,
                                             Integer id, Integer uid) {
        BaseResult br = new BaseResult();
        if (Utils.isBlank(uid)) {
            br.setErrorMsg("没有登录");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        if (Utils.isBlank(id)) {
            br.setErrorMsg("没有产品id");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        br = drActivityParameterService.getRandomCouponByProductId(uid, id);
        return JSON.toJSONString(br);
    }

    /**
     * 活动页，本期往期开奖结果
     *
     * @param req
     * @return
     */
    @RequestMapping("/getActivityPrizeResult")
    @ResponseBody
    public String getActivityPrizeResult(HttpServletRequest req,
                                         Integer pageOn, Integer pageSize) {
        return JSON.toJSONString(jsActivityProductInvestInfoService
                .getActivityPrizeResult(pageOn, pageSize));
    }

    /**
     * 活动中心-中奖记录
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getMyPrizeRecords", method = RequestMethod.POST)
    @ResponseBody
    public String jsActivityProductCenter(HttpServletRequest req, PageInfo pi,
                                          Integer prizeStatus, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();

        if (Utils.isBlank(uid)) {
            br.setErrorCode("9998");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        map.put("uid", uid);
        if (prizeStatus == null) {
            map.put("status", null);
        } else if (prizeStatus == 0) {// 未开奖
            map.put("status", new int[]{1, 2}); // 1-进行中，2-待开奖
        } else if (prizeStatus == 1) {// 已开奖
            map.put("status", new int[]{3});// 已完成
        }
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());

        br = jsActivityProductInvestInfoService.jsActivityProductCenter(map);

        return JSON.toJSONString(br);
    }

    /**
     * 产品详情页，查询中奖者
     *
     * @param req
     * @return
     */
    @RequestMapping("/getPrizeInfoByProductId")
    @ResponseBody
    public String jsActivityProductWinner(HttpServletRequest req, Integer id) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (id == null) {
                br.setErrorCode("产品id不能为null");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            map.put("pid", id);
            List<Map<String, Object>> list = jsActivityProductInvestInfoService
                    .selectJsActivityProductIsWinner(map);
            map.clear();
            map.put("list", list);
            br.setMap(map);
            br.setSuccess(true);

        } catch (Exception e) {
            log.error("查询中奖者", e);
            br.setErrorMsg("系统错误");
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public String index(HttpServletRequest req, Integer uid, Integer status,
                        PageInfo pi, Integer flag) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uid", uid);
            if (status != null) {
                if (status == 3) {
                    map.put("statuss", new Integer[]{1, 2});
                    map.put("desc", 1);
                } else {
                    map.put("status", status);
                }
            }
            Integer[] type = null;
            if (Utils.isObjectEmpty(flag)) {
                // 当flag为空的时候，默认查询红包的列表
                type = new Integer[]{1, 2, 3, 4};
            } else if (flag == 1) {
                // 查红包
                type = new Integer[]{1, 2, 4};
            } else if (flag == 2) {
                // 查返现券
                type = new Integer[]{1};
            } else if (flag == 3) {
                // 查加息券
                type = new Integer[]{2};
            } else if (flag == 4) {
                // 查翻倍券
                type = new Integer[]{4};
            } else if (flag == 0) {
                // 查体验金
                map.put("desc", 2);
                type = new Integer[]{3};
            }
            map.put("type", type);
            List<DrMemberFavourable> list = drMemberFavourableService
                    .selectByParam(map);
            BigDecimal amountSum = drMemberFavourableService
                    .selectByParamSum(map);
            map.clear();
            map.put("type", 1);
            map.put("status", 5);
            List<DrProductInfo> productInfoList = drProductInfoService
                    .selectProductInfo(map);
            map.clear();
            map.put("newHandId", productInfoList.size() > 0 ? productInfoList
                    .get(0).getId() : 1379);

            map.put("amountSum", amountSum);
            map.put("list", list);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 使用红包
     *
     * @param req
     * @param pid
     * @param uid
     * @return 日志: 1.2016-11-23 ,安卓, 红包查询,最低使用金额升序
     */
    @RequestMapping(value = "/usable", method = RequestMethod.POST)
    @ResponseBody
    public String usable(HttpServletRequest req, Integer pid, Integer uid) {
        BaseResult br = new BaseResult();
        if (Utils.isBlank(uid)) {
            br.setSuccess(false);
            br.setErrorMsg("uid不能为空");
            return JSON.toJSONString(br);
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            DrProductInfo info = drProductInfoService
                    .selectProductByPrimaryKey(pid);
            List<DrMemberFavourable> list = null;
            if (info.getAtid() == null) { // 活动标不能使用券
                param.put("status", 0);
                param.put("uid", uid);
                param.put("deadline", info.getDeadline());
                if (info.getType() == 1) {// 新手标
                    param.put("type", new Integer[]{3});
                } else {
                    Integer[] types = new Integer[3];
                    if (info.getIsCash() == 1) {
                        types[1] = 1;
                    }
                    if (info.getIsInterest() == 1) {
                        types[0] = 2;
                    }
                    if (info.getIsDouble() == 1) {
                        Integer version = Integer.parseInt(req
                                .getParameter("version").replace(".", "")
                                .toString());
                        if (version >= 112) {
                            types[2] = 4;
                        }
                    }
                    param.put("type", types);

                }
                if (info.getIsCash() == 0 && info.getIsInterest() == 0
                        && info.getIsDouble() == 0 && info.getType() != 1) {
                    list = new ArrayList<DrMemberFavourable>();
                } else {

                    list = drMemberFavourableService.selectByParam(param);
                    // 砸金蛋排除非本产品的加息券
                    Iterator<DrMemberFavourable> iter = list.iterator();
                    while (iter.hasNext()) {
                        DrMemberFavourable b = iter.next();
                        if (!Utils.isBlank(b.getPid())
                                && b.getPid().intValue() != info.getId()
                                .intValue()) {
                            iter.remove();
                        }
                    }
                }
            } else {
                list = new ArrayList<DrMemberFavourable>();
            }
            param.clear();
            param.put("list", list);
            br.setMap(param);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("我的优惠券查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);

    }

    /**
     * 根据用户UID获取用户推荐好友列表
     */
    @RequestMapping(value = "/myRecommend", method = RequestMethod.POST)
    @ResponseBody
    public String myFrieds(
            HttpServletRequest req,
            Integer uid,
            @RequestParam(value = "order", defaultValue = "0", required = false) Integer order) {
        BaseResult br = new BaseResult();
        PageInfo pi = new PageInfo();
        BigDecimal rewards = BigDecimal.ZERO;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("referrerId", uid);
            if (order == 1) {// 投资状态（未投资）
                map.put("order", " order by dmr.investTime ");
            } else if (order == 2) {
                map.put("order", " order by dmr.investTime desc ");
            } else if (order == 3) {
                map.put("order", " order by dmr.referrerReward ");
            } else if (order == 4) {
                map.put("order", " order by dmr.referrerReward desc ");
            }
            pi = drMemberRecommendedService.getDrMemberRecommended(map);
            rewards = drMemberRecommendedService.getRewardByReferrerId(uid);
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            map.clear();
            map.put("list", pi.getRows());
            map.put("total", pi.getRows().size());
            map.put("investTotal", pi.getTotal());
            map.put("rewards", rewards == null ? 0 : rewards);
            map.put("recommCodes", m.getRecommCodes());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("我的好友查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 根据推荐码获取脱敏手机号
     *
     * @param req
     * @param recommCode
     * @return
     */
    @RequestMapping(value = "/getMobilePhoneByRecommCode")
    @ResponseBody
    public String getMobilePhoneByRecommCode(HttpServletRequest req,
                                             String recommCode) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (recommCode != null && !"".equals(recommCode)) {
                String mobilePhone = drMemberService
                        .selectMobilePhoneByRecommCode(recommCode);
                if (null != mobilePhone) {
                    map.put("mobilePhone", mobilePhone);
                    br.setSuccess(true);
                } else {
                    br.setErrorCode("1001");
                    br.setErrorMsg("推荐码无效");
                }

            } else {
                br.setErrorCode("1001");
                br.setErrorMsg("推荐码无效");
            }
            br.setMap(map);
        } catch (Exception e) {
            log.error("根据推荐码获取脱敏手机号", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/isExperience", method = RequestMethod.POST)
    @ResponseBody
    public String isExperience(HttpServletRequest req, String toFrom,
                               Integer channel) {
        BaseResult br = new BaseResult();
        try {
            if (channel == null || channel != 2) {
                br.setSuccess(false);
                br.setErrorCode("1002");
                br.setErrorMsg("channel参数错误");
                return JSON.toJSONString(br);
            }
            if (toFrom.equals(redisClientTemplate.getProperties("yingyongbao"))) {
                br.setSuccess(true);
            } else {
                br.setSuccess(false);
                br.setErrorMsg("渠道参数有误");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error("isExperience", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双蛋活动首页
     *
     * @param req
     * @return
     */
    @RequestMapping("/doubleAggIndex")
    @ResponseBody
    public String doubleAggIndex(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<DrProductInfo> list = new ArrayList<DrProductInfo>();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            map.put("deadline", new Integer[]{60, 180});
            map.put("status", 5);
            map.put("type", 2);
            list = drProductInfoService.doubleEggList(map);
            br.setSuccess(true);
            map.clear();
            if (Utils.isObjectEmpty(uid)) {
                result.put("pullCount", 0);
                result.put("isOldUser", false);
            } else {
                map.clear();
                map.put("uid", uid);
                int count = drMemberLotteryLogService
                        .getDoubleAGGLotteryCountByUid(map);
                map.clear();
                map.put("uid", uid);
                map.put("type", 2);
                result.put("pullCount", count);
                result.put("isOldUser",
                        drProductInvestService.checkProductType(map));
            }
            result.put("luckyList",
                    drMemberLotteryLogService.getDoubleAGGListCountByUid());
            result.put("productList", list);
            result.put("activity_60",
                    redisClientTemplate.getProperties("activity_60"));
            result.put("activity_180",
                    redisClientTemplate.getProperties("activity_180"));
            br.setMap(result);
        } catch (Exception e) {
            log.error("双蛋活动首页", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 投即送账户中心投资记录
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/accountInvestLogs", method = RequestMethod.POST)
    @ResponseBody
    public String accountInvestLogs(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            param.put("uid", uid);
            br = jsProductPrizeLogService.selectPrizeLogByUid(param);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("投即送账户中心投资记录", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 添加预约
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/insertPrizeInfo", method = RequestMethod.POST)
    @ResponseBody
    public String insertPrizeInfo(HttpServletRequest req, Integer ppid,
                                  Integer uid) {
        BaseResult br = new BaseResult();
        if (Utils.isObjectEmpty(ppid)) {
            br.setErrorMsg("ppid不能为空");
            br.setSuccess(false);
            br.setErrorCode("1001");
            return JSON.toJSONString(br);
        }
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            JsProductPrizeLog jsProductPrizeLog = new JsProductPrizeLog();
            jsProductPrizeLog.setUid(uid);
            jsProductPrizeLog.setPpid(ppid);
            jsProductPrizeLog.setType(1);
            jsProductPrizeLogService.insert(jsProductPrizeLog);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("添加预约", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 查询活动聚合页list
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getAllActivity", method = RequestMethod.POST)
    @ResponseBody
    public String getAllActivity(HttpServletRequest req, Integer status,
                                 PageInfo pi) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(req.getParameter("channel"))) {
                br.setSuccess(false);
                br.setErrorCode("9999");
                br.setErrorMsg("channel不能为空");
                return JSON.toJSONString(br);
            }
            if (Utils.isObjectNotEmpty(status)) {
                map.put("status", status);
            }
            map.put("channel", req.getParameter("channel"));
            pi = jsActivityAggregatioPageService
                    .selectJsActivityAggregationPageList(map, pi);
            map.clear();
            map.put("Page", pi);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("查询活动聚合页list", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 长城宽带用户登录获取账户余额及红包
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getGreatWallInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getGreatWallInfo(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            map.put("isRealverify", drMemberService.selectByPrimaryKey(uid)
                    .getRealVerify() == 1 ? 1 : 0);
            map.put("redCount",
                    drMemberFavourableService.selectRedCountByUid(uid));
            map.put("balance",
                    drMemberFundsService.selectDrMemberFundsByUid(uid)
                            .getBalance());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人脉王排行榜top5
     */
    @RequestMapping(value = "/getTopFive", method = RequestMethod.POST)
    @ResponseBody
    public String getTopFive(HttpServletRequest req, Integer afid) {
        BaseResult br = new BaseResult();
        /*
         * if(Utils.isObjectEmpty(afid)){ br.setErrorCode("10002");
		 * br.setErrorMsg("afid不能为空"); br.setSuccess(false); return
		 * JSON.toJSONString(br); }
		 */
        br = jsActivityMemberAccountService.selectFriendAmountTopFive(afid);
        return JSON.toJSONString(br);
    }

    /**
     * 金鸡送福 来领压岁钱 status 0 :未开启 1： 已开启，2 已结束
     */
    @RequestMapping(value = "/luckyMoneyIndex", method = RequestMethod.POST)
    @ResponseBody
    public String luckyMoneyIndex(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            // 查询剩余未领取的压岁钱
            param.put("shaerUid", uid);
            param.put("uid", -1);
            param.put("time", new Date());
            List<JsActivityLuckyMoney> unluckList = jsActivityLuckyMoneyService
                    .selectJsActivityLuckyMoneyByMap(param);
            // 是否分享过
            param.clear();
            param.put("shaerUid", uid);
            param.put("time", new Date());
            List<JsActivityLuckyMoney> luckList = jsActivityLuckyMoneyService
                    .selectJsActivityLuckyMoneyByMap(param);
            if (Utils.isObjectNotEmpty(m)) {
                map.put("realverify", m.getRealVerify());// 是否绑卡
                map.put("isShare", luckList.size() > 0 ? true : false);// 是否分享过
                map.put("isOverCount", unluckList.size() == 0 ? true : false);// 是否已抢完.
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
            Date nowTime = dateFormat.parse(Utils.format(new Date(),
                    "yyyy-M-d H:m:s"));
            param.clear();
            param.put("nowTime", nowTime);
            List<DrActivity> isStart = drActivityService.selectByTime(param);
            map.put("isStartList", isStart);
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 领取压岁钱
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getLuckyMoney", method = RequestMethod.POST)
    @ResponseBody
    public String getLuckyMoney(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            br = jsActivityLuckyMoneyService.insert(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 吃元宵
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/eatGlutinous", method = RequestMethod.POST)
    @ResponseBody
    public String eatGlutinous(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        DrMember m = drMemberService.selectByPrimaryKey(uid);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("找不到id为" + uid + "用户");
            br.setSuccess(false);
            br.setErrorCode("10001");
            return JSON.toJSONString(br);
        }
        try {
            String glutinousStartDate = redisClientTemplate
                    .getProperties("glutinousStartDate");
            String glutinousEndDate = redisClientTemplate
                    .getProperties("glutinousEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(glutinousStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(glutinousEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                // 登陆的状态下查询该用户当天是否领取过
                map.put("uid", uid);
                map.put("aid", 4);// 4 元宵活动id
                map.put("addtime", nowDate);
                boolean isReceive = drMemberLotteryLogService
                        .selectListByParam(map);
                if (isReceive) {
                    // 领取过的情况下直接返回
                    map.clear();
                    map.put("isReceive", isReceive);// 是否领取
                    br.setSuccess(false);
                    br.setMap(map);
                    return JSON.toJSONString(br);
                }
                // 未领取的情况
                map.clear();
                map.put("uid", uid);
                map.put("atid", 4);// 4 元宵活动id
                map.put("classes", 1);// 1是虚拟2是实物
                br = jsActivityRewardService.getJsActivityRewardByAid(map);
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双旦杰活动抽奖次数查询 (改版捕鱼达人)
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getLotteryNum", method = RequestMethod.POST)
    @ResponseBody
    public String getLotteryNum(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean convert=false;
        String convertvalue = "";
        try {
            convertvalue=String.valueOf(System.currentTimeMillis());
            if (Utils.isObjectEmpty(uid)) {
                br.setErrorMsg("未登录");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                String glutinousStartDate = redisClientTemplate
                        .getProperties("glutinousStartDate");
                String glutinousEndDate = redisClientTemplate
                        .getProperties("glutinousEndDate");
                Date nowDate = new Date();
                map.put("uid", uid);
                map.put("startDate", Utils.format(glutinousStartDate, "yyyy-MM-dd"));
                map.put("endDate", Utils.format(glutinousEndDate, "yyyy-MM-dd"));
                map.put("aid", redisClientTemplate.getProperties("activityId"));
                convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + uid, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                if(convert){
                    Integer con = drMemberLotteryLogService.getDoubleAGGOneLottery(map);
                    if (nowDate.after(Utils.parseDate(glutinousStartDate,
                            "yyyy-MM-dd HH:mm:ss"))
                            && nowDate.before(Utils.parseDate(glutinousEndDate,
                            "yyyy-MM-dd HH:mm:ss"))) {
                        if (con <= 0) {
                            DrMemberLotteryLog drm = new DrMemberLotteryLog(Integer.parseInt(redisClientTemplate.getProperties("activityId")), uid,
                                    new Date(), null);
                            // 插入一次抽奖机会
                            drMemberLotteryLogService.insertLogtteryLog(drm);
                            ;
                            // 给前端返回值是1
                            map.clear();
                            map.put("num", 1);
                            map.put("uid", uid);
                            br.setMap(map);
                            br.setSuccess(true);
                        } else {
                            map.clear();
                            map.put("uid", uid);
                            int count = drMemberLotteryLogService
                                    .getDoubleAGGLotteryCountByUid(map);
                            if (count >= 1) {
                                map.put("num", count);
                                br.setMap(map);
                                br.setSuccess(true);
                            } else {
                                map.put("num", 0);
                                br.setMap(map);
                                br.setSuccess(true);
                            }
                        }
                    } else {
                        br.setErrorMsg("活动已过期");
                        br.setSuccess(false);
                        br.setErrorCode("9997");
                        return JSON.toJSONString(br);
                    }
                }else {
                    br.setErrorMsg("刷新过快");
                    log.error(req);
                    return JSON.toJSONString(br);
                }
            } else {
                map.put("num", 0);
                br.setMap(map);
                br.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            br.setErrorMsg("系统错误");
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(req);
            return JSON.toJSONString(br);
        }finally {
            if(convert){
                redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + uid, convertvalue);//释放枷锁
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 2017双旦节日活动
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/againDoubleegg", method = RequestMethod.POST)
    @ResponseBody
    public String againEatGlutinous(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(uid)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        DrMember m = drMemberService.selectByPrimaryKey(uid);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("找不到id为" + uid + "用户");
            br.setSuccess(false);
            br.setErrorCode("10001");
            return JSON.toJSONString(br);
        }
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            String glutinousStartDate = redisClientTemplate
                    .getProperties("glutinousStartDate");
            String glutinousEndDate = redisClientTemplate
                    .getProperties("glutinousEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(glutinousStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(glutinousEndDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && map.get("isCps").equals(0)) {
                // 这里限制客户抽奖次数先查询客户拥有的次数内部需要-- 扣除抽奖次数
                map.clear();
                map.put("uid", uid);
                int count = drMemberLotteryLogService
                        .getDoubleAGGLotteryCountByUid(map);
                if (count > 0) {
                    map.clear();
                    map.put("uid", uid);
                    map.put("atid", redisClientTemplate.getProperties("activityId"));// 4 元宵活动id 5捕鱼da人
                    map.put("classes", 1);// 1是虚拟2是实物
                    br = jsActivityRewardService.againDoubleeggRewardByAid(map);
                } else {
                    br.setErrorCode("10008");
                    br.setMsg("错误抽奖次数用光了");
                    br.setSuccess(false);
                }
            } else {
                br.setErrorMsg("活动已过期,或者用户是CPS用户");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e1) {
            log.info("双旦节日活动e1", e1);
            br.setErrorMsg("系统异常");
            br.setSuccess(false);
            br.setErrorCode("9999");
            e1.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 2017双旦节日活动 查看我的中奖纪录
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryAward", method = RequestMethod.POST)
    @ResponseBody
    public String queryAward(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setErrorMsg("uid不能为空");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<DrMemberLotteryLog> selectRecords = drMemberLotteryLogService
                    .selectRecords(uid);
            map.put("selectRecords", selectRecords);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.info("双旦节日活动 查看我的中奖纪录", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 吃元宵领取记录
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEatGlutinousLog", method = RequestMethod.POST)
    @ResponseBody
    public String getEatGlutinousLog(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DrMemberLotteryLog> drMemberLotteryLogList = drMemberLotteryLogService
                    .selectLotteryLogByAid();
            map.put("drMemberLotteryLogList", drMemberLotteryLogList);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.info("吃元宵纪录", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 查看双旦兑换top10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getIntegralLog", method = RequestMethod.POST)
    @ResponseBody
    public String getIntegralLog(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String doubleDanStartDate = redisClientTemplate
                    .getProperties("doubleDanStartDate");
            String doubleDanEndDate = redisClientTemplate
                    .getProperties("doubleDanEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(doubleDanStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(doubleDanEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                List<BypCommodityDetail> list = bypCommodityDetailService.selectTopIntegralLog();
                map.put("selectTopIntegralLog", list);
                br.setMap(map);
                br.setSuccess(true);
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.info("兑换top10", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }
    /**
     * 查看我的双旦兑换
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getMyIntegralLog", method = RequestMethod.POST)
    @ResponseBody
	public String getMyIntegralLog(HttpServletRequest req,Integer uid) {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<BypCommodityDetail> list=bypCommodityDetailService.selectMyIntegralLog(uid);
			map.put("selectMyIntegralLog", list);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.info("吃元宵纪录", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return JSON.toJSONString(br);
	}

    /**
     * 获取捕鱼达人活动奖品列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/getFishingProduct", method = RequestMethod.POST)
    @ResponseBody
    public String getFishingProduct(HttpServletRequest req){
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("activity_id","by");//捕鱼活动
            br = jsActivityRewardService.getFishingProduct(map);
        } catch (Exception e) {
            log.error("获取用户拥有的积分", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }
    /**
     * 双旦实物兑换获取用户拥有的积分
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getIntegralByUser", method = RequestMethod.POST)
    @ResponseBody
    public String getIntegralByUser(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setErrorMsg("uid不能为空");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            String doubleDanStartDate = redisClientTemplate
                    .getProperties("doubleDanStartDate");
            String doubleDanEndDate = redisClientTemplate
                    .getProperties("doubleDanEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(doubleDanStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(doubleDanEndDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && map.get("isCps").equals(0)) {
                br = jsActivityRewardService.getIntegralByUser(uid);
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error("获取用户拥有的积分", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双旦实物兑换
     *
     * @param req
     * @param uid 用户ID
     * @param pid 兑换的奖品ID
     * @return
     */
    @RequestMapping(value = "/insertConvertByUser", method = RequestMethod.POST)
    @ResponseBody
    public String insertConvertByUser(HttpServletRequest req, Integer uid,
                                      String pid) {
        BaseResult br = new BaseResult();
        br = bypCommodityDetailService.insertConvertByUserAndPid(uid, pid);
        return JSON.toJSONString(br);
    }

    /**
     * 新手标宣传页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getNoviceListAndCount", method = RequestMethod.POST)
    @ResponseBody
    public String getNoviceListAndCount(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            br = drProductInvestService.selectNoviceListAndCount();
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 投资获取可用优惠券
     *
     * @param req
     * @return
     * @version app2.0
     */
    @RequestMapping(value = "/getUsableCoupon", method = RequestMethod.POST)
    @ResponseBody
    public String getUsableCoupon(HttpServletRequest req, Integer uid,
                                  Integer pid, BigDecimal amount) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            DrProductInfo info = drProductInfoService
                    .selectProductByPrimaryKey(pid);
            List<DrMemberFavourable> list = null;
            // 活动标不能使用券
            if (info.getAtid() == null) {
                param.put("uid", uid);
                param.put("status", 0);
                param.put("deadline", info.getDeadline());
                // 新手标
                if (info.getType() == 1) {
                    param.put("type", new Integer[]{3});
                } else {
                    Integer[] types = new Integer[3];
                    if (info.getIsCash() == 1) {
                        types[1] = 1;
                    }
                    if (info.getIsInterest() == 1) {
                        types[0] = 2;
                    }
                    if (info.getIsDouble() == 1) {
                        types[2] = 4;
                    }
                    param.put("type", types);
                }
                if (info.getIsCash() == 0 && info.getIsInterest() == 0
                        && info.getIsDouble() == 0 && info.getType() != 1) {
                    list = new ArrayList<DrMemberFavourable>();
                } else {
                    list = drMemberFavourableService.selectByParam(param);
                    // 砸金蛋排除非本产品的加息券
                    Iterator<DrMemberFavourable> iter = list.iterator();
                    List<DrMemberFavourable> list2 = new ArrayList<DrMemberFavourable>();
                    while (iter.hasNext()) {
                        DrMemberFavourable b = iter.next();
                        if (!Utils.isBlank(b.getPid())
                                && b.getPid().intValue() != info.getId()
                                .intValue()) {
                            iter.remove();
                        } else {
                            // status = 3 表示红包不可用
                            if (amount.compareTo(b.getEnableAmount()) == -1) {
                                b.setStatus(3);
                                list2.add(b);
                                iter.remove();
                            } else {
                                // 计算预计收益
                                computerAmount(info, b, amount);
                            }
                        }
                    }
                    // 根据收益排序
                    sort(list);
                    list.addAll(list2);
                }
            } else {
                list = new ArrayList<DrMemberFavourable>();
            }
            param.clear();
            param.put("list", list);
            br.setMap(param);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    private DrMemberFavourable computerAmount(DrProductInfo info,
                                              DrMemberFavourable favourable, BigDecimal amount) {
        BigDecimal EstimateProfitAmount = new BigDecimal(0);
        // 返现券
        if (favourable.getType() == 1) {
            EstimateProfitAmount = amount
                    .multiply(
                            info.getRate()
                                    .divide(new BigDecimal(100), 10,
                                            RoundingMode.HALF_UP)
                                    .divide(new BigDecimal(360), 10,
                                            RoundingMode.HALF_UP))
                    .multiply(new BigDecimal(info.getDeadline()))
                    .add(favourable.getAmount());
        }
        // 加息券
        else if (favourable.getType() == 2) {
            EstimateProfitAmount = amount.multiply(
                    (info.getRate().add(favourable.getRaisedRates())).divide(
                            new BigDecimal(100), 10, RoundingMode.HALF_UP)
                            .divide(new BigDecimal(360), 10,
                                    RoundingMode.HALF_UP)).multiply(
                    new BigDecimal(info.getDeadline()));
        }
        // 翻倍券
        else if (favourable.getType() == 4) {
            EstimateProfitAmount = amount.multiply(
                    info.getRate()
                            .multiply(favourable.getMultiple())
                            .divide(new BigDecimal(100), 10,
                                    RoundingMode.HALF_UP)
                            .divide(new BigDecimal(360), 10,
                                    RoundingMode.HALF_UP)).multiply(
                    new BigDecimal(info.getDeadline()));
        }
        favourable.setEstimateProfitAmount(EstimateProfitAmount);
        return favourable;
    }

    private List<DrMemberFavourable> sort(List<DrMemberFavourable> list) {
        Collections.sort(list, new Comparator<DrMemberFavourable>() {
            @Override
            public int compare(DrMemberFavourable o1, DrMemberFavourable o2) {
                // 按收益倒序
                if (o1.getEstimateProfitAmount() != null
                        && o2.getEstimateProfitAmount() != null) {
                    if (o1.getEstimateProfitAmount().compareTo(
                            o2.getEstimateProfitAmount()) == -1) {
                        return 1;
                    }
                    if (o1.getEstimateProfitAmount().compareTo(
                            o2.getEstimateProfitAmount()) == 0) {
                        return 0;
                    }
                }
                return -1;
            }
        });
        return list;
    }

    /**
     * 我要报名
     *
     * @param req
     * @param uid
     * @param openDayId
     * @param userName
     * @param sex
     * @param city
     * @return
     */
    @RequestMapping(value = "/SignUp", method = RequestMethod.POST)
    @ResponseBody
    public String SignUp(HttpServletRequest req, Integer uid,
                         Integer openDayId, String userName, Integer sex, String city) {
        BaseResult outPut = new BaseResult();
        JsOpenDayLog jsOpenDayLog = new JsOpenDayLog();
        try {
            if (Utils.isObjectEmpty(uid) || Utils.isObjectEmpty(openDayId)
                    || Utils.isObjectEmpty(userName)
                    || Utils.isObjectEmpty(sex) || Utils.isObjectEmpty(city)) {
                outPut.setErrorMsg("参数有误");
                outPut.setSuccess(false);
                outPut.setErrorCode("9998");
                return JSON.toJSONString(outPut);
            }
            jsOpenDayLog.setUid(uid);
            jsOpenDayLog.setOpenDayId(openDayId);
            jsOpenDayLog.setUserName(userName);
            jsOpenDayLog.setSex(sex);
            jsOpenDayLog.setCity(city);
            jsOpenDayLogService.insert(jsOpenDayLog);
            outPut.setSuccess(true);
        } catch (Exception e) {
            outPut.setSuccess(false);
            outPut.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(outPut);
    }

    /**
     * 开放日列表
     */
    @RequestMapping(value = "/getOpenDayList", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenDayList(HttpServletRequest req, Integer status,
                                 PageInfo pi) {
        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            param.put("status", status);
            br = jsOpenDayService.selectJsOpenDayByParam(param, pi);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 开放日详情
     */
    @RequestMapping(value = "/getOpenDayDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenDayDetail(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JsSpecial jsSpecial = jsOpenDayService.selectJsSpecial();
            map.put("jsSpecial", jsSpecial);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 开放日文章详情
     */
    @RequestMapping(value = "/getOpenDayArticleDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenDayArticleDetail(HttpServletRequest req,
                                          Integer openDayId) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(openDayId)) {
                br.setErrorMsg("活动id不能为空");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            map.put("openDayId", openDayId);
            List<SysArticle> sysArticle = sysArticleService
                    .getArticleByParam(map);
            map.clear();
            map.put("sysArticle", sysArticle.size() > 0 ? sysArticle.get(0)
                    : null);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 三重礼，我的返现首投、复投列表
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/firstInvestList", method = RequestMethod.POST)
    @ResponseBody
    public String firstInvestList(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setErrorMsg("uid不能为空");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            br = jsActivityFriendService.firstInvestList(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 三重礼top10排行榜
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getRankingList", method = RequestMethod.POST)
    @ResponseBody
    public String getRankingList(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        byte[] threePresent = null;
        List<Map<String, Object>> top = new ArrayList<Map<String, Object>>();
        try {
            threePresent = redisClientTemplate
                    .get("threePresentMap".getBytes());
            Map<String, Object> threePresentMap = (Map<String, Object>) SerializeUtil
                    .unserialize(threePresent);
            if (threePresentMap != null) {
                top = (List<Map<String, Object>>) threePresentMap.get("top");
            }
            map.put("offset", 0);
            map.put("limit", 1);
            map.put("type", 1);
            map.clear();
            map.put("top", top);
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取账户信息
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getMyAccount", method = RequestMethod.POST)
    @ResponseBody
    public String getMyAccount(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        if (Utils.isObjectEmpty(uid)) {
            br.setSuccess(false);
            br.setErrorMsg("id不能为空");
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            param.put("status", 0);
            param.put("uid", uid);
            param.put("type", new Integer[]{1, 2, 4, 5});
            List<DrMemberFavourable> favourableList = drMemberFavourableService
                    .selectByParam(param);
            DrMemberFunds drMemberFunds = drMemberFundsService
                    .selectDrMemberFundsByUid(uid);
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            map.put("favourableCount", favourableList.size());
            map.put("balance", drMemberFunds.getFuiou_balance());
            map.put("mobilePhone", m.getMobilephone());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 翻牌活动首页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/flopIndex", method = RequestMethod.POST)
    @ResponseBody
    public String flopIndex(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            br = drActivityService.flopIndex();
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 翻牌
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/flop", method = RequestMethod.POST)
    @ResponseBody
    public String flop(HttpServletRequest req, Integer uid, String version,
                       Integer channel) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uid", uid);
            map.put("version", version);
            map.put("channel", channel);
            br = drActivityService.flop(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 翻牌分享
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/flopShare", method = RequestMethod.POST)
    @ResponseBody
    public String flopShare(HttpServletRequest req, Integer uid, Integer aid) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uid", uid);
            br = drActivityService.flopShare(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 新许愿
     *
     * @param req
     * @param uid
     * @param remarks
     * @return
     */
    @RequestMapping(value = "/newWishCommit", method = RequestMethod.POST)
    @ResponseBody
    public String newWishCommit(HttpServletRequest req, Integer uid, String remarks) {
        BaseResult br = new BaseResult();
        try {
            JsProductPrizeWish prizeWish = new JsProductPrizeWish(uid, remarks);
            br = jsProductPrizeWishService.newInsertPrizeWish(prizeWish);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 0元购top10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/zeroTopTen", method = RequestMethod.POST)
    @ResponseBody
    public String zeroPurchase(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            br = jsProductReservationLogService.getTopTen();
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取0元购产品列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getZeroBuy", method = RequestMethod.POST)
    @ResponseBody
    public String getZeroBuy(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            br = drProductInfoService.getZeroBuy();
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/getMyZeroBuy", method = RequestMethod.POST)
    @ResponseBody
    public String getMyZeroBuy(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsProductReservationLogService.getMyZeroBuy(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取许愿内容
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getWish", method = RequestMethod.POST)
    @ResponseBody
    public String getWish(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsProductPrizeWishService.selectWish(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 心愿提交
     *
     * @param req
     * @param uid
     * @param url
     * @param remarks
     * @return
     */
    @RequestMapping(value = "/wishCommit", method = RequestMethod.POST)
    @ResponseBody
    public String wishCommit(HttpServletRequest req, Integer uid, String url,
                             String remarks) {
        BaseResult br = new BaseResult();
        try {
            JsProductPrizeWish prizeWish = new JsProductPrizeWish(uid, url,
                    remarks);
            br = jsProductPrizeWishService.insertPrizeWish(prizeWish);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 端午活动首页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/dragonBoat", method = RequestMethod.POST)
    @ResponseBody
    public String dragonBoat(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            br = drProductInfoService.selectProductInfoByDragonBoat();
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping("/activityMay18d")
    @ResponseBody
    public String activityMay18d(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            DrMember member = null;
            if (!Utils.isBlank(uid))
                member = drMemberService.selectByPrimaryKey(uid);

            br = drProductInvestService.getActivityMay18d(member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 公益活动list
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/offlineActivityList", method = RequestMethod.POST)
    @ResponseBody
    public String offlineActivityList(HttpServletRequest req, PageInfo pi) {
        BaseResult br = new BaseResult();
        try {
            br = jsActivityOfflineService.selectJsActivityOfflineListByMap(pi);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 公益活动详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/offlineActivityDetail", method = RequestMethod.POST)
    @ResponseBody
    public String offlineActivityDetail(HttpServletRequest req, Integer id) {
        BaseResult br = new BaseResult();
        try {
            br = jsActivityOfflineService.selectJsActivityOfflineDetailById(id);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 查询注册送的体验金和红包是否激活
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getCouponIsActivation", method = RequestMethod.POST)
    @ResponseBody
    public String getCouponIsActivation(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean goldIsActivation = false;
        boolean redIsActivation = false;
        try {
            if (Utils.isObjectNotEmpty(uid)) {
                goldIsActivation = drMemberFavourableService
                        .selectIsShowCountByUid(uid) > 0 ? false : true;
            }
            if (Utils.isObjectNotEmpty(uid)) {
                redIsActivation = drMemberFavourableService
                        .getMemberFavourableTotal(uid) > 0 ? false : true;
            }
            map.put("goldIsActivation", goldIsActivation);
            map.put("redIsActivation", redIsActivation);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 投即送添加晒单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/insertOrderShare", method = RequestMethod.POST)
    @ResponseBody
    public String insertOrderShare(HttpServletRequest req, Integer uid,
                                   String describe, MultipartFile file) {
        BaseResult br = new BaseResult();
        try {
            JsProductPrizeOrderShare orderShare = new JsProductPrizeOrderShare();
            orderShare.setUid(uid);
            orderShare.setDescribes(Utils.stripHtmlLabel(describe));
            br = jsProductPrizeOrderShareService.insert(orderShare, file);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 往期晒单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/pastOrderShareList", method = RequestMethod.POST)
    @ResponseBody
    public String pastOrderShareList(HttpServletRequest req, PageInfo info,
                                     Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsProductPrizeOrderShareService.selectOrderShareByMap(info,
                    uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 添加祝福语
     */
    @RequestMapping(value = "/insertJsGratitudeBlessing", method = RequestMethod.POST)
    @ResponseBody
    public String insertJsGratitudeBlessing(HttpServletRequest req,
                                            Integer uid, String blessing) {
        BaseResult br = new BaseResult();
        try {
            DrActivity activity = drActivityService.selectObjectByName("感恩回馈");
            if (Utils.isObjectNotEmpty(activity)) {
                if (new Date().after(activity.getStartTime())
                        && new Date().before(activity.getEndTime())) {
                    if (Utils.isObjectEmpty(uid)) {
                        br.setSuccess(false);
                        br.setErrorCode("1001");
                        br.setErrorMsg("uid不能为空");
                        return JSON.toJSONString(br);
                    }
                    if (Utils.isObjectEmpty(blessing)) {
                        br.setSuccess(false);
                        br.setErrorCode("1002");
                        br.setErrorMsg("祝福语不能为空");
                        return JSON.toJSONString(br);
                    }
                    JsGratitudeBlessing vo = jsGratitudeBlessingService
                            .selectGratitudeBlessingByUid(uid);
                    if (Utils.isObjectNotEmpty(vo) && vo.getStatus() != null) {
                        br.setSuccess(false);
                        br.setErrorCode("1002");
                        br.setErrorMsg("祝福语不能重复提交");
                        return JSON.toJSONString(br);
                    }
                    if (Utils.isObjectNotEmpty(vo) && vo.getStatus() == null) {
                        vo.setBlessing(blessing);
                        vo.setStatus(0);
                        vo.setAddtime(new Date());
                        br = jsGratitudeBlessingService
                                .updateGratitudeBlessing(vo);
                    } else {
                        JsGratitudeBlessing jsGratitudeBlessing = new JsGratitudeBlessing();
                        jsGratitudeBlessing.setBlessing(blessing);
                        jsGratitudeBlessing.setStatus(0);
                        jsGratitudeBlessing.setUid(uid);
                        jsGratitudeBlessing.setSplit(0);
                        br = jsGratitudeBlessingService
                                .insertGratitudeBlessing(jsGratitudeBlessing);
                    }
                } else {
                    br.setErrorMsg("当前时间不在活动时间范围");
                }
            } else {
                br.setErrorMsg("活动不存在");
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 祝福语主页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/jsGratitudeBlessingHome", method = RequestMethod.POST)
    @ResponseBody
    public String jsGratitudeBlessingHome(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<>();
            List<JsGratitudeBlessing> list = jsGratitudeBlessingService
                    .selectGratitudeBlessing();
            List<DrActivityParameter> aclist = jsGratitudeBlessingService
                    .selectGratitudeBlessingFavourable();// 优惠券列表
            if (Utils.isObjectNotEmpty(uid)) {
                JsGratitudeBlessing vo = jsGratitudeBlessingService
                        .selectGratitudeBlessingByUid(uid);
                DrMemberFunds funds = drMemberFundsService
                        .selectDrMemberFundsByUid(uid);
                DrMember m = drMemberService.selectByPrimaryKey(uid);
                Map<String, Object> result = drProductInvestService
                        .selectInvestFirstByUid(uid);
                if (Utils.isObjectNotEmpty(vo)) {
                    map.put("blessing", vo.getBlessing());
                    map.put("status",
                            vo.getStatus() == null ? 3 : vo.getStatus());// 祝福语是否提交
                    map.put("isSplit", vo.getSplit());// 0:未拆 1:已拆
                } else {
                    map.put("isSplit", 0);// 0:未拆 1:已拆
                    map.put("status", 3);// 3:没提交祝福语
                }
                // 已收投资收益+推广收益
                map.put("regDate", Utils.format(m.getRegDate(), "yyyy年MM月dd日"));// 注册时间
                if (result != null) {
                    map.put("investDate", Utils.format(Utils.format(
                            result.get("investTime").toString(), "yyyy-MM-dd"),
                            "yyyy年MM月dd日"));// 首投时间
                    map.put("investAmount", result.get("amount"));// 首投金额
                    map.put("profit",
                            funds.getInvestProfit()
                                    .add(funds.getSpreadProfit())
                                    .add(funds.getFuiou_investProfit())
                                    .add(funds.getFuiou_spreadProfit()));
                    map.put("userName", m.getRealName());
                }
                map.put("day",
                        Utils.getQuot(new Date(), Utils.format(
                                Utils.format(m.getRegDate(), "yyyy-MM-dd"),
                                "yyyy-MM-dd"))
                                + new Long(1l));// 认识的天数
            }
            map.put("favourableLlist", aclist);// 优惠券列表
            map.put("list", list);
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 祝福语-拆红包
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/split", method = RequestMethod.POST)
    @ResponseBody
    public String split(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            DrActivity activity = drActivityService.selectObjectByName("感恩回馈");
            if (Utils.isObjectNotEmpty(activity)) {
                if (new Date().after(activity.getStartTime())
                        && new Date().before(activity.getEndTime())) {
                    if (Utils.isObjectEmpty(uid)) {
                        br.setSuccess(false);
                        br.setErrorCode("9999");
                        br.setErrorMsg("uid不能为空");
                        return JSON.toJSONString(br);
                    }
                    DrMember m = drMemberService.selectByPrimaryKey(uid);
                    if (Utils.isObjectEmpty(m)) {
                        br.setSuccess(false);
                        br.setErrorCode("9999");
                        br.setErrorMsg("用户不存在");
                        return JSON.toJSONString(br);
                    }
                    JsGratitudeBlessing vo = jsGratitudeBlessingService
                            .selectGratitudeBlessingByUid(uid);
                    if (Utils.isObjectNotEmpty(vo) && vo.getSplit() == 1) {
                        br.setErrorCode("1001");
                        br.setErrorMsg("已经拆过红包！");
                        br.setSuccess(false);
                        return JSON.toJSONString(br);
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", 4);
                    map.put("uid", m.getUid());
                    redisClientTemplate.lpush(
                            "regAndVerifySendRedUidList".getBytes(),
                            SerializeUtil.serialize(map));
                    br.setSuccess(true);
                } else {
                    br.setErrorMsg("当前时间不在活动时间范围");
                }
            } else {
                br.setErrorMsg("活动不存在");
            }

        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 叱咤风云签到
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/chiZhaSignIn", method = RequestMethod.POST)
    @ResponseBody
    public String chiZhaSignIn(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsSignInService.chiZhaInsert(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
            log.error("叱咤风云签到", e);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 叱咤风云排行榜
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/chiZhaIndex", method = RequestMethod.POST)
    @ResponseBody
    public String chiZhaIndex(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsSignInService.chiZhaIndex(uid);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(req);
            e.printStackTrace();
        }
        String a = JSON.toJSONString(br);
        // return JSON.toJSONString(br);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
        return jsonObject.toString();
    }

    /**
     * 双十二活动
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/doubleTwelve", method = RequestMethod.POST)
    @ResponseBody
    public String twelveActivities(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<BypCommodityDetail> bypCommodityDetails = bypCommodityDetailService
                    .doubleTwelve(uid);
            map.put("bypCommodityDetails", bypCommodityDetails);
            br.setMap(map);
            log.info(bypCommodityDetails);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("双十二活动", e);
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双十二活动查看
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectDoubleTwelve", method = RequestMethod.POST)
    @ResponseBody
    public String selectDoubleTwelve(HttpServletRequest req, Integer uid) {
        log.info(uid);
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                br.setMsg("UID为空.");
                return JSON.toJSONString(br);
            }
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            Map<String, Object> row = bypCommodityDetailService
                    .selectDoubleTwelve(uid);
            if (Utils.isObjectNotEmpty(row) && map.get("isCps").equals(0)) {
                map.clear();
                Set<Entry<String, Object>> entrySet = row.entrySet();
                for (Entry<String, Object> e : entrySet) {
                    switch (e.getKey()) {
                        case "hirtyDays":
                            if (Utils
                                    .compDecimals((BigDecimal) e.getValue(), 10000)) {
                                map.put("jdCardHirty", 0);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 10000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 30000)) {
                                map.put("jdCardHirty", 50);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 30000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 50000)) {
                                map.put("jdCardHirty", 150);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 50000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardHirty", 300);
                            }
                            if (Utils
                                    .compDecimal((BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardHirty", 600);
                            }
                            break;
                        case "threeScoreDays":
                            if (Utils
                                    .compDecimals((BigDecimal) e.getValue(), 10000)) {
                                map.put("jdCardThreeScore", 0);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 10000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 30000)) {
                                map.put("jdCardThreeScore", 100);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 30000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 50000)) {
                                map.put("jdCardThreeScore", 300);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 50000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardThreeScore", 600);
                            }
                            if (Utils
                                    .compDecimal((BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardThreeScore", 1200);
                            }
                            break;
                        case "oneEightyDays":
                            if (Utils
                                    .compDecimals((BigDecimal) e.getValue(), 10000)) {
                                map.put("jdCardOneEighty", 0);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 10000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 30000)) {
                                map.put("jdCardOneEighty", 200);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 30000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 50000)) {
                                map.put("jdCardOneEighty", 600);
                            }
                            if (Utils.compDecimal((BigDecimal) e.getValue(), 50000)
                                    && Utils.compDecimals(
                                    (BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardOneEighty", 1200);
                            }
                            if (Utils
                                    .compDecimal((BigDecimal) e.getValue(), 100000)) {
                                map.put("jdCardOneEighty", 2500);
                            }
                            break;
                        default:
                            break;
                    }
                }
                map.put("row", row);
                log.info(map);
            } else {
                Map<String, Object> row1 = new HashMap<String, Object>();
                map.put("jdCardHirty", 0);
                map.put("jdCardThreeScore", 0);
                map.put("jdCardOneEighty", 0);
                row1.put("hirtyDays", 0);
                row1.put("oneEightyDays", 0);
                row1.put("threeScoreDays", 0);
                map.put("row", row1);
                log.info(map);

            }
            br.setMap(map);
            log.info(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("双十二活动查看", e);
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取我的活动奖品
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getMyAward", method = RequestMethod.POST)
    @ResponseBody
    public String getMyAward(HttpServletRequest req, Integer uid, Integer type) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            map.put("uid", uid);
            map.put("type", type);
            List<BypCommodityDetail> bypCommodityDetails = bypCommodityDetailService
                    .selectMyAward(map);
            for (BypCommodityDetail c : bypCommodityDetails) {
                if (c.getCode() == null ? true : false) {
                    c.setCode("");
                }
            }
            map.clear();
            map.put("bypCommodityDetails", bypCommodityDetails);
            map.put("uid", uid);
            JsMemberInfo selectMemberInfoByMap = jsMemberInfoService.selectMemberInfoByMap(map);
            if (Utils.isObjectNotEmpty(selectMemberInfoByMap)) {
                map.put("address", true);
            } else {
                map.put("address", false);
            }
            br.setMap(map);
            log.info(bypCommodityDetails);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("双十二活动", e);
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 显示最近的20笔获奖用户名单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/bidActivityRedList", method = RequestMethod.POST)
    @ResponseBody
    public String bidActivityRedList(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("bidActivityStartDate");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("bidActivityEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                //显示最近的20笔获奖用户名单
                List<JsCompanyAccountLog> jsCompanyAccountLog = jsCompanyAccountLogService.selectBidPrize();
                map.put("jsCompanyAccountLog", jsCompanyAccountLog);
                br.setMap(map);
                br.setSuccess(true);
                log.info(jsCompanyAccountLog.toString());
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.info("显示最近的20笔获奖用户名单", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 显示用户已经获得的奖励
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/bidActivityRedByUid", method = RequestMethod.POST)
    @ResponseBody
    public String bidActivityRedByUid(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("bidActivityStartDate");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("bidActivityEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {

                //显示用户中奖纪录
                map.put("uid", m.getUid());
                List<JsCompanyAccountLog> jsCompanyAccountLogList = jsCompanyAccountLogService.selectBidPrizeByUid(map);
                map.put("jsCompanyAccountLogList", jsCompanyAccountLogList);
                br.setMap(map);
                br.setSuccess(true);
                log.info(jsCompanyAccountLogList.toString());
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("uid:" + uid + "/bidActivityRedByUid" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }
    /**
     * 显示用户已经获得的奖励
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/bidFullRedByUid", method = RequestMethod.POST)
    @ResponseBody
    public String bidFullRedByUid(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("bidActivityStartDate");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("bidActivityEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {

                //显示用户中奖纪录
                map.put("uid", m.getUid());
                List<DrMemberFundsRecord> drMemberFundsRecords = drMemberFundsRecordDAO.selectBidFullByUid(map);
                BigDecimal sumLog=new BigDecimal(0);
                for (DrMemberFundsRecord jc:drMemberFundsRecords) {
                    sumLog= sumLog.add(jc.getAmount());
                }
                map.put("jsCompanyAccountLogList", drMemberFundsRecords);
                map.put("fullRedSum", sumLog);
                br.setMap(map);
                br.setSuccess(true);
                log.info(drMemberFundsRecords.toString());
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("uid:" + uid + "/bidActivityRedByUid" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 我的礼品中心
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getMyPresent", method = RequestMethod.POST)
    @ResponseBody
    public String getMyPresent(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (Utils.isObjectEmpty(uid)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<JsProductPrizeLog> logsList = jsProductPrizeLogService.selectLogByUid(uid);
            map.clear();
            List<BypCommodityDetail> bypCommodityDetails = bypCommodityDetailService
                    .doubleTwelve(uid);
            map.put("bypCommodityDetails", bypCommodityDetails);
            map.put("uid", uid);
            map.put("logsList", logsList);
            List<BypCommodityExchange> bypCommodityExchanges = bypCommodityExchangeDao.selectJDCardByUid(map);
            map.put("bypCommodityExchanges",bypCommodityExchanges);
            JsMemberInfo selectMemberInfoByMap = jsMemberInfoService.selectMemberInfoByMap(map);
            if (Utils.isObjectNotEmpty(selectMemberInfoByMap)) {
                map.put("address", true);
            } else {
                map.put("address", false);
            }
            br.setMap(map);
            /*log.info(bypCommodityDetails);*/
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("双十二活动", e);
            log.error("uid:" + uid + "/getMyPresent" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_抢
     *
     * @param req
     * @param uid
     * @param fid
     * @return
     */
    @RequestMapping(value = "/lootRedEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String lootRedEnvelope(HttpServletRequest req, Integer uid, Integer fid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Date nowDate = new Date();
            Date startOne = Utils.setTimeTo(10, 00, 00);
            Date startOnes = Utils.setTimeTo(12, 00, 00);
            Date startTwo = Utils.setTimeTo(14, 00, 00);
            Date startTwos = Utils.setTimeTo(16, 00, 00);
            Date startThree = Utils.setTimeTo(20, 00, 00);
            Date startFour = Utils.setTimeTo(22, 00, 00);
            Date endOne = Utils.getAddTime(startOne, 600);
            Date endOnes = Utils.getAddTime(startOnes, 600);
            Date endTwo = Utils.getAddTime(startTwo, 600);
            Date endTwos = Utils.getAddTime(startTwos, 600);
            Date endThree = Utils.getAddTime(startThree, 600);
            Date endFour = Utils.getAddTime(startFour, 600);
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                String bidActivityStartDate = redisClientTemplate
                        .getProperties("lootRedEnvelopeStart");
                String bidActivityEndDate = redisClientTemplate
                        .getProperties("lootRedEnvelopeEnd");
                if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(bidActivityEndDate,
                        "yyyy-MM-dd HH:mm:ss")) && ((nowDate.after(startOne) && nowDate.before(endOne)) || (nowDate.after(startOnes) && nowDate.before(endOnes)) ||
                        (nowDate.after(startTwo) && nowDate.before(endTwo)) || (nowDate.after(startTwos) && nowDate.before(endTwos)) ||
                        (nowDate.after(startThree) && nowDate.before(endThree)) ||
                        (nowDate.after(startFour) && nowDate.before(endFour)))) {
                    map.clear();
                    Integer typeId = 7;
                    map.put("type", typeId);
                    map.put("uid", uid);
                    map.put("fid", fid);
                    br = drActivityParameterService.lootRedEnvelope(map);
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                br.setSuccess(false);
                br.setErrorCode("9997");
                br.setMsg("cps用户");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("uid:" + uid + "fid:" + fid + "/lootRedEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查有uid
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getUserEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String getUserEnvelope(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                map.clear();
                String bidActivityStartDate = redisClientTemplate
                        .getProperties("lootRedEnvelopeStart");
                String bidActivityEndDate = redisClientTemplate
                        .getProperties("lootRedEnvelopeEnd");
                Date nowDate = new Date();
                if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(bidActivityEndDate,
                        "yyyy-MM-dd HH:mm:ss"))) {
                    Integer typeId = 7;
                    map.put("type", typeId);
                    map.put("uid", uid);
                    br = drActivityParameterService.selectRedEnvelope(map);
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                br.setSuccess(false);
                br.setErrorCode("9997");
                br.setMsg("cps用户");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("uid:" + uid + "/getUserEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查banner红包是哪些
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String getEnvelope(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Date nowDate = new Date();
            Date startOne = Utils.setTimeTo(10, 00, 00);
            Date startOnes = Utils.setTimeTo(12, 00, 00);
            Date startTwo = Utils.setTimeTo(14, 00, 00);
            Date startTwos = Utils.setTimeTo(16, 00, 00);
            Date startThree = Utils.setTimeTo(20, 00, 00);
            Date startFour = Utils.setTimeTo(22, 00, 00);
            Date endOne = Utils.getAddTime(startOne, 600);
            Date endOnes = Utils.getAddTime(startOnes, 600);
            Date endTwo = Utils.getAddTime(startTwo, 600);
            Date endTwos = Utils.getAddTime(startTwos, 600);
            Date endThree = Utils.getAddTime(startThree, 600);
            Date endFour = Utils.getAddTime(startFour, 600);
            //每天10点、12点、14点、16点、20点、22点黄金600秒限量抢，抢完为止；
            // Utils.getAddTime(600);
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("lootRedEnvelopeStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("lootRedEnvelopeEnd");
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                Integer typeId = 7;
                map.put("type", typeId);
                map = drActivityParameterService.getEnvelope(map);
                if ((nowDate.after(startOne) && nowDate.before(endOne)) || (nowDate.after(startOnes) && nowDate.before(endOnes)) ||
                        (nowDate.after(startTwo) && nowDate.before(endTwo)) || (nowDate.after(startTwos) && nowDate.before(endTwos)) ||
                        (nowDate.after(startThree) && nowDate.before(endThree)) ||
                        (nowDate.after(startFour) && nowDate.before(endFour))) {
                    if ((nowDate.after(startOne) && nowDate.before(endOne))) {
                        long time = nowDate.getTime();
                        long time1 = endOne.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    if ((nowDate.after(startOnes) && nowDate.before(endOnes))) {
                        long time = nowDate.getTime();
                        long time1 = endOnes.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    if ((nowDate.after(startTwo) && nowDate.before(endTwo))) {
                        long time = nowDate.getTime();
                        long time1 = endTwo.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    if ((nowDate.after(startTwos) && nowDate.before(endTwos))) {
                        long time = nowDate.getTime();
                        long time1 = endTwos.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    if ((nowDate.after(startThree) && nowDate.before(endThree))) {
                        long time = nowDate.getTime();
                        long time1 = endThree.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    if ((nowDate.after(startFour) && nowDate.before(endFour))) {
                        long time = nowDate.getTime();
                        long time1 = endFour.getTime();
                        map.put("nextTime", time1 - time);
                    }
                    map.put("timeOut", true);
                }
                br.setMap(map);
                br.setSuccess(true);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("/getEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查top10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getRedEnvelopeTop", method = RequestMethod.POST)
    @ResponseBody
    public String getRedEnvelopeTop(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("lootRedEnvelopeStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("lootRedEnvelopeEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                Integer typeId = 7;
                map.put("type", typeId);
                br = drActivityParameterService.getRedEnvelopeTop(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("/getRedEnvelopeTop" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人人有份京东卡活动_查看累计
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEveryoneJdCard", method = RequestMethod.POST)
    @ResponseBody
    public String getEveryoneJdCard(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            String everyoneJdCardStart = redisClientTemplate
                    .getProperties("EveryoneJdCardStart");
            String everyoneJdCardEnd = redisClientTemplate
                    .getProperties("EveryoneJdCardEnd");
            Date nowDate = new Date();
            if (map.get("isCps").equals(0)) {
                if (nowDate.after(Utils.parseDate(everyoneJdCardStart,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(everyoneJdCardEnd,
                        "yyyy-MM-dd HH:mm:ss"))) {
                    Date startDate = Utils.parseDate(everyoneJdCardStart,
                            "yyyy-MM-dd HH:mm:ss");
                    Date endDate = Utils.parseDate(everyoneJdCardEnd,
                            "yyyy-MM-dd HH:mm:ss");
                    map.put("uid", uid);
                    map.put("startDate", startDate);
                    map.put("endDate", endDate);
                    map.put("activity_id", "ph");
                    br = drProductInvestService.getEveryoneJdCard(map);
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                map.clear();
                br.setMap(map);
                br.setSuccess(true);
                br.setMsg("渠道用户不参加活动");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("人人有份京东卡活动", e);
            log.error("/getEveryoneJdCard" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人人有份京东卡活动_TOP5土豪榜每日榜
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEveryoneTopFive", method = RequestMethod.POST)
    @ResponseBody
    public String getEveryoneTopFive(HttpServletRequest req, String thisDate) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String everyoneJdCardStart = redisClientTemplate
                    .getProperties("EveryoneJdCardStart");
            String everyoneJdCardEnd = redisClientTemplate
                    .getProperties("EveryoneJdCardEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(everyoneJdCardStart,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(everyoneJdCardEnd,
                    "yyyy-MM-dd HH:mm:ss"))) {
                Date dayBegin = Utils.setDayBegin(thisDate);
                Date dayEnd = Utils.setDayEnd(thisDate);
                map.put("startDate", Utils.format(dayBegin, "yyyy-MM-dd HH:mm:ss"));
                map.put("endDate", Utils.format(dayEnd, "yyyy-MM-dd HH:mm:ss"));
                br = drProductInvestService.getEveryoneTopFive(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("人人有份京东卡活动", e);
            log.error("/getEveryoneTopFive" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人人有份京东卡活动_TOP10VIP3榜单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEveryoneForVIP", method = RequestMethod.POST)
    @ResponseBody
    public String getEveryoneForVIP(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String everyoneJdCardStart = redisClientTemplate
                    .getProperties("EveryoneJdCardStart");
            String everyoneJdCardEnd = redisClientTemplate
                    .getProperties("EveryoneJdCardEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(everyoneJdCardStart,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(everyoneJdCardEnd,
                    "yyyy-MM-dd HH:mm:ss"))) {
                map.put("startDate", Utils.format(Utils.parseDate(everyoneJdCardStart,
                        "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
                map.put("endDate", Utils.format(Utils.parseDate(everyoneJdCardEnd,
                        "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
                br = drProductInvestService.getEveryoneForVIP(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("人人有份京东卡活动", e);
            log.error("/getEveryoneForVIP" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人人有份京东卡活动_TOP10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEveryoneTopTen", method = RequestMethod.POST)
    @ResponseBody
    public String getEveryoneTopTen(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String everyoneJdCardStart = redisClientTemplate
                    .getProperties("EveryoneJdCardStart");
            String everyoneJdCardEnd = redisClientTemplate
                    .getProperties("EveryoneJdCardEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(everyoneJdCardStart,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(everyoneJdCardEnd,
                    "yyyy-MM-dd HH:mm:ss"))) {
                br = drProductInvestService.getEveryoneTopTen();
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("人人有份京东卡活动", e);
            log.error("/getEveryoneForVIP" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 领宝箱
     *
     * @param req
     * @param uid   用户id
     * @param index 宝箱下标
     * @return
     */
    @RequestMapping(value = "/getTreasure", method = RequestMethod.POST)
    @ResponseBody
    public String getTreasure(HttpServletRequest req, Integer uid, Integer index) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        String value = "";
        Boolean falg = false;
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                String everyoneJdCardStart = redisClientTemplate
                        .getProperties("EveryoneJdCardStart");
                String everyoneJdCardEnd = redisClientTemplate
                        .getProperties("EveryoneJdCardEnd");
                Date nowDate = new Date();
                if (nowDate.after(Utils.parseDate(everyoneJdCardStart,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(everyoneJdCardEnd,
                        "yyyy-MM-dd HH:mm:ss"))) {
                    Date startDate = Utils.parseDate(everyoneJdCardStart,
                            "yyyy-MM-dd HH:mm:ss");
                    Date endDate = Utils.parseDate(everyoneJdCardEnd,
                            "yyyy-MM-dd HH:mm:ss");
                    map.put("uid", uid);
                    map.put("startDate", startDate);
                    map.put("endDate", endDate);
                    map.put("uid", uid);
                    map.put("index", index);
                    value = String.valueOf(System.currentTimeMillis());
                    falg = redisClientTemplate.tryLock(ConfigUtil.ABOUT_ACTIVITY + uid, 30, TimeUnit.SECONDS, false, value);// 枷锁
                    List<Map<String, Object>> list = drMemberFavourableDAO.selectByParam(map);
                    String id = list.get(index).get("id").toString();
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("activityId", id);
                    param.put("uid", uid);
                    Map<String, Object> res = drMemberFavourableDAO.selectUserByAid(param);
                    if (falg && Utils.isObjectEmpty(res)) {
                        br = drProductInvestService.getTreasure(map);
                    } else {
                        br.setSuccess(false);
                        br.setErrorCode("9992");
                        br.setMsg("用户存在强制刷新或者强制领取请注意!!!");
                        return JSON.toJSONString(br);
                    }
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                map.clear();
                br.setMap(map);
                br.setSuccess(true);
                br.setMsg("渠道用户不参加活动");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("人人有份京东卡活动", e);
            log.error("/getEveryoneForVIP" + e.getMessage());
        } finally {
            if (falg) {
                redisClientTemplate.releaseLock(ConfigUtil.ABOUT_ACTIVITY + uid, value);
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_抢
     *
     * @param req
     * @param uid
     * @param fid
     * @return
     */
    @RequestMapping(value = "/newLootRedEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String newLootRedEnvelope(HttpServletRequest req, Integer uid, Integer fid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                String bidActivityStartDate = redisClientTemplate
                        .getProperties("newlootRedEnvelopeStart");
                String bidActivityEndDate = redisClientTemplate
                        .getProperties("newlootRedEnvelopeEnd");
                map.clear();
                Integer typeId = 7;
                map.put("type", typeId);
                map.put("uid", uid);
                map.put("fid", fid);
                br = drActivityParameterService.lootRedEnvelope(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9997");
                br.setMsg("cps用户");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("uid:" + uid + "fid:" + fid + "/lootRedEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查有uid
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/newGetUserEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String newGetUserEnvelope(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                map.clear();
                String bidActivityStartDate = redisClientTemplate
                        .getProperties("newlootRedEnvelopeStart");
                String bidActivityEndDate = redisClientTemplate
                        .getProperties("newlootRedEnvelopeEnd");
                Date nowDate = new Date();
                if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(bidActivityEndDate,
                        "yyyy-MM-dd HH:mm:ss"))) {
                    Integer typeId = 7;
                    map.put("type", typeId);
                    map.put("uid", uid);
                    br = drActivityParameterService.selectRedEnvelope(map);
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                br.setSuccess(false);
                br.setErrorCode("9997");
                br.setMsg("cps用户");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("uid:" + uid + "/getUserEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查banner红包是哪些
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/newGetEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String newGetEnvelope(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Integer typeId = 7;
            map.put("type", typeId);
            map = drActivityParameterService.getEnvelope(map);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("/getEnvelope" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查top10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/newGetRedEnvelopeTop", method = RequestMethod.POST)
    @ResponseBody
    public String newGetRedEnvelopeTop(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("newlootRedEnvelopeStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("newlootRedEnvelopeEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                Integer typeId = 7;
                map.put("type", typeId);
                br = drActivityParameterService.getRedEnvelopeTop(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("抢红包活动", e);
            log.error("/getRedEnvelopeTop" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 扶贫助农 项目
     */
    @RequestMapping(value = "/toHelpFarmers")
    @ResponseBody
    public String toHelpFarmers(HttpServletRequest request, Integer uid){
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<BypCommodity> list = new ArrayList<>();
        BypCommodityDetail detail = null;
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("toHelpFarmersStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("toHelpFarmersEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                //查询标的id
                Integer pid = drProductInfoService.selectToHelpFarmersProduct();
                map.put("drProductId",pid);
                //扶贫助农项目
                list = bypCommodityService.selectToHelpFarmers();
                map.put("product", list);
                Integer prid = 0;
                for (BypCommodity commodity : list){
                    if (commodity.getType() == 2) prid = commodity.getPrid();
                }
            //两个活动兑换数据，排名前三，和轮播排名，是否已经兑换袁橙，未登录和已登陆状态，获取爱心数量
                if (uid != null && uid != 0){
                    //已登陆状态
                    DrMember drMember = drMemberService.selectByPrimaryKey(uid);
                    map.put("heartNumbers", drMember.getUser_integral());
                    detail = bypCommodityDetailDAO.selectToHelpFarmersBypCommodity(uid,prid);
                }
                if (detail == null){//是否可以兑换
                    prid = 1;
                }else{
                    prid = 0;
                }
                map.put("isExchange", prid);
                br.setSuccess(true);
                br.setMap(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动", e);
            log.error("/toHelpFarmers" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }
    /**
     * 扶贫助农 最新投资
     */
    @RequestMapping(value = "/toHelpFarmersInvest")
    @ResponseBody
    public String toHelpFarmersInvest(HttpServletRequest request){
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> investHistory = new ArrayList<>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("toHelpFarmersStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("toHelpFarmersEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                map.put("beginTime",bidActivityStartDate);
                map.put("endTime",bidActivityEndDate);
                map.put("fullName","公益活动");
                investHistory = drProductInvestService.selectNewInvestByActivityProduct(map);
                map.clear();
                map.put("investHistory",investHistory);
                br.setSuccess(true);
                br.setMap(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动", e);
            log.error("/toHelpFarmersInvest" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }
    /**
     * 扶贫助农 20最新捐赠记录
     */
    @RequestMapping(value = "/toHelpFarmersTwenty")
    @ResponseBody
    public String toHelpFarmersTwenty(HttpServletRequest request, Integer uid){
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> mapHistory = new ArrayList<>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("toHelpFarmersStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("toHelpFarmersEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                map.put("activityId","fpzn");
                map.put("type",1);
                map.put("beginTime",bidActivityStartDate);
                map.put("endTime",bidActivityEndDate);
                //爱心赠送记录最新
                map.put("limit",20);
                mapHistory = bypCommodityDetailService.selectToHelpFarmers(map);
                map.clear();
                map.put("heartHistory",mapHistory);
                br.setSuccess(true);
                br.setMap(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动", e);
            log.error("/getRedEnvelopeTop" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }
    /**
     * 扶贫助农 top3
     */
    @RequestMapping(value = "/toHelpFarmersTopThree")
    @ResponseBody
    public String toHelpFarmersTopThree(HttpServletRequest request){
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> mapTop3 = new ArrayList<>();
        try {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("toHelpFarmersStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("toHelpFarmersEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                map.put("activityId","fpzn");
                map.put("type",1);
                //排名前三
                map.put("limit",3);
                map.put("beginTime",bidActivityStartDate);
                map.put("endTime",bidActivityEndDate);
                mapTop3 =  bypCommodityDetailService.selectToHelpFarmersTop(map);
                map.clear();
                map.put("top3",mapTop3);
                br.setSuccess(true);
                br.setMap(map);
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动", e);
            log.error("/getRedEnvelopeTop" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }


    /**
     * 兑换猕猴桃
     */
    @RequestMapping("/forFruits")
    @ResponseBody
    public String forFruits(HttpServletRequest req,Integer uid, Integer pid){
        BaseResult br = new BaseResult();
        String value = null;
        Map<String , Object> map = new HashMap<>();
        boolean lockFlag = false;;
        if (uid == null || uid == 0){
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                String bidActivityStartDate = redisClientTemplate
                        .getProperties("toHelpFarmersStart");
                String bidActivityEndDate = redisClientTemplate
                        .getProperties("toHelpFarmersEnd");
                Date nowDate = new Date();
                if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(bidActivityEndDate,
                        "yyyy-MM-dd HH:mm:ss"))) {
                    if (Utils.isObjectNotEmpty(pid)) {
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.getToHelpFarmers() + member.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag) {
                            // 更新兑换礼品的相关代码
                            br = bypCommodityDetailService.getFruitsByUidAndPid(
                                    member, pid.toString());
                        }
                    } else {
                        br.setMsg("没有参数");
                        br.setErrorCode("1003");
                        log.error("兑换实物,未传参");
                    }
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("9991");
                    br.setMsg("活动过期了");
                    return JSON.toJSONString(br);
                }
            } else {
                    br.setSuccess(false);
                    br.setErrorCode("9997");
                    br.setMsg("cps用户");
                    return JSON.toJSONString(br);
                }
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动,兑换猕猴桃", e);
            log.error("/forFruits" + e.getMessage());
        }finally {
            if (lockFlag) {
                redisClientTemplate.releaseLock(ConfigUtil.getRedisKeyConvert()+uid,value);//解锁
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 捐赠爱心
     */
    @RequestMapping("/forLoves")
    @ResponseBody
    public String forLoves(HttpServletRequest req,Integer uid, Integer pid, Integer number){
        BaseResult br = new BaseResult();
        String value = null;
        Map<String , Object> map = new HashMap<>();
        boolean lockFlag = false;
        if (uid == null || uid == 0){
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            DrMember member = drMemberService.selectByPrimaryKey(uid);
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("toHelpFarmersStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("toHelpFarmersEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                if (number == null || number == 0)number = 1;
                if ( Utils.isObjectNotEmpty(pid)) {
                    value = String.valueOf(System.currentTimeMillis());
                    lockFlag = redisClientTemplate.tryLock(ConfigUtil.getToHelpFarmers() + member.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                    if (lockFlag) {
                        // 更新兑换礼品的相关代码
                        br = bypCommodityDetailService.getLoveByUidAndPid(
                                member, pid.toString(), number);
                    }
                } else {
                    br.setMsg("没有参数");
                    br.setErrorCode("1003");
                    log.error("兑换实物,未传参");
                }
            } else {
                br.setSuccess(false);
                br.setErrorCode("9991");
                br.setMsg("活动过期了");
                return JSON.toJSONString(br);
            }
            } else {
                br.setSuccess(false);
                br.setErrorCode("9997");
                br.setMsg("cps用户");
                return JSON.toJSONString(br);
            }
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动,兑换猕猴桃", e);
            log.error("/forFruits" + e.getMessage());
        }finally {
            if (lockFlag) {
                redisClientTemplate.releaseLock(ConfigUtil.getRedisKeyConvert()+uid,value);//解锁
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 邀请好友统计活动
     *
     * @param req
     * @return
     */
    @RequestMapping("/activityFriendPage")
    @ResponseBody
    public String getActivityFriendPage(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            br = jsActivityFriendService.getActivityFriendBonus(uid);
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorMsg("邀请好友活动数据查询错误!");
            log.error("邀请好友活动数据查询错误！"+e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 我的邀请 好友统计
     *
     * @param req
     * @return
     */
    @RequestMapping("/getMyFriendPage")
    @ResponseBody
    public String getMyFriendPage(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        if (uid == null || uid == 0){
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            br = jsActivityFriendService.getMyFriendBonus(uid);
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorMsg("邀请好友活动数据查询错误!");
            log.error("邀请好友活动数据查询错误！"+e.getMessage());
        }
        return JSON.toJSONString(br);
    }


}
