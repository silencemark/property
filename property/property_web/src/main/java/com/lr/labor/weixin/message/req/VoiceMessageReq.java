package com.lr.labor.weixin.message.req;

import com.lr.labor.weixin.message.BaseMessageReq;

public class VoiceMessageReq extends BaseMessageReq {
	private String MediaId;
	private String Format;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	
}
