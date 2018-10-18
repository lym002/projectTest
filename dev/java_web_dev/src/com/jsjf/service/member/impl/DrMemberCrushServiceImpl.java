package com.jsjf.service.member.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsjf.common.SystemConstant;
import com.jsjf.service.integral.TaskIntegralRulesService;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberCrushDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.DrSubjectInfoDAO;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.DrCompanyFundsLog;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.utils.DateTimeUtils;
import com.jytpay.utils.MapHelper;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.Decipher;
import com.reapal.utils.ReapalSubmit;
import com.reapal.utils.ReapalUtil;
import com.sftpay.config.ExpressGlobalConfig;
import com.sftpay.utils.BaseExpressService;
import com.sftpay.utils.RSA;

@Service
@Transactional
public class DrMemberCrushServiceImpl implements DrMemberCrushService {
	private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberCrushDAO drMemberCrushDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberBankDAO drMemberBankDAO;
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private DrMemberBaseInfoDAO drMemberBaseInfoDAO;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	@Autowired
	private DrSubjectInfoDAO drSubjectInfoDAO;
	@Autowired
	private DrClaimsLoanDAO drClaimsLoanDAO;
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	   

	@Override
	public BaseResult insertPayOrder(HttpServletRequest req, DrMember member, String amount, DrMemberBank drMemberBank,
			SysBank sysBank) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		DrMemberCrush drMemberCrush = new DrMemberCrush();

		drMemberCrush.setUid(member.getUid());
		drMemberCrush.setAmount(new BigDecimal(amount));
		drMemberCrush.setPoundFee(new BigDecimal(0));
		drMemberCrush.setPayNum(createOrderNo(6, member.getUid()));
		drMemberCrush.setChannel(0);
		drMemberCrush.setBankId(drMemberBank.getId());
		if (1 == sysBank.getChannel()) {
			drMemberCrush.setStatus(0);
			drMemberCrush.setType(1);
			drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);
			map.put("drMemberCrush", drMemberCrush);
			br.setMap(map);
			br.setSuccess(true);
			return br;
		} else if (2 == sysBank.getChannel()) {
			drMemberCrush.setType(3);
			// 第一步.提交订单到盛付通
			NameValuePair[] orderParam = { new NameValuePair("merchantNo", ExpressGlobalConfig.merchantNo),
					new NameValuePair("charset", ExpressGlobalConfig.utf8),
					new NameValuePair("requestTime", Utils.getTime()),
					new NameValuePair("merchantOrderNo", drMemberCrush.getPayNum()),
					new NameValuePair("productName", ExpressGlobalConfig.productName),
					new NameValuePair("currency", ExpressGlobalConfig.currency),
					new NameValuePair("amount", drMemberCrush.getAmount().toString()),
					new NameValuePair("notifyUrl", ExpressGlobalConfig.paymentNotifyUrl), new NameValuePair("exts", ""),
					new NameValuePair("userIp", Utils.getIpAddr(req)) };
			String responseBody = BaseExpressService.httpSend(ExpressGlobalConfig.expressCreatePaymentOrderUrl,
					orderParam);
			Map<String, Object> result = JSONObject.parseObject(responseBody);

			if ("SUCCESS".equals(result.get("returnCode"))) {
				if ("999999".equals(result.get("returnDetailCode"))) {
					drMemberCrush.setStatus(0);
					drMemberCrush.setSftOrderNo(result.get("sftOrderNo").toString());
					drMemberCrush.setSessionToken(result.get("sessionToken").toString());
					map.put("drMemberCrush", drMemberCrush);
					br.setMap(map);
					br.setSuccess(true);
				} else if ("HPS_705".equals(result.get("returnDetailCode"))
						|| "HPS_706".equals(result.get("returnDetailCode"))) {
					drMemberCrush.setStatus(2);
					drMemberCrush.setAuditTime(new Date());
					br.setErrorCode("1003");
					br.setSuccess(false);
				} else {
					drMemberCrush.setStatus(2);
					drMemberCrush.setAuditTime(new Date());
					br.setErrorCode("1002");
					br.setSuccess(false);
				}
			} else {
				drMemberCrush.setAuditTime(new Date());
				drMemberCrush.setStatus(2);
				br.setErrorCode("1002");
				br.setSuccess(false);
			}
			String returnMessage = result.get("returnMessage") == null ? "" : result.get("returnMessage").toString();
			String returnCode = result.get("returnCode") == null ? "" : result.get("returnCode").toString();
			String returnDetailCode = result.get("returnDetailCode") == null ? ""
					: result.get("returnDetailCode").toString();

			drMemberCrush.setRemark("创建订单：" + returnCode + "|" + returnDetailCode + "|" + returnMessage);
			drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);
			return br;
		} else if (3 == sysBank.getChannel()) {
			// 融宝认证
			int moeny = new BigDecimal(amount).multiply(new BigDecimal(100)).intValue();// 转换成以分为单位
			drMemberCrush.setType(4);// 设置支付方式
			// 获取用户基本信息
			DrMemberBaseInfo baseInfo = drMemberBaseInfoDAO.queryMemberBaseInfoByUid(member.getUid());
			Map<String, String> maprb = new HashMap<String, String>();
			maprb.put("merchant_id", ReapalConfig.getMerchant_id());
			maprb.put("version", ReapalConfig.getVersion());
			maprb.put("card_no", drMemberBank.getBankNum()); // 银行卡号
			maprb.put("owner", baseInfo.getRealName()); // 用户姓名
			maprb.put("cert_type", "01"); // 默认
			maprb.put("cert_no", baseInfo.getIdCards()); // 用户身份证
			maprb.put("phone", drMemberBank.getMobilePhone()); // 用户银行预留手机号
			maprb.put("order_no", drMemberCrush.getPayNum());
			maprb.put("transtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			maprb.put("currency", "156"); // 默认（币种类型）
			maprb.put("title", "title");
			maprb.put("body", "body");
			maprb.put("member_id", baseInfo.getUid().toString());// 用户id
			maprb.put("terminal_type", "web");// 终端类型
			maprb.put("terminal_info", "1"); // 终端标识(CPU序列号)
			maprb.put("notify_url", ReapalConfig.getNotify_url());
			maprb.put("member_ip", member.getLastLoginIp());// 用户IP
			maprb.put("seller_email", ReapalConfig.getSeller_email());
			// 金额
			maprb.put("total_fee", String.valueOf(moeny));
			maprb.put("token_id", ReapalUtil.getUUID());

			// 返回结果
			String post = ReapalSubmit.buildSubmit(maprb, ReapalConfig.getInsertPayOrderUrl());
			log.info("创建订单返回报文============" + post);
			// 解密返回的数据
			String res = Decipher.decryptData(post);
			net.sf.json.JSONObject jasonObject = net.sf.json.JSONObject.fromObject(res);

			if ("0000".equals(jasonObject.get("result_code"))) {
				drMemberCrush.setStatus(0);
				map.put("drMemberCrush", drMemberCrush);
				br.setMap(map);
				br.setSuccess(true);
			} else {
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				br.setErrorCode("1002");
				br.setSuccess(false);
			}
			String returnMessage = jasonObject.get("result_msg") == null ? "" : jasonObject.get("result_msg").toString();
			String returnCode = jasonObject.get("result_code") == null ? "" : jasonObject.get("result_code").toString();

			drMemberCrush.setRemark("创建订单：" + returnCode + "|" + "|" + returnMessage);
			drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);
		} else {
			br.setErrorCode("1002");
			br.setSuccess(false);
		}
		return br;
	}
	/**
	 * 获取CPU序列号
	 * @return
	 *//*
	private String getCpuSerial(){
		String serial = "";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(
			        new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.next();
			serial = sc.next();//CPU序列号
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}*/

	@Override
	public BaseResult savePay(HttpServletRequest req, DrMember member, DrMemberBank drMemberBank,
			DrMemberCrush drMemberCrush, String smsCode) throws Exception {
		BaseResult br = new BaseResult();

		if (1 == drMemberCrush.getType()) {
			br = saveJYTPay(member, drMemberBank, drMemberCrush);
		} else if (4 == drMemberCrush.getType()) {// 融宝认证
			br = saveRBPay(member, drMemberBank, drMemberCrush, smsCode);
		} else {
			br = saveSFTPay(req, member, drMemberBank, drMemberCrush, smsCode);
		}
		return br;
	}

	/**
	 * 快捷支付-金运通
	 */
	public BaseResult saveJYTPay(DrMember member, DrMemberBank drMemberBank, DrMemberCrush drMemberCrush)
			throws Exception {
		BaseResult br = new BaseResult();
		JYTSendData sendData = new JYTSendData();
		sendData.setTran_code(MockClientMsgBase.COLLECTION_TRAN_CODE);
		sendData.setBank_name(drMemberBank.getBankName());
		sendData.setAccount_no(drMemberBank.getBankNum());
		sendData.setAccount_name(member.getRealName());
		sendData.setAccount_type("00");
		sendData.setTran_amt(drMemberCrush.getAmount());
		sendData.setCurrency(MockClientMsgBase.CURRENCY);
		sendData.setBsn_code(MockClientMsgBase.COLLECTION_BSN_CODE);
		sendData.setTran_flowid(drMemberCrush.getPayNum());
		sendData.setMobile(drMemberBank.getMobilePhone());
		sendData.setCert_type("01");
		sendData.setCert_no(member.getIdCards());
		JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);

		redisClientTemplate.lock("notifyJYT" + drMemberCrush.getUid());

		if ("S0000000".equals(resultData.getResp_code()) && "01".equals(resultData.getTran_state())) {
			Map<String, Object> map = new HashMap<String, Object>();
			drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(drMemberCrush.getPayNum());
			if (0 == drMemberCrush.getStatus()) {
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
				if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
				drMemberFundsRecordDAO.insert(record);

				DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
						drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

				sendMsg(drMemberCrush, mf, member);

				// 充值活动-截止7-31
				String str = rechargeActivity(member.getUid(), drMemberCrush.getAmount().intValue());
				if (!Utils.strIsNull(str)) {
					map.put("src", "https://www.byp.cn/images/app/cashp.png");
				}

				map.put("amount", drMemberCrush.getAmount());
				br.setMap(map);
				br.setSuccess(true);
			} else if (1 == drMemberCrush.getStatus()) {
				map.put("amount", drMemberCrush.getAmount());
				br.setMap(map);
				br.setSuccess(true);
			} else {
				br.setSuccess(false);
				br.setErrorCode("1038");
			}
		} else if ("03".equals(resultData.getTran_state())
				|| (resultData.getResp_code().startsWith("E") && !"E0000000".equals(resultData.getResp_code()))) {
			drMemberCrush.setRemark(resultData.getResp_desc());
			drMemberCrush.setStatus(2);
			drMemberCrush.setAuditTime(new Date());
			drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

			br.setSuccess(false);
			String errorCode = redisClientTemplate.getProperties(resultData.getResp_code());
			br.setErrorCode(errorCode == null ? "1038" : errorCode);
		} else {
			br.setSuccess(false);
			br.setErrorCode("1004");
		}
		return br;
	}

	/**
	 * 快捷支付-盛付通
	 */
	public BaseResult saveSFTPay(HttpServletRequest req, DrMember member, DrMemberBank drMemberBank,
			DrMemberCrush drMemberCrush, String smsCode) throws Exception {
		BaseResult br = new BaseResult();
		NameValuePair[] params = { new NameValuePair("merchantNo", ExpressGlobalConfig.merchantNo),
				new NameValuePair("charset", ExpressGlobalConfig.utf8),
				new NameValuePair("requestTime", Utils.getTime()),
				new NameValuePair("sessionToken", drMemberCrush.getSessionToken()),
				new NameValuePair("validateCode", smsCode), new NameValuePair("isSign", "true"),
				new NameValuePair("userIp", Utils.getIpAddr(req)) };
		String responseBody = BaseExpressService.httpSend(ExpressGlobalConfig.expressPaymentUrl, params);
		Map<String, Object> result = JSONObject.parseObject(responseBody);
		String returnMessage = result.get("returnMessage") == null ? "" : result.get("returnMessage").toString();
		String returnCode = result.get("returnCode") == null ? "" : result.get("returnCode").toString();
		String returnDetailCode = result.get("returnDetailCode") == null ? ""
				: result.get("returnDetailCode").toString();
		drMemberCrush.setRemark("支付：" + returnCode + "|" + returnDetailCode + "|" + returnMessage);
		if ("SUCCESS".equals(result.get("returnCode"))) {
			if ("999999".equals(result.get("returnDetailCode"))) {
				Map<String, Object> map = new HashMap<String, Object>();
				drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(drMemberCrush.getPayNum());
				if (0 == drMemberCrush.getStatus()) {
					drMemberCrush.setRemark("支付：" + returnCode + "|" + returnDetailCode + "|" + returnMessage);
					if ("00".equals(result.get("paymentStatus"))) {
						br.setSuccess(false);
						br.setErrorCode("1004");
					} else if ("01".equals(result.get("paymentStatus"))) {
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
						//充值任务添加积分
						if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
						drMemberFundsRecordDAO.insert(record);

						DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
								drMemberCrush.getAmount(), 6, 1,
								"充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
						drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

						drMemberBank.setAgreementNo(result.get("agreementNo").toString());
						drMemberBankDAO.updateDrMemberBank(drMemberBank);

						sendMsg(drMemberCrush, mf, member);

						map.put("amount", drMemberCrush.getAmount());

						// 充值活动-截止7-31
						String str = rechargeActivity(member.getUid(), drMemberCrush.getAmount().intValue());
						if (!Utils.strIsNull(str)) {
							map.put("src", "https://www.byp.cn/images/app/cashp.png");
						}

						br.setMap(map);
						br.setSuccess(true);
					} else {
						drMemberCrush.setStatus(2);
						drMemberCrush.setAuditTime(new Date());
						drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
						br.setSuccess(false);
						br.setErrorCode("1038");
					}
				} else if (1 == drMemberCrush.getStatus()) {
					map.put("amount", drMemberCrush.getAmount());
					br.setMap(map);
					br.setSuccess(true);
				} else {
					br.setErrorCode("1038");
					br.setSuccess(false);
				}
			} else {
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
				br.setErrorCode("1038");
				br.setSuccess(false);
			}
		} else {
			if ("手机验证码不正确".equals(returnMessage) || "手机验证码有误[输入的验证码有误]".equals(returnMessage)
					|| "手机验证码有误[手机验证码不正确]".equals(returnMessage)) {
				br.setErrorCode("1003");
			} else if (returnMessage.contains("余额不足")) {
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				br.setErrorCode("1044");
			} else {
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				br.setErrorCode("1038");
			}
			br.setSuccess(false);
			drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
		}

		return br;
	}

	/**
	 * 快捷支付-融宝
	 */
	public BaseResult saveRBPay(DrMember member, DrMemberBank drMemberBank,
			DrMemberCrush drMemberCrush, String smsCode) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, String> m = new HashMap<String, String>();
		m.put("merchant_id", ReapalConfig.getMerchant_id());
		m.put("version", ReapalConfig.getVersion());
		m.put("order_no", drMemberCrush.getPayNum()); // 订单号
		m.put("check_code", smsCode); // 6位验证码

		// 返回结果
		String post = ReapalSubmit.buildSubmit(m, ReapalConfig.getSaveRBPay());
		// 解密返回的数据
		String res = Decipher.decryptData(post);

		net.sf.json.JSONObject jasonObject = net.sf.json.JSONObject.fromObject(res);

		String returnMessage = jasonObject.get("result_msg") == null ? "" : jasonObject.get("result_msg").toString();
		String returnCode = jasonObject.get("result_code") == null ? "" : jasonObject.get("result_code").toString();
		drMemberCrush.setRemark("支付：" + returnCode + "|" + returnMessage);

		if ("0000".equals(jasonObject.get("result_code"))) {
			// 成功
			Map<String, Object> map = new HashMap<String, Object>();
			drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(drMemberCrush.getPayNum());
			if (0 == drMemberCrush.getStatus()) {
				// 未处理
				drMemberCrush.setRemark("支付：" + returnCode + "|" + returnMessage);
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
				//充值任务添加积分
				if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
				drMemberFundsRecordDAO.insert(record);

				DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
						drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

				sendMsg(drMemberCrush, mf, member);

				map.put("amount", drMemberCrush.getAmount());

				br.setMap(map);
				br.setSuccess(true);
			} else {
				// 失败
				br.setErrorCode("1038");
				br.setSuccess(false);
			}
		} else {
			if ("验证码错误".equals(returnMessage)) {
				br.setErrorCode("1003");
			} else if (returnMessage.contains("余额不足")) {
				drMemberCrush.setAuditTime(new Date());
				drMemberCrush.setStatus(2);
				br.setErrorCode("1044");
			} else {
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				br.setErrorCode("1038");
			}
			br.setSuccess(false);
			drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
		}

		return br;
	}

	public void receiveNotifyJYT(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setCharacterEncoding("UTF-8");
		String merchantId = req.getParameter("merchant_id");
		String xmlEnc = req.getParameter("xml_enc");
		String keyEnc = req.getParameter("key_enc");
		String sign = req.getParameter("sign");
		JYTResultData resultData = MockClientMsgBase.getInstance().resultNoticeMsg(merchantId, xmlEnc, keyEnc, sign);

		redisClientTemplate.lock("notifyJYT" + resultData.getOri_tran_flowid() == null ? ""
				: resultData.getOri_tran_flowid().substring(20));
		updateJYTPayProcess(resultData);
	}

	/**
	 * @param JYTResultData
	 *            商户唯一订单号
	 * @throws SQLException
	 * @throws Exception
	 * @throws IOException
	 */
	private void updateJYTPayProcess(JYTResultData resultData) throws Exception {
		DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushByPayNum(resultData.getOri_tran_flowid());
		if (Utils.isObjectNotEmpty(drMemberCrush)) {
			if ("S0000000".equals(resultData.getTran_resp_code()) && "01".equals(resultData.getTran_state())) {
				drMemberCrush.setStatus(1);
				drMemberCrush.setAuditTime(new Date());
				drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

				DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
				mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
				mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
				drMemberFundsDAO.updateDrMemberFunds(mf);

				DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());

				DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1,
						drMemberCrush.getAmount(), mf.getBalance(), 3,
						"充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
				//充值任务添加积分
				if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
				drMemberFundsRecordDAO.insert(record);

				DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
						drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

				sendMsg(drMemberCrush, mf, member);

				// 充值活动-截止7-31
				rechargeActivity(member.getUid(), drMemberCrush.getAmount().intValue());
			} else {
				drMemberCrush.setRemark(resultData.getTran_resp_desc());
				drMemberCrush.setStatus(2);
				drMemberCrush.setAuditTime(new Date());
				drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
			}
		}
	}

	@Override
	public String receiveNotifySFT(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String Charset = req.getParameter("Charset");
		String SignMsg = req.getParameter("SignMsg");

		String OrderNo = req.getParameter("OrderNo");
		String TransStatus = req.getParameter("TransStatus");
		String ErrorCode = req.getParameter("ErrorCode");
		String ErrorMsg = req.getParameter("ErrorMsg");
		String uid = OrderNo == null ? "" : OrderNo.substring(20);
		// 按签名顺序
		@SuppressWarnings({ "unchecked", "rawtypes" })
		TreeMap<String, String> map = new TreeMap<String, String>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return 1;
			}
		});
		map.put("Name", req.getParameter("Name"));
		map.put("Version", req.getParameter("Version"));
		map.put("Charset", req.getParameter("Charset"));
		map.put("TraceNo", req.getParameter("TraceNo"));
		map.put("MsgSender", req.getParameter("MsgSender"));
		map.put("outMemberId", req.getParameter("outMemberId"));
		map.put("SendTime", req.getParameter("SendTime"));
		map.put("InstCode", req.getParameter("InstCode"));
		map.put("OrderNo", OrderNo);
		map.put("OrderAmount", req.getParameter("OrderAmount"));
		map.put("TransNo", req.getParameter("TransNo"));
		map.put("TransAmount", req.getParameter("TransAmount"));
		map.put("TransStatus", TransStatus);
		map.put("TransType", req.getParameter("TransType"));
		map.put("TransTime", req.getParameter("TransTime"));
		map.put("MerchantNo", req.getParameter("MerchantNo"));
		map.put("ErrorCode", ErrorCode);
		map.put("ErrorMsg", ErrorMsg);
		map.put("Ext1", req.getParameter("Ext1"));
		map.put("Ext2", req.getParameter("Ext2"));
		map.put("SignType", req.getParameter("SignType"));

		// 5分钟以后在执行
		String notifySFTTime = redisClientTemplate.get("notifySFTTime_" + OrderNo);
		if (StringUtils.isBlank(notifySFTTime)) {
			redisClientTemplate.setex("notifySFTTime_" + OrderNo, 1800,
					Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return "noOk";
		} else {
			int seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()),
					redisClientTemplate.get("notifySFTTime_" + OrderNo));
			if (seconds > 300) {
				StringBuffer buf = new StringBuffer();
				Set<Entry<String, String>> set = map.entrySet();
				for (Entry<String, String> item : set) {
					if (StringUtils.isEmpty(item.getValue())) {
						continue;
					}
					buf.append(item.getValue());
					buf.append("|");
				}
				if (StringUtils.isNotBlank(SignMsg)) {
					boolean signResult = RSA.verify(buf.toString(), SignMsg, ExpressGlobalConfig.sftRsaPublicKey,
							Charset);
					if (signResult) {
						log.info("盛付通异步通知：" + OrderNo + "|" + TransStatus + "|" + ErrorCode + "|" + ErrorMsg);
						redisClientTemplate.lock("notifySFT" + uid);
						DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(OrderNo);
						if (Utils.isObjectNotEmpty(drMemberCrush)) {
							drMemberCrush.setRemark("异步通知:" + TransStatus + "|" + ErrorCode + "|" + ErrorMsg);
							if (1 != drMemberCrush.getStatus()) {
								if ("01".equals(TransStatus)) {
									drMemberCrush.setStatus(1);
									drMemberCrush.setAuditTime(new Date());
									drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

									DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
									mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
									mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
									drMemberFundsDAO.updateDrMemberFunds(mf);

									DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());

									DrMemberFundsRecord record = new DrMemberFundsRecord(null, null,
											drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(), mf.getBalance(), 3,
											"充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】",
											drMemberCrush.getPayNum());
									//充值任务添加积分
									if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
									drMemberFundsRecordDAO.insert(record);

									DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(),
											record.getId(), drMemberCrush.getAmount(), 6, 1,
											"充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
									drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

									sendMsg(drMemberCrush, mf, member);

									// 充值活动-截止7-31
									rechargeActivity(member.getUid(), drMemberCrush.getAmount().intValue());
								} else if ("02".equals(TransStatus)) {
									drMemberCrush.setStatus(2);
									drMemberCrush.setAuditTime(new Date());
									drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
								}
							}
						}
					}
				}
				return "OK";
			} else {
				return "noOK";
			}
		}
	}

	@Override
	public Map<String, String> insertWYDrMemberCrush(DrMember member, String amount, String bankCode) throws Exception {
		DrMemberCrush drMemberCrush = new DrMemberCrush();
		drMemberCrush.setUid(member.getUid());
		drMemberCrush.setAmount(new BigDecimal(amount));
		drMemberCrush.setPoundFee(new BigDecimal(0)); // 充值手续费
		drMemberCrush.setPayNum(createOrderNo(6, member.getUid()));
		drMemberCrush.setStatus(0);
		drMemberCrush.setChannel(0);
		drMemberCrush.setType(2);
		drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);

		Map<String, String> map = new HashMap<String, String>();

		map.put("tranCode", "TN1001");
		map.put("version", "1.0.0");
		map.put("charset", "utf-8");
		map.put("uaType", "00");
		map.put("merchantId", MockClientMsgBase.WY_MERCHANT_ID);
		map.put("merOrderId", drMemberCrush.getPayNum());
		map.put("merTranTime", DateTimeUtils.getDateTimeToString(new Date(), "yyyyMMddHHmmss"));
		map.put("merUserId", member.getUid().toString());
		map.put("orderDesc", "用户充值");
		map.put("prodInfo", "理财投资");
		map.put("tranAmt", drMemberCrush.getAmount().toString());
		map.put("curType", "CNY");
		map.put("payMode", "00");
		map.put("bankCode", bankCode);
		map.put("bankCardType", "01");
		map.put("notifyUrl", MockClientMsgBase.WY_NOTIFY_Url);
		map.put("backUrl", MockClientMsgBase.WY_BACK_Url);
		map.put("signType", "SHA256");
		map.put("key", MockClientMsgBase.WY_KEY);
		map = MapHelper.signMap(map);
		map.put("url", MockClientMsgBase.WY_SERVER_URL);
		return map;
	}

	@Override
	public BaseResult saveJYTWYPay(HttpServletRequest req) throws Exception {
		BaseResult br = new BaseResult();
		String respCode = req.getParameter("respCode");
		if ("S0000000".equals(respCode)) {
			String tranState = req.getParameter("tranState");
			String oriMerOrderId = req.getParameter("oriMerOrderId");
			redisClientTemplate.lock("rechargeWY" + oriMerOrderId == null ? "" : oriMerOrderId.substring(20));
			DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushByPayNum(oriMerOrderId);
			if (Utils.isObjectNotEmpty(drMemberCrush)) {
				if ("02".equals(tranState)) {
					drMemberCrush.setStatus(1);
					drMemberCrush.setAuditTime(new Date());
					drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

					DrMemberFunds mf = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCrush.getUid()); // 获取会员资金信息
					mf.setBalance(mf.getBalance().add(drMemberCrush.getAmount())); // 充值金额
					mf.setCrushCount(mf.getCrushCount().add(drMemberCrush.getAmount()));
					drMemberFundsDAO.updateDrMemberFunds(mf);

					DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());

					DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, drMemberCrush.getUid(), 1, 1,
							drMemberCrush.getAmount(), mf.getBalance(), 3,
							"充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】", drMemberCrush.getPayNum());
					//充值任务添加积分
					if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
					drMemberFundsRecordDAO.insert(record);

					DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
							drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

					sendMsg(drMemberCrush, mf, member);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("amount", drMemberCrush.getAmount());

					// 充值活动-截止7-31
//					rechargeActivity(member.getUid(), drMemberCrush.getAmount().intValue());

					br.setMap(map);
					br.setSuccess(true);
					return br;
				} else if ("01".equals(tranState)) {
					br.setErrorCode("1001");
					br.setSuccess(false);
					return br;
				} else if (!"00".equals(tranState)) {
					drMemberCrush.setRemark(tranState);
					drMemberCrush.setStatus(2);
					drMemberCrush.setAuditTime(new Date());
					drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
					br.setErrorCode("1002");
					br.setSuccess(false);
					return br;
				}
			} else {
				br.setErrorCode("1002");
				br.setSuccess(false);
				return br;
			}
		} else {
			br.setErrorCode("1002");
			br.setSuccess(false);
			return br;
		}
		return br;
	}

	@Override
	public DrMemberCrush getDrMemberCrushByPayNum(String payNum) {
		return drMemberCrushDAO.getDrMemberCrushByPayNum(payNum);
	}

	/**
	 * 充值活动
	 */
	private String rechargeActivity(int uid, int amount) throws Exception {
		if (new Date().before(Utils.parse("2016-08-13 00:00:00", "yyyy-MM-dd HH:mm:ss"))
				&& new Date().after(Utils.parse("2016-07-21 00:00:00", "yyyy-MM-dd HH:mm:ss"))) {
			List<DrMember> mList = drMemberDAO.selectMemberIsJoinActivity(uid);
			if (!Utils.isEmptyList(mList)) {
				if (amount >= 5000 && amount < 10000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(266);// 获取奖品信息
																									// 266
																									// 346

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(262);// 获取奖品信息
																									// 262
																									// 353

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				} else if (amount >= 10000 && amount < 30000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(267);// 获取奖品信息
																									// 267
																									// 347

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(263);// 获取奖品信息
																									// 263
																									// 354

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				} else if (amount >= 30000 && amount < 50000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(268);// 获取奖品信息
																									// 268
																									// 349

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(263);// 获取奖品信息
																									// 263
																									// 355

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				} else if (amount >= 50000 && amount < 100000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(269);// 获取奖品信息
																									// 269
																									// 350

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(264);// 获取奖品信息
																									// 264
																									// 356

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				} else if (amount >= 100000 && amount < 200000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(270);// 获取奖品信息
																									// 270
																									// 351

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(264);// 获取奖品信息
																									// 264
																									// 357

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				} else if (amount >= 200000) {
					DrActivityParameter dap1 = drActivityParameterDAO.getActivityParameterById(272);// 获取奖品信息
																									// 272
																									// 352

					DrMemberFavourable dmf1 = new DrMemberFavourable(dap1.getId(), uid, dap1.getType(), dap1.getCode(),
							dap1.getName(), dap1.getAmount(), dap1.getRaisedRates(), dap1.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap1.getDeadline()), "充值活动", 0, 3, dap1.getProductDeadline(),
							dap1.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf1);

					DrActivityParameter dap2 = drActivityParameterDAO.getActivityParameterById(265);// 获取奖品信息
																									// 265
																									// 358

					DrMemberFavourable dmf2 = new DrMemberFavourable(dap2.getId(), uid, dap2.getType(), dap2.getCode(),
							dap2.getName(), dap2.getAmount(), dap2.getRaisedRates(), dap2.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap2.getDeadline()), "充值活动", 0, 3, dap2.getProductDeadline(),
							dap2.getMultiple());
					drMemberFavourableDAO.insertIntoInfo(dmf2);
					return "1";
				}
			}
		}
		return null;
	}

	/**
	 * 创建商户订单号
	 * 
	 * @param req
	 * @return
	 */
	private String createOrderNo(int length, int uid) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buffer = new StringBuffer();
		buffer.append(sdf.format(date));
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			int tempvalue = rand.nextInt(10);
			buffer.append(tempvalue);
		}
		buffer.append(uid);
		return buffer.toString();
	}

	private void sendMsg(DrMemberCrush drMemberCrush, DrMemberFunds mf, DrMember member) throws Exception {
		// 充值成功 发送站内信
		DrMemberMsg insertMemberMsg = new DrMemberMsg();
		insertMemberMsg.setRuId(drMemberCrush.getUid());
		insertMemberMsg.setPuId(0);
		insertMemberMsg.setType(3);
		insertMemberMsg.setTitle("充值成功");

		String msg = redisClientTemplate.getProperties("rechargeMsg")
				.replace("${1}", drMemberCrush.getAmount().setScale(2).toString())
				.replace("${2}", mf.getBalance().setScale(2).toString());
		insertMemberMsg.setContent(msg); // 消息内容',
		insertMemberMsg.setAddTime(new Date()); // 发送时间',
		insertMemberMsg.setIsRead(0); // 是否阅读0未读，1已读',
		insertMemberMsg.setStatus(0); // 状态，0正常，1删除',
		drMemberMsgDAO.insertDrMemberMsg(insertMemberMsg);

		String sms = redisClientTemplate.getProperties("rechargeSms").replace("${1}", member.getRealName())
				.replace("${2}", drMemberCrush.getAmount().setScale(2).toString());
		// 发送手机短信
		if (member.getMobilephone() != null && !member.getMobilephone().equals("")) {
			SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 7, null, member.getMobilephone());
			sysMessageLogService.sendMsg(logs, 1);
		}
	}

	@Override
	public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException {
		drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
	}

	@Override
	public String receiveNotifyRB(String decryData, String merchant_id) throws Exception {
		net.sf.json.JSONObject jasonObject = net.sf.json.JSONObject.fromObject(decryData);
		String orderNo = jasonObject.get("order_no").toString();
		DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(orderNo);
		if(drMemberCrush.getStatus() == 0){//未处理
			// 3分钟以后在执行
			String notifySFTTime = redisClientTemplate.get("notifyRBTime_" + orderNo);
			if (StringUtils.isBlank(notifySFTTime)) {
				redisClientTemplate.setex("notifyRBTime_" + orderNo, 1800,
						Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				int seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()),	redisClientTemplate.get("notifyRBTime_" + orderNo));
				if (seconds > 110) {
					if (jasonObject.get("status").equals("TRADE_FINISHED")) {
						drMemberCrush = drMemberCrushDAO.getDrMemberCrushByStatus(orderNo);// 查询订单号是否存在
						if (drMemberCrush != null) {// 订单存在
							if (drMemberCrush.getStatus() == 0) {// 未处理
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
								//充值任务添加积分
								if (drMemberCrush.getAmount().intValue()>=100) {
					//充值任务添加积分
					taskIntegralRulesService.addPoints(drMemberCrush.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
				}
								drMemberFundsRecordDAO.insert(record);
			
								DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
										drMemberCrush.getAmount(), 6, 1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
								drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
			
								DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCrush.getUid());
								sendMsg(drMemberCrush, mf, member);
								return "success";
							}else{
								return "success";
							}
						}
					}
				}
			}
			return "fail";
		}else{
			return "success";
		}
	}
	@Override
	public void insertFuiouOrder(String order,int type,int uid,BigDecimal amount) throws Exception {
		//充值记录
		DrMemberCrush drMemberCrush = new DrMemberCrush();
		drMemberCrush.setStatus(0);
		drMemberCrush.setAddTime(new Date());
		drMemberCrush.setUid(uid);
		drMemberCrush.setAmount(amount);
		drMemberCrush.setPayNum(order);
		drMemberCrush.setType(type);
		drMemberCrush.setChannel(0);//pc
		
		if(type==5){//存管认证
			DrMemberBank bank = drMemberBankDAO.selectFuiouIdentificationBank(uid);
			if(Utils.isObjectNotEmpty(bank)){
				drMemberCrush.setBankId(bank.getId());
			}
		}
		drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);
	}
	
	@Override
	public void depositsRecharge(DrMemberCrush drMemberCrush,DrMember member) throws Exception {
		DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());
		//充值记录	
		drMemberCrush.setAuditTime(new Date());
		drMemberCrush.setAuditId(0);
		if(drMemberCrush.getStatus() == 1){//成功
			DrMemberFunds funds = new DrMemberFunds();
			funds.setUid(fund.getUid());
			funds.setFuiou_balance(fund.getFuiou_balance().add(drMemberCrush.getAmount()));					
			funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(drMemberCrush.getAmount()));
			
			drMemberFundsDAO.updateDrMemberFunds(funds);
			
			drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
			
			DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCrush.getUid(), 1, 1,drMemberCrush.getAmount(), funds.getFuiou_balance(),3,
					"充值金额：【"+ drMemberCrush.getAmount().setScale(2)  + "】", drMemberCrush.getPayNum());
			if (drMemberCrush.getAmount().intValue()>=100) {
				//充值任务添加积分
				taskIntegralRulesService.addPoints(member.getUid(), SystemConstant.RECHARGE_TYPE, drMemberCrush.getAmount().intValue());
			}
			drMemberFundsRecordDAO.insert(record);
			
			DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(),record.getId(),drMemberCrush.getAmount(),6,1,
					"充值金额：【"+ drMemberCrush.getAmount().setScale(2)  + "】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
			
			//记公司账户日志 回款营销收益
			JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
			companyAccountLog.setCompanyfunds(17);//资金类型
			companyAccountLog.setType(0);//支出
			
			BigDecimal amount = drMemberCrush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN);
			BigDecimal poundage = new BigDecimal("2");			
			poundage = poundage.compareTo(amount) == 1? poundage : amount;
			
			companyAccountLog.setAmount(poundage);//金额
//					companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(balanceProfit));//支出
			companyAccountLog.setStatus(3);//成功
			companyAccountLog.setRemark(member.getMobilephone()+"充值手续费:"+poundage+",充值金额:"+drMemberCrush.getAmount());
			companyAccountLog.setAddTime(new Date());
			companyAccountLog.setChannelType(2);//存管
			companyAccountLog.setUid(member.getUid());//用户id
			jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
			
			// 充值成功 发送站内信
			DrMemberFunds f = new DrMemberFunds();
			f.setBalance(funds.getFuiou_balance());
			f.setFuiou_balance(funds.getFuiou_balance());
			sendMsg(drMemberCrush,f,member);
		}else if(drMemberCrush.getStatus() == 2){
			drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
		}
	}
	@Override
	public DrMemberCrush getFuiouDrMemberCrushByPayNum(String payNum) {
		return drMemberCrushDAO.getFuiouDrMemberCrushByPayNum(payNum);
	}
	
	@Override
	public String updateReFundDrProductLoanStatus(SysFuiouNoticeLog noticeLog) throws SQLException {
		boolean relockFlag = false;
		
		Map<String, Object>map=new HashMap<>();
		map.put("code", noticeLog.getProject_no());
		DrProductInfo drProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
		if(Utils.isObjectNotEmpty(drProductInfo)){
			relockFlag  = redisClientTemplate.tryLock("productInfoFuioulock_"+drProductInfo.getId(),  3, TimeUnit.SECONDS,false);// 枷锁
			try {
				if(relockFlag){	
					if(drProductInfo.getLoanStatus()!=2){//未放款
						BigDecimal amount=new BigDecimal(0);
						if(Utils.isObjectNotEmpty(noticeLog.getAmt())){
							 amount = new BigDecimal(noticeLog.getAmt());
						}
						String loanNo;//合同号
						DrSubjectInfo drSubjectInfo;
						if(Utils.isObjectEmpty(drProductInfo.getSid())){
							loanNo = "xxxxxxx";
						}
						drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(drProductInfo.getSid());
						if(Utils.isObjectEmpty(drSubjectInfo)){
							loanNo = "xxxxxxx";
						}
						DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.getDrClaimsLoanByid(drSubjectInfo.getLid());
						if(Utils.isObjectEmpty(drClaimsLoan)){
							loanNo = "xxxxxxx";
						}else{
							loanNo = drClaimsLoan.getNo();
						}
						
						String tran_flowid = Utils.createOrderNo(6,drProductInfo.getId(),"");
						drProductInfoDAO.updateReFundDrProductLoanStatus(drProductInfo.getId());
						DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(16, null, 
								drProductInfo.getId(), amount, 1, 
										"债券合同["+loanNo+"]回款"+amount+"元,流水号["+tran_flowid+"]",null);
						drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
						
						//记公司账户日志 收取手续费
						BigDecimal money = amount.multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN);
						BigDecimal poundage = new BigDecimal("2");			
						poundage = poundage.compareTo(money) == 1? poundage : money;
						
						JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
						companyAccountLog.setCompanyfunds(17);//资金类型
						companyAccountLog.setType(0);//支出
						companyAccountLog.setAmount(poundage);//金额
						
						/*companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(new BigDecimal(3)));*/
						companyAccountLog.setStatus(3);//成功
						companyAccountLog.setRemark("商户回款网银充值手续费");
						companyAccountLog.setAddTime(new Date());
						companyAccountLog.setChannelType(2);//存管
						/*companyAccountLog.setUid(member.getUid());//用户id
				*/				jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
						return "0000";
					}
					//通知 处理完成  返回结果
					return "0000";
				}else{
					log.info("存管企业网银充值-异步通知:系统繁忙");
				}
			} catch (Exception e) {
				log.info("存管企业网银充值-异步通知失败:"+e.getMessage());
			}finally{
				if(relockFlag){
					redisClientTemplate.del("productInfoFuioulock_"+drProductInfo.getId());//释放枷锁
				}
			}
			}else{
				log.info("存管企业网银充值-异步通知失败:没有产品");
			}
			return "0001";
	}

    @Override
    public List<DrMemberCrush> selectCrushByUid(Map<String, Object> properties2) {
        return drMemberCrushDAO.selectCrushByUid(properties2);
    }
}