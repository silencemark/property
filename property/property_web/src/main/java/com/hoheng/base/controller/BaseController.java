package com.hoheng.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.controller.EmployerController;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.UserUtil;
import com.lr.labor.weixin.util.SignUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.labor.weixin.util.WeiXinConfigure;

public class BaseController {
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(EmployerController.class);
	
	@Resource IndexService indexService;
	@Resource SystemService systemService;
	/**
	 * 得到openid
	 * @return
	 */
	public String getOpenId(HttpServletRequest request){
		//微信端正式
		if(Constants.BLAN_OPENID.equals("1")){
			Map<String, Object> weixinMember = UserUtil.getOpenid(request);
			log.info("openid="+weixinMember.get("openid"));
			String openid=weixinMember.get("openid")+"";
			return openid;
		}
		//雇主
		else if(Constants.BLAN_OPENID.equals("2")){
			return Constants.EMPLOYER_OPENID;
		}
		//工人
		else{
			return Constants.WORKER_OPENID;
		}
		
	}
	/**
	 * 得到用户id
	 * @return
	 */
	public String getUserId(HttpServletRequest request){
		if(HttpHeaderUtil.isWeiXinFrom(request) == true){//是微信请求
		//微信端正式
			String openid="";
			if(Constants.BLAN_OPENID.equals("1")){
				Map<String, Object> weixinMember = UserUtil.getOpenid(request);
				log.info("openid="+weixinMember.get("openid"));
				openid=weixinMember.get("openid")+"";
			}
			//雇主
			else if(Constants.BLAN_OPENID.equals("2")){
				openid = Constants.EMPLOYER_OPENID;
			}
			//工人
			else{
				openid = Constants.WORKER_OPENID;
			}
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("openid", openid);
			membermap=this.indexService.getMemberInfo(membermap);
			if(membermap!=null && !openid.equals("")){
				return membermap.get("memberid")+"";
			}else{
				return "xxx";
			}
	  }else{//不是微信请求
		 Map<String,Object> userMap =  UserUtil.getUser(request);
		  if(userMap.get("memberid") != null && !userMap.get("memberid").equals("")){
			  return userMap.get("memberid").toString();			  
		  } else{
			  return "xxx";
		  }
	  }
	}
	//分享参数
	public Map<String, Object> initWeixinShareParam(HttpServletRequest request,String... strings) {
		// 分享参数设置 微信分享参数
		System.out.println("微信分享参数");
		String url = getURL(request);
		String url2 = Constants.PROJECT_PATH + url;
		if (url.indexOf("/") == 0) {
			url = url.substring(1, url.length());
		}
		url = Constants.PROJECT_PATH + url;
		Map<String, Object> shareMap = new HashMap<String, Object>();
		try {
			String jsTicket = WeiXinCenterProxy.getJSApiTicket();
			String noncestr = UUID.randomUUID().toString();
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);
			// url = URLEncoder.encode(url,"UTF-8");
			String signature = "jsapi_ticket=" + jsTicket + "&noncestr="
			+ noncestr + "&timestamp=" + timestamp + "&url=" + url;
			String signature2 = "jsapi_ticket=" + jsTicket + "&noncestr="
			+ noncestr + "&timestamp=" + timestamp + "&url=" + url2;
			System.out
					.println("--------------------分享参数--------------------------------");
			System.out.println("\nString1-----------------" + signature);
			signature = SignUtil.getSignature(signature);
			signature2 = SignUtil.getSignature(signature2);
			shareMap.put("appId",WeiXinConfigure.APPID);

			System.out.println("\nappId-----------------" + WeiXinConfigure.APPID);
			shareMap.put("timestamp", timestamp);
			System.out.println("\ntimestamp-----------------" + timestamp);
			shareMap.put("jsapi_ticket", jsTicket);
			System.out.println("\njsTicket-----------------" + jsTicket);
			shareMap.put("nonceStr", noncestr);
			System.out.println("\nnonceStr-----------------" + noncestr);
			shareMap.put("url", url);
			System.out.println("\nurl-----------------" + url);

			shareMap.put("signature", signature.toLowerCase());
			shareMap.put("signature2", signature2.toLowerCase());
			System.out.println("\nsignature-----------------" + signature);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shareMap;
	}
	
	public String getURL(HttpServletRequest request) {
		String str = request.getServletPath();
		String  url= str;
		if(request.getQueryString()!=null){
			url = str + "?" + request.getQueryString();
		} 
		return url;
	}
	public String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		if ("/".equals(path)) path = "";
		int serverPort = request.getServerPort();
		String sPort = (serverPort == 80 || serverPort == 443)?"":(":" + request.getServerPort());
		String serverName = request.getServerName();
		String base = request.getScheme() + "://" + serverName+sPort; 
		String basePath = base + path + "/";
		return basePath;
	}
	public Double amountsum=0.00;
	public double getAmount(String agencyid){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		Map<String, Object> amountinfo=new HashMap<String, Object>();
		amountinfo.put("parentid", agencyid);
		dataList = this.systemService.getCommissionList(amountinfo);
		for(Map<String, Object> data:dataList){
			Map<String, Object> amountmap=new HashMap<String, Object>();
			amountmap.put("agencyid", data.get("agencyid"));
			String amount=systemService.getAmountAll(amountmap);
			amountsum+=Double.parseDouble(amount);
			getAmount(data.get("agencyid")+"");
		}
		return amountsum;
	}
	public String parentagencyid="";
	public Double proportion=1.00;
	public String getparentid(String agencyid){
		Map<String, Object> amountinfo=new HashMap<String, Object>();
		amountinfo.put("agencyid", agencyid);
		amountinfo=this.systemService.getAgencyMap(amountinfo);
		if(amountinfo != null && String.valueOf(amountinfo.get("parentid")).equals("0")){
			parentagencyid=amountinfo.get("agencyid")+"";
			proportion=proportion*(Double.parseDouble(amountinfo.get("commissionrate")+""))/100;
			return parentagencyid;
		}else{
			proportion=proportion*(Double.parseDouble(amountinfo.get("commissionrate")+""))/100;
			getparentid(amountinfo.get("parentid")+"");
		}
		return parentagencyid;
	}
	
	
	public String memberidlist="";
	public String getAgencyidlist(String agencyid){
		
		Map<String, Object> amountinfo=new HashMap<String, Object>();
		amountinfo.put("agencyid", agencyid);
		amountinfo=this.systemService.getAgencyMap(amountinfo);
		
		memberidlist+=amountinfo.get("userid")+",";
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		amountinfo=new HashMap<String, Object>();
		amountinfo.put("parentid", agencyid);
		dataList = this.systemService.getCommissionList(amountinfo);
		for(Map<String, Object> data:dataList){
			getAgencyidlist(data.get("agencyid")+"");
		}
		return memberidlist;
	}
	
	/**
	 * 判断是否是管理员
	 * @param request
	 * @param userid
	 * @return
	 */
	public boolean  isManager(HttpServletRequest request,String userid){
		Map<String,Object> pvMap = new HashMap<String, Object>();
	    pvMap.put("userid",userid);
	    List<Map<String,Object>> roleList =	this.systemService.judgeUserRoles(pvMap);
	    if(roleList.size()>0 && roleList.size() == 1){
		   for (Map<String,Object>data:roleList) {
			  if(data.get("roleid").equals(Constants.USER_MAMAGER_ROLE)){//管理员
				  return true;
			  }	
		}
      } 
	  return false;
  }
	public boolean  isInvestment(HttpServletRequest request,String userid){
		Map<String,Object> pvMap = new HashMap<String, Object>();
	    pvMap.put("userid",userid);
	    List<Map<String,Object>> roleList =	this.systemService.judgeUserRoles(pvMap);
	    if(roleList.size()>0 && roleList.size() == 1){
		   for (Map<String,Object>data:roleList) {
			  if(data.get("roleid").equals(Constants.USER_INVESTMENT)){//招商
				  return true;
			  }	
		}
      } 
	  return false;
  }
	
	public  int judgeUserRole(HttpServletRequest request,String userid){
		
		Map<String,Object> pvMap = new HashMap<String, Object>();
	    pvMap.put("userid",userid);
	    List<Map<String,Object>> roleList =	this.systemService.judgeUserRoles(pvMap);
	    int  rol=0;
	    if(roleList.size()>0 && roleList.size() == 1){
		   for (Map<String,Object>data:roleList) {
			  if(data.get("roleid").equals(Constants.USER_MAMAGER_ROLE)){//管理员
				  rol=1;
			  }else if(data.get("roleid").equals(Constants.USER_SELL_ROLE)){//销售商
				  rol=2;
			  }else if(data.get("roleid").equals(Constants.USER_AGENCY_ROLE)){//代理商
				  rol=3;
			  } 	
		}
      } 
	    return rol;
  }
}
