package com.jsjf.service.product.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductLoanRecordDAO;
import com.jsjf.model.product.DrproductLoanRecord;
import com.jsjf.service.product.DrProductLoanRecodService;

@Service
@Transactional
public class DrProductLoanRecordServiceImpl implements DrProductLoanRecodService {

	@Autowired
	private DrProductLoanRecordDAO drProductLoanRecordDAO;
	@Override
	public void insert() {
		drProductLoanRecordDAO.insert();
	}
	
	@Override
	public BaseResult drProductLoanRecordList(DrproductLoanRecord drproductLoanRecord, PageInfo pi) {
		// TODO Auto-generated method stub
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=getRecordExtractMap(drproductLoanRecord);;
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrproductLoanRecord> list = drProductLoanRecordDAO.drProductLoanRecordList(map);
		Integer total = drProductLoanRecordDAO.drProductLoanRecordListCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap); 
		return br;
	}
	
	/**
	 * 抽取放回款款列表功能的较通用部分
	 * @param drproductLoanRecord
	 */
	public  Map<String,Object> getRecordExtractMap(DrproductLoanRecord drproductLoanRecord){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("simpleName", drproductLoanRecord.getSimpleName());
		map.put("loanStatus", drproductLoanRecord.getLoanStatus());
		map.put("company", drproductLoanRecord.getCompany());
		map.put("refundStartDate",Utils.format(drproductLoanRecord.getRefundStartDate(),"yyyy-MM-dd"));//回款时间
		map.put("refundEndDate",Utils.format(drproductLoanRecord.getRefundEndDate(),"yyyy-MM-dd"));
		map.put("fullStartDate",Utils.format(drproductLoanRecord.getFullStartDate(),"yyyy-MM-dd"));//募集成功时间
		map.put("fullEndDate", Utils.format(drproductLoanRecord.getFullEndDate(),"yyyy-MM-dd"));
		map.put("startactLoanTime",Utils.format(drproductLoanRecord.getStartactLoanTime(),"yyyy-MM-dd"));//实际放款时间
		map.put("endactLoanTime", Utils.format(drproductLoanRecord.getEndactLoanTime(),"yyyy-MM-dd"));
		return map;
	}

	@Override
	public List<DrproductLoanRecord> getDrProductLoanRecordListByParam(DrproductLoanRecord drproductLoanRecord) {
		Map<String, Object> map = getRecordExtractMap(drproductLoanRecord);
		return drProductLoanRecordDAO.drProductLoanRecordList(map);
	}

	@Override
	public void updateDrProductLoanStatus(Map<String, Object> map) {
		drProductLoanRecordDAO.updateDrProductLoanStatus(map);
	}

	@Override
	public void updateReFundRecordDrProductLoan(Integer id) {
		drProductLoanRecordDAO.updateReFundRecordDrProductLoan(id);
	}
}