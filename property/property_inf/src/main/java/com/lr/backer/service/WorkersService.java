package com.lr.backer.service;

import java.util.List;
import java.util.Map;

public interface WorkersService {
	//查询抢单信息
	List<Map<String, Object>> getApplyOrderList(Map<String, Object> data);
	
	Map<String, Object> getApplyOrder(Map<String, Object> data);
	//项目信息
	Map<String, Object> getProjectInfo(Map<String, Object> data);
	//项目列表
	List<Map<String, Object>> getProjectChartList(Map<String, Object> data);
	//订单表
	int insertApplyOrder(Map<String, Object> data);
	//招工信息
	Map<String, Object> getJobInfo(Map<String, Object> data);
	//招工列表
	List<Map<String, Object>> getHireWorkerList(Map<String, Object> data);
	
	/**
	 *得到抢单招工记录总数 
	 * @param data
	 * @return
	 */
	int getHireWorkerListNum (Map<String, Object> data);
	
	/**
	 * 得到项目报价项目总记录数
	 * @param data
	 * @return
	 */
	int getProjectChartListNum (Map<String, Object> data);
	
	
	/**
	 * 判断订单是否满足条件
	 * @param data
	 * @return
	 */
	int judgeOrder(Map<String, Object> data);
 
}
