package com.lr.labor.weixin.util;

import java.util.Date;

/**
 * @ClassName:JsApiTicket.java
 * @ClassPath:com.cdmedia.reserve.util
 * @Desciption:TODO
 * @Author: robin
 * @Date: 2015-4-25 下午03:04:39
 * 
 */
public class JsApiTicket {

	public static String errcode = "";

	public static String errmsg = "";

	public static String ticket = "";

	// 有效时间，单位秒；默认7200
	public static long expiresIn = 7200;

	// 创建时间；用于判断jsapi_ticket是否过期
	public static Date starttime;

	/**
	 * 判断当前ticket是否失效 <br>
	 * true:失效<br>
	 * false：未失效
	 * 
	 * @datetime:2015-4-25 下午03:11:18
	 * @Author: robin
	 * @return boolean
	 */
	public static boolean isTimeout() {
		// 失效
		if (null == ticket || "".equals(ticket)) {
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
			// ticket过期，需要重新请求
			return true;
		}
		return false;
	}

}
