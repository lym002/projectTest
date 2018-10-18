package com.jsjf.service.activity.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrCouponsIssuedRulesDAO;
import com.jsjf.dao.activity.JsGratitudeBlessingDAO;
import com.jsjf.model.activity.JsGratitudeBlessing;
import com.jsjf.service.activity.JsGratitudeBlessingService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
public class JsGratitudeBlessingServiceImpl implements JsGratitudeBlessingService {
	
	@Autowired
	private JsGratitudeBlessingDAO jsGratitudeBlessingDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;

	@Override
	public List<JsGratitudeBlessing> selectJsGratitudeBlessingList(Map<String, Object> map) {
		return jsGratitudeBlessingDAO.selectJsGratitudeBlessingList(map);
	}

	@Override
	public BaseResult insert(Map<String, Object> map) {
		BaseResult br = new BaseResult();
		Integer uid = Integer.parseInt(map.get("uid").toString());
		try {
			JsGratitudeBlessing jsGratitudeBlessing = jsGratitudeBlessingDAO.selectJsGratitudeBlessingByUid(uid);
			br.setSuccess(true);
			if(Utils.isObjectEmpty(jsGratitudeBlessing)){
				JsGratitudeBlessing gratitudeBlessing = new JsGratitudeBlessing();
				gratitudeBlessing.setBlessing(map.get("blessing").toString().trim());
				gratitudeBlessing.setStatus(0);
				gratitudeBlessing.setAddtime(new Date());
				gratitudeBlessing.setUid(uid);
				gratitudeBlessing.setSplit(0);
				jsGratitudeBlessingDAO.insert(gratitudeBlessing);
			}else{
				if(Utils.isObjectEmpty(jsGratitudeBlessing.getStatus())){
					jsGratitudeBlessing.setBlessing(map.get("blessing").toString().trim());
					jsGratitudeBlessing.setStatus(0);
					jsGratitudeBlessing.setAddtime(new Date());
					jsGratitudeBlessing.setUpdatetime(new Date());
					jsGratitudeBlessingDAO.update(jsGratitudeBlessing);
				}else{
					br.setErrorCode("1002");
					br.setErrorMsg("已经提交过祝福了！");
					br.setSuccess(false);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return br;
	}

	@Override
	public BaseResult splitRed(Integer uid) {
		BaseResult br = new BaseResult();
		try {
			JsGratitudeBlessing jsGratitudeBlessing =jsGratitudeBlessingDAO.selectJsGratitudeBlessingByUid(uid);
			if(Utils.isObjectNotEmpty(jsGratitudeBlessing) && jsGratitudeBlessing.getSplit() == 1){
				br.setErrorCode("1001");
				br.setErrorMsg("已经拆过红包！");
				br.setSuccess(false);
				return br;
			}
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("type",4);
			map.put("uid", uid);
			redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),SerializeUtil.serialize(map));
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return br;
	}


}
