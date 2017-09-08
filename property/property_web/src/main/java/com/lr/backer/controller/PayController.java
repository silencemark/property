package com.lr.backer.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.lr.backer.util.Xml2JsonUtil;
import com.lr.backer.util.sendSms;
import com.lr.backer.vo.UploadUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.weixin.backer.service.MemberService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * 支付回调
 * @author slience
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {
	private transient static Log log = LogFactory.getLog(PayController.class);

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

	
	@RequestMapping(value="/payIosResult",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void payIosResult(@RequestParam Map<String, Object> map,Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(HttpHeaderUtil.isIOSFrom(request) || HttpHeaderUtil.isAndroidFrom(request)){

			JSONObject postData = new JSONObject();
			Map<String, Object> wxmap=new HashMap<String, Object>();
			String       out_trade_no = null;
			String       appsignature = null;
			try {
				ServletInputStream in = request.getInputStream();
				// 转换微信post过来的xml内容
				System.out.println("-------in :"+in);
				XStream xs = new XStream(new DomDriver());
				System.out.println("-------xs :"+xs);
				xs.alias("xml", WeChatBuyPost.class);
				String xmlMsg = Tools.inputStream2String(in);
				System.out.println("-------xmlMsg :"+xmlMsg);
				
				Xml2JsonUtil xml2=new Xml2JsonUtil();
				String jsonstr = xml2.xml2JSON(xmlMsg);
				postData=postData.fromObject(jsonstr);
				wxmap=postData.getJSONObject("xml");

				System.out.println("-------"+wxmap.toString());
				
				out_trade_no=wxmap.get("out_trade_no")+"";
				out_trade_no=out_trade_no.replace("[", "").replaceAll("\"", "").replace("]", "");
				System.out.println("------------"+out_trade_no);
				Map<String, Object> orderMap = RedisUtil.getMap("orderMap"+out_trade_no);
				System.out.println("orderMap---"+orderMap);
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
				orderMap.put("memberid", orderMap.get("publisherid"));
				orderMap.put("type", 2);
				this.orderService.savePayRecord(orderMap);
				RedisUtil.remove("orderMap" + out_trade_no);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String xmlreturn="<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
		writeString(response,xmlreturn);
	}
	
	/**

	 * 直接写字符串

	 * @param response

	 * @param msg

	 */
	private void writeString(HttpServletResponse response, String msg) {
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
