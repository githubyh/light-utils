package org.light4j.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.light4j.utils.date.DateFormatUtils;
import org.light4j.utils.date.DateUtils;

/**
 * ����Excel��֧��2003��2007
 * 
 * @author longjiazuo
 */
public class ExcelReadHelper {
	
	/**
	 * ����Excel ֧��2003��2007<br>
	 * ���÷��似�����propertis��obj�����ӳ�䣬�������Ӧ��ֵ�������Ӧsetter�������õ�obj���������add��list������<br>
	 * properties��obj��Ҫ�������¹���<br>
	 * 1��obj����������Ĭ�Ϲ��캯���������������setter����<br>
	 * 2��properties�е�ֵ��������obj�д��ڵ����ԣ���obj�б��������Щ���Ե�setter������<br>
	 * 3��properties��ֵ��˳��Ҫ��Excel��������Ӧ������ֵ�����ô�<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;excel:���    ����         ����       �Ա�<br>
	 * properties:id  name  age  sex<br>
	 * 
	 * @param file
	 * 				��������Excel�ļ�
	 * @param properties
	 * 				��Excel���Ӧ������
	 * @param obj
	 * 				��������Class
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> excelRead(File file,String[] properties,Class obj) throws Exception{
		Workbook book = null;
		try {
			book = new XSSFWorkbook(new FileInputStream(file));     //����2003
		} catch (Exception e) { 
			book = new HSSFWorkbook(new FileInputStream(file));      //����2007
		}
		
		return getExcelContent(book,properties,obj);    
	}

	/**
	 * ����Excel ֧��2003��2007<br>
	 * ���÷��似�����propertis��obj�����ӳ�䣬�������Ӧ��ֵ�������Ӧsetter�������õ�obj���������add��list������<br>
	 * properties��obj��Ҫ�������¹���<br>
	 * 1��obj����������Ĭ�Ϲ��캯���������������setter����<br>
	 * 2��properties�е�ֵ��������obj�д��ڵ����ԣ���obj�б��������Щ���Ե�setter������<br>
	 * 3��properties��ֵ��˳��Ҫ��Excel��������Ӧ������ֵ�����ô�<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;excel�����    ����         ����       �Ա�<br>
	 * properties��id  name  age  sex<br>
	 * 
	 * @param file
	 * 				��������Excel�ļ���·��
	 * @param properties
	 * 				��Excel���Ӧ������
	 * @param obj
	 * 				��������Class
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> excelRead(String filePath,String[] properties,Class obj) throws Exception{
		File file = new File(filePath);
		if(!file.exists()){
			throw new Exception("ָ�����ļ�������");
		}
		return excelRead(file, properties, obj);
	}
	
	/**
	 * ����params��object����Excel�����ҹ���list����
	 *
	 * @param book
	 * 				WorkBook�����������˴���������Excel�ļ�
	 * @param properties
	 * 				��Ҫ�ο�Object������
	 * @param object
	 * 				������Object����ÿһ��row���൱��һ��object����
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private static List<Object> getExcelContent(Workbook book, String[] properties,
			Class obj) throws Exception {
		List<Object> resultList = new ArrayList<Object>();        //��ʼ�������
		Map<String, Method> methodMap = getObjectSetterMethod(obj);  
		Map<String, Field> fieldMap = getObjectField(obj);
		for(int numSheet = 0 ; numSheet < book.getNumberOfSheets(); numSheet++){
			Sheet sheet = book.getSheetAt(numSheet);
			if(sheet == null){   //�����м��һ��
				continue;
			}
			
			for(int numRow = 1 ; numRow < sheet.getLastRowNum() ; numRow++){   //һ��row���൱��һ��Object
				Row row = sheet.getRow(numRow);
				if(row == null){
					continue;
				}
				resultList.add(getObject(row,properties,methodMap,fieldMap,obj));
			}
		}
		return resultList;
	}

	/**
	 * ��ȡrow�����ݣ����÷�����ƹ���Object����
	 *
	 * @param row
	 * 				row����
	 * @param properties
	 * 				Object�ο�������
	 * @param methodMap 
	 * 				object�����setter����ӳ��
	 * @param fieldMap
	 * 				object���������ӳ��
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private static Object getObject(Row row, String[] properties,
			Map<String, Method> methodMap,Map<String, Field> fieldMap,Class obj) throws Exception {
		Object object = obj.newInstance();
		for(int numCell = 0 ; numCell < row.getLastCellNum() ; numCell++){
			Cell cell = row.getCell(numCell);
			if(cell == null){
				continue;
			}
			String cellValue = getValue(cell);
			String property = properties[numCell].toLowerCase();
			Field field = fieldMap.get(property);    //��property��object�����ж�Ӧ������
			Method method = methodMap.get(property);  //��property��object�����ж�Ӧ��setter����
			setObjectPropertyValue(object,field,method,cellValue);
		}
		return object;
	}
	
	/**
	 * ����ָ�����Եĵ�setter������object��������ֵ
	 *
	 * @param obj
	 * 			object����
	 * @param field
	 * 				object���������
	 * @param method
	 * 				object�������Ե����Ӧ�ķ���
	 * @param value
	 * 				��Ҫ���õ�ֵ	
	 * @throws Exception 
	 */
	private static void setObjectPropertyValue(Object obj, Field field,
			Method method, String value) throws Exception {
		Object[] oo = new Object[1];

		String type = field.getType().getName();
		if ("java.lang.String".equals(type) || "String".equals(type)) {
			oo[0] = value;
		} else if ("java.lang.Integer".equals(type) || "java.lang.int".equals(type) || "Integer".equals(type) || "int".equals(type)) {
			if (value.length() > 0)
				oo[0] = Integer.valueOf(value);
		} else if ("java.lang.Float".equals(type) || "java.lang.float".equals(type)  || "Float".equals(type) || "float".equals(type)) {
			if (value.length() > 0)
				oo[0] = Float.valueOf(value);
		} else if ("java.lang.Double".equals(type)  || "java.lang.double".equals(type) || "Double".equals(type) || "double".equals(type)) {
			if (value.length() > 0)
				oo[0] = Double.valueOf(value);
		} else if ("java.math.BigDecimal".equals(type)  || "BigDecimal".equals(type)) {
			if (value.length() > 0)
				oo[0] = new BigDecimal(value);
		} else if ("java.util.Date".equals(type)  || "Date".equals(type)) {
			if (value.length() > 0){//������Ϊ19(yyyy-MM-dd HH24:mm:ss)����Ϊ14(yyyyMMddHH24mmss)ʱDate��ʽת��ΪyyyyMMddHH24mmss
				if(value.length() == 19 || value.length() == 14){    
					oo[0] = DateUtils.string2Date(value, "yyyyMMddHH24mmss");
				}
				else{     //����ȫ��ת��ΪyyyyMMdd��ʽ
					oo[0] = DateUtils.string2Date(value, "yyyyMMdd");
				}
			}
		} else if ("java.sql.Timestamp".equals(type)) {
			if (value.length() > 0)
				oo[0] = DateFormatUtils.formatDate(value, "yyyyMMddHH24mmss");
		} else if ("java.lang.Boolean".equals(type)  || "Boolean".equals(type)) {
			if (value.length() > 0)
				oo[0] = Boolean.valueOf(value);
		} else if ("java.lang.Long".equals(type) || "java.lang.long".equals(type)  || "Long".equals(type) || "long".equals(type)) {
			if (value.length() > 0)
				oo[0] = Long.valueOf(value);
		}
		try {
			method.invoke(obj, oo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("static-access")
	private static String getValue(Cell cell) {  
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(cell.getBooleanCellValue());  
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            return NumberToTextConverter.toText(cell.getNumericCellValue());  
        } else {  
            return String.valueOf(cell.getStringCellValue());  
        }  
    }  

	/**
	 * ��ȡobject�����������Ե�Setter������������map���󣬽ṹΪMap<'field','method'>
	 *
	 * @param object
	 * 				object����
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Method> getObjectSetterMethod(Class object) {
		Field[] fields = object.getDeclaredFields();       //��ȡobject�������������
        Method[] methods = object.getDeclaredMethods();    //��ȡobject��������з���
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for(Field field : fields){
        	String attri = field.getName();   
            for(Method method : methods){   
                String meth = method.getName(); 
                //ƥ��set���� 
                if(meth != null && "set".equals(meth.substring(0, 3)) && 
                   Modifier.isPublic(method.getModifiers()) && 
                   ("set"+Character.toUpperCase(attri.charAt(0))+attri.substring(1)).equals(meth)){   
                     methodMap.put(attri.toLowerCase(), method);       //��ƥ���setter��������map������
                          break;   
                    }   
                }   
        }
        
		return methodMap;
	}
	
	/**
	 * ��ȡobject������������ԣ�������map���󣬶�����ΪMap<'field','field'>
	 *
	 * @param object
	 * 				object����	
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Field> getObjectField(Class object) {
		Field[] fields = object.getDeclaredFields();       //��ȡobject�������������
		Map<String, Field> fieldMap = new HashMap<String,Field>();
		for(Field field : fields){
			String attri = field.getName();   
            fieldMap.put(attri.toLowerCase(), field);   
		}
		return fieldMap;
	}
}
