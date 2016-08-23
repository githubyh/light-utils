package org.light4j.utils.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Timestamp������
 * 
 * @author longjiazuo
 */
public class TimestampUtils {
	/**
     * ��String ת��Ϊ timestamp<br>
     * ע��value�������磺 yyyy-mm-dd hh:mm:ss[.f...] �����ĸ�ʽ�������ű�ʾ��ѡ�����򱨴����� 
     * 
     * @param value
     * @param format
     * @return
     */
    public static Timestamp string2Timestamp(String value){
    	Timestamp ts = new Timestamp(System.currentTimeMillis());  
    	ts = Timestamp.valueOf(value);
    	return ts;
    }
    
    /**
     * ��timeStamp ת��ΪString���ͣ�formatΪnull��ʹ��Ĭ�ϸ�ʽ yyyy-MM-dd HH:mm:ss
     * 
     * @param value
     * @param format
     * @return
     */
    public static String timeStamp2String(Timestamp value,String format){
    	if(null == value){
    		return "";
    	}
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	
    	return sdf.format(value);
    }
}
