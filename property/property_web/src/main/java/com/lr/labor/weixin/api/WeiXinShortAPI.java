package com.lr.labor.weixin.api;

import java.net.URLEncoder;

import net.sf.json.JSONObject;

import com.lr.labor.weixin.util.WeiXinCenterProxy;

/**
 * @ClassName:WeiXinShortAPI.java
 * @ClassPath:com.cdmedia.reserve.api
 * @Desciption:微信短链接的工具类
 * @Author: robin
 * @Date: 2015-7-26 下午3:30:49
 * 
 */
public class WeiXinShortAPI extends WeiXinBase {
	
	
	private static final String short_url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=";
	
	
	/**
	 * @function:生成短链接的url
	 * @datetime:2015-7-26 下午3:34:04
	 * @Author: robin
	 * @param: @param longUrl 长链接的地址
	 * @param: @return
	 * @return JSONObject
	 */
	public static String createShortUrl(String longUrl) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(short_url);
		String tokenid = WeiXinCenterProxy.getAccessToken();
		sbf.append(tokenid);
		
		//生成POST请求的参数
		JSONObject sendJson = new JSONObject();
		sendJson.put("action", "long2short");
		sendJson.put("long_url", longUrl);
		
		//发送请求
		JSONObject resultJson = doPost(sbf.toString(), sendJson.toString());
		if(resultJson.containsKey("errcode")){
			String errcode = resultJson.getString("errcode");
			if(null != errcode && "0".equals(errcode)){
				//正确返回短链接
				return resultJson.getString("short_url");
			}
		}
		return null ;
	}

}
