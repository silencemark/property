package com.hoheng.tool.baidupush;

public class BaiduPushConfig {
	
	public final static String APP_ID = "7882662";
	public final static String API_KEY = "CF8hV006GGbhVadi1ymWDr0h";
	public final static String SECRET_KEY = "0L30bom767rzvd12vOWQI0MOslUSdwQV";

	public static final String API_KEY_IOS = "UksaM2ah0mS9zmatwFUbbNAq";
	public static final String SECRET_KEY_IOS = "GorYZ1SYYbdfnXloIGvTGY6vHAfAKMFg";
	public final static String DEFAULT_TITLE = "嘀嗒叫人";
	/*
	 * deviceType => 1 for web, 2 for pc, 
	 *3 for android, 4 for ios, 5 for wp
	 */
	public final static Integer DEVICE_TYPE_WEB = 1;
	/*
	 * deviceType => 1 for web, 2 for pc, 
	 *3 for android, 4 for ios, 5 for wp
	 */
	public final static Integer DEVICE_TYPE_PC = 2;
	/*
	 * deviceType => 1 for web, 2 for pc, 
	 * 3 for android, 4 for ios, 5 for wp
	 */
	public final static Integer DEVICE_TYPE_ANDROID = 3;
	/*
	 * deviceType => 1 for web, 2 for pc, 
	 * 3 for android, 4 for ios, 5 for wp
	 */
	public final static Integer DEVICE_TYPE_IOS = 4;
	/*
	 * deviceType => 1 for web, 2 for pc, 
	 * 3 for android, 4 for ios, 5 for wp
	 */
	public final static Integer DEVICE_TYPE_WP = 5;
	// 0表示透传消息,1表示通知,默认为0
	public final static Integer MESSAGE_TYPE_NOTICE = 1;
	// 0表示透传消息,1表示通知,默认为0
	public final static Integer MESSAGE_TYPE_TRANSPARENT = 0;

	/*
	 * 消息过期默认时间，单位为秒。
	 */
	public final static Integer MSGEXPIRES_DEFAULT = 3600*5;
	
}
