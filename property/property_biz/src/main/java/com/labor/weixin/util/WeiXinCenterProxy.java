package com.labor.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.labor.weixin.api.HttpRequest;


/**
 * <b>微信中控代理</b><br>
 * 全局缓存access_tocken和jsapi_ticket
 * 
 * @ClassName:WeiXinCenterProxy.java
 * @ClassPath:com.cdmedia.reserve.util
 * @Author: robin
 * @Date: 2015-4-25 下午03:59:20
 * @Version:2.0
 */
public class WeiXinCenterProxy {

	private static Logger log = LoggerFactory
			.getLogger(WeiXinCenterProxy.class);

	private static int accessCount = 0;

	/**
	 * 获取access_token,失效后会重新请求，保证拿到的access_token是有效的。
	 * 
	 * @datetime:2015-4-25 下午04:22:06
	 * @Author: robin
	 * @param: @param appid
	 * @param: @param appsecret
	 * @return String
	 */
	public static String getAccessToken() {
		// 验证当前acessToken是否过期
		boolean flag = AccessToken.isTimeout();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (flag) {
			String appid = WeiXinConfigure.APPID;
			String appsecret = WeiXinConfigure.APPSECRET;
			String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
			String requestUrl = access_token_url.replace("APPID", appid)
					.replace("APPSECRET", appsecret);
			JSONObject jsonObject = HttpRequest.httpRequest(requestUrl, "GET",
					null);

			if (null != jsonObject && jsonObject.containsKey("access_token")) {
				// 已重新获取到accessToken
				log.info(format.format(new Date()) + "accessToken请求成功，请求次数"
						+ accessCount);
				AccessToken.starttime = new Date();
				AccessToken.accessToken = jsonObject.getString("access_token")
						.toString();
				AccessToken.expiresIn = jsonObject.getLong("expires_in");

			} else {
				log.info(format.format(new Date()) + "accessToken请求失败，请求次数"
						+ accessCount);
				// accessToken获取失败
				AccessToken.starttime = null;
				AccessToken.accessToken = "";
				AccessToken.expiresIn = 0;
				accessCount++;
				// 重新获取ticket
				if (accessCount < 5) {
					return getAccessToken();
				}
			}
		} else {
			log.info(format.format(new Date()) + "accessTocken请求正常");
		}
		accessCount = 0;
		return AccessToken.accessToken;
	}

	private static int ticketCount = 0;

	/**
	 * 获取jsapi_ticket,失效后会重新请求，保证拿到的jsapi_ticket是有效的。
	 * 
	 * @datetime:2015-4-26 下午02:32:42
	 * @Author: robin
	 * @param: @return
	 * @return String
	 */
	public static String getJSApiTicket() {
		// 验证当前ticket是否过期
		boolean flag = JsApiTicket.isTimeout();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (flag) {
			// 当前ticket已失效，需要重新请求
			String token = getAccessToken();
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ token + "&type=jsapi";
			JSONObject jsonObject = HttpRequest.httpRequest(requestUrl,
					HttpRequest.GET, null);
			if (null != jsonObject && jsonObject.containsKey("ticket")) {
				// ticket获取成功
				log.info("ticket获取成功，获取次数" + ticketCount);
				JsApiTicket.starttime = new Date();
				JsApiTicket.errcode = jsonObject.getString("errcode")
						.toString();
				JsApiTicket.errmsg = jsonObject.getString("errmsg").toString();
				JsApiTicket.ticket = jsonObject.getString("ticket").toString();
				JsApiTicket.expiresIn = jsonObject.getLong("expires_in");
			} else {
				// ticket获取失败，重新获取
				log.info("ticket获取失败，获取次数" + ticketCount);
				JsApiTicket.starttime = null;
				JsApiTicket.errcode = "";
				JsApiTicket.errmsg = "";
				JsApiTicket.ticket = "";
				JsApiTicket.expiresIn = 0;
				ticketCount++;
				// 重新获取ticket
				if (ticketCount < 5) {
					return getJSApiTicket();
				}
			}
		} else {
			log.info(format.format(new Date()) + "jspapi:ticket有效");
		}
		ticketCount = 0;
		// 当前ticket未失效，直接获取
		return JsApiTicket.ticket;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			String token = WeiXinCenterProxy.getAccessToken();
			System.out.println(token);
			System.out.println("------------------------");
			String ticket = WeiXinCenterProxy.getJSApiTicket();
			System.out.println(ticket);
		}
	}

}