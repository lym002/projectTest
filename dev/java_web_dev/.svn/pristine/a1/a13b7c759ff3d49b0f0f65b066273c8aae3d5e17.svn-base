package com.jsjf.service.vip.impl;

import com.jsjf.dao.vip.VipInfoMapper;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.vip.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VipInfoServiceImpl implements VipInfoService {

    @Autowired
    private VipInfoMapper vipInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return vipInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(VipInfo record) {
        return vipInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(VipInfo record) {
        return vipInfoMapper.insertSelective(record);
    }

    @Override
    public VipInfo selectByPrimaryKey(Integer id) {
        return vipInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(VipInfo record) {
        return vipInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(VipInfo record) {
        return vipInfoMapper.updateByPrimaryKey(record);
    }
}
