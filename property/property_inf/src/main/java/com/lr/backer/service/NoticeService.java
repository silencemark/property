package com.lr.backer.service;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author silence
 *
 */
public interface NoticeService {
	
    List<Map<String,Object>> getNoticesList(Map<String,Object> map);
	
	int  getNoticesListNum(Map<String,Object> map);
	
	void insertNotice(Map<String,Object> map);
	
	void updateNotice(Map<String,Object> map);	
	
    List<Map<String,Object>> getSystemNoticeMembers(Map<String,Object> map);
	
	int  getSystemNoticeMembersNum(Map<String,Object> map);
	
	void sendSystemNotice (Map<String,Object> map);
	
	List<Map<String,Object>> getBusinessNoticeList(Map<String,Object> map);
		
    int  getBusinessNoticeListNum(Map<String,Object> map);
    
    List<Map<String,Object>> getAllNotice(Map<String,Object> map);

    void updateNoticeMemberStatus(Map<String,Object> map);
    
    void updateNoticeBusinessStatus(Map<String,Object> map);
    
    int getAllNoticeCount(Map<String,Object> map);
    
    void insertNoticesMember(Map<String,Object> map);
}
