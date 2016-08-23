package org.light4j.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES�ӽ��ܹ�����
 * 
 * @author longjiazuo
 */
class DESUtils {
	/** Ĭ��key */
	protected final static String KEY = "ScAKC0XhadTHT3Al0QIDAQAB";
	
	/**
	 * DES����
	 * 
	 * @param data
	 * 				�������ַ���
	 * @param key
	 * 				У��λ
	 * @return
	 */
    protected static String encrypt(String data,String key) {  
        String encryptedData = null;  
        try {  
            // DES�㷨Ҫ����һ�������ε������Դ  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(key.getBytes());  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����һ��SecretKey����  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(deskey);  
            // ���ܶ���  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);  
            // ���ܣ������ֽ����������ַ���  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
        } catch (Exception e) {  
            throw new RuntimeException("���ܴ��󣬴�����Ϣ��", e);  
        }  
        return encryptedData;  
    }  
	
    /**
     * DES����
     *
     * @param cryptData
     * 						����������
     * @param key
     * 						У��λ
     * @return
     */
    protected static String decrypt(String cryptData,String key) {  
        String decryptedData = null;  
        try {  
            // DES�㷨Ҫ����һ�������ε������Դ  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(key.getBytes());  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����һ��SecretKey����  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(deskey);  
            // ���ܶ���  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);  
            // ���ַ�������Ϊ�ֽ����飬������  
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));  
        } catch (Exception e) {  
            throw new RuntimeException("���ܴ��󣬴�����Ϣ��", e);  
        }  
        return decryptedData;  
    }  
	     
}
