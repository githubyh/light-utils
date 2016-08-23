package org.light4j.utils.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ڸ�ʽ��������
 * 
 * @author longjiazuo
 */
public class DateFormatUtils {
	/** yyyy:�� */
	public static final String DATE_YEAR = "yyyy";
	
	/** MM���� */
	public static final String DATE_MONTH = "MM";
	
	/** DD���� */
	public static final String DATE_DAY = "dd";
	
	/** HH��ʱ */
	public static final String DATE_HOUR = "HH";
	
	/** mm���� */
	public static final String DATE_MINUTE = "mm";
	
	/** ss���� */
	public static final String DATE_SECONDES = "ss";
	
	/** yyyy-MM-dd */
	public static final String DATE_FORMAT1 = "yyyy-MM-dd";
		
	/** yyyy-MM-dd hh:mm:ss */
	public static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";

	/** yyyy-MM-dd hh:mm:ss|SSS */
	public static final String TIME_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss|SSS";
	
	/** yyyyMMdd */
	public static final String DATE_NOFUll_FORMAT = "yyyyMMdd";
	
	/** yyyyMMddhhmmss */
	public static final String TIME_NOFUll_FORMAT = "yyyyMMddHHmmss";
	
	/**
	 * 
	 * ��ʽת��<br>
	 * yyyy-MM-dd hh:mm:ss �� yyyyMMddhhmmss �໥ת��<br>
	 * yyyy-mm-dd ��yyyymmss �໥ת��
	 * @author chenssy
	 * @date Dec 26, 2013
	 * @param value 
	 * 				����
	 * @return String
	 */
	public static String formatString(String value) {
		String sReturn = "";
		if (value == null || "".equals(value))
			return sReturn;
		if (value.length() == 14) {   //����Ϊ14��ʽת����yyyy-mm-dd hh:mm:ss
			sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " "
					+ value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
			return sReturn;
		}
		if (value.length() == 19) {   //����Ϊ19��ʽת����yyyymmddhhmmss
			sReturn = value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10) + value.substring(11, 13)
					+ value.substring(14, 16) + value.substring(17, 19);
			return sReturn;
		}
		if(value.length() == 10){     //����Ϊ10��ʽת����yyyymmhh
			sReturn = value.substring(0, 4) + value.substring(5,7) + value.substring(8,10);
		}
		if(value.length() == 8){      //����Ϊ8��ʽת����yyyy-mm-dd
			sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
		}
		return sReturn;
	}
	
	public static String formatDate(String date, String format) {
		if (date == null || "".equals(date)){
			return "";
		}
		Date dt = null;
		SimpleDateFormat inFmt = null;
		SimpleDateFormat outFmt = null;
		ParsePosition pos = new ParsePosition(0);
		date = date.replace("-", "").replace(":", "");
		if ((date == null) || ("".equals(date.trim())))
			return "";
		try {
			if (Long.parseLong(date) == 0L)
				return "";
		} catch (Exception nume) {
			return date;
		}
		try {
			switch (date.trim().length()) {
			case 14:
				inFmt = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 12:
				inFmt = new SimpleDateFormat("yyyyMMddHHmm");
				break;
			case 10:
				inFmt = new SimpleDateFormat("yyyyMMddHH");
				break;
			case 8:
				inFmt = new SimpleDateFormat("yyyyMMdd");
				break;
			case 6:
				inFmt = new SimpleDateFormat("yyyyMM");
				break;
			case 7:
			case 9:
			case 11:
			case 13:
			default:
				return date;
			}
			if ((dt = inFmt.parse(date, pos)) == null)
				return date;
			if ((format == null) || ("".equals(format.trim()))) {
				outFmt = new SimpleDateFormat("yyyy��MM��dd��");
			} else {
				outFmt = new SimpleDateFormat(format);
			}
			return outFmt.format(dt);
		} catch (Exception ex) {
		}
		return date;
	}

	/**
	 * ��ʽ������
	 *
	 * @author chenming
	 * @date 2016��5��31��
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		return formatDate(DateUtils.date2String(date), format);
	}
	
	/**
	 * @desc:��ʽ����ʱ�䣬����Ĭ�ϸ�ʽ��yyyy-MM-dd HH:mm:ss��
	 * @autor:chenssy
	 * @date:2014��8��6��
	 *
	 * @param value
	 * @return
	 */
	public static String formatDate(String value){
		return getFormat(DATE_FORMAT2).format(DateUtils.string2Date(value, DATE_FORMAT2));
	}
	
	/**
	 * ��ʽ������
	 * 
	 * @author : chenssy
	 * @date : 2016��5��31�� ����5:40:58
	 *
	 * @param value
	 * @return
	 */
	public static String formatDate(Date value){
		return formatDate(DateUtils.date2String(value));
	}
	
	/**
	 * ��ȡ������ʾ��ʽ��Ϊ��Ĭ��Ϊyyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @date Dec 30, 2013
	 * @param format
	 * @return
	 * @return SimpleDateFormat
	 */
	protected static SimpleDateFormat getFormat(String format){
		if(format == null || "".equals(format)){
			format = DATE_FORMAT2;
		}
		return new SimpleDateFormat(format);
	}
}
