package org.light4j.utils.convert;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * ת��������<br>
 * ����ת��ֵΪnull���߳����쳣����ʹ��Ĭ��ֵ
 * 
 * @author longjiazuo
 */
public class ConvertUtils {
	
	/**
	 * �ַ���ת��Ϊint
	 *
	 * @param str	
	 * 					��ת�����ַ���
	 * @param defaultValue
	 * 					Ĭ��ֵ
	 * @return
	 */
	public static int strToInt(String str, int defaultValue) {
		try {
			defaultValue = Integer.parseInt(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * Stringת��Ϊlong
	 *
	 * @param str
	 * 					��ת���ַ���
	 * @param defaultValue
	 * 					Ĭ��ֵ
	 * @return
	 */
	public static long strToLong(String str, long defaultValue) {
		try {
			defaultValue = Long.parseLong(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}
	
	/**
	 * �ַ���ת��Ϊfloat
	 *
	 * @param str
	 * 				
	 * @param defaultValue
	 * @return
	 */
	public static float strToFloat(String str, float defaultValue) {
		try {
			defaultValue = Float.parseFloat(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * Stringת��ΪDouble
	 *
	 * @param str
	 * 					��ת���ַ���
	 * @param defaultValue
	 * 					Ĭ��ֵ
	 * @return
	 */
	public static double strToDouble(String str, double defaultValue) {
		try {
			defaultValue = Double.parseDouble(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * �ַ���ת������
	 *
	 * @param str
	 * 						��ת�����ַ���
	 * @param defaultValue
	 * 						Ĭ������
	 * @return
	 */
	public static java.util.Date strToDate(String str,java.util.Date defaultValue) {
		return strToDate(str, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	/**
	 * �ַ���ת��Ϊָ����ʽ������
	 *
	 * @param str
	 * 					��ת�����ַ���
	 * @param format
	 * 					���ڸ�ʽ
	 * @param defaultValue
	 * 					Ĭ������
	 * @return
	 */
	public static java.util.Date strToDate(String str, String format,java.util.Date defaultValue) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		try {
			defaultValue = fmt.parse(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * ����ת��Ϊ�ַ���
	 *
	 * @param date
	 * 				��ת��������
	 * @param defaultValue
	 * 				Ĭ���ַ���
	 * @return
	 */
	public static String dateToStr(java.util.Date date, String defaultValue) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	/**
	 * ����ת��Ϊָ����ʽ���ַ���
	 * 
	 * @param date
	 * 				��ת��������
	 * @param format
	 * 				ָ����ʽ
	 * @param defaultValue
	 * 				Ĭ��ֵ
	 * @return
	 */
	public static String dateToStr(java.util.Date date, String format, String defaultValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			defaultValue = sdf.format(date);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * ����ַ���Ϊ����ʹ��Ĭ���ַ���
	 *
	 * @param str
	 * 				�ַ���
	 * @param defaultValue
	 * 				Ĭ��ֵ
	 * @return
	 */
	public static String strToStr(String str, String defaultValue) {
		if ((str != null) && (!(str.isEmpty())))
			defaultValue = str;
		return defaultValue;
	}

	/**
	 * util date ת��Ϊ sqldate
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date dateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * sql date ת��Ϊ util date
	 *
	 * @param date
	 * @return
	 */
	public static java.util.Date sqlDateToDate(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}

	/**
	 * date ת��Ϊ timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp dateToSqlTimestamp(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * timestamp ת��Ϊdate
	 *
	 * @param date
	 * @return
	 */
	public static java.util.Date qlTimestampToDate(Timestamp date) {
		return new java.util.Date(date.getTime());
	}
}
