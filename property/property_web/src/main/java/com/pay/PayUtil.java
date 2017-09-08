package com.pay;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.lr.backer.util.Constants;
import com.utils.GetWxOrderno;
import com.utils.RequestHandler;
import com.utils.Sha1Util;
import com.utils.TenpayUtil;
import com.utils.http.HttpClientConnectionManager;

public class PayUtil {
	public static Map<String,String> withdrawal(HttpServletRequest request,HttpServletResponse response,Map<String, Object> orderMap){
		//公众账号appid
		String appid = Constants.APPID;
		//商户号
		String mchid = Constants.PARTNER;
		//设备号
		String device_info = "1000";
			//8位日期
			String currTime = TenpayUtil.getCurrTime();
			String strTime = currTime.substring(8, currTime.length());
			//四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
		//随机字符串
		String nonce_str = strTime + strRandom;
		//	String nonce_str = "2040511732";
			//商户订单号
		String partner_trade_no = appid+Sha1Util.getTimeStamp();
		//String partner_trade_no = appid + "1450702089"; 
		//用户opendid
		String openid = String.valueOf(orderMap.get("openid"));
		//校验用户姓名选项
		String check_name = "NO_CHECK"; 
		//收款用户姓名
		String re_user_name = "***";
		//金额
		String price = String.valueOf(orderMap.get("price")); 
		float sessionmoney = Float.parseFloat(price);
		String amount = String.format("%.2f", sessionmoney);
		amount = amount.replace(".","");
		int one = price.indexOf(".");
		if(one > 0){
			amount=Integer.parseInt(amount)+"";
		}
		//企业付款描述信息
		String desc = "瑞家U品用户提现";
		//Ip地址
		String spbill_create_ip = request.getRemoteAddr();	 
		//签名
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_appid", appid);  
		packageParams.put("mchid", mchid);  
		packageParams.put("device_info", device_info); 
		packageParams.put("nonce_str", nonce_str);   
		packageParams.put("partner_trade_no", partner_trade_no);  
		packageParams.put("amount", amount);  
		packageParams.put("spbill_create_ip", spbill_create_ip);   
		packageParams.put("desc", desc);    
		packageParams.put("openid", openid);  
		packageParams.put("check_name", check_name);
		packageParams.put("re_user_name", re_user_name);   
		RequestHandler reqHandler = new RequestHandler(request, response);	
		reqHandler.init(appid, Constants.PARTNER, Constants.PARTNERKEY); 
		//签名
		String sign = reqHandler.createSign(packageParams);  
		System.out.println("sign--------------------"+sign); 
		String xml="<xml>"+   
				"<mch_appid>"+appid+"</mch_appid>"+
				"<mchid>"+mchid+"</mchid>"+
				"<device_info>"+device_info+"</device_info>"+ 
				"<nonce_str>"+nonce_str+"</nonce_str>"+
				"<sign>"+sign+"</sign>"+
				"<partner_trade_no>"+partner_trade_no+"</partner_trade_no>"+
				"<openid>"+openid+"</openid>"+
				"<check_name>"+check_name+"</check_name>"+
				"<re_user_name>"+re_user_name+"</re_user_name>"+
				"<amount>"+amount+"</amount>"+
				"<desc>"+desc+"</desc>"+
				"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+				 			
				"</xml>";
		
		String allParameters = "";
		try {
			allParameters =  reqHandler.genPackage(packageParams);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
		Map<String,String> paymap = new HashMap<String,String>();
		try {
			paymap = new PayUtil().withdrawal(createOrderURL, xml);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return paymap;
	}
	
	public static Map<String,String> withdrawal(String url,String xmlParam){  
		 System.out.println("xml-----------------------:"+xmlParam);
//		 DefaultHttpClient client = getClient();
//		 client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		 HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		 try {
			 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			 HttpResponse response = getClient().execute(httpost);
		     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		     System.out.println("jsonStr-------------------:"+jsonStr);
		     Map<String,String> map = GetWxOrderno.doXMLParse(jsonStr);
		     return map;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String,String>();
		}
	 }  
	
	public static CloseableHttpClient getClient(){
		System.out.println("start-----------------------apiclient_cert.p12");
		try{
			//指定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			//读取本机存放的PKCS12证书文件   
 			FileInputStream instream = new FileInputStream(new File("/soft/project/apiclient_cert.p12")); 
		//	FileInputStream instream = new FileInputStream(new File("D:/Users/HYL/Desktop/cert (1)/apiclient_cert.p12"));
			try { 
			//指定PKCS12的密码(商户ID)
			keyStore.load(instream, "1292180301".toCharArray()); 
			} finally { 
			instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom()
			.loadKeyMaterial(keyStore, "1292180301".toCharArray()).build();
			//指定TLS版本
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
			sslcontext,new String[] { "TLSv1" },null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			//设置httpclient的SSLSocketFactory
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			System.out.println("end-----------------------"+httpclient);
			return httpclient;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//充值
	public Map<String, Object> recharge(HttpServletRequest request,HttpServletResponse response,Map<String, Object> orderMap){
		//星艺商户相关资料 
		String appid=Constants.APPID;
		String appsecret =Constants.APPSECRET;
		String partner = Constants.PARTNER;
		String partnerkey = Constants.PARTNERKEY;
		String price=orderMap.get("price")+"";
		float sessionmoney = Float.parseFloat(price);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".","");
		System.out.println(sessionmoney);
		int one=price.indexOf(".");
		if(one>0){
				finalmoney=Integer.parseInt(finalmoney)+"";
		}
		String openId =orderMap.get("openid")+"";
		
		//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				String currTime = TenpayUtil.getCurrTime();
				//8位日期
				String strTime = currTime.substring(8, currTime.length());
				//四位随机数
				String strRandom = TenpayUtil.buildRandom(4) + "";
				//10位序列号,可以自行调整。
				String strReq = strTime + strRandom;
				
				
				//商户号
				String mch_id = partner;
				//子商户号  非必输
				//String sub_mch_id="";
				//设备号   非必输
				String device_info="";
				//随机数 
				String nonce_str = strReq;
				//商品描述
				//String body = describe;
				
				//商品描述根据情况修改
				String body = "瑞家U品充值";
				//附加数据
				String attach = orderMap.get("userid")+"";
				//商户订单号
				String out_trade_no = orderMap.get("out_trade_no")+"";
				//总金额以分为单位，不带小数点
//				int total_fee = intMoney;
				//订单生成的机器 IP
				String spbill_create_ip = request.getRemoteAddr();
				//订 单 生 成 时 间   非必输
//				String time_start ="";
				//订单失效时间      非必输
//				String time_expire = "";
				//商品标记   非必输
//				String goods_tag = "";
				
				//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				//"http://zx.hoheng.cn/weixin/tender/queryTenderList"
				String notify_url =orderMap.get("notuify_url")+"";
				
				String trade_type = "JSAPI";
				String openid = openId;
				//非必输
//				String product_id = "";
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);  
				packageParams.put("mch_id", mch_id);  
				packageParams.put("nonce_str", nonce_str);  
				packageParams.put("body", body);  
				packageParams.put("attach", attach);  
				packageParams.put("out_trade_no", out_trade_no);  
				
				
				//这里写的金额为1 分到时修改
				packageParams.put("total_fee", finalmoney);  
				packageParams.put("spbill_create_ip", spbill_create_ip);  
				packageParams.put("notify_url", notify_url);  
				
				packageParams.put("trade_type", trade_type);  
				packageParams.put("openid", openid);  

				RequestHandler reqHandler = new RequestHandler(request, response);
				reqHandler.init(appid, appsecret, partnerkey);
				
				String sign = reqHandler.createSign(packageParams);
				String xml="<xml>"+
						"<appid>"+appid+"</appid>"+
						"<mch_id>"+mch_id+"</mch_id>"+
						"<nonce_str>"+nonce_str+"</nonce_str>"+
						"<sign>"+sign+"</sign>"+
						"<body><![CDATA["+body+"]]></body>"+
						"<attach>"+attach+"</attach>"+
						"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
						"<total_fee>"+finalmoney+"</total_fee>"+
						"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
						"<notify_url>"+notify_url+"</notify_url>"+
						"<trade_type>"+trade_type+"</trade_type>"+
						"<openid>"+openid+"</openid>"+
						"</xml>";
				System.out.println(xml);
				String allParameters = "";
				try {
					allParameters =  reqHandler.genPackage(packageParams);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String prepay_id="";
				try {
					prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
					if(prepay_id.equals("")){
						request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
						System.out.println("统一支付接口获取预支付订单出错");
						return new HashMap<String, Object>();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SortedMap<String, String> finalpackage = new TreeMap<String, String>();
				String appid2 = appid;
				String timestamp = Sha1Util.getTimeStamp();
				String nonceStr2 = nonce_str;
				String prepay_id2 = "prepay_id="+prepay_id;
				String packages = prepay_id2;
				finalpackage.put("appId", appid2);  
				finalpackage.put("timeStamp", timestamp);  
				finalpackage.put("nonceStr", nonceStr2);  
				finalpackage.put("package", packages);  
				finalpackage.put("signType", "MD5");
				String finalsign = reqHandler.createSign(finalpackage);
				System.out.println(request.getContextPath()+"/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
				  
				Map<String, Object> paymap=new HashMap<String, Object>();
				paymap.put("appid", appid2);
				paymap.put("timeStamp", timestamp);
				paymap.put("nonceStr", nonceStr2);
				paymap.put("packagevalue", packages);
				paymap.put("signvalue", finalsign);
				paymap.put("out_trade_no", out_trade_no);
				return paymap;
	}
}
