package com.labor.weixin.api;

import com.labor.weixin.util.WeiXinCenterProxy;

import net.sf.json.JSONObject;

/**
 * @ClassName:WeiXinGroup.java
 * @ClassPath:com.hk.reserve.main
 * @Desciption:微信分组工具类
 * @Author: robin
 * @Date: 2015-1-3 上午11:05:53
 * 
 */
public class WeiXinGroupAPI extends WeiXinBase {
	
	//添加分组
	public static String addGroup(String groupname){
		JSONObject groupJson = new JSONObject();
		groupJson.put("name", groupname);
		JSONObject t1 = new JSONObject();
		t1.put("group", groupJson);
		
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token="
				+ tokenid;
		
		JSONObject resultJson = doPost(url, t1.toString());
		
		//+"&name="+groupname;
		String result = resultJson.toString(); //MenuManager.get(url);
		System.out.println(result);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("group")) {
			String group = jsonobj.getString("group");// 获取字符串。
			JSONObject jsonopenid = JSONObject.fromObject(group);
			System.out.println(jsonopenid.get("id"));
			System.out.println(jsonopenid.get("name"));
			return jsonopenid.getString("id");
		}
		return null ;
	}
	//修改分组
	public static boolean updateGroup(String id,String groupname){
		JSONObject groupJson = new JSONObject();
		groupJson.put("name", groupname);
		groupJson.put("id", id);
		JSONObject t1 = new JSONObject();
		t1.put("group", groupJson);
		
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token="
				+ tokenid;
		
		JSONObject resultJson = doPost(url, t1.toString());
		
		//+"&name="+groupname;
		String result = resultJson.toString(); //MenuManager.get(url);
		System.out.println(result);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("errmsg")) {
			String errmsg = jsonobj.getString("errmsg");// 获取字符串。
			return "ok".equals(errmsg);
		}
		return false ;
	}
	
	//初始化查询所有分组
	public static JSONObject initAllGroup(){
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="
				+ tokenid;
		JSONObject resultJson = doGet(url);
		System.out.println(resultJson);
		return resultJson ;
	}
	//查询粉丝的分组
	public static String getMemberGroup(String openid){
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="
				+ tokenid;
		JSONObject param = new JSONObject();
		param.put("openid", openid);
		JSONObject resultJson = doPost(url,param.toString());
		System.out.println(resultJson);
		if(resultJson.containsKey("errcode")){
			// invalid openid
			return null;
		}else{
			return resultJson.getString("groupid");
		}
	}
	
	//更新粉丝的分组
	public static boolean updateMemberGroup(String openid,String groupid){
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="
				+ tokenid;
		JSONObject param = new JSONObject();
		param.put("openid", openid);
		param.put("to_groupid", groupid);
		JSONObject resultJson = doPost(url,param.toString());
		System.out.println(resultJson);
		String errmsg = resultJson.getString("errmsg");
		if("ok".equals(errmsg)){
			return true ;
		}
		return false ;
	}
	
	
	public static String deleteGroup(String groupname){
		JSONObject groupJson = new JSONObject();
		groupJson.put("name", groupname);
		JSONObject t1 = new JSONObject();
		t1.put("group", groupJson);
		
		String tokenid = WeiXinCenterProxy.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token="
				+ tokenid;
		
		JSONObject resultJson = doPost(url, t1.toString());
		
		//+"&name="+groupname;
		String result = resultJson.toString(); //MenuManager.get(url);
		System.out.println(result);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("group")) {
			String group = jsonobj.getString("group");// 获取字符串。
			JSONObject jsonopenid = JSONObject.fromObject(group);
			System.out.println(jsonopenid.get("id"));
			System.out.println(jsonopenid.get("name"));
			return jsonopenid.getString("id");
		}
		return null ;
	}

	public static void main(String[] args) {
		//String id = WeiXinGroup.addGroup("tttt");
		//System.out.println(id);
		initAllGroup();
		getMemberGroup("oYINut11s9UQEG4GX7nwsDyqUO2I");
	}
	
}
