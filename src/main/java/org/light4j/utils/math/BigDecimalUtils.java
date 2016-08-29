package org.light4j.utils.math;

import java.math.BigDecimal;

import org.light4j.utils.base.ValidateHelper;

/**
 *  �ṩ��ȷ�ļӼ��˳�����
 *  
 * @author longjiazuo
 */
public class BigDecimalUtils {
	
	/**
	 * Ĭ�ϱ���λ��2
	 */
	private static int 	DEFAULT_SCALE = 2;
	
	/**
	 * Ĭ�������������Ϊ����������
	 */
	private static int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;
	
	/**
	 * 
	 * �ӷ�����
	 *
	 * @param v1	����
	 * @param v2	������
	 * @return
	 */
	public static String add(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.add(b2).toString();
    }    
	
	/**
	 * ��������<br>
	 * �����������������ʱ����scale����ָ�����ȣ��Ժ�������������롣
	 *
	 * @param v1
	 * 			����
	 * @param v2
	 * 			������
	 * @param scale
	 * 			��ȷ����
	 * @return
	 */
	public static String div(String v1, String v2, int scale, int round) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		if (ValidateHelper.isEmpty(scale)) {
			scale = DEFAULT_SCALE;
		}

		if (ValidateHelper.isEmpty(round)) {
			round = DEFAULT_ROUND;
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * �Ƚ�������<br>
	 * v1 > v2 return 1<br>
	 * v1 = v2 return 0<br>
	 * v1 < v2 return -1
	 *
	 * @param v1
	 * 			�Ƚ���
	 * @param v2
	 * 			���Ƚ���
	 * @return
	 */
	public static int compareTo(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.compareTo(b2);    
    }   
	
	/**
	 * ���ؽ�С��
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMin(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.min(b2).toString();    
    }   
	
	/**
	 * ���ؽϴ���
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMax(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.max(b2).toString();    
    }   
	
	/**
	 * ����BigDecimal���ݣ�����scaleλС��
	 *
	 * @param value
	 * @param scale
	 * @return
	 */
	public static BigDecimal getValue(BigDecimal value,int scale){
		if(!ValidateHelper.isEmpty(value)){
			return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return value;
	}
	
	/**
	 * ��objectת��ΪBigdecimal
	 * 
	 * @param value
	 * 				��ת������ֵ
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object value){
		BigDecimal resultValue = new BigDecimal(0);
		if(value instanceof String){
			resultValue =  new BigDecimal((String)value);
		}
		else if(value instanceof Integer){
			resultValue =  new BigDecimal((Integer)value);
		}
		else if(value instanceof Long){
			resultValue =  new BigDecimal((Long)value);
		}
		else if(value instanceof Double){
			resultValue =  new BigDecimal((Double)value);
		}
		else{
			resultValue = (BigDecimal) value;
		}
		
		return resultValue;
	}
	
	
	/**
	 * ��objectת��ΪBigdecimal,��objectΪ�գ��򷵻�resultValue
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object value,BigDecimal resultValue){
		if(ValidateHelper.isEmpty(value)){
			return resultValue;
		}
		
		resultValue = getBigDecimal(resultValue);
		
		return resultValue;
	}
	
	/**
	 * ��BigDecimal ת����Long
	 *
	 * @param value
	 * @return
	 */
	public static Long bigDecimalToLong(BigDecimal value){
		if(value != null){
			return new Long(value.longValue());
		}
		return null;
	}
	
	/**
	 * ��BigDecimal ת����integer
	 *
	 * @param value
	 * @return
	 */
	public static Integer bigDecimalToInteger(BigDecimal value){
		if(value != null){
			return new Integer(value.intValue());
		}
		return null;
	}
}
