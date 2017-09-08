package com.lr.backer.dao;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
	//今日交易数
	int getTradeNumByNow();
	//今日抢单数
	int getApplyOrderCountByNow();
	//交易记录
	void insertTradeRecord(Map<String, Object> data);
	//嘀嗒记录表
	void insertCoinRecord(Map<String, Object> data);
	//修改我的信息扩展表
	void updateuserextend(Map<String, Object> data);
	//关注信息
	Map<String, Object> getAttention(Map<String, Object> data);
	//得到 发布订单 列表 
	List<Map<String, Object>> getReleaseOrderList(Map<String, Object> data);
	//得到 发布订单 数量
	int getReleaseOrderCount(Map<String, Object> data);
	//得到 抢单 列表 
	List<Map<String, Object>> getRushOrderList(Map<String, Object> data);
	//得到 抢单 数量  
	int getRushOrderCount(Map<String, Object> data);

	//根据项目id查询项目详情
	Map<String, Object> getProjectDetailById (Map<String, Object> data);
	//根据招工id查询招工详情
	Map<String, Object> getJobDetailById (Map<String, Object> data);
	//根据项目订单id查有多少人接单
	List<Map<String, Object>> getApplyOrderById(Map<String, Object> data);
	//根据项目订单id查有多少人接单总数量  
	int getApplyOrderByIdNum(Map<String, Object> data);
	//根据项目id查询项目图片
	List<Map<String, Object>> getProjectImgs(Map<String, Object> data);
	
	/**
	 * 获取订单发布数
	 * @param data
	 * @return
	 */
	Map<String,Object> getOrderCount(Map<String,Object> data);
	/**
	 * 获取抢单数
	 * @param data
	 * @return
	 */
	Map<String,Object> getApplyOrderCount(Map<String,Object> data);
	//取消订单
	void updateApplyOrderStatus (Map<String, Object> data);
	/**
	 * 更新订单表状态
	 * @param map
	 */
	void updateOrderStatus(Map<String, Object> map);
	/**
	 * 用户信誉记录
	 * @param map
	 */
	void insertCredit(Map<String, Object> map);
	
    /**
     * 后台查询 雇主订单有多少人抢单
     * @param data
     * @return
     */
	List<Map<String, Object>> getApplyPeopleList(Map<String, Object> data);
	
	/**
     * 后台查询 雇主订单有多少人抢单 总记录数
     * @param data
     * @return
     */
	int getApplyPeopleListNum(Map<String, Object> data);
	
}
