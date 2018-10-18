package com.jsjf.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.service.product.DrProductInfoService;

@Component
public class NewFinancingEndMsgTask {
	@Autowired
	private DrProductInfoService drProductInfoService;
	
	@Scheduled(cron="0 0 9-12 * * ?")
	public void getNewFinancingEnd(){
		int count = drProductInfoService.getNewFinancingEnd();//查询新手标融资资金为0的总数
		
		if(count <= 0){
			try {
				SmsSendUtil.sendMsg(ConfigUtil.yyPhone, "币优铺官网展示的新手标已被抢购完毕，请您及时添加新标的，辛苦了哦！");
				SmsSendUtil.sendMsg("13817960758", "币优铺官网展示的新手标已被抢购完毕，请您及时添加新标的，辛苦了哦！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}