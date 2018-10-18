package com.jsjf.controller.toutiao;

import com.alibaba.druid.util.Utils;
import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.model.toutiao.TTDeviceInfo;
import com.jsjf.service.toutiao.TouTiaoService;
import com.jytpay.utils.StringUtil;
import com.sftpay.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/toutiao")
public class TouTiaoController {

    private static transient Logger log = Logger.getLogger(TouTiaoController.class);

    @Autowired
    private TouTiaoService touTiaoService;

    @RequestMapping(value = "/ttpushAndroid", method = RequestMethod.GET)
    @ResponseBody
    public String ttpushAndroid(HttpServletRequest req, String aid, String cid, String imei, String mac, String androidid, String ip,
                                String os, String timestamp, String convertId, String callback) {
        //url : http://116.231.28.54:48070/toutiao/ttpushAndroid?adid={AID}&cid={CID}&imei={IMEI}&mac={MAC}&androidid={ANDROIDID1}&os={OS}&timestamp={TS}&callback_url={CALLBACK_URL}&ip={IP}
        if (StringUtils.isEmpty(aid)&&StringUtils.isEmpty(cid)&&
                StringUtils.isEmpty(imei)&&StringUtils.isEmpty(mac)&&
                StringUtils.isEmpty(androidid)&&StringUtils.isEmpty(ip)
                &&StringUtils.isEmpty(os)&&StringUtils.isEmpty(timestamp)
                &&StringUtils.isEmpty(convertId)&&StringUtils.isEmpty(callback)){
            return "false";
        }
        try {
        TTDeviceInfo touTiao = new TTDeviceInfo(null,aid, cid, os, imei, androidid, mac, ip, callback,
                timestamp, convertId);
        log.info("今日头条回馈安卓数据" + touTiao.toString());
            touTiao.setCreatedTime(new Date());
            touTiaoService.insertSelective(touTiao);
        }catch (Exception e){
            log.error("头条回馈设备数据Error！"+ e.getMessage());
        }
        return "success";
    }
    @RequestMapping(value = "/ttpushIOS", method = RequestMethod.GET)
    @ResponseBody
    public String ttpushIOS(HttpServletRequest req, String aid, String cid, String idfa, String mac, String ip,
                                String os, String timestamp, String convertId, String callback) {
        //url : https://www.bupul.com/toutiao/ttpushAndroid?aid={AID}&cid={CID}&imei={IMEI}&mac={MAC}&androidid={ANDROIDID1}&os={OS}&timestamp={TS}&callback_url={CALLBACK_URL}&ip={IP}
        if (StringUtils.isEmpty(aid)&&StringUtils.isEmpty(cid)&&
                StringUtils.isEmpty(idfa)&&StringUtils.isEmpty(mac)&&StringUtils.isEmpty(ip)
                &&StringUtils.isEmpty(os)&&StringUtils.isEmpty(timestamp)
                &&StringUtils.isEmpty(convertId)&&StringUtils.isEmpty(callback)){
            return "false";
        }
        try {
        TTDeviceInfo touTiao = new TTDeviceInfo(null,aid, cid, os, idfa, mac, ip, callback,
                timestamp, convertId);
        log.info("今日头条回馈IOS数据" + touTiao.toString());
            touTiao.setCreatedTime(new Date());
            touTiaoService.insertSelective(touTiao);
        }catch (Exception e){
            log.error("头条回馈设备数据Error！"+ e.getMessage());
        }
        return "success";
    }

    @RequestMapping("/deviceInfo")
    @ResponseBody
    public String deviceInfo(HttpServletRequest req,Map<String, Object> param, String idfa, String imei){
        param.put("idfa", idfa);
        param.put("imei", imei == null ? null:Utils.md5(imei));
        BaseResult br = new BaseResult();
        try {
            touTiaoService.callback(param);
            br.setSuccess(true);
        }catch (Exception e){
            br.setSuccess(false);
            br.setErrorCode("9999");
            log.error("回调失败！" + e.getMessage());
        }
        return JSON.toJSONString(br);
    }


}
