package com.lr.backer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.lr.backer.service.AgencyService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.UserUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/system/statistic")
public class StatisticsController extends BaseController {
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(StatisticsController.class);
	
	@Resource MemberService memberService ;
	@Resource SystemService systemService ;
	@Resource AgencyService agencyService ;
	
	
	/**
	 * 得到新的用户数据统计
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNewMemberStatistics")
	public String getNewMemberStatistics(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		 Map<String,Object> userMap = UserUtil.getUser(request);
		 String userid = userMap.get("userid")+"";
		 List<Map<String,Object>> agencyList = null;
		 int  rol = this.judgeUserRolePrivate(request,userid);
		 Map<String,Object> paramMap = null;
		 
		 List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		 Map<String,Object> pvMap = new HashMap<String, Object>();
		 
		 if(isManager(request, userid) || isInvestment(request, userid)){
			 if(!map.containsKey("agencyid")){
				 map.put("agencyid","");
			 	 map.put("agencyname","平台");
			 }
			//所有的代理商和销售
			 dataList = this.agencyService.getAgencyList(pvMap);
			 model.addAttribute("ifmanager", 1);
		 }else{
			 if(!map.containsKey("agencyid")){
				map.put("agencyid", userMap.get("userid"));
				Map<String, Object> agencymap=new HashMap<String, Object>();
				agencymap.put("userid", userMap.get("userid"));
				List<Map<String, Object>> agencylist=this.agencyService.getAgencyList(agencymap);
				if(agencylist!= null && agencylist.size()>0){
					agencymap=agencylist.get(0);
				}
				map.put("agencyname", agencymap.get("agencyname"));
			 }
			  Map<String, Object> dt = new HashMap<String, Object>();
			  dt.put("userid", userid);
			  List<Map<String,Object>> agency = this.agencyService.getAgencyList(dt);//得到当前代理商的代理id
			//我下面的代理商和销售
			  if(agency!=null && agency.size()>0){
				  pvMap.put("agencyid", agency.get(0).get("agencyid"));
				  dataList = this.agencyService.getChildrenAgency(pvMap);
			  }
		 }
		 model.addAttribute("dataList1",dataList);
		 
		 List<Map<String, Object>> memberCountList =new ArrayList<Map<String,Object>>();
			if ((!map.containsKey("ifcheck") || map.get("ifcheck").equals("1"))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String stoptime = sdf.format(new Date());
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(sdf.parse(stoptime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal.add(Calendar.DAY_OF_MONTH, -30);
				Date starttime = cal.getTime();
				map.put("starttime", sdf.format(starttime));
				map.put("stoptime", stoptime);
				memberCountList= this.memberService.getMemberCount(map);
				model.addAttribute("ifcheck","1");
			}else if(map.get("ifcheck").equals("2")){
				for(int i=1;i<5;i++){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String stoptime = sdf.format(new Date());
					Calendar cal = Calendar.getInstance();
					Calendar cal1 = Calendar.getInstance();
					try {
						cal.setTime(sdf.parse(stoptime));
						cal1.setTime(sdf.parse(stoptime));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cal.add(Calendar.DAY_OF_MONTH, -(i*7));
					cal1.add(Calendar.DAY_OF_MONTH, -((i-1)*7));
					map.put("starttime",cal.getTime());
					map.put("stoptime",cal1.getTime());
					int mountcount=this.memberService.getMemberCountByWeek(map);
					Map<String, Object> mountmap=new HashMap<String, Object>();
					mountmap.put("comparetime",i+"周");
					mountmap.put("countnum",mountcount);
					memberCountList.add(mountmap);
				}
				model.addAttribute("ifcheck","2");
			}else if(map.get("ifcheck").equals("3")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String stoptime = sdf.format(new Date());
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(sdf.parse(stoptime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal.add(Calendar.MONTH, -12);
				Date starttime = cal.getTime();
				map.put("starttime", sdf.format(starttime));
				map.put("stoptime", stoptime);
				memberCountList= memberService.getMemberCountByYear(map);
				for(Map<String, Object> member:memberCountList){
					member.put("comparetime", member.get("comparetime")+"月");
				}
				model.addAttribute("ifcheck","3");
			}
			model.addAttribute("dataList", memberCountList);
			model.addAttribute("map", map);
		 
		 return "/system/statistics/statistics_news_member";
	}
	
	
/**
 * 判断当前用户是管理员 还是代理商或者销售人员 返回true 是管理员
 * @param request
 * @return
 */
 private  int judgeUserRolePrivate(HttpServletRequest request,String userid){
	
		Map<String,Object> pvMap = new HashMap<String, Object>();
	    pvMap.put("userid",userid);
	    List<Map<String,Object>> roleList =	this.systemService.judgeUserRoles(pvMap);
	    int  rol=0;
	    if(roleList.size()>0 && roleList.size() == 1){
		   for (Map<String,Object>data:roleList) {
			  if(data.get("roleid").equals(Constants.USER_MAMAGER_ROLE)){//管理员
				  rol=1;
			  }else if(data.get("roleid").equals(Constants.USER_SELL_ROLE)){//销售商
				  rol=2;
			  }else if(data.get("roleid").equals(Constants.USER_AGENCY_ROLE)){//代理商
				  rol=3;
			  } 	
		}
      } 
	    return rol;
  }
}
 
