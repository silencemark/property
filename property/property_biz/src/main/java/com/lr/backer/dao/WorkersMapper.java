package com.lr.backer.dao;
import java.util.List;
import java.util.Map;

public interface WorkersMapper {
	//查询抢单信息
	List<Map<String, Object>> getApplyOrderList(Map<String, Object> data);
	//招工的接单列表
	List<Map<String, Object>> getApplyJobOrder(Map<String, Object> data);
	//接单信息
	Map<String, Object> getApplyOrder(Map<String, Object> data);
	//项目信息
	Map<String, Object> getProjectInfo(Map<String, Object> data);
	//项目列表
	List<Map<String, Object>> getProjectChartList(Map<String, Object> data);	
	//查询是否关注
	Map<String, Object> getAttention(Map<String, Object> data);
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
	 * 判断订单是否过期 
	 * @param map
	 * @return
	 */
	Map<String, Object> getOrderInfo(Map<String, Object> map);
	
	/**
	 * 查询当前订单雇主已经成交了多少人 
	 * @param map
	 * @return
	 */
	int getApplyNum(Map<String, Object> map);
}
