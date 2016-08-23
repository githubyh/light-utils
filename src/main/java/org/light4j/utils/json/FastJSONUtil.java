package org.light4j.utils.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * ��װfastJson������
 * 
 * @author longjiazuo
 */
public class FastJSONUtil {

	/**
	 * ��JSON�ı�parseΪJSONObject����JSONArray
	 */
	public static Object parse(String text) {
		return JSON.parse(text);
	}

	/**
	 * ��JSON�ı�parse��JSONObject
	 */
	public static JSONObject parseObject(String text) {
		return JSONObject.parseObject(text);
	}

	/**
	 * ��JSON�ı�parseΪJavaBean
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * ��JSON�ı�parse��JSONArray
	 */
	public static JSONArray parseArray(String text) {
		return JSON.parseArray(text);
	}

	/**
	 * ��JSON�ı�parse��JavaBean����
	 * 
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * ��JavaBean���л�ΪJSON�ı�
	 * 
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * ��JavaBean���л�ΪJSON�ı�
	 * 
	 */
	public static String toJSONString(Object object, String serializerFeature) {
		return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
	}

	/**
	 * ��JavaBean���л�Ϊ����ʽ��JSON�ı�
	 * 
	 */
	public static String toJSONString(Object object, boolean prettyFormat) {
		return JSON.toJSONString(object, prettyFormat);
	}

	/**
	 * ��JavaBeanת��ΪJSONObject����JSONArray
	 * 
	 */
	public static Object toJSON(Object javaObject) {
		return JSON.toJSON(javaObject);
	}
}
