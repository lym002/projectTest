package com.jsjf.service.creditorRight.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.Utils;
import com.jsjf.dao.creditorRight.DrCreditorRightMappingDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.model.creditorRight.DrCreditorRightMapping;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.service.creditorRight.DrCreditorRightMappingService;

@Service
public class DrCreditorRightMappingServiceImpl implements
		DrCreditorRightMappingService {
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;

	@Autowired
	private DrSubjectInfoDAO drSubjectInfoDAO;

	@Autowired
	private DrCreditorRightMappingDAO drCreditorRightMappingDAO;

	@Override
	public void autoCreditorRightMapping() {
		//获取到期匹配关系 将匹配关系结束   讲匹配金额加回未到期标的的未匹配金额
		List<DrCreditorRightMapping> mappingList = drCreditorRightMappingDAO.getCreditorRightMapping();
		if(!Utils.isEmptyList(mappingList)){
			for(DrCreditorRightMapping dcrm:mappingList){
				if(dcrm.getSubjectStatus()!=4){
					DrSubjectInfo drsubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(dcrm.getSid());
					DrSubjectInfo subjectInfo = new DrSubjectInfo();
					subjectInfo.setId(drsubjectInfo.getId());
					subjectInfo.setRemainsAmount(drsubjectInfo.getRemainsAmount().add(dcrm.getMappingAmount()));
					try {
						if(Utils.compare_date(Utils.format(drsubjectInfo.getEndDate(), "yyyy-MM-dd"), Utils.format(new Date(), "yyyy-MM-dd"))>0){
							subjectInfo.setMappingStatus(1);
						}
						drSubjectInfoDAO.updateDrSubjectInfo(subjectInfo);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取募集完成且未做债权匹配或部分债权匹配的产品
		map.clear();
		map.put("type", 2);
		map.put("status", new Integer[] {8});
		map.put("mappingStatuses", new Integer[] { 0, 1 });
		List<DrProductInfo> productList = drProductInfoDAO.getNeedMatchingProductList(map);

		if (productList != null && productList.size() > 0) {
			for (DrProductInfo product : productList) {
				boolean flag = false;
				Date mappingEndDate = product.getMappingEndDate();
				BigDecimal premainsAmount = product.getRemainsAmount();
				if (premainsAmount.compareTo(BigDecimal.ZERO) > 0) {
					map.clear();
					map.put("status", 3);
					map.put("mappingStatuses", new Integer[] { 0, 1 });
					map.put("remainsAmount", premainsAmount);
					//获取标的剩余未匹配金额大于产品未匹配金额的债券
					List<DrSubjectInfo> subjectList = drSubjectInfoDAO.getDrSubjectInfoList(map);
					if (subjectList != null && subjectList.size() > 0) {
						for(DrSubjectInfo si:subjectList){
							if(!Utils.isObjectEmpty(mappingEndDate) && Utils.areSameDay(mappingEndDate, product.getExpireDate())){
								break;
							}
							DrCreditorRightMapping mapping = new DrCreditorRightMapping();//债券匹配关系
							mapping.setPid(product.getId());
							mapping.setSid(si.getId());
							mapping.setMappingAmount(premainsAmount);
							if(product.getMappingEndDate()==null){
								mapping.setStartDate(product.getStartDate());
							}else{
								int d = Utils.compare_date(Utils.format(mappingEndDate, "yyyy-MM-dd"), Utils.format(si.getEndDate(), "yyyy-MM-dd"));//债券匹配表中的到期日期
								if(d==1){
									continue;
								}else{
									mapping.setStartDate(Utils.getDayNumOfAppointDate(mappingEndDate, -1));
								}
							}
							int d2 = Utils.compare_date(Utils.format(product.getExpireDate(), "yyyy-MM-dd"), Utils.format(si.getEndDate(), "yyyy-MM-dd"));
							if(d2==1){
								mapping.setEndDate(si.getEndDate());
							}else{
								mapping.setEndDate(product.getExpireDate());
								flag = true;
							}
							drCreditorRightMappingDAO.insertcreditorRightMapping(mapping);
							mappingEndDate = mapping.getEndDate();
							BigDecimal sremainsAmount = Utils.nwdBcsub(si.getRemainsAmount(), premainsAmount);
							si.setRemainsAmount(sremainsAmount);
							if(sremainsAmount.compareTo(BigDecimal.ZERO)==0){
								si.setMappingStatus(2);
							}else{
								si.setMappingStatus(1);
							}
							try {
								drSubjectInfoDAO.updateDrSubjectInfo(si);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					if(flag){
						product.setMappingStatus(2);
					}else{
						product.setMappingStatus(1);
					}
					try {
						drProductInfoDAO.updateDrProductInfo(product);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
