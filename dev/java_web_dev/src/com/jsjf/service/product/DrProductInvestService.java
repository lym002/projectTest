package com.jsjf.service.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberLotteryLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;

public interface DrProductInvestService {

	/**
	 * 拿到投资列表数据
	 * 
	 * @param DrProductInvest
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult selectInvestLogByParam(Map<String, Object> map, PageInfo pi);
	
	/**
	 * 获取投资成功的产品总金额
	 * @param map
	 * @return
	 */
	public BigDecimal selectInvestSumByParam(Map<String, Object> map);
	
	/**
	 * 根据投资记录id获取投资记录
	 * @param id
	 * @return
	 */
	public DrProductInvest selectByPrimaryKey(Integer id);
	
	public DrProductInvest selectByParam(Map<String,Object> map);
	
	/**
	 * 根据参数计算用户投资次数
	 * @param map
	 * @return
	 */
	public Integer selectInvestCountByMap(Map<String,Object> map);
	
	/**
	 * 查询时间段内，投资排名
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param topNum 投资排行前多少名
	 * @return
	 */
	public List<Map<String, Object>> selectInvestTopGroupByUid(Map<String, Object> map);
	
	/**
	 * 用户最后一次投资记录信息
	 * @param uid
	 * @return 产品类型   产品到期时间   标识（到期时间是否大于当前时间）
	 */
	public Map<String,Object> selectUserLastInvestmentInfo(Integer uid);
	
	/**
     * 赠送渠道用户电影票
     * @param param
     * @return
     */
    public void sendTicket(DrMember member,BigDecimal amount) throws Exception;
    
    /**
     * 人人利查询数据
     * @param param
     * @return
     */
    public List<Map<String, Object>> rrlQueryInvestList(Map<String, Object> param) throws Exception;
    
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
	 * 查询是否为老用户
	 * @param uid
	 * @return
	 */
	public int selectIsOldUserById(Integer uid);
	
	
	/**
	 * 判断用户投过新手标或者体验标
	 * 
	 * @param param
	 * @return
	 */
	public boolean checkProductType(Map<String, Object> param);
	
	public void insertLogtteryLog(DrMemberLotteryLog drMemberLotteryLog);

	/**
	 *  根据pid查询投资次数
	 * @param pid
	 * @return
	 */
	public Integer selectExperienceInvestCount(Integer pid);
	
	 /**
	  * 根据产品类型,产品状态，投资状态，查询投资记录
	  * @param param
	  * @return
	  */
	public List<DrProductInvest> getProductInvestListByType(Map<String,Object> param);
	
	/**
	 * 根据产品类型,产品状态，投资状态，查询投资总数
	 * @param param
	 * @return
	 */
	public int getProductInvestCountByType(Map<String,Object> param);
	/**
	 * 518活动
	 * @param param
	 * @return
	 */
	public BaseResult getActivityMay18d(DrMember member)  throws Exception ;
	/**
	 * 根据投资记录id获取E签宝的id
	 * @param string
	 * @return
	 */
	public DrProductInvest getEvidByInvestId(String string);

    List<Map<String,Object>> getCount(Integer id, Date bidStart, Date bidEnd);

    BaseResult getEveryoneJdCard(Map<String, Object> map) throws IOException;

    BaseResult getEveryoneTopFive(Map<String, Object> map);

    BaseResult getEveryoneForVIP(Map<String, Object> map);

    BaseResult getEveryoneTopTen();

    /**
     * 5880领取红包和加息券
     * @param map
     * @return
     */
    BaseResult getTreasure(Map<String, Object> map) throws SQLException;

    Integer selectNewInvest(Integer uid);

	List<Map<String,Object>> selectNewInvestByActivityProduct(Map<String, Object> map);
}
