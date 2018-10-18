package com.jzh;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jzh.data.BaseRspdata;
import com.jzh.data.FastRecharg;
import com.jzh.data.FreezeReqData;
import com.jzh.data.PreAuthCancelReqData;
import com.jzh.data.PreAuthReqData;
import com.jzh.data.RechargeReqData;
import com.jzh.data.RegData;
import com.jzh.data.ResetPassWordReqData;
import com.jzh.data.SendSmsData;
import com.jzh.data.WebReqData;
import com.jzh.data.WithdrawalsReqData;
import com.jzh.service.JZHService;
import com.jzh.util.ConfigReader;
import com.jzh.util.SecurityUtils;
import com.jzh.util.StringUtil;
/**
 * 恒丰存管2.0
 * @author Ernes
 *
 */
public class FuiouConfig {
	
	public final static String MCHNT_CD;//商户代码
	public final static String VER;//版本
	
	public final static String OPENACCOUNT_BACK_NOTIFY_URL;//商户异步地址
	public final static String OPENACCOUNT_PAGE_NOTIFY_URL;//商户同步通知地址

	
	public final static String PREAUTHURL;//预授权接口地址（直连）
	public final static String PREAUTHCANCELURL;//撤销预授权接口地址（直连）
	public final static String REGURL;//个人用户自助开户注册（网页版）
	public final static String APPWEBREGURL;//APP个人用户自助开户注册
	public final static String APPSIGN_CARDURL;//APP免登签约
	public final static String APPQRECHARGEURL;//商户APP个人用户免登录快速充值
	public final static String APPQRECHARGE2URL;//商户APP个人用户免登录快捷充值
	public final static String APPWITHDRAWURL;//商户APP个人用户免登录提现
	public final static String APPRESETPASSWORDURL;//用户密码修改重置免登陆接口(app版)
	public final static String BRECHARGEURL;//商户P2P网站免登录网银充值（网页版）
	public final static String MPAY_SENDSMSURL;//快捷充值短信直连
	public final static String MPAY_SENDPAYURL;//快捷充值
	public final static String FREEZEURL;//冻结
	public final static String UNFREEZEURL;//解冻

	public final static String REG = "000000";//开户注册（直连）
	public final static String ARTIFREG = "000001";//法人开户注册（直连）
	public final static String PREAUTH = "000002";//预授权（直连）
	public final static String PREAUTHCANCEL = "000003";//撤销预授权（直连）
	public final static String TRANSFERBMU = "000004";//转账（商户与个人之）（直连）
	public final static String TRANSFERBU = "000005";//划拨(个人与个人之间)（直连）
	public final static String FREEZE = "000006";//冻结（直连）
	public final static String TRANSFERBMUANDFREEZE = "000007";//转账预冻结（直连）
	public final static String TRANSFERBUANDFREEZE = "000008";//划拨预冻结（直连）
	public final static String TRANSFERBUANDFREEZE2FREEZE = "000009";//冻结到冻结接口（直连）
	public final static String UNFREEZE = "000010";//解冻（直连）
	public final static String USERCHANGECARD = "000011";//用户更换银行卡接口（直连）
	public final static String WEBREG = "000012";//个人用户自助开户注册（网页版）
	public final static String WEBARTIFREG = "000013";//法人用户自助开户注册（网页版）
	public final static String QRECHARGE = "000014";//商户P2P网站免登录快速充值（网页版）
	public final static String BRECHARGE = "000015";//商户P2P网站免登录网银充值（网页版）
	public final static String WITHDRAW = "000016";//商户P2P网站免登录提现接口（网页版）
	public final static String RESETPASSWORD = "000017";//用户密码修改重置免登陆接口(网页版)
	public final static String PCQRECHARGE500405 = "000018";//PC端个人用户免登录快捷充值(网页版)
	public final static String APPWEBREG = "000019";//APP个人用户自助开户注册
	public final static String APPSIGN_CARD = "000020";//APP免登签约
	public final static String APPQRECHARGE = "000021";//商户APP个人用户免登录快速充值
	public final static String APPQRECHARGE2 = "000022";//商户APP个人用户免登录快捷充值
	public final static String APPWITHDRAW = "000023";//商户APP个人用户免登录提现
	public final static String APPRESETPASSWORD = "000024";//用户密码修改重置免登陆接口(app版)
	public final static String MPAY_SENDSMS = "000025";//快捷充值短信
	public final static String MPAY_SENDPAY = "000026";//快捷充值短信与充值
	
	
	@Autowired
	private SysFuiouNoticeLogService sysFuiouNoticeLogService;
	private static FuiouConfig fuiouConfig;
	public FuiouConfig(){
		
	}
	public void setSysFuiouNoticeLogService(SysFuiouNoticeLogService sysFuiouNoticeLogService) {  
        this.sysFuiouNoticeLogService = sysFuiouNoticeLogService;  
    }
	//类似声明了，当你加载一个类的构造函数之后执行的代码块，也就是在加载了构造函数之后，就将service复制给一个静态的service
	@PostConstruct  
    public void init() {  
		fuiouConfig = this;
		fuiouConfig.sysFuiouNoticeLogService = this.sysFuiouNoticeLogService;  
    }  
	static { ////注意 properties 里统一小写
		MCHNT_CD = ConfigReader.getConfig("mchnt_cd");
		VER = ConfigReader.getConfig("ver");
		OPENACCOUNT_BACK_NOTIFY_URL = ConfigReader.getConfig("openaccount_back_notify_url");
		OPENACCOUNT_PAGE_NOTIFY_URL =  ConfigReader.getConfig("openaccount_page_notify_url_app");
		PREAUTHURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("preauthurl");
		PREAUTHCANCELURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("preauthcancelurl");
		REGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("regurl");
		APPWEBREGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appwebregurl");
		APPSIGN_CARDURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appsign_cardurl");
		APPQRECHARGEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500001url");
		APPQRECHARGE2URL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500002url");
		APPWITHDRAWURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500003url");
		APPRESETPASSWORDURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appresetpasswordurl");
		BRECHARGEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500002url");
		MPAY_SENDSMSURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("mpay_sendsmsurl");
		MPAY_SENDPAYURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("mpay_sendpayurl");
		FREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("freezeurl");
		UNFREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("unfreezeurl");
	}
	
	
	/**
	 * 解冻接口
	 * @param params
	 * @return
	 */
	public static BaseResult freezeCancel(Map<String, Object>params){
		BaseResult br = new BaseResult();
		FreezeReqData data = new FreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
//		data.setMchnt_txn_ssn(params.get("mchnt_txn_ssn").toString());
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setCust_no(params.get("cust_no").toString());
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(UNFREEZE);
		sysFuiouNoticeLog.setIcd_name("解冻结接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getCust_no());
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(UNFREEZEURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 *冻结
	 * @param params
	 * @return
	 */
	public static BaseResult freeze(Map<String, Object>params){
		BaseResult br = new BaseResult();
		FreezeReqData data = new FreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setCust_no(params.get("cust_no").toString());
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(FREEZE);
		sysFuiouNoticeLog.setIcd_name("冻结接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getCust_no());
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(FREEZEURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());		
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 支付密码修改(页面)
	 * @param user_id 存管id
	 * @param busi_tp	业务类型;1:重置登录密码,2:修改登录密码,3:支付密码重置 4、修改支付密码
	 * @param id uid
	 * @return
	 */
	public static String resetPassword(Map<String,Object> map){
		ResetPassWordReqData data = new ResetPassWordReqData();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, Integer.parseInt(map.get("id").toString()), ""));
		
		data.setBusi_tp(map.get("busi_tp").toString());//1:重置登录密码,2:修改登录密码,3:支付密码重置
		data.setLogin_id(map.get("login_id").toString());
		
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPRESETPASSWORD);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setIcd_name("支付密码修改(页面)");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		
		String result = sysFuiouNoticeLog.getReq_message();
		JSONObject obj = JSONObject.fromObject(result);
		obj.getJSONObject("message").put("back_url", OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		result = obj.toString();
		
		return result;
		
	}
	
	/**
	 * 充值
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static String rechargeFirst(Map<String,Object> map){
		RechargeReqData data = new RechargeReqData();
		String amt = yuanToCent(map.get("amt").toString());
		
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setLogin_id(map.get("login_id").toString());
		data.setAmt(amt);
		
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(map.get("icd").toString());
		sysFuiouNoticeLog.setAmt(map.get("amt").toString());
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		
		if(PCQRECHARGE500405.equals(map.get("icd").toString()))
			sysFuiouNoticeLog.setIcd_name("快捷充值pc（页面）");
		else
			sysFuiouNoticeLog.setIcd_name("快捷充值p2p（页面）");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 开户
	 * @param mobile_no
	 * @param cust_nm
	 * @param certif_id
	 * @return
	 */
	public static String webReg(Map<String,Object> map){
		
		WebReqData  data = new WebReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, Integer.parseInt(map.get("id").toString()), ""));
		data.setMobile_no(map.get("mobile_no").toString());
		data.setCust_nm(map.get("cust_nm").toString());
		data.setCertif_id(map.get("certif_id").toString());
//		data.setCertif_tp("0");//证件类型
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPWEBREG);
		sysFuiouNoticeLog.setUser_id(data.getMobile_no());
		sysFuiouNoticeLog.setIcd_name("个人开户app（页面）");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
	
		return sysFuiouNoticeLog.getReq_message();
	}
	/**
	 * 预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuth(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthReqData data = new PreAuthReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTH);
		sysFuiouNoticeLog.setIcd_name("预授权接口（直连）");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setContract_no(jsonObject.getString("contract_no"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 撤销预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuthCancel(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthCancelReqData data = new PreAuthCancelReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setRem((String)params.get("rem"));
		data.setContract_no((String)params.get("contract_no"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTHCANCEL);
		sysFuiouNoticeLog.setIcd_name("撤销预授权接口（直连）");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHCANCELURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setUser_id(data.getIn_cust_no());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}	
	
	/**
	 * 提现
	 * @param login_id 用户手机号
	 * @param paymentNum 流水号
	 * @param amount 提现金额 
	 * * @param channel app/wap
	 * @return
	 */
	public static String withdrawals(String login_id,String paymentNum,String amount,String channel){		
		WithdrawalsReqData data = new WithdrawalsReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(paymentNum);//流水号
		data.setLogin_id(login_id);//登录ID
		data.setAmt(yuanToCent(amount));//金额
		
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+channel);
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPWITHDRAW);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setAmt(amount);
		sysFuiouNoticeLog.setIcd_name("提现（页面）");
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);		
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 分转元
	 * @param amount
	 * @return
	 */
	public static BigDecimal centToYuan(String amount){
		return new BigDecimal(amount).divide(new BigDecimal("100"));
	}
	/**
	 * 元转分
	 * @param amount
	 * @return
	 */
	public static String yuanToCent(String amount){
		return new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()+"";
	}
	/**
	 * 是否授权
	 * @param auth 授权状态码
	 * @param type 1投资 2还款
	 * @return
	 */
	public static boolean  isAuth(String auth_st,int type){
		
		if(auth_st != null && !"".equals(auth_st)){
			if(type == 1){
				auth_st = auth_st.substring(0,1);
			}else if(type ==2){
				auth_st = auth_st.substring(1,2);
			}
			if("1".equals(auth_st))
				return true;
		}else{
			return false;
		}
		return false;
	}
	
	
	/**
	 * 商户响应通知
	 */
	public static String  resNotice(String mchnt_txn_ssn,String resp_code){
		
		BaseRspdata data = new BaseRspdata();
		data.setResp_code(resp_code);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(mchnt_txn_ssn);
		
		return StringUtil.encaJSONstring(data);
	}
	
	/**
	 * 开户直连
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static BaseResult reg(Map<String,String> map){
		BaseResult br = new BaseResult();
		RegData data = new RegData();
		
		data.setVer(VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));
		data.setCust_nm(map.get("cust_nm"));//客户姓名
		data.setCertif_id(map.get("certif_id"));//身份证号码/证件
		data.setMobile_no(map.get("mobile_no"));//手机号码
		data.setCity_id(map.get("city_id"));//开户行地区代码
		data.setParent_bank_id(map.get("parent_bank_id"));//开户行行别
		data.setCapAcntNo(map.get("capAcntNo"));//帐号
		data.setPassword(map.get("password"));//提现密码 o
		
//		data.setEmail(map.get("email"));//邮箱地址 o
//		data.setBank_nm(map.get("bank_nm"));//开户行支行名称 o
//		data.setCapAcntNm(map.get("capAcntNm"));//户名 o
//		data.setLpassword(map.get("lpassword"));//登录密码 o
//		data.setRem(map.get("rem"));//备注 o
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(REG);
		sysFuiouNoticeLog.setIcd_name("开户直连");
		sysFuiouNoticeLog.setUser_id(data.getMobile_no());
		sysFuiouNoticeLog.setChannel(Integer.valueOf(map.get("channel")));
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(REGURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 获取短信验证码
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static BaseResult sendSms(Map<String,String> map){
		BaseResult br = new BaseResult();
		SendSmsData data = new SendSmsData();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn")); //                
		data.setLogin_id(map.get("login_id")); //M交易用户           
		data.setAmt(yuanToCent(map.get("amt"))); //M交易金额           
		data.setBank_mobile(map.get("bank_mobile")); //O银行预留手机号    
//		data.setCard_no(map.get("card_no")); //O银行卡号     
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setMchnt_txn_ssn("R"+sysFuiouNoticeLog.getMchnt_txn_ssn());//日志表里流水号唯一,充值与短信流水是同一个所以加R
		sysFuiouNoticeLog.setIcd(MPAY_SENDSMS);
		sysFuiouNoticeLog.setIcd_name("充值短信验证码");
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setChannel(Integer.valueOf(map.get("channel")));
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(MPAY_SENDSMSURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	
	/**
	 * 充值直连
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static BaseResult fastRecharg(Map<String,String> map){
		BaseResult br = new BaseResult();
		FastRecharg data = new FastRecharg();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn")); //该流水必须与获取交易银行卡预留手机短信验证码时，上送的商户流水保持一致              
		data.setTxn_date(map.get("txn_date")); //M交易用户           
		data.setYzm(map.get("yzm")); //M交易金额 
//		data.setBank_mobile(map.get("bank_mobile")); //O银行预留手机号        

		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(MPAY_SENDPAY);
		sysFuiouNoticeLog.setIcd_name("快捷充值");
		sysFuiouNoticeLog.setUser_id(map.get("login_id"));
		sysFuiouNoticeLog.setChannel(Integer.valueOf(map.get("channel")));
		fuiouConfig.sysFuiouNoticeLogService.insert(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(MPAY_SENDPAYURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			fuiouConfig.sysFuiouNoticeLogService.update(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 金账户交易通知返回XML
	 * @param resp_code
	 * @param mchnt_cd
	 * @param mchnt_txn_ssn
	 * @return
	 * @throws Exception 
	 */
	public static String notifyRspXml(String resp_code,String mchnt_txn_ssn) {
	
		String plain = "<plain>";
		plain +="<resp_code>"+resp_code+"</resp_code>";
		plain +="<mchnt_cd>"+MCHNT_CD+"</mchnt_cd>";
		plain +="<mchnt_txn_ssn>"+mchnt_txn_ssn+"</mchnt_txn_ssn>";
		plain +="</plain>";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<ap>");
		sb.append(plain);
		sb.append("<signature>"+SecurityUtils.sign(plain)+"</signature>");
		sb.append("</ap>");
		return sb.toString();
	}
	
}
