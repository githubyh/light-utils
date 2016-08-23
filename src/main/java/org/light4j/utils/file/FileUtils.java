package org.light4j.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.light4j.utils.date.DateUtils;
import org.light4j.utils.math.RandomUtils;

/**
 * �ļ�������
 * 
 * @author longjiazuo
 */
public class FileUtils {
	private static final String FOLDER_SEPARATOR = "/";
	private static final char EXTENSION_SEPARATOR = '.';
	
	/**
	 * @desc:�ж�ָ��·���Ƿ���ڣ���������ڣ����ݲ��������Ƿ��½�
	 *
	 * @param filePath
	 * 			ָ�����ļ�·��
	 * @param isNew
	 * 			true���½���false�����½�
	 * @return ���ڷ���TRUE�������ڷ���FALSE
	 */
	public static boolean isExist(String filePath,boolean isNew){
		File file = new File(filePath);
		if(!file.exists() && isNew){    
			return file.mkdirs();    //�½��ļ�·��
		}
		return false;
	}
	
	/**
	 * ��ȡ�ļ����������ṹΪ prefix + yyyyMMddHH24mmss + 10λ����� + suffix + .type
	 *
	 * @param type
	 * 				�ļ�����
	 * @param prefix
	 * 				ǰ׺
	 * @param suffix
	 * 				��׺
	 * @return
	 */
	public static String getFileName(String type,String prefix,String suffix){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //��ǰʱ��
		String random = RandomUtils.generateNumberString(10);   //10λ�����
		
		//�����ļ���  
		return prefix + date + random + suffix + "." + type;
	}
	
	/**
	 * ��ȡ�ļ������ļ�������:��ǰʱ�� + 10λ����� + .type
	 *
	 * @param type
	 * 				�ļ�����
	 * @return
	 */
	public static String getFileName(String type){
		return getFileName(type, "", "");
	}
	
	/**
	 * ��ȡ�ļ������ļ����ɣ���ǰʱ�� + 10λ�����
	 *
	 * @return
	 */
	public static String getFileName(){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //��ǰʱ��
		String random = RandomUtils.generateNumberString(10);   //10λ�����
		
		//�����ļ���  
		return date + random;
	}
	
	/**
	 * ��ȡָ���ļ��Ĵ�С
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("resource")
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}
	
	/**
	 * ɾ�������ļ��������ļ���
	 * 
	 * @param dirpath
	 */
    public void deleteAll(String dirpath) {  
    	 File path = new File(dirpath);  
         try {  
             if (!path.exists())  
                 return;// Ŀ¼�������˳�   
             if (path.isFile()) // ������ļ�ɾ��   
             {  
                 path.delete();  
                 return;  
             }  
             File[] files = path.listFiles();// ���Ŀ¼�����ļ��ݹ�ɾ���ļ�   
             for (int i = 0; i < files.length; i++) {  
                 deleteAll(files[i].getAbsolutePath());  
             }  
             path.delete();  

         } catch (Exception e) {  
             e.printStackTrace();  
         }   
    }
    
    /**
     * �����ļ������ļ���
     * 
     * @param inputFile
     * 						Դ�ļ�
     * @param outputFile
     * 						Ŀ���ļ�
     * @param isOverWrite
     * 						�Ƿ񸲸��ļ�
     * @throws java.io.IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite)
			throws IOException {
		if (!inputFile.exists()) {
			throw new RuntimeException(inputFile.getPath() + "ԴĿ¼������!");
		}
		copyPri(inputFile, outputFile, isOverWrite);
	}
    
    /**
     * �����ļ������ļ���
     * 
     * @param inputFile
     * 						Դ�ļ�
     * @param outputFile
     * 						Ŀ���ļ�
     * @param isOverWrite
     * 						�Ƿ񸲸��ļ�
     * @throws java.io.IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		if (inputFile.isFile()) {		//�ļ�
			copySimpleFile(inputFile, outputFile, isOverWrite);
		} else {
			if (!outputFile.exists()) {		//�ļ���	
				outputFile.mkdirs();
			}
			// ѭ�����ļ���
			for (File child : inputFile.listFiles()) {
				copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
			}
		}
	}
    
    /**
     * ���Ƶ����ļ�
     * 
     * @param inputFile
     * 						Դ�ļ�
     * @param outputFile
     * 						Ŀ���ļ�
     * @param isOverWrite
     * 						�Ƿ񸲸�
     * @throws java.io.IOException
     */
    private static void copySimpleFile(File inputFile, File outputFile,
			boolean isOverWrite) throws IOException {
		if (outputFile.exists()) {
			if (isOverWrite) {		//���Ը���
				if (!outputFile.delete()) {
					throw new RuntimeException(outputFile.getPath() + "�޷����ǣ�");
				}
			} else {
				// ��������
				return;
			}
		}
		InputStream in = new FileInputStream(inputFile);
		OutputStream out = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}
    
    /**
     * ��ȡ�ļ���MD5
     * 
     * @param file
     * 				�ļ�
     * @return
     */
	public static String getFileMD5(File file){
		if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
	}
	
	/**
	 * ��ȡ�ļ��ĺ�׺
	 * 
	 * @param file
	 * 				�ļ�
	 * @return
	 */
	public static String getFileSuffix(String file) {
		if (file == null) {
			return null;
		}
		int extIndex = file.lastIndexOf(EXTENSION_SEPARATOR);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = file.lastIndexOf(FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}
		return file.substring(extIndex + 1);
	}
	
	/**
	 * �ļ�������
	 * 
	 * @param oldPath
	 * 					���ļ�
	 * @param newPath
	 * 					���ļ�
	 */
    public boolean renameDir(String oldPath, String newPath) {  
        File oldFile = new File(oldPath);// �ļ���Ŀ¼   
        File newFile = new File(newPath);// �ļ���Ŀ¼   
        
        return oldFile.renameTo(newFile);// ������   
    }
}
