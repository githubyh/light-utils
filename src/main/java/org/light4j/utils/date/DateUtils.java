package org.light4j.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ʱ�䴦������
 * 
 * @author longjiazuo
 */
public class DateUtils {
	private static final String[] weeks = {"������","����һ","���ڶ�","������","������","������","������"};
	/**
	 * ����ָ����ʽ��ȡ��ǰʱ��
	 * @param format
	 * @return String
	 */
	public static String getCurrentTime(String format){
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * ��ȡ��ǰʱ�䣬��ʽΪ��yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getCurrentTime(){
		return getCurrentTime(DateFormatUtils.DATE_FORMAT2);
	}
	
	/**
	 * ��ȡָ����ʽ�ĵ�ǰʱ�䣺Ϊ��ʱ��ʽΪyyyy-mm-dd HH:mm:ss
	 * @param format
	 * @return Date
	 */
	public static Date getCurrentDate(String format){
		 SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		 String dateS = getCurrentTime(format);
		 Date date = null;
		 try {
			date = sdf.parse(dateS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * ��ȡ��ǰʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss
	 * @return Date
	 */
	public static Date getCurrentDate(){
		return getCurrentDate(DateFormatUtils.DATE_FORMAT2);
	}
	
	/**
	 * ��ָ�����ڼ�����ݣ�Ϊ��ʱĬ�ϵ�ǰʱ��
	 * @param year ���  ������ӡ��������
	 * @param date Ϊ��ʱ��Ĭ��Ϊ��ǰʱ��
	 * @param format Ĭ�ϸ�ʽΪ��yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String addYearToDate(int year,Date date,String format){
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calender.add(Calendar.YEAR, year);
		
		return sdf.format(calender.getTime());
	}
	
	/**
	 * ��ָ�����ڼ�����ݣ�Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param year ���  ������ӡ��������
	 * @param date Ϊ��ʱ��Ĭ��Ϊ��ǰʱ��
	 * @param format Ĭ�ϸ�ʽΪ��yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String addYearToDate(int year,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addYearToDate(year, newDate, format);
	}
	
	/**
	 * ��ָ�����������·� Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param month  �����·�  ������ӡ��������
	 * @param date ָ��ʱ��
	 * @param format ָ����ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMothToDate(int month,Date date,String format) {
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calender.add(Calendar.MONTH, month);
		
		return sdf.format(calender.getTime());
	}
	
	/**
	 * ��ָ�����������·� Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param month  �����·�  ������ӡ��������
	 * @param date ָ��ʱ��
	 * @param format ָ����ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMothToDate(int month,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMothToDate(month, newDate, format);
	}
	
	/**
	 * ��ָ����������������Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param day �������� ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addDayToDate(int day,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.DATE, day);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ��ָ����������������Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param day �������� ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addDayToDate(int day,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addDayToDate(day, newDate, format);
	}
	
	/**
	 * ��ָ����������Сʱ��Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param hour ����Сʱ  ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addHourToDate(int hour,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.HOUR, hour);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ��ָ����������Сʱ��Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param hour ����Сʱ  ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addHourToDate(int hour,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addHourToDate(hour, newDate, format);
	}
	
	/**
	 * ��ָ�����������ӷ��ӣ�Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param minute ���ӷ���  ������ӡ��������
	 * @param date ָ������ 
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMinuteToDate(int minute,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.MINUTE, minute);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ��ָ�����������ӷ��ӣ�Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param minute ���ӷ���  ������ӡ��������
	 * @param date ָ������ 
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMinuteToDate(int minute,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMinuteToDate(minute, newDate, format);
	}
	
	/**
	 * ��ָ�����������룬Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param second ������ ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addSecondToDate(int second,Date date,String format){
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.SECOND, second);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ��ָ�����������룬Ϊ��ʱĬ�ϵ�ǰʱ��
	 *
	 * @param second ������ ������ӡ��������
	 * @param date ָ������
	 * @param format ���ڸ�ʽ Ϊ��Ĭ�� yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addSecondToDate(int second,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addSecondToDate(second, newDate, format);
	}
	
	/**
	 * ��ȡָ����ʽָ��ʱ�������
	 *
	 * @param date ʱ�� 
	 * @param format ��ʽ
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date,String format){
		if(date == null){
			date = getCurrentDate(format);
		}
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		
		return calender;
	}
	
	/**
	 * �ַ���ת��Ϊ���ڣ����ڸ�ʽΪ
	 * 
	 * @param value
	 * @return
	 */
	public static Date string2Date(String value){
		if(value == null || "".equals(value)){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT2);
		Date date = null;
		
		try {
			value = DateFormatUtils.formatDate(value, DateFormatUtils.DATE_FORMAT2);
			date = sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * ���ַ���(��ʽ���Ϲ淶)ת����Date
	 *
	 * @param value ��Ҫת�����ַ���
	 * @param format ���ڸ�ʽ 
	 * @return Date
	 */
	public static Date string2Date(String value,String format){
		if(value == null || "".equals(value)){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		Date date = null;
		
		try {
			value = DateFormatUtils.formatDate(value, format);
			date = sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * �����ڸ�ʽת����String
	 *
	 * @param value ��Ҫת��������
	 * @param format ���ڸ�ʽ
	 * @return String
	 */
	public static String date2String(Date value,String format){
		if(value == null){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		return sdf.format(value);
	}
	
	/**
	 * ����ת��Ϊ�ַ���
	 * 
	 * @param value
	 * @return
	 */
	public static String date2String(Date value){
		if(value == null){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT2);
		return sdf.format(value);
	}
	
	/**
	 * ��ȡָ�����ڵ����
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentYear(Date value){
		String date = date2String(value, DateFormatUtils.DATE_YEAR);
		return Integer.valueOf(date);
	}
	
	/**
	 * ��ȡָ�����ڵ����
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentYear(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_YEAR);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_YEAR);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * ��ȡָ�����ڵ��·�
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentMonth(Date value){
		String date = date2String(value, DateFormatUtils.DATE_MONTH);
		return Integer.valueOf(date);
	}
	
	/**
	 * ��ȡָ�����ڵ��·�
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentMonth(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_MONTH);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MONTH);
		
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * ��ȡָ�����ڵ����
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentDay(Date value){
		String date = date2String(value, DateFormatUtils.DATE_DAY);
		return Integer.valueOf(date);
	}
	
	/**
	 * ��ȡָ�����ڵ����
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentDay(String value){
		Date date = string2Date(value, DateFormatUtils.DATE_DAY);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_DAY);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * ��ȡ��ǰ����Ϊ���ڼ�
	 *
	 * @param value ����
	 * @return String
	 */
	public static String getCurrentWeek(Date value) {
		Calendar calendar = getCalendar(value, DateFormatUtils.DATE_FORMAT1);
		int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1 < 0 ? 0 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		return weeks[weekIndex];
	}
	
	/**
	 * ��ȡ��ǰ����Ϊ���ڼ�
	 *
	 * @param value ����
	 * @return String
	 */
	public static String getCurrentWeek(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_FORMAT1);
		return getCurrentWeek(date);
	}
	
	/**
	 * ��ȡָ�����ڵ�Сʱ
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentHour(Date value){
		String date = date2String(value, DateFormatUtils.DATE_HOUR);
		return Integer.valueOf(date);
	}
	
	/**
	 * ��ȡָ�����ڵ�Сʱ
	 *
	 * @param value ����
	 * @return
	 * @return int
	 */
	public static int getCurrentHour(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_HOUR);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_HOUR);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * ��ȡָ�����ڵķ���
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentMinute(Date value){
		String date = date2String(value, DateFormatUtils.DATE_MINUTE);
		return Integer.valueOf(date);
	}
	
	/**
	 * ��ȡָ�����ڵķ���
	 *
	 * @param value ����
	 * @return int
	 */
	public static int getCurrentMinute(String value){
		Date date = string2Date(value, DateFormatUtils.DATE_MINUTE);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MINUTE);
		
		return calendar.get(Calendar.MINUTE);
	}
	
	/**  
	 * �Ƚ������������������(�¡���) <br>
	 * ����<br>
	 * &nbsp;compareDate("2009-09-12", null, 0);//�Ƚ��� <br>
     * &nbsp;compareDate("2009-09-12", null, 1);//�Ƚ��� <br> 
     * &nbsp;compareDate("2009-09-12", null, 2);//�Ƚ��� <br>
     * @param startDay ��Ҫ�Ƚϵ�ʱ�� ����Ϊ��(null),��Ҫ��ȷ�����ڸ�ʽ ,�磺2009-09-12   
     * @param endDay ���Ƚϵ�ʱ��  Ϊ��(null)��Ϊ��ǰʱ��    
     * @param stype ����ֵ����   0Ϊ�����죬1Ϊ���ٸ��£�2Ϊ������    
     * @return int
     */    
    public static int compareDate(String startDay,String endDay,int stype) {     
        int n = 0;     
        startDay = DateFormatUtils.formatDate(startDay, "yyyy-MM-dd");
        endDay = DateFormatUtils.formatDate(endDay, "yyyy-MM-dd");
        
        String formatStyle = "yyyy-MM-dd";
        if(1 == stype){
        	formatStyle = "yyyy-MM";
        }else if(2 == stype){
        	formatStyle = "yyyy";
        }   
             
        endDay = endDay==null ? getCurrentTime("yyyy-MM-dd") : endDay;     
             
        DateFormat df = new SimpleDateFormat(formatStyle);     
        Calendar c1 = Calendar.getInstance();     
        Calendar c2 = Calendar.getInstance();     
        try {     
            c1.setTime(df.parse(startDay));     
            c2.setTime(df.parse(endDay));   
        } catch (Exception e) {    
        	e.printStackTrace();
        }     
        while (!c1.after(c2)) {                   // ѭ���Աȣ�ֱ����ȣ�n ������Ҫ�Ľ��     
            n++;     
            if(stype==1){     
                c1.add(Calendar.MONTH, 1);          // �Ƚ��·ݣ��·�+1     
            }     
            else{     
                c1.add(Calendar.DATE, 1);           // �Ƚ�����������+1     
            }     
        }     
        n = n-1;     
        if(stype==2){     
            n = (int)n/365;     
        }        
        return n;     
    }   
    
    /**
     * �Ƚ�����ʱ��������Сʱ(���ӡ���)
     *
     * @param startTime ��Ҫ�Ƚϵ�ʱ�� ����Ϊ�գ��ұ��������ȷ��ʽ��2012-12-12 12:12:
     * @param endTime ��Ҫ���Ƚϵ�ʱ�� ��Ϊ����Ĭ�ϵ�ǰʱ��
     * @param type 1��Сʱ   2������   3����
     * @return int
     */
    public static int compareTime(String startTime , String endTime , int type) {
    	//endTime�Ƿ�Ϊ�գ�Ϊ��Ĭ�ϵ�ǰʱ��
    	if(endTime == null || "".equals(endTime)){
    		endTime = getCurrentTime();
    	}
    	
    	SimpleDateFormat sdf = DateFormatUtils.getFormat("");
    	int value = 0;
    	try {
			Date begin = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000;  //����1000ת���ɺ���
			if(type == 1){   //Сʱ
				value = (int) (between % (24 * 36000) / 3600);
			}
			else if(type == 2){
				value = (int) (between % 3600 / 60);
			}
			else if(type == 3){
				value = (int) (between % 60 / 60);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return value;
    }
    
    /**
     * �Ƚ��������ڵĴ�С��<br>
     * ��date1 > date2 �򷵻� 1<br>
     * ��date1 = date2 �򷵻� 0<br>
     * ��date1 < date2 �򷵻�-1
     * @param date1  
     * @param date2
     * @param format  ��ת���ĸ�ʽ
     * @return �ȽϽ��
     */
    public static int compare(String date1, String date2,String format) {
        DateFormat df = DateFormatUtils.getFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    /**
     * ��ȡָ���·ݵĵ�һ�� 
     * 
     * @param date
     * @return
     */
    public static String getMonthFirstDate(String date){
    	date = DateFormatUtils.formatDate(date);
		return DateFormatUtils.formatDate(date, "yyyy-MM") + "-01";
    }
    
    /**
     * ��ȡָ���·ݵ����һ��
     * 
     * @param strdate
     * @return
     */
	public static String getMonthLastDate(String date) {
		Date strDate = DateUtils.string2Date(getMonthFirstDate(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return DateFormatUtils.formatDate(calendar.getTime());
	}
	
	/**
	 * ��ȡ�������ڵĵ�һ��
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getWeekFirstDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // ����һ
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}
	
	/**
	 * ��ȡ�������ڵ����һ��
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date geWeektLastDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // ����һ
		int last_day_of_week = first_day_of_week + 6; // ������
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}
}
