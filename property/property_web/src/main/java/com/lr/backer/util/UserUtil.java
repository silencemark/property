package com.lr.backer.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lr.backer.redis.RedisUtil;

public class UserUtil {
	public final static String USERINFO = "USERSESSIONID";  		//用户信息
	
	public final static String MENUINFO = "MENUSESSIONID";			//菜单信息
	
	public final static String FROMINFO = "FROMSESSIONID";  		//用户登陆来源     
	
	public final static String OPERATINGTIME = "OPERATINGTIME";	 	//最后操作系统时间
	
	public final static String IDENTITY = "IDENTITY";				//登陆者身份 
	
	public final static String URL = "URL";							//登陆者访问路径记录

	public final static String WEIXININFO = "WEIXINSESSIONID";  		//用户信息
	
	/**
	 * 微信cookie中的微信用户的信息
	 * @param request
	 * @param objStr 信息中的key 
	 * @param obj 信息中的value
	 */
	public static void pushOpenid(HttpServletRequest request,String objStr,String obj){
		 Map<String,Object> userMap = new HashMap<String,Object>();
		 Cookie c=CookieUtil.getCookie(request, UserUtil.WEIXININFO);
		 String d= "";
		 if(c != null){
			 CookieUtil.synchronousCookie(c);
			 d=c.getValue();
			 if(d != null){
				 userMap=RedisUtil.getMap(d);
			 }
		 }
		 userMap.put(objStr, obj);
		 RedisUtil.setMap(d, userMap);
	}
	
	/**
	 * 更新用户新消息
	 * @param request 
	 * @param objStr  
	 * @param obj 
	 */
	public static void pushUser(HttpServletRequest request,String objStr,String obj){
		 Map<String,Object> userMap = new HashMap<String,Object>();
		 Cookie c=CookieUtil.getCookie(request, UserUtil.USERINFO);
		 String d= "";
		 if(c != null){
			 CookieUtil.synchronousCookie(c);
			 d=c.getValue();
			 if(d != null){
				 userMap=RedisUtil.getMap(d);
			 }
		 }
		 userMap.put(objStr, obj);
		 RedisUtil.setMap(d, userMap);
	}
	/**
	 * 获取当前登陆者信息
	 * @param request
	 * @return
	 */
	 public static Map<String,Object> getUser(HttpServletRequest request){
		 
		 Map<String,Object> userMap = new HashMap<String,Object>();
		 Cookie c=CookieUtil.getCookie(request, UserUtil.USERINFO);
		 if(c != null){
			 CookieUtil.synchronousCookie(c);
			 String d=c.getValue();
			 if(d != null){
				 userMap=RedisUtil.getMap(d);
			 }
		 }
		 return userMap;
	 }
	 
	 /**
		 * 获取当前微信登录人信息
		 * @param request
		 * @return
		 */
		 public static Map<String,Object> getOpenid(HttpServletRequest request){
			 
			 Map<String,Object> userMap = new HashMap<String,Object>();
			 Cookie c=CookieUtil.getCookie(request, UserUtil.WEIXININFO);
			 if(c != null){
				 CookieUtil.synchronousCookie(c);
				 String d=c.getValue();
				 if(d != null){
					 userMap=RedisUtil.getMap(d);
				 }
			 }
			 return userMap;
		 }
		 
	/**
	 *  类似session存储用户信息
	 * @param response
	 * @param map				存储的用户信息
	 * @param age				过期时间
	 */
	 public static void setUser(HttpServletResponse response,String userId,Map<String,Object> map,String age){
		 String cookiesids = System.currentTimeMillis()+"_"+userId;
		 CookieUtil.setServerCookie(response, UserUtil.USERINFO, cookiesids, "/", age, null);
		 RedisUtil.setMap(cookiesids, map, age);
	 }
	 
	 public static void setOpenid(HttpServletResponse response,String openid,Map<String,Object> map,String age){
		 String cookiesids = System.currentTimeMillis()+"_"+openid;
		 System.out.println("保存的cookieid="+cookiesids);
		 CookieUtil.setCookie(response, UserUtil.WEIXININFO, cookiesids, "/", age);
		 RedisUtil.setMap(cookiesids, map, age);
	 }
	 
	 /**
	  * 根据cookieids获取当前登录者信息
	  * @param cookieids
	  * @return
	  */
	 public static Map<String,Object> getUserByCookie(String cookieids){
		 Map<String,Object> userMap = new HashMap<String,Object>();
		 if(cookieids != null){
			 userMap=RedisUtil.getMap(cookieids);
		 }
		 return userMap;
	 }
	 /**
	  * 获取当前登陆者菜单权限
	  * @param request
	  * @return
	  */
	 public static Map<String,Object> getMenu(HttpServletRequest request){
		 Cookie c=CookieUtil.getCookie(request,UserUtil.USERINFO);
		 if(c != null){
			 String d=c.getValue();
			 if(d != null ){
				 return RedisUtil.getMap(d+"_menu");
			 }
		 }
		 return null;
	 }
	 /**
	  * 用户退出,删除redis中信息和cookies信息
	  * @param request
	  * @param response
	  */
	 public static void deleteUser(HttpServletRequest request,HttpServletResponse response){
		 Cookie usercookie = CookieUtil.getCookie(request,UserUtil.USERINFO);
		 if(usercookie != null ){
			 RedisUtil.remove(usercookie.getValue());
			 CookieUtil.deleteCookie(response, usercookie, "/");
		 }
		 Cookie menucookie=CookieUtil.getCookie(request,UserUtil.MENUINFO);
		 if(menucookie != null ){
			 RedisUtil.remove(menucookie.getValue());
			 CookieUtil.deleteCookie(response, menucookie, "/");
		 }
		 Cookie fromcookie=CookieUtil.getCookie(request,UserUtil.FROMINFO);
		 if(fromcookie != null ){
			 CookieUtil.deleteCookie(response, fromcookie, "/");
		 }
		 Cookie identitycookie=CookieUtil.getCookie(request,UserUtil.IDENTITY);
		 if(identitycookie != null ){
			 CookieUtil.deleteCookie(response, identitycookie, "/");
		 }
//		 Cookie codecookie = CookieUtil.getCookie(request,"admin_vcode"); 
//		 if(codecookie != null ){
//			 CookieUtil.deleteCookie(response, codecookie, "/"); 
//		 }
//		 if(request.getSession().getAttribute("vcode2")!=null){
//			 request.getSession().removeAttribute("vcode2"); 
//		 } 
	 } 
	 /**
	  * 清空用户痕迹
	  * @param request
	  * @param response
	  */
	 public static void cleanUrl(HttpServletRequest request,HttpServletResponse response){
		 Cookie urlcookie = CookieUtil.getCookie(request, UserUtil.URL);
		 if(urlcookie != null){
			 CookieUtil.deleteCookie(response, urlcookie, "/");
		 }
	 }
	 /**
	  * 替换当前用户信息
	  * @param request
	  * @param value 
	  */
	public static void updateUser(HttpServletRequest request,Map<String, Object> value) {
		if(value != null){
			Cookie c = CookieUtil.getCookie(request, UserUtil.USERINFO);
			if(c != null){
				String key = c.getValue();
				RedisUtil.setMap(key, value); 
			}
		}
	}
}
