package com.jsjf.service.member.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFourElementsLogDAO;
import com.jsjf.dao.system.SysBankDAO;
import com.jsjf.dao.system.SysFuiouNoticeLogDAO;
import com.jsjf.dao.system.SysUsersVoDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.jzh.data.WebRspData;
import com.jzh.service.JZHService;

@Service
@Transactional
public class DrMemberServiceImpl implements DrMemberService {
	private static Logger logger = Logger.getLogger(DrMemberServiceImpl.class);

	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private DrMemberBaseInfoDAO drMemberBaseInfoDAO;
	@Autowired
	private SysUsersVoDAO sysUsersVoDAO;
	@Autowired 
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberBankDAO drMemberBankDAO;
	@Autowired
	private SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
	@Autowired
	private DrMemberFourElementsLogDAO drMemberFourElementsLogDAO;
	@Autowired
	private SysBankDAO sysBankDAO;
	
	
	@Override
	public PageInfo selectDrMemberList(DrMember member, PageInfo pi,boolean flag) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobilephone", member.getMobilephone());
		map.put("recommCodes", member.getRecommCodes());
		if(flag){
			map.put("realName", member.getRealName());
			map.put("recommCodes", member.getRecommCodes());
			map.put("referrer", member.getReferrer());//推荐人
			map.put("idcards", member.getIdCards());//身份证号码
			map.put("updateStatus", member.getUpdateStatus());//身份证号码

			map.put("offset", pi.getPageInfo().getOffset());
			map.put("limit", pi.getPageInfo().getLimit());
			List<DrMember> mList = drMemberDAO.selectDrMemberList(map);
			Integer total = drMemberDAO.selectDrMemberListCount(map);
			pi.setTotal(total);
			pi.setRows(mList);
		}else{
			List<DrMember> mList = drMemberDAO.queryDrMemberByMobilePhone(map);
			pi.setTotal(0);
			pi.setRows(mList);	
		}
		return pi;
	}

	@Override
	public DrMember queryDrMemberByUid(Integer uid) {
		return drMemberDAO.selectByPrimaryKey(uid);
	}

	@Override
	public DrMember queryReferrerMemberByUid(Integer uid) {
		return drMemberDAO.selectOnlyOneMember(uid);
	}

	@Override
	public PageInfo selectRegMemberInfoByParam(
			Map<String, Object> map, PageInfo pi) {
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<Map<String, Object>> rows = drMemberDAO.selectRegMemberInfoListByParam(map);
		Integer total = drMemberDAO.selectRegMemberInfoListCountByParam(map);
		pi.setRows(rows);
		pi.setTotal(total);
		return pi;
	}

	@Override
	public DrMemberBaseInfo selectByParam(Map<String, Object> param) {
		return drMemberBaseInfoDAO.selectByParam(param);
	}

	@Override
	public List<DrMemberBaseInfo> selectByCard(Map<String, Object> map) {
		return drMemberBaseInfoDAO.selectByCard(map);
	}

	@Override
	public List<Map<String, Object>> queryMember(Map<String, Object> param) {
		return drMemberDAO.queryMember(param);
	}

	@Override
	public int queryMemberCount(Map<String, Object> param) {
		return drMemberDAO.queryMemberCount(param);
	}

	@Override
	public List<Map<String, Object>> selCustomerManagement(
			Map<String, Object> param) {
		return drMemberDAO.selCustomerManagement(param);
	}

	@Override
	public int selCustomerManagementCount(Map<String, Object> param) {
		return drMemberDAO.selCustomerManagementCount(param);
	}

	@Override
	public Map<String, Object> selectMemberInfoDataSum(
			Map<String, Object> para) {
		return drMemberDAO.selectMemberInfoDataSum(para);
	}

	@Override
	public PageInfo selectGiveRegMemberInfoByParam(Map<String, Object> map, PageInfo pi) {
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<Map<String, Object>> rows = drMemberDAO.selectGiveRegMemberInfoListByParam(map);
		Integer total = drMemberDAO.selectGiveRegMemberInfoListCountByParam(map);
		pi.setRows(rows);
		pi.setTotal(total);
		return pi;
	}
	@Override
	public DrMember selectByPrimaryKey(Integer uid) {
		return drMemberDAO.selectByPrimaryKey(uid);
	}

	@Override
	public void SMSMarketNoTCertified(String key) {
		try {
			String content = PropertyUtil.getProperties(key);
			if(Utils.isObjectNotEmpty(content)){
				String[] array = drMemberDAO.selectNotCertified();//注册第二天未绑卡的
				SmsSendUtil.batchSMSMarketing(array, content);
				logger.info("营销短信-注册第二天未绑卡:"+array.length+"条,发送完成");
//				logger.info("手机号:"+Arrays.toString(array));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("营销短信-注册第二天未绑卡:发送失败",e);
		}
	}

	@Override
	public void allotCustomer() {
		List<Map<String,Object>> list = drMemberDAO.selectdrMemberGroupByChannle();
		String channel = "";
		int dept1 = 0;
		int dept2 = 0;
		boolean flag = false;
		List<Object> dL1 = new ArrayList<Object>();
		//List<Object> dL2 = new ArrayList<Object>();
		List<Object> tempList = null;
		List<Object> cacheList = new ArrayList<Object>();
		
		if(!Utils.isEmptyList(list)){
			Map<String,Object> salesMap = sysUsersVoDAO.selectDeptUserCount();
			dept1=Integer.valueOf(salesMap.get("dept1").toString());
			dept2=Integer.valueOf(salesMap.get("dept2").toString());
						
			for(int i=0;i<list.size();i++){
				//把渠道一样的用户分到一起
				if(channel.equals(list.get(i).get("code"))){
					cacheList.add(list.get(i).get("uid"));
					if(i==list.size()-1){
						tempList = cacheList;
					}
				}else{
					channel = list.get(i).get("code").toString();
					
					tempList = cacheList;
					cacheList = new ArrayList<Object>();
					cacheList.add(list.get(i).get("uid"));				
					if(i==list.size()-1){
						tempList.add(list.get(i).get("uid"));
					}
					if(i==list.size()-1){
						tempList.add(list.get(i).get("uid"));
					}
				}
				
				//分配 客户
				if(!Utils.isEmptyList(tempList)){
					int ds1 = (int)((double)dept1/(dept1+dept2)*tempList.size());
					//int ds2 = (int)((double)dept2/(dept1+dept2)*tempList.size() + ds1);
					for(int j=0;j<tempList.size();j++){
						/*if(j<ds1){
							dL1.add(tempList.get(j));
						}else if(j<ds2){
							dL2.add(tempList.get(j));
						}else if(flag){//除不完,先给 部门2,然后交替分配
							dL2.add(tempList.get(j));
							flag = false;
						}else{
							dL1.add(tempList.get(j));
							flag = true;
						}*/
						dL1.add(tempList.get(j));
					}								
					tempList = null;//清空中间
				}
			}
			Map<String,Object> param = new HashMap<String, Object>();
			if(!Utils.isEmptyList(dL1)){
				param.put("allot", 1);
				param.put("uids", dL1);
				drMemberDAO.updateMemberAllot(param);
			}
			/*if(!Utils.isEmptyList(dL2)){
				param.put("allot", 2);
				param.put("uids", dL2);
				drMemberDAO.updateMemberAllot(param);
			}*/
			//logger.info("电销分配完成,电销一部:"+dL1.size()+"人,电销二部:"+dL2.size()+"人");
			logger.info("电销分配完成,电销一部:"+dL1.size()+"人");
		}
		
	}
	
	public PageInfo selectdrMemberByMobilePhone(DrMember drMember,PageInfo pi){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobilePhone", drMember.getMobilephone());
		map.put("recommCodes", drMember.getRecommCodes());
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrMember> mList = drMemberDAO.selectdrMemberByMobile(map);
		Integer total = drMemberDAO.selectdrMemberByMobilePhoneCount(map);
		pi.setTotal(total);
		pi.setRows(mList);
		return pi;
	}

	@Override
	public void updateMemberAllot(Map<String, Object> map) {
		if(Utils.isObjectNotEmpty(map.get("uids"))){
			drMemberDAO.updateMemberAllot(map);	
		}
	}

	@Override
	public void customerLossPrevention() {
		//发送对象：投资≥2次的投资用户（新手标、体验标除外）
		String couponName = "加息专享";
		try {
			Integer sign = 1;
			Date  time = Utils.parse("2017-02-23 23:59:59", "yyyy-MM-dd HH:mm:ss");
			if(new Date().after(time)){
				sign = 0;
			}
			//1.未登录4天
			String notLogin4 = PropertyUtil.getProperties("notLogin4");
			try {
				if(Utils.isObjectNotEmpty(notLogin4)){
					String[] notloginList = drMemberDAO.gt4DayNotlogged(sign);
					List<String> nlist = new ArrayList<String>();
					//过滤
					for(String s:notloginList){
						long l1 =redisClientTemplate.sadd("notLogin4_1", s);//l1=0说明已经发过一次了
						if(l1>0){
							nlist.add(s);
						}else{
							long l2 =redisClientTemplate.sadd("notLogin4_2", s);//l2=0说明已经发过两次了
							if(l2>0){
								nlist.add(s);
							}
						}
					}					
					SmsSendUtil.batchSMSMarketing(nlist.toArray(new String[]{}), notLogin4);
					logger.info("未登录4天防流失营销短信发送成功:"+nlist.size()+"人");
//					logger.info("4天的发短信:"+StringUtils.join(nlist.toArray(),","));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("未登录4天防流失营销短信发送失败",e);
			}
			//2.未登录天数≥7天的用户
			String notLogin7 = PropertyUtil.getProperties("notLogin7");
			try {
				if(Utils.isObjectNotEmpty(notLogin7)){
					List<Map<String, Object>> notloginList = drMemberDAO.gt7DayNotlogged(sign);
					if(!Utils.isEmptyList(notloginList)){
						List<String> yList = new ArrayList<String>();//有可用券
						List<String> nList = new ArrayList<String>();//无可用券						
						List<Object> uids = new ArrayList<Object>(); 
						for(Map<String,Object> map:notloginList){
							if("1".equals(map.get("coupon").toString())){//有券的直接发短信
								//过滤
								long l1 =redisClientTemplate.sadd("notLogin4_1", map.get("mobilePhone").toString());//l1=0说明已经发过一次了
								if(l1>0){
									yList.add(map.get("mobilePhone").toString());
								}else{
									long l2 =redisClientTemplate.sadd("notLogin4_2", map.get("mobilePhone").toString());//l2=0说明已经发过两次了
									if(l2>0){
										yList.add(map.get("mobilePhone").toString());
									}
								}
							}else{//没券的发券
								//过滤
								long l =redisClientTemplate.sadd("notLogin7", map.get("mobilePhone").toString());//l=0说明已经发过一次了
								if(l>0){
									nList.add(map.get("mobilePhone").toString());
									uids.add(map.get("uid"));
								}
							}
						}
						if(yList.size()>0){//发短信,这里要发未登录4天的短信
							SmsSendUtil.batchSMSMarketing(yList.toArray(new String[]{}), notLogin4);
//							logger.info("7天已发过券的发出短信:"+StringUtils.join(yList.toArray(),","));
						}
						if(nList.size()>0){
							//发券
							Map<String,Object> para = new HashMap<String, Object>();
							para.put("name", couponName);
							para.put("type", 2);
							para.put("raisedRates", 1);
							para.put("orders", " amount,raisedRates,multiple ");
							para.put("offset", 0);
							para.put("limit", 1);
							List<DrActivityParameter> list = drActivityParameterDAO.selectDrActivityParameterByParam(para);
							if(list.size()>0){
								String text = "";
								if(list.get(0).getType() == 1){
									text = list.get(0).getAmount()+"元红包";
								}else if(list.get(0).getType() == 2){
									text = list.get(0).getRaisedRates().setScale(2)+"%加息券";
								}else if(list.get(0).getType() == 3){
									text = list.get(0).getAmount()+"元 体验金";
								}else if(list.get(0).getType() == 4){
									text = list.get(0).getMultiple()+"翻倍券";
								}
								
								para.clear();
								para.put("activityId", list.get(0).getId());
								para.put("uids", uids);
								drActivityParameterDAO.insertCouponByParam(para);//发券
								//发短信
								SmsSendUtil.batchSMSMarketing(nList.toArray(new String[]{}), notLogin7.replace("${name}", text));
							}
							logger.info("未登录7天防流失营销短信发送成功:已有券 "+yList.size()+"人,发出券 "+uids.size()+"人");
//							logger.info("发出券:"+StringUtils.join(nList.toArray(),","));
						}
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("未登录7天防流失营销短信发送失败",e);
			}
			
			//3.赠送加息券后第4天未使用
			String couponGived4 = PropertyUtil.getProperties("couponGived4");
			try {
				if(Utils.isObjectNotEmpty(couponGived4)){
					String[] list = drMemberDAO.couponGived4NotUse(couponName);
					SmsSendUtil.batchSMSMarketing(list, couponGived4);
					logger.info("赠送加息券4天防流失营销短信发送成功:"+list.length+"人");
//					logger.info("赠送加息券4天:"+StringUtils.join(list,","));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("赠送加息券4天防流失营销短信发送失败",e);
			}
						
		} catch (Exception e) {					
			e.printStackTrace();
		}
		
	}


	
	
	
	@Override
	public DrMember selectReferrerMemberByMobilePhone(Map<String, Object> map) {
		return drMemberDAO.selectReferrerMemberByMobilePhone(map);
	}

	@Override
	public void updateDrmember(DrMember drMember) {
		drMemberDAO.updateDrMemberByUid(drMember);
	}
	
	public DrMember selectDrMemberByMobilePhone(String mobilePhone){
		return drMemberDAO.selectDrMemberByMobilePhone(mobilePhone);
	}

	@Override
	public void openAccountRes(net.sf.json.JSONObject json) throws Exception {
		
		net.sf.json.JSONObject message = json.getJSONObject("message");
			
			if(JZHService.verifySignAsynNotice(new WebRspData(message), json.getString("signature"))){
				logger.info("["+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]开户验签成功---------------------");
				boolean lockFlag = false;
				String  openAccountRes= "openAccountRes_"+(String)message.get("mchnt_txn_ssn");
				try {
					if("0000".equals((String)message.get("resp_code"))){
						lockFlag = redisClientTemplate.tryLock(openAccountRes, 3, TimeUnit.SECONDS,false);
						if(lockFlag){
							DrMember m = drMemberDAO.selectDrMemberByMobilePhone((String)message.get("mobile_no"));
							if(Utils.isObjectNotEmpty(m) && m.getIsFuiou() == 0){
								//用户表
								m.setIsFuiou(1);
								m.setMchnt_txn_ssn((String)message.get("mchnt_txn_ssn"));
//							m.setFuiou_acnt((String)message.get("fuiou_acnt"));
//							m.setUser_id((String)message.get("user_id"));
//							m.setAuth_st((String)message.get("auth_st"));
//							m.setRealVerify(1);
								drMemberDAO.updateDrMemberByUid(m);
								//银行表
								DrMemberBank bank = new DrMemberBank();
								bank.setAddTime(new Date());
								bank.setAddUser(0);
								String bankname  = (String)message.get("bank_nm");
								if(bankname !=null){
									int index = bankname.indexOf("银行");	
									bankname = index==0?bankname:bankname.substring(0, index+2);
									bankname = bankname.contains("浦东发展")?"浦发银行":bankname;
								}
								bank.setBankName(bankname);//浦东发展
								bank.setBankNum((String)message.get("capAcntNo"));
								bank.setStatus(1);
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
									
									
									drMemberBaseInfoDAO.updateByUidSelective(info);
								}
							}
						}
					}		
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(lockFlag){
						redisClientTemplate.del(openAccountRes);
					}
				}
			}else{
				logger.error("["+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]开户验签失败---------------------");
			}
		
	}

	@Override
	public List<Map<String, Object>> selectPersonRegBatchUpload() {
		List<Map<String, Object>> customers=drMemberBaseInfoDAO.selectPersonRegBatchUpload();
		return customers;
	}

	@Override
	public void updateFileStatus(List<DrMember> param) {
		drMemberDAO.updateFileStatus(param);
	}

	@Override
	public BaseResult selectFuiouByPrimaryKey(DrMember member) throws Exception {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", member.getMobilephone());
		map.put("resp_code", "0000");
		map.put("icds", new String[]{FuiouConfig.REG});
		List<SysFuiouNoticeLog> list = sysFuiouNoticeLogDAO.selectFuiouNoticeLogByMap(map);
		if(!Utils.isEmptyList(list)){
			Map<String,String> param = new HashMap<String, String>();
			
			JSONObject json = JSONObject.fromObject(list.get(0).getReq_message());
			JSONObject message = json.getJSONObject("message");
			
			param.put("mchnt_txn_ssn", Utils.createOrderNo(6, member.getUid(), null));
			param.put("mchnt_txn_dt", Utils.format(new Date(), "yyyyMMdd"));//商户查询当前日期yyyyMMdd
//				param.put("user_bankCards", (String)message.get("capAcntNo"));//待查询的登录银行卡列表-M
//				param.put("user_idNos", (String)message.get("certif_id"));//待查询查询注册的身份证-M
			param.put("user_ids", (String)message.get("mobile_no"));//查询注册的手机号
			
			result = FuiouConfig.queryUserInfs_v2(param);
			
			if(result.isSuccess()){
				JSONObject jsonResult = JSONObject.fromObject(result.getMap());
				JSONObject JSONObject = jsonResult.optJSONObject("results");
				
				JSONObject messageResult =  JSONObject.optJSONObject("result");
				if(Utils.isObjectNotEmpty(messageResult)){
					DrMember m = drMemberDAO.selectDrMemberByMobilePhone((String)message.get("mobile_no"));
					if(m.getIsFuiou() ==0){
						
						//用户表
						m.setIsFuiou(1);
						m.setMchnt_txn_ssn(message.optString("mchnt_txn_ssn"));
						drMemberDAO.updateDrMemberByUid(m);
						//银行表
						DrMemberBank bank = new DrMemberBank();
						bank.setAddTime(new Date());
						bank.setAddUser(0);
						
						String parent_bank_id  = messageResult.optString("parent_bank_id");
						
						String bankname = sysBankDAO.selectBankByCode(parent_bank_id+"0000");
						bank.setBankName(bankname);
						bank.setBankNum(messageResult.optString("capAcntNo"));
						bank.setStatus(1);
						bank.setType(3);
						bank.setUid(m.getUid());				
						
						drMemberBankDAO.insertDrMemberBank(bank);
						
						//基础表
						DrMemberBaseInfo info = drMemberBaseInfoDAO.queryMemberBaseInfoByUid(m.getUid());				
						if(Utils.isObjectNotEmpty(info) && (Utils.isObjectEmpty(info.getRealName())|| Utils.isObjectEmpty(info.getIdCards()))){
							info.setRealName(messageResult.optString("cust_nm"));
							info.setIdCards(messageResult.optString("certif_id"));
							
							// 性别和生日
							Integer sexNum = Integer.parseInt(info.getIdCards().substring(16, 17));
							String birthday = info.getIdCards().substring(6, 14);
							info.setSex(sexNum % 2 != 0 ? 1 : 2);
							info.setBirthDate(new SimpleDateFormat("yyyyMMdd").parse(birthday));
							
							
							drMemberBaseInfoDAO.updateByUidSelective(info);
						}
					}
					result.setSuccess(true);
					result.setMsg("成功");
				}else{
					result.setSuccess(false);
					result.setErrorMsg("定单不存在");
				}
			}else{
				result.setSuccess(false);
				result.setErrorMsg("定单不存在");
			}
		}else{
			result.setErrorMsg("该用户没有开户操作");
		}
		return result;
	}

	@Override
	public void queryChangeCard(SysFuiouNoticeLog fuiouNoticeLog) {		
		Map<String, Object> map=new HashMap<>();
		map.put("login_id", fuiouNoticeLog.getUser_id());
		map.put("txn_ssn", fuiouNoticeLog.getMchnt_txn_ssn());
		map.put("mchnt_txn_ssn", Utils.createOrderNo(6, 1, null));
		BaseResult br=FuiouConfig.queryChangeCard(map);
		if(br.isSuccess()){
			if(br.getMap().get("examine_st").equals("1")){//审核成功
				fuiouNoticeLog.setUpdate_status(3);//审核成功
				sysFuiouNoticeLogDAO.update(fuiouNoticeLog);
				Map<String, Object>param=new HashMap<>();
				
				param.put("mobilephone", fuiouNoticeLog.getUser_id());
				List<DrMember> list=drMemberDAO.queryDrMemberByMobilePhone(param);
				
				param.clear();
				param.put("uid", list.get(0).getUid());
				param.put("reason", fuiouNoticeLog.getRemark());
				drMemberFourElementsLogDAO.updateFourelementsLog(param);
				
				param.clear();
				param.put("uid", list.get(0).getUid());
				param.put("realName", list.get(0).getRealName());
				param.put("idCards", list.get(0).getIdCards());
				param.put("bankNum", fuiouNoticeLog.getCard_no());
				param.put("bankName", fuiouNoticeLog.getBank());
				param.put("mobilePhone", fuiouNoticeLog.getMobile());
				drMemberFourElementsLogDAO.insertFourelementsLog(param);
				
				param.clear();
				param.put("uid", list.get(0).getUid());
				drMemberBankDAO.updateMemberBank(param);
				
				param.clear();
				param.put("uid", list.get(0).getUid());
				param.put("bankNum", fuiouNoticeLog.getCard_no());
				param.put("bankName", fuiouNoticeLog.getBank());
				param.put("mobilePhone", fuiouNoticeLog.getMobile());
				drMemberBankDAO.insertMemberBank(param);	
			}else if(br.getMap().get("examine_st").equals("2")){//审核失败
				fuiouNoticeLog.setUpdate_status(4);//审核失败
				String remark=fuiouNoticeLog.getRemark()+" 审核失败原因:"+br.getMap().get("remark");
				fuiouNoticeLog.setRemark(remark);
				sysFuiouNoticeLogDAO.update(fuiouNoticeLog);
			}
		}
	}

	@Override
	public DrMember selectByMobilephone(DrMember drMember) {
		return drMemberDAO.selectByMobilephone(drMember);
	}
	@Override
	public void update(DrMember DrMember) {
		drMemberDAO.updateByPrimaryKey(DrMember);
	}

	@Override
	public String queryOpenId(Integer uid) {
		return drMemberDAO.queryOpenId(uid);
	}

}
