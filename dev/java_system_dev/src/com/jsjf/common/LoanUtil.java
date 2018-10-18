package com.jsjf.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 * 生成贷款还款期次数据
	 * @param amount 贷款金额
	 * @param rate 利率
	 * @param deadline 期次
	 * @param repayment 还款方式
	 * @param couponAmount 理财金券金额
	 * @return
	 */
	public static List<Map<String,Object>> getLoanRepaymentData(BigDecimal amount,BigDecimal rate,Integer deadline,
			Integer repayment,BigDecimal couponAmount){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		
		//月化利率
		Double monthRate = Double.parseDouble(rate.toString())/100/12;
		
		if(repayment==0){//等额本息
			
			BigDecimal monthAmount = LoanUtil.debxBx(Double.parseDouble(amount.toString()), monthRate, deadline);//月还本息
			BigDecimal allAmount = monthAmount.multiply(new BigDecimal(deadline)); //应还总额
			BigDecimal allLx = Utils.nwdBcsub(allAmount, amount); //应还利息
			
			BigDecimal bjCount = BigDecimal.ZERO;//用于最后一期处理误差
			BigDecimal lxCount = BigDecimal.ZERO;//用于最后一期处理误差
			for(int i=1;i<=deadline;i++){
				map = new HashMap<String, Object>();
				map.put("num", i);
				if(i==deadline){//处理最后一期误差
					map.put("bj", Utils.setScale(Utils.nwdBcsub(amount, bjCount)));
					map.put("lx", Utils.nwdBcsub(allLx, lxCount));
				}else{
					BigDecimal bj = LoanUtil.debxBj(Double.parseDouble(amount.toString()), monthRate, deadline,i);
					map.put("bj", bj);
					map.put("lx", Utils.nwdBcsub(monthAmount,bj));
					bjCount = bjCount.add(bj);
					lxCount = lxCount.add(Utils.nwdBcsub(monthAmount,bj));
				}
				if(couponAmount.compareTo(BigDecimal.ZERO)>0){//理财金券利息
					map.put("lx", Utils.nwdBcadd(map.get("lx"), LoanUtil.debxLx(Double.parseDouble(couponAmount.toString()), monthRate, deadline, i)));
				}
				BigDecimal balance = Utils.nwdBcsub(allAmount, Utils.nwdMultiply(monthAmount, i));//剩余还款额
				map.put("balance", balance);
				map.put("monthAmount", Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				list.add(map);
//				System.out.println("第"+i+"期还款数据： 本金"+map.get("bj")+"   利息"+map.get("lx")+" 本息"+map.get("monthAmount")+
//						"     剩余金额"+map.get("balance"));
			}
		}else if(repayment==1){//等本等息
			BigDecimal allLx = Utils.setScale(Utils.nwdMultiply(Utils.nwdMultiply(amount,monthRate),deadline));
			BigDecimal monthBj = Utils.setScale(Utils.nwdDivide(amount, deadline));
			BigDecimal monthLx = Utils.setScale(Utils.nwdMultiply(amount, monthRate));
			BigDecimal couponLx = Utils.setScale(Utils.nwdMultiply(couponAmount, monthRate));//理财金券利息
			
			BigDecimal bxCount = BigDecimal.ZERO;
			for(int i=1; i <= deadline ; i++){
				map = new HashMap<String, Object>();
				map.put("num", i);
				map.put("bj", monthBj);
				map.put("lx", monthLx);
				if( i == deadline ){//最后一期误差处理
					map.put("bj", Utils.nwdBcsub(amount, Utils.nwdMultiply(monthBj,(deadline-1))));//贷款本金-已还本金
					map.put("lx", Utils.nwdBcsub(allLx,Utils.nwdMultiply(monthLx,(deadline-1))));//贷款利息-已还利息
					bxCount = bxCount.add(Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				}else{
					bxCount = bxCount.add(Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				}
				if(couponAmount.compareTo(BigDecimal.ZERO)>0){//理财金券利息
					map.put("lx", Utils.nwdBcadd(map.get("lx"), couponLx));
				}
				map.put("monthAmount", Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				map.put("balance", Utils.nwdBcsub(Utils.nwdBcadd(amount,allLx),bxCount));
				list.add(map);
			}
		}else if(repayment==2){//一次付息到期还本
			BigDecimal allLx = Utils.setScale(Utils.nwdMultiply(Utils.nwdMultiply(amount.add(couponAmount),monthRate),deadline));
			for(int i = 1 ; i <= 2 ; i++ ){
				map = new HashMap<String, Object>();
				map.put("num", i);
				if(i==1){
					map.put("lx", allLx);
					map.put("bj", 0);
					map.put("monthAmount", allLx);
					map.put("balance", amount);
				}else{
					map.put("lx", 0);
					map.put("bj", amount);
					map.put("monthAmount", amount);
					map.put("balance", 0);
				}
				list.add(map);
			}
		}else if(repayment==3){//先息后本
			BigDecimal allLx = Utils.setScale(Utils.nwdMultiply(Utils.nwdMultiply(amount.add(couponAmount),monthRate),deadline));//总利息
			BigDecimal monthLx = Utils.setScale(Utils.nwdMultiply(amount.add(couponAmount), monthRate));//月息
			
			BigDecimal bxCount = BigDecimal.ZERO;
			BigDecimal lxCount = BigDecimal.ZERO;
			for(int i = 1; i <= deadline; i++ ){
				map = new HashMap<String, Object>();
				map.put("num", i);
				map.put("lx", monthLx);
				map.put("bj", 0);
				if(i == deadline){
					map.put("bj", amount);
					map.put("lx", Utils.nwdBcsub(allLx,lxCount));
				}
				bxCount = bxCount.add(Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				lxCount = Utils.nwdBcadd(lxCount, map.get("lx"));
				map.put("balance", Utils.nwdBcsub(Utils.nwdBcadd(amount, allLx),bxCount));
				map.put("monthAmount", Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				list.add(map);
			}
		}
		else if(repayment==4){//新先息后本
			BigDecimal allLx = Utils.setScale(Utils.nwdMultiply(Utils.nwdMultiply(amount.add(couponAmount),monthRate),deadline));//总利息
			BigDecimal monthLx = Utils.setScale(Utils.nwdMultiply(amount.add(couponAmount), monthRate));//月息
			
			BigDecimal bxCount = BigDecimal.ZERO;
			BigDecimal lxCount = BigDecimal.ZERO;
			for(int i = 1; i <= deadline+1; i++ ){
				map = new HashMap<String, Object>();
				map.put("num", i);
				map.put("lx", monthLx);
				map.put("bj", 0);
				if(i == deadline){
					map.put("lx", Utils.nwdBcsub(allLx,lxCount));
				}else if(i == deadline+1){
					map.put("bj", amount);
					map.put("lx", 0);
				}
				bxCount = bxCount.add(Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				lxCount = Utils.nwdBcadd(lxCount, map.get("lx"));
				map.put("balance", Utils.nwdBcsub(Utils.nwdBcadd(amount, allLx),bxCount));
				map.put("monthAmount", Utils.nwdBcadd(map.get("bj"), map.get("lx")));
				list.add(map);
			}
		}
		return list;
		
	}
}
