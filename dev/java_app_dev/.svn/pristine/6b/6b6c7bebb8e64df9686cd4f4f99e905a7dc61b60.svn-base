package com.RSA.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ����:ͨ�õľ�̬���ߺ���(�������ں�ʱ�䴦��)
 * <p>Description: ͨ�ù�����</p>
 */
public class DateTimeUtils {

	public final static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public final static String DATETIME_FORMAT_HHMMSS = "HHmmss";
	public final static String DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public final static String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public final static String DATETIME_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
	public final static String DATETIME_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public final static String DATETIME_FORMAT_YYYY = "yyyy";
	public final static String DATETIME_FORMAT_YYYY_MM_DD_CN = "yyyy��MM��dd��";
	public final static String DATETIME_FORMAT_HH_MM_SS = "HH:mm:ss";
	/** AOP DateĬ��ʱ�� **/
	public static final String DATE_TIMEZONE = "GMT+8";
	
	private static final Log log = LogFactory.getLog(DateTimeUtils.class);
	
	/**
	* ���ָ�����ڵĺ�N�� 
	* @param specifiedDay 
	* @return 
	*/ 
	public static Date getSpecifiedDayAfter(Date date,int num){ 
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+num); 
		return c.getTime(); 
	} 
	
	/**
	 * ���ڼ���Ƿ����ָ����
	 * @param begin
	 * @param end
	 * @param timeinmill
	 * @return
	 */
	public static boolean isAfterInMill(Date begin,Date end,long timeinmill){ 
		long begininmill = begin.getTime();
		long endinmill = end.getTime();
		if(endinmill-begininmill>timeinmill){
			return true;
		}else{
			return false; 
		}
	} 

	/**
	 * �Ƚ�����ʱ���С
	 * wangbo 2009.5.5
	 * @param first
	 * @param second
	 * @return 
	 * 		<0: first<second
	 * 		=0: first=second
	 * 		>0: first>second
	 */
	public static int compareTwoDate(Date first, Date second){
		Calendar c1=java.util.Calendar.getInstance();
        Calendar c2=java.util.Calendar.getInstance();
        
        c1.setTime(first);
        c2.setTime(second);
        
		return c1.compareTo(c2);
	}

	/**
	* ȡ�õ�ǰ���������ܵĵ�һ��
	*
	* @param date
	* @return
	*/
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime ();
	}

	/**
	* ȡ�õ�ǰ���������ܵ����һ��
	*
	* @param date
	* @return
	*/
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * �����������ӻ����Сʱ��������
	 * @param date
	 * @param iΪ����ʾ������Сʱ
	 * @return
	 */
	public static Date addHH(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.HOUR, i);
		return c.getTime();
	}
	
	/**
	 * �����������ӻ���ٷ�����������
	 * @param date
	 * @param iΪ����ʾ�����ٷ���
	 * @return
	 */
	public static Date addMM(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.MINUTE, i);
		return c.getTime();
	}
	
	/**
	 * �����������ӻ��������������
	 * @param date
	 * @param iΪ����ʾ��������
	 * @return
	 */
	public static Date addSS(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.SECOND, i);
		return c.getTime();
	}

	/**
	 * �����������Ӽ�������������
	 * @param date
	 * @param iΪ����ʾ��������
	 * @return
	 */
	public static Date addDate(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, i);
		return c.getTime();
	}
	/**
	 * �����������Ӽ�������������
	 * @param date
	 * @param iΪ����ʾ��������
	 * @return
	 */
	public static Date addMonth(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.MONTH, i);
		return c.getTime();
	}
	/**
	 * �����������Ӽ�������������
	 * @param date
	 * @param iΪ����ʾ��������
	 * @return
	 */
	public static Date addYear(Date date,int i){
		if(date==null)return null;
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.YEAR, i);
		return c.getTime();
	}
	

	/**
	 * ��õ�ǰʱ���ַ�
	 * @param formatStr ���ڸ�ʽ
	 * @return string yyyy-MM-dd
	 */
	public static String getNowDateStr(String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(getNowDate());
	}

	/**
	 * ���ϵͳ��ǰʱ��
	 * @return Date
	 */
	public static Date getNowDate(){
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * �����ڰ���ָ����ʽ��ת�����ַ�
	 * @param date ���ڶ���
	 * @param formatStr ���ڸ�ʽ
	 * @return �ַ�ʽ������,��ʽΪ��yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeToString(Date date,String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	/**
	 * �������ַ�ת����ָ����ʽ�����ڶ���
	 * @param dateStr �����ַ�
	 * @param formatStr ���ڸ�ʽ
	 * @return Date���͵�����
	 * @throws Exception 
	 */
	public static Date getStringToDateTime(String dateStr,String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		
		date = format.parse(dateStr);
		return date;
	}

	/**
	 * �������ַ�ת����ָ����ʽ�����ڶ�������쳣�򷵻�null
	 * @param dateStr �����ַ�
	 * @param formatStr ���ڸ�ʽ
	 * @return Date���͵�����
	 * @throws Exception 
	 */
	public static Date getStringToDateTimeExceptionNull(String dateStr,String formatStr){
		Date date = null;
		
		if(StringUtils.isBlank(dateStr)){
			log.warn("�����������ַ�Ϊ��");
			return date;
		}
		
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			log.error("���ڸ�ʽ����"+dateStr,e);
			return null ;
		}
		return date;
	}

	/**
	 * У���������ʽ�Ƿ�һ��
	 * @param dttm
	 * @param format
	 * @return
	 */
	public static boolean isDate(String dttm, String format) {
	    if (dttm == null || dttm.isEmpty() || format == null || format.isEmpty()) {
	        return false;
	    }

	    if (format.replaceAll("'.+?'", "").indexOf("y") < 0) {
	        format += "/yyyy";
	        DateFormat formatter = new SimpleDateFormat("/yyyy");
	        dttm += formatter.format(new Date());
	    }

	    DateFormat formatter = new SimpleDateFormat(format);
	    formatter.setLenient(false);
	    ParsePosition pos = new ParsePosition(0);
	    Date date = formatter.parse(dttm, pos);

	    if (date == null || pos.getErrorIndex() > 0) {
	        return false;
	    }
	    if (pos.getIndex() != dttm.length()) {
	        return false;
	    }

	    if (formatter.getCalendar().get(Calendar.YEAR) > 9999) {
	        return false;
	    }

	    return true;
	}
	

	/**
	 * ��õ�ǰʱ���i���Ӻ󣨻�ǰ���ø����ʾ����ʱ��
	 * @param i
	 * @return
	 */
	public static String addMM(int i){
		Date currTime = addMM(getNowDate(),i);
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT_YYYYMMDDHHMMSS);
		return format.format(currTime);
	}
	
	/**
	 * ���ĳ��ʱ���i���Ӻ󣨻�ǰ���ø����ʾ����ʱ��
	 * @param i
	 * @return
	 */
	public static String dateAddMM(Date date, int i){
		Date currTime = addMM(date,i);
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT_YYYYMMDDHHMMSS);
		return format.format(currTime);
	}
	


	/**
	 * @param date ��ȡ�����ڵ����ʱ�� XX-XX-XX 00:00:00
	 * @return date
	 */
	public static Date getBegin(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return  calendar.getTime();
	}
	
	/**
	 * @param date ��ȡ�����ڵĽ���ʱ�� XX-XX-XX 23:59:59
	 * @return date
	 */
	public static Date getEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return  calendar.getTime();
	}

	public static boolean isBeforeToday( Date theDay ){
		Calendar cNow = Calendar.getInstance(); 
		int iYear = cNow.get(Calendar.YEAR);
		int iDay = iYear*1000+cNow.get( Calendar.DAY_OF_YEAR ) ;
		
		Calendar cDay = Calendar.getInstance(); 
		cDay.setTime(theDay); 
		int iTheYear = cDay.get(Calendar.YEAR);
		int iTheDay = iTheYear*1000+cDay.get( Calendar.DAY_OF_YEAR ) ;
	
		return iTheDay < iDay ; 
	}
	
	public static boolean isToday( Date theDay ){
		Calendar cNow = Calendar.getInstance(); 
		int iYear = cNow.get(Calendar.YEAR);
		int iDay = cNow.get( Calendar.DAY_OF_YEAR ) ;
		
		Calendar cDay = Calendar.getInstance(); 
		cDay.setTime(theDay); 
		int iTheYear = cDay.get(Calendar.YEAR);
		int iTheDay = cDay.get( Calendar.DAY_OF_YEAR ) ;
	
		return (iTheYear == iYear) && (iTheDay == iDay) ; 
	}
	
	public static boolean isToday240000( Date theDay ){
		Date tomorrow = getSpecifiedDayAfter( getNowDate(),1) ;
		
		String strDate = getDateTimeToString(tomorrow,DATE_FORMAT_YYYYMMDD);
		strDate+="000000" ;
		
		Date target = getStringToDateTimeExceptionNull( strDate,DATETIME_FORMAT_YYYYMMDDHHMMSS );
		
		return compareTwoDate(theDay,target) == 0;
	}
	
	/**
	 * ������������֮�����������. ���㷽ʽ��second - first
	 * <p> Create Date: 2015��1��22�� </p>
	 * @param smdate	��С��ʱ�� 
	 * @param bdate		�ϴ��ʱ�� 
	 * @return ��������
	 */
	public static int daysBetween( Date smdate,Date bdate ){
		int result  = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		try{
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	        result = Integer.parseInt(String.valueOf(between_days));  
		}
		catch(ParseException e){
			log.error("����ת���쳣",e);
		}
		catch(Exception e){
			log.error("��ʽת���쳣",e);
		}
            
       return   result  ;   
	}
	
	/**
	 * ������������֮�����������. ���㷽ʽ��second - first
	 * @param smdate	��С��ʱ�� 
	 * @param bdate		�ϴ��ʱ�� 
	 * @return ��������
	 */
	public static int secondsBetween( Date smdate, Date bdate ){
		int result  = 0;
		try{
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_min=(time2-time1)/1000;  
	        result = Integer.parseInt(String.valueOf(between_min));  
		}
		catch(Exception e){
			log.error("��ʽת���쳣",e);
		}
            
       return   result  ;
	}
	
	/**
	 * ��ʱ���ת��Ϊdate
	 * <p> Create Date: 2015��3��30�� </p>
	 * @param seconds
	 * @return
	 */
	public static Date getTimestampToDate(long seconds ){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTimeInMillis( seconds );
		
		return calendar.getTime()  ;
	}
}
