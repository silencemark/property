package com.lr.backer.service;

import java.util.List;
import java.util.Map;

public interface MyService {

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
	 * 生成会员二维码
	 * @param map
	 * @return
	 */
	String createMyQRCode(Map<String, Object> map);
	
	/**
	 * 获取会员关注信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getAttentionMembers(Map<String, Object> map);
	
	/**
	 * 获取会员详细信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getMemberDetail(Map<String, Object> map);
	/**
	 * 获取关注信息
	 * @param map
	 */
	List<Map<String,Object>> getAttention(Map<String, Object> map);
	/**
	 * 保存关注
	 * @param map
	 */
	void insertAttention(Map<String, Object> map);
	
	/**
	 * 查询申请订单及关联的发布订单信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getApplyOrderAndOrder(Map<String, Object> map);
	/**
	 * 更新申请订单状态
	 * @param map
	 */
	void updateApplyOrderStatus(Map<String, Object> map);
	String getWeixinQRcode(String memberid, String filePath, String access_token);
	
	/**
	 * 查询交记录总记录数
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
