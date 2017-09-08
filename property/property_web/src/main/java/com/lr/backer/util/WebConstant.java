package com.lr.backer.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface WebConstant {
	String USER_SESSION_KEY = "user";
	String PROJECT_SESSION_KEY = "project";
	String FUNCTION_MAP = "function";
	String FUNCTION_ID = "functionid";
	String NAVIGATION = "navi";
	String topicnum = "topicnum";
	String visitnum = "visitnum";
	Map<String,Object> Comm_stat = new ConcurrentHashMap<String,Object>();
	
	public enum CancleApointSourceEnum {
		self,//预约服务人自己
		agency; //经销商
	}
}
