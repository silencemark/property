package com.hoheng.tool.baidupush;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.hoheng.vo.PushMessage;

public class BaiduPushTool {

	public static Logger logger = Logger.getLogger(BaiduPushTool.class);

	/**
	 * 推送单个设备
	 * 
	 * @param message
	 * @param cid
	 * @param deviceTypeAndroid
	 * @return
	 * @throws RuntimeException
	 */
	public static int pushSingleMsg(BaiduNoticeMessage message, String cid,
			Integer deviceType) throws RuntimeException {
		try {
			// 创建推送客户端
			BaiduPushClient pushClient = createPushClient(deviceType);
			// 设置请求参数，创建请求实例
			PushMsgToSingleDeviceRequest request = getSingleDeviceRequest(
					message, cid, deviceType);

			// 执行Http请求
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// Http请求返回值解析
			logger.debug("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
			return 1;
		} catch (Exception e) {
			// ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
			// 'true' 表示抛出, 'false' 表示捕获。
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw new RuntimeException(e.getCause());
			} else {
				e.getMessage();
				//e.printStackTrace();
			}
		}
		return 0;
	}

	private static BaiduPushClient createPushClient(Integer deviceType) {
		String api_key =BaiduPushConfig.API_KEY;
		String secret_key =BaiduPushConfig.SECRET_KEY;
		if(deviceType!=null && deviceType==4){
			api_key =BaiduPushConfig.API_KEY_IOS;
			secret_key =BaiduPushConfig.SECRET_KEY_IOS;
		}
		/*
		 * 1. 创建PushKeyPair用于app的合法身份认证apikey和secretKey可在应用详情中获取
		 */
		PushKeyPair pair = new PushKeyPair(api_key,secret_key);

		// 2. 创建BaiduPushClient，访问SDK接口
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. 注册YunLogHandler，获取本次请求的交互信息
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				logger.debug("event=" + event.getMessage());
			}
		});
		return pushClient;
	}

	/**
	 * 推送android单个设备信息
	 * 
	 * @param message
	 * @param cid
	 * @return
	 * @throws RuntimeException
	 */
	public static int pushSingleToAndroid(BaiduNoticeMessage message, String cid) {
		try {
			return pushSingleMsg(message, cid,
					BaiduPushConfig.DEVICE_TYPE_ANDROID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 推送android单个设备信息
	 * 
	 * @param message
	 * @param cid
	 * @return
	 * @throws RuntimeException
	 */
	public static int pushSingleToIOS(BaiduNoticeMessage message, String cid) {
		try{
		return pushSingleMsg(message, cid, BaiduPushConfig.DEVICE_TYPE_IOS);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 推送多个设备
	 * 
	 * @param message
	 * @param cid
	 * @return
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public static int pushMultipleToAndroid(BaiduNoticeMessage message,
			String[] cid) {

		try {
			// 创建推送客户端
			BaiduPushClient pushClient = createPushClient();
			// 4. specify request arguments
			String[] channelIds = cid;
			PushBatchUniMsgRequest request = getBatchUniMsgRequest(message, cid);
			// 5. http request
			PushBatchUniMsgResponse response = pushClient
					.pushBatchUniMsg(request);
			// Http请求返回值解析
			logger.debug(String.format("msgId: %s, sendTime: %d",
					response.getMsgId(), response.getSendTime()));
			return 1;
		} catch (Exception e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 推送android所有设备信息
	 * 
	 * @param message
	 * @param seconds
	 *            延迟发送时间,必须大于1分钟。
	 * @return
	 * @throws Exception
	 */
	public static int pushAllToAndroid(BaiduNoticeMessage message,
			Integer seconds) {
		try {
			return pushAllMsg(message, seconds,
					BaiduPushConfig.DEVICE_TYPE_ANDROID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 推送ios所有设备信息
	 * 
	 * @param message
	 * @param seconds
	 *            延迟发送时间,必须大于1分钟。
	 * @return
	 * @throws Exception
	 */
	public static int pushAllToIos(BaiduNoticeMessage message, Integer seconds) {
		try {
			return pushAllMsg(message, seconds, BaiduPushConfig.DEVICE_TYPE_IOS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 推送所有设备
	 * 
	 * @param message
	 * @param seconds
	 * @param deviceType
	 * @return
	 * @throws Exception
	 */
	public static int pushAllMsg(BaiduNoticeMessage message, Integer seconds,
			Integer deviceType) throws Exception {
		if (seconds == null || seconds <= 60) {
			throw new Exception("延迟发送时间,必须大于1分钟");
		}
		try {
			// 创建推送客户端
			BaiduPushClient pushClient = createPushClient(deviceType);
			// 4. specify request arguments
			PushMsgToAllRequest request = getPushMsgToAllRequest(message,
					deviceType, seconds, null, null);
			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Http请求返回值解析
			logger.debug(String.format("msgId: %s, sendTime: %d",
					response.getMsgId(), response.getSendTime()));
			return 1;
		} catch (Exception e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 获取批量推送请求
	private static PushBatchUniMsgRequest getBatchUniMsgRequest(
			BaiduNoticeMessage message, String[] channelIds) {
		return getBatchUniMsgRequest(message, channelIds, null, null);
	}

	// 获取批量推送请求
	private static PushBatchUniMsgRequest getBatchUniMsgRequest(
			BaiduNoticeMessage message, String[] channelIds, Integer msgType,
			Integer expires) {
		if (msgType == null) {
			msgType = BaiduPushConfig.MESSAGE_TYPE_NOTICE;
		}
		if (expires == null) {
			expires = BaiduPushConfig.MSGEXPIRES_DEFAULT;
		}
		PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
				.addChannelIds(channelIds).addMsgExpires(expires)
				.addMessageType(msgType)
				.addMessage(message.convertToJson().toString())
				.addDeviceType(BaiduPushConfig.DEVICE_TYPE_ANDROID)
				.addTopicId("BaiduPush");// 设置类别主题
		return request;
	}

	/*
	 * 创建推送客户端
	 */
	private static BaiduPushClient createPushClient() {
		/*
		 * 1. 创建PushKeyPair用于app的合法身份认证apikey和secretKey可在应用详情中获取
		 */
		PushKeyPair pair = new PushKeyPair(BaiduPushConfig.API_KEY,
				BaiduPushConfig.SECRET_KEY);

		// 2. 创建BaiduPushClient，访问SDK接口
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. 注册YunLogHandler，获取本次请求的交互信息
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				logger.debug("event=" + event.getMessage());
			}
		});
		return pushClient;
	}

	/**
	 * 获取推送所有推送请求
	 * 
	 * @param message
	 * @param deviceType
	 * @param seconds
	 * @param msgType
	 * @param expires
	 * @return
	 * @throws Exception
	 */
	private static PushMsgToAllRequest getPushMsgToAllRequest(
			BaiduNoticeMessage message, Integer deviceType, Integer seconds,
			Integer msgType, Integer expires) throws Exception {
		if (msgType == null) {
			msgType = BaiduPushConfig.MESSAGE_TYPE_NOTICE;
		}
		if (expires == null) {
			expires = BaiduPushConfig.MSGEXPIRES_DEFAULT;
		}
		if (deviceType == null) {
			deviceType = BaiduPushConfig.DEVICE_TYPE_ANDROID;
		}
		PushMsgToAllRequest request = new PushMsgToAllRequest()
				.addMsgExpires(expires).addMessageType(msgType)
				.addMessage(message.convertToJson().toString())
				// 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例75秒后推送
				.addSendTime(System.currentTimeMillis() / 1000 + seconds)
				.addDeviceType(deviceType);
		return request;
	}

	/*
	 * 设置请求参数，创建请求实例
	 */
	private static PushMsgToSingleDeviceRequest getSingleDeviceRequest(
			BaiduNoticeMessage message, String cid) {
		return getSingleDeviceRequest(message, cid,
				BaiduPushConfig.DEVICE_TYPE_ANDROID, null, null);
	}

	/*
	 * 设置请求参数，创建请求实例
	 */
	private static PushMsgToSingleDeviceRequest getSingleDeviceRequest(
			BaiduNoticeMessage message, String cid, Integer deviceType) {
		return getSingleDeviceRequest(message, cid, deviceType, null, null);
	}

	/*
	 * 设置请求参数，创建请求实例
	 */
	private static PushMsgToSingleDeviceRequest getSingleDeviceRequest(
			BaiduNoticeMessage message, String cid, Integer deviceType,
			Integer msgType, Integer expires) {
		if (msgType == null) {
			msgType = BaiduPushConfig.MESSAGE_TYPE_NOTICE;
		}
		if (expires == null) {
			expires = BaiduPushConfig.MSGEXPIRES_DEFAULT;
		}
		if (deviceType == null) {
			deviceType = BaiduPushConfig.DEVICE_TYPE_ANDROID;
		}
		
		PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
				.addChannelId(cid).addMsgExpires(expires)
				. // 设置消息的有效时间,单位秒,默认3600*5.
				addMessageType(msgType)
				.// 设置消息类型,0表示透传消息,1表示通知,默认为0.
				addMessage(message.convertToJson().toString())
				.addDeviceType(deviceType);
		return request;
	}
	
	
 

	public static void main(String[] args) {
		BaiduNoticeMessage message = new BaiduNoticeMessage();
		message.setDescription("批量推送测试 订单详情");
		message.setOpen_type(2);
		message.setUrl("http://www.ddjiaoren.com/workers/initgrabsingle?jobid=badcbc661cf9475a870e4466d94e4855&flag=2");
		Map<String, Object> custom_content = new HashMap<String, Object>();
		custom_content.put("keyurl", "http://www.ddjiaoren.com/workers/initgrabsingle?jobid=badcbc661cf9475a870e4466d94e4855&flag=2");
		
		message.setCustom_content(JSONObject.fromObject(custom_content));
		message.setTitle("嘀嗒叫人测试");
		String cid = "4422003242362870199";//
		String cid2 = "3498937901150268370";//伊利
		String cid3 = "3815497908111263573";//名
		try {
			//pushMultipleToAndroid(message, new String[]{cid,cid2});
			 pushSingleToAndroid(message, cid2);
			 pushSingleToAndroid(message, cid);
			 pushSingleToAndroid(message, cid3);
			 logger.debug(message+"\n");
			//pushAllToIos(message, 61);
		 
				// pushSingleToIOS(message, cid2);	 
		    
		    /* baidupush("测试地址", "内容》》http://www.baidu.com", cid2,"http://www.baidu.com" );*/
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
