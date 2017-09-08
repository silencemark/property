package com.lr.labor.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
public class weixinManage {
	private static Logger log = Logger.getLogger(MenuManager.class);
		public static boolean sendMassage(String openid,String calbackurl,String title,String content,String remark,String fromname){
			// 第三方用户唯一凭证
			String appId = WeiXinConfigure.APPID;
			// 第三方用户唯一凭证密钥
			String appSecret = WeiXinConfigure.APPSECRET;
			// 调用接口获取access_token
			String tokenid = WeiXinCenterProxy.getAccessToken();
			Map<String, Object> pvMap= new HashMap<String, Object>();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pvMap.put("openid", openid);
			pvMap.put("createtime",sdf.format(new Date()));
			pvMap.put("calbackurl",calbackurl);
			pvMap.put("title",title);
			pvMap.put("content",content);
			pvMap.put("remark", remark);
			pvMap.put("fromname", fromname);
		if (null != tokenid) {
			// 调用接口创建菜单
			int result = WeixinUtil.sendMessage(pvMap,tokenid);

			// 判断菜单创建结果
			if (0 == result) {
				log.info("消息发送成功！");
				return true;
			} else {
				log.info("失败，错误码：" + result);
			}
		}
		return false;
	}
}
