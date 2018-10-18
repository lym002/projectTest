package com.jsjf.service.vip.impl;

import com.SensorsAnalytics.SensorsAnalytics;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SensorsAnalyticsUtil;
import com.jsjf.common.SystemConstant;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.vip.MemberGrowthValueDetailMapper;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.dao.vip.VipEquitiesMapper;
import com.jsjf.dao.vip.VipInfoMapper;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.vip.*;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.vip.MemberVipInfoService;
import com.jsjf.service.vip.VipActivityParameterService;
import com.jsjf.service.vip.VipEquitiesMemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class MemberVipInfoServiceImpl implements MemberVipInfoService {

    @Autowired
    private MemberVipInfoMapper memberVipInfoMapper;
    @Autowired
    private MemberGrowthValueDetailMapper memberGrowthValueDetailMapper;
    @Autowired
    private VipInfoMapper vipInfoMapper;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private VipActivityParameterService vipActivityParameterService;
    @Autowired
    private VipEquitiesMapper vipEquitiesMapper;
    @Autowired
    private DrMemberCarryService drMemberCarryService;
    @Autowired
    private VipEquitiesMemberService vipEquitiesMemberService;
    @Autowired
    private DrMemberService drMemberService;



    private static transient Logger log = Logger.getLogger(MemberVipInfoServiceImpl.class);

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return memberVipInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberVipInfo record) {
        return memberVipInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberVipInfo record) {
        return memberVipInfoMapper.insertSelective(record);
    }

    @Override
    public MemberVipInfo selectByPrimaryKey(Integer id) {
        return memberVipInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberVipInfo record) {
        return memberVipInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberVipInfo record) {
        return memberVipInfoMapper.updateByPrimaryKey(record);
    }

    /**
     * 增加用户成长值,提高用户等级
     * @param uid
     * @param productInfo
     * @param amount
     */
    @Override
    public void addMemberGrowthValue(Integer uid, DrProductInfo productInfo, BigDecimal amount){
        MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(uid);
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
        //查询如果为null，用户的vip数据还未录入数据库
        if (memberVipInfo == null){
            memberVipInfo = new MemberVipInfo(null, uid, new BigDecimal(0),1,Utils.getExpirationTime());
            memberVipInfoMapper.insertSelective(memberVipInfo);
            memberVipInfo = memberVipInfoMapper.selectByUid(uid);
        }
        if (memberVipInfo.getIsForbidden() != null && memberVipInfo.getIsForbidden() == 0) return;
        Integer vipLevel = memberVipInfo.getVipLevel();
        try {
            //单次投资金额*单次投资天数/360
            //修改用户成长值
            // done: insert member_growth_value_detail
            //投资获得的成长值
            BigDecimal growthValue = Utils.nwdDivide(Utils.nwdMultiply(amount, productInfo.getDeadline()), new BigDecimal(360)).setScale(2,BigDecimal.ROUND_FLOOR);
            //增加之前的成长值
            BigDecimal growthValueOld = memberVipInfo.getGrowthValue();
            //增加后的成长值
            BigDecimal growthValueMax = Utils.nwdBcadd(growthValueOld,growthValue).setScale(2, BigDecimal.ROUND_FLOOR);

            //insert member growth value detail
            MemberGrowthValueDetail detail = new MemberGrowthValueDetail(null, uid, growthValue, new Date(), 1, "投资获取成长值：" + growthValue);
            memberGrowthValueDetailMapper.insertSelective(detail);
            //用户vip升级
            Map<String, Object> param = new HashMap<>();
            //根据增加之前的成长值，和增加后的成长值获取下一个vip等级
            param.put("growthValueMin", growthValueOld);
            param.put("growthValueMax", growthValueMax);
            try {
                List<VipInfo> vipInfoList = vipInfoMapper.queryNextVipLevel(param);//done sql
                if (vipInfoList != null) {
                    for (VipInfo vip : vipInfoList) {
                        //如果满足升级，则进行红包发放
                        if (growthValueMax.compareTo(vip.getGrowthValueMin()) >= 0) {
                            param.clear();
                            //发升级红包
                            param.put("uid", uid);
                            param.put("remark", "");
                            param.put("code", vip.getLevelUpdateRedPacket());
                            DrMemberFavourable favourable = drMemberFavourableDAO.getMemberFavourableByCode(param);
                            if (favourable != null) {
                                favourable.setRemark(favourable.getName());
                                drMemberFavourableDAO.insertIntoInfo(favourable);
                                DrMember drMember = drMemberService.selectByPrimaryKey(uid);
                                VipEquities vipEquities = vipEquitiesMapper.selectByEquitiesName(SystemConstant.LEVEL_UP_RED_PACKET);
                                MemberVipInfo info = memberVipInfoMapper.selectByUid(uid);
                                if (vipEquities != null) {
                                    vipEquitiesMemberService.insertSelective(new VipEquitiesMember(null, uid, drMember.getMobilephone(), vip.getVipLevel(), vipEquities.getId(), new Date()));
                                }
                            } else {
                                log.error("VIP升级红包查询为空，请检查VIP升级红包列表！：uid ：" + uid +" ----  vip_level ："+ vip.getVipLevel());
                            }
                            //修改神策埋点用户vip等级
                            properties2.put("Ulevel", "VIP"+vip.getVipLevel());
                        }
                        //获取升级后的最高vip等级
                        if (vip.getVipLevel() > vipLevel) vipLevel = vip.getVipLevel();
                    }
                }
            }catch (Exception e){
                log.error("用户VIP升级发放红包错误：uid ：" + uid +" ----  vip_level ："+ vipLevel, e);
            }
            //设置vip等级
            memberVipInfo.setVipLevel(vipLevel);
            //update member growth value
            //设置增加后的成长值
            memberVipInfo.setGrowthValue(growthValueMax);
            memberVipInfoMapper.updateByPrimaryKeySelective(memberVipInfo);
        }catch (Exception e){
            log.error("用户添加成长值错误：uid ：" + uid +" ----  amount："+ amount, e);
        }finally {
            try {
                instance2.profileSet(String.valueOf(uid), true, properties2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询vip基本信息
     * @return
     */
    @Override
    public BaseResult memberVipInfo(Integer uid){
        BaseResult br = new BaseResult();
        Map<String, Object> resultMap = new HashMap<>();
        //查询该用户的vip信息
        MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(uid);
        String nextVipLevelName = "";
        String equitiesIds = "";
        try {
            if (memberVipInfo == null) {
                memberVipInfo = new MemberVipInfo(null, uid, new BigDecimal(0), 1,Utils.getExpirationTime());
                memberVipInfoMapper.insertSelective(memberVipInfo);
                memberVipInfo = memberVipInfoMapper.selectByUid(uid);
            }

            //查询vip下一个等级
            resultMap.put("growthValueMin", memberVipInfo.getGrowthValue());
            resultMap.put("order", " vip_level");
            resultMap.put("limit", "1");
            List<VipInfo> vipInfoList = vipInfoMapper.queryNextVipLevel(resultMap);
            //清空map
            resultMap.clear();
            //vip等级
            VipInfo vipInfo = vipInfoMapper.selectByVipLevel(memberVipInfo.getVipLevel());
            if (vipInfo == null) {
                br.setSuccess(false);
                br.setErrorCode("9999");
                br.setErrorMsg("当前vip等级查询错误！");
                return br;
            }
            if (vipInfoList != null && vipInfoList.size() != 0) {
                nextVipLevelName = vipInfoList.get(0).getVipName();
            } else {
                nextVipLevelName = vipInfo.getVipName();
            }
            //判断是否领取了月度礼包false代表还没领取
            boolean isGet = vipActivityParameterService.queryMonthPacketIsGet(uid);
            //判断改用户是否被禁用，禁用显示vip1等级权益
            if (memberVipInfo.getIsForbidden() != null && memberVipInfo.getIsForbidden() == 0){
                equitiesIds = vipInfoMapper.selectByVipLevel(1).getRightsAndInterestsId();
            }else{
                equitiesIds = vipInfo.getRightsAndInterestsId();
            }
            //获取该vip拥有的权益
            List<VipEquities> vipEquitiesList =  vipEquitiesMapper.selectByIds(equitiesIds.substring(0,equitiesIds.length()-1));
            List<String> equitiesNames = new ArrayList<>();
            if (vipEquitiesList != null){
                for (VipEquities vipEquities : vipEquitiesList) {
                    equitiesNames.add(vipEquities.getEquitiesName());
                }
            }
            //如果将月度礼包权益关闭，则有领取月度礼包可领取将不显示气泡提示
            if (!equitiesNames.contains(SystemConstant.MONTH_PACKETS)) {
                isGet = true;
            }
            Integer free = drMemberCarryService.getDrCarryParamIsChargeNew(uid, vipInfo.getFreeWithdrawDeposit());
            if (free <= 0) free = 0;
            resultMap.put("freeWithdrawDeposit", free);
            resultMap.put("growthValue", memberVipInfo.getGrowthValue());//用户拥有的成长值
            resultMap.put("vipLevelName", vipInfo.getVipName());//vip名称
            resultMap.put("vipLevel", vipInfo.getVipLevel());//vip等级
            resultMap.put("growthValueMax", vipInfo.getGrowthValueMax());//当前vip等级成长值的最大值 //如果最大值为null，则返回0
            resultMap.put("nextVipLevelName", nextVipLevelName);        //vip下一个等级的名称
            resultMap.put("isGet", isGet);      //是否领取了月度礼包
            resultMap.put("equitiesNames", equitiesNames);      //是否领取了月度礼包
            resultMap.put("isForbidden", memberVipInfo.getIsForbidden() == null ? 1 : memberVipInfo.getIsForbidden());      //vip是否可用
            br.setMap(resultMap);
            br.setSuccess(true);
        }catch (Exception e){
            log.error("vip首页查询错误！" + e.getMessage());
            br.setSuccess(false);
            br.setErrorCode("9999");
            br.setErrorMsg("vip首页查询错误！");
        }
        return br;
    }


}
