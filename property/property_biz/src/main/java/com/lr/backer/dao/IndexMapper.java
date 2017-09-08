package com.lr.backer.dao;
import java.util.List;
import java.util.Map;

public interface IndexMapper {
	//修改工具表表
	void updatesysparam(Map<String, Object> data);
	//修改扩展表
	void updateUserExtend(Map<String, Object> data);
	//字典表
	Map<String, Object> getDictInfo(Map<String, Object> data);
	//用户信息
	Map<String, Object> getMemberInfo(Map<String, Object> data);	
	//得到banner列表 
	List<Map<String, Object>> getBannerList(Map<String, Object> data);
	//雇主得到发布的订单 
	List<Map<String, Object>> getEmployerOrderList(Map<String, Object> data);
	//得到接收订单的人数 
	int giveOrderNum(Map<String, Object> data);
	//工人得到接收的订单
	List<Map<String, Object>> getWorkmanOrderList(Map<String, Object> data);	
	//查询字典表
	List<Map<String, Object>> getDictData(Map<String, Object> data);	
	//查询区域表
	List<Map<String, Object>> getAreaInfo(Map<String, Object> data);
}
