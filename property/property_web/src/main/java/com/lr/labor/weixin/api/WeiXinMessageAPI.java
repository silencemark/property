package com.lr.labor.weixin.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.lr.backer.util.FileUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.labor.weixin.util.WeiXinConfigure;

public class WeiXinMessageAPI extends WeiXinBase
{
  private static JSONObject getSendJson(Map<String, Object> sendMap, String msgtypekey)
  {
    if ((null == sendMap) || (sendMap.isEmpty())) {
      return null;
    }
    JSONObject sendJson = new JSONObject();

    boolean is_to_all = false;
    JSONObject filterJson = new JSONObject();
    if ((sendMap.containsKey("roletype")) && ("1".equals(sendMap.get("roletype").toString()))) {
      String groupid = FileUtil.getMapValue(sendMap, "groupid");
      if ((null == groupid) || ("".equals(groupid)))
      {
        is_to_all = true;
      }
      else filterJson.put("group_id", groupid);

      filterJson.put("is_to_all", Boolean.valueOf(is_to_all));
    }
    sendJson.put("filter", filterJson);

    JSONObject sendTypeJson = new JSONObject();
    String msgtype = "";
    if ((null != msgtypekey) && ("txt".equals(msgtypekey.toString())))
    {
      sendTypeJson.put("content", MapUtils.getString(sendMap, "content"));
      msgtype = "text";
    } else if ("img".equals(msgtypekey.toString()))
    {
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
      msgtype = "image";
    } else if ("article".equals(msgtypekey.toString()))
    {
      msgtype = "mpnews";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    else {
      msgtype = "voice";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    sendJson.put(msgtype, sendTypeJson);
    sendJson.put("msgtype", msgtype);
    return sendJson;
  }

  private static JSONObject getOpenidSendJson(Map<String, Object> sendMap, String msgtypekey, List<Map<String, Object>> openidList) {
    if ((null == sendMap) || (sendMap.isEmpty())) {
      return null;
    }
    JSONObject sendJson = new JSONObject();

    if ((null == openidList) || (openidList.isEmpty()) || (openidList.size() <= 0)) {
      return null;
    }

    String[] openidArray = new String[openidList.size()];
    for (int i = 0; i < openidList.size(); ++i) {
      Map map = (Map)openidList.get(i);
      if ((null != map) && (!(map.isEmpty())) && (map.containsKey("openid"))) {
        openidArray[i] = MapUtils.getString(map, "openid");
      }
    }
    System.out.println(openidArray);
    sendJson.put("touser", openidArray);

    JSONObject sendTypeJson = new JSONObject();
    String msgtype = "";
    if ((null != msgtypekey) && ("txt".equals(msgtypekey.toString())))
    {
      sendTypeJson.put("content", MapUtils.getString(sendMap, "content"));
      msgtype = "text";
    } else if ("img".equals(msgtypekey.toString()))
    {
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
      msgtype = "image";
    } else if ("article".equals(msgtypekey.toString()))
    {
      msgtype = "mpnews";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    else {
      msgtype = "voice";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    sendJson.put(msgtype, sendTypeJson);
    sendJson.put("msgtype", msgtype);
    return sendJson;
  }

  public static Map<String, Object> sendMessage(Map<String, Object> sendMap, String msgtypekey, List<Map<String, Object>> openidList) {
    if ((null == sendMap) || (sendMap.isEmpty())) {
      return null;
    }

    if(null != openidList && openidList.size()>1){
    	return sendAllByOpendid(sendMap, msgtypekey, openidList);
    }
    return sendAllByGroup(sendMap, msgtypekey);
  }

  public static Map<String, Object> sendAllByGroup(Map<String, Object> sendMap, String msgtypekey)
  {
    JSONObject sendJson = getSendJson(sendMap, msgtypekey);

    String tokenid = WeiXinCenterProxy.getAccessToken();
    StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=");
    temp.append(tokenid);

    System.out.println("appid=" + WeiXinConfigure.APPID);
    System.out.println("APPSECRET=" + WeiXinConfigure.APPSECRET);
//    System.out.println("token=" + tokenid);
    System.out.println("sendJson=" + sendJson);

    JSONObject resultJson = doPost(temp.toString(), sendJson.toString());

    String result = resultJson.toString();
    System.out.println(result);
    JSONObject jsonobj = JSONObject.fromObject(result);

    if (jsonobj.has("msg_id")) {
      String msgid = jsonobj.getString("msg_id");
      Map map = new HashMap();
      map.put("msgid", msgid);

      return map;
    }
    new Exception(result);

    return null;
  }

  public static Map<String, Object> sendAllByOpendid(Map<String, Object> sendMap, String msgtypekey, List<Map<String, Object>> openidList)
  {
    JSONObject sendJson = getOpenidSendJson(sendMap, msgtypekey, openidList);
    if (null == sendJson) {
      return null;
    }
    String tokenid = WeiXinCenterProxy.getAccessToken();
    StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=");
    temp.append(tokenid);
    System.out.println(sendJson.toString());
    JSONObject resultJson = doPost(temp.toString(), sendJson.toString());

    String result = resultJson.toString();
    System.out.println(result);
    JSONObject jsonobj = JSONObject.fromObject(result);
    if (jsonobj.has("msg_id")) {
      String msgid = jsonobj.getString("msg_id");
      Map map = new HashMap();
      map.put("msgid", msgid);

      return map;
    }
    new Exception(result);

    return null;
  }

  private static JSONObject getOpenidSendJson(Map<String, Object> sendMap, String msgtypekey, String openid)
  {
    if ((null == sendMap) || (sendMap.isEmpty())) {
      return null;
    }
    JSONObject sendJson = new JSONObject();

    sendJson.put("touser", openid);

    JSONObject sendTypeJson = new JSONObject();
    String msgtype = "";
    if ((null != msgtypekey) && ("txt".equals(msgtypekey.toString())))
    {
      sendTypeJson.put("content", MapUtils.getString(sendMap, "content"));
      msgtype = "text";
    } else if ("img".equals(msgtypekey.toString()))
    {
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
      msgtype = "image";
    } else if ("article".equals(msgtypekey.toString()))
    {
      msgtype = "mpnews";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    else {
      msgtype = "voice";
      sendTypeJson.put("media_id", MapUtils.getString(sendMap, "mediaid"));
    }
    sendJson.put(msgtype, sendTypeJson);
    sendJson.put("msgtype", msgtype);
    return sendJson;
  }

  public static Map<String, Object> sendMessagePreview(Map<String, Object> sendMap, String msgtypekey, String openid)
  {
    JSONObject sendJson = getOpenidSendJson(sendMap, msgtypekey, openid);

    String tokenid = WeiXinCenterProxy.getAccessToken();
    StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=");
    temp.append(tokenid);
    System.out.println(sendJson.toString());
    JSONObject resultJson = doPost(temp.toString(), sendJson.toString());

    String result = resultJson.toString();
    System.out.println(result);
    JSONObject jsonobj = JSONObject.fromObject(result);
    if (jsonobj.has("msg_id")) {
      String msgid = jsonobj.getString("msg_id");
      Map map = new HashMap();
      map.put("msgid", msgid);
      return map;
    }
    new Exception(result);

    return null;
  }
  
  public static void main(String[] args) {
	
	  
//	  {"errcode":0,"errmsg":"preview success"}

	  StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=");
	    temp.append("vOHCF_PeruhloDWYDLURK9GXYYdDzFGurjUyFBa8te-bt8y4fGPInGc5lBAgKDM8BfxixSXqoOfMAYkNFZo5IxORwD2fUs4RtpPS9zUnJH4");
	    String sendJson = "{\"touser\":\"oteX8vzsijyZvVZbXLYdwp24l7IQ\",\"mpnews\":{\"media_id\":\"DPaJ7Cn6DpwEc74g9EW8C88YcWP7D59EB0SwKpHqV-JUl6WssoC9iwniESATObby\"},\"msgtype\":\"mpnews\"}";
	    JSONObject resultJson = doPost(temp.toString(), sendJson.toString());

	    String result = resultJson.toString();
	    System.out.println(result);
	    JSONObject jsonobj = JSONObject.fromObject(result);
	    
	  
	  
}
  

  public static String sendTemplateMessage(String templateId, Map<String, Object> sendMap)
  {
    if ((null == sendMap) || (sendMap.isEmpty())) {
      return null;
    }
    JSONObject sendJson = new JSONObject();
    JSONObject data = new JSONObject();
    JSONObject first = new JSONObject();
    JSONObject keyword1 = new JSONObject();
    JSONObject keyword2 = new JSONObject();
    sendJson.put("touser", sendMap.get("openid"));
    sendJson.put("template_id", templateId);
    sendJson.put("url", "http://yy.hoheng.cn/wechat/user!myHouse.action");

    first.put("value", sendMap.get("title"));
    keyword1.put("value", sendMap.get("house"));
    keyword2.put("value", sendMap.get("status"));
    data.put("first", first);
    data.put("keyword1", keyword1);
    data.put("keyword2", keyword2);
    sendJson.put("data", data);

    String tokenid = WeiXinCenterProxy.getAccessToken();
    StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=");
    temp.append(tokenid);
    System.out.println(sendJson.toString());
    JSONObject resultJson = doPost(temp.toString(), sendJson.toString());

    String result = resultJson.toString();
    System.out.println(result);
    JSONObject jsonobj = JSONObject.fromObject(result);
    if (jsonobj.has("errcode")) {
      String errcode = jsonobj.getString("errcode");

      return errcode;
    }
    new Exception(result);

    return null;
  }
}