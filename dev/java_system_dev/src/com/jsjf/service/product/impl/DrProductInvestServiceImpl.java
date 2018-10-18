package com.jsjf.service.product.impl;

import com.jsjf.common.*;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.activity.FestivaiActivityDao;
import com.jsjf.dao.activity.PrizeManageDao;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.dao.member.*;
import com.jsjf.dao.product.*;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.dao.system.JsMerchantMarketingDAO;
import com.jsjf.dao.system.SysFuiouNoticeLogDAO;
import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.*;
import com.jsjf.model.product.*;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.JsMerchantMarketing;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.activity.DrRecommendedSettingsService;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrCarryParamService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.seq.SeqService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.sensorsdata.SensorsAnalytics;
import com.wechat.util.ModelPassivityMessageSendUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class DrProductInvestServiceImpl implements DrProductInvestService {

    private static Logger logger = Logger.getLogger(DrProductInvestServiceImpl.class);
    @Autowired
    private PrizeManageDao prizeManageDao;
    @Autowired
    private FestivaiActivityDao festivaiActivityDao;
    @Autowired
    private DrProductInvestDAO drProductInvestDAO;
    @Autowired
    private DrProductInfoDAO drProductInfoDAO;
    @Autowired
    private DrMemberFundsDAO drMemberFundsDAO;
    @Autowired
    private DrMemberFundsLogDAO drMemberFundsLogDAO;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    private DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;
    @Autowired
    private DrMemberMsgDAO drMemberMsgDAO;
    @Autowired
    private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
    @Autowired
    private SysMessageLogService sysMessageLogService;
    @Autowired
    private DrMemberDAO drMemberDAO;
    @Autowired
    private DrRecommendedSettingsService drRecommendedSettingsService;
    @Autowired
    private DrMemberRecommendedDAO drMemberRecommendedDAO;
    @Autowired
    private DrProductInvestTransferDAO drProductInvestTransferDAO;
    @Autowired
    private DrSubjectInfoDAO drSubjectInfoDAO;
    @Autowired
    private DrProductInfoRepayDetailDAO drProductInfoRepayDetailDAO;
    @Autowired
    private JsNoviceContinueRecordDAO jsNoviceContinueRecordDAO;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrCarryParamService drCarryParamService;
    @Autowired
    private DrClaimsInfoService drClaimsInfoService;
    @Autowired
    private SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private JsActivityProductDAO jsActivityProductDAO;
    @Autowired
    private JsProductReservationDAO jsProductReservationDAO;
    @Autowired
    private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
    @Autowired
    private JsProductPrizeDAO jsProductPrizeDAO;
    @Autowired
    private JsProductPrizeLogDAO jsProductPrizeLogDAO;
    @Autowired
    private SeqService seqService;
    @Autowired
    private JsActivityProductInvestInfoDAO jsActivityProductInvestInfoDAO;
    @Autowired
    private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
    @Autowired
    private JsMerchantMarketingDAO jsMerchantMarketingDAO;
	@Autowired
	private DrClaimsLoanDAO drClaimsLoanDAO;

    private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);

    @Override
    public BaseResult getDrProductInvestList(DrProductInvest drProductInvest, PageInfo pi) {
        Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("scode", drProductInvest.getScode());
        map.put("realname", drProductInvest.getRealname());
        map.put("mobilephone", drProductInvest.getMobilephone());
        map.put("recommCodes", drProductInvest.getRecommCodes());
        map.put("code", drProductInvest.getCode());
        map.put("startDate", drProductInvest.getStartDate());
        map.put("endDate", drProductInvest.getEndDate());
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        if (drProductInvest.getRepayType() != null) {
            map.put("repayType", drProductInvest.getRepayType());
        }
        List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestList(map);
        Integer total = drProductInvestDAO.getDrProductInvestCounts(map);
        pi.setTotal(total);
        pi.setRows(list);
        resultMap.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(resultMap);
        return br;
    }

    @Override
    public BigDecimal getDrProductInvestByTime(Integer uid, Integer period) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("period", period);
        BigDecimal investAmount = BigDecimal.ZERO;
        investAmount = drProductInvestDAO.getDrProductInvestByTime(map);
        return investAmount;
    }

    @Override
    public BaseResult selectInvestLogByParam(Map<String, Object> map,
                                             PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<DrProductInvest> list = drProductInvestDAO.selectInvestLogByParam(map);
        Integer total = drProductInvestDAO.selectInvestLogByParamCounts(map);
        map.clear();
        pi.setTotal(total);
        pi.setRows(list);
        map.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(map);
        return br;
    }

    @Autowired
    public List<DrProductInfo> selectExpireProductInfo() {
        return drProductInfoDAO.selectExpireProductInfo();
    }

    @Override
    public void saveInvestRepay(DrProductInfo info) throws Exception {
    	SensorsAnalytics sau = SensorsAnalyticsUtils.getInstance();
    	Map<String, Object> properties = new HashMap<String, Object>();
        Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();
        List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
        Date now = new Date();//还款时间
        StringBuffer buff = new StringBuffer();

        List<DrProductInvestRepayInfo> repayList = drProductInvestRepayInfoDAO.selectShouldRepayInfo(info.getId());
        DrProductInfoRepayDetail productRepayDetail = null;
        boolean expireFlag = true;//最后一期标记位
        if (info.getRepayType() == 2) {
            productRepayDetail = drProductInfoRepayDetailDAO.selectEarliestUnreimbursement(info.getId());
            expireFlag = productRepayDetail.getPeriods() == info.getDeadline() / 30;//判断按月付息的是否到期
        }
        BigDecimal infoInterest = BigDecimal.ZERO;//支付总利息
        BigDecimal infoPrincipal = BigDecimal.ZERO;
        if (repayList.size() > 0) {
        	ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
            for (int j = 0; j < repayList.size(); j++) {
                //回款信息
                DrProductInvestRepayInfo repayInfo = repayList.get(j);
                repayInfo.setFactPrincipal(repayInfo.getShouldPrincipal());
                repayInfo.setFactInterest(repayInfo.getShouldInterest());
                repayInfo.setFactTime(now);
                repayInfo.setStatus(1);//已还款
                infoInterest = infoInterest.add(repayInfo.getShouldInterest());
                infoPrincipal = infoPrincipal.add(repayInfo.getShouldPrincipal());

                DrMember member = drMemberDAO.selectByPrimaryKey(repayInfo.getUid());

                //资金变动
                DrMemberFunds funds = null;
                if (fundsMap.containsKey(repayInfo.getUid())) {
                    funds = fundsMap.get(repayInfo.getUid());
                } else {
                    funds = drMemberFundsDAO.queryDrMemberFundsByUid(repayInfo.getUid());
                }
                funds.setBalance(funds.getBalance().add(repayInfo.getFactPrincipal()).add(repayInfo.getFactInterest()));//账户余额
                funds.setWprincipal(funds.getWprincipal().subtract(repayInfo.getFactPrincipal()));//待收本金
                funds.setWinterest(funds.getWinterest().subtract(repayInfo.getFactInterest()));//待收利息
                funds.setInvestProfit(funds.getInvestProfit().add(repayInfo.getFactInterest()));//投资收益
                drMemberFundsDAO.updateDrMemberFunds(funds);
                fundsMap.put(repayInfo.getUid(), funds);

                //按月付息的，查询当前的还款期数

                if (info.getRepayType() == 2) {
                    DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(), repayInfo.getInvestId(), repayInfo.getUid(),
                            6, 1, repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()), funds.getBalance(), 3, "产品【" + info.getFullName() + "】第" + productRepayDetail.getPeriods() + "期回款", null);
                    drMemberFundsRecordDAO.insert(fundsRecord);

                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(), fundsRecord.getId(), repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()),
                            11, 1, "产品【" + info.getFullName() + "】第" + productRepayDetail.getPeriods() + "期回款");
                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                } else {

                    DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(), repayInfo.getInvestId(), repayInfo.getUid(),
                            info.getType() == 5 ? 7 : 6, 1, repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()), funds.getBalance(), 3, "产品【" + info.getFullName() + "】回款", null);
                    drMemberFundsRecordDAO.insert(fundsRecord);

                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(), fundsRecord.getId(), repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()),
                            11, 1, "产品【" + info.getFullName() + "】回款");
                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                }
                //新手标续投冻结资金
                if (info.getType() == 1) {//目前没有站内信
                    JsNoviceContinueRecord jncr = jsNoviceContinueRecordDAO.selectJsNoviceContinueRecord(repayInfo.getUid());
                    if (Utils.isObjectNotEmpty(jncr)) {
                        //冻结资金
                        funds.setBalance(Utils.nwdBcsub(funds.getBalance(), jncr.getAmount()));//减余额
                        funds.setFreeze(Utils.nwdBcadd(funds.getFreeze(), jncr.getAmount()));//加冻结

                        drMemberFundsDAO.updateDrMemberFunds(funds);
                        fundsMap.put(repayInfo.getUid(), funds);
                        //冻结资金日志1
                        DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, jncr.getUid(), 3,
                                0, jncr.getAmount(), funds.getBalance(), 5, "自动续投" + jncr.getPeriod() + "天产品", null);
                        drMemberFundsRecordDAO.insert(record);
                        //冻结资金日志2
                        DrMemberFundsLog log = new DrMemberFundsLog(repayInfo.getUid(), record.getId(), jncr.getAmount(), 3, 0, "自动续投" + jncr.getPeriod() + "天产品");
                        drMemberFundsLogDAO.insertDrMemberFundsLog(log);
                        logger.info("新手标待续投冻结资金[uid:" + jncr.getUid() + ",amount:" + jncr.getAmount() + "]");
                    }
                }

                String msgStr = "";//站内信
                String smslogStr = "";//短信
                if (info.getRepayType() == 2 && !expireFlag) {


                    if (info.getType() == 5) {
                        smslogStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthSms")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                        msgStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthMsg")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${interest}", repayInfo.getFactInterest().toString());

                    } else {
                        //每月付息站内信
                        msgStr = PropertyUtil.getProperties("paymentMonthMsg")
                                .replace("${realName}", member.getRealName())
                                .replace("${fullName}", info.getFullName())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                        //每月付息短信
                        smslogStr = PropertyUtil.getProperties("paymentMonthMsg")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${fullName}", info.getFullName())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                    }
                } else {
                    if (info.getType() == 5) {
                        //短信
                        smslogStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthSms")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                        //站内信
                        msgStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthMsg")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                    } else {
                        //站内信
                        msgStr = PropertyUtil.getProperties("paymentMsg")
                                .replace("${fullName}", info.getFullName())
                                .replace("${amount}", repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()).toString())
                                .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                        //短信
                        smslogStr = PropertyUtil.getProperties("paymentSms")
                                .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                .replace("${fullName}", info.getFullName())
                                .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                .replace("${interest}", repayInfo.getFactInterest().toString());
                    }
                }


                //回款成功站内信
                DrMemberMsg msg = new DrMemberMsg(repayInfo.getUid(), 0, 3, "回款通知", now, 0, 0, msgStr);
                drMemberMsgDAO.insertDrMemberMsg(msg);
                //回款成功短信
                SysMessageLog smslog = new SysMessageLog(repayInfo.getUid(), smslogStr,
                        14, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:mm:ss"), "yyyy-MM-dd HH:mm:ss"), member.getMobilephone());
                smsList.add(smslog);

                drProductInvestRepayInfoDAO.updateById(repayInfo);
                if (expireFlag) {
                    buff.append(repayInfo.getInvestId() + ",");
                }
                getType(properties,info);
                if(Utils.isObjectNotEmpty(info.getCode())){
   				 	properties.put("LoanId", info.getCode()); 
   			 	}
                DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.selectByPrimaryKey1(info.getId());
                if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(drClaimsLoan.getLoanUse())){
              	  properties.put("ProjectType",drClaimsLoan.getLoanUse());
	            }
	            if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(info.getFullName())){
	              	properties.put("ProjectName", info.getFullName()+"");
	            }
                if(Utils.isObjectNotEmpty(info.getDeadline())){
                	properties.put("ProjectDeadline", info.getDeadline()+"");
                }
                properties.put("ProjectDeadline", info.getDeadline()+"");
                properties.put("Rate", info.getRate());
                properties.put("IncomeType", info.getRepayTypeName()+"");
                properties.put("ReleaseTime", info.getStartDate());
                //查询优惠券金额
                String amountStr = drMemberFundsLogDAO.queryCouponAmount(info.getId());
                if(Utils.isObjectNotEmpty(amountStr)){
                	 properties.put("CouponAmount", Integer.parseInt(amountStr));
                }
                properties.put("ActualPaidAmount", info.getAmount());
                properties.put("RepaymentPrincipal", repayInfo.getShouldPrincipal());
                properties.put("RepaymentInterest", repayInfo.getShouldInterest());
                if(Utils.isObjectNotEmpty(info.getExpireDate())){
                	 properties.put("DueDate", info.getExpireDate());
                }
               
                Calendar c = Calendar.getInstance();  
                c.setTime(now);  
                c.add(Calendar.DAY_OF_MONTH, 1);
                properties.put("ArrivalTime", c.getTime());
                properties.put("ISFullyDue", "是");
                
                Integer firstCount = drProductInvestRepayInfoDAO.selectIsFirstShouldRepayInfo(info.getId());
                if(firstCount.equals(0)){
                	 Map<String, Object> properties2 = new HashMap<String, Object>();
                     properties2.put("first_repayment_time", new Date());
                     sau.profileSet(String.valueOf(repayInfo.getUid()), true,properties2);
                }
                sau.track(String.valueOf(repayInfo.getUid()), true, "InvestDue" ,properties);
                
                //微信模板消息
                String openId = drMemberDAO.queryOpenId(member.getUid());
                if(Utils.isObjectNotEmpty(openId)){
	                modelMessage.returnedMoneyJson(openId, repayInfo.getShouldPrincipal(),
	                		funds.getInvestProfit(), info.getFullName());
                }
                
            }
            if (info.getRepayType() == 2) { //修改产品按月付息的详情的状态
                productRepayDetail.setStatus(2);  //1-未还款，2-已还款，3-逾期
                drProductInfoRepayDetailDAO.updateById(productRepayDetail);
            }
            if (expireFlag) {
                DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(7, null, info.getId(), infoPrincipal, 0, "产品【" + info.getFullName() + "】到期,支付本金", null);
                drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
                cfundsLog = new DrCompanyFundsLog(8, null, info.getId(), infoInterest, 0, "产品【" + info.getFullName() + "】到期,支付利息", null);
                drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
            } else {
                DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(8, null, info.getId(), infoInterest, 0, "产品【" + info.getFullName() + "】第" + productRepayDetail.getPeriods() + "期回款,支付利息", null);
                drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
            }
            if ((info.getType() == 2 || info.getType() == 3) && expireFlag) {//普标
                info.setStatus(9);
                drProductInfoDAO.updateDrProductInfoStatusById(info.getStatus(), info.getId());
                //修改标的剩余金额
                DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());//标的以及部分贷款项目信息
                drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)).add(info.getAmount()));
                drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));
                drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
            }
        }

        if (fundsMap.values().size() > 0) {
            drMemberFundsDAO.batchUpdateDrMemberFunds(new ArrayList<DrMemberFunds>(fundsMap.values()));
        }
        if (buff.toString().length() > 0) {
            drProductInvestDAO.updateStatusByIds("3", buff.toString().substring(0, buff.lastIndexOf(",")).split(","));
        }
       
        //短信发送
        for (int i = 0; i < smsList.size(); i++) {
            sysMessageLogService.sendMsg(smsList.get(i));
        }
        sau.flush();

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

    @Override
    public void saveInvestRepayByFuiou(DrProductInfo info) throws Exception {
    	SensorsAnalytics sau = SensorsAnalyticsUtils.getInstance();
    	Map<String, Object> properties = new HashMap<String, Object>();
    	
        Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();
        List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
        Date now = new Date();//还款时间
        StringBuffer buff = new StringBuffer();

        List<DrProductInvestRepayInfo> repayList = drProductInvestRepayInfoDAO.selectShouldRepayInfo(info.getId());
        DrProductInfoRepayDetail productRepayDetail = null;
        boolean expireFlag = true;//最后一期标记位
        if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4) {
            productRepayDetail = drProductInfoRepayDetailDAO.selectEarliestUnreimbursement(info.getId());
            if (info.getRepayType() == 2 || info.getRepayType() == 4) {
                expireFlag = productRepayDetail.getPeriods() == info.getDeadline() / 30;//判断按月付息的是否到期
            } else {
                expireFlag = productRepayDetail.getPeriods() == info.getDeadline() / 7;//判断按周付息的是否到期
            }
        }
        List<Map<String, Object>> othreInterestCache = new ArrayList<Map<String, Object>>();
        BigDecimal infoInterest = BigDecimal.ZERO;//支付总利息
        BigDecimal infoPrincipal = BigDecimal.ZERO;
        int total = repayList.size();//产品下总的还款笔数
        int successTotal = 0;//还款成功笔数
        BigDecimal businessAccountBalance = BigDecimal.ZERO;// 企业 余额//可用余额
        BigDecimal advanceBalance = BigDecimal.ZERO;//垫付付总金额
        boolean isAdvance = false;//是否垫付
        String toAdvance = null;

        Map<String, Object> advanceMap = new HashMap<String, Object>();

        String link_project_no = info.getId() + "_" + Utils.format(new Date(), "yyyy-MM-dd");//理财计划项目编号
        String out_cust_no = "";
        if (info.getType() != 5) { //非体验标
            out_cust_no = drProductInfoDAO.getIn_cust_noByProductId(info.getId());

            //
            Map<String, String> balanceActionMap = new HashMap<String, String>();
            balanceActionMap.put("cust_no", out_cust_no);
            BaseResult bresult = FuiouConfig.balanceAction(balanceActionMap);

            if (bresult.isSuccess()) {
                JSONObject accountresults = (JSONObject) bresult.getMap().get("results");
                JSONObject accountresult = (JSONObject) accountresults.get("result");
                businessAccountBalance = FuiouConfig.centToYuan(accountresult.get("ca_balance").toString());//可用余额

            }

        }
        if (repayList.size() > 0) {
            for (int j = 0; j < repayList.size(); j++) {
                //回款信息
                DrProductInvestRepayInfo repayInfo = repayList.get(j);
                infoInterest = infoInterest.add(repayInfo.getShouldInterest());
                infoPrincipal = infoPrincipal.add(repayInfo.getShouldPrincipal());
                //对于状态是为划拨或者划拨失败数据进行划拨
                if (repayInfo.getRemitStatus() == 1 || repayInfo.getRemitStatus() == 3) {

                    DrMember member = drMemberDAO.selectByPrimaryKey(repayInfo.getUid());//投资人
                    BigDecimal normalInterest = new BigDecimal(0);
                    BigDecimal otherInterest = new BigDecimal(0);

                    //基本收益
                    normalInterest = repayInfo.getBasicprofit();
                    //其他收益 --> 走营销存管账户=应收收益-产品年化收益
                    otherInterest = repayInfo.getShouldInterest().subtract(normalInterest);


                    //本息合计=本金 +产品年化收益  --> 走存管商户账户
                    BigDecimal principalInterest = repayInfo.getShouldPrincipal().add(normalInterest);

                    BaseResult br = new BaseResult();
                    br.setSuccess(true);
                    if (info.getType() != 5) { //非体验标

                        if (!isAdvance && businessAccountBalance.compareTo(principalInterest) < 0) {//根据可用余额判断是否 要 商户 给企业垫付回款
                            isAdvance = true;
                        }

                        Map<String, String> params = new HashMap<String, String>();
                        if (!isAdvance) {
                            String remitMchntTxnSsn = Utils.createOrderNo(6, repayInfo.getId(), "");//流水号
                            repayInfo.setRemitMchntTxnSsn(remitMchntTxnSsn);

//							params.put("contract_no", drProductInvestDAO.selectByPrimaryKey(repayInfo.getInvestId()).getContract_no());
                            params.put("out_cust_no", out_cust_no);
                            params.put("in_cust_no", member.getMobilephone());
                            params.put("amt", principalInterest.toString());//精确到分
                            params.put("rem", "产品回款|pid:" + repayInfo.getPid() + "|repayInfoId:" + repayInfo.getId());
                            params.put("mchnt_txn_ssn", remitMchntTxnSsn);
                            br = FuiouConfig.transferBu(params);
                        }

                        //
                        if (!isAdvance && !br.isSuccess()) {
                            String transferBuCode = br.getMap().get("resp_code") + "";
                            //只有转帐企业可用余额不足时 商户才垫付, 其他错误 不垫付
                            if ("3017".equals(transferBuCode) || "3018".equals(transferBuCode) || "3023".equals(transferBuCode)) {
                                isAdvance = true;
                            }
                        }

                        if (isAdvance) {
                            String remitMchntTxnSsn = Utils.createOrderNo(6, repayInfo.getId(), "");//流水号
                            advanceMap.put("mchnt_txn_ssn", remitMchntTxnSsn);
                            advanceMap.put("out_cust_no", FuiouConfig.LOGIN_ID);
                            advanceMap.put("in_cust_no", member.getMobilephone());
                            advanceMap.put("amt", principalInterest.toString());
                            advanceMap.put("rem", "产品回款-公司垫付|pid:" + repayInfo.getPid() + "|repayInfoId:" + repayInfo.getId());
                            advanceMap.put("contract_no", "");
                            advanceMap.put("icd_name", "产品回款-公司垫付|pid:" + repayInfo.getPid() + "|repayInfoId:" + repayInfo.getId());

                            br = FuiouConfig.transferBmu(advanceMap);// 公司垫付转帐
                        }


                    }

                    if (!br.isSuccess()) {
                        repayInfo.setRemitStatus(3);//划拨失败
                        repayInfo.setRemitFailReson(StringUtils.left(br.getErrorMsg(), 255));//失败原因
                        repayInfo.setRemitMchntTxnSsn(br.getMap().get("mchnt_txn_ssn").toString());
                        drProductInvestRepayInfoDAO.updateByIdFuiou(repayInfo);
                        logger.error("回款划拨失败,产品ID："+repayInfo.getPid()
                        		+";用户ID："+repayInfo.getUid()+";应收本金："+repayInfo.getShouldPrincipal()+
                        		";应收利息："+repayInfo.getShouldInterest());
                        continue;
                    } else {

                        if (!isAdvance) {//不是公司垫付 企业余额 减去 还款
                            businessAccountBalance = businessAccountBalance.subtract(principalInterest);
                        } else {//垫付总额加上 公司垫付
                            advanceBalance = advanceBalance.add(principalInterest);
                        }

                        repayInfo.setRemitStatus(2);//成功
                        if (info.getType() != 5) {
                            repayInfo.setRemitMchntTxnSsn(br.getMap().get("mchnt_txn_ssn").toString());
                        }
                        //发放  其他收益  走队列发放
                        Map<String, Object> othreInterestMap = new HashMap<String, Object>();
                        othreInterestMap.put("uid", repayInfo.getUid());
                        othreInterestMap.put("repayInfoId", repayInfo.getId());//回款id可用 作验证
                        othreInterestMap.put("otherInterest", otherInterest);//其他收益额度
                        othreInterestMap.put("link_project_no", link_project_no);//理财计划项目编号
                        othreInterestMap.put("project_no", info.getProject_no());//项目编号
                        othreInterestMap.put("productType", info.getType());//是否体验标
                        othreInterestMap.put("project_id", info.getId());//产品ID

                        othreInterestCache.add(othreInterestMap);

                    }
                    repayInfo.setFactPrincipal(repayInfo.getShouldPrincipal());
                    repayInfo.setFactInterest(repayInfo.getShouldInterest());
                    repayInfo.setFactTime(now);
                    repayInfo.setStatus(1);//已还款

                    repayInfo.setMchntPay(isAdvance ? 1 : 0);//0 正常回款,1商户垫付


                    //资金变动
                    DrMemberFunds funds = null;
                    if (fundsMap.containsKey(repayInfo.getUid())) {
                        funds = fundsMap.get(repayInfo.getUid());
                    } else {
                        funds = drMemberFundsDAO.queryDrMemberFundsByUid(repayInfo.getUid());
                    }
                    funds.setFuiou_balance(funds.getFuiou_balance().add(repayInfo.getFactPrincipal().add(repayInfo.getFactInterest())));//账户余额=余额+其他收益+本息合计
                    funds.setFuiou_wprincipal(funds.getFuiou_wprincipal().subtract(repayInfo.getFactPrincipal()));//待收本金
                    funds.setFuiou_winterest(funds.getFuiou_winterest().subtract(repayInfo.getFactInterest()));//待收利息
                    funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(repayInfo.getFactInterest()));//投资收益
                    drMemberFundsDAO.updateDrMemberFunds(funds);
                    fundsMap.put(repayInfo.getUid(), funds);

                    if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4) {
                        DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(), repayInfo.getInvestId(), repayInfo.getUid(),
                                6, 1, repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()), funds.getFuiou_balance(), 3, "产品【" + info.getFullName() + "】第" + productRepayDetail.getPeriods() + "期回款", null);
                        drMemberFundsRecordDAO.insert(fundsRecord);

                        DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(), fundsRecord.getId(), repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()),
                                11, 1, "产品【" + info.getFullName() + "】第" + productRepayDetail.getPeriods() + "期回款");
                        drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                    } else {
                        DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(), repayInfo.getInvestId(), repayInfo.getUid(),
                                info.getType() == 5 ? 7 : 6, 1, repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()), funds.getFuiou_balance(), 3, "产品【" + info.getFullName() + "】回款", null);
                        drMemberFundsRecordDAO.insert(fundsRecord);

                        DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(), fundsRecord.getId(), repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()),
                                11, 1, "产品【" + info.getFullName() + "】回款");
                        drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                    }

                    String msgStr = "";//站内信
                    String smslogStr = "";//短信
                    if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4 && !expireFlag) {
                        if (info.getType() == 5) {
                            smslogStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthSms")
                                    .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                            msgStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthMsg")
                                    .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());

                        } else {
                            if (info.getRepayType() == 2) {
                                //每月付息站内信
                                msgStr = PropertyUtil.getProperties("paymentMonthMsg")
                                        .replace("${realName}", member.getRealName())
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${interest}", repayInfo.getFactInterest().toString());
                                //每月付息短信
                                smslogStr = PropertyUtil.getProperties("paymentMonthMsg")
                                        .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${interest}", repayInfo.getFactInterest().toString());
                            } else {//等本等息
                                msgStr = PropertyUtil.getProperties("paymentReceivableMsg")
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${num}", productRepayDetail.getPeriods().toString())
                                        .replace("${amount}", repayInfo.getFactInterest().add(repayInfo.getShouldPrincipal()).toString())
                                        .replace("${principal}", repayInfo.getShouldPrincipal().toString())
                                        .replace("${interest}", repayInfo.getFactInterest().toString());
                                smslogStr = PropertyUtil.getProperties("paymentReceivableMsg")
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${num}", productRepayDetail.getPeriods().toString())
                                        .replace("${amount}", repayInfo.getFactInterest().add(repayInfo.getShouldPrincipal()).toString())
                                        .replace("${principal}", repayInfo.getShouldPrincipal().toString())
                                        .replace("${interest}", repayInfo.getFactInterest().toString());
                            }
                        }
                    } else {
                        if (info.getType() == 5) {
                            //短信
                            smslogStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthSms")
                                    .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                            //站内信
                            msgStr = PropertyUtil.getProperties("tiyanbiaopaymentMonthMsg")
                                    .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                        } else {
                            //站内信
                            msgStr = PropertyUtil.getProperties("paymentMsg")
                                    .replace("${fullName}", info.getFullName())
                                    .replace("${amount}", repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()).toString())
                                    .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                            //短信
                            smslogStr = PropertyUtil.getProperties("paymentSms")
                                    .replace("${realName}", Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member.getMobilephone())
                                    .replace("${fullName}", info.getFullName())
                                    .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                        }
                    }
                    getType(properties,info);
                    if(Utils.isObjectNotEmpty(info.getCode())){
                    	properties.put("LoanId", info.getCode()); 
       			 	}
                    DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.selectByPrimaryKey1(info.getId());
                    
                    if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(drClaimsLoan.getLoanUse())){
                    	  properties.put("ProjectType",drClaimsLoan.getLoanUse());
                    }
                    if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(info.getFullName())){
                    	properties.put("ProjectName", info.getFullName()+"");
                    }
                 
                    if(Utils.isObjectNotEmpty(info.getDeadline())){
                    	properties.put("ProjectDeadline", info.getDeadline()+"");
                    }
                    properties.put("Rate", info.getRate());
                    properties.put("IncomeType", info.getRepayTypeName()+"");
                    properties.put("ReleaseTime", info.getStartDate());
                    //查询优惠券金额
                    String amountStr = drMemberFundsLogDAO.queryCouponAmount(info.getId());
                    if(Utils.isObjectNotEmpty(amountStr)){
                    	 properties.put("CouponAmount", Double.parseDouble(amountStr));
                    }
                    
                    properties.put("ActualPaidAmount", repayInfo.getFactPrincipal());
                    properties.put("RepaymentPrincipal", repayInfo.getShouldPrincipal());
                    properties.put("RepaymentInterest", repayInfo.getShouldInterest());
                    if(Utils.isObjectNotEmpty(info.getExpireDate())){
                   	 properties.put("DueDate", info.getExpireDate());
                   }
                    Calendar c = Calendar.getInstance();  
                    c.setTime(now);  
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    properties.put("ArrivalTime", c.getTime());
                    properties.put("ISFullyDue", "是");
                    //神策埋点
                    Integer firstCount = drProductInvestRepayInfoDAO.selectIsFirstShouldRepayInfo(info.getId());
                    if(firstCount.equals(0)){
                    	 Map<String, Object> properties2 = new HashMap<String, Object>();
                         properties2.put("first_repayment_time", new Date());
                         sau.profileSet(String.valueOf(repayInfo.getUid()), true,properties2);
                    }
                    
                    sau.track(String.valueOf(repayInfo.getUid()), true, "InvestDue" ,properties);
                    
                    //微信模板消息
                    String openId = drMemberDAO.queryOpenId(member.getUid());
                    if(Utils.isObjectNotEmpty(openId)){
	                    ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
	                    modelMessage.returnedMoneyJson(openId, repayInfo.getShouldPrincipal(),
	                    		normalInterest, info.getFullName());
                    }
                    
                    //回款成功站内信
                    DrMemberMsg msg = new DrMemberMsg(repayInfo.getUid(), 0, 3, "回款通知", now, 0, 0, msgStr);
                    drMemberMsgDAO.insertDrMemberMsg(msg);
                    //回款成功短信
                    SysMessageLog smslog = new SysMessageLog(repayInfo.getUid(), smslogStr,
                            14, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:mm:ss"), "yyyy-MM-dd HH:mm:ss"), member.getMobilephone());
                    smsList.add(smslog);

                    drProductInvestRepayInfoDAO.updateByIdFuiou(repayInfo);
                    if (expireFlag) {
                        buff.append(repayInfo.getInvestId().toString() + ",");
                    }
                    if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4) { //修改产品按月付息的详情的状态
                        productRepayDetail.setStatus(2);  //1-未还款，2-已还款，3-逾期
                        drProductInfoRepayDetailDAO.updateById(productRepayDetail);
                    }
                    successTotal++;
                } else {
                    successTotal++;
                }

            }
            	
            if (successTotal > 0) {
                DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(8, null, info.getId(), infoInterest, 0, "产品【" + info.getFullName() + "】回款,支付利息", null);
                drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);

                //垫付总额记公司日志
                if (advanceBalance.compareTo(BigDecimal.ZERO) == 1) {
                    //记公司账户日志 收取手续费
                    JsCompanyAccountLog accountLog = new JsCompanyAccountLog();
                    accountLog.setCompanyfunds(5);//资金类型:平台垫付
                    accountLog.setType(0);//支出
                    accountLog.setAmount(advanceBalance);//金额
/*					accountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(advanceBalance));
*/
                    accountLog.setStatus(3);//成功
                    accountLog.setRemark("-公司垫付:产品回款[" + info.getFullName() + ",产品ID=" + info.getId() + ",垫付总额:" + advanceBalance + "]");
                    accountLog.setAddTime(new Date());
                    accountLog.setChannelType(2);//存管
                    accountLog.setUid(null);//
                    jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);

                    //短信
                    toAdvance = PropertyUtil.getProperties("toAdvance")
                            .replace("${date}", Utils.format(now, "yyyy年MM月dd日"))
                            .replace("${business}", "")
                            .replace("${product}", info.getFullName())
                            .replace("${amount}", info.getAmount().toString())
                            .replace("${advanceBalance}", advanceBalance.toString());

                    SysMessageLog smslog = new SysMessageLog(null, toAdvance,
                            12, null, PropertyUtil.getProperties("advanceToMobile"));
                    sysMessageLogService.sendMsg(smslog);

                }

                if (!Utils.isEmptyList(othreInterestCache)) {
                    //其他收益放到redis里
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("list", othreInterestCache);
                    map.put("type", 51);//发放其他收益
                    try {
                        redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map));
                    } catch (Exception e) {
                        logger.error("redis 异常:发放  其他收益失败,json:" + JSONObject.fromObject(othreInterestCache).toString(), e);
                    }
                }

                if ((info.getType() == 2 || info.getType() == 3) && expireFlag) {//普标
                    info.setStatus(9);
                    drProductInfoDAO.updateDrProductInfoStatusById(info.getStatus(), info.getId());
                    if (successTotal == total) {
                        //修改标的剩余金额
                        DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());//标的以及部分贷款项目信息
                        drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)).add(info.getAmount()));
                        drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));
                        drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
                    }
                }
            }
            //更新产品的回款状态
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("repay_success_num", successTotal);
            infoMap.put("repay_fail_num", total - successTotal);
            if (expireFlag) {
                infoMap.put("repay_status", successTotal == total ? 2 : 3);//回款失败,1-未回款，2-回款成功，3-回款失败
            }
            drProductInfoDAO.updateDrProductInfoInterestRepay(infoMap);
        }

        if (fundsMap.values().size() > 0) {
            drMemberFundsDAO.batchUpdateDrMemberFunds(new ArrayList<DrMemberFunds>(fundsMap.values()));
        }
        if (buff.toString().length() > 0) {
            drProductInvestDAO.updateStatusByIds("3", buff.toString().substring(0, buff.lastIndexOf(",")).split(","));
        }
        //短信发送
        for (int i = 0; i < smsList.size(); i++) {
            sysMessageLogService.sendMsg(smsList.get(i));
        }
        sau.flush();
    }

    @Override
    public void updateFriendsFirstInvestmentReward() throws Exception {
        //获取首投用户
        List<Integer> uidList = drProductInvestDAO.getForFirstTimeInvestmentMember();
        //存放需要修改首投记录
        List<DrMemberRecommended> recList = new ArrayList<DrMemberRecommended>();
        if (!Utils.isEmptyList(uidList)) {
            for (Integer uid : uidList) {
                //获取此用户的首投记录
                DrProductInvest invest = drProductInvestDAO.getForFirstTimeInvestmentByUid(uid);
                //关系表
                DrMemberRecommended drMemberRecommended = new DrMemberRecommended();
                if (Utils.isObjectNotEmpty(invest)) {
                    //获取投资用户详细信息
                    DrMember m = drMemberDAO.selectByPrimaryKey(uid);
                    //首投记录  期限大于7天并且投资时间在注册日期起30天之内
                    if (invest.getDeadline() >= 7 && Utils.getQuot(invest.getInvestTime(), m.getRegDate()) <= 30) {
                        DrMember referrer = null;
                        if (null == m.getIsParticipation() || (null != m.getIsParticipation() && m.getIsParticipation() == 0)) {
                            referrer = drMemberDAO.selectOnlyOneMember(uid);
                        }

                        if (Utils.isObjectEmpty(referrer)) {
                            //如果没有推荐人，更新用户首投记录
                            drMemberRecommended.setUid(uid);
                            drMemberRecommended.setInvestTime(invest.getInvestTime());
                            drMemberRecommended.setAmount(invest.getFactAmount());
                            recList.add(drMemberRecommended);
                            continue;
                        }

                        BigDecimal rebate = drRecommendedSettingsService.FriendRecommendedRebate(invest);
                        //修改关系表投资记录
                        drMemberRecommended.setUid(uid);
                        drMemberRecommended.setInvestTime(invest.getInvestTime());
                        drMemberRecommended.setAmount(invest.getFactAmount());
                        drMemberRecommended.setReferrerReward(rebate);
                        recList.add(drMemberRecommended);

                        if (rebate.compareTo(BigDecimal.ZERO) > 0) {
                            DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(referrer.getUid());
                            funds.setBalance(funds.getBalance().add(rebate));//添加到推荐人余额
                            funds.setSpreadProfit(funds.getSpreadProfit().add(rebate));//添加推荐人推广利益
                            drMemberFundsDAO.updateDrMemberFunds(funds);
                            //添加推荐人资金日志
                            DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(invest.getPid(), invest.getId(), referrer.getUid(), 4, 1, rebate, funds.getBalance(), 3, "好友投资返利", null);
                            //公司日志
                            DrCompanyFundsLog companyLog = new DrCompanyFundsLog(13, referrer.getUid(), invest.getPid(), rebate, 0, "支付好友推荐返利(" + m.getRealName() + "投资" + invest.getFullName() + "产品" + invest.getFactAmount() + "元)", null);
                            drMemberFundsRecordDAO.insert(fundsRecord);
                            //添加推荐人资金日志详细
                            DrMemberFundsLog log = new DrMemberFundsLog(referrer.getUid(), fundsRecord.getId(), rebate, 16, 1, "好友投资返利");
                            drMemberFundsLogDAO.insertDrMemberFundsLog(log);
                            drCompanyFundsLogDAO.insertDrCompanyFundsLog(companyLog);
                        }
                    } else {
                        drMemberRecommended.setUid(uid);
                        drMemberRecommended.setInvestTime(invest.getInvestTime());
                        drMemberRecommended.setAmount(invest.getFactAmount());
                        recList.add(drMemberRecommended);
                    }
                }
            }
        }
        if (!Utils.isEmptyList(recList)) {
            drMemberRecommendedDAO.batchUpdate(recList);
        }
    }

    @Override
    public PageInfo selectInvestMemberInfoListByParam(Map<String, Object> map,
                                                      PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<Map<String, Object>> list = drProductInvestDAO.selectInvestMemberInfoListByParam(map);
        Map<String, Object> m = drProductInvestDAO.selectInvestMemberInfoListCountByParam(map);
        List<Map<String, Object>> footer = drProductInvestDAO.selectInvestPageCountByParam(map);

        pi.setRows(list);
        pi.setTotal(Integer.parseInt(m.get("total").toString()));
        Map<String, Object> ss = new HashMap<String, Object>();
        footer.add(m);
        pi.setFooter(footer);
        return pi;
    }


    @Override
    public void insertInvestTransfer() {
        //查询要做债权匹配的产品
        List<DrProductInfo> infoList = drProductInfoDAO.selectTransferProductInfo();
        List<DrProductInvestTransfer> list = new ArrayList<DrProductInvestTransfer>();
        if (infoList.size() > 0) {
            for (DrProductInfo info : infoList) {
                //受让人投资记录
                List<DrProductInvest> assigneeList = drProductInvestDAO.selectProductInvestByPid(info.getId());
                //转让人投资记录
                List<DrProductInvest> transferList = drProductInvestDAO.selectProductInvestByPid(info.getFid());
                outer:
                for (int i = 0; i < transferList.size(); i++) {
                    DrProductInvest tInvest = transferList.get(i);

                    for (int j = 0; j < assigneeList.size(); j++) {
                        DrProductInvest drProductInvest = assigneeList.get(j);
                        //求两笔投资的差额
                        BigDecimal amount = tInvest.getTransferSurplusAmount().subtract(drProductInvest.getTransferSurplusAmount());

                        if (amount.compareTo(BigDecimal.ZERO) > 0) {//转让金额大于投资金额
                            DrProductInvestTransfer t = new DrProductInvestTransfer(tInvest.getUid(), drProductInvest.getUid(), info.getId(),
                                    tInvest.getId(), drProductInvest.getId(), new Date(), drProductInvest.getTransferSurplusAmount());
//							logger.info(tInvest.getUid()+"---"+tInvest.getFactAmount()+"匹配金额"+drProductInvest.getUid()+"："+t.getAmount());
                            list.add(t);
                            tInvest.setTransferSurplusAmount(amount.abs());
                            assigneeList.remove(j);
                            j--;

                        } else if (amount.compareTo(BigDecimal.ZERO) == 0) {
                            DrProductInvestTransfer t = new DrProductInvestTransfer(tInvest.getUid(), drProductInvest.getUid(), info.getId(),
                                    tInvest.getId(), drProductInvest.getId(), new Date(), tInvest.getTransferSurplusAmount());
//							logger.info(tInvest.getUid()+"---"+tInvest.getFactAmount()+"匹配金额"+drProductInvest.getUid()+"："+t.getAmount());
                            list.add(t);
                            assigneeList.remove(j);
                            continue outer;
                        } else {
                            DrProductInvestTransfer t = new DrProductInvestTransfer(tInvest.getUid(), drProductInvest.getUid(), info.getId(),
                                    tInvest.getId(), drProductInvest.getId(), new Date(), tInvest.getTransferSurplusAmount());
//							logger.info(tInvest.getUid()+"---"+tInvest.getFactAmount()+"匹配金额"+drProductInvest.getUid()+"："+t.getAmount());
                            list.add(t);
                            drProductInvest.setTransferSurplusAmount(amount.abs());
                            continue outer;
                        }

                    }

                }
                drProductInfoDAO.updateMappingStatusByPid(info.getId());
            }
            drProductInvestTransferDAO.batchInsert(list);
        }
    }

    @Override
    public List<Map<String, Object>> QueryChannelInvestList(
            Map<String, Object> map) throws Exception {
        List<Map<String, Object>> list = drProductInvestDAO.QueryChannelInvestList(map);
        Map<String, Object> resultMap;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            Map<String, Object> ite = (Map<String, Object>) iterator.next();
            resultMap = new HashMap<String, Object>();
            resultMap.put("User_name", Utils.getHanyuToPinyin(ite.get("realName").toString()));
            resultMap.put("Order_no", ite.get("id"));
            resultMap.put("Pro_name", ite.get("fullName"));
            resultMap.put("Pro_id", ite.get("pid"));
            resultMap.put("Invest_money", ite.get("amount"));
            resultMap.put("Actual_invest_money", map.get("factAmount"));
            resultMap.put("Rate", ite.get("rate"));
            resultMap.put("Invest_start_date", ite.get("investTime"));
            resultMap.put("Invest_end_date", Utils.parse(Utils.format(Utils.getDayNumOfAppointDate(Utils.format(ite.get("expireDate").toString(), "yyyy-MM-dd"), 1), "yyyy-MM-dd 23:59:59"), "yyyy-MM-dd HH:mm:ss"));
            resultMap.put("Invest_full_scale_date", ite.get("fullDate"));
            resultMap.put("Commission_date", Utils.parse(Utils.format(Utils.getDayNumOfAppointDate(Utils.format(ite.get("expireDate").toString(), "yyyy-MM-dd"), 1), "yyyy-MM-dd 23:59:59"), "yyyy-MM-dd HH:mm:ss"));
            resultMap.put("Cust_key", ite.get("uid"));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> QueryChannelYRT_InvestList(
            Map<String, Object> map) throws Exception {

        return drProductInvestDAO.QueryChannelYRT_InvestList(map);
    }

    @Override
    public PageInfo selectActivityInvestListByParam(Map<String, Object> map, PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());

        List<Map<String, Object>> list = drProductInvestDAO.selectActivityInvestListByParam(map);
        Map<String, Object> m = drProductInvestDAO.selectActivityInvestListCountByParam(map);
        List<Map<String, Object>> footer = drProductInvestDAO.selectActivityInvestListByParamCensus(map);
        pi.setFooter(footer);
        pi.setRows(list);
        pi.setTotal(Integer.parseInt(m.get("total").toString()));
        return pi;
    }

    @Override
    public void investContinueInvest(DrProductInfo pis, JsNoviceContinueRecord jncr) throws Exception {
        boolean lockFlag = false;
        lockFlag = redisClientTemplate.tryLock("product.id." + pis.getId(), 3, TimeUnit.SECONDS, false);//产品全局锁
        final DrProductInfo pi;
        try {
            if (lockFlag) {
                //获取缓存中的产品
                DrProductInfo info = (DrProductInfo) SerializeUtil.unserialize(redisClientTemplate.get(("product.info." + pis.getId()).getBytes()));
                if (Utils.isObjectNotEmpty(info)) {//如果不为空,缓存中数据为最新
                    pi = info;
                } else {
                    pi = pis;
                }

                DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(jncr.getUid());
                if (funds.getFreeze().compareTo(jncr.getAmount()) < 0) {//冻结金额小于待投金额
                    return;
                }

                // 插入投资记录
                DrProductInvest invest = new DrProductInvest();
                invest.setAmount(jncr.getAmount());
                invest.setUid(jncr.getUid());
                invest.setJoinType(jncr.getJoinType());// 投资终端:续投渠道延续新手标投资终端
                invest.setStatus(0);
                invest.setPid(pi.getId());

                BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(pi.getRate().add(pi.getActivityRate()), 100), 360);
                invest.setInterest(jncr.getAmount().multiply(dayRate).multiply(new BigDecimal(pi.getDeadline())).setScale(2, BigDecimal.ROUND_FLOOR));
                invest.setInvestTime(new Date());
                drProductInvestDAO.insertSelective(invest);
                Integer investId = invest.getId();

                //更新产品剩余金额
                pi.setAlreadyRaiseAmount(pi.getAlreadyRaiseAmount().add(jncr.getAmount()));
                pi.setSurplusAmount(pi.getSurplusAmount().subtract(jncr.getAmount()));
                if (pi.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {
                    pi.setStatus(6);
                    pi.setIsHot(0);
                    pi.setFullDate(new Date());
                }
                //自动发标
                if (Utils.isBlank(pi.getAtid()) && pi.getType() != 1 && pi.getType() != 4) {
                    //每个产品只执行一次自动发标
                    if (redisClientTemplate.get("autoReleaseProductLimit_" + pi.getId()) == null) {
                        //可以放到 redis 缓存
                        redisClientTemplate.set("autoReleaseProductLimit_" + pi.getId(), "1");//原产品标记为已执行过一次自动发标
                        Map<String, Object> autoReleaseMap = new HashMap<String, Object>();
                        autoReleaseMap.put("type", 50);
                        autoReleaseMap.put("product", pi);
                        //自动发标样本放到redis里
                        redisClientTemplate.lpush("autoReleaseProductIdCache".getBytes(), SerializeUtil.serialize(autoReleaseMap));
//						autoReleaseMap.clear();
                    }
                }

                drProductInfoDAO.updateDrProductInfo(pi);

                //获取并修改待续投日志
                Map<String, Object> map = new HashMap<>();
                map.put("uid", jncr.getUid());
                map.put("status", 5);//待续投
                DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.getDrMemberFundsRecordByMap(map);

                fundsRecord.setPid(pi.getId());

                fundsRecord.setInvestId(investId);
                fundsRecord.setStatus(4);
                fundsRecord.setRemark(fundsRecord.getRemark() + "[" + pi.getFullName() + "]");
                drMemberFundsRecordDAO.updateDrMemberFundsRecord(fundsRecord);


                //回填投资记录
                jncr.setInvestId(investId);
                jncr.setStatus(1);
                jsNoviceContinueRecordDAO.updateJsNoviceContinueRecord(jncr);

                // 发送站内信---短信消息服务可以放到线存池里跑,提高主业务程序效率
                String str = "尊敬的用户，您已成功投资" + pi.getFullName() + ",金额" + invest.getAmount() + "元，次日开始计息，敬请期待！";
                DrMemberMsg msg = new DrMemberMsg(jncr.getUid(), 0, 3, "续投成功", new Date(), 0, 0, str);
                drMemberMsgDAO.insertDrMemberMsg(msg);

                //业务跑完最后把 产品放到 缓存中
                redisClientTemplate.setex(("product.info." + pi.getId()).getBytes(), 600, SerializeUtil.serialize(info));

            } else {
                //续投失败
                logger.info("自动续投失败-系统繁忙,用户uid:" + jncr.getUid());
            }

        } finally {
            if (lockFlag) {
                redisClientTemplate.del("product.id." + pis.getId());
            }
        }
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

    private void autoReleaseProduct(DrProductInfo pInfo) {
        logger.info("--------进入自动发标");
        //当期募集完成自动上架下一个同类型（募集期限）的储备产品，
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status", 2); //2已审核 （待上架）
        param.put("deadline", pInfo.getDeadline());//期限
        param.put("isShow", 1);//期限
        param.put("prizeId", pInfo.getPrizeId());//商品ID
        List<DrProductInfo> list = drProductInfoDAO.selectProductInfo(param);
        String sendMobile1 = "";
        String sendMobile2 = "";
        try {
            sendMobile1 = PropertyUtil.getProperties("autoPublishMobile1");
            sendMobile2 = PropertyUtil.getProperties("autoPublishMobile2");
            if (!Utils.isEmptyList(list)) {
                StringBuffer errorMsg = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    DrProductInfo dpi = list.get(i);
                    //判断产品的到期日是否已经超过标的的到期日
                    DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(dpi.getSid());
                    int counts = Utils.daysBetween(dpi.getDeadline() + dpi.getRaiseDeadline() - 1, drSubjectInfo.getEndDate(), null);
                    if (counts > 0) {
                        errorMsg.append("产品[" + dpi.getFullName() + "]到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
                        continue;
                    }
                    BaseResult result = new BaseResult();
                    result.setSuccess(true);
                    dpi.setProject_no("jzh");
                    /*
                    if(Utils.isObjectEmpty(dpi.getProject_no())){
						//项目报备
						DrClaimsLoan loan = drClaimsInfoService.getDrClaimsLoanBySid(dpi.getSid());
						
						DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(loan.getId());
						loan.setDrClaimsCustomer(customer);
						
//						result = FuiouConfig.productReport(dpi, loan);
						result =  new BaseResult();
						
						if(result.isSuccess()){//项目报备成功
							net.sf.json.JSONObject json = (net.sf.json.JSONObject)(result.getMap().get("json"));
							//更新报备成功的本地产品
							dpi.setProject_no(json.getString("project_no"));
							dpi.setProject_st(json.getString("project_st"));
							dpi.setProject_usage(json.getString("project_usage"));
						}
					}
					 */
                    if (result.isSuccess()) {
                        dpi.setStatus(5);//募集中
                        dpi.setStartDate(new Date());
                        Date establish = Utils.getDayNumOfAppointDate(dpi.getStartDate(), -(dpi.getRaiseDeadline()));
                        //根据开始时间 ,募集期,期限, 计算成立时间 与计算时间
                        dpi.setEstablish(establish);
                        dpi.setExpireDate(Utils.getDayNumOfAppointDate(dpi.getStartDate(), -(dpi.getDeadline() + dpi.getRaiseDeadline())));
                        dpi.setUpdUser(0);
                        dpi.setUpdDate(new Date());

                        drProductInfoDAO.updateProductSelective(dpi);

                        //可以放到 redis 缓存
                        if (pInfo.getStatus() == null || pInfo.getStatus() == 5)
                            redisClientTemplate.set("autoReleaseProductLimit_" + pInfo.getId(), "1");//原产品标记为已执行过一次自动发标

                        //上架成功短信提醒  --》自动发标信息提醒>${1}产品已募集结束，${2}产品自动发布上架成功，自动发布储备标的剩余${3}个。  15221219118  18930352770
                        String autoPublishSms = PropertyUtil.getProperties("autoPublish").replace("${1}", pInfo.getFullName())
                                .replace("${2}", dpi.getFullName()).replace("${3}", list.size() - 1 + "");//短信模板

                        SysMessageLog sms1 = new SysMessageLog(0, autoPublishSms, 1, null, sendMobile1);
                        SysMessageLog sms2 = new SysMessageLog(0, autoPublishSms, 1, null, sendMobile2);
                        sysMessageLogService.sendMsg(sms1, 1);
                        sysMessageLogService.sendMsg(sms2, 1);
                        logger.info("自动发标：" + autoPublishSms + Utils.format(new Date(), "yyyy-MM-dd HH-mm-ss"));
                        break;
                    } else {
                        errorMsg.append("产品自动上架失败:" + dpi.getFullName() + ",pid:" + dpi.getId());
                    }
                }
                if (!"".equals(errorMsg.toString())) {
                    SysMessageLog sms1 = new SysMessageLog(0, errorMsg.toString(), 1, null, sendMobile1);
                    SysMessageLog sms2 = new SysMessageLog(0, errorMsg.toString(), 1, null, sendMobile2);
                    sysMessageLogService.sendMsg(sms1, 1);
                    sysMessageLogService.sendMsg(sms2, 1);
                }
            } else {
                SysMessageLog sms1 = new SysMessageLog(0, pInfo.getDeadline() + "天期限产品已无储备标，请及时发标！", 1, null, sendMobile1);
                SysMessageLog sms2 = new SysMessageLog(0, pInfo.getDeadline() + "天期限产品已无储备标，请及时发标！", 1, null, sendMobile2);
                sysMessageLogService.sendMsg(sms1, 1);
                sysMessageLogService.sendMsg(sms2, 1);
            }
        } catch (Exception e) {
            SysMessageLog sms1 = new SysMessageLog(0, "自动发标异常，" + e.getMessage(), 1, null, "15800784479");
            SysMessageLog sms2 = new SysMessageLog(0, "自动发标异常，" + e.getMessage(), 1, null, "15221219118");
            sysMessageLogService.sendMsg(sms1, 1);
            sysMessageLogService.sendMsg(sms2, 1);
            logger.error("自动发标异常", e);
        }
    }

    @Override
    public Integer selectInvestCountByPid(Integer pid) {
        List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestListByPid(pid);
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public void marketSMSNotInvest(Integer day, String key, boolean isbatch) {
        try {
            String content = PropertyUtil.getProperties(key);
            if (Utils.isObjectNotEmpty(content)) {
                if (isbatch) {
                    String[] array = drProductInvestDAO.selectRegNotInvest(day - 1);//注册第n天-未投资type=2 and if(atid or prizeId,0,1)
                    SmsSendUtil.batchSMSMarketing(array, content);

                    logger.info("营销短信-注册第" + day + "天未绑卡:" + array.length + "条,发送完成");
//					logger.info("mobile:"+Arrays.toString(array));
                } else {
                    List<Map<String, Object>> list = drProductInvestDAO.selectRegNotInvests(day - 1);

                    for (Map param : list) {
                        SmsSendUtil.sendMsgByMarketing(param.get("mobile").toString(), content.replace("${amount}", param.get("amount").toString()));
                    }

                    logger.info("营销短信-注册第" + day + "天未绑卡:" + list.size() + "条,发送完成");
//					logger.info("mobile:"+StringUtils.join(list.toArray(), ","));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("营销短信-注册第" + day + "天未绑卡:发送失败", e);
        }

    }


    @Override
    public PageInfo selectExperienceInvestMemberInfoListByParam(Map<String, Object> map, PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<Map<String, Object>> list = drProductInvestDAO.selectExperienceInvestMemberInfoListByParam(map);
        Map<String, Object> m = drProductInvestDAO.selectExperienceInvestMemberInfoListCountByParam(map);
        List<Map<String, Object>> footer = drProductInvestDAO.selectExperienceInvestPageCountByParam(map);

        pi.setRows(list);
        pi.setTotal(Integer.parseInt(m.get("total").toString()));
        Map<String, Object> ss = new HashMap<String, Object>();
        footer.add(m);
        pi.setFooter(footer);
        return pi;
    }

    @Override
    public PageInfo getInvestListForFuTou(Map<String, Object> map, PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<Map<String, Object>> fuTouList = drProductInvestDAO.getInvestListForFuTou(map);
        Map<String, Object> m = drProductInvestDAO.getInvestListForFuTouCount(map);
        Map<String, Object> all = drProductInvestDAO.getInvestListForFuTouSumAll(map);//总计
        List<Map<String, Object>> footer = drProductInvestDAO.getInvestListForFuTouSumPage(map);
        pi.setRows(fuTouList);
        pi.setTotal(Integer.parseInt(m.get("total").toString()));
        footer.add(all);
        pi.setFooter(footer);
        return pi;
    }

    @Override
    public PageInfo getProductInvestListByUid(Map<String, Object> map, PageInfo pi) {
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<Map<String, Object>> list = drProductInvestDAO.getProductInvestListByUid(map);
        Map<String, Object> m = drProductInvestDAO.getProductInvestCountByUid(map);
        List<Map<String, Object>> footer = drProductInvestDAO.getProductInvestSumByUid(map);
        pi.setRows(list);
        pi.setTotal(Integer.parseInt(m.get("total").toString()));
        pi.setFooter(footer);
        return pi;
    }

    @Override
    public void toAutoReleaseProduct(Map<String, Object> map) {

        DrProductInfo pi = new DrProductInfo();
        pi.setId((Integer) map.get("id"));
        pi.setDeadline((Integer) map.get("deadline"));
        pi.setFullName((String) map.get("fullName"));
        pi.setStatus((Integer) map.get("status"));
        pi.setSurplusAmount((BigDecimal) map.get("surplusAmount"));
        if (map.get("prizeId") != null) {
            pi.setPrizeId((Integer) map.get("prizeId"));
        }
        //执行回调接口
        autoReleaseProduct(pi);
    }

    @Override
    public void updateInvestInfo(JSONObject message) {
        logger.info("--------------进入后台更新投资记录");
        boolean lockFlag = false;
        boolean ssnLockFlag = false;
        boolean thawLockFlag = false;
        JSONObject acnts_amt = message.getJSONArray("acnts_amt").getJSONObject(0);
        String project_no = (String) message.get("project_no");
        BigDecimal amount = FuiouConfig.centToYuan(acnts_amt.getString("amt"));
        String txn_res_cd = acnts_amt.getString("txn_res_cd");
        String user_id = (String) message.get("user_id");
        String fuiou_acnt = acnts_amt.getString("fuiou_acnt");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("mchnt_txn_ssn", (String) message.get("mchnt_txn_ssn"));
        SysFuiouNoticeLog sysFuiouNoticeLog = sysFuiouNoticeLogDAO.getSysFuiouNoticeLogByParam(param);
        Integer fid = sysFuiouNoticeLog.getMf_id();
        param.clear();
        param.put("project_no", project_no);
        DrProductInfo pInfo = drProductInfoDAO.getDrProductInfoByMap(param);
        DrMember drMember = drMemberDAO.selectDrMemberByUserId(user_id);

        if ("0000".equals((String) message.get("resp_code"))) {
            if (txn_res_cd.equals("0000")) {
                ssnLockFlag = redisClientTemplate.tryLock("invest.mchnt_txn_ssn." + (String) message.get("mchnt_txn_ssn"), 3, TimeUnit.SECONDS, false);
                try {
                    if (ssnLockFlag) {
                        param.clear();
                        param.put("mchnt_txn_ssn", (String) message.get("mchnt_txn_ssn"));
                        SysFuiouNoticeLog fuiouNoticeLog = sysFuiouNoticeLogDAO.getSysFuiouNoticeLogByParam(param);
                        if (fuiouNoticeLog.getStatus() == 2) {
                            logger.info("--------------------进入后台投资回调：流水号已被处理，不做更新处理");
                        } else {
                            logger.info("--------------------进入后台投资回调：流水号未处理，开始处理");
                            try {
                                lockFlag = redisClientTemplate.tryLock("product.id." + pInfo.getId(), 3, TimeUnit.SECONDS, false);
                                if (lockFlag) {
                                    // 更新产品信息
                                    logger.info("-------------------投资更新 Pid： " + pInfo.getId());
                                    pInfo.setAlreadyRaiseAmount(pInfo.getAlreadyRaiseAmount().add(amount));
                                    pInfo.setSurplusAmount(pInfo.getSurplusAmount().subtract(amount));
                                    //投资金额大于剩余金额就解冻金额
                                    if (amount.compareTo(pInfo.getSurplusAmount()) == 1) {
                                        thawLockFlag = redisClientTemplate.tryLock("thaw.fuiou_acnt." + drMember.getFuiou_acnt(), 3, TimeUnit.SECONDS, false);
                                        try {
                                            if (thawLockFlag) {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("mchnt_txn_ssn", (String) message.get("mchnt_txn_ssn"));
                                                params.put("amt", acnts_amt.getString("amt"));
                                                params.put("fuiou_acnt", fuiou_acnt);
                                                params.put("user_id", user_id);
                                                params.put("project_no", project_no);
//													FuiouConfig.thaw(params);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (thawLockFlag) {
                                                redisClientTemplate.del("thaw.fuiou_acnt." + drMember.getFuiou_acnt());
                                            }
                                        }
                                    }
                                    if (pInfo.getType() != 1 && pInfo.getType() != 4 && pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {// 募集完成
                                        // 新手标始终处于募集中
                                        pInfo.setStatus(6);
                                        pInfo.setFullDate(new Date());
                                        pInfo.setEstablish(Utils
                                                .getDayNumOfAppointDate(Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -1));
                                        pInfo.setExpireDate(Utils.getDayNumOfAppointDate(pInfo.getEstablish(), 0 - pInfo.getDeadline()));
//						redisClientTemplate.del("product.info." + pInfo.getId());
                                        // 砸金蛋活动 把产品绑定的加息券失效
                                        autoEggActivityFavourableInvalid(pInfo);

                                        // 约标开启
                                        if (!Utils.isBlank(pInfo.getAtid())) {
                                            jsProductReservationOpen(pInfo);
                                        }
                                    }
                                    // 自动发标
                                    if (Utils.isBlank(pInfo.getAtid()) && Utils.isBlank(pInfo.getPrizeId()) && pInfo.getType() != 1 && pInfo.getType() != 4) {
                                        // 每个产品只执行一次自动发标
                                        if (redisClientTemplate.get("autoReleaseProductLimit_" + pInfo.getId()) == null) {
                                            Map<String, Object> autoReleaseMap = new HashMap<String, Object>();
                                            autoReleaseMap.put("type", 50);
                                            autoReleaseMap.put("id", pInfo.getId());
                                            autoReleaseMap.put("deadline", pInfo.getDeadline());
                                            autoReleaseMap.put("fullName", pInfo.getFullName());
                                            autoReleaseMap.put("surplusAmount", pInfo.getSurplusAmount());
                                            //自动发标样本放到redis里
                                            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(autoReleaseMap));
                                        } else {
                                            if (pInfo.getStatus() == 6)
                                                redisClientTemplate.del("autoReleaseProductLimit_" + pInfo.getId());    // 产品募集完成自动发标原产品标记 失效
                                        }

                                    }

                                    if (!Utils.isBlank(pInfo.getAtid()) || !Utils.isBlank(pInfo.getPrizeId())) {// 活动产品满标提醒
                                        try {
                                            BigDecimal productSurplusAmount = pInfo.getSurplusAmount();// 产品剩余金额
                                            if (productSurplusAmount.compareTo(BigDecimal.ZERO) == 0) {
                                                String sendMobiles = redisClientTemplate.getProperties("autoPublishMobile_90");
                                                String fullScaleRemindSms = redisClientTemplate.getProperties("fullScaleRemind").replace("${1}",
                                                        pInfo.getFullName());
                                                String[] mobile = sendMobiles.split(",");
                                                for (int i = 0; i < mobile.length; i++) {
                                                    SysMessageLog sms = new SysMessageLog(0, fullScaleRemindSms, 1, null, mobile[i]);
                                                    sysMessageLogService.sendMsg(sms, 1);
                                                }
                                            }
                                        } catch (Exception e) {
                                            logger.error("满标提醒短信发送失败！");
                                        }
                                    }
                                    redisClientTemplate.setex(("product.info." + pInfo.getId()).getBytes(), 600, SerializeUtil.serialize(pInfo));
                                    drProductInfoDAO.updateProductSelective(pInfo);

                                    // 将优惠券置为已使用
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("uid", drMember.getUid());
                                    map.put("status", 0);
                                    map.put("amount", amount);
                                    map.put("id", fid);
                                    map.put("deadline", pInfo.getDeadline());
                                    List<DrMemberFavourable> list = drMemberFavourableDAO.getMemberFavourableByParam(map);
                                    DrMemberFavourable dmf = list.size() > 0 ? list.get(0) : null;
                                    if (dmf != null) {
                                        dmf.setStatus(1);
                                        dmf.setUsedTime(new Date());
                                        drMemberFavourableDAO.updateFavourableStatus(dmf);
                                    }
										
										/*
										 * param.clear(); param.put("uid", loginMember.getUid());
										 * param.put("status", 0); param.put("source", 0);//注册发放
										 * if(pInfo.getType() == 1){//新手标 param.put("type", new Integer[]{3});
										 * }else{ param.put("type", new Integer[]{3,4});//3:体验金 4:翻倍券 }
										 * List<DrMemberFavourable> list =
										 * drMemberFavourableDAO.getMemberFavourableByParam(param);
										 * if(!Utils.isEmptyList(list) && list.size()>0){ for(int
										 * i=0;i<list.size();i++){ dmf = new DrMemberFavourable();
										 * dmf.setStatus(2); dmf.setId(list.get(i).getId());
										 * drMemberFavourableDAO.updateFavourableStatus(dmf); } }
										 */

                                    // 插入投资记录
                                    logger.info("-------------------插入投资记录 Pid：" + pInfo.getId());
                                    DrProductInvest invest = new DrProductInvest();
                                    invest.setAmount(amount);
                                    invest.setUid(drMember.getUid());
                                    invest.setFid(fid);
//						invest.setJoinType(sysFuiouNoticeLog.getjoin_type);// PC端加入
                                    invest.setStatus(0);
                                    invest.setPid(pInfo.getId());
                                    BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(pInfo.getRate().add(pInfo.getActivityRate()), 100), 360);
                                    invest.setInterest(amount.multiply(dayRate).multiply(new BigDecimal(pInfo.getDeadline())).setScale(2,
                                            BigDecimal.ROUND_FLOOR));
                                    invest.setInvestTime(new Date());
                                    boolean isDoubleEgg = false;
                                    DrMemberLotteryLog drMemberLotteryLog = new DrMemberLotteryLog();
                                    int result = drProductInvestDAO.selectIsOldUserById(drMember.getUid());
                                    // 是否老用户
                                    if (result > 0) {
                                        String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
                                        String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
                                        String activity_60 = redisClientTemplate.getProperties("activity_60");
                                        String activity_180 = redisClientTemplate.getProperties("activity_180");
                                        Date nowDate = new Date();
                                        Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
                                        Date EndDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
                                        // 活动未过期有机会
                                        if (nowDate.before(EndDate) && nowDate.after(startDate)) {
                                            // 投资非新手标和体验标的产品有机会拆双蛋
                                            if (pInfo.getType() != 1 && pInfo.getType() != 5) {
                                                // 投资标时间60天或180天有机会
                                                if (pInfo.getDeadline() == 60 || pInfo.getDeadline() == 180) {
                                                    if (pInfo.getDeadline() == 60) {
                                                        invest.setSpecialRate(new BigDecimal(activity_60));
                                                    } else if (pInfo.getDeadline() == 180) {
                                                        invest.setSpecialRate(new BigDecimal(activity_180));
                                                    }
                                                    // 投资金额大于1000有机会拆双蛋
                                                    if (amount.compareTo(new BigDecimal(1000)) == 0
                                                            || amount.compareTo(new BigDecimal(1000)) == 1) {
                                                        if (amount.compareTo(new BigDecimal(10000)) == -1) {
                                                            drMemberLotteryLog.setAid(1);
                                                        } else if (amount.compareTo(new BigDecimal(100000)) == -1) {
                                                            drMemberLotteryLog.setAid(2);
                                                        } else if (amount.compareTo(new BigDecimal(100000)) == 0
                                                                || amount.compareTo(new BigDecimal(100000)) == 1) {
                                                            drMemberLotteryLog.setAid(3);
                                                        }
                                                        isDoubleEgg = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    drProductInvestDAO.insertSelective(invest);
                                    if (isDoubleEgg) {
                                        drMemberLotteryLog.setUid(drMember.getUid());
                                        drMemberLotteryLog.setInvestAmount(amount);
                                        drMemberLotteryLog.setAddTime(new Date());
                                        drMemberLotteryLog.setInvestId(invest.getId());
                                        drMemberLotteryLogDAO.insert(drMemberLotteryLog);
                                    }

                                    // 投资后资金变动
                                    DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(drMember.getUid());
                                    DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(pInfo.getId(), invest.getId(), drMember.getUid(),
                                            3, 0, amount, funds.getFuiou_balance().subtract(amount), 4, "投资【" + pInfo.getFullName() + "】产品", null);
                                    drMemberFundsRecordDAO.insert(fundsRecord);

                                    // 返现
                                    BigDecimal balanceProfit = BigDecimal.ZERO;// 返现金额
                                    DrMemberFavourable dm = drMemberFavourableDAO.selectByPrimaryKey(fid);
                                    // 红包做返现
                                    if (dm != null && dm.getType() == 1) {
                                        balanceProfit = dm.getAmount();
                                        if (balanceProfit.compareTo(BigDecimal.ZERO) > 0) {
                                            //放到 redis 缓存
                                            Map<String, Object> crushBackMap = new HashMap<String, Object>();
                                            crushBackMap.put("type", 52);
                                            crushBackMap.put("uid", drMember.getUid());
                                            crushBackMap.put("investId", invest.getId());
                                            crushBackMap.put("project_no", pInfo.getProject_no());
                                            //自动发标样本放到redis里
                                            redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(crushBackMap));
                                        }
                                    }

                                    // 操作日志
                                    DrMemberFundsLog fundslog = new DrMemberFundsLog(drMember.getUid(), fundsRecord.getId(), amount, 3, 0,
                                            "投资【" + pInfo.getFullName() + "】产品,资金冻结");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundslog);

                                    // 发送站内信
                                    DrMemberMsg msg = new DrMemberMsg(drMember.getUid(), 0, 3, "投资成功", new Date(), 0, 0,
                                            redisClientTemplate.getProperties("investSuccess").replace("${fullName}", pInfo.getFullName())
                                                    .replace("${amount}", invest.getAmount().toString()));
                                    drMemberMsgDAO.insertDrMemberMsg(msg);

//										redisClientTemplate.del("error.tpwd.uid." + drMember.getUid());

                                    // 用户资金记录 修改用户资金
                                    funds.setFuiou_balance(funds.getFuiou_balance().subtract(amount));// 可用余额减去投资金额
                                    funds.setFuiou_freeze(funds.getFuiou_freeze().add(amount));
                                    drMemberFundsDAO.updateDrMemberFunds(funds);

                                    Map<String, Object> m = new HashMap<String, Object>();

                                    // 年末投即送活动
                                    if (!Utils.isBlank(pInfo.getPrizeId())) {
                                        Map<String, Object> mparam = new HashMap<String, Object>();
                                        mparam.put("id", pInfo.getPrizeId());
                                        JsProductPrize jpPrize = jsProductPrizeDAO.selectJsPorudctPrize(mparam);
                                        if (amount.compareTo(jpPrize.getAmount()) == 0) {
                                            JsProductPrizeLog pLog = new JsProductPrizeLog();
                                            pLog.setInvestId(invest.getId());
                                            pLog.setPpid(jpPrize.getId());
                                            pLog.setType(0);// 投资订单
                                            pLog.setUid(drMember.getUid());
                                            jsProductPrizeLogDAO.insert(pLog);
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

                                        // 投资金额 每 1000 获得一个幸运码
                                        int luckCodeCount = invest.getAmount().intValue() / 1000;
                                        // 取得活动固定码
                                        // String prefix =
                                        // jsActivityProductDAO.selectActivityCodeFixation(pInfo.getAtid());
                                        Map<String, Object> template = jsActivityProductDAO.selectActivityTemplate(pInfo.getAtid());
                                        // 调接口获得 luckCodes
                                        List<String> luckList = seqService.generateLuckCodes(pInfo.getId(), luckCodeCount,
                                                (String) template.get("codeFixation"), (Integer) template.get("digit"));

                                        StringBuffer luckCode = new StringBuffer();
                                        for (String lucks : luckList) {
                                            luckCode.append(lucks).append(",");
                                        }
                                        String luckCodes = luckCode.toString();
                                        luckCodes = luckCodes.substring(0, luckCodes.length() - 1);
                                        // 插入 活动产品投资记录表
                                        JsActivityProductInvestInfo activityProduct = new JsActivityProductInvestInfo(invest.getId(), luckCodes, 0,
                                                0, null, null, null);

                                        jsActivityProductInvestInfoDAO.insert(activityProduct);

                                        m.clear();
                                        m.put("luckCodes", luckCodes);
                                        m.put("luckCodeCount", luckCodeCount);
                                    }
                                    m.put("investTime", new Date());
                                    m.put("investId", invest.getId());
                                    m.put("isDoubleEgg", isDoubleEgg);
                                    SysFuiouNoticeLog noticeLog = new SysFuiouNoticeLog();
                                    noticeLog.setStatus(2);
                                    noticeLog.setInvest_id(invest.getId());
                                    noticeLog.setId(fuiouNoticeLog.getId());
                                    sysFuiouNoticeLogDAO.update(noticeLog);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (lockFlag) {
                                    redisClientTemplate.del("product.id." + pInfo.getId());
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ssnLockFlag) {
                        redisClientTemplate.del("invest.mchnt_txn_ssn." + pInfo.getId());
                    }
                }
            }
        }
    }

    //产品募集完成
    private void autoEggActivityFavourableInvalid(DrProductInfo pinfo) {
        if (pinfo.getDeadline() == 60 || pinfo.getDeadline() == 180) {//把产品绑定的加息券失效
            Map<String, Object> map = new HashMap<>();
            map.put("status", 2);
            map.put("pid", pinfo.getId());
            map.put("eStatus", 0);
            drMemberFavourableDAO.updateFavourableStatusByMap(map);
        }
    }

    /**
     * ip7约标开启
     */
    private void jsProductReservationOpen(DrProductInfo drProductInfo) {
        try {
            Map<String, Object> acMap = jsActivityProductDAO.selectActivityProduct(drProductInfo.getId());
            if (Utils.isObjectNotEmpty(acMap) && Utils.isObjectNotEmpty(acMap.get("activityPeriods"))) {//期限不为空的
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("periods", Integer.parseInt(acMap.get("activityPeriods").toString()) + 1);
                map.put("order", " id desc ");
                map.put("offset", 0);
                map.put("limit", 1);
                List<JsProductReservation> list = jsProductReservationDAO.selectJsProductReservationByMap(map);
                if (!Utils.isEmptyList(list) && list.get(0).getStatus() == 0) {
                    JsProductReservation bean = new JsProductReservation();
                    bean.setId(list.get(0).getId());
                    bean.setStatus(1); //状态  0:待开启 1:开启 2:关闭
                    jsProductReservationDAO.update(bean);
                } else {
                    logger.info("ip7约标开启失败:" + "1002");
                }
            } else {
                logger.info("ip7约标开启失败:" + "1001");
            }
        } catch (Exception e) {
            logger.error("ip7约标开启失败:" + Utils.format(new Date(), "yyyy-MM-dd") + ".--->" + e.getMessage(), e);
        }
    }

    @Override
    public void productOtherInterestDistributeByFuiouMarketingAccount(Map<String, Object> param) throws Exception {
        BaseResult result = new BaseResult();
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("list");

        Map<String, Object> map;
        Map<String, Object> updateMap;
        JsMerchantMarketing jmm;
        JsCompanyAccountLog companyAccountLog;
        int pid = 0;


        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> failList = new ArrayList<Map<String, Object>>();
        List<JsCompanyAccountLog> companyLogList = new ArrayList<JsCompanyAccountLog>();
        List<JsMerchantMarketing> jmmList = new ArrayList<JsMerchantMarketing>();


        String successStr = null;
        try {
            for (Map<String, Object> mpas : list) {
                map = new HashMap<String, Object>();
                updateMap = new HashMap<String, Object>();
                //
                Integer uid = (Integer) mpas.get("uid");
                Integer project_id = (Integer) mpas.get("project_id");
                Integer repayInfoId = (Integer) mpas.get("repayInfoId");// 回款id可用作验证
                BigDecimal otherInterest = (BigDecimal) mpas.get("otherInterest");//// 其他收益额度


                DrProductInvestRepayInfo repayInfo = drProductInvestRepayInfoDAO.selectObjectById(repayInfoId);
                pid = repayInfo.getPid();

                if (Utils.isObjectNotEmpty(repayInfo) && (Utils.isBlank(repayInfo.getTransfer_status()) || repayInfo.getTransfer_status() != 2)) {
                    DrMember member = drMemberDAO.selectByPrimaryKey(uid);

                    String remitMchntTxnSsn = Utils.createOrderNo(6, repayInfoId, "");// 流水号

                    map.put("mchnt_txn_ssn", remitMchntTxnSsn);
                    map.put("out_cust_no", FuiouConfig.LOGIN_ID);
                    map.put("in_cust_no", member.getMobilephone());
                    map.put("amt", "" + otherInterest);
                    map.put("rem", "回款营销收益|repayInfoId:" + repayInfo.getId());
                    map.put("contract_no", "");
                    map.put("icd_name", "回款营销收益");

                    updateMap.put("id", repayInfoId);
                    updateMap.put("transfer_status", 2);// 转账成功
                    updateMap.put("transfer_fail_reson", "成功");//// 失败原因
                    updateMap.put("transferMchntTxnSsn", remitMchntTxnSsn);

                    //记公司账户日志 回款营销收益
                    companyAccountLog = new JsCompanyAccountLog();
                    companyAccountLog.setCompanyfunds(11);//资金类型
                    companyAccountLog.setType(0);//支出
                    companyAccountLog.setAmount(otherInterest);//金额
                    companyAccountLog.setStatus(3);//成功
                    companyAccountLog.setRemark(member.getMobilephone() + "回款营销收益");
                    companyAccountLog.setAddTime(new Date());
                    companyAccountLog.setChannelType(2);//存管
                    companyAccountLog.setUid(member.getUid());//用户id
                    companyAccountLog.setPid(repayInfo.getPid());//产品ID

                    //商户营销流水
//						jmm = new JsMerchantMarketing
//								(otherInterest, repayInfo.getInvestId(),repayInfo.getPid(), null, member.getUid(),
//										0, new Date(), remitMchntTxnSsn, "回款营销收益");

                    result = FuiouConfig.transferBmu(map);// 转帐
                    if (!result.isSuccess()) {
                        updateMap.put("transfer_status", 3);// 转账失败
                        updateMap.put("transfer_fail_reson", StringUtils.left(result.getErrorMsg(), 255));//// 失败原因
                        updateMap.put("transferMchntTxnSsn", remitMchntTxnSsn);
                        failList.add(updateMap);
                        logger.info("回款发放收益失败,uid=" + uid + ",repayInfoId=" + repayInfoId + ",存管流水:" + remitMchntTxnSsn);
                    } else {
                        logger.info("回款发放收益成功,uid=" + uid + ",repayInfoId=" + repayInfoId + ",存管流水:" + remitMchntTxnSsn);
                        successList.add(updateMap);//修改成功的
                        companyLogList.add(companyAccountLog);//添加公司日志
//							jmmList.add(jmm);//添加营销流水
                    }
                }
            }//for 结束

            successStr = "商户营销派发:success[" + successList.size() + "],fail[" + failList.size() + "],产品ID[" + pid + "]";

            // 执行事务
            if (successList.size() > 0) {//修改成功的
                drProductInvestRepayInfoDAO.updateRemitBatch(successList);
            }
            if (failList.size() > 0) {//修改失败的
                drProductInvestRepayInfoDAO.updateRemitBatch(failList);
            }
            if (companyLogList.size() > 0) {//公司日志
                jsCompanyAccountLogDAO.insertBatch(companyLogList);
            }
//				if(jmmList.size() > 0){//营销流水
//					jsMerchantMarketingDAO.insertBatch(jmmList);
//				}
        } catch (Exception e) {
            logger.error(successStr + "\n" + e.getMessage(), e);
            if (successStr != null) {
                SmsSendUtil.sendMsg("15800784479,15000895119", "异常执行事务失败:" + successStr);
            }
        }
    }

    @Override
    public void cashbackDistributeByFuiouMarketingAccount(Map<String, Object> param) throws Exception {

        BaseResult result = new BaseResult();
        Integer uid = (Integer) param.get("uid");
        Integer investId = (Integer) param.get("investId");//
        DrProductInvest invest = drProductInvestDAO.selectByPrimaryKey(investId);
        DrMemberFavourable dm = drMemberFavourableDAO.selectByPrimaryKey(invest.getFid());

        if (Utils.isObjectNotEmpty(invest) && uid.intValue() == dm.getUid().intValue()
                && Utils.isObjectNotEmpty(dm) && dm.getType() == 1
                && BigDecimal.ZERO.compareTo(dm.getProfitAmount()) == 0) {

            Integer pid = invest.getPid();//
            BigDecimal balanceProfit = dm.getAmount();//返现
            BigDecimal investAmount = invest.getAmount();//
            String fullName = invest.getFullName();//产品名
            String project_no = (String) param.get("project_no");//存管项目编号

            DrMember member = drMemberDAO.selectByPrimaryKey(uid);

            Map<String, Object> map = new HashMap<String, Object>();

            String remitMchntTxnSsn = Utils.createOrderNo(6, dm.getId(), "");//流水号
            map.put("mchnt_txn_ssn", remitMchntTxnSsn);
            map.put("out_cust_no", FuiouConfig.LOGIN_ID);
            map.put("in_cust_no", member.getMobilephone());
            map.put("amt", balanceProfit.toString());
            map.put("rem", "投资红包返现|investId:" + investId);
            map.put("contract_no", "");
            map.put("icd_name", "投资红包返现");

            try {

                dm.setProfitAmount(balanceProfit);
                drMemberFavourableDAO.updateByPrimaryKey(dm);

                //
                DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(uid);

                //用户资金记录 修改用户资金
                funds.setFuiou_balance(funds.getFuiou_balance().add(balanceProfit));
                funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(balanceProfit));// 已收益加上红包收益
                drMemberFundsDAO.updateDrMemberFunds(funds);

                DrMemberFundsRecord record = new DrMemberFundsRecord(pid, investId, uid, 4, 1,
                        balanceProfit,
                        funds.getFuiou_balance(), 3, "投资产品【" + fullName + "】返现，投资金额：" + investAmount, null);
                drMemberFundsRecordDAO.insert(record);

                DrMemberFundsLog logs = new DrMemberFundsLog(uid, record.getId(), balanceProfit,
                        20, 1, "投资产品【" + fullName + "】返现，投资金额：" + investAmount);
                drMemberFundsLogDAO.insertDrMemberFundsLog(logs);

                DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11, uid, pid, balanceProfit,
                        0, "投资产品【" + fullName + "】返现，投资金额：" + investAmount, 0);
                drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);

                //....
                //记公司账户日志 回款营销收益
                JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                companyAccountLog.setCompanyfunds(11);//资金类型
                companyAccountLog.setType(0);//支出
                companyAccountLog.setAmount(balanceProfit);//金额
                //companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(balanceProfit));//支出
                companyAccountLog.setStatus(3);//成功
                companyAccountLog.setPid(invest.getPid());//
                companyAccountLog.setRemark(member.getMobilephone() + "投资红包返现");
                companyAccountLog.setAddTime(new Date());
                companyAccountLog.setChannelType(2);//存管
                companyAccountLog.setUid(member.getUid());//用户id
                jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
					 
					 /*//商户营销流水
				JsMerchantMarketing jmm = new JsMerchantMarketing
						(balanceProfit, invest.getId(),invest.getPid(), invest.getFid(), member.getUid(),
								0, new Date(), remitMchntTxnSsn, "投资红包返现");
				jsMerchantMarketingDAO.insert(jmm);*/


            } catch (Exception e) {
                logger.error("投资返现红包失败:uid=" + uid + ",investId=" + investId, e);
                throw new RuntimeException("投资返现红包失败:uid=" + uid + ",investId=" + investId);
            }
            //调用fuiou接口
            result = FuiouConfig.transferBmu(map);//转帐
            if (!result.isSuccess()) {// 失败回滚事务
                logger.error("投资返现红包失败:" + result.getErrorMsg() + ",uid=" + uid + ",investId=" + investId + ",balanceProfit=" + balanceProfit);
                throw new RuntimeException("投资返现红包失败:" + result.getErrorMsg() + ",uid=" + uid + ",investId=" + investId + ",balanceProfit=" + balanceProfit);//外面捕捉到异常 ,会保存到redis里
            }

            logger.info("投资返现红包成功:uid=" + uid + ",investId=" + investId + ",balanceProfit=" + balanceProfit);
        } else {
            logger.error("投资返现红包失败[重复]:uid=" + uid + ",investId=" + investId);
        }

    }

    @Override
    public void updateFileStatus(List<DrProductInvest> param) {
        drProductInvestDAO.updateFileStatus(param);

    }

    @Override
    public void updateFileStatusRepayInfo(List<DrProductInvestRepayInfo> param) {
        drProductInvestRepayInfoDAO.updateFileStatus(param);

    }

    @Override
    public List<Map<String, Object>> getInvestRepayinfo(Map<String, Object> map) {
        return drProductInvestRepayInfoDAO.getInvestRepayinfo(map);
    }

    @Override
    public Map<String, Object> getInvestRepayinfoResidueSum(Map<String, Object> map) {
        return drProductInvestRepayInfoDAO.getInvestRepayinfoResidueSum(map);
    }

    @Override
    public Map<String, Object> getInvestRepayinfoAlreadySum(Map<String, Object> map) {
        return drProductInvestRepayInfoDAO.getInvestRepayinfoAlreadySum(map);
    }

    @Override
    public void updateEvid(List<Map<String, String>> productList) {
        drProductInvestDAO.updateEvid(productList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void BidreversalImplement(Map<String, Object> param) throws Exception{
        BaseResult result = new BaseResult();
        BigDecimal bidAmt = new BigDecimal(param.get("bidAmt").toString());
        Map<String, Object> mapTrans = new HashMap<String, Object>();
        Integer uid = (Integer) param.get("uid");
        logger.info("uid:" + uid);
        Integer investId = (Integer) param.get("investId");//
        logger.info("investId:" + investId);
        DrProductInvest invest = drProductInvestDAO
                .selectByPrimaryKey(investId);
        logger.info("invest:***************************" + invest);
        //BigDecimal investAmount = invest.getAmount();
        DrMember member = drMemberDAO.selectByPrimaryKey(uid);
        String remitMchntTxnSsn = Utils.createOrderNo(6, investId, "");// 流水号//
        mapTrans.put("mchnt_txn_ssn", remitMchntTxnSsn);
        mapTrans.put("out_cust_no", FuiouConfig.LOGIN_ID);
        mapTrans.put("in_cust_no", member.getMobilephone());
        mapTrans.put("amt", bidAmt);
        mapTrans.put("rem", "满标奖");
        mapTrans.put("contract_no", "");
        mapTrans.put("icd_name", "满标奖");
        try {
            DrMemberFundsRecord record = null;
            DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(uid);
            // 用户资金记录 修改用户资金
            funds.setFuiou_balance(funds.getFuiou_balance().add(bidAmt));
            funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(bidAmt));
            drMemberFundsDAO.updateDrMemberFunds(funds);
            logger.info("用户资金记录 修改用户资金" + funds.toString());
            // 5.用户资金交易记录表
            record = new DrMemberFundsRecord(invest.getPid(), investId,
                    uid, 4, 1, bidAmt, funds.getFuiou_balance(), 3,
                    "满标奖" + bidAmt + "元", null);
            drMemberFundsRecordDAO.insert(record);
            logger.info("用户资金交易记录表" + record.toString());
            // 6.用户资金日志明显表
            DrMemberFundsLog logs = new DrMemberFundsLog(uid, record.getId(),
                    bidAmt, 20, 1, "满标奖" + bidAmt + "元");
            drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
            logger.info("用户资金日志明显表" + logs.toString());
            DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11, uid, invest.getPid(),
                    bidAmt, 0, "满标奖" + bidAmt + "元", 0);
            drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
            logger.info("DrCompanyFundsLog=========" + cfundsLog.toString());
            // ....
            // 记公司账户日志 回款营销收益
            JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
            companyAccountLog.setCompanyfunds(11);// 资金类型
            companyAccountLog.setType(0);// 支出
            companyAccountLog.setAmount(bidAmt);// 金额
            companyAccountLog.setStatus(3);// 成功
            companyAccountLog.setPid(invest.getPid());//
            companyAccountLog.setRemark(member.getMobilephone() + "满标奖");
            companyAccountLog.setAddTime(new Date());
            companyAccountLog.setChannelType(2);// 存管
            companyAccountLog.setUid(member.getUid());// 用户id
            jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
            logger.info("记公司账户日志 回款营销收益" + companyAccountLog.toString());
            // 发送站内信
            DrMemberMsg drMemberMsg = new DrMemberMsg(uid, 0, 3, "您的满标奖励已派发！", new Date(), 0, 0,
                    "您在参与“满标赏金计划”活动中，获得“满标奖”" + bidAmt + "元！奖励已派发至您的“账户余额”，请及时查看！");
            drMemberMsgDAO.insertDrMemberMsg(drMemberMsg);
            // 调用fuiou接口
            result = FuiouConfig.transferBmu(mapTrans);// 转帐
            if (!result.isSuccess()) {// 失败回滚事务
                throw new RuntimeException("满标奖:" + result.getErrorMsg()
                        + ",uid=" + uid + ",investId=" + investId + ",bidAmt="
                        + param.get("bidAmt"));// 外面捕捉到异常 ,会保存到redis里
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void setEveryoneJdCard(Map<String, Object> param) throws SQLException {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        list=drProductInvestDAO.selectEveryoneTopFive(param);
        BypCommodityDetailBean bean = new BypCommodityDetailBean();
        bean.setStatus(1);
        bean.setAddtime(new Date());
        for(int i =0 ; i<list.size() ; i++){
            String uid = list.get(i).get("uid").toString();
            if (0==i){//给第一名发奖品
                //发站内信
                DrMemberMsg msglog = new DrMemberMsg(Integer.parseInt(uid), 0, 1, "恭喜您“5880元京东卡”中奖啦!!!", new Date(), 0,0, "" +
                        "恭喜您在币优铺“5880元京东卡”天天土豪争霸榜活动中获得第" +
                        "1名，奖励200元京东卡，奖励已累计到活动页面“已获200元京东卡”，" +
                        "请注意查看，活动结束后统一发放。感谢您对币优铺的信任和支持，更多精彩活动，请随时关注币优铺微信：" +
                        "Byoupu。若有疑问，欢迎致电：400-820-4684。");
                drMemberMsgDAO.insertDrMemberMsg(msglog);
                //给客户发京东卡
                map.put("price",200);
                map.put("prizeName","5880");
                map.put("activity_id","ph");
                map=prizeManageDao.selectPrizeId(map);
                bean.setPrid(Integer.parseInt(map.get("prid").toString()));
                bean.setUid(Integer.parseInt(uid));
                festivaiActivityDao.addPrizeStatistics(bean);
                logger.info("第一名奖品发送完毕,UID="+Integer.parseInt(uid));
            }else if (1==i){//给第二名发奖品
                //发站内信
                //发站内信
                DrMemberMsg msglog = new DrMemberMsg(Integer.parseInt(uid), 0, 1, "恭喜您“5880元京东卡”中奖啦!!!", new Date(), 0,0, "" +
                        "恭喜您在币优铺“5880元京东卡”天天土豪争霸榜活动中获得第" +
                        "2名，奖励100元京东卡，奖励已累计到活动页面“已获100元京东卡”，" +
                        "请注意查看，活动结束后统一发放。感谢您对币优铺的信任和支持，更多精彩活动，请随时关注币优铺微信：" +
                        "Byoupu。若有疑问，欢迎致电：400-820-4684。");
                drMemberMsgDAO.insertDrMemberMsg(msglog);
                //给客户发京东卡
                map.put("price",100);
                map.put("prizeName","5880");
                map.put("activity_id","ph");
                map=prizeManageDao.selectPrizeId(map);
                bean.setPrid(Integer.parseInt(map.get("prid").toString()));
                bean.setUid(Integer.parseInt(uid));
                festivaiActivityDao.addPrizeStatistics(bean);
                logger.info("第二名奖品发送完毕,UID="+Integer.parseInt(uid));
            }else {
                //发站内信
                DrMemberMsg msglog = new DrMemberMsg(Integer.parseInt(uid), 0, 1, "恭喜您“5880元京东卡”中奖啦!!!", new Date(), 0,0, "" +
                        "恭喜您在币优铺“5880元京东卡”天天土豪争霸榜活动中获得第" +
                        (i+1)+"名，奖励50元京东卡，奖励已累计到活动页面“已获50元京东卡”，" +
                        "请注意查看，活动结束后统一发放。感谢您对币优铺的信任和支持，更多精彩活动，请随时关注币优铺微信：" +
                        "Byoupu。若有疑问，欢迎致电：400-820-4684。");
                drMemberMsgDAO.insertDrMemberMsg(msglog);
                //给客户发京东卡
                map.put("price",50);
                map.put("prizeName","5880");
                map.put("activity_id","ph");
                map=prizeManageDao.selectPrizeId(map);
                bean.setPrid(Integer.parseInt(map.get("prid").toString()));
                bean.setUid(Integer.parseInt(uid));
                festivaiActivityDao.addPrizeStatistics(bean);
                logger.info("奖品发送完毕,UID="+Integer.parseInt(uid));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newBidreversalImplement(Map<String, Object> param) throws Exception{
        BaseResult result = new BaseResult();
        BigDecimal bidAmt = new BigDecimal(param.get("bidAmt").toString());
        Map<String, Object> mapTrans = new HashMap<String, Object>();
        Integer uid = (Integer) param.get("uid");
        logger.info("uid:" + uid);
        Integer investId = (Integer) param.get("investId");//
        logger.info("investId:" + investId);
        DrProductInvest invest = drProductInvestDAO
                .selectByPrimaryKey(investId);
        logger.info("invest:***************************" + invest);
        //BigDecimal investAmount = invest.getAmount();
        DrMember member = drMemberDAO.selectByPrimaryKey(uid);
        String remitMchntTxnSsn = Utils.createOrderNo(6, investId, "");// 流水号//
        mapTrans.put("mchnt_txn_ssn", remitMchntTxnSsn);
        mapTrans.put("out_cust_no", FuiouConfig.LOGIN_ID);
        mapTrans.put("in_cust_no", member.getMobilephone());
        mapTrans.put("amt", bidAmt);
        mapTrans.put("rem", "满万嘉奖");
        mapTrans.put("contract_no", "");
        mapTrans.put("icd_name", "满万嘉奖");
        try {
            DrMemberFundsRecord record = null;
            DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(uid);
            // 用户资金记录 修改用户资金
            funds.setFuiou_balance(funds.getFuiou_balance().add(bidAmt));
            funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(bidAmt));
            drMemberFundsDAO.updateDrMemberFunds(funds);
            logger.info("用户资金记录 修改用户资金" + funds.toString());
            // 5.用户资金交易记录表
            record = new DrMemberFundsRecord(invest.getPid(), investId,
                    uid, 4, 1, bidAmt, funds.getFuiou_balance(), 3,
                    "满万嘉奖" + bidAmt + "元", null);
            drMemberFundsRecordDAO.insert(record);
            logger.info("用户资金交易记录表" + record.toString());
            // 6.用户资金日志明显表
            DrMemberFundsLog logs = new DrMemberFundsLog(uid, record.getId(),
                    bidAmt, 20, 1, "满万嘉奖" + bidAmt + "元");
            drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
            logger.info("用户资金日志明显表" + logs.toString());
            DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11, uid, invest.getPid(),
                    bidAmt, 0, "满万嘉奖" + bidAmt + "元", 0);
            drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
            logger.info("DrCompanyFundsLog=========" + cfundsLog.toString());
            // ....
            // 记公司账户日志 回款营销收益
            JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
            companyAccountLog.setCompanyfunds(11);// 资金类型
            companyAccountLog.setType(0);// 支出
            companyAccountLog.setAmount(bidAmt);// 金额
            companyAccountLog.setStatus(3);// 成功
            companyAccountLog.setPid(invest.getPid());//
            companyAccountLog.setRemark(member.getMobilephone() + "满万嘉奖");
            companyAccountLog.setAddTime(new Date());
            companyAccountLog.setChannelType(2);// 存管
            companyAccountLog.setUid(member.getUid());// 用户id
            jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
            logger.info("记公司账户日志 回款营销收益" + companyAccountLog.toString());
            // 发送站内信
            DrMemberMsg drMemberMsg = new DrMemberMsg(uid, 0, 3, "您的满标奖励已派发！", new Date(), 0, 0,
                    "您在参与“满标赏金计划”活动中，获得“满万嘉奖”" + bidAmt + "元！奖励已派发至您的“账户余额”，请及时查看！");
            drMemberMsgDAO.insertDrMemberMsg(drMemberMsg);
            // 调用fuiou接口
            result = FuiouConfig.transferBmu(mapTrans);// 转帐
            if (!result.isSuccess()) {// 失败回滚事务
                throw new RuntimeException("满万嘉奖:" + result.getErrorMsg()
                        + ",uid=" + uid + ",investId=" + investId + ",bidAmt="
                        + param.get("bidAmt"));// 外面捕捉到异常 ,会保存到redis里
            }
        } catch (Exception e) {
            throw e;
        }
    }

}