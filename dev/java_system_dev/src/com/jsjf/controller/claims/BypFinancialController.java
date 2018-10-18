package com.jsjf.controller.claims;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.service.claims.BypFinancialService;
import com.jsjf.service.claims.DrClaimsInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/financial")
public class BypFinancialController {
    protected static final Logger log = Logger.getLogger(BypFinancialController.class);
    @Autowired
    private BypFinancialService bypFinancialService;
    /**
     * 资产对接接口_接收数据接口
     *
     * @return
     */
    @RequestMapping(value = "/listPushedCr")
    @ResponseBody
    public String addClaimsInfo(HttpServletRequest request, @RequestParam String data) {
        BaseResult br = new BaseResult();
        String  jsonString = "";
        try {
            jsonString =  bypFinancialService.addClaimsInfo(data);
        } catch (Exception e) {
            log.error("资产对接接口异常" + e.getMessage());
            br.setSuccess(false);
            br.setErrorCode("9999");
            br.setSuccess(false);
            e.getStackTrace();
            return JSONObject.toJSONString(br);
        }
        return jsonString;
    }
}
