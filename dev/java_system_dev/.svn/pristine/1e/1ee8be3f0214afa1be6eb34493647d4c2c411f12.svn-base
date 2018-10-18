package com.jsjf.service.store.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.ReadExcel;
import com.jsjf.common.Utils;
import com.jsjf.dao.store.CommodityExchangeDao;
import com.jsjf.dao.store.CommodityRepertoryDao;
import com.jsjf.model.store.CommodityExchange;
import com.jsjf.model.store.CommodityRepertory;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.service.store.CommodityExchangeServer;

@Service
@Transactional
public class CommodityExchangeServerImpl implements CommodityExchangeServer {

	@Autowired
	private CommodityExchangeDao commodityExchangeDao;
	
	@Autowired
	private CommodityRepertoryDao commodityRepertoryDao;
	
	@Override
	public BaseResult queryCommodityExchangeList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<VipEquities> list = commodityExchangeDao.queryCommodityExchangeList(param);
		Integer total = commodityExchangeDao.queryCommodityExchangeListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addCommodityExchange(CommodityExchange bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setAddDate(new Date());
			if(Utils.isObjectEmpty(bean.getExpirationTime())){
				 Calendar c = Calendar.getInstance();
		         c.add(Calendar.YEAR, 3);
				 bean.setExpirationTime(c.getTime().toString());
			}
			commodityExchangeDao.addCommodityExchange(bean);
			 //添加成功加1库存量
        	CommodityRepertory repBean = new CommodityRepertory();
        	repBean.setUpdateTime(new Date());
        	repBean.setId(bean.getExchangeId());
        	repBean.setRepertoryInto(1);
        	commodityRepertoryDao.updateRepertoryInto(repBean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateCommodityExchange(CommodityExchange bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setUpdateTime(new Date());
			if(Utils.isObjectEmpty(bean.getExpirationTime())){
				 Calendar c = Calendar.getInstance();
		         c.add(Calendar.YEAR, 3);
				bean.setExpirationTime(c.getTime().toString());
			}
			commodityExchangeDao.updateCommodityExchange(bean);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("修改失败！");
		}
		return br;
	}

	@Override
	public BaseResult deleteCommodityExchange(int id) {
		BaseResult br = new BaseResult();
		try{
			commodityExchangeDao.deleteCommodityExchange(id);
			br.setSuccess(true);
			br.setMsg("删除成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("删除失败！");
		}
		return br;
	}

	@Override
	public BaseResult addImportCommodity(String name, MultipartFile bannerAddPicFile) throws Exception {
		BaseResult br = new BaseResult();
		//创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        CommodityExchange bean = new CommodityExchange();
        //解析excel
        List<CommodityExchange> beanList = readExcel.getCommodityExcelInfo(name ,bannerAddPicFile,bean);
        //不能添加库存为0
        for (int i = 0; i < beanList.size(); i++) {
        	if(Utils.isObjectNotEmpty(beanList.get(i).getExchangeId())){
	        	int repCount = commodityExchangeDao.queryCommodityInto(beanList.get(i).getExchangeId());
	        	if(Utils.isObjectEmpty(repCount) || repCount <= 0){
	        		br.setSuccess(false);
	        		br.setErrorCode("9999");
	        		br.setMsg("商品ID不存在！");
	        		return br;
	        	}
	        	 //添加成功加1库存量
	        	CommodityRepertory repBean = new CommodityRepertory();
	        	repBean.setUpdateTime(new Date());
	        	repBean.setId(beanList.get(i).getExchangeId());
	        	repBean.setRepertoryInto(1);
	        	commodityRepertoryDao.updateRepertoryInto(repBean);
        	}else{
        		br.setSuccess(false);
        		br.setErrorCode("9999");
        		br.setMsg("商品ID不能为空！");
        		return br;
        	}
		}
        //添加纪录
        commodityExchangeDao.addImportCommodity(beanList);
        br.setSuccess(true);
    	br.setMsg("上传成功");
    	return br;
	}

} 
