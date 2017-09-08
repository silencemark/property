package com.lr.labor.weixin.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.URL;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.util.FileDeal;
import com.lr.backer.util.WeiXinSetUtil;
import com.lr.backer.util.baidujingweicity;
import com.lr.weixin.backer.service.BatteryService;
import com.lr.weixin.backer.service.MemberService;
import com.lr.weixin.backer.service.SystemWXService;
import com.lr.weixin.backer.service.WeiXinSetService;
import com.lr.labor.weixin.api.WeiXinMaterialAPI;
import com.lr.labor.weixin.message.resp.Article;
import com.lr.labor.weixin.message.resp.Image;
import com.lr.labor.weixin.message.resp.ImageMessageResp;
import com.lr.labor.weixin.message.resp.NewsMessageResp;
import com.lr.labor.weixin.message.resp.TextMessageResp;
import com.lr.labor.weixin.message.resp.Voice;
import com.lr.labor.weixin.message.resp.VoiceMessageResp;
import com.lr.labor.weixin.util.MessageUtil;
import com.lr.labor.weixin.util.WeiXinConfigure;
import com.lr.labor.weixin.util.WeixinUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
public class CoreService {
	
	private static final Logger LOGGER = Logger.getLogger(CoreService.class);
	private static String projectPath = WeiXinConfigure.PROJECT_PATH;

	private BatteryService batteryService;
	private SystemWXService systemService;
	private static CoreService coreService;
	private WeiXinSetService weiXinSetService;
	private MemberService memberService;

	public void init() {
		coreService = this;
		coreService.batteryService = this.batteryService;
		coreService.systemService = this.systemService;
		coreService.memberService = this.memberService;
		weiXinSetService = this.weiXinSetService;
	}

	public static BatteryService getBatteryService() {
		return coreService.batteryService;
	}

	public static MemberService getMemberService() {
		return coreService.memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setBatteryService(BatteryService batteryService) {
		this.batteryService = batteryService;
	}

	public static SystemWXService getSystemService() {
		return coreService.systemService;
	}

	public void setSystemService(SystemWXService systemService) {
		this.systemService = systemService;
	}

	public static WeiXinSetService getWeiXinSetService() {
		return coreService.weiXinSetService;
	}

	public void setWeiXinSetService(WeiXinSetService weiXinSetService) {
		this.weiXinSetService = weiXinSetService;
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		LOGGER.info("...........coreservice...............");
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			LOGGER.info("content::::::::::::::::" + requestMap);
			// 发送方帐号(open_id)
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 事件KEY值，qrscene_为前缀
			String eventKey = requestMap.get("EventKey");
			LOGGER.debug("CORESERVICE++++requestMap+++++++++++++++"+requestMap+"++++++++++++++++");
			LOGGER.debug("CORESERVICE++++eventKey+++++++++++++++"+eventKey+"++++++++++++++++++");

			BatteryService battaryService = getBatteryService();
			request.getSession().setAttribute("openid", fromUserName);
			// 回复文本消息
			TextMessageResp textMessageResp = new TextMessageResp();
			textMessageResp.setToUserName(fromUserName);
			textMessageResp.setFromUserName(toUserName);
			textMessageResp.setCreateTime(new Date().getTime());
			textMessageResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			// 切换多客服 = MoreCustomerServiceResp
			TextMessageResp moreCustomerServiceResp = new TextMessageResp();
			moreCustomerServiceResp.setToUserName(fromUserName);
			moreCustomerServiceResp.setFromUserName(toUserName);
			moreCustomerServiceResp.setCreateTime(new Date().getTime());
			moreCustomerServiceResp.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);

			NewsMessageResp newsMessageResp = new NewsMessageResp();
			newsMessageResp.setToUserName(fromUserName);
			newsMessageResp.setFromUserName(toUserName);
			newsMessageResp.setCreateTime(new Date().getTime());
			newsMessageResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

			LOGGER.info("msgType::::::::::::::::::" + msgType);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");

				// 用户发送的消息保存到数据库中
				/*Map<String, Object> message = new HashMap<String, Object>();
				message.put("openid", fromUserName);
				message.put("message", content);
				battaryService.insertMessage(message);*/

				// 创建图文消息
				NewsMessageResp newsMessage = new NewsMessageResp();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				// 单图文消息

				// 1. 根据用户输入的关键字，匹配对应需要回复的内容
				Map<String, Object> paramMap = new HashMap<String, Object>();
				// 文本消息
				// paramMap.put("msgtypeKey", msgType);
				// 关键字
				paramMap.put("key", content);
				
				// 匹配出的关键字列表
				LOGGER.info("=====key===========" + content);
				Map<String, Object> msgMap = getWeiXinSetService().getMsgByKey(
						paramMap);
				LOGGER.info(msgMap);
				if (null == msgMap || msgMap.isEmpty() || msgMap.size() == 0) {
					
					// 未匹配到关键字，切换为多客服
					String moreCustomerService = MessageUtil.textMessageToXml(moreCustomerServiceResp) ;
					LOGGER.info(moreCustomerService);
					return moreCustomerService;
/*
					// 未匹配到的关键字回复
					Map<String, Object> map = new HashMap<String, Object>();
					// 未匹配到的关键字回复 = 4
					map.put("typeid", "4");
					List<Map<String, Object>> dataList = getWeiXinSetService()
							.getDTTemplateList(map);
					if (null != dataList && dataList.size() > 0) {
						map = dataList.get(0);
					}
					String templatetype = MapUtils
							.getString(map, "templettype");
					String templatename = MapUtils
							.getString(map, "templetname");
					LOGGER.info(templatetype + "::::");
					if ("1".equals(templatetype)) {
						// 文本
						respContent = MapUtils.getString(map, "templetname");
					} else if ("3".equals(templatetype)) {
						// 图文消息
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("imgtextid", templatename.substring(2));
						List<Map<String, Object>> graphicList = getWeiXinSetService()
								.getDTImgTextItemList(temp);
						List<Article> artlist = new ArrayList<Article>();
						if (null != graphicList && graphicList.size() > 0) {
							for (Map<String, Object> item : graphicList) {
								Article article = new Article();
								article.setTitle(getMapValue(item, "title"));
								String picUrl = projectPath
										+ getMapValue(item, "imgurl");
								LOGGER
										.info(":::::::::::::::picUrl::::::::::::::"
												+ picUrl);
								article.setPicUrl(picUrl);
								 article.setUrl(getMapValue(item, "linkurl"));
								String linkurl = WeiXinConfigure.PROJECT_PATH
										+ "wwxset!showDTImgText.action?imgtextlistid="
										+ WeiXinSetUtil.getSecretID(MapUtils
												.getString(item,
														"imgtextlistid"));
								article.setUrl(linkurl);
								article.setDescription(getMapValue(item,
										"summary"));
								artlist.add(article);
							}
						}
						newsMessageResp.setArticles(artlist);
						newsMessageResp
								.setArticleCount(null != artlist ? artlist
										.size() : 0);
						String tempString = MessageUtil
								.newsMessageToXml(newsMessageResp);
						LOGGER.info(tempString);
						LOGGER.info(tempString);
						return tempString;
					} else if ("5".equals(templatetype)) {
						// 图片
						ImageMessageResp imageMessageResp = new ImageMessageResp();
						imageMessageResp.setToUserName(fromUserName);
						imageMessageResp.setFromUserName(toUserName);
						imageMessageResp.setCreateTime(new Date().getTime());
						imageMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);

						LOGGER.info(templatename);
						LOGGER.info(templatename.substring(2));
						Map<String, Object> temp = getSendImgMessage(templatename);

						if (null != map) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							Image image = new Image();
							image.setMediaId(mediaid);
							imageMessageResp.setImage(image);
							String resp = MessageUtil
									.imageMessageToXml(imageMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
					 
							return resp;
						}
					} else if ("12".equals(templatetype)) {
						// 语音
						VoiceMessageResp voiceMessageResp = new VoiceMessageResp();
						voiceMessageResp.setToUserName(fromUserName);
						voiceMessageResp.setFromUserName(toUserName);
						voiceMessageResp.setCreateTime(new Date().getTime());
						voiceMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);

						LOGGER.info("语音模版templatename=" + templatename);
						LOGGER.info(templatename.substring(2));
						Map<String, Object> temp = getSendVoiceMessage(templatename);

						if (null != temp) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							LOGGER.info("mediaid=" + mediaid);
							Voice voice = new Voice();
							voice.setMediaId(mediaid);
							LOGGER.info("voice.getMediaId()="
									+ voice.getMediaId());
							voiceMessageResp.setVoice(voice);
							System.out
									.println("voiceMessageResp.voice.getMediaId()="
											+ voiceMessageResp.getVoice()
													.getMediaId());
							String resp = MessageUtil
									.voiceMessageToXml(voiceMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
						 
							return resp;
						}
					}
					// =====================================================
*/
				} else {
					// 匹配到内容
					Map<String, Object> map = msgMap;
					String ruleid = MapUtils.getString(map, "ruleid");
					String replytype = MapUtils.getString(map, "replytype");
					String replycontent = MapUtils.getString(map,
							"replycontent");
					Integer replycount = MapUtils.getInteger(map, "replycount");
					replycount++;
					map.put("replycount", replycount);
					map.put("ruleid", ruleid);
					getWeiXinSetService().updateDTKeyRuleUseCount(map);
					LOGGER.info(replytype);
					if ("1".equals(replytype)) {
						// 文本
						respContent = replycontent;
					} else if ("3".equals(replytype)) {
						// 图文消息
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("imgtextid", replycontent.substring(2));
						List<Map<String, Object>> graphicList = getWeiXinSetService()
								.getDTImgTextItemList(temp);
						List<Article> artlist = new ArrayList<Article>();
						if (null != graphicList && graphicList.size() > 0) {
							for (Map<String, Object> item : graphicList) {
								Article article = new Article();
								article.setTitle(getMapValue(item, "title"));
								String picUrl = projectPath
										+ getMapValue(item, "imgurl");
								LOGGER
										.info(":::::::::::::::picUrl::::::::::::::"
												+ picUrl);
								article.setPicUrl(picUrl);
								 article.setUrl(getMapValue(item, "linkurl"));
								/*String linkurl = WeiXinConfigure.PROJECT_PATH
										+ "wwxset!showDTImgText.action?imgtextlistid="
										+ WeiXinSetUtil.getSecretID(MapUtils
												.getString(item,
														"imgtextlistid"));
								article.setUrl(linkurl);*/
								article.setDescription(getMapValue(item,
										"summary"));
								artlist.add(article);
							}
						}
						newsMessageResp.setArticles(artlist);
						newsMessageResp
								.setArticleCount(null != artlist ? artlist
										.size() : 0);
						String tempString = MessageUtil
								.newsMessageToXml(newsMessageResp);
						LOGGER.info(tempString);
						LOGGER.info(tempString);
						return tempString;
					} else if ("5".equals(replytype)) {
						// 图片
						ImageMessageResp imageMessageResp = new ImageMessageResp();
						imageMessageResp.setToUserName(fromUserName);
						imageMessageResp.setFromUserName(toUserName);
						imageMessageResp.setCreateTime(new Date().getTime());
						imageMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);

						LOGGER.info(replycontent);
						LOGGER.info(replycontent.substring(2));
						Map<String, Object> temp = getSendImgMessage(replycontent);

						if (null != map) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							Image image = new Image();
							image.setMediaId(mediaid);
							imageMessageResp.setImage(image);
							String resp = MessageUtil
									.imageMessageToXml(imageMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
							return resp;
						}
					} else if ("12".equals(replytype)) {
						// 语音
						VoiceMessageResp voiceMessageResp = new VoiceMessageResp();
						voiceMessageResp.setToUserName(fromUserName);
						voiceMessageResp.setFromUserName(toUserName);
						voiceMessageResp.setCreateTime(new Date().getTime());
						voiceMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);

						LOGGER.info("语音模版replycontent=" + replycontent);
						LOGGER.info(replycontent.substring(2));
						Map<String, Object> temp = getSendVoiceMessage(replycontent);

						if (null != temp) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							Voice voice = new Voice();
							voice.setMediaId(mediaid);
							voiceMessageResp.setVoice(voice);
							MessageUtil.voiceMessageToXml(voiceMessageResp);
							String resp = MessageUtil
									.voiceMessageToXml(voiceMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
							
							return resp;
						}
					}

				}
				// =================
				textMessageResp.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessageResp);

				return respMessage;
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				LOGGER.info("++++++++++++++++您发送的是图片消息");
//				respContent = "您发送的是图片消息！";

				// 未匹配到关键字，切换为多客服
				String moreCustomerService = MessageUtil.textMessageToXml(moreCustomerServiceResp) ;
				LOGGER.info(moreCustomerService);
				return moreCustomerService;
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				LOGGER.info("++++++++++++++++您发送的是地理位置消息！");
//				respContent = "您发送的是地理位置消息！";
				// 未匹配到关键字，切换为多客服
				String moreCustomerService = MessageUtil.textMessageToXml(moreCustomerServiceResp) ;
				LOGGER.info(moreCustomerService);
				return moreCustomerService;
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				LOGGER.info("++++++++++++++++您发送的是链接消息！");
//				respContent = "您发送的是链接消息！";
				// 未匹配到关键字，切换为多客服
				String moreCustomerService = MessageUtil.textMessageToXml(moreCustomerServiceResp) ;
				LOGGER.info(moreCustomerService);
				return moreCustomerService;
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				LOGGER.info("++++++++++++++++您发送的是音频消息！");
//				respContent = "您发送的是音频消息！";
				// 未匹配到关键字，切换为多客服
				String moreCustomerService = MessageUtil.textMessageToXml(moreCustomerServiceResp) ;
				LOGGER.info(moreCustomerService);
				return moreCustomerService;
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				LOGGER.info("eventType::::::::::::::::::::::::::::::::::::::::"
						+ eventType);
				LOGGER.info(MessageUtil.MASSSENDJOBFINISH + "===="
						+ eventType.toString() + "======"
						+ MessageUtil.MASSSENDJOBFINISH.equals(eventType));
					Map<String, Object> membermap=new HashMap<String, Object>();
					membermap.put("lasttime", new Date());
					membermap.put("openid",fromUserName);
					getMemberService().updateMember(membermap);
					if("SCAN".equals(eventType)){
						respContent = "您已关注，无需重复关注。";
					}
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					LOGGER.info("进入到订阅…………");

					Map<String, Object> pvMap = new HashMap<String, Object>();
					pvMap.put("openid", fromUserName);
					Map infoMap = WeixinUtil.getUserInfo(fromUserName);
					Map member = getMemberService().getMemberInfo(pvMap);
					
					
					
					if (member == null || member.isEmpty()) {
						
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("cityname", infoMap.get("city"));
						Map<String, Object> provinceCityMap = getBatteryService().getAreaLike(temp);
						
						String province = "";
						String city = "" ;
						
						if(null != provinceCityMap && !provinceCityMap.isEmpty()){
							city = MapUtils.getString(provinceCityMap, "areaid");
							province = MapUtils.getString(provinceCityMap, "parentid");
						}
/*						
						Map<String, Object> cityInfo=new HashMap<String, Object>();
						cityInfo.put("cname", infoMap.get("city"));
						cityInfo.put("iscityid", 1);
						Map<String, Object> citymap=getMemberService().getAreaInfo(cityInfo);
						cityInfo.put("cname", infoMap.get("province"));
						cityInfo.put("iscityid", 1);
						Map<String, Object> provincemap=getMemberService().getAreaInfo(cityInfo);
						if(citymap!=null && citymap.size()>0){
							city=citymap.get("areaid")+"";
						}if(provincemap!=null && provincemap.size()>0){
							province=provincemap.get("areaid")+"";
						}*/
						String scenestr = "";
						if(StringUtils.isNotBlank(eventKey)||eventKey.length()>8){
							scenestr = eventKey.substring(8, eventKey.length());
						}
						LOGGER.info("eventKey"+eventKey+"----------silence----------------"+scenestr);
						CoreService core=new CoreService();
						String parentpids="";
						if(!scenestr.equals("")){
							parentpids=core.getParentpid(scenestr,parentpids);
						}
						parentpids+=scenestr;
						LOGGER.info("parentpids:"+parentpids+"----------silence----------------scenestr:"+scenestr);
						
						member = new HashMap();
						member.put("openid", fromUserName);
						member.put("nickname", infoMap.get("nickname")+"");
						if(String.valueOf(infoMap.get("sex")).equals("0")){
							infoMap.put("sex", 2);
						}else if(String.valueOf(infoMap.get("sex")).equals("2")){
							infoMap.put("sex", 0);
						}
						member.put("sex",infoMap.get("sex"));
						member.put("cityid", city);
						member.put("provinceid", province);
						member.put("headimage", infoMap.get("headimgurl")); 
						member.put("ifattention", "1");
						member.put("gaintime", FileDeal.getSubscribeTime(infoMap));
						member.put("unionid", infoMap.get("unionid"));
						member.put("source", scenestr);
						member.put("sourcepid", parentpids);
						member.put("sourcetype", 1);
						LOGGER.debug("CORESERVICE++++member+++++++++++++++"+member+"++++++++++++++++++");
						LOGGER.info("unionid---------------------------------------"+infoMap.get("unionid"));
						String memberid=getMemberService().insertMember(member);
					}

					LOGGER.info("获取到的粉丝信息=" + infoMap);
					LOGGER.info("FileDeal.getSubscribeTime(infoMap)="
							+ FileDeal.getSubscribeTime(infoMap));

					// 关注后回复
					Map<String, Object> map = new HashMap<String, Object>();
					// 关注后自动回复 = 2
					map.put("typeid", "2");
					List<Map<String, Object>> dataList = getWeiXinSetService()
							.getDTTemplateList(map);
					if (null != dataList && dataList.size() > 0) {
						map = dataList.get(0);
					}
					String templatetype = MapUtils
							.getString(map, "templettype");
					String templatename = MapUtils
							.getString(map, "templetname");
					if ("1".equals(templatetype)) {
						// 文本
						respContent = MapUtils.getString(map, "templetname");
					} else if ("3".equals(templatetype)) {
						// 图文消息
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("imgtextid", templatename.substring(2));
						List<Map<String, Object>> graphicList = getWeiXinSetService()
								.getDTImgTextItemList(temp);
						List<Article> artlist = new ArrayList<Article>();
						if (null != graphicList && graphicList.size() > 0) {
							for (Map<String, Object> item : graphicList) {
								Article article = new Article();
								article.setTitle(getMapValue(item, "title"));
								String picUrl = projectPath
										+ getMapValue(item, "imgurl");
								LOGGER
										.info(":::::::::::::::picUrl::::::::::::::"
												+ picUrl);
								article.setPicUrl(picUrl);
								 article.setUrl(getMapValue(item, "linkurl"));
								/*String linkurl = WeiXinConfigure.PROJECT_PATH
										+ "wwxset!showDTImgText.action?imgtextlistid="
										+ WeiXinSetUtil.getSecretID(MapUtils
												.getString(item,
														"imgtextlistid"));
								article.setUrl(linkurl);*/
								article.setDescription(getMapValue(item,
										"summary"));
								artlist.add(article);
							}
						}
						newsMessageResp.setArticles(artlist);
						newsMessageResp
								.setArticleCount(null != artlist ? artlist
										.size() : 0);
						String tempString = MessageUtil
								.newsMessageToXml(newsMessageResp);
						LOGGER.info(tempString);
						LOGGER.info(tempString);
						return tempString;
					} else if ("5".equals(templatetype)) {
						// 图片
						ImageMessageResp imageMessageResp = new ImageMessageResp();
						imageMessageResp.setToUserName(fromUserName);
						imageMessageResp.setFromUserName(toUserName);
						imageMessageResp.setCreateTime(new Date().getTime());
						imageMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);

						LOGGER.info(templatename);
						LOGGER.info(templatename.substring(2));
						Map<String, Object> temp = getSendImgMessage(templatename);

						if (null != map) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							Image image = new Image();
							image.setMediaId(mediaid);
							imageMessageResp.setImage(image);
							String resp = MessageUtil
									.imageMessageToXml(imageMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
							return resp;
						}
					} else if ("12".equals(templatetype)) {
						// 语音
						VoiceMessageResp voiceMessageResp = new VoiceMessageResp();
						voiceMessageResp.setToUserName(fromUserName);
						voiceMessageResp.setFromUserName(toUserName);
						voiceMessageResp.setCreateTime(new Date().getTime());
						voiceMessageResp
								.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);

						LOGGER.info("语音模版templatename=" + templatename);
						LOGGER.info(templatename.substring(2));
						Map<String, Object> temp = getSendVoiceMessage(templatename);

						if (null != temp) {
							String mediaid = MapUtils
									.getString(temp, "mediaid");
							LOGGER.info("需要回复的语音消息的id=" + mediaid);
							Voice voice = new Voice();
							voice.setMediaId(mediaid);
							voiceMessageResp.setVoice(voice);
							String resp = MessageUtil
									.voiceMessageToXml(voiceMessageResp);
							// 生成微信返回的图片的xml
							LOGGER.info(resp);
							return resp;
						}
					}
					LOGGER.info("关注后自定义回复的内容="+respContent);
					// respContent = attentionReply(newsMessageResp);
					// LOGGER.info(respContent);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					LOGGER.info("进入到取消订阅…………");
					Map<String, Object> pvMap = new HashMap<String, Object>();
					pvMap.put("openid", fromUserName);
					getMemberService().cancelAttention(pvMap);
					// battaryService.cancalMember(pvMap);
				}
				//地理定位处理
				else if (eventType.equals("LOCATION")){
					
					Map<String, Object> pvMap = new HashMap<String, Object>();
					pvMap.put("openid", fromUserName);
					Map<String, Object> member = getMemberService().getMemberInfo(pvMap);
					LOGGER.info("获取地理位置---------membermap-"+member);
					String latitude = requestMap.get("Latitude");
					String longitude = requestMap.get("Longitude");
					baidujingweicity baidu=new baidujingweicity();
					String city=baidu.getcity(latitude, longitude);
					String province=baidu.getProvince(latitude, longitude);
					Map<String, Object> cityInfo=new HashMap<String, Object>();
					cityInfo.put("cname", city);
					Map<String, Object> citymap=getMemberService().getAreaInfo(cityInfo);
					cityInfo.put("cname", province);
					Map<String, Object> provincemap=getMemberService().getAreaInfo(cityInfo);
					if(member!=null && member.size()>0){
						pvMap=new HashMap<String, Object>();
						pvMap.put("userid", member.get("id"));
						pvMap.put("latitude", latitude);
						pvMap.put("longitude", longitude);
						if(citymap!=null && citymap.size()>0){
							pvMap.put("cityid", citymap.get("areaid"));
						}if(provincemap!=null && provincemap.size()>0){
							pvMap.put("provinceid", provincemap.get("areaid"));
						}
						
						getMemberService().updateExtend(pvMap);
					}
					
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

					Map<String, Object> paramMap = new HashMap<String, Object>();
					// 自定义菜单点击事件
					paramMap.put("msgtypeKey", eventType);
					// 关键字
					paramMap.put("key", eventKey);
					paramMap.put("ifactive", 1);
					if (MessageUtil.MASSSENDJOBFINISH.equals(eventType
							.toString())) {
						// 群发推送的结果
						messSendJobFinish(requestMap);
					} else {
						LOGGER.info("自定义菜单==============");

						// 自定义菜单
						// ******************* START（自定义菜单）
						// *******************************
						// eventKey即菜单表中的id值
						// 查询该表中的信息，根据数据，推送不同的消息
						Map<String, Object> temp = new HashMap<String, Object>();
						temp.put("menuid", eventKey);
						Map<String, Object> menuMap = getSystemService()
								.getDTMenu(temp);
						LOGGER.info("需要返回的菜单00000000000000000000");
						if (null != menuMap && menuMap.size() > 0) {

							String msgtype = MapUtils.getString(menuMap,
									"msgtype");
							String content = MapUtils.getString(menuMap,
									"linkurl");
							LOGGER.info("content=" + content);
							if ("1".equals(msgtype)) {
								// 文本
								respContent = content;
							} else if ("3".equals(msgtype)) {
								// 图文消息
								temp = new HashMap<String, Object>();
								temp.put("imgtextid", content.substring(2));
								List<Map<String, Object>> graphicList = getWeiXinSetService()
										.getDTImgTextItemList(temp);
								List<Article> artlist = new ArrayList<Article>();
								if (null != graphicList
										&& graphicList.size() > 0) {
									for (Map<String, Object> item : graphicList) {
										Article article = new Article();
										article.setTitle(getMapValue(item,
												"title"));
										String picUrl = projectPath
												+ getMapValue(item, "imgurl");
										LOGGER
												.info(":::::::::::::::picUrl::::::::::::::"
														+ picUrl);
										article.setPicUrl(picUrl);
										 article.setUrl(getMapValue(item,"linkurl"));
										/*String linkurl = WeiXinConfigure.PROJECT_PATH
												+ "wwxset!showDTImgText.action?imgtextlistid="
												+ WeiXinSetUtil
														.getSecretID(MapUtils
																.getString(
																		item,
																		"imgtextlistid"));
										article.setUrl(linkurl);*/
										article.setDescription(getMapValue(
												item, "summary"));
										artlist.add(article);
									}
								}
								newsMessageResp.setArticles(artlist);
								newsMessageResp
										.setArticleCount(null != artlist ? artlist
												.size()
												: 0);
								String tempString = MessageUtil
										.newsMessageToXml(newsMessageResp);
								LOGGER.info(tempString);
								LOGGER.info(tempString);
								return tempString;
							} else if ("5".equals(msgtype)) {
								// 图片
								ImageMessageResp imageMessageResp = new ImageMessageResp();
								imageMessageResp.setToUserName(fromUserName);
								imageMessageResp.setFromUserName(toUserName);
								imageMessageResp.setCreateTime(new Date()
										.getTime());
								imageMessageResp
										.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);

								LOGGER.info(content);
								LOGGER.info(content.substring(2));
								temp = getSendImgMessage(content);

								if (null != temp) {
									String mediaid = MapUtils.getString(temp,
											"mediaid");
									Image image = new Image();
									image.setMediaId(mediaid);
									imageMessageResp.setImage(image);
									String resp = MessageUtil
											.imageMessageToXml(imageMessageResp);
									// 生成微信返回的图片的xml
									LOGGER.info(resp);
									return resp;
								}
							} else if ("12".equals(msgtype)) {
								// 语音
								VoiceMessageResp voiceMessageResp = new VoiceMessageResp();
								voiceMessageResp.setToUserName(fromUserName);
								voiceMessageResp.setFromUserName(toUserName);
								voiceMessageResp.setCreateTime(new Date()
										.getTime());
								voiceMessageResp
										.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);

								LOGGER.info("语音模版content=" + content);
								LOGGER.info(content.substring(2));
								Map<String, Object> tt = getSendVoiceMessage(content);
								LOGGER.info("tt====" + tt);
								if (null != tt) {
									String mediaid = MapUtils.getString(tt,
											"mediaid");
									LOGGER.info("mediaid=" + mediaid);
									Voice voice = new Voice();
									voice.setMediaId(mediaid);
									voiceMessageResp.setVoice(voice);
									System.out
											.println("voiceMessageResp.voice.mediaId="
													+ voiceMessageResp
															.getVoice()
															.getMediaId());
									// MessageUtil.voiceMessageToXml(voiceMessageResp);
									String resp = MessageUtil
											.voiceMessageToXml(voiceMessageResp);
									// 生成微信返回的图片的xml
									LOGGER.info(resp);
									return resp;
								}
							}
						}
						// ******************* OVER（自定义菜单）
						// *******************************

					}
					// ========= OVER ============
					// return respMessage;
				}
			}
			
			LOGGER.info("关注后自定义回复的内容2222222="+respContent);
			textMessageResp.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessageResp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	/**
	 * 获取要发送的语音消息
	 */
	private static Map<String, Object> getSendVoiceMessage(String content) {
		Map<String, Object> temp = new HashMap<String, Object>();
		// 语音信息
		temp.put("id", content.substring(2));
		List<Map<String, Object>> imgList = getWeiXinSetService()
				.getDTAudioList(temp);
		temp = FileDeal.getListFirst(imgList);
		if (null == temp) {
			return null;
		}

		// 微信的媒体文件，上传只能保留三天
		// 每一次使用媒体文件之前首先判断当前的媒体文件是否超过三天，
		// 超过三天则重新上传一次
		// 未超过三天，则直接使用之前上传的信息
		// 判断当前的媒体文件是否已过期
		// true 表示过期 ； false 表示未过期
		LOGGER.info("test!!!!");
		boolean uploadstatus = WeiXinMaterialAPI.isMaterialOverdue(temp,
				"uploadwxtime");
		LOGGER.info(uploadstatus);
		LOGGER.info(uploadstatus);

		if (uploadstatus && (null != content && content.length() >= 2)) {

			String voicepath = MapUtils.getString(temp, "path");
			if (null == voicepath || "".equals(voicepath)) {
				return null;
			}

			// TODO 设置的地址需要修改
			//TODO 这里的地址需要修改
//			/E:/ws_jinyisheng/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/xingyi.web/WEB-INF/classes/
			String webProjectPath = CoreService.class.getResource("/").getPath(); 
			if(webProjectPath.contains("WEB-INF/classes/")){
				webProjectPath = webProjectPath.replace("WEB-INF/classes/","");
			}
			String url = webProjectPath + voicepath ;
			LOGGER.info(url);
			LOGGER.info(url);
			File file = new File(url);
			LOGGER.info(file.exists());
			LOGGER.info(file.exists());

			Map<String, Object> resultMap = WeiXinMaterialAPI.uploadMp3(file);
			LOGGER.info(resultMap);
			// 将结果更新到数据库中
			String mediaid = MapUtils.getString(resultMap, "mediaid");
			temp.put("mediaid", mediaid);
			temp.put("uploadwxtime", MapUtils.getString(resultMap, "createat"));
			// 将数据同步到数据库
			if (null != mediaid && !"".equals(mediaid)) {
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("id", MapUtils.getString(temp, "id"));
				tMap.put("mediaid", mediaid);
				tMap.put("uploadwxtime", MapUtils.getString(resultMap,
						"createat"));
				getWeiXinSetService().updateDTAudio(tMap);
			}
			// ===========
		} else {
			temp.put("id", content.substring(2));
			temp = getWeiXinSetService().getDTAudio(temp);
			LOGGER.info(temp);
		}
		return temp;
	}

	/**
	 * 获取要发送的图片消息
	 */
	private static Map<String, Object> getSendImgMessage(String content) {
		Map<String, Object> temp = new HashMap<String, Object>();
		// 上传图文信息
		temp.put("imgid", content.substring(2));
		List<Map<String, Object>> imgList = getWeiXinSetService().getDTImgList(
				temp);
		temp = FileDeal.getListFirst(imgList);
		if (null == temp) {
			return null;
		}

		// 微信的媒体文件，上传只能保留三天
		// 每一次使用媒体文件之前首先判断当前的媒体文件是否超过三天，
		// 超过三天则重新上传一次
		// 未超过三天，则直接使用之前上传的信息
		// 判断当前的媒体文件是否已过期
		// true 表示过期 ； false 表示未过期
		LOGGER.info("test!!!!");
		boolean uploadstatus = WeiXinMaterialAPI.isMaterialOverdue(temp,
				"uploadwxtime");
		LOGGER.info(uploadstatus);
		LOGGER.info(uploadstatus);

		if (uploadstatus && (null != content && content.length() >= 2)) {

			String img = MapUtils.getString(temp, "img");
			if (null == img || "".equals(img)) {
				return null;
			}

			//TODO 这里的地址需要修改
//			/E:/ws_jinyisheng/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/xingyi.web/WEB-INF/classes/
			String webProjectPath = CoreService.class.getResource("/").getPath(); 
			if(webProjectPath.contains("WEB-INF/classes/")){
				webProjectPath = webProjectPath.replace("WEB-INF/classes/","");
			}
			String url = webProjectPath + img ;
			LOGGER.info(url);
			LOGGER.info(url);
			File file = new File(url);
			LOGGER.info(file.exists());
			LOGGER.info(file.exists());

			Map<String, Object> resultMap = WeiXinMaterialAPI.uploadImg(file);
			LOGGER.info(resultMap);
			// 将结果更新到数据库中
			temp.put("mediaid", MapUtils.getString(resultMap, "mediaid"));
			temp.put("uploadwxtime", MapUtils.getString(resultMap, "createat"));
			// 将数据同步到数据库
			getWeiXinSetService().updateDTImg(temp);
		} else {
			temp.put("imgid", content.substring(2));
			temp = getWeiXinSetService().getDTImg(temp);
			LOGGER.info(temp);
		}
		return temp;
	}

	/**
	 * @function: 群发推送的结果
	 * @datetime:2015-1-26 下午02:00:30
	 * @Author: robin
	 * @param: @param requestMap
	 */
	private static void messSendJobFinish(Map<String, String> requestMap) {
		LOGGER.info("messSendJobFinish：：：：：：：：：：群发推送结果！！！");

		// 事件推送群发结果
		String msgid = MapUtils.getString(requestMap, "MsgID");
		String Status = MapUtils.getString(requestMap, "Status");
		//
		String totalCount = MapUtils.getString(requestMap, "TotalCount");
		String sendCount = MapUtils.getString(requestMap, "SentCount");
		String errorCount = MapUtils.getString(requestMap, "ErrorCount");
		// 根据消息id，将结果显示到
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("msgid", msgid);
		dataMap.put("totalCount", totalCount);
		dataMap.put("sendcount", sendCount);
		dataMap.put("errorCount", errorCount);
		String sendstatus = "";
		if ("send success".equals(Status)) {
			sendstatus = "3";
		} else if ("send fail".equals(Status)) {
			sendstatus = "4";
		}
		dataMap.put("sendstatus", sendstatus);
		// 微信创建时间
		long createTime = MapUtils.getLongValue(requestMap, "CreateTime");
		dataMap.put("sendtime", FileDeal.formatTimeStamp(createTime));

		LOGGER.info(dataMap.toString());
		// 将推送的结果存储到数据库中
		getWeiXinSetService().updateSYNoticeSendByWX(dataMap);
	}

	/**
	 * 判断是否是QQ表情
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;

		// 判断QQ表情的正则表达式
		String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	private static String getMapValue(Map<String, Object> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object valueObject = map.get(key);
			return null != valueObject ? valueObject.toString() : "";
		}
		return "";
	}
	
	 /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            
            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
        
        return false;
    }


    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || 
                (codePoint == 0x9) ||                            
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    
    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            System.out.println("+++++++++++++++"+i+"++++++++++++++"+codePoint);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                
                buf.append(codePoint);
            } else {
            }
        }
        
        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
        
    }
    /**
     * 获取父id 集合
     * @param source
     * @return
     */
    public String getParentpid(String source,String parentpids){
    	Map<String, Object> agencymap=new HashMap<String, Object>();
    	agencymap.put("userid", source);
    	agencymap=getMemberService().getAgencyInfo(agencymap);
    	if(agencymap!= null && agencymap.containsKey("parentuserid") && agencymap.get("parentuserid")!=null && !String.valueOf(agencymap.get("parentuserid")).equals("")){
    		String parentuserid=agencymap.get("parentuserid")+"";
    		parentpids+=parentuserid+",";
    		getParentpid(parentuserid,parentpids);
    	}
    	return parentpids;
    }
}
