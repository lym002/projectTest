package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.activity.JsActivityLuckyMoneyDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.model.activity.JsActivityLuckyMoney;
import com.jsjf.model.member.DrMemberMsg;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsActivityLuckyMoneyDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.activity.JsActivityLuckyMoney;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.activity.JsActivityLuckyMoneyService;
@Service
@Transactional
public class JsActivityLuckyMoneyServiceImpl implements JsActivityLuckyMoneyService {

	@Autowired
	private JsActivityLuckyMoneyDAO JsActivityLuckyMoneyServiceDao;

	@Autowired
	JsActivityLuckyMoneyDAO jsActivityLuckyMoneyDAO;
	@Autowired
	DrActivityDAO drActivityDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	
	@Override
	public List<JsActivityLuckyMoney> selectJsActivityLuckyMoneyByMap(Map<String, Object> map) {
		return JsActivityLuckyMoneyServiceDao.selectJsActivityLuckyMoneyByMap(map);
	}
	
	@Override
	public BaseResult insert(Integer uid) {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("shaerUid", uid);
			map.put("time", new Date());
			List<JsActivityLuckyMoney> Lmlist = jsActivityLuckyMoneyDAO.selectJsActivityLuckyMoneyByMap(map);
			if(Lmlist.size() == 0){
				List<DrActivity> list = drActivityDAO.selectDrActivityList(null);
				Date nowDate = new Date();
				if (list.size() > 0) {
					if (nowDate.after(list.get(0).getStartTime()) && nowDate.before(list.get(0).getEndTime())) {
						inserts(uid, new BigDecimal(5888), new BigDecimal(2600));
					} else if (nowDate.after(list.get(1).getStartTime()) && nowDate.before(list.get(1).getEndTime())) {
						inserts(uid, new BigDecimal(8988), new BigDecimal(3980));
					} else if (nowDate.after(list.get(2).getStartTime()) && nowDate.before(list.get(2).getEndTime())) {
						inserts(uid, new BigDecimal(3888), new BigDecimal(2400));
					}
				}
			}
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}
	//批量添加
	private void inserts(Integer uid,BigDecimal amount1,BigDecimal amount2){
		JsActivityLuckyMoney jsActivityLuckyMoney = new JsActivityLuckyMoney();
		jsActivityLuckyMoney.setShaerUid(uid);
		BigDecimal[] amounts = {amount1,amount2,amount2,amount2,amount2,amount2};
		for (int i = 0; i < amounts.length; i++) {
			if(i == 0){
				try {
					jsActivityLuckyMoney.setUid(uid);
					jsActivityLuckyMoney.setUpdateTime(new Date());
					//体验金过期时间为15天
					Calendar calendar = new GregorianCalendar(); 
					calendar.setTime(new Date()); 
					calendar.add(calendar.DATE,15);
					DrMemberFavourable drMemberFavourable = new DrMemberFavourable(null,uid, 3,null, "体验金", amounts[i], null , null, 0,calendar.getTime(),"体验金", 0, 0, null, 1);
					drMemberFavourableDAO.insertIntoInfo(drMemberFavourable);
					//发送站内信
					DrMemberMsg msg = new DrMemberMsg(uid, 0, 1, "压岁钱", new Date(), 0, 0,"尊敬的用户，您收到压岁钱"+amounts[i]+"元");
					drMemberMsgDAO.insertDrMemberMsg(msg);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				jsActivityLuckyMoney.setUid(null);
				jsActivityLuckyMoney.setUpdateTime(null);
			}
			jsActivityLuckyMoney.setAmount(amounts[i]);
			jsActivityLuckyMoneyDAO.insert(jsActivityLuckyMoney);
		}
	}

	@Override
	public List<JsActivityLuckyMoney> selectByshaerUid(Integer shareUid) {
		// TODO Auto-generated method stub
		return jsActivityLuckyMoneyDAO.selectByshaerUid(shareUid);
	}

}
