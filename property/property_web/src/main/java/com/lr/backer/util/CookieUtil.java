package com.lr.backer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CookieUtil {
	private transient static Log log = LogFactory.getLog(CookieUtil.class);
	
	public static void synchronousCookie(Cookie cookie){
		cookie.setMaxAge(cookie.getMaxAge());
	}
	
	public static void setServerCookie(HttpServletResponse response, String name,
            String value, String path,String age  ,String domain){
		  if (log.isDebugEnabled()) {
	            log.debug("Setting cookie '" + name + "' on path '" + path + "' and domain "+ domain);
	        }

	        Cookie cookie = new Cookie(name, value);
	        cookie.setSecure(false);
	        cookie.setPath(path);
	        if(Constants.COOK_DOMAIN != null){
	        	cookie.setDomain(Constants.COOK_DOMAIN);       
	        } 
	        if(age!=null) {       
	        	int n=2;     
	        	try {
					n=Integer.parseInt(age);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
	        	cookie.setMaxAge(1800 * n);
	        }else{
	        	cookie.setMaxAge(86400*365);
	        }
	       
	        response.addCookie(cookie);
	}
    /**
     * Convenience method to set a cookie
     *
     * @param response
     * @param name
     * @param value
     * @param path
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, String path,String age) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path); 
        if(Constants.COOK_DOMAIN != null){
        	cookie.setDomain(Constants.COOK_DOMAIN);       
        }
        if(age!=null) {
        	int n=2;
        	try {
				n=Integer.parseInt(age);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
        	cookie.setMaxAge(1800 * n);
        }else{
        	cookie.setMaxAge(86400*365);
        }
        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request the current request
     * @param name the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
    	Cookie returnCookie = null;
    	if(request == null){
    		return returnCookie;
    	}
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return returnCookie;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie thisCookie = cookies[i];
            if (thisCookie.getName() != null && thisCookie.getName().equals(name)) {
                returnCookie = thisCookie;
                break;
            }
        }
        return returnCookie;
    }
    
    public static Object getValue(HttpServletRequest request, String name){
    	Cookie cookie = getCookie(request, name);
    	if(cookie!=null){
    		return cookie.getValue();
    	}
    	return null;
    }
    
    public static String getAllCookieNameAndValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        StringBuffer cs=new StringBuffer();

        for (int i = 0; i < cookies.length; i++) {
            cs.append(cookies[i].getName()).append("=").append(cookies[i].getValue()).append(";");           
        }
        return cs.toString();
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set (i.e. /appfuse)
     */
    public static void deleteCookie(HttpServletResponse response,
                                    Cookie cookie, String path) {
    	if (log.isDebugEnabled()) {
             log.debug("Delete cookie '" + cookie + "' on path '" + path + "'");
        }
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
        	cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setPath(path);
            if(Constants.COOK_DOMAIN != null){
            	cookie.setDomain(Constants.COOK_DOMAIN);       
            }
            response.addCookie(cookie);
        }
    }
    
    /**
     * Convenience method to get the application's URL based on request
     * variables.
     */
    public static String getAppURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
    	int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(Constants.PROJECT_PATH+"");
        return url.toString();
    }
    
    /**
	 * 把json 转换为 Map<String,Object> 形式
	 * @return
	 */
    public static Map<String, Object> getMap(String jsonString)
	{
	  JSONObject jsonObject;
	  try
	  {
	   jsonObject = new JSONObject(jsonString);  
	   @SuppressWarnings("unchecked")
	   Iterator<String> keyIter = jsonObject.keys();
	   String key;
	   Object value;
	   Map<String, Object> valueMap = new HashMap<String, Object>();
	   while (keyIter.hasNext())
	   {
	    key = (String) keyIter.next();
	    value = jsonObject.get(key);
	    valueMap.put(key, value);
	   }
	   return valueMap;
	  }
	  catch (JSONException e)
	  {
	   e.printStackTrace();
	  }
	  return null;
	}

	/**
	 * 把json 转换为 ArrayList 形式
	 * @return
	 */
	public static List<Map<String, Object>> getList(String jsonString)
	{
	  List<Map<String, Object>> list = null;
	  try
	  {
	   JSONArray jsonArray = new JSONArray(jsonString);
	   JSONObject jsonObject;
	    list = new ArrayList<Map<String, Object>>();
	   for (int i = 0; i < jsonArray.length(); i++)
	   {
	    jsonObject = jsonArray.getJSONObject(i);
	    list.add(getMap(jsonObject.toString()));
	   }
	  }
	  catch (Exception e)
	  {
	   e.printStackTrace();
	  }
	  return list;
	}
	public static void main(String[] args) throws JSONException {
			String a="[{createtime=2015-07-13 21:40:20.0, status=1, remark=nav-priv, name=权限设置, linkurl=, parentid=0, funid=1}, {createtime=2015-07-11 20:50:53.0, status=1, remark=nav-admin-dr, name=医生管理, linkurl=/system/yslist, parentid=1, funid=2}, {createtime=2015-07-11 20:52:10.0, status=1, remark=nav-admin-pt, name=患者管理, linkurl=/system/hzlist, parentid=1, funid=3}, {createtime=2015-07-14 00:12:52.0, status=1, remark=nav-system, name=系统设置, linkurl= , parentid=0, funid=4}, {createtime=2015-07-11 20:55:18.0, status=1, remark=nav-admin-dict, name=字典管理, linkurl=/system/getDictTypeList, parentid=4, funid=5}, {createtime=2015-07-13 13:28:59.0, status=1, remark=nav-admin-role, name=角色管理, linkurl=/system/getRoleList, parentid=1, funid=6}, {createtime=2015-07-13 11:16:09.0, status=1, remark=nav-admin-menu, name=菜单管理, linkurl=/system/getMyMenuList, parentid=1, funid=7}, {createtime=2015-07-25 17:57:50.0, status=1, remark=nav-cssz-chedule, name=客服排班, linkurl=/kf/getSchedule, parentid=11, funid=223}, {createtime=2015-07-25 20:33:44.0, status=1, remark=nav-circle, name=圈子管理, parentid=0, funid=221}, {createtime=2015-07-25 20:34:01.0, status=1, remark=nav-circle-circlelist, name=动态管理, linkurl=/circle/circlelist, parentid=221, funid=222}, {createtime=2015-07-26 13:56:39.0, status=1, remark=nav-cssz-sfgl, name=随访管理, linkurl=/sf/getSFquesOrg, parentid=11, funid=200}, {createtime=2015-07-27 17:00:36.0, status=1, remark=nav-admin-user, name=员工管理, linkurl=/system/userlist, parentid=1, funid=194}, {createtime=2015-07-16 14:26:30.0, status=1, remark=nav-cssz, name=参数设置, linkurl=, parentid=0, funid=11}, {createtime=2015-07-14 00:14:03.0, status=1, remark=nav-param-aboutus, name=关于我们, linkurl=/mh/aboutus, parentid=4, funid=40}, {createtime=2015-07-14 00:14:05.0, status=1, remark=nav-param-linkus, name=联系我们, linkurl=/mh/linkus, parentid=4, funid=41}, {createtime=2015-07-14 00:14:08.0, status=1, remark=nav-param-seo, name=金医生SEO配置, linkurl=/mh/seo, parentid=4, funid=42}, {createtime=2015-07-16 09:45:51.0, status=1, remark=nav-activity, name=活动管理, linkurl=, parentid=0, funid=10}, {createtime=2015-07-16 09:46:09.0, status=1, remark=nav-activity-list, name=活动管理, linkurl=/system/activitylist, parentid=10, funid=203}, {createtime=2015-07-22 13:04:03.0, status=1, remark=nav-other-diener, name=医学助手, linkurl=/diener/getColumn, parentid=206, funid=211}, {createtime=2015-07-17 20:45:45.0, status=1, remark=nav-other, name=其他管理, linkurl=, parentid=0, funid=206}, {createtime=2015-07-18 14:51:43.0, status=1, remark=nav-myd-manager, name=满意度管理, linkurl=/system/satis/satisList, parentid=206, funid=207}, {createtime=2015-07-22 18:17:24.0, status=1, remark=nav-other-ksmanage, name=科室管理, linkurl=/ks/text, parentid=206, funid=208}, {createtime=2015-08-06 09:55:57.0, status=1, remark=nav-other-checkreport, name=检查报告, linkurl=/system/checkreport/checkreportlist, parentid=234, funid=210}, {createtime=2015-07-23 20:31:04.0, status=1, remark=nav-other-user, name=个人中心, linkurl=/system/personal, parentid=206, funid=215}, {createtime=2015-07-24 10:03:29.0, status=1, remark=nav-other-ft, name=访谈管理, linkurl=/interview/ftlist, parentid=206, funid=216}, {createtime=2015-07-28 11:54:20.0, status=1, remark=nav-cssz-protocol, name=随访协议, linkurl=/sf/getSFprotocol, parentid=11, funid=225}, {createtime=2015-08-06 09:53:35.0, status=1, remark=nav-other-healthrecord, name=健康档案, linkurl=/healthrecord/healthrecordlist, parentid=234, funid=218}, {createtime=2015-07-25 17:58:05.0, status=1, remark=nav-cssz-rule, name=接入规则, linkurl=/kf/getRule, parentid=11, funid=219}, {createtime=2015-07-28 15:49:14.0, status=1, remark=nav-cssz-sheet, name=随访问卷, linkurl=/sf/getSFsheet, parentid=11, funid=226}, {createtime=2015-08-03 09:28:22.0, status=1, remark=nav-other-mark, name=疾病标签, linkurl=/system/mark/markList, parentid=206, funid=227}, {createtime=2015-08-20 12:22:08.0, status=1, remark=nav-admin-banner, name=banner管理, linkurl=/system/getPositionList, parentid=4, funid=228}, {createtime=2015-08-05 16:31:48.0, status=1, remark=nav-other-register, name=医生排班, linkurl=/register/register.html, parentid=206, funid=229}, {createtime=2015-08-04 17:45:36.0, status=1, remark=nav-admin-special, name=疾病专题, linkurl=/system/getSpecialList, parentid=4, funid=230}, {createtime=2015-08-05 14:24:33.0, status=1, remark=p-admin-gj, name=管家管理, linkurl=/gj/index.html, parentid=1, funid=231}, {createtime=2015-08-06 09:49:22.0, status=1, remark=nav-jk, name=健康管理, linkurl=, parentid=0, funid=234}, {createtime=2015-08-06 10:29:50.0, status=0, remark=nav-cssz-diagnose, name=联合会诊, linkurl=/register/getDiagnoseList, parentid=11, funid=233}, {createtime=2015-08-06 09:55:15.0, status=1, remark=nav-jk-height, name=成长记录, linkurl=/growthrecord/height.html, parentid=234, funid=235}]";
			//JSONObject jsonObject=new JSONObject(a.trim().replaceAll("=", ":"));
			
			JSONArray s=new JSONArray(a.trim().replaceAll("=", ":"));
			
	}
}
