package com.jsjf.controller.subject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.claims.DrAuditInfo;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.claims.DrClaimsFinanc;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.claims.JsLoanRecord;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.subject.DrSubjectInfoService;

@Controller
@RequestMapping("/subject")
public class DrSubjectInfoController{
	
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private DrSubjectInfoService drSubjectInfoService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	
	/**
	 * 获取放款记录
	 * @return
	 */
	@RequestMapping("/getJsLoanRecordList")
	@ResponseBody
	public PageInfo getJsLoanRecordList(HttpServletRequest reg,Integer page,Integer rows,JsLoanRecord jlr){
		try {
			return drClaimsInfoService.selectJsLoanRecord(page, rows, jlr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转到放款记录
	 */
	@RequestMapping("/toJsLoanRecordList")
	public String toJsLoanRecordList(HttpServletRequest reg,Map<String,Object> model) {
	
		return "system/subject/jsLoanRecordList";
	}
	/**
	 * 跳转到标的新增页面
	 */
	@RequestMapping("/toAddDrSubjectInfoList")
	public String toAddDrSubjectInfoList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("claimsStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/subject/addDrSubjectInfoList";
	}
	
	/**
	 * 显示标的新增列表数据
	 * @param DrClaimsLoan
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/addDrSubjectInfoList")
	@ResponseBody
	public PageInfo addDrSubjectInfoList(DrClaimsLoan drClaimsLoan,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		if(drClaimsLoan.getStatus() == null){
			drClaimsLoan.setStatus(300);
		}
		BaseResult result = drClaimsInfoService.getDrClaimsLoanList(drClaimsLoan, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到标的审核页面
	 * @param req
	 * @param model
	 * @param lid
	 * @return
	 */
	@RequestMapping("/toAuditDrSubjectInfo")
	public String toAuditDrSubjectInfo(HttpServletRequest req,Map<String,Object> model,Integer lid){
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
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", lid);
			map.put("type", 1);
			map.put("sort", "ASC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(map));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/subject/auditDrSubjectInfo";
	}
	
	/**
	 * 添加债权审核信息
	 * @param req
	 * @param lid
	 * @param opinion
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/addDrSubjectInfoAudit")
	@ResponseBody
	public BaseResult addDrSubjectInfoAudit(Integer lid,String opinion,Integer status,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
//			//生成标的时判断是否存管开户
//			if(status == 4){
//				DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(lid);
//				if(StringUtils.isBlank(customer.getUser_id())){
//					br.setMsg("该标的对应的企业未开通存管账户");
//					br.setSuccess(false);
//					return br;
//				}
//			}
			request.setCharacterEncoding("UTF-8");
			String opinionStr=new String((request.getParameter("opinion")).getBytes("iso-8859-1"),"utf-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrAuditInfo drAuditInfo = new DrAuditInfo();
			drAuditInfo.setFid(lid);
			drAuditInfo.setOpinion(opinionStr);
			drAuditInfo.setStatus(status);
			drAuditInfo.setType(1);
			drAuditInfo.setAddUser(usersVo.getUserKy().intValue());
			drClaimsInfoService.insertDrSubjectInfoAudit(drAuditInfo);
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
	 * 跳转到标的查询页面
	 */
	@RequestMapping("/toDrSubjectInfoList")
	public String toDrSubjectInfoList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("subjectStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/subject/drSubjectInfoList";
	}
	
	/**
	 * 显示标的查询列表数据
	 * @param DrSubjectInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drSubjectInfoList")
	@ResponseBody
	public PageInfo drSubjectInfoList(DrSubjectInfo drSubjectInfo,Integer page,Integer rows,String sort,String order) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drSubjectInfoService.getDrSubjectInfoList(drSubjectInfo, pi,sort,order);
		
		return (PageInfo)result.getMap().get("page");
	}
	
	
	/**
	 * 跳转到标的池查询页面
	 */
	@RequestMapping("/toSubjectPoolList")
	public String toSubjectPoolList(Map<String,Object> model,
			@RequestParam(value="code",required=false)String code) {
		model.put("pcode", code);
		return "system/subject/subjectPoolList";
	}
	
	/**
	 * 获取标的池列表
	 * @param subjectInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/subjectPoolList")
	@ResponseBody
	public PageInfo subjectPoolList(DrSubjectInfo subjectInfo,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drSubjectInfoService.getDrSubjectPoolList(subjectInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到标的详情页面
	 */
	@RequestMapping("/showDrSubjectInfo")
	public String showDrSubjectInfo(Map<String,Object> model,Integer id) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("subjectStatus")));
			model.put("type", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("billType")));
			model.put("auditType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("auditType")));
			
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(id);
			model.put("drSubjectInfo", drSubjectInfo);
			
			if(Utils.isObjectNotEmpty(drSubjectInfo.getStartDate())){
				model.put("subjectStartDate", Utils.getparseDate(drSubjectInfo.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(Utils.isObjectNotEmpty(drSubjectInfo.getEndDate())){
				model.put("subjectEndDate", Utils.getparseDate(drSubjectInfo.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fid", drSubjectInfo.getLid());
			map.put("type", "1");
			map.put("sort", "DESC");
			model.put("drAuditInfo",  drClaimsInfoService.getDrAuditInfo(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/subject/showDrSubjectInfo";
	}
	/**
	 * 获取标的产品列表
	 * */
	
	@RequestMapping(value= "/showSubjectProductInfoBtn")
	@ResponseBody
	public PageInfo toShowSubjectProductInfoBtn(DrProductInfo drProductInfo,Integer page,Integer rows) {
		
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drSubjectInfoService.getSubjectProductInfoList(drProductInfo, pi);
		return (PageInfo)result.getMap().get("page");//获取对应key的值
	}
	/** 
	 * 跳转到标的产品列表页面
	 */
	@RequestMapping("/toSubjectDrProductInfoList")
	public String toSubjectDrProductInfoList(Map<String,Object> model, Integer id) {
		try {
			model.put("id", id);
			model.put("drProductInfo", drSubjectInfoService.getDrSubjectInfoByid(id));
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/subject/subjectDrProductInfoList";
	}
	
	/**
	 * 修改标的状态信息
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateDrSubjectInfo")
	@ResponseBody
	public BaseResult updateDrSubjectInfo(Integer id,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			if(Utils.isObjectEmpty(id)){
				br.setMsg("该标的不存在!");
				br.setSuccess(true);
				return br;
			}
			
			DrSubjectInfo drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(id);
			if(drSubjectInfo.getStatus() != 1){
				br.setMsg("该标的不可以入池！");
				br.setSuccess(true);
				return br;
			}
			drSubjectInfoService.updateDrSubjectInfo(id,usersVo);
			br.setMsg("入池成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setMsg("入池失败!");
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
     * 导出标的查询列表
     * @param req
     * @param drClaimsLoan
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportDrSubjectInfo")
	public ModelAndView exportDrSubjectInfo(HttpServletRequest req,DrSubjectInfo drSubjectInfo,String order) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//两种日期格式转换之一
	   Map<String, Object> param = new HashMap<String, Object>();
		param.put("no", drSubjectInfo.getNo());
		param.put("code", drSubjectInfo.getCode());
		param.put("amount", drSubjectInfo.getAmount());
		param.put("surplusAmount", drSubjectInfo.getSurplusAmount());
		param.put("startDate", drSubjectInfo.getStartDate());
		param.put("endDate", drSubjectInfo.getEndDate());
		if(drSubjectInfo.getStatus()!=null){
			param.put("status", drSubjectInfo.getStatus());
		}
		if(order != null){
			param.put("order", order);
		}

		List<DrSubjectInfo> drSubjectInfoList = drSubjectInfoService.exportDrSubjectInfo(param);
		
		String[] title = new String[]{"序号","标的编号","债权编号","公司名称","标的金额(元)","剩余金额(元)","到期日期","标的状态","产品名称"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		
		List<Object> lc = null;
		int i = 1;
		for(DrSubjectInfo drSubjectInf : drSubjectInfoList){
			lc = new ArrayList<Object>();
			lc.add(i++);//序号
			lc.add(drSubjectInf.getCode());//标的编号
			lc.add(drSubjectInf.getNo());//债权编号
			lc.add(drSubjectInf.getCompanyName());//公司名称
			lc.add(drSubjectInf.getAmount()==null?"":drSubjectInf.getAmount());//金额
			lc.add(drSubjectInf.getSurplusAmount()==null?"":drSubjectInf.getSurplusAmount());//剩余金额(元)
			lc.add(sdf.format(drSubjectInf.getEndDate()));//到期日期
			lc.add(drSubjectInf.getStatusName());//标的状态
			lc.add(drSubjectInf.getSimpleName()==null?"":drSubjectInf.getSimpleName());//产品名称
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "Subject_Info"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
   
	
}