package com.lr.weixin.util;

import java.util.Date;

import com.labor.weixin.util.WeiXinCenterProxy;

import net.sf.json.JSONObject;


/**
 * @ClassName:WeiXinUser.java
 * @ClassPath:com.hk.reserve.main
 * @Desciption:微信粉丝工具类
 * @Author: robin
 * @Date: 2015-1-3 上午11:06:25
 * 
 */
public class WeiXinUser extends WeiXinBase {

	/**
	 * @function:获取所有粉丝，默认取出10000个，想获取下一个10000个，需要传递NEXT_OPENID
	 * @datetime:2015-1-3 上午11:12:05
	 * @Author: robin
	 * @param: @param nextOpenid
	 * @param: @return
	 * @return JSONObject
	 */
	public static JSONObject initAllUser(String nextOpenid){
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ tokenid;
		if(null!=nextOpenid && !"".equals(nextOpenid)){
			url += "&next_openid="+nextOpenid;
		}
		JSONObject resultJson = doGet(url);
		return resultJson ;
	}
	
	public static JSONObject getUser(String openid){
		String tokenid = WeiXinCenterProxy.getAccessToken();
		// 获取该openid的详细信息
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ tokenid + "&lang=zh_CN&openid=" + openid;
		JSONObject resultJson = doGet(url);
		return resultJson ;
	}
	
	public static Date getSubscribeTime(Object subscribeTimeObject){
		if(null!=subscribeTimeObject){
			long subscribeTime = Long.valueOf(subscribeTimeObject.toString());
			return new Date(subscribeTime*1000l);
		}
		return null ;
	}
	
	
	public static void main(String[] args) {
		JSONObject json = initAllUser(null);
		System.out.println(json);
		
		json = initAllUser("oYINutw5DoYAYajTu0VOCUKsfqaA");
		System.out.println(json);
		
		json = getUser("oYINutw5DoYAYajTu0VOCUKsfqaA");
		System.out.println(json);
	}
	
}