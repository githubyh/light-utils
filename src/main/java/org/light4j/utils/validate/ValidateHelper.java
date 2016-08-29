package org.light4j.utils.validate;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *  �ж϶����ַ����������Ƿ�Ϊ�ա���Ϊ��
 *  
 * @author longjiazuo
 */
public final class ValidateHelper {
	
	/**
	 *  �ж������Ƿ�Ϊ��
	 * @param array
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	private static <T> boolean isEmptyArray(T[] array){
		if (array == null || array.length == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �ж������Ƿ�Ϊ��
	 * @param array
	 * @return boolean
	 */
	public static <T> boolean isNotEmptyArray(T[] array){
		if (array != null && array.length > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �ж��ַ����Ƿ�Ϊ��
	 * @param string
	 * @return boolean
	 */
	public static boolean isEmptyString(String string){
		if (string == null || string.length() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �ж��ַ����Ƿ�Ϊ��
	 * @param string
	 * @return boolean
	 */
	public static boolean isNotEmptyString(String string){
		if (string != null && string.length() > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �жϼ����Ƿ�Ϊ��
	 * @param collection
	 * @return boolean
	 */
	public static boolean isEmptyCollection(Collection<?> collection){
		if (collection == null || collection.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �жϼ����Ƿ�Ϊ��
	 * @param collection
	 * @return boolean
	 */
	public static boolean isNotEmptyCollection(Collection<?> collection){
		if (collection != null && !collection.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �ж�map�����Ƿ�Ϊ��
	 * @param map
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmptyMap(Map map){
		if (map != null && !map.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  �ж�map�����Ƿ�Ϊ��
	 * @param map
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyMap(Map map){
		if (map == null || map.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * ��������Ƿ�Ϊ��,String ��ֻ�пո��ڶ�����Ҳ���.
	 * @param object
	 * @return Ϊ�շ���true,����false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object) {
		if (null == object)
			return true;
		else if (object instanceof String)
			return "".equals(object.toString().trim());
		else if (object instanceof Iterable)
			return !((Iterable) object).iterator().hasNext();
		else if (object.getClass().isArray())
			return Array.getLength(object) == 0;
		else if (object instanceof Map)
			return ((Map) object).size() == 0;
		else if (Number.class.isAssignableFrom(object.getClass()))
			return false;
		else if (Date.class.isAssignableFrom(object.getClass()))
			return false;
		else
			return false;
	}
}
