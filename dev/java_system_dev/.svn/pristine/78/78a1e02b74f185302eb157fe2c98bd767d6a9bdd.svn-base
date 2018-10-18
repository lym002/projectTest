package com.jsjf.controller.finance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jsjf.model.system.SysUsersVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.member.DrCompanyFundsLogService;
import com.jsjf.service.member.JsCompanyAccountLogService;
import com.jsjf.service.member.JsThreeFundSituationService;
import com.jsjf.service.product.DrProductInfoService;

@Controller
@RequestMapping("/companyFundsLog")
public class DrCompanyFundsLogController{
	
	@Autowired
	private DrCompanyFundsLogService drCompanyFundsLogService;
	@Autowired
	private JsCompanyAccountLogService jsCompanyAccountLogService;
	@Autowired
	private JsThreeFundSituationService jsThreeFundSituationService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	private static transient Logger log = Logger.getLogger(DrCompanyFundsLogController.class);
	
	/**
	 * 跳转到公司收支记录列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toCompanyFundsLogList")
	public String toCompanyFundsLogList(Map<String,Object> model) {
		try {
			model.put("companyFundsType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("companyfunds")));
			model.put("fundtype",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("balanceType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/finance/drCompanyFundsLogList";
	}
	
	/**
	 * 显示公司收支记录列表数据
	 * @param drCompanyFundsLog
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/companyFundsLogList")
	@ResponseBody
	public PageInfo companyFundsLogList(DrCompanyFundsLog drCompanyFundsLog,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drCompanyFundsLogService.getCompanyFundsLogList(drCompanyFundsLog, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 统计收入支出
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/companyFundsLogSum")
	@ResponseBody
	public Map<String,Object> companyFundsLogSum(DrCompanyFundsLog drCompanyFundsLog) {
		return drCompanyFundsLogService.getDrCompanyFundsLogSum(drCompanyFundsLog);
	}

	/**
	 * 表单提交日期绑定 
	 * @param binder
	 */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  
    
    /**
	 * 导出公司收支记录列表
	 * @param model
	 * @return
     * @throws ParseException 
	 */
	@RequestMapping("/exportCompanyFundsLogList")
	public ModelAndView exportCompanyFundsLogList(HttpServletRequest req,
			@RequestParam(value="startDate",required=false) String startDate,
			@RequestParam(value="endDate",required=false) String endDate,
			@RequestParam(value="pcode",required=false) String pcode,
			@RequestParam(value="type",required=false) Integer type,
			@RequestParam(value="fundTypes",required=false) String fundTypes) throws ParseException {
		
		DrCompanyFundsLog drCompanyFundsLog=new DrCompanyFundsLog();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(startDate!=null && !startDate.equals(""))	drCompanyFundsLog.setStartDate(sdf.parse(startDate));
		if(endDate!=null && !endDate.equals(""))	drCompanyFundsLog.setEndDate(sdf.parse(endDate));
		if(pcode!=null && !pcode.equals(""))	drCompanyFundsLog.setPcode(pcode);
		if(type!=null && !type.equals(""))	drCompanyFundsLog.setType(type);
		if(fundTypes!=null && !fundTypes.equals(""))	drCompanyFundsLog.setFundTypes(fundTypes);
		
		PageInfo pi = new PageInfo(1,1000000);
		BaseResult result = drCompanyFundsLogService.getCompanyFundsLogList(drCompanyFundsLog, pi);
		List<DrCompanyFundsLog> list=(List<DrCompanyFundsLog>) pi.getRows();
		
		String[] title = new String[]{"产品编号","交易类型","收入/支出","金额","用户姓名","用户手机号","备注","交易时间"};
		Integer[] columnWidth = new Integer[]{30,20,20,20,20,20,40,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(DrCompanyFundsLog companyFundsLog : list){
			lc = new ArrayList<Object>();
			if(companyFundsLog.getPcode()!=null){
				lc.add(companyFundsLog.getPcode());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getFundTypeName()!=null){
				lc.add(companyFundsLog.getFundTypeName());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getTypeName()!=null){
				lc.add(companyFundsLog.getTypeName());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getAmount()!=null){
				lc.add(companyFundsLog.getAmount());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getRealName()!=null){
				lc.add(companyFundsLog.getRealName());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getPhone()!=null){
				lc.add(companyFundsLog.getPhone());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getRemark()!=null){
				lc.add(companyFundsLog.getRemark());
			}else{
				lc.add("");
			}
			if(companyFundsLog.getAddTime()!=null){
				lc.add(sdf.format(companyFundsLog.getAddTime()));
			}else{
				lc.add("");
			}
			tableList.add(lc);
		}
		Map<String,Object> map=new HashMap();
		map.put("excelName", "companyFundsLog_"+System.currentTimeMillis()+".xls");
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		log.info("系统审计：导出第三方资金支出情况——"+vo.getName());
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	
	/**
	 * 跳转到平台账户查询
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPlatformAccount")
	public String toPlatformAccount(Map<String,Object> model) {
		return "system/finance/platformAccount";
	}
	
	/**
	 * 查询平台账户
	 * @param map
	 * @return
	 */
	@RequestMapping("/getPlatformAccount")
	@ResponseBody
	public String getPlatformAccount(HttpServletRequest req) {
		/*	BaseResult br = FuiouConfig.getPlatformAccount();
		List<Map<String, Object>> list=new ArrayList<>();
		if (br.isSuccess()) {
			List li=(List) br.getMap().get("result");
			for (Object object : li) {
				Map<String, Object> map=(Map<String, Object>) object;
				StringBuffer str = new StringBuffer();
				str.append(map.get("acnt_nm")).append(",").append(map.get("fuiou_acnt"));
				map.put("fuiouAccount",str.toString());
				map.put("ct_balance", FuiouConfig.centToYuan(map.get("ct_balance").toString()));
				map.put("ca_balance", FuiouConfig.centToYuan(map.get("ca_balance").toString()));
				map.put("cf_balance", FuiouConfig.centToYuan(map.get("cf_balance").toString()));
				map.put("cu_balance", FuiouConfig.centToYuan(map.get("cu_balance").toString()));
				list.add(map);		
			}	
		}
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();*/
		return "";
	}
	

	/**
	 * 划拨
	 * @param map
	 * @return
	 */
	@RequestMapping("/remitAccount")
	@ResponseBody
	public String remitAccount(HttpServletRequest req,String out_user_id,String out_fuiou_acnt,String in_user_id,String in_fuiouAccount,BigDecimal amt) {
	/*	Map<String, String> params = new HashMap<String, String>();
		String remitMchntTxnSsn = Utils.createOrderNo(6, 1, "");// 流水号
		 
		String[] in = in_fuiouAccount.split(",");//收款人
		params.put("in_fuiou_acnt", in[1]);
		params.put("in_user_id", in_user_id);
	      
		params.put("out_user_id", out_user_id);
		params.put("out_fuiou_acnt", out_fuiou_acnt);
		params.put("mchnt_txn_ssn", remitMchntTxnSsn);
			
		params.put("amt", "" + amt.multiply(new BigDecimal(100)).intValue());// 精确到分
		params.put("busi_tp", "Q");// 平台账户划拨
		BaseResult br = FuiouConfig.transferBu(params);
		if (!br.isSuccess()) {
			return br.getErrorMsg();
		}*/
		return "success";
	}
	
	/**
	 * 公司账户查询
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/toCompanyAccountList")	
	public String toCompanyAccountList(HttpServletRequest req,Map<String,Object> model){
		try {
			model.put("companyfunds",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("companyfunds")));
			model.put("type",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("balanceType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/finance/companyAccountList";
	}
	
	/**
	 * 公司账户查询
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/getCompanyAccount")	
	@ResponseBody
	public String getFundsRecord(HttpServletRequest req,Integer rows,Integer page,
			String companyfunds,String startaddTime,String endaddTime,String type,String channelType,String fullName){
		PageInfo pi = new PageInfo(page, rows);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		if(companyfunds!=null && !companyfunds.equals("")){
			map.put("companyfunds",companyfunds);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		if(type!=null && !type.equals("")){
			map.put("type",type);
		}
		if(channelType!=null && !channelType.equals("")){
			map.put("channelType",channelType);
		}
		if(fullName!=null && !fullName.equals("")){
			map.put("fullName",fullName);
		}
		List<Map<String, Object>> list= jsCompanyAccountLogService.getCompanyAccountLog(map);
		int count=jsCompanyAccountLogService.getCompanyAccountLogCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	
	/**
	 * 统计收入支出
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/companyAccountSum")
	@ResponseBody
	public Map<String,Object> companyAccountSum(HttpServletRequest req,String companyfunds,
			String startaddTime,String endaddTime,String type,String channelType,String fullName) {
		BigDecimal sr=new BigDecimal(0);
		BigDecimal zc=new BigDecimal(0);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		if(companyfunds!=null && !companyfunds.equals("")){
			map.put("companyfunds",companyfunds);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		if(channelType!=null && !channelType.equals("")){
			map.put("channelType",channelType);
		}
		if(fullName!=null && !fullName.equals("")){
			map.put("fullName",fullName);
		}
		if(type!=null && !type.equals("")){
			if(type.equals("1")){//收入
				sr=jsCompanyAccountLogService.getCompanyAccountSRSum(map);
			}else{//支出
				zc=jsCompanyAccountLogService.getCompanyAccountZCSum(map);
			}
			map.put("type",type);
		}else{
			sr=jsCompanyAccountLogService.getCompanyAccountSRSum(map);
			zc=jsCompanyAccountLogService.getCompanyAccountZCSum(map);
		}	
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("sr", sr);
		result.put("zc", zc);
		return result;
	}
	
	/**
	 * 公司账户
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/exportcompanyAccount",produces = "text/html; charset=utf-8")	
	@ResponseBody
	public ModelAndView exportcompanyAccount(HttpServletRequest req,
			String companyfunds,String startaddTime,String endaddTime,String type,String channelType,String fullName) throws UnsupportedEncodingException{
		BigDecimal sr=new BigDecimal(0);
		BigDecimal zc=new BigDecimal(0);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset",0); 
		map.put("limit",100000000);
		if(companyfunds!=null && !companyfunds.equals("")){
			map.put("companyfunds",companyfunds);
		}
		if(startaddTime!=null && !startaddTime.equals("")){
			map.put("startaddTime",startaddTime);
		}
		if(endaddTime!=null && !endaddTime.equals("")){
			map.put("endaddTime",endaddTime);
		}
		if(type!=null && !type.equals("")){
			map.put("type",type);
		}
		if(channelType!=null && !channelType.equals("")){
			map.put("channelType",channelType);
		}
		if(fullName!=null && !fullName.equals("")){
			map.put("fullName",new String(fullName.getBytes("ISO-8859-1"),"utf-8"));
		}
		List<Map<String, Object>> list= jsCompanyAccountLogService.getCompanyAccountLog(map);
		
		sr=jsCompanyAccountLogService.getCompanyAccountSRSum(map);
		zc=jsCompanyAccountLogService.getCompanyAccountZCSum(map);
		
		String[] title = new String[]{"交易时间","资金类型","收入","支出","备注","产品","通道"};
		Integer[] columnWidth = new Integer[]{20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map m : list){
			lc = new ArrayList<Object>();
			lc.add(m.get("addTime")==null?"":m.get("addTime"));//交易时间
			lc.add(m.get("companyfundsName")==null?"":m.get("companyfundsName"));//资金类型
			lc.add(m.get("sr")==null?"":m.get("sr"));//收入
			lc.add(m.get("zc")==null?"":m.get("zc"));//支出
			lc.add(m.get("remark")==null?"":m.get("remark"));//备注	
			lc.add(m.get("fullName")==null?"":m.get("fullName"));//备注	
			lc.add(m.get("channelTypeName")==null?"":m.get("channelTypeName"));//通道	
			tableList.add(lc);
		}
		lc = new ArrayList<Object>();
		lc.add("小计");//交易时间
		lc.add("");//资金类型
		lc.add(sr);//收入
		lc.add(zc);//支出
		lc.add("");//余额
		lc.add("");//备注
		lc.add("");//通道	
		tableList.add(lc);
		Map<String,Object> param=new HashMap();
		param.put("excelName", "companyAccount_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	@RequestMapping(value= "/insertcompanyAccount")
	@ResponseBody
	public BaseResult insertcompanyAccount(HttpServletRequest req,Integer companyfunds,
			String addTime,Integer type,Integer channelType,BigDecimal amount,BigDecimal balance,String remark,Integer id,String fullName ) throws ParseException {
		BaseResult br=new BaseResult();
		JsCompanyAccountLog accountLog=new JsCompanyAccountLog();
		
		if(fullName!=null){
			Map<String, Object>map=new HashMap<>();
			map.put("fullName", fullName);
			DrProductInfo drProductInfo=drProductInfoService.getDrProductInfoByMap(map);
			if(drProductInfo==null){
				br.setSuccess(false);
				br.setErrorMsg("产品不存在");
				return br;
			}else{
				accountLog.setPid(drProductInfo.getId());
			}
		}
		accountLog.setAmount(amount);
		/*accountLog.setBalance(balance);*/
		accountLog.setChannelType(channelType);
		accountLog.setType(type);
		accountLog.setCompanyfunds(companyfunds);

		if(remark!=null && !remark.equals("")){
			accountLog.setRemark(remark);
		}else{
			accountLog.setRemark("");
		}
		accountLog.setRemark(remark);
		accountLog.setStatus(3);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		accountLog.setAddTime(sdf.parse(addTime));
		if(id==null){//新增
			jsCompanyAccountLogService.insertCompanyAccountLog(accountLog);
		}else{//修改
			accountLog.setId(id);
			jsCompanyAccountLogService.updateCompanyAccountLog(accountLog);
		}
		br.setSuccess(true);
		br.setMsg("操作成功");
		return br;
	}
	
	/**
	 * 第三方资金支出情况
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/toThreeFundSituationList")	
	public String toThreeFundSituationList(HttpServletRequest req,Map<String,Object> model){
		return "system/finance/threeFundSituationList";
	}
	
	/**
	 * 公司账户查询
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/getThreeFundSituation")	
	@ResponseBody
	public String getThreeFundSituation(HttpServletRequest req,Integer rows,Integer page,
			String startdatetime,String enddatetime){
		PageInfo pi = new PageInfo(page, rows);
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		if(startdatetime!=null && !startdatetime.equals("")){
			map.put("startdatetime",startdatetime);
		}
		if(enddatetime!=null && !enddatetime.equals("")){
			map.put("enddatetime",enddatetime);
		}
		List<Map<String, Object>> list= jsThreeFundSituationService.getThreeFundSituation(map);
		int count=jsThreeFundSituationService.getThreeFundSituationCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 统计收入支出
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/getThreeFundSituationSum")
	@ResponseBody
	public Map<String,Object> getThreeFundSituationSum(HttpServletRequest req,String startdatetime,String enddatetime
		) {
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		if(startdatetime!=null && !startdatetime.equals("")){
			map.put("startdatetime",startdatetime);
		}
		if(enddatetime!=null && !enddatetime.equals("")){
			map.put("enddatetime",enddatetime);
		}
		Map<String, Object> result=jsThreeFundSituationService.getThreeFundSituationSum(map);
		return result;
	}
	
	/**
	 * 修改
	 * @param drCompanyFundsLog
	 * @return
	 */
	@RequestMapping(value= "/updateThreeFundSituation")
	@ResponseBody
	public BaseResult updateThreeFundSituation(HttpServletRequest req,
			Integer id,
			BigDecimal incomeAmount,
			BigDecimal standbyRechargeAmount,
			BigDecimal loanAmount,
			BigDecimal poundageAmount,
			BigDecimal standbyWithdrawalAmount,
			BigDecimal activityAmount
		) {
		BaseResult br=new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		if(id!=null ){
			map.put("id",id);
		}
		if(incomeAmount!=null ){
			map.put("incomeAmount",incomeAmount);
		}
		if(standbyRechargeAmount!=null ){
			map.put("standbyRechargeAmount",standbyRechargeAmount);
		}
		if(loanAmount!=null ){
			map.put("loanAmount",loanAmount);
		}
		if(poundageAmount!=null ){
			map.put("poundageAmount",poundageAmount);
		}
		if(standbyWithdrawalAmount!=null ){
			map.put("standbyWithdrawalAmount",standbyWithdrawalAmount);
		}
		if(activityAmount!=null ){
			map.put("activityAmount",activityAmount);
		}
		
		jsThreeFundSituationService.updateThreeFundSituation(map);
		br.setMsg("操作成功");
		br.setSuccess(true);
		return br;
	}
	
	/**
	 * 导出第三方资金支出情况
	 * @param req
	 * @param rows
	 * @param page
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/exportThreeFundSituation")	
	@ResponseBody
	public ModelAndView exportThreeFundSituation(HttpServletRequest req,
			String startdatetime,String enddatetime){
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("offset",0); 
		map.put("limit",100000000);
		if(startdatetime!=null && !startdatetime.equals("")){
			map.put("startdatetime",startdatetime);
		}
		if(enddatetime!=null && !enddatetime.equals("")){
			map.put("enddatetime",enddatetime);
		}
		List<Map<String, Object>> list= jsThreeFundSituationService.getThreeFundSituation(map);
		Map<String, Object> result=jsThreeFundSituationService.getThreeFundSituationSum(map);
		
		String[] title = new String[]{"时间","投资交易额","客户充值","借款收款额","公司备付金充值","收入合计","收款放款额","客户提现","相关手续费","公司备付金提取","活动金","支出合计"};
		Integer[] columnWidth = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map m : list){
			lc = new ArrayList<Object>();
			lc.add(m.get("datetime")==null?"":m.get("datetime"));//时间
			lc.add(m.get("investAmount")==null?"":m.get("investAmount"));//投资交易额
			lc.add(m.get("rechargeAmount")==null?"":m.get("rechargeAmount"));//客户充值
			lc.add(m.get("incomeAmount")==null?"":m.get("incomeAmount"));//借款收款额
			lc.add(m.get("standbyRechargeAmount")==null?"":m.get("standbyRechargeAmount"));//公司备付金充值
			lc.add(m.get("srSum")==null?"":m.get("srSum"));//收入合计
			lc.add(m.get("loanAmount")==null?"":m.get("loanAmount"));//收款放款额
			lc.add(m.get("withdrawalAmount")==null?"":m.get("withdrawalAmount"));//客户提现
			lc.add(m.get("poundageAmount")==null?"":m.get("poundageAmount"));//相关手续费
			lc.add(m.get("standbyWithdrawalAmount")==null?"":m.get("standbyWithdrawalAmount"));//公司备付金提取
			lc.add(m.get("activityAmount")==null?"":m.get("activityAmount"));//活动金
			lc.add(m.get("zcSum")==null?"":m.get("zcSum"));//支出合计
			tableList.add(lc);
		}
		lc = new ArrayList<Object>();
		lc.add("合计");//时间
		lc.add(result.get("investAmount")==null?"":result.get("investAmount"));//投资交易额
		lc.add(result.get("rechargeAmount")==null?"":result.get("rechargeAmount"));//客户充值
		lc.add(result.get("incomeAmount")==null?"":result.get("incomeAmount"));//借款收款额
		lc.add(result.get("standbyRechargeAmount")==null?"":result.get("standbyRechargeAmount"));//公司备付金充值
		lc.add(result.get("srSum")==null?"":result.get("srSum"));//收入合计
		lc.add(result.get("loanAmount")==null?"":result.get("loanAmount"));//收款放款额
		lc.add(result.get("withdrawalAmount")==null?"":result.get("withdrawalAmount"));//客户提现
		lc.add(result.get("poundageAmount")==null?"":result.get("poundageAmount"));//相关手续费
		lc.add(result.get("standbyWithdrawalAmount")==null?"":result.get("standbyWithdrawalAmount"));//公司备付金提取
		lc.add(result.get("activityAmount")==null?"":result.get("activityAmount"));//活动金
		lc.add(result.get("zcSum")==null?"":result.get("zcSum"));//支出合计
		tableList.add(lc);
		Map<String,Object> param=new HashMap();
		param.put("excelName", "threeFundSituation_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		log.info("系统审计：导出第三方资金支出情况——"+vo.getName());
		return new ModelAndView(new JXLExcelView(), param);
	}
}
