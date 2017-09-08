package com.lr.backer.service;

import java.util.List;
import java.util.Map;
 
public interface EvaluationService {
	 //查询评价列表 
	 List<Map<String,Object>> getEvalList(Map<String,Object> map);
	 //查询评价信息总记录数
	 int getEvalListNum(Map<String,Object> map);
	 //修改评价状态
	 void updateEvaluateStatus(Map<String,Object> map);
	 /**
	  * 基于评价人ID、被评价人ID查询评价信息
	  * @param map
	  * @return
	  */
	List<Map<String, Object>> getEvaluations(Map<String, Object> map);
	/**
	  * 评价信息统计，平均值（天花板取）、总分数、评价数量
	  * @param map
	  * @return
	  */
	Map<String, Object> getEvaStatistics(Map<String, Object> map);
	
	//新增评论信息
	void  insertEvaluate(Map<String,Object> map);
	
	/**
	 * 评价列表总记录数
	 * @param map
	 * @return 
	 */
	int getEevaluationsNum (Map<String, Object> map);
}
