package com.jsjf.service.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;

public interface DrProductInvestService {
	
	
	/**
	 * 根据投资记录id获取投资记录
	 * @param id
	 * @return
	 */
	public DrProductInvest selectByPrimaryKey(Map<String,Object> map);

	/**
	 * 拿到投资列表数据
	 * 
	 * @param DrProductInvest
	 * @param PageInfo
	 * @return BaseResult
	 */
	public PageInfo selectInvestLogByParam(Map<String, Object> map, PageInfo pi);
	
	
	/**
	 * 产品已投资人数
	 * @param pid
	 * @return
	 */
	public Integer selectInvestNumsByPid(Integer pid);
	
    /**
     * 通过状态查找用户对应本金总合
     * @param status
     * @param uid
     * @return
     */
    public BigDecimal selectUserSumPrincipalByStatus(Map<String, Object> map);
    /**
     * 查询累计收益
     * @param map
     * @return
     */
    public BigDecimal selectAccumulatedIncomeSumByUid(Map<String,Object> map);
    
    /**
     * 通过状态查找用户对应利息总合
     * @param status
     * @param uid
     * @return
     */
    public BigDecimal selectUserSumInterestByStatus(Map<String, Object> map);
    
    /**
     * 用户最后一次投资信息
     * @param uid
     * @return
     */
    public Map<String,Object> selectUserLastInvestmentInfo(Integer uid);
    
    /**
     * 赠送渠道用户电影票
     * @param param
     * @return
     */
    public void sendTicket(DrMember member,BigDecimal amount) throws Exception;
    
    /**
     * 查询投资记录前100条数据
     * @param param
     * @return
     */
    public List<DrProductInfo>  selectInvest();
    
    /**
     * 查询投资记录条数
     * @param param
     * @return
     */
    public Integer  selectInvestLogCountByParam(Map<String, Object> param);
    /**
     * 查询
     * @param param
     * @return
     */
    public List<DrProductInvest> selectInvestByMap(Map<String, Object> param);
    
    /**
     * 查询自己使用的体验金
     * @param uid
     * @param type
     * @param status
     * @return
     */
    public BigDecimal selectExperienceByStatus(Integer uid,Integer type,Integer status);
    
    /**
	 * 判断用户投过新手标或者体验标
	 * @param param
	 * @return
	 */
	public boolean checkProductType(Map<String,Object> param);
	/**
	 * 查询投即送投资list
	 * @param param
	 * @return
	 */
	public List<DrProductInvest> investSendList(Map<String,Object> param);
	
	/**
	 * 查询是否为老用户
	 * @param uid
	 * @return
	 */
	public int selectIsOldUserById(Integer uid);
	
	/**
	 * 查询体验标的投资人数
	 * @param pid
	 * @return
	 */
	public int selectExperInvestNumsByPid(Integer pid);
	
	public List<DrProductInvest> selectSimpleInvestLog(int pid);
	/**
	 * 根据条件查询是否可提现
	 * @param param
	 * @return
	 */
	public Boolean selectInvestCount(Integer uid,BigDecimal amount,Integer type);
	/**
	 * 查询投资体验标人的list和人数
	 * @return
	 */
	public BaseResult selectNoviceListAndCount();
	/**
	 * 根据投资id查奖品详情
	 * @param investId
	 * @return
	 */
	public List<Map<String, Object>> selectprizeInfoByInvestId(Map<String,Object> param);
	/**
	 * 根据uid查询用户投资投即送标的list
	 * @param uid
	 * @return
	 */
	public List<DrProductInvest> selectInvestSendDrProductInfoByUid(Integer uid);
	/**
	 * 根据投资id查询是否完善信息
	 * @param investId
	 * @return
	 */
	public Map<String,Object> selectIsPerfectByInvestId(Integer investId);
	/**
	 * 查询投即送没有地址的记录
	 * @param uid
	 * @return
	 */
	public List<DrProductInvest> selectInvestSendNotAddress(Integer uid);
	/**
	 * 518活动
	 * @param param
	 * @return
	 */
	public BaseResult getActivityMay18d(DrMember member)  throws Exception ;
	/**
	 * 根据参数计算用户投资次数
	 * @param map
	 * @return
	 */
	public Integer selectInvestCountByMap(Map<String,Object> map);
	/**
	 * 查询首投
	 * @param uid
	 * @return
	 */
	public Map<String,Object> selectInvestFirstByUid(Integer uid);
	/**
	 * 查询新版本是否投资过
	 * @param uid
	 * @return
	 */
	public Integer selectNewInvest(Integer uid);

    /**
     * 查看用户人人有份活动累计投资额
     * @param map
     * @return
     */
    BaseResult getEveryoneJdCard(Map<String, Object> map) throws IOException;

    /**
     * 根据时间查询当日top5土豪获奖人员名单
     * @param map
     * @return
     */
    BaseResult getEveryoneTopFive(Map<String, Object> map);

    /**
     * 人人有份京东卡活动_TOP10VIP3榜单
     * @return
     */
    BaseResult getEveryoneForVIP(Map<String, Object> map);

    BaseResult getEveryoneTopTen();

    /**
     * 5880领取红包和加息券
     * @param map
     * @return
     */
    BaseResult getTreasure(Map<String, Object> map) throws SQLException;

	List<Map<String,Object>> selectNewInvestByActivityProduct(Map<String, Object> map);

    Map<String,Object> selectInvestInfo(Map<String, Object> map);
}
