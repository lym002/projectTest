package com.jsjf.service.vip;

import com.jsjf.common.BaseResult;
import com.jsjf.model.vip.VipActivityParameter;

import java.util.List;

public interface VipActivityParameterService {
    int deleteByPrimaryKey(Integer id);

    int insert(VipActivityParameter record);

    int insertSelective(VipActivityParameter record);

    VipActivityParameter selectByPrimaryKey(Integer id);

    List<VipActivityParameter> selectByVipLevel(Integer id);

    int updateByPrimaryKeySelective(VipActivityParameter record);

    int updateByPrimaryKey(VipActivityParameter record);

    /**
     * 查询月度礼包
     * @return
     */
    BaseResult queryMonthPacket(Integer vipLevel, Integer uid);

    /**
     * 查询月度礼包是否领取过
     * @return
     */
    boolean queryMonthPacketIsGet(Integer uid);

}