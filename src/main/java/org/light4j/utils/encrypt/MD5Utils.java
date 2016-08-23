package org.light4j.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5����
 * 
 * @author longjiazuo
 */
class MD5Utils {
	protected final static String MD5_KEY = "MD5";
	
	protected final static String SHA_KEY = "SHA1";
	
	protected static String encrypt(String value,String key) {
		try {
			// �õ�һ��MD5ת�����������ҪSHA1�������ɡ�SHA1����
			MessageDigest messageDigest = MessageDigest.getInstance(key);
			// ������ַ���ת�����ֽ�����
			byte[] inputByteArray = value.getBytes();
			// inputByteArray�������ַ���ת���õ����ֽ�����
			messageDigest.update(inputByteArray);
			// ת�������ؽ����Ҳ���ֽ����飬����16��Ԫ��
			byte[] resultByteArray = messageDigest.digest();
			// �ַ�����ת�����ַ�������
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String byteArrayToHex(byte[] byteArray) {

		// ���ȳ�ʼ��һ���ַ����飬�������ÿ��16�����ַ�
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		// newһ���ַ����飬�������������ɽ���ַ����ģ�����һ�£�һ��byte�ǰ�λ�����ƣ�Ҳ����2λʮ�������ַ���2��8�η�����16��2�η�����
		char[] resultCharArray = new char[byteArray.length * 2];
		// �����ֽ����飬ͨ��λ���㣨λ����Ч�ʸߣ���ת�����ַ��ŵ��ַ�������ȥ
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// �ַ�������ϳ��ַ�������
		return new String(resultCharArray);
	}
}
