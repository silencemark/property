package com.lr.backer.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lr.backer.service.ComplaintService;
import com.lr.backer.util.PageHelper;

@Controller
@RequestMapping("/system/complaint")
public class SystemComplaintController {
	
	 
	@Resource ComplaintService complaintService;
	
	/**
	 * 意见与反馈列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComplaintList")
	public String getComplaintList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("status", "0");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.complaintService.getComplaintListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.complaintService.getComplaintList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/complaint_list";
	}
	
	 
	/**
	 * 修改意见与反馈状态
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public  Map<String,Object> updateStatus(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		this.complaintService.updateComplaint(map);
		return map;
	}
	
	 
	
}
