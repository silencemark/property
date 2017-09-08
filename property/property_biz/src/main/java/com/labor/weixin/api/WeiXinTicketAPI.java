package com.labor.weixin.api;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.labor.weixin.util.WeiXinCenterProxy;


/**
 * @ClassName:WeiXinTicketAPI.java
 * @ClassPath:com.cdmedia.reserve.api
 * @Desciption:微信带参数二维码的接口
 * @Author: robin
 * @Date: 2015-4-11 下午05:33:37
 * 
 */
public class WeiXinTicketAPI extends WeiXinBase {

	/**
	 * 获取永久ticket
	 * 
	 * @datetime:2015-4-11 下午05:35:45
	 * @Author: robin
	 * @return String
	 */
	private static JSONObject getPermanentTicket(Map<String, Object> data) {
		String tokenid = WeiXinCenterProxy.getAccessToken();
		StringBuffer temp = new StringBuffer(
				"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=");
		temp.append(tokenid);

		// 获取post请求的参数
		JSONObject sendJson = new JSONObject();
		// {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene":
		// {"scene_id": 123}}}
		
//		{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
		sendJson.put("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject scene = new JSONObject();
		String qrcode = MapUtils.getString(data, "qrcode");
		scene.put("scene_str", qrcode);
		JSONObject actioninfo = new JSONObject();
		actioninfo.put("scene", scene);
		sendJson.put("action_info", actioninfo);

		JSONObject ticketResultJson = doPost(temp.toString(), sendJson.toString());
		// {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==","expire_seconds":60,"url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"}
		System.out.println(ticketResultJson);
		return ticketResultJson;
	}

}
