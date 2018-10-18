package com.jsjf.service.member.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFourElementsLogDAO;
import com.jsjf.dao.system.SysBankDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberFourElementsLog;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberFourElementsLogService;

@Service
public class DrMemberFourElementsLogServiceImpl implements DrMemberFourElementsLogService {
	@Autowired
	public DrMemberFourElementsLogDAO drMemberFourElementsLogDAO;
	@Autowired
	private DrMemberBaseInfoDAO drMemberBaseInfoDAO;
	@Autowired
	private DrMemberBankDAO drMemberBankDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private SysBankDAO sysBankDAO;
	
	@Override
	public void insertMemberFourElementsLog(DrMemberFourElementsLog record) {
		drMemberFourElementsLogDAO.insertMemberFourElementsLog(record);
	}

	@Override
	public BaseResult getMemberFourElementsLogList(DrMemberFourElementsLog log,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobilePhone", log.getMobilePhone());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrMemberFourElementsLog> list = drMemberFourElementsLogDAO.getMemberFourElementsLogList(map);
		Integer total = drMemberFourElementsLogDAO.getMemberFourElementsLogCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public void updateDrMemberFourElementsLog(DrMemberFourElementsLog log,SysUsersVo vo) throws Exception{
		DrMemberBaseInfo baseinfo = new DrMemberBaseInfo();
		baseinfo.setUid(log.getUid());
		baseinfo.setRealName(log.getRealName());
		baseinfo.setIdCards(log.getIdCards());
		//性别和生日
		Integer sexNum = Integer.parseInt(baseinfo.getIdCards().substring(16, 17)); 
		String birthday = baseinfo.getIdCards().substring(6, 14);  
		baseinfo.setSex(sexNum%2 !=0 ? 1:2);
		baseinfo.setBirthDate(new SimpleDateFormat("yyyyMMdd").parse(birthday));
		drMemberBaseInfoDAO.updateByUidSelective(baseinfo);//修改基本信息
		
//		log.setFlag(0);
//		log.setReasonStatus("S0000000");
//		log.setReason("成功");
//		drMemberFourElementsLogDAO.insertMemberFourElementsLog(log); //插入认证记录
	
		DrMemberBank drMemberBank = new DrMemberBank();
		drMemberBank.setUid(log.getUid());
		drMemberBank.setBankName(log.getBankName());
		drMemberBank.setBankNum(log.getBankNum());
		drMemberBank.setType(1);
		drMemberBank.setStatus(1);
		drMemberBank.setCardFlag(0);
		drMemberBank.setChannel(4);
		drMemberBank.setMobilePhone(log.getMobilePhone());
		drMemberBank.setAddUser(vo.getUserKy().intValue());
		drMemberBankDAO.insertDrMemberBank(drMemberBank);
		
		DrMember member = new DrMember();
		member.setUid(log.getUid());
		member.setRealVerify(1);
		drMemberDAO.updateDrMemberByUid(member);
	}

	@Override
	public List<SysBank> selectSysBank() {
		return sysBankDAO.selectSysBank();
	}

	@Override
	public BaseResult updateDrMemberFourElementsLogAgain(Integer uid)
			throws Exception {
		BaseResult br = new BaseResult();
		List<DrMemberFourElementsLog> log = drMemberFourElementsLogDAO.queryMemberFourElementsLogList(uid);
		if(!Utils.isEmptyList(log)){
			for(DrMemberFourElementsLog drMemberFourElementsLog : log){
				if(drMemberFourElementsLog.getUid()<0){
					br.setSuccess(true);
					br.setMsg("操作成功！");
					return br;
				}
			}
			drMemberFourElementsLogDAO.updateMemberFourElementsLog(log.get(0));
		}
		br.setSuccess(true);
		br.setMsg("操作成功！");
		return br;
	}

	@Override
	public BaseResult drMemberIdentificationLogList(DrMemberFourElementsLog log, PageInfo pi) {
		// TODO Auto-generated method stub
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobilePhone", log.getMobilePhone());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrMemberFourElementsLog> list = drMemberFourElementsLogDAO.getMemberIdentificationLogList(map);
		Integer total = drMemberFourElementsLogDAO.getMemberIdentificationLogCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult updateDrMemberIdentificationLogAgain(String mobilePhone)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobilePhone", mobilePhone);
		BaseResult br = new BaseResult();
		List<DrMemberFourElementsLog> log = drMemberFourElementsLogDAO.getMemberIdentificationLogList(map);
		if(!Utils.isEmptyList(log)){
			for(DrMemberFourElementsLog drMemberFourElementsLog : log){
				if(drMemberFourElementsLog.getUid()<0){
					br.setSuccess(true);
					br.setMsg("已提供一次认证机会！");
					return br;
				}
			}
			if(log.size() == 3 ){
				drMemberFourElementsLogDAO.updateMemberFourElementsLog(log.get(0));
				br.setSuccess(true);
				br.setMsg("操作成功！");
			}else if(log.size()>=4){
				br.setSuccess(true);
				br.setMsg("认证次数已超过3次");
			}else{
				br.setSuccess(true);
				br.setMsg("未达到重新认证条件");
			}
		}else{
			br.setSuccess(true);
			br.setMsg("请核对手机号是否正确！");
		}
		return br;
	}
}
