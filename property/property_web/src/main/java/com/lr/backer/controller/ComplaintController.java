package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.service.ComplaintService;


@Controller
@RequestMapping("/complaint")
public class ComplaintController extends BaseController{
	
	 
	@Resource ComplaintService complaintService;
	 
	/**
	 * 保存意见与反馈状态
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/savecomplaint")
	@ResponseBody
	public  Map<String,Object> saveComplaint(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("feedbackid", UUID.randomUUID().toString().replace("-", ""));
		map.put("userid",this.getUserId(request));
		map.put("createtime", new Timestamp(System.currentTimeMillis()));
		map.put("status","0");
		map.put("type", "1");
		this.complaintService.insertComplaint(map);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 跳转到feedback
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/gofeedback")
	public String gofeedback(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/my/feedback";
	}
	
}
