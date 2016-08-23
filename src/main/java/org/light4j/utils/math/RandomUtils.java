package org.light4j.utils.math;

import java.util.Random;

/**
 * �����������
 * 
 * @author longjiazuo
 */
public class RandomUtils {
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String NUMBER_CHAR = "0123456789";
    
    /**
     * ��ȡ�������������������Сд������
     *
     * @param length
     * 				���������
     * @return
     */
    public static String generateString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * ��ȡ�������������������Сд��ĸ
     *
     * @param length
     * 				���������
     * @return
     */
    public static String generateMixString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * ��ȡ�������������ֻ����Сд��ĸ
     *
     * @param length	
     * 				���������
     * @return
     */
    public static String generateLowerString(int length) { 
        return generateMixString(length).toLowerCase(); 
    } 
    
    /**
     * ��ȡ�������������ֻ������д��ĸ
     *
     * @param length
     * 				���������
     * @return
     */
    public static String generateUpperString(int length) { 
        return generateMixString(length).toUpperCase(); 
    } 
    
    /**
     * ��ȡ�������������ֻ��������
     *
     * @param length
     * 				���������
     * @return
     */
    public static String generateNumberString(int length){
    	StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length()))); 
        } 
        return sb.toString(); 
    }
    
}
