package com.lr.labor.weixin.message.resp;

import com.lr.labor.weixin.message.BaseMessageResp;

public class VideoMessageResp extends BaseMessageResp {
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
