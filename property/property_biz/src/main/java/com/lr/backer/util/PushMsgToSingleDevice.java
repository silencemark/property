package com.lr.backer.util;
  

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * 百度云推送
 * @author Administrator
 *
 */
public class PushMsgToSingleDevice {
    
	 /*1. 创建PushKeyPair
     *用于app的合法身份认证
     *apikey和secretKey可在应用详情中获取
     */
	
	private final static String apiKey = "CF8hV006GGbhVadi1ymWDr0h";
	private final static String secretKey = "0L30bom767rzvd12vOWQI0MOslUSdwQV";
	
	public static void main(String[] args) 
            throws Exception {
       // PushUtil.push("IOS", "推送成功", "4422003242362870199", "IOS");
		baidupush("测试推送通知", "测试通知内容", "4422003242362870199");
        
    }
	public void test() throws PushClientException, PushServerException{
		PushKeyPair pair = new PushKeyPair(apiKey,secretKey);

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler () {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
        // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                addChannelId("5276400098609349344").
                addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                addMessageType(1).//设置消息类型,0表示透传消息,1表示通知,默认为0.
                addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}"). 
                addDeviceType(4);      //设置设备类型，deviceType => 1 for web, 2 for pc, 
                                       //3 for android, 4 for ios, 5 for wp.
        // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                pushMsgToSingleDevice(request);
        // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) { 
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
	}
    
	public static int baidupush(Object title, Object text,Object cid) throws Exception{
    	JSONObject json = new JSONObject();
    	json.put("title", title==null?"滴嗒叫人":title);
    	json.put("description", text==null?"滴嗒叫人":text);
    	
    	/*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */
        PushKeyPair pair = new PushKeyPair(apiKey,secretKey);

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler () {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
        // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                addChannelId(cid.toString()).
                addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                addMessageType(1).//设置消息类型,0表示透传消息,1表示通知,默认为0.
                addMessage(json.toString()). 
                addDeviceType(4);      //设置设备类型，deviceType => 1 for web, 2 for pc, 
                                       //3 for android, 4 for ios, 5 for wp.
        // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                pushMsgToSingleDevice(request);
        // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
            return 1;
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) { 
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return 0;
    }
	
	/**
	 * 推送多台设备
	 * @param title
	 * @param text
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public static int baidupush(Object title, Object text,String[] cid) throws Exception{
		// 1. get apiKey and secretKey from developer console
		JSONObject json = new JSONObject();
    	json.put("title", title==null?"金童医生":title);
    	json.put("description", text==null?"金童医生":text);
    	
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            String[] channelIds = cid;
            PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
                    .addChannelIds(channelIds)
                    .addMsgExpires(new Integer(3600))
                    .addMessageType(1)
                    .addMessage(json.toString())
                    .addDeviceType(3).addTopicId("BaiduPush");// 设置类别主题
            // 5. http request
            PushBatchUniMsgResponse response = pushClient
                    .pushBatchUniMsg(request);
            // Http请求返回值解析
            System.out.println(String.format("msgId: %s, sendTime: %d",
                    response.getMsgId(), response.getSendTime()));
            return 1;
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
		return 0;
	}
	 
} 