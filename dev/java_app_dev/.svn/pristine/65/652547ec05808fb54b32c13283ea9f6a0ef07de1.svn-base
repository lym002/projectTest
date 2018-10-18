package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.jsjf.common.BaseResult;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.BypDeviceMessageDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.dao.system.SysBankDAO;
import com.jsjf.model.member.BypDeviceMessage;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberRecommended;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.model.weixin.BindWeixinBean;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class DrMemberServiceImpl implements DrMemberService {
	
	@Autowired
	public DrMemberDAO drMemberDAO;
	@Autowired
	public DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	public DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	public DrMemberBaseInfoDAO drMemberBaseInfoDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrMemberRecommendedDAO drMemberRecommendedDAO;
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrChannelInfoDAO drChannelInfoDAO;
	@Autowired
	private SysBankDAO sysBankDAO;
	@Autowired
	private DrMemberBankDAO drMemberBankDAO;
	
	@Autowired
	private BypDeviceMessageDAO bypDeviceMessageDAO;
	
	@Override
	public DrMember selectDrMemberByMobilephone(String loginId) {
		return drMemberDAO.selectDrMemberByMobilephone(loginId);
	}
	
	@Override
	public Integer insertMember(DrMemberBaseInfo baseinfo,DrMember member,Integer rid,String deviceJson) throws Exception {
		String recommCode = getRecommCode();
		String androidUuid = null;
		member.setRecommCodes(recommCode);
		if(!Utils.strIsNull(deviceJson)){
			com.alibaba.fastjson.JSONObject json = JSON.parseObject(deviceJson);
			androidUuid = json.getString("phoneId");
		}
		if(!Utils.strIsNull(androidUuid)){
			//查询uuid是否存在
			String uid = drMemberDAO.queryMemberUid(androidUuid);
			if(Utils.isObjectNotEmpty(uid)){
				member.setAndroidUuid(androidUuid);
				member.setUid(Integer.parseInt(uid));
				drMemberDAO.updateDrMember(member);
			}else{
				drMemberDAO.insertDrMember(member);
			}
		}else{
			drMemberDAO.insertDrMember(member);
		}
		
		//生成会员统计数据
		BigDecimal defmount = new BigDecimal(0);
		//生成会员资金数据
		DrMemberFunds mf = new DrMemberFunds();
		mf.setUid(member.getUid());
		mf.setBalance(defmount);
		mf.setFreeze(defmount);
		mf.setWprincipal(defmount);
		mf.setWinterest(defmount);
		mf.setWpenalty(defmount);
		mf.setCarryCount(defmount);
		mf.setCrushCount(defmount);
		drMemberFundsDAO.insertDrMemberFunds(mf);
		
		//个人信息
		baseinfo.setUid(member.getUid());
		baseinfo.setSex(null);
		baseinfo.setAddTime(new Date());
		drMemberBaseInfoDAO.insertDrMemberBaseInfo(baseinfo);
		
		//推荐人
		DrMemberRecommended recommended = new DrMemberRecommended(member.getUid(), rid, null, null, null, null);
		drMemberRecommendedDAO.insertMemberRecommended(recommended);
		
		//赠送体验金
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			if(2 == member.getRegFrom().intValue() && Utils.isObjectNotEmpty(member.getToFrom()) && member.getToFrom().equals(redisClientTemplate.getProperties("yingyongbao"))){
//				map.put("type", 3);//新手体验金	
//				map.put("status", 0);
//				map.put("code", "TYJ-20170919105406");//TODO:XXQ
//				DrActivityParameter dap = drActivityParameterDAO.getDrActivityParameterByParam(map);
//				DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(),member.getUid(), dap.getType(), dap.getCode(), dap.getName(), dap.getAmount(), dap.getRaisedRates(),
//						dap.getEnableAmount(), 0, Utils.getDayNumOfAppointDate(new Date(), -dap.getDeadline()), dap.getName(), 0,0,dap.getMultiple(),dap.getProductDeadline());
//				drMemberFavourableDAO.insertIntoInfo(dmf);
//				dap.setGrantQty(dap.getGrantQty()+1);
//				drActivityParameterDAO.updateActivityParameter(dap);
//			}
//		} catch (Exception e) {
//			System.out.println("安卓赠送体验金异常[uid:"+member.getUid()+",错误:"+e.getMessage()+"]");
//		}

//		//赠送翻倍券（不参与的渠道注册用户不发放翻倍券；当活动类型的翻倍券剩余数量为0时，此活动自动结束）
//		boolean flag = true;
//		map.clear();
//		map.put("inverted", member.getToFrom());
//		List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);
//		if(!Utils.isEmptyList(channelList)){
//			DrChannelInfo channel = channelList.get(0);
//			if(channel.getIsParticipation()==1){
//				flag = false;
//				System.out.println("渠道【"+channel.getName()+"】不参与注册送翻倍券活动");
//			}
//		}
//		
//		if(flag){
//			map.clear();
//			map.put("type", 4);//注册送翻倍券
//			map.put("status",0);
//			map.put("applicableScenarios","1");
//			DrActivityParameter activity = drActivityParameterDAO.getDrActivityParameterByParam(map);
//			if(Utils.isObjectNotEmpty(activity) && activity.getSurplusQty()-1>=0){//剩余数量大于0,继续给用户送翻倍券
//				DrMemberFavourable favourable = new DrMemberFavourable(activity.getId(),member.getUid(), activity.getType(), activity.getCode(), activity.getName(), activity.getAmount(), activity.getRaisedRates(),
//						activity.getEnableAmount(), 0, Utils.getDayNumOfDate(1-activity.getDeadline()), "注册送翻倍券", 0,0,activity.getMultiple(),activity.getProductDeadline());
//				drMemberFavourableDAO.insertIntoInfo(favourable);
////				activity.setSurplusQty(activity.getSurplusQty()-1);
////				drActivityParameterDAO.updateActivityParameter(activity);
//			}
//		}
		
		// 发送手机短信
		String sms = redisClientTemplate.getProperties("receiveCouponSms");
		if (member.getMobilephone() != null && !member.getMobilephone().equals("")) {
			SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 2, null, member.getMobilephone());
			sysMessageLogService.sendMsg(logs,1);
		}
//		//发站内信
//		DrMemberMsg msg = new DrMemberMsg(member.getUid(), 0, 1, "注册成功", new Date(), 0, 0, redisClientTemplate.getProperties("receiveCouponMsg"));
//		drMemberMsgDAO.insertDrMemberMsg(msg);
        return member.getUid();
	}

	@Override
	public void update(DrMember DrMember) throws Exception {
		drMemberDAO.updateByPrimaryKey(DrMember);
	}
	
	@Override
	public DrMember selectByPrimaryKey(Integer uid) {
		return drMemberDAO.selectByPrimaryKey(uid);
		
	}

	@Override
	public Map<String, Integer> queryCollectCountAndStayCount(
			Map<String, Object> map) {
		return drMemberDAO.queryCollectCountAndStayCount(map);
	}
	

	public String getRecommCode(){
		String code = "";
		String[] beforeShuffle = new String[] {
				"A", "B", "C", "D", "E", "F", "G", "H", "J",  
               "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",  
               "W", "X", "Y", "Z" }; 
		Random rd = new Random();
		do {
			code = beforeShuffle[rd.nextInt(24)]+Utils.getRandomNumber();
			code = code.substring(0,3)+beforeShuffle[rd.nextInt(24)]+code.substring(3,5);
			DrMember member = drMemberDAO.selectDrMemberByMobileOrRecomm(code);
			if(member == null){
				break;
			}
		} while (true);
		return code;
	}

	@Override
	public DrMember selectDrMemberByMobileOrRecomm(String str) {
		return drMemberDAO.selectDrMemberByMobileOrRecomm(str);
	}

	@Override
	public boolean isExistsMobilphone(String mobilephone) {
		Integer count = drMemberDAO.isExistsMobilphone(mobilephone);
		if(count==0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public String selectMobilePhoneByRecommCode(String recommCode) {
		return drMemberDAO.selectMobilePhoneByRecommCode(recommCode);
	}

	@Override
	public int selectFirstPayMent(Integer uid) {
		return drMemberDAO.selectFirstPayMent(uid);
	}

	@Override
	public List<Map<String, Object>> lastRegMember() {
		
		return drMemberDAO.lastRegMember();
	}
	

	@Override
	public void openAccount(JSONObject message) throws Exception {
		System.out.println("个人开户验签成功---------------------");
		boolean lockFlag = false;
		String  openAccountRes= "openAccountRes_"+(String)message.get("mchnt_txn_ssn");
		try {
			lockFlag = redisClientTemplate.tryLock(openAccountRes, 3, TimeUnit.SECONDS,false);
			if(lockFlag){
				DrMember m = drMemberDAO.selectDrMemberByMobilephone((String)message.get("mobile_no"));
				if(Utils.isObjectNotEmpty(m) && (m.getIsFuiou() == null || m.getIsFuiou() ==0)){
					//用户表
					m.setIsFuiou(1);
					m.setMchnt_txn_ssn((String)message.get("mchnt_txn_ssn"));
//							m.setFuiou_acnt((String)message.get("fuiou_acnt"));
//							m.setUser_id((String)message.get("user_id"));
//							m.setAuth_st((String)message.get("auth_st"));
//						m.setRealVerify(1);
					drMemberDAO.updateByPrimaryKey(m);
					//银行表
					DrMemberBank bank = new DrMemberBank();
					bank.setAddTime(new Date());
					bank.setAddUser(0);
					String parent_bank_id  = message.optString("parent_bank_id");
					String bankname = sysBankDAO.selectBankByCode(parent_bank_id+"0000");
					
					bank.setBankName(bankname);
					bank.setBankNum((String)message.get("capAcntNo"));
					bank.setStatus(1);
					bank.setChannel(Integer.valueOf(message.optString("channel")));
					bank.setType(3);
					bank.setUid(m.getUid());				
					
					drMemberBankDAO.insertDrMemberBank(bank);
					
					//基础表
					DrMemberBaseInfo info = drMemberBaseInfoDAO.queryMemberBaseInfoByUid(m.getUid());				
					if(Utils.isObjectNotEmpty(info) && (Utils.isObjectEmpty(info.getRealName())|| Utils.isObjectEmpty(info.getIdCards()))){
						info.setRealName((String)message.get("cust_nm"));
						info.setIdCards((String)message.get("certif_id"));
						
						// 性别和生日
						Integer sexNum = Integer.parseInt(info.getIdCards().substring(16, 17));
						String birthday = info.getIdCards().substring(6, 14);
						info.setSex(sexNum % 2 != 0 ? 1 : 2);
						info.setBirthDate(new SimpleDateFormat("yyyyMMdd").parse(birthday));
						
						drMemberBaseInfoDAO.updateDrMemberBaseInfoById(info);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(lockFlag){
				redisClientTemplate.del(openAccountRes);
			}
		}
	}
	@Override
	public int selectDrmembercount() {
		
		return drMemberDAO.selectDrmembercount();
	}

	
	@Override
	public Map<String, Object> selectIndexSummaryData() {
		return drMemberDAO.selectIndexSummaryData();
	}

	@Override
	public DrMember selectIsBypOldUser(Integer uid,int[] ids) {
		return drMemberDAO.selectIsBypOldUser(uid,ids);
	}

	@Override
	public DrMember selectForUpDateByPrimaryKey(Integer uid) {
		return drMemberDAO.selectForUpDateByPrimaryKey(uid);
	}

	@Override
	public void insertDevice(com.alibaba.fastjson.JSONObject json, Integer uid) {
		BypDeviceMessage deviceBean = new BypDeviceMessage();
		if(!Utils.strIsNull(json.getString("phoneId"))) {
			String phoneId = json.getString("phoneId");
			deviceBean.setPhoneId(phoneId);
		}
		if(!Utils.strIsNull(json.getString("other"))) {
			String other = json.getString("other");
			deviceBean.setOther(other);
		}
		deviceBean.setUid(uid);
		bypDeviceMessageDAO.insertDevice(deviceBean);
	}

	@Override
	public void insertMemberAndroidUuio(String androidUuid,String toFrom) {
		DrMember member = new DrMember();
		if(!Utils.strIsNull(androidUuid)) {
			//查询uuid是否存在
			int count = drMemberDAO.queryMemberUuid(androidUuid);
			if(Utils.isObjectEmpty(count) || count == 0){
				member.setPassWord(androidUuid);
				member.setToFrom(toFrom);
				if("appstore".equals(toFrom)){
					member.setRegFrom(1);
				}else{
					member.setRegFrom(2);
				}
				drMemberDAO.insertDevice(member);
			}
		}
	}

    @Override
    public String selectBankName(String parent_bank_id) {
        return sysBankDAO.selectBankByCode(parent_bank_id+"0000");
    }

    @Override
    public BaseResult selectDrmemberSignDays(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrMember member = drMemberDAO.selectByPrimaryKey((Integer)param.get("uid"));
        Map<String,Object> res = new HashMap<>();
        Integer count=drMemberDAO.selectDrmemberSignDays(param);
        res.put("SignDays",count==null?0:count);
        res.put("LastTime",member.getLastSignInTime()==null?0:member.getLastSignInTime());
        br.setSuccess(true);
        br.setMap(res);
        return br;
    }

	@Override
	public String queryOpenId(Integer uid) {
		return drMemberDAO.queryOpenId(uid);
	}

	@Override
	public void insertopenId(BindWeixinBean wxBean) {
		drMemberDAO.insertopenId(wxBean);
	}

}
