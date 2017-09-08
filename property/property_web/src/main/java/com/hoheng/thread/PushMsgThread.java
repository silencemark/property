package com.hoheng.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hoheng.tool.baidupush.BaiduNoticeMessage;
import com.hoheng.tool.baidupush.BaiduPushTool;
import com.hoheng.vo.PushMessage;
import com.lr.backer.service.EmployerService;
import com.lr.labor.weixin.util.weixinManage;

public class PushMsgThread implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	private EmployerService employerService;
	private static PushMsgThread pushMsgThread;

	public void init() {
		pushMsgThread = this;
		pushMsgThread.employerService = this.employerService;
	}

	public EmployerService getEmployerService() {
		return pushMsgThread.employerService;
	}

	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}

	public void run() {
		int i = 0;
		while (true) {
			i++;
			BaiduNoticeMessage message = null;
			List<PushMessage> msgs = null;
			try {
				msgs = MemoryStatic.pushMsgQueue.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("come in " + i + "次" + "msgs.size="
					+ msgs.size());
			for (PushMessage msg : msgs) {

				try {
					// 判断是否推送所有,是则使用百度推送推送所有
					if (msg.isPushAll()) {
						message = new BaiduNoticeMessage();
						message.setTitle(msg.getTitle());
						message.setDescription(msg.getContent());
						message.setUrl(msg.getUrl());
						Map<String, Object> contenurl=new HashMap<String, Object>();
						contenurl.put("keyurl",msg.getUrl());
						message.setCustom_content(JSONObject.fromObject(contenurl));
						logger.debug("push android Allmessage begin=========-------------------------------------------------------------------------------------------1");
						BaiduPushTool.pushAllToAndroid(message, 100);
						logger.debug("push android Allmessage end=========-----------------------------------------------------------------------------------------------2");
						logger.debug("push ios Allmessage begin=========-----------------------------------------------------------------------------------3");
						BaiduPushTool.pushAllToIos(message, 100);
						logger.debug("push ios Allmessage end=========-------------------------------------------------------------------------------------------4");
						continue;
					}
					// 判断 weixnopenid不为空则推送微信信息
					if (StringUtils.isNotBlank(msg.getOpenId())) {
						// 推送微信信息
						logger.debug("push weixin message begin=========------------------------------------------------------5");
						try {
							weixinManage.sendMassage(msg.getOpenId(),
									msg.getUrl(), msg.getTitle(),
									msg.getContent(), msg.getRemark(),
									msg.getFromname());
						} catch (Exception e) {
							logger.error(e.getMessage());
							e.printStackTrace();
						}
						logger.debug("push weixin message end=========----------------------------------------------------------------------6");
					}

					// 判断百度chainid不为空则推送百度信息
					if (StringUtils.isNotBlank(msg.getBaiduChainId())) {
						// 构建百度推送信息
						message = new BaiduNoticeMessage();
						message.setTitle(msg.getTitle());
						message.setDescription(msg.getContent());
						Map<String, Object> contenurl=new HashMap<String, Object>();
						contenurl.put("keyurl",msg.getUrl());
						message.setCustom_content(JSONObject.fromObject(contenurl));
						message.setUrl(msg.getUrl());
						message.setOpen_type(2);
						logger.debug("构建后的百度推送信息=====message=" + message);
						// 推送android信息
						logger.debug("push android message begin=========--------------------------------------------------------------------7");
						BaiduPushTool.pushSingleToAndroid(message,
								msg.getBaiduChainId());
						logger.debug("push android message end=========------------------------------------------------------------------------------------8");
						// 推送ios信息
						message.setOpen_type(0);
						logger.debug("push ios message begin=========------------------------------------------------------------------------------------9");
						BaiduPushTool.pushSingleToIOS(message,
								msg.getBaiduChainId());
						logger.debug("push ios message end=========---------------------------------------------------------------------------------------10");
					}
					// 如果实体map为不空则插入数据库
					if (msg.getEntryMap() != null
							&& msg.getEntryMap().size() > 0) {
						getEmployerService().insertprojectMessage(
								msg.getEntryMap());
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			// 休眠200ms
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}