package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.util.StringUtil;
import com.lr.backer.util.Constants;
import com.lr.backer.util.CookieUtil;
import com.lr.backer.util.Md5Util;
import com.lr.backer.util.UserUtil;
import com.lr.backer.util.baidujingweicity;
import com.lr.backer.util.paymoneyutil;
import com.lr.backer.util.sendSms;
import com.lr.labor.weixin.core.CoreService;
import com.lr.weixin.backer.service.MemberService;

/**
 * 
 * @author win7
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private transient static Log log = LogFactory.getLog(LoginController.class);
	@Resource
	MemberService memberService;

	/**
	 * 初始化用户密码登陆页面
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/inintLoginPassword")
	public String inintLoginPassword(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		UserUtil.deleteUser(request, response);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/login/login_password";
	}

	/**
	 * 初始化手机号码直接登陆页面
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/startPhoneLogin")
	public String startPhoneLogin(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/login/login_tel";
	}

	/**
	 * 初始化用户协议页面
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/startAgreement")
	public String startAgreement(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request,HttpServletResponse response) {
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request) + "");
		
		//测试 支付签名
		/*Map<String, Object> orderMap=new HashMap<String, Object>();
		orderMap.put("price", 0.01);
		orderMap.put("notuify_url",Constants.PROJECT_PATH
				+ "pay/payIosResult");
		paymoneyutil pay = new paymoneyutil();
		Map<String, Object> appSignMap=pay.getAppSign(request, response, orderMap);
		model.addAttribute("appSignMap",appSignMap);
		*/
		return "/phone/login/agreement";
	}

	/**
	 * 用户密码登陆
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/passwordLogin")
	@ResponseBody
	public Map<String, Object> passwordLogin(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flg = false;
		if (map.get("username") != null && !map.get("username").equals("") && map.get("password") != null
				&& !map.get("password").equals("")) {
			Map<String, Object> memberMap = new HashMap<String, Object>();
			memberMap.put("loginname", map.get("username"));
			List<Map<String, Object>> memberList = this.memberService.getMemberList(memberMap);
			if (memberList != null && memberList.size() > 0) {
				Object password = memberList.get(0).get("password");
				Object logPassword = Md5Util.getMD5(map.get("password").toString());
				if (logPassword.equals(password)) {
					if(String.valueOf(memberList.get(0).get("status")).equals("1")){
						boolean lg = false;
						if (map.get("latitude") == null || map.get("latitude").equals("") || map.get("longitude") == null
								|| map.get("longitude").equals("")) {
							log.debug(
									"------------------------------------------*********************************************************************************************login:获取用户经纬度失败");
							lg = false;
						} else {
							lg = true;
						}

						/*
						 * if(map.get("longitude") == null ||
						 * map.get("longitude").equals("")){ map.put("longitude",
						 * "121.497008"); }
						 */

						Timestamp tm = new Timestamp(System.currentTimeMillis());
						Map<String, Object> dt = new HashMap<String, Object>();
						Map<String, Object> ex = new HashMap<String, Object>();

						if (lg) {
							Map<String, Object> area = LoginController.getArea(map.get("latitude").toString(),
									map.get("longitude").toString());
							if (area != null) {
								ex.put("provinceid", area.get("provinceid"));
								ex.put("cityid", area.get("cityid"));
							}
						}

						
						if (map.get("device") != null && !map.get("device").equals("") && map.get("channelid") != null
								&& !map.get("channelid").equals("")) {
							// 如果已经有channelid 删除原有的设备id信息
							Map<String, Object> ch = new HashMap<String, Object>();
							ch.put("channelid", "");
							ch.put("chid", map.get("channelid"));
							this.memberService.updateExtend(ch);
							// 重新赋值新的channelid
							ex.put("userid", memberList.get(0).get("memberid"));
							ex.put("device", map.get("device"));
							ex.put("channelid", map.get("channelid"));
							this.memberService.updateExtend(ex);
						}

						dt.put("memberid", memberList.get(0).get("memberid"));
						dt.put("lasttime", tm);

						this.memberService.updateMember(dt);
						resultMap.put("msg", "登陆成功！");
						flg = true;
						UserUtil.setUser(response, memberList.get(0).get("memberid").toString(), memberList.get(0), "365");
						resultMap.put("userId", memberList.get(0).get("memberid"));
					}else{
						resultMap.put("msg", "您的帐户存在异常请联系管理员！");
					}
					
				} else {
					resultMap.put("msg", "密码错误，请重新输入！");
				}

			} else {
				resultMap.put("msg", "您登陆的用户不存在！");
			}
		} else {
			resultMap.put("msg", "手机号或者密码不能为空，请重新输入！");
		}

		resultMap.put("flg", flg);
		resultMap.put("iswx", HttpHeaderUtil.isWeiXinFrom(request));
		return resultMap;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/sendPhoneCode/{phone}")
	@ResponseBody
	public boolean sendPhone(@PathVariable String phone, HttpServletResponse response) {
		String code = "8888";
		// 发送验证码
		try {
			Random r = new Random();
			code = r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10);
			sendSms.sendSms1(phone, code);
		} catch (Exception e) {
			return false;
		}
		log.debug("code=" + code + "-------------------------------------");
		CookieUtil.setCookie(response, "PHONE_" + phone, code, "/", "1");
		return true;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param code
	 * 
	 * @return
	 */
	@RequestMapping("/validateCode")
	@ResponseBody
	public Map<String, Object> validateCode(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pvMap = new HashMap<String, Object>();
		Map<String, Object> dt = new HashMap<String, Object>();
		Map<String, Object> ex = new HashMap<String, Object>();
		Map<String, Object> memberMap = new HashMap<String, Object>();
		Map<String, Object> extMap = new HashMap<String, Object>();
		boolean flg = false;
		pvMap.put("loginname", map.get("phone"));
		log.info("----------------validateCode---map:"+map);
		Cookie obj = CookieUtil.getCookie(request, "PHONE_" + map.get("phone"));
		if (map.get("code") == null || map.get("code").equals("") || obj == null || obj.getValue() == null
				|| obj.getValue().toString().equals("")) {
			resultMap.put("msg", "手机号或者验证码不能为空");
		}else{
			if (obj.getValue().toString().equals(map.get("code").toString())) {
				String phone = map.get("phone").toString();
				Timestamp tm = new Timestamp(System.currentTimeMillis());
				boolean lg = false;
				if (map.get("latitude") == null || map.get("latitude").equals("") || map.get("longitude") == null
						|| map.get("longitude").equals("")) {
					log.debug(
							"------------------------------------------*********************************************************************************************login:获取用户经纬度失败");
					lg = false;
				} else {
					lg = true;
				}

				if (lg) {
					Map<String, Object> area = LoginController.getArea(map.get("latitude").toString(),
							map.get("longitude").toString());
					ex.put("provinceid", area.get("provinceid"));
					ex.put("cityid", area.get("cityid"));
					memberMap.put("provinceid", area.get("provinceid"));
					memberMap.put("cityid", area.get("cityid"));
					extMap.put("provinceid", area.get("provinceid"));
					extMap.put("cityid", area.get("cityid"));

				} else {
					log.debug(
							"------------------------------------------*********************************************************************************************login:获取用户经纬度失败");

				}

				List<Map<String, Object>> memberList = this.memberService.getMemberList(pvMap);
				if (memberList != null && memberList.size() > 0) {
					ex.put("userid", memberList.get(0).get("memberid"));
					if (map.get("device") != null && !map.get("device").equals("") && map.get("channelid") != null
							&& !map.get("channelid").equals("")) {
						// 如果已经有channelid 删除原有的设备id信息
						Map<String, Object> ch = new HashMap<String, Object>();
						ch.put("channelid", "");
						ch.put("chid", map.get("channelid"));
						this.memberService.updateExtend(ch);

						ex.put("device", map.get("device"));
						ex.put("channelid", map.get("channelid"));
						this.memberService.updateExtend(ex);
					}

					dt.put("memberid", memberList.get(0).get("memberid"));
					dt.put("lasttime", tm);
					this.memberService.updateMember(dt);
					UserUtil.setUser(response, memberList.get(0).get("memberid").toString(), memberList.get(0),
							Constants.REDIS_TIME);
					resultMap.put("userId", memberList.get(0).get("memberid"));
					flg = true;
				} else {
					// 创建新用户

					memberMap.put("memberid", StringUtil.getUUID());
					memberMap.put("username", phone);
					memberMap.put("phone", phone);
					memberMap.put("sex", "2");
					memberMap.put("status", "1");
					memberMap.put("createtime", tm);
					memberMap.put("ifattention", "0");
					// memberMap.put("nickname",phone.replace(phone.substring(3,7),"****"));
					memberMap.put("lasttime", tm);

					extMap.put("extendid", StringUtil.getUUID());
					extMap.put("userid", memberMap.get("memberid"));
					// 是否送滴滴币(待处理)extMap.put("tickcoin", "");

					extMap.put("evaluationavg", "0");
					extMap.put("createtime", tm);
					extMap.put("delflag", "0");
					extMap.put("perfectdegree", "20");
					extMap.put("iscompletion", "0");
					extMap.put("isgive", "0");
					extMap.put("origin", map.get("device"));
					if (map.get("device") != null && !map.get("device").equals("") && map.get("channelid") != null
							&& !map.get("channelid").equals("")) {
						extMap.put("device", map.get("device"));
						extMap.put("channelid", map.get("channelid"));
					}

					this.memberService.addMember(memberMap);
					this.memberService.insertExtend(extMap);
					UserUtil.setUser(response, memberMap.get("memberid").toString(), memberMap, "365");
					resultMap.put("userId", memberMap.get("memberid"));
					flg = true;
				}

			} else {

				resultMap.put("msg", "验证码输入错误,请重新输入！");
			}
		}
		resultMap.put("iswx", HttpHeaderUtil.isWeiXinFrom(request));
		resultMap.put("flg", flg);
		return resultMap;
	}

	/**
	 * 验证用户名是否唯一
	 * 
	 * @return
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public boolean checkUserName(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		boolean flg = false;
		List<Map<String, Object>> data = this.memberService.getMemberList(map);
		if (data != null && data.size() > 0) {
			flg = true;
		}
		return flg;
	}

	/**
	 * 得到用户最后一次登陆地址
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@SuppressWarnings({ "static-access" })
	private static Map<String, Object> getArea(String latitude, String longitude) {
		Map<String, Object> pvMap = new HashMap<String, Object>();
		Map<String, Object> locamap = new HashMap<String, Object>();
		locamap.put("latitude", latitude);
		locamap.put("longitude", longitude);
		baidujingweicity baidu = new baidujingweicity();

		String city = "";
		String province = "";
		try {
			city = baidu.getcity(latitude, longitude);
			province = baidu.getProvince(latitude, longitude);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取城市信息失败了---------------------------------");
		}

		Map<String, Object> cityInfo = new HashMap<String, Object>();
		cityInfo.put("cname", city);
		Map<String, Object> citymap = CoreService.getMemberService().getAreaInfo(cityInfo);
		cityInfo.put("cname", province);
		Map<String, Object> provincemap = CoreService.getMemberService().getAreaInfo(cityInfo);
		if (citymap != null && citymap.size() > 0) {
			pvMap.put("cityid", citymap.get("areaid"));
		}
		if (provincemap != null && provincemap.size() > 0) {
			pvMap.put("provinceid", provincemap.get("areaid"));
		}
		return pvMap;
	}

}
