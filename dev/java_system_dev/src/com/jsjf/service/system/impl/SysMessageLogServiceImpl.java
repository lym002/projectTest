package com.jsjf.service.system.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.system.SysMessageLogDAO;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.system.SysMessageLogService;

@Service
@Transactional
public class SysMessageLogServiceImpl implements SysMessageLogService {
	private static final Logger logger = Logger.getLogger(SysMessageLogServiceImpl.class);

	@Autowired
	public SysMessageLogDAO sysMessageLogDAO;
	
	@Autowired
	public DrMemberDAO drMemberDAO;
	

	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Override
	public Integer insert(SysMessageLog sysMessageLog) throws SQLException {
		sysMessageLogDAO.insertSysMessageLog(sysMessageLog);
		return sysMessageLog.getMsgId();
	}

	@Override
	public SysMessageLog selectByProperty(SysMessageLog sysMessageLog) {
		return sysMessageLogDAO.selectByProperty(sysMessageLog);
	}


	@Override
	public Integer sendMsg(SysMessageLog logs) {
		try {
			Integer result;
			if(Utils.isObjectNotEmpty(logs.getSendTime())){
				result = SmsSendUtil.sendTimeMsg(logs.getPhone(), logs.getMessage(),Utils.format(logs.getSendTime(), "yyyy-MM-dd HH:mm:ss"));//发送短信
				logs.setSendTime(new Date());
			}else{
				result = SmsSendUtil.sendMsg(logs.getPhone(), logs.getMessage());
				logs.setSendTime(new Date());
			}
			//记录日志
			logs.setResults(result);
			if(!logs.getPhone().contains(",")){
				sysMessageLogDAO.insertSysMessageLog(logs);
			}else{
				String[] phone = logs.getPhone().split(",");
				for (String s : phone) {
					logs.setPhone(s);
					sysMessageLogDAO.insertSysMessageLog(logs);
				}
			}
			
			return result;
		} catch (Exception e) {
			logger.error("短信发送失败", e);
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override	
	public BaseResult selectByType(SysMessageLog sysMessageLog, PageInfo pi){
			
			Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
			Map map=new HashMap();  
			map.put("type", sysMessageLog.getType());
			if(sysMessageLog.getPhone()!=null && sysMessageLog.getPhone()!=""){
				map.put("phone", sysMessageLog.getPhone());
			}
			if(sysMessageLog.getSendTime()!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				String str=sdf.format(sysMessageLog.getSendTime());
				map.put("sendTime", str);
			}
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit()); 
			List<SysMessageLog> list = sysMessageLogDAO.selectByType(map);
			Integer total = sysMessageLogDAO.selectByTypeCounts(map);
			pi.setTotal(total);
			pi.setRows(list);

			resultMap.put("page", pi);
			BaseResult br = new BaseResult();
			br.setMap(resultMap);
			return br;
		}
	@Override
	public Integer sendMsg(SysMessageLog logs,int type) {
		try {
			Integer result;
			if(Utils.isObjectNotEmpty(logs.getSendTime())){
				result = SmsSendUtil.sendTimeMsg(logs.getPhone(), logs.getMessage(),Utils.format(logs.getSendTime(), "yyyy-MM-dd HH:mm:ss"));//发送短信
			}else{
				result = SmsSendUtil.sendMsg(logs.getPhone(), logs.getMessage());
				logs.setSendTime(new Date());
			}
			//记录日志
			logs.setResults(result);
			sysMessageLogDAO.insertSysMessageLog(logs);
			if(result>100){
				result = -1;
			}
			return result;
		} catch (Exception e) {
			logger.error("短信发送失败", e);
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<SysMessageLog> selectSysMessageLogList(Map<String, Object> map) {
		
		return sysMessageLogDAO.selectByType(map);
	}


	@Override
	public void sendSmsHandle() {
		Map<String,Object> map = new HashMap<String, Object> ();
		map.put("results", -9);
		map.put("sendTime", Utils.format(new Date(), "yyyy-MM-dd"));
		map.put("offset", 0);
		map.put("limit", 500);
		Integer count = sysMessageLogDAO.selectByTypeCounts(map);
		
		if(Utils.isBlank(count)){
			return;
		}
		List<Integer> ids = new ArrayList<Integer>(count);
		List<SysMessageLog> list = sysMessageLogDAO.selectByType(map);
		for(SysMessageLog logs:list){
			try {
				SmsSendUtil.sendMsg(logs.getPhone(), logs.getMessage());
				ids.add(logs.getMsgId());
			} catch (Exception e) {
				logger.error("处理失败msgid="+logs.getMsgId()+"(处理定时短信发送失败的定时任务)"+e.getMessage(), e);
			}
		}
		map.clear();
		map.put("ids", ids);
		map.put("resutls", 88);
		if(ids.size()>0){			
			sysMessageLogDAO.update(map);
		}
		
	}

	@Override
	public void luckyMoneySms() {
		List<String> list = drMemberDAO.selectLuckyMoney();
		Date dt = new Date();
		if(Utils.isObjectNotEmpty(list)){
			try {
				String content = PropertyUtil.getProperties(Utils.format(dt, "yyyyMMddHH"));
				if(Utils.isObjectNotEmpty(content)){
					StringBuffer mobileSB = new StringBuffer();
					for(int i= 0;i<list.size();i++){
						mobileSB = mobileSB.append(list.get(i)).append(",");
						if(i!=0 && i%100==0){
							SmsSendUtil.sendMsgByMarketing(mobileSB.deleteCharAt(mobileSB.length()-1).toString(), content) ;
							mobileSB = new StringBuffer();
						}
					}
					if(mobileSB.length()>0){
						SmsSendUtil.sendMsgByMarketing(mobileSB.deleteCharAt(mobileSB.length()-1).toString(), content) ;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void tiYanJinGuoQiSms() {
		List<Map<String,Object>> list = drMemberDAO.selectTiYanJinGuoQi();
		if(Utils.isObjectNotEmpty(list)){
			Date dt = new Date();
			try {
				for(Map<String,Object> map:list){
					SmsSendUtil.sendMsgByMarketing(map.get("mobile").toString(), PropertyUtil.getProperties(Utils.format(dt, "yyyyMMddHH")).replace("${1}",map.get("amount").toString())) ;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getFuiouRspCode() {
		
		List<Map<String,Object>> list = sysMessageLogDAO.getFuiouRspCode();
		Map<String,String> hash = new HashMap<String, String>();
		
		for (Map<String, Object> map : list) {
			hash.put((String) map.get("code"), (String) map.get("tips"));
		}
		redisClientTemplate.hmset("fuiouCode", hash);
	}
}
