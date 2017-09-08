package com.hoheng.thread;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hoheng.vo.PushMessage;

public class MemoryStatic {
	//消息推送队列
	public static BlockingQueue<List<PushMessage>> pushMsgQueue = new LinkedBlockingQueue<List<PushMessage>>(100);
}
