package com.lr.backer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.hoheng.thread.MemoryStatic;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.vo.PushMessage;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.SystemService;
import com.lr.backer.service.WorkersService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.PageScroll;
import com.lr.backer.vo.UploadUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/workers")
public class WorkersController extends BaseController{
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(WorkersController.class);
	
	@Resource WorkersService workersService;
	
	@Resource IndexService indexService;
	
	@Resource EmployerService employerService;
	
	@Resource MemberService memberService;
	
	@Resource SystemService systemService;
	
	
	/**
	 * 招工列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/hireworkerlist")
	public String hireworkerlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("applycreateid", getUserId(request));
		//时间字典
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("typeid", Constants.TIMETYPE);
		List<Map<String, Object>> timelist = indexService.getDictData(dicMap);
		model.addAttribute("timelist", timelist);
		
		//薪资字典
		Map<String,Object> salaryMap = new HashMap<String,Object>();
		salaryMap.put("typeid", Constants.SALARYTYPE);
		List<Map<String, Object>> salarylist = indexService.getDictData(salaryMap);
		model.addAttribute("salarylist", salarylist);
		
		//所属行业字典
		Map<String,Object> industryMap = new HashMap<String,Object>();
		industryMap.put("typeid", Constants.INDUSTRY);
		industryMap.put("ifparentidone","1");
		List<Map<String, Object>> industrylist = indexService.getDictData(industryMap);
		model.addAttribute("industrylist", industrylist);
		
		//地区
		Map<String,Object> areaMap = new HashMap<String,Object>();
		areaMap.put("parentid", 4);
		List<Map<String, Object>> areaList = indexService.getAreaInfo(areaMap);
		model.addAttribute("areaList", areaList);
		
		
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		
		if(cityInfo!=null && cityInfo.size()>0 && !map.containsKey("areaid") && !map.containsKey("provinceid")){
			if(cityInfo.containsKey("provinceid")){
				map.put("provinceid", cityInfo.get("provinceid"));
				map.put("provincename", cityInfo.get("provincename"));
			}else{
				map.put("areaid", cityInfo.get("areaid"));
				map.put("cityname", cityInfo.get("cname"));
			}
		}
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", this.getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		if(!map.containsKey("parentjobtype")){
			map.put("parentjobtype", membermap.get("parentjobtype"));
			map.put("parentjobtypename", membermap.get("parentjobtypename"));
		}
		model.addAttribute("map", map);
		return "/phone/workers/hireworker_list";
	}
	
	
	/**
	 * 招工列表ajax分页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/hireworkerlistAjax")
	@ResponseBody
	public Map<String,Object> hireworkerlistAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		
		model.addAttribute("cityInfo", cityInfo);
		double lat=Double.parseDouble((membermap.get("latitude")==null?0:membermap.get("latitude"))+"");
		double lon=Double.parseDouble((membermap.get("longitude")==null?0:membermap.get("longitude"))+"");;
		if(cityInfo!= null && cityInfo.size()>0){
			lat=Double.valueOf(cityInfo.get("latitude")+"");
			lon=Double.valueOf(cityInfo.get("longitude")+"");
		}
		
		map.put("longitude", lon);
		map.put("latitude", lat);
		
		Map<String, Object> paramInfo=new HashMap<String, Object>();
		paramInfo.put("pkey", Constants.DISTANCE_KEY);
		paramInfo=this.systemService.getParams(paramInfo).get(0);
		int distance=Integer.parseInt(paramInfo.get("pvalue")+"");
		map.put("maxjuli", distance*1000);
		
		//查询封杀的信息
		Map<String, Object> killmap=new HashMap<String, Object>();
		List killers=new ArrayList();
		killmap.put("killerid", getUserId(request));
		killmap.put("isban", 1);
		List<Map<String, Object>> killlist1=new ArrayList<Map<String,Object>>();
		killlist1=this.employerService.getKillList(killmap);
		for(Map<String, Object> killmap1:killlist1){
			killers.add(killmap1.get("bekillerid"));
		}
		killmap= new HashMap<String, Object>();
		killmap.put("bekillerid", getUserId(request));
		killmap.put("isban", 1);
		List<Map<String, Object>> killlist2=new ArrayList<Map<String,Object>>();
		killlist2=this.employerService.getKillList(killmap);
		for(Map<String, Object> killmap2:killlist2){
			killers.add(killmap2.get("killerid"));
		}
		//招工列表
		map.put("killers", killers);
		List<Map<String, Object>> hireworkerlist=this.workersService.getHireWorkerList(map);
		PageScroll page = new PageScroll();
		int num = this.workersService.getHireWorkerListNum(map);
		
		if(hireworkerlist!= null && hireworkerlist.size()>0){
			for(int i=0;i<hireworkerlist.size();i++){
				if(hireworkerlist.get(i).get("juli") != null){
					if(Double.parseDouble(hireworkerlist.get(i).get("juli")+"") > 1000){
						String amount = String.format("%.1f", new Double(hireworkerlist.get(i).get("juli")+"").intValue()/(double)1000);
						hireworkerlist.get(i).put("spacing", amount + "公里");
					}else{
						hireworkerlist.get(i).put("spacing", new Double(hireworkerlist.get(i).get("juli")+"").intValue()+"米");
					}
				}else{
					hireworkerlist.get(i).put("spacing"," ");
				}
				String headimage = "";
				headimage = (String) hireworkerlist.get(i).get("headimage");
				if(StringUtils.isEmpty(headimage)){
					hireworkerlist.get(i).put("headimage","/appcssjs/images/page/pic_bg.png");
				}else{
					hireworkerlist.get(i).put("headimage", UploadUtil.downImg(hireworkerlist.get(i).get("headimage")+""));
				}
			}
		}
		page.setTotalRecords(num);
		page.initPage(map);
		
		dataMap.put("dataList",hireworkerlist);
		dataMap.put("page", page);
		return dataMap;
	}
	
	
	
	
	
	/**
	 * 初始化抢单页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initgrabsingle")
	public String initgrabsingle(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid", getUserId(request));
		userInfo.put("lasttype", 2);
		this.indexService.updateUserExtend(userInfo);
		
		Map<String, Object> jobmap=this.workersService.getJobInfo(map);
		Map<String, Object> applymap=new HashMap<String, Object>();
		applymap.put("orderid", jobmap.get("orderid"));
		applymap.put("applicantid", getUserId(request));
		applymap=this.workersService.getApplyOrder(applymap);
		if(applymap != null && applymap.size()>0){
			return "redirect:/employer/applyJobDetail?applyorderid="+applymap.get("applyorderid");
		}
		model.addAttribute("jobmap", jobmap);
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		//当前用户
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("memberid", getUserId(request));
		List<Map<String, Object>> list = memberService.getMemberList(userMap);
		if (list != null && list.size() > 0) {
			list.get(0).put("headimage_show",
		    UploadUtil.downImg(list.get(0).get("headimage") + ""));
			model.addAttribute("user", list.get(0));
		}
		
		return "/phone/workers/grabsingle";
	}
	
	/**
	 * 抢单
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/grabsingle")
	@ResponseBody
	public String grabsingle(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("createrid", getUserId(request));
		map.put("applicantid", getUserId(request));
		int status=this.workersService.insertApplyOrder(map);
		if(status==0){
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", getUserId(request));
			membermap=this.indexService.getMemberInfo(membermap);
			
			Map<String, Object> membermap1=new HashMap<String, Object>();
			membermap1.put("memberid", map.get("publisherid"));
			membermap1=this.indexService.getMemberInfo(membermap1);
			String title="";
			String content="";
			String calbackurl="";
			String fromname="";
			String remark="";
			if(map.containsKey("jobid") && !map.get("jobid").equals("")){
				//微信信息 --招工
				title="您发布的招工有人抢单!";
				content=membermap.get("realname")+"对您发布的招工信息进行了抢单，请尽快去我的订单查询详情!";
				remark="温馨提示：请及时前往处理";
				calbackurl=Constants.PROJECT_PATH+"employer/workermapinfo?jobid="+map.get("jobid")+"&orderid="+map.get("orderid")+"&flag=1";
				fromname="嘀嗒叫人";
				/*weixinManage manage=new weixinManage();
				manage.sendMassage(membermap1.get("openid")+"", calbackurl, title, content, remark, fromname);*/
				//app信息搁置
			}else{
				//微信信息 --招工
				title="您发布的项目有人报价!";
				content=membermap.get("realname")+"对您发布的项目信息进行了报价，请尽快去我的订单查询详情!";
				remark="温馨提示：请及时前往处理";
				calbackurl=Constants.PROJECT_PATH+"/employer/releasemapinfo?projectid="+map.get("projectid")+"&orderid="+map.get("orderid")+"&flag=1";
				fromname="嘀嗒叫人";
				/*weixinManage manage=new weixinManage();
				manage.sendMassage(membermap1.get("openid")+"", calbackurl, title, content, remark, fromname);*/
				//app信息搁置
			}
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",title);
			messagemap.put("content",content);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", membermap1.get("memberid"));
			messagemap.put("type",2);
			messagemap.put("url",calbackurl);
			
		    List<PushMessage> pushlist=new ArrayList<PushMessage>();
			PushMessage message=new PushMessage();
			message.setBaiduChainId(membermap1.get("channelid")+"");
			message.setContent(content);
			message.setFromname(fromname);
			message.setOpenId(membermap1.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(title);
			message.setUrl(calbackurl);
		    pushlist.add(message);
		    try {
				MemoryStatic.pushMsgQueue.put(pushlist);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.employerService.insertprojectMessage(messagemap);
			
			
		}
		return status+"";
	}
	
	/**
	 * 项目列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/projectlist")
	public String projectlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		map.put("applycreateid", getUserId(request));
		//时间字典
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("typeid", Constants.TIMETYPE);
		List<Map<String, Object>> timelist = indexService.getDictData(dicMap);
		model.addAttribute("timelist", timelist);
		
		//薪资字典
		Map<String,Object> salaryMap = new HashMap<String,Object>();
		salaryMap.put("typeid", Constants.SALARYTYPE);
		List<Map<String, Object>> salarylist = indexService.getDictData(salaryMap);
		model.addAttribute("salarylist", salarylist);
		
		//所属行业字典
		Map<String,Object> industryMap = new HashMap<String,Object>();
		industryMap.put("typeid", Constants.INDUSTRY);
		industryMap.put("ifparentidone","1");
		List<Map<String, Object>> industrylist = indexService.getDictData(industryMap);
		model.addAttribute("industrylist", industrylist);
		
		//地区
		Map<String,Object> areaMap = new HashMap<String,Object>();
		areaMap.put("parentid", 4);
		List<Map<String, Object>> areaList = indexService.getAreaInfo(areaMap);
		model.addAttribute("areaList", areaList);
		
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		if(cityInfo!=null && cityInfo.size()>0 && !map.containsKey("areaid") && !map.containsKey("provinceid")){
			if(cityInfo.containsKey("provinceid")){
				map.put("provinceid", cityInfo.get("provinceid"));
				map.put("provincename", cityInfo.get("provincename"));
			}else{
				map.put("areaid", cityInfo.get("areaid"));
				map.put("cityname", cityInfo.get("cname"));
			}
		}
		model.addAttribute("map", map);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", this.getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		if(!map.containsKey("parentjobtype")){
			map.put("parentjobtype", membermap.get("parentjobtype"));
			map.put("parentjobtypename", membermap.get("parentjobtypename"));
		}
		return "/phone/workers/project_list";
	}
	
	/**
	 * 项目列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/projectlistAjax")
	@ResponseBody
	public Map<String,Object> projectlistAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		
		model.addAttribute("cityInfo", cityInfo);
		double lat=Double.parseDouble((membermap.get("latitude")==null?0:membermap.get("latitude"))+"");
		double lon=Double.parseDouble((membermap.get("longitude")==null?0:membermap.get("longitude"))+"");;
		
		if(cityInfo!= null && cityInfo.size()>0){
			lat=Double.valueOf(cityInfo.get("latitude")+"");
			lon=Double.valueOf(cityInfo.get("longitude")+"");
		}
		//项目列表
		PageScroll page = new PageScroll();
		map.put("longitude", lon);
		map.put("latitude", lat);
		
		Map<String, Object> paramInfo=new HashMap<String, Object>();
		paramInfo.put("pkey", Constants.DISTANCE_KEY);
		paramInfo=this.systemService.getParams(paramInfo).get(0);
		int distance=Integer.parseInt(paramInfo.get("pvalue")+"");
		map.put("maxjuli", distance*1000);
		
		//查询封杀的信息
		Map<String, Object> killmap=new HashMap<String, Object>();
		List killers=new ArrayList();
		killmap.put("killerid", getUserId(request));
		killmap.put("isban", 1);
		List<Map<String, Object>> killlist1=new ArrayList<Map<String,Object>>();
		killlist1=this.employerService.getKillList(killmap);
		for(Map<String, Object> killmap1:killlist1){
			killers.add(killmap1.get("bekillerid"));
		}
		killmap= new HashMap<String, Object>();
		killmap.put("bekillerid", getUserId(request));
		killmap.put("isban", 1);
		List<Map<String, Object>> killlist2=new ArrayList<Map<String,Object>>();
		killlist2=this.employerService.getKillList(killmap);
		for(Map<String, Object> killmap2:killlist2){
			killers.add(killmap2.get("killerid"));
		}
		map.put("killers", killers);
		List<Map<String, Object>> projectlist=this.workersService.getProjectChartList(map);
		int num = this.workersService.getProjectChartListNum(map);
		
		if(projectlist!= null && projectlist.size()>0){
			for(int i=0;i<projectlist.size();i++){
				if(projectlist.get(i).get("juli") != null){
					if(Double.parseDouble(projectlist.get(i).get("juli")+"") > 1000){
						String amount = String.format("%.1f", new Double(projectlist.get(i).get("juli")+"").intValue()/(double)1000);
						projectlist.get(i).put("spacing", amount + "公里");
					}else{
						projectlist.get(i).put("spacing", new Double(projectlist.get(i).get("juli")+"").intValue()+"米");
					}
				}else{
					projectlist.get(i).put("spacing"," ");
				}
				String headimage = "";
				headimage = (String) projectlist.get(i).get("headimage");
				if(StringUtils.isEmpty(headimage)){
					projectlist.get(i).put("headimage","/appcssjs/images/page/pic_bg.png");
				}else{
					projectlist.get(i).put("headimage", UploadUtil.downImg(projectlist.get(i).get("headimage")+""));
				}
				
			}
		}
		page.setTotalRecords(num);
		page.initPage(map);
		dataMap.put("dataList",projectlist);
		dataMap.put("page", page);
		return dataMap;
	}
	
	
	/**
	 * 初始化承接项目页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initgrabproject")
	public String initgrabproject(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid", getUserId(request));
		userInfo.put("lasttype", 2);
		this.indexService.updateUserExtend(userInfo);
		
		Map<String, Object> projectmap=this.workersService.getProjectInfo(map);
		Map<String, Object> applymap=new HashMap<String, Object>();
		applymap.put("orderid", projectmap.get("orderid"));
		applymap.put("applicantid", getUserId(request));
		applymap=this.workersService.getApplyOrder(applymap);
		if(applymap != null && applymap.size()>0){
			return "redirect:/employer/applyProjectDetail?applyorderid="+applymap.get("applyorderid");
		}

		model.addAttribute("projectmap", projectmap);
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		//当前用户
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("memberid", getUserId(request));
		List<Map<String, Object>> list = memberService.getMemberList(userMap);
		if (list != null && list.size() > 0) {
			list.get(0).put("headimage_show",
		    UploadUtil.downImg(list.get(0).get("headimage") + ""));
			model.addAttribute("user", list.get(0));
		}
		
		return "/phone/workers/receiveproject";
	}
	
	/**
	 * 承接项目
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/grabproject")
	@ResponseBody
	public String grabproject(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		map.put("createrid", getUserId(request));
		map.put("applicantid", getUserId(request));
		return this.workersService.insertApplyOrder(map)+"";
	}
	
	
	/**
	 * 判断订单是否满足条件
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/judgeorder")
	@ResponseBody
	public String judgeOrder(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
	   int num = this.workersService.judgeOrder(map);
		return num+"";
	}
}
