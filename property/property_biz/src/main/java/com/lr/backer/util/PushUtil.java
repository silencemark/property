package com.lr.backer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 


import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class PushUtil {

	private static String appId = "7882662";
	private static String appkey = "CF8hV006GGbhVadi1ymWDr0h";
	private static String master = "I7FtfRDJCS7FCcZQ35cfN";
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	private static String LogoUrl = "http://www.doctorjin.net/logo_min.jpg";
  
	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		/*Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("device", "IOS");
		map1.put("cid", "5276400098609349344");
	    list.add(map1);
	    Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("device", "IOS");
		map2.put("cid", "5330362087073915072");
	    list.add(map2);
	    Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("device", "Android");
		map3.put("cid", "664efebb3cbd0124f08071ba45ab6b36");
	    list.add(map3);*/ 
	    Map<String, Object> map4 = new HashMap<String, Object>();
	   /* map4.put("device", "ios");
		map4.put("cid", "4614690643311065677");
	    list.add(map4);*/
	    map4.put("device", "Android");
		map4.put("cid", "3965443519988614579");
	    list.add(map4); 
	    
	    push("text", "ccontent", null, "2a93c450b2271492f44cd3e8f5c33553");
	    
		//pushList("批量推送测试", "收到==>批量推送测试",list);
	}
	
	/**
	 * 推送
	 * @param title  标题
	 * @param text   内容
	 * @param url    网址
	 * @param cid    app cid
	 * @param cid    app Android IOS
	 * @return
	 * @throws Exception
	 */
	public static int push(Object title, Object text, String url,
			Object cid,Object device) throws Exception{
		System.out.println("机型："+device);
		if(device.toString().equals("Android")){       
			return push(title, text, url, cid);
		}else{
			return PushMsgToSingleDevice.baidupush(title,text,cid);			
		} 
	}
	/**
	 * 批量推送到IOS设备
	 * @param title
	 * @param text
	 * @param url
	 * @param cid
	 * @return
	 */
	public static int pushIOSList(Object title, Object text, String[] cid) throws Exception{
		System.out.println("机型：IOS");
		System.out.println("百度推送暂时不支持IOS批量推送，则将批量分为单个推送");
		for(String c:cid){
			int i = PushMsgToSingleDevice.baidupush(title, text, c);
		}
		return 0;
	}
	/**
	 * 批量推送到Android设备
	 * @param title  标题
	 * @param text   内容
	 * @param url    地址
	 * @param cid    Android 手机标识
	 * @return
	 */
	public static int pushAndroidList(Object title, Object text,String url,String[] cid) throws Exception{
		System.out.println("机型：Android");
		String str = AppPasstHroughlist(title, text,url, cid);
		if(str.equals("ok")){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 批量推送
	 * @param title
	 * @param text
	 * @param userlist
	 * @return
	 * @throws Exception 
	 */
	public static int pushList(Object title, Object text,List<Map<String, Object>> userlist) throws Exception{
		String []str = {} ;
		String []str2 = {} ;
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for(Map<String, Object> map:userlist){
			try {
				if(map.get("device").toString().equals("Android")){
					 list1.add(map.get("cid").toString());
				 }else{
					 list2.add(map.get("cid").toString());
				 }
			} catch (Exception e) { }
		}
		str = list1.toArray(str);
		str2 = list2.toArray(str2);
		if(list1.size()>0){//Android
			return pushAndroidList(title, text,null, str);
		}else{//IOS
			return pushIOSList(title, text, str2);
		}
	}
	/**
	 * 批量推送
	 * @param title
	 * @param text
	 * @param url
	 * @param userlist
	 * @return
	 * @throws Exception
	 */
	public static int pushList(Object title, Object text,String url,List<Map<String, Object>> userlist) throws Exception{
		String []str = {} ;
		String []str2 = {} ;
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for(Map<String, Object> map:userlist){
			try {
				if(map.get("device").toString().equals("Android")){
					 list1.add(map.get("cid").toString());
				 }else{
					 list2.add(map.get("cid").toString());
				 }
			} catch (Exception e) { }
		}
		str = list1.toArray(str);
		str2 = list2.toArray(str2);
		if(list1.size()>0){//Android
			return pushAndroidList(title, text,url, str);
		}else{//IOS
			return pushIOSList(title, text, str2);
		}
	}
	/**
	 * 获取用户APP状态
	 */
	public static int getUserStatus(String cid) {
	    IGtPush push = new IGtPush(host, appkey, master);
	    IQueryResult abc = push.getClientIdStatus(appId, cid);
	    System.out.println(abc.getResponse());
	    Map<String, Object> resultMap = abc.getResponse();
	    if("Offline".equals(resultMap.get("result").toString())){//离线
	    	return 1;
	    }else if("Online".equals(resultMap.get("result").toString())){//在线
	    	return 2;
	    }else{ //有误
	    	return 0;
	    } 
	}
	/**
	 * andorid 推送
	 * @param title
	 * @param text
	 * @param url
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public static int push(Object title, Object text, String url,
			Object cid) throws Exception{
		int status = getUserStatus(cid.toString());//用户app状态
		if(status==0){
			throw new Exception("error"); 
		}else if(status==1){ //不在线     打开app
			String str = AppPasstHrough(title, text, url, cid,1);
			if(str.equals("ok")){
				return 1;
			}else{
				return 0;
			}
		}else if(status == 2){  //在线   不用打开app
			//String str = GeneralNotice(title, text, url, cid);
			String str = AppPasstHrough(title, text, url, cid,0);  
			if(str.equals("ok")){
				return 1;
			}else{
				return 0;
			}
		} 
		return 0;
	}
	/**
	 * 透传推送
	 * @param title  通知栏标题
	 * @param text   通知栏内容
	 * @param url    透传内容（不可见）
	 * @param cid    用户app唯一凭证  t_dt_user 中 clientId 字段 
	 * @return
	 * @throws Exception
	 */
	public static String AppPasstHrough(Object title, Object text, String url,
			Object cid,int cont) throws Exception {
		// 配置返回每个用户返回用户状态
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IGtPush push = new IGtPush(host, appkey, master);
		// 建立连接，开始鉴权
		push.connect();
		// 通知透传模板
		NotificationTemplate template = NotificationTemplateDemo(title,text,url,cont);

		ListMessage message = new ListMessage();
		message.setData(template);

		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);

		// 配置推送目标
		List<Target> targets = new ArrayList<Target>();
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(cid.toString());
		targets.add(target1);
		// 获取taskID
		String taskId = push.getContentId(message);
		// 使用taskID对目标进行推送
		IPushResult ret = push.pushMessageToList(taskId, targets);
		// 打印服务器返回信息
		System.out.println(ret.getResponse().toString()); 
		System.out.println("===============\n\n"+msg);
		return ret.getResponse().get("result").toString();
	}
	
	public static String AppPasstHroughlist(Object title, Object text,String url, String[] cid) throws Exception{
		        for(String id:cid){
		        	push(title, text, url, cid);	
		        }
				return "ok";
		
		      /*// 配置返回每个用户返回用户状态
				System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
				IGtPush push = new IGtPush(host, appkey, master);
				// 建立连接，开始鉴权
				push.connect();
				// 通知透传模板
				NotificationTemplate template = NotificationTemplateDemo(title,text,null,1);

				ListMessage message = new ListMessage();
				message.setData(template);

				// 设置消息离线，并设置离线时间
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(24 * 1000 * 3600);

				// 配置推送目标
				List<Target> targets = new ArrayList<Target>();
				for (String cidstr:cid) {
					Target target1 = new Target();
					target1.setAppId(appId);
					target1.setClientId(cidstr);
					targets.add(target1);
				} 
				// 获取taskID
				String taskId = push.getContentId(message);
				// 使用taskID对目标进行推送
				IPushResult ret = push.pushMessageToList(taskId, targets);
				// 打印服务器返回信息
				System.out.println(ret.getResponse().toString()); 
				System.out.println("===============\n\n"+msg);
				return ret.getResponse().get("result").toString(); */
	}
	
	/**
	 * 普通通知
	 * @return
	 * @throws Exception 
	 */
	/*public static String GeneralNotice(Object title, Object text, Object url,
			Object cid) throws Exception{
		IGtPush push = new IGtPush(host, appkey, master);
		push.connect(); 
		LinkTemplate template = linkTemplateDemo(title, text, url);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
                //离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template); 
		Target target1 = new Target(); 

		target1.setAppId(appId);
		target1.setClientId(cid.toString());

		IPushResult ret = push.pushMessageToSingle(message, target1);
		System.out.println(ret.getResponse().toString());
		return ret.getResponse().get("result").toString();
	}*/
	 
	/**
	 * 
	 * 普通通知 模版
	 * 
	 * @return
	 */
	/*public static LinkTemplate linkTemplateDemo(Object title,
			Object text, Object url) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		// 设置通知栏标题与内容
		template.setTitle(title==null||title.toString().equals("")?"金童医生":title.toString());
		template.setText(text==null||text.toString().equals("")?"金童医生":text.toString());
		// 配置通知栏图标
		template.setLogo(LogoUrl);
		// 配置通知栏网络图标
		template.setLogoUrl(LogoUrl);
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);   
		// 设置打开的网址地址
		template.setUrl("myapp://doctorjin/openwith?from=data");
		return template;
	}*/
	/**
	 * 透传
	 * 
	 * @param title
	 *            标题 0-20
	 * @param text
	 *            内容 0-50
	 * @param url
	 *            透传内容
	 * @return
	 */
	public static NotificationTemplate NotificationTemplateDemo(Object title,
			Object text, String url,int cont) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle(title==null||title.toString().equals("")?"金童医生":title.toString());
		template.setText(text==null||text.toString().equals("")?"金童医生":text.toString());
		// 配置通知栏图标
		template.setLogo(LogoUrl);
		// 配置通知栏网络图标
		template.setLogoUrl(LogoUrl);
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true); 
		// 透传消息设置   0 不打开  1  打开
		template.setTransmissionType(0);
		if(url==null){
			url="http://www.doctorjin.net/phone_im/pushindex";
		}
		template.setTransmissionContent(url);
		
		return template;
	}
	
	private static String  msg = "正确返回\n"+
    "successed_online	用户在线，消息在线下发\n"+
	"successed_offline	用户离线，消息存入离线系统\n"+
	"Ok	                             消息发送成功\n"+
	"details	                     返回用户状态的详细信息\n"+
	"contentId	              任务ID（当result值为ok时，有此字段）\n"+
	"错误返回	\n"+
	"Error	请求信息填写有误\n"+
	"action_error	未找到对应的action动作\n"+
	"appkey_error	Appkey填写错误\n"+
	"domain_error	填写的域名错误或者无法解析\n"+
	"sign_error	Appkey与ClientId不匹配，鉴权失败\n"+
	"TargetListIsNullOrSizeIs0	推送target列表为空\n"+
	"PushTotalNumOverLimit	推送消息个数总数超限\n"+
	"TokenMD5NoUsers	target列表没有有效的clientID\n"+
	"NullMsgCommon	未找到contentId对应的任务\n"+
	"TaskIdHasBeanCanceled	任务已经被取消\n"+
	"flow_exceeded	接口消息推送流量已超限\n"+
	"OtherError	无法判定错误类型\n"+
	"TokenMD5Error	clientID填写有误\n"+
	"AppidNoMatchAppKey	appid和鉴权appKey不匹配\n"+
	"AppidError	appid和clientId绑定的appid不匹配\n"+
	"SendError	消息发送错误\n"+
	"successed_ignore	无效用户，消息丢弃";
	/*
	 * 
正确返回
    successed_online	用户在线，消息在线下发
	successed_offline	用户离线，消息存入离线系统
	Ok	                             消息发送成功
	details	                     返回用户状态的详细信息
	contentId	              任务ID（当result值为ok时，有此字段）
错误返回	
	Error	请求信息填写有误
	action_error	未找到对应的action动作
	appkey_error	Appkey填写错误
	domain_error	填写的域名错误或者无法解析
	sign_error	Appkey与ClientId不匹配，鉴权失败
	TargetListIsNullOrSizeIs0	推送target列表为空
	PushTotalNumOverLimit	推送消息个数总数超限
	TokenMD5NoUsers	target列表没有有效的clientID
	NullMsgCommon	未找到contentId对应的任务
	TaskIdHasBeanCanceled	任务已经被取消
	flow_exceeded	接口消息推送流量已超限
	OtherError	无法判定错误类型
	TokenMD5Error	clientID填写有误
	AppidNoMatchAppKey	appid和鉴权appKey不匹配
	AppidError	appid和clientId绑定的appid不匹配
	SendError	消息发送错误
	successed_ignore	无效用户，消息丢弃*/
	  
}
