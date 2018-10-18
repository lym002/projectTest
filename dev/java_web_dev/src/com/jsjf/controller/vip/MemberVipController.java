package com.jsjf.controller.vip;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.SystemConstant;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.vip.MemberVipInfo;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.service.vip.MemberVipInfoService;
import com.jsjf.service.vip.VipActivityParameterService;
import com.jsjf.service.vip.VipEquitiesMemberService;
import com.jsjf.service.vip.VipEquitiesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/memberVip")
public class MemberVipController {

    @Autowired
    private VipActivityParameterService vipActivityParameterService;
    @Autowired
    private MemberVipInfoMapper memberVipInfoMapper;
    @Autowired
    private MemberVipInfoService memberVipInfoService;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private DrActivityParameterDAO drActivityParameterDAO;
    @Autowired
    private VipEquitiesService vipEquitiesService;
    @Autowired
    private VipEquitiesMemberService vipEquitiesMemberService;

    private static transient Logger logger = Logger.getLogger(MemberVipController.class);

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest req){
        //会员中心页面基础数据
        BaseResult br = new BaseResult();
        DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        br = memberVipInfoService.memberVipInfo(m.getUid());
        return JSON.toJSONString(br);

    }

    /**
     * 查询用户月度礼包
     * @param req
     * @return
     */
    @RequestMapping("/monthPacket")
    @ResponseBody
    public String monthPacket(HttpServletRequest req){
        //会员中心查询用户月度礼包
        BaseResult br = new BaseResult();
        DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        try {
            //获取用户的vip等级
            MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(m.getUid());
            if (memberVipInfo == null) {
                memberVipInfo = new MemberVipInfo(null, m.getUid(), new BigDecimal(0), 1,Utils.getExpirationTime());
                memberVipInfoMapper.insertSelective(memberVipInfo);
            }
            br = vipActivityParameterService.queryMonthPacket(memberVipInfo.getVipLevel(), m.getUid());
        }catch (Exception e){
            logger.error("月度礼包查询错误！" + e );
        }
        return JSON.toJSONString(br);
    }
    /**
     * 查询用户月度礼包
     * @param req
     * @return
     */
    @RequestMapping("/getMonthPacket")
    @ResponseBody
    public String getMonthPacket(HttpServletRequest req, @RequestParam String fid){
        //会员中心查询用户月度礼包
        BaseResult br = new BaseResult();
        DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        if (Utils.isObjectEmpty(m)) {
            br.setErrorMsg("未登录");
            br.setSuccess(false);
            br.setErrorCode("9998");
            return JSON.toJSONString(br);
        }
        if (Utils.isObjectEmpty(fid)){
            br.setErrorMsg("红包错误");
            br.setSuccess(false);
            br.setErrorCode("9999");
            return JSON.toJSONString(br);
        }
        boolean flag = vipActivityParameterService.queryMonthPacketIsGet(m.getUid());
        if (flag){
            br.setErrorMsg("当月礼包已被领取！");
            br.setSuccess(false);
            br.setErrorCode("9997");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("fid", fid);
        DrActivityParameter drActivityParameter = drActivityParameterDAO.selectParameterPrimaryKey(param);
        if (drActivityParameter != null) {
            try {
                Map<String, Object> map = new HashMap<>();
                //领取红包
                map.put("uid", m.getUid());
                map.put("remark", "");
                map.put("code", drActivityParameter.getCode());
                DrMemberFavourable favourable = drMemberFavourableDAO.getMemberFavourableByCode(map);
                favourable.setRemark("月度礼包");
                drMemberFavourableDAO.insertIntoInfo(favourable);
                VipEquities vipEquities = vipEquitiesService.selectByEquitiesName(SystemConstant.MONTH_PACKETS);
                MemberVipInfo info = memberVipInfoMapper.selectByUid(m.getUid());
                if (vipEquities != null) {
                    vipEquitiesMemberService.insertSelective(new VipEquitiesMember(null, m.getUid(), m.getMobilephone(), info.getVipLevel(), vipEquities.getId(), new Date()));
                }
                br.setSuccess(true);
                br.setMsg("领取成功！");
            } catch (Exception e) {
                logger.error("月度礼包领取错误！" + e);
                br.setErrorCode("9999");
                br.setSuccess(false);
                br.setErrorMsg("礼包领取失败！");
            }
        }else{
            logger.error("没有查到该红包！");
            br.setErrorCode("9999");
            br.setSuccess(false);
            br.setErrorMsg("礼包领取失败！");
        }
        return JSON.toJSONString(br);
    }


//
//    public static void main (String[] args){
//        int []ints = new int[]{119900,100,20000,49900,100,3000,64900,100,59900,100,5000,5000,27400,100,49900,100,69900,100};
//        BigDecimal invest = new BigDecimal(0);
//        for(int i = 0; i < ints.length; i++) {
//            BigDecimal investt = new BigDecimal(ints[i]).divide(new BigDecimal(100)).multiply(
//                    new BigDecimal(180)
//                            .divide(new BigDecimal(30))
//            );
//            invest = invest.add(investt);
//        System.out.println("investt = "+investt +"   amount:"+ints[i]);
//        }
//        BigDecimal investtt = new BigDecimal(20000).divide(new BigDecimal(100)).multiply(
//                new BigDecimal(60)
//                        .divide(new BigDecimal(30))
//        );
//        System.out.println("investtt = "+investtt);
//    }

}
