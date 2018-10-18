package com.jsjf.service.member.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrCarryParamDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFourElementsLogDAO;
import com.jsjf.dao.system.SysBankDAO;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.member.DrMemberFourElementsLog;
import com.jsjf.model.system.SysBank;
import com.jsjf.service.member.DrMemberBankService;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.vo.JYTFourElementsSendDate;
import com.jytpay.vo.JYTResultData;

@Service
public class DrMemberBankServiceImpl implements DrMemberBankService {
	
	@Autowired
	public DrMemberBankDAO drMemberBankDAO;
	@Autowired
	private DrMemberFourElementsLogDAO drMemberFourElementsLogDAO;
	@Autowired
	private DrMemberBaseInfoDAO drMemberBaseInfoDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private SysBankDAO sysBankDAO;
	@Autowired
	private DrCarryParamDAO drCarryParamDAO;
	
	@Override
	public BaseResult insertDrMemberBank(Integer uid,String realName,String idCards,String bankNum,String phone,Integer channel)throws Exception{
		BaseResult br = new BaseResult();
		DrCarryParam drCarryParam = drCarryParamDAO.getDrCarryParam();
        List<DrMemberFourElementsLog> list = drMemberFourElementsLogDAO.queryMemberFourElementsLogList(uid);
        if(list.size() > drCarryParam.getFourElementCount()-1){
        	br.setSuccess(false);
        	br.setErrorCode("1010");
        	return br;
        }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idCards", idCards);
        List<DrMemberBaseInfo> baseList = drMemberBaseInfoDAO.queryMemberBaseInfoByMap(map);
		if(baseList.size()>0){
        	br.setSuccess(false);
        	br.setErrorCode("1012");
        	return br;
		}
		map.clear();
		
		JYTFourElementsSendDate sendData = new JYTFourElementsSendDate();
		sendData.setBank_card_no(bankNum);
		sendData.setId_num(idCards);
		sendData.setId_name(realName);
		sendData.setTerminal_type("01");//app传01  PC传03
		sendData.setPhone_no(phone);
		sendData.setTran_code(MockClientMsgBase.FOUR_ELEMENTS_TRAN_CODE);
		sendData.setTran_flowid(Utils.createOrderNo(6,uid,""));
		sendData.setBank_card_type("D");//D借记卡,C贷记卡,A全部（如果商户平台借记卡贷记卡都支持的话传A）
		JYTResultData resultData = MockClientMsgBase.getInstance().getFourElements(sendData);
		
		DrMemberFourElementsLog lo = new DrMemberFourElementsLog();
		lo.setUid(uid);
		lo.setRealName(realName);
		lo.setBankNum(bankNum);
		lo.setBankName(resultData.getBank_name());
		lo.setMobilePhone(phone);
		lo.setFlag(channel);
		lo.setIdCards(idCards);
		lo.setReasonStatus(resultData.getResp_code());
		lo.setReason(resultData.getResp_desc());
		lo.setOrderNo(sendData.getTran_flowid());
		
		//如果四要素认证失败
		if("S0000000".equals(resultData.getResp_code())){
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
			
			map.put("bankName", resultData.getBank_name());
			SysBank sysBank = sysBankDAO.selectSysBank(map);
			map.clear();
			if(Utils.isObjectEmpty(sysBank)){
	        	br.setSuccess(false);
	        	br.setErrorCode("1009");
	        	return br;
			}
			
			DrMemberBank drMemberBank = new DrMemberBank();
			drMemberBank.setUid(uid);
			drMemberBank.setBankNum(bankNum);
			drMemberBank.setBankName(resultData.getBank_name());
			drMemberBank.setType(1);
			drMemberBank.setStatus(1);
			drMemberBank.setCardFlag(0);
			drMemberBank.setChannel(channel);
			drMemberBank.setMobilePhone(phone);
			drMemberBankDAO.insertDrMemberBank(drMemberBank);
			
			DrMemberBaseInfo baseInfo = new DrMemberBaseInfo();
			baseInfo.setUid(uid);
			baseInfo.setRealName(realName);
			baseInfo.setIdCards(idCards);
			// 性别和生日
			Integer sexNum = Integer.parseInt(idCards.substring(16, 17));
			String birthday = idCards.substring(6, 14);
			baseInfo.setSex(sexNum % 2 != 0 ? 1 : 2);
			baseInfo.setBirthDate(new SimpleDateFormat("yyyyMMdd").parse(birthday));

			drMemberBaseInfoDAO.updateDrMemberBaseInfoById(baseInfo);
			
			DrMember member = new DrMember();
			member.setUid(uid);
			member.setRealVerify(1);
			drMemberDAO.updateByPrimaryKey(member);
			
			map.put("realName",realName);
			map.put("idCards",idCards.substring(0,4)+"***"+idCards.substring(idCards.length()-3));
			map.put("bankNum",bankNum.substring(bankNum.length()-4,bankNum.length()));
			map.put("bankCode",sysBank.getId());
			map.put("bankName",sysBank.getBankName());
			
			br.setMap(map);
        	br.setSuccess(true);
		}else if("ER001005".equals(resultData.getResp_code())){//银行卡类型不符，请更换银行卡后重试
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1007");
        	return br;
		}else if("ER000027".equals(resultData.getResp_code())){//此卡未开通银联在线支付功能,实名认证失败，请联系发卡银行
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1008");
        	return br;
		}else if("ER001004".equals(resultData.getResp_code())){//不支持此银行卡的验证
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1009");
        	return br;
		}else if("ER000013".equals(resultData.getResp_code()) 
				|| "ER000014".equals(resultData.getResp_code()) 
				|| "ER000025".equals(resultData.getResp_code())){//请核对个人信息
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1014");
        	return br;
		}else if("ER000023".equals(resultData.getResp_code())){//请核对银行卡信息
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1015");
        	return br;
		}else if("ER001001".equals(resultData.getResp_code())){//该银行卡bin不支持
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1016");
        	return br;
		}else if("ER999999".equals(resultData.getResp_code())){//认证失败，系统异常请稍后再试
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1017");
        	return br;
		}else{
			drMemberFourElementsLogDAO.insertMemberFourElementsLog(lo);
        	br.setSuccess(false);
        	br.setErrorCode("1011");
        	return br;
		}
		return br;
	}

	@Override
	public DrMemberBank selectIdentificationBank(int uid) {
		return drMemberBankDAO.selectIdentificationBank(uid);
	}

	@Override
	public SysBank selectSysBank(Map<String, Object> map) {
		return sysBankDAO.selectSysBank(map);
	}

	@Override
	public List<Map<String, Object>> selectSysBankQuotaList() {
		return sysBankDAO.selectSysBankQuotaList();
	}

	@Override
	public DrMemberBank selectFuiouIdentificationBank(int uid) {
		return drMemberBankDAO.selectFuiouIdentificationBank(uid);
	}

	@Override
	public void updateDrMemberBank(DrMemberBank drMemberBank)
			throws SQLException {
		drMemberBankDAO.updateDrMemberBank(drMemberBank);
		
	}

}
