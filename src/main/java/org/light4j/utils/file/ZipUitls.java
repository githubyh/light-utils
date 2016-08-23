package org.light4j.utils.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * �ļ�ѹ������ѹ�����ࡣ�ļ�ѹ����ʽΪzip
 * 
 * @author longjiazuo
 */
public class ZipUitls {
	/** �ļ���׺�� */
	private static final String ZIP_FILE_SUFFIX = ".zip";
	
	/**
	 * ѹ���ļ�
	 *
	 * @param resourcePath
	 * 						Դ�ļ�
	 * @param targetPath
	 * 						Ŀ���ļ�,�����ļ�·��
	 */
	public static void zipFile(String resourcePath,String targetPath){
		File resourcesFile = new File(resourcePath); 
		File targetFile = new File(targetPath);
		
		//Ŀ���ļ������ڣ����½�
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//�ļ���
		String targetName = resourcesFile.getName() + ZIP_FILE_SUFFIX;
		
		ZipOutputStream out = null;
		try {
			FileOutputStream outputStream = new FileOutputStream(targetPath+"//"+targetName);
			out = new ZipOutputStream(new BufferedOutputStream(outputStream));

			compressedFile(out, resourcesFile, "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}

	/**
	 *
	 * @param out
	 * @param resourcesFile
	 * @param dir
	 */
	private static void compressedFile(ZipOutputStream out, File file, String dir) {
		FileInputStream fis = null;
		try {
			if (file.isDirectory()) {	//�ļ���
				// �õ��ļ��б���Ϣ
				File[] files = file.listFiles();
				// ���ļ�����ӵ���һ�����Ŀ¼
				out.putNextEntry(new ZipEntry(dir + "/"));

				dir = dir.length() == 0 ? "" : dir + "/";

				// ѭ�����ļ����е��ļ����
				for (int i = 0; i < files.length; i++) {
					compressedFile(out, files[i], dir + files[i].getName()); // �ݹ鴦��
				}
			} else { 	//������ļ���������
				fis = new FileInputStream(file);

				out.putNextEntry(new ZipEntry(dir));
				// ����д����
				int j = 0;
				byte[] buffer = new byte[1024];
				while ((j = fis.read(buffer)) > 0) {
					out.write(buffer, 0, j);
				}
				// �ر�������
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
