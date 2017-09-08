package com.lr.backer.service.impl;

 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.NoticeMapper;
import com.lr.backer.service.NoticeService;
import com.lr.backer.util.StringUtil;
import com.lr.weixin.backer.dao.MemberMapper;
 

public class NoticeServiceImpl implements NoticeService {

	@Autowired NoticeMapper noticeMapper;
	@Autowired MemberMapper memMapper;

	public List<Map<String, Object>> getNoticesList(Map<String, Object> map) {
		 
		return this.noticeMapper.getNoticesList(map);
	}

	public int getNoticesListNum(Map<String, Object> map) {
		 
		return this.noticeMapper.getNoticesListNum(map);
	}

	public void insertNotice(Map<String, Object> map) {
		this.noticeMapper.insertNotice(map);
	}

	public void updateNotice(Map<String, Object> map) {
		 this.noticeMapper.updateNotice(map);
	}

	@Override
	public List<Map<String, Object>> getSystemNoticeMembers(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getSystemNoticeMembers(map);
	}

	@Override
	public int getSystemNoticeMembersNum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getSystemNoticeMembersNum(map);
	}

	@Override
	public void sendSystemNotice(Map<String, Object> map) {
		List<Map<String,Object>> memberList = this.memMapper.getMemberList(map);
		List<Map<String,Object>> noticeList = this.noticeMapper.getNoticesList(map);
		Map<String, Object> data = null;
		if(noticeList.size()>0){
			data = noticeList.get(0);
		}
		Map<String, Object> messageMap = null;
		Map<String, Object> pushMap = null;
		Map<String, Object> pv = null;
		List<Map<String,Object>> pushList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		if(data !=null){
		int count = 1;
		for (int k = 0; k < memberList.size(); k++) {
			// 插入消息表
			messageMap = new HashMap<String, Object>();
			pushMap = new HashMap<String, Object>();
			messageMap.put("mid", StringUtil.getUUID());
			messageMap.put("noticeid", map.get("noticeid"));
			messageMap.put("receiverid", memberList.get(k).get("memberid"));
			messageMap.put("isread", "0");
			messageMap.put("delflag", 0);
			messageMap.put("createtime", new Timestamp(System.currentTimeMillis()));
			if (memberList.get(0).get("clientId") != null && !memberList.get(0).get("clientId").equals("")) {
				pushMap.put("cid", memberList.get(0).get("clientId"));
				pushMap.put("device", memberList.get(0).get("client_device"));
				pushList.add(pushMap);
			}
			dataList.add(messageMap);

			if (count == 100 || k == memberList.size() - 1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("dataList", dataList);
				this.noticeMapper.insertNoticesMember(m);
				dataList = new ArrayList<Map<String, Object>>();
				count = 0;
			}
			count++;

		}
		 
		
		 try {
				if (pushList.size() > 0) {
				   //接口调用推送	
					
			 
				/*	com.lr.backer.util.PushUtil.pushList("访谈邀请通知", data.get("ysname")
							+ "邀请你参加访谈【" + data.get("fttitle") + "】,赶快点击参加吧！",
							pushList);*/
					pushList = new ArrayList<Map<String, Object>>();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		    //修改已经推送消息模板状态
		    pv= new HashMap<String, Object>();
            pv.put("issend", "1");
            pv.put("noticeid", map.get("noticeid"));
            this.noticeMapper.updateNotice(pv);
            
	     	}
		
		
	}

	@Override
	public List<Map<String, Object>> getBusinessNoticeList(Map<String, Object> map) {

		return this.noticeMapper.getBusinessNoticeList(map);
	}

	@Override
	public int getBusinessNoticeListNum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getBusinessNoticeListNum(map);
	}

	@Override
	public List<Map<String, Object>> getAllNotice(Map<String, Object> map) {
		
		return this.noticeMapper.getAllNotice(map);
	}

	@Override
	public void updateNoticeMemberStatus(Map<String, Object> map) {
		 this.noticeMapper.updateNoticeMemberStatus(map);
		
	}

	@Override
	public void updateNoticeBusinessStatus(Map<String, Object> map) {
		 
		this.noticeMapper.updateNoticeBusinessStatus(map);
	}

	@Override
	public int getAllNoticeCount(Map<String, Object> map) {
		
		return this.noticeMapper.getAllNoticeCount(map);
	}

	@Override
	public void insertNoticesMember(Map<String, Object> map) {
		this.noticeMapper.insertNoticesMember(map);
		
	}

	 
	
}
