package com.jsjf.common;

import java.math.BigDecimal;

/**
 * 双12活动 奖励设置规则
 * Created by zhangting on 2017/11/26
 */
public enum CardRuleEnum {
    CARD_30_10000_30000_50(30, 10000, 30000, 50),
    CARD_30_30000_50000_150(30, 30000, 50000, 150),
    CARD_30_50000_100000_300(30, 50000, 100000, 300),
    CARD_30_100000_100000000_600(30, 100000, 100000000, 600),
    CARD_60_10000_30000_100(60, 10000, 30000, 100),
    CARD_60_30000_50000_300(60, 30000, 50000, 300),
    CARD_60_50000_100000_600(60, 50000, 100000, 600),
    CARD_60_100000_100000000_1200(60, 100000, 100000000, 1200),
    CARD_180_10000_30000_200(180, 10000, 30000, 200),
    CARD_180_30000_50000_600(180, 30000, 50000, 600),
    CARD_180_50000_100000_1200(180, 50000, 100000, 1200),
    CARD_180_100000_100000000_2500(180, 100000, 100000000, 2500);

    Integer day;//双12活动 投资项目的分类天数 ，分别是30天项目、60天项目、180天项目
    Integer minAmount;//不同投资项目 最小的投资金额，包括这个金额
    Integer maxAmount;//不同投资项目 最大的投资金额，不包括这个金额
    Integer cardAmount;//根据用户投资的金额计算出来的当前分类项目的 京东卡 金额

    CardRuleEnum(Integer day, Integer minAmount, Integer maxAmount, Integer cardAmount) {
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
    public static Integer getCardAmount(String dayStr, BigDecimal userAmountBigDecimal) {
        Integer day = Integer.valueOf(dayStr);
        Integer userAmount = userAmountBigDecimal.intValue();
        CardRuleEnum[] values = CardRuleEnum.values();
        for (CardRuleEnum cardRuleEnum : values) {
        	Integer cardRuleDay = cardRuleEnum.day;
        	Integer minAmount = cardRuleEnum.minAmount;
        	Integer maxAmount = cardRuleEnum.maxAmount;
            if (cardRuleDay.equals(day) && userAmount >= minAmount && userAmount < maxAmount) {
                return cardRuleEnum.cardAmount.intValue();
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        System.out.println(getCardAmount("180", new BigDecimal(10000.00)));
    }
}
