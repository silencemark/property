package com.lr.backer.dao;

import java.util.List;
import java.util.Map;

public interface MyMapper {

	/**
	 * 查询滴嗒用户信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getTickUserList(Map<String, Object> map);
	/**
	 * 查询会员交易记录信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getTradeRecord(Map<String,Object> map);
	/**
	 * 查询会员交易总金
	 * @param map
	 * @return
	 */
	Map<String,Object> getTradeSum(Map<String,Object> map);
	/**
	 * 查询滴嗒币记录
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getcoinRecord(Map<String,Object> map);
	
	/**
	 * 获取用户信息及用户所属性区域
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getMembersForArea(Map<String,Object> map);
	

	/**
	 * 基于会员ID查询会员关注人员信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getAttentionMembers(Map<String,Object> map);
	
	/**
	 * 获取用户信息及用户所属性区域
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getMemberDetail(Map<String,Object> map);
	
	/**
	 * 获取关注信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getAttention(Map<String, Object> map);
	void insertAttention(Map<String, Object> map);
	/**
	 * 查询申请订单及关联的发布订单信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getApplyOrderAndOrder(Map<String, Object> map);
	void updateApplyOrderStatus(Map<String, Object> map);
	
	/**
	 * 查询交记滴答币录总记录数
	 * @param map
	 * @return
	 */
	int getcoinRecordNum(Map<String, Object> map);
	
	/**
	 * 交易记录总数
	 * @param map
	 * @return
	 */
	int getTradeRecordNum(Map<String, Object> map);
	
	/**
	 *  基于会员ID查询会员关注用户总记录数 
	 * @param map
	 * @return
	 */
	int getAttentionMembersNum(Map<String, Object> map);
	/**
	 * 获取剩余招聘人数
	 * @param map
	 * @return
	 */
	int getSurplusAppNum(Map<String, Object> map);
	/**
	 *取消关注 
	 * @param map
	 */
	void cancelAttention(Map<String, Object> map);
	

}
