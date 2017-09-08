package com.lr.backer.controller;

import java.io.FileOutputStream;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.ExportExcelPoi;
import com.lr.backer.service.TradeService;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;

@Controller
@RequestMapping("/system/trade")
public class TradeController extends BaseController {

	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(TestController.class);

	@Resource
	TradeService TradeService;

	/**
	 * 查询财务记录信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/gettradelist")
	public String gettradelist(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		// 初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num = this.TradeService.gettradenum(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> datalist = this.TradeService.gettradelist(map);
		model.addAttribute("datalist", datalist);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/trade_list";
	}

	/**
	 * 导出财务记录
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportTradeRecord")
	@ResponseBody
	public Map<String, Object> exportRushOrderRecord(@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		// 获取当前用户id，以区分数据权限
		Map<String, Object> userMap = UserUtil.getUser(request);
		String userid = userMap.get("userid") + "";
		if (!isManager(request, userid)) {
			map.put("sourcepid", userid);
		}
		List<Map<String, Object>> dataList = this.TradeService.gettradelist(map);
		String importurl = request.getSession().getServletContext().getRealPath("/upload/滴答叫人财务记录.xls");
		System.out.println(importurl);
		ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();
		String[] headers = { "序号", "订单号", "收支类型", "收支额度", "支付方式", "支付类型", "会员姓名", "会员来源", "交易时间" };
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

					dt.put("c", data.get("incomepay").toString() == "1" ? "收入" : "支出");
					dt.put("d", data.get("amount") == null ? "" : data.get("amount"));
					if (data.get("paymethod").toString().equals("1")) {
						dt.put("e", "滴滴币");
					} else if (data.get("paymethod").toString().equals("2")) {
						dt.put("e", "微信");
					} else if (data.get("paymethod").toString().equals("3")) {
						dt.put("e", "支付宝");
					} else {
						dt.put("e", "其它");
					}

					if (data.get("paypurposetype").toString().equals("1")) {
						dt.put("f", "消费");
					} else if (data.get("paypurposetype").toString().equals("2")) {
						dt.put("f", "查看工人信息");
					} else {
						dt.put("f", "系统赚送");
					}
					dt.put("g", data.get("realname") == null ? "" : data.get("realname"));
					dt.put("h", data.get("agencyname") == null ? "" : data.get("agencyname"));
					if (data.get("createtime") != null && !data.get("createtime").equals("")) {
						dt.put("i", sf.format(sf.parse(data.get("createtime").toString())));
					} else {
						dt.put("i", "");
					}
					dataset.add(dt);
				}

				OutputStream out = new FileOutputStream(importurl);
				ex.exportExcel(sf1.format(System.currentTimeMillis()) + "滴答叫人财务记录", headers, dataset, out);
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

}
