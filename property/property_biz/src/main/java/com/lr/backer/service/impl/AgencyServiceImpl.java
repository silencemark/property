package com.lr.backer.service.impl;

 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.AgencyMapper;
import com.lr.backer.service.AgencyService;
 

public class AgencyServiceImpl implements AgencyService {

	@Autowired AgencyMapper agencyMapper;

	@Override
	public List<Map<String, Object>> getAgencyList(Map<String, Object> map) {
		 
		return this.agencyMapper.getAgencyList(map);
	}

	@Override
	public int getAgencyListNum(Map<String, Object> map) {
		
		return this.agencyMapper.getAgencyListNum(map);
	}

	@Override
	public void insertAgency(Map<String, Object> map) {
		 
		this.agencyMapper.insertAgency(map);
		
	}

	@Override
	public void updateAgency(Map<String, Object> map) {
	 
		this.agencyMapper.updateAgency(map);
		
	}

	@Override
	public List<Map<String, Object>> getChildrenAgency(Map<String, Object> map) {
		 
		
		return this.agencyMapper.getChildrenAgency(map);
	}

	@Override
	public List<Map<String, Object>> getAgencyImgList(Map<String, Object> map) {
		 
		return this.agencyMapper.getAgencyImgList(map);
	}

	@Override
	public void deleteAgencyImg(Map<String, Object> map) {
	   this.agencyMapper.deleteAgencyImg(map);
		
	}

	@Override
	public void insertAgencyImg(Map<String, Object> map) {
		 this.agencyMapper.insertAgencyImg(map);
		
	}

 
  
	 
	
}
