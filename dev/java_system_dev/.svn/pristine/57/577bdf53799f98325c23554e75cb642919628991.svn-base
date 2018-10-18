package com.jsjf.service.activity.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsChannelCouponPutDAO;
import com.jsjf.model.activity.JsChannelCouponPut;
import com.jsjf.model.activity.JsChannelCouponPutDetail;
import com.jsjf.service.activity.JsChannelCouponPutService;
@Service
@Transactional
public class JsChannelCouponPutServiceImpl implements JsChannelCouponPutService {
	@Autowired
	private JsChannelCouponPutDAO jsChannelCouponPutDAO;

	@Override
	public JsChannelCouponPut selectObjectById(Integer id) {
		return jsChannelCouponPutDAO.selectObjectById(id);
	}

	@Override
	public void insert(JsChannelCouponPut jsChannelCouponPut) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(JsChannelCouponPut jsChannelCouponPut) {
			jsChannelCouponPutDAO.update(jsChannelCouponPut);
	}

	@Override
	public PageInfo selectObjectList(PageInfo info,
			JsChannelCouponPut jsChannelCouponPut) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		
		map.put("name", jsChannelCouponPut.getName());
		map.put("type", jsChannelCouponPut.getType());
		map.put("status", jsChannelCouponPut.getStatus());
		map.put("startDate", jsChannelCouponPut.getStartDate());
		map.put("endDate", jsChannelCouponPut.getEndDate());
		map.put("orders", "id desc");
		
		List<JsChannelCouponPut> rows = jsChannelCouponPutDAO.selectObjectList(map);
		int total = jsChannelCouponPutDAO.selectObjectListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
	}

	@Override
	public BaseResult addCouponPut(MultipartFile fileImport, JsChannelCouponPut jsChannelCouponPut)
			throws Exception {
		BaseResult result = new BaseResult();
		if(Utils.isObjectNotEmpty(fileImport)){
			String excleName = fileImport.getOriginalFilename().substring(0,
							fileImport.getOriginalFilename().lastIndexOf("."));
			
			jsChannelCouponPut.setName(excleName);			
			jsChannelCouponPutDAO.insert(jsChannelCouponPut);
			
			List<Map<String, Object>> list = execlParse(fileImport);
			
			if(list !=null && list.size()>0){
				Map<String,Object> para = new HashMap<String, Object>();
				para.put("list", list);
				para.put("putId", jsChannelCouponPut.getId());
				jsChannelCouponPutDAO.insertPutDetail(para);
				result.setMsg("上传成功");
				result.setSuccess(true);
			}else{
				result.setErrorMsg("失败:文件无有效数据,第一行标题,数据从第二行开始");
			}
		}else{
			result.setErrorMsg("没有文件");
		}
		return result;
	}
	
	@Override
	public BaseResult UpDateCouponPut(MultipartFile fileImport,
			JsChannelCouponPut put) throws Exception {
		BaseResult result = new BaseResult();
		JsChannelCouponPut jsChannelCouponPut = jsChannelCouponPutDAO.selectObjectById(put.getId());
		if(Utils.isObjectNotEmpty(fileImport)){
			String excleName = fileImport.getOriginalFilename().substring(0,
							fileImport.getOriginalFilename().lastIndexOf("."));
			put.setName(excleName);
			put.setStatus(0);
			put.setAddtime(new Date());
			jsChannelCouponPutDAO.update(put);
			
			jsChannelCouponPutDAO.deleteCouponByPutDetail(put.getId());
			
			List<Map<String, Object>> list = execlParse(fileImport);
			
			if(list !=null && list.size()>0){
				//过滤参数
				for(Map<String, Object> map:list){
					if(Utils.isObjectEmpty(map.get("mobile")) && Utils.isObjectEmpty(map.get("recommCodes"))){
						result.setErrorMsg("存在无手机号并且无推荐码列");
						return result;
					}
					if(!Utils.isObjectEmpty(map.get("mobile")) && map.get("mobile").toString().length()>11){
						result.setErrorMsg("存在手机号位数超长");
						return result;
					}
					if(!Utils.isObjectEmpty(map.get("recommCodes")) && map.get("recommCodes").toString().length()>6){
						result.setErrorMsg("存在推荐码位数超长");
						return result;
					}
					if(Utils.isObjectEmpty(map.get("amount")) || map.get("amount").toString().length()>10){
						result.setErrorMsg("存在金额位数超长或无金额的行");
						return result;
					}
					if(Utils.isObjectEmpty(map.get("code")) || map.get("code").toString().length() >100){
						result.setErrorMsg("存在编码位数超长或无编码的行");
						return result;
					}
				}
				
				Map<String,Object> para = new HashMap<String, Object>();
				para.put("list", list);
				para.put("putId", jsChannelCouponPut.getId());
				jsChannelCouponPutDAO.insertPutDetail(para);
				result.setMsg("修改成功");
				result.setSuccess(true);
			}else{
				result.setErrorMsg("失败");
			}
		}else{
			result.setErrorMsg("没有文件");
		}
		return result;
	}
	public List<Map<String, Object>> execlParse(MultipartFile fileImport) throws Exception{
		Workbook workbook = WorkbookFactory.create(fileImport.getInputStream());  
		Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet  
		Row row = sheet.getRow(0);//获得row
		int rowNum = sheet.getLastRowNum();//获得行数
		int colNum = row.getPhysicalNumberOfCells();//获得列数
		 
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowMap ;
		
		for(int i=1;i<=rowNum;i++){
			rowMap = new HashMap<String, Object>();
			row = sheet.getRow(i);
			if(Utils.isObjectEmpty(row) || (Utils.isObjectEmpty(row.getCell(0)) && Utils.isObjectEmpty(row.getCell(1))) || Utils.isObjectEmpty(row.getCell(2)) || Utils.isObjectEmpty(row.getCell(3)) ){
				return null;
			}
			if(!Utils.isObjectEmpty(row.getCell(0))){
				rowMap.put("mobile", getStringCellValue(row.getCell(0),null));
			}else{
				rowMap.put("mobile", null);
			}
			
			if(!Utils.isObjectEmpty(row.getCell(1))){
				rowMap.put("recommCodes", getStringCellValue(row.getCell(1),null));
			}else{
				rowMap.put("recommCodes", null);
			}
//			rowMap.put("channelType", getStringCellValue(row.getCell(1),null).toLowerCase().equals("cps")?1:0);
			rowMap.put("amount", getStringCellValue(row.getCell(2),"#0.00"));
			rowMap.put("code", getStringCellValue(row.getCell(3),null));
			list.add(rowMap);
		}
		return list;
	}
	/**
     * 获取单元格数据内容为字符串类型的数据
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell,String decimalFormat) {
    	
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
        	if(decimalFormat == null || decimalFormat ==""){
        		decimalFormat = "#";
        	}
            strCell = String.valueOf(new DecimalFormat(decimalFormat).format(cell.getNumericCellValue()));
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

	@Override
	public PageInfo selectCouponDetailList(PageInfo info, Integer putId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		map.put("putId", putId);
		
		map.put("orders", "a.status desc,a.id desc");
		
		List<JsChannelCouponPutDetail> rows = jsChannelCouponPutDAO.selectCouponDetailListList(map);
		int total = jsChannelCouponPutDAO.selectCouponDetailListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
	}

	@Override
	public void checkOutCouponPut(BaseResult result,JsChannelCouponPut put) throws Exception {
			jsChannelCouponPutDAO.checkOutCouponPutDetail(put.getId());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("putId", put.getId());
			map.put("statuses", new Integer[]{2,3,4,5});//校验2错误信息备注3编号不存在4数值错误5手机号码不存在
			int total = jsChannelCouponPutDAO.selectCouponDetailListCount(map);
			if(total==0){
				put.setStatus(2);//校验成功待审
				result.setMsg("校验成功");
			}else{
				put.setStatus(1);//校验失败
				result.setMsg("校验失败");
			}
			result.setSuccess(true);
			jsChannelCouponPutDAO.update(put);
	}

	@Override
	public void auditCouponPut(BaseResult result, JsChannelCouponPut put) throws Exception {
			int count = jsChannelCouponPutDAO.insertCouponByPutDetail(put.getId());
			if(count > 0){
				put.setStatus(3);//成功
				result.setMsg("审核成功");
			}else{
				put.setStatus(4);//失败
				result.setMsg("审核失败");
			}
			result.setSuccess(true);
			jsChannelCouponPutDAO.update(put);
	}
}
