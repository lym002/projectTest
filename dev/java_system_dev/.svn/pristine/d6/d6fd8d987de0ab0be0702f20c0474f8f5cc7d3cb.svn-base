package com.jsjf.controller.task;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.model.product.DrProductInfoVo;
import com.jsjf.service.product.SynPayProductInfoService;
import com.reapal.utils.ReapalSubmit;

import net.sf.json.JSONObject;
@Component
public class SynRefundTask {
	@Autowired
	private SynPayProductInfoService productInfoService;
	private static Logger LOGGER = LoggerFactory.getLogger(SynRefundTask.class);
	
//	@Scheduled(cron="0 0/3 * * * ?")//每半小时执行一次定时任务
	public void synRefundTask(){
		BaseResult br = new BaseResult();
		//先去数据库中查询出同步未还款的产品
		List<DrProductInfoVo> drProductInfoList = productInfoService.getSynRefundProductInfo();
		if (!drProductInfoList.isEmpty()) {
			String url = ConfigUtil.getSynrefundurl();
			Map<String, Object> map = new TreeMap<String, Object>();
			for (DrProductInfoVo drProductInfo : drProductInfoList) {
				map.put("code", drProductInfo.getCode());
				map.put("status", drProductInfo.getStatus());
				map.put("json",JSONObject.fromObject(map).toString());
				try {
					String post = ReapalSubmit.bulidSubmitObj(map, url);
					boolean jsonPost = (boolean) JSON.parseObject(post).get("success");
					String errorCode = (String) JSON.parseObject(post).get("errorCode");
					if (jsonPost) {
						productInfoService.updateSynRefundStatus(drProductInfo.getId());
						System.out.println("同步成功");
					}else{
						LOGGER.error(errorCode,"系统错误");
						System.out.println("同步失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("9999");
					LOGGER.error("系统错误");
				}
			}
		}else{
			LOGGER.error("暂未获取到数据");
		}
	}
}
