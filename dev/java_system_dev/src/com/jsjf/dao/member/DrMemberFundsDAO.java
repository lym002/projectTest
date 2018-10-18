package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberFunds;

public interface DrMemberFundsDAO {
	
	/**
	 * 根据uid传入变动金额changeAmount,在原有的金额上加上变动金额
	 * @param changeAmount
	 * @param uid
	 * @throws SQLException
	 */
	public void updateYsmemberFundsSelfAdd(DrMemberFunds drMemberFunds) throws SQLException;

	/**
	 * @Description 根据id查询
	 * @param uid
	 * @return DrMemberFunds
	 * @throws 
	 */
    public DrMemberFunds queryDrMemberFundsByUid(int uid); 
    
	/**
	 * @Description 修改
	 * @param DrMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException; 
    
	void insert(DrMemberFunds record);
	
    public List<DrMemberFunds> getNeedPayDrMemberFundsList();
    
    void batchUpdateDrMemberFunds(List<DrMemberFunds> list);
    
    /**
     * 平台当前账户余额总额
     * @return
     */
    public Double getDrMemberBalanceSum();
    
    /**
     * 富有当前账户余额总额
     * @return
     */
    public Double getDrMemberBalanceSumByFuiou();
    
    /**
     * 平台用户明日到期资金总额
     * @return
     */
    public Double getDrMemberExpireSum();

    List<DrMemberFunds> selectListByUids(String uids);

    void updateMemberCarry(Map<String, Object> param);

    void updateFuiouNotice(Map<String, Object> param);

    void updateMemberFunds(Map<String, Object> param);

    Integer insertMemberFundsRecord(Map<String, Object> param);

    void insertMemberFundsLog(Map<String, Object> param);

    Integer selectCarryId(Map<String, Object> param);

    Integer selectRecordId(Map<String, Object> param);
}
