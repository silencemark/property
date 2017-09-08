package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.thread.MemoryStatic;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.util.StringUtil;
import com.hoheng.vo.PushMessage;
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.EvaluationService;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.OrderService;
import com.lr.backer.service.WorkersService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.PageScroll;
import com.lr.backer.vo.UploadUtil;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(OrderController.class);
	
	@Resource OrderService orderService;
	@Resource EvaluationService evaluationService;
	
	@Resource EmployerService employerService;
	 
	@Resource WorkersService workersService;
	
	@Resource IndexService indexService;
	
	  
	/**
	 * 雇主订单列表
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEmployerOrderList")
	public String getEmployerOrderList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid", getUserId(request));
		userInfo.put("lasttype", 1);
		this.indexService.updateUserExtend(userInfo);
		
		map.put("publisherid", this.getUserId(request)); 
		PageScroll page = new PageScroll();
		int num = orderService.getReleaseOrderCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> orderList = orderService.getReleaseOrderList(map); 
		for(Map<String, Object> datamap:orderList){
			datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
		}
		model.addAttribute("orderList",orderList);
		model.addAttribute("page",page);
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/order/order_employer"; 
	}
	
	/**
	 * ajax 雇主订单分页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmployerOrderListAjax")
	@ResponseBody
	public Map<String,Object> getEmployerOrderListAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		map.put("publisherid", this.getUserId(request)); 
		PageScroll page = new PageScroll();
		int num = orderService.getReleaseOrderCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> orderList = orderService.getReleaseOrderList(map); 
		for(Map<String, Object> datamap:orderList){
			datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
		}
		 
		dataMap.put("page", page);
		dataMap.put("dataList", orderList);
		return dataMap;
	}
	
	
	/**
	 * 雇主订单项目详情
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEmployerOrderProjectDetail")
	public String getEmployerOrderProjectDetail(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
	 
		Map<String,Object> projectMap = this.orderService.getProjectDetailById(map);
		List<Map<String, Object>> photolist=this.employerService.getProjectPicture(projectMap);
		if(photolist!= null && photolist.size()>0){
			for(Map<String, Object> photo:photolist){
				photo.put("url", UploadUtil.downImg(photo.get("url")+""));
			}
		}
		projectMap.put("photolist", photolist);
		model.addAttribute("projectMap", projectMap);
		/*List<Map<String,Object>> imgList = this.orderService.getProjectImgs(map);
		model.addAttribute("imgList", imgList);*/
		PageScroll page = new PageScroll();
		int num = orderService.getApplyOrderByIdNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> peopleList = orderService.getApplyOrderById(map);
		String headimage = "";
		for(Map<String, Object> datamap:peopleList){
			headimage = datamap.get("headimage")+"";
			if(headimage.equals("")){
				datamap.put("headimage", "/appcssjs/images/page/pic_bg.png");
			}else{
				datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
			}
			
		}
		model.addAttribute("peopleList", peopleList);
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/order/employer_order_projectdetail"; 
	}
	
	/**
	 * 雇主订单零时工详情
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEmployerOrderJobctDetail")
	public String getEmployerOrderJobctDetail(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> jobMap = this.orderService.getJobDetailById(map);
		model.addAttribute("jobMap", jobMap);
		PageScroll page = new PageScroll();
		int num = orderService.getApplyOrderByIdNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> peopleList = orderService.getApplyOrderById(map);
		String headimage = "";
		for(Map<String, Object> datamap:peopleList){
			headimage = (String) datamap.get("headimage");
			if(StringUtils.isEmpty(headimage)){
				datamap.put("headimage", "/appcssjs/images/page/pic_bg.png");
			}else{
				datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
			}
			
		}
		model.addAttribute("peopleList", peopleList);
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/order/employer_order_jobdetail"; 
	}
	
	/**
	 * 雇主订单申请人ajax 分页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmployerOrderJobctDetailAjax")
	@ResponseBody
	public Map<String,Object> getEmployerOrderJobctDetailAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = orderService.getApplyOrderByIdNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> peopleList = orderService.getApplyOrderById(map);
		String headimage = "";
		for(Map<String, Object> datamap:peopleList){
			headimage = (String) datamap.get("headimage");
			if(StringUtils.isEmpty(headimage)){
				datamap.put("headimage", "/appcssjs/images/page/pic_bg.png");
			}else{
				datamap.put("headimage",UploadUtil.downImg(datamap.get("headimage")+""));
			}
			
		}
		dataMap.put("dataList", peopleList);
		dataMap.put("page", page);
		return dataMap;
	}
	
	 
	/**
	 * 工人接单列表
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEmployeeOrderList")
	public String getEmployeeOrderList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<String> statusList = new ArrayList<String>();
		if(map.get("type")!=null && map.get("type").equals("1")){
			 statusList.add("1");
			 statusList.add("3");
			 statusList.add("5");
			 statusList.add("8");
	    }else{
	     statusList.add("2");
		 statusList.add("3");
		 statusList.add("4");
		 statusList.add("6");
		 statusList.add("7");
	    for(int i =9 ;i<=11;i++){
				statusList.add(i+"");	
		 }
		
	  }
		map.put("statusList", statusList);
		map.put("applicantid", this.getUserId(request));
		PageScroll page = new PageScroll();
		int num = orderService.getRushOrderCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> dataList = this.orderService.getRushOrderList(map);
		for(Map<String, Object> datamap:dataList){
			datamap.put("headimage_show",UploadUtil.downImg(String.valueOf(datamap.get("headimage")+"")));
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", page);
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/order/order_employee"; 
	}
	/**
	 * 接单列表ajax分页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmployeeOrderListAjax")
	@ResponseBody
	public  Map<String,Object> getEmployeeOrderListAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		if(map.get("type")!=null && map.get("type").equals("1")){
			 statusList.add("1");
			 statusList.add("5");
			 statusList.add("8");
	    }else{
	     statusList.add("2");
		 statusList.add("3");
		 statusList.add("4");
		 statusList.add("6");
		 statusList.add("7");
	    for(int i =9 ;i<=11;i++){
				statusList.add(i+"");	
		 }
		
	}
		map.put("statusList", statusList);
		map.put("applicantid", this.getUserId(request));
		PageScroll page = new PageScroll();
		int num = orderService.getRushOrderCount(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> dataList = this.orderService.getRushOrderList(map);
		for(Map<String, Object> datamap:dataList){
			datamap.put("headimage_show",UploadUtil.downImg(String.valueOf(datamap.get("headimage")+"")));
		}
		dataMap.put("dataList", dataList);
		dataMap.put("page", page);
		return  dataMap; 
	}
	
	
	
	
	
	
	/**
	 * 修改订单状态(取消订单)
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateApplyOrderStatus")
	@ResponseBody
	public boolean updateApplyOrderStatus(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		 boolean flg =false;
		 if(map.get("iscancel")!=null && !map.get("iscancel").equals("")){
			 Timestamp tm = new Timestamp(System.currentTimeMillis());
			 Map<String, Object> data= new HashMap<String, Object>();
			 data.put("creditid", StringUtil.getUUID());
			 data.put("userid", this.getUserId(request));
			 data.put("description","当前用户取消订单，记录信用");
			 data.put("createtime", tm);
			 this.orderService.insertCredit(data);
		 }
		
		Map<String, Object> applyorderinfo=new HashMap<String, Object>();
		applyorderinfo.put("applyorderid", map.get("applyorderid"));
		applyorderinfo=workersService.getApplyOrder(applyorderinfo);
		
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid",applyorderinfo.get("orderpublisherid"));
		membermap=this.indexService.getMemberInfo(membermap);
		
		Map<String, Object> membermap1=new HashMap<String, Object>();
		membermap1.put("memberid", applyorderinfo.get("applicantid"));
		membermap1=this.indexService.getMemberInfo(membermap1);
		String title="雇主同意取消通知";
		String errortitle="雇主不同意取消通知";
		String titleemplyor="雇主取消您的申请通知";
		String titleworker="工人取消订单申请通知";
		String titleworkersuccess="工人同意取消订单通知";
		String titleworkererror="工人不同意取消订单通知";
		String content="";
		String errorcontent="";
		String contentemplyor="";
		String contentworker="";
		String calbackurlworker="";
		String contentworkersuccess="";
		String contentworkererror="";
		if(applyorderinfo.containsKey("jobid") && !applyorderinfo.get("jobid").equals("")){
			Map<String, Object> jobinfo=this.workersService.getJobInfo(map);
			
			content="恭喜您，您申请取消的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"已同意取消!";
			
			errorcontent="很遗憾，您申请取消的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"不同意取消!";
			
			contentemplyor="很遗憾，您申请的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"取消了您的申请!";
			
			contentworker="关于您发布的招工："+jobinfo.get("jobtitle")+"，工人"+membermap1.get("realname")+"取消了订单申请!";
			calbackurlworker=Constants.PROJECT_PATH+"/employer/workermapinfo?jobid="+jobinfo.get("jobid")+"&orderid="+map.get("orderid");
			
			contentworkersuccess="关于您发布的招工："+jobinfo.get("jobtitle")+"，工人"+membermap1.get("realname")+"已同意取消订单";
			
			contentworkererror="关于您发布的招工："+jobinfo.get("jobtitle")+"，工人"+membermap1.get("realname")+"不同意取消";
		}
		if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
			Map<String, Object> projectinfo=this.workersService.getProjectInfo(map);
			content="恭喜您，您申请取消的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"已同意取消!";
			errorcontent="很遗憾，您申请取消的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"不同意取消!";
			contentemplyor="很遗憾，您申请的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"取消了您的申请!";
			contentworker="关于您发布的项目："+projectinfo.get("projecttitle")+"，工人"+membermap1.get("realname")+"取消了订单申请!";
			calbackurlworker=Constants.PROJECT_PATH+"/employer/releasemapinfo?projectid="+projectinfo.get("projectid")+"&orderid="+map.get("orderid");
			contentworkersuccess="关于您发布的项目："+projectinfo.get("projecttitle")+"，工人"+membermap1.get("realname")+"已同意取消订单";
			contentworkererror="关于您发布的项目："+projectinfo.get("projecttitle")+"，工人"+membermap1.get("realname")+"不同意取消";
		}
		
		List<PushMessage> pushlist=new ArrayList<PushMessage>();
		
		if (String.valueOf(map.get("status")).equals("6")){
			//微信信息 搁置
			String remark="温馨提示：请及时前往处理";
			String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=1";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap1.get("channelid")+"");
			message.setContent(content);
			message.setFromname(fromname);
			message.setOpenId(membermap1.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(title);
			message.setUrl(calbackurl);
			
			//weixinManage manage=new weixinManage();
			//manage.sendMassage(membermap1.get("openid")+"", calbackurl, title, content, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",title);
			messagemap.put("content",content);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap1.get("memberid"));
			messagemap.put("type", 6);
			messagemap.put("url", calbackurl);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			
			//this.employerService.insertprojectMessage(messagemap);
		}else if(String.valueOf(map.get("status")).equals("7")) {
			//微信信息 搁置
			String remark="温馨提示：请及时前往处理";
			String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=1";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap1.get("channelid")+"");
			message.setContent(errorcontent);
			message.setFromname(fromname);
			message.setOpenId(membermap1.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(errortitle);
			message.setUrl(calbackurl);
			
//			weixinManage manage=new weixinManage();
//			manage.sendMassage(membermap1.get("openid")+"", calbackurl, errortitle, errorcontent, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",errortitle);
			messagemap.put("content",errorcontent);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap1.get("memberid"));
			messagemap.put("type", 7);
			messagemap.put("url", calbackurl);
			message.setEntryMap(messagemap);
			pushlist.add(message);
			//this.employerService.insertprojectMessage(messagemap);
		}else if(String.valueOf(map.get("status")).equals("8")) {
			
			//微信信息  雇主主动取消
			String remark="温馨提示：请及时前往处理";
			String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=1";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap1.get("channelid")+"");
			message.setContent(contentemplyor);
			message.setFromname(fromname);
			message.setOpenId(membermap1.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(titleemplyor);
			message.setUrl(calbackurl);
			
//			weixinManage manage=new weixinManage();
//			manage.sendMassage(membermap1.get("openid")+"", calbackurl, titleemplyor, contentemplyor, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",titleemplyor);
			messagemap.put("content",contentemplyor);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap1.get("memberid"));
			messagemap.put("type", 8);
			messagemap.put("url", calbackurl);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			
			//this.employerService.insertprojectMessage(messagemap);
		}else if(String.valueOf(map.get("status")).equals("5")){
			String remark="温馨提示：请及时前往处理";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap.get("channelid")+"");
			message.setContent(contentworker);
			message.setFromname(fromname);
			message.setOpenId(membermap.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(titleworker);
			message.setUrl(calbackurlworker);
			
//			weixinManage manage=new weixinManage();
//			manage.sendMassage(membermap.get("openid")+"", calbackurlworker, titleworker, contentworker, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",titleworker);
			messagemap.put("content",contentworker);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap.get("memberid"));
			messagemap.put("type", 5);
			messagemap.put("url", calbackurlworker);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			//this.employerService.insertprojectMessage(messagemap);
		}else if(String.valueOf(map.get("status")).equals("9")) {
			
			//微信信息
			String remark="温馨提示：请及时前往处理";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap.get("channelid")+"");
			message.setContent(contentworkersuccess);
			message.setFromname(fromname);
			message.setOpenId(membermap.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(titleworkersuccess);
			message.setUrl(calbackurlworker);
			
//			weixinManage manage=new weixinManage();
//			manage.sendMassage(membermap.get("openid")+"", calbackurlworker, titleworkersuccess, contentworkersuccess, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",titleworkersuccess);
			messagemap.put("content",contentworkersuccess);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap.get("memberid"));
			messagemap.put("type", 9);
			messagemap.put("url", calbackurlworker);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			
			//this.employerService.insertprojectMessage(messagemap);
		}else if(String.valueOf(map.get("status")).equals("10")) {
			
			//微信信息
			String remark="温馨提示：请及时前往处理";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap.get("channelid")+"");
			message.setContent(contentworkererror);
			message.setFromname(fromname);
			message.setOpenId(membermap.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(titleworkererror);
			message.setUrl(calbackurlworker);
			
//			weixinManage manage=new weixinManage();
//			manage.sendMassage(membermap.get("openid")+"", calbackurlworker, titleworkererror, contentworkererror, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",titleworkererror);
			messagemap.put("content",contentworkererror);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap.get("memberid"));
			messagemap.put("type", 10);
			messagemap.put("url", calbackurlworker);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			
			//this.employerService.insertprojectMessage(messagemap);
		}
		try {
			MemoryStatic.pushMsgQueue.put(pushlist);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.orderService.updateApplyOrderStatus(map);
	    flg =true;
		return  flg;
	}
	
}
