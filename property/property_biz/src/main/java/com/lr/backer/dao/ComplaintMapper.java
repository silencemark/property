package com.lr.backer.dao;

import java.util.List;
import java.util.Map;

public interface ComplaintMapper {
  
	List<Map<String,Object>> getComplaintList(Map<String,Object> map);
	
	int  getComplaintListNum(Map<String,Object> map);
	
	void insertComplaint(Map<String,Object> map);
	
	void updateComplaint(Map<String,Object> map);
	
}
