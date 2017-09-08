package com.lr.backer.util;

import java.util.logging.Logger;

public class WeiXinSetUtil {
	
	
	// 明文密钥
	public static final String SECRET_KEY = "12345678";

	/**
	 * @function:对id进行加密
	 * @datetime:2014-12-30 上午10:07:29
	 * @Author: robin
	 * @param: @param value
	 * @return String
	 */
	public static String getSecretID(String value) {
		value += SECRET_KEY;
		try {
			return AESUtil.defaultEncrypt(value);
		} catch (Exception e) {
			System.out.println("加密失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	
}
