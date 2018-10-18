package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.jsjf.common.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrCouponsIssuedRulesDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class DrActivityParameterServiceImpl implements DrActivityParameterService {
    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private DrMemberMsgDAO drMemberMsgDAO;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    public SysMessageLogService sysMessageLogService;
    @Autowired
    public DrCouponsIssuedRulesDAO drCouponsIssuedRulesDAO;
    @Autowired
    public DrActivityParameterDAO drActivityParameterDAO;
    @Autowired
    public DrProductInvestDAO drProductInvestDAO;
    @Autowired
    public DrProductInfoDAO drProductInfoDAO;
    @Autowired
    public DrChannelInfoDAO drChannelInfoDAO;

    @Override
    public boolean valentineActivity(DrMember member) throws Exception {
        boolean flag = false;
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        map.put("type", 1);//投资送券活动
        map.put("status", 1);//0:新建  1：生效 2：失效
        List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.getCouponsIssuedRulesList(map);
        if (!Utils.isEmptyList(list)) {
            for (int i = 0; i < list.size(); i++) {
                DrCouponsIssuedRules rules = list.get(i);
                String coupons = rules.getCoupons();
                String[] couponIds = coupons.split(",");
                map.clear();
                map.put("uid", member.getUid());
                map.put("fids", Utils.StringConvertInteger(couponIds));
                map.put("remark", rules.getName());
                //查询用户能使用的大礼包
                List<DrMemberFavourable> mfList = drMemberFavourableDAO.getMemberFavourableByValentine(map);
                if (!Utils.isEmptyList(mfList)) {
                    flag = true;
                    //批量发放优惠券
                    drMemberFavourableDAO.batchInsert(mfList);
                    //发送站内信
                    String content = redisClientTemplate.getProperties("activityMsg").replace("${1}", rules.getName());
                    DrMemberMsg msg = new DrMemberMsg(member.getUid(), 0, 2, rules.getName(), new Date(), 0, 0, content);
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                    SysMessageLog logs = new SysMessageLog(member.getUid(), content, 1, null, member.getMobilephone());
                    sysMessageLogService.sendMsg(logs, 1);
                }
            }
        }
        return flag;
    }

    @Override
    public void valentineActivitys(int uid, int type, int status, String toFrom)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        map.put("code", toFrom);
        List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
        map.clear();
        map.put("type", type);
        map.put("status", status);
        if (!Utils.isEmptyList(channelList)) {
            DrChannelInfo channel = channelList.get(0);//获取渠道
            map.put("isCps", channel.getType() == null ? 0 : channel.getType());//渠道类型，如果为null默认为非CPS
        } else {
            map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
        }

        List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.getCouponsIssuedRulesList(map);

        if (!Utils.isEmptyList(list)) {
            for (int i = 0; i < list.size(); i++) {
                DrCouponsIssuedRules rules = list.get(i);
                String coupons = rules.getCoupons();
                String[] couponIds = coupons.split(",");
                map.clear();
                map.put("uid", uid);
                map.put("fids", Utils.StringConvertInteger(couponIds));
                map.put("remark", rules.getName());
                //查询用户能使用的大礼包
                List<DrMemberFavourable> mfList = drMemberFavourableDAO.getMemberFavourableByValentine(map);
                if (!Utils.isEmptyList(mfList)) {
                    //批量发放优惠券
                    drMemberFavourableDAO.batchInsert(mfList);
                    //发送站内信
                    DrMemberMsg msg = new DrMemberMsg(uid, 0, 2, rules.getName(), new Date(), 0, 0, rules.getMessage());
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                }
            }
        }

    }

    @Override
    public BaseResult getRandomCouponByProductId(Integer uid, int pid) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        BaseResult br = new BaseResult();
        BigDecimal rate = BigDecimal.ZERO;
        BigDecimal oldRate = BigDecimal.ZERO;
        try {
            //是否投资普标
            if (drProductInvestDAO.selectInvestCountExcludeNewHand(uid) <= 0) {
                br.setErrorMsg("没有投资过普标");
                br.setSuccess(false);
                return br;
            }
            //产品是否绑定加息券
            List<DrActivityParameter> activityParameter = drActivityParameterDAO.getActivityParameterByPid(pid);
            if (Utils.isEmptyList(activityParameter)) {
                br.setErrorMsg("产品没有绑定加息券");
                br.setSuccess(false);
                return br;
            }
            //是否已砸
            map.put("pid", pid);
            map.put("uid", uid);
            List<DrMemberFavourable> favList = drMemberFavourableDAO.selectDrMemberFavourableByPid(map);
            if (!Utils.isEmptyList(favList) && favList.size() >= 2) {//只能砸俩次
                br.setErrorMsg("已砸过两次");
                br.setSuccess(false);
                return br;
            }
            //调用随机加息券接口
            List<BigDecimal> listrate = new ArrayList<>();
            Map<BigDecimal, DrActivityParameter> mapdata = new HashMap<>();
            for (DrActivityParameter d : activityParameter) {
                listrate.add(d.getRaisedRates());
                mapdata.put(d.getRaisedRates(), d);
            }
            DrProductInfo dpi = drProductInfoDAO.selectProductByPrimaryKey(pid);//产品
            if (Utils.isEmptyList(favList)) {//第一次
                rate = RandomUtil.randomByList(listrate);    //随机加息券接口
            } else {//第二次
                oldRate = favList.get(0).getRaisedRates();
                rate = RandomUtil.randomByListGEValue(listrate, oldRate);//随机加息券接口
                favList.get(0).setFullName(dpi.getFullName());
                result.put("oldActivityCoupon", favList.get(0));
                favList.get(0).setStatus(2);
                favList.get(0).setRemark("金蛋活动:失效第一次");
                drMemberFavourableDAO.updateFavourableStatus(favList.get(0));
            }
            DrActivityParameter dap = mapdata.get(rate);//
            DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(), uid, 2, dap.getCode(), dap.getName(), dap.getAmount(),
                    dap.getRaisedRates(), dap.getEnableAmount(), 0, Utils.getDayNumOfAppointDate(new Date(), 0 - dap.getDeadline()), "金蛋活动", 0, 0, dap.getProductDeadline(), dap.getMultiple(), pid);
            drMemberFavourableDAO.insertIntoInfo(dmf);//添加

            dmf.setFullName(dpi.getFullName());
            result.put("productType", dpi.getType());
            result.put("newActivityCoupon", dmf);
            br.setSuccess(true);
            br.setMap(result);
        } catch (SQLException e) {
            e.printStackTrace();
            br.setErrorMsg("系统错误");
            br.setSuccess(false);
        }
        return br;
    }

    @Override
    public void insertActivityParameter(List<DrMemberCpsFavourableRule> drMemberCpsFavourableRuleList, Integer uId,
                                        BigDecimal ShouldPrincipalCount, String mobilephone) throws Exception {
        List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
        for (int j = 0, length_j = drMemberCpsFavourableRuleList.size(); j < length_j; j++) {
            DrMemberCpsFavourableRule rule = drMemberCpsFavourableRuleList.get(j);
            Integer deadline = 0;
            BigDecimal dmfAmount = BigDecimal.ZERO;
            // 发放第一档红包
            if (Utils.isObjectNotEmpty(rule.getActivityId_1())) {
                DrActivityParameter activity_1 = drActivityParameterDAO.getActivityParameterById(rule.getActivityId_1());
                if (activity_1.getType() == 5) {
                    // 比例红包转换成用户返现红包
                    BigDecimal amount = Utils
                            .nwdMultiply(ShouldPrincipalCount, Utils.nwdDivide(activity_1.getRaisedRates(), 100))
                            .setScale(0, BigDecimal.ROUND_DOWN);
                    if (amount.compareTo(BigDecimal.ZERO) == 0) {
                        amount = BigDecimal.ONE;
                    }
                    if (amount.compareTo(dmfAmount) > 0) {
                        dmfAmount = amount;
                    }
                    if (activity_1.getDeadline() > deadline) {
                        deadline = activity_1.getDeadline();
                    }

                    DrMemberFavourable dmf = new DrMemberFavourable(activity_1.getId(), Integer.parseInt(uId + ""), 1,
                            activity_1.getCode(), activity_1.getName(), amount, null, ShouldPrincipalCount, 0,
                            Utils.getDayNumOfAppointDate(new Date(), -7), null, "福利发放", 0, 0,
                            activity_1.getProductDeadline(), activity_1.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0,
                            "尊敬的用户，" + dmf.getAmount() + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                } else {
                    DrMemberFavourable dmf = new DrMemberFavourable(activity_1.getId(), Integer.parseInt(uId + ""), activity_1.getType(), activity_1.getCode(), activity_1.getName(),
                            activity_1.getAmount(), activity_1.getRaisedRates(), activity_1.getEnableAmount(), 0, Utils.getDayNumOfAppointDate(new Date(), -activity_1.getDeadline()), null, "福利发放",
                            0, 0, activity_1.getProductDeadline(), activity_1.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    if (activity_1.getAmount().compareTo(dmfAmount) > 0) {
                        dmfAmount = activity_1.getAmount();
                    }
                    if (activity_1.getDeadline() > deadline) {
                        deadline = activity_1.getDeadline();
                    }
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0, "尊敬的用户，" + dmf.getAmount()
                            + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                }

            }
            // 发放第二档红包
            if (Utils.isObjectNotEmpty(rule.getActivityId_2())) {
                DrActivityParameter activity_2 = drActivityParameterDAO.getActivityParameterById(rule.getActivityId_2());
                if (activity_2.getType() == 5) {
                    // 比例红包转换成用户返现红包
                    BigDecimal amount = Utils.nwdMultiply(ShouldPrincipalCount, Utils.nwdDivide(activity_2.getRaisedRates(), 100)).setScale(0, BigDecimal.ROUND_DOWN);
                    if (amount.compareTo(BigDecimal.ZERO) == 0) {
                        amount = BigDecimal.ONE;
                    }
                    if (amount.compareTo(dmfAmount) > 0) {
                        dmfAmount = amount;
                    }
                    if (activity_2.getDeadline() > deadline) {
                        deadline = activity_2.getDeadline();
                    }
                    DrMemberFavourable dmf = new DrMemberFavourable(activity_2.getId(), uId, 1, activity_2.getCode(), activity_2.getName(),
                            amount, null, ShouldPrincipalCount, 0, Utils.getDayNumOfAppointDate(new Date(), -activity_2.getDeadline()), null,
                            "福利发放", 0, 0, activity_2.getProductDeadline(), activity_2.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0, "尊敬的用户，"
                            + dmf.getAmount() + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                } else {
                    DrMemberFavourable dmf = new DrMemberFavourable(activity_2.getId(), Integer.parseInt(uId + ""), activity_2.getType(), activity_2.getCode(), activity_2.getName(),
                            activity_2.getAmount(), activity_2.getRaisedRates(), activity_2.getEnableAmount(), 0, Utils.getDayNumOfAppointDate(new Date(), -activity_2.getDeadline()), null,
                            "福利发放", 0, 0, activity_2.getProductDeadline(), activity_2.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    if (activity_2.getAmount().compareTo(dmfAmount) > 0) {
                        dmfAmount = activity_2.getAmount();
                    }
                    if (activity_2.getDeadline() > deadline) {
                        deadline = activity_2.getDeadline();
                    }
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0, "尊敬的用户，"
                            + dmf.getAmount() + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                }
            }
            // 发放第三档红包
            if (Utils.isObjectNotEmpty(rule.getActivityId_3())) {

                DrActivityParameter activity_3 = drActivityParameterDAO.getActivityParameterById(rule.getActivityId_3());
                if (activity_3.getType() == 5) {
                    // 比例红包转换成用户返现红包
                    BigDecimal amount = Utils.nwdMultiply(ShouldPrincipalCount, Utils.nwdDivide(activity_3.getRaisedRates(), 100)).setScale(0, BigDecimal.ROUND_DOWN);
                    if (amount.compareTo(BigDecimal.ZERO) == 0) {
                        amount = BigDecimal.ONE;
                    }
                    if (amount.compareTo(dmfAmount) > 0) {
                        dmfAmount = amount;
                    }
                    if (activity_3.getDeadline() > deadline) {
                        deadline = activity_3.getDeadline();
                    }

                    DrMemberFavourable dmf = new DrMemberFavourable(activity_3.getId(), uId, 1, activity_3.getCode(), activity_3.getName(), amount, null, ShouldPrincipalCount, 0,
                            Utils.getDayNumOfAppointDate(new Date(), -activity_3.getDeadline()), null, "福利发放", 0, 0, activity_3.getProductDeadline(), activity_3.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0, "尊敬的用户，" + dmf.getAmount() + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                } else {
                    DrMemberFavourable dmf = new DrMemberFavourable(activity_3.getId(), Integer.parseInt(uId + ""), 1,
                            activity_3.getCode(), activity_3.getName(), activity_3.getAmount(), null,
                            activity_3.getEnableAmount(), 0,
                            Utils.getDayNumOfAppointDate(new Date(), -activity_3.getDeadline()), null, "福利发放", 0, 0,
                            activity_3.getProductDeadline(), activity_3.getMultiple());
                    drMemberFavourableDAO.insertIntoInfo(dmf);
                    if (activity_3.getAmount().compareTo(dmfAmount) > 0) {
                        dmfAmount = activity_3.getAmount();
                    }
                    if (activity_3.getDeadline() > deadline) {
                        deadline = activity_3.getDeadline();
                    }
                    DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(uId + ""), 0, 2, "福利发放", new Date(), 0, 0,
                            "尊敬的用户，" + dmf.getAmount() + "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                }
            }
            // 发送短信和站内信
            String sms = "主人！我是币优铺理财送给你的红包啊，最高" + dmfAmount + "元，有效期" + deadline + "天。已经在您账户了，快来激活提现我吧！！";
            SysMessageLog smsVo = new SysMessageLog(Integer.parseInt(uId + ""), sms, 18, null, mobilephone);
            smsList.add(smsVo);
        }
        // 短信发送
        for (SysMessageLog smsLog : smsList) {
            sysMessageLogService.sendMsg(smsLog, 1);
        }
    }

    @Override
    public BaseResult selectRedEnvelope(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> pam = new HashMap<>();
        List<Map<String, Object>> redEnvelopeList = new LinkedList<>();
        Integer count = 3;
        String res = null;
        BaseResult br = new BaseResult();
        try {
            List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.selectRedEnvelope(map);
            res = list.get(0).getCoupons();
            String[] split = res.split(",");
            map.put("ids", split);
            //查询用户还有几次机会
            count -= drMemberFavourableDAO.selectUserCount(map);
            //查询用已领取的活动红包
            List<DrCouponsIssuedRules> UserRedlist = drCouponsIssuedRulesDAO.selectUserRedEnvelope(map);
            result.put("UserRedlist", UserRedlist);
            result.put("count", count);
            br.setMap(result);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(map.get("uid")+":查看"+e.getMessage());
            br.setErrorMsg("系统错误");
            br.setErrorCode("9999");
            br.setSuccess(false);
            return br;
        }
        return br;
    }

    @Override
    public BaseResult lootRedEnvelope(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> pam = new HashMap<>();
        BaseResult br = new BaseResult();
        String value = String.valueOf(System.currentTimeMillis());
        Integer count = 3;
        List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.selectRedEnvelope(map);
        String res = list.get(0).getCoupons();
        String[] split = res.split(",");
        map.put("ids", split);
        Integer activityId = null;
        try {
            //开始兑换逻辑
            Boolean relockFlag = redisClientTemplate.tryLock(ConfigUtil.RedEnvelope + map.get("uid"), 30, TimeUnit.SECONDS, false, value);// 枷锁
            String fid = map.get("fid").toString();
            if (relockFlag) {
                //查看用户当天是否已经兑换改红包
                Integer coun=drMemberFavourableDAO.selectByParameterId(map);
                if (coun!=0){
                    br.setErrorMsg("用户已经兑换过这个了");
                    br.setErrorCode("9996");
                    br.setSuccess(false);
                    return br;
                }
                //查询用户还有几次机会
                count -= drMemberFavourableDAO.selectUserCount(map);
                if (count >= 1) {
                    DrActivityParameter dap = drActivityParameterDAO.selectParameterPrimaryKey(map);
                    DrMemberFavourable dmf = new DrMemberFavourable(Integer.parseInt(fid), Integer.parseInt(map.get("uid").toString()), 1, dap.getCode(), dap.getName(), dap.getAmount(),
                            dap.getRaisedRates(), dap.getEnableAmount(), 0, Utils.getDayEnd(), "新年红包", 0, 0, dap.getProductDeadline(), dap.getMultiple(), null);
                    drMemberFavourableDAO.insertIntoInfo(dmf);//添加
                } else {
                    br.setErrorMsg("用户实际没有次数可以兑换了");
                    br.setErrorCode("9997");
                    br.setSuccess(false);
                    return br;
                }
                br.setSuccess(true);
                br.setMap(result);
            } else {
                br.setErrorMsg("redis加锁了");
                br.setErrorCode("9998");
                br.setSuccess(false);
                return br;
            }
        } catch (Exception e) {
            log.error(map.get("uid")+"兑换"+e.getMessage());
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误");
            br.setSuccess(false);
            return br;
        } finally {
            redisClientTemplate.releaseLock(ConfigUtil.RedEnvelope + map.get("uid"), value);//解锁
        }
        return br;
    }

    @Override
    public Map<String, Object> getEnvelope(Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> pam = new HashMap<>();
        List<Map<String, Object>> redEnvelopeList = new LinkedList<>();
        String res = null;
        try {
            List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.selectRedEnvelope(map);
            res = list.get(0).getCoupons();
            String[] split = res.split(",");
            map.put("ids", split);
            for (String s : split) {
                Map<String, Object> resRedEnvelope = new HashMap<>();
                pam.put("id", s);
                DrActivityParameter dr = drActivityParameterDAO.selectActivityParameterPrimaryKey(pam);
                resRedEnvelope.put("RedEnvelope", dr);
                redEnvelopeList.add(resRedEnvelope);
            }
            result.put("redEnvelopeList", redEnvelopeList);
            result.put("timeOut", false);
        } catch (Exception e) {
            log.error("获取"+e.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult getRedEnvelopeTop(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> pam = new HashMap<>();
        String res = null;
        BaseResult br = new BaseResult();
        try {
            List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.selectRedEnvelope(map);
            res = list.get(0).getCoupons();
            String[] split = res.split(",");
            map.put("ids", split);
            //查询用已领取的活动红包
            List<DrCouponsIssuedRules> UserRedlist = drCouponsIssuedRulesDAO.selectRedEnvelopeTop(map);
            result.put("allUserRedlist", UserRedlist);
            br.setMap(result);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("getRedEnvelopeTop"+e.getMessage());
            br.setErrorMsg("系统错误");
            br.setErrorCode("9999");
            br.setSuccess(false);
            return br;
        }
        return br;
    }

}
