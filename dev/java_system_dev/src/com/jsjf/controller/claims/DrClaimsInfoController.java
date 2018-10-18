package com.jsjf.controller.claims;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.jsjf.service.claims.BypFinancialService;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.model.claims.DrAuditInfo;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.claims.DrClaimsFinanc;
import com.jsjf.model.claims.DrClaimsGuarantee;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.claims.DrClaimsPic;
import com.jsjf.model.claims.DrClaimsShareholder;
import com.jsjf.model.claims.JsClaimsAuditEdit;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.product.DrProductInfoService;
import com.jzh.FuiouConfig;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.Md5Utils;

@Controller
@RequestMapping("/claims")
public class DrClaimsInfoController{
	
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private DrMemberCarryService drMemberCarryService;
	@Autowired
	private DrProductInfoService drProductInfoService; 
    @Autowired
    private BypFinancialService bypFinancialServiceImpl;
	/**
	 * 跳转到债权列表
	 */
	@RequestMapping("/toDrClaimsLoanList")
	public String toDrClaimsLoanList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("claimsStatus")));
			model.put("bankCode", drClaimsInfoService.getFuiouBankCode());
			model.put("city", drClaimsInfoService.getFuiouAreaCity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/drClaimsInfoList";
	}
	
	@RequestMapping(value= "/getCityList")
	@ResponseBody
	public List<Map<String,Object>> getCityList() {
		return drClaimsInfoService.getFuiouAreaCity();
	}
	@RequestMapping(value= "/getBankCode")
	@ResponseBody
	public List<Map<String,Object>> getBankCode() {
		return drClaimsInfoService.getFuiouBankCode();
	}
	
	/**
	 * 显示债权列表数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drClaimsLoanList")
	@ResponseBody
	public PageInfo drClaimsLoanList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drClaimsInfoService.getDrClaimsLoanList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到债权添加页面
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddClaimsInfo")
	public String toAddClaimsInfo(HttpServletRequest req,Map<String,Object> model){
		try {
			model.put("billType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("certificateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("certificateType")));
			model.put("sex",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("sex")));
			model.put("shareholderType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("shareholderType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("repayDeadline",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayDeadline")));
			model.put("dateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("dateType")));
			model.put("pawnType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("pawnType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("cardFlag",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("cardFlag")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/addClaimsInfo";
	}
	
	/**
	 * 验证添加产品编号
	 * @param no
	 * @return BaseResult
	 */
	@RequestMapping(value= "/validatorProductNo")
	@ResponseBody
	public BaseResult validatorProductNo(String no) {
		BaseResult br = new BaseResult();
		try{
			List<DrClaimsLoan> list = drClaimsInfoService.getDrClaimsLoanByMap(no);
			if(Utils.isEmptyList(list)){
				br.setSuccess(true);
			}else{
				br.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	
	/**
	 * 验证修改产品编号
	 * @param no
	 * @param oldNo
	 * @return BaseResult
	 */
	@RequestMapping(value= "/updateValidatorProductNo")
	@ResponseBody
	public BaseResult updateValidatorProductNo(String no,String oldNo) {
		BaseResult br = new BaseResult();
		try{
			List<DrClaimsLoan> list = drClaimsInfoService.getDrClaimsLoanByMap(no);
			if(no.equals(oldNo)){
				if(list.size() >= 2){
					br.setSuccess(false);
				}else{
					br.setSuccess(true);
				}
			}else{
				if(Utils.isEmptyList(list)){
					br.setSuccess(true);
				}else{
					br.setSuccess(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 债权同步接口（与代收付对接）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/synClaimsInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String synClaimsInfo(String data) {
		BaseResult br = new BaseResult();

		net.sf.json.JSONObject  jasonObject = net.sf.json.JSONObject.fromObject(data);
		if(Md5Utils.validateMD5Sign(jasonObject.getString("payLoanNo"), ReapalConfig.getKey(), jasonObject.getString("signature"))){
			net.sf.json.JSONObject drClaimsLoan = jasonObject.getJSONObject("drClaimsLoan");
			Map<String, Class> mapper = new HashMap<>();//在JSONObject.toBean的时候如果转换的类中有集合,可以先定义Map<String, Class> classMap
			mapper.put("drClaimsPic", DrClaimsPic.class);
			
			DrClaimsLoan dcl= (DrClaimsLoan) net.sf.json.JSONObject.toBean(drClaimsLoan,DrClaimsLoan.class,mapper); 
			
			String payLoanNo = jasonObject.getString("payLoanNo");
			String name = StringUtils.left((String)drClaimsLoan.get("name"), 50);
			String no = StringUtils.left((String)drClaimsLoan.get("no"), 100);

			dcl.setName(name);
			dcl.setNo(no);
			dcl.setPayLoanNo(payLoanNo);
			dcl.setClaimsOriginate(2);
			dcl.setStatus(1);
//			MultipartFile[] claimsFiles =  (MultipartFile[]) jasonObject.get("claimsFiles");
			MultipartFile[] claimsFiles = null;
			try {
				drClaimsInfoService.insertDrClaimsInfoForPay(dcl,claimsFiles);
				
			} catch (Exception e) {
				e.printStackTrace();
				br.setSuccess(false);
				br.setErrorCode("9999");
			}
			br.setSuccess(true);
		}else{
			br.setSuccess(false);
			br.setErrorCode("0000");//MD5校验码不匹配
		}
		return JSON.toJSONString(br); 
	}


	/**
	 * 添加债权信息
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/addClaimsInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addClaimsInfo(DrClaimsLoan drClaimsLoan,HttpServletRequest request,
			@RequestParam MultipartFile[] claimsFiles){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			
			for(int i=0;i<claimsFiles.length;i++){
				String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
				Pattern pattern = Pattern.compile(reg);
				Matcher matcher = pattern.matcher(claimsFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = claimsFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			
			List<DrClaimsLoan> list = drClaimsInfoService.getDrClaimsLoanByMap(drClaimsLoan.getNo());
			if(!Utils.isEmptyList(list)){
				br.setSuccess(false);
				br.setMsg("借款合同编号已存在!");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			drClaimsLoan.setAddUser(usersVo.getUserKy().intValue());
			drClaimsLoan.setStatus(1);
			drClaimsInfoService.insertDrClaimsInfo(drClaimsLoan,claimsFiles);
			br.setMsg("添加成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("添加失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到债权修改页面
	 * @param req
	 * @param model
	 * @param lid
	 * @return
	 */
	@RequestMapping("/toUpdateClaimsInfo")
	public String toUpdateClaimsInfo(HttpServletRequest req,Map<String,Object> model,Integer lid){
		try {
			model.put("billType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("certificateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("certificateType")));
			model.put("sex",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("sex")));
			model.put("shareholderType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("shareholderType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("repayDeadline",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayDeadline")));
			model.put("dateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("dateType")));
			model.put("pawnType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("pawnType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("whether",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("whether")));
			model.put("cardFlag",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("cardFlag")));
			
			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(lid);
			model.put("drClaimsLoan",  drClaimsLoan);
			
			if(Utils.isObjectNotEmpty(drClaimsLoan.getStartDate())){
				model.put("LoanStartDate", Utils.getparseDate(drClaimsLoan.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsLoan.getEndDate())){
				model.put("LoanEndDate", Utils.getparseDate(drClaimsLoan.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsBill drClaimsBill = drClaimsInfoService.getDrClaimsBillByLid(lid);
			model.put("drClaimsBill",  drClaimsBill);
			
			if(Utils.isObjectNotEmpty(drClaimsBill.getStartDate())){
				model.put("billStartDate", Utils.getparseDate(drClaimsBill.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsBill.getEndDate())){
				model.put("billEndDate", Utils.getparseDate(drClaimsBill.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsFinanc drClaimsFinanc = drClaimsInfoService.getDrClaimsFinancByLid(lid);
			if(drClaimsFinanc == null ) drClaimsFinanc = new DrClaimsFinanc();
			
			model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(lid));
			model.put("drClaimsProject",  drClaimsInfoService.getDrClaimsProjectByLid(lid));
			
			DrClaimsCustomer drClaimsCustomer = drClaimsInfoService.getDrClaimsCustomerByLid(lid);
			if(drClaimsCustomer == null) drClaimsCustomer = new DrClaimsCustomer();			
			
			
			List<DrClaimsGuarantee> drClaimsGuarantee = drClaimsInfoService.getDrClaimsGuaranteeByLid(lid);
			List<DrClaimsShareholder> drClaimsShareholder = drClaimsInfoService.getDrClaimsShareholderByLid(lid);
		
			
//			if(drClaimsLoan.getStatus() !=1 && drClaimsLoan.getStatus() !=3){
//				drClaimsLoan.setIsAuditEdit(1);	//债权审核通过后(包括,已使用,待还款,已还款),部分修改	
//				
//				Map<String,Object> map = new HashMap<String, Object>();
//				map.put("type", 0);//数据类型 0:暂存资料
//				map.put("statuses", new int[]{0,2});//0:待审核,1:审核通过,2:驳回,
//				map.put("lid", drClaimsLoan.getId());
//				map.put("offset", 0);
//				map.put("limit",1);
//				map.put("orders","id desc");
//				JsClaimsAuditEdit bean = null;
//				List<JsClaimsAuditEdit> list = drClaimsInfoService.selectJsClaimsAuditEditByMap(map);
//				
//				if(!Utils.isEmptyList(list)){
//					net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(list.get(0).getCache());
//					DrClaimsLoan dcls = (DrClaimsLoan) net.sf.json.JSONObject.toBean(object,DrClaimsLoan.class);
//					net.sf.json.JSONObject dcfJsonObject=net.sf.json.JSONObject.fromObject(object.get("drClaimsFinanc"));
//					
//					Map<String,Object> maps = this.ConverterParam(dcls, drClaimsLoan, drClaimsCustomer, drClaimsGuarantee, drClaimsShareholder, drClaimsFinanc);
//					
//					drClaimsGuarantee = (List<DrClaimsGuarantee>) maps.get("dcgList");
//					drClaimsShareholder = (List<DrClaimsShareholder>) maps.get("dcslist");
//					drClaimsFinanc = (DrClaimsFinanc) maps.get("dcf");
//					if(Utils.isObjectNotEmpty(dcfJsonObject.get("establishDate")))
//						drClaimsFinanc.setEstablishDate(new Date(Long.parseLong(dcfJsonObject.get("establishDate").toString())));
//					drClaimsCustomer = (DrClaimsCustomer) maps.get("dcc");
//				}
//			}
			
			if(Utils.isObjectNotEmpty(drClaimsFinanc)){
				if(Utils.isObjectNotEmpty(drClaimsFinanc.getEstablishDate())){
					model.put("establishDate", Utils.getparseDate(drClaimsFinanc.getEstablishDate(),"yyyy-MM-dd HH:mm:ss"));
				}
			}		
			
			model.put("drClaimsFinanc",  drClaimsFinanc);
			model.put("drClaimsCustomer",  drClaimsCustomer);
			model.put("drClaimsGuarantee",  drClaimsGuarantee);
			model.put("drClaimsShareholder",  drClaimsShareholder);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", lid);
			map.put("type", 1);
			map.put("sort", "ASC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(map));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/updateClaimsInfo";
	}
	
	/**
	 * 修改债权信息
	 * @param req
	 * @param DrClaimsLoan
	 * @param name
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/updateClaimsInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateClaimsInfo(DrClaimsLoan drClaimsLoan,HttpServletRequest request,
			@RequestParam MultipartFile[] claimsFiles,Integer claimsUsed){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			for(int i=0;i<claimsFiles.length;i++){
				String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
				Pattern pattern = Pattern.compile(reg);
				Matcher matcher = pattern.matcher(claimsFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = claimsFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			
			
			drClaimsLoan.setUpdUser(usersVo.getUserKy().intValue());
			
			drClaimsInfoService.updateDrClaimsInfo(drClaimsLoan,claimsFiles);
			
			br.setMsg("修改成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("修改失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到债权审核列表
	 */
	@RequestMapping("/toAuditDrClaimsLoanList")
	public String toAuditDrClaimsLoanList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("claimsStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/auditDrClaimsInfoList";
	}
	
	/**
	 * 显示债权审核列表数据
	 * @param DrClaimsLoan
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/auditDrClaimsLoanList")
	@ResponseBody
	public PageInfo auditDrClaimsLoanList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		if(drClaimsLoan.getStatus() == null){
			drClaimsLoan.setStatus(100);
		}
		BaseResult result = drClaimsInfoService.getDrClaimsLoanList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到债权审核页面
	 * @param req
	 * @param model
	 * @param lid
	 * @return
	 */
	@RequestMapping("/toAuditClaimsInfo")
	public String toAuditClaimsInfo(HttpServletRequest req,Map<String,Object> model,Integer lid){
		try {
			model.put("billType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("certificateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("certificateType")));
			model.put("sex",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("sex")));
			model.put("shareholderType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("shareholderType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("repayDeadline",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayDeadline")));
			model.put("dateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("dateType")));
			model.put("pawnType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("pawnType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("whether",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("whether")));
			model.put("auditType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("auditType")));
			model.put("cardFlag",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("cardFlag")));
			
			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(lid);
			model.put("drClaimsLoan",  drClaimsLoan);
			
			if(Utils.isObjectNotEmpty(drClaimsLoan.getStartDate())){
				model.put("LoanStartDate", Utils.getparseDate(drClaimsLoan.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsLoan.getEndDate())){
				model.put("LoanEndDate", Utils.getparseDate(drClaimsLoan.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsBill drClaimsBill = drClaimsInfoService.getDrClaimsBillByLid(lid);
			model.put("drClaimsBill",  drClaimsBill);
			
			if(Utils.isObjectNotEmpty(drClaimsBill.getStartDate())){
				model.put("billStartDate", Utils.getparseDate(drClaimsBill.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsBill.getEndDate())){
				model.put("billEndDate", Utils.getparseDate(drClaimsBill.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsFinanc drClaimsFinanc = drClaimsInfoService.getDrClaimsFinancByLid(lid);
			if(drClaimsFinanc == null ) drClaimsFinanc = new DrClaimsFinanc();
			
			model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(lid));
			model.put("drClaimsProject",  drClaimsInfoService.getDrClaimsProjectByLid(lid));
			
			DrClaimsCustomer drClaimsCustomer = drClaimsInfoService.getDrClaimsCustomerByLid(lid);
			if(drClaimsCustomer == null) drClaimsCustomer = new DrClaimsCustomer();			

			
			List<DrClaimsGuarantee> drClaimsGuarantee = drClaimsInfoService.getDrClaimsGuaranteeByLid(lid);
			List<DrClaimsShareholder> drClaimsShareholder = drClaimsInfoService.getDrClaimsShareholderByLid(lid);
		
					
			
			if(1 == drClaimsLoan.getIsAuditEdit()){		
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("type", 0);//数据类型 0:暂存资料
				map.put("statuses", new int[]{0,2});//0:待审核,1:审核通过,2:驳回,
				map.put("lid", drClaimsLoan.getId());
				map.put("offset", 0);
				map.put("limit",1);
				map.put("orders","id desc");
				JsClaimsAuditEdit bean = null;
				List<JsClaimsAuditEdit> list = drClaimsInfoService.selectJsClaimsAuditEditByMap(map);
				
				if(!Utils.isEmptyList(list)){
				
					net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(list.get(0).getCache());
					DrClaimsLoan dcls = (DrClaimsLoan) net.sf.json.JSONObject.toBean(object,DrClaimsLoan.class);
					net.sf.json.JSONObject dcfJsonObject=net.sf.json.JSONObject.fromObject(object.get("drClaimsFinanc"));
					
					Map<String,Object> maps = this.ConverterParam(dcls, drClaimsLoan, drClaimsCustomer, drClaimsGuarantee, drClaimsShareholder, drClaimsFinanc);
					drClaimsGuarantee = (List<DrClaimsGuarantee>) maps.get("dcgList");
					drClaimsShareholder = (List<DrClaimsShareholder>) maps.get("dcslist");
					drClaimsFinanc = (DrClaimsFinanc) maps.get("dcf");
					if(Utils.isObjectNotEmpty(dcfJsonObject.get("establishDate")))
						drClaimsFinanc.setEstablishDate(new Date(Long.parseLong(dcfJsonObject.get("establishDate").toString())));
					drClaimsCustomer = (DrClaimsCustomer) maps.get("dcc");
				}
				
			}
			if(Utils.isObjectNotEmpty(drClaimsFinanc)){
				if(Utils.isObjectNotEmpty(drClaimsFinanc.getEstablishDate())){
					model.put("establishDate", Utils.getparseDate(drClaimsFinanc.getEstablishDate(),"yyyy-MM-dd HH:mm:ss"));
				}
			}
			model.put("drClaimsCustomer",  drClaimsCustomer);
			model.put("drClaimsFinanc",  drClaimsFinanc);
			model.put("drClaimsGuarantee",  drClaimsGuarantee);
			model.put("drClaimsShareholder",  drClaimsShareholder);
			model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(lid));
			model.put("drClaimsProject",  drClaimsInfoService.getDrClaimsProjectByLid(lid));
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", lid);
			map.put("type", 1);
			map.put("sort", "ASC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(map));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/auditClaimsInfo";
	}
	private Map<String,Object> ConverterParam (DrClaimsLoan dcls,DrClaimsLoan dcln,
			 DrClaimsCustomer dcc,List<DrClaimsGuarantee> dcgList,List<DrClaimsShareholder> dcslist,DrClaimsFinanc dcf){
		
		//1
		  dcln.setName(dcls.getName());
		  dcln.setNo(dcls.getNo());
		  dcln.setLoanUse(dcls.getLoanUse());
		  dcln.setCardFlag(dcls.getCardFlag());
		  dcln.setLoanName(dcls.getLoanName());
		  dcln.setBankName(dcls.getBankName());
		  dcln.setBankNo(dcls.getBankNo());
		  dcln.setBankAddress(dcls.getBankAddress());
		//2	
		  DrClaimsCustomer dccs = dcls.getDrClaimsCustomer();
		  dcc.setName(dccs.getName());
		  dcc.setSex(dccs.getSex());
		  dcc.setCertificateNo(dccs.getCertificateNo());
		  dcc.setAddress(dccs.getAddress());
		  dcc.setBusinessNo(dccs.getBusinessNo());
		//3 
		  DrClaimsGuarantee[] dcgs = dcls.getDrClaimsGuarantee();
		  if(dcgList.size() > 0){
			  for(DrClaimsGuarantee dcgn:dcgList){
				  for(DrClaimsGuarantee dcg:dcgs){
					  if(dcgn.getId().intValue() == dcg.getId()){
						  dcgn.setIsPawn(dcg.getIsPawn());
						  dcgn.setPawnType(dcg.getPawnType());
						  dcgn.setAssessAmount(dcg.getAssessAmount());
						  dcgn.setIsAcceptance(dcg.getIsAcceptance());
						  dcgn.setGuarantor(dcg.getGuarantor());
						  dcgn.setIsGuarantee(dcg.getIsGuarantee());
						  dcgn.setGuaranteeType(dcg.getGuaranteeType());
						  dcgn.setGuarantee(dcg.getGuarantee());
						  dcgn.setIsFinanc(dcg.getIsFinanc());
						  dcgn.setFinancType(dcg.getFinancType());
						  dcgn.setGuaranteeAmount(dcg.getGuaranteeAmount());
						  break;
					  }
				  }
			  }
		  }else{
			  dcgList =  Arrays.asList(dcgs);
		  }		  		  
		
		 //4 
		  DrClaimsShareholder[] dcss = dcls.getDrClaimsShareholder();
		  if(dcslist.size()>0){
			  for(DrClaimsShareholder dcsn: dcslist){
				  for(DrClaimsShareholder dcs : dcss){
					  if(dcsn.getId().intValue() == dcs.getId()){
						  dcsn.setName(dcs.getName());
						  dcsn.setSex(dcs.getSex());
						  dcsn.setCertificateNo(dcs.getCertificateNo());
						  break;
					  }
				  }
			  }
		  }else{
			  dcslist =  Arrays.asList(dcss);
		  }
		  //5
	 	DrClaimsFinanc dcfs = dcls.getDrClaimsFinanc();
	 	dcf = dcfs;
	 	/*
		dcf.setCompanyName(dcfs.getCompanyName());
		dcf.setName(dcfs.getName());
		dcf.setSex(dcfs.getSex());
		dcf.setPhone(dcfs.getPhone());
		dcf.setCertificateType(dcfs.getCertificateType());
		dcf.setCertificateNo(dcfs.getCertificateNo());
		dcf.setMechanismNo(dcfs.getMechanismNo());
		dcf.setIndustryType(dcfs.getIndustryType());
		dcf.setBusinessNo(dcfs.getBusinessNo());
		dcf.setTaxNo(dcfs.getTaxNo());
		dcf.setCompanyPhone(dcfs.getCompanyPhone());
		dcf.setCompanyMail(dcfs.getCompanyMail());
		dcf.setRegisterFund(dcfs.getRegisterFund());
		dcf.setRegisterCurrency(dcfs.getRegisterCurrency());
		dcf.setEstablishDate(dcfs.getEstablishDate());
		dcf.setAddress(dcfs.getAddress());
		*/
	 	
	 	Map<String,Object> map = new HashMap<String, Object> ();
	 	map.put("dcgList", dcgList);
	 	map.put("dcslist", dcslist);
	 	map.put("dcf", dcf);
	 	map.put("dcc", dcc);
	 	return map;
	}
	
	
	
	/**
	 * 添加债权审核信息
	 * @param request
	 * @param lid
	 * @param opinion
	 * @param status 
	 * @return
	 */
	@RequestMapping(value="/addDrAuditInfo")
	@ResponseBody
	public BaseResult addDrAuditInfo(Integer lid,String opinion,Integer status,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			String opinionStr=new String((request.getParameter("opinion")).getBytes("iso-8859-1"),"utf-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrAuditInfo drAuditInfo = new DrAuditInfo();
			drAuditInfo.setFid(lid);
			drAuditInfo.setType(1);
			drAuditInfo.setOpinion(opinionStr);
			drAuditInfo.setStatus(status);
			drAuditInfo.setAddUser(usersVo.getUserKy().intValue());
			//查询审核的产品
			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(lid);
			
			if(Utils.isObjectNotEmpty(drClaimsLoan) && drClaimsLoan.getIsAuditEdit() == 1){
				drClaimsLoan.setUpdUser(usersVo.getUserKy().intValue());
				boolean flag = drClaimsInfoService.updateMaintenance(drClaimsLoan,drAuditInfo);
				br.setMsg(flag?"审核成功!":"审核失败!");
				br.setSuccess(flag);
			}else{
				drClaimsInfoService.insertDrAuditInfo(drAuditInfo);
			}
			br.setMsg("审核成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("审核失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 跳转到债权显示页面
	 * @param req
	 * @param model
	 * @param lid
	 * @return
	 */
	@RequestMapping("/toShowClaimsInfo")
	public String toShowClaimsInfo(HttpServletRequest req,Map<String,Object> model,Integer lid){
		try {
			model.put("billType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("certificateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("certificateType")));
			model.put("sex",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("sex")));
			model.put("shareholderType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("shareholderType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("repayDeadline",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayDeadline")));
			model.put("dateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("dateType")));
			model.put("pawnType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("pawnType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("guaranteeType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("guaranteeType")));
			model.put("whether",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("whether")));
			model.put("auditType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("auditType")));
			model.put("cardFlag",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("cardFlag")));
			
			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(lid);
			model.put("drClaimsLoan",  drClaimsLoan);
			
			if(Utils.isObjectNotEmpty(drClaimsLoan.getStartDate())){
				model.put("LoanStartDate", Utils.getparseDate(drClaimsLoan.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsLoan.getEndDate())){
				model.put("LoanEndDate", Utils.getparseDate(drClaimsLoan.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsBill drClaimsBill = drClaimsInfoService.getDrClaimsBillByLid(lid);
			model.put("drClaimsBill",  drClaimsBill);
			
			if(Utils.isObjectNotEmpty(drClaimsBill.getStartDate())){
				model.put("billStartDate", Utils.getparseDate(drClaimsBill.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drClaimsBill.getEndDate())){
				model.put("billEndDate", Utils.getparseDate(drClaimsBill.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			DrClaimsFinanc drClaimsFinanc = drClaimsInfoService.getDrClaimsFinancByLid(lid);
			model.put("drClaimsFinanc",  drClaimsFinanc);
			if(Utils.isObjectNotEmpty(drClaimsFinanc)){
				if(Utils.isObjectNotEmpty(drClaimsFinanc.getEstablishDate())){
					model.put("establishDate", Utils.getparseDate(drClaimsFinanc.getEstablishDate(),"yyyy-MM-dd HH:mm:ss"));
				}
			}
			
			model.put("drClaimsCustomer",  drClaimsInfoService.getDrClaimsCustomerByLid(lid));
			
			model.put("drClaimsGuarantee",  drClaimsInfoService.getDrClaimsGuaranteeByLid(lid));
			model.put("drClaimsShareholder",  drClaimsInfoService.getDrClaimsShareholderByLid(lid));
			
			model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(lid));
			model.put("drClaimsProject",  drClaimsInfoService.getDrClaimsProjectByLid(lid));
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", lid);
			map.put("type", 1);
			map.put("sort", "DESC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(map));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/showClaimsInfo";
	}
	
	/**
	 * 跳转到债权催收列表
	 */
	@RequestMapping("/toCollectionDrClaimsLoan")
	public String toCollectionDrClaimsLoan(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("claimsStatus")));
			model.put("recentlyDate", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("recentlyDate")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/collectionDrClaimsInfoList";
	}
	
	/**
	 * 显示债权催收列表数据
	 * @param DrSubjectInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/collectionDrClaimsLoanList")
	@ResponseBody
	public PageInfo collectionDrClaimsLoanList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		if(drClaimsLoan.getStatus() == null){
			drClaimsLoan.setStatus(200);
		}
		BaseResult result = drClaimsInfoService.getDrClaimsLoanList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示债权催收金额合计
	 * @param DrClaimsLoan
	 * @return
	 */
	@RequestMapping("/drClaimsLoanSum")
	@ResponseBody
	public Map<String,Object> drClaimsLoanSum(DrClaimsLoan drClaimsLoan) {
		Map<String,Object> model = new HashMap<String, Object>();
		model = drClaimsInfoService.getDrClaimsLoanSum(drClaimsLoan);
		return model;
	}
	
	/**
	 * 导出贷款信息
	 * @param req
	 * @param drClaimsLoan
	 * @return
	 */
	@RequestMapping("/exportLoanRecord")
	public ModelAndView exportLoanRecord(HttpServletRequest req,DrClaimsLoan drClaimsLoan){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = drClaimsInfoService.exportLoan(drClaimsLoan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}

	/**
	 * 跳转到债权还款列表
	 */
	@RequestMapping("/toRepayDrClaimsLoan")
	public String toRepayDrClaimsLoan(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("claimsStatus")));
			model.put("recentlyDate", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("recentlyDate")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/claims/repayDrClaimsInfoList";
	}
	
	/**
	 * 显示债权还款列表数据
	 * @param DrClaimsLoan
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/repayDrClaimsLoanList")
	@ResponseBody
	public PageInfo repayDrClaimsLoanList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		if(drClaimsLoan.getStatus() == null){
			drClaimsLoan.setStatus(200);
		}
		BaseResult result = drClaimsInfoService.getDrClaimsLoanList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	/**
	 * 修改债权还款状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateRepayClaimsInfo")
	@ResponseBody
	public BaseResult updateRepayClaimsInfo(Integer id){
		BaseResult br = new BaseResult();
		try {
			drClaimsInfoService.updateRepayStatusClaimsInfo(id);
			br.setMsg("还款成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("还款失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 修改债权批量还款状态
	 * @return
	 */
	@RequestMapping(value="/updateBatchRepayDrClaimsInfo")
	@ResponseBody
	public BaseResult updateBatchRepayDrClaimsInfo(Integer[] ids){
		BaseResult br = new BaseResult();
		try {
			drClaimsInfoService.updateBatchRepayDrClaimsInfo(ids);
			br.setMsg("发放成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("发放失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	
	/**
	 * 查询是否可以修改或审核
	 * @param request
	 * @param operate 区分是修改,审核
	 * @return
	 */
	@RequestMapping(value="/isOperate")
	@ResponseBody
	public BaseResult isOperate(Integer id,String operate,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			/*
			if("update".equals(operate)){
				if(drClaimsLoan.getStatus() != 1 && drClaimsLoan.getStatus() != 3){
					drClaimsLoan.setIsAuditEdit(1);
					drClaimsLoan.setLoanAmount(drClaimsLoan.getLoanAmount().multiply(new BigDecimal("10000")));
					drClaimsInfoService.updateDrClaimsLoan(drClaimsLoan);
				}
			}*/
			/**if("audit".equals(operate)){
				if(drClaimsLoan.getStatus() != 1 && drClaimsLoan.getStatus() != 3){
					br.setErrorMsg("该债权不可审核!");
					br.setSuccess(false);
					return br;
				}
			}
			*/
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("操作失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	
	
	/**
	 * 跳转到债权统计列表
	 */
	@RequestMapping("/toDrClaimsStatisList")
	public String toDrClaimsStatisList(Map<String,Object> model) {
		return "system/claims/drClaimsStatisList";
	}
	
	/**
	 * 显示债权统计列表数据
	 * @param DrClaimsLoan
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drClaimsStatisList")
	@ResponseBody
	public PageInfo drClaimsStatisList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drClaimsInfoService.getDrClaimsStatisList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 放款操作
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/addDrClaimsInfoAdvance")
	@ResponseBody
	public BaseResult addDrClaimsInfoAdvance(DrClaimsLoan drClaimsLoan,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrClaimsLoan claimsLoan = drClaimsInfoService.getDrClaimsLoanByid(drClaimsLoan.getId());
			if(drClaimsLoan.getStartDate().getTime() >= claimsLoan.getEndDate().getTime()){
				br.setErrorMsg("放款时间不可大于等于还款时间！");
				br.setSuccess(false);
				return br;
			}
			claimsLoan.setStartDate(drClaimsLoan.getStartDate());
			claimsLoan.setServiceCharge(drClaimsLoan.getServiceCharge());
			claimsLoan.setUpdUser(usersVo.getUserKy().intValue());
			
			if(Utils.isObjectEmpty(claimsLoan.getReceiveInterest())){
				Long counts = Utils.getQuot(claimsLoan.getEndDate(), claimsLoan.getStartDate())+1;
				claimsLoan.setLoanDeadline(counts.toString());
				BigDecimal rate = Utils.nwdDivide(Utils.nwdDivide(claimsLoan.getRate(),100),360);
				claimsLoan.setLoanAmount(claimsLoan.getLoanAmount().multiply(new BigDecimal(10000)));
				BigDecimal receiveInterest = claimsLoan.getLoanAmount().multiply(new BigDecimal(counts.intValue())).multiply(rate);
				claimsLoan.setReceiveInterest(receiveInterest.setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				br.setErrorMsg("该债权已放款!");
				br.setSuccess(false);
				return br;
			}
			drClaimsInfoService.updateDrClaimsLoan(claimsLoan);
			br.setMsg("操作成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("放款失败!");
			br.setSuccess(false);
		}
		return br;
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
	 * 修改资产的关闭开启状态
	 * @return 
	 */
   @RequestMapping(value="/updateShowOffStatusBtn")
   @ResponseBody
    public BaseResult updateShowOffStatusBtn(Integer id){
    	BaseResult br = new BaseResult();
		try {
			
			drClaimsInfoService.updateShowOffStatusBtn(id);
			br.setMsg("操作成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("操作失败!");
			br.setSuccess(false);
		}
		return br;
    }
   /**
    * 修改资产为开启状态
    * @return 
    */
   @RequestMapping(value="/updateShowOnStatusBtn")
   @ResponseBody
   public BaseResult updateShowOnStatusBtn(Integer id){
	   BaseResult br = new BaseResult();
	   try {
		   
		   drClaimsInfoService.updateShowOnStatusBtn(id);
		   br.setMsg("操作成功!");
		   br.setSuccess(true);
	   } catch (Exception e) {
		   e.printStackTrace();
		   br.setMsg("操作失败!");
		   br.setSuccess(false);
	   }
	   return br;
   }
   /**
	 * 导出债权记录信息
	 * @param req
	 * @param drClaimsLoan
	 * @return
	 */
   @RequestMapping("/exportClaimsLoanList")
	public ModelAndView exportClaimsLoanList(HttpServletRequest req,DrClaimsLoan drClaimsLoan) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//两种日期格式转换之一
	   Map<String, Object> param = new HashMap<String, Object>();
		param.put("no", drClaimsLoan.getNo());
		param.put("name", java.net.URLDecoder.decode(drClaimsLoan.getName(),"utf-8"));
		param.put("companyName", java.net.URLDecoder.decode(drClaimsLoan.getCompanyName(),"utf-8"));
		param.put("startDate", Utils.format(drClaimsLoan.getStartDate(), "yyyy-MM-dd"));
		param.put("endDate", Utils.format(drClaimsLoan.getEndDate(), "yyyy-MM-dd"));
		if(drClaimsLoan.getStatus() == null){
			param.put("status", drClaimsLoan.getStatus());
		}else{
			if(drClaimsLoan.getStatus() == 100){//显示审核页面
				param.put("status", new Integer[]{1,3});
			}else if(drClaimsLoan.getStatus() == 200){//显示债权催收和债权还款
				param.put("status", new Integer[]{5});
			}else if(drClaimsLoan.getStatus() == 300){//显示标的新增
				param.put("status", new Integer[]{2});
			}else{
				param.put("status", new Integer[]{drClaimsLoan.getStatus()});
			}
		}
		List<DrClaimsLoan> rowsList = drClaimsInfoService.exportClaimsLoanList(param);
		String[] title = new String[]{"借款合同编号","产品名称","企业名称","企业名称协议显示","借款金额","应收利息","服务费","新增日期","截止日期","当前状态","基本操作"};
		Integer[] columnWidth = new Integer[]{20,20,20,20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(DrClaimsLoan claimsLoan : rowsList){
			lc = new ArrayList<Object>();
			lc.add(claimsLoan.getNo());//借款合同编号
			lc.add(claimsLoan.getName()==null?"":claimsLoan.getName());//产品名称
			lc.add(claimsLoan.getCompanyName()==null?"":claimsLoan.getCompanyName());//企业名称
			lc.add(claimsLoan.getCompanyNameProtocolShow()==null?"":claimsLoan.getCompanyNameProtocolShow());//企业名称协议显示
			lc.add(claimsLoan.getLoanAmount()==null?"":claimsLoan.getLoanAmount());//借款金额
			lc.add(claimsLoan.getReceiveInterest()==null?"":claimsLoan.getReceiveInterest());//应收利息
			lc.add(claimsLoan.getServiceCharge()==null?"":claimsLoan.getServiceCharge());//服务费
			lc.add(sdf.format(claimsLoan.getAddDate()));//新增时间
			lc.add(Utils.format(claimsLoan.getEndDate(), "yyyy-MM-dd"));//截止时间，利用自己工程中的工具进行格式转换
			lc.add(claimsLoan.getStatusName());//当前状态
			lc.add(claimsLoan.getShowStatus()==1?" ":"关闭");
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "Claims_info_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		
		return new ModelAndView(new JXLExcelView(), param);
	}

	/**
	 * 存管企业开户(页面)
	 * 
	 * @param no
	 * @return BaseResult
	 */
	@RequestMapping(value = "/addEnterpriseAccount")
	@ResponseBody
	public BaseResult addEnterpriseAccount(Integer id, Integer lid) {
		BaseResult br = new BaseResult();
		try {
			if (Utils.isObjectEmpty(id)) {
				br.setErrorMsg("贷款项目没有对应的企业信息！");
				br.setSuccess(false);
				return br;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("lid", lid);
			br = drClaimsInfoService.getDrClaimsCustomerById(param);

		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}

	/**
	 * 存管企业开户(直连),查询企业信息
	 * 
	 * @param no
	 * @return BaseResult
	 */
	@RequestMapping(value = "/addEnterpriseAccountByHttp")
	@ResponseBody
	public BaseResult addEnterpriseAccountByHttp(Integer id, Integer lid) {
		BaseResult br = new BaseResult();
		try {
			if (Utils.isObjectEmpty(id)) {
				br.setErrorMsg("贷款项目没有对应的企业信息！");
				br.setSuccess(false);
				return br;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("lid", lid);
			br = drClaimsInfoService.getDrClaimsCustomerByHttp(param);

		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorMsg("企业信息查询失败");
		}
		return br;
	}
	
	/**
	 * 存管企业开户(直连)
	 * 
	 * @param no
	 * @return BaseResult
	 */
	@RequestMapping(value = "/addEnterpriseAccountByAjax")
	@ResponseBody
	public BaseResult addEnterpriseAccountByAjax(HttpServletRequest request,@RequestParam Map<String,Object> map) {
		BaseResult br = new BaseResult();
		try {
			br = drClaimsInfoService.addArtifReg(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 跳转企业贷款资金管理
	 */
	@RequestMapping("/toAssetManagement")
	public String toAssetManagement(HttpServletRequest req,Map<String,Object> model){
		return "system/claims/assetManagement";
	}
	

	/**
	 *获取存管企业用户信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectCustomerFuiou")
	@ResponseBody
	public String selectCustomerFuiou(HttpServletRequest req,Integer page,Integer rows,String phone,String name) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("phone", phone);
		map.put("name", name);
		List<Map<String,Object>> list= drClaimsInfoService.selectCustomerFuiou(map);
		int count=drClaimsInfoService.selectCustomerFuiouCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 *提现
	 * @param map
	 * @return
	 */
	@RequestMapping("/customerwithdrawals")
	@ResponseBody
	public BaseResult customerwithdrawals(BigDecimal amount,Integer id) {
		BaseResult br = new BaseResult();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", id);
			DrClaimsCustomer claimsCustomer = drClaimsInfoService.selectDrClaimsCustomerById(id);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("signature", FuiouConfig.withdrawals(claimsCustomer.getPhone(),""+System.currentTimeMillis(),amount.toString()));
			m.put("fuiouUrl", FuiouConfig.WITHDRAWURL);
			br.setMap(m);
			br.setMsg("成功！");
			br.setSuccess(true);
			return br;
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 *充值
	 * @param map
	 * @return
	 */
	@RequestMapping("/customerrecharge")
	@ResponseBody
	public BaseResult customerrecharge(BigDecimal amount,Integer id,Integer type) {
		BaseResult br = new BaseResult();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", id);
			DrClaimsCustomer claimsCustomer = drClaimsInfoService.selectDrClaimsCustomerById(id);
			Map<String, Object> m = new HashMap<String, Object>();
			Map<String, Object> map=new HashMap<>();
			if(type==1){//快捷充值
				map.put("amt", amount);
				map.put("mchnt_txn_ssn", Utils.createOrderNo(6, id, null));
				map.put("login_id", claimsCustomer.getPhone());
				map.put("icd", FuiouConfig.PCQRECHARGE500405);
				m.put("signature", FuiouConfig.rechargeData(map));
				m.put("fuiouUrl", FuiouConfig.PCQRECHARGE500405URL);
			}else{
				map.put("amt", amount);
				map.put("mchnt_txn_ssn", Utils.createOrderNo(6, id, null));
				map.put("login_id", claimsCustomer.getPhone());
				m.put("signature", FuiouConfig.onlineBankingRechargeData(map));
				m.put("fuiouUrl", FuiouConfig.BRECHARGEURL);
			}
			br.setMap(m);
			br.setMsg("成功！");
			br.setSuccess(true);
			return br;
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 查询区
	 */
	@RequestMapping("/getFuiouAreaByCity")
	@ResponseBody
	public String getFuiouAreaByCity(Integer cityCode) {
		List<Map<String, Object>> list=drClaimsInfoService.getFuiouAreaByCity(cityCode);
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	/**
	 * 查存管信息
	 */
	@RequestMapping("/getFuiouEnterpriseInfo")
	@ResponseBody
	public BaseResult getFuiouEnterpriseInfo(String user_id) {
		BaseResult br = new BaseResult();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user_id);
		br = drClaimsInfoService.getFuiouEnterpriseInfo(param);
		return br;
	}
	
	/**
	 * 查询单个企业的存管余额
	 */
	@RequestMapping("/getCompanyFuiouBalance")
	@ResponseBody
	public String getCompanyFuiouBalance(Integer id){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		BaseResult account=new BaseResult();
		Map<String, String> m = new HashMap<String, String>();
		DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
		
		if(Utils.isObjectEmpty(id)){
			resultMap.put("errorMsg", "id不能为空");
			resultMap.put("flag", false);
			return JSON.toJSONString(resultMap);
		}
		DrClaimsCustomer drCustomer = drClaimsInfoService.selectCustomerByPhone(id);
		m.put("cust_no", drCustomer.getPhone());
		account=FuiouConfig.balanceAction(m);
		System.out.println("account"+account);
		if (account.isSuccess()) {
			net.sf.json.JSONObject accountresults= (net.sf.json.JSONObject) account.getMap().get("results");
			net.sf.json.JSONObject accountresult=(net.sf.json.JSONObject) accountresults.get("result");
			resultMap.put("ca_balance",Utils.setScale(Utils.nwdDivide(Utils.isObjectEmpty(accountresult.get("ca_balance"))?0:accountresult.get("ca_balance"),100)));//账户余额
			resultMap.put("customerName", drCustomer.getName());
			resultMap.put("companyName", drCustomer.getCompanyName());
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
			resultMap.put("errorMsg", "系统错误");
		}
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
}