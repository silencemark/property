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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lr.backer.redis.RedisUtil;
import com.lr.backer.util.paymoneyutil;
import com.hoheng.base.controller.BaseController;
import com.hoheng.thread.MemoryStatic;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.util.StringUtil;
import com.hoheng.vo.PushMessage;
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.EvaluationService;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.MyService;
import com.lr.backer.service.OrderService;
import com.lr.backer.service.SystemService;
import com.lr.backer.service.TradeService;
import com.lr.backer.service.WorkersService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.Md5Util;
import com.lr.backer.util.PageScroll;
import com.lr.backer.util.sendSms;
import com.lr.backer.vo.UploadUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.weixin.backer.service.MemberService;

@Controller
@RequestMapping("/my")
public class MyController extends BaseController {
	private transient static Log log = LogFactory.getLog(MyController.class);

	@Resource
	MyService myService;
	@Resource
	MemberService memberService;
	@Resource
	OrderService orderService;
	@Resource
	EvaluationService evaluationService;
	@Resource
	private SystemService systemService;

	@Resource
	private IndexService indexService;
	@Resource
	private TradeService tradeService;
	@Resource
	private WorkersService workersService;

	@Resource
	private EmployerService employerService;
	/**
	 * 我的中心
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mycenter")
	public String myCenter(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " myCenter start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("memberid", memberid);
		List<Map<String, Object>> list = this.myService.getTickUserList(map);
		if (list != null && list.size() > 0) {
			list.get(0).put("headimage_show",
					UploadUtil.downImg(list.get(0).get("headimage") + ""));
			model.addAttribute("user", list.get(0));
			log.debug(list.get(0));
		}
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)
				+ "");
		log.debug(this.getClass().getSimpleName() + " myCenter end"
				+ System.currentTimeMillis());
		return "/phone/my/mycenter";
	}

	/**
	 * 我的帐户滴嗒币信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mycoin")
	public String mycoin(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " mycoin start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("memberid", memberid);
		List<Map<String, Object>> userlist = myService.getTickUserList(map);
		PageScroll page = new PageScroll();
		int num = this.myService.getcoinRecordNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> coinList = myService.getcoinRecord(map);
		if (userlist != null && userlist.size() > 0) {
			userlist.get(0).put("headimage",
					UploadUtil.downImg(userlist.get(0).get("headimage") + ""));
			model.addAttribute("user", userlist.get(0));
		}
		model.addAttribute("memberid", memberid);
		model.addAttribute("page", page);
		model.addAttribute("coinList", coinList);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)
				+ "");
		log.debug(this.getClass().getSimpleName() + " mycoin end"
				+ System.currentTimeMillis());
		return "/phone/my/mycoin";
	}

	/**
	 * 我的滴答币交易分页
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mycoinAjax")
	@ResponseBody
	public Map<String, Object> mycoinAjax(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.myService.getcoinRecordNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> coinList = myService.getcoinRecord(map);
		dataMap.put("dataList", coinList);
		dataMap.put("page", page);
		return dataMap;
	}

	/**
	 * 我的账户交易记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mypayrecord")
	public String mypayrecord(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " mypayrecord start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("memberid", memberid);
		Map<String, Object> sumMap = myService.getTradeSum(map);
		PageScroll page = new PageScroll();
		int num = this.myService.getTradeRecordNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> list = myService.getTradeRecord(map);
		model.addAttribute("payList", list);
		model.addAttribute("sumMap", sumMap);
		model.addAttribute("page", page);
		model.addAttribute("memberid", memberid);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " mypayrecord end"
				+ System.currentTimeMillis());
		return "/phone/my/mypayrecord";
	}

	/**
	 * 我的账户交易记录 ajax 分页
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mypayrecordAjax")
	@ResponseBody
	public Map<String, Object> mypayrecordAjax(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.myService.getTradeRecordNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> list = myService.getTradeRecord(map);
		dataMap.put("dataList", list);
		dataMap.put("page", page);
		return dataMap;
	}

	/**
	 * 我的二维码
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/myqrcode")
	public String myqrcode(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " myqrcode start"
				+ System.currentTimeMillis());
		//获取传值的memberid
		String memberid = (String) map.get("memberid");
		//当memberid为空时再获取memberid
		if (StringUtils.isEmpty(memberid)) {
			memberid = this.getUserId(request);
			map.put("memberid", memberid);
		}
		
		String qrcodeurl = "";
		Map<String, Object> user = null;
		List<Map<String, Object>> list = myService.getTickUserList(map);
		if (list != null && list.size() > 0) {
			list.get(0).put("headimage",
					UploadUtil.downImg(list.get(0).get("headimage") + ""));
			user = list.get(0);
			qrcodeurl = (String) user.get("qrcodeurl");
		} else {
			throw new RuntimeException("未找到当前用户信息！");
		}
		if (StringUtils.isEmpty(qrcodeurl)) {
			String access_token = WeiXinCenterProxy.getAccessToken();
			if (StringUtils.isEmpty(access_token)) {
				throw new RuntimeException("access_token is null");
			}
			qrcodeurl = myService.getWeixinQRcode(memberid,
					request.getRealPath("/qrcode"), access_token);
			if (StringUtils.isNotEmpty(qrcodeurl)) {
				user.put("qrcodeurl", "/qrcode/" + memberid + ".jpg");
			}
			// 将二维码信息更新到数据库
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("qrcodeurl", "/qrcode/" + memberid + ".jpg");
			paramMap.put("extendid", user.get("extendid"));
			memberService.updateExtend(paramMap);
		}

		model.addAttribute("user", user);
		model.addAttribute("qrcodeurl", qrcodeurl);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " myqrcode end"
				+ System.currentTimeMillis());
		return "/phone/my/myqrcode";
	}

	/**
	 * 我的关注
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/myattention")
	public String myattention(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " myattention start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("memberid", memberid);
		PageScroll page = new PageScroll();
		int num = this.myService.getAttentionMembersNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> list = myService.getAttentionMembers(map);
		for (Map<String, Object> data : list) {
			data.put("headimage_show",UploadUtil.downImg(data.get("headimage") + ""));
		}
		model.addAttribute("attentions", list);
		model.addAttribute("memberid", memberid);
		model.addAttribute("page", page);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " myattention end"
				+ System.currentTimeMillis());
		return "/phone/my/myattention";
	}

	/**
	 * 我的关注ajax 分页
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/myattentionAjax")
	@ResponseBody
	public Map<String, Object> myattentionAjax(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.myService.getAttentionMembersNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		List<Map<String, Object>> list = myService.getAttentionMembers(map);
		for (Map<String, Object> data : list) {
			data.put("headimage",
					UploadUtil.downImg(data.get("headimage") + ""));
		}
		dataMap.put("dataList", list);
		dataMap.put("page", page);
		return dataMap;
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goupdatepwd")
	public String goupdatepwd(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " goupdatepwd start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("memberid", memberid);
		List<Map<String, Object>> list = myService.getTickUserList(map);
		if (list != null && list.size() > 0) {
			model.addAttribute("user", list.get(0));
		}
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " goupdatepwd end"
				+ System.currentTimeMillis());
		return "/phone/my/updatepwd";
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getvalidcode")
	@ResponseBody
	public String getvalidcode(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " myattention start"
				+ System.currentTimeMillis());
		
		String code = map.get("code")+"";
		Map<String, Object> codemap=new HashMap<String, Object>();
		codemap.put("userid", this.getUserId(request));
		codemap.put("verifycode", code);
		this.memberService.updateExtend(codemap);
		// 发送验证码
		try {
			request.getSession().setAttribute("phonecode", code);
			sendSms.sendSms1(map.get("phone")+"", code);
		} catch (Exception e) {
		}
		log.debug(this.getClass().getSimpleName() + " myattention end"
				+ System.currentTimeMillis());
		return code;
	}

	/**
	 * 修改密码
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatepwd")
	@ResponseBody
	public Map<String, Object> updatepwd(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " updatepwd start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		String password = (String) map.get("password");
		if (StringUtils.isEmpty(password)) {
			map.put("success", false);
			return map;
		}
		map.put("memberid", memberid);
		map.put("password", Md5Util.getMD5(password));
		try {
			memberService.updateMember(map);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		log.debug(this.getClass().getSimpleName() + " updatepwd end"
				+ System.currentTimeMillis());
		return map;
	}

	/**
	 * 详细信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/detailinfo")
	public String detailinfo(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " detailinfo start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);// 当前用户
		Object userid = map.get("userid");// 进入用户 被关注人
		boolean ifattention = false;
		map.put("memberid", memberid);
		// 查询是否有被关注
		if (userid != null && !userid.equals("")) {// 如果传了userid
			map.put("userid", userid);
			List<Map<String, Object>> attentions = myService.getAttention(map);
			if (attentions != null && attentions.size() > 0) {
				ifattention = true;
				model.addAttribute("attentionid", attentions.get(0).get("attentionid"));
			}
			map.put("memberid", userid);// 覆盖当前用户id
			model.addAttribute("isself", "no");
		
		}
		map.put("status_det", "3");
		map.put("applicantid", map.get("memberid"));
		map.put("publisherid_det", map.get("memberid"));
		model.addAttribute("ifattention", ifattention);
		setDetailInfo(map, model, request);
		log.debug(this.getClass().getSimpleName() + " detailinfo end"
				+ System.currentTimeMillis());
		return "/phone/my/detailinfo";
	}

	/**
	 * 关注
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/attention")
	@ResponseBody
	public Map<String, Object> attention(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " attention start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		map.put("attentionerid", memberid);
		map.put("attentionid", UUID.randomUUID().toString().replace("-", ""));
		map.put("createtime", new Timestamp(System.currentTimeMillis()));
		String userid = (String) map.get("userid");
		if (memberid.equals(userid)) {
			map.put("success", false);
			map.put("errorMsg", "无需关注自己！");
			return map;
		}

		try {
			myService.insertAttention(map);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorMsg", "系统异常：" + e.getMessage());
		}
		log.debug(this.getClass().getSimpleName() + " attention end"
				+ System.currentTimeMillis());
		return map;
	}

	/**
	 * 取消关注
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelAttention")
	@ResponseBody
	public boolean cancelAttention(@RequestParam Map<String, Object> map,Model model, HttpServletRequest request) {
	     boolean flg= false;
	     this.myService.cancelAttention(map);
	     flg =true;
		return flg; 
	}
	
	
	/**
	 * 详细信息评价
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goEvaluate")
	public String goEvaluate(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		map.put("userid", map.get("memberid"));
		map.put("status", "0");
		PageScroll page = new PageScroll();
		int num = this.evaluationService.getEevaluationsNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		// 查询评价信息
		List<Map<String, Object>> list = evaluationService.getEvaluations(map);
		for (Map<String, Object> data : list) {
			data.put("headimage_show",
					UploadUtil.downImg(data.get("headimage") + ""));
		}
		// 获取评价是统计信息

		setDetailInfo(map, model, request);
		model.addAttribute("evaluations", list);
		model.addAttribute("map", map);
		model.addAttribute("page", page);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));

		return "/phone/my/evaluate";
	}

	@RequestMapping("/goEvaluateAjax")
	@ResponseBody
	public Map<String, Object> goEvaluateAjax(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		map.put("userid", map.get("memberid"));
		map.put("status", "0");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.evaluationService.getEevaluationsNum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		// 查询评价信息
		List<Map<String, Object>> list = evaluationService.getEvaluations(map);
		for (Map<String, Object> data : list) {
			data.put("headimage_show",
					UploadUtil.downImg(data.get("headimage") + ""));
		}
		// 获取评价是统计信息
		dataMap.put("dataList", list);
		dataMap.put("page", page);
		return dataMap;
	}

	/**
	 * 雇主查看工人详细信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goWorkmandetail")
	public String goWorkmandetail(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " workmandetail start"
				+ System.currentTimeMillis());
		// 当前登录人员ID
		String memberid = this.getUserId(request);
		// 工人id
		String userid = map.get("userid") + "";
		// 订单id
		String orderid = map.get("orderid") + "";
		// 申请订单id
		String applyorderid = map.get("applyorderid") + "";
		// 查询设计用户相关信息开始
		boolean ifattention = false;
		map.put("memberid", memberid);
		// 查询是否有被关注
		if (userid != null && !userid.equals("")) {
			map.put("userid", userid);
			List<Map<String, Object>> attentions = myService.getAttention(map);
			if (attentions != null && attentions.size() > 0) {
				ifattention = true;
				model.addAttribute("attentionid", attentions.get(0).get("attentionid"));
			}
			map.put("memberid", userid);// 覆盖用户id
		}
		map.put("status_det", "3");
		map.put("applicantid", map.get("memberid"));
		map.put("publisherid_det", map.get("memberid"));
		model.addAttribute("ifattention", ifattention);
		
		Map<String, Object> ordermap=new HashMap<String, Object>();
		ordermap.put("orderid", orderid);
		ordermap=this.employerService.getOrderInfo(ordermap);
		if(ordermap.containsKey("jobid")){
			map.put("jobid", ordermap.get("jobid"));
		}
		if(ordermap.containsKey("projectid")){
			map.put("projectid", ordermap.get("projectid"));
		}
		model.addAttribute("map", map);
		model = setDetailInfo(map, model, request);
		// 查询设计用户相关信息结束
		// 查询申请订单及关联的发布订单信息
		
		map.put("applyorderid", applyorderid);

		List<Map<String, Object>> applyOrders = myService.getApplyOrderAndOrder(map);

		if (applyOrders != null && applyOrders.size() > 0) {
			model.addAttribute("applyorder", applyOrders.get(0));
			
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pcategory", Constants.PARAMCATEGORY_EMPLOYER);
		paramMap.put("pkey", Constants.PARAMKEY_BROKERAGE);
		List<Map<String, Object>> params = systemService.getParams(paramMap);
		if (params != null && params.size() > 0) {
			model.addAttribute("amount", params.get(0).get("pvalue"));
		}
		log.debug(this.getClass().getSimpleName() + " workmandetail end"
				+ System.currentTimeMillis());
		return "/phone/my/workmandetail";
	}

	/**
	 * 雇主查看工人详细评价
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goWorkmanevaluate")
	public String goWorkmanevaluate(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " goWorkmanevaluate start"
				+ System.currentTimeMillis());
		String memberid = this.getUserId(request);
		String userid = (String) map.get("userid");
		// 申请订单id
		String applyorderid = (String) map.get("applyorderid");
		Boolean ifattention = null;
		map.put("memberid", memberid);
		// 获取用户详情
		// 查询是否有被关注
		if (StringUtils.isNotBlank(userid)) {
			map.put("userid", userid);
			List<Map<String, Object>> attentions = myService.getAttention(map);
			if (attentions != null && attentions.size() > 0) {
				ifattention = true;
			} else {
				ifattention = false;
			}
		} else {
			// 当userid为空时则表示，当前用户查看自己的详细评价信息
			map.put("userid", memberid);
		}
		// 查询申请订单及关联发布订单信息
		map.put("applyorderid", applyorderid);
		List<Map<String, Object>> applyOrders = myService
				.getApplyOrderAndOrder(map);
		if (applyOrders != null && applyOrders.size() > 0) {
			model.addAttribute("applyorder", applyOrders.get(0));
		}
		// 查询评价信息
		List<Map<String, Object>> list = evaluationService.getEvaluations(map);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("headimage",
					UploadUtil.downImg(list.get(i).get("headimage") + ""));
			list.get(i).put("uheadimage",
					UploadUtil.downImg(list.get(i).get("uheadimage") + ""));
		}
		// 获取评价是统计信息
		Map<String, Object> statistics = evaluationService
				.getEvaStatistics(map);
		// 获取用户详情
		List<Map<String, Object>> users = myService.getMemberDetail(map);
		if (users != null && users.size() > 0) {
			users.get(0).put("headimage",
					UploadUtil.downImg(users.get(0).get("headimage") + ""));
			model.addAttribute("user", users.get(0));
		}

		model.addAttribute("evaluations", list);
		model.addAttribute("statistics", statistics);
		model.addAttribute("ifattention", ifattention);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " goWorkmanevaluate end"
				+ System.currentTimeMillis());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pcategory", Constants.PARAMCATEGORY_EMPLOYER);
		paramMap.put("pkey", Constants.PARAMKEY_BROKERAGE);
		List<Map<String, Object>> params = systemService.getParams(paramMap);
		if (params != null && params.size() > 0) {
			model.addAttribute("amount", params.get(0).get("pvalue"));
		}
		return "/phone/my/workmanevaluate";
	}

	/**
	 * 设置详细信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param ifattention
	 * @return
	 */
	private Model setDetailInfo(Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 查询用户抢单数

		Map<String, Object> applyCount = orderService.getApplyOrderCount(map);
		// 获取用户发布订单数
		Map<String, Object> orderCount = orderService.getOrderCount(map);
		// 获取用户详情
		List<Map<String, Object>> list = myService.getMemberDetail(map);
		if (list != null && list.size() > 0) {
			list.get(0).put("headimage_show",
					UploadUtil.downImg(list.get(0).get("headimage") + ""));
			model.addAttribute("user", list.get(0));
		}
		
		//个人照
		 Map<String,Object> personalMap = new HashMap<String,Object>();
		 personalMap.put("type", "2");
		 personalMap.put("memberid", map.get("memberid"));
		 List<Map<String,Object>> personalImgList = this.memberService.getMemberImgList(personalMap);
		 if(personalImgList.size()>0){
			 for(Map<String,Object> data : personalImgList){
				 data.put("personal_show", UploadUtil.downImg(String.valueOf(data.get("url"))));	 
			 }
			 
		 }
		 personalMap.put("type", "1");
		 List<Map<String,Object>> idCardImgList = this.memberService.getMemberImgList(personalMap);
		 if(idCardImgList.size()>0){
			 for(Map<String,Object> data : idCardImgList){
				 data.put("idCard_show", UploadUtil.downImg(String.valueOf(data.get("url"))));	 
			 }
			 
		 }
		model.addAttribute("idCardImgList", idCardImgList); 
		model.addAttribute("personalImgList", personalImgList);
		model.addAttribute("applynum", applyCount);
		model.addAttribute("ordernum", orderCount);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		return model;
	}

	@RequestMapping("/updateApplyOrderStatus")
	@ResponseBody
	public Map<String, Object> updateApplyOrderStatus(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName()
				+ " updateApplyOrderStatus start" + System.currentTimeMillis());
		// 获取修改申请订单状态值
		String status = (String) map.get("status");
		Integer surplusNum = 0;
		try {
			// 判断状态值是否为空，如果状态值为空则返回参数异常
			if (StringUtils.isEmpty(status)) {
				map.put("success", false);
				map.put("msg", " param error status is empty");
				return map;
			}
			// 确认时判断 招工或项目招聘人数是否已达到上限，是则返回提示
			
			Map<String, Object> applyorderinfo=new HashMap<String, Object>();
			applyorderinfo.put("applyorderid", map.get("applyorderid"));
			applyorderinfo=workersService.getApplyOrder(applyorderinfo);
			
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", applyorderinfo.get("orderpublisherid"));
			membermap=this.indexService.getMemberInfo(membermap);
			
			Map<String, Object> membermap1=new HashMap<String, Object>();
			membermap1.put("memberid", applyorderinfo.get("applicantid"));
			membermap1=this.indexService.getMemberInfo(membermap1);
			String title="";
			String errortitle="";
			String content="";
			String errorcontent="";
			if(applyorderinfo.containsKey("jobid") && !applyorderinfo.get("jobid").equals("")){
				Map<String, Object> jobinfo=this.workersService.getJobInfo(map);
				title="恭喜您，抢单成功";
				errortitle="很遗憾，抢单失败";
				content="恭喜您，您申请的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"已同意!";
				errorcontent="很遗憾，您申请的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"认为您暂不合适!";
			}
			if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
				Map<String, Object> projectinfo=this.workersService.getProjectInfo(map);
				title="恭喜您，抢单成功";
				errortitle="很遗憾，抢单失败";
				title="您有一个项目信息";
				content="恭喜您，您申请的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"已同意!";
				errorcontent="很遗憾，您申请的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"认为您暂不合适!";
			}
			
			List<PushMessage> pushlist=new ArrayList<PushMessage>();
			
			if (status.equals("3")) {
				surplusNum = myService.getSurplusAppNum(map);
				if (surplusNum <= 0) {
					map.put("success", false);
					map.put("msg", "确认人数已达到上限。");
					return map;
				}
				/*if (surplusNum == 1) {
					Map<String, Object> paramMap = map;
					paramMap.put("status", 2);
					orderService.updateOrderStatus(paramMap);
				}*/
				//微信信息 搁置
				String remark="温馨提示：请及时前往处理";
				String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=2";
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
				messagemap.put("type", 3);
				messagemap.put("url", calbackurl);
				
				message.setEntryMap(messagemap);
				pushlist.add(message);
				
				//this.employerService.insertprojectMessage(messagemap);
				
			}else{
				
				String remark="温馨提示：请及时前往处理";
				String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=2";
				String fromname="嘀嗒叫人";
				
				PushMessage message=new PushMessage();
				message.setBaiduChainId(membermap1.get("channelid")+"");
				message.setContent(errorcontent);
				message.setFromname(fromname);
				message.setOpenId(membermap1.get("openid")+"");
				message.setRemark(remark);
				message.setTitle(errortitle);
				message.setUrl(calbackurl);
				
				//weixinManage manage=new weixinManage();
				//manage.sendMassage(membermap1.get("openid")+"", calbackurl, errortitle, errorcontent, remark, fromname);
				//app信息搁置 
				
				//添加推送信息
				Map<String, Object> messagemap=new HashMap<String, Object>();
				messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
				messagemap.put("orderid", map.get("orderid"));
				messagemap.put("title",errortitle);
				messagemap.put("content",errorcontent);
				messagemap.put("createtime",new Date());
				messagemap.put("memberid", membermap1.get("memberid"));
				messagemap.put("type",4);
				messagemap.put("url", calbackurl);
				
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
			
			myService.updateApplyOrderStatus(map);
			if (status.equals("3")) {
				//发送信息提示用户订单已被抢
				Map<String, Object> ordermap=new HashMap<String, Object>();
				ordermap.put("orderid", map.get("orderid"));
				List<String> statuslist=new ArrayList<String>();
				statuslist.add("1");
				statuslist.add("5");
				statuslist.add("7");
				statuslist.add("8");
				statuslist.add("10");
				ordermap.put("statuslist",statuslist);
				sendMessage(ordermap);
			}
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		log.debug(this.getClass().getSimpleName()
				+ " updateApplyOrderStatus end" + System.currentTimeMillis());

		return map;

	}

	/**
	 * 测试方法
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendmessage")
	@ResponseBody
	public Map<String, Object> sendmessage(@RequestParam Map<String, Object> map, Model model,HttpServletRequest request) {
		Map<String, Object> ordermap=new HashMap<String, Object>();
		ordermap.put("orderid", map.get("orderid"));
		List<String> statuslist=new ArrayList<String>();
		statuslist.add("1");
		statuslist.add("5");
		statuslist.add("7");
		statuslist.add("8");
		statuslist.add("10");
		ordermap.put("statuslist",statuslist);
		sendMessage(ordermap);
		return new HashMap<String, Object>();
	}
	/**
	 * 发送信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	public void sendMessage(Map<String, Object> map){
		//查询所有此订单下的 抢单人（未成交的）
		List<Map<String, Object>> appleorderlist=this.workersService.getApplyOrderList(map);
		log.info("appleorderlist----------"+appleorderlist);
		List<PushMessage> pushlist=new ArrayList<PushMessage>();
		for(Map<String, Object> appleorder:appleorderlist){
			String title="";
			String content="";

			if(appleorder.containsKey("jobid") && !"".equals(appleorder.get("jobid"))){
				Map<String, Object> jobmap=new HashMap<String, Object>();
				jobmap.put("jobid", appleorder.get("jobid"));
				jobmap=this.workersService.getJobInfo(jobmap);
				
				title="很遗憾，抢单失败";
				content="很遗憾，您申请的招工："+jobmap.get("jobtitle")+",订单已被抢走!";
			}else if(appleorder.containsKey("projectid") && !"".equals(appleorder.get("projectid"))){
				Map<String, Object> projectmap=new HashMap<String, Object>();
				projectmap.put("projectid", appleorder.get("projectid"));
				projectmap=this.workersService.getProjectInfo(projectmap);
				
				title="很遗憾，抢单失败";
				content="很遗憾，您申请的项目："+projectmap.get("projecttitle")+",项目已被抢走!";
			}
			
			String remark="温馨提示：请及时前往处理";
			String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=2";
			String fromname="嘀嗒叫人";
			
			PushMessage message=new PushMessage();
			message.setBaiduChainId(appleorder.get("channelid")+"");
			message.setContent(content);
			message.setFromname(fromname);
			message.setOpenId(appleorder.get("openid")+"");
			message.setRemark(remark);
			message.setTitle(title);
			message.setUrl(calbackurl);
			
			//weixinManage manage=new weixinManage();
			//manage.sendMassage(membermap1.get("openid")+"", calbackurl, errortitle, errorcontent, remark, fromname);
			//app信息搁置 
			
			//添加推送信息
			Map<String, Object> messagemap=new HashMap<String, Object>();
			messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
			messagemap.put("orderid", map.get("orderid"));
			messagemap.put("title",title);
			messagemap.put("content",content);
			messagemap.put("createtime",new Date());
			messagemap.put("memberid", appleorder.get("memberid"));
			messagemap.put("type",7);
			messagemap.put("url", calbackurl);
			
			message.setEntryMap(messagemap);
			pushlist.add(message);
			
			
			//更改抢单状态
			Map<String, Object> applyordermap=new HashMap<String, Object>();
			applyordermap.put("applyorderid",appleorder.get("applyorderid"));
			applyordermap.put("status",12);
			this.employerService.updateApplyOrderinfo(applyordermap);
		}
		
		try {
			MemoryStatic.pushMsgQueue.put(pushlist);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 商务合作
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/cooperation")
	public String cooperation(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName() + " cooperation start"
				+ System.currentTimeMillis());
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));
		log.debug(this.getClass().getSimpleName() + " myqrcode end"
				+ System.currentTimeMillis());
		return "/phone/my/cooperation";
	}

	/**
	 * 初始化支付佣金页面
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		Map<String, Object> memberMap=new HashMap<String, Object>();
		memberMap.put("memberid",getUserId(request));
		memberMap=this.indexService.getMemberInfo(memberMap);
		if(HttpHeaderUtil.isWeiXinFrom(request)){
			orderMap.put("openid", memberMap.get("openid")+"");
		}
		orderMap.put("orderid", map.get("orderid"));
		orderMap.put("applyorderid", map.get("applyorderid"));
		orderMap.put("price", map.get("amount"));
		orderMap.put("applicantid", map.get("applicantid"));
		orderMap.put("publisherid", getUserId(request));
		orderMap.put("notuify_url", Constants.PROJECT_PATH+ "pay/payIosResult");
		paymoneyutil pay = new paymoneyutil();
		Map<String, Object> payMap = pay.payMoney(request, response, orderMap);
		if(HttpHeaderUtil.isWeiXinFrom(request)){
			if (payMap == null || payMap.size() == 0) {
				return "redirect:/index.jsp";
			}
			model.addAttribute("payMap", payMap);
			orderMap.put("out_trade_no", payMap.get("out_trade_no"));
		}
		
		orderMap.put("successurl","/my/goWorkmandetail?orderid=" + map.get("orderid")
						+ "&userid=" + map.get("applicantid")
						+ "&applyorderid=" + map.get("applyorderid"));
		
		Map<String, Object> appSignMap=pay.getAppSign(request, response, orderMap);
		model.addAttribute("appSignMap",appSignMap);
		
		if(HttpHeaderUtil.isIOSFrom(request) || HttpHeaderUtil.isAndroidFrom(request)){
			RedisUtil.setMap("orderMap" + appSignMap.get("out_trade_no"), orderMap);
		}else{
			RedisUtil.setMap("orderMap" + getUserId(request), orderMap);
		}

		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));

		Map<String, Object> membermap = new HashMap<String, Object>();
		membermap.put("memberid", this.getUserId(request));
		membermap = this.indexService.getMemberInfo(membermap);
		model.addAttribute("membermap", membermap);
		model.addAttribute("map", map);
		
		Map<String, Object> ordermap=new HashMap<String, Object>();
		ordermap.put("orderid", map.get("orderid"));
		ordermap=employerService.getOrderInfo(ordermap);
		model.addAttribute("orderno", "WX"+orderMap.get("orderno"));
		model.addAttribute("partnerid", Constants.PARTNER);

		model.addAttribute("successurl", orderMap.get("successurl"));
		return "/phone/my/paydeposit";
	}
    
	
	/**
	 * 支付成功
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/paysuccess")
	public String paysuccess(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> orderMap = RedisUtil.getMap("orderMap"
				+ map.get("out_trade_no"));
		
		//修改当前查看人的订单状态（雇主是否 已经付款）
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("ifpay", "1");
		appMap.put("applyorderid", orderMap.get("applyorderid"));
		this.orderService.updateApplyOrderStatus(appMap);
		
		//保存保险记录
		Map<String, Object> insurinceMap = new HashMap<String, Object>();
		insurinceMap.put("insuranceid", StringUtil.getUUID());
		insurinceMap.put("orderid", orderMap.get("orderid"));
		insurinceMap.put("memberid", orderMap.get("applicantid"));
		insurinceMap.put("status","0");
		insurinceMap.put("createtime",new Timestamp(System.currentTimeMillis())); 
		this.tradeService.insertInsurance(insurinceMap);
		// 保存交易记录
		
		String successurl = orderMap.get("successurl") + "";
		orderMap.put("memberid", getUserId(request));
		orderMap.put("type", map.get("type"));
		this.orderService.savePayRecord(orderMap);
		RedisUtil.remove("orderMap" + getOpenId(request));
		return "redirect:" + successurl;
	}
	
	
	/**
	 * 前往支付页面
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/payclinch")
	public String payclinch(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		
		Map<String, Object> memberMap=new HashMap<String, Object>();
		memberMap.put("memberid",getUserId(request));
		memberMap=this.indexService.getMemberInfo(memberMap);
		if(HttpHeaderUtil.isWeiXinFrom(request)){
			orderMap.put("openid", memberMap.get("openid")+"");
		}
		orderMap.put("orderid", map.get("orderid"));
		orderMap.put("applyorderid", map.get("applyorderid"));
		orderMap.put("price", map.get("amount"));
		orderMap.put("applicantid", map.get("applicantid"));
		orderMap.put("publisherid", getUserId(request));
		orderMap.put("notuify_url",  Constants.PROJECT_PATH + "pay/payIosResult");
		paymoneyutil pay = new paymoneyutil();
		Map<String, Object> payMap = pay.payMoney(request, response, orderMap);
		if(HttpHeaderUtil.isWeiXinFrom(request)){
			if (payMap == null || payMap.size() == 0) {
				return "redirect:/index.jsp";
			}
			model.addAttribute("payMap", payMap);
			orderMap.put("out_trade_no", payMap.get("out_trade_no"));
		}
		orderMap.put("successurl",
				"/my/updateApplyOrderSuccessStatus?orderid=" + map.get("orderid")
						+ "&status=3"
						+ "&applyorderid=" + map.get("applyorderid"));
		

		Map<String, Object> appSignMap=pay.getAppSign(request, response, orderMap);
		model.addAttribute("appSignMap",appSignMap);
		
		if(HttpHeaderUtil.isIOSFrom(request) || HttpHeaderUtil.isAndroidFrom(request)){
			RedisUtil.setMap("orderMap" + appSignMap.get("out_trade_no"), orderMap);
		}else{
			RedisUtil.setMap("orderMap" + getUserId(request), orderMap);
		}
		
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request));

		Map<String, Object> membermap = new HashMap<String, Object>();
		membermap.put("memberid", this.getUserId(request));
		membermap = this.indexService.getMemberInfo(membermap);
		model.addAttribute("membermap", membermap);
		model.addAttribute("map", map);
		
		Map<String, Object> ordermap=new HashMap<String, Object>();
		ordermap.put("orderid", map.get("orderid"));
		ordermap=employerService.getOrderInfo(ordermap);
		model.addAttribute("orderno", "WX"+orderMap.get("orderno"));
		model.addAttribute("partnerid", Constants.PARTNER);
		
		model.addAttribute("successurl", orderMap.get("successurl"));
		
		return "/phone/my/payclinchdeposit";
	}
	
	@RequestMapping("/payclinchdeposit")
	public String payclinchdeposit(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> orderMap = RedisUtil.getMap("orderMap"
				+ map.get("out_trade_no"));
		
		//修改当前查看人的订单状态（雇主是否 已经付款）
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("ifpay", "1");
		appMap.put("applyorderid", orderMap.get("applyorderid"));
		this.orderService.updateApplyOrderStatus(appMap);
		
		//保存保险记录
		Map<String, Object> insurinceMap = new HashMap<String, Object>();
		insurinceMap.put("insuranceid", StringUtil.getUUID());
		insurinceMap.put("orderid", orderMap.get("orderid"));
		insurinceMap.put("memberid", orderMap.get("applicantid"));
		insurinceMap.put("status","0");
		insurinceMap.put("createtime",new Timestamp(System.currentTimeMillis())); 
		this.tradeService.insertInsurance(insurinceMap);
		// 保存交易记录
		
		String successurl = orderMap.get("successurl") + "";
		orderMap.put("memberid", getUserId(request));
		orderMap.put("type", map.get("type"));
		this.orderService.savePayRecord(orderMap);
		RedisUtil.remove("orderMap" + getOpenId(request));
		return "redirect:" + successurl;
	}
	@RequestMapping("/updateApplyOrderSuccessStatus")
	public String updateApplyOrderSuccessStatus(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		log.debug(this.getClass().getSimpleName()
				+ " updateApplyOrderStatus start" + System.currentTimeMillis());
		// 获取修改申请订单状态值
		String status = (String) map.get("status");
		Integer surplusNum = 0;
		Map<String, Object> applyorderinfo=new HashMap<String, Object>();
		try {
			// 判断状态值是否为空，如果状态值为空则返回参数异常
			
			// 确认时判断 招工或项目招聘人数是否已达到上限，是则返回提示
			
			
			applyorderinfo.put("applyorderid", map.get("applyorderid"));
			applyorderinfo=workersService.getApplyOrder(applyorderinfo);
			
			if (StringUtils.isEmpty(status)) {
				map.put("success", false);
				map.put("msg", " param error status is empty");
				if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
					return "redirect:/employer/releasemapinfo?projectid="+applyorderinfo.get("projectid")+"&orderid="+map.get("orderid");
				}else{
					return "redirect:/employer/workermapinfo?jobid="+applyorderinfo.get("jobid")+"&orderid="+map.get("orderid");
				}
			}
			
			Map<String, Object> membermap=new HashMap<String, Object>();
			membermap.put("memberid", applyorderinfo.get("orderpublisherid"));
			membermap=this.indexService.getMemberInfo(membermap);
			
			Map<String, Object> membermap1=new HashMap<String, Object>();
			membermap1.put("memberid", applyorderinfo.get("applicantid"));
			membermap1=this.indexService.getMemberInfo(membermap1);
			String title="";
			String errortitle="";
			String content="";
			String errorcontent="";
			if(applyorderinfo.containsKey("jobid") && !applyorderinfo.get("jobid").equals("")){
				Map<String, Object> jobinfo=this.workersService.getJobInfo(map);
				title="恭喜您，抢单成功";
				errortitle="很遗憾，抢单失败";
				content="恭喜您，您申请的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"已同意!";
				errorcontent="很遗憾，您申请的招工："+jobinfo.get("jobtitle")+",雇主"+membermap.get("realname")+"认为您暂不合适!";
			}
			if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
				Map<String, Object> projectinfo=this.workersService.getProjectInfo(map);
				title="恭喜您，抢单成功";
				errortitle="很遗憾，抢单失败";
				title="您有一个项目信息";
				content="恭喜您，您申请的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"已同意!";
				errorcontent="很遗憾，您申请的项目："+projectinfo.get("projecttitle")+",雇主"+membermap.get("realname")+"认为您暂不合适!";
			}
			List<PushMessage> pushlist=new ArrayList<PushMessage>();
			if (status.equals("3")) {
				surplusNum = myService.getSurplusAppNum(map);
				if (surplusNum <= 0) {
					map.put("success", false);
					map.put("msg", "确认人数已达到上限。");
					if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
						return "redirect:/employer/releasemapinfo?projectid="+applyorderinfo.get("projectid")+"&orderid="+map.get("orderid");
					}else{
						return "redirect:/employer/workermapinfo?jobid="+applyorderinfo.get("jobid")+"&orderid="+map.get("orderid");
					}
				}
				if (surplusNum == 1) {
					Map<String, Object> paramMap = map;
					paramMap.put("status", 2);
					orderService.updateOrderStatus(paramMap);
				}
				//微信信息 搁置
				String remark="温馨提示：请及时前往处理";
				String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=2";
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
				messagemap.put("type", 3);
				messagemap.put("url", calbackurl);
				
				message.setEntryMap(messagemap);
				pushlist.add(message);
				
				//this.employerService.insertprojectMessage(messagemap);
				
			}else{
				
				String remark="温馨提示：请及时前往处理";
				String calbackurl=Constants.PROJECT_PATH+"/order/getEmployeeOrderList?type=2";
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
				//manage.sendMassage(membermap1.get("openid")+"", calbackurl, errortitle, errorcontent, remark, fromname);
				//app信息搁置 
				
				//添加推送信息
				Map<String, Object> messagemap=new HashMap<String, Object>();
				messagemap.put("businessid", UUID.randomUUID().toString().replace("-", ""));
				messagemap.put("orderid", map.get("orderid"));
				messagemap.put("title",errortitle);
				messagemap.put("content",errorcontent);
				messagemap.put("createtime",new Date());
				messagemap.put("memberid", membermap1.get("memberid"));
				messagemap.put("type",4);
				messagemap.put("url", calbackurl);
				
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
			map.put("status", 3);
			myService.updateApplyOrderStatus(map);

			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		log.debug(this.getClass().getSimpleName()
				+ " updateApplyOrderStatus end" + System.currentTimeMillis());
		if(applyorderinfo.containsKey("projectid") && !applyorderinfo.get("projectid").equals("")){
			return "redirect:/employer/releasemapinfo?projectid="+applyorderinfo.get("projectid")+"&orderid="+map.get("orderid");
		}else{
			return "redirect:/employer/workermapinfo?jobid="+applyorderinfo.get("jobid")+"&orderid="+map.get("orderid");
		}
	}
}
