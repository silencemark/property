package com.lr.labor.weixin.util;

import java.util.Date;

/**
 * access_token
 * 
 * @ClassName:AccessToken.java
 * @ClassPath:com.cdmedia.reserve.util
 * @Author: robin
 * @Date: 2015-4-25 下午04:14:02
 * 
 */
public class AccessToken {

	public static String accessToken = "";

	// 有效时间，单位秒；默认7200
	public static long expiresIn = 7200;

	// 创建时间；用于判断accessToken是否过期
	public static Date starttime;

	/**
	 * 判断当前accessToken是否失效 <br>
	 * true:失效<br>
	 * false：未失效
	 * 
	 * @datetime:2015-4-25 下午03:11:18
	 * @Author: robin
	 * @return boolean
	 */
	public static boolean isTimeout() {
		// 失效
		if (null == accessToken || "".equals(accessToken)) {
			return true;
		}
		// 失效
		if (null == starttime) {
			return true;
		}
		// 失效
		if (expiresIn < 0) {
			return true;
		}
		Date current = new Date();
		long timestamp = current.getTime() - starttime.getTime();

		// 缓冲时间；10分钟
		long temp = 10*60;
//		long temp = 110*60;

		if (timestamp / 1000.0 > expiresIn - temp) {
			// accessToken过期，需要重新请求
			return true;
		}
		return false;
	}

}
