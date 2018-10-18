package com.jsjf.controller.product;

import com.SensorsAnalytics.SensorsAnalytics;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.activity.JsProductReservationLog;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.*;
import com.jsjf.model.system.SysBanner;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.activity.JsProductReservationLogService;
import com.jsjf.service.activity.JsProductReservationService;
import com.jsjf.service.claims.DrClaimsProjectService;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.member.*;
import com.jsjf.service.product.*;
import com.jsjf.service.system.SysBannerService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jsjf.service.vip.MemberVipInfoService;
import com.jzh.FuiouConfig;
import com.wechat.util.ModelPassivityMessageSendUtil;
import com.wechat.util.WeixinUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequestMapping("/product")
@Controller
public class ProductController {
    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private JsProductPrizeLogService jsProductPrizeLogService;
    @Autowired
    private JsCompanyAccountLogService jsCompanyAccountLogService;
    @Autowired
    private DrProductInfoService drProductInfoService;
    @Autowired
    private DrProductInvestService drProductInvestService;
    @Autowired
    private DrMemberFundsService drMemberFundsService;
    @Autowired
    private DrProductPicService drProductPicService;
    @Autowired
    private DrMemberService drMemberService;
    @Autowired
    private DrMemberFavourableService drMemberFavourableService;
    @Autowired
    private DrProductExtendService drProductExtendService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrClaimsProjectService drClaimsProjectService;
    @Autowired
    private JsActivityProductInvestInfoService jsActivityProductInvestInfoService;
    @Autowired
    private JsActivityProductService jsActivityProductService;
    @Autowired
    private JsProductReservationService jsProductReservationService;
    @Autowired
    private JsProductReservationLogService jsProductReservationLogService;
    @Autowired
    private DrMemberMsgService drMemberMsgService;
    @Autowired
    private JsNoviceContinueRecordService jsNoviceContinueRecordService;
    @Autowired
    private DrMemberLotteryLogService drMemberLotteryLogService;
    @Autowired
    private JsproductPrizeService jsproductPrizeService;
    @Autowired
    private SysBannerService sysBannerService;
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;
    @Autowired
    private DrMemberFundsRecordService drMemberFundsRecordService;
    @Autowired
    private DrClaimsLoanDAO drClaimsLoanDAO;
    @Autowired
    private MemberVipInfoService memberVipInfoService;

    /**
     * 续投接口
     *
     * @param req
     * @return
     */
    @RequestMapping("/addContinueReward")
    @ResponseBody
    public String addContinueReward(HttpServletRequest req, Integer uid, Integer period) {
        BaseResult br = new BaseResult();
        try {
            if (!Utils.isBlank(period) && !Utils.isBlank(uid)) {
                //查询投资用户新手标投资
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uid", uid);
                map.put("type", 1);//新手标
                map.put("pStatus", 5);//募集中
                map.put("isShow", 1);//显示
                map.put("order", " dpi.id desc ");
                List<DrProductInvest> list = drProductInvestService.selectInvestByMap(map);
                if (!Utils.isEmptyList(list)) {
                    boolean lockFlag = redisClientTemplate.tryLock("addContinueReward" + uid, 3, TimeUnit.SECONDS, true);
                    if (lockFlag) {
                        br = jsNoviceContinueRecordService.addContinueReward(list.get(0), period, Integer.valueOf(req.getParameter("channel")));
                    } else {
                        br.setErrorCode("1002");
                        br.setErrorMsg("续投失败");
                    }
                } else {
                    br.setErrorCode("1001");
                    br.setErrorMsg("没有投资");
                }
            } else {
                br.setErrorCode("1000");
                br.setErrorMsg("没有参数或没登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            br.setSuccess(false);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }

        return JSON.toJSONString(br);
    }

    /**
     * 新首续投 领奖红包及已领
     *
     * @param req
     * @return
     */
    @RequestMapping("/getContinueReward")
    @ResponseBody
    public String getContinueReward(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            if (!Utils.isBlank(uid)) {
                //查询投资用户新手标投资
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uid", uid);
                map.put("type", 1);//新手标
                map.put("pStatus", 5);//募集中
                map.put("isShow", 1);//显示
                map.put("order", " dpi.id desc ");
                List<DrProductInvest> list = drProductInvestService.selectInvestByMap(map);
                if (!Utils.isEmptyList(list)) {
                    //红包处理
                    map = jsNoviceContinueRecordService.getContinueReward(list.get(0));
                    br.setMap(map);
                    br.setSuccess(true);
                } else {
                    br.setErrorCode("1001");
                    br.setErrorMsg("没有投资");
                }
            } else {
                br.setErrorCode("1000");
                br.setErrorMsg("没有参数或没登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            br.setSuccess(false);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
        }
        return JSON.toJSONString(br);
    }

    /**
     * iphone7预约 投资
     *
     * @param req
     * @return
     */
    @RequestMapping("/getReservation")
    @ResponseBody
    public String getReservation(HttpServletRequest req, Integer prid, BigDecimal amount, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            if (!Utils.isBlank(prid) && Utils.isObjectNotEmpty(amount)
                    && !Utils.isBlank(uid)) {
                if (amount.compareTo(new BigDecimal("1000")) >= 0) {
                    JsProductReservation jpr = jsProductReservationService.selectReservationProduct(prid);
                    if (Utils.isObjectNotEmpty(jpr) && jpr.getStatus() == 1 &&
                            jpr.getStartTime().before(new Date()) &&
                            jpr.getEndTime().after(new Date())) {
                        JsProductReservationLog jsLog = new JsProductReservationLog(
                                prid, uid, amount, 0);
                        jsProductReservationLogService.insert(jsLog);
                        //发站内信
                        DrMemberMsg msg = new DrMemberMsg(uid, 0, 2, jpr.getName() + "预约通知", new Date(), 0, 0,
                                redisClientTemplate.getProperties("reservationMsg").replace("${name}", jpr.getName())
                                        .replace("${amount}", amount.toString()));
                        drMemberMsgService.insert(msg);

                        br.setSuccess(true);
                    } else {
                        br.setSuccess(false);
                        br.setErrorCode("1003");
                        br.setErrorMsg("预约结束");
                    }
                } else {
                    br.setSuccess(false);
                    br.setErrorCode("1002");
                    br.setErrorMsg("至少预约投资1000元");
                }
            } else {
                br.setSuccess(false);
                br.setErrorCode("1001");
                br.setErrorMsg("没有参数或未登录");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 我的幸运码
     *
     * @param req
     * @return
     */
    @RequestMapping("/getMyLuckCodes")
    @ResponseBody
    public String getMyLuckCodes(HttpServletRequest req, Integer uid, Integer id) {
        BaseResult br = new BaseResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            if (Utils.isBlank(uid)) {
                br.setErrorCode("9998");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            params.put("uid", uid);
            params.put("pid", id);
            br = drProductInfoService.getMyLuckCodes(params);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 我的幸运码and是否已开奖
     *
     * @param req
     * @return
     */
    @RequestMapping("/getMyLuckCodesAndStatus")
    @ResponseBody
    public String getMyLuckCodesAndStatus(HttpServletRequest req, Integer uid, Integer pid, Integer investId) {
        BaseResult br = new BaseResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            if (Utils.isBlank(uid)) {
                br.setErrorCode("9998");
                br.setMsg("uid不能为空");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            if (Utils.isBlank(pid)) {
                br.setErrorCode("9997");
                br.setErrorMsg("pid不能为空");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            params.put("uid", uid);
            params.put("pid", pid);
            params.put("investId", investId);
            br = drProductInfoService.selectjsActivityProductLuckCodesAndStatus(params);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setSuccess(false);
            e.printStackTrace();
        }

        return JSON.toJSONString(br);
    }

    /**
     * JS活动页
     *
     * @param req
     * @return
     */
    @RequestMapping("/getNewActivityProduct")
    @ResponseBody
    public String getNewActivityProduct(HttpServletRequest req, Integer uid) {
        BaseResult br = new BaseResult();
        br = drProductInfoService.getNewActivityProduct();
        //约标
        if (br.isSuccess()) {
            Map<String, Object> map = jsProductReservationService.
                    reservationProduct((DrProductInfo) br.getMap().get("result"), uid);
            ((Map<String, Object>) br.getMap()).putAll(map);
        }
        return JSON.toJSONString(br);
    }


    @RequestMapping(value = "/activityPrizeBanner", method = RequestMethod.POST)
    @ResponseBody
    public String activityPrizeBanner(HttpServletRequest req, PageInfo pi, Integer type, Integer uid) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 9);
            List<SysBanner> sysBanners = sysBannerService.indexBanner(map);
            map.clear();
            map.put("sysBanners", sysBanners);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("活动奖品banner获取失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);

    }

    /**
     * 聚划算 产品获取
     *
     * @param req
     * @param pi
     * @return
     */
    @RequestMapping("/getPeroidProList")
    @ResponseBody
    public String getPeroidProList(HttpServletRequest req, Integer[] statuses, Integer order, PageInfo pi) {
        BaseResult br = new BaseResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("statuses", statuses);
            params.put("order", order);
            br.setMap(drProductInfoService.selectPeriodProductList(params, pi));
            br.setSuccess(true);
        } catch (Exception e) {
            br.setErrorMsg("系统错误");
            br.setErrorMsg("9999");
        }
        return JSON.toJSONString(br);//聚划算
    }

    @RequestMapping(value = "/getPorductList", method = RequestMethod.POST)
    @ResponseBody
    public String getPorductList(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list = drProductInfoService.selectProductInfoByDeadLine();
            map.put("list", list);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("投资列表获取失败", e);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);

    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public String list(HttpServletRequest req, PageInfo pi, Integer type, Integer uid, Integer status) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            br = drProductInfoService.selectProductInfoByParams(type, pi, uid, status);
            if (!Utils.isBlank(uid)) {
                //砸蛋活动处理
                drProductInfoService.eggActivityRuleFilter(null, (List<Map<String, Object>>) ((PageInfo) br.getMap().get("page")).getRows(), uid);
            }
            String activity_60 = redisClientTemplate.getProperties("activity_60");
            String activity_180 = redisClientTemplate.getProperties("activity_180");
            String activityUrl = redisClientTemplate.getProperties("activityUrl");
            String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
            String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
            Date nowDate = new Date();
            Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
            Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
            if (nowDate.after(startDate) && nowDate.before(endDate)) {
                map.put("activity_60", activity_60);
                map.put("activity_180", activity_180);
                map.put("activityUrl", activityUrl);
            } else {
                map.put("activity_60", 0);
                map.put("activity_180", 0);
                map.put("activityUrl", "");
            }
            map.put("newHandLabel", redisClientTemplate.getProperties("newHandLabel"));
            ((Map<String, Object>) br.getMap()).putAll(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("我要投资列表获取失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/newList", method = RequestMethod.POST)
    @ResponseBody
    public String newList(HttpServletRequest req, PageInfo pi, Integer type, Integer uid, Integer status, Integer isActivity) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            br = drProductInfoService.selectProductInfoByParams(type, pi, uid, status, isActivity);
            /*if(!Utils.isBlank(uid)){
                //砸蛋活动处理
				drProductInfoService.eggActivityRuleFilter(null,(List<Map<String, Object>>)((PageInfo)br.getMap().get("page")).getRows(),uid);
			}
			String activity_60 = redisClientTemplate.getProperties("activity_60");
			String activity_180 = redisClientTemplate.getProperties("activity_180");
			String activityUrl = redisClientTemplate.getProperties("activityUrl");
			String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
			String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
			Date nowDate = new Date();
			Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
			Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
			if(nowDate.after(startDate)&&nowDate.before(endDate)){
				map.put("activity_60", activity_60);
				map.put("activity_180", activity_180);
				map.put("activityUrl", activityUrl);
			}else{
				map.put("activity_60", 0);
				map.put("activity_180", 0);
				map.put("activityUrl", "");
			}
			map.put("newHandLabel", redisClientTemplate.getProperties("newHandLabel"));
			((Map<String,Object>)br.getMap()).putAll(map);*/
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("我要投资列表获取失败", e);
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);

    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public String detail(HttpServletRequest req, Integer pid, Integer uid) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrProductInfo productInfo = null;//产品信息
        DrMemberFunds funds = new DrMemberFunds();//资金信息
        DrMember drMember = null;
        Date regDate = null;
        boolean isNewUser = true;
        try {
            if (Utils.isObjectEmpty(pid)) {
                br.setSuccess(false);
                br.setErrorMsg("产品不存在或已下架");
                br.setErrorCode("1001");
                return JSON.toJSONString(br);
            }
            map.put("id", pid);
            productInfo = drProductInfoService.selectProductDetailByPid(pid);
            if (Utils.isObjectEmpty(productInfo)) {
                br.setSuccess(false);
                br.setErrorMsg("产品不存在或已下架");
                br.setErrorCode("1001");
                return JSON.toJSONString(br);
            }
            List<DrMemberFavourable> list = null;
            Integer investTotal = 0;
            Integer rows = 0;
            Integer investeds = 0;
            drMember = new DrMember();
            if (Utils.isObjectNotEmpty(uid)) {
                drMember = drMemberService.selectByPrimaryKey(uid);
                regDate = drMember.getRegDate();
                funds = drMemberFundsService.selectDrMemberFundsByUid(uid);
                map.clear();
                map.put("uid", uid);
                map.put("status", 0);
                map.put("deadline", productInfo.getDeadline());
                if (productInfo.getType() == 1 || productInfo.getType() == 3) {//新手标
                    map.put("type", new Integer[]{3});
                } else {
                    Integer[] types = new Integer[3];
                    if (productInfo.getIsCash() == 1) {
                        types[1] = 1;
                    }
                    if (productInfo.getIsInterest() == 1) {
                        types[0] = 2;
                    }
                    if (productInfo.getIsDouble() == 1) {
                        Integer version = Integer.parseInt(req.getParameter("version").replace(".", "").toString());
                        if (version >= 112) {
                            types[2] = 4;
                        }
                    }
                    map.put("type", types);

                }
                if (productInfo.getIsCash() == 0 && productInfo.getIsInterest() == 0
                        && productInfo.getIsDouble() == 0 && (productInfo.getType() != 1 || productInfo.getType() != 3)) {
                    list = new ArrayList<DrMemberFavourable>();
                } else {
                    list = drMemberFavourableService.selectByParam(map);
                    //砸金蛋排除非本产品的加息券
                    Iterator<DrMemberFavourable> iter = list.iterator();
                    while (iter.hasNext()) {
                        DrMemberFavourable b = iter.next();
                        if (!Utils.isBlank(b.getPid()) && b.getPid().intValue() != productInfo.getId().intValue()) {
                            iter.remove();
                        }
                    }
                }
                map.clear();
                map.put("uid", uid);
                Integer[] statuses = new Integer[]{0, 1, 3, 4};
                map.put("statuses", statuses);
                if (productInfo.getType() == 1) {
                    //查询投资总计
                    investTotal = drProductInvestService.selectInvestLogCountByParam(map);
                    map.clear();
                    map.put("uid", uid);
                    map.put("statuses", new Integer[]{0, 1, 3, 4}); //2是投资失败的，可以继续投,4为划拨失败
                    map.put("type", 1);
                    map.put("phones", redisClientTemplate.getProperties("newHandPhone").split(","));
                    //查询投资新手标数量
                    rows = drProductInvestService.selectInvestLogCountByParam(map);
                    map.clear();
                    map.put("uid", uid);
                    map.put("statuses", new Integer[]{0, 1, 3, 4}); //2是投资失败的，可以继续投
                    map.put("type", 2);
                    //查询是否投资过普通标
                    investeds = drProductInvestService.selectInvestLogCountByParam(map);
                } else if (productInfo.getType() == 3) {
                    map.clear();
                    map.put("uid", uid);
                    map.put("statuses", new Integer[]{0, 1, 3, 4}); //2是投资失败的，可以继续投,4为划拨失败
                    map.put("type", 3);
                    //判断用户是不是新新手标以前注册的
                    String fuiouNehHand = redisClientTemplate.getProperties("fuiouNewHand");
                    Date fuiouNewHandTime = Utils.parse(fuiouNehHand, "yyyy-MM-dd HH:mm:ss");
                    isNewUser = regDate.after(fuiouNewHandTime) ? true : false;
                    map.put("phones", redisClientTemplate.getProperties("newHandPhone").split(","));
                    rows = drProductInvestService.selectInvestLogCountByParam(map);
                } else {
                    Integer[] barring = new Integer[]{1};
                    map.put("barring", barring);//
                    investTotal = drProductInvestService.selectInvestLogCountByParam(map);
                }
            }

            map.clear();
//			if(Utils.isObjectNotEmpty(productInfo.getExpireDate())){
//				productInfo.setExpireDate(Utils.getDayNumOfAppointDate(productInfo.getExpireDate(), 1));
//			}
            if (productInfo.getType() == 1 || productInfo.getType() == 3) {
                map.put("newHandInvested", rows > 0 ? true : false);
                map.put("isInvested", investeds > 0 ? true : false);
            }
            //存管新手标
            if (productInfo.getType() == 3) {
                map.put("fuiouNewHandInvested", rows > 0 ? true : false);
                map.put("isNewUser", isNewUser);
            }

            //活动参与人数量
            if (!Utils.isBlank(productInfo.getAtid())) {
                map.put("activityInvestTotal", jsActivityProductInvestInfoService.selectjsActivityProductInvestInfoCount(productInfo.getAtid()));
                //
                Map<String, Object> acMap = jsActivityProductService.selectActivityProduct(productInfo.getId());
                map.put("linkURL", redisClientTemplate.getProperties("iphone7linkURL"));//活动详情页link
                map.put("iphoneDetailUrl", redisClientTemplate.getProperties("iphoneDetailUrl"));//iphone7奖品详情链接
                map.put("appTitle", acMap.get("appTitle"));//移动端短标题
                //约标
                map.putAll(jsProductReservationService.reservationProduct(productInfo, uid));
            }
            //砸蛋活动处理
            List<DrProductInfo> listProduct = new ArrayList<>();
            listProduct.add(productInfo);
            drProductInfoService.eggActivityRuleFilter(listProduct, null, uid);

            map.put("info", productInfo);
            map.put("couponList", list);
            map.put("investTotal", investTotal);
            map.put("extendInfos", drProductExtendService.getDrProductExtendByPid(pid));
            map.put("investNums", drProductInvestService.selectInvestNumsByPid(pid));
            map.put("preProInvestNums", drProductInvestService.selectInvestNumsByPid(productInfo.getFid()));
            map.put("balance", funds.getBalance() == null ? BigDecimal.ZERO : funds.getBalance());
            map.put("balanceFuiou", funds.getFuiou_balance() == null ? BigDecimal.ZERO : funds.getFuiou_balance());
            //判断用户是否老用户
            boolean isOldUser = false;
            if (Utils.isObjectNotEmpty(uid)) {
                int result = drProductInvestService.selectIsOldUserById(uid);
                if (result > 0) {
                    isOldUser = true;
                }
            }
            map.put("isOldUser", isOldUser);
            String activity_60 = redisClientTemplate.getProperties("activity_60");
            String activity_180 = redisClientTemplate.getProperties("activity_180");
            String doubleEggrule = redisClientTemplate.getProperties("doubleEggrule");
            map.put("doubleEggrule", doubleEggrule);
            String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
            String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
            Date nowDate = new Date();
            Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
            Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
            if (nowDate.after(startDate) && nowDate.before(endDate)) {
                map.put("doubleEggrule", doubleEggrule);
                if (productInfo.getDeadline() != 60 && productInfo.getDeadline() != 180) {
                    map.put("specialRate", 0);
                } else if (productInfo.getDeadline() == 60) {
                    map.put("specialRate", activity_60);
                } else if (productInfo.getDeadline() == 180) {
                    map.put("specialRate", activity_180);
                }
            } else {
                map.put("doubleEggrule", "");
                map.put("specialRate", 0);
            }
            //投即送
            if (!Utils.isBlank(productInfo.getPrizeId())) {
                Map<String, Object> pmap = new HashMap<String, Object>();
                pmap.put("pid", productInfo.getId());
                pmap.put("id", productInfo.getPrizeId());
                JsProductPrize prize = jsproductPrizeService.selectJsPorudctPrize(pmap);
                prize.setInvestSendUrl(redisClientTemplate.getProperties("investSendUrl"));
                prize.setInvestSendDetailUrl(redisClientTemplate.getProperties("investSendUrl"));
                map.put("prize", prize);
            }
            //开始计息时间
            DrProductInfo spi = drProductInfoService.selectProductDetailById(pid);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE, 1);
            Date nowtime = null;
            if (spi.getFullDate() != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(spi.getFullDate());
                c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
                nowtime = c.getTime();
            } else {
                nowtime = calendar.getTime();
            }
            map.put("nowTime", nowtime);
            //新新手标不显示激活体验金标签
            if (productInfo.getAtid() != null || productInfo.getPrizeId() != null || productInfo.getType() == 3) {
                map.put("isShowLabel", false);
            } else {
                map.put("isShowLabel", drMemberFavourableService.selectIsShowCountByUid(uid) > 0 ? true : false);
            }
            if (Utils.isObjectNotEmpty(drMember)) {
                map.put("isFuiou", drMember.getIsFuiou());
                map.put("isAuth", FuiouConfig.isAuth(drMember.getAuth_st() != null ? drMember.getAuth_st() : null, 1));
            }
            //等本等息-聚划算 产品
            if (productInfo.getRepayType() == 3 || productInfo.getRepayType() == 4) {
                int period = productInfo.getRepayType() == 3 ? 7 : 30;
                map.put("firstRepayDate", Utils.getDayNumOfAppointDate(productInfo.getEstablish(), -period));//首次回款
                map.put("repayPeriod", productInfo.getDeadline() / period);//总期数
            }
            if (Utils.isObjectNotEmpty(drMember)) {
                map.put("tpwdFlag", StringUtils.isBlank(drMember.getTpassWord()) ? 0 : 1);
                map.put("realVerify", drMember.getRealVerify());
            }

            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(uid + "获取产品[" + pid + "]失败,参数：" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);

    }

    /**
     * 获取产品投资记录/图片/审核项目
     *
     * @param req
     * @param pid
     * @param type
     * @return
     */
    @RequestMapping(value = "/detail_info")
    @ResponseBody
    public String detail_info(HttpServletRequest req, Integer pid, Integer type) {
        BaseResult br = new BaseResult();

        if (Utils.isBlank(pid)) {
            log.error("未收到pid");
            br.setSuccess(false);
            br.setErrorCode("9999");
            return JSON.toJSONString(br);
        }
        List<String> pList = new ArrayList<String>() {{
            add("公司工商信息");
            add("营业执照");
            add("开户许可证");
            add("法人及股东身份证");
            add("公司章程");
            add("经营场所实地认证");
        }};
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DrProductInvest> investList = drProductInvestService.selectSimpleInvestLog(pid);
            map.clear();
            if (type == 2 || type == 3) {
                List<DrProductPic> picList = drProductPicService.getDrProductPicByPid(pid);
                map.put("picList", picList);
                List<String> projectList = drClaimsProjectService.selectListProjectByPid(pid);
                map.put("projectList", !Utils.isEmptyList(projectList) ? projectList : pList);
            }
            map.put("investList", investList);
            //插入快如风逻辑
            map.put("pid", pid);
            List<JsCompanyAccountLog> jsCompanyAccountLogList = jsCompanyAccountLogService.selectBidPrizeByUid(map);
            map.put("jsCompanyAccountLogList", jsCompanyAccountLogList);
            /**
             * 活动标产品详情
             */
            List<JsProductPrizeLog> prizeList = jsProductPrizeLogService.selectLogByPid(pid);
            map.put("prizeList", prizeList);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("产品详情TAB页读取数据错误" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);

    }

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    @ResponseBody
    public String invest(HttpServletRequest req, String tpwd, Integer pid, Integer uid,
                         BigDecimal amount, Integer fid) {
        BaseResult br = new BaseResult();
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//提交投资
        Map<String, Object> properties = new HashMap<String, Object>();
        SensorsAnalytics instance1 = SensorsAnalyticsUtil.getInstance();//支付投资项目
        Map<String, Object> properties1 = new HashMap<String, Object>();
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
        JSONObject json = new JSONObject();
        DrMember loginMember = drMemberService.selectByPrimaryKey(uid);
        String value = String.valueOf(System.currentTimeMillis());
        String values = "";
        String convertvalue = "";
        boolean lockFlag = false;
        Boolean lockFlags=false;
        Boolean convert=false;
        Boolean isSuccess=false;
        try {
            //判断客户是否第一次投资
            if (Utils.isObjectNotEmpty(uid)){
                Integer integer = drProductInvestService.selectNewInvest(uid);
                if(Utils.isObjectEmpty(integer)){
                    properties2.put("first_invest_time",new Date());
                    instance2.profileSet(String.valueOf(uid),true,properties2);
                    instance2.flush();
                }
            }
            //神策埋点
            DrProductInfo info1 = drProductInfoService.selectProductByPrimaryKey(Integer.parseInt(pid.toString()));
            properties.put("OperTime",Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            getType(properties, info1);
            properties.put("LoanId",info1.getCode());
            properties.put("ProjectName",info1.getFullName());
            DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.selectByPrimaryKey1(pid);
            properties.put("ProjectType",drClaimsLoan.getLoanUse());
            properties.put("ProjectDeadline",info1.getDeadline()+"");
            properties.put("Rate",info1.getRate());
            properties.put("IncomeType",drClaimsLoan.getRepayType()==1?"到期一次还本付息":drClaimsLoan.getRepayType());
            properties.put("ReleaseTime",Utils.format(info1.getEstablish(),"yyyy-MM-dd HH:mm:ss"));
            properties.put("AmountOfInvestment",amount);
            //dpi.rate*dpi.deadline*dpi.amount/360/100
            BigDecimal res=Utils.nwdDivide(Utils.nwdDivide(Utils.nwdMultiply(Utils.nwdMultiply(info1.getRate(),amount),
                    new BigDecimal(info1.getDeadline())),new BigDecimal(360)),new BigDecimal(100)).setScale(2,BigDecimal.ROUND_FLOOR);
            properties.put("IncomeOfInvestment",res);
            //埋点2
            properties1.put("OperTime",Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            getType(properties1, info1);
            properties1.put("LoanId",info1.getCode());
            properties1.put("ProjectName",info1.getFullName());
            properties1.put("ProjectType",drClaimsLoan.getLoanUse());
            properties1.put("ProjectDeadline",info1.getDeadline()+"");
            properties1.put("Rate",info1.getRate());
            properties1.put("IncomeType",drClaimsLoan.getRepayType()==1?"到期一次还本付息":drClaimsLoan.getRepayType());
            properties1.put("ReleaseTime",Utils.format(info1.getEstablish(),"yyyy-MM-dd HH:mm:ss"));
            if (info1.getSurplusAmount().subtract(amount).compareTo(BigDecimal.ZERO) == 0) {//满标情况
                properties1.put("IsFull",true);
                properties1.put("FullScaleTime",Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                properties1.put("FullScaleDuration",(Utils.getSpacings(info1.getEstablish())/60));
            }
            properties1.put("AmountOfInvestment",amount);
            //dpi.rate*dpi.deadline*dpi.amount/360/100
            BigDecimal res1=Utils.nwdDivide(Utils.nwdDivide(Utils.nwdMultiply(Utils.nwdMultiply(info1.getRate(),amount),
                    new BigDecimal(info1.getDeadline())),new BigDecimal(360)),new BigDecimal(100)).setScale(2,BigDecimal.ROUND_FLOOR);
            properties1.put("IncomeOfInvestment",res1);
            DrMemberFavourable drMemberFavourable = null;
            if (Utils.isObjectNotEmpty(fid)){
                properties1.put("CouponId",fid);
                drMemberFavourable = drMemberFavourableService.selectByPrimaryKey(fid);
                properties1.put("CouponAmount",drMemberFavourable.getAmount());
            }
            properties1.put("ActualPaidAmount",amount);
            //投资过几次
            Map<String ,Object> para=new HashMap<>();
            para.put("uid", uid);
            para.put("statuses", new Integer[] { 0, 1, 3, 4 }); // 2是投资失败的，可以继续投
            Integer integer = drProductInvestService.selectInvestCountByMap(para);
            properties1.put("InvestCount",integer);
            //判断投资 时间
            String investBanTime = redisClientTemplate.getProperties("investBanTime");
            String[] investBanTimes;
            if (!Utils.strIsNull(investBanTime) && Utils.nearDawnMinutes(new Date(), investBanTimes = investBanTime.split(","))) {
                br.setSuccess(false);
                br.setErrorMsg("23点" + (60 - Integer.parseInt(investBanTimes[0])) + "分钟 至 0点 " + Integer.parseInt(investBanTimes[1]) + "分钟 系统维护,请稍后继续");
                properties1.put("IsSuccess",isSuccess);
                return JSON.toJSONString(br);
            }
            Integer errorNums = StringUtils.isBlank(redisClientTemplate.get("error.tpwd.uid." + loginMember.getUid())) ? 0 :
                    Integer.parseInt(redisClientTemplate.get("error.tpwd.uid." + loginMember.getUid()));//密码错误次数
            if (Utils.isObjectEmpty(loginMember)) {
                br.setSuccess(false);
                br.setErrorCode("1009");
                properties1.put("IsSuccess",isSuccess);
                return JSON.toJSONString(br);
            }
            if (errorNums > 2) {
                br.setSuccess(false);
                br.setErrorCode("2001");
                properties1.put("IsSuccess",isSuccess);
                return JSON.toJSONString(br);
            }
            json.put("tpwd", tpwd);
            json.put("amount", amount);
            json.put("fid", fid);
            json.put("channel", req.getParameter("channel"));
            lockFlag = redisClientTemplate.tryLock("product.id." + pid, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
            if (lockFlag) {
                DrProductInfo info = (DrProductInfo) SerializeUtil.unserialize(redisClientTemplate.get(("product.info." + pid).getBytes()));
                if (Utils.isObjectEmpty(info)) {
                    info = drProductInfoService.selectProductByPrimaryKey(Integer.parseInt(pid.toString()));
                    if (info.getStartDate().after(new Date())) {
                        br.setSuccess(false);
                        br.setErrorMsg("产品未上架");
                        return JSON.toJSONString(br);
                    }
                    if (info.getStatus() == 5) {
                        redisClientTemplate.setex(("product.info." + pid).getBytes(), 600, SerializeUtil.serialize(info));
                    }
                }
                value = String.valueOf(System.currentTimeMillis());
                lockFlags = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + loginMember.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, values);
                if (lockFlags) {
                    Map<String, Object> param = new HashMap<>();
                    //查询累计额
                    param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                    param.put("uid",loginMember.getUid());
                    Integer sum = drMemberFundsRecordService.selectInvestSumByOnlineTime(param);
                    br = drProductInfoService.saveInvest(loginMember, info, json);
                    
                    
                    //投资成功后续业务
                    if (br.isSuccess()) {
                        drProductInfoService.investSuccessAfter(info, (Map<String, Object>) br.getMap(), loginMember);
                        convertvalue=String.valueOf(System.currentTimeMillis());
                        convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + loginMember.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                        if(convert){
                            taskIntegralRulesService.addPointsByInvest(loginMember.getUid(),sum,amount,info);
                        }
                        
                        //微信模板消息
                        String openId = drMemberService.queryOpenId(uid);
                        if(Utils.isObjectNotEmpty(openId)){
	                        ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
	                        BigDecimal nhlv = info1.getActivityRate().add(info1.getRate());
	                        if(Utils.isObjectNotEmpty(drMemberFavourable) && Utils.isObjectNotEmpty(drMemberFavourable.getRaisedRates()) 
	                        		&& drMemberFavourable.getRaisedRates().compareTo(new BigDecimal(0)) != 0){
	                        	nhlv = drMemberFavourable.getRaisedRates().add(nhlv);
	                        }
	                        BigDecimal sy = new BigDecimal(info1.getDeadline()).
	                        		multiply(amount.multiply(nhlv).
	                        				divide(new BigDecimal(100),2,RoundingMode.DOWN)).
	                        				setScale(2,RoundingMode.DOWN);
	                        String yqsy = sy.divide(new BigDecimal(360),2,RoundingMode.DOWN).toString();
	                        modelMessage.investJson(openId, amount.toString(), info1.getFullName(), 
	                        		info1.getDeadline().toString(), yqsy);
                        }
                    }
                }
            } else {
                br.setErrorCode("1011");//投资失败
                properties1.put("IsSuccess",isSuccess);
                return JSON.toJSONString(br);
            }
            if (Utils.isObjectEmpty(br.getErrorCode())){
                properties1.put("IsSuccess",true);
            }else {
                properties1.put("IsSuccess",false);
            }
        } catch (Exception e) {
            log.error("用户[" + uid + "]投标[" + pid + "]失败", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            properties1.put("IsSuccess",isSuccess);
        } finally {
            if (convert){
                redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + loginMember.getUid(), convertvalue);
            }
            if (lockFlags){
                redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash + loginMember.getUid(), values);
            }
            if(lockFlag){
                redisClientTemplate.releaseLock("product.id." + pid, value);
            }
        }
        try {
            instance.track(String.valueOf(uid), true, "SubmissionInvest" ,properties);
            instance1.track(String.valueOf(uid), true, "PayInvestmentProject" ,properties1);
            instance.flush();
            instance1.flush();
        }catch (Exception e){
            log.error("神策埋点报错!:"+e.getMessage());
        }
        return JSON.toJSONString(br);
    }

    private void getType(Map<String, Object> properties1, DrProductInfo info1) {
        if (3==info1.getType()){
            properties1.put("ProductType","存管新手标");
        }else if (5==info1.getType()){
            properties1.put("ProductType","体验标");
        }else if (Utils.isObjectNotEmpty(info1.getPrizeId())||Utils.isObjectNotEmpty(info1.getAtid())) {
            properties1.put("ProductType", "活动标");
        }else {
            properties1.put("ProductType","普通标");
        }
    }

    /**
     * 查询投资记录前100条数据
     *
     * @return
     */
    @RequestMapping(value = "/selectInvest", method = RequestMethod.POST)
    @ResponseBody
    public String selectInvest(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String invest100 = redisClientTemplate.get("invest100");
            if (!StringUtils.isNotEmpty(invest100)) {
                List<DrProductInfo> list = drProductInvestService.selectInvest();
                redisClientTemplate.setex("invest100", 1200, JSON.toJSONString(list).toString());
                map.put("invest100", invest100);
            } else {
                map.put("invest100", invest100);
            }
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("获取投资数据失败", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 查询推荐的产品，三个类型最早发布正在募集中的产品
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/selectProductInfoRecommend", method = RequestMethod.POST)
    @ResponseBody
    public String selectProductInfoRecommend(HttpServletRequest req) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
//			Object firstTime =  SerializeUtil.unserialize(redisClientTemplate.get(("productInfoRecommend.Time").getBytes()));//获取缓存时间

//			List<DrProductInfo> list = (List<DrProductInfo>)SerializeUtil.unserialize(redisClientTemplate.get(("productInfoRecommend").getBytes()));
//			if(Utils.isObjectEmpty(firstTime) ||  (System.currentTimeMillis() - (long)firstTime)/1000/60 > 10 || Utils.isObjectEmpty(list)){
//				System.out.println((System.currentTimeMillis() - (long)firstTime)/1000/60);

//				list =  drProductInfoService.selectProductInfoRecommend();

//				redisClientTemplate.setex(("productInfoRecommend").getBytes(), 600,SerializeUtil.serialize(list));//缓存时间
//				redisClientTemplate.setex(("productInfoRecommend.Time").getBytes(), 600,SerializeUtil.serialize(System.currentTimeMillis()));//缓存
//			}
//			result.put("info", list);
            result.put("info", drProductInfoService.selectProductInfoRecommend());
            result.put("success", true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("success", false);
            result.put("msg", "系统错误");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 微信签名
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/signWeChat", method = RequestMethod.POST)
    @ResponseBody
    public String signWeChat(String url) {
        BaseResult br = new BaseResult();
        try {
            String ticket = redisClientTemplate.get("ticket-wechat");
            if (StringUtils.isEmpty(ticket)) {
                ticket = WeixinUtil.getInstance().getTicket();
                redisClientTemplate.setex("ticket-wechat", 7000, ticket);
            }
            Map<String, Object> map = WeixinUtil.getInstance().sign(url, ticket);
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorMsg("请稍后重试！");
        }
        return JSON.toJSONString(br);

    }

    /**
     * 体验标详情页
     *
     * @param req
     * @param uid
     * @param channel
     * @return
     */
    @RequestMapping(value = "/experienceDetail", method = RequestMethod.POST)
    @ResponseBody
    public String experienceDetail(HttpServletRequest req, Integer uid, Integer channel) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrProductInfo productInfo = null;//体验标
        try {
            DrMember m = drMemberService.selectByPrimaryKey(uid);
            if (m == null) {
                br.setSuccess(false);
                br.setErrorCode("1000");
                br.setErrorMsg("用户id不存在");
                return JSON.toJSONString(br);
            }
			/*if(channel == null || channel != 2){
				br.setSuccess(false);
				br.setErrorCode("1000");
				br.setErrorMsg("渠道参数有误");
				return JSON.toJSONString(br);
			}*/
            productInfo = drProductInfoService.selectExperienceDetail();
            if (Utils.isObjectEmpty(productInfo)) {
                br.setSuccess(false);
                br.setErrorCode("1001");
                br.setErrorMsg("体验标不存在");
                return JSON.toJSONString(br);
            } else {
                map.put("Info", productInfo);//体验标信息
                //投资人数
                Integer investCount = drProductInvestService.selectExperInvestNumsByPid(productInfo.getId());
                map.put("investCount", investCount);
            }
            //体验金总数
            Map<String, Object> experience = drMemberFavourableService.selectExperSumAmountId(m.getUid());
            map.put("experienceAmount", experience);
            map.put("isFuiou", m.getIsFuiou());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(uid + "获取体验标失败,参数：" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);

    }

    /**
     * 体验标投资
     *
     * @param req
     * @param pid
     * @param uid
     * @param channel
     * @return
     */
    @RequestMapping(value = "/experienceInvest", method = RequestMethod.POST)
    @ResponseBody
    public String experienceInvest(HttpServletRequest req, Integer pid, Integer uid, Integer channel, String ids) {
        BaseResult br = new BaseResult();
        DrMember m = drMemberService.selectByPrimaryKey(uid);
        DrProductInfo drProductInfo = drProductInfoService.selectProductByPrimaryKey(pid);
        boolean lockFlag = false;
        try {
            if (m == null) {
                br.setSuccess(false);
                br.setErrorCode("1000");
                br.setErrorMsg("用户id不存在");
                return JSON.toJSONString(br);
            }
            if (Utils.strIsNull(ids)) {
                br.setSuccess(false);
                br.setErrorCode("1001");
                br.setErrorMsg("参数有误");
                return JSON.toJSONString(br);
            }

            lockFlag = redisClientTemplate.tryLock("product.exper.uid." + uid, 3, TimeUnit.SECONDS, false);

            if (lockFlag) {
                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("uid", m.getUid());
                maps.put("ids", ids.split(","));
                Map<String, Object> map = drMemberFavourableService.selectExperSumAmountId(m.getUid());

                if (Utils.isObjectEmpty(map) || Utils.isObjectEmpty(map.get("experAmount"))
                        || Utils.isObjectEmpty(map.get("ids")) || !ids.equals(map.get("ids"))) {
                    br.setSuccess(false);
                    br.setErrorCode("1002");
                    br.setErrorMsg("体验金不存在或数据错误");
                    return JSON.toJSONString(br);
                }
                if (drProductInfo == null) {
                    br.setSuccess(false);
                    br.setErrorCode("1003");
                    br.setErrorMsg("体验标不存在");
                    return JSON.toJSONString(br);
                } else if (drProductInfo.getType() != 5 || drProductInfo.getStatus() != 5) {
                    br.setSuccess(false);
                    br.setErrorCode("1004");
                    br.setErrorMsg("体验标已关闭");
                    return JSON.toJSONString(br);
                }
                br = drProductInfoService.experienceInvest(drProductInfo, uid, map, channel);
            } else {
                br.setSuccess(false);
                br.setErrorCode("1005");
                br.setErrorMsg("系统繁忙");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error(uid + "投资体验标失败,参数：" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            return JSON.toJSONString(br);
        } finally {
            if (lockFlag) {
                redisClientTemplate.del("product.exper.uid." + uid);
            }
        }
        //红包数量 和 是否认证
        Map<String, Object> map = new HashMap<String, Object>();
        Integer redTotal = 0;
        try {
            redTotal = drMemberFavourableService.selectRedCountByUid(m.getUid());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put("expireDate", Utils.getDayNumOfAppointDate(Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -(drProductInfo.getDeadline() + 1)));
        map.put("redTotal", redTotal);
        map.put("realverify", m.getRealVerify());
        br.setMap(map);
        return JSON.toJSONString(br);
    }

    /**
     * 判断是否完善信息
     *
     * @param req
     * @param investId
     * @return
     */
    @RequestMapping(value = "/isPerfect", method = RequestMethod.POST)
    @ResponseBody
    public String isPerfect(HttpServletRequest req, Integer investId) {
        BaseResult br = new BaseResult();
        try {
            Map<String, Object> map = drProductInvestService.selectIsPerfectByInvestId(investId);
            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorMsg("请稍后重试！");
        }
        return JSON.toJSONString(br);
    }

    /**
     * 按期限分类取产品
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/selectPorductClassifyByDeadline", method = RequestMethod.POST)
    @ResponseBody
    public String selectPorductClassifyByDeadline(HttpServletRequest req, Integer deadlineOne, Integer
            deadlineTwo, Integer deadlineThree, Integer amount) {
        BaseResult br = new BaseResult();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deadlineOne", deadlineOne);
            map.put("deadlineTwo", deadlineTwo);
            map.put("deadlineThree", deadlineThree);
            map.put("amount", amount);
            List<DrProductInfo> list = drProductInfoService.selectPorductClassifyByDeadline(map);

            map.clear();
            map.put("list", list);

            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorMsg("请稍后重试！");
            e.printStackTrace();
        }
        return JSON.toJSONString(br);
    }


}
