package com.jsjf.service.member.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberCouponDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCoupon;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberCouponService;
import com.jsjf.service.system.SysMessageLogService;
@Service
public class DrMemberCouponServiceImpl implements DrMemberCouponService {
	@Autowired
	DrMemberCouponDAO drMemberCouponDAO; 
	@Autowired
	DrMemberMsgDAO drMemberMsgDAO; 
	@Autowired
	SysMessageLogService sysMessageLogService; 
	@Autowired
	DrMemberDAO drMemberDAO; 
	
	@Override
	public void couponMature() throws Exception {
		List<DrMemberCoupon> list = drMemberCouponDAO.selectCouponList();
		if(!Utils.isEmptyList(list)){
			for(DrMemberCoupon coupon:list){
				DrMember member = drMemberDAO.selectByPrimaryKey(coupon.getUid());
				DrMemberMsg memberMsg = new DrMemberMsg(member.getUid(), 0, 20,"理财金券", new Date(), 0, 0, 
						PropertyUtil.getProperties("couponMature").replace("${1}", coupon.getAmount().intValue()+"")
						.replace("${2}", Utils.format(coupon.getDestroyTime(), "yyyy年MM月dd日 ")));
				drMemberMsgDAO.insertDrMemberMsg(memberMsg);
				
				//投资成功发短信给投资人	
				SysMessageLog logs = new SysMessageLog(member.getUid(), PropertyUtil.getProperties("couponMatureMobile").replace("${1}", coupon.getAmount().intValue()+"")
						.replace("${2}", Utils.format(coupon.getDestroyTime(), "yyyy年MM月dd日 ")),
						14, null, member.getMobilephone());
				sysMessageLogService.sendMsg(logs);
			}
		}
	}
}
