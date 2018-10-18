package com.jsjf.common;

import java.math.BigDecimal;

public class LoanUtil {
	
	/**
	 * 等额本息每月还款本息
	 * 
	 * @param amount
	 *            贷款本金
	 * @param monthRate
	 *            月利率
	 * @param deadline
	 *            还款月数
	 * @return 每月还款额
	 */
	public static BigDecimal debxBx(Double amount, Double monthRate, int deadline) {
		Double cf = (Double) Math.pow((1 + monthRate), deadline); // （1+月利率）^还款月数
		Double monthAmount = amount * monthRate * cf / (cf - 1); // 每月还款额 =
																	// [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
		BigDecimal bd = new BigDecimal(monthAmount);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); // 取小数点后2位
		return bd;
	}
	
	/**
	 * 等额本息第N期还款利息
	 * 
	 * @author liuyong
	 * @param amount
	 *            贷款本金
	 * @param monthRate
	 *            月利率/日利率
	 * @param deadline
	 *            还款月数
	 * @param n
	 *            期数
	 * @return 第N期还款本金
	 */
	public static BigDecimal debxLx(Double amount, Double monthRate, int deadline, int n){
		Double cf = (Double) Math.pow((1 + monthRate), n-1); // （1+月利率）^(期数-1)
		BigDecimal monthAmount = debxBx(amount, monthRate, deadline);
		Double result = (amount*monthRate-monthAmount.doubleValue())*cf+monthAmount.doubleValue();
		BigDecimal lx = new BigDecimal(result);
		lx = lx.setScale(2, BigDecimal.ROUND_HALF_UP); // 取小数点后2位
		return lx;
	}
	
	/**
	 * 等额本息第N期还款本金
	 * 
	 * @author liuyong
	 * @param amount
	 *            贷款本金
	 * @param monthRate
	 *            月利率/日利率
	 * @param deadline
	 *            还款月数
	 * @param n
	 *            期数
	 * @return 第N期还款本金
	 */
	public static BigDecimal debxBj(Double amount, Double monthRate, int deadline,
			int n) {
		// 每期应还本金 = a*i(1+i)^(n-1)/[(1+i)^N-1] N:还款月数
		// [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]

		Double cf1 = (Double) Math.pow((1 + monthRate), (n - 1)); // （1+月利率）^还款月数
		Double cf2 = (Double) (Math.pow((1 + monthRate), deadline) - 1);
		Double cf3 = amount * monthRate * cf1 / cf2;
		BigDecimal bd = new BigDecimal(cf3);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); // 取小数点后2位
		return bd;
	}
	
	/**
	 * 返回小数点后2位
	 * 
	 * @param object
	 * @return .00
	 */
	public static BigDecimal setScale(Object object) {
		BigDecimal bd = new BigDecimal(object.toString());
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); // 取小数点后2位 /ROUND_HALF_UP
		return bd;
	}

}
