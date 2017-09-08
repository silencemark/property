package com.labor.weixin.api;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.labor.weixin.util.WeiXinCenterProxy;
import com.lr.backer.util.FileDeal;




/**
 * @ClassName:WeiXinMaterialAPI.java
 * @ClassPath:com.hk.reserve.api
 * @Desciption:微信素材api接口
 * @Author: robin
 * @Date: 2015-1-19 下午01:49:03
 * 
 */
public class WeiXinMaterialAPI extends WeiXinBase {
	
	//上传媒体文件
	private static Map<String, Object> uploadMaterial(File file,String contentType,String type){
		//
		if(null==file || !file.exists()){
			return null ;
		}
		
		String filename = file.getName();
		String filePath = file.getPath();
		
		JSONObject fileJson = new JSONObject();
		fileJson.put("filename", filename);
		fileJson.put("filePath", filePath);
		fileJson.put("contentType", contentType);
		
		String tokenid = WeiXinCenterProxy.getAccessToken();
		StringBuffer temp = new StringBuffer();
		temp.append("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=");
		temp.append(tokenid);
		temp.append("&type="+type);
		
		String url = temp.toString();
		
		JSONObject resultJson = HttpRequest.httpRequestForm(url, file, fileJson) ;
		
		String result = resultJson.toString(); //MenuManager.get(url);
		System.out.println(result);
		//{"type":"image","media_id":"Oxif1yZ0v7L3--NMYEXmeYgCTMiOhVIw7xsvo0CRCbAI3ozu3gkfJbpIOAuZtIYL","created_at":1421673781}
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("thumb_media_id")) {
			//上传缩略图
			String thumbmediaid = jsonobj.getString("thumb_media_id");// 获取字符串。
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("thumbmediaid", thumbmediaid) ;
			map.put("createat", FileDeal.formatTimeStamp(jsonobj.getLong("created_at")));
			return map ;
		}else if (jsonobj.has("media_id")) {
			//上传图片
			String mediaid = jsonobj.getString("media_id");// 获取字符串。
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mediaid", mediaid) ;
			map.put("createat", FileDeal.formatTimeStamp(jsonobj.getLong("created_at")));
			return map ;
		}else{
			new Exception(result);
		}
		return null ;
	}
	
	//上传图片
	//http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	public static Map<String, Object> uploadImg(File file){
		return uploadMaterial(file, HttpRequest.JPEG,"image") ;
	}
	
	//上传缩略图
	//http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	public static Map<String, Object> uploadThumb(File file){
		return uploadMaterial(file, HttpRequest.JPEG,"thumb") ;
	}
	
	//上传语音（支持mp3）
	//http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	public static Map<String, Object> uploadMp3(File file){
		return uploadMaterial(file, HttpRequest.MPEG,"voice") ;
	}
	
	//https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN
	/**
	 * @function: 上传图文
	 * @datetime:2015-1-20 上午10:20:47
	 * @Author: robin
	 * @param: @param graphicList
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> uploadGraphic(List<Map<String, Object>> graphicList){
		
		JSONObject graphicJson = new JSONObject();
		graphicJson.put("articles", parseArticle(graphicList));
		System.out.println(graphicJson.toString());
		
		String tokenid = WeiXinCenterProxy.getAccessToken();
		StringBuffer temp = new StringBuffer("https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=");
		temp.append(tokenid);
		JSONObject resultJson = doPost(temp.toString(), graphicJson.toString());
		
		String result = resultJson.toString(); //MenuManager.get(url);
		System.out.println(result);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("media_id")) {
			String mediaid = jsonobj.getString("media_id");// 获取字符串。
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mediaid", mediaid) ;
			map.put("createat", FileDeal.formatTimeStamp(jsonobj.getLong("created_at")));
			return map ;
		}else {
			new Exception(result);
		}
		return null ;
	}
	
	/**
	 * @function:组装图文json
	 * @datetime:2015-1-23 下午08:11:03
	 * @Author: robin
	 * @param: @param graphicList
	 * @return JSONObject[]
	 */
	private static JSONObject[] parseArticle(List<Map<String, Object>> graphicList){
		if(null == graphicList || graphicList.isEmpty() ){
			return null ;
		}
		JSONObject[] jsonObjects = new JSONObject[graphicList.size()] ;
		int i =0 ;
		for (Map<String, Object> map : graphicList) {
			if(null!=map && !map.isEmpty()){
				JSONObject  graphic = new JSONObject();
				graphic.put("thumb_media_id", MapUtils.getString(map, "thumbmediaid"));
				graphic.put("author", MapUtils.getString(map, "author"));
				graphic.put("title", MapUtils.getString(map, "title"));
				graphic.put("content_source_url", MapUtils.getString(map, "linkurl"));
				graphic.put("content", MapUtils.getString(map, "content"));
				graphic.put("digest", MapUtils.getString(map, "summary"));
				graphic.put("show_cover_pic", MapUtils.getString(map, "ifviewcontent"));
				
//				Articles	是	图文消息，一个图文消息支持1到10条图文
//				thumb_media_id	是	图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
//				author	否	图文消息的作者
//				title	是	图文消息的标题
//				content_source_url	否	在图文消息页面点击“阅读原文”后的页面
//				content	是	图文消息页面的内容，支持HTML标签
//				digest	否	图文消息的描述
//				show_cover_pic	否	是否显示封面，1为显示，0为不显示
				jsonObjects[i] = graphic ;
				i++ ;
			}
		}
		return jsonObjects ;
	}
	
	
	/**
	 * @function: 判断当前的素材是否过期<br/>
	 * <p> 微信会对上传的媒体文件进行删除（媒体文件在微信中只能保留三天） </p>
	 * <p> 使用媒体文件时，需要判断文件是否过期被删除 </p>
	 * @datetime:2015-1-23 下午08:26:34
	 * @Author: robin
	 * @param: @param data 媒体对象
	 * @param: @param key 媒体文件上传的日期key
	 * @return boolean true 表示过期 false 表示未过期
	 */
	public static boolean isMaterialOverdue(Map<String, Object> data,String key){
		if(null == data || data.isEmpty() || !data.containsKey(key)){
			return true ;
		}
		Object valueObject = MapUtils.getString(data, key) ;
		if(null == valueObject){
			return true ;
		}
		Date datetime = null ;
		if(valueObject instanceof Date){
			 datetime = (Date) valueObject ;
		}else if (valueObject instanceof String) {
			try {
				datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueObject.toString()) ;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(null == datetime){
			return true ;
		}
		
		Calendar upload3 = Calendar.getInstance() ;
		upload3.setTime(datetime);
		upload3.add(Calendar.DAY_OF_MONTH, 3);
		if(Calendar.getInstance().after(upload3)){
			System.out.println("超过三天，素材文件已过期！");
			return true ;
		}else {
			System.out.println("未超过三天，素材文件可以继续使用！");
			return false ;
		}
		//==============
	}
	
	
}