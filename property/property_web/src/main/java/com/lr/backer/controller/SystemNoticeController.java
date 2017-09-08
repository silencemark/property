package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.thread.MemoryStatic;
import com.hoheng.util.StringUtil;
import com.hoheng.vo.PushMessage;
import com.lr.backer.service.DictionarieService;
import com.lr.backer.service.NoticeService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/system/notice")
public class SystemNoticeController extends BaseController {
	
 
	private transient static Log log = LogFactory.getLog(SystemNoticeController.class);
	
	@Resource NoticeService noticeService;
	@Resource DictionarieService dictionarieService;
	@Resource MemberService memberService ;
	
	
	/**
	 * 系统通知列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNoticeList")
	public String getNoticeList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("delflag", "0");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.noticeService.getNoticesListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.noticeService.getNoticesList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/notice/notice_list";
	}
	
	/**
	 * 删除系统通知
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public Map<String,Object> updateStatus(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> pv = new HashMap<String, Object>();
		pv.put("noticeid", map.get("noticeid"));
		List<Map<String,Object>>dataList = this.noticeService.getNoticesList(pv);
		boolean flg = false;
		if(dataList.get(0).get("issend") != null && dataList.get(0).get("issend").equals("0")){
			this.noticeService.updateNotice(map);
			flg =true;
			map.put("msg", "恭喜你删除成功");
		}else{
			flg =false;
			map.put("msg", "该消息已经发送，不能删除！");
		}
		map.put("flg", flg);
		return map;
	}
	
	/**
	 * 初始化系统通知编辑页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initEditNotice")
	public String initEditNotice(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		if(map.get("noticeid") !=null && !map.get("noticeid").equals("")){
		 List<Map<String,Object>> data = this.noticeService.getNoticesList(map);
		 if(data.size()>0){
			 model.addAttribute("data", data.get(0));	 
		 }
		 
		}
		if(map.get("stp") != null && map.get("stp") != ""){
			return "/system/notice/notice_info";
		}else{
			return "/system/notice/notice_edit";	
		}
		
	}
	
	/**
	 * 编辑系统通知消息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/editNotice")
	public String editNotice(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		Map<String,Object> user=  UserUtil.getUser(request);
		if(map.get("noticeid") !=null && !map.get("noticeid").equals("")){
			this.noticeService.updateNotice(map);
		}else{
			map.put("noticeid", StringUtil.getUUID());
			map.put("createrid", user.get("userid"));
			map.put("createtime", tm);
			map.put("delflag", "0");
			map.put("issend", "0");
			this.noticeService.insertNotice(map);
		}
		return "redirect:/system/notice/getNoticeList";
	}
	
	
	/**
	 * 系统通知消息用户列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendSystemNotice")
	public String sendSystemNotice(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
	    
		List<Map<String,Object>> memberList = this.memberService.getMemberList(map);
		List<Map<String,Object>> noticeList = this.noticeService.getNoticesList(map);
		Map<String, Object> data = null;
		if(noticeList.size()>0){
			data = noticeList.get(0);
		}
		Map<String, Object> messageMap = null;
		Map<String, Object> pushMap = null;
		Map<String, Object> pv = null;
		List<PushMessage> pushlist=new ArrayList<PushMessage>();
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		PushMessage message=new PushMessage();
		pushMap = new HashMap<String, Object>();
		String title=""+data.get("title")+"";
		String content="尊贵的用户，嘀嗒平台提醒您，有一条新消息，请点击查看";
		String calbackurl=Constants.PROJECT_PATH+"notice/inintMessage";
		String fromname="嘀嗒叫人";
		message.setTitle(title);
		message.setContent(content);
		message.setUrl(calbackurl);
		message.setPushAll(true);
		pushlist.add(message);
		try {
			MemoryStatic.pushMsgQueue.put(pushlist);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		if(data !=null){
		int count = 1;
		for (int k = 0; k < memberList.size(); k++) {
			// 插入消息表
			messageMap = new HashMap<String, Object>();
			messageMap.put("mid", StringUtil.getUUID());
			messageMap.put("noticeid", map.get("noticeid"));
			messageMap.put("receiverid", memberList.get(k).get("memberid"));
			messageMap.put("isread", "0");
			messageMap.put("delflag", 0);
			messageMap.put("createtime", new Timestamp(System.currentTimeMillis()));
			//系统推送
			/*String title=""+data.get("title")+"";
			String content="尊敬的"+memberList.get(k).get("nickname")+"，嘀嗒平台提醒您，有一条新消息，请点击详情查看";
			String calbackurl=Constants.PROJECT_PATH+"notice/inintMessage";
			String fromname="嘀嗒叫人";*/
			/*weixinManage manage=new weixinManage();
			manage.sendMassage(memberList.get(k).get("openid")+"", calbackurl, title, content, remark, fromname);*/
			
			/*pushMap.put("title",title);
			pushMap.put("content",content);
			pushMap.put("url",calbackurl);
			pushMap.put("isPushAll",true);
			pushMap.put("fromname", fromname);
			message.setEntryMap(pushMap);
			pushlist.add(message);*/
			dataList.add(messageMap);

			if (count == 100 || k == memberList.size() - 1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("dataList", dataList);
				this.noticeService.insertNoticesMember(m);
				dataList = new ArrayList<Map<String, Object>>();
				count = 0;
			}
			count++;

		}
		 
		    //修改已经推送消息模板状态
		    pv= new HashMap<String, Object>();
            pv.put("issend", "1");
            pv.put("noticeid", map.get("noticeid"));
            this.noticeService.updateNotice(pv);
            
	     	}
		
		//this.noticeService.sendSystemNotice(map);
		
		return "redirect:/system/notice/getNoticeList";
	}
	
	/**
	 * 系统通知消息用户列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNoticeMemberList")
	public String getNoticeMemberList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("delflag", "0");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.noticeService.getSystemNoticeMembersNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>> dataList = this.noticeService.getSystemNoticeMembers(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/notice/notice_member_list";
	}
	
	
	
	/**
	 * 系统通知列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAutoMessage")
	public String getAutoMessage(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("delflag", "0");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.noticeService.getBusinessNoticeListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.noticeService.getBusinessNoticeList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/notice/notice_auto_list";
	}
	
	
	
}
