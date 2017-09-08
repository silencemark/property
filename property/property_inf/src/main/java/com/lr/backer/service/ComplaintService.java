package com.lr.backer.service;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author silence
 *
 */
public interface ComplaintService {
 
	List<Map<String,Object>> getComplaintList(Map<String,Object> map);
	
	int  getComplaintListNum(Map<String,Object> map);
	
	void insertComplaint(Map<String,Object> map);
	
	void updateComplaint(Map<String,Object> map);
}
