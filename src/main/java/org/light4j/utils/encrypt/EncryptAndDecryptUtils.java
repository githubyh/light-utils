package org.light4j.utils.encrypt;


/**
 * �ӽ��ܹ�����<br>
 * �����������MD5���ܡ�SHA���ܡ�Base64�ӽ��ܡ�DES�ӽ��ܡ�AES�ӽ���<br>
 * 
 * @author longjiazuo
 */
public class EncryptAndDecryptUtils {
	
	/**
	 * MD5 ����
	 * 
	 * @param value
	 * 				�������ַ�
	 * @return
	 */
	public static String md5Encrypt(String value){
		String result = null;
		if(value != null && !"".equals(value.trim())){
			result =  MD5Utils.encrypt(value,MD5Utils.MD5_KEY);
		}
		return result;
	}
	
	/**
	 * SHA����
	 * 
	 * @param value		
	 * 					�������ַ�
	 * @return	����
	 */
	public static String shaEncrypt(String value){
		String result = null;
		if(value != null && !"".equals(value.trim())){
			result =  MD5Utils.encrypt(value,MD5Utils.SHA_KEY);
		}
		return result;
	}
	
	/**
	 * BASE64 ����
	 * 
	 * @param value
	 * 				�������ַ���
	 * @return
	 */
	public static String base64Encrypt(String value){
		String result = null;
		if(value != null && !"".equals(value.trim())){
			result =  Base64Utils.encrypt(value.getBytes());
		}
		return result;
		
	}
	
	/**
	 * BASE64 ����
	 * 
	 * @param value
	 * 				�������ַ���
	 * @return
	 */
	public static String base64Decrypt(String value){
		String result = null;
		try {
			if(value != null && !"".equals(value.trim())){
				byte[] bytes = Base64Utils.decrypt(value);
				result = new String(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DES����<br>
	 * 
	 * @param value
	 * 				�������ַ�
	 * @param key	
	 * 				��keyΪ�գ���ʹ��Ĭ��key
	 * @return
	 * 			���ܳɹ��������ģ����򷵻�null
	 */
	public static String desEncrypt(String value,String key){
		key = key == null ? DESUtils.KEY : key;
		String result = null;
		
		try {
			if(value != null && !"".equals(value.trim())){
				result = DESUtils.encrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DES����
	 * 
	 * @param value
	 * 				�������ַ�
	 * @param key	
	 * 				��keyΪ�գ���ʹ��Ĭ��key
	 * @return
	 * @return
	 */
	public static String desDecrypt(String value,String key){
		key = key == null ? DESUtils.KEY : key;
		String result = null;
		
		try {
			if(value != null && !"".equals(value.trim())){
				result =  DESUtils.decrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * AES����
	 *
	 * @param value
	 * 					����������
	 * @param key
	 * 					��Կ
	 * @return
	 */
	public static String aesEncrypt(String value,String key ){
		key = key == null ? AESUtils.KEY : key;
		String result = null;
		try {
			if(value != null && !"".equals(value.trim())){		//value is not null
				result = AESUtils.encrypt(value,key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * AES����
	 * 
	 * @param value
	 * 				����������
	 * @param key
	 * 				��Կ
	 * @return
	 */
	public static String aesDecrypt(String value , String key){
		key = key == null ? AESUtils.KEY : key;
		String result = null;
		try {
			if(value != null && !"".equals(value.trim())){		//value is not null
				result = AESUtils.decrypt(value,key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
