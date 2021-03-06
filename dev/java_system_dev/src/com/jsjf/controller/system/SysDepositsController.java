package com.jsjf.controller.system;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.SystemConstant;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.jzh.data.WebArtifRspData;
import com.jzh.data.WtRechargeAndWtWithdrawalRspData;
import com.jzh.data.WtwithdrawRspData;
import com.jzh.service.JZHService;
import com.jzh.util.SecurityUtils;
import com.wechat.util.ModelPassivityMessageSendUtil;

@Controller
@RequestMapping("/deposits")
public class SysDepositsController {

    private static Logger log = Logger.getLogger(SysDepositsController.class);
    @Autowired
    DrMemberService drMemberService;
    @Autowired
    SysFuiouNoticeLogService sysFuiouNoticeLogService;
    @Autowired
    DrMemberCrushService drMemberCrushService;
    @Autowired
    DrMemberCarryService drMemberCarryService;
    @Autowired
    DrClaimsInfoService claimsInfoService;
    @Autowired
    DrProductInvestService drProductInvestService;
    @Autowired
    DrProductInfoService drProductInfoService;
    @Autowired
    RedisClientTemplate redisClientTemplate;
    @Autowired
    DrMemberFundsService drMemberFundsService; 



    @RequestMapping("/openAccountRes")
    @ResponseBody
    public String openAccountRes(HttpServletRequest req) {
        SysFuiouNoticeLog log = null;
        net.sf.json.JSONObject json = showParams(req);//获得请求参数并转成 json
        String mchnt_txn_ssn = "";
        String mobilePhone = "";
        String value = "";
        Boolean lockFlag = false;
        Boolean lockFlags = false;
        if (Utils.isObjectNotEmpty(json)) {
            try {
                net.sf.json.JSONObject message = json.getJSONObject("message"); //报文正文JSON对象
                mobilePhone = (String) message.get("login_id");
                mchnt_txn_ssn = (String) message.get("mchnt_txn_ssn");
                log = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(mchnt_txn_ssn);
                if (Utils.isObjectNotEmpty(log)) {

                    if (FuiouConfig.WEBREG.equals(log.getIcd()) || FuiouConfig.APPWEBREG.equals(log.getIcd())) {//个人开户
                        drMemberService.openAccountRes(json);

                    } else if (FuiouConfig.PCQRECHARGE500405.equals(log.getIcd())) {//快捷充值FuiouConfig.APPQRECHARGE2.equals(log.getIcd())
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        lockFlags = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag && lockFlags) {
                            drMemberCrushService.depositsRecharge(json, 5, log);
                        }
                    } else if (FuiouConfig.APPQRECHARGE2.equals(log.getIcd())) {//app快捷充值
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        lockFlags = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag && lockFlags) {
                            drMemberCrushService.depositsRecharge(json, 7, log);
                        }

                    } else if (FuiouConfig.WITHDRAW.equals(log.getIcd())) {//pc提现异步通知
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag) {
                            drMemberCarryService.withdrawalsData(json);
                        }
                    } else if (FuiouConfig.APPWITHDRAW.equals(log.getIcd())) {//app提现异步通知
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag) {
                            drMemberCarryService.withdrawalsData(json);
                        }
                    } else if (FuiouConfig.BRECHARGE.equals(log.getIcd())) {//网银跳存管
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        lockFlags = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag && lockFlags) {
                            drMemberCrushService.depositsRecharge(json, 6, log);
                        }

                    } else if (FuiouConfig.BRECHARGE12.equals(log.getIcd())) {//网银跳银行
                        value = String.valueOf(System.currentTimeMillis());
                        lockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        lockFlags = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + mobilePhone, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
                        if (lockFlag && lockFlags) {
                            drMemberCrushService.depositsRecharge(json, 6, log);
                        }
                    } else if (FuiouConfig.WEBARTIFREG.equals(log.getIcd())) {//企业开户
                        claimsInfoService.updateDrClaimsCustomer(json);

                    }

                    //记录存管返回的日志
                    log.setMessage(json.toString());
                    log.setStatus("0000".equals((String) message.get("resp_code")) ? 2 : 3);//2成功3失败
                    log.setResp_code((String) message.get("resp_code"));
                    log.setResp_desc(message.containsKey("resp_desc") ? (String) message.get("resp_desc") : null);
                    sysFuiouNoticeLogService.update(log);
                    System.out.println("存管处理通知结束");

                    //通知成返回成功
                    return FuiouConfig.notifyRspXml("0000", log.getMchnt_txn_ssn());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return FuiouConfig.notifyRspXml("1111", mchnt_txn_ssn);
            } finally {
                if (lockFlag) {
                    redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash + mobilePhone, value);
                }
                if (lockFlags) {
                    redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + mobilePhone, value);
                }
            }
        }
            //通知成返回成功
            return FuiouConfig.notifyRspXml("1111", mchnt_txn_ssn);
    }

    /**
     * 委托提现通知（失败和成功都会通知的  这个失败的是包含交易流程走完了的  如果发起交易就失败了  没有走完完整流程的  这种失败的交易是不会通知的）
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/txTpBackMchntRspData")
    @ResponseBody
    public String txTpBackMchntRspData(HttpServletRequest req) throws Exception {

        WtRechargeAndWtWithdrawalRspData rspData = new WtRechargeAndWtWithdrawalRspData();
        String signature = req.getParameter("signature");
        BeanUtils.populate(rspData, req.getParameterMap());
        boolean signVal = SecurityUtils.verifySign(rspData.createSignValue(), signature);
        if (signVal) {
        	log.info("[" + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]委托提现验签成功---------------------");
            SysFuiouNoticeLog log = null;
            if (rspData.getMchnt_txn_ssn() != null) {
                try {
                    log = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(rspData.getMchnt_txn_ssn());
                    if (Utils.isObjectNotEmpty(log)) {
                        if (log.getStatus().equals(2) && !rspData.getResp_code().equals("0000")) {//失败
                            //冻结
                            BaseResult result = new BaseResult();
                            Map<String, Object> params = new HashMap<>();
                            params.put("uid", "1");//为了生成流水号用到
                            params.put("cust_no", log.getUser_id());
                            params.put("amt", log.getAmt());
                            params.put("rem", "");
                            result = FuiouConfig.freeze(params);
                            if (!result.isSuccess()) {//冻结失败
                                return FuiouConfig.notifyRspXml("1111", log.getMchnt_txn_ssn());
                            }
                            BaseResult br = drProductInfoService.updateDrProductLoanByMchntTxnSsn(rspData.getMchnt_txn_ssn());
                            //记录存管返回的日志
                            log.setMessage(rspData.createSignValue());
                            log.setStatus(3);//3失败
                            sysFuiouNoticeLogService.update(log);
                            System.out.println("存管处理通知结束");
                        }
                        return FuiouConfig.notifyRspXml("0000", log.getMchnt_txn_ssn());
                    } else {
                        return FuiouConfig.notifyRspXml("1111", log.getMchnt_txn_ssn());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return FuiouConfig.notifyRspXml("1111", log.getMchnt_txn_ssn());
                }
            } else {
                //通知成返回成功
                return FuiouConfig.notifyRspXml("1111", log.getMchnt_txn_ssn());
            }
        } else {
            return FuiouConfig.notifyRspXml("1111", rspData.getMchnt_txn_ssn());
        }

    }


    private net.sf.json.JSONObject showParams(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        net.sf.json.JSONObject message = new net.sf.json.JSONObject();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    if (!"signature".equals(paramName)) {
                        message.put(paramName, paramValue);
                    } else {
                        json.put(paramName, paramValue);
                    }

                }
            }
        }
        json.put("message", message);
//        System.out.println("恒丰2.0返回参数:"+json.toString());
        log.info("----------------------存管异步通知 -返回参数:" + json.toString());
        return json;
    }

    @RequestMapping("/openPageNotify")
    public String openPageNotify(HttpServletRequest req,
                                 Map<String, Object> model) {
        // Map<String,Object> map = new HashMap<String, Object>();
        SysFuiouNoticeLog noticeLog = null;
        net.sf.json.JSONObject json = getParams(req);
        try {
            req.setCharacterEncoding("UTF-8");
            if (Utils.isObjectNotEmpty(json)) {
                JSONObject message = json.getJSONObject("message"); // 报文正文JSON对象
                String resp_desc = (String) message.get("resp_desc");
                String resp_code = (String) message.get("resp_code");
                if (JZHService.verifySignAsynNotice(new WebArtifRspData(message), json.getString("signature"))) {
                    noticeLog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn((String) message.get("mchnt_txn_ssn"));
                    if (noticeLog.getIcd().equals(FuiouConfig.WEBARTIFREG)) {// 企业开户
                        model.put("resp_desc", resp_desc);
                        model.put("resp_code", resp_code);
                        return "system/claims/fuiouSuccess";
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/common/include/error";
        // BaseResult br = new BaseResult();
        // Map<String, Object> map = new HashMap<String, Object>();
        // try {
        // req.setCharacterEncoding("UTF-8");
        // if (Utils.isObjectNotEmpty(json)) {
        // System.out.println("fuiou通知收到----");
        // JSONObject jsons = JSONObject.fromObject(json);
        // JSONObject message = jsons.getJSONObject("message"); //报文正文JSON对象
        // String signature = jsons.getString("signature");
        // String resp_desc = (String)message.get("resp_desc");
        // String resp_code = (String)message.get("resp_code");
        // if (SecurityUtils.verifySign(message.toString(), signature)) {
        // SysFuiouNoticeLog noticeLog =
        // sysFuiouNoticeLogService.selectObjectBy_txn_ssn((String)message.get("mchnt_txn_ssn"));
        // if (noticeLog.getIcd().equals(FuiouConfig.ARTIFREG)) {// 企业开户
        // model.put("resp_desc", resp_desc);
        // model.put("resp_code",resp_code);
        // return "system/claims/drClaimsInfoList";
        // }
        // } else {
        // return "/common/include/error";
        // }
        // } else {
        // return "/common/include/error";
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return "/common/include/error";
    }

    private net.sf.json.JSONObject getParams(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        net.sf.json.JSONObject message = new net.sf.json.JSONObject();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    if (!"signature".equals(paramName)) {
                        message.put(paramName, paramValue);
                    } else {
                        json.put(paramName, paramValue);
                    }

                }
            }
        }
        json.put("message", message);
        log.info("fuiou 2.0返回参数:" + json.toString());
        return json;
    }


    /**
     * 提现异步通知(网银(对公,对私),快捷,直连充值)
     * 存管 提现异步通知, 只对充值成的 流水 推送
     * 1是系统在交易完成后定时通知；
     * 2只通知成功交易；
     * 3通知后需收到对方返回成功应答码，没有返回或应答码失败，存管系统会定时再次通知，反复次数为6次。（生产环境通知时间 2m,15m,50m,2h,5h,12h）
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/fuiouWithdrawalsNotice")
    @ResponseBody
    public String fuiouWithdrawalsNotice(HttpServletRequest req) throws Exception {
        WtwithdrawRspData data = new WtwithdrawRspData();
        String signature = req.getParameter("signature");
        SysFuiouNoticeLog noticeLog = null;

        String resp_code = "0001";
        BeanUtils.populate(data, req.getParameterMap());// 数据处理
        String reqParam = net.sf.json.JSONObject.fromObject(data).toString();
        log.info("-----存管提现直连异步通知------\n" + reqParam);
        try {
            if (JZHService.verifySignAsynNotice(data, signature)) {// 验签
                noticeLog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(data.getMchnt_txn_ssn());
                if (Utils.isObjectNotEmpty(noticeLog) && noticeLog.getStatus() == 1) {
                    if (FuiouConfig.WITHDRAW.equals(noticeLog.getIcd()) || FuiouConfig.APPWITHDRAW.equals(noticeLog.getIcd())) {// 直连
                        resp_code = updatecarry(data);
                    }
                    SysFuiouNoticeLog nlog = new SysFuiouNoticeLog();
                    //记录存管返回的日志
                    nlog.setId(noticeLog.getId());
                    nlog.setMessage(reqParam);
                    nlog.setStatus(2);//2成功3失败
                    nlog.setResp_code("0000");
                    nlog.setResp_desc("成功");
                    sysFuiouNoticeLogService.update(nlog);
                }
            }

        } catch (Exception e) {
            log.error("------存管提现-异步通知:系统错误[" + reqParam + "]\n" + e.getMessage(), e);
        }
        return FuiouConfig.notifyRspXml(resp_code, data.getMchnt_txn_dt());
    }

    private String updatecarry(WtwithdrawRspData data) {
        DrMember m = drMemberService.selectDrMemberByMobilePhone(data.getMobile_no());
        boolean relockFlag = false;
        if (Utils.isObjectNotEmpty(m)) {
            String value = String.valueOf(System.currentTimeMillis());
            relockFlag = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + m.getUid(), 30, TimeUnit.SECONDS, false, value);// 枷锁
            try {
                if (relockFlag) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("paymentnum", data.getMchnt_txn_ssn());
                    DrMemberCarry carry = drMemberCarryService.selectDrMemberCarryByPaymentnum(map);
                    if (carry.getStatus() == 0) {//订单存在且未处理
                        carry.setStatus(2);
                        drMemberCarryService.depositsMemberCarry(carry, m);
                    }
                    //通知 处理完成  返回结果
                    return "0000";
                } else {
                    log.info("存管提现-异步通知:系统繁忙");
                }
            } catch (Exception e) {
                log.info("存管提现-异步通知失败:" + e.getMessage());
            } finally {
                if (relockFlag) {
                    redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash + m.getUid(), value);//释放枷锁
                }
            }
        } else {
            log.info("存管充值-异步通知:没有用户");
        }
        return "0001";
    }
}
