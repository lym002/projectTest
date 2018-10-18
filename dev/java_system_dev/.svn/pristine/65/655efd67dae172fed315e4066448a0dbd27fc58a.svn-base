package com.jsjf.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.service.subject.DrSubjectInfoService;

@Component
public class SubjectTask {
	@Autowired
	public DrSubjectInfoService drSubjectInfoService;
	
	/**
	 * 每天0点把到期的标的状态修改为已到期
	 */
	@Scheduled(cron="0 0 0 * * ?") //定义每天0点触发一次
   	public void queryPayResult() {
    	try {
    		drSubjectInfoService.updateDrSubjectInfoByExpire();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
}