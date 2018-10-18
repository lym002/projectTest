package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrLotteryParamDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberLotteryLogDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.model.activity.DrLotteryParam;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberLotteryLog;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.system.DrCompanyFundsLog;
import com.jsjf.service.member.DrMemberLotteryLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class DrMemberLotteryLogServiceImpl implements DrMemberLotteryLogService{

	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrMemberLotteryLogDAO drMemberLotteryLogDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrLotteryParamDAO drLotteryParamDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	
	
	@Override
	public Integer getDoubleAGGLotteryCountByUid(Map<String, Object> map) {
		return drMemberLotteryLogDAO.getDoubleAGGLotteryCountByUid(map);
	}
	@Override
	public List<DrMemberLotteryLog> getDoubleAGGListCountByUid() {
		return drMemberLotteryLogDAO.getDoubleAGGListCountByUid();
	}

	@Override
	public BaseResult insertLogtteryLogDoubleEgg(DrMember m)
			throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		if(Utils.parse(redisClientTemplate.getProperties("activityStartDate"), "yyyy-MM-dd HH:mm:ss").before(new Date())
				&& Utils.parse(redisClientTemplate.getProperties("activityEndDate"), "yyyy-MM-dd HH:mm:ss").after(new Date())){
			map.put("uid",m.getUid());
			map.put("giftId", -1);
			map.put("orders", " id asc ");
			boolean lockFlag = redisClientTemplate.tryLock("tearOpen."+m.getUid(), 3, TimeUnit.SECONDS,true);
			if(lockFlag){
				List<DrMemberLotteryLog> looteryList = drMemberLotteryLogDAO.selectListByParam(map);
				if(!Utils.isEmptyList(looteryList)){
					List<DrLotteryParam> paramList = drLotteryParamDAO.selectByAid(looteryList.get(0).getAid());
					double lotterFirst = 0;
					double lotterEnd = 0;
					double lotter = 0;
					lotter = Math.random();//随机数
					for(int i=0;i<paramList.size();i++){
						if(i!=0)
							lotterFirst += paramList.get(i-1).getProbability();//概率区间1
						lotterEnd += paramList.get(i).getProbability();//概率区间2
						if( lotter >lotterFirst && lotter <= lotterEnd){//概率判断
							looteryList.get(0).setGiftId(paramList.get(i).getId());	
							drMemberLotteryLogDAO.update(looteryList.get(0));
							map.clear();
							BigDecimal amount = new BigDecimal(paramList.get(i).getGiftName().toString());
							String prizeName = "三等奖";
							if(paramList.get(i).getGiftId()==1){
								prizeName = "一等奖";
							}else if(paramList.get(i).getGiftId()==2){
								prizeName = "二等奖";
							}
							
							map.put("pullCount", looteryList.size()-1);//剩余次数
							map.put("amount", paramList.get(i).getGiftName());//奖品金额
							map.put("prizeName", prizeName);//奖品金额
							//资金
							DrMemberFunds fund =  drMemberFundsDAO.queryDrMemberFundsByUid(m.getUid());
							fund.setBalance(fund.getBalance().add(amount));//余额
							fund.setSpreadProfit(fund.getSpreadProfit().add(amount));//其他收益
							drMemberFundsDAO.updateDrMemberFunds(fund);
							//资金记录recode
							DrMemberFundsRecord record = new DrMemberFundsRecord(null, looteryList.get(0).getInvestId(), m.getUid(), 4, 1, amount, fund.getBalance(), 3, "双旦活动:"+prizeName, null);
							drMemberFundsRecordDAO.insert(record);
							//资金记录log
							DrMemberFundsLog fundslog = new DrMemberFundsLog(m.getUid(), record.getId(), amount, 20, 1, "双旦活动:"+prizeName);
							drMemberFundsLogDAO.insertDrMemberFundsLog(fundslog);
							//公司资金交易记录表
							DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11, m.getUid(), null,amount,
									0, "双旦活动", 0);
							drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);	
							
							String msgContext = "尊敬的用户，恭喜您参与“双蛋嘉年华”活动，拆钱袋获得"+amount+"元现金，可在账户中心查看。";
							DrMemberMsg msg = new DrMemberMsg(m.getUid(), 0, 2, "双旦活动", new Date(), 0, 0, msgContext);
							drMemberMsgDAO.insertDrMemberMsg(msg);
							
							br.setMap(map);
							br.setSuccess(true);
							break;
						}
					}
				}else{
					br.setErrorCode("1003");
					br.setErrorMsg("没有抽奖次数");	
				}				
			}else{
				br.setErrorCode("1002");
				br.setErrorMsg("系统繁忙稍后重试");	
			}
		}else{
			br.setErrorCode("1001");
			br.setErrorMsg("活动已经结束");				
		}	
		return br;
	}
	@Override
	public String selectGiftName(int uid) {
		return drMemberLotteryLogDAO.selectGiftName(uid);
	}

	@Override
	public void insertLogtteryLog(DrMemberLotteryLog drMemberLotteryLog) {
		drMemberLotteryLogDAO.insert(drMemberLotteryLog);
	}
	
	@Override
	public boolean selectListByParam(Map<String, Object> map) {
		boolean result = false;
		List<DrMemberLotteryLog> list = drMemberLotteryLogDAO.selectListByParam(map);
		if(Utils.isObjectNotEmpty(list) && list.size()>0){
			result = true;
		}
		return result;
	}
	@Override
	public List<DrMemberLotteryLog> selectLotteryLogByAid() {
		return drMemberLotteryLogDAO.selectLotteryLogByAid();
	}
	@Override
	public List<DrMemberLotteryLog> selectRecords(Integer uid) {
		return drMemberLotteryLogDAO.selectRecords(uid);
	}
	@Override
	public Integer getDoubleAGGOneLottery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return drMemberLotteryLogDAO.getDoubleAGGOneLottery(map);
	}
}
