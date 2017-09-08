package com.lr.labor.weixin.message.resp;

import com.lr.labor.weixin.message.BaseMessageResp;

public class VoiceMessageResp extends BaseMessageResp {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}