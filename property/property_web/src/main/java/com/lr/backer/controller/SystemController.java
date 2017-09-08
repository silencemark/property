package com.lr.backer.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.hoheng.base.controller.BaseController;
import com.hoheng.util.DateUtil;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.service.AgencyService;
import com.lr.backer.service.SystemService;

import com.lr.backer.util.CookieUtil;
import com.lr.backer.util.Md5Util;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.backer.vo.UploadUtil;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(SystemController.class);
	
	@Resource
	private SystemService systemService;
	@Resource
	private AgencyService agencyService;

	/**
	 * 轮播广告
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/bannerListHome")
	public String bannerListHome(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		List<Map<String, Object>> bannerPositionList = systemService.getbannerPositionListHome(map);
		int num = systemService.getbannerPositionListHomeNum(map);
		pageHelper.setTotalCount(num);

		model.addAttribute("bannerPositionList", bannerPositionList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/banner_position_home_list";
	}

	/**
	 * 轮播广告
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/banner2ListHome")
	public String banner2ListHome(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		List<Map<String, Object>> bannerList = systemService.getbannerListHome(map);
		int num = systemService.getbannerListHomeNum(map);
		pageHelper.setTotalCount(num);
		for (Map<String, Object> ap : bannerList) {
			map.put("size", ap.get("size"));
			ap.put("imgurl_show", UploadUtil.downImg(String.valueOf(ap.get("imgurl"))));
		}

		model.addAttribute("bannerList", bannerList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/banner2_home_list";
	}

	/**
	 * 修改banner
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateBanner2Home")
	public String updateBanner2Home(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		if (map.get("id") == null || "".equals(map.get("id"))) {
			this.systemService.insertBanner(map);
		} else {
			this.systemService.updateBanner(map);
		}
		return "redirect:../system/banner2ListHome?positionid=" + map.get("positionid");
	}

	/**
	 * 删除banner
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteBanner2Home")
	public String deleteBanner2Home(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		this.systemService.deleteBanner(map);
		return "redirect:../system/banner2ListHome?positionid=" + map.get("positionid");
	}

	/**
	 * 用户登录
	 * 
	 * @param map
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String adminLogin(@RequestParam Map<String, Object> map, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		// 查询权限菜单
		if (map.size() > 0) {
			Object oldcode = map.get("userCode");
			Object newcode = CookieUtil.getValue(request, "admin_vcode");
			if (oldcode == null || oldcode.equals("")
					|| !String.valueOf(oldcode).equalsIgnoreCase(String.valueOf(newcode))) {
				model.addAttribute("errors", "验证码错误");
				return "/system/admin_login";
			}
			String password = Md5Util.getMD5(map.get("password") + "");
			map.put("password", password);
			map.put("username", map.get("username"));
			Map<String, Object> userMap = this.systemService.getUserInfo(map);
			if (userMap != null && userMap.size() > 0) {

				// 登陆成功生成唯一cookiesid
				String cookiesid = userMap.get("userid") + "_" + System.currentTimeMillis();
				// cookiesid存入cookies中,有效期30分钟
				CookieUtil.setCookie(response, UserUtil.USERINFO, cookiesid, "/", null);
				// 用户信息存入reids中,有效期30分钟
				RedisUtil.setMap(cookiesid, userMap);
				// 存储登录来源
				CookieUtil.setCookie(response, UserUtil.FROMINFO, "ADMIN", "/", null);

				CookieUtil.setCookie(response, UserUtil.IDENTITY, "GLY", "/", null);
				// 查询 该用户的角色对应的菜单
				List<Map<String, Object>> menuList = this.systemService.getMenuList(userMap);
				Map<String, Object> menuMap = new HashMap<String, Object>();
				try {
					menuMap.put("data", JSON.json(menuList));
				} catch (IOException e) {
					e.printStackTrace();
				}
				RedisUtil.setMap(cookiesid + "_menu", menuMap);
				;
				return "redirect:/system/index";
			} else {
				model.addAttribute("errors", "用户名或密码错误");
				return "/system/admin_login";
			}
		} else {
			return "/system/admin_login";
		}
	}

	@RequestMapping("/index")
	public String testUser() {
		return "/system/main";
	}

	/**
	 * 菜单管理
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/functionList")
	public String functionList(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);

		List<Map<String, Object>> functionList = systemService.getFunctionList(map);
		int num = systemService.getFunctionListNum(map);
		pageHelper.setTotalCount(num);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("parentid", "0");
		List<Map<String, Object>> parentList = systemService.getFunctionList(data);
		model.addAttribute("parentList", parentList);
		model.addAttribute("functionList", functionList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/function_list";
	}

	/**
	 * 编辑菜单信息
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/editfunction")
	public String editfunction(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		if (map.get("id").equals("") || !map.containsKey("id")) {
			this.systemService.insertFunction(map);
		} else {
			this.systemService.updateFunction(map);
		}
		return "redirect:../system/functionList";
	}

	/**
	 * 删除菜单信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletefunction")
	public String deletefunction(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		this.systemService.deleteFunction(map);
		return "redirect:../system/functionList";
	}

	/**
	 * 编辑用户信息
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/editUser")
	public String editUser(@RequestParam Map<String, Object> map, Model model) {
		if (map.containsKey("ispass") && map.get("ispass").equals("yes")) {
			map.put("password", Md5Util.getMD5(map.get("password") + ""));
		}
		int row = systemService.editUser(map);
		return "redirect:/system/queryUserList";
	}

	/**
	 * 重置用户密码
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(@RequestParam Map<String, Object> map, Model model) {
		map.put("password", Md5Util.getMD5(map.get("password") + ""));
		systemService.resetPwd(map);
		return "redirect:/system/queryUserList";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryUserList")
	public String queryUserList(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);

		List<Map<String, Object>> userList = systemService.getUserList(map);
		int num = systemService.getUserListNum(map);
		pageHelper.setTotalCount(num);
		model.addAttribute("userList", userList);
		model.addAttribute("map", map);
		model.addAttribute("roleList", systemService.getRole());
		model.addAttribute("pager", pageHelper.paginate1().toString());

		Map<String, Object> provinceMap = new HashMap<String, Object>();
		provinceMap.put("parentid", 4);
		List<Map<String, Object>> provinceList = systemService.getAreaList(provinceMap);
		model.addAttribute("provinceList", provinceList);

		return "/system/user_list";
	}

	/**
	 * 修改用户权限
	 * 
	 * @param userid
	 * @param roleids
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping("/editUserRole")
	public String editUserRole(@RequestParam("userid") String userid, @RequestParam("roleids") String roleids,
			@RequestParam("roleid") String[] ids, Model model) {
		systemService.editUserRole(userid, roleids, ids);
		return "redirect:/system/queryUserList";
	}

	/**
	 * 角色管理
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param data
	 * @return
	 */
	@RequestMapping("/getRoleList")
	public String getRoleList(HttpServletRequest request, Model model, @RequestParam Map<String, Object> data) {

		PageHelper pageHelper = new PageHelper(request);
		List<Map<String, Object>> functionList = this.systemService.getFunctionListAll(); // 菜单功能
		// 处理分页参数
		pageHelper.initPage(data);
		// RoleList=this.systemService.getRoleList(data);

		int num = systemService.getRoleListNum(data);// 角色数量
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> roleList = this.systemService.getRoleListAll();
		request.setAttribute("RoleList", roleList);
		model.addAttribute("functionList", functionList);
		request.setAttribute("data", data);
		request.setAttribute("pager", pageHelper.paginate1().toString());
		return "/system/role_list";
	}

	/**
	 * 编辑角色
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	@RequestMapping("/editRole")
	public String editRole(HttpServletRequest request, @RequestParam Map<String, Object> paramCondition) {
		if (paramCondition.get("roleid").equals("") || paramCondition.get("roleid") == null) {
			systemService.insertRole(paramCondition);
		} else {
			systemService.updateRole(paramCondition);
		}
		return "redirect:/system/getRoleList";
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	@RequestMapping("/deleteRole")
	public String deleteRole(HttpServletRequest request, @RequestParam Map<String, Object> paramCondition) {
		if (paramCondition.get("status").equals("") || paramCondition.get("status") == null) {
			this.systemService.deleteRole(paramCondition);
		} else {
			this.systemService.updateRole(paramCondition);
		}
		return "redirect:/system/getRoleList";
	}

	/**
	 * 角色菜单
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	@RequestMapping("/editRoleFunction")
	public String editRoleFunction(@RequestParam("roleid") String roleid, @RequestParam("funids") String funids,
			@RequestParam("funid") String[] ids) {
		this.systemService.editRoleFunction(roleid, funids, ids);
		return "redirect:/system/getRoleList?role="+roleid;
	}

	/**
	 * 查询角色对应的功能
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	@RequestMapping("/findFactFunByRole")
	public void findFactFunByRole(@RequestParam Map<String, Object> map, HttpServletResponse response,
			HttpServletRequest request) {
		List<String> roleFuncList = systemService.findFactFunByRole(map);
		try {
			PrintWriter print = response.getWriter();
			print.print(JSON.json(roleFuncList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到地区
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAreaDic")
	public String getAreaDic(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		if (!map.containsKey("parentid")) {
			map.put("parentid", 4);
			map.put("fromid", "province");
		}
		List<Map<String, Object>> provinceList = this.systemService.getAreaList(map);
		int num = this.systemService.getAreaCount(map);
		pageHelper.setTotalCount(num);
		model.addAttribute("map", map);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		if (map.get("fromid").equals("province")) {
			return "/system/area_province_dic_list";
		} else if (map.get("fromid").equals("city")) {
			return "/system/area_city_dic_list";
		} else {
			return "/system/area_district_dic_list";
		}

	}

	/**
	 * 修改地区
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateArea")
	public String updateAreaCity(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		this.systemService.updateArea(map);
		return "redirect:/system/getAreaDic?parentid=" + map.get("parentid") + "&fromid=" + map.get("fromid");

	}

	/**
	 * 新增地区
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/insertArea")
	public String insertArea(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		this.systemService.insertArea(map);
		return "redirect:/system/getAreaDic?parentid=" + map.get("parentid") + "&fromid=" + map.get("fromid");
	}

	/**
	 * 修改密码页面
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/goUpdatePwd")
	public String goUpdatePwd(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		Map<String, Object> usermap = systemService.getUserInfo(map);
		map.put("oldPassword", usermap.get("password"));
		model.addAttribute("map", map);
		return "/system/password_modification";

	}

	/**
	 * 修改密码
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(@RequestParam Map<String, Object> map) {
		map.put("password", Md5Util.getMD5(map.get("password") + ""));
		this.systemService.resetPwd(map);
		return "redirect:/system/login";
	}

	@RequestMapping("/gettestcode")
	@ResponseBody
	public String gettestcode(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		String testcode = String.valueOf(request.getSession().getAttribute("testcode"));
		return testcode;
	}

	/**
	 * 查询佣金统计列表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/commissionlist")
	public String commissionlist(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {

		List<Map<String, Object>> agencysaleList = new ArrayList<Map<String, Object>>();
		Map<String, Object> agencyMap = new HashMap<String, Object>();
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		boolean flag = isManager(request, userid);
		if (flag || isInvestment(request, userid)) {
			if (!map.containsKey("salerid") && !map.containsKey("parentid")) {
				map.put("salerid", "");
				map.put("agencysalename", "平台");
			}
			// 所有的代理商和销售
			agencysaleList = this.agencyService.getAgencyList(agencyMap);
			model.addAttribute("ifmanager", 1);
		} else {
			if (!map.containsKey("salerid") && !map.containsKey("parentid")) {
				map.put("salerid", userMap.get("userid"));
				Map<String, Object> agencymap = new HashMap<String, Object>();
				agencymap.put("userid", userMap.get("userid"));
				List<Map<String, Object>> agencysalelist = this.agencyService.getAgencyList(agencymap);
				if (agencysalelist != null && agencysalelist.size() > 0) {
					agencymap = agencysalelist.get(0);
				}
				map.put("agencysalename", agencymap.get("agencyname"));
			}

			Map<String, Object> dt = new HashMap<String, Object>();
			dt.put("userid", userid);
			List<Map<String, Object>> agency = this.agencyService.getAgencyList(dt);// 得到当前代理商的代理id
			// 我下面的代理商和销售
			if(agency!=null && agency.size()>0){
				agencyMap.put("agencyid", agency.get(0).get("agencyid"));
				agencysaleList = this.agencyService.getChildrenAgency(agencyMap);
			}
		}
		if (map.containsKey("salerid") && "".equals(map.get("salerid"))) {
			map.put("parentid", '0');
		}
		model.addAttribute("agencysaleList", agencysaleList);

		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);

		Map<String, Object> map_v = new HashMap<String, Object>();
		map_v.putAll(map);
		List<Map<String, Object>> dataList = systemService.getCommissionList(map_v);
		for (Map<String, Object> data : dataList) {
			// 计算总消费金额
			if (String.valueOf(data.get("parentid")).equals("0")) {
				Map<String, Object> amountmap = new HashMap<String, Object>();
				amountmap.put("agencyid", data.get("agencyid"));
				String amount = this.systemService.getAmountAll(amountmap);
				amountsum = 0.00;
				Double amounts = getAmount(data.get("agencyid") + "");
				Double commissionamount = ((Double.parseDouble(amount) + amounts)
						* Double.parseDouble(data.get("commissionrate") + "")) / 100;
				data.put("amount", Double.parseDouble(amount) + amounts);
				String lastamount = commissionamount + "";
				data.put("commissionamount", lastamount.substring(0, lastamount.indexOf(".") + 2));
			} else {
//				parentagencyid = "";
//				proportion = 1.00;
//				String agencyid = getparentid(data.get("agencyid") + "");

				Map<String, Object> amountmap = new HashMap<String, Object>();
				amountmap.put("agencyid", data.get("agencyid") + "");
				String amount = this.systemService.getAmountAll(amountmap);
				amountsum = 0.00;
				Double amounts = getAmount(data.get("agencyid") + "");
				Double commissionamount =  ((Double.parseDouble(amount) + amounts)
						* Double.parseDouble(data.get("commissionrate") + "")) / 100;

				/*amountmap.put("agencyid", data.get("agencyid"));
				String amount1 = this.systemService.getAmountAll(amountmap);
				data.put("amount", Double.parseDouble(amount1));*/
				data.put("amount", Double.parseDouble(amount) + amounts);
				String lastamount = commissionamount + "";
				data.put("commissionamount", lastamount.substring(0, lastamount.indexOf(".") + 2));
			}
		}
		int num = systemService.getCommissionListNum(map_v);
		pageHelper.setTotalCount(num);
		model.addAttribute("dataList", dataList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());

		return "/system/statistical_commissionlist";
	}

	/**
	 * 查询活跃
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/activemembers")
	public String activemembers(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		
		List<Map<String,Object>> agencysaleList = new ArrayList<Map<String,Object>>();
		Map<String,Object> agencyMap = new HashMap<String, Object>();
		Map<String,Object> userMap = UserUtil.getUser(request);
		String userid= userMap.get("userid")+"";
		boolean flag=isManager(request,userid);
		if(flag || isInvestment(request, userid)){
			if(!map.containsKey("agencyid")){
				map.put("agencysalename","平台");
				map.put("agencyid","");
			}
			//所有的代理商和销售
			agencysaleList = this.agencyService.getAgencyList(agencyMap);
			model.addAttribute("ifmanager", 1);
		}else{
			 if(!map.containsKey("agencyid")){
				map.put("agencyid", userMap.get("userid"));
				Map<String, Object> agencymap=new HashMap<String, Object>();
				agencymap.put("userid", userMap.get("userid"));
				List<Map<String, Object>> agencysalelist=this.agencyService.getAgencyList(agencymap);
				if(agencysalelist!= null && agencysalelist.size()>0){
					agencymap=agencysalelist.get(0);
				}
				map.put("agencysalename", agencymap.get("agencyname"));
			  }else{
				//获取当前用户id，以区分数据权限
				map.put("sourcepid", userid);
			  }
			  
			  Map<String, Object> dt = new HashMap<String, Object>();
			  dt.put("userid", userid);
			  List<Map<String,Object>> agency = this.agencyService.getAgencyList(dt);//得到当前代理商的代理id
			  //我下面的代理商和销售
			  if(agency!=null && agency.size()>0){
				  agencyMap.put("agencyid", agency.get(0).get("agencyid"));
				  agencysaleList = this.agencyService.getChildrenAgency(agencyMap);
			  }
		}
		model.addAttribute("agencysaleList",agencysaleList);
		

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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

			Map<String, Object> map_v = new HashMap<String, Object>();
			map_v.putAll(map);
			dataList = systemService.getActiveMembers(map_v);
			model.addAttribute("ifcheck", "1");
		} else if (map.get("ifcheck").equals("2")) {
			for (int i = 1; i < 5; i++) {
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
				cal.add(Calendar.DAY_OF_MONTH, -(i * 7));
				cal1.add(Calendar.DAY_OF_MONTH, -((i - 1) * 7));
				map.put("starttime", cal.getTime());
				map.put("stoptime", cal1.getTime());

				Map<String, Object> map_v = new HashMap<String, Object>();
				map_v.putAll(map);
				int mountcount = this.systemService.getActiveMounthcount(map_v);
				Map<String, Object> mountmap = new HashMap<String, Object>();
				mountmap.put("comparetime", i + "周");
				mountmap.put("countnum", mountcount);
				dataList.add(mountmap);
			}
			model.addAttribute("ifcheck", "2");
		} else if (map.get("ifcheck").equals("3")) {
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

			Map<String, Object> map_v = new HashMap<String, Object>();
			map_v.putAll(map);
			dataList = systemService.getActiveMembersbyYear(map_v);
			for (Map<String, Object> member : dataList) {
				member.put("comparetime", member.get("comparetime") + "月");
			}
			model.addAttribute("ifcheck", "3");
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("map", map);

		return "/system/statistical_activemembers";
	}

	/**
	 * 查询用户活跃度（点击节点触发跳转）
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/clickactivemembers")
	public String clickactivemembers(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		SimpleDateFormat sdf_public = new SimpleDateFormat("yyyy-");
		String text_public = sdf_public.format(new Date());

		String v_str = "";
		if (map.containsKey("salerid") && map.get("salerid") != null && !map.get("salerid").equals("")) {
			v_str = "&salerid=" + map.get("salerid");
		} else {
			if (map.containsKey("agencyid") && map.get("agencyid") != null && !map.get("agencyid").equals("")) {
				v_str = "&agencyid=" + map.get("agencyid");
			}
		}

		if (String.valueOf(map.get("ifcheck")).equals("1")) {
			text_public += map.get("name");
			return "redirect:/system/member/getMemberList?v_query=" + text_public + v_str;
		} else if (String.valueOf(map.get("ifcheck")).equals("2")) {
			String name = String.valueOf(map.get("name")).replaceAll("周", "");
			int y_name_old = Integer.parseInt(name);
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
			cal.add(Calendar.DAY_OF_MONTH, -(y_name_old * 7));
			cal1.add(Calendar.DAY_OF_MONTH, -((y_name_old - 1) * 7));
			return "redirect:/system/member/getMemberList?v_query_start=" + sdf.format(cal.getTime()) + "&v_query_end="
					+ sdf.format(cal1.getTime()) + v_str;
		} else {
			String name = String.valueOf(map.get("name")).replaceAll("月", "");
			int y_name_old = Integer.parseInt(name);
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String text = sdf.format(new Date());
			int y_name_new = Integer.parseInt(text);
			if (y_name_new > y_name_old) {
				return "redirect:/system/member/getMemberList?v_query_ym=" + text_public + name + v_str;
			} else if (y_name_new == y_name_old) {
				if (String.valueOf(map.get("index")).equals("12")) {
					return "redirect:/system/member/getMemberList?v_query_ym=" + text_public + name + v_str;
				} else {
					Date date = DateUtil.changeTime(new Date(), "month", -12);
					String year = sdf_public.format(date);
					return "redirect:/system/member/getMemberList?v_query_ym=" + year + name + v_str;
				}
			} else {
				Date date = DateUtil.changeTime(new Date(), "month", -12);
				String year = sdf_public.format(date);
				return "redirect:/system/member/getMemberList?v_query_ym=" + year + name + v_str;
			}
		}

	}
	
	/**
	 * 查询用户活跃度（点击节点触发跳转）
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/addmembers")
	public String addmembers(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		SimpleDateFormat sdf_public = new SimpleDateFormat("yyyy-");
		String text_public = sdf_public.format(new Date());

		String v_str = "";
		if (map.containsKey("salerid") && map.get("salerid") != null && !map.get("salerid").equals("")) {
			v_str = "&salerid=" + map.get("salerid");
		} else {
			if (map.containsKey("agencyid") && map.get("agencyid") != null && !map.get("agencyid").equals("")) {
				v_str = "&agencyid=" + map.get("agencyid");
			}
		}

		if (String.valueOf(map.get("ifcheck")).equals("1")) {
			text_public += map.get("name");
			return "redirect:/system/member/getMemberList?add_query=" + text_public + v_str;
		} else if (String.valueOf(map.get("ifcheck")).equals("2")) {
			String name = String.valueOf(map.get("name")).replaceAll("周", "");
			int y_name_old = Integer.parseInt(name);
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
			cal.add(Calendar.DAY_OF_MONTH, -(y_name_old * 7));
			cal1.add(Calendar.DAY_OF_MONTH, -((y_name_old - 1) * 7));
			return "redirect:/system/member/getMemberList?add_query_start=" + sdf.format(cal.getTime()) + "&add_query_end="
					+ sdf.format(cal1.getTime()) + v_str;
		} else {
			String name = String.valueOf(map.get("name")).replaceAll("月", "");
			int y_name_old = Integer.parseInt(name);
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String text = sdf.format(new Date());
			int y_name_new = Integer.parseInt(text);
			if (y_name_new > y_name_old) {
				return "redirect:/system/member/getMemberList?add_query_ym=" + text_public + name + v_str;
			} else if (y_name_new == y_name_old) {
				if (String.valueOf(map.get("index")).equals("12")) {
					return "redirect:/system/member/getMemberList?add_query_ym=" + text_public + name + v_str;
				} else {
					Date date = DateUtil.changeTime(new Date(), "month", -12);
					String year = sdf_public.format(date);
					return "redirect:/system/member/getMemberList?add_query_ym=" + year + name + v_str;
				}
			} else {
				Date date = DateUtil.changeTime(new Date(), "month", -12);
				String year = sdf_public.format(date);
				return "redirect:/system/member/getMemberList?add_query_ym=" + year + name + v_str;
			}
		}

	}

	/**
	 * 查询抢单数量
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/grabsinglelist")
	public String grabsinglelist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){

		List<Map<String,Object>> agencysaleList = new ArrayList<Map<String,Object>>();
		Map<String,Object> agencyMap = new HashMap<String, Object>();
		Map<String,Object> userMap = UserUtil.getUser(request);
		String userid= userMap.get("userid")+"";
		boolean flag=isManager(request,userid);
		if(flag || isInvestment(request, userid)){
			if(!map.containsKey("agencyid")){
				map.put("agencyid","");
				map.put("agencysalename","平台");
			}
			//所有的代理商和销售
			agencysaleList = this.agencyService.getAgencyList(agencyMap);
			model.addAttribute("ifmanager", 1);
		}else{
			 if(!map.containsKey("agencyid")){
				map.put("agencyid", userMap.get("userid"));
				Map<String, Object> agencymap=new HashMap<String, Object>();
				agencymap.put("userid", userMap.get("userid"));
				List<Map<String, Object>> agencysalelist=this.agencyService.getAgencyList(agencymap);
				if(agencysalelist!= null && agencysalelist.size()>0){
					agencymap=agencysalelist.get(0);
				}
				map.put("agencysalename", agencymap.get("agencyname"));
			  }
			  
			  Map<String, Object> dt = new HashMap<String, Object>();
			  dt.put("userid", userid);
			  List<Map<String,Object>> agency = this.agencyService.getAgencyList(dt);//得到当前代理商的代理id
			  //我下面的代理商和销售
			  if(agency!=null && agency.size()>0){
				  agencyMap.put("agencyid", agency.get(0).get("agencyid"));
				  agencysaleList = this.agencyService.getChildrenAgency(agencyMap);
			  }
		}
		model.addAttribute("agencysaleList",agencysaleList);
		
		List<Map<String, Object>> dataList =new ArrayList<Map<String,Object>>();
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
			
			Map<String,Object> map_v = new HashMap<String,Object>();
			map_v.putAll(map);
			dataList= systemService.getGrabsinglelist(map_v);
			model.addAttribute("ifcheck","1");
			
		} else if (map.get("ifcheck").equals("2")) {
			for (int i = 1; i < 5; i++) {
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
				
				Map<String,Object> map_v = new HashMap<String,Object>();
				map_v.putAll(map);
				int mountcount=this.systemService.getGrabsinglecount(map_v);
				Map<String, Object> mountmap=new HashMap<String, Object>();
				mountmap.put("comparetime",i+"周");
				mountmap.put("countnum", mountcount);
				dataList.add(mountmap);
			}
			model.addAttribute("ifcheck", "2");
		} else if (map.get("ifcheck").equals("3")) {
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
			
			Map<String,Object> map_v = new HashMap<String,Object>();
			map_v.putAll(map);
			
			dataList= systemService.getGrabsinglebyYear(map_v); 
			for(Map<String, Object> member:dataList){
				member.put("comparetime", member.get("comparetime")+"月");
			}
			model.addAttribute("ifcheck", "3");
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("map", map);

		return "/system/statistical_grabsingle";
	}

	/**
	 * 查询抢单数量（点击节点触发跳转）
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/clickgrabsingle")
	public String clickgrabsingle(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		SimpleDateFormat sdf_public = new SimpleDateFormat("yyyy-");
		String text_public = sdf_public.format(new Date());

		String v_str = "";
		if (map.containsKey("salerid") && map.get("salerid") != null && !map.get("salerid").equals("")) {
			v_str = "&salerid=" + map.get("salerid");
		} else {
			if (map.containsKey("agencyid") && map.get("agencyid") != null && !map.get("agencyid").equals("")) {
				v_str = "&agencyid=" + map.get("agencyid");
			}
		}

		if (String.valueOf(map.get("ifcheck")).equals("1")) {
			text_public += map.get("name");
			return "redirect:/system/order/rushorder?status=3&v_query=" + text_public + v_str;
		} else if (String.valueOf(map.get("ifcheck")).equals("2")) {
			String name = String.valueOf(map.get("name")).replaceAll("周", "");
			int y_name_old = Integer.parseInt(name);
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
			cal.add(Calendar.DAY_OF_MONTH, -(y_name_old * 7));
			cal1.add(Calendar.DAY_OF_MONTH, -((y_name_old - 1) * 7));
			return "redirect:/system/order/rushorder?status=3&v_query_start=" + sdf.format(cal.getTime())
					+ "&v_query_end=" + sdf.format(cal1.getTime()) + v_str;
		} else {
			String name = String.valueOf(map.get("name")).replaceAll("月", "");
			int y_name_old = Integer.parseInt(name);
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String text = sdf.format(new Date());
			int y_name_new = Integer.parseInt(text);
			if (y_name_new > y_name_old) {
				return "redirect:/system/order/rushorder?v_query_ym=" + text_public + name + v_str;
			} else if (y_name_new == y_name_old) {
				if (String.valueOf(map.get("index")).equals("12")) {
					return "redirect:/system/order/rushorder?v_query_ym=" + text_public + name + v_str;
				} else {
					Date date = DateUtil.changeTime(new Date(), "month", -12);
					String year = sdf_public.format(date);
					return "redirect:/system/order/rushorder?v_query_ym=" + year + name + v_str;
				}
			} else {
				Date date = DateUtil.changeTime(new Date(), "month", -12);
				String year = sdf_public.format(date);
				return "redirect:/system/order/rushorder?v_query_ym=" + year + name + v_str;
			}
		}

	}

	/**
	 * 查询发布订单报表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/publishorderlist")
	public String publishorderlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		
		List<Map<String,Object>> agencysaleList = new ArrayList<Map<String,Object>>();
		Map<String,Object> agencyMap = new HashMap<String, Object>();
		Map<String,Object> userMap = UserUtil.getUser(request);
		String userid= userMap.get("userid")+"";
		boolean flag=isManager(request,userid);
		if(flag || isInvestment(request, userid)){
			if(!map.containsKey("agencyid")){
				map.put("agencyid","");
				map.put("agencysalename","平台");
			}
			//所有的代理商和销售
			agencysaleList = this.agencyService.getAgencyList(agencyMap);
			model.addAttribute("ifmanager", 1);
		}else{
			 if(!map.containsKey("agencyid")){
				map.put("agencyid", userMap.get("userid"));
				Map<String, Object> agencymap=new HashMap<String, Object>();
				agencymap.put("userid", userMap.get("userid"));
				List<Map<String, Object>> agencysalelist=this.agencyService.getAgencyList(agencymap);
				if(agencysalelist!= null && agencysalelist.size()>0){
					agencymap=agencysalelist.get(0);
				}
				map.put("agencysalename", agencymap.get("agencyname"));
			  }
			  
			  Map<String, Object> dt = new HashMap<String, Object>();
			  dt.put("userid", userid);
			  List<Map<String,Object>> agency = this.agencyService.getAgencyList(dt);//得到当前代理商的代理id
			  //我下面的代理商和销售
			  if(agency!=null && agency.size()>0){
				  agencyMap.put("agencyid", agency.get(0).get("agencyid"));
				  agencysaleList = this.agencyService.getChildrenAgency(agencyMap);
			  }
		}
		model.addAttribute("agencysaleList",agencysaleList);
	
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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

			Map<String, Object> map_v = new HashMap<String, Object>();
			map_v.putAll(map);
			if (map.containsKey("agencyid") && map.containsKey("salerid")) {
				map_v.remove("agencyid");
			}
			dataList = systemService.getpublishorderlist(map_v);
			model.addAttribute("ifcheck", "1");
		} else if (map.get("ifcheck").equals("2")) {
			for (int i = 1; i < 5; i++) {
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
				cal.add(Calendar.DAY_OF_MONTH, -(i * 7));
				cal1.add(Calendar.DAY_OF_MONTH, -((i - 1) * 7));
				map.put("starttime", cal.getTime());
				map.put("stoptime", cal1.getTime());

				Map<String, Object> map_v = new HashMap<String, Object>();
				map_v.putAll(map);
				if (map.containsKey("agencyid") && map.containsKey("salerid")) {
					map_v.remove("agencyid");
				}
				int mountcount = this.systemService.getpublishordercount(map_v);
				Map<String, Object> mountmap = new HashMap<String, Object>();
				mountmap.put("comparetime", i + "周");
				mountmap.put("countnum", mountcount);
				dataList.add(mountmap);
			}
			model.addAttribute("ifcheck", "2");
		} else if (map.get("ifcheck").equals("3")) {
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

			Map<String, Object> map_v = new HashMap<String, Object>();
			map_v.putAll(map);
			if (map.containsKey("agencyid") && map.containsKey("salerid")) {
				map_v.remove("agencyid");
			}
			dataList = systemService.getpublishorderbyYear(map_v);
			for (Map<String, Object> member : dataList) {
				member.put("comparetime", member.get("comparetime") + "月");
			}
			model.addAttribute("ifcheck", "3");
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("map", map);

		return "/system/statistical_publishorder";
	}

	/**
	 * 查询发布订单报表（点击节点触发跳转）
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/clickpublishorder")
	public String clickpublishorder(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		SimpleDateFormat sdf_public = new SimpleDateFormat("yyyy-");
		String text_public = sdf_public.format(new Date());

		String v_str = "";
		if (map.containsKey("salerid") && map.get("salerid") != null && !map.get("salerid").equals("")) {
			v_str = "&salerid=" + map.get("salerid");
		} else {
			if (map.containsKey("agencyid") && map.get("agencyid") != null && !map.get("agencyid").equals("")) {
				v_str = "&agencyid=" + map.get("agencyid");
			}
		}

		if (String.valueOf(map.get("ifcheck")).equals("1")) {
			text_public += map.get("name");
			return "redirect:/system/order/releaseorder?v_query=" + text_public + v_str;
		} else if (String.valueOf(map.get("ifcheck")).equals("2")) {
			String name = String.valueOf(map.get("name")).replaceAll("周", "");
			int y_name_old = Integer.parseInt(name);
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
			cal.add(Calendar.DAY_OF_MONTH, -(y_name_old * 7));
			cal1.add(Calendar.DAY_OF_MONTH, -((y_name_old - 1) * 7));
			return "redirect:/system/order/releaseorder?v_query_start=" + sdf.format(cal.getTime()) + "&v_query_end="
					+ sdf.format(cal1.getTime()) + v_str;
		} else {
			String name = String.valueOf(map.get("name")).replaceAll("月", "");
			int y_name_old = Integer.parseInt(name);
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String text = sdf.format(new Date());
			int y_name_new = Integer.parseInt(text);
			if (y_name_new > y_name_old) {
				return "redirect:/system/order/releaseorder?v_query_ym=" + text_public + name + v_str;
			} else if (y_name_new == y_name_old) {
				if (String.valueOf(map.get("index")).equals("12")) {
					return "redirect:/system/order/releaseorder?v_query_ym=" + text_public + name + v_str;
				} else {
					Date date = DateUtil.changeTime(new Date(), "month", -12);
					String year = sdf_public.format(date);
					return "redirect:/system/order/releaseorder?v_query_ym=" + year + name + v_str;
				}
			} else {
				Date date = DateUtil.changeTime(new Date(), "month", -12);
				String year = sdf_public.format(date);
				return "redirect:/system/order/releaseorder?v_query_ym=" + year + name + v_str;
			}
		}

	}

	/**
	 * 查询交易金额报表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/traderecordlist")
	public String traderecordlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		 
		 Map<String,Object> userMap = UserUtil.getUser(request);
		 String userid = userMap.get("userid")+"";
		 
		 List<Map<String,Object>> agenList = new ArrayList<Map<String,Object>>();// 代理商销售商集合
		 Map<String,Object> pvMap = new HashMap<String, Object>();
		 
		 if(isManager(request, userid) || isInvestment(request, userid)){
			//所有的代理商和销售
			 if(!map.containsKey("agencyid")){
				 map.put("agencyname", "平台");	 
				 map.put("agencyid", "");
			 }
			 agenList = this.agencyService.getAgencyList(pvMap);
			 model.addAttribute("ifmanager", 1);
		 }else{
			  if(!map.containsKey("agencyid")){
					map.put("agencyid", userMap.get("userid"));
					Map<String, Object> agencymap=new HashMap<String, Object>();
					agencymap.put("userid", userMap.get("userid"));
					List<Map<String, Object>> agencysalelist=this.agencyService.getAgencyList(agencymap);
					if(agencysalelist!= null && agencysalelist.size()>0){
						agencymap=agencysalelist.get(0);
					}
					map.put("agencyname", agencymap.get("agencyname"));
				  }
			  Map<String, Object> dt = new HashMap<String, Object>();
			  dt.put("userid", userid);
			  List<Map<String,Object>> agency = this.agencyService.getAgencyList(dt);//得到当前代理商的代理id
			  //我下面的代理商和销售
			  if(agency!=null && agency.size()>0){
				  pvMap.put("agencyid", agency.get(0).get("agencyid"));
			  	  agenList = this.agencyService.getChildrenAgency(pvMap);
			  }
		 }
		model.addAttribute("agenList",agenList);
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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
			dataList = systemService.gettraderecordlist(map);
			model.addAttribute("ifcheck", "1");
		} else if (map.get("ifcheck").equals("2")) {
			for (int i = 1; i < 5; i++) {
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
				cal.add(Calendar.DAY_OF_MONTH, -(i * 7));
				cal1.add(Calendar.DAY_OF_MONTH, -((i - 1) * 7));
				map.put("starttime", cal.getTime());
				map.put("stoptime", cal1.getTime());
				Map<String, Object> mountinfo=new HashMap<String, Object>();
				
				mountinfo = this.systemService.gettraderecordcount(map);
				Map<String, Object> mountmap = new HashMap<String, Object>();
				mountmap.put("comparetime", i + "周");
				mountmap.put("countnum", mountinfo.get("tradeamount"));
				dataList.add(mountmap);
			}
			model.addAttribute("ifcheck", "2");
		} else if (map.get("ifcheck").equals("3")) {
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
			dataList = systemService.gettraderecordbyYear(map);
			for (Map<String, Object> member : dataList) {
				member.put("comparetime", member.get("comparetime") + "月");
			}
			model.addAttribute("ifcheck", "3");
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("map", map);

		return "/system/statistical_traderecord";
	}

	/**
	 * 查询交易金额报表（点击节点触发跳转）
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/clicktraderecord")
	public String clicktraderecord(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		SimpleDateFormat sdf_public = new SimpleDateFormat("yyyy-");
		String text_public = sdf_public.format(new Date());

		String v_str = "";
		if (map.containsKey("salerid") && map.get("salerid") != null && !map.get("salerid").equals("")) {
			v_str = "&salerid=" + map.get("salerid");
		} else {
			if (map.containsKey("agencyid") && map.get("agencyid") != null && !map.get("agencyid").equals("")) {
				v_str = "&agencyid=" + map.get("agencyid");
			}
		}

		if (String.valueOf(map.get("ifcheck")).equals("1")) {
			text_public += map.get("name");
			return "redirect:/system/trade/gettradelist?v_query=" + text_public + v_str;
		} else if (String.valueOf(map.get("ifcheck")).equals("2")) {
			String name = String.valueOf(map.get("name")).replaceAll("周", "");
			int y_name_old = Integer.parseInt(name);
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
			cal.add(Calendar.DAY_OF_MONTH, -(y_name_old * 7));
			cal1.add(Calendar.DAY_OF_MONTH, -((y_name_old - 1) * 7));
			return "redirect:/system/trade/gettradelist?v_query_start=" + sdf.format(cal.getTime()) + "&v_query_end="
					+ sdf.format(cal1.getTime()) + v_str;
		} else {
			String name = String.valueOf(map.get("name")).replaceAll("月", "");
			int y_name_old = Integer.parseInt(name);
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String text = sdf.format(new Date());
			int y_name_new = Integer.parseInt(text);
			if (y_name_new > y_name_old) {
				return "redirect:/system/trade/gettradelist?v_query_ym=" + text_public + name + v_str;
			} else if (y_name_new == y_name_old) {
				if (String.valueOf(map.get("index")).equals("12")) {
					return "redirect:/system/trade/gettradelist?v_query_ym=" + text_public + name + v_str;
				} else {
					Date date = DateUtil.changeTime(new Date(), "month", -12);
					String year = sdf_public.format(date);
					return "redirect:/system/trade/gettradelist?v_query_ym=" + year + name + v_str;
				}
			} else {
				Date date = DateUtil.changeTime(new Date(), "month", -12);
				String year = sdf_public.format(date);
				return "redirect:/system/trade/gettradelist?v_query_ym=" + year + name + v_str;
			}
		}

	}

	/**
	 * 验证用户名是否存在
	 * 
	 */
	@RequestMapping("/validateUsername")
	@ResponseBody
	public boolean validateUsername(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		boolean flg = false;
		Map<String, Object> user = this.systemService.getUserInfo(map);
		if (user == null || user.size() == 0) {
			flg = true;
		}
		return flg;
	}

	/**
	 * 查询代理商下的销售
	 * 
	 * @return
	 */
	@RequestMapping("/getAgencyData")
	@ResponseBody
	public Map<String, Object> getAgencyData(@RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		List<Map<String, Object>> agencylist = this.systemService.getMemberList(map);
		returnMap.put("data", agencylist);

		return returnMap;
	}
}
