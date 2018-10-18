package com.jsjf.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberCrushService;

@Component
public class PayTask {

	@Autowired
	public DrMemberCarryService drMemberCarryService;
	@Autowired
	public DrMemberCrushService drMemberCrushService;
	
	/**
	 * 定义每天9点20触发一次
	 */
	@Scheduled(cron="0 20 9 * * ?") 
   	public void queryPayResult() {
    	try {
    		drMemberCrushService.updatePayResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
   	
	/**
	 * 每天的21点执行
	 */
	@Scheduled(cron="0 05 21 * * ?") 
   	public void queryPaymentResult() {
    	try {
    		drMemberCarryService.updatePaymentResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
}