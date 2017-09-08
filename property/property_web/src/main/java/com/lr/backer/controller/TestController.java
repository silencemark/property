package com.lr.backer.controller;

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

import com.lr.backer.service.TestService;


@Controller
@RequestMapping("/test")
public class TestController {
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(TestController.class);
	
	/*@Resource TestService testService;*/
	
	/**
	 * 测试查询
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/test")
	public String getTestList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		/*List<Map<String, Object>> list = testService.getTestList(map);
		model.addAttribute("list", list);
		return "/user/test";*/
		return null;
	}
	
}
