package com.lr.backer.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoheng.thread.MemoryStatic;
import com.hoheng.vo.PushMessage;
import com.lr.backer.service.EmployerService;
import com.lr.backer.util.Constants;
import com.lr.weixin.backer.service.MemberService;

@Service

public class CreateVisitLog implements Job {
	@Autowired
	private EmployerService employerService;
	private static CreateVisitLog createVisitLog;
	@Autowired
	private MemberService memberService;
	
	public void init() {
		createVisitLog = this;
		createVisitLog.employerService = this.employerService;
		createVisitLog.memberService = this.memberService;

	}
	
	
	public MemberService getMemberService() {
		return createVisitLog.memberService;
	}


	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}


	public EmployerService getEmployerService() {
		return createVisitLog.employerService;
	}


	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		//判断已完工的 修改
		List<Map<String, Object>> orderlist=new ArrayList<Map<String,Object>>();
		orderlist=getEmployerService().getOutTimeOrder();
		if(orderlist!=null && orderlist.size()>0){
			for(Map<String, Object> order:orderlist){
				Map<String, Object> ordermap=new HashMap<String, Object>();
				ordermap.put("orderid", order.get("orderid"));
				ordermap.put("status", 2);
				getEmployerService().updateorderInfo(ordermap);
				Map<String, Object> applymap=new HashMap<String, Object>();
				applymap.put("orderid", order.get("orderid"));
				this.getEmployerService().updateApplyOrder(applymap);
				pushCompleteMessage(order);
			}
		}
		
	}


	private void pushCompleteMessage(Map<String,Object> order) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> memberinfo = new HashMap<String,Object>();
		String memberid = (String) order.get("memberid");
		if(StringUtils.isNotBlank(memberid)){
			paramMap.put("memberid", memberid);
			 list = getMemberService().getMemberList(paramMap);
			 if(list!=null&&list.size()>0){
				 memberinfo = list.get(0);
			 }
		}
		List<PushMessage> pushlist=new ArrayList<PushMessage>();
		// 完成推送雇主信息
		PushMessage message = pushCompleteMsgForEmployer(order, memberinfo);
		pushlist.add(message);
		List<Map<String, Object>> members =  getMemberService().geMemberOfComplete(order);
		for (Map<String, Object> map : members) {
			message = pushCompleteMsgForWorker(order, map);
			pushlist.add(message);
		}
		try {
			MemoryStatic.pushMsgQueue.put(pushlist);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private PushMessage pushCompleteMsgForEmployer(Map<String, Object> order, Map<String, Object> memberinfo) {
		String realname = (String) order.get("realname");
		String jobtitle = (String) order.get("title");
		String jobid = (String) order.get("jobid");
		String projectid = (String) order.get("projectid");
		String title="您有一个完成订单";
		String content=realname+"您的订单："+jobtitle+",已完成，请对您的已完成订单进行评价，方便双方提供更好的服务!";
		String remark="温馨提示：请及时前往处理";
		String calbackurl="";
		if(StringUtils.isNotBlank(jobid)){
			calbackurl = Constants.PROJECT_PATH+"employer/workermapinfo?jobid="+jobid+"&orderid="+order.get("orderid");
		}else{
			calbackurl = Constants.PROJECT_PATH+"employer/releasemapinfo?projectid="+projectid+"&orderid="+order.get("orderid");
		}
		String fromname="嘀嗒叫人";
		return getPushMessage(order, memberinfo, title, content, remark, calbackurl, fromname);
	}
	
	private PushMessage pushCompleteMsgForWorker(Map<String, Object> order, Map<String, Object> memberinfo) {
		String realname = (String) memberinfo.get("realname");
		String jobtitle = (String) order.get("title");
		String apployorderid = (String) memberinfo.get("applyorderid");
		String applicantid = (String) memberinfo.get("applicantid");
		String publisherid = (String) memberinfo.get("publisherid");
		String title="您有一个完成订单";
		String content=realname+"您抢的订单："+jobtitle+",已完成，请对您的已完成订单进行评价，方便双方提供更好的服务!";
		String remark="温馨提示：请及时前往处理";
		String calbackurl = Constants.PROJECT_PATH+"evaluate/initAddEvalue?orderid="+apployorderid+"&evaluationerid="+applicantid+"&userid="+publisherid+"&type=employee";
		                                                   
		String fromname="嘀嗒叫人";
		return getPushMessage(order, memberinfo, title, content, remark, calbackurl, fromname);
	}

	private PushMessage getPushMessage(Map<String, Object> order, Map<String, Object> memberinfo, String title, String content,
			String remark, String calbackurl, String fromname) {
		PushMessage message=new PushMessage();
		message.setBaiduChainId(memberinfo.get("channelid")+"");
		message.setContent(content);
		message.setFromname(fromname);
		message.setOpenId(memberinfo.get("openid")+"");
		message.setRemark(remark);
		message.setTitle(title);
		message.setUrl(calbackurl);
		
		//添加推送信息
		Map<String, Object> messagemap=new HashMap<String, Object>();
		messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
		messagemap.put("orderid", order.get("orderid"));
		messagemap.put("title",title);
		messagemap.put("content",content);
		messagemap.put("createtime",new Date());
		messagemap.put("memberid", memberinfo.get("memberid"));
		messagemap.put("url", calbackurl);
		message.setEntryMap(messagemap);
		return message;
	}
	
	
}
