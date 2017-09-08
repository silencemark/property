package com.lr.backer.service.impl;

 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.ComplaintMapper;
import com.lr.backer.service.ComplaintService;
 

public class ComplaintServiceImpl implements ComplaintService {

	@Autowired ComplaintMapper complaintMapper;


	@Override
	public List<Map<String, Object>> getComplaintList(Map<String, Object> map) {
		 
		return this.complaintMapper.getComplaintList(map);
	}

	@Override
	public int getComplaintListNum(Map<String, Object> map) {
		
		return this.complaintMapper.getComplaintListNum(map);
	}

	@Override
	public void insertComplaint(Map<String, Object> map) {
		 
		this.complaintMapper.insertComplaint(map);
	}

	@Override
	public void updateComplaint(Map<String, Object> map) {
	 
		this.complaintMapper.updateComplaint(map);
		
	}
  
	 
	
}
