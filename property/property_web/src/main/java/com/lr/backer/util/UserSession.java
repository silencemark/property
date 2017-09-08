package com.lr.backer.util;

import java.util.Map;

import com.lr.backer.vo.TDTUser;

public class UserSession {

	private static UserSession _instance = null;

	private UserSession() {
	}

	public static UserSession getInstance() {
		if (null == _instance)
			_instance = new UserSession();
		return _instance;
	}

	public static Map<String, Object> getSession() {
		//TODO 从集群环境中获取当前用户的信息 
		// 后台登录用户的信息
		//return ActionContext.getContext().getSession();
		return null ;
	}

	/**
	 * @function:获取当前登录用户
	 * @datetime:2014年6月12日 下午7:21:35
	 * @Author: Robin
	 * @param: @return
	 * @return DTUser
	 */
	public TDTUser getCurrentUser() {
		Map<String, Object> session = getSession();
		if (null == session) {
			return null;
		}
		if (session.containsKey(WebConstant.USER_SESSION_KEY)) {
			TDTUser user = null != session.get(WebConstant.USER_SESSION_KEY) ? (TDTUser) session
					.get(WebConstant.USER_SESSION_KEY)
					: null;
			return user;
		}
		return null;
	}

	/**
	 * @function:获取当前登录用户的ID
	 * @datetime:2014年6月12日 下午7:22:50
	 * @Author: Robin
	 * @param: @return
	 * @return Integer
	 */
	public Integer getCurrentUserID() {
		return null != getCurrentUser() ? getCurrentUser().getId() : null;
	}

	/**
	 * @function:获取当前登录用户的用户名
	 * @datetime:2014年6月12日 下午7:25:09
	 * @Author: Robin
	 * @return String
	 */
	public String getCurrentUserName() {
		return null == getCurrentUser() ? "" : getCurrentUser().getUsername();
	}

	/**
	 * @function:获取当前登录用户的用户名
	 * @datetime:2014年6月12日 下午7:25:09
	 * @Author: Robin
	 * @return String
	 */
	public String getRoleType() {
		return null == getCurrentUser() ? "" : getCurrentUser().getRoletype();
	}

}
