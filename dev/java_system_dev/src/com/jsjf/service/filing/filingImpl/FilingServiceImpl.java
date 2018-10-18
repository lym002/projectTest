package com.jsjf.service.filing.filingImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.filing.FilingDao;
import com.jsjf.model.filing.DrFiling;
import com.jsjf.service.filing.FilingService;
import com.jsjf.service.jzh.JzhMessageService;
import com.jsjf.service.jzh.SysFuiouMessageService;
import com.jsjf.service.seq.SeqService;
import com.jzh.util.ConfigReader;

import net.sf.json.JSONArray;

@Service
@Transactional
public class FilingServiceImpl implements FilingService {
	private static final Logger LOGGER = Logger.getLogger(FilingService.class);
	@Autowired
	private FilingDao filingDao;
	@Autowired
	private SeqService seqService;
	@Autowired
	private SysFuiouMessageService sysFuiouMessageService;
	@Autowired
	private JzhMessageService jzhMessageService;

	private String checkpath = ConfigReader.getConfig("check");// 上传报文路径

	@Override
	public BaseResult getInvestFilingList(DrFiling drFiling, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", pi.getPageInfo().getLimit());
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("businessType", drFiling.getBusinessType());
		map.put("failureCauseType",drFiling.getFailureCauseType());
		List<DrFiling> list = filingDao.getInvestFilingList(map);
		Integer total = filingDao.getInvestFilingCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	/**
	 * 将任意对象转换成map
	 * 
	 * @param obj
	 * @return
	 */
	private <Object> Map<String, Object> convert2Map(DrFiling obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = obj.getClass().getMethods();
		try {
			for (Method method : methods) {
				Class<Object>[] paramClass = (Class<Object>[]) method.getParameterTypes();
				if (paramClass.length > 0) {// 表示这个方法是有参数的，那么就继续不做处理
					continue;
				}
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					Object value = (Object) method.invoke(obj);
					map.put(methodName, value);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return (Map<String, Object>) map;
	}

	@Override
	public void getInvestFiling(JSONArray jsonArray, String businessType) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			map = (Map<String,Object>) jsonArray.get(i);
			list.add(map);
		}
		jzhMessageService.getInvestFiling(list, businessType);
	}

	@Override
	public void getFullCreditFiling(JSONArray filingListData, String businessType) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < filingListData.size(); i++) {
			map = (Map<String,Object>) filingListData.get(i);
			list.add(map);
		}
		jzhMessageService.getFullCreditFiling(list, businessType);
	}

	@Override
	public void getInvestReturnedMoneyFiling(JSONArray filingListData, String businessType) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < filingListData.size(); i++) {
			map = (Map<String,Object>) filingListData.get(i);
			list.add(map);
		}
		jzhMessageService.getInvestReturnedMoneyFiling(list, businessType);
	}
}
