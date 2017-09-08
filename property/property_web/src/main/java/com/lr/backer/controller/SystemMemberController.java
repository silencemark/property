package com.lr.backer.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.ExportExcelPoi;
import com.hoheng.util.StringUtil;
import com.lr.backer.redpack.SendMoney;
import com.lr.backer.service.AgencyService;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.MyService;
import com.lr.backer.service.OrderService;
import com.lr.backer.service.SupplierService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.backer.util.Xml2JsonUtil;
import com.lr.backer.vo.UploadUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.weixin.backer.service.MemberService;

@Controller
@RequestMapping("/system/member")
public class SystemMemberController extends BaseController {
	
	private transient static Log log = LogFactory.getLog(SystemMemberController.class);
	@Resource
	MemberService memberService;
	@Resource
	IndexService indexService;
	@Resource
	SupplierService supplierService;
	@Resource
	SystemService systemService;
	@Resource
	OrderService orderService;
	@Resource
	MyService myService;
	@Resource 
	AgencyService agencyService;
	/**
	 * 得到会员记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMemberList")
	public String getMemberList(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid",userid);
		List<Map<String, Object>> rolelist=new ArrayList<Map<String,Object>>();
		rolelist=systemService.getRoleListByUserid(userInfo);
		boolean flag=false;
		for(Map<String, Object> rolemap:rolelist){
			if(String.valueOf(rolemap.get("roleid")).equals(Constants.USER_INVESTMENT)){
				flag=true;
				break;
			}
		}
		if(flag){
			model.addAttribute("zhangshangflag", flag);
		}
		
		if (!isManager(request, userid) && flag==false) {
			
			Map<String, Object> dt = new HashMap<String, Object>();
			dt.put("userid", userid);
			List<Map<String, Object>> agency = this.agencyService.getAgencyList(dt);// 得到当前代理商的代理id
			if(agency!=null && agency.size()>0){
				String agencyid=agency.get(0).get("agencyid")+"";
				memberidlist="";
				String memberids=getAgencyidlist(agencyid);
				String [] sourcezu=memberids.split(",");
				List<String> sourcelist=new ArrayList<String>();
				for(int i=0;i<sourcezu.length;i++){
					sourcelist.add(sourcezu[i]+"");
				}
				map.put("sourcelist",sourcelist);
			}
		}
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = this.memberService.getMemberListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> dataList = this.memberService.getMemberList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/member/member_list";
	}
	
	/**
	 * 修改会员状态
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/memberedit")
	public String memberedit(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		
		this.memberService.updateMember(map);
		
		return "redirect:/system/member/getMemberList";
	}
	
	/**
	 * 会员审核列表
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkMember")
	public String checkMember(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		
		Map<String, Object> userInfo=new HashMap<String, Object>();
		userInfo.put("userid",userid);
		List<Map<String, Object>> rolelist=new ArrayList<Map<String,Object>>();
		rolelist=systemService.getRoleListByUserid(userInfo);
		boolean flag=false;
		for(Map<String, Object> rolemap:rolelist){
			if(String.valueOf(rolemap.get("roleid")).equals(Constants.USER_INVESTMENT)){
				flag=true;
				break;
			}
		}
		if (!isManager(request, userid) && flag==false) {
			
			Map<String, Object> dt = new HashMap<String, Object>();
			dt.put("userid", userid);
			List<Map<String, Object>> agency = this.agencyService.getAgencyList(dt);// 得到当前代理商的代理id
			if(agency!=null && agency.size()>0){
				String agencyid=agency.get(0).get("agencyid")+"";
				memberidlist="";
				String memberids=getAgencyidlist(agencyid);
				String [] sourcezu=memberids.split(",");
				List<String> sourcelist=new ArrayList<String>();
				for(int i=0;i<sourcezu.length;i++){
					sourcelist.add(sourcezu[i]+"");
				}
				map.put("sourcelist",sourcelist);
			}
		}
		
		map.put("t_status", "0");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = this.memberService.getMemberListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> dataList = this.memberService.getMemberList(map);
		for (Map<String, Object> data : dataList) {
			Map<String, Object> idCardMap = new HashMap<String, Object>();
			idCardMap.put("type", "1");
			idCardMap.put("memberid", data.get("memberid"));
			List<Map<String, Object>> idCardImgList = this.memberService.getMemberImgList(idCardMap);
			if (idCardImgList.size() > 0) {
				for (Map<String, Object> data1 : idCardImgList) {
					data1.put("idcardimgurl_show", UploadUtil.downImg(String.valueOf(data1.get("url"))));
				}
				data.put("idCardImgList", idCardImgList);
			}

			data.put("companyimgurl_show", UploadUtil.downImg(String.valueOf(data.get("companyimgurl"))));
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pkey","sendredpackswitch");
		paramMap.put("pstatus", "1");
		List<Map<String, Object>> paramList = systemService
				.getParams(paramMap);
		if(paramList!= null && paramList.size()>0){
			model.addAttribute("switchmap", paramList.get(0));
		}
		
		return "/system/member/member_check_list";
	}
	
	@RequestMapping("/updatesysparam")
	@ResponseBody
	public Map<String, Object> updatesysparam(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		map.put("pkey","sendredpackswitch");
		this.indexService.updatesysparam(map);
		return map;
	}
	
	
	public String getSysParam(String key){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pkey",key);
		paramMap.put("pstatus", "1");
		List<Map<String, Object>> paramList = systemService
				.getParams(paramMap);
		if(paramList!= null && paramList.size()>0){
			return paramList.get(0).get("pvalue")+"";
		}else{
			return "";
		}
	}
	/**
	 * 修改会员审核状态（个人审核 和企业审核）
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeCheckStatus")
	@ResponseBody
	public Map<String, Object> changeCheckStatus(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		String redpackswitch=getSysParam("sendredpackswitch");
		int num=this.orderService.getTradeNumByNow();
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderno="dc"+sdf.format(new Date())+String.format("%05d",(num+1));
		
		if (map.get("flg") != null && !map.get("flg").equals("")) {
			if (map.get("idlist") != null && !map.get("idlist").equals(""))
				;
			String idList[] = map.get("idlist").toString().split(",");
			map.put("idList", idList);
			
			int status=1;
			for(int i=0;i<idList.length;i++){
				Map<String, Object> extendmap=new HashMap<String, Object>();
				extendmap.put("extendid", idList[i]);
				Map<String, Object> openidmap=this.memberService.getMemberByExtendid(extendmap);
				
				if(openidmap.containsKey("openid") && !"".equals(openidmap.get("openid"))){
					
					if(map.containsKey("individualstatus") && "1".equals(String.valueOf(map.get("individualstatus"))) && redpackswitch.equals("1")){
						//送红包
						String price=getSysParam("sendredpack");
						String wishes=getSysParam("wishesredpack");
						String activityname=getSysParam("activitynameredpack");
						try {
							String result=SendMoney.sendRedPack(openidmap.get("openid")+"",price, 1, wishes, activityname);
							Xml2JsonUtil jsonxml=new Xml2JsonUtil();
							boolean flag=jsonxml.isSuccessByxml(result);
							if(flag==false){
								status=0;
							}else{
								//添加支付记录
								Map<String, Object> trademap=new HashMap<String, Object>();
								trademap.put("tradeid",UUID.randomUUID().toString().replace("-", ""));
								trademap.put("incomepay",1);
								trademap.put("amount",price);
								trademap.put("paymethod",2);
								trademap.put("paypurposetype",99);
								trademap.put("pay_userid",openidmap.get("memberid"));
								trademap.put("status",1);
								trademap.put("createtime",new Date());
								trademap.put("orderno", orderno);
								this.orderService.insertTradeRecord(trademap);
							}
						} catch (Exception e) {
							// TODO: handle exception
							log.debug("------------sendredpack error发送红包失败");
							status=0;
						}
					}
					if(map.containsKey("enterprisestatus") && "1".equals(String.valueOf(map.get("enterprisestatus")))){
						//送嘀嗒币
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("pkey", "sendcoin");
						paramMap.put("pstatus", "1");
						List<Map<String, Object>> paramList = systemService
								.getParams(paramMap);
						if(paramList!= null && paramList.size()>0){
							map.put("tickcoin", paramList.get(0).get("pvalue"));
							// 插入滴答币记录表
							Map<String, Object> coinMap = new HashMap<String, Object>();
							coinMap.put("recordid", StringUtil.getUUID());
							coinMap.put("title", "企业认证通过赠送");
							coinMap.put("description", "企业认证通过赠送嘀嗒币");
							coinMap.put("amount", paramList.get(0).get("pvalue"));
							coinMap.put("income_userid", openidmap.get("memberid"));
							coinMap.put("paytime", new Date());
							this.orderService.insertCoinRecord(coinMap);
						}
						
					}
				}
			}
			if(status==1){
				//修改认证状态
				this.memberService.updateExtendPrise(map);
			}else{
				map.put("status", "1");
			}
			
			
		} else {
			
			Map<String, Object> openidmap=this.memberService.getMemberByExtendid(map);
			int status=1;
			if(openidmap.containsKey("openid") && !"".equals(openidmap.get("openid"))){
				if(map.containsKey("individualstatus") && "1".equals(String.valueOf(map.get("individualstatus"))) && redpackswitch.equals("1")){
					//送红包
					String price=getSysParam("sendredpack");
					String wishes=getSysParam("wishesredpack");
					String activityname=getSysParam("activitynameredpack");
					try {
						String result=SendMoney.sendRedPack(openidmap.get("openid")+"",price, 1, wishes, activityname);
						Xml2JsonUtil jsonxml=new Xml2JsonUtil();
						boolean flag=jsonxml.isSuccessByxml(result);
						if(flag==false){
							status=0;
						}else{
							//添加支付记录
							Map<String, Object> trademap=new HashMap<String, Object>();
							trademap.put("tradeid",UUID.randomUUID().toString().replace("-", ""));
							trademap.put("incomepay",1);
							trademap.put("amount",price);
							trademap.put("paymethod",2);
							trademap.put("paypurposetype",99);
							trademap.put("pay_userid",openidmap.get("memberid"));
							trademap.put("status",1);
							trademap.put("createtime",new Date());
							trademap.put("orderno", orderno);
							this.orderService.insertTradeRecord(trademap);
						}
					} catch (Exception e) {
						// TODO: handle exception
						log.debug("------------sendredpack error发送红包失败");
						status=0;
					}
				}
				if(map.containsKey("enterprisestatus") && "1".equals(String.valueOf(map.get("enterprisestatus")))){
					//送嘀嗒币
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pkey", "sendcoin");
					paramMap.put("pstatus", "1");
					List<Map<String, Object>> paramList = systemService
							.getParams(paramMap);
					if(paramList!= null && paramList.size()>0){
						map.put("tickcoin", paramList.get(0).get("pvalue"));
						// 插入滴答币记录表
						Map<String, Object> coinMap = new HashMap<String, Object>();
						coinMap.put("recordid", StringUtil.getUUID());
						coinMap.put("title", "企业认证通过赠送");
						coinMap.put("description", "企业认证通过赠送嘀嗒币");
						coinMap.put("amount", paramList.get(0).get("pvalue"));
						coinMap.put("income_userid", openidmap.get("memberid"));
						coinMap.put("paytime", new Date());
						this.orderService.insertCoinRecord(coinMap);
					}
					
				}
			}
			
			if(status==1){
				//修改认证状态
				this.memberService.updateExtend(map);
			}else{
				map.put("status", "1");
			}
			
		}

		return map;
	}

	/**
	 * 得到用户详情
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMemberDetail")
	public String getMemberDetail(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {

		List<Map<String, Object>> dataList = this.memberService.getMemberList(map);
		Map<String,Object> user = UserUtil.getUser(request);
		if(!this.isManager(request,user.get("userid")+"") && this.isInvestment(request,user.get("userid")+"")){
			for(Map<String, Object> su:dataList){
				if(su.containsKey("phone") && !"".equals(su.get("phone"))){
					String phone=su.get("phone")+"";
					phone=phone.substring(0,3)+"****"+phone.substring(7,phone.length());
					su.put("phone", phone);
				}
			}
		}
		if (dataList.size() > 0) {
			dataList.get(0).put("headimage_show", UploadUtil.downImg(String.valueOf(dataList.get(0).get("headimage"))));
			dataList.get(0).put("companyimgurl_show",
					UploadUtil.downImg(String.valueOf(dataList.get(0).get("companyimgurl"))));
			model.addAttribute("data", dataList.get(0));
			// 身份证照片
			Map<String, Object> idCardMap = new HashMap<String, Object>();
			idCardMap.put("type", "1");
			idCardMap.put("memberid", map.get("memberid"));
			List<Map<String, Object>> idCardImgList = this.memberService.getMemberImgList(idCardMap);
			if (idCardImgList.size() > 0) {
				for (Map<String, Object> data : idCardImgList) {
					data.put("idcardimgurl_show", UploadUtil.downImg(String.valueOf(data.get("url"))));
				}

			}
			model.addAttribute("idCardImgList", idCardImgList);
			// 个人照
			Map<String, Object> personalMap = new HashMap<String, Object>();
			personalMap.put("type", "2");
			personalMap.put("memberid", map.get("memberid"));
			List<Map<String, Object>> personalImgList = this.memberService.getMemberImgList(personalMap);
			if (personalImgList.size() > 0) {
				for (Map<String, Object> data : personalImgList) {
					data.put("personal_show", UploadUtil.downImg(String.valueOf(data.get("url"))));
				}

			}
			model.addAttribute("personalImgList", personalImgList);

		}
		return "/system/member/member_info";
	}

	/**
	 * 得到会员保险购买记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInstanceList")
	public String getInstanceList(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		Map<String,Object> user = UserUtil.getUser(request);
		model.addAttribute("isInvestment",this.isInvestment(request,user.get("userid")+"")+"");
		PageHelper pageHelper = new PageHelper(request);
		int num = this.memberService.getInsuranceListNum(map);
		pageHelper.setTotalCount(num);
		pageHelper.initPage(map);
		List<Map<String,Object>> dataList = this.memberService.getInsuranceList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/member/member_instance_list";
	}

	/**
	 * 修改保险购买记录状态
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateInstance")
	@ResponseBody
	public boolean updateInstance(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		boolean flg = false;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		Map<String, Object> userMap = UserUtil.getUser(request);
		String[] ids = map.get("idList").toString().split(",");
		for (int i = 0; i < ids.length; i++) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("status", "1");
			dataMap.put("updaterid", userMap.get("userid"));
			dataMap.put("updatetime", new Timestamp(System.currentTimeMillis()));
			dataMap.put("insuranceid", ids[i]);
			dataList.add(dataMap);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		this.memberService.updateInsurance(resultMap);
		flg = true;
		return flg;
	}

	/**
	 * 我的二维码
	 * 
	 * 代理商代理二维码
	 * 
	 * @param map
	 * @param model
	 * @param request
	 *            memberid 为t_dt_member 表里的userid
	 * @return
	 */
	@RequestMapping("/myqrcode")
	@ResponseBody
	public Map<String, Object> myqrcode(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取传值的memberid
		String userid = (String) map.get("userid");

		String qrcodeurl = "";
		Map<String, Object> user = null;
		List<Map<String, Object>> list = systemService.getUserList(map);
		if (list != null && list.size() > 0) {

			user = list.get(0);
			qrcodeurl = (String) user.get("ewm");
		} else {
			throw new RuntimeException("未找到当前用户信息！");
		}
		if (StringUtils.isEmpty(qrcodeurl)) {
			String access_token = WeiXinCenterProxy.getAccessToken();
			if (StringUtils.isEmpty(access_token)) {
				throw new RuntimeException("access_token is null");
			}
			qrcodeurl = myService.getWeixinQRcode(userid, request.getRealPath("/qrcode"), access_token);
			if (StringUtils.isNotEmpty(qrcodeurl)) {
				user.put("qrcodeurl", "/qrcode/" + userid + ".jpg");
			}
			// 将二维码信息更新到数据库
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ewm", "/qrcode/" + userid + ".jpg");
			paramMap.put("userid", userid);
			systemService.updateUser(paramMap);
		}
		resultMap.put("user", user);
		resultMap.put("ewm", "/qrcode/" + userid + ".jpg");
		return resultMap;
	}

	/**
	 * 导出会员用户购买保险记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportMemberInstanceRecord")
	@ResponseBody
	public Map<String, Object> exportMemberInstanceRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		List<Map<String, Object>> dataList = this.memberService.getInsuranceList(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人工人保险购买记录.xls");

		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "订单编号", "订单标题", "登录名", "姓名", "身份证号", "手机号", "生成时间", "购买时间", "是否购买" };
		List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
		Map<String, Object> dt = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flg = false;

		if (dataList.size() > 0) {
			try {
				int i = 0;
				for (Map<String, Object> data : dataList) {
					i++;
					dt = new LinkedHashMap<String, Object>();
					dt.put("a", i);
					dt.put("b", data.get("orderno") == null ? "" : data.get("orderno"));
					dt.put("c", data.get("title") == null ? "" : data.get("title"));
					dt.put("d", data.get("username") == null ? "" : data.get("username"));
					dt.put("e", data.get("realname") == null ? "" : data.get("realname"));
					dt.put("f", data.get("idcard") == null ? "" : data.get("idcard"));
					dt.put("g", data.get("phone") == null ? "" : data.get("phone"));
					if (data.get("createtime") != null && !data.get("createtime").equals("")) {
						dt.put("h", sf.format(sf.parse(data.get("createtime").toString())));
					} else {
						dt.put("h", "");
					}
					if (data.get("updatetime") != null && !data.get("updatetime").equals("")) {
						dt.put("i", sf.format(sf.parse(data.get("updatetime").toString())));
					} else {
						dt.put("i", "");
					}

					if (data.get("status").toString().equals("1")) {
						dt.put("j", "是");
					} else {
						dt.put("j", "否");
					}
					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人工人订单保险购买记录", headers, dataset, out);
				out.close();
				System.out.println("帐号excel导出成功！");
				flg = true;
				resultMap.put("msg", "导出成功");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			resultMap.put("msg", "您没有数据可以导出！");
		}
		resultMap.put("flg", flg);
		return resultMap;
	}

	/**
	 * 下载excel
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();

		try {
			return ex.download("滴答叫人工人保险购买记录.xls", response, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 导出会员用户记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportMemberRecord")
	@ResponseBody
	public Map<String, Object> exportMemberRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		List<Map<String, Object>> dataList = this.memberService.getMemberList(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人会员信息记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "姓名", "地区", "手机号", "公司名称", "个人认证状态", "企业认证状态", "发布订单数", "滴滴币余额", "来源", "最后登陆时间","状态"};
		List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
		Map<String, Object> dt = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flg = false;

		if (dataList.size() > 0) {
			try {
				int i = 0;
				for (Map<String, Object> data : dataList) {
					i++;
					dt = new LinkedHashMap<String, Object>();
					dt.put("a", i);
					dt.put("b", data.get("realname") == null ? "" : data.get("realname"));
					Object provincename = data.get("provincename") == null ? "" : data.get("provincename");
					Object cityname = data.get("cityname") == null ? "" : data.get("cityname");
					dt.put("c", provincename + " " + cityname);
					dt.put("d", data.get("phone") == null ? "" : data.get("phone"));
					dt.put("e", data.get("companyname") == null ? "" : data.get("companyname"));
					if (data.get("individualstatus") == null || data.get("individualstatus").toString().equals("0")) {

						dt.put("f", "待审核");
					} else if (data.get("individualstatus").toString().equals("1")) {

						dt.put("f", "审核通过");
					} else if (data.get("individualstatus").toString().equals("2")) {

						dt.put("f", "审核不通过");
					} else {

						dt.put("f", "再次审核");
					}

					if (data.get("enterprisestatus") == null || data.get("enterprisestatus").toString().equals("0")) {

						dt.put("g", "待审核");
					} else if (data.get("enterprisestatus").toString().equals("1")) {

						dt.put("g", "审核通过");
					} else if (data.get("enterprisestatus").toString().equals("2")) {

						dt.put("g", "审核不通过");
					} else {

						dt.put("g", "再次审核");
					}

					dt.put("h", data.get("ordernum") == null ? "" : data.get("ordernum"));
					dt.put("i", data.get("tickcoin") == null ? "0" : data.get("tickcoin"));
					dt.put("j", data.get("agencyname") == null ? "" : data.get("agencyname"));

					if (data.get("lasttime") != null && !data.get("lasttime").equals("")) {
						dt.put("k", sf.format(sf.parse(data.get("lasttime").toString())));
					} else {
						dt.put("k", "");
					}
					if (data.get("status") != null && String.valueOf(data.get("status")).equals("1")) {
						dt.put("l", "已启用");
					} else {
						dt.put("l", "已禁用");
					}

					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人会员信息记录", headers, dataset, out);
				out.close();
				System.out.println("帐号excel导出成功！");
				flg = true;
				resultMap.put("msg", "导出成功");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			resultMap.put("msg", "您没有数据可以导出！");
		}
		resultMap.put("flg", flg);
		return resultMap;
	}

	/**
	 * 下载会员信息excel
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadMemberInfoExcel")
	public ResponseEntity<byte[]> downloadMemberInfoExcel(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();

		try {
			return ex.download("滴答叫人会员信息记录.xls", response, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出佣金记录信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCommissionRecord")
	@ResponseBody
	public Map<String, Object> exportCommissionRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		List<Map<String, Object>> agencysaleList = new ArrayList<Map<String, Object>>();
		Map<String, Object> agencyMap = new HashMap<String, Object>();
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		boolean flag = isManager(request, userid);
		if (flag==false) {
			if (!map.containsKey("salerid")) {
				map.put("salerid", userMap.get("userid"));
			}
		}
		if (map.containsKey("salerid") && "".equals(map.get("salerid")) && map.containsKey("parentid") && "".equals(map.get("parentid"))) {
			map.put("parentid", '0');
		}
		List<Map<String, Object>> dataList = systemService.getCommissionList(map);
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

		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人佣金信息记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "代理商账号", "代理商名称", "代理区域", "上级代理", "会员消费金额", "佣金比例", "获得佣金" };
		List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
		Map<String, Object> dt = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flg = false;

		if (dataList.size() > 0) {
			try {
				int i = 0;
				for (Map<String, Object> data : dataList) {
					i++;
					dt = new LinkedHashMap<String, Object>();
					dt.put("a", i);
					dt.put("b", data.get("username") == null ? "" : data.get("username"));
					dt.put("c", data.get("agencyname") == null ? "" : data.get("agencyname"));
					Object provincename = data.get("provincename") == null ? "" : data.get("provincename");
					Object cityname = data.get("cityname") == null ? "" : data.get("cityname");
					Object districtname = data.get("districtname") == null ? "" : data.get("districtname");
					dt.put("d", provincename + " " + cityname + " " + districtname);
					dt.put("e", data.get("parentid") == null || data.get("parentid").equals("0") ? "无"
							: data.get("parentname"));
					dt.put("f", data.get("amount") == null ? "" : data.get("amount"));
					dt.put("g", data.get("commissionrate") == null ? "" : data.get("commissionrate") + "%");
					dt.put("h", data.get("commissionamount") == null ? "" : data.get("commissionamount"));

					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人佣金信息记录", headers, dataset, out);
				out.close();
				System.out.println("帐号excel导出成功！");
				flg = true;
				resultMap.put("msg", "导出成功");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			resultMap.put("msg", "您没有数据可以导出！");
		}
		resultMap.put("flg", flg);
		return resultMap;
	}

	/**
	 * 下载佣金记录excel
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadCommissionExcel")
	public ResponseEntity<byte[]> downloadCommissionExcel(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();

		try {
			return ex.download("滴答叫人佣金信息记录.xls", response, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
