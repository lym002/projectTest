package com.jsjf.controller.task;

import com.jsjf.common.SystemConstant;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.dao.vip.VipEquitiesDao;
import com.jsjf.dao.vip.VipEquitiesMemberDao;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.vip.MemberVipInfo;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.vip.MemberVipInfoService;
import com.jsjf.service.vip.VipEquitiesMemberService;
import com.jsjf.service.vip.VipEquitiesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VipMemberTask {

    @Autowired
    private MemberVipInfoService memberVipInfoService;
    @Autowired
    private DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    private VipEquitiesMemberDao vipEquitiesMemberDao;
    @Autowired
    private DrMemberService drMemberService;
    @Autowired
    private VipEquitiesDao vipEquitiesDao;
    @Autowired
    private MemberVipInfoMapper memberVipInfoMapper;

    private static transient Logger log = Logger.getLogger(VipMemberTask.class);

    /**
     * 每日发放vip会员红包
     */
    @Scheduled(cron="0 1 0 * * ? ")
    public void sendCode4VipMemberTask(){
        Map<String, Object> param = new HashMap<>();
        List<MemberVipInfo> list = memberVipInfoService.selectTodayBirthdayMemberCode();
        if (list == null){
            list = new ArrayList<>();
        }
        log.info("====================VIP生日红包开始发放======================" );
        try{
            param.put("remark", "");
            for (MemberVipInfo memberVipInfo : list) {
                param.put("uid", memberVipInfo.getUid());
                param.put("code", memberVipInfo.getCode());
                DrMemberFavourable favourable = drMemberFavourableDAO.getMemberFavourableByCode(param);
                if (favourable != null) {
                    favourable.setRemark("生日红包");
                    drMemberFavourableDAO.insertIntoInfo(favourable);
                    DrMember drMember = drMemberService.selectByPrimaryKey(memberVipInfo.getUid());
                    VipEquities vipEquities = vipEquitiesDao.selectByEquitiesName(SystemConstant.BIRTHDAY_PACKETS);
                    MemberVipInfo info = memberVipInfoMapper.selectByUid(memberVipInfo.getUid());
                    if (vipEquities != null) {
                        vipEquitiesMemberDao.insertSelective(new VipEquitiesMember(null, drMember.getUid(), drMember.getMobilephone(), info.getVipLevel(), vipEquities.getId(), new Date()));
                    }
                    log.info("VIP生日红包发放：uid ：" + memberVipInfo.getUid() + " ----  vip_level ：" + memberVipInfo.getVipLevel());
                } else {
                    log.error("VIP生日红包查询为空，请检查VIP生日红包列表！：uid ：" + memberVipInfo.getUid() + " ----  vip_level ：" + memberVipInfo.getVipLevel());
                }
            }
        }catch (Exception e){
            log.error("VIP生日红包查询错误！" + e);
        }
        log.info("====================VIP生日红包发放结束======================" );
        //sql
//        <select id="selectTodayBirthdayMemberCode" resultMap="BaseResultMap">
//                SELECT
//        bmvi.*,bvap.`code`
//        FROM (select uid, FROM_UNIXTIME(
//                UNIX_TIMESTAMP(
//                        CAST(
//                                SUBSTRING(
//                                        dr_member_base_info.`idcards`,
//                                        7,
//                                        8
//                                ) AS DATETIME
//                        )
//                ),
//                '%m%d'
//        ) nowDate from dr_member_base_info) as dr_member LEFT JOIN
//        dr_member_base_info ON dr_member_base_info.`uid` = dr_member.`uid`
//        LEFT JOIN byp_member_vip_info bmvi ON dr_member_base_info.`uid` = bmvi.`uid`
//        LEFT JOIN byp_vip_info bvi ON bmvi.`vip_level` = bvi.`vip_level`
//        LEFT JOIN byp_vip_activity_parameter bvap ON bvi.`vip_level` = bvap.`vip_level`
//        WHERE  dr_member.nowDate = DATE_FORMAT(NOW(),'%m%d')
//        AND bvap.`is_birthday` = 1 and bvap.is_open = 1
//                </select>
    }

}
