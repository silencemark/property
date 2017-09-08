package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.service.EvaluationService;


@Controller
@RequestMapping("/evaluate")
public class EvaluationController extends BaseController{
	
	@Resource EvaluationService evaluationService;
	
	/**
	 * 修改评论状态
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveEvluate")
	@ResponseBody
	public boolean saveEvluate(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//userid、evaluationerid、score、orderid、description、createtime、updaterid、updatetime、status、isban
		boolean flg = false;
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("evaluationerid", memberid);
		
		map.put("updaterid", memberid);
		map.put("updatetime", tm);
		this.evaluationService.updateEvaluateStatus(map);
		flg =true ; 
		return flg;
	}
	
	/**
	 * 初始化新增评论页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initAddEvalue")
	public String initAddEvalue(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		model.addAttribute("map", map);
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/employer/evalue_add";
	}
	
	/**
	 * 新增评价
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addEvluate")
	@ResponseBody
	public boolean addEvluate(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		boolean flg = false;
		 this.evaluationService.insertEvaluate(map);
		flg =true ; 
		return flg;
	}
	
	
	
}
