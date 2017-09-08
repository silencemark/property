package com.lr.weixin.backer.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SystemWXMapper {

	// 字典类型表
	void deleteDictType(Map<String, Object> data) ;
	void insertDictType(Map<String, Object> data) ;
	void updateDictType(Map<String, Object> data) ;
	Map<String, Object> getDictType(Map<String, Object> data) ;
	List<Map<String, Object>> getDictTypeList(Map<String, Object> data) ;
	int getDictTypeListNum(Map<String,Object> data);
	
	// 字典数据表
	void deleteDictData(Map<String, Object> data) ;
	void insertDictData(Map<String, Object> data) ;
	void updateDictData(Map<String, Object> data) ;
	Map<String, Object> getDictData(Map<String, Object> data) ;
	List<Map<String, Object>> getDictDataByCode(Map<String, Object> data);
	List<Map<String, Object>> getDictDataList(Map<String, Object> data) ;
	int getDictDataListNum(Map<String,Object> data);
	
	//用户表
	Map<String, Object> getInsertUser();
	void insertUser(Map<String, Object> data) ;
	void updateUser(Map<String, Object> data) ;
	void deleteUser(Map<String, Object> data) ;
	List<Map<String, Object>> getUserList(Map<String, Object> data) ;
	int getUserListNum(Map<String, Object> data) ;
	
	//角色
	void insertRole(Map<String, Object> data) ;
	void updateRole(Map<String, Object> data) ;
	void deleteRole(Map<String, Object> data) ;
	List<Map<String, Object>> getRoleList(Map<String, Object> data) ;
	int getRoleListNum(Map<String, Object> data) ;
	
	//功能
	void insertFunction(Map<String, Object> data) ;
	void updateFunction(Map<String, Object> data) ;
	void deleteFunction(Map<String, Object> data) ;
	List<LinkedHashMap<String, Object>> getFunctionList(Map<String, Object> data) ;
	int getFunctionListNum(Map<String, Object> data) ;
	
	//用户角色关联表
	void saveUserRole(Map<String, Object> data);
	void deleteUserRole(Map<String, Object> data);
	
	//角色功能
	void saveRoleFunction(Map<String, Object> data);
	void deleteRoleFunction(Map<String, Object> data);
	
	//素材管理
	void deleteMLBanner(Map<String, Object> data) ;
	void insertMLBanner(Map<String, Object> data) ;
	void updateMLBanner(Map<String, Object> data) ;
	Map<String, Object> getMLBanner(Map<String, Object> data) ;
	List<Map<String, Object>> getMLBannerList(Map<String, Object> data) ;
	int getMLBannerListNum(Map<String,Object> data);
	
	
	//菜单管理
	void deleteDTMenu(Map<String, Object> data) ;
	void insertDTMenu(Map<String, Object> data) ;
	void updateDTMenu(Map<String, Object> data) ;
	Map<String, Object> getDTMenu(Map<String, Object> data) ;
	List<LinkedHashMap<String, Object>> getDTMenuList(Map<String, Object> data);
	List<Map<String, Object>> getTopDTMenuList(Map<String, Object> data);
	
	//积分有效期设置
	List<Map<String, Object>> getInteValidtime();
	void updateInteValidtime(Map<String,Object> data);
	List<Map<String, Object>> getInteValidtimeXX();
	

	//获取消息类型
	List<Map<String, Object>> getMsgtypeList(Map<String, Object> data) ;
	
}