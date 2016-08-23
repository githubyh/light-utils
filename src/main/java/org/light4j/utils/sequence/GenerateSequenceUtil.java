package org.light4j.utils.sequence;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ����ʱ������Ψһ����ID<br>
 * ʱ�侫ȷ���룬ID���ֵΪ99999��ѭ��ʹ��
 * 
 * @author longjiazuo
 */
public class GenerateSequenceUtil {
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	
	/** ʱ�䣺��ȷ���� */
	private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
	
	private final static NumberFormat numberFormat = new DecimalFormat("00000");
	
	private static int seq = 0;
	 
    private static final int MAX = 99999;
	
	public static synchronized String generateSequenceNo() {
		 
        Calendar rightNow = Calendar.getInstance();
       
        StringBuffer sb = new StringBuffer();
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
 
        numberFormat.format(seq, sb, HELPER_POSITION);
 
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
 
        return sb.toString();
    }
}
