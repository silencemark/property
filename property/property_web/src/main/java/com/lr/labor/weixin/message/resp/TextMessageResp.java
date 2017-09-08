package com.lr.labor.weixin.message.resp;

import com.lr.labor.weixin.message.BaseMessageResp;

/**
 * 文本消息
 * @author zhouqiuming
 *
 */
public class TextMessageResp extends BaseMessageResp{
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TextMessageResp:[");
		sb.append("toUserName="+this.ToUserName).append(",");
		sb.append("fromUserName="+this.FromUserName).append(",");
		sb.append("createTime="+this.CreateTime).append(",");
		sb.append("msgType="+this.MsgType).append(",");
		sb.append("content="+this.Content);
		sb.append("]");
		return sb.toString();
		
	}
	
	
}
