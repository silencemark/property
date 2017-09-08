package com.lr.labor.weixin.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.http.client.ClientProtocolException;

import com.lr.backer.util.FileDeal;
import com.lr.labor.weixin.util.WeiXinCenterProxy;

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

	/**
	 * 生成微信二维码
	 * @function:
	 * @datetime:2015-4-11 下午06:28:41
	 * @Author: robin
	 * @return void
	 */
	public static Map<String, Object> createWeixinCode(Map<String, Object> data,String basepath) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 利用永久二维码获取方式获取ticket
		JSONObject ticketJson = getPermanentTicket(data);
		if (null != ticketJson && ticketJson.containsKey("ticket")) {
			// 接口调用成功
			String ticket = ticketJson.getString("ticket");
			String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
					+ URLEncoder.encode(ticket);

			//要保存的文件
			String fileName = FileDeal.getUUID() ;
			String qrcodepath = "/qrcode/"+fileName+".png";
			try {
				FileOutputStream out = new FileOutputStream(basepath+qrcodepath);
				HttpRequest.httpRequestDownload(url, out);
				map.put("ticket", ticket);
				map.put("qrcodepath", qrcodepath);
				return map ;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// 接口调用失败
			map = null;
		}
		return map ;
	}

	public static void main(String[] args) {
//		createWeixinCode("");

	}

}
