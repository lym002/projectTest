package com.jsjf.controller.partner.rrl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.SecurityUtils;
import com.jsjf.common.Utils;
import com.jsjf.service.product.DrProductInvestService;

@Component
public class RrlController {
	private static Logger log = Logger.getLogger(RrlController.class);
	@Autowired
	private DrProductInvestService drProductInvestService;
	
	/**
	 * 定时推送人人利用户投资数据
	 * @return
	 */
//	@Scheduled(cron="0 0 8,10,14,16,18,20,23 * * ?")
	public void pushRrlInvest(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		 try {
			String ip = InetAddress.getLocalHost().getHostAddress();
//			if("139.196.197.241".equals(ip)||"10.47.129.32".equals(ip)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("channel", "rrl");
				map.put("startDate", Utils.format(Utils.getDayNumOfAppointDate(new Date(), 1), "yyyy-MM-dd 23:00:00"));
				map.put("endDate", Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				List<Map<String, Object>> list = drProductInvestService.QueryChannelInvestList(map);
				if(list.size()>0){
					log.info("推送人人利数据："+JSON.toJSONString(list));
					RrlBase.getInstance().pushInvestData(list);
				}
//			}
		} catch (Exception e) {
			log.error("推送人人利数据失败",e);
		}
		
	}

}
