package com.lr.weixin.backer.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SystemWXService {

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
	List<Map<String, Object>> getDictDataList(Map<String, Object> data) ;
	int getDictDataListNum(Map<String,Object> data);
	
	
	String getInsertUser();
	//用户表
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

	List<LinkedHashMap<String, Object>> getFunctionDataGridList() ;
	
	//用户角色关联表
	void saveUserRole(Map<String, Object> data);
	
	//角色功能
	void saveRoleFunction(Map<String, Object> data);
	
	void deleteMLBanner(Map<String, Object> data) ;
	void insertMLBanner(Map<String, Object> data) ;
	void updateMLBanner(Map<String, Object> data) ;
	Map<String, Object> getMLBanner(Map<String, Object> data) ;
	List<Map<String, Object>> getMLBannerList(Map<String, Object> data) ;
	int getMLBannerListNum(Map<String,Object> data);
	/**
	 * @function:
	 * @datetime:2014-11-5 上午11:13:43
	 * @Author: robin
	 * @param: @param placecode 位置编码
	 * @return Map<String,Object>
	 */
	Map<String, Object> getMLBannerByPlaceCode(String placecode);
	/**
	 * @function:获取位置对应的图片
	 * @datetime:2014-11-5 上午11:27:28
	 * @Author: robin
	 * @param: @param placecode 图片编码
	 * @return String
	 */
	String getImgByPlaceCode(String placecode);
	
	//菜单管理
	void deleteDTMenu(Map<String, Object> data) ;
	void insertDTMenu(Map<String, Object> data) ;
	void updateDTMenu(Map<String, Object> data) ;
	Map<String, Object> getDTMenu(Map<String, Object> data) ;
	List<LinkedHashMap<String, Object>> getDTMenuList(Map<String, Object> data);
	List<Map<String, Object>> getTopDTMenuList(Map<String, Object> data);
	

	//积分有效期设置
	Map<String, Object> getInteValidtime();
	void updateInteValidtime(Map<String,Object> data);

	// 获取消息类型
	List<Map<String, Object>> getMsgtypeList(String requestType);
	List<Map<String, Object>> getMsgtypeList(Map<String, Object> data);

	
}
