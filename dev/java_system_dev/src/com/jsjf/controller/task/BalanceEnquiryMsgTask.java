package com.jsjf.controller.task;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.PropertyUtil;
import com.jsjf.service.product.DrProductInfoService;

@Component
public class BalanceEnquiryMsgTask {
	@Autowired
	private DrProductInfoService drProductInfoService;
	
	/**
	 * 每天的十点钟将未来7,3,1天的产品发给相关人员
	 */
	@Scheduled(cron="0 0 10 * * ?")
	public void sendBalanceDeficiencyMsg(){
		try {
			String[] dayNumS = PropertyUtil.getProperties("dayNum").split(",");
			for(String d : dayNumS){
				int dayNum = Integer.parseInt(d);
				drProductInfoService.sendBalanceDeficiencyMsg(dayNum);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 0 10 * * ?")
	public void sendCompanyRepaymentMsg(){
		try {
			String  flag = PropertyUtil.getProperties("sendMsgFlag");//flag为1的时候发送短信
			if(flag == "1"){
				String[] dayNumS = PropertyUtil.getProperties("dayNum").split(",");
				for(String d : dayNumS){
					int dayNum = Integer.parseInt(d);
					if(dayNum != 7){
						drProductInfoService.sendMsgForRepayment(dayNum);
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
