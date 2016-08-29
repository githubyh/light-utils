package org.light4j.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import org.light4j.utils.date.DateUtils;
import org.light4j.utils.random.RandomUtils;

/**
 * �ļ�������
 * 
 * @author longjiazuo
 */
public class FileUtils {
	private static final String FOLDER_SEPARATOR = "/";
	private static final char EXTENSION_SEPARATOR = '.';
	
	/**
	 * �����ļ������ļ�
	 * @param filepath     �ļ�·��
	 * @return ����file�ۣ� ����
	 */
	public static File[] getFileList(String filepath) {
		File d = null;
		File list[] = null;
		/** ������ǰĿ¼���ļ���File���� **/
		try {
			d = new File(filepath);
			if (d.exists()) {
				list = d.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** ȡ�ô���Ŀ¼�������ļ���File�������� **/
		return list;
	}
	
	/**
	 * ��ȡ�ı��ļ�����
	 * @param filePathAndName  ������������·�����ļ���
	 * @param encoding         �ı��ļ��򿪵ı��뷽ʽ
	 * @return                 �����ı��ļ�������
	 */
	public static String readTxt(String filePathAndName, String encoding) throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data);
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			if (st != null && st.length() > 1)
				st = st.substring(0, st.length() - 1);
		} catch (IOException es) {
			st = "";
		}
		return st;
	}
	
	/**
	 * �½�Ŀ¼
	 * @param folderPath  Ŀ¼
	 * @return            ����Ŀ¼�������·��
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * �༶Ŀ¼����
	 * @param folderPath ׼��Ҫ�ڱ���Ŀ¼�´�����Ŀ¼��Ŀ¼·������ c:myf
	 * @param paths      ���޼�Ŀ¼����������Ŀ¼�Ե��������� ���� a|b|c
	 * @return           ���ش����ļ����·��
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txts;
	}
	
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
	 * �½��ļ�
	 * @param filePathAndName �ı��ļ���������·�����ļ���
	 * @param fileContent     �ı��ļ�����
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �б��뷽ʽ���ļ�����
	 * @param filePathAndName �ı��ļ���������·�����ļ���
	 * @param fileContent     �ı��ļ�����
	 * @param encoding  ���뷽ʽ ���� GBK ���� UTF-8
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent, String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���ļ�
	 * @param filePathAndName �ı��ļ���������·�����ļ���
	 * @return Boolean �ɹ�ɾ������true�����쳣����false
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bea;
	}
	
	/**
	 * ɾ���ļ����Լ�����������ļ�
	 * 
	 * @param folderPath
	 *            �ļ�����������·��
	 * @return
	 */
	public static void delFolder(String folderPath) {
		try {
			/**ɾ����������������**/
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			/**ɾ�����ļ���**/
			myFilePath.delete(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��ָ���ļ����������ļ�
	 * @param path �ļ�����������·��
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) { return bea;}
		if (!file.isDirectory()) { return bea;}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) { temp.delete(); }
			if (temp.isDirectory()) {
				/**��ɾ���ļ���������ļ�**/
				delAllFile(path + "/" + tempList[i]);
				/**��ɾ�����ļ�**/
				delFolder(path + "/" + tempList[i]);
				bea = true;
			}
		}
		return bea;
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
	 * ���Ƶ����ļ�
	 * @param oldPathFile  ׼�����Ƶ��ļ�Դ
	 * @param newPathFile �������¾���·�����ļ���
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPathFile);
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������ļ��е�����
	 * @param oldPath  ׼��������Ŀ¼
	 * @param newPath  ָ������·������Ŀ¼
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			/**����ļ��в����� �������ļ�**/
			new File(newPath).mkdirs(); 
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				/**��������ļ�**/
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * �ƶ��ļ�
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * �ƶ�Ŀ¼
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
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
