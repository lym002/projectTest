package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.member.DrMemberCoupon;

public interface DrMemberCouponDAO {

    /**
     * 查找会员理财金券
     * @param id
     * @return
     */
    DrMemberCoupon selectByPrimaryKey(Integer id);

    /**
     * 修改会员理财金券
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DrMemberCoupon record);
    /**
     * 查询快到期的理财金券(快到期时间等于一个月的)
     * @return
     * @throws SQLException
     */
    public List<DrMemberCoupon> selectCouponList() throws SQLException;
}