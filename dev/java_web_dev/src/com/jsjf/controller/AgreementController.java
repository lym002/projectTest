package com.jsjf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ItextPdfUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.claims.DrClaimsBillService;
import com.jsjf.service.claims.DrClaimsLoanService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.product.DrProductInvestTransferService;

@Controller
@RequestMapping("/agreement")
public class AgreementController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInvestTransferService drProductInvestTransferService;
	@Autowired
	private DrClaimsBillService drClaimsBillService;
	@Autowired
	private DrClaimsLoanService drClaimsLoanService;
	
	/**
	 * 投资协议
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="product",method = RequestMethod.POST)
	@ResponseBody
	public String productAgreement(HttpServletRequest req, @RequestBody Map<String, String> map){
		BaseResult br = new BaseResult();
		try {
			Integer pid = Integer.parseInt(map.get("pid"));
			Integer uid = Integer.parseInt(map.get("uid"));
			Integer investId = Integer.parseInt(map.get("investId"));
			if(investId == null){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			map.clear();
			//转让人
			map.put("intermediary", "刘神");
			map.put("intermediaryCard", "352202198708092513");
			
			//投资人信息
			DrProductInvest invest = drProductInvestService.selectByPrimaryKey(investId);
			if(Utils.isObjectEmpty(invest) || !invest.getPid().equals(pid) || !invest.getUid().equals(uid)){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			DrMember m = drMemberService.selectByPrimaryKey(uid);
			DrProductInfo info = drProductInfoService.selectProductByPrimaryKey(pid);
			
			map.put("realName", m.getRealName());
			map.put("idCard", m.getIdCards());
			map.put("mobilephone", m.getMobilephone());
			map.put("investAmount", invest.getFactAmount().toString());
			map.put("rate", info.getRate().toString());
			map.put("productName", info.getFullName());
			map.put("deadline", info.getDeadline().toString());
			if(info.getType() == 1 || info.getType() == 4){
				map.put("date", Utils.getDayNumOfAppointDate(invest.getInvestTime(), -1, "yyyy年MM月dd日") );
			}else{
				map.put("date", Utils.getDayNumOfAppointDate(info.getExpireDate(), info.getDeadline(), "yyyy年MM月dd日") );
			}
			map.put("investTime", Utils.format(invest.getInvestTime(), "yyyy年MM月dd日"));
			map.put("agreementNo", invest.getAgreementNo());
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("协议信息读取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 借款协议
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="borrow",method = RequestMethod.POST)
	@ResponseBody
	public String borrowAgreement(HttpServletRequest req, @RequestBody Map<String, String> map){
		BaseResult br = new BaseResult();
		try {
			Integer pid = Integer.parseInt(map.get("pid"));
			Integer uid = Integer.parseInt(map.get("uid"));
			Integer investId = Integer.parseInt(map.get("investId"));
			if(investId == null){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			
			DrProductInvest invest = drProductInvestService.selectByPrimaryKey(investId);//投资信息
			if(Utils.isObjectEmpty(invest) || !invest.getPid().equals(pid) || !invest.getUid().equals(uid)){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			DrMember m = drMemberService.selectByPrimaryKey(uid);
			map.clear();
			DrProductInfo info = drProductInfoService.selectProductByPrimaryKey(pid);//产品信息
			if(info.getType() != 1 && info.getType() != 4){
				DrClaimsBill bill = drClaimsBillService.getDrClaimsBillBySid(info.getSid());//票据信息
				DrClaimsLoan loan = drClaimsLoanService.selectByPrimaryKey(bill.getLid());//债权信息
				map.put("borrowName", loan.getCompanyNameProtocolShow()!=null?loan.getCompanyNameProtocolShow():null);
				map.put("businessNum", StringUtils.isNotBlank(loan.getBusinessNo())?loan.getBusinessNo().substring(0,loan.getBusinessNo().length()-4)+"****":null);
				map.put("loanName", loan.getLoanName());
			}
				
			map.put("investName", m.getRealName());
			map.put("agreementNo", invest.getAgreementNo());
			map.put("mobilephone", m.getMobilephone());
			map.put("idCard", m.getIdCards().substring(0,6)+"**********"+m.getIdCards().substring(m.getIdCards().length()-4));
			if(info.getType() == 1){
				//新手标天数上线是28天，9月28后之后，新手标改为15天
				List<Map<String, Object>>repayResult = drProductInvestRepayInfoService.selectRepayInfoDetail(investId);
				if(repayResult != null && !repayResult.isEmpty() && repayResult.size() > 0){
					int days = Utils.daysOfTwo(invest.getInvestTime(), Utils.parse((String)repayResult.get(0).get("date"), "yyyy-MM-dd")) - 1;
					map.put("deadline", "" + days);
				}
			}else{
				map.put("deadline", info.getDeadline().toString());
			}
			map.put("investTime", Utils.format(invest.getInvestTime(), "yyyy年MM月dd日"));
			map.put("factAmount", invest.getAmount().toString());
			map.put("rate", info.getRate().toString());
			map.put("activityRate", info.getActivityRate().toString());
			map.put("code", info.getCode());
			map.put("expectedBX", invest.getAmount().add(invest.getInterest()).toString());//预计本息
			map.put("expectedhksj", Utils.format(info.getExpireDate(), "yyyy年MM月dd日"));
			map.put("type", info.getType().toString());
			map.put("businessNo", "91310120323128877Y");
			
			//回款记录
			List<Map<String,Object>> list = drProductInvestRepayInfoService.selectRepayInfoDetail(invest.getId()); 
			int period = info.getRepayType() ==2 || info.getRepayType() ==4 ? 30 : (info.getRepayType() == 3 ? 7 : info.getDeadline());
			int deadline = info.getDeadline()/period;//期限数	
			int i = 0;
			BigDecimal residualPri = BigDecimal.ZERO;
			
			for (Map<String, Object> para : list) {
				para.put("index", ++i);
				residualPri = residualPri.add( (BigDecimal)para.get("shouldPrincipal"));
				para.put("shouldSum", ((BigDecimal)para.get("shouldPrincipal")).add((BigDecimal)para.get("shouldInterest")));
				para.put("residualPrincipal", invest.getAmount().subtract(residualPri) );
			}
			
			map.put("period", deadline+"");
			map.put("result", JSONObject.toJSONString(list));
			map.put("interest", invest.getFactInterest().toString());
			map.put("shouldAmount", invest.getFactAmount().add(invest.getFactInterest()).toString());
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("借款信息读取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 借款协议
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="transfer",method = RequestMethod.POST)
	@ResponseBody
	public String transferAgreement(HttpServletRequest req, @RequestBody Map<String, String> map){
		BaseResult br = new BaseResult();
		try {
			Integer pid = Integer.parseInt(map.get("pid"));
			Integer uid = Integer.parseInt(map.get("uid"));
			Integer investId = Integer.parseInt(map.get("investId"));
			if(investId == null){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			
			DrProductInvest invest = drProductInvestService.selectByPrimaryKey(investId);//投资信息
			if(Utils.isObjectEmpty(invest) || !invest.getPid().equals(pid) || !invest.getUid().equals(uid)){
				br.setSuccess(true);
				return JSON.toJSONString(br);
			}
			DrMember m = drMemberService.selectByPrimaryKey(uid);
			DrProductInfo info = drProductInfoService.selectProductByPrimaryKey(pid);//产品信息
			DrClaimsBill bill = drClaimsBillService.getDrClaimsBillBySid(info.getSid());//票据信息
			
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("agreementNo", invest.getAgreementNo());
			mp.put("investName", m.getRealName());
			mp.put("idCard", m.getIdCards());
			mp.put("userName", m.getMobilephone());
			mp.put("acceptor", bill.getAcceptor());
			mp.put("investName_1", m.getRealName());
			mp.put("investAmount", invest.getFactAmount().toString());
			mp.put("loanForceDate", Utils.format(info.getEstablish(), "yyyy年MM月dd日"));
			mp.put("loanExpireDate", Utils.format(bill.getEndDate(), "yyyy年MM月dd日"));
			mp.put("productExpireDate", Utils.format(Utils.getDayNumOfAppointDate(info.getExpireDate(), 1), "yyyy年MM月dd日"));
			mp.put("productRepaymentDate", Utils.format(info.getExpireDate(), "yyyy年MM月dd日"));
			mp.put("rate", info.getRate().toString());
			mp.put("activityRate", info.getActivityRate().toString());
			mp.put("transferList", drProductInvestTransferService.selectTransferInfoByAssigneeUid(info.getId(), m.getUid(),invest.getId()));
			br.setMap(mp);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("债权转让信息读取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 下载合同
	 * @param res
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping("/download")  
	public void download(HttpServletResponse res,HttpServletRequest req) throws IOException{
		System.out.println("开始下载合同");
		Integer pid = Integer.parseInt(req.getParameter("pid"));
		Integer investId = Integer.parseInt(req.getParameter("investId"));
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		
		DrProductInfo info = drProductInfoService.selectProductByPrimaryKey(pid);//产品信息
		DrProductInvest invest = drProductInvestService.selectByPrimaryKey(investId);//投资信息
		String fileaddress = req.getSession().getServletContext().getRealPath("/");//项目目录
		
		OutputStream os = res.getOutputStream();
		Map<String, String> map = new HashMap<String, String>();
		try {

			if(info.getType() != 1 && info.getType() != 4){
				DrClaimsBill bill = drClaimsBillService.getDrClaimsBillBySid(info.getSid());//票据信息
				DrClaimsLoan loan = drClaimsLoanService.selectByPrimaryKey(bill.getLid());//债权信息
				map.put("borrowName", loan.getCompanyNameProtocolShow());
				map.put("businessNum", loan.getBusinessNo().substring(0,loan.getBusinessNo().length()-4)+"****");
				map.put("loanName", loan.getLoanName());
			}
//			}else if(info.getType() == 1){
//				map.put("borrowName", "上海术千实业有限公司");//借款人
//				map.put("businessNum", "310115002706869");//借款人营业执照
//				map.put("loanName", "上海术千实业有限公司");//账户名称
//			}
				
			map.put("investName", m.getRealName());//出借人
			map.put("agreementNo", invest.getAgreementNo());//协议号
			map.put("mobilephone", m.getMobilephone());//会员账号
			map.put("idCard", m.getIdCards().substring(0,6)+"**********"+m.getIdCards().substring(m.getIdCards().length()-4));//会员身份证
			if(info.getType() == 1){
				//新手标天数上线是28天，9月28后之后，新手标改为15天
				List<Map<String, Object>>repayResult = drProductInvestRepayInfoService.selectRepayInfoDetail(investId);
				if(repayResult != null && !repayResult.isEmpty() && repayResult.size() > 0){
					int days = Utils.daysOfTwo(invest.getInvestTime(), Utils.parse((String)repayResult.get(0).get("date"), "yyyy-MM-dd")) - 1;
					map.put("deadline", "" + days);
				}
			}else{
				map.put("deadline", info.getDeadline().toString());//期限
			}
			map.put("investTime", Utils.format(invest.getInvestTime(), "yyyy年MM月dd日"));//投资日期
			map.put("factAmount", invest.getAmount().toString());//投资金额
			BigDecimal rate = BigDecimal.ZERO;
			if(Utils.isObjectNotEmpty(info.getActivityRate())){
				rate =  Utils.nwdBcadd(info.getRate(), info.getActivityRate());
			}else {
				rate = info.getRate();
			}
			map.put("rate", rate.toString());//利率
			map.put("code", info.getCode());//产品编号
			map.put("interest", invest.getInterest().toString());//利息
			map.put("expectedBX", invest.getAmount().add(invest.getInterest()).toString());//预计本息
			map.put("expectedhksj", Utils.format(info.getExpireDate(), "yyyy年MM月dd日"));//到期日
//			map.put("type", info.getType().toString());//产品类型
//			map.put("businessNo", "91310113067826632E");//公司营业执照
			
			
			//回款记录
			List<Map<String,Object>> list = drProductInvestRepayInfoService.selectRepayInfoDetail(invest.getId()); 
			int period = info.getRepayType() ==2 || info.getRepayType() ==4 ? 30 : (info.getRepayType() == 3 ? 7 : info.getDeadline());
			int deadline = info.getDeadline()/period;//期限数	
			map.put("period", deadline+"");
			int i = 0;
			BigDecimal residualPri = BigDecimal.ZERO;//
			BigDecimal bxhj = BigDecimal.ZERO;//本金合计
			BigDecimal sybj = BigDecimal.ZERO;//剩余本金
			Map<String,Object> mapPdftable  = new HashMap<String, Object>();
			List<String[]> listPdftable = new ArrayList<String[]>();
			float[] widths = { 20f, 25f, 25f, 25f, 25f, 25f};
			String[] headers = {"期数", "还款日", "应收本金（元）", "应收利息（元）", "应收总额（元）", "剩余本金（元）" };
			String[] footer = {"总计", "", invest.getAmount().toString(),  invest.getInterest().toString(),  invest.getAmount().add(invest.getInterest()).toString(),  ""};
			
			for (Map<String, Object> para : list) {
				para.put("index", ++i);
				residualPri = residualPri.add( (BigDecimal)para.get("shouldPrincipal"));
				bxhj =( (BigDecimal)para.get("shouldInterest")).add((BigDecimal)para.get("shouldPrincipal"));
				sybj = invest.getAmount().subtract(residualPri);
				
				//
				listPdftable.add(new String[]{""+i,para.get("date").toString(),para.get("shouldPrincipal").toString(),para.get("shouldInterest").toString(),bxhj.toString(),sybj.toString() });
			}
			
			mapPdftable.put("body", listPdftable);
			mapPdftable.put("widths", widths);
			mapPdftable.put("headers", headers);
			mapPdftable.put("footer", footer);
			
			System.out.println("开始加载模板");
			String tempFile = fileaddress+"pdf/jkxytemp.pdf";//模板文件
			String tableFile = fileaddress+"pdf/回款table.pdf";//pdf table 中间文件用来做合并			
			String contentFile = fileaddress+"pdf/借款协议content.pdf";//生成后的借款协议
			String savePDF = fileaddress+"pdf/借款协议.pdf";//生成后的借款协议
			System.out.println(tempFile+"=====================================");
			//填充借款协议
			ItextPdfUtil.fromPDFTempletToPdfWithValue(tempFile, contentFile , map);
			
			ItextPdfUtil.pdfTable(tableFile, mapPdftable);//生成pdf表格
			
			String[] files = new String[]{contentFile,tableFile};//在 生成后的pdf 模版中追加 table
			
			ItextPdfUtil.mergePDF(files, savePDF);//合并
			
			res.reset();  
	        res.setHeader("Content-Disposition", "attachment; filename="+invest.getAgreementNo()+".pdf");  
	        res.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(savePDF)));  
	        os.flush();
	        System.out.println("完成下载");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {  
	        if (os != null) {  
	            os.close();  
	        }  
	    } 
		
	    
	}  
	/**
	 * pdf添加table
	 * @param file
	 * @param map {"widths":float[],"headers":String[],"footer":String[],"list":List<String[]>}
	 * @throws Exception
	 */
	public static void pdfTable(String file,Map<String,Object> map) throws Exception{
		
		if(!(map.containsKey("body") && map.containsKey("headers") && map.containsKey("widths"))){
			throw new  Exception("参数错误");
		}
		float[] widths = (float[]) map.get("widths");//{ 20f, 25f, 25f, 25f, 25f, 25f};// 设置表格的列宽和列数
		String[] headers = (String[]) map.get("headers");// {"期数", "还款日", "应收本金（元）", "应收利息（元）", "应收总额（元）", "剩余本金（元）" };//表头
		String[] footer = (String[]) map.get("footer");//{"总数", "", "1",  "2",  "3",  ""}
		
		List<String[]> list = (List<String[]>) map.get("body");
		
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bf, 8); // 其他所有文字字体
		Document document = new Document(PageSize.A4, 80, 79, 20, 45);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
    	
		Paragraph title = new Paragraph("附：回款详情", FontChinese);
		title.setLeading(30f);// 设置行间距
		document.add(title);
		
		//添加表格
		
		PdfPTable table = new PdfPTable(widths);
		table.setSpacingBefore(20);
		table.setWidthPercentage(100);// 设置表格宽度为%100
		//添加表头
		
		PdfPCell cell = null;
		for (String header : headers) {
			cell = new PdfPCell(new Paragraph(header, FontChinese));// 描述
			cell.setFixedHeight(18);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
		}
		
		//添加body
		for (String[] tr : list) {
			for (String td : tr) {
				cell = new PdfPCell(new Paragraph(td, FontChinese));
				cell.setFixedHeight(18);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
				table.addCell(cell);
			}
		}
		
		//添加 footer
		if(footer !=null && footer.length >0){
			for (String td : footer) {
				cell = new PdfPCell(new Paragraph(td, FontChinese));
				cell.setFixedHeight(18);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
				table.addCell(cell);
			}
		}
						  
		document.add(table);
		document.close();
		writer.close();
         
	}

}
