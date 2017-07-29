package com.wh.whtth.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

/**
 *
 * PBE 对称加解密
 * 密码反编译在这里
 */
public class EncryptUtil {
    private static Logger logger = Logger.getLogger(EncryptUtil.class);
	private static String ARITHMETIC = "PBEWithSHA1AndDESede";
	private static String SALTSTRING = "whtth123"; 
	private static String PASSWORD = "wh_private_key";
	private static int ITERATION_COUNT = 100;
	public static void main(String[] args) {
		String psd = "sysadmin";
		String a = PBEEncrypt(psd);
		System.out.println(a);
		String b = PBEDecrypt("6d7d070f654b1d218b2ea1abd261bcd6");
		System.out.println(b);
	}
	
	public static String PBEEncrypt(String src){		
		try {
			byte[] salt = SALTSTRING.getBytes();
			PBEKeySpec pbeKeySpec = new PBEKeySpec(PASSWORD.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ARITHMETIC);
			Key key = factory.generateSecret(pbeKeySpec);
			//加密
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ARITHMETIC);
			cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
			byte[] result = cipher.doFinal(src.getBytes());
			return new String(Hex.encodeHex(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String PBEDecrypt(String src){
		byte[] salt = SALTSTRING.getBytes();
		PBEKeySpec pbeKeySpec = new PBEKeySpec(PASSWORD.toCharArray());
		SecretKeyFactory factory;
		try {
			byte[] result = Hex.decodeHex(src.toCharArray());
			factory = SecretKeyFactory.getInstance(ARITHMETIC);
			Key key = factory.generateSecret(pbeKeySpec);
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ARITHMETIC);
			cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
			//解密
			cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
			result = cipher.doFinal(result);
			return new String(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
