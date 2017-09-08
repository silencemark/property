package com.lr.labor.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lr.labor.weixin.core.CoreService;
import com.lr.labor.weixin.util.SignUtil;

public class CDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CDServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CDServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		log
				.info("...............................doget...............................");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(nonce)) {
			out.print("普通http");
		} else {
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				System.out.println("微信检验成功！");
				out.print(echostr);
			}
		}
		out.close();
		out = null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log
				.info("...............................dopost...............................");
		System.out.println("+++++++++++进入weixinServlet doPost方法..........");
		log.debug("进入weixinServlet doPost方法..........");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		response.setContentType("text/xml; charset=UTF-8");
		log.debug("增加设置类型");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(request);
		log.debug("respMessage=" + respMessage);

		// 响应消息
		PrintWriter out = response.getWriter();
		if(null!=respMessage){
			out.print(respMessage);
		}else {
			out.print("");
		}
		out.close();
		
	}

}
