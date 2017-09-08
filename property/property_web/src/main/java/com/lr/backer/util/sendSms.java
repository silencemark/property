package com.lr.backer.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class sendSms {
	private static String BASE_URI = "http://yunpian.com";
	private static String VERSION = "v1";
	private static String ENCODING = "UTF-8";
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";
	public static String sendSms(String phonenum) throws Exception  {
		Random r = new Random();
		String code = r.nextInt(10) + "" + r.nextInt(10) + "" +r.nextInt(10) + "" +r.nextInt(10);
		String phone = phonenum;
		
		HttpClient client = new HttpClient();
		NameValuePair[] nameValuePairs = new NameValuePair[3];
		nameValuePairs[0] = new NameValuePair("apikey", "9749936aa6776244858aec2253a42690");
		nameValuePairs[1] = new NameValuePair("text", "【嘀嗒叫人】您本次验证码是："+code+"。如非本人操作，请忽略本短信");
		nameValuePairs[2] = new NameValuePair("mobile", phone);
		PostMethod method = new PostMethod(URI_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		String re =  method.getResponseBodyAsString();
		System.out.println(re);
//		String re = "{\"code\":0,\"msg\":\"OK\",\"result\":{\"count\":1,\"fee\":1,\"sid\":1424122269}}";
		JSONObject jo = JSONObject.fromObject(re);
		if(jo != null){
			String recode = jo.getString("code");
			if("0".equals(recode)){
				return code;
			}else{
				return recode;
			}
		}else{
			return "0";
		}
	}
	public static String sendSms1(String phonenum,String code) throws Exception  {
		String phone = phonenum;
		
		HttpClient client = new HttpClient();
		NameValuePair[] nameValuePairs = new NameValuePair[3];
		nameValuePairs[0] = new NameValuePair("apikey", "9749936aa6776244858aec2253a42690");
		nameValuePairs[1] = new NameValuePair("text", "【嘀嗒叫人】您本次验证码是："+code+"。如非本人操作，请忽略本短信");
		nameValuePairs[2] = new NameValuePair("mobile", phone);
		PostMethod method = new PostMethod(URI_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		String re =  method.getResponseBodyAsString();
		System.out.println(re);
//		String re = "{\"code\":0,\"msg\":\"OK\",\"result\":{\"count\":1,\"fee\":1,\"sid\":1424122269}}";
		JSONObject jo = JSONObject.fromObject(re);
		if(jo != null){
			String recode = jo.getString("code");
			if("0".equals(recode)){
				return code;
			}else{
				return recode;
			}
		}else{
			return "0";
		}
	}
}
