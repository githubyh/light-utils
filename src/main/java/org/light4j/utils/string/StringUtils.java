package org.light4j.utils.string;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ַ��������࣬���ַ������г���Ĵ���
 * 
 * @author longjiazuo
 */
public class StringUtils {
	
	/**
	 * ����ַ����Ƿ�Ϊ��
	 * @param str �ַ���
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ����ַ����Ƿ�Ϊ�ǿ�
	 * @param str �ַ���
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	 /**
	  * ���ַ������ָ���ת��Ϊ����
	  * @param str  �ַ���
	  * @param expr �ָ���
	  * @return
	  */
	 public static String[] stringToArray(String str, String expr){
		 return str.split(expr);
	 }
	
	 /**
	  * �����鰴�ո����ķָ�ת�����ַ���
	  * @param arr
	  * @param expr
	  * @return
	  */
	 public static String arrayToString(String[] arr,String expr){
		 String strInfo = "";
		 if(arr != null && arr.length > 0){
			 StringBuffer sf = new StringBuffer();
			 for(String str : arr){
				 sf.append(str);
				 sf.append(expr);
			 }
			 strInfo = sf.substring(0, sf.length()-1);
		 }
		 return strInfo;
	 }
	 
	 
	 /**
	  * �����ϰ��ո����ķָ�ת�����ַ���
	  * @param arr
	  * @param expr
	  * @return
	  */
	 public static String listToString(List<String> list,String expr){
		 String strInfo = "";
		 if(list != null && list.size() > 0){
			 StringBuffer sf = new StringBuffer();
			 for(String str : list){
				 sf.append(str);
				 sf.append(expr);
			 }
			 strInfo = sf.substring(0, sf.length()-1);
		 }
		 return strInfo;
	 }
	 
	/**
	 * ����ǵķ���ת����ȫ�Ƿ���.(��Ӣ���ַ�ת�����ַ�)
	 *
	 * @param str
	 * 			Ҫת�����ַ�
	 * @return
	 */
	public static String changeToFull(String str) {
        String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
        String[] decode = { "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
                "'", "\"", "��", "��", "��", "��", "��", "��" };
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int pos = source.indexOf(str.charAt(i));
            if (pos != -1) {
                result += decode[pos];
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
	
	/**
	 *  ���ַ�ת��Ϊ����ΪUnicode����ʽ Ϊ'\u0020'<br>
	 * 		  unicodeEscaped(' ') = "\u0020"<br>
	 * 		  unicodeEscaped('A') = "\u0041"
	 *
	 * @param ch
	 * 			��ת����char �ַ�
	 * @return
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}
	
	/**
	 * ����toString��������Ϊ�գ�����Ĭ��ֵ
	 *
	 * @param object
	 * 				Ҫ����toString�����Ķ���
	 * @param nullStr
	 * 				���ص�Ĭ��ֵ
	 * @return
	 */
	public static String toString(Object object,String nullStr){
		return object == null ? nullStr : object.toString();
	}
	
	/**
	 * ���ַ����ظ�N�Σ�null��""����ѭ���������� <br>
	 * 		 ��value == null || value == "" return value;<br>
	 * 		 ��count <= 1 ����  value
	 *
	 * @param value
	 * 				��Ҫѭ�����ַ���
	 * @param count
	 * 				ѭ���Ĵ���
	 * @return
	 */
	public static String repeatString(String value,int count){
		if(value == null || "".equals(value) || count <= 1){
			return value;
		}
		
		int length = value.length();
		if(length == 1){          //����Ϊ1�������ַ�
			return repeatChar(value.charAt(0), count);
		}
		
		int outputLength = length * count;
		switch (length) {
		case 1:
			return repeatChar(value.charAt(0), count);
		case 2:
			char ch0 = value.charAt(0);
			char ch1 = value.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = count * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default: 
			StringBuilder buf = new StringBuilder(outputLength);
			for (int i = 0; i < count; i++) {
				buf.append(value);
			}
			return buf.toString();
		}

	}
	
	/**
	 * ��ĳ���ַ��ظ�N��
	 *
	 * @param ch
	 * 			��Ҫѭ�����ַ�
	 * @param count
	 * 			ѭ���Ĵ���
	 * @return
	 */
	public static String repeatChar(char ch, int count) {
		char[] buf = new char[count];
		for (int i = count - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * �ж��ַ����Ƿ�ȫ����ΪСд
	 * 
	 * @param value
	 * 				���жϵ��ַ���
	 * @return
	 */
	public static boolean isAllLowerCase(String value){
		if(value == null || "".equals(value)){
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isLowerCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * �ж��ַ����Ƿ�ȫ����д
	 *
	 * @param value
	 * 				���жϵ��ַ���
	 * @return
	 */
	public static boolean isAllUpperCase(String value){
		if(value == null || "".equals(value)){
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isUpperCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ��ת�ַ���
	 *
	 * @param value
	 * 				����ת���ַ���
	 * @return
	 */
	public static String reverse(String value){
		if(value == null){
			return null;
		}
		return new StringBuffer(value).reverse().toString();
	}
	
	/**
	 * @desc:��ȡ�ַ�����֧����Ӣ�Ļ��ң��������ĵ�����λ����
	 *
	 * @param resourceString
	 * @param length
	 * @return
	 */
	public static String subString(String resourceString,int length){
		String resultString = "";
		if (resourceString == null || "".equals(resourceString) || length < 1) {
			return resourceString;
		}

		if (resourceString.length() < length) {
			return resourceString;
		}

		char[] chr = resourceString.toCharArray();
		int strNum = 0;
		int strGBKNum = 0;
		boolean isHaveDot = false;

		for (int i = 0; i < resourceString.length(); i++) {
			if (chr[i] >= 0xa1){// 0xa1������Сλ��ʼ
				strNum = strNum + 2;
				strGBKNum++;
			} else {
				strNum++;
			}

			if (strNum == length || strNum == length + 1) {
				if (i + 1 < resourceString.length()) {
					isHaveDot = true;
				}
				break;
			}
		}
		resultString = resourceString.substring(0, strNum - strGBKNum);
		if (isHaveDot) {
			resultString = resultString + "...";
		}

		return resultString;
	}
	
	/**
	 * 
	 * @param htmlString
	 * @param length
	 * @return
	 */
	public static String subHTMLString(String htmlString,int length){
		return subString(delHTMLTag(htmlString), length);
	}
	
	/**
	 * ����html��ǩ������script��style��html���ո񡢻س���ǩ
	 *
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // ����script��������ʽ  
	    String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // ����style��������ʽ  
	    String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ  
	    String regEx_space = "\\s*|\t|\r|\n";//����ո�س����з� 
	    
	    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // ����script��ǩ  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // ����style��ǩ  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
        Matcher m_html = p_html.matcher(htmlStr);  
        htmlStr = m_html.replaceAll(""); // ����html��ǩ  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // ���˿ո�س���ǩ  
        
        return htmlStr.trim(); // �����ı��ַ���
	}
}
