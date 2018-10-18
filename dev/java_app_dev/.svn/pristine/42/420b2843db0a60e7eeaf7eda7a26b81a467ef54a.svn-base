package com.jsjf.service.product.impl;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.*;
import com.jsjf.dao.member.DrMemberCarryDAO;
import com.jsjf.dao.member.DrMemberCrushDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrGiftCardSetUp;
import com.jsjf.model.activity.DrGiftCardSetUpDetail;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class DrProductInvestServiceImpl implements DrProductInvestService {
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private DrGiftCardSetUpDAO drGiftCardSetUpDAO;
	@Autowired
	private DrGiftCardSetUpDetailDAO drGiftCardSetUpDetailDAO;
	@Autowired
	private DrMemberCarryDAO drMemberCarryDAO;
	@Autowired
	private DrMemberCrushDAO drMemberCrushDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    public DrActivityParameterDAO drActivityParameterDAO;
    @Autowired
    private BypCommodityDetailDAO bypCommodityDetailDAO;
	@Override
	public PageInfo selectInvestLogByParam(Map<String, Object> map,PageInfo pi) {
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrProductInvest> list = drProductInvestDAO.selectInvestLogByParam(map);
		Integer total = drProductInvestDAO.selectInvestLogCountByParam(map);
		/**
		 * TODO
		 * 添加E签宝业务逻辑start
		 */
		for (DrProductInvest dr : list) {
			String url = ConfigUtil.getEsignUrl();
			String projectId=ConfigUtil.getProjectId();
			long timestamp=System.currentTimeMillis();
			if (Utils.isObjectNotEmpty(dr.getEvid())) {
				String signature = Utils.getXtimevaleSignature("id="+dr.getEvid()+"&projectId="+projectId+"&timestamp="+timestamp+"&reverse=false&type=ID_CARD&number="+dr.getIdCards(),
						ConfigUtil.getProjectSecret(), ConfigUtil.getAlgorithm(), ConfigUtil.getEncoding());
				//System.out.println(url+"?id="+evid+"&projectId="+projectId+"&timestamp="+timestamp+"&signature="+signature);
				dr.setEvid(url+"?id="+dr.getEvid()+"&projectId="+projectId+"&timestamp="+timestamp+"&reverse=false&type=ID_CARD&number="+dr.getIdCards()+"&signature="+signature);
				dr.setIdCards(dr.getIdCards().substring(0, 4).concat("**************"));
			}else {
				dr.setEvid("");
			}
		}
		pi.setTotal(total);
		pi.setRows(list);
		return pi;
	}

	@Override
	public Integer selectInvestNumsByPid(Integer pid) {
		return drProductInvestDAO.selectInvestNumsByPid(pid);
	}

	@Override
	public BigDecimal selectUserSumPrincipalByStatus(Map<String, Object> map) {
		return drProductInvestDAO.selectUserSumPrincipalByStatus(map);
	}

	@Override
	public BigDecimal selectUserSumInterestByStatus(Map<String, Object> map) {
		return drProductInvestDAO.selectUserSumInterestByStatus(map);
	}

	@Override
	public Map<String, Object> selectUserLastInvestmentInfo(Integer uid) {
		return drProductInvestDAO.selectUserLastInvestmentInfo(uid);
	}
	
	@Override
	public void sendTicket(DrMember member, BigDecimal amount) throws Exception {
		if (amount.compareTo(new BigDecimal(2000)) >= 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.clear();
			map.put("uid", member.getUid());
			map.put("deadline", 15);
			map.put("amount", 2000);
			List<DrProductInvest> investList = drProductInvestDAO.selectInvestList(map);
			if (investList.size() == 1) {
				map.clear();
				map.put("toFrom", member.getToFrom());
				map.put("now", investList.get(0).getInvestTime());
				List<DrGiftCardSetUp> setUplist = drGiftCardSetUpDAO.getEfficaciousDrGiftCardSetUpList(map);
				if (!Utils.isEmptyList(setUplist)) {
					for (DrGiftCardSetUp setup : setUplist) {
						map.clear();
						map.put("parentId", setup.getId());
						map.put("limit", setup.getOnceQty());
						DrGiftCardSetUpDetail giftCardDetail = drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailBySetUpId(map);
						if (Utils.isObjectNotEmpty(giftCardDetail)) {
							String code = giftCardDetail.getGiftCard();
							map.clear();
							map.put("ids", giftCardDetail.getIds());
							map.put("uid", member.getUid());
							map.put("investId", investList.get(0).getId());
							drGiftCardSetUpDetailDAO.updateGiftCardSetUpDetailMap(map);
							DrMemberMsg msg = new DrMemberMsg(member.getUid(),0, 2, "0元看电影", new Date(), 0, 0,
									redisClientTemplate.getProperties("ticketMsg").replace("${1}", code));
							drMemberMsgDAO.insertDrMemberMsg(msg);
						}
					}

				}
			}
		}
	}
	
	@Override
	public List<DrProductInfo> selectInvest() {
		return drProductInvestDAO.selectInvest();
	}

	@Override
	public DrProductInvest selectByPrimaryKey(Map<String,Object> map) {
		return drProductInvestDAO.selectByPrimaryKey(map);
	}

	@Override
	public Integer selectInvestLogCountByParam(Map<String, Object> param) {
		Integer rows = drProductInvestDAO.selectInvestLogCountByParam(param);
		if(rows == null){
			rows = 0;
		}
		return rows;
	}
	@Override
	public List<DrProductInvest> selectInvestByMap(Map<String, Object> param) {
		
		return drProductInvestDAO.selectInvestByMap(param);
	}

	@Override
	public BigDecimal selectExperienceByStatus(Integer uid, Integer type,
			Integer status) {
		return drProductInvestDAO.selectExperienceByStatus(uid, type, status);
	}

	@Override
	public boolean checkProductType(Map<String, Object> param) {
			List<DrProductInvest> list = drProductInvestDAO.checkProductType(param);
			if(list.size()>0){
				return true;
			}else{
				return false;
			}
	}

	@Override
	public int selectIsOldUserById(Integer uid) {
		return drProductInvestDAO.selectIsOldUserById(uid);
	}

	@Override
	public int selectExperInvestNumsByPid(Integer pid) {
		return drProductInvestDAO.selectExperInvestNumsByPid(pid);
	}

	@Override
	public List<DrProductInvest> selectSimpleInvestLog(int pid) {
		
		return drProductInvestDAO.selectSimpleInvestLog(pid);
	}

	@Override
	public Boolean selectInvestCount(Integer uid,BigDecimal amount,Integer type) {
		boolean result = false; 
		Integer investCount = drProductInvestDAO.selectInvestCount(uid);
		//type = 1 存管，type = 2平台
		BigDecimal carryAmountCount = drMemberCarryDAO.selectCarryAmountCount(uid,type);
		BigDecimal crushAmountCount = drMemberCrushDAO.getDrMemberCrushAmountByUID(uid,type);
		if(investCount ==  0 && (amount.add(carryAmountCount).compareTo(crushAmountCount)==1)){ //提现大于充值并且未投资除体验标之外的产品
			result = false;
		}else{
			result = true;
		}
		return result;
	}

	@Override
	public BaseResult selectNoviceListAndCount() {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("type", 3);
		map.put("newHand", drProductInfoDAO.selectNewHandInfo(param));
		map.put("noviceCount", drProductInvestDAO.selectNoviceCount());
		map.put("noviceList", drProductInvestDAO.selectNoviceList());
		br.setMap(map);
		return br;
	}

	@Override
	public BigDecimal selectAccumulatedIncomeSumByUid(Map<String, Object> map) {
		BigDecimal AccumulatedIncome = drMemberFundsRecordDAO.selectAccumulatedIncomeSumByUid(map);
		return AccumulatedIncome;
	}

	@Override
	public List<Map<String, Object>> selectprizeInfoByInvestId(Map<String,Object> param) {
		List<Map<String,Object>> map = drProductInvestDAO.selectprizeInfoByInvestId(param);
		return map;
	}

	@Override
	public List<DrProductInvest> selectInvestSendDrProductInfoByUid(Integer uid) {
		return drProductInvestDAO.selectInvestSendDrProductInfoByUid(uid);
	}

	@Override
	public List<DrProductInvest> investSendList(Map<String, Object> param) {
		return drProductInvestDAO.checkProductType(param);
	}

	@Override
	public Map<String,Object> selectIsPerfectByInvestId(Integer investId) {
		return drProductInvestDAO.selectIsPerfectByInvestId(investId);
	}

	@Override
	public List<DrProductInvest> selectInvestSendNotAddress(Integer uid) {
		return drProductInvestDAO.selectInvestSendNotAddress(uid);
	}
	@Override
	public Integer selectInvestCountByMap(Map<String, Object> map) {
		return drProductInvestDAO.selectInvestCountByMap(map);
	}
	@Override
	public BaseResult getActivityMay18d(DrMember member) throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		
		String startDate  = redisClientTemplate.getProperties("activityMay18d_start");
		String endDate  = redisClientTemplate.getProperties("activityMay18d_end");
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<Map<String,Object>> activList = drProductInvestDAO.selectActivityMay18d(map);
		
		int count = drProductInvestDAO.selectActivityMay18dCount(map);
		
		int amount = 0;
		if(Utils.isObjectNotEmpty(member)){
			map.put("uid", member.getUid());
			amount = drProductInvestDAO.selectActivityMay18dInvestAmountByUid(map);
		}
		
		map.clear();
		map.put("activList", activList);
		map.put("count", count);
		map.put("amount", amount);
		br.setMap(map);
		br.setSuccess(true);
		return br;
	}

	@Override
	public Map<String, Object> selectInvestFirstByUid(Integer uid) {
		return drProductInvestDAO.selectInvestFirstByUid(uid);
	}

	@Override
	public Integer selectNewInvest(Integer uid) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		return drProductInvestDAO.selectNewInvest(map);
	}

    @Override
    public BaseResult getEveryoneJdCard(Map<String, Object> param) throws IOException {
        BaseResult br = new BaseResult();
        Map<String,Object> map = new HashMap<String, Object>();
        map=drProductInvestDAO.selectEveryoneJdCard(param);
        if (Utils.isObjectNotEmpty(map)) {
            BigDecimal sumAmount=(BigDecimal) map.get("sumAmount");
            String keyNumber = redisClientTemplate.getProperties("keyNumber");
            String[] splitNumber = keyNumber.split(",");
            Double[] jdCardNumber={30.00,50.00,100.00,200.00,100.00,100.00,200.00,300.00,500.00,300.00,2000.00,2000.00};
            Double sumJdCard=0.00;
            for (int i=0;i<splitNumber.length;i++) {
                if(1==sumAmount.compareTo(new BigDecimal(splitNumber[i]))){
                    sumJdCard+=jdCardNumber[i];
                    map.put("key",i);
                }
                if (0==sumAmount.compareTo(new BigDecimal(splitNumber[i]))){
                    sumJdCard+=jdCardNumber[i];
                    map.put("key",i);
                }
                if (-1==sumAmount.compareTo(new BigDecimal(splitNumber[i]))){
                    map.put("key",i-1);
                    break;
                }
            }
            //查看每日累计的京东卡加入累计金额
            List<HashMap<String,Object>> list1=bypCommodityDetailDAO.getMyEveryoneJdCard(param);
            for (HashMap<String,Object> s:list1) {
                Double price= Double.valueOf(s.get("price").toString());
                sumJdCard += price;
            }
            map.put("sumJdCard",sumJdCard);
            //给前端提示客户宝箱领取了哪些
            List<Map<String, Object>> list = drMemberFavourableDAO.selectByParam(param);
            for (int i =0;i<list.size();i++) {
                String id = list.get(i).get("id").toString();
                param.put("activityId",id);
                Map<String,Object> res=drMemberFavourableDAO.selectUserByAid(param);
                if(Utils.isObjectNotEmpty(res)){
                    map.put("userKey"+(i+1),1);//用户开启的红包宝箱到第1个了
                    res.clear();
                }else {
                    map.put("userKey"+(i+1),0);//用户开启的红包宝箱到第1个了
                }
            }

        }else {
            Map<String,Object> map1 = new HashMap<String, Object>();
            map1.put("key",-1);
            map1.put("sumJdCard",0);
            map1.put("sumAmount",0);
            map1.put("userKey1",0);//用户开启的红包宝箱到第1个了
            map1.put("userKey2",0);//用户开启的红包宝箱到第2个了
            map1.put("userKey3",0);//用户开启的红包宝箱到第3个了
            br.setSuccess(true);
            br.setMap(map1);
            return br;
        }
        br.setSuccess(true);
        br.setMap(map);
        return br;
    }

    @Override
    public BaseResult getEveryoneTopFive(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        list=drProductInvestDAO.selectEveryoneTopFive(param);
        map.put("list",list);
        br.setSuccess(true);
        br.setMap(map);
        return br;
    }

    @Override
    public BaseResult getEveryoneForVIP(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        list=drProductInvestDAO.selectEveryoneForVIP(param);
        map.put("list",list);
        br.setSuccess(true);
        br.setMap(map);
        return br;
    }

    @Override
    public BaseResult getEveryoneTopTen() {
        BaseResult br = new BaseResult();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        list=drProductInvestDAO.getEveryoneTopTen();
        map.put("list",list);
        br.setSuccess(true);
        br.setMap(map);
        return br;
    }

    /**
     * map:("uid",uid),("index",index);
     * @param param
     * @return
     */
    @Override
    public BaseResult getTreasure(Map<String, Object> param) throws SQLException {
        BaseResult br = new BaseResult();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        Integer index = (Integer) param.get("index");
        Integer uid = (Integer) param.get("uid");
        //查询红包id
        list=drMemberFavourableDAO.selectByParam(param);
        map.put("fid",list.get(index).get("id"));
        DrActivityParameter dap = drActivityParameterDAO.selectParameterPrimaryKey(map);
        //判断客户积分是否可以领取
        map=drProductInvestDAO.selectEveryoneJdCard(param);
        BigDecimal key1=new BigDecimal(6000);
        BigDecimal key2=new BigDecimal(20000);
        BigDecimal key3=new BigDecimal(50000);
        String sumAmount = map.get("sumAmount").toString();
        BigDecimal sum= new BigDecimal(sumAmount);
        if (0==index&&(sum.compareTo(key1)>=0)||(1==index&&(sum.compareTo(key2)>=0))||(2==index&&(sum.compareTo(key3)>=0))){
            DrMemberFavourable dmf = new DrMemberFavourable(Integer.parseInt(list.get(index).get("id").toString()), uid,  dap.getType(), dap.getCode(), dap.getName(), dap.getAmount(),
                    dap.getRaisedRates(), dap.getEnableAmount(), 0, Utils.getDayNumOfAppointDate(new Date(), -dap.getDeadline()), "5880活动奖品", 0, 0, dap.getProductDeadline(), dap.getMultiple(), null);
            drMemberFavourableDAO.insertIntoInfo(dmf);//添加
            br.setSuccess(true);
            br.setMap(map);
            return br;
        }else {
            br.setSuccess(false);
            br.setErrorMsg("您还没满足条件,快去投资吧");
            return br;
        }
    }

	@Override
	public List<Map<String, Object>> selectNewInvestByActivityProduct(Map<String, Object> map) {
		return drProductInvestDAO.selectNewInvestByActivityProduct(map);
	}

    @Override
    public Map<String, Object> selectInvestInfo(Map<String, Object> map) {
        return drProductInvestDAO.selectInvestInfo(map);
    }

}
