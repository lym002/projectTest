package com.jsjf.controller.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.SensorsAnalytics.SensorsAnalytics;
import com.SensorsAnalytics.exceptions.InvalidArgumentException;
import com.jsjf.common.*;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.member.*;
import com.jsjf.service.product.*;
import com.jsjf.service.vip.MemberVipInfoService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jzh.FuiouConfig;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.activity.JsProductReservationLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductPic;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.activity.JsProductReservationLogService;
import com.jsjf.service.activity.JsProductReservationService;
import com.jsjf.service.claims.DrClaimsProjectService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.wechat.util.ModelPassivityMessageSendUtil;

@RequestMapping("/product")
@Controller
public class ProductController {
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private JsProductPrizeLogService jsProductPrizeLogService;
    @Autowired
    private DrProductInfoService drProductInfoService;
    @Autowired
    private DrProductInvestService drProductInvestService;
    @Autowired
    private DrMemberFundsService drMemberFundsService;
    @Autowired
    private DrProductPicService drProductPicService;
    @Autowired
    private DrActivityParameterService drActivityParameterService;
    @Autowired
    private DrProductExtendService drProductExtendService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrClaimsProjectService drClaimsProjectService;
    @Autowired
    private JsActivityProductInvestInfoService jsActivityProductInvestInfoService;
    @Autowired
    private JsProductReservationService jsProductReservationService;
    @Autowired
    private JsProductReservationLogService jsProductReservationLogService;
    @Autowired
    private DrMemberMsgService drMemberMsgService;
    @Autowired
    private JsNoviceContinueRecordService jsNoviceContinueRecordService;
    @Autowired
    private DrMemberFavourableService drMemberFavourableService;
    @Autowired
    private DrMemberBankService drMemberBankService;
    @Autowired
    private JsCompanyAccountLogService jsCompanyAccountLogService;
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;
    @Autowired
    private DrMemberFundsRecordService drMemberFundsRecordService;
    @Autowired
    private DrClaimsLoanDAO drClaimsLoanDAO;
    @Autowired
    private DrMemberService drMemberService;

    /**
     * 续投接口
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/addContinueReward")
    @ResponseBody
    public String addContinueReward(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        try {
            if (Utils.isObjectNotEmpty(param.get("period")) && Utils.isObjectNotEmpty(m)) {
                //查询投资用户新手标投资
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uid", m.getUid());
                map.put("type", 1);//新手标
                map.put("pStatus", 5);//募集中
                map.put("isShow", 1);//显示
                map.put("order", " dpi.id desc ");
                List<DrProductInvest> list = drProductInvestService.selectInvestByMap(map);
                if (!Utils.isEmptyList(list)) {
                    boolean lockFlag = redisClientTemplate.tryLock("addContinueReward" + m.getUid(), 3, TimeUnit.SECONDS, true);
                    if (lockFlag) {
                        br = jsNoviceContinueRecordService.addContinueReward(list.get(0), Integer.parseInt(param.get("period").toString()));
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
    public String getContinueReward(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        try {
            if (Utils.isObjectNotEmpty(m)) {
                //查询投资用户新手标投资
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uid", m.getUid());
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
                br.setErrorCode("1001");
                br.setErrorMsg("没有投资");
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
     * @param param
     * @return
     */
    @RequestMapping("/getReservation")
    @ResponseBody
    public String getReservation(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        try {
            if (Utils.isObjectNotEmpty(param.get("prid")) && Utils.isObjectNotEmpty(param.get("amount"))
                    && Utils.isObjectNotEmpty(m)) {
                BigDecimal amount = new BigDecimal(param.get("amount").toString());
                if (amount.compareTo(new BigDecimal("1000")) >= 0) {
                    JsProductReservation jpr = jsProductReservationService.selectReservationProduct(Integer.parseInt(param.get("prid").toString()));
                    if (Utils.isObjectNotEmpty(jpr) && jpr.getStatus() == 1 &&  //要开启: 0:待开启 1:开启 2:关闭
                            jpr.getStartTime().before(new Date()) &&  // now开始时间之后
                            jpr.getEndTime().after(new Date())) {  //now结束时间之前
                        JsProductReservationLog jsLog = new JsProductReservationLog(
                                Integer.valueOf(param.get("prid").toString()), m.getUid(), amount, 0);
                        jsProductReservationLogService.insert(jsLog);
                        //发站内信
                        DrMemberMsg msg = new DrMemberMsg(m.getUid(), 0, 2, jpr.getName() + "预约通知", new Date(), 0, 0,
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
    public String getMyLuckCodes(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
            if (Utils.isObjectEmpty(m)) {//用户要登录
                br.setErrorCode("9998");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
            params.put("uid", m.getUid());
            params.put("pid", param.get("id"));//产品id
            br = drProductInfoService.getMyLuckCodes(params);
        } catch (Exception e) {
            br.setErrorCode("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    /**
     * ip7活动页 +约标
     *
     * @param req
     * @return
     */
    @RequestMapping("/getNewActivityProduct")
    @ResponseBody
    public String getNewActivityProduct(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        br = drProductInfoService.getNewActivityProduct();
        //约标
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        if (br.isSuccess()) {
            Map<String, Object> map = jsProductReservationService.reservationProduct((DrProductInfo) br.getMap().get("result"), Utils.isObjectEmpty(m) ? null : m.getUid());
            ((Map<String, Object>) br.getMap()).putAll(map);
        }
        return JSON.toJSONString(br);
    }

    /**
     * 聚划算 产品获取
     *
     * @param req
     * @param params
     * @param pi
     * @return
     */
    @RequestMapping("/getPeroidProList")
    @ResponseBody
    public String getPeroidProList(HttpServletRequest req, @RequestBody Map<String, Object> params, PageInfo pi) {
        BaseResult br = new BaseResult();
        try {
            Utils.getObjectFromMap(pi, params);
            br.setMap(drProductInfoService.selectPeriodProductList(params, pi));
            br.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            br.setErrorMsg("系统错误");
            br.setErrorMsg("9999");
        }
        return JSON.toJSONString(br);//聚划算
    }

    /**
     * 理财列表
     *
     * @param req
     * @param params
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public String productList(HttpServletRequest req, @RequestBody Map<String, Object> params, PageInfo pi) {
        BaseResult br = null;
        try {
            DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
            Integer uid = null;
            if (null != m) {
                uid = m.getUid();
            }
            //未登录,不显示新手标
            params.put("uid", uid);
            Utils.getObjectFromMap(pi, params);
            br = drProductInfoService.selectProductInfoByParams(params, pi);
            //砸蛋活动处理
            drProductInfoService.eggActivityRuleFilter(null, (List<Map<String, Object>>) ((PageInfo) br.getMap().get("page")).getRows(), m);
            //双旦活动
            Map<String, Object> map = new HashMap<String, Object>();
            String activity_60 = redisClientTemplate.getProperties("activity_60");
            String activity_180 = redisClientTemplate.getProperties("activity_180");
            String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
            String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
            Date nowDate = new Date();
            Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
            Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
            if (nowDate.after(startDate) && nowDate.before(endDate)) {
                map.put("activity_60", activity_60);
                map.put("activity_180", activity_180);
            } else {
                map.put("activity_60", 0);
                map.put("activity_180", 0);
            }
            ((Map<String, Object>) br.getMap()).putAll(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("票据安选列表获取失败", e);
            br.setErrorMsg("9999");
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public String detail(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        List<String> pList = new ArrayList<String>() {{
            add("公司工商信息");
            add("营业执照");
            add("开户许可证");
            add("法人及股东身份证");
            add("公司章程");
            add("经营场所实地认证");
        }};
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        String pid = Utils.isObjectEmpty(param.get("id")) ? null : param.get("id").toString();//产品ID
        String type = Utils.isObjectEmpty(param.get("type")) ? null : param.get("type").toString();
        DrProductInfo productInfo = null;//产品信息
        DrMemberFunds funds = null;//资金信息
        try {
            if (!"4".equals(type) && Utils.isObjectEmpty(pid)) {
                throw new Exception("非法请求");
            }
            if (pid == null && "4".equals(type)) {//CPA产品页面
                productInfo = drProductInfoService.selectActivityProduct();
            } else {
                productInfo = drProductInfoService.selectProductDetailByPid(Integer.parseInt(pid));
            }
            if (Utils.isObjectNotEmpty(productInfo)) {
                param.clear();
                if (Utils.isObjectNotEmpty(m)) {
                    param.put("isFuiou", m.getIsFuiou());
                    funds = drMemberFundsService.selectDrMemberFundsByUid(m.getUid());
                    param.put("tpwdFlag", m.getTpassWord() == null ? false : true);
                    if (productInfo.getType() == 1 || productInfo.getType() == 3) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("uid", m.getUid());
                        map.put("statuses", new Integer[]{0, 1, 3, 4}); //2是投资失败的，可以继续投
                        map.put("type", 1);
                        Integer rows = drProductInvestService.selectInvestLogCountByParam(map);
                        param.put("newHandInvested", rows > 0 ? true : false);
                        map.put("type", 3);
                        String properties = redisClientTemplate.getProperties("newHandPhone");
                        String[] phones = properties == null ? null : properties.split(",");
                        map.put("phones", phones);
                        Integer fuiouRows = drProductInvestService.selectInvestLogCountByParam(map);
                        //判断用户是不是新新手标以前注册的
                        String fuiouNehHand = redisClientTemplate.getProperties("fuiouNewHand");
                        Date fuiouNewHandTime = Utils.parse(fuiouNehHand, "yyyy-MM-dd HH:mm:ss");
                        boolean isNewUser = m.getRegDate().after(fuiouNewHandTime) ? true : false;
                        param.put("fuiouNewHandInvested", fuiouRows > 0 ? true : false);
                        param.put("isNewUser", isNewUser);
                        map.clear();
                        map.put("uid", m.getUid());
                        map.put("statuses", new Integer[]{0, 1, 3, 4}); //2是投资失败的，可以继续投
                        map.put("type", 2);
                        Integer investeds = drProductInvestService.selectInvestLogCountByParam(map);
                        param.put("isInvested", investeds > 0 ? true : false);
                        //方便前端显示,新手标的成立时间
                        productInfo.setEstablish(Utils.getDayNumOfAppointDate(Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -1));
                        log.info("成立时间:" + Utils.getDayNumOfAppointDate(Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -1));
                        productInfo.setExpireDate(Utils.getDayNumOfAppointDate(productInfo.getEstablish(), 0 - productInfo.getDeadline()));
                        log.info("到期时间" + Utils.getDayNumOfAppointDate(productInfo.getEstablish(), 0 - productInfo.getDeadline()));
                        //查询未激活优惠券
                        Map<String, Object> param2 = new HashMap<String, Object>();
                        param2.put("source", 100);
                        param2.put("status", 0);
                        param2.put("type", new Integer[]{3});
                        param2.put("uid", m.getUid());
                        List<DrMemberFavourable> drMemberFavourableList = drMemberFavourableService.getMemberFavourableByParam(param2);
                        param.put("drMemberFavourableList", drMemberFavourableList);
                    }
                    //新新手标不显示激活体验金标签
                    if (productInfo.getAtid() != null || productInfo.getPrizeId() != null || productInfo.getType() == 3) {
                        param.put("isShowLabel", false);
                    } else {
                        param.put("isShowLabel", drMemberFavourableService.selectIsShowCountByUid(m.getUid()) > 0 ? true : false);
                    }
                    param.put("isAuth", FuiouConfig.isAuth(m.getAuth_st() != null ? m.getAuth_st() : null, 1));
                }
                //读取安选和优选
                if (productInfo.getType() == 2 || productInfo.getType() == 3) {
                    List<DrProductPic> listPic = drProductPicService.selectProductPicByPid(Integer.parseInt(pid));
                    param.put("picList", listPic);
                    List<String> projectList = drClaimsProjectService.selectListProjectByPid(Integer.parseInt(pid));
                    param.put("projectList", !Utils.isEmptyList(projectList) ? projectList : pList);
                }
                if (Utils.isObjectNotEmpty(productInfo.getExpireDate())) {
                    productInfo.setExpireDate(Utils.getDayNumOfAppointDate(productInfo.getExpireDate(), 1));
                }

                //ip7活动参与人数量
                if (!Utils.isBlank(productInfo.getAtid())) {
                    param.put("investTotal", jsActivityProductInvestInfoService.selectjsActivityProductInvestInfoCount(productInfo.getAtid(), productInfo.getId()));
                    //约标
                    param.putAll(jsProductReservationService.reservationProduct(productInfo, Utils.isObjectEmpty(m) ? null : m.getUid()));
                }
                //砸蛋活动处理
                List<DrProductInfo> list = new ArrayList<>();
                list.add(productInfo);
                drProductInfoService.eggActivityRuleFilter(list, null, m);

                param.put("info", productInfo);
                param.put("funds", funds);
                param.put("extendInfos", drProductExtendService.getDrProductExtendByPid(productInfo.getId()));
                //判断用户是否老用户
                boolean isOldUser = false;
                if (Utils.isObjectNotEmpty(m)) {
                    int result = drProductInvestService.selectIsOldUserById(m.getUid());
                    if (result > 0) {
                        isOldUser = true;
                    }
                }
                param.put("isOldUser", isOldUser);
                String activity_60 = redisClientTemplate.getProperties("activity_60");
                String activity_180 = redisClientTemplate.getProperties("activity_180");
                String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
                String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
                Date nowDate = new Date();
                Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
                Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
                if (nowDate.after(startDate) && nowDate.before(endDate)) {
                    if (productInfo.getDeadline() == 60) {
                        param.put("specialRate", activity_60);
                    } else if (productInfo.getDeadline() == 180) {
                        param.put("specialRate", activity_180);
                    } else {
                        param.put("specialRate", 0);
                    }
                } else {
                    param.put("specialRate", 0);
                }
                //等本等息-聚划算 产品
                if (productInfo.getRepayType() == 3 || productInfo.getRepayType() == 4) {
                    int period = productInfo.getRepayType() == 3 ? 7 : 30;
                    param.put("firstRepayDate", Utils.format(Utils.getDayNumOfAppointDate(productInfo.getEstablish(), -period - 1), "yyyy年MM月dd日"));//首次回款
                    param.put("repayPeriod", productInfo.getDeadline() / period);//总期数
                }
                //插入快如风逻辑
                param.put("pid", pid);
                List<JsCompanyAccountLog> jsCompanyAccountLogList = jsCompanyAccountLogService.selectBidPrizeByUid(param);
                param.put("jsCompanyAccountLogList", jsCompanyAccountLogList);
                /**
                 * 活动标产品详情
                 */
                List<JsProductPrizeLog> prizeList = jsProductPrizeLogService.selectLogByPid(Integer.parseInt(pid));
                param.put("prizeList", prizeList);
                br.setMap(param);
                br.setSuccess(true);
            } else {
                br.setSuccess(false);
                br.setErrorCode("1001");
            }
        } catch (Exception e) {
            log.error("详情获取失败,接收到的参数：" + JSON.toJSONString(param), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSON(br).toString();
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

    @RequestMapping(value = "/investList", method = RequestMethod.POST)
    @ResponseBody
    public String investList(HttpServletRequest req, PageInfo pi, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Utils.getObjectFromMap(pi, param);
        try {
            param.put("statuses", new Integer[]{0, 1, 3});
            if (pi.getPageInfo().getLimit() > 50) {//接口没有拦截,limit做控制
                pi.setPageSize(20);
            }
            br = drProductInvestService.selectInvestLogByParam(param, pi);
            log.info(br.toString());
            br.setSuccess(true);
        } catch (Exception e) {
            log.error("投资记录获取失败", e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);
    }

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    @ResponseBody
    public String invest(HttpServletRequest req, @RequestBody Map<String, Object> param) {

        BaseResult br = new BaseResult();
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//提交投资
        Map<String, Object> properties = new HashMap<String, Object>();
        SensorsAnalytics instance1 = SensorsAnalyticsUtil.getInstance();//支付投资项目
        Map<String, Object> properties1 = new HashMap<String, Object>();
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
        DrMember loginMember = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        Object pid = param.get("pid");
        String value = String.valueOf(System.currentTimeMillis());
        String values = "";
        String convertvalue = "";
        boolean lockFlag = false;
        Boolean lockFlags=false;
        Boolean convert=false;
        Boolean isSuccess=false;
        try {
            //判断客户是否第一次投资
            if (Utils.isObjectNotEmpty(loginMember.getUid())){
                Integer integer = drProductInvestService.selectNewInvest(loginMember.getUid());
                if(Utils.isObjectEmpty(integer)){
                    properties2.put("first_invest_time",new Date());
                    instance2.profileSet(String.valueOf(loginMember.getUid()),true,properties2);
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
            properties.put("AmountOfInvestment",new BigDecimal(param.get("amount").toString()));
            //dpi.rate*dpi.deadline*dpi.amount/360/100
            BigDecimal res=Utils.nwdDivide(Utils.nwdDivide(Utils.nwdMultiply(Utils.nwdMultiply(info1.getRate(),new BigDecimal(param.get("amount").toString())),
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
            if (info1.getSurplusAmount().subtract(new BigDecimal(param.get("amount").toString())).compareTo(BigDecimal.ZERO) == 0) {//满标情况
                properties1.put("IsFull",true);
                properties1.put("FullScaleTime",Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                properties1.put("FullScaleDuration",(Utils.getSpacings(info1.getEstablish())/60));
            }
            properties1.put("AmountOfInvestment",new BigDecimal(param.get("amount").toString()));
            //dpi.rate*dpi.deadline*dpi.amount/360/100
            BigDecimal res1=Utils.nwdDivide(Utils.nwdDivide(Utils.nwdMultiply(Utils.nwdMultiply(info1.getRate(),new BigDecimal(param.get("amount").toString())),
                    new BigDecimal(info1.getDeadline())),new BigDecimal(360)),new BigDecimal(100)).setScale(2,BigDecimal.ROUND_FLOOR);
            properties1.put("IncomeOfInvestment",res1);
            DrMemberFavourable drMemberFavourable = null;
            if (Utils.isObjectNotEmpty(param.get("fid"))){
                properties1.put("CouponId",Integer.parseInt(param.get("fid").toString()));
                drMemberFavourable = drMemberFavourableService.selectByPrimaryKey(Integer.parseInt(param.get("fid").toString()));
                properties1.put("CouponAmount",drMemberFavourable.getAmount());
            }
            properties1.put("ActualPaidAmount",new BigDecimal(param.get("amount").toString()));
            //投资过几次
            Map<String ,Object> para=new HashMap<>();
            para.put("uid", loginMember.getUid());
            para.put("statuses", new Integer[] { 0, 1, 3, 4 }); // 2是投资失败的，可以继续投
            Integer integer = drProductInvestService.selectInvestCountByMap(para);
            properties1.put("InvestCount",integer);
            //判断投资 时间
            String investBanTime = redisClientTemplate.getProperties("investBanTime");
            String[] investBanTimes;
            if (!Utils.strIsNull(investBanTime) && Utils.nearDawnMinutes(new Date(), investBanTimes = investBanTime.split(","))) {
                br.setSuccess(false);
                br.setErrorMsg("23点" + (60 - Integer.parseInt(investBanTimes[0])) + "分钟 至 0点 " + Integer.parseInt(investBanTimes[1]) + "分钟 系统维护,请稍后继续");
                properties1.put("IsSuccess",false);
                return JSON.toJSONString(br);
            }
            if (Utils.isObjectEmpty(loginMember)) {
                br.setSuccess(false);
                br.setErrorCode("1009");//用户未登陆
                properties1.put("IsSuccess",false);
                return JSON.toJSONString(br);
            }
            Integer errorNums = StringUtils.isBlank(redisClientTemplate.get("error.tpwd.uid." + loginMember.getUid())) ? 0 :
                    Integer.parseInt(redisClientTemplate.get("error.tpwd.uid." + loginMember.getUid()));//密码错误次数
            if (errorNums > 2) {
                br.setSuccess(false);
                br.setErrorCode("2001");
                properties1.put("IsSuccess",false);
                return JSON.toJSONString(br);
            }
            lockFlag = redisClientTemplate.tryLock("product.id." + pid, SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, value);
            if (lockFlag) {
                BigDecimal amount = new BigDecimal(String.valueOf(param.get("amount")));
                DrProductInfo info = (DrProductInfo) SerializeUtil.unserialize(redisClientTemplate.get(("product.info." + pid).getBytes()));
                if (Utils.isObjectEmpty(info)) {
                    info = drProductInfoService.selectProductByPrimaryKey(Integer.parseInt(pid.toString()));
                    if (info.getStartDate().after(new Date())) {
                        br.setSuccess(false);
                        br.setErrorMsg("产品未上架");
                        properties1.put("IsSuccess",false);
                        return JSON.toJSONString(br);
                    }
                    if (info.getStatus() == 5) {
                        redisClientTemplate.setex(("product.info." + pid).getBytes(), 600, SerializeUtil.serialize(info));
                    }
                }
                values = String.valueOf(System.currentTimeMillis());
                lockFlags = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash + loginMember.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, values);
                if (lockFlags) {
                    Map<String, Object> sumparam = new HashMap<>();
                    //查询累计额
                    sumparam.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                    sumparam.put("uid",loginMember.getUid());
                    Integer sum = drMemberFundsRecordService.selectInvestSumByOnlineTime(sumparam);
                    convertvalue=String.valueOf(System.currentTimeMillis());
                    convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + loginMember.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                    if(convert){
                        br = drProductInfoService.saveInvest(loginMember, info, param);
                    }
                    //投资成功后续业务
                    if (br.isSuccess()) {
                        drProductInfoService.investSuccessAfter(info, (Map<String, Object>) br.getMap(), loginMember);
                        if(convert){
                            taskIntegralRulesService.addPointsByInvest(loginMember.getUid(),sum,amount,info);
                        }
                      //微信模板消息
                        String openId = drMemberService.queryOpenId(loginMember.getUid());
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
                return JSON.toJSONString(br);
            }
            if (Utils.isObjectEmpty(br.getErrorCode())){
                properties1.put("IsSuccess",true);
            }else {
                properties1.put("IsSuccess",false);
            }
        } catch (Exception e) {
            log.error("投标失败,接收到的参数：" + JSON.toJSONString(param), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
            properties1.put("IsSuccess",false);
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
            try {
                instance.track(String.valueOf(loginMember.getUid()), true, "SubmissionInvest" ,properties);
                instance1.track(String.valueOf(loginMember.getUid()), true, "PayInvestmentProject" ,properties1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance.flush();
            instance1.flush();
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
     * 体验标详情页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/experienceDetail", method = RequestMethod.POST)
    @ResponseBody
    public String experienceDetail(HttpServletRequest req) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        DrMember loginMember = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        DrProductInfo productInfo = null;//体验标
        try {

            //判断是否登录，未登录的话只返回体验标详情。登录的情况下返回体验标详情&用户红包数量&投资次数统计&是否绑卡
            productInfo = drProductInfoService.selectExperienceDetail();
            if (Utils.isObjectEmpty(productInfo)) {
                br.setSuccess(false);
                br.setErrorCode("9999");
                br.setErrorMsg("体验标不存在");
                JSON.toJSONString(br);
            }
            if (Utils.isObjectNotEmpty(loginMember)) {
                map.put("uid", loginMember.getUid());
                map.put("type", 1);
                map.put("status", 0);//0是未使用的
                Integer favourablecount = drMemberFavourableService.selectDrMemberFavourableCountByUid(map);
                DrMemberBank bank = drMemberBankService.selectIdentificationBank(loginMember.getUid());
                Map<String, Object> experienceAmount = drMemberFavourableService.selectExperSumAmountId(loginMember.getUid());
                map.clear();
                map.put("favourablecount", favourablecount);
                map.put("bankVerify", Utils.isObjectEmpty(bank) ? 0 : 1);
                map.put("experienceAmount", experienceAmount);
            }
            Integer investCount = drProductInvestService.selectExperienceInvestCount(productInfo.getId());
            map.put("investCount", investCount);
            map.put("productInfo", productInfo);
            map.put("isFuiou", loginMember.getIsFuiou());
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            log.error(loginMember.getUid() + "获取体验标失败,参数：" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        }
        return JSON.toJSONString(br);

    }

    /**
     * 体验标投资
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/experienceInvest", method = RequestMethod.POST)
    @ResponseBody
    public String experienceInvest(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setSuccess(false);
            br.setErrorCode("1000");
            br.setErrorMsg("用户不存在");
            return JSON.toJSONString(br);
        } else {
            if (m.getIsFuiou() == 0) {
                br.setErrorCode("1006");
                br.setErrorMsg("未开通存管账户");
                br.setSuccess(false);
                return JSON.toJSONString(br);
            }
        }
        if (Utils.isObjectEmpty(param.get("pid")) || Utils.isObjectEmpty(param.get("ids"))) {
            br.setSuccess(false);
            br.setErrorCode("1001");
            br.setErrorMsg("参数不正确");
            return JSON.toJSONString(br);
        }
        boolean lockFlag = false;
        try {
            lockFlag = redisClientTemplate.tryLock("product.exper.uid." + m.getUid(), 3, TimeUnit.SECONDS, false);
            if (lockFlag) {
                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("uid", m.getUid());
                maps.put("ids", param.get("ids").toString().split(","));
                maps.put("status", 0);
                Map<String, Object> map = drMemberFavourableService.selectExperSumAmountIdByMap(maps);

                if (Utils.isObjectEmpty(map) || Utils.isObjectEmpty(map.get("experAmount"))
                        || Utils.isObjectEmpty(map.get("ids")) || !param.get("ids").equals(map.get("ids"))) {
                    br.setSuccess(false);
                    br.setErrorCode("1002");
                    br.setErrorMsg("体验金不存在或数据错误");
                    return JSON.toJSONString(br);
                }
                DrProductInfo drProductInfo = drProductInfoService.selectProductByPrimaryKey(Integer.parseInt(param.get("pid").toString()));
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
                br = drProductInfoService.experienceInvest(drProductInfo, m.getUid(), map, 0);

            } else {
                br.setSuccess(false);
                br.setErrorCode("1005");
                br.setErrorMsg("系统繁忙");
                return JSON.toJSONString(br);
            }
        } catch (Exception e) {
            log.error(m.getUid() + "投资体验标失败,参数：" + JSON.toJSONString(Utils.getParameterMap(req)), e);
            br.setSuccess(false);
            br.setErrorCode("9999");
        } finally {
            if (lockFlag) {
                redisClientTemplate.del("product.exper.uid." + m.getUid());
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
        map.put("redTotal", redTotal);
        map.put("realverify", m.getRealVerify());
        br.setMap(map);
        return JSON.toJSONString(br);
    }

    /**
     * 按期限分类取产品
     *
     * @param req
     * @param investId
     * @return
     */
    @RequestMapping(value = "/selectPorductClassifyByDeadline", method = RequestMethod.POST)
    @ResponseBody
    public String selectPorductClassifyByDeadline(HttpServletRequest req, @RequestBody Map<String, Object> param) {
        BaseResult br = new BaseResult();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deadlineOne", param.get("deadlineOne"));
            map.put("deadlineTwo", param.get("deadlineTwo"));
            map.put("deadlineThree", param.get("deadlineThree"));
            map.put("amount", param.get("amount"));
            List<DrProductInfo> list = drProductInfoService.selectPorductClassifyByDeadline(map);

            map.clear();
            map.put("list", list);

            br.setSuccess(true);
            br.setMap(map);
        } catch (Exception e) {
            br.setSuccess(false);
            br.setErrorMsg("请稍后重试！");
        }
        return JSON.toJSONString(br);
    }
}
