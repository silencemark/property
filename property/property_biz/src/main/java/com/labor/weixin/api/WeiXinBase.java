package com.labor.weixin.api;

import net.sf.json.JSONObject;

public class WeiXinBase {

	protected static JSONObject doPost(String url, String postParam) {
		System.out.println("url="+url);
		System.out.println("postParam="+postParam);
		JSONObject jsonObject = HttpRequest.httpRequest(url, HttpRequest.POST,postParam);
		return jsonObject;
	}

	protected static JSONObject doGet(String url) {
		JSONObject jsonObject = HttpRequest.httpRequest(url, HttpRequest.GET,null);
		return jsonObject;
	}

}
