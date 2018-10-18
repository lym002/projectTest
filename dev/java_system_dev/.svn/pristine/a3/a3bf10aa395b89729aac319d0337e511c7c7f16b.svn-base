package com.jsjf.common;

import java.math.BigDecimal;

/**
 * 双12活动 奖励设置规则
 * Created by zhangting on 2017/11/26
 */
public enum CardRuleEnum {
    CARD_30_10000_30000_50(30, new BigDecimal(10000), new BigDecimal(30000), new BigDecimal(50)),
    CARD_30_30000_50000_150(30, new BigDecimal(30000), new BigDecimal(50000), new BigDecimal(150)),
    CARD_30_50000_100000_300(30, new BigDecimal(50000), new BigDecimal(100000), new BigDecimal(300)),
    CARD_30_100000_100000000_600(30, new BigDecimal(100000), new BigDecimal(100000000), new BigDecimal(600)),
    CARD_60_10000_30000_100(60, new BigDecimal(10000), new BigDecimal(30000), new BigDecimal(100)),
    CARD_60_30000_50000_300(60, new BigDecimal(30000), new BigDecimal(50000), new BigDecimal(300)),
    CARD_60_50000_100000_600(60, new BigDecimal(50000), new BigDecimal(100000), new BigDecimal(600)),
    CARD_60_100000_100000000_1200(60, new BigDecimal(100000), new BigDecimal(100000000), new BigDecimal(1200)),
    CARD_180_10000_30000_200(180, new BigDecimal(10000), new BigDecimal(30000), new BigDecimal(200)),
    CARD_180_30000_50000_600(180, new BigDecimal(30000), new BigDecimal(50000), new BigDecimal(600)),
    CARD_180_50000_100000_1200(180, new BigDecimal(50000), new BigDecimal(100000), new BigDecimal(1200)),
    CARD_180_100000_100000000_2500(180, new BigDecimal(100000), new BigDecimal(100000000), new BigDecimal(2500));

    Integer day;//双12活动 投资项目的分类天数 ，分别是30天项目、60天项目、180天项目
    BigDecimal minAmount;//不同投资项目 最小的投资金额，包括这个金额
    BigDecimal maxAmount;//不同投资项目 最大的投资金额，不包括这个金额
    BigDecimal cardAmount;//根据用户投资的金额计算出来的当前分类项目的 京东卡 金额

    CardRuleEnum(Integer day, BigDecimal minAmount, BigDecimal maxAmount, BigDecimal cardAmount) {
        this.day = day;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.cardAmount = cardAmount;
    }


    /**
     * 根据用户投资的金额计算出来的当前分类项目的 京东卡 金额
     *
     * @param dayStr 不同的分类用户投资的天数
     * @param userAmountBigDecimal 不同分类的项目用户投资的总金额
     * @return Integer
     * @author zhangting
     * @since 2017-11-30
     */
    public static BigDecimal getCardAmount(String dayStr, BigDecimal userAmountBigDecimal) {
        Integer day = Integer.valueOf(dayStr);
        BigDecimal userAmount = userAmountBigDecimal;
        CardRuleEnum[] values = CardRuleEnum.values();
        for (CardRuleEnum cardRuleEnum : values) {
        	Integer cardRuleDay = cardRuleEnum.day;
        	BigDecimal minAmount = cardRuleEnum.minAmount;
        	BigDecimal maxAmount = cardRuleEnum.maxAmount;
            if (cardRuleDay.equals(day) && userAmount.compareTo(minAmount) >= 0 && userAmount.compareTo(maxAmount) == -1) {
                return cardRuleEnum.cardAmount;
            }
        }
        return new BigDecimal(0);
    }


    public static void main(String[] args) {
        System.out.println(getCardAmount("180", new BigDecimal(50000.00)));
    }
}
