
package com.lr.backer.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lr.backer.service.IndexService;
import com.lr.backer.util.UserUtil;
import com.lr.labor.weixin.util.WeiXinConfigure;
import com.lr.labor.weixin.util.WeixinUtil;




/**
 * 微信openid的controller
 * @author robin88
 *
 */
@Controller
@RequestMapping("/wx")
public class WeiXinController {

	private transient static Log log = LogFactory.getLog(WeiXinController.class);
	@Resource IndexService indexService;
	/**
	 * 请求该方法，为了获取微信openid
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/getopenid")
	public String getOpenid(HttpServletRequest request){
		log.info("调用getOpenid，获取微信openid");
		String sourceUrl = request.getParameter("sourceUrl");
		
		//请求微信的接口，获取openid
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&amp;redirect_uri=${reurl}&amp;response_type=code&amp;scope=snsapi_base&amp;state=123#wechat_redirect
		StringBuilder openidUrl = new StringBuilder() ;

		try {
			// 定义回调地址
			String redirectUri = URLEncoder.encode(WeiXinConfigure.PROJECT_PATH + "wx/callbackopenid", "UTF-8");
			
			//TODO 这里的值可以自定义参数
			String state = URLEncoder.encode(sourceUrl, "UTF-8") ;
			
			openidUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
			openidUrl.append(WeiXinConfigure.APPID);
			openidUrl.append("&redirect_uri=").append(redirectUri);
			openidUrl.append("&response_type=code&scope=snsapi_base&state=").append(state);
			openidUrl.append("#wechat_redirect");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "redirect:"+openidUrl.toString();
//		return "redirect:/system/queryUserList";
	}
	
	/**
	 * 调用微信接口获取到openid之后跳转到该方法
	 * @return
	 */
	@RequestMapping("/callbackopenid")
	public String callbackOpenid(HttpServletRequest request,HttpServletResponse response){
		try {
			log.info("微信返回了openid！！！");
			
			String scode = request.getParameter("code");
	        System.out.println("code:" + scode);
	        String result = new WeixinUtil().getInfo(scode);
	        log.info("获取openid，微信返回的结果="+result);
	        JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
	        String openid = jsonobj.getString("openid");// 获取openid

	        log.info("openid:" + openid);
	        
	     // state参数 ; 保存用户之前访问的url地址
	        String state = request.getParameter("state") ;
	        if(null!=state && !"".equals(state)){
	        	// 用户需要访问的地址
	        	System.out.println("用户需要访问的地址="+state);

		        // 将用户信息放置到redis中
//		        UserUtil.pushOpenid(request, "openid", openid);
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("openid", openid);
	        	UserUtil.setOpenid(response, openid, map, null);
	        	
	        	//修改最后一次登录时间
				Map<String,Object> giveMap = new HashMap<String,Object>();
				giveMap.put("lasttime", new Date());
				giveMap.put("openid", map.get("openid"));
				//personalService.updateUserInfo(giveMap);
				
				/*
				 * 基于openid查询是否用户是否关注本平台
				 * 如有关注则放开，如未关注则跳转到关注
				 */
				Map<String, Object> membermap=new HashMap<String, Object>();
				membermap.put("openid", openid);
				membermap=this.indexService.getMemberInfo(membermap);
	        	if(membermap!=null && membermap.size()>0){
				return "redirect:"+state;
	        	}else{
	        		if(state.contains("weixin/supplier/getsupplierdetail") || state.contains("my/myqrcode") || state.contains("employer/applyJobDetail") || state.contains("employer/applyProjectDetail")){
	        			return "redirect:"+state;
	        		}else{
	        			return "redirect:/public/attention.jsp";
	        		}
	        	}
	        	// 需要重定向该地址
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	@RequestMapping("/openid")
	public String openidtest(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = "xxx" ;
	        log.info("openid:" + openid);
	        
	     // state参数 ; 保存用户之前访问的url地址
	        String state = request.getParameter("state") ;
	        if(null!=state && !"".equals(state)){
	        	// 用户需要访问的地址
	        	System.out.println("用户需要访问的地址="+state);

		        // 将用户信息放置到redis中
//		        UserUtil.pushOpenid(request, "openid", openid);
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("openid", openid);
	        	UserUtil.setOpenid(response, openid, map, null);
	        	
	        	return "redirect:"+state;
	        	// 需要重定向该地址
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
}