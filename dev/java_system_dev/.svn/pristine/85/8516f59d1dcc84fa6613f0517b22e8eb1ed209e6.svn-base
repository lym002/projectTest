package com.jsjf.controller.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.FileUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrproductLoanRecord;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductLoanRecodService;

@SuppressWarnings("all")
@Controller
@RequestMapping("/loanRecord")
public class DrProductLoanRecordController {
	@Autowired
	private DrProductLoanRecodService drProductLoanRecodService;
	
	@Autowired
	private DrProductInfoService drProductInfoService;
	/**
	 * 跳转到新手标的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toDrProductLoanRecordList")
	public String toDrProductLoanList(Map<String,Object> model) {
		return "system/product/drProductLoanRecordList";
	}
	/**
	 * 新手标列表查询
	 * @param drproductLoanRecord
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/drProductLoanRecordList")
	@ResponseBody
	public PageInfo drProductLoanRecordList(DrproductLoanRecord drproductLoanRecord,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drProductLoanRecodService.drProductLoanRecordList(drproductLoanRecord, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 新手标放款状态更新
	 * @param id
	 * @param request
	 * @param actLoanTime
	 * @return
	 */
	@RequestMapping(value="/updateDrProductLoanRecordBtn")
	@ResponseBody
	public BaseResult updateDrProductLoanRecordBtn(Integer id,HttpServletRequest request,String loanTime){
    	BaseResult br = new BaseResult();
    	try {
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("id", id);
    		map.put("loanTime", loanTime);
    		drProductLoanRecodService.updateDrProductLoanStatus(map);
			br.setSuccess(true);
			br.setMsg("操作成功！");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("999");
		}
    	return br;
    }
	/**
	 * 新手标回款状态更新
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateReFundRecordDrProductLoan")
	@ResponseBody
	public BaseResult updateReFundRecordDrProductLoan(Integer id,HttpServletRequest request ){
		BaseResult br = new BaseResult();
		try {
			drProductLoanRecodService.updateReFundRecordDrProductLoan(id);
			br.setSuccess(true);
			br.setMsg("操作成功");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("999");
		}
		return br;
	}
	/**
	 * 表单提交日期绑定
	 * 真对springMVC的表单日期提交时报错问题
	 * @param binder
	 */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    } 
    /**
	 * 新手标项目放款审批表
	 */
	@RequestMapping("/exprotProductLoanRecordAuditList")
	@ResponseBody
	public String exprotProductLoanRecordAuditList(HttpServletRequest request, HttpServletResponse response, DrproductLoanRecord drproductLoanRecord){
		BaseResult br = new BaseResult();
		try {
//			drproductLoanRecord.setSimpleName(URLDecoder.decode(drproductLoanRecord.getSimpleName(),"utf-8"));
//			drproductLoanRecord.setCompany(URLDecoder.decode(drproductLoanRecord.getCompany(),"utf-8"));
			List<DrproductLoanRecord> list = drProductLoanRecodService.getDrProductLoanRecordListByParam(drproductLoanRecord);
			if(null == list || list.isEmpty()){
				br.setSuccess(false);
				br.setMsg("No Data!");
			}else{	
				//读取模板
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
				String filename = "ProductLoanAudit-" + sdf1.format(new Date());
				File file =  new File(  request.getRealPath( "/" ) + filename +".xlsx");
				FileOutputStream os = new FileOutputStream(file);
				generateLoanRecordExcel(os,list);//创建excel工作簿
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
	 * 生成导出新手标放款审批表Excel
	 * @param osw
	 * @param list
	 */
	private void generateLoanRecordExcel(FileOutputStream osw, List<DrproductLoanRecord> list){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productLoanAuditTemplate.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);//in表示在内存中多少行，超过in这个参数值的显示到磁盘中，-->创建了一个excel文件
			XSSFSheet sheet = workeBook.getSheetAt(0);//创建一个工作
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row0 = sheet.getRow(0);//获取第一行
			row0.getCell(0).setCellValue(new XSSFRichTextString("币优铺金服新手标项目放款通知"));
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(9).setCellValue(new XSSFRichTextString(sdf.format(new Date())));
			XSSFRow row = null;
			XSSFRow row3 = sheet.getRow(3);
			int start = 3;
			for(int i = 0; i < list.size(); i ++){
				if(i == 0){
					//第四行，模板已经创建，不需要新增
					setRowValues(list.get(i), row3);
					if(list.size() > 1){
						sheet.shiftRows(4, sheet.getLastRowNum(), list.size() - 1, true, false);
					}
				}else{
					row = sheet.createRow(start + i);
					copyRow(row3, row);
					setRowValues(list.get(i), row);
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
	 * 新手标还款表行赋值
	 * @param drproductLoanRecord
	 * @param sheet
	 * @param index
	 * @throws ParseException
	 */
	private void setRowValues(DrproductLoanRecord drproductLoanRecord, XSSFRow row) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		getCell(row, 0).setCellValue(new XSSFRichTextString(drproductLoanRecord.getContractCode()));//合同编号
		getCell(row, 1).setCellValue(new XSSFRichTextString(drproductLoanRecord.getSimpleName()));//产品简称
		getCell(row, 2).setCellValue(new XSSFRichTextString(drproductLoanRecord.getLoanName()));//债权名称
		getCell(row, 3).setCellValue(new XSSFRichTextString(drproductLoanRecord.getCompany()));//收款单位全称
		getCell(row, 4).setCellValue(drproductLoanRecord.getAmount().doubleValue());//借款金额
		getCell(row, 5).setCellValue(drproductLoanRecord.getRate().doubleValue());//年化率
		getCell(row, 6).setCellValue(drproductLoanRecord.getDeadline().doubleValue());//期限
		/*BigDecimal interestTotal = drproductLoanRecord.getAmount().multiply(new BigDecimal(drproductLoanRecord.getDeadline()))
				.multiply(drproductLoanRecord.getRate()).divide(new BigDecimal(100)).divide(new BigDecimal(360), 2, BigDecimal.ROUND_FLOOR);*/
		getCell(row, 7).setCellValue(drproductLoanRecord.getInterest().doubleValue());//利息
		getCell(row, 8).setCellValue(drproductLoanRecord.getPrincipalInterest().doubleValue());//预计应收本息
		if(drproductLoanRecord.getFullDate()!=null){
			getCell(row, 9).setCellValue(sdf.parse(sdf.format(drproductLoanRecord.getFullDate())));//募集成功日期
		}
		if(drproductLoanRecord.getPrePayDate()!=null){
			getCell(row, 10).setCellValue(sdf.parse(sdf.format(drproductLoanRecord.getPrePayDate())));//实际付款日期
		}
		getCell(row, 11).setCellValue(drproductLoanRecord.getActAmount().doubleValue());//实际付款金额
		if(drproductLoanRecord.getShouldDate()!=null){
			getCell(row, 12).setCellValue(Utils.getDayNumOfAppointDate(drproductLoanRecord.getShouldDate(), 1));//预计还款日期（回款日期前一天）
		}
	}
	
	/**
	 * 新手标项目导出还款通知表
	 */
	@RequestMapping("/productPaymentRecordNoticeList")
	@ResponseBody
	public String productPaymentRecordNoticeList(HttpServletRequest request, HttpServletResponse response, DrproductLoanRecord drproductLoanRecord){
		BaseResult br = new BaseResult();
		try {
			//drproductLoanRecord.setSimpleName(URLDecoder.decode(drproductLoanRecord.getSimpleName(),"utf-8"));
			//drproductLoanRecord.setCompany(URLDecoder.decode(drproductLoanRecord.getCompany(),"utf-8"));
			List<DrproductLoanRecord> list = drProductLoanRecodService.getDrProductLoanRecordListByParam(drproductLoanRecord);
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
	 * @param list
	 */
	private void productPaymentNoticeExcel(FileOutputStream osw, List<DrproductLoanRecord> list){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productPaymentNotice.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);
			XSSFSheet sheet = workeBook.getSheetAt(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row0 = sheet.getRow(0);//获取第一行
			row0.getCell(0).setCellValue(new XSSFRichTextString("币优铺金服新手标项目还款通知"));
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(9).setCellValue(new XSSFRichTextString(sdf.format(new Date())));
			XSSFRow row = null;
			XSSFRow row3 = sheet.getRow(3);
			int start = 3;
			for(int i = 0; i < list.size(); i ++){
				if(i == 0){
					//第四行，模板已经创建，不需要新增
					productPaymentNoticeSetRowValues(list.get(i), row3);
					if(list.size() > 1){
						sheet.shiftRows(4, sheet.getLastRowNum(), list.size() - 1, true, false);
					}
				}else{
					row = sheet.createRow(start + i);
					copyRow(row3, row);
					productPaymentNoticeSetRowValues(list.get(i), row);
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
	 * 还款表行赋值
	 * @param drproductLoanRecord
	 * @param sheet
	 * @param index
	 * @throws ParseException
	 */
	private void productPaymentNoticeSetRowValues(DrproductLoanRecord drproductLoanRecord, XSSFRow row) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		getCell(row, 0).setCellValue(new XSSFRichTextString(drproductLoanRecord.getContractCode()));//合同编号
		getCell(row, 1).setCellValue(new XSSFRichTextString(drproductLoanRecord.getSimpleName()));//产品简称
		getCell(row, 2).setCellValue(new XSSFRichTextString(drproductLoanRecord.getLoanName()));//债权名称
		getCell(row, 3).setCellValue(new XSSFRichTextString(drproductLoanRecord.getCompany()));//收款单位全称
		getCell(row, 4).setCellValue(drproductLoanRecord.getAmount().doubleValue());//借款金额
		getCell(row, 5).setCellValue(drproductLoanRecord.getRate().doubleValue());//年化率
		getCell(row, 6).setCellValue(drproductLoanRecord.getDeadline().doubleValue());//期限
		/*BigDecimal interestTotal = drproductLoanRecord.getAmount().multiply(new BigDecimal(drproductLoanRecord.getDeadline()))
				.multiply(drproductLoanRecord.getRate()).divide(new BigDecimal(100)).divide(new BigDecimal(360), 2, BigDecimal.ROUND_FLOOR);*/
		getCell(row, 7).setCellValue(drproductLoanRecord.getInterest().doubleValue());//利息
		getCell(row, 8).setCellValue(drproductLoanRecord.getAmount().add(drproductLoanRecord.getInterest()).doubleValue());//预计应收本息
		if(drproductLoanRecord.getShouldDate()!=null){
			getCell(row, 9).setCellValue(sdf.parse(sdf.format(drproductLoanRecord.getShouldDate())));//还款到期日期
		}
		if(drproductLoanRecord.getShouldDate()!=null){
			getCell(row, 10).setCellValue(Utils.getDayNumOfAppointDate(drproductLoanRecord.getShouldDate(), 1));//预计还款日期（回款日期前一天）
		}
		
	}
	/**
	 * 新手标项目导出回款明细表
	 * @return 
	 */
	@RequestMapping("productReturnNoticeList")
	@ResponseBody
	public String productReturnNoticeList(HttpServletRequest request, HttpServletResponse response, DrproductLoanRecord drproductLoanRecord
	) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			/*drproductLoanRecord.setSimpleName(URLDecoder.decode(drproductLoanRecord.getSimpleName(),"utf-8"));
			drproductLoanRecord.setCompany(URLDecoder.decode(drproductLoanRecord.getCompany(),"utf-8"));*/
			List<DrproductLoanRecord> list = drProductLoanRecodService.getDrProductLoanRecordListByParam(drproductLoanRecord);
			if(null == list || list.isEmpty()){
				br.setSuccess(false);
				br.setMsg("No Data!");
			}else{
				//文件都是在system下
				String filePath = request.getSession().getServletContext().getRealPath("/");
				List<File> fileList = new ArrayList<File>();//用于存放文件
				//循环产品信息list
				for(int i=0;i<list.size();i++){
					map.put("id", list.get(i).getId());
					map.put("fullDate", Utils.format(list.get(i).getFullDate(), "yyyy-MM-dd"));
					List<Map<String,Object>> returnList = drProductInfoService.getReturnNoticeRecordList(map);
					if(returnList.isEmpty())continue;
					//读取模板
					String filename = "productReturnNotice-" + sdf.format(new Date()) + i;
					File file =  new File(filePath +"/"+ filename +".xlsx");
					FileOutputStream os = new FileOutputStream(file);
					
					productReturnNoticeExcel(os,list.get(i),returnList);//创建excel工作簿
					os.flush();
					os.close();
					fileList.add(file);
				}
				if(fileList.size() > 1){
					//压缩文件名
					String strZipName = sdf.format(new Date())+".zip";
					File outFile = new File(filePath + "/" + strZipName);
					//压缩文件输出流
					FileOutputStream outStream = new FileOutputStream(outFile);
					//压缩流
					ZipOutputStream out = new ZipOutputStream(outStream);
					//设置编码格式
					out.setEncoding("gbk");
					//压缩文件
					FileUtil.zipFile(fileList, out);
					out.close();
					outStream.close();
					//下载
					FileUtil.download(outFile,response);
					if(outFile.exists()){
						outFile.delete();
					}
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
				//删除文件
				for (File file1 : fileList) {
					if(file1.exists()){
						file1.delete();
					}
				}
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
	 * 生成回款明细表
	 * @param osw
	 * @param drproductLoanRecord
	 * @param returnList
	 */
	private void productReturnNoticeExcel(FileOutputStream osw,DrproductLoanRecord drproductLoanRecord,List<Map<String,Object>> returnList){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/productReturnNotice.xlsx");
		try {
			XSSFWorkbook workeBook = new XSSFWorkbook(in);
			XSSFSheet sheet = workeBook.getSheetAt(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			XSSFRow row0 = sheet.getRow(0);
			row0.getCell(1).setCellValue(new XSSFRichTextString(drproductLoanRecord.getSimpleName()));//项目名称
			row0.getCell(2).setCellValue(new XSSFRichTextString("产品编号："+drproductLoanRecord.getCompany()));//产品编号
			row0.getCell(4).setCellValue(new XSSFRichTextString("借款方："+drproductLoanRecord.getLoanName()));//借款方
			row0.getCell(6).setCellValue(new XSSFRichTextString("年华收益率："+drproductLoanRecord.getRate()+"%("+drproductLoanRecord.getDeadline()+"天)"));
			XSSFRow row1 = sheet.getRow(1);//获取第二行
			row1.getCell(5).setCellValue(sdf.parse(sdf.format(drproductLoanRecord.getShouldDate())));
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
    
}
