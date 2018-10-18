package com.jsjf.controller.activity;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.*;
import com.jsjf.dao.activity.BypCommodityDetailDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberLotteryLogDAO;
import com.jsjf.model.activity.*;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.*;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.model.system.SysArticle;
import com.jsjf.service.activity.*;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jsjf.service.member.*;
import com.jsjf.service.product.*;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private DrMemberFavourableService drMemberFavourableService;
    @Autowired
    private DrProductInfoService drProductInfoService;
    @Autowired
    private DrMemberRecommendedService drMemberRecommendedService;
    @Autowired
    private DrProductInvestService drProductInvestService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrMemberLotteryLogService drMemberLotteryLogService;
    @Autowired
    private DrMemberService drMemberService;
    @Autowired
    private DrActivityParameterService drActivityParameterService;
    @Autowired
    private JsActivityProductInvestInfoService jsActivityProductInvestInfoService;
    @Autowired
    private JsActivityFriendService jsActivityFriendService;
    @Autowired
    private JsActivityProductService jsActivityProductService;
    @Autowired
    private JsproductPrizeService jsproductPrizeService;
    @Autowired
    private JsProductPrizeLogService jsProductPrizeLogService;
    @Autowired
    private JsActivityAggregationPageService jsActivityAggregatioPageService;
    @Autowired
    private JsActivityMemberAccountService jsActivityMemberAccountService;
    @Autowired
    private DrMemberFundsService drMemberFundsService;
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
    private DrActivityService drActivityService;
    @Autowired
    private JsGratitudeBlessingService jsGratitudeBlessingService;
    @Autowired
    private BypCommodityDetailService bypCommodityDetailService;
    @Autowired
    private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
    @Autowired
    private BypCommodityService bypCommodityService;
    @Autowired
    private BypMemberIntegralService bypMemberIntegralService;
    @Autowired
    private JsMemberInfoService jsMemberInfoService;
    @Autowired
    private DrChannelInfoService drChannelInfoService;
    @Autowired
    private JsCompanyAccountLogService jsCompanyAccountLogService;
    @Autowired
    private DrChannelInfoDAO drChannelInfoDAO;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private BypCommodityDetailDAO bypCommodityDetailDAO;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    /**
     * 签到
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public String signIn(HttpServletRequest req) {
        Date now = new Date();
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
            if (Utils.isObjectNotEmpty(m)) {
                br = drActivityService.signIn(m.getUid(), 0, now);
            } else {
                br.setErrorMsg("用户未登录");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setSuccess(false);
            br.setErrorMsg("系统错误");
        }

        return JSON.toJSONString(br);
    }

    /**
     * 叱咤风云榜
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/allPowerfullTop", method = RequestMethod.POST)
    @ResponseBody
    public String allPowerfullTop(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {

            br = drActivityService.allPowerfullTop(m);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setSuccess(false);
            br.setErrorMsg("系统错误");
        }

        return JSON.toJSONString(br);
    }

    /**
     * 投及送 ,用户晒单添加
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addOrderShare", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String addOrderShare(HttpServletRequest req, MultipartFile file,
                                String describes) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // MultipartFile file = (MultipartFile) param.get("file");
            // String describe = (String) param.get("describe");
            describes = Utils.stripHtmlLabel(describes);
            if (!Utils.strIsNull(describes) && describes.trim().length() < 141) {
                if (Utils.isObjectNotEmpty(file)) {
                    if (Utils.isObjectNotEmpty(m)) {
                        // 2.用户晒单次数小于投资次数
                        map.put("uid", m.getUid());
                        int orderShareTotal = jsProductPrizeOrderShareService
                                .selectByMapCount(map);
                        map.put("prizeIds", 1);// 是投投即送产品
                        map.put("investStatuses", new Integer[]{1, 3});
                        int investTotal = drProductInvestService
                                .selectInvestCountByMap(map);
                        if (orderShareTotal < investTotal) {
                            jsProductPrizeOrderShareService.insert(m.getUid(),
                                    describes, file, br);
                        } else {
                            br.setErrorMsg("晒单次数超过投资次数");
                        }
                    } else {
                        br.setErrorMsg("用户未登录");
                    }
                } else {
                    br.setErrorMsg("图片未上传");
                }
            } else {
                br.setErrorMsg("超过 140 个字符限定");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setSuccess(false);
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 投及送 ,用户晒单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderShareList", method = RequestMethod.POST)
    @ResponseBody
    public String orderShareList(HttpServletRequest req,
                                 @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {

            Integer pageOn = (Integer) param.get("pageOn");
            Integer pageSize = (Integer) param.get("pageSize");

            PageInfo info = new PageInfo(pageOn, pageSize);

            map.put("offset", info.getPageInfo().getOffset());
            map.put("limit", info.getPageInfo().getLimit());

            map.put("orders", " order by po.addtime desc ");
            map.put("isShow", 1);

            List<JsProductPrizeOrderShare> list = jsProductPrizeOrderShareService
                    .selectByMap(map);
            int total = jsProductPrizeOrderShareService.selectByMapCount(map);

            info.setRows(list);
            info.setTotal(total);

            map.clear();
            // 2.用户晒单次数小于投资次数
            if (Utils.isObjectNotEmpty(m)) {
                map.put("uid", m.getUid());
                int orderShareTotal = jsProductPrizeOrderShareService
                        .selectByMapCount(map);
                map.put("prizeIds", 1);// 是投投即送产品
                map.put("investStatuses", new Integer[]{1, 3});
                int investTotal = drProductInvestService
                        .selectInvestCountByMap(map);
                map.clear();
                map.put("islogin", true);
                map.put("isOrderShare", false);
                if (orderShareTotal < investTotal) {
                    map.put("isOrderShare", true);
                }
            } else {
                map.put("islogin", false);
                map.put("isOrderShare", false);
            }
            map.put("info", info);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setSuccess(false);
            br.setErrorMsg("系统错误");
        }

        return JSON.toJSONString(br);
    }

    /**
     * 砸冰块
     *
     * @param req
     * @return
     */
    @RequestMapping("/hitIce")
    @ResponseBody
    public String hitIce(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
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
    public String activityHitIceIndex(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
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
            // 注册送体验金总计
            map.put("regSendCount",
                    drMemberFavourableService.selectRegSendCount());
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
    public String selectProductPrize(HttpServletRequest req,
                                     @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JsProductPrize prize = jsproductPrizeService
                    .selectJsPorudctPrize(param);
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
            map.put("orders",
                    " ORDER BY jpp.sort DESC,dp.`status`,jpp.`addTime` ");
            map.put("status", 1);
            map.put("category", 1);// 1:夏日专享2:品质生活3:数码优选'
            map.put("limit", 3);
            List<JsProductPrize> prizeList = jsproductPrizeService
                    .selectActivityIndexJsproductPrize(map);
            map.put("category", 2);// 1:夏日专享2:品质生活3:数码优选'
            prizeList.addAll(jsproductPrizeService
                    .selectActivityIndexJsproductPrize(map));
            map.put("category", 3);// 1:夏日专享2:品质生活3:数码优选'
            prizeList.addAll(jsproductPrizeService
                    .selectActivityIndexJsproductPrize(map));

            map.clear();
            // map.put("status", 1);//商品上架
            map.put("type", 0);// 0商品投资订单 1商品预约订单
            map.put("offset", 0);
            map.put("limit", 30);
            // 投资记录
            List<Map<String, Object>> investList = jsProductPrizeLogService
                    .selectListMap(map);
            // 总投资人数
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
            map.put("orders", " ORDER BY po.sort DESC ");
            map.put("offset", 0);
            map.put("limit", 5);
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
     * 分享双旦活动
     *
     * @param req
     * @return
     */
    @RequestMapping("/dobuleEggShare")
    @ResponseBody
    public String dobuleEggShare(HttpServletRequest req) {
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String amount = drMemberLotteryLogService
                    .selectGiftName(m.getUid());
            if (!Utils.strIsNull(amount)) {
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
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
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
    public String tearOpen(HttpServletRequest req) {
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        BaseResult br = new BaseResult();
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
     * @param param
     * @return
     */
    @RequestMapping("/myInvitation")
    @ResponseBody
    public String getActivityFriendStatisticsTwo(HttpServletRequest req,
                                                 @RequestBody Map<String, Object> param) {
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        BaseResult br = jsActivityFriendService.myInvitation(m, param);
        return JSON.toJSONString(br);
    }

    /**
     * 好友邀请活动统计
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/getActivityFriendStatistics")
    @ResponseBody
    public String getActivityFriendStatistics(HttpServletRequest req,
                                              @RequestBody Map<String, Object> param) {
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        BaseResult br = jsActivityFriendService.getActivityFriendStatistics(m,
                param);
        return JSON.toJSONString(br);
    }

    /**
     * 活动列表
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/getActivityFriendConfigAll")
    @ResponseBody
    public String getActivityFriendConfigAll(HttpServletRequest req,
                                             @RequestBody Map<String, Object> param) {
        BaseResult br = jsActivityFriendService.selectJsActivityFriend(param); // 邀请好有返利列表
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
    public String getActivityFriendConfig(HttpServletRequest req,
                                          @RequestBody Map<String, Object> param) {
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        BaseResult br = jsActivityFriendService.selectNewJsActivityFriend(m,
                param); // 邀请好有返利
        return JSON.toJSONString(br);
    }

    /**
     * 砸金蛋,随机加息券
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/getRandomCouponByProductId")
    @ResponseBody
    public String getRandomCouponByProductId(HttpServletRequest req,
                                             @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("没有登录");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        if (!param.containsKey("id") || null == param.get("id")
                || "".equals(param.get("id").toString())) {
            br.setErrorMsg("id不能为空");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        br = drActivityParameterService.getRandomCouponByProductId(m,
                (Integer) param.get("id"));

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
                                         @RequestBody Map<String, Object> param) {
        return JSON.toJSONString(jsActivityProductInvestInfoService
                .getActivityPrizeResult(param));
    }

    /**
     * 活动中心-中奖记录
     *
     * @param req
     * @param type
     * @return
     */
    @RequestMapping("/getMyPrizeRecords")
    @ResponseBody
    public String jsActivityProductCenter(HttpServletRequest req,
                                          @RequestBody Map<String, Object> param) {

        PageInfo pi = new PageInfo();
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);

        if (Utils.isObjectEmpty(m)) {
            br.setErrorCode("9998");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        map.put("uid", m.getUid());
        if (!param.containsKey("prizeStatus")
                || param.get("prizeStatus") == null) {
            map.put("status", null);
        } else if ((Integer) param.get("prizeStatus") == 0) {// 未开奖
            map.put("status", new int[]{1, 2}); // 1-进行中，2-待开奖
        } else if ((Integer) param.get("prizeStatus") == 1) {// 已开奖
            map.put("status", new int[]{3});// 已完成
        }
        if (param.containsKey("pageOn") && param.containsKey("pageSize")) {
            pi.setPageOn((Integer) param.get("pageOn"));
            pi.setPageSize((Integer) param.get("pageSize"));
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
     * @param type
     * @return
     */
    @RequestMapping("/getPrizeInfoByProductId")
    @ResponseBody
    public String jsActivityProductWinner(HttpServletRequest req,
                                          @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!param.containsKey("id")) {
                br.setErrorCode("产品id不能为null");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            map.put("pid", param.get("id"));
            List<Map<String, Object>> list = jsActivityProductInvestInfoService
                    .selectJsActivityProductIsWinner(map);
            map.clear();
            map.put("list", list);
            Map<String, Object> aMap = jsActivityProductService
                    .selectActivityProduct(Integer.parseInt(param.get("id")
                            .toString()));
            map.put("pcDetailImg", aMap.get("pcDetailImg"));
            br.setMap(map);
            br.setSuccess(true);

        } catch (Exception e) {
            br.setErrorMsg("系统错误");
            br.setErrorCode("9999");
            br.setSuccess(false);
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public String index(HttpServletRequest req,
                        @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
            // 前台传一个flag 0是我的体验金，1是我的优惠券
            param.put("uid", m.getUid());
            if (Utils.isObjectNotEmpty(param.get("flag"))
                    && (int) param.get("flag") == 1) {
                param.put("type", new Integer[]{1, 2, 4});
            } else if (Utils.isObjectNotEmpty(param.get("flag"))
                    && (int) param.get("flag") == 0) {
                param.put("type", new Integer[]{3});
            } else {
                // flag !=0&1的时候，全部查询
                param.put("type", new Integer[]{1, 2, 3, 4});
            }
            List<DrMemberFavourable> list = drMemberFavourableService
                    .selectByParam(param);
            param.clear();
            param.put("list", list);
            br.setMap(param);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/usable", method = RequestMethod.POST)
    @ResponseBody
    public String usable(HttpServletRequest req,
                         @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", m.getUid());
        map.put("barring", new Integer[]{1});
        Integer investCount = drProductInvestService
                .selectInvestCountByMap(map);

        try {
            DrProductInfo info = drProductInfoService
                    .selectProductByPrimaryKey(Integer.parseInt(param
                            .get("pid").toString()));
            List<DrMemberFavourable> list = null;
            if (info.getAtid() == null) { // 不是活动标才能使用券
                param.put("status", 0);
                param.put("uid", m.getUid());
                param.put("deadline", info.getDeadline());
                if (info.getType() == 1) {// 新手标
                    param.put("type", new Integer[]{3});
                } else {
                    Integer[] types = new Integer[3];
                    if (info.getIsCash() == 1) {
                        types[1] = 1;
                    }
                    if (info.getIsInterest() == 1) {// 是否加息
                        types[0] = 2;
                    }
                    if (info.getIsDouble() == 1) {
                        types[2] = 4;
                    }
                    if ("2".equals(req.getParameter("channel"))) {
                        types = new Integer[]{1, 2};
                    }
                    param.put("type", types);

                    // if(info.getIsInterest() == 1 && info.getIsCash() == 1 &&
                    // info.getIsDouble() == 1){//加息券、返现、翻倍券
                    // param.put("type", new Integer[]{1, 2, 4});
                    // }else if(info.getIsCash() == 1){//返现红包
                    // param.put("type", new Integer[]{1});
                    // }else if(info.getIsInterest() == 1){//加息券
                    // param.put("type", new Integer[]{2});
                    // }else if(info.getIsDouble() == 1){//翻倍券
                    // param.put("type", new Integer[]{4});
                    // }
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
            param.put("investCount", investCount);
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
    public String myFrieds(HttpServletRequest req,
                           @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        PageInfo pi = new PageInfo();
        BigDecimal rewards = BigDecimal.ZERO;
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("referrerId", m.getUid());
            map.put("other", " investTime is not null and referrerReward > 0");
            pi = drMemberRecommendedService.getDrMemberRecommended(map);
            rewards = drMemberRecommendedService.getRewardByReferrerId(m
                    .getUid());
            param.clear();
            param.put("list", pi.getRows());
            param.put("total", pi.getTotal());
            param.put("rewards", rewards == null ? 0 : rewards);
            param.put("recommCodes", m.getRecommCodes());
            br.setMap(param);
            br.setSuccess(true);

        } catch (Exception e) {
            log.error("我的好友查询失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 根据用户UID获取用户是否参与了活动
     */
    @RequestMapping(value = "/isParticipationActivity", method = RequestMethod.POST)
    @ResponseBody
    public String isParticipationActivity(HttpServletRequest req,
                                          @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            // 获取登录ID
            Object uid = Utils.isObjectNotEmpty(param.get("uid")) ? param
                    .get("uid") : Utils.isObjectNotEmpty(m) ? m.getUid() : null;
            Map<String, Object> map = new HashMap<String, Object>();
            // 获取活动详情
            map.put("type", param.get("type"));
            map.put("status", 1);// 生效
            DrCouponsIssuedRules rules = drActivityParameterService
                    .getcouponsIssuedRules(map);
            // 判断登录用户是否已经参与活动
            if (Utils.isObjectNotEmpty(uid)) {
                map.clear();
                map.put("uid", uid);
                map.put("type", param.get("type"));// 赠送时刻 0 注册 1：投资赠送
                map.put("statuses", new Integer[]{1});// 生效
                Integer total = drMemberFavourableService
                        .getParticipationActivityTotal(map);
                if (total > 0) {
                    br.setFlag(true);
                } else {
                    br.setFlag(false);
                }
            } else {
                br.setFlag(false);
            }
            map.clear();
            map.put("startTime", rules == null ? "" : rules.getStartTime());
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setErrorCode("9999");// 系统错误
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取端午节投资数据
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getInvestTopData", method = RequestMethod.POST)
    @ResponseBody
    public String getDwData(HttpServletRequest req,
                            @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        try {
            // Integer activityId =
            // Integer.parseInt(redisClientTemplate.getProperties("online.activity.id"));
            // DrActivity drActivity =
            // drActivityService.selectByPrimaryKey(activityId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startDate", param.get("startDate"));
            map.put("endDate", param.get("endDate"));
            map.put("topNum", param.get("topNum"));
            List<Map<String, Object>> list = drProductInvestService
                    .selectInvestTopGroupByUid(map);// 前十名
            Map<String, Object> memberInvest = new HashMap<String, Object>();
            DrMember member = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);

            Object uid = Utils.isObjectNotEmpty(param.get("uid")) ? param
                    .get("uid") : Utils.isObjectNotEmpty(member) ? member
                    .getUid() : null;

            if (Utils.isObjectNotEmpty(uid)) {// 获取用户的投资额
                map.put("uid", uid);
                List<Map<String, Object>> mMap = drProductInvestService
                        .selectInvestTopGroupByUid(map);
                if (mMap.size() > 0) {
                    memberInvest = mMap.get(0);
                }
                // map.put("aid", drActivity.getId());
                // map.put("addtime", new Date());
                // //查询当天已抽奖次数
                // List<DrMember> mList =
                // drMemberService.selectMemberIsJoinActivity(Integer.parseInt(uid.toString()));
                // if(mList.size() > 0){
                // useCount =
                // drMemberLotteryLogService.selectListByParam(map).size();
                // }
            }
            map.clear();
            map.put("investList", list);
            map.put("memberInvest", Utils.isObjectEmpty(memberInvest
                    .get("total")) ? 0 : memberInvest.get("total"));
            map.put("readTime", new Date());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("获取端午节数据失败", e);
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
                                             @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        String recommCode = (String) param.get("recommCode");
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
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双蛋活动首页
     *
     * @param req
     * @param sendCoupon
     * @return
     */
    @RequestMapping("/doubleAggIndex")
    @ResponseBody
    public String doubleAggIndex(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<DrProductInfo> list = new ArrayList<DrProductInfo>();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            // DrMember m = drMemberService.selectByPrimaryKey(21);
            // 双旦活动查询60 和180的产品各一个
            map.put("deadline",
                    new Integer[]{
                            Integer.valueOf(redisClientTemplate
                                    .getProperties("productDealine2")),
                            Integer.valueOf(redisClientTemplate
                                    .getProperties("productDealine3"))});
            map.put("status", 5);
            map.put("type", 2);
            list = drProductInfoService.doubleEggList(map);
            br.setSuccess(true);
            map.clear();
            if (Utils.isObjectEmpty(m)) {
                result.put("pullCount", 0);
                result.put("isOldUser", false);
            } else {
                map.clear();
                map.put("uid", m.getUid());
                int count = drMemberLotteryLogService
                        .getDoubleAGGLotteryCountByUid(map);
                map.clear();
                map.put("uid", m.getUid());
                map.put("type", 2);
                result.put("isOldUser",
                        drProductInvestService.checkProductType(map));
                result.put("pullCount", count);
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
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String accountInvestLogs(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
            param.put("uid", m.getUid());
            br = jsProductPrizeLogService.selectPrizeLogByUid(param);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setSuccess(false);
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 添加预约
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertPrizeInfo", method = RequestMethod.POST)
    @ResponseBody
    public String insertPrizeInfo(HttpServletRequest req,
                                  @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(param.get("ppid"))) {
            br.setErrorMsg("奖品id不能为空");
            br.setSuccess(false);
            return JSON.toJSONString(br);
        }
        try {
            JsProductPrizeLog jsProductPrizeLog = new JsProductPrizeLog();
            jsProductPrizeLog.setUid(m.getUid());
            jsProductPrizeLog.setPpid(Integer.parseInt(param.get("ppid")
                    .toString()));
            jsProductPrizeLog.setType(1);
            jsProductPrizeLogService.insert(jsProductPrizeLog);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String getAllActivity(HttpServletRequest req,
                                 @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        PageInfo pi = new PageInfo();
        try {
            if (param.containsKey("pageOn") && param.containsKey("pageSize")) {
                pi.setPageOn(Integer.parseInt(param.get("pageOn").toString()));
                pi.setPageSize(Integer.parseInt(param.get("pageSize")
                        .toString()));
            }
            param.put("offset", pi.getPageInfo().getOffset());
            param.put("limit", pi.getPageInfo().getLimit());
            br = jsActivityAggregatioPageService
                    .selectJsActivityAggregationPageList(param);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 人脉王排行榜top10
     */
    @RequestMapping(value = "/getTopTen", method = RequestMethod.POST)
    @ResponseBody
    public String getTopTen(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        br = jsActivityMemberAccountService.selectFriendAmountTopTen(null);
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
    public String getGreatWallInfo(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            map.put("isRealverify",
                    drMemberService.selectByPrimaryKey(m.getUid())
                            .getRealVerify() == 1 ? 1 : 0);
            map.put("redCount",
                    drMemberFavourableService.selectRedCountByUid(m.getUid()));
            map.put("balance",
                    drMemberFundsService.selectDrMemberFundsByUid(m.getUid())
                            .getBalance());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 吃元宵
     *
     * @param req
     * @param uid
     * @return
     */
    @RequestMapping(value = "/eatGlutinous", method = RequestMethod.POST)
    @ResponseBody
    public String eatGlutinous(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
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
                map.put("uid", m.getUid());
                map.put("aid", 4);// 4 元宵活动id
                map.put("addtime", nowDate);
                List<DrMemberLotteryLog> list = drMemberLotteryLogService
                        .selectListByParam(map);
                if (Utils.isObjectNotEmpty(list) && list.size() > 0) {
                    // 领取过的情况下直接返回
                    map.clear();
                    map.put("isReceive", true);// 是否领取
                    br.setSuccess(false);
                    br.setMap(map);
                    return JSON.toJSONString(br);
                }
                // 未领取的情况
                map.clear();
                map.put("uid", m.getUid());
                map.put("atid", 4);// 4 元宵活动id
                map.put("classes", 1);// 1是虚拟2是实物
                br = jsActivityRewardService.getJsActivityRewardByAid(map);
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 2017元旦领取记录
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
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    // 新手标投资记录以及投资人数
    @RequestMapping(value = "/getProductInvestList", method = RequestMethod.POST)
    @ResponseBody
    public String getProductInvestList(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dpiStatus", new Integer[]{0, 1, 3, 4});
            map.put("dpType", 3);
            map.put("type", 3);
            List<DrProductInvest> list = drProductInvestService
                    .getProductInvestListByType(map);
            int investCount = drProductInvestService
                    .getProductInvestCountByType(map);// 投资人数
            DrProductInfo drProductInfo = drProductInfoService
                    .selectNewHandInfo(map);
            map.clear();
            map.put("newHand", drProductInfo);
            map.put("investList", list);
            map.put("investCount", investCount);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
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
    public String SignUp(HttpServletRequest req,
                         @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        JsOpenDayLog jsOpenDayLog = new JsOpenDayLog();
        try {
            if (Utils.isObjectEmpty(map.get("uid"))
                    || Utils.isObjectEmpty(map.get("openDayId"))
                    || Utils.isObjectEmpty(map.get("userName"))
                    || Utils.isObjectEmpty(map.get("sex"))
                    || Utils.isObjectEmpty(map.get("city"))) {
                br.setErrorMsg("参数有误");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            jsOpenDayLog.setUid(Integer.parseInt(map.get("uid").toString()));
            jsOpenDayLog.setOpenDayId(Integer.parseInt(map.get("openDayId")
                    .toString()));
            jsOpenDayLog.setUserName(map.get("userName").toString());
            jsOpenDayLog.setSex(Integer.parseInt(map.get("sex").toString()));
            jsOpenDayLog.setCity(map.get("city").toString());
            jsOpenDayLogService.insert(jsOpenDayLog);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 开放日列表
     */
    @RequestMapping(value = "/getOpenDayList", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenDayList(HttpServletRequest req,
                                 @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        PageInfo pi = new PageInfo();
        try {
            if (param.containsKey("pageOn") && param.containsKey("pageSize")) {
                pi.setPageOn((Integer) param.get("pageOn"));
                pi.setPageSize((Integer) param.get("pageSize"));
            }
            param.put("offset", pi.getPageInfo().getOffset());
            param.put("limit", pi.getPageInfo().getLimit());
            br = jsOpenDayService.selectJsOpenDayByParam(param);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 开放日文章详情
     */
    @RequestMapping(value = "/getOpenDayArticleDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenDayArticleDetail(HttpServletRequest req,
                                          @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            if (Utils.isObjectEmpty(map.get("openDayId"))) {
                br.setErrorMsg("活动id不能为空");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<SysArticle> sysArticle = sysArticleService
                    .getArticleByParam(map);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("status", 2);
            List<JsOpenDay> list = jsOpenDayService
                    .selectJsOpenDayListByParam(param);
            Integer upOpenDayId = null;
            Integer downOpenDayId = null;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == map.get("openDayId")) {
                    if (i != 0) {
                        upOpenDayId = list.get(i - 1).getId();
                    }
                    if (i != list.size() - 1) {
                        downOpenDayId = list.get(i + 1).getId();
                    }
                }
            }
            map.clear();
            map.put("sysArticle", sysArticle.size() > 0 ? sysArticle.get(0)
                    : null);
            map.put("upOpenDayId", upOpenDayId);
            map.put("downOpenDayId", downOpenDayId);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String firstInvestList(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
            if (Utils.isObjectEmpty(m)) {
                br.setErrorMsg("未登录");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            br = jsActivityFriendService.firstInvestList(m.getUid());
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 三重礼top10排行榜
     *
     * @param req
     * @param uid
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
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectNotEmpty(m)) {// 获得推荐吗
                map.put("recommCode", m.getRecommCodes());
            }

            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String getMyAccount(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            param.put("status", 0);
            param.put("uid", m.getUid());
            param.put("type", new Integer[]{1, 2, 4, 5});
            List<DrMemberFavourable> favourableList = drMemberFavourableService
                    .selectByParam(param);
            DrMemberFunds drMemberFunds = drMemberFundsService
                    .selectDrMemberFundsByUid(m.getUid());
            map.put("favourableCount", favourableList.size());
            map.put("balance", drMemberFunds.getFuiou_balance());
            map.put("mobilePhone", m.getMobilephone());
            br.setMap(map);
            br.setSuccess(true);
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
    public String newWishCommit(HttpServletRequest req,
                                @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            JsProductPrizeWish prizeWish = new JsProductPrizeWish(m.getUid(),
                    (String) map.get("url"), (String) map.get("remarks"));
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
    public String getMyZeroBuy(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
            br = jsProductReservationLogService.getMyZeroBuy(m.getUid());
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
     * @param url
     * @param remarks
     * @return
     */
    @RequestMapping(value = "/getWish", method = RequestMethod.POST)
    @ResponseBody
    public String getWish(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            br = jsProductPrizeWishService.selectWish(m.getUid());
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String wishCommit(HttpServletRequest req, @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            JsProductPrizeWish prizeWish = new JsProductPrizeWish(m.getUid(), (String) map.get("url"),
                    (String) map.get("remarks"));
            br = jsProductPrizeWishService.insertPrizeWish(prizeWish);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
    public String offlineActivityList(HttpServletRequest req,
                                      @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            br = jsActivityOfflineService.selectJsActivityOfflineListByMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    public String offlineActivityDetail(HttpServletRequest req,
                                        @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            br = jsActivityOfflineService
                    .selectJsActivityOfflineDetailById(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping("/activityMay18d")
    @ResponseBody
    public String activityMay18d(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            DrMember member = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);

            br = drProductInvestService.getActivityMay18d(member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setErrorCode("9999");
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
    public String getCouponIsActivation(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean goldIsActivation = false;
        boolean redIsActivation = false;
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectNotEmpty(m)) {
                goldIsActivation = drMemberFavourableService
                        .selectIsShowCountByUid(m.getUid()) > 0 ? false : true;
            }
            if (Utils.isObjectNotEmpty(m)) {
                redIsActivation = drMemberFavourableService
                        .getMemberFavourableTotal(m.getUid()) > 0 ? false
                        : true;
            }
            map.put("goldIsActivation", goldIsActivation);
            map.put("redIsActivation", redIsActivation);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 感恩活动首页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/thanksGiveIndex", method = RequestMethod.POST)
    @ResponseBody
    public String thanksGiveIndex(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectNotEmpty(m)) {
                param.put("uid", m.getUid());
                param.put("status", 2);
                param.put("type", 5);
                param.put("order", " ORDER BY a.investTime ");
                param.put("limit", 1);
                DrProductInvest invest = drProductInvestService
                        .selectByParam(param);
                DrMemberFunds funds = drMemberFundsService
                        .selectDrMemberFundsByUid(m.getUid());
                param.clear();
                param.put("uid", m.getUid());
                List<JsGratitudeBlessing> blessings = jsGratitudeBlessingService
                        .selectJsGratitudeBlessingList(param);
                // true = 已提交，false = 未提交
                map.put("status", blessings.size() > 0 ? blessings.get(0)
                        .getStatus() : null);
                map.put("blessing", blessings.size() > 0 ? blessings.get(0)
                        .getBlessing() : null);
                // true = 已拆，false = 未拆
                map.put("isSplit", blessings.size() > 0 ? blessings.get(0)
                        .getSplit() == 1 ? true : false : false);
                // 已收投资收益+推广收益
                map.put("profit",
                        funds.getInvestProfit().add(funds.getSpreadProfit())
                                .add(funds.getFuiou_investProfit())
                                .add(funds.getFuiou_spreadProfit()));
                map.put("redDate", m.getRegDate());
                map.put("jsAndYou",
                        Utils.getQuot(new Date(), m.getRegDate()) + 1);
                map.put("firstAmount",
                        Utils.isObjectEmpty(invest) ? 0 : invest.getAmount());
                map.put("investTime", Utils.isObjectEmpty(invest) ? null
                        : invest.getInvestTime());
            }
            // 获取祝福list
            param.clear();
            param.put("status", 1);
            param.put("order", " ORDER BY a.addtime desc ");
            param.put("limit", 100);
            map.put("thanksGiveList", jsGratitudeBlessingService
                    .selectJsGratitudeBlessingList(param));
            map.put("redList",
                    drActivityParameterService.getActivityParameterByRules());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 提交感恩
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/insertThanksGive", method = RequestMethod.POST)
    @ResponseBody
    public String insertThanksGive(HttpServletRequest req,
                                   @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            DrActivity drActivity = drActivityService
                    .selectObjectByName("感恩回馈");
            Date nowTime = new Date();
            if (Utils.isObjectEmpty(drActivity)) {
                br.setSuccess(false);
                br.setErrorCode("1001");
                br.setErrorMsg("未找到活动！");
                return JSON.toJSONString(br);
            }
            if (!(nowTime.after(drActivity.getStartTime()) && nowTime
                    .before(drActivity.getEndTime()))) {
                br.setSuccess(false);
                br.setErrorCode("1003");
                br.setErrorMsg("不在活动时间内！");
                return JSON.toJSONString(br);
            }
            String blessing = Utils
                    .stripHtmlLabel((String) map.get("blessing")).trim();
            if (Utils.isObjectEmpty(blessing)) {
                br.setSuccess(false);
                br.setErrorCode("1001");
                br.setErrorMsg("祝福语不能为空！");
                return JSON.toJSONString(br);
            }
            map.put("uid", m.getUid());
            br = jsGratitudeBlessingService.insert(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 拆感恩红包
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/splitThanksGiveRed", method = RequestMethod.POST)
    @ResponseBody
    public String splitThanksGiveRed(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            br = jsGratitudeBlessingService.splitRed(m.getUid());
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 双12送京东e卡
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/doubleTwelve")
    @ResponseBody
    public String doubleTwelve(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<BypCommodityDetail> bypCommodityDetails = bypCommodityDetailService
                    .doubleTwelve(m.getUid());
            map.put("bypCommodityDetails", bypCommodityDetails);
            br.setMap(map);
            br.setSuccess(true);
            log.info(bypCommodityDetails);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
    @RequestMapping(value = "/selectDoubleTwelve")
    @ResponseBody
    public String selectDoubleTwelve(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Integer cardAmount = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            map.put("code", m.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoService
                    .getDrChannelInfoListForMap(map);// 获取toFrom对应的渠道
            map.clear();
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel = channelList.get(0);// 获取渠道
                map.put("isCps",
                        channel.getType() == null ? 0 : channel.getType());// 渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);// 如果渠道不存在，则默认是非CPS
            }
            Map<String, BigDecimal> drProductInversts = bypCommodityDetailService
                    .selectDoubleTwelve(m.getUid());
            if (Utils.isObjectNotEmpty(drProductInversts)
                    && map.get("isCps").equals(0)) {
                Set<Entry<String, BigDecimal>> entrySet = drProductInversts
                        .entrySet();
                for (Entry<String, BigDecimal> entry : entrySet) {
                    String key = entry.getKey();
                    switch (key) {
                        case "hirtyDays":
                            key = "30";
                            cardAmount = CardRuleEnum.getCardAmount(key,
                                    entry.getValue());
                            map.put("jdCardHirty", cardAmount);
                            break;
                        case "threeScoreDays":
                            key = "60";
                            cardAmount = CardRuleEnum.getCardAmount(key,
                                    entry.getValue());
                            map.put("jdCardThreeScore", cardAmount);
                            break;
                        case "oneEightyDays":
                            key = "180";
                            cardAmount = CardRuleEnum.getCardAmount(key,
                                    entry.getValue());
                            map.put("jdCardOneEighty", cardAmount);
                            break;
                    }
                }
                map.put("row", drProductInversts);
                log.info("双十二活动查看" + map);
            } else {
                Map<String, Object> row1 = new HashMap<String, Object>();
                row1.put("hirtyDays", 0);
                row1.put("oneEightyDays", 0);
                row1.put("threeScoreDays", 0);
                map.put("row", row1);
                map.put("jdCardHirty", 0);
                map.put("jdCardThreeScore", 0);
                map.put("jdCardOneEighty", 0);
                log.info("双十二活动查看" + map);
            }
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 2017双旦杰活动抽奖次数查询(改版捕鱼达人)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getLotteryNum")
    @ResponseBody
    public String getLotteryNum(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Boolean convert = false;
        String convertvalue = "";
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        map.put("code", m.getToFrom());
        List<DrChannelInfo> channelList = drChannelInfoService
                .getDrChannelInfoListForMap(map);// 获取toFrom对应的渠道
        map.clear();
        if (!Utils.isEmptyList(channelList)) {
            DrChannelInfo channel = channelList.get(0);// 获取渠道
            map.put("isCps",
                    channel.getType() == null ? 0 : channel.getType());// 渠道类型，如果为null默认为非CPS
        } else {
            map.put("isCps", 0);// 如果渠道不存在，则默认是非CPS
        }
        try {
            convertvalue = String.valueOf(System.currentTimeMillis());
            String glutinousStartDate = redisClientTemplate
                    .getProperties("glutinousStartDate");
            String glutinousEndDate = redisClientTemplate
                    .getProperties("glutinousEndDate");
            Date nowDate = new Date();
            map.put("uid", m.getUid());
            map.put("startDate", Utils.format(glutinousStartDate, "yyyy-MM-dd"));
            map.put("endDate", Utils.format(glutinousEndDate, "yyyy-MM-dd"));
            map.put("aid", redisClientTemplate.getProperties("activityId"));
            Integer con = drMemberLotteryLogService.getDoubleAGGOneLottery(map);
            log.info("查询抽奖次数" + con);
            convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
            if (convert) {
                if (nowDate.after(Utils.parseDate(glutinousStartDate,
                        "yyyy-MM-dd HH:mm:ss"))
                        && nowDate.before(Utils.parseDate(glutinousEndDate, "yyyy-MM-dd HH:mm:ss"))) {
                    if (con <= 0 && map.get("isCps").equals(0)) {
                        DrMemberLotteryLog drm = new DrMemberLotteryLog(
                                Integer.parseInt(redisClientTemplate.getProperties("activityId")),
                                m.getUid(), new Date(), null);
                        // 每个用户可以获得一次抽奖机会, 插入一次抽奖机会
                        drMemberLotteryLogDAO.insert(drm);
                        // 给前端返回值是1
                        map.clear();
                        map.put("num", 1);
                        map.put("uid", m.getUid());
                        br.setMap(map);
                        br.setSuccess(true);
                        log.info(br);
                    } else {
                        // 查询用户所有抽奖次数
                        map.clear();
                        map.put("uid", m.getUid());
                        int count = drMemberLotteryLogService
                                .getDoubleAGGLotteryCountByUid(map);
                        if (count >= 1) {
                            map.put("num", count);
                            br.setMap(map);
                            br.setSuccess(true);
                            log.info(br);
                        } else {
                            map.put("num", 0);
                            br.setMap(map);
                            br.setSuccess(true);
                            log.info(br);
                        }
                    }
                }else {
                    br.setErrorMsg("刷新过快");
                    log.error(req);
                    return JSON.toJSONString(br);
                }

            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                log.info("2017双旦杰活动抽奖次数查询接口:" + br);
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            br.setErrorMsg("系统错误");
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("2017双旦杰活动抽奖次数查询接口:" + br);
        }finally {
            if(convert){
                redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(), convertvalue);//释放枷锁
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 2017元旦抽奖活动
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/againDoubleegg", method = RequestMethod.POST)
    @ResponseBody
    public String againDoubleegg(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }

        map.put("code", m.getToFrom());
        List<DrChannelInfo> channelList = drChannelInfoService
                .getDrChannelInfoListForMap(map);// 获取toFrom对应的渠道
        map.clear();
        if (!Utils.isEmptyList(channelList)) {
            DrChannelInfo channel = channelList.get(0);// 获取渠道
            map.put("isCps",
                    channel.getType() == null ? 0 : channel.getType());// 渠道类型，如果为null默认为非CPS
        } else {
            map.put("isCps", 0);// 如果渠道不存在，则默认是非CPS
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
                    "yyyy-MM-dd HH:mm:ss")) && map.get("isCps").equals(0)) {
                map.put("uid", m.getUid());
                map.put("atid", redisClientTemplate.getProperties("activityId"));// 4 元宵活动id
                map.put("classes", 1);// 1是虚拟2是实物
                int count = drMemberLotteryLogService
                        .getDoubleAGGLotteryCountByUid(map);
                if (count >= 1) {
                    br = jsActivityRewardService.getJsActivityRewardByAid(map);
                }
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                log.info("元旦抽奖活动已过期");
                return JSON.toJSONString(br);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
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
    public String getIntegralByUser(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> result = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        map.put("code", m.getToFrom());
        List<DrChannelInfo> channelList = drChannelInfoService
                .getDrChannelInfoListForMap(map);// 获取toFrom对应的渠道
        map.clear();
        if (!Utils.isEmptyList(channelList)) {
            DrChannelInfo channel = channelList.get(0);// 获取渠道
            map.put("isCps",
                    channel.getType() == null ? 0 : channel.getType());// 渠道类型，如果为null默认为非CPS
        } else {
            map.put("isCps", 0);// 如果渠道不存在，则默认是非CPS
        }

        try {
            String doubleDanStartDate = redisClientTemplate
                    .getProperties("doubleDanStartDate");
            String doubleDanEndDate = redisClientTemplate
                    .getProperties("doubleDanEndDate");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(doubleDanStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(doubleDanEndDate,
                    "yyyy-MM-dd HH:mm:ss")) && map.get("isCps").equals(0)) {
                DrMember drMember = drMemberService.selectByPrimaryKey(m
                        .getUid());
                BigDecimal user_integral = drMember.getUser_integral();
                result.put("user_integral", user_integral);
                br.setMap(result);
                br.setSuccess(true);
            } else {
                br.setErrorMsg("活动已过期");
                log.info("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error("获取用户拥有的积分", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 获取捕鱼达人活动奖品列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getFishingProduct", method = RequestMethod.POST)
    @ResponseBody
    public String getFishingProduct(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("activity_id", "by");//捕鱼活动
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
     * 双旦实物兑换
     *
     * @param req
     * @param uid 用户ID
     * @param pid 兑换的奖品ID
     * @return
     */
    @RequestMapping(value = "/insertConvertByUser", method = RequestMethod.POST)
    @ResponseBody
    public String insertConvertByUser(HttpServletRequest req,
                                      @RequestBody Map<String, Object> param) {
        String pid = param.get("pid").toString();
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectNotEmpty(m)) {
            if (param.containsKey("pid")
                    && Utils.isObjectNotEmpty(param.get("pid"))) {
                // 更新兑换礼品的相关代码
                br = bypCommodityDetailService.updateConvertGiftByUidAndPid(
                        m.getUid(), pid);
            } else {
                br.setMsg("没有参数");
                br.setErrorCode("1003");
                log.error("双旦兑换实物,未传参");
            }
        } else {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            log.error("未登录,请登录");
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
    public String queryAward(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            List<DrMemberLotteryLog> selectRecords = drMemberLotteryLogService
                    .selectRecords(m.getUid());
            map.put("selectRecords", selectRecords);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.info("双旦节日活动 查看我的中奖纪录", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
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
                List<BypCommodityDetail> selectTopIntegralLog = bypCommodityDetailService
                        .selectTopIntegralLog();
                map.put("selectTopIntegralLog", selectTopIntegralLog);
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
            log.error(e.getMessage());
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
    public String getMyIntegralLog(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            List<BypCommodityDetail> list = bypCommodityDetailService.selectMyIntegralLog(m.getUid());
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
                // 显示最近的20笔获奖用户名单
                List<JsCompanyAccountLog> jsCompanyAccountLog = jsCompanyAccountLogService
                        .selectBidPrize();
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
            log.error(e.getMessage());
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
    public String bidActivityRedByUid(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setErrorMsg("未登录");
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
                map.put("uid", m.getUid());
                // 显示用户中奖纪录
                List<JsCompanyAccountLog> jsCompanyAccountLogList = jsCompanyAccountLogService
                        .selectBidPrizeByUid(map);
                log.info(jsCompanyAccountLogList.toString());
                map.put("jsCompanyAccountLogList", jsCompanyAccountLogList);
                br.setMap(map);
                br.setSuccess(true);
            } else {
                br.setErrorMsg("活动已过期");
                br.setSuccess(false);
                br.setErrorCode("9997");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error("系统异常", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }
    /**
     * 满标奖
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/bidFullRedByUid", method = RequestMethod.POST)
    @ResponseBody
    public String bidFullRedByUid(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setErrorMsg("未登录");
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
                map.put("uid", m.getUid());
                // 显示用户中奖纪录
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
            log.error("系统异常", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error(e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 我的礼品中心
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getMyPresent", method = RequestMethod.POST)
    @ResponseBody
    public String getMyPresent(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {
                br.setErrorMsg("未登录");
                br.setSuccess(false);
                br.setErrorCode("9998");
                return JSON.toJSONString(br);
            }
            List<JsProductPrizeLog> logsList = jsProductPrizeLogService.selectLogByUid(m.getUid());
            map.clear();
            List<BypCommodityDetail> bypCommodityDetails = bypCommodityDetailService
                    .doubleTwelve(m.getUid());
            map.put("bypCommodityDetails", bypCommodityDetails);
            map.put("logsList", logsList);
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
     * 抢红包活动_抢
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/lootRedEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String lootRedEnvelope(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
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
            DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
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
                    map.put("uid", m.getUid());
                    map.put("fid", param.get("fid"));
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
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查有uid
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getUserEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String getUserEnvelope(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
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
                    map.put("uid", m.getUid());
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
            e.printStackTrace();
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
                if (((nowDate.after(startOne) && nowDate.before(endOne)) || (nowDate.after(startOnes) && nowDate.before(endOnes)) ||
                        (nowDate.after(startTwo) && nowDate.before(endTwo)) || (nowDate.after(startTwos) && nowDate.before(endTwos)) ||
                        (nowDate.after(startThree) && nowDate.before(endThree)) ||
                        (nowDate.after(startFour) && nowDate.before(endFour)))) {
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
            log.error("抢红包活动", e);
            e.printStackTrace();
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
            e.printStackTrace();
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
    public String getEveryoneJdCard(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = (DrMember) req.getSession().getAttribute(
                    ConfigUtil.FRONT_LOGIN_USER);
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
                    map.put("uid", member.getUid());
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
    public String getEveryoneTopFive(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        String thisDate = param.get("thisDate").toString();
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
     * 人人有份京东卡活动_TOP10VIP3榜单
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
     * @return
     */
    @RequestMapping(value = "/getTreasure", method = RequestMethod.POST)
    @ResponseBody
    public String getTreasure(HttpServletRequest req, @RequestBody Map<String, Object> param1) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer index = Integer.parseInt(param1.get("index").toString());
        String value = "";
        Boolean falg = false;
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        try {
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
                    map.put("uid", member.getUid());
                    map.put("index", index);
                    value = String.valueOf(System.currentTimeMillis());
                    falg = redisClientTemplate.tryLock(ConfigUtil.ABOUT_ACTIVITY + member.getUid(), 30, TimeUnit.SECONDS, false, value);// 枷锁
                    List<Map<String, Object>> list = drMemberFavourableDAO.selectByParam(map);
                    String id = list.get(index).get("id").toString();
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("activityId", id);
                    param.put("uid", member.getUid());
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
                redisClientTemplate.releaseLock(ConfigUtil.ABOUT_ACTIVITY + member.getUid(), value);
            }
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_抢
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/newLootRedEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String newLootRedEnvelope(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Date nowDate = new Date();
            DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
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
                map.put("uid", m.getUid());
                map.put("fid", param.get("fid"));
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
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 抢红包活动_查有uid
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/newGetUserEnvelope", method = RequestMethod.POST)
    @ResponseBody
    public String newGetUserEnvelope(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrMember member = drMemberService.selectByPrimaryKey(m.getUid());
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
                    map.put("uid", m.getUid());
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
            e.printStackTrace();
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
            Date nowDate = new Date();
            // Utils.getAddTime(600);
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("newlootRedEnvelopeStart");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("newlootRedEnvelopeEnd");
            if (nowDate.after(Utils.parseDate(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(bidActivityEndDate,
                    "yyyy-MM-dd HH:mm:ss"))) {
                Integer typeId = 7;
                map.put("type", typeId);
                map = drActivityParameterService.getEnvelope(map);
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
            log.error("抢红包活动", e);
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }

    /**
     * 扶贫助农 项目
     */
    @RequestMapping(value = "/toHelpFarmers")
    @ResponseBody
    public String toHelpFarmers(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<BypCommodity> list = new ArrayList<>();
        BypCommodityDetail detail = null;
        DrMember m = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
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
                map.put("drProductId", pid);
                //两个活动兑换数据，排名前三，和轮播排名，是否已经兑换袁橙，未登录和已登陆状态，获取爱心数量
                Integer prid = 0;
                //扶贫助农项目
                list = bypCommodityService.selectToHelpFarmers();
                map.put("product", list);
                for (BypCommodity commodity : list) {
                    if (commodity.getType() == 2) prid = commodity.getPrid();
                }
                if (Utils.isObjectNotEmpty(m)) {
                    detail = bypCommodityDetailDAO.selectToHelpFarmersBypCommodity(m.getUid(), prid);
                    m = drMemberService.selectByPrimaryKey(m.getUid());
                    map.put("heartNumbers", m.getUser_integral() == null ? 0 : m.getUser_integral());
                }
                if (detail == null) {//是否可以兑换
                    prid = 1;
                } else {
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
    public String toHelpFarmersInvest(HttpServletRequest request) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> investHistory = new ArrayList<>();
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
                map.put("beginTime", bidActivityStartDate);
                map.put("endTime", bidActivityEndDate);
                map.put("fullName", "公益活动");
                investHistory = drProductInvestService.selectNewInvestByActivityProduct(map);
                map.clear();
                map.put("investHistory", investHistory);
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
    public String toHelpFarmersTwenty(HttpServletRequest request, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapHistory = new ArrayList<>();
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
                map.put("activityId", "fpzn");
                map.put("type", 1);
                map.put("beginTime", bidActivityStartDate);
                map.put("endTime", bidActivityEndDate);
                //爱心赠送记录最新
                map.put("limit", 20);
                mapHistory = bypCommodityDetailService.selectToHelpFarmers(map);
                map.clear();
                map.put("heartHistory", mapHistory);
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
            log.error("/toHelpFarmersTwenty" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 扶贫助农 top3
     */
    @RequestMapping(value = "/toHelpFarmersTopThree")
    @ResponseBody
    public String toHelpFarmersTopThree(HttpServletRequest request) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapTop3 = new ArrayList<>();
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
                map.put("activityId", "fpzn");
                map.put("type", 1);
                //排名前三
                map.put("limit", 3);
                map.put("beginTime", bidActivityStartDate);
                map.put("endTime", bidActivityEndDate);
                mapTop3 = bypCommodityDetailService.selectToHelpFarmersTop(map);
                map.clear();
                map.put("top3", mapTop3);
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
            log.error("/toHelpFarmersTopThree" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 兑换猕猴桃
     */
    @RequestMapping("/forFruits")
    @ResponseBody
    public String forFruits(HttpServletRequest req, @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        String value = null;
        boolean lockFlag = false;
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(member)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
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
                    Integer number = 1;
                    if (map.get("number") != null) number = Integer.parseInt(map.get("number").toString());
                    if (map.containsKey("pid")
                            && Utils.isObjectNotEmpty(map.get("pid"))) {
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.getToHelpFarmers() + member.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag) {
                            // 更新兑换礼品的相关代码
                            br = bypCommodityDetailService.getFruitsByUidAndPid(
                                    member, map.get("pid").toString());
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
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动,兑换猕猴桃", e);
            log.error("/forFruits" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    /**
     * 捐赠爱心
     */
    @RequestMapping("/forLoves")
    @ResponseBody
    public String forLoves(HttpServletRequest req, @RequestBody Map<String, Object> map) {
        BaseResult br = new BaseResult();
        String value = null;
        boolean lockFlag = false;
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(member)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
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
                    Integer number = 1;
                    if (map.get("number") != null) number = Integer.parseInt(map.get("number").toString());
                    if (map.containsKey("pid")
                            && Utils.isObjectNotEmpty(map.get("pid"))) {
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.getToHelpFarmers() + member.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag) {
                            // 更新兑换礼品的相关代码
                            br = bypCommodityDetailService.getLoveByUidAndPid(
                                    member, map.get("pid").toString(), number);
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
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("扶贫助农活动,兑换爱心", e);
            log.error("/forLoves" + e.getMessage());
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
    public String getActivityFriendPage(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        Integer uid = null;
        try {
            if (Utils.isObjectNotEmpty(member))  uid = member.getUid();
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
    public String getMyFriendPage(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember member = (DrMember) req.getSession().getAttribute(
                ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(member)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            br = jsActivityFriendService.getMyFriendBonus(member.getUid());
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorMsg("邀请好友活动数据查询错误!");
            log.error("邀请好友活动数据查询错误！"+e.getMessage());
        }
        return JSON.toJSONString(br);
    }


}
