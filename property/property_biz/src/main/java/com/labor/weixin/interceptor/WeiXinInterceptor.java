package com.labor.weixin.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		// TODO 这里暂时使用从session中获取，需要替换为从cookie中获取的方式
		Map<String, Object> weixinMember =  (Map<String, Object>)request.getSession().getAttribute("WEIXIN.MEMBER") ;
		if(null == weixinMember){
			// 还没有登录用户,调用微信api 获取openid  
			 log.info("还没有登录用户,调用微信api 获取openid");
			 // 请求 /wx/getopenid 获取openid
            request.getRequestDispatcher("/wx/getopenid").forward(request, response);
            return false ;
		}
		String openid = MapUtils.getString(weixinMember, "openid");
		if(null == openid || "".equals(openid)){
			// openid为空，调用微信api 获取openid
			log.info("openid为空，调用微信api 获取openid");
			request.getRequestDispatcher("/wx/getopenid").forward(request, response);
            return false ;
		}
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		log.info("postHandle");
	}

}
