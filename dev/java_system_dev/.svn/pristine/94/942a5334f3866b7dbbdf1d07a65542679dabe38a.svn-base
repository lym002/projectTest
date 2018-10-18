package com.jsjf.controller.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipOutputStream;
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

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ExcelUtil;
import com.jsjf.common.FileUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.claims.DrAuditInfo;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.claims.DrClaimsPic;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.JsCoverCharge;
import com.jsjf.model.product.JsInvoice;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.ActivityTemplateService;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrCompanyFundsLogService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.JsproductPrizeService;
import com.jsjf.service.subject.DrSubjectInfoService;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;
import com.jzh.FuiouConfig;

@Controller
@RequestMapping("/product")
public class DrProductInfoController{
	private Logger log = Logger.getLogger(this.getClass().getName());
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrSubjectInfoService drSubjectInfoService;
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private ActivityTemplateService activityTemplateService;
	@Autowired
	private DrMemberCarryService drMemberCarryService;
	@Autowired
	private DrCompanyFundsLogService drCompanyFundsLogService;
	@Autowired
	private JsproductPrizeService jsproductPrizeService;
	
	/**
	 * 放款走存管,直接修改放款状态,并生成放款记录
	 * @param req
	 * @param lid
	 * @return
	 */
	@RequestMapping("/updateLoanStatus")
	@ResponseBody
	public BaseResult updateLoanStatus(HttpServletRequest req,Integer id,String phone,String amount){
		SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		DrProductInfo drProductInfo =drProductInfoService.getDrProductInfoByid(id);
		BaseResult result = new BaseResult();
		/*String mchnt_txn_ssn=Utils.createOrderNo(6, drProductInfo.getId(), "");
		if(drProductInfo.getProject_no()!=null){	
			BaseResult br = new BaseResult();
			Map<String, String> map=new HashMap<>();
			map.put("login_id", phone);
			map.put("amt", amount);
			map.put("rem", "");
			map.put("mchnt_txn_ssn", mchnt_txn_ssn);
			br=FuiouConfig.wtwithdraw(map);
			if(!br.isSuccess()){//存管委托提现失败
				result.setErrorMsg("放款失败");
				result.setSuccess(false);
				return result;
			}
		}*/
		try {
			if(!Utils.isBlank(id) && Utils.isObjectNotEmpty(vo)){
				result = drProductInfoService.updateLoanStatus(id,vo.getUserKy().intValue(),null,null);
			}else{
				result.setErrorMsg("没有参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	/**
	 * 跳转到放款管理页面
	 */
	@RequestMapping("/toDrProductLoanList")
	public String toDrProductLoanList(HttpServletRequest req,Map<String,Object> model,String menuType) {
		try{
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("menuType", menuType);//1：财务 显示放款按钮。2非财务 不显示放款按钮

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drProductLoanList";
	}
	/**
	 * 显示放款清单列表
	 */
	@RequestMapping("/drProductLoanList")
	@ResponseBody
	public PageInfo drProductLoanList(DrProductInfo drProductInfo,String fullStartDate,String fullEndDate,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInfoService.getProductLoanList(drProductInfo,fullStartDate,fullEndDate,pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到产品列表页面
	 */
	@RequestMapping("/toDrProductInfoList")
	public String toDrProductInfoList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drProductInfoList";
	}
	
	/**
	 * 跳转到体验标列表页面
	 */
	@RequestMapping("/toExperienceDrProductInfoList")
	public String toExperienceDrProductInfoList(Map<String,Object> model) {
		try {
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drExperienceProductInfoList";
	}
	
	/**
	 * 跳转到产品审核列表页面
	 */
	@RequestMapping("/toAuditDrProductInfoList")
	public String toAuditDrProductInfoList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/auditDrProductInfoList";
	}
	
	/**
	 * 跳转到投资池
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/toInvestmentPoolList")
	public String toInvestmentPoolList(Map<String,Object> model) throws IOException{
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/product/investmentPoolList";
	}
	
	@RequestMapping("/investmentPoolList")
	@ResponseBody
	public PageInfo investmentPoolList(DrProductInfo drProductInfo,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInfoService.getInvestmentPoolList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示产品列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drProductInfoList")
	@ResponseBody
	public PageInfo drProductInfoList(DrProductInfo drProductInfo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInfoService.getDrProductInfoList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示体验标列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drexperienceProductInfoList")
	@ResponseBody
	public PageInfo drexperienceProductInfoList(DrProductInfo drProductInfo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		drProductInfo.setType(5);
		BaseResult result = drProductInfoService.getDrExperienceProductInfoList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示产品审核列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/auditDrProductInfoList")
	@ResponseBody
	public PageInfo auditDrProductInfoList(DrProductInfo drProductInfo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		if(drProductInfo.getStatus() == null){
			drProductInfo.setStatus(100);
		}
		BaseResult result = drProductInfoService.getDrProductInfoList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到产品新增页面
	 */
	@RequestMapping("/toAddDrProductInfo")
	public String toAddDrProductInfo(Map<String,Object> model,HttpServletRequest request) {
		try {
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			model.put("productCode", Utils.createOrderNo(4, usersVo.getUserKy().intValue(), "CP-"));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());
			model.put("productPrize",jsproductPrizeService.getJsProductPrizeforProduct(1));
			model.put("principleType",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("principleType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/addDrProductInfo";
	}
	/**
	 *获取产品原理图
	 */
	@RequestMapping("/getPrinciple")
	@ResponseBody
	public Map<String,Object> getPrinciple(HttpServletRequest request,Integer type) {
		Map<String,Object>  map = new HashMap<>();
		try {
			String principle = PropertyUtil.getProperties("principle");
			net.sf.json.JSONObject jsons = net.sf.json.JSONObject.fromObject(principle);		
			net.sf.json.JSONObject json = jsons.getJSONObject(""+type);		
			map.put("pcUrl", json.get("pc"));
			map.put("appUrl", json.get("h5"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 跳转到体验标新增页面
	 */
	@RequestMapping("/toAddExperienceDrProductInfo")
	public String toAddExperienceDrProductInfo(Map<String,Object> model,HttpServletRequest request) {
		try {
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			model.put("productCode", Utils.createOrderNo(4, usersVo.getUserKy().intValue(), "CP-"));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/addExperienceDrProductInfo";
	}
	
	/**
	 * 得到债权图片数据和 
	 * @param id 标的ID
	 * @return
	 */
	@RequestMapping(value= "/getClaimsPicInfo")
	@ResponseBody
	public Map<String,Object> getClaimsPicInfo(Integer id) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<DrClaimsPic> drClaimsPic = new ArrayList<DrClaimsPic>();
		if(Utils.isObjectNotEmpty(id)){
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(id);
			drClaimsPic = drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid());
			result.put("drClaimsPic", drClaimsPic);
			//获得债权最新产品的 扩展信息			
			result.put("drProductExtend", drProductInfoService.getDrProductExtendBySid(drSubjectInfo.getId()));
		}
		return result;
	}
	
	/**
	 * 得到标的数据
	 * @param id 标的ID
	 * @return
	 */
	@RequestMapping(value= "/getDrSubjectInfo")
	@ResponseBody
	public List<DrSubjectInfo> getDrSubjectInfo(Integer type,Integer id) {
		DrSubjectInfo subjectInfo = null;
		if(Utils.isObjectNotEmpty(id)){
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				subjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				subjectInfo.setSurplusAmount(subjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
				subjectInfo.setAmount(subjectInfo.getAmount().multiply(new BigDecimal(10000)));
			}
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(Utils.isObjectNotEmpty(type)){
			if(2 == type){
				map.put("type", 3);
			}
		}
		map.put("status", new int[]{1,2});
		map.put("surplusAmount", 0);
		List<DrSubjectInfo> drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByMap(map);
		if(Utils.isObjectNotEmpty(subjectInfo)){
			if(subjectInfo.getSurplusAmount().compareTo(new BigDecimal(0)) == 0){
				drSubjectInfo.add(subjectInfo);
			}
		}
		return drSubjectInfo;
	}
	
	/**
	 * 添加产品信息
	 * @param req
	 * @param DrCarryParameter
	 * @param name
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/addDrProductInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addDrProductInfo(DrProductInfo drProductInfo,HttpServletRequest request,
			@RequestParam MultipartFile[] productFiles,MultipartFile acceptPicFile,MultipartFile principlePcFile
			,MultipartFile principleAppFile){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fullName", drProductInfo.getFullName());
			int count = drProductInfoService.getDrProductInfoListCountByMap(map);
			if(count>0){
				br.setErrorCode("9888");
				br.setErrorMsg("产品名字已存在!!!");
				br.setSuccess(false);
				return ((JSONObject) JSONObject.toJSON(br)).toString();
			}
			
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			for(int i=0;i<productFiles.length;i++){
				Matcher matcher = pattern.matcher(productFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = productFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(acceptPicFile)){
				Matcher matcher = pattern.matcher(acceptPicFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确pc原理图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = acceptPicFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("pc原理图不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			
			if(Utils.isObjectNotEmpty(principlePcFile)){
				Matcher matcher = pattern.matcher(principlePcFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确h5原理图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principlePcFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("h5原理图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(principleAppFile)){
				Matcher matcher = pattern.matcher(principleAppFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principleAppFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			
			if(Utils.isObjectNotEmpty(principlePcFile)){
				Matcher matcher = pattern.matcher(principlePcFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principlePcFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(principleAppFile)){
				Matcher matcher = pattern.matcher(principleAppFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principleAppFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
				JsProductPrize jsProductPrize = jsproductPrizeService.getJsProductPrizeById(drProductInfo.getPrizeId());
				if(jsProductPrize.getStatus() !=1){
					br.setSuccess(false);
					br.setErrorMsg("关联的商品已下架，请重新选择。");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			drProductInfo.setAddUser(usersVo.getUserKy().intValue());
			drProductInfo.setStatus(1);
			br = drProductInfoService.insertDrProductInfo(drProductInfo,productFiles,acceptPicFile,principlePcFile,principleAppFile);
		} catch (Exception e) {
			log.error("添加产品失败："+e);
			br.setErrorMsg("添加失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 添加体验标信息
	 * @param req
	 * @param DrCarryParameter
	 * @param name
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/addExperienceDrProductInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addExperienceDrProductInfo(DrProductInfo drProductInfo,HttpServletRequest request){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			drProductInfo.setAddUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.insertDrExperienceProductInfo(drProductInfo);
		} catch (Exception e) {
			log.error("添加体验标失败："+e);
			br.setErrorMsg("添加体验标失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到标的详情页面
	 */
	@RequestMapping("/showDrSubjectInfo")
	public String showDrSubjectInfo(Map<String,Object> model,Integer id) {
		try {
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("dateType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("dateType")));
			
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(id);
			model.put("drSubjectInfo", drSubjectInfo);
			
			if(Utils.isObjectNotEmpty(drSubjectInfo.getStartDate())){
				model.put("subjectStartDate", Utils.getparseDate(drSubjectInfo.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drSubjectInfo.getEndDate())){
				model.put("subjectEndDate", Utils.getparseDate(drSubjectInfo.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/showDrSubjectInfo";
	}
	
	/**
	 * 验证产品金额
	 * @param amount
	 * @param sid
	 * @return BaseResult
	 */
	@RequestMapping(value= "/validatorProductAmount")
	@ResponseBody
	public BaseResult validatorProductAmount(BigDecimal amount,Integer sid) {
		BaseResult br = new BaseResult();
		try{
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(sid);
			if(drSubjectInfo == null || amount.compareTo(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)))>0){
				br.setSuccess(false);
			}else{
				br.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 验证修改产品金额
	 * @param amount
	 * @param sid
	 * @return BaseResult
	 */
	@RequestMapping(value= "/validatorUpdateProductAmount")
	@ResponseBody
	public BaseResult validatorUpdateProductAmount(BigDecimal amount,Integer sid,Integer id) {
		BaseResult br = new BaseResult();
		try{
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(sid);
			drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
			if(drProductInfo.getSid().intValue() == sid.intValue()){
				drSubjectInfo.setSurplusAmount(Utils.nwdBcadd(drSubjectInfo.getSurplusAmount(), drProductInfo.getAmount()));
			}
			if(amount.compareTo(drSubjectInfo.getSurplusAmount())>0){
				br.setSuccess(false);
			}else{
				br.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 跳转到产品新增页面
	 */
	@RequestMapping("/toUpdateProductInfo")
	public String toUpdateProductInfo(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			model.put("principleType",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("principleType")));
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
//			if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
//				model.put("productPrize",jsproductPrizeService.getJsProductPrizeforProductUpdate(drProductInfo.getPrizeId()));
//			}else{
			model.put("productPrize",jsproductPrizeService.getJsProductPrizeforProduct(1));
//			}
			model.put("drProductInfo",drProductInfo);
			
			if(Utils.isObjectNotEmpty(drProductInfo.getFid())){
				DrProductInfo fProductInfo = drProductInfoService.getDrProductInfoByid(drProductInfo.getFid());
				model.put("fcode",fProductInfo.getCode());
			}
			
			model.put("drProductExtend",drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
			}
			
			model.put("drProductPic", drProductInfoService.getDrProductPicByPid(id));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/updateDrProductInfo";
	}
	
	/**
	 * 跳转到体验标修改页面
	 */
	@RequestMapping("/toUpdateExperienceProductInfo")
	public String toUpdateExperienceProductInfo(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			model.put("drProductInfo",drProductInfo);
			
			/*if(Utils.isObjectNotEmpty(drProductInfo.getFid())){
				DrProductInfo fProductInfo = drProductInfoService.getDrProductInfoByid(drProductInfo.getFid());
				model.put("fcode",fProductInfo.getCode());
			}
			
			model.put("drProductExtend",drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
			}*/
			/*
			model.put("drProductPic", drProductInfoService.getDrProductPicByPid(id));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());*/

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/updateDrExperienceProductInfo";
	}
	
	/**
	 * 修改产品信息
	 * @param req
	 * @param DrCarryParameter
	 * @param name
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/updateDrProductInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateDrProductInfo(DrProductInfo drProductInfo,HttpServletRequest request,
			@RequestParam MultipartFile[] productFiles,MultipartFile acceptPicFile,MultipartFile principlePcFile
			,MultipartFile principleAppFile){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			for(int i=0;i<productFiles.length;i++){
				Matcher matcher = pattern.matcher(productFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = productFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(acceptPicFile)){
				Matcher matcher = pattern.matcher(acceptPicFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = acceptPicFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(principlePcFile)){
				Matcher matcher = pattern.matcher(principlePcFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principlePcFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(principleAppFile)){
				Matcher matcher = pattern.matcher(principleAppFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = principleAppFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
				JsProductPrize jsProductPrize = jsproductPrizeService.getJsProductPrizeById(drProductInfo.getPrizeId());
				if(jsProductPrize.getStatus() !=1){
					br.setSuccess(false);
					br.setErrorMsg("关联的商品已下架，请重新选择。");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			drProductInfo.setStatus(1);
			br = drProductInfoService.updateDrProductInfo(drProductInfo,productFiles,acceptPicFile,principlePcFile,principleAppFile);
		} catch (Exception e) {
			log.error("修改产品失败："+e);
			br.setErrorMsg("修改失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到产品审核页面
	 */
	@RequestMapping("/toAuditDrProductInfo")
	public String toAuditDrProductInfo(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
				model.put("productPrize",jsproductPrizeService.getJsProductPrizeforProductUpdate(drProductInfo.getPrizeId()));
			}else{
				model.put("productPrize",jsproductPrizeService.getJsProductPrizeforProduct(1));
			}
			model.put("drProductInfo",drProductInfo);
			
			if(Utils.isObjectNotEmpty(drProductInfo.getFid())){
				DrProductInfo fProductInfo = drProductInfoService.getDrProductInfoByid(drProductInfo.getFid());
				model.put("fcode",fProductInfo.getCode());
			}
			
			model.put("drProductExtend", drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
				model.put("drSubjectInfo", drSubjectInfo);
			}

			
			model.put("auditType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("auditType")));
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put("fid", id);
			mapAudit.put("type", 2);
			mapAudit.put("sort", "DESC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(mapAudit));
			
			model.put("drProductPic",  drProductInfoService.getDrProductPicByPid(id));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/auditDrProductInfo";
	}
	
	/**
	 * 添加产品审核信息
	 * @param request
	 * @param lid
	 * @param opinion
	 * @param status 
	 * @return
	 */
	@RequestMapping(value="/addDrAuditInfo")
	@ResponseBody
	public BaseResult addDrAuditInfo(Integer pid,String opinion,Integer status,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(pid);
			if(drProductInfo.getStatus() != 1 && drProductInfo.getStatus() != 3){
				br.setErrorMsg("该产品不可审核!");
				br.setSuccess(false);
				return br;
			}
			if(Utils.isObjectNotEmpty(status) && status == 1){
				if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
					JsProductPrize jsProductPrize = jsproductPrizeService.getJsProductPrizeById(drProductInfo.getPrizeId());
					if(jsProductPrize.getStatus() !=1){
						br.setSuccess(false);
						br.setErrorMsg("关联的商品已下架，不能审核通过。");
						return br;
					}
				}
			}
			String opinionStr=new String((request.getParameter("opinion")).getBytes("iso-8859-1"),"utf-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrAuditInfo drAuditInfo = new DrAuditInfo();
			drAuditInfo.setFid(pid);
			drAuditInfo.setType(2);
			drAuditInfo.setOpinion(opinionStr);
			drAuditInfo.setStatus(status);
			drAuditInfo.setAddUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.insertDrAuditInfo(drAuditInfo);
		} catch (Exception e) {
			log.error("审核产品失败："+e);
			br.setErrorMsg("审核失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 跳转到产品显示页面
	 */
	@RequestMapping("/toShowDrProductInfo")
	public String toShowDrProductInfo(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			model.put("principleType",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("principleType")));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			model.put("drProductInfo",drProductInfo);
			if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
				model.put("productPrize", jsproductPrizeService.getJsProductPrizeById(drProductInfo.getPrizeId()));
			}else{
				model.put("productPrize", null);
			}
			
			if(Utils.isObjectNotEmpty(drProductInfo.getFid())){
				DrProductInfo fProductInfo = drProductInfoService.getDrProductInfoByid(drProductInfo.getFid());
				model.put("fcode",fProductInfo.getCode());
			}
			
			model.put("drProductExtend",drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
				model.put("drSubjectInfo", drSubjectInfo);
			}
			
			model.put("auditType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("auditType")));
			Map<String,Object> mapAudit = new HashMap<String, Object>();
			mapAudit.put("fid", id);
			mapAudit.put("type", 2);
			mapAudit.put("sort", "DESC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(mapAudit));
			
			model.put("drProductPic",  drProductInfoService.getDrProductPicByPid(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/showDrProductInfo";
	}
	
	/**
	 * 跳转到体验标产品显示页面
	 */
	@RequestMapping("/toShowDrExperienceProductInfo")
	public String toShowDrExperienceProductInfo(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			model.put("activitymap",activityTemplateService.selActivityTemplateAll());
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			model.put("drProductInfo",drProductInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/showDrExperienceProductInfo";
	}
	
	/**
	 * 查询是否可以修改或审核
	 * @param request
	 * @param operate 区分是修改审核
	 * @return
	 */
	@RequestMapping(value="/isOperate")
	@ResponseBody
	public BaseResult isOperate(Integer id,String operate,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if("update".equals(operate)){
				if(drProductInfo.getStatus() != 1 && drProductInfo.getStatus() != 3 && drProductInfo.getStatus() != 2){
					br.setErrorMsg("该产品不可修改!");
					br.setSuccess(false);
					return br;
				}
			}
			if("audit".equals(operate)){
				if(drProductInfo.getStatus() != 1 && drProductInfo.getStatus() != 3){
					br.setErrorMsg("该产品不可审核!");
					br.setSuccess(false);
					return br;
				}
			}
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("操作失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 上架操作
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateDrProductStatus")
	@ResponseBody
	public BaseResult updateDrProductStatus(Integer id,String startDate,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);

			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			
//			// 生成标的时判断是否存管开户
//			DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(drProductInfo.getSid());
//			if (StringUtils.isBlank(customer.getUser_id())) {
//				br.setMsg("该产品对应的企业未开通存管账户");
//				br.setSuccess(false);
//				return br;
//			}
			
			Date establish = Utils.getDayNumOfAppointDate(Utils.parse(startDate, "yyyy-MM-dd"),-(drProductInfo.getRaiseDeadline()));

			if(drProductInfo.getStatus() == 2){
				if(drProductInfo.getFid() != null){
					DrProductInfo previousProduct = drProductInfoService.getDrProductInfoByid(drProductInfo.getFid());
					if(!Utils.areSameDay(establish, Utils.parse(previousProduct.getExpireDate(),"yyyy-MM-dd"))){
						br.setErrorMsg("产品募集期不正确,该产品不可上架！");
						br.setSuccess(false);
						return br;
					}
				}
				drProductInfo.setStatus(5);
				drProductInfo.setProject_no("jzh");
			}else{
				br.setErrorMsg("该产品不可上架!");
				br.setSuccess(false);
				return br;
			}
			drProductInfo.setStartDate(Utils.parse(startDate,"yyyy-MM-dd HH:mm:ss"));
			drProductInfo.setExpireDate(Utils.getDayNumOfAppointDate(Utils.parse(drProductInfo.getStartDate(), "yyyy-MM-dd"),-(drProductInfo.getDeadline()+drProductInfo.getRaiseDeadline())));
			drProductInfo.setEstablish(establish);
			drProductInfo.setIsShow(1);
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.updateDrProductStatus(drProductInfo,null,"validatorSid");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("上架失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 取消预约操作
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateDrProductCancelBespoke")
	@ResponseBody
	public BaseResult updateDrProductCancelBespoke(Integer id,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(drProductInfo.getStartDate().getTime()<new Date().getTime()){
				br.setErrorMsg("该产品已上架，不可取消预约!");
				br.setSuccess(false);
				return br;
			}
			drProductInfo.setStartDate(null);
			drProductInfo.setExpireDate(null);
			drProductInfo.setEstablish(null);
			drProductInfo.setStatus(2);
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			drProductInfoService.updateDrProductCancelBespoke(drProductInfo);
			br.setMsg("操作成功！");
			br.setSuccess(true);
			return br;
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("取消预约失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 复制操作
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/copyDrProductInfo")
	@ResponseBody
	public BaseResult copyDrProductInfo(String code){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("code", code);
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByMap(map);
			if(Utils.isObjectEmpty(drProductInfo)){
				br.setSuccess(false);
				br.setErrorMsg("没有此产品编号！");
				return br;
			}
			map.clear();
			map.put("drProductInfo", drProductInfo);
			
			map.put("drProductExtend", drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
			if(Utils.isObjectNotEmpty(drSubjectInfo)){
				map.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
			}
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 修改是否显示
	 * @param request
	 * @param id
	 * @param isShow
	 * @return
	 */
	@RequestMapping(value="/updateDrProductIsShow")
	@ResponseBody
	public BaseResult updateDrProductIsShow(Integer id,Integer isShow,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(drProductInfo.getIsShow() == isShow){
				if(0 == isShow){
					drProductInfo.setIsShow(1);
				}else{
					drProductInfo.setIsShow(0);
				}
			}
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.updateDrProductStatus(drProductInfo,null,null);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 修改是否热推
	 * @param id
	 * @param isHot
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateDrProductIsHot")
	@ResponseBody
	public BaseResult updateDrProductIsHot(Integer id,Integer isHot,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(drProductInfo.getIsHot() == isHot){
				if(0 == isHot){
					drProductInfo.setIsHot(1);
				}else{
					drProductInfo.setIsHot(0);
				}
			}
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.updateDrProductStatus(drProductInfo,null,null);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 根据ID删除产品图片
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteDrProductPic")
	@ResponseBody
	public BaseResult deleteDrProductPic(Integer id){
		BaseResult br = new BaseResult();
		try {
			drProductInfoService.deleteDrProductPicById(id);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 作废操作
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateDrProductDelete")
	@ResponseBody
	public BaseResult updateDrProductDelete(Integer id,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(drProductInfo.getStatus() == 2){
				drProductInfo.setStatus(4);
			}else{
				br.setErrorMsg("该产品不可作废!");
				br.setSuccess(false);
				return br;
			}
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.updateDrProductStatus(drProductInfo,null,"validatorSid");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("操作失败!");
			br.setSuccess(false);
		}
		return br;
	}
	/**
	 * 放款状态更新 old
	 * 更改为 放款状态为申请 
	 * @param binder
	 */
    @RequestMapping(value="/updateDrProductLoanBtn")
	@ResponseBody
	public BaseResult updateDrProductLoanBtn(Integer id,HttpServletRequest request,String actLoanTime){
    	BaseResult br = new BaseResult();
    	try {
    		Map<String,Object> map = new HashMap<String, Object>();
    		DrProductInfo drPI = drProductInfoService.getDrProductInfoByid(id);
    		if(drPI.getLoanStatus()==0){
    			map.put("loanStatus", 3);
    		}else {
    			map.put("loanStatus", 0);
    		}
    		map.put("id", id);
    		map.put("actLoanTime", actLoanTime);
        	drProductInfoService.updateDrProductLoanStatusSQ(map);
			br.setSuccess(true);
			br.setMsg("操作成功！");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("999");
		}
    	return br;
    }
    /**
     * 回款状态更新
     * @param binder
     */
    @RequestMapping(value="/updateReFundDrProductLoan")
    @ResponseBody
    public BaseResult updateReFundDrProductLoanBtn(String phone,String heji,HttpServletRequest request,String code,Integer id){
    	BaseResult br = new BaseResult();
    	@SuppressWarnings("unused")    	 	
		DrProductInfo info =drProductInfoService.getDrProductInfoByid(id);
    	phone = drProductInfoService.getIn_cust_noByProductId(info.getId());
    	if(info.getProject_no()!=null){//存管项目
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("amt", heji);
			map.put("mchnt_txn_ssn", Utils.createOrderNo(6, 1, null));
			map.put("login_id", phone);
			map.put("project_no", code);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("signature", FuiouConfig.onlineBankingRechargeData(map));
			m.put("fuiouUrl", FuiouConfig.BRECHARGEURL);
			br.setMap(m);
			br.setSuccess(true);
			br.setMsg("操作成功！");
    	}else{
    		try {
        		//生成流水记录
    			BigDecimal amount=new BigDecimal(0);
        		if(Utils.isObjectNotEmpty(heji)){
        			amount = new BigDecimal(heji);
        		}
        		if(Utils.isObjectEmpty(info)){
        			br.setSuccess(false);
        			br.setErrorCode("999");
        			br.setErrorMsg("id不能为空");
        			return br;
        		}
        		String loanNo;//合同号
        		DrSubjectInfo drSubjectInfo;
        		if(Utils.isObjectEmpty(info.getSid())){
        			loanNo = "xxxxxxx";
        		}
    			drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(info.getSid());
    			if(Utils.isObjectEmpty(drSubjectInfo)){
    				loanNo = "xxxxxxx";
    			}
    			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(drSubjectInfo.getLid());
    			if(Utils.isObjectEmpty(drClaimsLoan)){
    				loanNo = "xxxxxxx";
    			}else{
    				loanNo = drClaimsLoan.getNo();
    			}
        		
        		String tran_flowid = Utils.createOrderNo(6,info.getId(),"");
        		drProductInfoService.updateReFundDrProductLoanStatus(id);
        		DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(16, null, 
        				id, amount, 1, 
        						"债券合同["+loanNo+"]回款"+amount+"元,流水号["+tran_flowid+"]",null);
        		drCompanyFundsLogService.insertDrCompanyFundsLog(drCompanyFundsLog);
        		br.setSuccess(true);
        		br.setMsg("操作成功！");
        	} catch (Exception e) {
        		br.setSuccess(false);
        		br.setErrorCode("999");
        	}
    	}
    	return br;
    }
	/**
	 * 根据ID查询产品信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/queryDrProductInfo")
	@ResponseBody
	public BaseResult queryDrProductInfo(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
		map.put("drProductInfo",drProductInfo);
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 上架后修改产品信息
	 * @param DrChannelInfo
	 * @return
	 */
	@RequestMapping(value= "/updateDrProductInfoForElse" ,produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateDrProductInfoForElse(DrProductInfo drProductInfo,@RequestParam MultipartFile[] productFiles,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			for(int i=0;i<productFiles.length;i++){
				Matcher matcher = pattern.matcher(productFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = productFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			DrProductInfo product = drProductInfoService.getDrProductInfoByid(drProductInfo.getId());
			if(product != null){
				if(product.getStatus() == 5){
					if(product.getRaiseDeadline().intValue() <= drProductInfo.getRaiseDeadline().intValue()){
						DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(product.getSid());
						int counts = Utils.daysBetween(product.getDeadline()+drProductInfo.getRaiseDeadline()-1,drSubjectInfo.getEndDate(),Utils.parse(product.getStartDate(), "yyyy-MM-dd"));
						if(counts>0){
							br.setErrorMsg("产品到期日不可大于标的到期日【"+Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd")+"】！");
							br.setSuccess(false);
							JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
							return jsonObject.toString();
						}
						//根据开始时间 ,募集期,期限, 计算成立时间 与计算时间
						drProductInfo.setEstablish(Utils.getDayNumOfAppointDate(Utils.parse(product.getStartDate(), "yyyy-MM-dd"),-(drProductInfo.getRaiseDeadline())));
						drProductInfo.setExpireDate(Utils.getDayNumOfAppointDate(Utils.parse(product.getStartDate(), "yyyy-MM-dd"),-(product.getDeadline()+drProductInfo.getRaiseDeadline())));
	
						br = drProductInfoService.updateDrProductStatus(drProductInfo,productFiles,null);
					}else{
						br.setErrorMsg("修改后的募集期限必须要大于原募集期限（"+product.getRaiseDeadline().intValue()+"天）!");
						br.setSuccess(false);
					}
				}else if(product.getStatus() != 5){
					drProductInfo.setRaiseDeadline(product.getRaiseDeadline());//不是募集中状态的，不更新募集期限
					br = drProductInfoService.updateDrProductStatus(drProductInfo,productFiles,null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("修改失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 修改体验标信息
	 * @param DrChannelInfo
	 * @return
	 */
	@RequestMapping(value= "/updateDrExperienceProductInfo")
	@ResponseBody
	public BaseResult updateDrExperienceProductInfo(DrProductInfo drProductInfo,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			drProductInfo.setUpdUser(usersVo.getUserKy().intValue());
			br = drProductInfoService.updateDrExperienceProductInfo(drProductInfo);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("修改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 判断是否可以续发
	 * @param DrChannelInfo
	 * @return
	 */
	@RequestMapping(value= "/validatorAddDrProductInfoRenewal")
	@ResponseBody
	public BaseResult validatorAddDrProductInfoRenewal(int id,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", id);
			map.put("noStatus", 4);
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByMap(map);
			if(Utils.isObjectNotEmpty(drProductInfo)){
				br.setErrorMsg("该产品已续发!");
				br.setSuccess(false);
			}else{
				br.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("操作失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 跳转到产品续发页面
	 */
	@RequestMapping("/toAddProductInfoRenewal")
	public String toAddProductInfoRenewal(Map<String,Object> model,HttpServletRequest request,Integer id) {
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			model.put("productCode", Utils.createOrderNo(4, usersVo.getUserKy().intValue(), "CP-"));
			
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("intermediary", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("intermediary")));
			
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			model.put("drProductInfo",drProductInfo);
			model.put("drProductExtend",drProductInfoService.getDrProductExtendByPid(drProductInfo.getId()));
			
			if(drProductInfo.getType() != 1 && drProductInfo.getSid() != null){
				DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
				model.put("drClaimsPic",  drClaimsInfoService.getDrClaimsPicByLid(drSubjectInfo.getLid()));
				model.put("drSubjectInfo", drSubjectInfo);
			}

			model.put("drProductPic", drProductInfoService.getDrProductPicByPid(id));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/addDrProductInfoRenewal";
	}
	
	/**
	 * 添加产品续发信息
	 * @param req
	 * @param DrProductInfo
	 * @param productFiles
	 * @param acceptPicFile
	 * @return
	 */
	@RequestMapping(value="/addDrProductInfoRenewal",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addDrProductInfoRenewal(DrProductInfo drProductInfo,HttpServletRequest request,
			@RequestParam MultipartFile[] productFiles,MultipartFile acceptPicFile){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			for(int i=0;i<productFiles.length;i++){
				Matcher matcher = pattern.matcher(productFiles[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = productFiles[i].getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			if(Utils.isObjectNotEmpty(acceptPicFile)){
				Matcher matcher = pattern.matcher(acceptPicFile.getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setMsg("请上传正确承兑图片的格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = acceptPicFile.getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setMsg("承兑图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			
			drProductInfo.setAddUser(usersVo.getUserKy().intValue());
			drProductInfo.setStatus(1);
			br = drProductInfoService.insertDrProductInfoRenewal(drProductInfo,productFiles,acceptPicFile);
		} catch (Exception e) {
			log.error("续发产品失败："+e);
			br.setErrorMsg("续发失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	@RequestMapping("/exportDrProductInfo")
	public ModelAndView exportDrProductInfo(DrProductInfo drProductInfo,Integer page,Integer rows)throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", drProductInfo.getCode());
		param.put("simpleName", java.net.URLDecoder.decode(drProductInfo.getSimpleName(),"utf-8"));
		if(drProductInfo.getStatus()!=null){
			param.put("status", drProductInfo.getStatus());
		}
		param.put("repayType", drProductInfo.getRepayType());

		List<Map<String, Object>> rowsList = drProductInfoService.selectDrProductInfoList(param);
		String[] title = new String[]{"产品编号","产品全称","产品利率","产品金额","已募金额","产品状态","上架时间","还款方式","募集期限","满标时间",
				"到期时间","剩余到期天数","承兑机构","可否加息","可否返现","可否加倍","关联标的名称","续发产品名称"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("code"));//产品编号
			lc.add(map.get("fullName")==null?"":map.get("fullName"));//产品名称
			lc.add(map.get("rate"));//产品利率
			lc.add(map.get("amount"));//产品金额
			lc.add(map.get("alreadyRaiseAmount"));//已募金额
			lc.add(map.get("statusName"));//产品状态
			lc.add(map.get("startDate")==null?"":map.get("startDate"));//上架时间
			lc.add(map.get("repayTypeName")==null?"":map.get("repayTypeName"));//产品名称
			lc.add(map.get("raiseDeadline"));//募集期限
			lc.add(map.get("fullDate")==null?"":map.get("fullDate"));//满标时间
			lc.add(map.get("expireDate")==null?"":map.get("expireDate"));//到期时间
			lc.add(map.get("surplusDay"));//剩余到期天数
			lc.add(map.get("accept"));//承兑机构
			lc.add(map.get("isInterest"));//可否加息
			lc.add(map.get("isCash"));//可否返现
			lc.add(map.get("isDouble"));//可否加倍
			lc.add(map.get("sName")==null?"":map.get("sName"));//关联标的名称
			lc.add(map.get("parentName")==null?"":map.get("parentName"));//续发产品名称
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "product_info_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	/**
	 * 导出体验标信息
	 * @param drProductInfo
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDrExperienceProductInfo")
	public ModelAndView exportDrExperienceProductInfo(DrProductInfo drProductInfo,Integer page,Integer rows)throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", 5);
		param.put("code", drProductInfo.getCode());
		param.put("simpleName", java.net.URLDecoder.decode(drProductInfo.getSimpleName(),"utf-8"));
		if(drProductInfo.getStatus()!=null){
			param.put("status", drProductInfo.getStatus());
		}
		List<Map<String, Object>> rowsList = drProductInfoService.selectDrProductInfoList(param);
		String[] title = new String[]{"产品编号","产品类型","产品全称","产品简称","年化利率","活动利率","还款方式","产品期限"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("code"));//产品编号
			lc.add(map.get("type"));//产品类型
			lc.add(map.get("fullName")==null?"":map.get("fullName"));//产品全称
			lc.add(map.get("simpleName")==null?"":map.get("simpleName"));//产品简称
			lc.add(map.get("rate")==null?"":map.get("rate"));//年化利率
			lc.add(map.get("activityRate")==null?"":map.get("activityRate"));//活动利率
//			lc.add(map.get("repayType")==null?"":map.get("repayType"));//还款方式
			if(map.get("repayType")!=null){
				int repayType =(int)map.get("repayType");
				if(repayType==1){
					lc.add("到期一次还本付息");
				}else if(repayType==2){
					lc.add("按月付息到期还本");
				}
			}else{
				lc.add("");
			}
			lc.add(map.get("deadline")==null?"":map.get("deadline"));//产品期限
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "ExperienceProduct_info_"+System.currentTimeMillis()+".xls");
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
    
    /**
	 * 项目放款审批表
	 */
	@RequestMapping("/exprotProductLoanAuditList")
	@ResponseBody
	public String exprotProductLoanAuditList(HttpServletRequest request, HttpServletResponse response, DrProductInfo drProductInfo,String fullStartDate,String fullEndDate){
		BaseResult br = new BaseResult();
		try {
			/*drProductInfo.setSimpleName(URLDecoder.decode(drProductInfo.getSimpleName(),"utf-8"));
			drProductInfo.setLoanName(URLDecoder.decode(drProductInfo.getLoanName(),"utf-8"));*/
			List<DrProductInfo> list = drProductInfoService.getDrProductLoanListByParam(drProductInfo,fullStartDate,fullEndDate);
			if(null == list || list.isEmpty()){
				br.setSuccess(false);
				br.setMsg("No Data!");
			}else{	
				//读取模板
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
				String filename = "ProductLoanAudit-" + sdf1.format(new Date());
				File file =  new File(  request.getRealPath( "/" ) + filename +".xlsx");
				FileOutputStream os = new FileOutputStream(file);
				generateLoanExcel(os,list);//创建excel工作簿
				os.flush();//flush是清空不是刷新，一般用在io中，当用读写流的时候，数据是先被读到内存中，然后用数据写到文件中的时候，读完但是不代表写完了，所以需要先将数据清空，意思就是将数据立刻写出。
				os.close();
				FileUtil.download(file,response);
				if(file.exists()){
					file.delete();
				}
				br.setSuccess(true);
				br.setMsg("Exprot success!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setMsg("System exception!");
		} 
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	/**
	 * 生成导出放款审批表Excel
	 * @param osw
	 * @param productList
	 */
	private void generateLoanExcel(FileOutputStream osw, List<DrProductInfo> productList){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productLoanAuditTemplate.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);//in表示在内存中多少行，超过in这个参数值的显示到磁盘中，-->创建了一个excel文件
			XSSFSheet sheet = workeBook.getSheetAt(0);//创建一个工作
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(11).setCellValue(new XSSFRichTextString(sdf.format(new Date())));
			XSSFRow row = null;
			XSSFRow row3 = sheet.getRow(3);
			int start = 3;
			for(int i = 0; i < productList.size(); i ++){
				if(i == 0){
					//第四行，模板已经创建，不需要新增
					setRowValues(productList.get(i), row3);
					if(productList.size() > 1){
						sheet.shiftRows(4, sheet.getLastRowNum(), productList.size() - 1, true, false);
					}
				}else{
					row = sheet.createRow(start + i);
					copyRow(row3, row);
					setRowValues(productList.get(i), row);
				}
			}
			sheet.setForceFormulaRecalculation(true);
			workeBook.write(osw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 币优铺金服项目导出还款通知表
	 */
	@RequestMapping("/productPaymentNoticeList")
	@ResponseBody
	public String productPaymentNoticeList(HttpServletRequest request, HttpServletResponse response, DrProductInfo drProductInfo,String fullStartDate,String fullEndDate){
		BaseResult br = new BaseResult();
		try {
			//drProductInfo.setSimpleName(URLDecoder.decode(drProductInfo.getSimpleName(),"utf-8"));
			//drProductInfo.setLoanName(URLDecoder.decode(drProductInfo.getLoanName(),"utf-8"));
			List<DrProductInfo> list = drProductInfoService.getDrProductLoanListByParam(drProductInfo,fullStartDate,fullEndDate);
			if(null == list || list.isEmpty()){
				br.setSuccess(false);
				br.setMsg("No Data!");
			}else{
				//读取模板
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
				String filename = "productPaymentNotice-" + sdf1.format(new Date());
				File file =  new File(  request.getRealPath( "/" ) + filename +".xlsx");
				FileOutputStream os = new FileOutputStream(file);
				productPaymentNoticeExcel(os,list);//创建excel工作簿
				os.flush();
				os.close();
				FileUtil.download(file,response);
				if(file.exists()){
					file.delete();
				}
				br.setSuccess(true);
				br.setMsg("Exprot success!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setMsg("System exception!");
		} 
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
 		return jsonObject.toString();
	}
	/**
	 * 生成生成还款Excel
	 * @param osw
	 * @param productList
	 */
	private void productPaymentNoticeExcel(FileOutputStream osw, List<DrProductInfo> productList){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productPaymentNotice.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);
			XSSFSheet sheet = workeBook.getSheetAt(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(11).setCellValue(new XSSFRichTextString(sdf.format(new Date())));
			XSSFRow row = null;
			XSSFRow row3 = sheet.getRow(3);
			int start = 3;
			for(int i = 0; i < productList.size(); i ++){
				if(i == 0){
					//第四行，模板已经创建，不需要新增
					productPaymentNoticeSetRowValues(productList.get(i), row3);
					if(productList.size() > 1){
						sheet.shiftRows(4, sheet.getLastRowNum(), productList.size() - 1, true, false);
					}
				}else{
					row = sheet.createRow(start + i);
					copyRow(row3, row);
					productPaymentNoticeSetRowValues(productList.get(i), row);
				}
			}
			sheet.setForceFormulaRecalculation(true);
			workeBook.write(osw);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 币优铺金服项目导出回款明细表
	 * @return 
	 */
	@RequestMapping("productReturnNoticeList")
	@ResponseBody
	public String productReturnNoticeList(HttpServletRequest request, HttpServletResponse response, DrProductInfo drProductInfo,String fullStartDate,String fullEndDate)  {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		BaseResult br = new BaseResult();
		List<File> fileList = new ArrayList<File>();//用于存放文件
		
		//文件都是在/WEB-INF/classes目录下
		String filePath = request.getSession().getServletContext().getRealPath("/");	
		
		//压缩文件名
		String strZipName = sdf.format(new Date())+".zip";
		File outFile = new File(filePath + "/" + strZipName);
		try {
			//drProductInfo.setSimpleName(URLDecoder.decode(drProductInfo.getSimpleName(),"utf-8"));
			//drProductInfo.setLoanName(URLDecoder.decode(drProductInfo.getLoanName(),"utf-8"));
			List<DrProductInfo> list = drProductInfoService.getDrProductLoanListByParam(drProductInfo,fullStartDate,fullEndDate);
			if(null == list || list.isEmpty()){
				br.setSuccess(false);
				br.setMsg("No Data!");
			}else{
				
				//循环产品信息list
				for(int i=0;i<list.size();i++){
					Integer id = list.get(i).getId();
					List<Map<String,Object>> returnList = drProductInfoService.getReturnNoticeList(id);
					if(returnList.isEmpty())continue;
					//读取模板
					String filename = "productReturnNotice-" + sdf.format(new Date()) + i;
					File file =  new File(filePath +"/"+ filename +".xlsx");
					FileOutputStream os = new FileOutputStream(file);
					try {
						productReturnNoticeExcel(os,list.get(i),returnList);//创建excel工作簿
						os.flush();
						os.close();
						fileList.add(file);
					} catch (Exception e) {
						os.flush();
						os.close();
					}
					
				}
				if(fileList.size() > 1){
					
					//压缩文件输出流
					FileOutputStream outStream = new FileOutputStream(outFile);
					//压缩流
					ZipOutputStream out = new ZipOutputStream(outStream);
					//设置编码格式
					out.setEncoding("gbk");
					//压缩文件
					try {
						FileUtil.zipFile(fileList, out);
						out.close();
						outStream.close();
					} catch (Exception e) {
						out.close();
						outStream.close();
					}
					
					//下载
					FileUtil.download(outFile,response);
					
					br.setSuccess(true);
					br.setMsg("Exprot success!");
				}else if(fileList.size() == 1){
					FileUtil.download(fileList.get(0),response);
					br.setSuccess(true);
					br.setMsg("Exprot success!");
				}else{
					br.setSuccess(false);
					br.setMsg("No Data!");
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setMsg("System exception!");
		}finally {
			//删除文件
			if(fileList.size() > 1){
				for (File file1 : fileList) {
					if(file1.exists()){
						file1.delete();
					}
				}
			}	
			if(outFile.exists()){
				outFile.delete();
			}
		} 
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
		
	}
	/**
	 * 生成回款明细表
	 * @param osw
	 * @param product
	 * @param returnList
	 */
	private void productReturnNoticeExcel(FileOutputStream osw,DrProductInfo product,List<Map<String,Object>> returnList){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productReturnNotice.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);
			XSSFSheet sheet = workeBook.getSheetAt(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row0 = sheet.getRow(0);
			row0.getCell(1).setCellValue(new XSSFRichTextString(product.getSimpleName()));
			row0.getCell(2).setCellValue(new XSSFRichTextString("产品编号："+product.getCode()));
			row0.getCell(4).setCellValue(new XSSFRichTextString("借款方："+product.getLoanName()));
			row0.getCell(6).setCellValue(new XSSFRichTextString("年华收益率："+product.getRate()+"%("+product.getDeadline()+"天)"));
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(5).setCellValue(sdf.parse(sdf.format(product.getExpireDate())));
			XSSFRow row = null;
			XSSFRow row3 = sheet.getRow(3);
			int start = 3;
			for(int i = 0; i < returnList.size(); i ++){
				if(i == 0){
					//第四行，模板已经创建，不需要新增
					productreturnNoticeSetRowValues(returnList.get(i), row3, i + 1);
					if(returnList.size() > 1){
						sheet.shiftRows(4, sheet.getLastRowNum(), returnList.size() - 1, true, false);
					}
				}else{
					row = sheet.createRow(start + i);
					copyRow(row3, row);
					productreturnNoticeSetRowValues(returnList.get(i), row, i + 1);
				}
			}
			sheet.setForceFormulaRecalculation(true);
			workeBook.write(osw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 回款表行
	 * @param product
	 * @param row
	 * @throws ParseException
	 */
	private void productreturnNoticeSetRowValues(Map<String,Object> map, XSSFRow row, int i) throws ParseException{
		getCell(row, 0).setCellValue(i);//序号
		getCell(row, 1).setCellValue(new XSSFRichTextString((String)map.get("mobilePhone")));//收款方账号
		getCell(row, 2).setCellValue(new XSSFRichTextString((String)map.get("realname")));//收款方姓名
		getCell(row, 3).setCellValue(((BigDecimal)map.get("shouldPrincipal")).doubleValue());//收款本金
		getCell(row, 4).setCellValue(((BigDecimal)map.get("shouldInterest")).doubleValue());//收款利息
		if(map.get("profitAmount") != null){
			getCell(row, 5).setCellValue(Double.parseDouble(map.get("profitAmount").toString()));//加息金额
		}else{
			getCell(row, 5).setCellValue(0);//加息金额
		}
		getCell(row, 6).setCellValue( Double.parseDouble(map.get("returnAmount").toString()));//收款金额
	}
	/**
	 * 放款表行赋值
	 * @param product
	 * @param sheet
	 * @param index
	 * @throws ParseException
	 */
	private void productPaymentNoticeSetRowValues(DrProductInfo product, XSSFRow row) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		getCell(row, 0).setCellValue(new XSSFRichTextString(product.getLoanCode()));//合同编号
		getCell(row, 1).setCellValue(new XSSFRichTextString(product.getSimpleName()));//产品简称
		getCell(row, 2).setCellValue(new XSSFRichTextString(product.getLoanName()));//债权名称
		getCell(row, 3).setCellValue(new XSSFRichTextString(product.getCompanyName()));//收款单位全称
		getCell(row, 4).setCellValue(product.getAmount().doubleValue());//借款金额
		getCell(row, 5).setCellValue(product.getRate().doubleValue());//年化率
		getCell(row, 6).setCellValue(product.getActivityRate().doubleValue());//年化率
		getCell(row, 7).setCellValue(product.getDeadline().doubleValue());//期限
		BigDecimal interestTotal = product.getAmount().multiply(new BigDecimal(product.getDeadline()))
				.multiply(product.getRate()).divide(new BigDecimal(100)).divide(new BigDecimal(360), 2, BigDecimal.ROUND_FLOOR);
		getCell(row, 8).setCellValue(interestTotal.doubleValue());//利息
		if(product.getShouldInterest()!=null){
			getCell(row, 9).setCellValue(product.getShouldInterest().doubleValue());//基本利息
		}
		getCell(row, 10).setCellValue(product.getAmount().add(interestTotal).doubleValue());//预计应收本息
		if(product.getExpireDate()!=null){
			getCell(row, 11).setCellValue(sdf.parse(sdf.format(product.getExpireDate())));//还款到期日期
		}
		if(product.getExpireDate()!=null){
			getCell(row, 12).setCellValue(Utils.getDayNumOfAppointDate(product.getExpireDate(), 1));//预计还款日期（回款日期前一天）
		}
		
	}
	
	
	/**
	 * 还款表行赋值
	 * @param product
	 * @param sheet
	 * @param index
	 * @throws ParseException
	 */
	private void setRowValues(DrProductInfo product, XSSFRow row) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdffullDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		getCell(row, 0).setCellValue(new XSSFRichTextString(product.getLoanCode()));//合同编号
		getCell(row, 1).setCellValue(new XSSFRichTextString(product.getSimpleName()));//产品简称
		getCell(row, 2).setCellValue(new XSSFRichTextString(product.getLoanName()));//债权名称
		getCell(row, 3).setCellValue(new XSSFRichTextString(product.getCompanyName()));//收款单位全称
		getCell(row, 4).setCellValue(product.getAmount().doubleValue());//借款金额
		getCell(row, 5).setCellValue(product.getRate().doubleValue());//年化率
		getCell(row, 6).setCellValue(product.getActivityRate().doubleValue());//活动利率
		getCell(row, 7).setCellValue(product.getDeadline().doubleValue());//期限
		BigDecimal interestTotal = product.getAmount().multiply(new BigDecimal(product.getDeadline()))
				.multiply(product.getRate()).divide(new BigDecimal(100)).divide(new BigDecimal(360), 2, BigDecimal.ROUND_FLOOR);
		getCell(row, 8).setCellValue(interestTotal.doubleValue());//利息
		if(product.getShouldInterest()!=null){
			getCell(row, 9).setCellValue(product.getShouldInterest().doubleValue());//活动利息
		}
		getCell(row, 10).setCellValue(product.getAmount().add(interestTotal).doubleValue());//预计应收本息
		if(product.getFullDate()!=null){
			getCell(row, 11).setCellValue(sdffullDate.parse(sdffullDate.format(product.getFullDate())));//募集成功日期
		}
		if(product.getEstablish()!=null){
			getCell(row, 12).setCellValue(sdf.parse(sdf.format(product.getEstablish())));//实际付款日期
		}
		getCell(row, 13).setCellValue(product.getAmount().doubleValue());//实际付款金额
		if(product.getExpireDate()!=null){
			getCell(row, 14).setCellValue(Utils.getDayNumOfAppointDate(product.getExpireDate(), 1));//预计还款日期（回款日期前一天）
		}
	}
	
	/**
	 * 获取单元格
	 * @param row
	 * @param index
	 * @return
	 */
	private XSSFCell getCell(XSSFRow row, int index){
		XSSFCell cell = row.getCell(index);
		if(cell == null){
			cell = row.createCell(index);
		}
		return cell;
	}
	//复制行
	private void copyRow(XSSFRow sourceRow, XSSFRow targetRow){
        int columnCount = sourceRow.getLastCellNum();  
        if (sourceRow != null) {  
        	targetRow.setHeight(sourceRow.getHeight());  
            for (int j = 0; j < columnCount; j++) {  
            	XSSFCell templateCell = sourceRow.getCell(j);  
                if (templateCell != null) {  
                    XSSFCell newCell = targetRow.createCell(j);  
                    copyCell(templateCell, newCell);
                }  
            }  
        }  
	}
	//复制单元格
	private void copyCell(XSSFCell srcCell, XSSFCell distCell) {  
        distCell.setCellStyle(srcCell.getCellStyle());  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (srcCellType == XSSFCell.CELL_TYPE_NUMERIC) {  
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                distCell.setCellValue(srcCell.getDateCellValue());  
            } else {  
                distCell.setCellValue(srcCell.getNumericCellValue());  
            }  
        } else if (srcCellType == XSSFCell.CELL_TYPE_STRING) {  
            distCell.setCellValue(srcCell.getRichStringCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_BLANK) {  
            // nothing21  
        } else if (srcCellType == XSSFCell.CELL_TYPE_BOOLEAN) {  
            distCell.setCellValue(srcCell.getBooleanCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_ERROR) {  
            distCell.setCellErrorValue(srcCell.getErrorCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_FORMULA) {  
            distCell.setCellFormula(srcCell.getCellFormula());  
        } else { // nothing29  
  
        }  
    }  
    
	/**
	 * 跳转审核列表
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/toAuditDrProductInfoListSQ")
	public String toAuditDrProductInfoListSQ(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drProductLoanListSQ";
	}
	
	/**
	 * 显示放款审核列表
	 */
	@RequestMapping("/drProductLoanListSQ")
	@ResponseBody
	public PageInfo drProductLoanListSQ(DrProductInfo drProductInfo,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInfoService.getProductLoanListSQ(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示放款账户的信息
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/drProductInfoByidSQ")
	@ResponseBody
	public BaseResult drProductInfoByidSQ(Integer id) {
			BaseResult br = new BaseResult();
			Map<String,Object> map =new HashMap<String,Object>();
	    	try {
	    		map.put("info", drProductInfoService.getDrProductInfoByidSQ(id));
	    		br.setSuccess(true);
	    		br.setMap(map);
	    	} catch (Exception e) {
	    		br.setSuccess(false);
	    		br.setErrorCode("999");
	    	}
	    	return br;
	}
	

	//放款审核接口
	@RequestMapping("/drProductLoanSQ")
	@ResponseBody
	public BaseResult drProductLoanSQ(Integer id,HttpServletRequest request,String actLoanTime,String phone){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			//调用金运通大夫接口
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByid(id);
			if(Utils.isObjectEmpty(drProductInfo)){
				br.setErrorCode("10000");
				br.setErrorMsg("产品不能为空");
				br.setSuccess(false);
				return br;
			}
			BigDecimal alreadyRaiseAmount = drProductInfo.getAlreadyRaiseAmount();//已经募集的金额
			/*调用金运通的借口*/
			//查询标的信息 
			if(Utils.isObjectEmpty(drProductInfo.getSid())){
				br.setErrorCode("10000");
				br.setErrorMsg("该产品未关联标");
				br.setSuccess(false);
				return br;
			}
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
			if(Utils.isObjectEmpty(drSubjectInfo)){
				br.setErrorCode("10000");
				br.setErrorMsg("标不能为空");
				br.setSuccess(false);
				return br;
			}
			DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(drSubjectInfo.getLid());
			if(Utils.isObjectEmpty(drClaimsLoan)){
				br.setErrorCode("10000");
				br.setErrorMsg("债权不能为空");
				br.setSuccess(false);
				return br;
			}
			//生成流水记录
			String tran_flowid = Utils.createOrderNo(6,drSubjectInfo.getId(),"");
			
			//审核记录
			DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(15, null, 
					id, alreadyRaiseAmount, 0, "债券合同["+drClaimsLoan.getNo()+"]放款"+alreadyRaiseAmount+"元,流水号["+tran_flowid+"]",null);
			drCompanyFundsLog.setStatus(0);//状态为不成功
			drCompanyFundsLogService.insertDrCompanyFundsLogFK(drCompanyFundsLog);
			
			
			if(drProductInfo.getProject_no()!=null){//存管
				if(drProductInfo.getProject_no()!=null){
					BaseResult result1 = new BaseResult();
					Map<String, String> m=new HashMap<>();
					m.put("cust_no", phone);
					m.put("amt", alreadyRaiseAmount.toString());
					m.put("rem", drProductInfo.getFullName()+":"+drProductInfo.getId());
					m.put("mchnt_txn_ssn", Utils.createOrderNo(6,drSubjectInfo.getId(),""));
					
					result1=FuiouConfig.unFreeze(m);//解冻
					if(!result1.isSuccess()){//解冻失败
						br.setErrorMsg(result1.getErrorCode()+result1.getErrorMsg()+"解冻失败");
						br.setSuccess(false);
						return br;
					}	
					
					BaseResult result2 = new BaseResult();
					Map<String, String> map=new HashMap<>();
					map.put("login_id", phone);
					map.put("amt", alreadyRaiseAmount.toString());
					map.put("rem", drProductInfo.getFullName()+":"+drProductInfo.getId());
					map.put("mchnt_txn_ssn", tran_flowid);
					result2=FuiouConfig.wtwithdraw(map);
					if(!result2.isSuccess()){//存管委托提现
						//冻结
						BaseResult result3 = new BaseResult();
						Map<String, Object>params=new HashMap<>();
						params.put("uid", "1");//为了生成流水号用到
						params.put("cust_no",phone);
						params.put("amt",alreadyRaiseAmount.toString());
						params.put("rem",drProductInfo.getFullName()+":"+drProductInfo.getId());
						result3= FuiouConfig.freeze(params);
						if(!result3.isSuccess()){//存管委托提现失败
							br.setErrorMsg(result3.getErrorCode()+result3.getErrorMsg()+"冻结失败,请联系技术部");
							br.setSuccess(false);
							return br;
						}				
						br.setMsg(result2.getErrorCode()+"放款失败");
						br.setSuccess(true);
						return br;
					}
					br.setMsg("放款成功");
					br.setSuccess(true);
					drProductInfoService.updateLoanStatus(drProductInfo.getId(),usersVo.getUserKy().intValue(),actLoanTime,tran_flowid);
					drCompanyFundsLog.setStatus(1);//状态改为成功
					drCompanyFundsLogService.upDateDrCompanyFundsLog(drCompanyFundsLog);
				}
				
			}else{//金运通
				//获取债权企业的信息
	//			redisClientTemplate.lock("withdrawals"+member.getUid().toString());
				/*------------------------------------------------------------------------------*/
				JYTSendData sendData = new JYTSendData();
				sendData.setTran_code(MockClientMsgBase.PAY_TRAN_CODE);
				sendData.setBank_name(drClaimsLoan.getBankName());
				sendData.setAccount_no(drClaimsLoan.getBankNo());
				sendData.setAccount_name(drClaimsLoan.getLoanName());
				sendData.setRemark(drProductInfo.getFullName());
				if(!Utils.isObjectEmpty(drClaimsLoan.getCardFlag()) && drClaimsLoan.getCardFlag() == 0){
					sendData.setAccount_type("00");//对私
				}else if(!Utils.isObjectEmpty(drClaimsLoan.getCardFlag()) && drClaimsLoan.getCardFlag() == 1){
					sendData.setAccount_type("01");//对公
				}
				sendData.setTran_amt(alreadyRaiseAmount);
				sendData.setCurrency(MockClientMsgBase.CURRENCY);
				sendData.setBsn_code(MockClientMsgBase.PAY_BSN_CODE);
				sendData.setTran_flowid(tran_flowid);
				sendData.setMobile(usersVo.getMobile());
	//			sendData.setMer_viral_acct(MockClientMsgBase.PAY_ACCOUNT);//代付账号
				
				log.info("金运通发送内容："+sendData.toString());
				JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
				if("S0000000".equals(resultData.getResp_code()) && "01".equals(resultData.getTran_state())){
					drProductInfoService.updateLoanStatus(drProductInfo.getId(),usersVo.getUserKy().intValue(),actLoanTime,null);
					drCompanyFundsLog.setStatus(1);//状态改为成功
					drCompanyFundsLogService.upDateDrCompanyFundsLog(drCompanyFundsLog);
					br.setSuccess(true);
					br.setMsg("放款成功");
				}else if("03".equals(resultData.getTran_state())){
					
					br.setSuccess(false);
					br.setErrorMsg("金运通打款失败：<br/>"+resultData.getResp_desc());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorMsg("系统异常，请联系技术部-李世梁");
		}
		return br;
	}
	
	/**
	 * 跳转到待解冻页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toProductThaw")
	public String toProductThaw(Map<String,Object> model) {
		return "system/product/productThaw";
	}
	
	/**
	 * 查询待解冻列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/getProductThaw")
	@ResponseBody
	public String getProductThaw(HttpServletRequest req) {
		Map<String, Object> map=new HashMap<>();
		List<Map<String, Object>> list=drProductInfoService.getProductByThaw(map);
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	/**
	 * 解冻
	 * @param map
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("/thaw")
	@ResponseBody
	public String thaw(HttpServletRequest req,Integer id) throws SQLException {
		DrProductInfo p = new DrProductInfo(); 
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		List<Map<String, Object>> list=drProductInfoService.getProductByThaw(map);
		if(list.size()>0){
			DrClaimsLoan loan = drClaimsInfoService.getDrClaimsLoanBySid(Integer.parseInt(list.get(0).get("sid").toString()));
			DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(loan.getId());// 借款人
			Map<String, String> thawparams = new HashMap<String, String>();
			String mchnt_txn_ssn=Utils.createOrderNo(6, Integer.parseInt(list.get(0).get("id").toString()), "");
			thawparams.put("mchnt_txn_ssn", mchnt_txn_ssn);
			thawparams.put("cust_no", customer.getPhone());
			thawparams.put("amt",  "" + list.get(0).get("alreadyRaiseAmount").toString());
			thawparams.put("rem", "");
			BaseResult re = FuiouConfig.unFreeze(thawparams);
			if (!re.isSuccess()) {//失败
				return re.getErrorMsg();
			}else{
				p.setId(id);
				p.setThaw(1);
				drProductInfoService.updateDrProductInfo(p);// 把产品解冻状态改成已解冻
			}
			
		}
		return "success";
	}
	
	
	
	/**
	 * 项目数据情况列表
	 */
	@RequestMapping("/getProductInfoDetail")
	@ResponseBody
	public String getProductInfoDetail(Integer page,Integer rows,
			String code,String fullName,String loanName,String contractCode,String startfullDate,String endfullDate,
			String startloanTime,String endloanTime,String startexpireDate,String endexpireDate,String isReimbursement,
			String startserviceTime,String endserviceTime,String isService) {
			Map<String,Object> map =new HashMap<String,Object>();		
			if(page == null){
				page = PageInfo.DEFAULT_PAGE_ON;
			}
			if(rows == null){
				rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
			}
			PageInfo pi = new PageInfo(page,rows);
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit());
			if(code!=null && !code.equals("")){
				map.put("code", code);
			}
			if(fullName!=null && !fullName.equals("")){
				map.put("fullName", fullName);
			}
			if(loanName!=null && !loanName.equals("")){
				map.put("loanName", loanName);
			}
			if(contractCode!=null && !contractCode.equals("")){
				map.put("contractCode", contractCode);
			}
			if(startfullDate!=null && !startfullDate.equals("")){
				map.put("startfullDate", startfullDate);
			}
			if(endfullDate!=null && !endfullDate.equals("")){
				map.put("endfullDate", endfullDate);
			}
			if(startloanTime!=null && !startloanTime.equals("")){
				map.put("startloanTime", startloanTime);
			}
			if(endloanTime!=null && !endloanTime.equals("")){
				map.put("endloanTime", endloanTime);
			}
			if(startexpireDate!=null && !startexpireDate.equals("")){
				map.put("startexpireDate", startexpireDate);
			}
			if(endexpireDate!=null && !endexpireDate.equals("")){
				map.put("endexpireDate", endexpireDate);
			}
			if(isReimbursement!=null && !isReimbursement.equals("")){
				map.put("isReimbursement", isReimbursement);
			}
			if(startserviceTime!=null && !startserviceTime.equals("")){
				map.put("startserviceTime", startserviceTime);
			}
			if(endserviceTime!=null && !endserviceTime.equals("")){
				map.put("endserviceTime", endserviceTime);
			}
			if(isService!=null){
				map.put("isService", isService);
			}
			List<Map<String, Object>> list=drProductInfoService.getProductInfoDetail(map);
			int count=drProductInfoService.getProductInfoDetailCount(map);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("rows", list);
			resultMap.put("total", count);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
			return jsonObject.toString();
	}
	
	/**
	 * 跳转到项目数据情况页面
	 */
	@RequestMapping("/toProductInfoDetail")
	public String toProductInfoDetail(Map<String,Object> model) {
		return "system/product/productInfoDetail";
	}
	
		
	/**
	 * 项目数据情况列表
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/exportProductInfoDetail")
	public ModelAndView exportCompanyFundsLogList(String code,String fullName,String loanName,String contractCode,String startfullDate,String endfullDate,
			String startloanTime,String endloanTime,String startexpireDate,String endexpireDate,String isReimbursement,
			String startserviceTime,String endserviceTime,String isService) throws UnsupportedEncodingException {
			Map<String,Object> map =new HashMap<String,Object>();		
			if(code!=null && !code.equals("")){
				map.put("code", code);
			}
			if(fullName!=null && !fullName.equals("")){
				map.put("fullName", new String(fullName.getBytes("iso-8859-1"),"utf-8"));
			}
			if(loanName!=null && !loanName.equals("")){
				map.put("loanName", new String(loanName.getBytes("iso-8859-1"),"utf-8"));
			}
			if(contractCode!=null && !contractCode.equals("")){
				map.put("contractCode", contractCode);
			}
			if(startfullDate!=null && !startfullDate.equals("")){
				map.put("startfullDate", startfullDate);
			}
			if(endfullDate!=null && !endfullDate.equals("")){
				map.put("endfullDate", endfullDate);
			}
			if(startloanTime!=null && !startloanTime.equals("")){
				map.put("startloanTime", startloanTime);
			}
			if(endloanTime!=null && !endloanTime.equals("")){
				map.put("endloanTime", endloanTime);
			}
			if(startexpireDate!=null && !startexpireDate.equals("")){
				map.put("startexpireDate", startexpireDate);
			}
			if(endexpireDate!=null && !endexpireDate.equals("")){
				map.put("endexpireDate", endexpireDate);
			}
			if(isReimbursement!=null && !isReimbursement.equals("")){
				map.put("isReimbursement", isReimbursement);
			}
			if(startserviceTime!=null && !startserviceTime.equals("")){
				map.put("startserviceTime", startserviceTime);
			}
			if(endserviceTime!=null && !endserviceTime.equals("")){
				map.put("endserviceTime", endserviceTime);
			}
			if(isService!=null){
				map.put("isService", isService);
			}
			map.put("offset",0); 
			map.put("limit",100000000);
			List<Map<String, Object>> list=drProductInfoService.getProductInfoDetail(map);
	
		String[] title = new String[]{"项目产品编号","项目合同编号","项目名称","借款方","项目借款金额","年化率","期限","利息","应收本息","满标日期","实际付款日期","付款金额","预计还款日","实际还款日","还款本金","还款利息","已还/未还","预计服务费金额","实际收到日期","已收服务费金额","已收/未收","是否首笔"};
		Integer[] columnWidth = new Integer[]{30,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map m : list){
			lc = new ArrayList<Object>();
			lc.add(m.get("code")==null?"":m.get("code"));//项目产品编号
			lc.add(m.get("contractCode")==null?"":m.get("contractCode"));//项目合同编号
			lc.add(m.get("fullName")==null?"":m.get("fullName"));//项目名称
			lc.add(m.get("loanName")==null?"":m.get("loanName"));//借款方
			lc.add(m.get("amount")==null?"":m.get("amount"));//项目借款金额
			lc.add(m.get("rate")==null?"":m.get("rate"));//年化率
			lc.add(m.get("deadline")==null?"":m.get("deadline"));//期限
			lc.add(m.get("interest")==null?"":m.get("interest"));//利息
			lc.add(m.get("principalInterest")==null?"":m.get("principalInterest"));//应收本息
			lc.add(m.get("fullDate")==null?"":m.get("fullDate"));//满标日期
			lc.add(m.get("loanTime")==null?"":m.get("loanTime"));//实际付款日期
			lc.add(m.get("loanAmount")==null?"":m.get("loanAmount"));//付款金额
			lc.add(m.get("expireDate")==null?"":m.get("expireDate"));//预计还款日
			lc.add(m.get("factTime")==null?"":m.get("factTime"));//实际还款日
			lc.add(m.get("factPrincipal")==null?"":m.get("factPrincipal"));//还款本金
			lc.add(m.get("factInterest")==null?"":m.get("factInterest"));//还款利息
			lc.add(m.get("isReimbursement")==null?"":m.get("isReimbursement"));//已还/未还
			
			//计算预计服务费
			BigDecimal money=new BigDecimal(0);//预计费用总额
			BigDecimal dbmoney=new BigDecimal(0);//预计担保费
			BigDecimal countAmount=new BigDecimal(m.get("countAmount").toString());
			BigDecimal sumamount=new BigDecimal(m.get("sumamount").toString());
			BigDecimal amount=new BigDecimal(m.get("amount").toString());

			if(countAmount.compareTo(sumamount)!=1){//首笔 预计费用总额=产品金额*8%
				money=amount.multiply(new BigDecimal(8)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);//费用总额
				dbmoney=amount.multiply(new BigDecimal(1.5)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);//预计担保费;
			}else{
				BigDecimal a=countAmount.subtract(sumamount);//计算超出多少
				if(a.compareTo(amount)!=-1){//不小于
					money=amount.multiply(new BigDecimal(1)).multiply(new BigDecimal(m.get("deadline").toString())).divide(new BigDecimal(30), 2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);//全部超出
				}else{
					BigDecimal b=amount.subtract(a);//非超出部分
					money=b.multiply(new BigDecimal(8)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);
					money=a.multiply(new BigDecimal(1)).multiply(new BigDecimal(m.get("deadline").toString())).divide(new BigDecimal(30), 2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN).add(money);
					dbmoney=b.multiply(new BigDecimal(1.5)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);//预计担保费;
				}	
			}
			lc.add(money.subtract(dbmoney));//预计服务费
			lc.add(m.get("serviceTime")==null?"":m.get("serviceTime"));//实际收到日期
			lc.add(m.get("serviceCharge")==null?"":m.get("serviceCharge"));//已收服务费金额
			if(m.get("serviceCharge")!=null){
				lc.add("已收");
			}else{
				lc.add("未收");
			}
			if(countAmount.compareTo(sumamount)!=1){
				lc.add("是");
			}else{
				lc.add("否");
			}	
			tableList.add(lc);
		}
		Map<String,Object> param=new HashMap();
		param.put("excelName", "productInfoDetail_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	
	/**
	 * 跳转到服务费管理页面
	 */
	@RequestMapping("/toCoverChargeList")
	public String toCoverChargeList(Map<String,Object> model) {
		return "system/product/coverChargeList";
	}
	
	/**
	 * 服务费管理列表
	 */
	@RequestMapping("/getProductServiceManagement")
	@ResponseBody
	public String getProductServiceManagement(Integer page,Integer rows,
			String productName,String productCode,String loanName,String startserviceTime,String endserviceTime,
			String startinvoiceTime,String endinvoiceTime,String number,String serviceStatus,String invoiceStatus) {
			Map<String,Object> map =new HashMap<String,Object>();		
			if(page == null){
				page = PageInfo.DEFAULT_PAGE_ON;
			}
			if(rows == null){
				rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
			}
			PageInfo pi = new PageInfo(page,rows);
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit());
			
			if(productName!=null && !productName.equals("")){
				map.put("fullName",productName); 
			}
			if(productCode!=null && !productCode.equals("")){
				map.put("code",productCode); 
			}
			if(loanName!=null && !loanName.equals("")){
				map.put("loanName",loanName); 
			}
			if(startserviceTime!=null && !startserviceTime.equals("")){
				map.put("startserviceTime",startserviceTime); 
			}
			if(endserviceTime!=null && !endserviceTime.equals("")){
				map.put("endserviceTime",endserviceTime); 
			}
			if(startinvoiceTime!=null && !startinvoiceTime.equals("")){
				map.put("startinvoiceTime",startinvoiceTime); 
			}
			if(endinvoiceTime!=null && !endinvoiceTime.equals("")){
				map.put("endinvoiceTime",endinvoiceTime); 
			}
			if(number!=null && !number.equals("")){
				map.put("invoiceNumber",number); 
			}
			if(invoiceStatus!=null && !invoiceStatus.equals("")){
				map.put("invoiceStatus",invoiceStatus); 
			}
			if(serviceStatus!=null && !serviceStatus.equals("")){
				map.put("serviceStatus",serviceStatus); 
			}
			
			List<Map<String, Object>> list=drProductInfoService.getProductServiceManagement(map);
			int count=drProductInfoService.getProductServiceManagementCount(map);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("rows", list);
			resultMap.put("total", count);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultMap);
			return jsonObject.toString();
	}
	
	/**
	 * 导入服务费
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/importCovercharge")
	@ResponseBody
	public BaseResult importCovercharge(HttpServletRequest request,
			@RequestParam(value="file", required=true) MultipartFile file 
			){
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String, String>();
			fieldMap.put("产品编号", "code");
			fieldMap.put("已收服务费", "serviceCharge");
			fieldMap.put("已收担保费", "receivedGuarantee");
			fieldMap.put("备注", "serviceRemerk");
			fieldMap.put("收款时间(yyyy-mm-dd hh:mm:ss)", "serviceTime");
			String[] uniqueFields = new String[]{"产品编号","已收服务费","已收担保费","备注","收款时间(yyyy-mm-dd hh:mm:ss)"};
			List<JsCoverCharge> list = ExcelUtil.excelToList(file.getInputStream(), 0, JsCoverCharge.class, fieldMap, uniqueFields);
			br=drProductInfoService.importCovercharge(list, user.getUserKy());
		} catch (Exception e) {
			log.error("导入服务费失败",e);
			br.setErrorMsg(e.getMessage());
			br.setSuccess(false);
		}
		return br;
	}
	
	
	/**
	 * 导入票据
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/importInvoice")
	@ResponseBody
	public BaseResult importInvoice(HttpServletRequest request,
			@RequestParam(value="invoiceFile", required=true) MultipartFile invoiceFile
			){
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String, String>();
			fieldMap.put("产品编号", "code");
			fieldMap.put("开票时间(yyyy-mm-dd hh:mm:ss)", "invoiceTime");
			fieldMap.put("开票金额", "invoiceAmount");
			fieldMap.put("开票号码", "invoiceNumber");
			fieldMap.put("开票备注", "invoiceRemerk");
			String[] uniqueFields = new String[]{"产品编号","开票时间(yyyy-mm-dd hh:mm:ss)","开票金额","开票号码","开票备注"};
			List<JsInvoice> list = ExcelUtil.excelToList(invoiceFile.getInputStream(), 0, JsInvoice.class, fieldMap, uniqueFields);
			br=drProductInfoService.importInvoice(list, user.getUserKy());
		} catch (Exception e) {
			log.error("导入票据失败",e);
			br.setErrorMsg(e.getMessage());
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 服务费经办
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/insertCharge")
	@ResponseBody
	public BaseResult insertCharge(HttpServletRequest request,
			BigDecimal serviceCharge,
			BigDecimal receivedGuarantee,
			String serviceTime,
			String serviceRemerk,
			String code
			)throws ParseException{
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult br = new BaseResult();
		Map<String, Object> map=new HashMap<>();
		map.put("serviceCharge",serviceCharge);
		map.put("receivedGuarantee",receivedGuarantee);
		map.put("serviceTime",serviceTime);
		map.put("serviceRemerk",serviceRemerk);
		map.put("code",code);
		map.put("userKy",user.getUserKy());
		br=drProductInfoService.insertCharge(map);
		return br;
	}
	
	/**
	 * 撤销服务费
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/deleteCharge")
	@ResponseBody
	public BaseResult deleteCharge(HttpServletRequest request,
			String code){
		BaseResult br = new BaseResult();
		drProductInfoService.deleteCoverCharge(code);
		br.setSuccess(true);
		br.setMsg("撤销成功");
		return br;
	}
	
	/**
	 * 开票信息经办
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/insertInvotice")
	@ResponseBody
	public BaseResult insertInvotice(HttpServletRequest request,
			BigDecimal invoiceAmount,
			String invoiceNumber,
			String invoiceTime,
			String invoiceRemerk,
			String code
			)throws ParseException{
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult br = new BaseResult();
		Map<String, Object> map=new HashMap<>();
		map.put("invoiceAmount",invoiceAmount);
		map.put("invoiceNumber",invoiceNumber);
		map.put("invoiceTime",invoiceTime);
		map.put("invoiceRemerk",invoiceRemerk);
		map.put("code",code);
		map.put("userKy",user.getUserKy());
		br=drProductInfoService.insertInvotice(map);
		return br;
	}
	
	/**
	 * 撤销开票信息
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/deleteInvoice")
	@ResponseBody
	public BaseResult deleteInvoice(HttpServletRequest request,
			String code){
		BaseResult br = new BaseResult();
		drProductInfoService.deleteInvoice(code);
		br.setSuccess(true);
		br.setMsg("撤销成功");
		return br;
	}
	
	/**
	 * 导出服务费管理列表
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/exportCharge")
	public ModelAndView exportCharge(String productName,String productCode,String loanName,String startserviceTime,String endserviceTime,
			String startinvoiceTime,String endinvoiceTime,String number,String invoiceStatus,String serviceStatus) throws UnsupportedEncodingException {
			Map<String,Object> map =new HashMap<String,Object>();		
			
			if(productName!=null && !productName.equals("")){
				map.put("fullName", new String(productName.getBytes("iso-8859-1"),"utf-8")); 
			}
			if(productCode!=null && !productCode.equals("")){
				map.put("code",productCode); 
			}
			if(loanName!=null && !loanName.equals("")){
				map.put("loanName",new String(loanName.getBytes("iso-8859-1"),"utf-8")); 
			}
			if(startserviceTime!=null && !startserviceTime.equals("")){
				map.put("startserviceTime",startserviceTime); 
			}
			if(endserviceTime!=null && !endserviceTime.equals("")){
				map.put("endserviceTime",endserviceTime); 
			}
			if(startinvoiceTime!=null && !startinvoiceTime.equals("")){
				map.put("startinvoiceTime",startinvoiceTime); 
			}
			if(endinvoiceTime!=null && !endinvoiceTime.equals("")){
				map.put("endinvoiceTime",endinvoiceTime); 
			}
			if(number!=null && !number.equals("")){
				map.put("invoiceNumber",number); 
			}
			if(invoiceStatus!=null && !invoiceStatus.equals("")){
				map.put("invoiceStatus",invoiceStatus); 
			}
			if(serviceStatus!=null && !serviceStatus.equals("")){
				map.put("serviceStatus",serviceStatus); 
			}
			map.put("offset",0); 
			map.put("limit",100000000);
			List<Map<String, Object>> list=drProductInfoService.getProductServiceManagement(map);	
		String[] title = new String[]{"项目名称","项目产品编号","借款方名称","期限","产品金额","本年度累计额借款","授信额度","预计费用总额","预计服务费","预计担保费","已收服务费","已收担保费","收款时间","备注","服务费审核状态","开票时间","开票金额","开票号码","开票备注","开票信息复核状态"};
		Integer[] columnWidth = new Integer[]{30,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map m : list){
			lc = new ArrayList<Object>();
			lc.add(m.get("fullName")==null?"":m.get("fullName"));//项目名称
			lc.add(m.get("code")==null?"":m.get("code"));//项目产品编号
			lc.add(m.get("loanName")==null?"":m.get("loanName"));//借款方名称
			lc.add(m.get("deadline")==null?"":m.get("deadline"));//期限
			lc.add(m.get("amount")==null?"":m.get("amount"));//产品金额
			lc.add(m.get("yearAmount")==null?"":m.get("yearAmount"));//本年度累计额借款
			lc.add(m.get("loanAmount")==null?"":m.get("loanAmount"));//授信额度
			
			//计算预计费用总额
			BigDecimal money=new BigDecimal(0);//预计费用总额
			BigDecimal dbmoney=new BigDecimal(0);//预计担保费
			BigDecimal countAmount=new BigDecimal(m.get("countAmount").toString());
			BigDecimal loanAmount=new BigDecimal(m.get("loanAmount").toString());
			BigDecimal amount=new BigDecimal(m.get("amount").toString());

			if(countAmount.compareTo(loanAmount)!=1){//首笔 预计费用总额=产品金额*8%
				money=amount.multiply(new BigDecimal(8)).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);//费用总额
				dbmoney=amount.multiply(new BigDecimal(1.5)).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);//预计担保费;
			}else{
				BigDecimal a=countAmount.subtract(loanAmount);//计算超出多少
				if(a.compareTo(amount)!=-1){//不小于
					money=amount.multiply(new BigDecimal(1)).multiply(new BigDecimal(m.get("deadline").toString())).divide(new BigDecimal(30), 2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);//全部超出
				}else{
					BigDecimal b=amount.subtract(a);//非超出部分
					money=b.multiply(new BigDecimal(8)).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
					money=a.multiply(new BigDecimal(1)).multiply(new BigDecimal(m.get("deadline").toString())).divide(new BigDecimal(30), 2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN).add(money);
					dbmoney=b.multiply(new BigDecimal(1.5)).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);//预计担保费;
				}	
			}
			lc.add(money.toString());//预计费用总额
			lc.add(money.subtract(dbmoney).toString());//预计服务费
			lc.add(dbmoney.toString());//预计担保费
			lc.add(m.get("serviceCharge")==null?"":m.get("serviceCharge"));//已收服务费
			lc.add(m.get("receivedGuarantee")==null?"":m.get("receivedGuarantee"));//已收担保费
			lc.add(m.get("serviceTime")==null?"":m.get("serviceTime"));//收款时间
			lc.add(m.get("serviceRemerk")==null?"":m.get("serviceRemerk"));//备注
			lc.add(m.get("serviceStatus")==null?"":m.get("serviceStatus").equals(1)?"服务费待审核":m.get("serviceStatus").equals(2)?"服务费复审通过":"服务费复审驳回");//服务费审核状态
			lc.add(m.get("invoiceTime")==null?"":m.get("invoiceTime"));//开票时间
			lc.add(m.get("invoiceAmount")==null?"":m.get("invoiceAmount"));//开票金额
			lc.add(m.get("invoiceNumber")==null?"":m.get("invoiceNumber"));//开票号码
			lc.add(m.get("invoiceRemerk")==null?"":m.get("invoiceRemerk"));//开票备注
			lc.add(m.get("invoiceStatus")==null?"":m.get("invoiceStatus").equals(1)?"开票信息待审核":m.get("invoiceStatus").equals(2)?"开票信息审通过":"开票信息复审驳回");//开票信息审核状态
			tableList.add(lc);
		}
		Map<String,Object> param=new HashMap();
		param.put("excelName", "coverCharge_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("columnWidth", columnWidth);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	/**
	 * 跳转到服务费管理审核
	 */
	@RequestMapping("/toCoverChargeAudit")
	public String toCoverChargeAudit(Map<String,Object> model) {
		return "system/product/coverChargeAudit";
	}
	
	/**
	 * 服务费复核
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/updateCharge")
	@ResponseBody
	public BaseResult updateCharge(HttpServletRequest request,
			String serviceRemerk,
			String code,
			String serviceStatus
			)throws ParseException{
		BaseResult br = new BaseResult();
		Map<String, Object> map=new HashMap<>();
		map.put("serviceRemerk",serviceRemerk);
		map.put("code",code);
		if(serviceStatus.equals("1")){//通过
			map.put("serviceStatus",2);
		}else{
			map.put("serviceStatus",3);//驳回
		}
		br=drProductInfoService.updateCharge(map);
		return br;
	}
	
	/**
	 * 服务费复核
	 * @param request
	 * @param multipartFile
	 * @param coupons
	 * @param couponNames
	 * @param cirId
	 * @return
	 */
	@RequestMapping("/updateInvoice")
	@ResponseBody
	public BaseResult updateInvoice(HttpServletRequest request,
			String invoiceRemerk,
			String code,
			String invoiceStatus
			)throws ParseException{
		BaseResult br = new BaseResult();
		Map<String, Object> map=new HashMap<>();
		map.put("invoiceRemerk",invoiceRemerk);
		map.put("code",code);
		if(invoiceStatus.equals("1")){//通过
			map.put("invoiceStatus",2);
		}else{
			map.put("invoiceStatus",3);//驳回
		}
		br=drProductInfoService.updateInvoice(map);
		return br;
	}
	
	/**
	 * 跳转到服务费撤销页面
	 */
	@RequestMapping("/toRepealCoverCharge")
	public String toRepealCoverCharge(Map<String,Object> model) {
		return "system/product/repealCoverCharge";
	}
	
	/**
	 * 存管回款列表
	 * Deposit存管
	 */
	@RequestMapping("/toDepositReturnLoan")
	public String toDepositReturnLoan(Map<String,Object> model,String menuType){
		try {
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("menuType", menuType);//1：财务 显示回款按钮。2非财务 不显示回款按钮
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "system/product/depositReturnLoan";
	}
	
	@RequestMapping("/depositReturnLoanList")
	@ResponseBody
	public PageInfo depositReturnLoanList(HttpServletRequest request,DrProductInfo drProductInfo,Integer page,Integer rows){
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(user.getRoleCode().equals("Enterprise-User")){//Enterprise-User 企业用户角色code
			drProductInfo.setLoginId(user.getLoginId());
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductInfoService.getDepositReturnLoanList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 风控放款回款管理跳转页面
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/toRiskControlDrProductLoanList")
	public String toRiskControlDrProductLoanList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drProductLoanListForRiskControl";
	}
	
	/**
	 * 获取放回款金额总和
	 * @param drProductInfo
	 * @return
	 */
	@RequestMapping("/drProductLoanAmountSum")
	@ResponseBody
	public Map<String,Object> drProductLoanAmountSum(DrProductInfo drProductInfo){
		return drProductInfoService.selectDrProductLoanAmountSum(drProductInfo);
	}
	
	/**
	 * 跳转到存管新手标放款管理页面
	 */
	@RequestMapping("/toDrProductLoanListFuiou")
	public String toDrProductLoanListFuiou(HttpServletRequest req,Map<String,Object> model,String menuType) {
		try{
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("menuType", menuType);//1：财务。2非财务
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/drProductLoanListFuiou";
	}
	
	/**
	 * 存管新手标回款列表
	 * Deposit存管
	 */
	@RequestMapping("/toDepositReturnLoanFuiou")
	public String toDepositReturnLoanFuiou(Map<String,Object> model,String menuType){
		try {
			model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
			model.put("menuType", menuType);//1：财务 显示回款按钮。2非财务 不显示回款按钮

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "system/product/depositReturnLoanFuiou";
	}
}