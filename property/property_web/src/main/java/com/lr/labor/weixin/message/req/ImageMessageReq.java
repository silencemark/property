package com.lr.labor.weixin.message.req;

import com.lr.labor.weixin.message.BaseMessageReq;

/**
 * 图片消息
 * @author zhouqiuming
 *
 */
public class ImageMessageReq extends BaseMessageReq{
	private String PicUrl;
	private String MediaId;
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
