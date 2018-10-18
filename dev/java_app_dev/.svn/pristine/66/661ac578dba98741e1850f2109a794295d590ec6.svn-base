package com.jsjf.dao.vip;

import com.jsjf.model.vip.VipEquities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VipEquitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipEquities record);

    int insertSelective(VipEquities record);

    VipEquities selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipEquities record);

    int updateByPrimaryKey(VipEquities record);

    List<VipEquities> selectByIds(@Param("ids") String ids);

    VipEquities selectByEquitiesName(@Param("equitiesName") String equitiesName);
}