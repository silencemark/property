package com.lr.backer.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.IndexMapper;
import com.lr.backer.service.IndexService;
import com.lr.backer.util.DateUtil;

public class IndexServiceImpl implements IndexService {

	@Autowired IndexMapper indexMapper;

	public List<Map<String, Object>> getBannerList(Map<String, Object> data) {
		return indexMapper.getBannerList(data);
	}
	
	public List<Map<String, Object>> getEmployerOrderList(Map<String, Object> data) {
		List<Map<String, Object>> list = indexMapper.getEmployerOrderList(data);
		if(list != null && list.size() > 0){
			for(Map<String,Object> map:list){
				if(map.containsKey("orderid")){
					map.put("numcount", indexMapper.giveOrderNum(map));
				}	
			}
		}
		return list;
	}

	public List<Map<String, Object>> getWorkmanOrderList(Map<String, Object> data) {
		List<Map<String, Object>> list = indexMapper.getWorkmanOrderList(data);
		if(list != null && list.size() > 0){
			for(Map<String,Object> map:list){
				if(map.containsKey("starttime")){
					map.put("daytext", DateUtil.getDatePoor(DateUtil.textToDate(map.get("starttime").toString()), new Date()));
				}	
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getDictData(Map<String, Object> data) {
		return indexMapper.getDictData(data);
	}

	@Override
	public List<Map<String, Object>> getAreaInfo(Map<String, Object> data) {
		return indexMapper.getAreaInfo(data);
	}

	@Override
	public Map<String, Object> getMemberInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.indexMapper.getMemberInfo(data);
	}

	@Override
	public Map<String, Object> getDictInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.indexMapper.getDictInfo(data);
	}

	@Override
	public void updateUserExtend(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.indexMapper.updateUserExtend(data);
	}

	@Override
	public void updatesysparam(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.indexMapper.updatesysparam(data);
	}
	
}
