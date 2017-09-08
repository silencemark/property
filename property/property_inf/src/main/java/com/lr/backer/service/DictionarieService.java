package com.lr.backer.service;

import java.util.List;
import java.util.Map;

public interface DictionarieService {
	
List<Map<String,Object>> getDictTypeList(Map<String,Object> map);
	
	int  getDictTypeListNum(Map<String,Object> map);
	
	void insertDicType(Map<String,Object> map);
	
	void updateDicType(Map<String,Object> map);
	
	List<Map<String,Object>> getDicList(Map<String,Object> map);
		
	int  getDicListNum(Map<String,Object> map);
		
	void insertDic(Map<String,Object> map);
		
	void updateDic(Map<String,Object> map);
}
