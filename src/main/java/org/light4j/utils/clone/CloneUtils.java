package org.light4j.utils.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

/**
 * ��¡�����࣬�������¡,�������󡢼���
 * 
 * @Author:longjiazuo
 */
public class CloneUtils {

	/**
	 * ���ö�������л���ɶ�������¡
	 *
	 * @param obj
	 * 			����¡�Ķ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneObject(T obj) {
		T cloneObj = null;
		try {
			// д���ֽ���
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream obs = new ObjectOutputStream(out);
			obs.writeObject(obj);
			obs.close();

			// �����ڴ棬д��ԭʼ���������¶���
			ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(ios);
			// �������ɵ��¶���
			cloneObj = (T) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}
	
	/**
	 * �������л���ɼ��ϵ����¡
	 * @param collection
	 * 					����¡�ļ���
	 * @return
	 * @throws ClassNotFoundException
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> cloneCollection(Collection<T> collection) throws ClassNotFoundException, IOException{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(collection);
	    out.close();
	  
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    Collection<T> dest = (Collection<T>) in.readObject();  
	    in.close();
	    
	    return dest;  
	}
}
