package com.jsjf.service.seq;

import java.util.Calendar;
import java.util.List;

public interface SeqService {
	/**
     * 重置模式：每天重置计数器
     */
    public static final byte RESET_BY_DAY= 'd';
    /**
     * 重置模式：以年度周数为单位重置计数器。周的第一天以改国日历为准，中国第一天是周日
     */
    public static final byte RESET_BY_WEEK_OF_YEAR='w';
    /**
     * 重置模式：当月改变，和周改变时都重置计数器。 周的第一天以改国日历为准，中国第一天是周日
     */
    public static final byte RESET_BY_WEEK_OF_MONTH='o';
    /**
     *   重置模式：每月重置计数器
     */
    public static final byte RESET_BY_MONTH='m';
    /**
     *   重置模式：每年重置计数器
     */
    public static final byte RESET_BY_YEAR='y';
    /**
     *   重置模式：不按时间重置计数器
     */
    public static final byte RESET_NEVER='n';

    /**
     * 溢出模式：当计数器超过最大置时重新计数
     */
    public static final byte OVERFLOW_RESET='r';
    /**
     * 溢出模式：当计数器超过最大值时报错
     */
    public static final byte OVERFLOW_ERROR='e';

	public int getSeqNo(String key);

	public List<Integer> getSeqNoList(String key, int total,int digit);
	
	public List<String> generateLuckCodes(Integer pid, int total, String prefix,Integer digit);

	int getSequence(String category);

	int getSequenceResetByDay(String category);

	int getSequence(String category, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue,
			Calendar _current_);
	
}
