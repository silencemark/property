package com.lr.backer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.util.Xml2JsonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Servlet implementation class payservlet
 */
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String STATUC_SUCCESS = "success";
	private static final String STATUC_FAIL    = "fail";
	/*@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("-----------------post"+HttpHeaderUtil.isIOSFrom(request));
		
		if(HttpHeaderUtil.isIOSFrom(request)){

			WeChatBuyPost postData = null;
			String           openid   = null;
			String       appsignature = null;
			try {
				ServletInputStream in = request.getInputStream();
				// 转换微信post过来的xml内容
				System.out.println("-------in :"+in);
				XStream xs = new XStream(new DomDriver());
				System.out.println("-------xs :"+xs);
				xs.alias("xml", WeChatBuyPost.class);
				String xmlMsg = Tools.inputStream2String(in);
				System.out.println("-------xmlMsg :"+xmlMsg);
				postData = (WeChatBuyPost) xs.fromXML(xmlMsg);
				
				System.out.println("-------"+postData.toString());
				// 校验支付
				long timestamp   = postData.getTimeStamp();
				String noncestr  = postData.getNonceStr();
				openid = postData.getOpenId();
				int issubscribe  = postData.getIsSubscribe();
				appsignature = postData.getAppSignature();
				boolean temp = Pay.verifySign(timestamp, noncestr, openid, issubscribe, appsignature);
				if (!temp) {
					System.out.println("校验支付error！");
					writeString(response, STATUC_FAIL);
				}else{
					Map<String, Object> orderMap = RedisUtil.getMap("orderMap"+openid);
					
					//修改当前查看人的订单状态（雇主是否 已经付款）
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("ifpay", "1");
					appMap.put("applyorderid", orderMap.get("applyorderid"));
					this.orderService.updateApplyOrderStatus(appMap);
					
					//保存保险记录
					Map<String, Object> insurinceMap = new HashMap<String, Object>();
					insurinceMap.put("insuranceid", StringUtil.getUUID());
					insurinceMap.put("orderid", orderMap.get("orderid"));
					insurinceMap.put("memberid", orderMap.get("applicantid"));
					insurinceMap.put("status","0");
					insurinceMap.put("createtime",new Timestamp(System.currentTimeMillis())); 
					this.tradeService.insertInsurance(insurinceMap);
					// 保存交易记录
					
					String successurl = orderMap.get("successurl") + "";
					orderMap.put("memberid", getUserId(request));
					orderMap.put("type", map.get("type"));
					this.orderService.savePayRecord(orderMap);
					RedisUtil.remove("orderMap" + getOpenId(request));
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("----------error");
			}
			// 微信post过来的参数


			@SuppressWarnings("unchecked")
			Map<String, String[]> parasMap = request.getParameterMap();
			// 构造新的参数


			Map<String, String> paraMap = new HashMap<String, String>();
			for (Entry<String, String[]> entry : parasMap.entrySet()) {
				String   key   = entry.getKey();
				String[] value = entry.getValue();
				System.out.println(key + ":\t" + value);
				if (null == value) {
					continue;
				} else {
					paraMap.put(key, value[0]);
				}
			}
			
			String trade_state  = request.getParameter("trade_state");
			String totalFee     = request.getParameter("total_fee");
			String orderId      = request.getParameter("out_trade_no");
			String transId      = request.getParameter("transaction_id");
			String timeEnd      = request.getParameter("time_end");

			System.out.println("trade_state:\t" + trade_state + "totalFee:\t" + totalFee + "orderId:\t" + orderId);
			
			if (StringUtils.isEmpty(orderId)) {
				writeString(response, STATUC_FAIL);
				return;
			}
			return;
		}
		return;
	}

	*//**

	 * 直接写字符串

	 * @param response

	 * @param msg

	 *//*
	private void writeString(HttpServletResponse response, String msg) {
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	public static void main(String[] args) {
		XStream xs = new XStream(new DomDriver());
		Xml2JsonUtil xml2=new Xml2JsonUtil();
		String jsonstr = xml2.xml2JSON("<xml><appid><![CDATA[wx13eb79f1d7c5ef59]]></appid></xml>");
		JSONObject postData=new JSONObject();
		Map<String, Object> wxmap=new HashMap<String, Object>();
		postData=postData.fromObject(jsonstr);
		wxmap=postData.getJSONObject("xml");
		System.out.println("-------"+wxmap.toString());
		String openid=wxmap.get("appid")+"";
		openid=openid.replace("[", "").replaceAll("\"", "").replace("]", "");
		System.out.println(openid);
	}
}
