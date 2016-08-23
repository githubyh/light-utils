package org.light4j.utils.excel;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel ����ͨ���࣬Ϊ�˼��ݣ����� Excel ͳһ���� Excel2003 ����xx.xls
 * @Author : chenssy
 * @Date �� 2014��6��15�� ����9:09:38
 */
public class ExcelExportHelper {
	
	/** ʱ���ʽ��Ĭ��Ϊyyyy-MM-dd */
	private String DATE_PATTERN = "yyyy-MM-dd";
	
	/** ͼƬ��ȣ�Ĭ��Ϊ��100 */
	private int IMAGE_WIDTH = 30;
	 
	/** ͼƬ�߶ȣ�Ĭ��Ϊ��50 */
	private int IMAGE_HEIGHT = 5;
	
	/** ��Ԫ�������� */
	private int[] maxWidth;
	
	/** 
	 * ��ҳ֧����������У�����65534�����
	 * �������ж���65534����Ҫͨ��MORE_EXCEL_FLAG��MORE_SHEET_FLAG���������ɶ��Excel������sheet
	 */
	private int maxRowCount = 2500;
	
	/** �������ݣ����Excel��ʶ---0001 */
	private String  MORE_EXCEL_FLAG = "0001";
	
	/** �������ݣ����sheet��ʶ---0001 */
	private String MORE_SHEET_FLAG = "0002";
	
	/**
	 * Ĭ�Ϲ��캯�� 
	 */
	public ExcelExportHelper(){
	}
	
	/**
	 * @param datePattern ָ����ʱ���ʽ
	 */
	public ExcelExportHelper(String datePattern){
		this.DATE_PATTERN = datePattern;
	}
	
	/**
	 * @param imageWidth 
	 * 					ָ��ͼƬ�Ŀ��
	 * @param imageHeight
	 * 				           ָ��ͼƬ�ĸ߶�
	 */
	public ExcelExportHelper(int imageWidth,int imageHeight){
		this.IMAGE_HEIGHT = imageHeight;
		this.IMAGE_WIDTH = imageWidth;
	}
	
	/**
	 * @param datePatter 
	 * 					ָ��ʱ���ʽ
	 * @param imageWidth 
	 * 					ָ��ͼƬ�Ŀ��
	 * @param imageHeight 
	 * 					ָ��ͼƬ�ĸ߶�
	 */
	public ExcelExportHelper(String datePatter,int imageWidht,int imageHeight){
		this.DATE_PATTERN = datePatter;
		this.IMAGE_HEIGHT = imageHeight;
		this.IMAGE_WIDTH = imageWidht;
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����ͼƬ��ת��Ϊbyte[]<br>
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean�����Ӧ��javaBean������˳�򣩣�����<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��15�� ����9:18:37
	 * 
	 * @param header  
	 * 				���������������
	 * @param excelList 
	 * 			��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ� javabean
	 *          ���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 	
	 * 			��������
	 * @return ���ɵ�HSSFWorkBook
	 * @version 1.0
	 */
	public HSSFWorkbook exportExcel(String[] header,List<Object> excelList,String sheetTitle){
		//����һ��Excel
		HSSFWorkbook book = new HSSFWorkbook();  
		//����һ�����
		sheetTitle = getSheetTitle(sheetTitle);   //�жϡ�����sheetTitle
		HSSFSheet sheet = book.createSheet(sheetTitle);
		
		//����Excel��������
		setExcelContentData(book,sheet,header,excelList);
		
		System.out.println("������������������������������������ExcelExportHelper:Excel���ɳɹ�...");
		
		return book;
	}
	
	/**
	 * 
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����ͼƬ��ת��Ϊbyte[]<br>
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:02:02
	 * 
	 * @param header  
	 * 				Excel��ͷ
	 * @param properties  
	 * 				��ͷ��ӦjavaBean�е�����
	 * @param excelList  
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ� 
	 * 				javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle  
	 * 				��������
	 * 
	 * @return ���ɵ�HSSFWorkbook
	 * @version 1.0
	 */
	public HSSFWorkbook exportExcel(String[] header,String[] properties,List<Object> excelList,
			String sheetTitle){
		//����һ��Excel
		HSSFWorkbook book = new HSSFWorkbook();
		// ����һ�����
		sheetTitle = getSheetTitle(sheetTitle); // �жϡ�����sheetTitle
		HSSFSheet sheet = book.createSheet(sheetTitle);

		// ����Excel��������
		setExcelContentData(book, sheet, header, properties ,excelList);

		System.out.println("������������������������������������ExcelExportHelper:Excel���ɳɹ�...");

		return book;
	}

	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����Excel������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean����һһ��Ӧ(javaBean������˳��)������<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��17�� ����2:24:38
	 * 
	 * @param header 
	 * 				���������������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param filePath 
	 * 				Excel�ļ�����λ�� 
	 * @param fileName 
	 * 				Excel�ļ���
	 * 
	 * @return
	 * @version 1.0
	 */
	public void exportExcelAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String filePath,String fileName){
		//����Excel
		HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);
		
		//�������ɵ�Excel
		saveExcel(book,filePath,fileName);
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����Excel������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]<Br>
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:24:56
	 * 
	 * @param header 
	 * 				���������������
	 * @param properties 
	 * 				��ͷ��ӦjavaBean�е�����
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param filePath 
	 * 				Excel�ļ�����λ�� 
	 * @param fileName 
	 * 				Excel�ļ���
	 * @version 1.0
	 */
	public void exportExcelAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String filePath,String fileName){
		//����Excel
		HSSFWorkbook book = exportExcel(header, properties,excelList, sheetTitle);	
		//�������ɵ�Excel
		saveExcel(book,filePath,fileName);
	}

	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,���� Excel ��� zip ��ʽ������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean����һһ��Ӧ(javaBean������˳��)������<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��18�� ����12:36:01
	 * 
	 * @param header 
	 * 				���������������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle
	 *				��������
	 * @param filePath 		
	 * 				zip�ļ�����λ�� 
	 * @param excelName  
	 * 				Excel����
	 * @param zipName 
	 * 				zip����
	 * 
	 * @version 1.0
	 */
	public void exportExcelAndZip(String[] header,List<Object> excelList,String sheetTitle,
			String filePath,String excelName,String zipName){
		//����Excel
		HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);
		
		//�����ɵ�Excel�����������
		List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
		books.add(book);
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,���� Excel ��� zip ��ʽ������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:33:04
	 * 
	 * @param header 
	 * 				���������������
	 * @param properties
	 *				��ͷ��ӦjavaBean������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param filePath 
	 * 				zip�ļ�����λ�� 
	 * @param excelName  
	 * 				Excel����
	 * @param zipName
	 * 				zip����
	 * 
	 * @version 1.0
	 */
	public void exportExcelAndZip(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String filePath,String excelName,String zipName){
		//����Excel
		HSSFWorkbook book = exportExcel(header, properties,excelList, sheetTitle);
				
		//�����ɵ�Excel�����������
		List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
		books.add(book);
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean����һһ��Ӧ(javaBean������˳��)������<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��17�� ����9:53:15
	 * 
	 * @param header 
	 * 				���������������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ��flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * 
	 * @return List<HSSFWorkbook>
	 * @version 1.0
	 */
	public List<HSSFWorkbook> exportExcelForBigData(String[] header,List<Object> excelList,String sheetTitle,
			String flag){
		List<HSSFWorkbook> list = new ArrayList<HSSFWorkbook>();    //���������ݽ����

		//�ж���Ҫ���ɼ���Excel
		int num  = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount : excelList.size() / maxRowCount + 1;
		
		HSSFWorkbook book = new HSSFWorkbook();
		List<Object> newList  = null;    //�������б�
		String newTitle = null;    //��title
		for(int i = 0 ; i < num ; i++){
			//�����µ������б�
			int beginRowNum = maxRowCount * i;
			int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
			newList = excelList.subList(beginRowNum, endRowNum);
			newTitle = getSheetTitle(sheetTitle) + "_" + i;    
			if(MORE_EXCEL_FLAG.equals(flag)){     //����Ǵ������Excel
				book = exportExcel(header, newList, newTitle);
				list.add(book);
			}
			else if(MORE_SHEET_FLAG.equals(flag)){   //������sheet
				HSSFSheet sheet = book.createSheet(newTitle);
				setExcelContentData(book,sheet,header,newList);
			}
		}
		
		if(MORE_SHEET_FLAG.equals(flag)){   //������sheet
			list.add(book);
		}
		
		return list;
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:41:23
	 * 
	 * @param header 
	 * 				���������������
	 * @param properties 
	 * 				��ͷ��ӦjavaBean������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ��flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * @return List<HSSFWorkbook>
	 * @version 1.0
	 */
	public List<HSSFWorkbook> exportExcelForBigData(String[] header,String[] properties,
			List<Object> excelList,String sheetTitle, String flag){
		List<HSSFWorkbook> list = new ArrayList<HSSFWorkbook>();    //���������ݽ����
		// �ж���Ҫ���ɼ���Excel
		int num = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount : excelList.size() / maxRowCount + 1;

		HSSFWorkbook book = new HSSFWorkbook();
		List<Object> newList = null; // �������б�
		String newTitle = null; // ��title
		for (int i = 0; i < num; i++) {
			// �����µ������б�
			int beginRowNum = maxRowCount * i;
			int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
			newList = excelList.subList(beginRowNum, endRowNum);
			newTitle = getSheetTitle(sheetTitle) + "_" + i;
			if (MORE_EXCEL_FLAG.equals(flag)) { // ����Ǵ������Excel
				book = exportExcel(header,properties, newList, newTitle);
				list.add(book);
			} else if (MORE_SHEET_FLAG.equals(flag)) { // ������sheet
				HSSFSheet sheet = book.createSheet(newTitle);
				setExcelContentData(book, sheet, header, properties,newList);
			}
		}

		if (MORE_SHEET_FLAG.equals(flag)) { // ������sheet
			list.add(book);
		}
		return list;
	}
	
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����Excel������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean����һһ��Ӧ(javaBean������˳��)������<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��17�� ����10:39:15
	 * 
	 * @param header 
	 * 				���������������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ��flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * @param filePath
	 * 			 	�ļ�����·��
	 * @param fileName 
	 * 				�����ļ���
	 * @return 
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String fileName){
		//��ȡ���ݽ����
		List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);
		String _fileName = "";
		for(int i = 0 ; i < books.size() ; i ++){
			HSSFWorkbook book = books.get(i);
			_fileName = getFileName(fileName) + "_0" + i;
			//����Excel�ļ�
			saveExcel(book, filePath, _fileName);
		}
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,����Excel������ĳ��·����,
	 * ����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����8:22:25
	 * 
	 * @param header 
	 * 				���������������
	 * @param properties 
	 * 				��ͷ��ӦjavaBean����
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ��flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * @param filePath 
	 * 				�ļ�����·��
	 * @param fileName 
	 * 				�����ļ���
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String fileName){
		//��ȡ���ݽ����
		List<HSSFWorkbook> books = exportExcelForBigData(header, properties,excelList, sheetTitle, flag);
		
		String _fileName = "";
		for(int i = 0 ; i < books.size() ; i ++){
			HSSFWorkbook book = books.get(i);
			_fileName = getFileName(fileName) + "_0" + i;
			//����Excel�ļ�
			saveExcel(book, filePath, _fileName);
		}
	}
	
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,���� Excel ����� ZIP 
	 * ������ĳ��·����,����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��excelList�������£�<br>
	 * header��excelList�е�Bean����һһ��Ӧ(javaBean������˳��)������<br>
	 * header�����������䡢�Ա𡢰༶<br>
	 * Bean��name��age��sex��class<br>
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����10:39:15
	 * 
	 * @param header 
	 * 				���������������
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ��flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * @param filePath 
	 * 				�ļ�����·��
	 * @param excelName 
	 * 				Excel�ļ���
	 * @param zipName 
	 * 				zip�ļ���
	 * @return 
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndZipAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String excelName,String zipName){
		//��ȡ���ɵ�Excel����
		List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);
		
		//�����ɵ�Excel���������
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * ͨ�÷�����ʹ�� java ������ƣ������ṩ��ͷ header �������� excelList ���� Excel,���� Excel ����� ZIP 
	 * ������ĳ��·����,����ͼƬ��ת��Ϊbyte[]<br>
	 * ���ڴ�������ʱʹ��,�漰��һ����ֻ����65536��,���������ϴ�ʱ��ֱ��д����һ����(excel��sheet)
	 * header��properties��Ҫһһ��Ӧ��<Br>
	 * header = ["ѧ��","����","�Ա�","�༶"]
	 * properties = ["id","age","sex","class"],���Ӧ��excelList��javaBean������ֵ
	 * 
	 * @author chenssy 
	 * @date 2014��6��19�� ����8:24:21
	 * 
	 * @param header 
	 * 				���������������
	 * @param properties 
	 * 				��ͷ��ӦjavaBean����
	 * @param excelList 
	 * 				��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *              javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
	 * @param sheetTitle 
	 * 				��������
	 * @param flag 
	 * 				��ҳ��ʶΪ�� flag == 0001�����ɶ��Excel,flag == 0002�����ɶ��sheet
	 * @param filePath 
	 * 				�ļ�����·��
	 * @param excelName  
	 * 				Excel�ļ���
	 * @param zipName 
	 * 				ZIP�ļ���
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndZipAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String excelName,String zipName){
		//��ȡ���ɵ�Excel����
		List<HSSFWorkbook> books = exportExcelForBigData(header, properties,excelList, sheetTitle, flag);
		
		//�����ɵ�Excel���������
		zipExcelAndSave(books, filePath, zipName, excelName);
	}

	/**
	 * ���Excel��������
	 * @author chenssy 
	 * @date 2014��6��17�� ����10:32:34
	 * @param book Excel
	 * @param sheet sheet
	 * @param header Excelͷ��title
	 * @param excelList Excel������
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	private void setExcelContentData(HSSFWorkbook book,HSSFSheet sheet,String[] header,List<Object> excelList) {
		//������ͷ��ʽ(���С���֡���ɫ)
		HSSFCellStyle headerStyle = book.createCellStyle();
		setHeaderStyle(headerStyle, book);

		// ���õ�Ԫ����ʽ
		HSSFCellStyle cellStyle = book.createCellStyle();
		setCellStyle(cellStyle, book);

		// ����ͷ��
		HSSFRow row = createHeader(sheet, headerStyle, header);

		// ��ͼ�Ķ�����������һ��sheetֻ�ܻ�ȡһ����һ��Ҫע����㣩
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		
		int index = 0;
		/* �����ڵ��������в������¶���̫�࣬���ｲѭ���ڲ�����ȫ���Ƴ��� */
		Object t = null;    
		HSSFCell cell = null;
		Field field = null;
		String fieldName = null;
		String getMethodName = null;
		Class tCls = null;
		Method getMethod = null;
		Object value = null;
		// �����������ݣ�����������
		Iterator<Object> it = excelList.iterator();
		maxWidth = new int[header.length];   //��ʼ����Ԫ����
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			// ����������
			t = it.next();
			// ���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  //����getter����
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName,new Class[] {});
				    value = (Object) getMethod.invoke(t, new Object[] {});
					// ��value���õ���Ԫ��ָ��λ��
					setCellData(row, index, i, value, cell, sheet, patriarch, book);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (SecurityException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				}
			}
		}
		
		System.out.println("-------------------------���Excel���ݳɹ�..........");
	}
	
	/**
	 * ���Excel����
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:00:35
	 * @param book
	 * @param sheet
	 * @param header
	 * @param properties
	 * @param excelList
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, String[] properties,
			List<Object> excelList) {
		//������ͷ��ʽ(���С���֡���ɫ)
		HSSFCellStyle headerStyle = book.createCellStyle();
		setHeaderStyle(headerStyle, book);

		// ���õ�Ԫ����ʽ
		HSSFCellStyle cellStyle = book.createCellStyle();
		setCellStyle(cellStyle, book);

		// ����ͷ��
		HSSFRow row = createHeader(sheet, headerStyle, header);

		// ��ͼ�Ķ�����������һ��sheetֻ�ܻ�ȡһ����һ��Ҫע����㣩
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		/* Ϊ�˱�����������в���������¶������ｫѭ���ڲ�����ȫ���Ƴ��� */
		int index = 0;
		Object t = null;
		HSSFCell cell = null;
		Object o = null;
		Class clazz = null;
		PropertyDescriptor pd = null;
		Method getMethod = null;
		// �����������ݣ�����������
		Iterator<Object> it = excelList.iterator();
		maxWidth = new int[header.length];   //��ʼ����Ԫ����
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			// ����������
			t = it.next();
			for(int i = 0 ; i < header.length ; i++){
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				o = null;    //ÿһ����Ԫ����Ҫ��O����Ϊnull
				try {
					clazz = t.getClass();
					pd = new PropertyDescriptor(properties[i],clazz);
					getMethod = pd.getReadMethod();   // ���get����
					if (pd != null) {  
			           o  = getMethod.invoke(t);   //ִ��get��������һ��Object  
			        }  
					setCellData(row, index, i, o, cell, sheet, patriarch, book);
				} catch (IntrospectionException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("����������������������������������������Excel�����б�ʱ����method:setDataRow,message��"+e.getMessage());
				}
			}
		}

		System.out.println("���������������������������������������Excel���ݳɹ�..........");
	}
	
	/**
	 * ����sheet��title����Ϊ����ΪyyyyMMddHH24mmss
	 * @author chenssy 
	 * @date 2014��6��16�� ����1:46:06
	 * @param sheetTitle 
	 * @return
	 * @version 1.0
	 */
	private  String getSheetTitle(String sheetTitle) {
		String title = null;
		if(sheetTitle != null && !"".equals(sheetTitle)){
			title = sheetTitle;
		}
		else{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
			title = sdf.format(date);
		}
		return title;
	}
	
	/**
	 * ����ExcelͼƬ�ĸ�ʽ��������С���֡���ɫ��12��
	 * @author chenssy 
	 * @date 2014��6��16�� ����8:46:49
	 * @param headerStyle
	 * 				ͷ����ʽ
	 * @param book
	 * 		  		������excel book 	 HSSFWorkbook����	
	 * @version 1.0
	 */
	private void setHeaderStyle(HSSFCellStyle headerStyle,HSSFWorkbook book) {
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //ˮƽ����
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//��ֱ���� 
		//��������
		HSSFFont font = book.createFont();
		font.setFontHeightInPoints((short) 12);     //�ֺţ�12��
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //���
		font.setColor(HSSFColor.BLUE.index);   //��ɫ
		
		headerStyle.setFont(font);
	}
	
	/**
	 * ���õ�Ԫ����ʽ
	 * @author chenssy 
	 * @date 2014��6��17�� ����11:00:53
	 * @param cellStyle
	 * 			��Ԫ����ʽ
	 * @param book
	 * 			book HSSFWorkbook����
	 * @version 1.0
	 */
	private void setCellStyle(HSSFCellStyle cellStyle, HSSFWorkbook book) {
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //ˮƽ����
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//��ֱ���� 
		
		HSSFFont font = book.createFont();
		font.setFontHeightInPoints((short)12);
		
		cellStyle.setFont(font);
	}
	
	/**
	 * ����ͷ����ʽ��ͷ�����ݴ���Excelͷ��
	 * @author chenssy 
	 * @date 2014��6��17�� ����11:37:28
	 * @param sheet 
	 * 				sheet
	 * @param headerStyle 
	 * 				ͷ����ʽ
	 * @param header 
	 * 				ͷ������
	 * @return ������ɵ�ͷ��Row
	 * @version 1.0
	 */
	private HSSFRow createHeader(HSSFSheet sheet,HSSFCellStyle headerStyle,
			String[] header) {
		HSSFRow headRow = sheet.createRow(0);
		headRow.setHeightInPoints((short)(20));   //����ͷ���߶�
		//�������
		HSSFCell cell = null;
		for(int i = 0 ; i < header.length ; i++){
			cell = headRow.createCell(i);
			cell.setCellStyle(headerStyle);
			HSSFRichTextString text = new HSSFRichTextString(header[i]);
			cell.setCellValue(text);
		}
		
		return headRow;
	}
	
	/**
	 * ���õ�Ԫ������
	 * @author chenssy 
	 * @date 2014��6��17�� ����11:48:14
	 * @param row  
	 * 				ָ����
	 * @param index 
	 * @param i 
	 * 				����
	 * @param value 
	 * 				��Ԫ��ֵ cellValue
	 * @param cell 
	 * 				��Ԫ�� HSSFCell����
	 * @param sheet 
	 * 				sheet HSSFSheet����
	 * @param patriarch  
	 * 				�������� ����ʵ��ͻ��
	 * @param book 
	 * 			Excel HSSFWorkbook����
	 * @version 1.0
	 */
	private void setCellData(HSSFRow row, int index ,int i ,Object value,HSSFCell cell,HSSFSheet sheet,HSSFPatriarch patriarch,HSSFWorkbook book) {
		String textValue = null; 
		if (value instanceof Date) {    //Ϊ��������ʱ���ʽ
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			textValue = sdf.format(date);  
		}
		if(value instanceof byte[]){   //byteΪͼƬ
			//����ͼƬ��Ԫ���ȡ��߶�
			row.setHeightInPoints((short)(IMAGE_HEIGHT * 10));
			sheet.setColumnWidth(i, IMAGE_WIDTH * 256);
		    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,(short) i, index, (short) i, index);   
	        anchor.setAnchorType(3);   
	        //����ͼƬ  
	        byte[] bsValue = (byte[]) value;
	        patriarch.createPicture(anchor, book.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG)); 
		}
		else{   //����ȫ�������ַ�����
			if(value != null){
				textValue = String.valueOf(value);
			}
			else{
				textValue = "";
			}
		}
		// �������ͼƬ���ݣ�������������ʽ�ж�textValue�Ƿ�ȫ�����������
		if (textValue != null) {
			Pattern p = Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher = p.matcher(textValue);
			
			//���õ�Ԫ���ȣ��������ܹ�ȫ����ʾ
			setCellMaxWidth(textValue,i);
			sheet.setColumnWidth(i, maxWidth[i]);    //���õ�Ԫ����
			row.setHeightInPoints((short)(20));   //���õ�Ԫ��߶�
			if (matcher.matches()) {
				// �����ֵ���double����
				cell.setCellValue(Double.parseDouble(textValue));
			} else {
				cell.setCellValue(textValue);
			}
		}
	}

	/**
	 * ��ȡ�ļ�������Ϊ�գ������Ϊ��yyyyMMddHH24mmss+6λ�����
	 * @author chenssy 
	 * @date 2014��6��17�� ����5:44:27
	 * @param fileName
	 * 				�ļ���
	 * @return
	 * @version 1.0
	 */
	private String getFileName(String fileName) {
		if(fileName == null || "".equals(fileName)){
			//����
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
			//�����
			Random random = new Random();
			fileName = sdf.format(date) + String.valueOf(Math.abs(random.nextInt() * 1000000));
		}
		return fileName;
	}
	
	/**
	 * ������������ȡ��Ԫ���С,�����µ�ǰ�е������
	 * @author chenssy 
	 * @date 2014��6��17�� ����7:35:52
	 * @param textValue 
	 * @param ָ����
	 * @return
	 * @version 1.0
	 */
	private void setCellMaxWidth(String textValue,int i ) {
		int size = textValue.length();
		int width = (size + 6) * 256;
		if(maxWidth[i] <= width){
			maxWidth[i] = width;
		}
	}
	
	/**
	 * �����ɵ�Excel���浽ָ��·����
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:10:17
	 * @param book 
	 * 			���ɵ�Excel HSSFWorkbook����
	 * @param filePath 
	 * 			��Ҫ�����·��
	 * @param fileName 
	 * 			Excel�ļ���
	 * @version 1.0
	 */
	private void saveExcel(HSSFWorkbook book, String filePath, String fileName) {
		//��Ᵽ��·���Ƿ���ڣ����������½�
		checkFilePathIsExist(filePath);
		//��Excel������ָ��Ŀ¼��
		fileName = getFileName(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath + "\\" + fileName + ".xls");
			book.write(out); 
			System.out.println("����������������������������������������Excel�ļ��ɹ�������·����" + filePath + "\\" + fileName + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����������������������������������������Excel�ļ�ʧ�ܡ�exportExcelForListAndSave,message��"+e.getMessage());
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �����ɵ�Excel��������浽ָ��·����
	 * @author chenssy 
	 * @date 2014��6��19�� ����6:18:09
	 * @param book 
	 * 			���ɵ�Excel HSSFWorkbook list����
	 * @param filePath 
	 * 			����·��
	 * @param zipName 
	 * 			zip �ļ���
	 * @param excelName 
	 * 			Excel�ļ���
	 * @version 1.0
	 */
	private void zipExcelAndSave(List<HSSFWorkbook> books,String filePath,String zipName,String excelName){
		//��Ᵽ��·���Ƿ���ڣ������������½�
		checkFilePathIsExist(filePath);
		
		zipName = getFileName(zipName);
		excelName = getFileName(excelName);
		
		//��Excel�����������ָ��Ŀ¼��
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		try {
			out = new FileOutputStream(filePath + "\\" + zipName + ".zip");
			zip = new ZipOutputStream(out);
			HSSFWorkbook book = null;
			String _excelName = "";
			for (int i = 0; i < books.size(); i++) {
				book = books.get(i);
				_excelName = getFileName(excelName) + "_0" + i;
				ZipEntry entry = new ZipEntry(_excelName + ".xls");
				zip.putNextEntry(entry);
				book.write(zip);
			}
			System.out.println("����������������������������������������Excel�ļ��ɹ�������·����" + filePath + "\\" + zipName + ".xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("����������������������������������������Excel�ļ�ʧ�ܡ�method:exportExcelForBigDataAndSave,message��" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����������������������������������������Excel�ļ�ʧ�ܡ�method:exportExcelForBigDataAndSave,message��" + e.getMessage());
		} finally {
			if (zip != null) {
				try {
					zip.flush();
					zip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ��Ᵽ��·���Ƿ���ڣ����������½�
	 * @author chenssy 
	 * @date 2014��6��18�� ����1:05:17
	 * @param filePath  
	 * 				�ļ�·��
	 * @version 1.0
	 */
	private void checkFilePathIsExist(String filePath) {
		File file = new File(filePath);
		
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
