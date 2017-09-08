package com.lr.backer.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	public static final String DEFAULT_SECRET = "12345678";

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
			
			SecretKey secretKey = getSecretKey(password) ;
			
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @function:对字符串进行加密
	 * @datetime:2014-12-30 上午11:40:38
	 * @Author: robin
	 * @param: @param content
	 * @return String
	 */
	public static String defaultEncrypt(String content) {
		byte[] encryptResult = encrypt(content, DEFAULT_SECRET);
		return parseByte2HexStr(encryptResult);
	}
	
	private static SecretKey getSecretKey(String password){
		/*SecretKey secretKey = null ;
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");  
		    DESKeySpec keySpec = new DESKeySpec(password.getBytes());  
		    keyFactory.generateSecret(keySpec);  
		    secretKey = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(secretKey);
		return secretKey ;
		*/
		
		/*SecureRandom secureRandom = null ;
		KeyGenerator kg = null;  
	    try {  
	    	secureRandom = SecureRandom.getInstance("SHA1PRNG");  
		    secureRandom.setSeed(password.getBytes());  
		    // 为我们选择的DES算法生成一个KeyGenerator对象  
	        kg = KeyGenerator.getInstance("AES");  
	    } catch (NoSuchAlgorithmException e) {  
	    }  
	    kg.init(secureRandom);  
	    //kg.init(56, secureRandom);  
	      
	    // 生成密钥  
	    return kg.generateKey();*/
		
		/**
		 SecureRandom 实现完全随操作系统本身的內部状态，除非调用方在调用 getInstance 方法之后又调用了 setSeed 方法；
		 该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同。 
		 */
		try {
		      KeyGenerator _generator=KeyGenerator.getInstance("AES");
		      SecureRandom secureRandom=
		      SecureRandom.getInstance("SHA1PRNG");
		      secureRandom.setSeed(password.getBytes());
		      _generator.init(128,secureRandom);
		      return _generator.generateKey();
	    }  catch (Exception e) {
	    	  throw new RuntimeException("初始化密钥出现异常");
	    }
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			//1. SecureRandom 实现完全随操作系统本身的內部状态，除非调用方在调用 getInstance 方法，然后调用 setSeed 方法；
			// 该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同。
			/* KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();*/
			
			//2. 
			SecretKey secretKey = getSecretKey(password) ;
			
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @function:默认解密
	 * @datetime:2014-12-30 上午11:42:28
	 * @Author: robin
	 * @param: @param value
	 * @return String
	 */
	public static String defaultDecrypt(String value) {
		// 解密
		byte[] tt2 = parseHexStr2Byte(value);
		byte[] decryptResult = decrypt(tt2, DEFAULT_SECRET);
		return new String(decryptResult);
	}
	
	
	
	

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt2(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		String content = "test";
		String password = "12345678";
		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content, password);
		String string = parseByte2HexStr(encryptResult);
		
		System.out.println("加密后："+string);
		// 解密
		byte[] tt2 = parseHexStr2Byte(string);
		byte[] decryptResult = decrypt(tt2, password);
		System.out.println("解密后：" + new String(decryptResult));
		
		
		String content2 = "tesdsfsdft";
		// 加密
		System.out.println("加密前：" + content2);
		string = defaultEncrypt(content2);
		
		System.out.println("加密后："+string);
		// 解密
		System.out.println("解密后：" + defaultDecrypt(string));
	}

}
