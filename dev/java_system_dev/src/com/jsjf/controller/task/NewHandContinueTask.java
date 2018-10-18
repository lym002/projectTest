package com.jsjf.controller.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.Utils;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.product.JsNoviceContinueRecordService;

@Component
public class NewHandContinueTask {
	private static Logger log = Logger.getLogger(NewHandContinueTask.class);

	@Autowired
	public JsNoviceContinueRecordService jsNoviceContinueRecordService;
	
	/**
	 * 定义6点到23点 每半小时执行一次新手续投任务
	 */
//	@Scheduled(cron="0 0/10 6-23 * * ?") 
	public void newHandContinueInvest(){
		log.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"开始续投任务");
		jsNoviceContinueRecordService.insertInvestNewHandContinue();
		log.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"结束续投任务");
	}

//	@Scheduled(cron="0 50 23 * * ?") 
//	public void newHandContinueRefund(){
//		
//	}
	
}