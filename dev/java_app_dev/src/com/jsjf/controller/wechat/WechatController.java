package com.jsjf.controller.wechat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/weChat")
@Controller
public class WechatController {
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value="/getToken", method = RequestMethod.GET)
    @ResponseBody
    public String getToken(HttpServletRequest req){
        String echostr = req.getParameter("echostr");
        return echostr;
    }
    
    /**
     * 被动消息回复
     * @param req
     * @return
     */
  /*  @RequestMapping(value="/sendPassiveMessage", method = RequestMethod.GET)
    @ResponseBody
    public String sendPassiveMessage(HttpServletRequest req){
        String echostr = req.getParameter("echostr");
        return echostr;
    }*/
}
