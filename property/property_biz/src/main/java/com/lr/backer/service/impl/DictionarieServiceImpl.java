package com.lr.backer.service.impl;

 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.DictionarieMapper;
import com.lr.backer.service.DictionarieService;
 

public class DictionarieServiceImpl implements DictionarieService {

	@Autowired DictionarieMapper dictionarieMapper;

	@Override
	public List<Map<String, Object>> getDictTypeList(Map<String, Object> map) {
		 
		return this.dictionarieMapper.getDictTypeList(map);
	}

	@Override
	public int getDictTypeListNum(Map<String, Object> map) {
		
		return this.dictionarieMapper.getDictTypeListNum(map);
	}

	@Override
	public void insertDicType(Map<String, Object> map) {
		 
		this.dictionarieMapper.insertDicType(map);
		
	}

	@Override
	public void updateDicType(Map<String, Object> map) {
		 
		this.dictionarieMapper.updateDicType(map);
	}

	@Override
	public List<Map<String, Object>> getDicList(Map<String, Object> map) {
		
		return this.dictionarieMapper.getDicList(map);
	}

	@Override
	public int getDicListNum(Map<String, Object> map) {
		 
		return this.dictionarieMapper.getDicListNum(map);
	}

	@Override
	public void insertDic(Map<String, Object> map) {
		 
		this.dictionarieMapper.insertDic(map);
	}

	@Override
	public void updateDic(Map<String, Object> map) {
		 
		this.dictionarieMapper.updateDic(map);
	}

 
	 
	
}
