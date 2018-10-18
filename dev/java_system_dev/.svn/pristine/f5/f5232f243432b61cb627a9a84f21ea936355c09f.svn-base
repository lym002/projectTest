package com.jsjf.service.activity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

/**
 *  返现红包
 * @author xxq
 *
 */
public interface ActivityReversalService {
	public void reversal() throws Exception;
	
	public void reversals() throws Exception;
	
	public void reversalImplement(Map<String, Integer> uInfo) throws Exception;
	
	public void reversalImplement(Map<String, Integer> uInfo,BigDecimal min) throws Exception;

	public void selectReversal();

    void transferToClient(Integer uid, BigDecimal bigDecimal,String msg) throws SQLException;
}
