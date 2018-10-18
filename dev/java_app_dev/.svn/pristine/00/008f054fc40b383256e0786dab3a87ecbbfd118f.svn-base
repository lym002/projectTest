package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.MailUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.ThreadPool;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrCarryParamDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberCarryDAO;
import com.jsjf.dao.member.DrMemberCrushDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.system.DrCompanyFundsLog;
import com.jsjf.model.system.Mail;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;

@Service
@Transactional
public class DrMemberCarryServiceImpl implements DrMemberCarryService {
	@Autowired
	private DrMemberCarryDAO drMemberCarryDAO;
	@Autowired
	private DrCarryParamDAO drCarryParamDAO;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	public DrMemberBankDAO drMemberBankDAO;
	@Autowired
	public DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	public DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private DrMemberCrushDAO drMemberCrushDAO;
	
	ThreadPool t = ThreadPool.getThreadPool(20);
	@Override
	public Map<String, Object> insertDrMemberCarry(final DrMember member,DrMemberCarry drMemberCarry,DrMemberBank drMemberBank,DrMemberFunds drMemberFunds,DrCarryParam drCarryParam)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		drMemberCarry.setUid(member.getUid());
		drMemberCarry.setPaymentNum(createOrderNo(6,drMemberCarry.getUid())); 
		drMemberCarry.setAddTime(new Date()); 
		drMemberCarry.setBankId(drMemberBank.getId());
		drMemberCarry.setBankName(drMemberBank.getBankName());
		drMemberCarry.setBankNum(drMemberBank.getBankNum().substring(0,4)+"********"+drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
		drMemberCarry.setType(2);//金运通
		
		drMemberFunds.setBalance(Utils.nwdBcsub(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
		drMemberFunds.setFreeze(Utils.nwdBcadd(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
		drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
		
		if(drCarryParam.getAmount().compareTo(drMemberCarry.getAmount())>=0){
			drMemberCarry.setAuditTime(new Date());
			drMemberCarry.setStatus(5);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
			drMemberCarry.setReason("提现金额小于后台设置的最小提现金额！");
		}else{
			drMemberCarry.setStatus(0);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
		}
		//判断资金是否异常
		BigDecimal income  = drMemberFunds.getCrushCount().add(drMemberFunds.getInvestProfit().add(drMemberFunds.getSpreadProfit()));//收入
		BigDecimal expenditure = drMemberFunds.getBalance().add(drMemberFunds.getFreeze().add(drMemberFunds.getWprincipal().add(drMemberFunds.getCarryCount())));//支出
		//收入 - 支出 = 0
		if(income.subtract(expenditure).compareTo(BigDecimal.ZERO)!=0){
		        t.execute(new Runnable() {
					@Override
					public void run() {
						try {
							sendSmsAndMail(member.getUid());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			
			drMemberCarry.setStatus(0);
			drMemberCarry.setReason("资金账户出现异常!");
			flag = false;
		}
		
		drMemberCarryDAO.insertDrMemberCarry(drMemberCarry);
		DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCarry.getUid(), 2, 0,drMemberCarry.getAmount().add(drMemberCarry.getPoundage()), drMemberFunds.getBalance(),1,
				"提现金额：【"+ drMemberCarry.getAmount().setScale(2)  + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】", drMemberCarry.getPaymentNum());
		drMemberFundsRecordDAO.insert(record);
		DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),13,0,
				"提现金额冻结：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
		drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);
		
		String msg = redisClientTemplate.getProperties("carryApplyMsg").replace("${1}",drMemberCarry.getAmount().setScale(2).toString())
				.replace("${2}",drMemberBank.getBankName());
		String sms = redisClientTemplate.getProperties("carryApplySms").replace("${1}",member.getRealName())
				.replace("${2}", drMemberCarry.getAmount().setScale(2).toString());
		sendMsg(member,1,"提现申请",msg,3,sms,10);
		
		if(!flag) {
			return map;
		}
		
		map.put("drMemberCarry", drMemberCarry);
		map.put("record", record);
		map.put("drMemberFunds", drMemberFunds);
		return map;
	}
	
	@Override
	public BaseResult saveJYTpayment(DrMember member,Map<String, Object> map,DrMemberBank drMemberBank,DrCarryParam drCarryParam)throws Exception {
		Date confirmTime = new Date();
		BaseResult br = new BaseResult();
		DrMemberFunds drMemberFunds = (DrMemberFunds) map.get("drMemberFunds");
		DrMemberCarry drMemberCarry = (DrMemberCarry) map.get("drMemberCarry");
		DrMemberFundsRecord record = (DrMemberFundsRecord) map.get("record");
		
		if(drCarryParam.getAmount().compareTo(drMemberCarry.getAmount()) >= 0 && drMemberCarry.getStatus() == 5){
			JYTSendData sendData = new JYTSendData();
			sendData.setTran_code(MockClientMsgBase.PAY_TRAN_CODE);
			sendData.setBank_name(drMemberBank.getBankName());
			sendData.setAccount_no(drMemberBank.getBankNum());
			sendData.setAccount_name(member.getRealName());
			sendData.setAccount_type("00");
			sendData.setTran_amt(drMemberCarry.getAmount());
			sendData.setCurrency(MockClientMsgBase.CURRENCY);
			sendData.setBsn_code(MockClientMsgBase.PAY_BSN_CODE);
			sendData.setTran_flowid(drMemberCarry.getPaymentNum());
			sendData.setMobile(drMemberBank.getMobilePhone());
			
			JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
			
			if("S0000000".equals(resultData.getResp_code()) && "01".equals(resultData.getTran_state())){
				Date withdrawalsSuccessTime = new Date();
				drMemberCarry.setStatus(2);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
				record.setStatus(3);
				drMemberFundsRecordDAO.updateStatusByNo(record);
				//解冻会员资金
				drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				
				DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
						"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(log);
				
				drMemberFunds.setBalance(Utils.nwdBcsub(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				
				log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),5,0,
						"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(log);
				
				// 提现总额
				drMemberFunds.setCarryCount(drMemberFunds.getCarryCount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
				drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
				
				if(drMemberCarry.getPoundage().intValue() == 2){
					DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
					drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
				}
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("amount", drMemberCarry.getAmount());
				resultMap.put("withdrawalsSuccessTime", withdrawalsSuccessTime);
				br.setMap(resultMap);
	        	br.setSuccess(true);
			}else if("03".equals(resultData.getTran_state()) || (resultData.getResp_code().startsWith("E") && !"E0000000".equals(resultData.getResp_code()))){
				drMemberCarry.setStatus(3);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
				drMemberCarry.setReason(resultData.getResp_desc());
				
				//解冻会员资金
				drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);

				DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
						"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(log);
				
				record.setStatus(2);
				record.setBalance(drMemberFunds.getBalance());
				drMemberFundsRecordDAO.updateStatusByNo(record);
				
				String sms = redisClientTemplate.getProperties("carryFail")
						.replace("${1}", member.getRealName())
						.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
						.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
				SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 11, null, member.getMobilephone());
				
				sysMessageLogService.sendMsg(logs,1);
	        	br.setSuccess(false);
	        	br.setErrorCode("1005");
			}else{
				drMemberCarry.setStatus(1);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
	        	br.setSuccess(false);
	        	br.setErrorCode("1006");
			}
			drMemberCarryDAO.updateStatusById(drMemberCarry);
		}else{
        	br.setSuccess(false);
        	br.setErrorCode("1006");
		}
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("confirmTime", confirmTime);
		if(br.getMap() == null){
			br.setMap(map2);
		}else
		((Map<String,Object>)br.getMap()).putAll(map2);
		return br;
	}
	
    /**
     * 创建商户订单号
     * @param req
     * @return
     */
	private String createOrderNo(int length,int uid) {
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

	private void sendMsg(DrMember member,Integer type,String title,String msg,Integer msgType,String sms,Integer smsType) throws Exception {
		DrMemberMsg insertMemberMsg = new DrMemberMsg();
		insertMemberMsg.setRuId(member.getUid());
		insertMemberMsg.setPuId(0);
		insertMemberMsg.setType(msgType); 
		insertMemberMsg.setTitle(title); 
		insertMemberMsg.setContent(msg); // 消息内容',
		insertMemberMsg.setAddTime(new Date()); // 发送时间',
		insertMemberMsg.setIsRead(0); // 是否阅读0未读，1已读',
		insertMemberMsg.setStatus(0); // 状态，0正常，1删除',
		drMemberMsgDAO.insertDrMemberMsg(insertMemberMsg);

		if(1 == type){
			// 发送手机短信
			if (member.getMobilephone() != null && !member.getMobilephone().equals("")) {
				SysMessageLog logs = new SysMessageLog(member.getUid(), sms, smsType, null, member.getMobilephone());
				sysMessageLogService.sendMsg(logs,1);
			}
		}
	}

	@Override
	public Integer getDrCarryParamIsCharge(Integer uid, Integer free) {
		return drCarryParamDAO.getDrCarryParamIsCharge(uid, free);
	}

	@Override
	public Integer getDrCarryParamIsChargeNew(Integer uid, Integer free) {
		return drCarryParamDAO.getDrCarryParamIsChargeNew(uid, free);
	}
	
	@Override
	public DrCarryParam getDrCarryParam() {
		return drCarryParamDAO.getDrCarryParam();
	}

	//发送邮件和短信
	private Integer sendSmsAndMail(Integer uId) throws Exception{
		Integer result = 0;
		Set<String> emailSet = redisClientTemplate.getSet("emial.");
		Set<String> mobileSet = redisClientTemplate.getSet("mobile.");
		String content = "用户资金出现异常，提现失败！uid="+uId; 
		//发送短信
		for (String mobile : mobileSet) {
			SysMessageLog sysMessageLog = new SysMessageLog(uId, content, 6, new Date(), mobile);
			result = SmsSendUtil.sendMsg(sysMessageLog.getPhone(), sysMessageLog.getMessage(), 0);
			sysMessageLog.setResults(result);
			sysMessageLogService.insert(sysMessageLog);
		}
		// 发送邮件
		Mail mail = new Mail();
		mail.setMessage("用户资金出现异常，提现失败！uid="+uId);
		mail.setSubject("提现异常");
		MailUtil.sendMails(mail, emailSet);
		return result;
	}
	@Override
	public boolean checkDrMemberCarryAmount(Integer uid,BigDecimal amount) {
		boolean result = false;
		int investCount = drProductInvestDAO.selectProductInvestCountByUid(uid);
		Integer type = 1;//1=存管，2=平台
		BigDecimal tixian = drMemberCarryDAO.selectAmountByUid(uid);
		BigDecimal chongzhi = drMemberCrushDAO.getDrMemberCrushAmountByUID(uid,type);
		if(investCount ==  0 && (amount.add(tixian).compareTo(chongzhi)==1)){ //提现大于充值并且未投资除体验标之外的产品
			result = false;
		}else{
			result = true;
		}
		return result;
	}
	@Override
	public Map<String, Object> insertFuiouDrMemberCarry(final DrMember member,DrMemberCarry drMemberCarry,DrMemberBank drMemberBank,DrMemberFunds drMemberFunds,DrCarryParam drCarryParam)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		drMemberCarry.setUid(member.getUid());
		drMemberCarry.setPaymentNum(createOrderNo(6,drMemberCarry.getUid())); 
		drMemberCarry.setAddTime(new Date()); 
		drMemberCarry.setBankId(drMemberBank.getId());
		drMemberCarry.setBankName(drMemberBank.getBankName());
		drMemberCarry.setBankNum(drMemberBank.getBankNum().substring(0,4)+"********"+drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
		
		/*drMemberFunds.setFuiou_balance(Utils.nwdBcsub(drMemberFunds.getFuiou_balance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
		drMemberFunds.setFuiou_freeze(Utils.nwdBcadd(drMemberFunds.getFuiou_freeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));*/
		drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
		
		/*if(drCarryParam.getAmount().compareTo(drMemberCarry.getAmount())>=0){
			drMemberCarry.setAuditTime(new Date());
			drMemberCarry.setStatus(5);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
			drMemberCarry.setReason("提现金额小于后台设置的最小提现金额！");
		}else{*/
			drMemberCarry.setStatus(0);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
			drMemberCarry.setType(3);//存管
		/*}*/
		//判断资金是否异常
		BigDecimal income  = drMemberFunds.getFuiou_crushcount().add(drMemberFunds.getFuiou_investProfit().add(drMemberFunds.getFuiou_spreadProfit()));//收入
		BigDecimal expenditure = drMemberFunds.getFuiou_balance().add(drMemberFunds.getFuiou_freeze().add(drMemberFunds.getFuiou_wprincipal().add(drMemberFunds.getFuiou_carrycount())));//支出
		//收入 - 支出 = 0
		if(income.subtract(expenditure).compareTo(BigDecimal.ZERO)!=0){
			//TODO 需要增加线程池
			 ThreadPool t = ThreadPool.getThreadPool(20);  
		        t.execute(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							sendSmsAndMail(member.getUid());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			drMemberCarry.setStatus(0);
			drMemberCarry.setReason("资金账户出现异常!");
			flag = false;
		}
		
		drMemberCarryDAO.insertDrMemberCarry(drMemberCarry);
		
		/*DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCarry.getUid(), 2, 0,drMemberCarry.getAmount().add(drMemberCarry.getPoundage()), drMemberFunds.getFuiou_balance(),1,
				"提现金额：【"+ drMemberCarry.getAmount().setScale(2)  + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】", drMemberCarry.getPaymentNum());
		drMemberFundsRecordDAO.insert(record);
		DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),13,0,
				"提现金额冻结：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
		drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);*/
		
		String msg = redisClientTemplate.getProperties("carryApplyMsg").replace("${1}",drMemberCarry.getAmount().setScale(2).toString())
				.replace("${2}",drMemberBank.getBankName());
		String sms = redisClientTemplate.getProperties("carryApplySms").replace("${1}",member.getRealName())
				.replace("${2}", drMemberCarry.getAmount().setScale(2).toString());
		/*sendMsg(member,1,"提现申请",msg,3,sms,10);*/
		
		if(!flag){
			return map;
		}

		map.put("drMemberCarry", drMemberCarry);
		map.put("drMemberFunds", drMemberFunds);
		return map;
	}
}
