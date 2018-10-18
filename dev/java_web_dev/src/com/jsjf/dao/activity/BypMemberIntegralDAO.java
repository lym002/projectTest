package com.jsjf.dao.activity;

import com.jsjf.model.activity.BypMemberIntegral;

public interface BypMemberIntegralDAO {

    /**插积分数据
     * @param record
     * @return
     */
    int insertSelective(BypMemberIntegral record);

	void insert(BypMemberIntegral bmi);


}