package com.lr.backer.dao;

import java.util.List;
import java.util.Map;

public interface EvaluationMapper {
	//添加封杀记录
	void insertKillInfo(Map<String, Object> data);
    //查询评价列表 
	List<Map<String,Object>> getEvalList(Map<String,Object> map);
	//查询评价信息总记录数
	int  getEvalListNum(Map<String,Object> map);
	//修改评价状态
	void updateEvaluateStatus(Map<String,Object> map);
	/**
	  * 基于评价人ID、被评价人ID查询评价信息
	  * @param map
	  * @return
	  */
	List<Map<String, Object>> getEevaluations(Map<String, Object> map);
	Map<String, Object> getEevaStatistics(Map<String, Object> map);
	//新增评论信息
	void  insertEvaluate(Map<String,Object> map);
	/**
	 * 评价列表总记录数
	 * @param map
	 * @return
	 */
	int getEevaluationsNum (Map<String, Object> map);
	
}
