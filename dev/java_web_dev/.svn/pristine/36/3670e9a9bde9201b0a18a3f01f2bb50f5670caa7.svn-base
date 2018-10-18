package com.jsjf.service.activity.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsProductPrizeWishDAO;
import com.jsjf.model.activity.JsProductPrizeWish;
import com.jsjf.service.activity.JsProductPrizeWishService;

@Service
@Transactional
public class JsProductPrizeWishServiceImpl implements JsProductPrizeWishService {
	
	@Autowired
	private JsProductPrizeWishDAO JsProductPrizeWishDAO;

	@Override
	public BaseResult insertPrizeWish(JsProductPrizeWish jsProductPrizeWish) {
		BaseResult br = new BaseResult();
		try {
			if (Utils.isObjectEmpty(jsProductPrizeWish.getUrl())) {
				br.setErrorCode("1005");
				br.setSuccess(false);
				br.setErrorMsg("url不能为空");
				return br;
			}
			// 判断url是否为天猫、淘宝
			URL url;
			try {
				String jd = "jd";
				String tmall = "tmall";
				String wishUrl = jsProductPrizeWish.getUrl().trim();
				if (wishUrl.indexOf(jd) == -1 && wishUrl.indexOf(tmall) == -1) {
					br.setErrorCode("1004");
					br.setSuccess(false);
					br.setErrorMsg("不属于天猫或京东url");
					return br;
				}
				url = new URL(jsProductPrizeWish.getUrl().trim());
				InputStream in = url.openStream();
			} catch (Exception e1) {
				br.setErrorCode("1003");
				br.setSuccess(false);
				br.setErrorMsg("url不能正常访问");
				url = null;
				return br;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			// 判断是否提交重复url
			map.put("uid", jsProductPrizeWish.getUid());
			map.put("url", jsProductPrizeWish.getUrl().trim());
			List<JsProductPrizeWish> isDoubleList = JsProductPrizeWishDAO.selectByMap(map);
			if (isDoubleList.size() > 0) {
				br.setErrorCode("1002");
				br.setSuccess(false);
				br.setErrorMsg("用户已提交过该心愿");
				return br;
			}
			// 判断当天是否已提交5条
			map.clear();
			map.put("uid", jsProductPrizeWish.getUid());
			map.put("addtime", new Date());
			List<JsProductPrizeWish> wishList = JsProductPrizeWishDAO.selectByMap(map);
			if (wishList.size() > 4) {
				br.setErrorCode("1001");
				br.setSuccess(false);
				br.setErrorMsg("一个用户每天只能提交5个心愿");
				return br;
			}
			JsProductPrizeWishDAO.insert(jsProductPrizeWish);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult newInsertPrizeWish(JsProductPrizeWish jsProductPrizeWish) {
		BaseResult br = new BaseResult();
		try {
			if (Utils.isObjectEmpty(jsProductPrizeWish.getUid())) {
				br.setErrorCode("9998");
				br.setSuccess(false);
				br.setErrorMsg("uid不能为空");
				return br;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", jsProductPrizeWish.getUid());
			map.put("addtime", new Date());
			map.put("remark",jsProductPrizeWish.getRemark());
			JsProductPrizeWishDAO.insert(jsProductPrizeWish);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult selectWish(Integer uid) {
		BaseResult br = new BaseResult();
		try {
			if (Utils.isObjectEmpty(uid)) {
				br.setErrorCode("9998");
				br.setSuccess(false);
				br.setErrorMsg("uid不能为空");
				return br;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			List<JsProductPrizeWish> isDoubleList= JsProductPrizeWishDAO.selectByMap(map);
			map.clear();
			if(0!=isDoubleList.size()){
				map.put("remark",isDoubleList.get(0).getRemark());
				map.put("size",isDoubleList.size());
				br.setMap(map);
				br.setSuccess(true);
			}else {
				map.put("remark","");
				map.put("size",0);
				br.setMap(map);
				br.setSuccess(true);
			}
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}



}
