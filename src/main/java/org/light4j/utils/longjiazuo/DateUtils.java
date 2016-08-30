package org.light4j.utils.longjiazuo;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * ʱ�乤����
 * 
 * @author longjiazuo
 */
public class DateUtils {

	private static DateUtils instance = null;
	public static final String DEF_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEF_DATE_NO_TIME_FORMAT = "yyyy-MM-dd";
    /**
     * 
     * Methods Descrip:����һ������ģʽ����Ʒ�ʽ,�����������Ҫ�ǵõ������ʵ������
     * 
     *  
     */
    public static synchronized DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    /**
     * ����һ�����캯��
     *  
     */
    public DateUtils() {
    }

    /**
     * 
     * Methods Descrip:��������Ҫ�ǵõ���ǰ������
     * 
     * @return
     *  
     */
    public static Timestamp getSysTimestamp() {
    	final TimeZone zone = TimeZone.getTimeZone("GMT+8"); //��ȡ�й�ʱ��
		TimeZone.setDefault(zone); //����ʱ��
        return new Timestamp((new Date()).getTime());
    }

    /**
     * 
     * Methods Descrip:��ָ����ʽ��������Ϊtimestamp����
     * 
     * @param datetime:�����String���͵������ַ���
     *            ��ʽΪ:2004-01-11
     * @param datepattern:ָ������ĸ�ʽ����
     * @return Timestamp����
     *  
     */
    public static Timestamp getstrTimestamp(String datetime, String datepattern) {
        Date bb = null;
        try {
            DateFormat parser = new SimpleDateFormat(datepattern);
            bb = parser.parse(datetime);
            return new Timestamp(bb.getTime());
        } catch (ParseException ex) {
            //TODO
        }
        return null;
    }

    /**
     * 
     * Methods Descrip:�õ����¿�ʼʱ��
     * 
     * @param strMon
     *            :�����String���͵��·��ַ��� ��ʽ:2004-03
     * @return:String���͵ı��¿�ʼʱ��
     *  
     */
    public static String getCurMonBegin(String strMon) {
        String bdate = "";
        bdate = strMon + "-01";
        return bdate;
    }

    /**
     * 
     * Methods Descrip:�õ����½���ʱ��
     * 
     * @param strMon:�����String���͵��·��ַ���
     *            ��ʽ:2004-03
     * @return :String���͵ı��½���ʱ��
     *  
     */
    public static String getCurMonEnd(String strMon) {
        return getCurMonEnd(strMon, "yyyy-MM");
    }
    public static String getCurMonEndCHN(String strMon) {
        return getCurMonEnd(strMon, "yyyy��MM��");
    }
    
    public static String getCurMonEnd(String strMon, String pattern) {
        return null;
    }

    /**
     * 
     * Methods Descrip:���ָ����������week����ʼ���ڣ��ṩ�ͷ��ص����ڸ�ʽΪyyyy-MM-dd
     * 
     * @param strDate:�ṩ������
     * @return ָ����������week����ʼ����
     *  
     */
    public static String getCurWeekBegin(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.parse(strDate, new ParsePosition(0));
        Calendar calendar = dateFormat.getCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 1 - day);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 
     * Methods Descrip:���ָ����������week����ֹ���ڣ��ṩ�ͷ��ص����ڸ�ʽΪyyyy-MM-dd
     * 
     * @param strDate:�ṩ������
     * @return:ָ����������week����ֹ����
     *  
     */
    public static String getCurWeekEnd(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.parse(strDate, new ParsePosition(0));
        Calendar calendar = dateFormat.getCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 7 - day);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 
     * Methods Descrip:���ڴ�С�ıȽ�
     * 
     * @param date:�ַ�����ʽ�Ŀ�ʼ����
     * @param edate:�ַ�����ʽ�����ڽ�������
     * @return:boolean ���date>=edate�򷵻�true,���򷵻�false
     *  
     */
    public static boolean comDate(String date, String edate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = formatter.parse(date, new ParsePosition(0));
        Date dt2 = formatter.parse(edate, new ParsePosition(0));
        return (!dt1.before(dt2));
    }
    
    /**
     * 
     * Methods Descrip:���ڴ�С�ıȽ�
     * 
     * @param date:�ַ�����ʽ�Ŀ�ʼ����
     * @param edate:�ַ�����ʽ�����ڽ�������
     * @param pattern:ָ�������ڸ�ʽ
     * @return:boolean ���date>=edate�򷵻�true,���򷵻�false
     *  
     */
    public static boolean comDate(String date, String edate,String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date dt1 = formatter.parse(date, new ParsePosition(0));
        Date dt2 = formatter.parse(edate, new ParsePosition(0));
        return (!dt1.before(dt2));
    }
    /**
     * 
     * Methods Descrip:String���͵����ڼӼ�
     * 
     * @param date:ϵͳʱ�䣻
     * @param type:�Ӽ�������
     *            D ���� M �·� Y ��
     * @param into:�Ӽ�������
     * @return:String ����ʱ��
     *  
     */
    public static String addTime(String date, String type, int into,String pattern) {
        date = date.replaceAll("-", "/");
        GregorianCalendar grc = new GregorianCalendar();
        Date d =  parseDate(date);
        if(d == null){
        	d = new Date();
        }
        grc.setTime(d);
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return new String(formatter.format(grc.getTime()));
    }
    
    public static String addTimeByPattern(String date, String type, int into,String pattern) {
        date = date.replaceAll("-", "/");
        GregorianCalendar grc = new GregorianCalendar();
        Date d =  parseDate(date, pattern);
        if(d == null){
        	d = new Date();
        }
        grc.setTime(d);
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        return new String(formatter.format(grc.getTime()));
    }

    /**
     * 
     * Methods Descrip:Timestamp�͵����ڼӼ�
     * 
     * @param date:Timestamp
     *            ����Ļ���ʱ��
     * @param type:String
     *            �Ӽ������� D ���� M �·� Y ��
     * @param into:int
     *            �Ӽ�������
     * @return:Timestamp ����ʱ��
     *  
     */
    public static Timestamp addDateTime(Timestamp date, String type, int into) {
        GregorianCalendar grc = new GregorianCalendar();
        grc.setTime(new Date(date.getTime()));
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }
        return new Timestamp(new Date(grc.getTimeInMillis())
                .getTime());
    }

    /**
     * 
     * ���ڵļӼ���No ʱ���֣���
     * 
     * @param date:ϵͳʱ��
     * @param type:String
     *            �Ӽ������� D ���� M �·� Y ��
     * @param into:int
     *            �Ӽ�������
     * @return ����ʱ��
     *  
     */
    public static String addDate(String date, String type, int into) {
    	date = date.replaceAll("-", "/");//��Ӧϵͳʱ���ʽ������ֱ�Ӵ���yyyy-MM-DD��ʽ�Ĳ���������޸�������Դ��ʱ�䣺2009-08-03
        GregorianCalendar grc = new GregorianCalendar();
        //grc.setTime(new Date(date));
        Date d =  parseDate(date);
        if(d == null){
        	d = new Date();
        }
        grc.setTime(d);
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else {
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new String(formatter.format(grc.getTime()));
    }

    /**
     * 
     * Methods Descrip:�õ����ݿ�ʱ������
     * 
     * @param date:���ݿ�õ��Ĵ�ʱ����ַ���
     * @return:String �����ַ���
     *  
     */
    public static String getDateString(String date) {
    	if(date!=null && date.length()>10)
    		date = date.substring(0, 10);
        return date;
    }
   
    /**
     *  
     * Methods Descrip:�������ݿ�ʱ���ǰ10λ�ַ�����ʾ
     * @param date:Timestamp ����
     * @return
     *
     */
    public static String getDateString(Timestamp date) {
		if (date == null)
			return null;
		String s = date.toString();
		return s.substring(0, 10);
	}
    
    /**
     *  
     * Methods Descrip:���ַ���ת��Ϊ����
     * @param exifDate:�����ַ���
     * @return Date:
     *
     */
    public static Date parseDate(String exifDate) {
		if (exifDate == null) {
			return null;
		}
		String patterns[];
		int i;
		patterns = (new String[] { "yyyy:MM:dd HH:mm:ss", "yyyy:MM:dd HH:mm",
				"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "dd.MM.yy HH:mm",
				"yyyyMMdd HHmmss","yyyyMMdd.HHmmss", "yyyyMMdd HHmm", "MM/dd/yy hh:mm a",
				"HH:mm:ss dd.MM.yyyy", "yyyy:MM:dd", "yyyy-MM-dd", "dd.MM.yy",
				"yyyyMMdd", "yyyy/MM/dd","yyyy/MM/dd HH:mm:ss", "MM/dd/yy", "yyyy:MM:dd HH:mm:sss"});
		for (i = 0; i < patterns.length; i++) {
			try {
				DateFormat parser = new SimpleDateFormat(patterns[i]);
				return parser.parse(exifDate);
			} catch (ParseException ex) {
			}
		}
		return null;
	}
    
    /**
     *  
     * Methods Descrip:��ָ����ʽת�������ַ���Ϊ���ڶ���,�������ʧ��,����null
     * @param date:�����ַ���
     * @param pattern:ָ�������ڸ�ʽ
     * @return:Date ����
     *
     */
    public static Date parseDate(String date, String pattern) {
		if (date == null)
			return null;

		try {
			DateFormat parser = new SimpleDateFormat(pattern);
			return parser.parse(date);
		} catch (ParseException ex) {
		}

		return null;
	}
    
    /**
     *  
     * Methods Descrip:��ָ����ʽת�����ڶ���Ϊ�����ַ���,�������ʧ��,����null
     * @param timestamp:Timestamp���͵�����
     * @param pattern:ָ�������ڸ�ʽ
     * @return: String ����
     *
     */
    public static String parseDate(Timestamp timestamp, String pattern) {
		if (timestamp == null)
			return null;

		DateFormat parser = new SimpleDateFormat(pattern);
		return parser.format(timestamp);
	}
    
    /**
     *  
     * Methods Descrip:��������ʱ����������
     * @param time1:��ʼ����
     * @param time2:��������
     * @return:int �������
     *
     */
	public static int getDiscrepancyNum(Timestamp time1, Timestamp time2) {
		int result = 0;
		if (time1 != null && time2 != null) {
			long temp = time1.getTime() - time2.getTime();
			if (temp > 0) {
				result = (int) ((temp / (24 * 60 * 60 * 1000)));
			} else {
				result = -(int) (((temp / (24 * 60 * 60 * 1000))));
			}

		}
		return result;
	}
	
	public static String getString(Date d, String pattern) {
		String ret;
		try {
			ret = new SimpleDateFormat(pattern).format(d);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}
   
	/**
	 * �Ƚ�����������������
	 * 
	 * @param unit
	 *            ��λ,'D' = ��
	 * @param testDate
	 * @param refDate
	 * @return
	 * @throws java.lang.IllegalArgumentException
	 */
	public static int dateDiff(char unit, Date testDate, Date refDate)
			throws IllegalArgumentException {
		long testDateMillis = testDate.getTime();
		long refDateMillis = refDate.getTime();
		unit = Character.toUpperCase(unit);
		if (unit != 'D') {
			throw new IllegalArgumentException("��Ч�ĵ�λ����ǰֻ����� 'D' (����) �ļ���!");
		} else {
			return (int) ((testDateMillis - refDateMillis) / 1000L / 60L / 60L / 24L);
			
		}
		/* @todo,���Ҫ���������д������ */
	}
	

	public static int dateDiffNew(char unit, Date testDate, Date refDate)
			throws IllegalArgumentException {
		long testDateMillis = testDate.getTime();
		long refDateMillis = refDate.getTime();
		unit = Character.toUpperCase(unit);
		if (unit != 'D') {
			throw new IllegalArgumentException("��Ч�ĵ�λ����ǰֻ����� 'D' (����) �ļ���!");
		} else {
			long time = testDateMillis - refDateMillis;			 
			return  (int )  time/(1000*60*60*24);

		}
		
	}
	
	/**
	 * 
	 * @param unit
	 *            ��λ,'D' = ��
	 * @param testDate
	 * @param refDate
	 * @return
	 * @see dateDiff(char unit, Date testDate, Date refDate)
	 * @throws java.lang.IllegalArgumentException
	 */
	public static int dateDiff(char unit, String testDate, String refDate)
			throws IllegalArgumentException {
		return dateDiff(unit, toDate(testDate), toDate(refDate));
	}

	public static Date toDate(String dateString) {
		return parseDate(dateString);
	}
	
	
	/**�õ�����ʱ��ĵ�һ��
	 * @author yuanz
	 * @param	String ������ʱ��yyyy-MM-dd
	 * @return String ���ظ���ʱ��ı��µ�һ��
	 * */ 
	public static String getMonthFirstDay(String inpdate) {
		String[] inpdates =  inpdate.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(inpdates[0]),Integer.parseInt(inpdates[1]) -1, Integer.parseInt(inpdates[2]));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}
	
	/**�õ����µ����һ��* 
	 * @author yuanz
	 * @param	String ������ʱ�� yyyy-MM-dd
	 * @return String ���ظ���ʱ��ı������һ��
	 * @return*/ 
	public static String getMonthLastDay(String inpdate) {
		String[] inpdates =  inpdate.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(inpdates[0]),Integer.parseInt(inpdates[1]) -1, Integer.parseInt(inpdates[2]));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	} 
	/**
	 * ��ȡ�����³�ʱ��
	 * @author lr
	 * @version 1.0
	 * @date 2016��3��22�� ����2:08:53
	 * @return String
	 */
	public static String   getCurDayBegin(){
		
		  Calendar cal = Calendar.getInstance(); 
		  cal.setTime(new Date()); 
		  cal.set(Calendar.DAY_OF_MONTH, 1);
		 return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
	}
	
	/**
	 * 
	 * ��ȡ������ĩʱ��
	 * @author lr
	 * @version 1.0
	 * @date 2016��3��22�� ����2:09:36
	 * @return String
	 */
	public static String   getCurDayEnd(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar ca = Calendar.getInstance();    
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String last = format.format(ca.getTime());
		return last; 

	}
	/**
	 * ����Ӫҵ���ڻ�ȡ·��
	 * @version 1.0
	 * @date 2013��6��26�� ����1:41:07
	 * @param busiDate
	 * @return String
	 */
	public static String getBusiDate(Date busiDate) {
		StringBuilder filepath = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(busiDate);
		filepath.append(calendar.get(Calendar.YEAR)).append(File.separator);
		filepath.append(calendar.get(Calendar.MONTH) + 1).append(File.separator);
		filepath.append(calendar.get(Calendar.DAY_OF_MONTH));
		return filepath.toString();
	}
    public static String getBeforeDateStr(Date date){
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, -1);//ȡ��ǰ���ǰһ��
		 date = calendar.getTime();
		 return sdf.format(date);
    }
    public static Date getBeforeDate(Date date){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, -1);//ȡ��ǰ���ǰһ��
		 date = calendar.getTime();
		 return date;
   }
   public static String getBeforeDateByStrFormat(Date date,String str){
	   date=getBeforeDate(date);
	   SimpleDateFormat sdf=new SimpleDateFormat(str);
	   return sdf.format(date);
   }
}
