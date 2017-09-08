package com.lr.labor.weixin.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.service.IndexService;
import com.lr.backer.util.CookieUtil;
import com.lr.backer.util.UserUtil;
import com.lr.weixin.backer.service.MemberService;

/**
 * @author robin88
 * 
 */
public class WeiXinInterceptor extends HandlerInterceptorAdapter {

	private transient static Log log = LogFactory
			.getLog(WeiXinInterceptor.class);

	@Autowired
	private MemberService memberService;
	@Resource
	private IndexService indexService;

	/**
	 * 获取原来的url地址
	 * 
	 * @param request
	 * @return
	 */
	private String getSourceUrl(HttpServletRequest request) {
		String contextPath = ((HttpServletRequest) request).getContextPath();
		String sourceUrl = ((HttpServletRequest) request).getRequestURL()
				.toString();
		System.out.println(contextPath);
		System.out.println(sourceUrl);
		String queryString = request.getQueryString();
		try {
			if (null != queryString && !"".equals(queryString)) {
				sourceUrl += "?" + queryString;
			}
			return URLDecoder.decode(sourceUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		if (!HttpHeaderUtil.isWeiXinFrom(request)) {
			return true;
		}

		// TODO 这里暂时使用从session中获取，需要替换为从cookie中获取的方式
		// Map<String, Object> weixinMember = (Map<String,
		// Object>)request.getSession().getAttribute("WEIXIN.MEMBER") ;
		// 从redis中获取当前微信用户
		Map<String, Object> weixinMember = UserUtil.getOpenid(request);
		log.info("------------当前" + weixinMember);
		log.info("原地址：" + getSourceUrl(request));
		if (null == weixinMember) {
			// 还没有登录用户,调用微信api 获取openid
			log.info("还没有登录用户,调用微信api 获取openid");
			// 请求 /wx/getopenid 获取openid
			request.getRequestDispatcher(
					"/wx/getopenid?sourceUrl=" + getSourceUrl(request))
					.forward(request, response);
			Cookie cookie = CookieUtil.getCookie(request, UserUtil.WEIXININFO);
			if (cookie != null) {
				CookieUtil.deleteCookie(response, cookie, "/");
			}
			return false;
		}
		String openid = MapUtils.getString(weixinMember, "openid");
		log.info("当前微信用户的openid=" + openid);
		if (null == openid || "".equals(openid)) {
			// openid为空，调用微信api 获取openid
			log.info("openid为空，调用微信api 获取openid");
			request.getRequestDispatcher(
					"/wx/getopenid?sourceUrl=" + getSourceUrl(request))
					.forward(request, response);
			Cookie cookie = CookieUtil.getCookie(request, UserUtil.WEIXININFO);
			if (cookie != null) {
				CookieUtil.deleteCookie(response, cookie, "/");
			}
			return false;
		} else {
			/*
			 * 基于openid查询是否用户是否关注本平台 如有关注则放开，如未关注则跳转到关注
			 */
			Map<String, Object> membermap = new HashMap<String, Object>();
			String state = getSourceUrl(request);
			Logger.getLogger(this.getClass()).debug(
					"++++++state+++++++++++" + state
							+ "++++++++++++++++++++++++++++++++++");
			membermap.put("openid", openid);
			membermap = this.indexService.getMemberInfo(membermap);
			Logger.getLogger(this.getClass()).debug(
					"++++++forward+++++++++++++begin++++++++++++++");
			if (membermap != null && membermap.size() > 0) {
				return true;
			} else {
				if (state.contains("weixin/supplier/getsupplierdetail")
						|| state.contains("my/myqrcode")
						|| state.contains("employer/applyJobDetail")
						|| state.contains("employer/applyProjectDetail")) {
					return true;
				} else {
					Logger.getLogger(this.getClass()).debug(
							"++++++forward+++++++++++++begin++++++++++++++");
					request.getRequestDispatcher("/public/attention.jsp")
							.forward(request, response);
					Logger.getLogger(this.getClass())
							.debug("++++++forward++++++++end+++++++++++++++++++++++++++++++++");
				}
			}
			// 需要重定向该地址
			return true;
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		log.info("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		log.info("postHandle");
	}

	public static void main(String[] args) {
		System.out
				.println("http://www.1391rj.com/weixin/system/sharedesignerInfo?infoid=1"
						.contains("weixin/system/getDesignerInfo"));
	}

}
