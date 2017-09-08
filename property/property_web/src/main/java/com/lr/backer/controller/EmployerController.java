package com.lr.backer.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hoheng.base.controller.BaseController;
import com.hoheng.thread.MemoryStatic;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.vo.PushMessage;
import com.lr.backer.util.CookieUtil;
import com.lr.backer.util.PageScroll;
import com.lr.backer.util.baidujingweicity;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.OrderService;
import com.lr.backer.service.SupplierService;
import com.lr.backer.service.SystemService;
import com.lr.backer.service.WorkersService;
import com.lr.backer.util.Constants;
import com.lr.backer.vo.UploadUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/employer")
public class EmployerController extends BaseController{
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(EmployerController.class);
	
	@Resource EmployerService employerService;
	
	@Resource IndexService indexService;
	
	@Resource WorkersService workersService;
	
	@Resource MemberService memberService;
	
	@Resource OrderService  orderService;
	
	@Resource SystemService systemService;
	
	@Resource SupplierService supplierService;
	/**
	 * 进入发布项目页 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/intoproject")
	public String intoReleaseProject(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		String key = this.getUserId(request)+"_xm_memberinfo";
		if(RedisUtil.exist(key) && !this.getUserId(request).equals("")){
			try{
				Map<String,Object> data = RedisUtil.getMap(key);
				if(data.get("projectImgList") != null ){
					data.put("projectImgList", CookieUtil.getList(data.get("projectImgList").toString()));	
				}else{
					data.remove("projectImgList");
				}
				
				Map<String, Object> membermap=new HashMap<String, Object>();
				membermap.put("memberid", this.getUserId(request));
				membermap=this.indexService.getMemberInfo(membermap);
				data.putAll(membermap);
				
				model.addAttribute("membermap", data);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", this.getUserId(request));
			membermap=this.indexService.getMemberInfo(membermap);
			model.addAttribute("membermap", membermap);
			RedisUtil.setObject(key, membermap, 336);
		}
		
		model.addAttribute("key",key);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)
				+ "");
		//行业
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("typeid", Constants.INDUSTRY);
		dicMap.put("ifparentidone","1");
		List<Map<String, Object>> industrylist = indexService.getDictData(dicMap);
		model.addAttribute("industrylist", industrylist);
		//地区
		Map<String,Object> areaMap = new HashMap<String,Object>();
		areaMap.put("parentid", 4);
		List<Map<String, Object>> areaList = indexService.getAreaInfo(areaMap);
		model.addAttribute("areaList", areaList);
		
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("isAndroidFrom", HttpHeaderUtil.isAndroidFrom(request));
		model.addAttribute("isIosFrom", HttpHeaderUtil.isIOSFrom(request));
		return "/phone/employer/public_project";
	}
	
	@RequestMapping("/getnextdict")
	@ResponseBody
	public List<Map<String, Object>> getnextdict(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		//字典下级
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("parentid",map.get("dataid"));
		List<Map<String, Object>> industrylist = indexService.getDictData(dicMap);
		return industrylist;
	}
	@RequestMapping("/getlastdict")
	@ResponseBody
	public List<Map<String, Object>> getlastdict(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		//字典上级
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("dataid",map.get("dataid"));
		List<Map<String, Object>> dictlist = indexService.getDictData(dicMap);
		Map<String, Object> parentmap=new HashMap<String, Object>();
		List<Map<String, Object>> dictlist1 =new ArrayList<Map<String,Object>>();
		dicMap=new HashMap<String, Object>();
		if(dictlist!= null && dictlist.size()>0){
			parentmap=dictlist.get(0);
			if(!parentmap.containsKey("parentid")){
				dicMap.put("ifparentidone","1");
			}else{
				dicMap.put("parentid", parentmap.get("parentid"));
			}
			dictlist1= indexService.getDictData(dicMap);
		}
		return dictlist1;
	}
	
	@RequestMapping("/getnextarea")
	@ResponseBody
	public List<Map<String, Object>> getnextarea(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		//城市下级
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("parentid",map.get("areaid"));
		List<Map<String, Object>> arealist = indexService.getAreaInfo(dicMap);
		return arealist;
	}
	@RequestMapping("/getlastarea")
	@ResponseBody
	public List<Map<String, Object>> getlastarea(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		//城市上级
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("areaid",map.get("areaid"));
		List<Map<String, Object>> arealist = indexService.getAreaInfo(dicMap);
		Map<String, Object> parentmap=new HashMap<String, Object>();
		List<Map<String, Object>> arealist1 =new ArrayList<Map<String,Object>>();
		dicMap=new HashMap<String, Object>();
		if(arealist!= null && arealist.size()>0){
			parentmap=arealist.get(0);
			dicMap.put("parentid", parentmap.get("parentid"));
			arealist1= indexService.getAreaInfo(dicMap);
		}
		return arealist1;
	}
	
	/**
	 * 初始化发布招工页
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/intojob")
	public String intoReleaseJob(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){	
		String key  = this.getUserId(request)+"_zg_memberinfo";
		if(RedisUtil.exist(key) && !this.getUserId(request).equals("")){
			try{
			Map<String,Object> data = RedisUtil.getMap(key);
			/*data.put("projectImgList", CookieUtil.getList(data.get("projectImgList").toString()));*/
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", getUserId(request));
			membermap=this.indexService.getMemberInfo(membermap);
			data.putAll(membermap);
			
            model.addAttribute("membermap", data);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", getUserId(request));
			membermap=this.indexService.getMemberInfo(membermap);
			//用户信息存redis里
			RedisUtil.setObject(key, membermap, 336);
			model.addAttribute("membermap", membermap);
		}
		
		//下拉选择用户最近的10个项目
		map.put("createrid", getUserId(request));
 		List<Map<String, Object>> projectlist = employerService.getTopTenproject(map);
   		model.addAttribute("projectlist", projectlist);
		//工种
		Map<String,Object> dicMap = new HashMap<String,Object>();
		dicMap.put("typeid", Constants.OCCUPATION);
		dicMap.put("ifparentidone","1");
		List<Map<String, Object>> occupationlist = indexService.getDictData(dicMap);
		model.addAttribute("occupationlist", occupationlist);
		//地区
		Map<String,Object> areaMap = new HashMap<String,Object>();
		areaMap.put("parentid", 4);
		List<Map<String, Object>> areaList = indexService.getAreaInfo(areaMap);
		model.addAttribute("areaList", areaList);
		//结算方式
		dicMap.put("typeid", Constants.CLEARINGFORM);
		List<Map<String, Object>> clearingformlist = indexService.getDictData(dicMap);
		model.addAttribute("clearingformlist", clearingformlist);
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		model.addAttribute("key", key);
		return "/phone/employer/public_recruit"; 
	}
	
	/**
	 * 发布招工
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/releasejob")
	public String releaseJob(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//添加到记录表
		Map<String, Object> projectjob=new HashMap<String, Object>();
		projectjob.put("memberid", getUserId(request));
		projectjob.put("projectname", map.get("projectname"));
		
		this.employerService.insertprojectjob(projectjob);
		map.put("createrid", getUserId(request));
		String jobid=employerService.insertJob(map);
		String key  = this.getUserId(request)+"_zg_memberinfo";
		RedisUtil.deleteRedis(key);
		return "redirect:/employer/workermapinfo?jobid="+jobid+"&isfrist=1";
	}
	
	/**
	 * 发布招工 地图明细
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/workermapinfo")
	public String workermapinfo(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid", getUserId(request));
		userInfo.put("lasttype", 1);
		this.indexService.updateUserExtend(userInfo);
		
		//查询发布招工的信息
		Map<String, Object> jobmap=new HashMap<String, Object>();
		jobmap=this.workersService.getJobInfo(map);
		
		if(jobmap.get("noticenum")==null){
			//查询用户  
			Map<String, Object> infomap=new HashMap<String, Object>();
			infomap.put("projectarea", jobmap.get("projectarea"));
			infomap.put("projectprovince", jobmap.get("projectprovince"));
			infomap.put("jobtype", jobmap.get("jobtype"));
			List<Map<String, Object>> userlist=this.employerService.getMemberListByinfo(infomap);
			
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

			//推送信息
			List<PushMessage> pushlist=new ArrayList<PushMessage>();
			for(Map<String, Object> user:userlist){
				int killstatus=0;
				for(int i=0;i<killers.size();i++){
					if(String.valueOf(user.get("memberid")).equals(killers.get(i))){
						killstatus=1;
						continue;
					}
				}
				if(killstatus==1){
					continue;
				}
				PushMessage message=new PushMessage();
				if(!String.valueOf(user.get("memberid")).equals(getUserId(request))){
					
					//微信信息 搁置
					String title="您有一个招工";
					String content=jobmap.get("realname")+"发布了新的招工：（"+jobmap.get("jobtitle")+"）,赶快去抢单吧!";
					String remark="温馨提示：请及时前往处理";
					String calbackurl=Constants.PROJECT_PATH+"workers/initgrabsingle?jobid="+jobmap.get("jobid")+"&flag=2";
					String fromname="嘀嗒叫人";
					
					
					message.setBaiduChainId(user.get("channelid")+"");
					message.setContent(content);
					message.setFromname(fromname);
					message.setOpenId(user.get("openid")+"");
					message.setRemark(remark);
					message.setTitle(title);
					message.setUrl(calbackurl);
					
					//添加推送信息
					Map<String, Object> messagemap=new HashMap<String, Object>();
					messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
					messagemap.put("orderid", jobmap.get("orderid"));
					messagemap.put("title",title);
					messagemap.put("content",content);
					messagemap.put("createtime",new Date());
					messagemap.put("memberid", user.get("memberid"));
					messagemap.put("url", calbackurl);
					message.setEntryMap(messagemap);
					
					pushlist.add(message);
					/*weixinManage manage=new weixinManage();
					manage.sendMassage(user.get("openid")+"", calbackurl, title, content, remark, fromname);
					//app信息搁置 
					
					//添加推送信息
					Map<String, Object> messagemap=new HashMap<String, Object>();
					messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
					messagemap.put("orderid", jobmap.get("orderid"));
					messagemap.put("title",title);
					messagemap.put("content",content);
					messagemap.put("createtime",new Date());
					messagemap.put("memberid", user.get("memberid"));
					this.employerService.insertprojectMessage(messagemap);*/
				}
			}
			try {
				MemoryStatic.pushMsgQueue.put(pushlist);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//获取当前的时间毫秒数
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		try {
			jobmap.put("starttimesecond", sdf.parse(jobmap.get("starttime")+"").getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("jobmap", jobmap);
		//查询已经接单的列表（申请记录）
		PageScroll page = new PageScroll();
		int num = orderService.getApplyOrderByIdNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		map.put("publishmemberid", getUserId(request));
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
		model.addAttribute("peopleList", peopleList);
		model.addAttribute("ordernum",peopleList.size());
		model.addAttribute("createrid", getUserId(request));
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid",this.getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		if(membermap.containsKey("headimage") && !membermap.get("headimage").equals("")){
			membermap.put("headimage", UploadUtil.downImg(membermap.get("headimage")+""));
		}
		model.addAttribute("membermap", membermap);
		
		model.addAttribute("map", map);
		
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pcategory", Constants.PUBLISHTEXT_KEY_JOB);
		paramMap.put("pkey", Constants.PUBLISHTEXT_KEY_JOB);
		List<Map<String, Object>> params = systemService.getParams(paramMap);
		if (params != null && params.size() > 0) {
			model.addAttribute("publishtext", params.get(0).get("pvalue"));
		}
		return "/phone/employer/public_jobmap";
	}
	
	
	
	/**
	 * 发布项目
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/releaserelease")
	public String releaseRelease(@RequestParam Map<String,Object> map,@RequestParam String[] projectimg,Model model,HttpServletRequest request){
		map.put("createrid", getUserId(request));
		String projectid=employerService.insertProject(map);
		if(projectimg.length>0){
			Map<String, Object> picturemap=new HashMap<String, Object>();
			for(int i=0;i<projectimg.length;i++){
				//添加到图片表
				picturemap.put("url", projectimg[i]);
				picturemap.put("createtime", new Date());
				picturemap.put("name", projectimg[i].replace("/upload/", ""));
				picturemap.put("suffix",projectimg[i].substring(projectimg[i].indexOf(".")+1,projectimg[i].length()));
				picturemap.put("createrid", getUserId(request));
				String imgid=this.employerService.insertImg(picturemap);
				
				//添加到图片项目表 
				Map<String, Object> projectimgmap=new HashMap<String, Object>();
				projectimgmap.put("createtime", new Date());
				projectimgmap.put("createrid", getUserId(request));
				projectimgmap.put("imgid", imgid);
				projectimgmap.put("projectid", projectid);
				this.employerService.insertProjectImg(projectimgmap);
			}
		}
		String key = this.getUserId(request)+"_xm_memberinfo";
		RedisUtil.deleteRedis(key);
		return "redirect:/employer/releasemapinfo?projectid="+projectid+"&isfrist=1";
	}
	/**
	 * 发布项目 地图明细
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/releasemapinfo")
	public String releasemapinfo(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid", getUserId(request));
		userInfo.put("lasttype", 1);
		this.indexService.updateUserExtend(userInfo);
		
		//查询发布项目的信息
		Map<String, Object> projectmap=new HashMap<String, Object>();
		projectmap=this.employerService.getProjectInfo(map);
		//查询通知人数
		if(projectmap.get("noticenum")==null){
			//查询用户 
			Map<String, Object> infomap=new HashMap<String, Object>();
			infomap.put("projectarea",  projectmap.get("projectarea"));
			infomap.put("projectprovince",  projectmap.get("projectprovince"));
			infomap.put("jobtype", projectmap.get("ownedindustry"));
			List<Map<String, Object>> userlist=this.employerService.getMemberListByinfo(infomap);
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
			
			//推送信息
			List<PushMessage> pushlist=new ArrayList<PushMessage>();
			for(Map<String, Object> user:userlist){
				int killstatus=0;
				for(int i=0;i<killers.size();i++){
					if(String.valueOf(user.get("memberid")).equals(killers.get(i))){
						killstatus=1;
						continue;
					}
				}
				if(killstatus==1){
					continue;
				}
				if(!String.valueOf(user.get("memberid")).equals(getUserId(request))){
					//微信信息 搁置
					String title="您有一个项目";
					String content=projectmap.get("realname")+"发布了新的项目：（"+projectmap.get("projecttitle")+"）,赶快去承接项目吧!";
					String remark="温馨提示：请及时前往处理";
					String calbackurl=Constants.PROJECT_PATH+"workers/initgrabproject?projectid="+projectmap.get("projectid")+"&flag=2";
					String fromname="嘀嗒叫人";
					
					PushMessage message=new PushMessage();
					message.setBaiduChainId(user.get("channelid")+"");
					message.setContent(content);
					message.setFromname(fromname);
					message.setOpenId(user.get("openid")+"");
					message.setRemark(remark);
					message.setTitle(title);
					message.setUrl(calbackurl);
					
					Map<String, Object> messagemap=new HashMap<String, Object>();
					messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
					messagemap.put("orderid", projectmap.get("orderid"));
					messagemap.put("title",title);
					messagemap.put("content",content);
					messagemap.put("createtime",new Date());
					messagemap.put("memberid", user.get("memberid"));
					messagemap.put("url", calbackurl);
					message.setEntryMap(messagemap);
					
					pushlist.add(message);
					
				/*	weixinManage manage=new weixinManage();
					manage.sendMassage(user.get("openid")+"", calbackurl, title, content, remark, fromname);
					//app信息搁置
					
					//添加推送信息
					Map<String, Object> messagemap=new HashMap<String, Object>();
					messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
					messagemap.put("orderid", projectmap.get("orderid"));
					messagemap.put("title",title);
					messagemap.put("content",content);
					messagemap.put("createtime",new Date());
					messagemap.put("memberid", user.get("memberid"));
					this.employerService.insertprojectMessage(messagemap);*/
				}
			}
			try {
				System.out.println("发布项目pushlist的值:"+pushlist+"-------------------------------------------------------------------------------------");
				MemoryStatic.pushMsgQueue.put(pushlist);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//获取当前的时间毫秒数
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		try {
			projectmap.put("starttimesecond", sdf.parse(projectmap.get("starttime")+"").getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("projectmap", projectmap);
		//查询已经接单的列表（申请记录）
		PageScroll page = new PageScroll();
		int num = orderService.getApplyOrderByIdNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		map.put("publishmemberid", getUserId(request));
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
		model.addAttribute("ordernum",peopleList.size());
		model.addAttribute("createrid", getUserId(request));
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", this.getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		if(membermap.containsKey("headimage") && !membermap.get("headimage").equals("")){
			membermap.put("headimage", UploadUtil.downImg(membermap.get("headimage")+""));
		}
		model.addAttribute("membermap", membermap);
		
		model.addAttribute("map", map);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pcategory", Constants.PUBLISHTEXT_KEY_PROJECT);
		paramMap.put("pkey", Constants.PUBLISHTEXT_KEY_PROJECT);
		List<Map<String, Object>> params = systemService.getParams(paramMap);
		if (params != null && params.size() > 0) {
			model.addAttribute("publishtext", params.get(0).get("pvalue"));
		}
		return "/phone/employer/public_projectmap";
	}
	/**
	 * 抢单详情（项目）
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyProjectDetail")
	public String applyProjectDetail(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> applydetailmap=new HashMap<String, Object>();
		applydetailmap=this.employerService.getApplyProjectDetail(map);
		model.addAttribute("applydetailmap", applydetailmap);
		//分享参数
		Map<String, Object> shareMap=initWeixinShareParam(request);
		model.addAttribute("shareMap", shareMap);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		return "/phone/employer/apply_project_detail";
	}
	
	/**
	 * 抢单详情（招工）
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyJobDetail")
	public String applyJobDetail(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> membermap = new HashMap<String, Object>();
		String memberid=getUserId(request);
		
		Map<String, Object> applydetailmap=new HashMap<String, Object>();
		applydetailmap=this.employerService.getApplyJobDetail(map);
		model.addAttribute("applydetailmap", applydetailmap);
		
		//分享参数
		Map<String, Object> shareMap=initWeixinShareParam(request);
		model.addAttribute("shareMap", shareMap);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		return "/phone/employer/apply_job_detail";
	}
	
	//分享ajax
	@RequestMapping("/givesharecoin")
	@ResponseBody
	public String givesharecoin(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//送嘀嗒币
		/*String memberid=this.getUserId(request);
		map.put("memberid", memberid);
		if(!memberid.equals("")){
			this.memberService.givesharecoin(map);
		}*/
		//保存分享记录
		map.put("createrid", getUserId(request));
		this.employerService.insertShareRecore(map);
		//分享次数
		if(map.containsKey("supplierid")){
			map.put("addsharetime", 1);
			this.supplierService.updatesupper(map);
		}
		return "1";
	}
	
	
	/**
	 * 通知人数ajax
	 * @param maps
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getnoticenum")
	@ResponseBody
	public String getnoticenum(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//查询通知人数
		Map<String, Object> orderMap=this.employerService.getOrderInfo(map);
		int noticenum=0;
		if(orderMap.containsKey("noticenum") && !orderMap.get("noticenum").equals("")){
			noticenum=Integer.parseInt(orderMap.get("noticenum")+"");
		}
		if(noticenum==0){
			//通知人数=实际人数+50-100的随机数、
			Map<String, Object> infomap=new HashMap<String, Object>();
			infomap.put("dataid", Constants.INFONUMID);
			infomap=this.indexService.getDictInfo(infomap);
			String info[]=String.valueOf(infomap.get("datacode")).split("-");
			int ran = (int)(Integer.parseInt(info[0])*Math.random()+(Integer.parseInt(info[1])-Integer.parseInt(info[0])));
			int num=this.employerService.getnoticenum(map);
			System.out.println("通知人数："+num+"-----"+ran);
			//存入数据库
			map.put("noticenum", num+ran);
			this.employerService.updateorderInfo(map);
			return (num+ran)+"";
		}
		else{
			return noticenum+"";
		}
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 * @author silence
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadimg",method=RequestMethod.POST)    
	@ResponseBody 
	public Map<String, Object> headimg(HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		String filename = null;
		if(request.getParameter("filename") != null && !request.getParameter("filename").equals("")){
			filename = String.valueOf(request.getParameter("filename"));
		}else{
			filename = "myfiles";  
		}
		String realPath = request.getSession().getServletContext()
				.getRealPath("/upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 获得文件：
		List<MultipartFile> myfiles = multipartRequest.getFiles(filename);
		System.out.println(myfiles.get(0).getContentType()); 
		
		Map<String, Object> map = new HashMap<String, Object>();
//		for (MultipartFile myfile : myfiles) {
//			if (myfile.isEmpty()) {
//				map.put("error", "请选择文件后上传");
//				return map;
//			} else {
//				CommonsMultipartFile cf= (CommonsMultipartFile)myfile; 
//				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
//				File file= fi.getStoreLocation();
//				 
//				String newImg = System.currentTimeMillis()/1000l + "_" + getUserId(request);
//
//			    boolean fag = UploadUtil.uploadImg(file, newImg);
//			    fi.delete();
//			    if(fag){
//			    	String imgurl = UploadUtil.downImg(newImg);
//			    	map.put("imgurl", imgurl);
//			    	map.put("imgkey", newImg);
//			    }else{
//			    	map.put("error", "上传失败");
//			    }	    
//			    
////				System.out.println("文件名称: " + myfile.getName());
////				System.out.println("文件长度: " + myfile.getSize());
////				System.out.println("文件类型: " + myfile.getContentType());
//			}
//		}
		String url=null;
		String originalFilename=null;
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				map.put("error", "请选择文件后上传");
				return map;
			} else {
				originalFilename = myfile.getOriginalFilename();
				System.out.println("文件原名: " + originalFilename);
				originalFilename = new Date().getTime()
						+ ""
						+ originalFilename.substring(originalFilename
								.lastIndexOf("."));
				System.out.println("文件名称: " + myfile.getName());
				System.out.println("文件长度: " + myfile.getSize());
				System.out.println("文件类型: " + myfile.getContentType());
				System.out.println("========================================");
				try {
					// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
					// 此处也可以使用Spring提供的MultipartFile.transferTo(File
					// dest)方法实现文件的上传
					FileUtils.copyInputStreamToFile(myfile.getInputStream(),
							new File(realPath, originalFilename));
					
				
					url="/upload/" + originalFilename;
				        
				        
				        
				} catch (IOException e) {
					System.out.println("文件[" + originalFilename
							+ "]上传失败,堆栈轨迹如下");
					e.printStackTrace();
					map.put("error", "1`文件上传失败，请重试！！");
					return map;
				}
			}
		}
		map.put("url", url);

		return map;
	}
	
	@RequestMapping("/getcity")
	@ResponseBody
	public Map<String, Object> getcity(HttpServletRequest request,@RequestParam Map<String, Object> map, Model model) {
		Map<String, Object> cityInfo=new HashMap<String, Object>();
		if(map.get("lat")!=null && !map.get("lat").equals("") && map.get("lng") !=null && !map.get("lng").equals("")){
			baidujingweicity city=new baidujingweicity();
			String cityname=city.getcity(map.get("lat")+"", map.get("lng")+"");
			String provincename=city.getProvince(map.get("lat")+"",map.get("lng")+"");
			
			cityInfo.put("cname", cityname);
			cityInfo.put("iscityid", 1);
			List<Map<String, Object>> citylist=this.indexService.getAreaInfo(cityInfo);
			
			Map<String, Object> provinceInfo=new HashMap<String, Object>();
			provinceInfo.put("provincename", provincename);
			List<Map<String, Object>> provincelist=this.indexService.getAreaInfo(provinceInfo);
			
			cityInfo=citylist.get(0);
			cityInfo.put("latitude", map.get("lat"));
			cityInfo.put("longitude", map.get("lng"));
			if(provincelist!= null && provincelist.size()>0){
				cityInfo.put("provincename", provincelist.get(0).get("cname"));
				cityInfo.put("provinceid", provincelist.get(0).get("areaid"));
			}
			RedisUtil.setObject("cityInfo"+getUserId(request),cityInfo,2);
	   }
		return cityInfo;
	}
}
