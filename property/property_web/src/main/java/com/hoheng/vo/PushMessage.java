package com.hoheng.vo;

import java.util.Map;

public class PushMessage {
	private String title;//消息标题
	private String content; //消息内容
	private String openId;//weixinopenid
	private String baiduChainId;//百度推送唯一id
	private String baiduTag;//百度推送tag
	private String url;//点击消息打开url
	private boolean isPushAll;//是否推送所有
	private String remark;//备注
	private String fromname;//发送来源
	private Map<String,Object> entryMap;//
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getBaiduChainId() {
		if("null".equals(baiduChainId)){
			return null;
		}
		return baiduChainId;
	}
	public void setBaiduChainId(String baiduChainId) {
		this.baiduChainId = baiduChainId;
	}
	public String getBaiduTag() {
		return baiduTag;
	}
	public void setBaiduTag(String baiduTag) {
		this.baiduTag = baiduTag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isPushAll() {
		return isPushAll;
	}
	public void setPushAll(boolean isPushAll) {
		this.isPushAll = isPushAll;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public Map<String, Object> getEntryMap() {
		return entryMap;
	}
	public void setEntryMap(Map<String, Object> entryMap) {
		this.entryMap = entryMap;
	}
	
}
