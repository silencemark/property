package com.hoheng.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpHeaderUtil {

	private static final String USER_AGENT = "user-agent";

	private static final String WEIXIN = "MicroMessenger";
	private static final String ANDROID = "android";
	private static final String IOS = "iphone";

	private static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}

	private static String getHeader(HttpServletRequest request) {
		return request.getHeader(USER_AGENT);
	}

	/**
	 * 判断是否是从微信点击的
	 * @param request
	 * @return
	 */
	public static boolean isWeiXinFrom(HttpServletRequest request) {
		String header = getHeader(request);
		return regex(WEIXIN, header);
	}
	
	/**
	 * 判断是否来自android
	 * @param request
	 * @return
	 */
	public static boolean isAndroidFrom(HttpServletRequest request) {
		String header = getMobileAgent(request);
		if(StringUtils.isEmpty(header)){
			return true;
		}
		return header.equals(ANDROID);
	}
	/**
	 * 判断是否来自ios
	 * @param request
	 * @return
	 */
	public static boolean isIOSFrom(HttpServletRequest request) {
		String header = getMobileAgent(request);
		if(StringUtils.isEmpty(header)){
			return true;
		}
		return header.equals(IOS);
	}
	
	private static String getMobileAgent(HttpServletRequest request) {
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					return mobileAgent;
				}
			}
		}
		return null;
	}     
}
