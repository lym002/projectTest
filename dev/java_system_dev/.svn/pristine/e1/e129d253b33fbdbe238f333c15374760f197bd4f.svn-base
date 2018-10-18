package com.jsjf.service.vip.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.vip.VipInfoDao;
import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.vip.VipInfoService;

@Service
@Transactional
public class VipInfoServiceImpl implements VipInfoService{
	
	@Autowired
	private VipInfoDao vipInfoDao;

	@Override
	public BaseResult queryVipInfoList(Map<String, Object> param, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<BypCommodityDetailBean> list = vipInfoDao.queryVipInfoList(param);
		Integer total = vipInfoDao.queryVipInfoListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addVipinfo(VipInfo vipInfoBean) {
		BaseResult br = new BaseResult();
		try{
			String rightsAndInterestsId = vipInfoBean.getRightsAndInterestsId();
			if(Utils.isObjectNotEmpty(rightsAndInterestsId) && ",".equals(rightsAndInterestsId.substring(0,1))){
				vipInfoBean.setRightsAndInterestsId(
						vipInfoBean.getRightsAndInterestsId().
						substring(1,vipInfoBean.getRightsAndInterestsId().length())+",");
			}else{
				vipInfoBean.setRightsAndInterestsId(rightsAndInterestsId+",");
			}
			vipInfoBean.setCreatedTime(new Date());
			vipInfoDao.addVipinfo(vipInfoBean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateVipInfo(VipInfo vipInfoBean) {
		BaseResult br = new BaseResult();
		try{
			String rightsAndInterestsId = vipInfoBean.getRightsAndInterestsId();
			if(Utils.isObjectNotEmpty(rightsAndInterestsId) && ",".equals(rightsAndInterestsId.substring(0,1))){
				vipInfoBean.setRightsAndInterestsId(
						vipInfoBean.getRightsAndInterestsId().
						substring(1,vipInfoBean.getRightsAndInterestsId().length())+",");
			}else{
				vipInfoBean.setRightsAndInterestsId(rightsAndInterestsId+",");
			}
			vipInfoBean.setUpdateTime(new Date());
			vipInfoDao.updateVipInfo(vipInfoBean);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("修改失败！");
		}
		return br;
	}

}
