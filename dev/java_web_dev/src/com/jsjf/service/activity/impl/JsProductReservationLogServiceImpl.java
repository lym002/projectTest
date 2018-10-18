package com.jsjf.service.activity.impl;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.service.system.impl.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.JsProductReservationLogDAO;
import com.jsjf.model.activity.JsProductReservationLog;
import com.jsjf.service.activity.JsProductReservationLogService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class JsProductReservationLogServiceImpl implements
		JsProductReservationLogService {
	
	@Autowired
	JsProductReservationLogDAO jsProductReservationLogDAO;

	@Autowired
	private RedisClientTemplate redisClientTemplate;

	@Override
	public void insert(JsProductReservationLog jsProductReservationLog) throws Exception {		
		jsProductReservationLogDAO.insert(jsProductReservationLog);
	}

	@Override
	public BaseResult getTopTen() {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map=new HashMap<>();
            Map<String, Object> param=new HashMap<>();
            param.put("startTime","2018-3-5 00:00:00");
			List<JsProductPrizeLog> list= jsProductReservationLogDAO.selectTopTen(param);
			if (Utils.isObjectNotEmpty(list)){
				map.put("list",list);
			}else {
				map.put("list","");
			}
			br.setSuccess(true);
			br.setMap(map);
		}catch (Exception e){
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}


	@Override
	public BaseResult getMyZeroBuy(Integer uid) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map=new HashMap<>();
			if (Utils.isObjectEmpty(uid)){
				br.setErrorCode("9998");
				br.setMsg("用户没有登录");
				br.setSuccess(false);
				return br;
			}
			String start = redisClientTemplate.getProperties("zeroBuyDateStart");
			String end = redisClientTemplate.getProperties("zeroBuyDateEnd");
			Date nowDate = new Date();
			if (nowDate.after(Utils.parseDate(start,
					"yyyy-MM-dd HH:mm:ss"))
					&& nowDate.before(Utils.parseDate(end,
					"yyyy-MM-dd HH:mm:ss"))) {
				map.put("uid",uid);
                map.put("startTime","2018-3-5 00:00:00");
				List<JsProductPrizeLog> list =  jsProductReservationLogDAO.selectMyZeroBuy(map);
				map.put("myZeroBuy",list);
				br.setMap(map);
				br.setSuccess(true);
			}else {
				br.setSuccess(false);
				br.setErrorCode("9997");
				br.setMsg("产品过期");
			}
		}catch (Exception e){
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}



}
