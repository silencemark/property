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

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.IndexService;
import com.lr.backer.vo.UploadUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(IndexController.class);
	
	@Resource IndexService indexService;
	@Resource MemberService memberService;
	@Resource EmployerService employerService;
	/**
	 * 雇主首页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/employerindex")
	public String employerIndex(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		
		map.put("code", "wxindex");
		//banner
		List<Map<String, Object>> banerlist = indexService.getBannerList(map);
		for(Map<String, Object> baner:banerlist){
			baner.put("imgurl",UploadUtil.downImg(baner.get("imgurl")+""));
		}
		model.addAttribute("banerlist", banerlist);
		
		if(!getUserId(request).equals("xxx")){
			
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", getUserId(request));
			membermap=this.indexService.getMemberInfo(membermap);
			if(membermap.containsKey("lasttype") && String.valueOf(membermap.get("lasttype")).equals("2") && !String.valueOf(map.get("type")).equals("1")){
				return "redirect:/index/workmanindex";
			}else{
				//当前用户
				map.put("memberid", getUserId(request));
				List<Map<String, Object>> list = memberService.getMemberList(map);
				if (list != null && list.size() > 0) {
					list.get(0).put("headimage_show",
				    UploadUtil.downImg(list.get(0).get("headimage") + ""));
					model.addAttribute("user", list.get(0));
				}
				
				//雇主订单
				map.put("status",1);
				List<Map<String, Object>> orderlist = indexService.getEmployerOrderList(map);
				for(Map<String, Object> datamap:orderlist){
					datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
				}
				model.addAttribute("orderlist", orderlist);
				
				Map<String, Object> userInfo=new HashMap<String, Object>();
				userInfo.put("userid", getUserId(request));
				userInfo.put("lasttype", 1);
				this.indexService.updateUserExtend(userInfo);
			}
			
		}
		

	
		model.addAttribute("id",this.getUserId(request));

		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		return "/phone/employer_index";
	}
	/**
	 * 工人首页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/workmanindex")
	public String workmanIndex(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		
		map.put("code", "wxindex");
		//banner
		List<Map<String, Object>> banerlist = indexService.getBannerList(map);
		for(Map<String, Object> baner:banerlist){
			baner.put("imgurl",UploadUtil.downImg(baner.get("imgurl")+""));
		}
		model.addAttribute("banerlist", banerlist);
		
		if(!getUserId(request).equals("xxx")){
			//map.put("memberid", getUserId(request));
			map.put("memberid", getUserId(request));//需修改，此处暂时默认
			//工人订单
			map.put("status",1);
			List<Map<String, Object>> orderlist = indexService.getWorkmanOrderList(map);
			for(Map<String, Object> datamap:orderlist){
				datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
			}
			model.addAttribute("orderlist", orderlist);
			
			Map<String, Object> userInfo=new HashMap<String, Object>();
			userInfo.put("userid", getUserId(request));
			userInfo.put("lasttype", 2);
			this.indexService.updateUserExtend(userInfo);
		}
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		return "/phone/workman_index"; 
	}
	
}
