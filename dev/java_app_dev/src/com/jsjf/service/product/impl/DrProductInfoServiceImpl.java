package com.jsjf.service.product.impl;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.dao.activity.*;
import com.jsjf.dao.activity.JsProductReservationDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.*;
import com.jsjf.dao.product.*;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.dao.system.SysFuiouNoticeLogDAO;
import com.jsjf.model.activity.*;
import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.*;
import com.jsjf.model.product.*;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.member.DrCarryParamService;
import com.jsjf.service.member.DrMemberLotteryLogService;
import com.jsjf.service.member.DrMemberMsgService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.seq.SeqService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jsjf.service.vip.MemberVipInfoService;
import com.jzh.FuiouConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class DrProductInfoServiceImpl implements DrProductInfoService {
    private final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private DrProductInfoDAO drProductInfoDAO;
    @Autowired
    private DrProductInvestDAO drProductInvestDAO;
    @Autowired
    private DrMemberMsgDAO drMemberMsgDAO;
    @Autowired
    private DrMemberFundsDAO drMemberFundsDAO;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    private DrMemberFundsLogDAO drMemberFundsLogDAO;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
    @Autowired
    private SysMessageLogService sysMessageLogService;
    @Autowired
    private JsActivityProductInvestInfoDAO jsActivityProductInvestInfoDAO;
    @Autowired
    private DrSubjectInfoDAO drSubjectInfoDAO;
    @Autowired
    private JsActivityProductDAO jsActivityProductDAO;
    @Autowired
    private SeqService seqService;
    @Autowired
    private JsProductReservationDAO jsProductReservationDAO;
    @Autowired
    private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
    @Autowired
    private JsProductPrizeDAO jsProductPrizeDAO;
    @Autowired
    private JsProductPrizeLogDAO jsProductPrizeLogDAO;
    @Autowired
    private SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
    @Autowired
    private DrActivityDAO drActivityDAO;
    @Autowired
    private DrMemberLotteryLogService drMemberLotteryLogService;
    @Autowired
    private BypMemberIntegralDAO bypMemberIntegralDAO;
    @Autowired
    private DrMemberDAO drMemberDAO;
    @Autowired
    private MemberVipInfoService memberVipInfoService;

    private static ExecutorService cachedThreadPool = Executors
            .newFixedThreadPool(10);
    @Autowired
    public DrChannelInfoDAO drChannelInfoDAO;
    @Autowired
    private DrCarryParamService drCarryParamService;
    @Autowired
    private BypActivityIntegralDAO bypActivityIntegralDAO;
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;
    @Autowired
    private BypMemberInviteDao bypMemberInviteDao;
    @Autowired
    private DrMemberMsgService drMemberMsgService;
    @Autowired
    private DrMemberRecommendedDAO drMemberRecommendedDAO;

    @Override
    public BaseResult selectProductInfoByParams(Integer type, PageInfo pi,
                                                Integer uid, Integer status) {
        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer orderStr = new StringBuffer(
                " dpi.status,dpi.atid DESC,dpi.prizeId asc, dpi.isHot desc, dpi.startDate desc");// 新增了个活动产品置顶（atid）
        param.put("orderStr", orderStr);
        param.put("status", status);
        param.put("statuses", status == null ? new Integer[]{5, 6, 8, 9}
                : new Integer[]{status});
        /*
         * if(Utils.isObjectEmpty(uid)){ param.put("types", new
		 * Integer[]{1,2,3}); param.put("statuses", new Integer[] { 5, 6, 8, 9
		 * }); param.put("offset", pi.getPageInfo().getOffset());
		 * param.put("limit", pi.getPageInfo().getLimit()); }else{
		 * map.put("uid", uid); map.put("type", 1); List<DrProductInvest> list =
		 * drProductInvestDAO.checkProductType(map); if(list.size()>0){
		 * param.clear(); param.put("statuses", new Integer[] { 5, 6, 8, 9 });
		 * param.put("types", new Integer[]{2,3}); param.put("offset",
		 * pi.getPageInfo().getOffset()); param.put("limit",
		 * pi.getPageInfo().getLimit()); }else{ param.clear();
		 * param.put("types", new Integer[]{1,2,3}); param.put("statuses", new
		 * Integer[] { 5, 6, 8, 9 }); param.put("offset",
		 * pi.getPageInfo().getOffset()); param.put("limit",
		 * pi.getPageInfo().getLimit()); }
		 * 
		 * }
		 */
        param.put("offset", pi.getPageInfo().getOffset());
        param.put("limit", pi.getPageInfo().getLimit());
        if (Utils.isObjectNotEmpty(type)) {
            if (type.intValue() == 1) {
                param.put("types", new Integer[]{2});
                param.put("atid", 1);
                param.put("repayTypes", new Integer[]{1});
            } else if (type.intValue() == 2) {
                param.put("types", new Integer[]{2});
                param.put("atid", 2);
                param.put("repayTypes", new Integer[]{1});
            } else if (type == 3) {
                param.put("repayTypes", new Integer[]{2, 4});
            }
            List<Map<String, Object>> list = drProductInfoDAO
                    .selectProductInfoListByParam(param);
            Integer total = drProductInfoDAO
                    .selectProductInfoListCountByParam(param);
            pi.setRows(list);
            pi.setTotal(total);
            param.clear();
            param.put("page", pi);
        } else {

            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("maxRate", "7.5");
            maps.put("periodProInvestCount", 0);
            // 聚划算利息区间
            Map<String, Object> perMap = new HashMap<String, Object>();
            perMap.put("repayTypes", new Integer[]{3, 4});// 3等本等息按周回款4等本等息按月回款
            Map<String, Object> perMapRate = drProductInfoDAO
                    .getProductRateInterval(perMap);

            if (Utils.isObjectEmpty(perMapRate)) {
                maps.put("jhsFlag", false);
            } else {
                maps.putAll(perMapRate);
                maps.put("jhsFlag", true);
                // 聚划算
                param.put("repayTypes", new Integer[]{2, 3, 4});
                List<Map<String, Object>> list = drProductInfoDAO
                        .selectProductInfoListByParam(param);
                Integer total = drProductInfoDAO
                        .selectProductInfoListCountByParam(param);
                // 上期被多少人瓜分
                perMap.clear();
                perMap.put("repayTypes", new Integer[]{3, 4});// 3等本等息按周回款4等本等息按月回款
                perMap.put("statuses", new Integer[]{5, 6, 8, 9});//
                perMap.put("orderStr", "ORDER BY status asc,startDate desc ");
                perMap.put("offset", 0);
                perMap.put("limit", 1);
                List<DrProductInfo> periodProList = drProductInfoDAO
                        .selectProductbyMap(perMap);
                maps.put("periodProInvestCount", 0);
                if (!Utils.isEmptyList(periodProList)) {
                    perMap.clear();
                    perMap.put("pid", periodProList.get(0).getId());
                    maps.put("periodProInvestCount",
                            drProductInvestDAO.selectInvestCountByMap(perMap));
                }
                pi.setRows(list);
                pi.setTotal(total);
                maps.put("pageJHS", pi);
            }
            // 优选产品
            param.put("types", new Integer[]{2});

            param.put("atid", 2);
            param.put("repayTypes", new Integer[]{1});

            List<Map<String, Object>> list1 = drProductInfoDAO
                    .selectProductInfoListByParam(param);
            Integer total1 = drProductInfoDAO
                    .selectProductInfoListCountByParam(param);
            PageInfo pi1 = new PageInfo();
            pi1.setRows(list1);
            pi1.setTotal(total1);
            maps.put("page", pi1);

            param.clear();
            param.putAll(maps);

        }
        /*
         * if (Utils.isObjectNotEmpty(type)) { if (type.intValue() == 1) {
		 * param.put("status", 5); param.put("type", type); param.put("offset",
		 * pi.getPageInfo().getOffset()); param.put("limit", 1); } else {
		 * param.put("statuses", new Integer[] { 5, 6, 8, 9 });
		 * param.put("type", type); param.put("offset",
		 * pi.getPageInfo().getOffset()); param.put("limit",
		 * pi.getPageInfo().getLimit()); } } else { param.put("types", new
		 * Integer[]{2,3}); param.put("statuses", new Integer[] { 5, 6, 8, 9 });
		 * param.put("offset", pi.getPageInfo().getOffset()); param.put("limit",
		 * pi.getPageInfo().getLimit()); }
		 */

        try {
            // 90天活动标信息
            Map<String, Object> activityProduct = drProductInfoDAO
                    .getActivityProductInfo();
            activityProduct.put("activityRate",
                    redisClientTemplate.getProperties("activityRatePList"));
            activityProduct.put("rate",
                    redisClientTemplate.getProperties("ratePList"));
            activityProduct.put("deadline",
                    redisClientTemplate.getProperties("deadlinePList"));
            activityProduct.put("maxRate",
                    redisClientTemplate.getProperties("maxRatePList"));
            activityProduct.put("title",
                    redisClientTemplate.getProperties("titlePList"));
            param.put("activityProduct", activityProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        br.setMap(param);

        return br;
    }

    @Override
    public BaseResult selectProductInfoByParams(Integer type, PageInfo pi, Integer uid, Integer status, Integer isActivity) {

        BaseResult br = new BaseResult();
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        isActivity = isActivity == null ? 0 : isActivity;
        if (1 == isActivity) {
            StringBuffer orderStr = new StringBuffer(
                    " dpi.status,dpi.atid DESC,dpi.prizeId asc, dpi.isHot desc, dpi.startDate desc");// 新增了个活动产品置顶（atid）
            param.put("orderStr", orderStr);
            param.put("status", status);
            param.put("statuses", status == null ? new Integer[]{5, 6, 8, 9}
                    : new Integer[]{status});
            param.put("offset", pi.getPageInfo().getOffset());
            param.put("limit", pi.getPageInfo().getLimit());
            param.put("types", new Integer[]{2});
            param.put("atid", 1);
            param.put("repayTypes", new Integer[]{1});
            List<Map<String, Object>> list = drProductInfoDAO
                    .selectProductInfoListByParam(param);
            Integer total = drProductInfoDAO
                    .selectProductInfoListCountByParam(param);
            pi.setRows(list);
            pi.setTotal(total);
            param.clear();
            param.put("page", pi);
        } else {
            StringBuffer orderStr = new StringBuffer(
                    " dpi.status,dpi.atid DESC,dpi.prizeId asc, dpi.isHot desc, dpi.startDate desc");// 新增了个活动产品置顶（atid）
            param.put("orderStr", orderStr);
            param.put("status", status);
            param.put("statuses", status == null ? new Integer[]{5, 6, 8, 9}
                    : new Integer[]{status});
            param.put("offset", pi.getPageInfo().getOffset());
            param.put("limit", pi.getPageInfo().getLimit());
            if (Utils.isObjectNotEmpty(type)) {
                if (type.intValue() == 1) {
                    param.put("types", new Integer[]{2});
                    param.put("atid", 1);
                    param.put("repayTypes", new Integer[]{1});
                } else if (type.intValue() == 2) {
                    param.put("types", new Integer[]{2});
                    param.put("atid", 2);
                    param.put("repayTypes", new Integer[]{1});
                } else if (type == 3) {
                    param.put("repayTypes", new Integer[]{2, 4});
                }
                List<Map<String, Object>> list = drProductInfoDAO
                        .selectProductInfoListByParam(param);
                Integer total = drProductInfoDAO
                        .selectProductInfoListCountByParam(param);
                pi.setRows(list);
                pi.setTotal(total);
                param.clear();
                param.put("page", pi);
            } else {

                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("maxRate", "7.5");
                maps.put("periodProInvestCount", 0);
                // 聚划算利息区间
                Map<String, Object> perMap = new HashMap<String, Object>();
                perMap.put("repayTypes", new Integer[]{3, 4});// 3等本等息按周回款4等本等息按月回款
                Map<String, Object> perMapRate = drProductInfoDAO
                        .getProductRateInterval(perMap);

                if (Utils.isObjectEmpty(perMapRate)) {
                    maps.put("jhsFlag", false);
                } else {
                    maps.putAll(perMapRate);
                    maps.put("jhsFlag", true);
                    // 聚划算
                    param.put("repayTypes", new Integer[]{2, 3, 4});
                    List<Map<String, Object>> list = drProductInfoDAO
                            .selectProductInfoListByParam(param);
                    Integer total = drProductInfoDAO
                            .selectProductInfoListCountByParam(param);
                    // 上期被多少人瓜分
                    perMap.clear();
                    perMap.put("repayTypes", new Integer[]{3, 4});// 3等本等息按周回款4等本等息按月回款
                    perMap.put("statuses", new Integer[]{5, 6, 8, 9});//
                    perMap.put("orderStr", "ORDER BY status asc,startDate desc ");
                    perMap.put("offset", 0);
                    perMap.put("limit", 1);
                    List<DrProductInfo> periodProList = drProductInfoDAO
                            .selectProductbyMap(perMap);
                    maps.put("periodProInvestCount", 0);
                    if (!Utils.isEmptyList(periodProList)) {
                        perMap.clear();
                        perMap.put("pid", periodProList.get(0).getId());
                        maps.put("periodProInvestCount",
                                drProductInvestDAO.selectInvestCountByMap(perMap));
                    }
                    pi.setRows(list);
                    pi.setTotal(total);
                    maps.put("pageJHS", pi);
                }
                // 优选产品
                param.put("types", new Integer[]{2});

                param.put("atid", 2);
                param.put("repayTypes", new Integer[]{1});

                List<Map<String, Object>> list1 = drProductInfoDAO
                        .selectProductInfoListByParam(param);
                Integer total1 = drProductInfoDAO
                        .selectProductInfoListCountByParam(param);
                PageInfo pi1 = new PageInfo();
                pi1.setRows(list1);
                pi1.setTotal(total1);
                maps.put("page", pi1);

                param.clear();
                param.putAll(maps);

            }
            try {
                // 90天活动标信息
                Map<String, Object> activityProduct = drProductInfoDAO
                        .getActivityProductInfo();
                activityProduct.put("activityRate",
                        redisClientTemplate.getProperties("activityRatePList"));
                activityProduct.put("rate",
                        redisClientTemplate.getProperties("ratePList"));
                activityProduct.put("deadline",
                        redisClientTemplate.getProperties("deadlinePList"));
                activityProduct.put("maxRate",
                        redisClientTemplate.getProperties("maxRatePList"));
                activityProduct.put("title",
                        redisClientTemplate.getProperties("titlePList"));
                param.put("activityProduct", activityProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        br.setMap(param);
        return br;
    }

    @Override
    public DrProductInfo selectProductDetailByPid(Integer pid) {
        return drProductInfoDAO.selectProductDetailByPid(pid);
    }

    @Override
    public DrProductInfo selectProductDetailById(Integer pid) {
        return drProductInfoDAO.selectProductDetailById(pid);
    }

    @Override
    public BaseResult saveInvest(DrMember loginMember,
                                 final DrProductInfo pInfo, JSONObject json) throws Exception {
        BaseResult br = new BaseResult();
        Map<String, Object> backMap = new HashMap<String, Object>(); // 返回参数的map
        String tpwd = json.getString("tpwd");
        BigDecimal amount = json.getBigDecimal("amount");
        Integer errorNums = StringUtils.isBlank(redisClientTemplate
                .get("error.tpwd.uid." + loginMember.getUid())) ? 0 : Integer
                .parseInt(redisClientTemplate.get("error.tpwd.uid."
                        + loginMember.getUid()));// 密码错误次数

        DrMemberFunds funds = drMemberFundsDAO
                .queryDrMemberFundsByUid(loginMember.getUid());
        DrMember drMember = drMemberDAO.selectByPrimaryKey(loginMember.getUid());
        BigDecimal annualInvestment = null;
        // boolean auth = FuiouConfig.isAuth(loginMember.getAuth_st(), 1);
        // if (!loginMember.getTpassWord().equals(tpwd) && auth) {// 交易密码不正确
        if (!loginMember.getTpassWord().equals(tpwd)) {// 交易密码不正确
            if (errorNums >= 2) {
                br.setSuccess(false);
                redisClientTemplate.setex(
                        "error.tpwd.uid." + loginMember.getUid(), 3600,
                        String.valueOf(errorNums + 1));
                br.setErrorCode("2001");
                return br;
            }
            Integer seconds = Utils.getDateSecondsSub(
                    Utils.format(new Date(), "yyyy-MM-dd 23:59:59"),
                    Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            redisClientTemplate.setex("error.tpwd.uid." + loginMember.getUid(),
                    seconds, String.valueOf(errorNums + 1));
            br.setSuccess(false);
            br.setErrorCode("1001");
            return br;
        } else if (Utils.isObjectEmpty(pInfo) || pInfo.getStatus() != 5) {// 产品已募集完成
            br.setSuccess(false);
            br.setErrorCode("1002");
            return br;
        } else if ((pInfo.getType() == 2 || pInfo.getType() == 3)
                && pInfo.getSurplusAmount().compareTo(amount) < 0) {// 剩余可投资金额不足
            br.setSuccess(false);
            br.setErrorCode("1003");
            return br;
        } else if (pInfo.getLeastaAmount().compareTo(amount) > 0) {// 投资金额小于起投金额
            br.setSuccess(false);
            br.setErrorCode("1004");
            return br;
        } else if (amount.remainder(pInfo.getIncreasAmount()).compareTo(
                BigDecimal.ZERO) != 0) {// 投资金额非递增金额的整数倍
            br.setSuccess(false);
            br.setErrorCode("1005");
            return br;
        } else if (amount.compareTo(pInfo.getMaxAmount()) > 0) {// 投资金额大于个人可投资金额
            br.setSuccess(false);
            br.setErrorCode("1006");
            return br;
        } else if (funds.getFuiou_balance().compareTo(amount) < 0) {
            br.setSuccess(false);
            br.setErrorCode("1007");
            return br;
        }
        // 新手标
        Map<String, Object> map = new HashMap<String, Object>();
        if (pInfo.getType() == 1) {
            map.put("uid", loginMember.getUid());
            map.put("statuses", new Integer[]{0, 1, 3, 4}); // 2是投资失败的，可以继续投
            map.put("type", 1);
            map.put("phones", redisClientTemplate.getProperties("newHandPhone")
                    .split(","));
            Integer rows = drProductInvestDAO.selectInvestLogCountByParam(map);
            if (rows > 0) {
                br.setSuccess(false);
                br.setErrorCode("1008");
                return br;
            }
        }
        // 存管新手标
        Map<String, Object> param = new HashMap<String, Object>();
        if (pInfo.getType() == 3) {
            param.put("uid", loginMember.getUid());
            param.put("statuses", new Integer[]{0, 1, 3, 4}); // 2是投资失败的，可以继续投
            param.put("type", 3);
            param.put("phones",
                    redisClientTemplate.getProperties("newHandPhone")
                            .split(","));
            Integer rows = drProductInvestDAO
                    .selectInvestLogCountByParam(param);
            // 判断用户是不是新新手标以前注册的
            String fuiouNehHand = redisClientTemplate
                    .getProperties("fuiouNewHand");
            Date fuiouNewHandTime = Utils.parse(fuiouNehHand,
                    "yyyy-MM-dd HH:mm:ss");
            boolean isNewHand = loginMember.getRegDate()
                    .after(fuiouNewHandTime) ? true : false;
            if (!isNewHand) {
                br.setSuccess(false);
                br.setErrorCode("1008");
                return br;
            }
            if (rows > 0) {
                br.setSuccess(false);
                br.setErrorCode("1008");
                return br;
            }
        }
        DrMemberFavourable dmf = null;
        Integer fid = json.getInteger("fid");
        if (Utils.isObjectNotEmpty(fid)) {
            DrMemberFavourable drMemberFavourable = drMemberFavourableDAO
                    .selectByPrimaryKey(fid);
            if (pInfo.getType() != 5) {
                if (pInfo.getIsDouble() == 0
                        && drMemberFavourable.getType() == 4) {
                    br.setErrorCode("1015");
                    br.setErrorMsg("翻倍券只能用于翻倍标");
                    return br;
                }
                if (pInfo.getIsInterest() == 0
                        && drMemberFavourable.getType() == 2) {
                    br.setErrorCode("1016");
                    br.setErrorMsg("该标不能使用加息劵");
                    return br;
                }
                if (pInfo.getIsCash() == 0 && drMemberFavourable.getType() == 1) {
                    br.setErrorCode("1017");
                    br.setErrorMsg("该标不能使用红包");
                    return br;
                }
            }
        }
        if (fid != null) {// 使用优惠券
            map.clear();
            map.put("uid", loginMember.getUid());
            map.put("status", 0);
            map.put("amount", amount);
            map.put("id", fid);
            map.put("deadline", pInfo.getDeadline());
            if (pInfo.getType() != 1) {// 5是体验金,体验金只有体验标可用
                map.put("time", new Date());
            }
            List<DrMemberFavourable> list = drMemberFavourableDAO
                    .getMemberFavourableByParam(map);
            dmf = list.size() > 0 ? list.get(0) : null;
            if (dmf == null || 3 == dmf.getType()) {
                br.setSuccess(false);
                br.setErrorCode("1010");
                return br;
            } else {
                if (dmf.getType() == 4 && dmf.getSource() == 0) {// 非首投用户，不能使用系统赠送的翻倍券
                    map.clear();
                    map.put("uid", loginMember.getUid());
                    map.put("barring", new Integer[]{1, 5});
                    Integer investCount = drProductInvestDAO
                            .selectInvestCountByMap(map);
                    if (investCount > 0) {
                        br.setSuccess(false);
                        br.setErrorCode("1012");
                        return br;
                    }
                }
            }
        }
        // 恒丰存管-冻结接口
        boolean fuiouLockFlag = false;
        // 预授权合同号
        String contract_no = null;
        String mchnt_txn_ssn = null;
        fuiouLockFlag = redisClientTemplate.tryLock(
                "product.invest.fuiou_acnt." + loginMember.getMobilephone(), 3,
                TimeUnit.SECONDS, false);
        Map<String, Object> paramsPreAuth = new HashMap<String, Object>();
        try {
            if (fuiouLockFlag) {
                // 2.0参数
                paramsPreAuth.put("uid", loginMember.getUid());
                paramsPreAuth.put("cust_no", loginMember.getMobilephone());
                paramsPreAuth.put("amt", amount.toString());
                paramsPreAuth.put("rem", "投资" + pInfo.getFullName() + "|pid:"
                        + pInfo.getId());
                BaseResult baseResult = FuiouConfig.freeze(paramsPreAuth);
                // 授权用户，直连冻结
                if (!baseResult.isSuccess()) {
                    return baseResult;
                } else {
                    String resp_code = (String) baseResult.getMap().get(
                            "resp_code");
                    if (!resp_code.equals("0000")) {
                        baseResult.setErrorCode((String) baseResult.getMap()
                                .get("resp_code"));
                        baseResult
                                .setErrorMsg("存管返回:"
                                        + (String) baseResult.getMap().get(
                                        "resp_desc"));
                        baseResult.setMap(null);
                        baseResult.setSuccess(false);
                        return baseResult;
                    }
                }
                mchnt_txn_ssn = baseResult.getMap().get("mchnt_txn_ssn")
                        .toString();
                // paramsPreAuth.put("mchnt_txn_ssn", mchnt_txn_ssn);

            } else {
                br.setErrorCode("1021");
                br.setErrorMsg("系统繁忙,稍后重试");
                br.setSuccess(false);
                return br;
            }
            // 更新产品信息
            pInfo.setAlreadyRaiseAmount(pInfo.getAlreadyRaiseAmount().add(
                    amount));
            pInfo.setSurplusAmount(pInfo.getSurplusAmount().subtract(amount));
            if (pInfo.getType() != 1 && pInfo.getType() != 4
                    && pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {// 募集完成
                // 新手标始终处于募集中
                pInfo.setStatus(6);
                pInfo.setFullDate(new Date());
                System.out.println("update募集完成==>>");
                System.out.println("new date==>>"
                        + Utils.format(new Date(), "yyyy-MM-dd"));
                pInfo.setEstablish(Utils.getDayNumOfAppointDate(Utils.format(
                        Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"),
                        -1));
                pInfo.setExpireDate(Utils.getDayNumOfAppointDate(
                        pInfo.getEstablish(), 0 - pInfo.getDeadline()));
                System.out.println("pInfo establish==>>"
                        + Utils.format(pInfo.getEstablish(), "yyyy-MM-dd"));
                redisClientTemplate.del("product.info." + pInfo.getId());
            }

            redisClientTemplate.setex(
                    ("product.info." + pInfo.getId()).getBytes(), 600,
                    SerializeUtil.serialize(pInfo));
            drProductInfoDAO.updateProductSelective(pInfo);
            // 将优惠券置为已使用
            if (dmf != null) {
                dmf.setStatus(1);
                dmf.setUsedTime(new Date());
                drMemberFavourableDAO.updateFavourableStatus(dmf);
            }

            // 插入投资记录
            DrProductInvest invest = new DrProductInvest();
            invest.setAmount(amount);
            invest.setUid(loginMember.getUid());
            invest.setJoinType(json.getInteger("channel"));// 投资渠道
            invest.setStatus(0);
            invest.setPid(pInfo.getId());
            invest.setFid(fid);
            invest.setContract_no(contract_no);
            invest.setMchnt_txn_ssn(mchnt_txn_ssn);
            BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(pInfo
                    .getRate().add(pInfo.getActivityRate()), 100), 360);
            invest.setInterest(amount.multiply(dayRate)
                    .multiply(new BigDecimal(pInfo.getDeadline()))
                    .setScale(2, BigDecimal.ROUND_FLOOR));
            invest.setInvestTime(new Date());
            boolean isDoubleEgg = false;
            int result = drProductInvestDAO.selectIsOldUserById(loginMember.getUid());
            String activityUrl = redisClientTemplate
                    .getProperties("activityUrl");
            DrMemberLotteryLog drMemberLotteryLog = new DrMemberLotteryLog();
            // 是否为老用户
            /*if (result > 0) {
				String activityEndDate = redisClientTemplate
						.getProperties("activityEndDate");
				String activityStartDate = redisClientTemplate
						.getProperties("activityStartDate");
				String activity_60 = redisClientTemplate
						.getProperties("activity_60");
				String activity_180 = redisClientTemplate
						.getProperties("activity_180");
				Date nowDate = new Date();
				Date startDate = Utils.parse(activityStartDate,
						"yyyy-MM-dd HH:mm:ss");
				Date EndDate = Utils.parse(activityEndDate,
						"yyyy-MM-dd HH:mm:ss");
				// 活动未过期有机会
				if (nowDate.before(EndDate) && nowDate.after(startDate)) {
					// 投资非新手标和体验标的产品有机会拆双蛋
					if (pInfo.getType() != 1 && pInfo.getType() != 5) {
						// 投资标时间60天或180天有机会
						if (pInfo.getDeadline() == 60
								|| pInfo.getDeadline() == 180) {
							if (pInfo.getDeadline() == 60) {
								invest.setSpecialRate(new BigDecimal(
										activity_60));
							} else if (pInfo.getDeadline() == 180) {
								invest.setSpecialRate(new BigDecimal(
										activity_180));
							}
							// 投资金额大于1000有机会拆双蛋
							if (amount.compareTo(new BigDecimal(1000)) == 0
									|| amount.compareTo(new BigDecimal(1000)) == 1) {
								if (amount.compareTo(new BigDecimal(10000)) == -1) {
									drMemberLotteryLog.setAid(1);
								} else if (amount.compareTo(new BigDecimal(
										100000)) == -1) {
									drMemberLotteryLog.setAid(2);
								} else if (amount.compareTo(new BigDecimal(
										100000)) == 0
										|| amount.compareTo(new BigDecimal(
												100000)) == 1) {
									drMemberLotteryLog.setAid(3);
								}
								isDoubleEgg = true;
							}
						}
					}
				}
			}*/
            if (pInfo.getFullName().contains("公益活动")) {
                //投资增加爱心
                BigDecimal heartCount = amount.divide(new BigDecimal(1000).setScale(0));
                drMember.setUser_integral(drMember.getUser_integral().add(heartCount));
                drMemberDAO.updateByPrimaryKey(drMember);
            }
            //是否存储用户红利
            boolean inviteMoeny = false;
            Map<String, Object> returnMoneyMap = new HashMap<>();
            BypMemberInvite invite = new BypMemberInvite();
            if (pInfo.getType() == 2 && pInfo.getPrizeId() == null) {
                taskIntegralRulesService.addPoints(loginMember.getUid(), SystemConstant.INVEST_TYPE, amount.intValue());
                //投资成功后，添加成长值
                //done: add user growth value; doing test
                memberVipInfoService.addMemberGrowthValue(drMember.getUid(),pInfo,amount);
                //排除活动和新手
                //done 投资成功，若是15天内首单，给推荐人发送红包  投资大于5000
                //获取该投资者的推荐人
                List<Map<String, Object>> maps = new ArrayList<>();
                returnMoneyMap.put("uid", drMember.getUid());
                DrMemberRecommended recommended = drMemberRecommendedDAO.selectByUid(returnMoneyMap);
                //是否有推荐人
                if (Utils.isObjectNotEmpty(recommended)
                        && recommended.getReferrerId() != null){
                    //是否是活动上线后注册的
                    if (drMember.getRegDate().getTime()> Utils.parseDate(SystemConstant.INVITE_FRIENDS).getTime()) {
                        long regDate = (new Date().getTime() - drMember.getRegDate().getTime()) / 1000 / 60 / 60 / 24;
                        if (regDate <= 90) {
                            //done 90天内返红利奖
                            //M*项目期限/360*1% 90天内返红利奖
                            BigDecimal inviteBonus = amount.multiply(new BigDecimal(pInfo.getDeadline()))
                                    .divide(new BigDecimal(360),2,BigDecimal.ROUND_DOWN)
                                    .multiply(new BigDecimal(0.01)).setScale(2,BigDecimal.ROUND_DOWN);
                            //存储用户红利
                            invite.setUserId(drMember.getUid());
                            invite.setReferrerid(recommended.getReferrerId());
                            invite.setInviteBonus(inviteBonus);
                            inviteMoeny = true;
                        }
                    }
                    maps = null;
                }
                //done 做定时任务 90天内任意单，每月15日给推荐人发送红包
            }
            drProductInvestDAO.insertSelective(invest);
            //邀请投资红利
            if (inviteMoeny){
                invite.setAddTime(new Date());
                invite.setInvestId(invest.getId());
                bypMemberInviteDao.insertSelective(invite);
            }
			/*if (isDoubleEgg) {
				drMemberLotteryLog.setUid(loginMember.getUid());
				drMemberLotteryLog.setInvestAmount(amount);
				drMemberLotteryLog.setAddTime(new Date());
				drMemberLotteryLog.setInvestId(invest.getId());
				drMemberLotteryLogDAO.insert(drMemberLotteryLog);
			}*/
            /**
             * start
             * 插入2017双旦抽奖次数增加业务逻辑
             */
            DrMember member = drMemberDAO.selectByPrimaryKey(loginMember.getUid());
            map.put("code", member.getToFrom());
            List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelList)) {
                DrChannelInfo channel1 = channelList.get(0);//获取渠道
                map.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            if (map.get("isCps").equals(0)) {
                //赠送一次免费抽奖
                Map<String, Object> map1 = new HashMap<String, Object>();
                String glutinousStartDate = redisClientTemplate
                        .getProperties("glutinousStartDate");
                String glutinousEndDate = redisClientTemplate
                        .getProperties("glutinousEndDate");
                Date nowDate = new Date();
                map1.put("uid", loginMember.getUid());
                map1.put("startDate", Utils.format(glutinousStartDate, "yyyy-MM-dd"));
                map1.put("endDate", Utils.format(glutinousEndDate, "yyyy-MM-dd"));
                map1.put("aid", redisClientTemplate.getProperties("activityId"));
                Integer con = drMemberLotteryLogService.getDoubleAGGOneLottery(map1);
                if (Utils.isObjectEmpty(con)) {
                    DrMemberLotteryLog drm = new DrMemberLotteryLog(Integer.parseInt(redisClientTemplate.getProperties("activityId")), loginMember.getUid(),
                            new Date(), null);
                    // 插入一次抽奖机会
                    drMemberLotteryLogDAO.insert(drm);
                }
                Date startDate = Utils.parse(glutinousStartDate,
                        "yyyy-MM-dd HH:mm:ss");
                Date EndDate = Utils.parse(glutinousEndDate,
                        "yyyy-MM-dd HH:mm:ss");
                // 活动未过期有机会startDatestartDate
                if (nowDate.before(EndDate) && nowDate.after(startDate)) {
                    // 投资非新手标和体验标的产品有机会拆双蛋
                    if (pInfo.getDeadline() >= 30 && pInfo.getType() != 3 && pInfo.getType() != 5 &&Utils.isObjectEmpty(pInfo.getPrizeId())
                            && Utils.isObjectEmpty(pInfo.getAtid())) {
                        Integer intValue = amount.intValue() / 2000;
                        drMemberLotteryLog.setUid(loginMember.getUid());
                        drMemberLotteryLog.setInvestAmount(amount);
                        drMemberLotteryLog.setAddTime(new Date());
                        drMemberLotteryLog.setAid(Integer.parseInt(redisClientTemplate.getProperties("activityId")));
                        drMemberLotteryLog.setInvestId(invest.getId());
                        for (int i = 0; i < intValue; i++) {
                            drMemberLotteryLogDAO.insert(drMemberLotteryLog);
                        }
                    }
                }
                //end
                //元旦期间投资产品30天以上投资积分核算
                List<BypActivityIntegral> bypActivityIntegrals = bypActivityIntegralDAO.selectBypActivityIntegral();
                BypMemberIntegral bypMemberIntegral = new BypMemberIntegral();
                String doubleDanStartDate = redisClientTemplate
                        .getProperties("doubleDanStartDate");
                String doubleDanEndDate = redisClientTemplate
                        .getProperties("doubleDanEndDate");
                Date now = new Date();
                Date start = Utils.parse(doubleDanStartDate,
                        "yyyy-MM-dd HH:mm:ss");
                Date end = Utils.parse(doubleDanEndDate, "yyyy-MM-dd HH:mm:ss");
                // 活动未过期有机会
                if (now.before(end) && now.after(start)) {
                    // 投资非新手标和体验标的产品有机会积分兑奖
                    if (pInfo.getType() != 3 && pInfo.getType() != 5) {
                        // 投资标时间30以上有机会
                        if (pInfo.getDeadline() >= 30) {
                            bypMemberIntegral.setAddtime(new Date());
                            bypMemberIntegral.setUid(loginMember.getUid());
                            if (Utils.isObjectNotEmpty(bypActivityIntegrals) && bypActivityIntegrals.size() > 0) {
                                for (BypActivityIntegral bypActivityIntegral : bypActivityIntegrals) {
                                    if (1 == bypActivityIntegral.getType()) {
                                        bypMemberIntegral.setIntegralTypeId(bypActivityIntegral.getId());
                                    }
                                }
                            }
                            //计算年化投资额
                            if (pInfo.getDeadline() == 30) {
                                //annualInvestment= (amount.intValue() *30)/360;
                                BigDecimal nwdMultiply = Utils.nwdMultiply(amount, 30);
                                BigDecimal nwdDivide = Utils.nwdDivide(nwdMultiply, 360);
                                annualInvestment = Utils.setScaleBidDecimal(nwdDivide);
                                bypMemberIntegral.setAmount(annualInvestment);
                            } else if (pInfo.getDeadline() == 60) {
                                BigDecimal nwdMultiply = Utils.nwdMultiply(amount, 60);
                                BigDecimal nwdDivide = Utils.nwdDivide(nwdMultiply, 360);
                                annualInvestment = Utils.setScaleBidDecimal(nwdDivide);
                                bypMemberIntegral.setAmount(annualInvestment);
                            } else if (pInfo.getDeadline() == 180) {
                                BigDecimal nwdMultiply = Utils.nwdMultiply(amount, 180);
                                BigDecimal nwdDivide = Utils.nwdDivide(nwdMultiply, 360);
                                annualInvestment = Utils.setScaleBidDecimal(nwdDivide);
                                bypMemberIntegral.setAmount(annualInvestment);
                            }
                            if (Utils.isObjectNotEmpty(annualInvestment)) {
                                BigDecimal bigDecimal = Utils.nwdBcadd(drMember.getUser_integral(), annualInvestment);
                                drMember.setUser_integral(Utils.setScale(bigDecimal));
                                drMemberDAO.updateByPrimaryKey(drMember);
                                bypMemberIntegralDAO.insertSelective(bypMemberIntegral);
                            }
                        }
                    }
                }
            }

            DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(
                    pInfo.getId(), invest.getId(), loginMember.getUid(), 3, 0,
                    amount, funds.getFuiou_balance().subtract(amount), 4, "投资【"
                    + pInfo.getFullName() + "】产品", null);

            // fundsRecord.setBalance(funds.getBalance().subtract(amount).add(balanceProfit));//可用余额减去投资金额加上返现金额
            drMemberFundsRecordDAO.insert(fundsRecord);

            // 返现
            BigDecimal balanceProfit = BigDecimal.ZERO;// 返现金额
            DrMemberFavourable dm = drMemberFavourableDAO
                    .selectByPrimaryKey(fid);
            if (dm != null && dm.getType() == 1) {
                balanceProfit = dm.getAmount();
                if (balanceProfit.compareTo(BigDecimal.ZERO) > 0) {
                    // 放到 redis 缓存
                    Map<String, Object> crushBackMap = new HashMap<String, Object>();
                    crushBackMap.put("type", 52);
                    crushBackMap.put("uid", loginMember.getUid());
                    crushBackMap.put("investId", invest.getId());
                    crushBackMap.put("project_no", pInfo.getProject_no());

                    backMap.put("crushBackMap", crushBackMap);

                }
            }

            DrMemberFundsLog fundslog = new DrMemberFundsLog(
                    loginMember.getUid(), fundsRecord.getId(), amount, 3, 0,
                    "投资【" + pInfo.getFullName() + "】产品,资金冻结");
            drMemberFundsLogDAO.insertDrMemberFundsLog(fundslog);

            // 发送站内信
            DrMemberMsg msg = new DrMemberMsg(
                    loginMember.getUid(),
                    0,
                    3,
                    "投资成功",
                    new Date(),
                    0,
                    0,
                    redisClientTemplate
                            .getProperties("investSuccess")
                            .replace("${fullName}", pInfo.getFullName())
                            .replace("${amount}", invest.getAmount().toString()));
            drMemberMsgDAO.insertDrMemberMsg(msg);

            redisClientTemplate.del("error.tpwd.uid." + loginMember.getUid());

            // 用户资金记录 修改用户资金
            funds.setFuiou_balance(funds.getFuiou_balance().subtract(amount));// 可用余额减去投资金额
            funds.setFuiou_freeze(funds.getFuiou_freeze().add(amount));
            drMemberFundsDAO.updateDrMemberFunds(funds);
            Map<String, Object> m = new HashMap<String, Object>();

            // 年末投即送活动
            if (!Utils.isBlank(pInfo.getPrizeId())) {
                Map<String, Object> mparam = new HashMap<String, Object>();
                mparam.put("id", pInfo.getPrizeId());
                JsProductPrize jpPrize = jsProductPrizeDAO
                        .selectJsPorudctPrize(mparam);
                if (amount.compareTo(jpPrize.getAmount()) == 0) {
                    JsProductPrizeLog pLog = new JsProductPrizeLog();
                    pLog.setInvestId(invest.getId());
                    pLog.setPpid(jpPrize.getId());
                    pLog.setType(0);
                    pLog.setUid(loginMember.getUid());
                    jsProductPrizeLogDAO.insert(pLog);
                    //发送站内信
                    Map<String,Object> maps=new HashMap<>();
                    maps.put("id",pInfo.getPrizeId());
                    JsProductPrize jsProductPrize = jsProductPrizeDAO.selectByPrimaryKey(maps);
                    DrMemberMsg msglog = new DrMemberMsg(member.getUid(), 0, 1, "恭喜您0元购得壕礼", new Date(), 0,0, "恭喜您在“0元购壕礼”中0元购买" +
                            jsProductPrize.getName()+
                            "成功，商品将于7-15个工作日为您发送，请记得正确完善您的收货地址。若有疑问请致电：400-820-4684。");
                    drMemberMsgDAO.insertDrMemberMsg(msglog);
                }
            }
            //抢标
            String bidActivityStartDate = redisClientTemplate
                    .getProperties("bidActivityStartDate");
            String bidActivityEndDate = redisClientTemplate
                    .getProperties("bidActivityEndDate");
            Date bidActivityNow = new Date();
            Date bidStart = Utils.parse(bidActivityStartDate,
                    "yyyy-MM-dd HH:mm:ss");
            Date bidEnd = Utils.parse(bidActivityEndDate, "yyyy-MM-dd HH:mm:ss");
            HashMap<String,Object> params=new HashMap<>();
            params.put("code", loginMember.getToFrom());
            List<DrChannelInfo> channelLists = drChannelInfoDAO.getDrChannelInfoListForMap(params);//获取toFrom对应的渠道
            if (!Utils.isEmptyList(channelLists)) {
                DrChannelInfo channel1 = channelLists.get(0);//获取渠道
                params.put("isCps", channel1.getType() == null ? 0 : channel1.getType());//渠道类型，如果为null默认为非CPS
            } else {
                params.put("isCps", 0);//如果渠道不存在，则默认是非CPS
            }
            // 活动未过期有机会
            if (bidActivityNow.before(bidEnd) && bidActivityNow.after(bidStart) && Utils.isObjectEmpty(pInfo.getPrizeId()) && Utils.isObjectEmpty(pInfo.getAtid())&&params.get("isCps").equals(0)) {
                /// 募集满的时候
                if (pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {
                   /* if(pInfo.getStartDate().getTime()>= bidStart.getTime()&&pInfo.getStartDate().getTime()<=bidEnd.getTime()){*/
                    if (pInfo.getType() != 3 && pInfo.getType() != 5) {
                        //投资标时间30以上有机会
                        //(老版本含有最快最大满标)
                       /* if (pInfo.getDeadline() >= 30) {
                            Map<String, Object> map1 = new HashMap<String, Object>();
                            //获取第一个投资的用户
                            DrProductInvest drProductInvest = drProductInvestDAO.selectOneInvestUser(pInfo.getId());
                            if (Utils.isObjectNotEmpty(drProductInvest)) {
                                //第一个投资的用户获得8元红包
                                map1.put("type", 8);
                                map1.put("uid", drProductInvest.getUid());
                                map1.put("bidAmt", new BigDecimal(8));
                                map1.put("investId", drProductInvest.getId());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));
                            }

                            //获取累积投资最多的用户
                            DrProductInvest drProductInvestMax = drProductInvestDAO.selectMaxInvestUser(pInfo.getId());
                            if (Utils.isObjectNotEmpty(drProductInvestMax)) {
                                map1.clear();
                                map1.put("type", 8);
                                map1.put("bidAmt", new BigDecimal(58));
                                map1.put("investId", drProductInvestMax.getId());
                                map1.put("uid", drProductInvestMax.getUid());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));

                            }
                            //获取最后 一位投资用户
                            DrProductInvest drProductInvestLast = drProductInvestDAO.selectLastInvestUser(pInfo.getId());
                            if (Utils.isObjectNotEmpty(drProductInvestMax)) {
                                map1.clear();
                                map1.put("type", 8);
                                map1.put("bidAmt", new BigDecimal(18));
                                map1.put("investId", drProductInvestLast.getId());
                                map1.put("uid", drProductInvestLast.getUid());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));
                            }
                        }end*/
                        //新版满标抢标活动
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        if (pInfo.getDeadline() == 30) {//月优享-Y137	8元 	6元
                            map1.put("type", 8);
                            map1.put("uid", invest.getUid());
                            map1.put("bidAmt", new BigDecimal("8"));
                            map1.put("investId", invest.getId());
                            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));
                            //满万元
                            if (amount.compareTo(new BigDecimal(redisClientTemplate.getProperties("bidActivityAmount")))>=0){
                                map2.put("type", 9);
                                map2.put("uid", invest.getUid());
                                map2.put("bidAmt", new BigDecimal("6"));
                                map2.put("investId", invest.getId());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map2));
                            }
                        }
                        if (pInfo.getDeadline() == 60) {//60天	18元	16元
                            map2.put("type", 8);
                            map2.put("uid", invest.getUid());
                            map2.put("bidAmt", new BigDecimal("18"));
                            map2.put("investId", invest.getId());
                            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map2));
                            //满万元
                            if (amount.compareTo(new BigDecimal(redisClientTemplate.getProperties("bidActivityAmount")))>=0){
                                map1.put("type", 9);
                                map1.put("uid", invest.getUid());
                                map1.put("bidAmt", new BigDecimal("16"));
                                map1.put("investId", invest.getId());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));
                            }
                        }
                        if (pInfo.getDeadline() == 180) {//180天	38元	36元
                            map2.put("type", 8);
                            map2.put("uid", invest.getUid());
                            map2.put("bidAmt", new BigDecimal("38"));
                            map2.put("investId", invest.getId());
                            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map2));
                            //满万元
                            if (amount.compareTo(new BigDecimal(redisClientTemplate.getProperties("bidActivityAmount")))>=0){
                                map1.put("type", 9);
                                map1.put("uid", invest.getUid());
                                map1.put("bidAmt", new BigDecimal("36"));
                                map1.put("investId", invest.getId());
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map1));
                            }
                        }
                    }
                    //}
                }
            }


            // 活动标
            if (!Utils.isBlank(pInfo.getAtid())) {

                // 募集满的时候 修改 活动产品关联表
                if (pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {
                    m.put("atid", pInfo.getAtid());// 活动id
                    m.put("pid", pInfo.getId());// 产品id
                    m.put("status", 2);// 代开奖
                    jsActivityProductDAO.updateJsActivityProduct(m);
                }

                // 投资金额 每 1000 获得一个幸运码 起投金额leastaAmount
                int leastaAmount = pInfo.getLeastaAmount().intValue();
                int luckCodeCount = invest.getAmount().intValue()
                        / leastaAmount;
                // 取得活动固定码
                // String prefix =
                // jsActivityProductDAO.selectActivityCodeFixation(pInfo.getAtid());
                Map<String, Object> template = jsActivityProductDAO
                        .selectActivityTemplate(pInfo.getAtid());
                // 调接口获得 luckCodes
                List<String> luckList = seqService.generateLuckCodes(
                        pInfo.getId(), luckCodeCount,
                        (String) template.get("codeFixation"),
                        (Integer) template.get("digit"));

                StringBuffer luckCode = new StringBuffer();
                for (String lucks : luckList) {
                    luckCode.append(lucks).append(",");
                }
                String luckCodes = luckCode.toString();
                luckCodes = luckCodes.substring(0, luckCodes.length() - 1);
                // 插入 活动产品投资记录表
                JsActivityProductInvestInfo activityProduct = new JsActivityProductInvestInfo(
                        invest.getId(), luckCodes, 0, 0, null, null, null);

                jsActivityProductInvestInfoDAO.insert(activityProduct);

                m.clear();
                backMap.put("luckCodes", luckCodes);
                backMap.put("luckCodeCount", luckCodeCount);
            }
            backMap.put("investTime", new Date());
            backMap.put("isDoubleEgg", isDoubleEgg);
            backMap.put("activityUrl", isDoubleEgg == true ? activityUrl : "");
            backMap.put("investId", invest.getId());
            backMap.put("amount", amount);
            if (pInfo.getType() == 1) {
                backMap.put("expireDate", Utils.getDayNumOfAppointDate(Utils
                        .format(Utils.format(new Date(), "yyyy-MM-dd"),
                                "yyyy-MM-dd"), -(pInfo.getDeadline() + 1)));
            } else {
                backMap.put("expireDate", pInfo.getExpireDate());
            }
            SysFuiouNoticeLog noticeLog = new SysFuiouNoticeLog();
            noticeLog.setStatus(2);
            noticeLog.setInvest_id(invest.getId());
            sysFuiouNoticeLogDAO.update(noticeLog);
            br.setMap(backMap);
            br.setSuccess(true);
        } catch (Exception e1) {
            log.error(loginMember.getUid() + "投资冻结失败", e1);
            br.setSuccess(false);
            br.setErrorCode("9999");
            redisClientTemplate.del("product.info." + pInfo.getId());
            FuiouConfig.freezeCancel(paramsPreAuth);
            throw new RuntimeException();// 让事务回滚
        } finally {
            if (fuiouLockFlag) {
                redisClientTemplate.del("product.invest.fuiou_acnt."
                        + loginMember.getMobilephone());
            }
        }
        return br;
    }

    /**
     * 判断产品期限是否存在字符串中
     *
     * @param deadlines
     * @param deadline
     * @return
     */
    private boolean existDeadline(String deadlines, String deadline) {
        if (deadlines != null) {
            String[] array = deadlines.split(",");
            for (String temp : array) {
                if (temp.equals(deadline)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ip7约标开启
     */
    private void jsProductReservationOpen(DrProductInfo drProductInfo) {
        try {
            Map<String, Object> acMap = jsActivityProductDAO
                    .selectActivityProduct(drProductInfo.getId());
            if (Utils.isObjectNotEmpty(acMap)
                    && Utils.isObjectNotEmpty(acMap.get("activityPeriods"))) {// 期限不为空的
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("periods", Integer.parseInt(acMap
                        .get("activityPeriods").toString()) + 1);
                map.put("order", " id desc ");
                map.put("offset", 0);
                map.put("limit", 1);
                List<JsProductReservation> list = jsProductReservationDAO
                        .selectJsProductReservationByMap(map);
                if (!Utils.isEmptyList(list) && list.get(0).getStatus() == 0) {
                    JsProductReservation bean = new JsProductReservation();
                    bean.setId(list.get(0).getId());
                    bean.setStatus(1); // 状态 0:待开启 1:开启 2:关闭
                    jsProductReservationDAO.update(bean);
                } else {
                    System.out.println("ip7约标开启失败:" + "1002");
                }
            } else {
                System.out.println("ip7约标开启失败:" + "1001");
            }
        } catch (Exception e) {
            System.out.println("ip7约标开启失败:"
                    + Utils.format(new Date(), "yyyy-MM-dd") + ".--->"
                    + e.getMessage());
        }
    }

    // 产品募集完成
    private void autoEggActivityFavourableInvalid(DrProductInfo pinfo) {
        if (pinfo.getDeadline() == 60 || pinfo.getDeadline() == 180) {// 把产品绑定的加息券失效
            Map<String, Object> map = new HashMap<>();
            map.put("status", 2);
            map.put("pid", pinfo.getId());
            map.put("eStatus", 0);
            drMemberFavourableDAO.updateFavourableStatusByMap(map);
        }
    }

    private void autoReleaseProduct(DrProductInfo pInfo) {
        // 当期募集完成自动上架下一个同类型（募集期限）的储备产品，
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status", 2); // 2已审核 （待上架）
        param.put("deadline", pInfo.getDeadline());// 期限
        param.put("isShow", 1);// 期限
        List<DrProductInfo> list = drProductInfoDAO.selectProductInfo(param);
        String sendMobile1 = "";
        String sendMobile2 = "";
        try {
            sendMobile1 = redisClientTemplate
                    .getProperties("autoPublishMobile1");
            sendMobile2 = redisClientTemplate
                    .getProperties("autoPublishMobile2");
            if (!Utils.isEmptyList(list)) {
                StringBuffer errorMsg = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    DrProductInfo dpi = list.get(i);
                    // 判断产品的到期日是否已经超过标的的到期日
                    DrSubjectInfo drSubjectInfo = drSubjectInfoDAO
                            .getDrSubjectInfoByid(dpi.getSid());
                    int counts = Utils.daysBetween(
                            dpi.getDeadline() + dpi.getRaiseDeadline() - 1,
                            drSubjectInfo.getEndDate(), null);
                    if (counts > 0) {
                        errorMsg.append("产品["
                                + dpi.getFullName()
                                + "]到期日不可大于标的到期日【"
                                + Utils.format(drSubjectInfo.getEndDate(),
                                "yyyy-MM-dd") + "】！");
                        continue;
                    }
                    dpi.setStatus(5);// 募集中
                    dpi.setStartDate(new Date());
                    Date establish = Utils.getDayNumOfAppointDate(
                            dpi.getStartDate(), -(dpi.getRaiseDeadline()));
                    // 根据开始时间 ,募集期,期限, 计算成立时间 与计算时间
                    dpi.setEstablish(establish);
                    dpi.setExpireDate(Utils.getDayNumOfAppointDate(
                            dpi.getStartDate(),
                            -(dpi.getDeadline() + dpi.getRaiseDeadline())));
                    dpi.setUpdUser(0);
                    dpi.setUpdDate(new Date());

                    drProductInfoDAO.updateProductSelective(dpi);
                    // 可以放到 redis 缓存
                    redisClientTemplate.set(
                            "autoReleaseProductLimit_" + pInfo.getId(), "1");// 原产品标记为已执行过一次自动发标

                    // 上架成功短信提醒
                    // --》自动发标信息提醒>${1}产品已募集结束，${2}产品自动发布上架成功，自动发布储备标的剩余${3}个。
                    // 15221219118 18930352770
                    String autoPublishSms = redisClientTemplate
                            .getProperties("autoPublish")
                            .replace("${1}", pInfo.getFullName())
                            .replace("${2}", dpi.getFullName())
                            .replace("${3}", list.size() - 1 + "");// 短信模板

                    SysMessageLog sms1 = new SysMessageLog(0, autoPublishSms,
                            1, null, sendMobile1);
                    SysMessageLog sms2 = new SysMessageLog(0, autoPublishSms,
                            1, null, sendMobile2);
                    sysMessageLogService.sendMsg(sms1, 1);
                    sysMessageLogService.sendMsg(sms2, 1);
                    System.out.println("自动发标：" + autoPublishSms
                            + Utils.format(new Date(), "yyyy-MM-dd HH-mm-ss"));
                    break;
                }
                if (!"".equals(errorMsg.toString())) {
                    SysMessageLog sms1 = new SysMessageLog(0,
                            errorMsg.toString(), 1, null, sendMobile1);
                    SysMessageLog sms2 = new SysMessageLog(0,
                            errorMsg.toString(), 1, null, sendMobile2);
                    sysMessageLogService.sendMsg(sms1, 1);
                    sysMessageLogService.sendMsg(sms2, 1);
                }
            } else {
                SysMessageLog sms1 = new SysMessageLog(0, pInfo.getDeadline()
                        + "天期限产品已无储备标，请及时发标！", 1, null, sendMobile1);
                SysMessageLog sms2 = new SysMessageLog(0, pInfo.getDeadline()
                        + "天期限产品已无储备标，请及时发标！", 1, null, sendMobile2);
                sysMessageLogService.sendMsg(sms1, 1);
                sysMessageLogService.sendMsg(sms2, 1);
            }
        } catch (Exception e) {
            SysMessageLog sms1 = new SysMessageLog(0, "自动发标异常，"
                    + e.getMessage(), 1, null, "15801868241");
            SysMessageLog sms2 = new SysMessageLog(0, "自动发标异常，"
                    + e.getMessage(), 1, null, "15221219118");
            sysMessageLogService.sendMsg(sms1, 1);
            sysMessageLogService.sendMsg(sms2, 1);
            e.printStackTrace();
        }
    }

    @Override
    public DrProductInfo selectProductByPrimaryKey(Integer id) {
        return drProductInfoDAO.selectProductByPrimaryKey(id);
    }

    @Override
    public List<DrProductInfo> selectHotProductInfo() {
        return drProductInfoDAO.selectHotProductInfo();
    }

    @Override
    public DrProductInfo selectNewHandInfo(Map<String, Object> map) {
        return drProductInfoDAO.selectNewHandInfo(map);// 新手标
    }

    @Override
    public List<DrProductInfo> selectProductInfoRecommend() {

        return drProductInfoDAO.selectProductInfoRecommend();
    }

    @Override
    public BaseResult getNewActivityProduct() {
        BaseResult br = new BaseResult();
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrProductInfo info = drProductInfoDAO.selectJSProductActive();// 活动产品
            if (Utils.isObjectEmpty(info)) {
                br.setErrorCode("没有产品！");
                br.setSuccess(false);
                return br;
            }
            result.put("result", info);

            // 投资记录
            map.put("pid", info.getId());
            map.put("atid", info.getAtid());
            result.put("investList", jsActivityProductInvestInfoDAO
                    .selectJsActivityInvestList(map));

            // 活动期数
            // map.clear();
            // map.put("pid", info.getId());
            // result.put("activityPeriods",
            // jsActivityProductDAO.selectActivityPeriods(map));
            // 活动配置
            Map<String, Object> acMap = jsActivityProductDAO
                    .selectActivityProduct(info.getId());
            result.put("codeFixation", acMap.get("codeFixation"));// 固定吗
            result.put("activityPeriods", acMap.get("activityPeriods"));// 期数
            result.put("bannerUrl", acMap.get("h5BannerUrl"));// h5banner

            // 参与人数量
            map.clear();
            map.put("atid", info.getAtid());
            map.put("pid", info.getId());
            result.put("investTotal", jsActivityProductInvestInfoDAO
                    .selectjsActivityProductInvestInfoCount(map));

            // 最新动态
            result.put("newTrends",
                    jsActivityProductInvestInfoDAO.selectNewTrends());

            br.setMap(result);
            br.setSuccess(true);
        } catch (Exception e) {
            br.setErrorMsg("系统错误");
            br.setErrorCode("9999");
            br.setSuccess(false);
        }

        return br;
    }

    @Override
    public BaseResult getMyLuckCodes(Map<String, Object> map) {
        BaseResult br = new BaseResult();
        if (map.containsKey("uid") && map.containsKey("pid")) {
            String luckCodes = jsActivityProductInvestInfoDAO
                    .selectjsActivityProductLuckCodes(map);
            int luckAmount = 0;

            if (!Utils.strIsNull(luckCodes)) {// 要验证
                luckAmount = luckCodes.toString().split(",").length;
            }
            map.clear();
            map.put("luckCodes", luckCodes);
            map.put("luckAmount", luckAmount);
            br.setMap(map);
            br.setSuccess(true);
        } else {
            br.setErrorCode("9998");
            br.setSuccess(false);
        }
        return br;
    }

    @Override
    public DrProductInfo selectJSProductActive() {

        return drProductInfoDAO.selectJSProductActive();
    }

    @Override
    public void eggActivityRuleFilter(List<DrProductInfo> list,
                                      List<Map<String, Object>> listMap, Integer uid) {
        // TODO Auto-generated method stub
        // 是否投资过除了新手标
        if (!Utils.isBlank(uid)
                && drProductInvestDAO.selectInvestCountExcludeNewHand(uid) > 0) {
            // 查询募集中产品绑定最高利率或已砸开得到的加息券
            List<Map<String, Object>> raiseList = drMemberFavourableDAO
                    .selectRaisedRatesAndPid(uid);
            if (Utils.isEmptyList(raiseList)) {
                return;
            }
            Map<String, Map<String, Object>> raiseMap = new HashMap<String, Map<String, Object>>();
            for (Map<String, Object> map : raiseList) {
                raiseMap.put(map.get("pid").toString(), map);
            }
            // 给以list<DrProductInfo>形式 set 值
            if (!Utils.isEmptyList(list)) {
                for (DrProductInfo info : list) {
                    if (info.getStatus() == 5
                            && (info.getDeadline() == 60 || info.getDeadline() == 180)) {// 只有60,180天的有
                        if (Utils.isObjectNotEmpty(raiseMap.get(info.getId()
                                .toString()))) {
                            info.setMaxActivityCoupon(new BigDecimal(raiseMap
                                    .get(info.getId().toString())
                                    .get("maxActivityCoupon").toString()));
                            Object count = raiseMap
                                    .get(info.getId().toString()).get("isEgg");
                            int isEgg = 0;
                            if (Utils.isObjectNotEmpty(isEgg)) {
                                isEgg = Integer.parseInt(count.toString());
                            }
                            info.setIsEgg(isEgg > 0 ? 2 : 1);
                        }
                    }
                }
            }
            // 给以list<DrProductInfo>形式 set 值
            if (!Utils.isEmptyList(listMap)) {
                for (Map<String, Object> info : listMap) {
                    Integer deadline = (Integer) info.get("deadline");
                    Integer status = (Integer) info.get("status");
                    if (status == 5 && (deadline == 60 || deadline == 180)) {// 只有60,180天的有
                        if (Utils.isObjectNotEmpty(raiseMap.get(info.get("id")
                                .toString()))) {
                            info.put("maxActivityCoupon",
                                    raiseMap.get(info.get("id").toString())
                                            .get("maxActivityCoupon")
                                            .toString());
                            Object count = raiseMap.get(
                                    info.get("id").toString()).get("isEgg");
                            int isEgg = 0;
                            if (Utils.isObjectNotEmpty(isEgg)) {
                                isEgg = Integer.parseInt(count.toString());
                            }
                            info.put("isEgg", isEgg > 0 ? 2 : 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<DrProductInfo> selectProductbyMap(Map<String, Object> map) {

        return drProductInfoDAO.selectProductbyMap(map);
    }

    @Override
    public DrProductInfo selectExperienceDetail() {
        return drProductInfoDAO.selectExperienceDetail();
    }

    @Override
    public BaseResult experienceInvest(DrProductInfo info, Integer uid,
                                       Map<String, Object> param, Integer channel) {
        BaseResult br = new BaseResult();
        try {
            BigDecimal experAmount = new BigDecimal(param.get("experAmount")
                    .toString());// 所以体验金的总额
            String ids = param.get("ids").toString();

            DrProductInvest invest = new DrProductInvest();
            invest.setUid(uid);
            invest.setJoinType(channel);// 投资渠道
            invest.setStatus(0);
            invest.setPid(info.getId());
            invest.setAmount(new BigDecimal(0));
            invest.setExperience(ids);// 体验标ids
            invest.setInvestTime(new Date());
            BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate()
                    .add(info.getActivityRate()), 100), 360);
            invest.setInterest(experAmount.multiply(dayRate)
                    .multiply(new BigDecimal(info.getDeadline()))
                    .setScale(2, BigDecimal.ROUND_FLOOR));
            drProductInvestDAO.insertSelective(invest);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);// 已使用
            map.put("uid", uid);
            map.put("eStatus", 0);// 未使用
            map.put("type", 3);// 体验金
            map.put("ids", ids.split(","));// ids
            drMemberFavourableDAO.updateFavourableStatusByMap(map);
            // 查资金表
            DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(uid);
            DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(
                    info.getId(), invest.getId(), uid, 7, 0, BigDecimal.ZERO,
                    funds.getBalance(), 4, "投资【" + info.getFullName() + "】产品",
                    null);
            drMemberFundsRecordDAO.insert(fundsRecord);
            DrMemberFundsLog fundslog = new DrMemberFundsLog(uid,
                    fundsRecord.getId(), BigDecimal.ZERO, 3, 0, "投资【"
                    + info.getFullName() + "】产品,资金冻结");
            drMemberFundsLogDAO.insertDrMemberFundsLog(fundslog);
            // 发送站内信
            DrMemberMsg msg = new DrMemberMsg(uid, 0, 3, "投资成功", new Date(), 0,
                    0, redisClientTemplate.getProperties("investSuccess")
                    .replace("${fullName}", info.getFullName())
                    .replace("${amount}", experAmount.toString()));
            drMemberMsgDAO.insertDrMemberMsg(msg);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            br.setSuccess(false);
        }
        return br;
    }

    @Override
    public List<DrProductInfo> doubleEggList(Map<String, Object> param) {
        return drProductInfoDAO.doubleEggList(param);
    }

    @Override
    public List<DrProductInfo> selectProductInfo(Map<String, Object> param) {
        return drProductInfoDAO.selectProductInfo(param);
    }

    @Override
    public DrProductInfo selectPreferredInvest() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        return drProductInfoDAO.selectPreferredInvest(param);
    }

    @Override
    public DrProductInfo selectLessThirtyInvest() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        param.put("deadline", 30);
        return drProductInfoDAO.selectPreferredInvest(param);
    }

    @Override
    public BaseResult selectjsActivityProductLuckCodesAndStatus(
            Map<String, Object> map) {
        BaseResult br = new BaseResult();
        if (map.containsKey("uid") && map.containsKey("pid")) {
            Map<String, Object> map2 = jsActivityProductInvestInfoDAO
                    .selectjsActivityProductLuckCodesAndStatus(map);
            br.setMap(map2);
            br.setSuccess(true);
        } else {
            br.setErrorCode("9998");
            br.setSuccess(false);
        }
        return br;
    }

    @Override
    public void investSuccessAfter(DrProductInfo info,
                                   Map<String, Object> param, DrMember loginMember) {

        // 红包返现
        if (Utils.isObjectNotEmpty(param.get("crushBackMap"))) {
            // 自动发标样本放到redis里
            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),
                    SerializeUtil.serialize(param.get("crushBackMap")));
        }

        // 投资非活动标大于5000激活10万体验金
        if (info.getAtid() == null && info.getPrizeId() == null
                && info.getType() != 3 && info.getRepayType() != 3
                && info.getRepayType() != 4) {
            BigDecimal amount = new BigDecimal(String.valueOf(param
                    .get("amount")));
            Map<String, Object> param2 = new HashMap<String, Object>();
            if (amount.compareTo(new BigDecimal(2000)) != -1) {
                param2.put("source", 99);
                param2.put("esource", 100);
                param2.put("type", 3);
                param2.put("eStatus", 0);
                param2.put("uid", loginMember.getUid());
                drMemberFavourableDAO.updateFavourableStatusByMap(param2);
            }
        }
        // 获得同类产品投资次数 目前(2016-11-30)排除新手标,体验标,90天活动标
        param.put("isRepeats", false);
        if (info.getType() == 2 && Utils.isBlank(info.getAtid())) {
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", 2);
            maps.put("deadline", info.getDeadline());
            maps.put("uid", loginMember.getUid());
            maps.put("offset", 0);
            maps.put("limit", 2);
            List<DrProductInvest> dpiList = drProductInvestDAO
                    .selectInvestByMap(maps);
            if (!Utils.isEmptyList(dpiList) && dpiList.size() >= 2) {
                param.put("isRepeats", true);
            }
        }

        // 年终奖活动
		/*
		 * try { if ( info.getType() == 2) { if (info.getDeadline() == 30 ||
		 * info.getDeadline() == 60 || info.getDeadline() == 180) { String
		 * startDateStr =
		 * redisClientTemplate.getProperties("annualBonusStartDate"); String
		 * endDateStr = redisClientTemplate.getProperties("annualBonusEndDate");
		 * Date startDate = Utils.parse(startDateStr,"yyyy-MM-dd HH:mm:ss");
		 * Date endDate = Utils.parse(endDateStr,"yyyy-MM-dd HH:mm:ss"); Date
		 * nowDate = new Date(); if (nowDate.after(startDate) &&
		 * nowDate.before(endDate)) { Map<String, Object> map = new
		 * HashMap<String, Object>(); map.put("type", 1); map.put("uid",
		 * loginMember.getUid()); map.put("deadline", info.getDeadline());
		 * map.put("amount", new
		 * BigDecimal(String.valueOf(param.get("amount"))));
		 * redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),
		 * SerializeUtil.serialize(map)); } } } } catch (Exception e) {
		 * 
		 * }
		 */

        try {
            // 自动发标
            if (Utils.isBlank(info.getAtid()) && info.getType() != 1
                    && info.getType() != 4 /*&& info.getDeadline() != 45这次的活动是45的而且要自动发标*/
                    && info.getDeadline() != 75) {
                // 每个产品只执行一次自动发标
                if (redisClientTemplate.get("autoReleaseProductLimit_"
                        + info.getId()) == null) {
                    BigDecimal productSurplusAmount = info.getSurplusAmount();// 产品剩余金额
                    DrCarryParam drCarryParam = drCarryParamService
                            .getDrCarryParam();
                    if ((existDeadline(drCarryParam.getAutoReleaseProduct(),
                            info.getDeadline().toString()) && productSurplusAmount
                            .compareTo(drCarryParam
                                    .getAutoReleaseProductLimit()) <= 0)// 指定产品期限的剩余募集金额小于自动发标阈值时，自动发标
                            || (!Utils.isBlank(info.getPrizeId())
                            && productSurplusAmount
                            .compareTo(BigDecimal.ZERO) == 0 && !existDeadline(
                            drCarryParam.getAutoReleaseProduct(),
                            info.getDeadline().toString()))// 0元享150天募集完成自动发标
                            ) {
                    	if(info.getDeadline() == 7 && info.getType() == 3 && productSurplusAmount
                                .compareTo(new BigDecimal(0)) <= 0){
	                        Map<String, Object> autoReleaseMap = new HashMap<String, Object>();
	                        autoReleaseMap.put("type", 50);
	                        autoReleaseMap.put("id", info.getId());
	                        autoReleaseMap.put("deadline", info.getDeadline());
	                        autoReleaseMap.put("status", info.getStatus());
	                        autoReleaseMap.put("fullName", info.getFullName());
	                        autoReleaseMap.put("surplusAmount",
	                                info.getSurplusAmount());
	                        autoReleaseMap.put("prizeId", info.getPrizeId());
	                        // 自动发标样本放到redis里
	                        redisClientTemplate.lpush(
	                                "regAndVerifySendRedUidList".getBytes(),
	                                SerializeUtil.serialize(autoReleaseMap));
                    	}else if(info.getDeadline() != 7 && info.getType() != 3){
                    		 Map<String, Object> autoReleaseMap = new HashMap<String, Object>();
 	                        autoReleaseMap.put("type", 50);
 	                        autoReleaseMap.put("id", info.getId());
 	                        autoReleaseMap.put("deadline", info.getDeadline());
 	                        autoReleaseMap.put("status", info.getStatus());
 	                        autoReleaseMap.put("fullName", info.getFullName());
 	                        autoReleaseMap.put("surplusAmount",
 	                                info.getSurplusAmount());
 	                        autoReleaseMap.put("prizeId", info.getPrizeId());
 	                        // 自动发标样本放到redis里
 	                        redisClientTemplate.lpush(
 	                                "regAndVerifySendRedUidList".getBytes(),
 	                                SerializeUtil.serialize(autoReleaseMap));
                    	}
                    }

                } else {
                    if (info.getStatus() == 6)
                        redisClientTemplate.del("autoReleaseProductLimit_"
                                + info.getId()); // 产品募集完成自动发标原产品标记失效

                }
               
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 活动产品满标提醒
        try {
            if (!Utils.isBlank(info.getAtid())
                    || !Utils.isBlank(info.getPrizeId())) {// 活动产品满标提醒
                try {
                    BigDecimal productSurplusAmount = info.getSurplusAmount();// 产品剩余金额
                    if (productSurplusAmount.compareTo(BigDecimal.ZERO) == 0) {
                        String sendMobiles = redisClientTemplate
                                .getProperties("autoPublishMobile_90");
                        String fullScaleRemindSms = redisClientTemplate
                                .getProperties("fullScaleRemind").replace(
                                        "${1}", info.getFullName());
                        String[] mobile = sendMobiles.split(",");
                        for (int i = 0; i < mobile.length; i++) {
                            SysMessageLog sms = new SysMessageLog(0,
                                    fullScaleRemindSms, 1, null, mobile[i]);
                            sysMessageLogService.sendMsg(sms, 1);
                        }
                    }
                } catch (Exception e) {
                    log.error("满标提醒短信发送失败！");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }

        // 满标时
        if (info.getStatus() == 6) {
            // 约标开启
            if (!Utils.isBlank(info.getAtid())) {
                try {
                    jsProductReservationOpen(info);
                } catch (Exception e) {
                    log.error("约标开启:" + info.getId() + "," + info.getFullName()
                            + "===>" + e.getMessage(), e);
                }
            }
            // 砸金蛋活动 把产品绑定的加息券失效
            try {
                autoEggActivityFavourableInvalid(info);
            } catch (Exception e) {
                log.error("砸金蛋:" + info.getId() + "," + info.getFullName()
                        + "===>" + e.getMessage(), e);
            }
            // 冻结到冻结 队列
            if (!Utils.strIsNull(info.getProject_no())) {// 存管产品
                try {
                    Map<String, Object> mapFreeze = new HashMap<String, Object>();
                    mapFreeze.put("type", 53);
                    mapFreeze.put("pid", info.getId());

                    redisClientTemplate.lpush(
                            "regAndVerifySendRedUidList".getBytes(),
                            SerializeUtil.serialize(mapFreeze));

                } catch (Exception e) {
                    System.out.println("---app-满标-冻结到冻结 失败,"
                            + info.getFullName());
                    try {
                        SmsSendUtil.sendMsg("15800784479,15221219118",
                                "app 产品满标-冻结到冻结[" + info.getFullName() + ","
                                        + info.getId() + "]存放redis 失败", 0);
                    } catch (Exception e1) {
                    }
                }
            }

        }
    }

    @Override
    public Map<String, Object> selectPeriodProductList(
            Map<String, Object> param, PageInfo pi) throws Exception {
        StringBuffer orderStr = new StringBuffer("ORDER BY status ");// 新增了个活动产品置顶（atid）
        Integer order = (Integer) param.get("order");// 排序方式
        if (order == 0) {
            orderStr.append(" , startDate desc");
        } else if (order == 1) {
            orderStr.append(" , rate desc");
        } else if (order == 2) {
            orderStr.append(" , rate asc");
        } else if (order == 3) {
            orderStr.append(" , deadline desc");
        } else if (order == 4) {
            orderStr.append(" , deadline asc");
        } else if (order == 5) {
            orderStr.append(" , leastaAmount desc");
        } else if (order == 6) {
            orderStr.append(" , leastaAmount asc");
        }
        param.put("repayTypes", new Integer[]{3, 4});// 3等本等息按周回款4等本等息按月回款
        param.put("orderStr", "ORDER BY repayType ASC , startDate ASC");
        param.put("offset", pi.getPageInfo().getOffset());
        param.put("limit", pi.getPageInfo().getLimit() >= 100 ? 100 : pi
                .getPageInfo().getLimit());

        List<DrProductInfo> periodProList = drProductInfoDAO
                .selectProductbyMap(param);
        int total = drProductInfoDAO.selectProductCountbyMap(param);

        pi.setRows(periodProList);
        pi.setTotal(total);
        param.clear();
        param.put("pi", pi);
        return param;
    }

    @Override
    public Map<String, Object> getProductRateInterval(Map<String, Object> map) {
        Map<String, Object> m = drProductInfoDAO.getProductRateInterval(map);
        if (Utils.isObjectEmpty(m)) {
            m = new HashMap<String, Object>();
        }
        return m;
    }

    @Override
    public List<Map<String, Object>> selectProductInfoByDeadLine()
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        map.put("offset", 0);
        map.put("limit", 1);
        // map.put("isHot", 0);
        map.put("types", new Integer[]{2, 3});
        map.put("deadline",
                redisClientTemplate.getProperties("productDealine1"));//
        map.put("statuses", new Integer[]{5, 6, 8, 9});//
        map.put("orderStr", " dpi.status,dpi.isHot desc,dpi.addDate desc");
        map.put("excludeAtid", " and dpi.atid is null and dpi.prizeId is null");// 排除
        // 活动标
        list.addAll(drProductInfoDAO.selectProductInfoListByParam(map));
        map.put("deadline",
                redisClientTemplate.getProperties("productDealine2"));//
        list.addAll(drProductInfoDAO.selectProductInfoListByParam(map));
        map.put("deadline",
                redisClientTemplate.getProperties("productDealine3"));//
        list.addAll(drProductInfoDAO.selectProductInfoListByParam(map));

        return list;
    }

    @Override
    public BaseResult selectProductInfoByDragonBoat() {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询端午节活动
        map.put("activityName", "端午活动");
        List<DrActivity> drActivity = drActivityDAO.selectDrActivityList(map);
        if (drActivity.size() == 0) {
            br.setSuccess(false);
            br.setErrorCode("1001");
            br.setErrorMsg("未找到端午活动");
            return br;
        }
        Date startTime = Utils.parseDate(Utils.format(drActivity.get(0)
                .getStartTime(), "yyyy-MM-dd"));
        Date endTime = Utils.parseDate(Utils.format(drActivity.get(0)
                .getEndTime(), "yyyy-MM-dd"));
        Date nowTime = new Date();
        Date seckillTime = null;
        // 活动时间未到时判断下次秒杀时间
        Calendar ca = Calendar.getInstance();
        if (nowTime.before(startTime)) {
            ca.setTime(drActivity.get(0).getStartTime());
            ca.add(Calendar.HOUR_OF_DAY, 11);
            seckillTime = ca.getTime();
        }

        Map<String, Object> productInfo45 = new HashMap<String, Object>();
        Map<String, Object> productInfo75 = new HashMap<String, Object>();
        productInfo45 = drProductInfoDAO.getProductInfoByDealine(45);
        productInfo75 = drProductInfoDAO.getProductInfoByDealine(75);
        // 判断下次秒杀时间
        Date now = Utils.parseDate(Utils.format(nowTime, "yyyy-MM-dd"));
        int hour = nowTime.getHours();
        if (Utils.isObjectEmpty(productInfo45)
                || Utils.isObjectEmpty(productInfo75)) {
            if (seckillTime == null
                    && (now.before(endTime) || now.equals(endTime))) {
                if (hour < 11) {
                    ca.setTime(now);
                    ca.add(Calendar.HOUR_OF_DAY, 11);
                    seckillTime = ca.getTime();
                } else if (hour >= 11 && hour < 14) {
                    ca.setTime(now);
                    ca.add(Calendar.HOUR_OF_DAY, 14);
                    seckillTime = ca.getTime();
                } else if (hour >= 14 && hour < 24 && now.before(endTime)) {
                    ca.setTime(now);
                    ca.add(Calendar.DAY_OF_MONTH, 1);
                    ca.add(Calendar.HOUR_OF_DAY, 11);
                    seckillTime = ca.getTime();
                }
            }
        }
        // 活动时间最后一天抢完后，剩余时间设置为空
        if (Utils.isObjectNotEmpty(productInfo45)
                || Utils.isObjectNotEmpty(productInfo75)) {
            if (now.equals(endTime) && hour >= 14) {
                seckillTime = null;
            }
        }
        // 计算倒计时时间
        Long surpluSeckillTime = null;
        if (Utils.isObjectNotEmpty(seckillTime)) {
            surpluSeckillTime = seckillTime.getTime() - nowTime.getTime();
        }
        if (Utils.isObjectEmpty(productInfo45)) {
            productInfo45 = new HashMap<String, Object>();
            productInfo45.put("rate", 7);
            productInfo45.put("activityRate", 8);
            productInfo45.put("deadline", 45);
        }
        if (Utils.isObjectEmpty(productInfo75)) {
            productInfo75 = new HashMap<String, Object>();
            productInfo75.put("rate", 8);
            productInfo75.put("activityRate", 8);
            productInfo75.put("deadline", 75);
        }

        map.clear();
        map.put("productInfo45", productInfo45);
        map.put("productInfo75", productInfo75);
        map.put("seckillTime", surpluSeckillTime);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        map.put("investLog", drProductInvestDAO.selectInvestByDragonBoat(param));
        br.setMap(map);
        br.setSuccess(true);
        return br;
    }

    @Override
    public List<DrProductInfo> selectPorductClassifyByDeadline(
            Map<String, Object> map) {

        return drProductInfoDAO.selectPorductClassifyByDeadline(map);
    }

    @Override
    public DrProductInfo selectMaxRateInvestByLess() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        param.put("deadline", 30);
        return drProductInfoDAO.selectMaxRateInvest(param);
    }

    @Override
    public DrProductInfo selectThirtyInvest() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        param.put("equalDeadline", 30);
        return drProductInfoDAO.selectPreferredInvest(param);
    }

    @Override
    public DrProductInfo selectSixtyInvest() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        param.put("equalDeadline", 60);
        return drProductInfoDAO.selectPreferredInvest(param);
    }

    @Override
    public DrProductInfo selectMaxRateInvest(int i) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("types", new Integer[]{2});
        param.put("statuss", new Integer[]{5});
        param.put("equalDeadline", i);
        return drProductInfoDAO.selectMaxRateInvest(param);
    }

    @Override
    public BaseResult getZeroBuy() {
        Map<String, Object> pam = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        BaseResult br = new BaseResult();
        try {
            String start = redisClientTemplate.getProperties("kingBuyDateStart");
            String end = redisClientTemplate.getProperties("kingBuyDateEnd");
            Date nowDate = new Date();
            if (nowDate.after(Utils.parseDate(start,
                    "yyyy-MM-dd HH:mm:ss"))
                    && nowDate.before(Utils.parseDate(end,
                    "yyyy-MM-dd HH:mm:ss"))) {
                //切割配置文件数据按照天数和金额切割获取对应的产品id
                String fiveAmountSplit = redisClientTemplate.getProperties("kingfiveAmountSplit");
                String ninetyAmountSplit = redisClientTemplate.getProperties("kingninetyAmountSplit");
                String[] fiveSplit = fiveAmountSplit.split(",");
                String[] ninetySplit = ninetyAmountSplit.split(",");
                map.put("five", forgetPam(pam, fiveSplit, "45"));
                map.put("ninety", forgetPam(pam, ninetySplit, "90"));
                br.setSuccess(true);
                br.setMap(map);
            } else {
                br.setMsg("活动过期");
                br.setSuccess(false);
                br.setErrorCode("9991");
            }
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setSuccess(false);
            log.error(e.getMessage());
        }
        return br;
    }

    private List<Map<String, Object>> forgetPam(Map<String, Object> pam, String[] fiveSplit, String day) {
        List<Map<String, Object>> list = new LinkedList<>();
        for (String five : fiveSplit) {
            pam.put("amount", five);
            pam.put("deadline", day);
            List<DrProductInfo> periodProList = drProductInfoDAO.selectZeroBuy(pam);
            if (0 != periodProList.size()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("amount", five);
                map.put("pid", periodProList.get(0).getId());
                list.add(map);
            } else {
                pam.clear();
            }
        }
        return list;
    }

    @Override
    public Integer selectToHelpFarmersProduct() {
        Map<String, Object> map = new HashMap<>();
        Integer pid = 0;
        map.put("fullName","公益活动");
        List<DrProductInfo> list = drProductInfoDAO.selectToHelpFarmers(map);
        if (list != null && list.size() != 0){
            pid = list.get(0).getId();
        }else{
            log.warn("公益活动标的暂无，请添加标的！");
        }
        return pid;
    }
}
