package com.lr.backer.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.GetWxOrderno;
import com.utils.RequestHandler;
import com.utils.Sha1Util;
import com.utils.TenpayUtil;

public class paymoneyutil {
	
	public Map<String, Object> payMoney(HttpServletRequest request,HttpServletResponse response,Map<String, Object> orderMap){
		
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
				String body = "嘀嗒叫人查看工人信息所付佣金";
				//附加数据
				String attach = "支付佣金";
				//商户订单号
				String out_trade_no = appid+Sha1Util.getTimeStamp();
				int intMoney = Integer.parseInt(finalmoney);
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
				String trade_type ="";
//				if(openId!=null && !openId.equals("") && !openId.equals("null")){ 
					trade_type = "JSAPI";
//				}else{
//					trade_type = "APP";
//				}
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
//				if(openId!=null && !openId.equals("") && !openId.equals("null")){ 
					packageParams.put("openid", openid);  
//				}
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
						"<trade_type>"+trade_type+"</trade_type>";
				if(openId!=null && !openId.equals("") && !openId.equals("null")){ 
					xml+="<openid>"+openid+"</openid></xml>";
				}else{
					xml+="</xml>";
				}
						
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
				paymap.put("prepay_id", prepay_id);
				return paymap;
	}
	
	/**
	 * 生成app 微信支付所需签名 
	 * @author silence
	 */
	public Map<String, Object> getAppSign(HttpServletRequest request,HttpServletResponse response,Map<String, Object> orderMap){
		String appid="wx13eb79f1d7c5ef59";
		String appsecret="a66f78da1fe9e855e6d04224a1757a35";
		String partner ="1363829402";
		String partnerkey ="95e4ab543797f7bd93788dd64205b9db";
		String price=orderMap.get("price")+"";
		float sessionmoney = Float.parseFloat(price);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".","");
		System.out.println(sessionmoney);
		int one=price.indexOf(".");
		if(one>0){
				finalmoney=Integer.parseInt(finalmoney)+"";
		}
		
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

				//商品描述根据情况修改
				String body = "嘀嗒叫人查看工人信息所付佣金";
				//附加数据
				String attach = "支付佣金";
				//商户订单号
				String out_trade_no = appid+Sha1Util.getTimeStamp();

				//订单生成的机器 IP
				String spbill_create_ip = request.getRemoteAddr();
				String trade_type ="";
				
				String notify_url=orderMap.get("notuify_url")+"";
				trade_type = "APP";
				
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);  
				packageParams.put("mch_id", mch_id);  
				packageParams.put("nonce_str", nonce_str);  
				packageParams.put("body", body);  
				packageParams.put("attach", attach);  
				packageParams.put("out_trade_no", out_trade_no);  
				packageParams.put("notify_url", notify_url);  
				//这里写的金额为1 分到时修改
				packageParams.put("total_fee", finalmoney);  
				packageParams.put("spbill_create_ip", spbill_create_ip);  
				packageParams.put("trade_type", trade_type);
				
				System.out.println("---------------"+packageParams.toString());
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
						"<notify_url>"+notify_url+"</notify_url>"+
						"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
						"<trade_type>"+trade_type+"</trade_type>"+
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
				String timestamp = Sha1Util.getTimeStamp();
				
				finalpackage.put("appid", appid);  
				finalpackage.put("partnerid", mch_id);  
				finalpackage.put("prepayid", prepay_id);  
				finalpackage.put("package", "Sign=WXPay");
				finalpackage.put("noncestr", nonce_str);
				finalpackage.put("timestamp", timestamp);  
				String finalsign = reqHandler.createSign(finalpackage);
				
				
				Map<String, Object> datamap=new HashMap<String, Object>();
				datamap.put("total_fee", finalmoney);
				datamap.put("timestamp", timestamp);
				datamap.put("nonce_str", nonce_str);
				datamap.put("mch_id", mch_id);
				datamap.put("prepay_id", prepay_id);
				datamap.put("sign", finalsign);
				datamap.put("out_trade_no", out_trade_no);
				return datamap;
	}
}
