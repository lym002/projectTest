package com.jsjf.service.member;

import com.jsjf.model.member.BypMemberInvite;

public interface BypMemberInviteService {
    int deleteByPrimaryKey(Integer id);

    int insert(BypMemberInvite record);

    int insertSelective(BypMemberInvite record);

    BypMemberInvite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BypMemberInvite record);

    int updateByPrimaryKey(BypMemberInvite record);
}