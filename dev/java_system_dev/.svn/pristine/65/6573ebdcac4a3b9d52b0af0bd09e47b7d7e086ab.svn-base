package com.jsjf.controller.partner.yrt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.Utils;
import com.jsjf.service.product.DrProductInvestService;

@Component
@RequestMapping("yrt")
public class YrtController {
	private static Logger log = Logger.getLogger(YrtController.class);
	@Autowired
	private DrProductInvestService drProductInvestService;
	
	@RequestMapping("yrtMemberInfoQuery")
	@ResponseBody
	public String yrtMemberInfoQuery(@RequestParam(value = "startday")String startday, 
			@RequestParam(value = "endday")String endday,
			@RequestParam(value = "sign")String sign){
		boolean signResult = YrtBase.getInstance().validateSign(startday, endday, sign);
		Map<String,Object> param = new HashMap<String, Object>();
		try {
			if(signResult){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("toFrom", YrtBase.TOFROM);
				map.put("startday", startday);
				map.put("endday", endday);
				List<Map<String,Object>> list = drProductInvestService.QueryChannelYRT_InvestList(map);
				param.put("info", list);
				param.put("errorcode", 1);
				param.put("errormsg", "");
			}else{
				param.put("errorcode", -1);
				param.put("errormsg", "验证失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			param.put("errorcode", -1);
			param.put("errormsg", "系统错误");
		}
		return JSONObject.fromObject(param).toString();
	}
}
