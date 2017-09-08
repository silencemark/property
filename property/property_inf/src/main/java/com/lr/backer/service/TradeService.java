package com.lr.backer.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 财务管理
 * @author 唐杰 
 * 
 */
public interface TradeService {
	List<Map<String, Object>> gettradelist(Map<String, Object> data);
	int gettradenum(Map<String, Object> data);
	/**
	 * 新增用户保险记录
	 * @param data
	 */
	void insertInsurance(Map<String, Object> data);
	
}
