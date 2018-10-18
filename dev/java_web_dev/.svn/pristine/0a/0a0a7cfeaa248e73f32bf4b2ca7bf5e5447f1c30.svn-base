package com.jsjf.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	/**
	 * 随机获取指定list中的一个值
	 * @param list
	 * @return
	 */
	public static BigDecimal randomByList(List<BigDecimal>list){
		if(list == null || list.isEmpty())return null;
		Random ra =new Random();
		return list.get(ra.nextInt(list.size()));
	}
	/**
	 * 随机从list中获取一个大于等于_value的值
	 * @param list
	 * @param _value
	 * @return
	 */
	public static BigDecimal randomByListGEValue(List<BigDecimal>list, BigDecimal _value){
		if(list == null || list.isEmpty())return null;
		List<BigDecimal>temp = new ArrayList<>();
		BigDecimal eqValue = null;
		for(int i = 0; i < list.size(); i++){
			BigDecimal tempValue = list.get(i);
			if(tempValue.compareTo(_value) > 0){
				temp.add(tempValue);
			}else if(tempValue.compareTo(_value) == 0){
				eqValue = tempValue;
			}
		}
		if(temp.isEmpty()){
			temp.add(eqValue);
		}
		Random ra =new Random();
		return temp.get(ra.nextInt(temp.size()));
	}
	
	public static void main(String[] args){
		List<BigDecimal>temp = new ArrayList<>();
		temp.add(new BigDecimal(0.5));
		temp.add(new BigDecimal(0.6));
		temp.add(new BigDecimal(0.7));
		temp.add(new BigDecimal(0.8));
		temp.add(new BigDecimal(0.9));
		temp.add(new BigDecimal(1));
		for(int i = 0; i < 20; i ++){
			System.out.println("1========" + randomByList(temp));
			System.out.println(i + "==0.5======" + randomByListGEValue(temp, new BigDecimal(0.5)));
			System.out.println(i + "==0.7======" + randomByListGEValue(temp, new BigDecimal(0.7)));
			System.out.println(i + "==1======" + randomByListGEValue(temp, new BigDecimal(1)));
		}
	}
	
}
