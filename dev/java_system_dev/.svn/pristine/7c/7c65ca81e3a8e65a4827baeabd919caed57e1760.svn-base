package com.jsjf.service.product.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.JsNoviceContinueRecordDAO;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.JsNoviceContinueRecord;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.product.JsNoviceContinueRecordService;
@Service
//@Transactional
public class JsNoviceContinueRecordServiceImpl implements
		JsNoviceContinueRecordService {
	private Logger log = Logger.getLogger(this.getClass().getName());
	@Autowired
	private JsNoviceContinueRecordDAO jsNoviceContinueRecordDAO;
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;
	@Autowired
	private DrProductInvestService drProductInvestService;
	

	@Override
	public BaseResult getJsNoviceContinueRecord(
			JsNoviceContinueRecord jsNoviceContinueRecord, PageInfo pi){
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("realName", jsNoviceContinueRecord.getRealName()!=null?jsNoviceContinueRecord.getRealName().trim():null);
		map.put("mobilePhone", jsNoviceContinueRecord.getMobilePhone()!=null?jsNoviceContinueRecord.getMobilePhone().trim():null);
		map.put("startAddTime", jsNoviceContinueRecord.getStartAddTime());
		map.put("endAddTime", jsNoviceContinueRecord.getEndAddTime());
		map.put("startShouldTime", jsNoviceContinueRecord.getStartShouldTime());
		map.put("endShouldTime", jsNoviceContinueRecord.getEndShouldTime());
		map.put("status", jsNoviceContinueRecord.getStatus());
		map.put("period",jsNoviceContinueRecord.getPeriod());
		map.put("recommCodes",jsNoviceContinueRecord.getRecommCodes());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		BaseResult br = new BaseResult();
		try {
			List<JsNoviceContinueRecord> list = jsNoviceContinueRecordDAO.getJsNoviceContinueRecordList(map);
			Integer total = jsNoviceContinueRecordDAO.getJsNoviceContinueRecordCount(map);
			pi.setTotal(total);
			pi.setRows(list);
			resultMap.put("page", pi);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		br.setMap(resultMap); 
		return br;
	}

	@Override
	public void insertInvestNewHandContinue() {
		//获取续投标期
		List<Integer> periodList = jsNoviceContinueRecordDAO.getContinuePeriodList();
		if(periodList.size()>0){
			for(int i=0;i<periodList.size();i++){
				Map<String, Object> map = new HashMap<>();
				try {
					//获取待续投记录
					map.put("shouldTime", Utils.parse(new Date(), "yyyy-MM-dd"));//新手标回款日期为当日
					map.put("status", 0);//待续投
					map.put("period", periodList.get(i));
					List<JsNoviceContinueRecord> jncrList = jsNoviceContinueRecordDAO.getNewHandContinueList(map);
					if(jncrList.size()>0){
						for(int j=0;j<jncrList.size();j++){
							JsNoviceContinueRecord jncr = jncrList.get(j);
							Map<String,Object> param = new HashMap<>();
							param.put("period", periodList.get(i));
							param.put("amount",jncr.getAmount());//后台没有标识控制30天的自动发标
//							param.put("amount", periodList.get(i)==30?jncr.getAmount().add(new BigDecimal("5000")):jncr.getAmount());//后台没有标识控制30天的自动发标
							//获取未满标标的
							List<DrProductInfo> piList = drProductInfoDAO.getDrProductInfoByPeriodList(param);
							if(piList.size()>0){
								try {
									drProductInvestService.investContinueInvest(piList.get(0), jncr);
								} catch (Exception e) {
									e.printStackTrace();
									log.error("用户【"+jncr.getUid()+"】自动续投失败"+e.getMessage());
								};
								
							}else {
								log.info("用户【"+jncr.getUid()+"】自动续投未找到对应标的");
								continue;
							}
							
						}
					}else {
						continue;
					}
					
				} catch (ParseException e) {
					log.error(periodList.get(i)+"天标期数据续投失败===="+e.getMessage());
				}
			}
		}else{
			log.info("本次无续投："+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
	}

}
