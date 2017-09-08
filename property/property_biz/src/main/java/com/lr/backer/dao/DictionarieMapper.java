package com.lr.backer.dao;

import java.util.List;
import java.util.Map;

public interface DictionarieMapper {
    //字典类型
	List<Map<String,Object>> getDictTypeList(Map<String,Object> map);
	
	int  getDictTypeListNum(Map<String,Object> map);
	
	void insertDicType(Map<String,Object> map);
	
	void updateDicType(Map<String,Object> map);
	//字典详情
    List<Map<String,Object>> getDicList(Map<String,Object> map);
	
	int  getDicListNum(Map<String,Object> map);
	
	void insertDic(Map<String,Object> map);
	
	void updateDic(Map<String,Object> map);
	
	
	
}
