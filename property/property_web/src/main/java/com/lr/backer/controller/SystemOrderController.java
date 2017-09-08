package com.lr.backer.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.lr.backer.service.EmployerService;
import com.lr.backer.service.EvaluationService;
import com.lr.backer.service.OrderService;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.backer.vo.UploadUtil;

@Controller
@RequestMapping("/system/order")
public class SystemOrderController extends BaseController {

	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(SystemOrderController.class);

	@Resource
	OrderService orderService;
	@Resource
	EvaluationService evaluationService;

	@Resource
	EmployerService employerService;

	/**
	 * 发布订单列表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/releaseorder")
	public String releaseOrder(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = orderService.getReleaseOrderCount(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> orderList = orderService.getReleaseOrderList(map);
		model.addAttribute("orderList", orderList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/release_order";
	}

	/**
	 * 发布订单详情列表
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/releaseorderDetail")
	public String releaseorderDetail(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		if (map.get("type") == null) {
			// 临时工订单
			Map<String, Object> jobMap = this.orderService.getJobDetailById(map);
			model.addAttribute("jobMap", jobMap);
		} else {
			// 项目订单
			Map<String, Object> projectMap = this.orderService.getProjectDetailById(map);
			List<Map<String, Object>> photolist = this.employerService.getProjectPicture(projectMap);
			if (photolist != null && photolist.size() > 0) {
				for (Map<String, Object> photo : photolist) {
					photo.put("url", UploadUtil.downImg(photo.get("url") + ""));
				}
			}
			model.addAttribute("photolist", photolist);
			model.addAttribute("projectMap", projectMap);
		}
		model.addAttribute("type", map.get("type"));
		map.put("delflag", "0");
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = orderService.getApplyPeopleListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> peopleList = orderService.getApplyPeopleList(map);
		model.addAttribute("peopleList", peopleList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/release_order_detail";
	}

	/**
	 * 抢单 列表
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/rushorder")
	public String rushOrder(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = orderService.getRushOrderCount(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> orderList = orderService.getRushOrderList(map);
		model.addAttribute("orderList", orderList);
		model.addAttribute("map", map);
		model.addAttribute("pager", pageHelper.paginate1().toString());
		return "/system/rush_order";
	}

	/**
	 * 导出发布订单记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportPublishOrderRecord")
	@ResponseBody
	public Map<String, Object> exportPublishOrderRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		List<Map<String, Object>> dataList = orderService.getReleaseOrderList(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人发布订单记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "订单号", "订单标题", "发布人", "金额", "工种/行业", "工期", "订单类型", "状态", "发布时间" };
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

					dt.put("d", data.get("realname") == null ? "" : data.get("realname"));
					dt.put("e", data.get("amount") == null ? "" : data.get("amount"));
					dt.put("f", data.get("jobname") == null ? "" : data.get("jobname"));
					Object tem;
					Object tem2;
					if (data.get("starttime") != null && !data.get("starttime").equals("")) {
						tem = sf.format(sf.parse(data.get("starttime").toString()));
					} else {
						tem = "";
					}

					if (data.get("endtime") != null && !data.get("endtime").equals("")) {
						tem2 = sf.format(sf.parse(data.get("endtime").toString()));
					} else {
						tem2 = "";
					}
					if (tem2.equals("") && tem.equals("")) {
						dt.put("g", "");
					} else {
						dt.put("g", tem + "至" + tem2);
					}
					dt.put("h", data.get("isjob") == null ? "项目招工" : "临时招工");
					dt.put("i", data.get("status").toString() == "1" ? "处理中" : "已完成");
					if (data.get("createtime") != null && !data.get("createtime").equals("")) {
						dt.put("j", sf.format(sf.parse(data.get("createtime").toString())));
					} else {
						dt.put("j", "");
					}

					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人发布订单记录", headers, dataset, out);
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
	 * 导出抢单记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportRushOrderRecord")
	@ResponseBody
	public Map<String, Object> exportRushOrderRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		List<Map<String, Object>> dataList = orderService.getRushOrderList(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人抢单记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "抢单单号", "抢单人", "联系方式", "发单单号", "订单标题", "发布人", "工种/行业", "金额/报价", "订单类型", "抢单状态",
				"抢单时间" };
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
					dt.put("b", data.get("applyno") == null ? "" : data.get("applyno"));
					dt.put("c", data.get("releasename") == null ? "" : data.get("releasename"));
					dt.put("d", data.get("appphone") == null ? "" : data.get("appphone"));
					dt.put("e", data.get("sendno") == null ? "" : data.get("sendno"));
					dt.put("f", data.get("title") == null ? "" : data.get("title"));
					dt.put("g", data.get("pname") == null ? "" : data.get("pname"));
					dt.put("h", data.get("jobname") == null ? "" : data.get("jobname"));
					dt.put("i", data.get("amount") == null ? "" : data.get("amount"));
					dt.put("j", data.get("isjob") == null ? "项目招工" : "临时招工");
					if (data.get("status").toString().equals("1")) {
						dt.put("k", "待处理");
					} else if (data.get("status").toString().equals("2")) {
						dt.put("k", "已取消");
					} else if (data.get("status").toString().equals("3")) {
						dt.put("k", "已成交");
					} else if (data.get("status").toString().equals("4")) {
						dt.put("k", "未成交");
					} else if (data.get("status").toString().equals("5")) {
						dt.put("k", "工人取消");
					} else if (data.get("status").toString().equals("6")) {
						dt.put("k", "雇主同意");
					} else if (data.get("status").toString().equals("7")) {
						dt.put("k", "雇主拒绝");
					} else if (data.get("status").toString().equals("8")) {
						dt.put("k", "雇主取消");
					} else if (data.get("status").toString().equals("9")) {
						dt.put("k", "工人同意");
					} else if (data.get("status").toString().equals("10")) {
						dt.put("k", "工人拒绝");
					} else if (data.get("status").toString().equals("11")) {
						dt.put("k", "已过期");
					}
					if (data.get("createtime") != null && !data.get("createtime").equals("")) {
						dt.put("l", sf.format(sf.parse(data.get("createtime").toString())));
					} else {
						dt.put("l", "");
					}
					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人抢单记录", headers, dataset, out);
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
	 * 下载发布订单记录excel
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadMemberInfoExcel(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();

		try {
			return ex.download(map.get("exname").toString(), response, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出发布订单记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportReleaseOrderRecord")
	@ResponseBody
	public Map<String, Object> exportReleaseOrderRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		List<Map<String, Object>> dataList = orderService.getReleaseOrderList(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人发布订单记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号","订单号", "所属项目", "订单标题", "发布人", "金额", "工种/行业", "工期", "订单类型", "状态", "发布时间" };
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
					dt.put("c", data.get("projectname") == null ? data.get("title") : data.get("projectname"));
					dt.put("d", data.get("title") == null ? "" : data.get("title"));
					dt.put("e", data.get("realname") == null ? "" : data.get("realname"));
					dt.put("f", data.get("amount") == null ? "--------" : data.get("amount"));
					dt.put("g", data.get("jobname") == null ? "" : data.get("jobname"));
					dt.put("h", sf.format(sf.parse(data.get("starttime").toString())) + "至"
							+ sf.format(sf.parse(data.get("endtime").toString())));
					dt.put("i", data.get("isjob") == null ? "项目招工" : "临时招工");
					dt.put("j", String.valueOf(data.get("status")).equals("1") ? "处理中": "已完成");
					dt.put("k", sf.format(sf.parse(data.get("createtime").toString())));
					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人发布订单记录", headers, dataset, out);
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
	 * 下载发布订单记录excel
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadReleaseExcel")
	public ResponseEntity<byte[]> downloadReleaseInfoExcel(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();

		try {
			return ex.download("滴答叫人发布订单记录.xls", response, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
