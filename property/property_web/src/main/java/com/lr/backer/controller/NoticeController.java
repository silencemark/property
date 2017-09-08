package com.lr.backer.controller;

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
import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.service.DictionarieService;
import com.lr.backer.service.NoticeService;
import com.lr.backer.util.PageScroll;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(NoticeController.class);
	
	@Resource NoticeService noticeService;
	@Resource DictionarieService dictionarieService;
	@Resource MemberService memberService ;
	
	 
	/**
	 * 初始化手机消息页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/inintMessage")
	public String inintMessage(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("userid",this.getUserId(request));
		map.put("pageSize", "20");
		PageScroll page = new PageScroll();
	     
		int num = this.noticeService.getAllNoticeCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String,Object>>dataList = this.noticeService.getAllNotice(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", page);
		model.addAttribute("userid", this.getUserId(request));
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/notice/notice";
	}
	
	/**
	 * 初始化手机消息页面分页消息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/inintMessageAjax")
	@ResponseBody
	public Map<String,Object> inintMessageAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("pageSize", "20");
		Map<String,Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.noticeService.getAllNoticeCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String,Object>>dataList = this.noticeService.getAllNotice(map);
		dataMap.put("dataList", dataList);
		dataMap.put("page", page);
		return dataMap;
	}
	
	
	
	
	/**
	 * 删除消息信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMessage")
	@ResponseBody
	public boolean delMessage(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		boolean flg =false;
		if(map.get("type") != null &&  map.get("type").equals("1")){
          this.noticeService.updateNoticeBusinessStatus(map);			
		  flg =true;
		} else{
			this.noticeService.updateNoticeMemberStatus(map);
			flg=true;
		}
		return flg;
	} 
	
	
	
}
