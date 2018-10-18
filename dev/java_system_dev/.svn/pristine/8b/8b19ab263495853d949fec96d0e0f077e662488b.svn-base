package com.jsjf.service.jzh.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInvestRepayInfoDAO;
import com.jsjf.dao.system.SysFuiouMessageLogDAO;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.model.system.SysFuiouMessageLog;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.jzh.JzhMessageService;
import com.jsjf.service.jzh.SysFuiouMessageService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.seq.SeqService;
import com.jzh.FuiouConfig;
import com.jzh.util.ConfigReader;
import com.jzh.util.JzhSFtpUtil;


@Service
@Transactional
public class JzhMessageServiceImpl implements JzhMessageService{
	 @Autowired
	  private DrProductInfoService drProdectInfoService;
	  
	  @Autowired
	  private SysFuiouMessageLogDAO sysFuiouMessageLogDAO;
	  
	  @Autowired
	  private SysFuiouMessageService sysFuiouMessageService;
	  
	  @Autowired
	  private DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;
	  
	  private String checkpath = ConfigReader.getConfig("check");//上传报文路径
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private SeqService seqService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	/**
	 * 个人开户报文报备
	 * 每天早上的1点钟报备昨日的个人开户
	 */
  	public void selectPersonRegBatchUpload() {
		String filename = "P2P_PW10_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PW10"),4 )+ ".txt";
		//获取数据
		List<DrMember>batchUpdateList = new ArrayList<>();//更新数据列表
		List<Map<String,Object>> list= drMemberService.selectPersonRegBatchUpload();
		List<Map<String,Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PW10");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setReportCount(list.size());
		Map<String,Object>tempMap = null;
		for(int i = 0; i < list.size(); i ++){
			tempList.add(list.get(i));
			tempMap = list.get(i);
			String seqNo = Utils.createOrderNo(6, (Integer)tempMap.get("mid"), "");
			tempMap.put("seqNo", seqNo);
			DrMember dm = new DrMember();
			dm.setFuiouMessageNo(tempMap.get("mchntTxnSsn").toString());
			dm.setFileStatus(1);
			dm.setUid((Integer)list.get(i).get("mid"));
			batchUpdateList.add(dm);
			if(i > 0 && i % 1999 == 0){
				generateAnduploadTxtFile(filename, checkpath,  getPersonRegData(tempList));
				tempList = new ArrayList<>();
				filename = "P2P_PW10_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PW10"),4 )+ ".txt";
			}
		}
		if(!tempList.isEmpty() || !list.isEmpty()){
			generateAnduploadTxtFile(filename, checkpath, getPersonRegData(tempList));
		}
		//更新数据
		sysFuiouMessageService.updateMemberMessageUpload(log, batchUpdateList);
  	}
	
	/**
	 * 法人开户报文报备
	 * 每天早上的1点钟报备昨日的法人开户
	 */
		//获取数据
  	public void companyRegBatchUpload(){
  		String filename = "P2P_PW11_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PW11"),4 )+ ".txt";
  		List<DrClaimsCustomer>batchUpdateList = new ArrayList<>();//更新数据列表
  		drClaimsInfoService.updateCompanyStatus();//将多笔债权对应无流水号的法人信息fileStatus更新
		List<Map<String,Object>> list= drClaimsInfoService.selectGenerateAndupload();
		List<Map<String,Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PW11");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setReportCount(list.size());
		Map<String,Object>tempMap = null;
		for(int i = 0; i < list.size(); i ++){
			tempList.add(list.get(i));
			tempMap = list.get(i);
			String seqNo = Utils.createOrderNo(6, (Integer)tempMap.get("mid"), "");
			tempMap.put("seqNo", seqNo);
			DrClaimsCustomer dcc = new DrClaimsCustomer();
			dcc.setFuiouMessageNo(tempMap.get("mchntTxnSsn").toString());
			dcc.setFileStatus(1);
			dcc.setId((Integer)list.get(i).get("mid"));
			batchUpdateList.add(dcc);
			if(i > 0 && i % 1999 == 0){
				generateAnduploadTxtFile(filename, checkpath, getCorporationRegData(tempList));
				tempList = new ArrayList<>();
				filename = "P2P_PW11_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PW11"),4 )+ ".txt";
			}
		}
		if(!tempList.isEmpty() || !list.isEmpty()){
			generateAnduploadTxtFile(filename, checkpath, getCorporationRegData(tempList));
		}
		
		//更新数据
		sysFuiouMessageService.updateCustomerMessageUpload(log, batchUpdateList);
  	}
  	 /**
     * P2P商户交易（冻结，动账）
 	 * 获取投标记录
 	 * 每天早上的3:00进行投标交易报备
 	 */
	// 正常报备
	public void getProductByProjectNo() {
		String businessType = "0";// 业务类型： 0.投标 1.满标放款 2.转让 3.回款 4.其他 5.流标 6平台手续费 7.风险保证金
		Map<String, Object> map = new HashMap<>();
		map.put("businessType", businessType);
		List<Map<String, Object>> list = drProductInfoService.getProductByProjectNo(map);
		getInvestFiling(list, businessType);
	}

	// 补报
	public void getInvestFiling(List<Map<String, Object>> list, String businessType) {
		String filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
				+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
		// 获取数据
		List<DrProductInvest> batchUpdateList = new ArrayList<>();// 更新数据列表
		List<Map<String, Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PWJY");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setBusinessType(businessType);
		log.setReportCount(list.size());
		Map<String, Object> tempMap = null;
		for (int i = 0; i < list.size(); i++) {
			tempMap = list.get(i);
			tempList.add(tempMap);
			DrProductInvest drProductInvest = new DrProductInvest();
			drProductInvest.setFuiouMessageNo(tempMap.get("mchntTxnSsn").toString());
			if ("1".equals(tempMap.get("fileStatus"))) {
				drProductInvest.setFileStatus(4);
			} else if ("3".equals(tempMap.get("fileStatus"))) {
				drProductInvest.setFileStatus(4);
			} else {
				drProductInvest.setFileStatus(1);
			}
			drProductInvest.setId((Integer) tempMap.get("id"));
			batchUpdateList.add(drProductInvest);
			if (i > 0 && i % 1999 == 0) {
				generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDJ", businessType));
				tempList = new ArrayList<>();
				filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
						+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
			}
		}
		if (!tempList.isEmpty() || !list.isEmpty()) {
			generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDJ", businessType));
		}
		// 更新数据
		sysFuiouMessageService.updateProjectInventUpload(log, batchUpdateList);
	}
	
	/**
	 * 满标放款
	 * @param drProductInfoService
	 * @param seqService
	 * @param sysFuiouMessageService
	 * 每天早上3:15报备满标放款
	 */
	// 正常报备
	public void getProductFullCreditByProjectNo() {
		String businessType = "1";// 业务类型： 0.投标 1.满标放款 2.转让 3.回款 4.其他 5.流标// 6平台手续费 7.风险保证金
		Map<String, Object> map = new HashMap<>();
		map.put("businessType", businessType);
		List<Map<String, Object>> list = drProductInfoService.getProductByProjectNo(map);// 获取数据
		getFullCreditFiling(list, businessType);
	}

		//补报
	public void getFullCreditFiling(List<Map<String, Object>> list,String businessType){
		String filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
				+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
		List<DrProductInvest> batchUpdateList = new ArrayList<>();// 更新数据列表
		List<Map<String, Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PWJY");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setBusinessType(businessType);
		log.setReportCount(list.size());
		Map<String, Object> tempMap = null;
		for (int i = 0; i < list.size(); i++) {
			tempMap = list.get(i);
			tempList.add(tempMap);
			DrProductInvest drProductInvest = new DrProductInvest();
			drProductInvest.setFullFuiouMessageNo(tempMap.get("mchntTxnSsn").toString());
			if("1".equals(tempMap.get("fullFileStatus"))){
				drProductInvest.setFullFileStatus(4);
			}else if("3".equals(tempMap.get("fullFileStatus"))){
				drProductInvest.setFullFileStatus(4);
			}else{
				drProductInvest.setFullFileStatus(1);
			}
			drProductInvest.setId((Integer) tempMap.get("id"));
			batchUpdateList.add(drProductInvest);
			if (i > 0 && i % 1999 == 0) {
				generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDZ", businessType));
				tempList = new ArrayList<>();
				filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
						+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
			}
		}
		if (!tempList.isEmpty() || !list.isEmpty()) {
			generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDZ", businessType));
		}
		// 更新数据
		sysFuiouMessageService.updateProjectInventUpload(log, batchUpdateList);
	}

	/**
	 * P2P商户交易（冻结，动账）
	 * 获取回款记录
	 * 每天早上的3:00报回款
	 */
	// 正常报备
	public void getProductInvestRepayInfoByProjectNo() {
		String businessType = "3";// 业务类型： 0.投标 1.满标放款 2.转让 3.回款 4.其他 5.流标
									// 6平台手续费 7.风险保证金
		List<Map<String, Object>> list = drProductInfoService.getProductInvestRepayInfoByProjectNo();
		getInvestReturnedMoneyFiling(list, businessType);
	}

		//补报
	public void getInvestReturnedMoneyFiling(List<Map<String, Object>> list,String businessType){
		String filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
				+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
		// 获取数据
		List<DrProductInvestRepayInfo> batchUpdateList = new ArrayList<>();// 更新数据列表
		List<Map<String, Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PWJY");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setBusinessType(businessType);
		log.setReportCount(list.size());
		Map<String, Object> tempMap = null;
		for (int i = 0; i < list.size(); i++) {
			tempMap = list.get(i);
			tempList.add(tempMap);
			DrProductInvestRepayInfo drProductInvestRepayInfo = new DrProductInvestRepayInfo();
			drProductInvestRepayInfo.setFuiouMessageNo(tempMap.get("mchntTxnSsn").toString());
			if("1".equals(tempMap.get("fileStatus"))){
				drProductInvestRepayInfo.setFileStatus(4);
			}else if("3".equals(tempMap.get("fileStatus"))){
				drProductInvestRepayInfo.setFileStatus(4);
			}else{
				drProductInvestRepayInfo.setFileStatus(1);
			}
			drProductInvestRepayInfo.setId((Integer) tempMap.get("id"));
			batchUpdateList.add(drProductInvestRepayInfo);
			if (i > 0 && i % 1999 == 0) {
				generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDZ", businessType));
				tempList = new ArrayList<>();
				filename = "P2P_PWJY_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_"
						+ Utils.intToString(seqService.getSequenceResetByDay("P2P_PWJY"), 4) + ".txt";
			}
		}
		if (!tempList.isEmpty() || !list.isEmpty()) {
			generateAnduploadTxtFile(filename, checkpath, getProductInvestRegData(tempList, "PWDZ", businessType));
		}
		// 更新数据
		sysFuiouMessageService.updateProjectInventRepayInfoUpload(log, batchUpdateList);
	}
    	
	/**
	 * P2P 项目信息
	 * 定义每5分钟执行一次项目信息报备
	 * 
	 */
  	public void projectInfoUpload(){	
  		String filename = "P2P_PWXM_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PWXM"),4 )+ ".txt";
		//获取数据
		List<DrProductInfo>batchUpdateList = new ArrayList<>();//更新数据列表
		List<Map<String,Object>> list= drProductInfoService.selectProjectInformation();
		List<Map<String,Object>> tempList = new ArrayList<>();
		SysFuiouMessageLog log = new SysFuiouMessageLog();
		log.setType("P2P_PWXM");
		log.setCheckFileName(filename);
		log.setStatus(1);
		log.setReportCount(list.size());
		Map<String,Object>tempMap = null;
		for(int i = 0; i < list.size(); i ++){
			tempList.add(list.get(i));
			tempMap = list.get(i);
			String seqNo = Utils.createOrderNo(6, (Integer)tempMap.get("id"), "");
			String itemNo =Utils.intToString(seqService.getSequence("itemNo"), 10) ;//存管项目编号
			tempMap.put("seqNo", seqNo);
			tempMap.put("itemNo", itemNo);
			DrProductInfo dp = new DrProductInfo();
			dp.setFuiouMessageNo(seqNo);
			dp.setFileStatus(1);
			dp.setId((Integer)list.get(i).get("id"));
			dp.setItemNo(itemNo);
			batchUpdateList.add(dp);
			if(i > 0 && i % 1999 == 0){
				generateAnduploadTxtFile(filename, checkpath,  getProjectInformationData(tempList));
				tempList = new ArrayList<>();
				filename = "P2P_PWXM_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +Utils.intToString(seqService.getSequenceResetByDay("P2P_PWXM"),4 )+ ".txt";
			}
		}
		if(!tempList.isEmpty() || !list.isEmpty()){
			generateAnduploadTxtFile(filename, checkpath, getProjectInformationData(tempList));
		}
		//更新数据
		sysFuiouMessageService.updateProductInfoUpload(log, batchUpdateList);
  	}
  	/**
	 * 报文回调
	 * 定义每半小时执行一次改掉overcheck中匹配成功的数据状态
	 */
	   	public void overcheck() {
		Map<String, Object>map = new HashMap<>();
		map.put("status", 1);
		List<SysFuiouMessageLog>logList = sysFuiouMessageLogDAO.getSysFuiouMessageLogByParam(map);
		Map<String, SysFuiouMessageLog>logMap = new HashMap<>();
		for(SysFuiouMessageLog log : logList){
			logMap.put(log.getCheckFileName(), log);
		}
		List<DrMember>updateMemberList = new ArrayList<>();//个人用户
		List<DrClaimsCustomer>updateClaimsCustomerList = new ArrayList<>();//企业用户
		List<DrProductInfo>updateProductInfoList = new ArrayList<>();//项目用户
		List<DrProductInvest>updateInvestList = new ArrayList<>();//投资
		List<DrProductInvestRepayInfo>updateInvestRepayInfoList = new ArrayList<>();//回款
		
		List<SysFuiouMessageLog>updateLogList = new ArrayList<>();//更新报文日志
		JzhSFtpUtil sftp = new JzhSFtpUtil();
		try {
			sftp.connectServer();
			String overcheckPath = ConfigReader.getConfig("overcheck");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String backupOvercheckPath = ConfigReader.getConfig("downloadfilepath") + sdf.format(new Date());//本地备份路径
			File backupOvercheckFile = new File(backupOvercheckPath);
			if(!backupOvercheckFile.exists()){
				backupOvercheckFile.mkdirs();
			}
			String[]lsFiles = sftp.lsFiles(overcheckPath);
			//文件下载及处理
			for(String filename : lsFiles){
				String checkFileName = filename.substring(filename.indexOf("_") + 1);
				if(logMap.containsKey(checkFileName)){
					SysFuiouMessageLog log = logMap.get(checkFileName);
					//下载文件
					InputStream is = sftp.download(overcheckPath, filename);
					//解析文件
					String result = IOUtils.toString(is, "GBK");
					System.out.println("执行解析文件。。");
					String[]lines = result.split("\r\n");
					boolean success = false;
					//处理报文
					for(String line : lines){
						String[]values = line.split("\\|");
						success = validateResult(values[values.length - 2]);
						int filestatus = success ? 2 : 3;
						String failureCause = success ? null : values[values.length - 1];
						switch (log.getType()) {
						case "P2P_PW10":
							DrMember dr = new DrMember();
							dr.setFuiouMessageNo(values[1]);
							dr.setFileStatus(filestatus);
							dr.setFailureCause(failureCause);
							updateMemberList.add(dr);
							break;
						case "P2P_PW11":
							DrClaimsCustomer dcc = new DrClaimsCustomer();
							dcc.setFuiouMessageNo(values[1]);
							dcc.setFileStatus(filestatus);
							dcc.setFailureCause(failureCause);
							updateClaimsCustomerList.add(dcc);
							break;
						case "P2P_PWJY":
							if("0".equals(log.getBusinessType())){
								DrProductInvest invest = new DrProductInvest();
								invest.setFuiouMessageNo(values[1]);
								invest.setFileStatus(filestatus);
								invest.setFailureCause(failureCause);
								updateInvestList.add(invest);
							}else if("1".equals(log.getBusinessType())){
								DrProductInvest invest = new DrProductInvest();
								invest.setFullFuiouMessageNo(values[1]);
								invest.setFullFileStatus(filestatus);
								invest.setFullFailureCause(failureCause);
								updateInvestList.add(invest);
							}else if("3".equals(log.getBusinessType())){
								DrProductInvestRepayInfo repayInfo = new DrProductInvestRepayInfo();
								repayInfo.setFuiouMessageNo(values[1]);
								repayInfo.setFileStatus(filestatus);
								repayInfo.setFailureCause(failureCause);
								updateInvestRepayInfoList.add(repayInfo);
							}
							break;
						case "P2P_PWXM":
							DrProductInfo dpi = new DrProductInfo();
							dpi.setFuiouMessageNo(values[1]);
							dpi.setFileStatus(filestatus);
							dpi.setFailureCause(failureCause);
							updateProductInfoList.add(dpi);
							break;
						default:
							break;
						}
					}
					//修改日志
					log.setDownloadTime(new Date());
					log.setOvercheckFileName(filename);
					log.setStatus(2);//返回报文处理成功
					updateLogList.add(log);
					//保存文件
					sftp.download(overcheckPath, filename, backupOvercheckPath + "/" + filename);
				}
			}
			//更新数据
			map.put("updateMemberList", updateMemberList);
			map.put("updateClaimsCustomerList", updateClaimsCustomerList);
			map.put("updateProductInfoList", updateProductInfoList);
			map.put("updateInvestList", updateInvestList);
			map.put("updateInvestRepayInfoList", updateInvestRepayInfoList);
			map.put("updateLogList", updateLogList);
			sysFuiouMessageService.updateMessageData(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
  	}
  	/**
  	 * 验证报文结果
  	 * @param resp_code
  	 * @return
  	 */
  	private boolean validateResult(String resp_code){
  		if("核验成功".equals(resp_code)){
  			return true;
  		}else if("该记录已核验通过".equals(resp_code)){
  			return true;
  		}
  		for(int i = 0; i < resp_code.length(); i ++){
  			if(!"0".equals(resp_code.substring(i, i + 1))){
  				return false;
  			}
  		}
  		return true;
  	}
	//生成文件
	private boolean generateAnduploadTxtFile(String filename, String realPath, String fileContent) {
		String fileTempPath = ConfigReader.getConfig("filepath") + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		boolean success = true;
		OutputStreamWriter pw = null;//定义一个流
		File file = null;
		try {
			file = new File(fileTempPath);
			if(!file.exists()){
				file.mkdirs();
			}
			fileTempPath += "/" + filename;
			pw = new OutputStreamWriter(new FileOutputStream(fileTempPath),"GBK");
			//确认流的输出文件和编码格式
			pw.write(fileContent);//将要写入文件的内容，可以多次write
			pw.close();//关闭流
			
			JzhSFtpUtil sftp = new JzhSFtpUtil();
			sftp.connectServer();
			sftp.put(new FileInputStream(fileTempPath), realPath, filename);
			sftp.closeServer();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			success = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		if(!success && file != null){
			//删除临时文件
			if(file.exists()){
				file.delete();
			}
		}
		return success;
   }
	//个人开户
	private String getPersonRegData(List<Map<String, Object>> data){
   	//处理生成文件的内容
   	StringBuffer sb = new StringBuffer();
   	if(data != null){
   		for(Map<String, Object> temp : data){
       		Utils.trimMapStrng(temp);//去掉String值的空格
       		
   			sb.append(StringUtils.left(FuiouConfig.MCHNT_CD, 15))//商户号
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"),30))//平台注册流水
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("realname"), 60))//平台用户名
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mobilePhone").toString(), 160))//存管账户系统登陆用户名
   	    	.append("|")
   	    	.append("")
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("realname"), 160))//户名
   	    	.append("|")
   	    	.append("0")
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("idcards"), 20))//证件号
   	    	.append("|")
   	    	.append(StringUtils.left((temp.get("sex")).toString(),1))//性别
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mobilePhone"), 11))//手机号
   	    	.append("|")
   	    	.append("")
   	    	.append("|")
   	    	.append("1")//用户属性
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"), 8))//注册时即金账户的开户时间（取的是流水号中的前8位）
   	    	.append("|")
   	    	.append(StringUtils.left(ConfigReader.getConfig("paycompanyid"), 30))//第三方祝福公司ID
   	    	.append("|")
   	    	.append("ADD")
   	    	.append("|")
   	    	.append("ADD")//备注
   	    	.append("\r\n");
   		}
   	}
   	return sb.toString();
   }
	
	
		
	//法人开户
	private String getCorporationRegData(List<Map<String, Object>> data){
   	//处理生成文件的内容
   	StringBuffer sb = new StringBuffer();
   	if(data != null){
   		for(Map<String, Object> temp : data){
       		Utils.trimMapStrng(temp);//去掉String值的空格
       		
   			sb.append(StringUtils.left(FuiouConfig.MCHNT_CD, 15))//商户号
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"), 30))//流水
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("companyName"), 30))//企业名称
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"), 8))//注册时即金账户的开户时间（取的是流水号中的前8位）
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("name"), 30))//法人姓名
   	    	.append("|")
   	    	.append(StringUtils.left((temp.get("certificateNo")).toString(), 20))//身份证件号
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("phone"), 11))//手机号
   	    	.append("|")
   	    	.append("")
   	    	.append("|")
   	    	.append(StringUtils.left(ConfigReader.getConfig("paycompanyid"), 30))//第三方支付公司ID
   	    	.append("|")
   	    	.append("ADD")//操作类型
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("businessNo"), 20))//营业执照登记号（待定）
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("taxNo"), 20))//税务登记号（待定）
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("mechanismNo"), 20))//组织机构代码（待定）
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("name"), 60))//平台用户号
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("phone"), 16))//存管账户系统登陆用户名
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("companyName"), 30))//备注
   	    	.append("|")
   	    	.append("1")
   	    	.append("|")
   	    	.append("")
   	    	.append("|")
   	    	.append("")
   	    	.append("|")
   	    	.append(StringUtils.left((String)temp.get("businessNo"), 60))//统一社会信用代码（待定）
   	    	.append("\r\n");
   	    	
   		}
   	}
   	return sb.toString();
   }
	//项目信息
	private String getProjectInformationData(List<Map<String, Object>> data){
	   	//处理生成文件的内容
	   	StringBuffer sb = new StringBuffer();
	   	if(data != null){
	   		for(Map<String, Object> temp : data){
	       		Utils.trimMapStrng(temp);//去掉String值的空格
	       		
	   			sb.append(StringUtils.left(FuiouConfig.MCHNT_CD, 15))//商户号
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("seqNo"), 30))//平台流水号
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("itemNo"), 10))//项目编号
	   	    	.append("|")
	   	    	.append("0")//借款类型
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("name"), 30))//借款标题
	   	    	.append("|")
	   	    	.append("")
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("loanUse"), 200))//借款用途
	   	    	.append("|")
	   	    	.append(StringUtils.left(yuanToCent(temp.get("amount").toString()),12))//借款金额
	   	    	.append("|")
	   	    	.append(StringUtils.left(temp.get("rate").toString(), 12))//预期收益
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("fullName"), 30))//产品名称
	   	    	.append("|")
	   	    	.append(StringUtils.left(temp.get("repayType").toString(), 1))//还款方式
	   	    	.append("|")
	   	    	.append("")
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("startDate"), 8))//筹标起始日
	   	    	.append("|")
	   	    	.append(StringUtils.left(yuanToCent(temp.get("leastaAmount").toString()),12))//每份投标金额
	   	    	.append("|")
	   	    	.append("1")
	   	    	.append("|")
	   	    	.append(StringUtils.left(yuanToCent(temp.get("maxAmount").toString()), 12))//最多投标金额
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("name"), 50))//借款人平台用户名
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("phone"), 50))//借款人存管账户系统登录用户名
	   	    	.append("|")
	   	    	.append(StringUtils.left((String)temp.get("loanName"), 200))//借款人项目描述
	   	    	.append("|")
	   	    	.append(StringUtils.left(yuanToCent(temp.get("maxAmount").toString()), 11))//费用项
	   	    	.append("|")
	   	    	.append("")
	   	    	.append("|")
	   	    	.append("1")
	   	    	.append("|")
	   	    	.append("")
	   	    	.append("|")
	   	    	.append(StringUtils.left(ConfigReader.getConfig("paycompanyid"), 30))//第三方支付公司ID
	   	    	.append("\r\n");
	   	    	
	   		}
	   	}
	   	return sb.toString();
   }
	
   public static String getStr(String value, int len){
   	return StringUtils.left(value, len);
   }
 
 
  	
 //投资报备
 	private String getProductInvestRegData(List<Map<String, Object>> data,String type,String businesstype){
     	//处理生成文件的内容
     	StringBuffer sb = new StringBuffer();
     	if(data != null){
     		for(Map<String, Object> temp : data){
       		Utils.trimMapStrng(temp);//去掉String值的空格
       		
     			sb.append(StringUtils.left(FuiouConfig.MCHNT_CD, 15))//商户号
     	    	.append("|")
     	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"),30))//交易流水号
     	    	.append("|")
     	    	.append(StringUtils.left((String)temp.get("mchntTxnSsn"),8))//交易日期
     	    	.append("|")
     	    	.append(type)//交易类型
     	    	.append("|")
     	    	.append(Utils.isObjectNotEmpty(temp.get("itemNo"))&&!"null".equals(temp.get("itemNo").toString())?StringUtils.left((String)temp.get("itemNo"), 10):"")//项目编号
     	    	.append("|")
     	    	.append(StringUtils.left((String)temp.get("code"), 30))//合同编号
     	    	.append("|")
     	    	.append(StringUtils.left((String)temp.get("out_cust_no"), 50))//出账人存管账户系统用户名 
     	    	.append("|")
     	    	.append(StringUtils.left((String)temp.get("realname"), 50))//出账人平台用户名
     	    	.append("|")
     	    	.append(StringUtils.left(yuanToCent(temp.get("amount").toString()), 12))//金额
     	    	.append("|")
     	    	.append("")
     	    	.append("|")
     	    	.append("0".equals(businesstype) == true?"": StringUtils.left((String)temp.get("in_cust_no"), 50))//入账人存管账户系统用户名
     	    	.append("|")
     	    	.append("0".equals(businesstype) == true?"":StringUtils.left((String)temp.get("name"), 50))//入账人平台用户名
     	    	.append("|")
     	    	.append("")
     	    	.append("|")
     	    	.append("")
     	    	.append("|")
     	    	.append(businesstype)//业务类型
     	    	.append("|")
     	    	.append(ConfigReader.getConfig("paycompanyid"))
     	    	.append("|")
     	    	.append("\r\n");
     		}
     	}
     	return sb.toString();
     }
  	
  	/**
	 * 元转分
	 * @param amount
	 * @return
	 */
	public static String yuanToCent(String amount){
		return new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()+"";
	}
}
