package com.lr.backer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.lr.backer.service.IndexService;
import com.lr.backer.util.Constants;
import com.lr.backer.vo.UploadUtil;
import com.lr.weixin.backer.service.MemberService;

@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(IndexController.class);

	@Resource
	IndexService indexService;
	@Resource
	MemberService memberService;

	/**
	 * 获取基本信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseInfo")
	@ResponseBody
	public Map<String, Object> baseInfo(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// banner
		List<Map<String, Object>> banerlist = indexService.getBannerList(map);
		for (Map<String, Object> baner : banerlist) {
			baner.put("imgurl", UploadUtil.downImg(baner.get("imgurl") + ""));
		}
		returnMap.put("banerlist", banerlist);

		String userId = (String) map.get("userId");
		if (StringUtils.isNotEmpty(userId)) {
			// 当前用户
			map.put("memberid", userId);
			List<Map<String, Object>> list = memberService.getMemberList(map);
			if (list != null && list.size() > 0) {
				list.get(0).put("headimage_show",
						UploadUtil.downImg(list.get(0).get("headimage") + ""));
				returnMap.put("user", list.get(0));
			}
		}
        
		returnMap.put("publishJobUrl", Constants.PROJECT_PATH+"employer/intojob");//发布招工
		returnMap.put("publishProjectUrl", Constants.PROJECT_PATH+"employer/intoproject");//发布项目
		returnMap.put("EmployerOrderUrl", Constants.PROJECT_PATH+"order/getEmployerOrderList?resource=index");//雇主订单更多
		returnMap.put("EmployerDetailUrl", Constants.PROJECT_PATH+"my/detailinfo?resource=index");//雇主个人详情
		returnMap.put("employerWorkDetailUrl", Constants.PROJECT_PATH+"employer/workermapinfo?resource=index");//雇主订单招工详情
		returnMap.put("employerProjectDetailUrl", Constants.PROJECT_PATH+"employer/releasemapinfo?resource=index");//雇主订单项目详情
		
		
		returnMap.put("hireWorkUrl", Constants.PROJECT_PATH+"workers/hireworkerlist");//工人抢单页面
		returnMap.put("hireProjectUrl", Constants.PROJECT_PATH+"workers/projectlist");//工人承接项目
		returnMap.put("worksOrderUrl", Constants.PROJECT_PATH+"order/getEmployeeOrderList?type=1&resource=index");//工人订单更多
		returnMap.put("worksUrl", Constants.PROJECT_PATH+"employer/applyJobDetail?resource=index");//工人招工详情
		returnMap.put("ProjectUrl", Constants.PROJECT_PATH+"employer/applyProjectDetail?resource=index");//工人项目详情
		returnMap.put("workDetailtUrl", Constants.PROJECT_PATH+"my/detailinfo?resource=index");//工人头像查看雇主信息 
		

		return returnMap;
	}

	@RequestMapping("/employerorders")
	@ResponseBody
	public Map<String, Object> employerorders(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		String userId = (String) map.get("userId");
		map.put("memberid", userId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		map.put("status", 1);
		if (!userId.equals("") && userId != null) {
			List<Map<String, Object>> orderlist = indexService
					.getEmployerOrderList(map);
			for (Map<String, Object> datamap : orderlist) {
				datamap.put("headimage",
						UploadUtil.downImg(datamap.get("headimage") + ""));
			}
			returnMap.put("orderlist", orderlist);
		}
		return returnMap;
	}

	@RequestMapping("/workerorders")
	@ResponseBody
	public Map<String, Object> workerorders(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		String userId = (String) map.get("userId");
		map.put("memberid", userId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		map.put("status", 1);
		if (!userId.equals("") && userId != null) {
			List<Map<String, Object>> orderlist = indexService
					.getWorkmanOrderList(map);
			for (Map<String, Object> datamap : orderlist) {
				datamap.put("headimage",
						UploadUtil.downImg(datamap.get("headimage") + ""));
			}
			returnMap.put("orderlist", orderlist);
		}

		return returnMap;
	}
}
