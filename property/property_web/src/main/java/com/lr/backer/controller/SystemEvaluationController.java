package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.lr.backer.service.EvaluationService;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;


@Controller
@RequestMapping("/system/evaluate")
public class SystemEvaluationController extends BaseController{
	
	@Resource EvaluationService evaluationService;
	
	/**
	 * 得到评价列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEvaluateList")
	public String getEvaluateList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = this.evaluationService.getEvalListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>> dataList = this.evaluationService.getEvalList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/evaluate_list";
	}
	
	/**
	 * 修改评论状态
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateEvluate")
	@ResponseBody
	public boolean updateEvluate(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		boolean flg = false;
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		Map<String,Object> user = UserUtil.getUser(request);
		map.put("updaterid", user.get("userid"));
		map.put("updatetime", tm);
		this.evaluationService.updateEvaluateStatus(map);
		flg =true ; 
		return flg;
	}
	 
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
	
	 
	
	
}
