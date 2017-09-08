package com.lr.backer.redpack;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lr.backer.controller.MyController;
import com.lr.backer.util.Constants;

public class SendMoney {
	final static  String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	private transient static Log log = LogFactory.getLog(SendMoney.class);
	
	public static String sendRedPack(String openid,String price,int sendnum,String wishes,String activityname){
		float sessionmoney = Float.parseFloat(price);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".","");
		System.out.println(sessionmoney);
		int one=price.indexOf(".");
		if(one>0){
			finalmoney=Integer.parseInt(finalmoney)+"";
		}
		int amount=Integer.parseInt(finalmoney);
		
		String orderNo =  MoneyUtils.getOrderNo() ; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nonce_str", MoneyUtils.buildRandom());//随机字符串
		map.put("mch_billno", orderNo);//商户订单
		map.put("mch_id", Constants.PARTNER);//商户号
		map.put("wxappid", Constants.APPID);//商户appid
		map.put("send_name", "资料完善奖励");//用户名
		map.put("re_openid", openid);//用户openid
		map.put("total_amount", amount);//付款金额
		map.put("total_num", sendnum);//红包发送总人数
		map.put("wishing", wishes);//红包祝福语
		map.put("client_ip", "121.41.13.18");//ip地址
		map.put("act_name", activityname);//活动名称
		map.put("remark", "请尽快领取");//备注
		map.put("sign", MoneyUtils.createSign(map));//签名
		log.info("sendmoney："+map.toString());
		String result = "";
		try {
			result = MoneyUtils.doSendMoney(url, MoneyUtils.createXML(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("result:"+result);
		return result;
	}
	
/*	public static void main(String[] args) {
		String orderNNo =  MoneyUtils.getOrderNo() ; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nonce_str", MoneyUtils.buildRandom());//随机字符串
		map.put("mch_billno", orderNNo);//商户订单
		map.put("mch_id", Constants.PARTNER);//商户号
		map.put("wxappid", Constants.APPID);//商户appid
		map.put("send_name", "嘀嗒叫人");//用户名
		map.put("re_openid", "");//用户openid
		map.put("total_amount", 100);//付款金额
		map.put("total_num", 1);//红包发送总人数
		map.put("wishing", "恭喜发财");//红包祝福语
		map.put("client_ip", "121.41.13.18");//ip地址
		map.put("act_name", "认证通过");//活动名称
		map.put("remark", "认证成功");//备注
		map.put("sign", MoneyUtils.createSign(map));//签名
		
		String result = "";
		try {
			result = MoneyUtils.doSendMoney(url, MoneyUtils.createXML(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("result:"+result);
	}*/
}
