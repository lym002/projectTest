package com.jsjf.controller.finance;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberCrushService;
import com.jytpay.HttpClient431Util;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.utils.MapHelper;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;
import com.jytpay.vo.JYTWYResultData;
import com.jzh.FuiouConfig;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.DecipherWeb;
import com.reapal.utils.ReapalSubmit;

@Controller
@RequestMapping("/crush")
public class DrMemberCrushController{
	
	@Autowired
	private DrMemberCrushService drMemberCrushService;
	private static Logger log = Logger.getLogger(DrMemberCrushController.class);


	/**
	 * 跳转到充值待审列表
	 * @return
	 */
	@RequestMapping("/toMemberCrushList")
	public String toMemberCrushList(Map<String,Object> model) {
		return "system/finance/drMemberCrushList";
	}
	
	/**
	 * 显示充值待审列表数据
	 */
	@RequestMapping(value= "/memberCrushList")
	@ResponseBody
	public PageInfo memberCrushList(HttpServletRequest req, DrMemberCrush drMemberCrush,Integer page,Integer rows) {
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drMemberCrushService.getMemberCrushList(drMemberCrush, pi);
		log.info("系统审计:" + new Date()+"充值待审数据列表拉取： -【"+vo.getName()+"】！ rows："+rows+"   pages：" + page);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 充值待审-通过
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/memberCrushAudit")
	@ResponseBody
	public BaseResult memberCrushAudit(int id,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			drMemberCrushService.updateMemberCrushAudit(id,usersVo);
			result.setSuccess(true);
			result.setMsg("审核成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("审核失败！");
			e.getStackTrace();
		}
		return result;
	}
	
	/**
	 * 充值待审-拒绝
	 * @param drMemberCrush
	 * @param request
	 * @return
	 */
	@RequestMapping("/memberCrushRefuse")
	@ResponseBody
	public BaseResult memberCrushRefuse(DrMemberCrush drMemberCrush,HttpServletRequest request) {
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult br = new BaseResult();
		try {
			drMemberCrushService.updateMemberCrushRefuse(drMemberCrush,usersVo);
			br.setSuccess(true);
			br.setMsg("拒绝成功！");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("拒绝失败！");
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 跳转到充值记录列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMemberCrushRecordList")
	public String toMemberCrushRecordList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("crushstatus")));
			model.put("channel", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("channel")));
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("payType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/finance/drMemberCrushRecordList";
	}
	
	/**
	 * 显示充值记录列表数据
	 * @param drMemberCrush
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/memberCrushRecordList")
	@ResponseBody
	public PageInfo memberCrushRecordList(DrMemberCrush drMemberCrush,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drMemberCrushService.getMemberCrushRecordList(drMemberCrush, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示充值金额合计
	 * @param drMemberCrush
	 * @return
	 */
	@RequestMapping("/memberCrushRecordSum")
	@ResponseBody
	public Map<String,Object> memberCrushRecordSum(DrMemberCrush drMemberCrush,HttpServletRequest request) {
		Map<String,Object> model = new HashMap<String, Object>();
		NumberFormat nf = NumberFormat.getInstance();   
		nf.setGroupingUsed(false);  
		model.put("recordSum", nf.format(drMemberCrushService.getDrMemberCrushRecordSum(drMemberCrush)));
		/*try {
			if(request.getSession().getAttribute("JYTCrushBalance") == null){
				JYTSendData sendData = new JYTSendData();
				sendData.setMer_viral_acct(MockClientMsgBase.COLLECTION_ACCOUNT);
				sendData.setTran_code(MockClientMsgBase.QUERY_BALANCE_CODE);
	
				JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
				if("S0000000".equals(resultData.getResp_code())){
					model.put("JYTCrushBalance", nf.format(resultData.getBalance()));
					request.getSession().setAttribute("JYTCrushBalance", resultData.getBalance());
				}else if("E9000017".equals(resultData.getResp_code())){
					model.put("JYTCrushBalance",
						nf.format(request.getSession().getAttribute("JYTCrushBalance") == null ? resultData.getBalance() : request.getSession().getAttribute("JYTCrushBalance")));
				}
			}else{
				model.put("JYTCrushBalance",nf.format(request.getSession().getAttribute("JYTCrushBalance")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return model;
	}
	
	/**
	 * 后台充值
	 * @param drMemberCrush
	 * @param request
	 * @return
	 */
	@RequestMapping("/memberCrushAdd")
	@ResponseBody
	public BaseResult memberCrushAdd(DrMemberCrush drMemberCrush,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		result = drMemberCrushService.addMemberCrush(drMemberCrush,usersVo);
		return result;
	}
	
    /**
     * 测试查询接口
     * @param req
     * @return
     */
	@RequestMapping("/queryPayResult")
	@ResponseBody
	public BaseResult queryPayResult(int id) {
		BaseResult result = new BaseResult();
		Map<String,String> map = new HashMap<String, String>();
		DrMemberCrush drMemberCrush = drMemberCrushService.getDrMemberCrushById(id);
		try{
			if(1 == drMemberCrush.getType()){
				JYTSendData sendData = new JYTSendData();
				sendData.setOri_tran_flowid(drMemberCrush.getPayNum());
				sendData.setTran_code(MockClientMsgBase.QUERY_COLLECTION_TRAN_CODE);
				JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
				if("S0000000".equals(resultData.getResp_code())){
					map.put("dtorder", Utils.format(drMemberCrush.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
					map.put("noorder", drMemberCrush.getPayNum());
					map.put("moneyorder", drMemberCrush.getAmount().toString());
					map.put("paytype", "认证支付");
					if("S0000000".equals(resultData.getTran_resp_code()) && "01".equals(resultData.getTran_state())){
						map.put("resultpay", "成功");
						result.setSuccess(true);
					}else if("E0000000".equals(resultData.getTran_resp_code())){
						map.put("resultpay", "处理中");
						result.setSuccess(true);
					}else if("S0000000".equals(resultData.getTran_resp_code()) && "00".equals(resultData.getTran_state())){
						map.put("resultpay", "处理中");
						result.setSuccess(true);
					}else{
						result.setSuccess(false);
						result.setErrorMsg(resultData.getTran_resp_desc());
					}
					result.setMap(map);
				}else{
					result.setSuccess(false);
					result.setErrorMsg(resultData.getTran_resp_desc());
				}
			}else if(2 == drMemberCrush.getType()){
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("tranCode","TN2001");
				paramMap.put("version","1.0.0");
				paramMap.put("charset","utf-8");
				paramMap.put("merchantId",MockClientMsgBase.WY_MERCHANT_ID);
				paramMap.put("oriMerOrderId",drMemberCrush.getPayNum());
				paramMap.put("orderType","0");
				paramMap.put("signType","SHA256");
				paramMap.put("key",MockClientMsgBase.WY_KEY);
				paramMap = MapHelper.signMap(paramMap);
				paramMap.remove("key");
				// 获取执行结果
				String res = HttpClient431Util.doPost(paramMap, MockClientMsgBase.WY_QUERY_URL);
				if(!Utils.strIsNull(res)){
					JYTWYResultData resultDate = JSON.parseObject(res, JYTWYResultData.class);
					if("S0000000".equals(resultDate.getRespCode())){
						map.put("dtorder", Utils.format(drMemberCrush.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
						map.put("noorder", drMemberCrush.getPayNum());
						map.put("moneyorder", drMemberCrush.getAmount().toString());
						map.put("paytype", "网银支付");
						//00-初始 01-支付中，02-支付成功，03-支付失败，04-过期订单 ,05-撤销成功,06-作废订单
						if("02".equals(resultDate.getTranState())){
							map.put("resultpay", "成功");
							result.setSuccess(true);
						}else if("01".equals(resultDate.getTranState())){
							map.put("resultpay", "处理中");
							result.setSuccess(true);
						}else if("00".equals(resultDate.getTranState())){
							map.put("resultpay", "初始");
							result.setSuccess(true);
						}else if("03".equals(resultDate.getTranState())){
							map.put("resultpay", "支付失败");
							result.setSuccess(true);
						}else if("04".equals(resultDate.getTranState())){
							map.put("resultpay", "过期订单");
							result.setSuccess(true);
						}else if("05".equals(resultDate.getTranState())){
							map.put("resultpay", "撤销成功");
							result.setSuccess(true);
						}else if("06".equals(resultDate.getTranState())){
							map.put("resultpay", "作废订单");
							result.setSuccess(true);
						}
						result.setMap(map);
					}else{
						result.setSuccess(false);
						result.setErrorMsg(resultDate.getRespDesc());
					}
				}else{
					result.setSuccess(false);
					result.setErrorMsg("系统异常");
					return result;
				}
			}else if(4 == drMemberCrush.getType()){
				Map<String,String> reapalData = new HashMap<>();
				reapalData.put("merchant_id", ReapalConfig.getMerchant_id());
				reapalData.put("version", ReapalConfig.getVersion());
				reapalData.put("order_no", drMemberCrush.getPayNum());
				//请求接口
				String url = "/fast/search";
				//返回结果
				String post = ReapalSubmit.buildSubmit(reapalData, ReapalConfig.getMerchant_id(),url);		    
			    //解密返回的数据
			    String res = DecipherWeb.decryptData(post);
			    
			    Map<String,String> jsonMap = (Map<String, String>) JSONObject.parse(res);
				
			    map.put("dtorder", Utils.format(drMemberCrush.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("noorder", drMemberCrush.getPayNum());
				map.put("moneyorder", drMemberCrush.getAmount().toString());
				map.put("paytype", "认证支付");
				result.setSuccess(true);
			    if("0000".equals(jsonMap.get("result_code")) && "completed".equals(jsonMap.get("status"))){ //status =completed：交易完成、failed：支付失败、processing:交易处理中wait : 等待买家付款;closed:订单关闭
					map.put("resultpay", "交易完成");
					result.setMap(map);
				}else if("3015".equals(jsonMap.get("result_code"))){
					map.put("resultpay", jsonMap.get("result_msg"));
					result.setMap(map);		
				}else if("failed".equals(jsonMap.get("status"))){
					map.put("resultpay", "支付失败");
					result.setMap(map);		
				}else if("processing".equals(jsonMap.get("status"))){
					map.put("resultpay", "交易处理中");
					result.setMap(map);		
				}else if("wait".equals(jsonMap.get("status"))){
					map.put("resultpay", "等待买家付款");
					result.setMap(map);		
				}else if("closed".equals(jsonMap.get("status"))){
					map.put("resultpay", "订单关闭");
					result.setMap(map);		
				}else{
					result.setSuccess(false);
					result.setErrorMsg("其他情况");
					return result;
				}
			}else if(5 == drMemberCrush.getType() || 6 == drMemberCrush.getType()){
				Map<String, String> param = new HashMap<>();
				param.put("txn_ssn", drMemberCrush.getPayNum());
				param.put("mchnt_txn_ssn", Utils.createOrderNo(6, drMemberCrush.getUid(), ""));
				param.put("busi_tp", "PW11");
				param.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), 1), "yyyy-MM-dd")+" 00:00:00");
				param.put("end_time",  Utils.format(Utils.getDayNumOfAppointDate(drMemberCrush.getAddTime(), -1), "yyyy-MM-dd")+" 23:59:59");
				
				result = FuiouConfig.QueryCzTx(param);
				
				if(result.isSuccess()){
					map.put("resultpay", result.getMsg());
				}else {
					map.put("resultpay",result.getErrorMsg());
				}
				//订单 不是成功的
				if(drMemberCrush.getStatus()!=1){
					drMemberCrushService.updateFuiouCrush(drMemberCrush.getPayNum(), result);
				}
				
				map.put("dtorder", Utils.format(drMemberCrush.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("noorder", drMemberCrush.getPayNum());
				map.put("moneyorder", drMemberCrush.getAmount().toString());
				map.put("paytype", 5 == drMemberCrush.getType()?"存管认证支付":"存管网银支付");
				result.setMap(map);
				result.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorMsg("系统异常");
			return result;
		}
        return result;
	}
	
    /**
     * 批量跑充值
     */
	@RequestMapping("/updatePayResult")
	public void updatePayResult(){
		try {
			drMemberCrushService.updatePayResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/updateOnePayResult")
	public void updateOnePayResult(String paynum){
		try {
			drMemberCrushService.updatePayResult(paynum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportMemberCrushRecord")
	public ModelAndView exportMemberCrushRecord(DrMemberCrush drMemberCrush,Integer page,Integer rows) throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		
		
		
		drMemberCrush.setRealName(java.net.URLDecoder.decode(drMemberCrush.getRealName(),"utf-8"));
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		BaseResult result = drMemberCrushService.getMemberCrushRecordList(drMemberCrush, pi);
		pi = (PageInfo) result.getMap().get("page");
		List<DrMemberCrush> rowsList = (List<DrMemberCrush>)pi.getRows();
		String[] title = new String[]{"订单号","用户姓名 ","用户手机号","充值金额","充值时间","充值银行","充值类型","充值渠道","充值状态","备注"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(DrMemberCrush crush:rowsList){
			lc = new ArrayList<Object>();
			lc.add(crush.getPayNum());
			lc.add(crush.getRealName()==null?"":crush.getRealName());
			lc.add(crush.getPhone());
			lc.add(crush.getAmount());
			lc.add(Utils.getparseDate(crush.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(crush.getBankName());
			lc.add(crush.getTypeName());
			lc.add(crush.getChannelName());
			lc.add(crush.getStatusName());
			lc.add(crush.getRemark());
			tableList.add(lc);
		}
		
		param.clear();
		param.put("excelName", "member_crush_record_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
		
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
}
