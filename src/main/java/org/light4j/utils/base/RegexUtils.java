package org.light4j.utils.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������ʽ�����࣬��֤�����Ƿ���Ϲ淶
 * 
 * @author longjiazuo
 */
public class RegexUtils {
	
	/**
	 * �ж��ַ����Ƿ����������ʽ
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean find(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.find();
		return b;
	}
	
	/**
	 * �ж�������ַ����Ƿ����Email��ʽ.
	 *
	 * @param email
	 * 				������ַ���
	 * @return ����Email��ʽ����true�����򷵻�false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}
	
	/**
	 * �ж�������ַ����Ƿ�Ϊ������
	 *
	 * @param value
	 * 				������ַ���
	 * @return
	 */
	public static boolean isChinese(String value) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * �ж��Ƿ�Ϊ������������double��float
	 *
	 * @param value
	 * 			������ַ���
	 * @return
	 */
	public static boolean isDouble(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * �ж��Ƿ�Ϊ����
	 * 
	 * @param value
	 * 			������ַ���
	 * @return
	 */
	public static boolean isInteger(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(value).matches();
	}
}
