package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.jsjf.common.*;
import com.jsjf.service.integral.TaskIntegralRulesService;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberCrushDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrCompanyFundsLogService;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.subject.DrSubjectInfoService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.HttpClient431Util;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.utils.MapHelper;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;
import com.jytpay.vo.JYTWYResultData;
import com.jzh.FuiouConfig;
import com.jzh.data.APPRechargeRspData;
import com.jzh.data.FuiouOnlineBankingRspData;
import com.jzh.data.RechargeRspData;
import com.jzh.service.JZHService;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.DecipherWeb;
import com.reapal.utils.ReapalSubmit;
import com.wechat.util.ModelPassivityMessageSendUtil;


@Service
@Transactional
public class DrMemberCrushServiceImpl implements DrMemberCrushService {
    private static Logger logger = Logger.getLogger(DrMemberCrushServiceImpl.class);
    @Autowired
    private DrMemberCrushDAO drMemberCrushDAO;
    @Autowired
    private DrMemberBaseInfoDAO drMemberBaseInfoDAO;
    @Autowired
    private DrMemberFundsDAO drMemberFundsDAO;
    @Autowired
    private DrMemberFundsLogDAO drMemberFundsLogDAO;
    @Autowired
    private DrMemberMsgDAO drMemberMsgDAO;
    @Autowired
    private SysMessageLogService sysMessageLogService;
    @Autowired
    private DrMemberDAO drMemberDAO;
    @Autowired
    public DrMemberBankDAO drMemberBankDAO;
    @Autowired
    public DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    public RedisClientTemplate redisClientTemplate;
    @Autowired
    public DrProductInfoService drProductInfoService;
    @Autowired
    public DrSubjectInfoService drSubjectInfoService;
    @Autowired
    public DrClaimsInfoService drClaimsInfoService;
    @Autowired
    public DrCompanyFundsLogService drCompanyFundsLogService;
    @Autowired
    private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;

    @Override
    public BaseResult getMemberCrushList(DrMemberCrush drMemberCrush, PageInfo pi) {
        Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("startDate", Utils.format(drMemberCrush.getStartDate(), "yyyy-MM-dd"));
        map.put("endDate", Utils.format(drMemberCrush.getEndDate(), "yyyy-MM-dd"));
        map.put("realName", drMemberCrush.getRealName());
        map.put("phone", drMemberCrush.getPhone());
        map.put("channel", 4);
        map.put("status", 0);
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<DrMemberCrush> list = drMemberCrushDAO.getDrMemberCrushList(map);
        Integer total = drMemberCrushDAO.getDrMemberCrushCounts(map);
        pi.setTotal(total);
        pi.setRows(list);

        resultMap.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(resultMap);
        return br;
    }

    @Override
    public void updateMemberCrushRefuse(DrMemberCrush drMemberCrush, SysUsersVo userVo) throws Exception {
        drMemberCrush.setStatus(2);
        drMemberCrush.setAuditId(userVo.getUserKy().intValue());
        drMemberCrush.setAuditTime(new Date());
        drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
    }

    @Override
    public void updateMemberCrushAudit(int id, SysUsersVo userVo) throws Exception {
        DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushById(id);
        DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());

        DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
        mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
        mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
        drMemberFundsDAO.updateDrMemberFunds(mf);

        DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), mf.getBalance(), 3,
                "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
        drMemberFundsRecordDAO.insert(record);

        DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(), drMemberCrush.getAmount(), 6, 1,
                "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
        drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

        // 充值成功 发送站内信
        sendMsg(drMemberCrush, mf, member);

        drMemberCrush.setStatus(1);
        drMemberCrush.setAuditId(userVo.getUserKy().intValue());
        drMemberCrush.setAuditTime(new Date());
        drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
    }

    @Override
    public BaseResult getMemberCrushRecordList(DrMemberCrush drMemberCrush, PageInfo pi) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("startDate", Utils.format(drMemberCrush.getStartDate(), "yyyy-MM-dd"));
        map.put("endDate", Utils.format(drMemberCrush.getEndDate(), "yyyy-MM-dd"));
        map.put("realName", drMemberCrush.getRealName());
        map.put("phone", drMemberCrush.getPhone());
        map.put("payNum", drMemberCrush.getPayNum());
        map.put("channel", drMemberCrush.getChannel());
        map.put("status", drMemberCrush.getStatus());
        map.put("recommCodes", drMemberCrush.getRecommCodes());
        map.put("type", drMemberCrush.getType());
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<DrMemberCrush> list = drMemberCrushDAO.getDrMemberCrushList(map);
        Integer total = drMemberCrushDAO.getDrMemberCrushCounts(map);
        pi.setTotal(total);
        pi.setRows(list);
        resultMap.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(resultMap);
        return br;
    }

    @Override
    public Double getDrMemberCrushRecordSum(DrMemberCrush drMemberCrush) {
        Double recordSum = 0.0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", Utils.format(drMemberCrush.getStartDate(), "yyyy-MM-dd"));
        map.put("endDate", Utils.format(drMemberCrush.getEndDate(), "yyyy-MM-dd"));
        map.put("realName", drMemberCrush.getRealName());
        map.put("phone", drMemberCrush.getPhone());
        map.put("payNum", drMemberCrush.getPayNum());
        map.put("channel", drMemberCrush.getChannel());
        map.put("status", drMemberCrush.getStatus());
        map.put("type", drMemberCrush.getType());
        recordSum = drMemberCrushDAO.getDrMemberCrushRecordSum(map);
        return recordSum;
    }

    @Override
    public BaseResult addMemberCrush(DrMemberCrush drMemberCrush, SysUsersVo usersVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        BaseResult result = new BaseResult();
        map.put("mobilePhone", drMemberCrush.getPhone());
        map.put("realName", drMemberCrush.getRealName());
        map.put("idCards", drMemberCrush.getIdCards());
        try {
            DrMemberBaseInfo drMemberBaseInfos = drMemberBaseInfoDAO.selectByParam(map);
            if (Utils.isObjectEmpty(drMemberBaseInfos)) {
                result.setSuccess(false);
                result.setErrorMsg("充值信息填写有误，系统找不到该信息对应的用户信息！请重试！");
                return result;
            } else {
                DrMemberCrush crush = new DrMemberCrush();
                crush.setUid(drMemberBaseInfos.getUid());
                crush.setAmount(drMemberCrush.getAmount());
                crush.setPoundFee(new BigDecimal(0));  //充值手续费
                crush.setStatus(0);
                crush.setChannel(4);  //冲值渠道
                crush.setSubmitUserKy(usersVo.getUserKy().intValue());
                drMemberCrushDAO.insertDrMemberCrush(crush);
                result.setSuccess(true);
                result.setMsg("申请成功！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg("申请失败！");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DrMemberCrush getDrMemberCrushById(int lid) {
        return drMemberCrushDAO.getDrMemberCrushById(lid);
    }

    @Override
    public void updatePayResult(String paynum) throws Exception {
        logger.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ":开始实时查询充值结果:" + paynum);
        DrMemberCrush drMemberCrush = drMemberCrushDAO.getdrMemberCrushByPaynum(paynum);

        if (Utils.isObjectNotEmpty(drMemberCrush) && drMemberCrush.getStatus() == 0) {
            Map<String, String> param = new HashMap<>();
            param.put("txn_ssn", drMemberCrush.getPayNum());
            param.put("mchnt_txn_ssn", Utils.createOrderNo(6, drMemberCrush.getUid(), ""));
            param.put("busi_tp", "PW11");
            param.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), 1), "yyyy-MM-dd") + " 00:00:00");
            param.put("end_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), -1), "yyyy-MM-dd") + " 23:59:59");

            BaseResult result = FuiouConfig.QueryCzTx(param);
            if (result.isSuccess()) {
                if (result.getMsg().contains("0000|成功")) {
                    DrMemberCrush crush = drMemberCrushDAO.getdrMemberCrushByPaynum(drMemberCrush.getPayNum());
                    if (Utils.isObjectNotEmpty(crush) && crush.getStatus() == 0) {
                        crush.setAuditId(0);
                        crush.setAuditTime(new Date());

                        DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(crush.getUid());
                        DrMemberFunds funds = new DrMemberFunds();
                        funds.setUid(crush.getUid());
                        funds.setFuiou_balance(fund.getFuiou_balance().add(crush.getAmount()));
                        funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(crush.getAmount()));

                        drMemberFundsDAO.updateDrMemberFunds(funds);

                        crush.setStatus(1);
                        crush.setRemark(result.getMsg());
                        drMemberCrushDAO.updateMemberCrushById(crush);

                        DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, crush.getUid(), 1, 1, crush.getAmount(), funds.getFuiou_balance(), 3,
                                "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", crush.getPayNum());
                        drMemberFundsRecordDAO.insert(record);

                        DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(crush.getUid(), record.getId(), crush.getAmount(), 6, 1,
                                "充值金额：【" + crush.getAmount().setScale(2) + "】");
                        drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
                        // 充值成功 发送站内信
                        DrMemberFunds f = new DrMemberFunds();
                        DrMember dm = drMemberDAO.selectByPrimaryKey(crush.getUid());
                        f.setBalance(funds.getFuiou_balance());
                        sendMsg(drMemberCrush, f, dm);

                        //记公司账户日志 收取手续费
                        BigDecimal amount = crush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                        BigDecimal poundage = new BigDecimal("2");
                        poundage = poundage.compareTo(amount) == 1 ? poundage : amount;

                        JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                        companyAccountLog.setCompanyfunds(17);//资金类型
                        companyAccountLog.setType(0);//支出
                        companyAccountLog.setAmount(poundage);//金额
                        companyAccountLog.setStatus(3);//成功
                        companyAccountLog.setRemark(dm.getMobilephone() + "用户充值手续费");
                        companyAccountLog.setAddTime(new Date());
                        companyAccountLog.setChannelType(2);//存管
                        companyAccountLog.setUid(dm.getUid());//用户id
                        jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
                    }
                }
            }
        }
    }

    @Override
    public void updatePayResult() throws Exception {
        long t1 = System.currentTimeMillis();
        logger.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ":开始实时查询充值结果");

        List<DrMemberCrush> list = drMemberCrushDAO.getDrMemberCrush();
        if (!Utils.isEmptyList(list)) {
            for (DrMemberCrush drMemberCrush : list) {
                if (Utils.isObjectNotEmpty(drMemberCrush)) {
                    if (1 == drMemberCrush.getType()) {
                        JYTSendData sendData = new JYTSendData();
                        sendData.setOri_tran_flowid(drMemberCrush.getPayNum());
                        sendData.setTran_code(MockClientMsgBase.QUERY_COLLECTION_TRAN_CODE);
                        JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
                        if (Utils.isObjectNotEmpty(resultData)) {
                            if ("S0000000".equals(resultData.getResp_code())) {
                                if ("S0000000".equals(resultData.getTran_resp_code()) && "01".equals(resultData.getTran_state())) {
                                    drMemberCrush.setStatus(1);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

                                    DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
                                    mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
                                    mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
                                    drMemberFundsDAO.updateDrMemberFunds(mf);

                                    DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), mf.getBalance(), 3,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
                                    drMemberFundsRecordDAO.insert(record);

                                    DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(), drMemberCrush.getAmount(), 6, 1,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

                                    DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());
                                    sendMsg(drMemberCrush, mf, member);
                                } else if ("03".equals(resultData.getTran_state())) {
                                    drMemberCrush.setStatus(2);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrush.setRemark(resultData.getTran_resp_desc());
                                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                                }
                            }
                        }
                    } else if (2 == drMemberCrush.getType()) {
                        Map<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("tranCode", "TN2001");
                        paramMap.put("version", "1.0.0");
                        paramMap.put("charset", "utf-8");
                        paramMap.put("merchantId", MockClientMsgBase.WY_MERCHANT_ID);
                        paramMap.put("oriMerOrderId", drMemberCrush.getPayNum());
                        paramMap.put("orderType", "0");
                        paramMap.put("signType", "SHA256");
                        paramMap.put("key", MockClientMsgBase.WY_KEY);
                        paramMap = MapHelper.signMap(paramMap);
                        paramMap.remove("key");
                        // 获取执行结果
                        String res = HttpClient431Util.doPost(paramMap, MockClientMsgBase.WY_QUERY_URL);
                        if (!Utils.strIsNull(res)) {
                            JYTWYResultData resultDate = JSON.parseObject(res, JYTWYResultData.class);
                            if ("S0000000".equals(resultDate.getRespCode())) {
                                //00-初始 01-支付中，02-支付成功，03-支付失败，04-过期订单 ,05-撤销成功,06-作废订单
                                if ("02".equals(resultDate.getTranState())) {
                                    drMemberCrush.setStatus(1);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

                                    DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
                                    mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
                                    mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
                                    drMemberFundsDAO.updateDrMemberFunds(mf);

                                    DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), mf.getBalance(), 3,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
                                    drMemberFundsRecordDAO.insert(record);

                                    DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(), drMemberCrush.getAmount(), 6, 1,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

                                    DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());
                                    sendMsg(drMemberCrush, mf, member);
                                } else if (!"00".equals(resultDate.getTranState()) && !"01".equals(resultDate.getTranState())) {
                                    drMemberCrush.setStatus(2);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrush.setRemark(resultDate.getRespDesc());
                                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                                }
                            } else {
                                drMemberCrush.setStatus(2);
                                drMemberCrush.setAuditTime(new Date());
                                drMemberCrush.setRemark(resultDate.getRespDesc());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                            }
                        }
                    } else if (4 == drMemberCrush.getType()) {//融宝认证
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("merchant_id", ReapalConfig.getMerchant_id());
                        map.put("version", ReapalConfig.getVersion());
                        map.put("order_no", drMemberCrush.getPayNum());
                        //请求接口
                        String url = "/fast/search";
                        //返回结果
                        String post = ReapalSubmit.buildSubmit(map, ReapalConfig.getMerchant_id(), url);
                        //解密返回的数据
                        String res = DecipherWeb.decryptData(post);
                        net.sf.json.JSONObject jasonObject = net.sf.json.JSONObject.fromObject(res);
                        if ("0000".equals(jasonObject.get("result_code"))) {//查询成功
                            if ("completed".equals(jasonObject.get("status"))) {//交易完成
                                drMemberCrush.setRemark("支付：" + jasonObject.get("status"));
                                drMemberCrush.setStatus(1);
                                drMemberCrush.setAuditTime(new Date());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                                DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
                                mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
                                mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
                                drMemberFundsDAO.updateDrMemberFunds(mf);

                                DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1,
                                        drMemberCrush.getAmount(), mf.getBalance(), 3,
                                        "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
                                drMemberFundsRecordDAO.insert(record);

                                DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
                                        drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                                drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

                                DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());
                                sendMsg(drMemberCrush, mf, member);
                            } else if ("failed".equals(jasonObject.get("status"))) {//交易失败
                                drMemberCrush.setStatus(2);
                                drMemberCrush.setAuditTime(new Date());
                                drMemberCrush.setRemark(jasonObject.get("result_msg").toString());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                            } else if ("closed".equals(jasonObject.get("status"))) {//订单关闭
                                drMemberCrush.setStatus(2);
                                drMemberCrush.setAuditTime(new Date());
                                drMemberCrush.setRemark(jasonObject.get("result_msg").toString());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                            }
                        } else {
                            if (jasonObject.get("result_code") == null) {
                                if (!"wait".equals(jasonObject.get("status"))) {
                                    drMemberCrush.setStatus(2);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrush.setRemark(jasonObject.get("status").toString());
                                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                                }
                            } else {
                                drMemberCrush.setStatus(2);
                                drMemberCrush.setAuditTime(new Date());
                                drMemberCrush.setRemark(jasonObject.get("result_msg").toString());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                            }

                        }
                    } else if (5 == drMemberCrush.getType() || 6 == drMemberCrush.getType()) {//5 存管认证 6存管网银
                        Map<String, String> param = new HashMap<>();
                        param.put("txn_ssn", drMemberCrush.getPayNum());
                        param.put("mchnt_txn_ssn", Utils.createOrderNo(6, drMemberCrush.getUid(), ""));
                        param.put("busi_tp", "PW11");
                        param.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), 1), "yyyy-MM-dd") + " 00:00:00");
                        param.put("end_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), -1), "yyyy-MM-dd") + " 23:59:59");

                        BaseResult result = FuiouConfig.QueryCzTx(param);
                        if (result.isSuccess()) {
                            if (result.getMsg().contains("0000|成功")) {
                                DrMemberCrush crush = drMemberCrushDAO.getdrMemberCrushByPaynum(drMemberCrush.getPayNum());
                                if (Utils.isObjectNotEmpty(crush) && crush.getStatus() == 0) {
                                    crush.setAuditId(0);
                                    crush.setAuditTime(new Date());

                                    DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(crush.getUid());
                                    DrMemberFunds funds = new DrMemberFunds();
                                    funds.setUid(crush.getUid());
                                    funds.setFuiou_balance(fund.getFuiou_balance().add(crush.getAmount()));
                                    funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(crush.getAmount()));

                                    drMemberFundsDAO.updateDrMemberFunds(funds);

                                    crush.setStatus(1);
                                    crush.setRemark(result.getMsg());
                                    drMemberCrushDAO.updateMemberCrushById(crush);

                                    DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), funds.getFuiou_balance(), 3,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
                                    drMemberFundsRecordDAO.insert(record);

                                    DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(), drMemberCrush.getAmount(), 6, 1,
                                            "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
                                    // 充值成功 发送站内信
                                    DrMemberFunds f = new DrMemberFunds();
                                    DrMember dm = drMemberDAO.selectByPrimaryKey(crush.getUid());
                                    f.setBalance(funds.getFuiou_balance());
                                    sendMsg(drMemberCrush, f, dm);

                                    //记公司账户日志 收取手续费
                                    BigDecimal amount = drMemberCrush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                                    BigDecimal poundage = new BigDecimal("2");
                                    poundage = poundage.compareTo(amount) == 1 ? poundage : amount;

                                    JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                                    companyAccountLog.setCompanyfunds(17);//资金类型
                                    companyAccountLog.setType(0);//支出
                                    companyAccountLog.setAmount(poundage);//金额
                                    companyAccountLog.setStatus(3);//成功
                                    companyAccountLog.setRemark(dm.getMobilephone() + "用户充值手续费");
                                    companyAccountLog.setAddTime(new Date());
                                    companyAccountLog.setChannelType(2);//存管
                                    companyAccountLog.setUid(dm.getUid());//用户id
                                    jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
                                }

                            } else {
                                drMemberCrush.setStatus(2);
                                drMemberCrush.setRemark(result.getErrorMsg() != null ? result.getErrorMsg() : result.getMsg());
                                drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                            }
                        }
                    }
                }
            }
        }
        long t2 = System.currentTimeMillis(); // 排序后取得当前时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t2 - t1);

        logger.info("结束实时查询充值结果 耗时: " + c.get(Calendar.MINUTE) + "分 "
                + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
                + " 毫秒");
    }

    private void sendMsg(DrMemberCrush drMemberCrush, DrMemberFunds mf, DrMember member) throws Exception {
        // 充值成功 发送站内信
        DrMemberMsg insertMemberMsg = new DrMemberMsg();
        insertMemberMsg.setRuId(drMemberCrush.getUid());
        insertMemberMsg.setPuId(0);
        insertMemberMsg.setType(3);
        insertMemberMsg.setTitle("充值成功");

        String msg = PropertyUtil.getProperties("rechargeMsg").replace("${1}", drMemberCrush.getAmount().setScale(2).toString())
                .replace("${2}", mf.getBalance().setScale(2).toString());
        insertMemberMsg.setContent(msg); // 消息内容',
        insertMemberMsg.setAddTime(new Date()); // 发送时间',
        insertMemberMsg.setIsRead(0); // 是否阅读0未读，1已读',
        insertMemberMsg.setStatus(0); // 状态，0正常，1删除',
        drMemberMsgDAO.insertDrMemberMsg(insertMemberMsg);

        String sms = PropertyUtil.getProperties("rechargeSms").replace("${1}", member.getRealName())
                .replace("${2}", drMemberCrush.getAmount().setScale(2).toString());
        // 发送手机短信
        if (member.getMobilephone() != null
                && !member.getMobilephone().equals("")) {
            SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 7, null, member.getMobilephone());
            sysMessageLogService.sendMsg(logs);
        }
    }

    public static void main(String[] args) {
        //00-初始 01-支付中，02-支付成功，03-支付失败，04-过期订单 ,05-撤销成功,06-作废订单
        String transtate = "06";
        if ("02".equals(transtate)) {
            System.out.println(2);
        } else if (!"00".equals(transtate) && !"01".equals(transtate)) {
            System.out.println(1);
        }
    }


    @Override
    public void depositsRecharge(JSONObject json, Integer type, SysFuiouNoticeLog log) throws Exception {

        net.sf.json.JSONObject message = json.getJSONObject("message");


        if (JZHService.verifySignAsynNotice(type == 5 ? new RechargeRspData(message) :
                        type == 6 ? new FuiouOnlineBankingRspData(message) : new APPRechargeRspData(message),
                json.getString("signature"))) {
            logger.info("[" + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]充值验签成功---------------------");
            boolean lockFlag = false;
            if (log.getProject_no() == null) {
                DrMember member = drMemberDAO.selectDrMemberByMobilePhone((String) message.get("login_id"));
                String value = String.valueOf(System.currentTimeMillis());
                ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
                try {
                    if (Utils.isObjectNotEmpty(member)) {
                        DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());
                            if (Utils.isNumber((String) message.get("amt"))) {
                                //充值记录
                                DrMemberCrush drMemberCrush = drMemberCrushDAO.getdrMemberCrushByPaynum((String) message.get("mchnt_txn_ssn"));
                                if (Utils.isObjectNotEmpty(drMemberCrush) && drMemberCrush.getStatus() == 0) {
                                    drMemberCrush.setAuditId(0);
                                    drMemberCrush.setAuditTime(new Date());
                                    drMemberCrush.setType(type == 6 ? type : 5);//   5,7,都是存管认证,6是存管网银(方法上的type 是用来判断 认证和网银的, crush里的type 只有5认证,6城管)
                                    if ("0000".equals((String) message.get("resp_code"))) {
                                        if (drMemberCrush.getUid().intValue() == member.getUid().intValue()) {
                                            DrMemberFunds funds = new DrMemberFunds();
                                            funds.setUid(fund.getUid());
                                            funds.setFuiou_balance(fund.getFuiou_balance().add(drMemberCrush.getAmount()));
                                            funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(drMemberCrush.getAmount()));

                                            drMemberFundsDAO.updateDrMemberFunds(funds);

                                            drMemberCrush.setStatus(1);
                                            drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

                                            DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), funds.getFuiou_balance(), 3,
                                                    "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
                                            drMemberFundsRecordDAO.insert(record);

                                            DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(), drMemberCrush.getAmount(), 6, 1,
                                                    "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                                            drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
                                            // 充值成功 发送站内信
                                            DrMemberFunds f = new DrMemberFunds();
                                            f.setBalance(funds.getFuiou_balance());
                                            sendMsg(drMemberCrush, f, member);
                                            
                                            //微信模板消息
                                            /*String openId = drMemberDAO.queryOpenId(member.getUid());
                                            modelMessage.payJson(openId,Utils.format
                                            		(new Date(), "yyyy-MM-dd HH:mm:ss"),
                                            		drMemberCrush.getAmount().toString(),  
                                            		funds.getFuiou_balance().toString());*/

                                            //记公司账户日志 收取手续费
                                            BigDecimal amount = drMemberCrush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                                            BigDecimal poundage = new BigDecimal("2");
                                            poundage = poundage.compareTo(amount) == 1 ? poundage : amount;

                                            JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                                            companyAccountLog.setCompanyfunds(17);//资金类型
                                            companyAccountLog.setType(0);//支出
                                            companyAccountLog.setAmount(poundage);//金额
/*										companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(new BigDecimal(3)));
*/
                                            companyAccountLog.setStatus(3);//成功
                                            companyAccountLog.setRemark(member.getMobilephone() + "用户充值手续费");
                                            companyAccountLog.setAddTime(new Date());
                                            companyAccountLog.setChannelType(2);//存管
                                            companyAccountLog.setUid(member.getUid());//用户id
                                            jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);

                                            //首冲加积分
                                            //充值任务添加积分
                                            if (drMemberCrush.getAmount().intValue()>=100) {
                                                taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE,drMemberCrush.getAmount().intValue());
                                            }
                                        }

                                    } else {
                                        drMemberCrush.setStatus(2);
                                        drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                                    }
                                }
                            }

                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
                //企业网银充值
                //生成流水记录
                if ("0000".equals((String) message.get("resp_code"))) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", log.getProject_no());
                    DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByMap(map);
                    BigDecimal amount = new BigDecimal(0);
                    if (Utils.isObjectNotEmpty(log.getAmt())) {
                        amount = new BigDecimal(log.getAmt());
                    }
                    String loanNo;//合同号
                    DrSubjectInfo drSubjectInfo;
                    if (Utils.isObjectEmpty(drProductInfo.getSid())) {
                        loanNo = "xxxxxxx";
                    }
                    drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
                    if (Utils.isObjectEmpty(drSubjectInfo)) {
                        loanNo = "xxxxxxx";
                    }
                    DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(drSubjectInfo.getLid());
                    if (Utils.isObjectEmpty(drClaimsLoan)) {
                        loanNo = "xxxxxxx";
                    } else {
                        loanNo = drClaimsLoan.getNo();
                    }

                    String tran_flowid = Utils.createOrderNo(6, drProductInfo.getId(), "");
                    drProductInfoService.updateReFundDrProductLoanStatus(drProductInfo.getId());
                    DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(16, null,
                            drProductInfo.getId(), amount, 1,
                            "债券合同[" + loanNo + "]回款" + amount + "元,流水号[" + tran_flowid + "]", null);
                    drCompanyFundsLogService.insertDrCompanyFundsLog(drCompanyFundsLog);

                    //记公司账户日志 收取手续费
                    BigDecimal money = amount.multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                    BigDecimal poundage = new BigDecimal("2");
                    poundage = poundage.compareTo(money) == 1 ? poundage : money;

                    JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                    companyAccountLog.setCompanyfunds(17);//资金类型
                    companyAccountLog.setType(0);//支出
                    companyAccountLog.setAmount(poundage);//金额

				/*companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(new BigDecimal(3)));*/
                    companyAccountLog.setStatus(3);//成功
                    companyAccountLog.setRemark("商户回款网银充值手续费");
                    companyAccountLog.setAddTime(new Date());
                    companyAccountLog.setChannelType(2);//存管
                    companyAccountLog.setPid(drProductInfo.getId());
				/*companyAccountLog.setUid(member.getUid());//用户id
*/
                    jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);

                }
            }
        } else {
            logger.error("[" + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]" + (type == 5 ? "pc" : type == 6 ? "网银" : "app") + "充值验签失败---------------------");
        }
    }

    @Override
    public void updateFuiouCrush(String paynum, BaseResult result) {
        try {
            DrMemberCrush crush = drMemberCrushDAO.getFuioudrMemberCrushByPaynum(paynum);
            if (Utils.isObjectNotEmpty(crush) && (crush.getType() == 5 || crush.getType() == 6) && crush.getStatus() != 1) {//订单不是成功的

                crush.setAuditId(0);
                crush.setAuditTime(new Date());
                if (result.isSuccess() && result.getMsg().contains("0000|成功")) {

                    DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(crush.getUid());
                    DrMemberFunds funds = new DrMemberFunds();
                    funds.setUid(crush.getUid());
                    funds.setFuiou_balance(fund.getFuiou_balance().add(crush.getAmount()));
                    funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(crush.getAmount()));

                    drMemberFundsDAO.updateDrMemberFunds(funds);

                    crush.setStatus(1);
                    crush.setRemark(result.getMsg());
                    drMemberCrushDAO.updateMemberCrushById(crush);

                    DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, crush.getUid(), 1, 1, crush.getAmount(), funds.getFuiou_balance(), 3,
                            "充值金额：【" + crush.getAmount().setScale(2) + "】", crush.getPayNum());
                    drMemberFundsRecordDAO.insert(record);

                    DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(crush.getUid(), record.getId(), crush.getAmount(), 6, 1,
                            "充值金额：【" + crush.getAmount().setScale(2) + "】");
                    drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
                    // 充值成功 发送站内信
                    DrMemberFunds f = new DrMemberFunds();
                    DrMember dm = drMemberDAO.selectByPrimaryKey(crush.getUid());
                    f.setBalance(funds.getFuiou_balance());
                    sendMsg(crush, f, dm);

                    //记公司账户日志 收取手续费
                    BigDecimal amount = crush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                    BigDecimal poundage = new BigDecimal("2");
                    poundage = poundage.compareTo(amount) == 1 ? poundage : amount;

                    JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                    companyAccountLog.setCompanyfunds(17);//资金类型
                    companyAccountLog.setType(0);//支出
                    companyAccountLog.setAmount(poundage);//金额
                    companyAccountLog.setStatus(3);//成功
                    companyAccountLog.setRemark(dm.getMobilephone() + "用户充值手续费");
                    companyAccountLog.setAddTime(new Date());
                    companyAccountLog.setChannelType(2);//存管
                    companyAccountLog.setUid(dm.getUid());//用户id
                    jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
                } else if (crush.getStatus() == 0) {//失败
                    crush.setStatus(2);//失败
                    crush.setRemark(result.getErrorMsg());
                    drMemberCrushDAO.updateMemberCrushById(crush);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
