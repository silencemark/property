package com.lr.labor.weixin.backer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hoheng.util.StringUtil;
import com.lr.backer.util.Constants;
import com.lr.backer.util.FileDeal;
import com.lr.backer.util.PageHelper;
import com.lr.weixin.backer.service.SystemWXService;
import com.lr.labor.weixin.pojo.Menu;
import com.lr.labor.weixin.util.MenuManager;

@Controller
@RequestMapping("/ws/backer/system")
public class SystemWXController extends BaseActionImpl {

	private transient static Log log = LogFactory
			.getLog(SystemWXController.class);

	@Autowired
	private SystemWXService systemWXService;

	/**
	 * @function:微信菜单管理
	 * @datetime:2014-11-5 下午01:28:00
	 * @Author: robin
	 * @return String
	 */
	@RequestMapping("/findMenuList")
	public String findMenuList(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		// 菜单管理中不需要分页
		FileDeal.parseSimpleForm(pvMap);
		menuList = this.systemWXService.getDTMenuList(pvMap);
		topMenuList = this.systemWXService.getTopDTMenuList(null);
		msgtypeList = this.systemWXService.getMsgtypeList(Constants.REQUEST_MSG);
		request.setAttribute("weixinmenuList", menuList);
		request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("msgtypeList", msgtypeList);
		return "/wx/backer/dtMenuList";
	}

	// 菜单管理
	private List<LinkedHashMap<String, Object>> menuList;

	public List<LinkedHashMap<String, Object>> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<LinkedHashMap<String, Object>> menuList) {
		this.menuList = menuList;
	}

	private List<Map<String, Object>> topMenuList;
	private List<Map<String, Object>> msgtypeList;

	public List<Map<String, Object>> getTopMenuList() {
		return topMenuList;
	}

	public void setTopMenuList(List<Map<String, Object>> topMenuList) {
		this.topMenuList = topMenuList;
	}

	public List<Map<String, Object>> getMsgtypeList() {
		return msgtypeList;
	}

	public void setMsgtypeList(List<Map<String, Object>> msgtypeList) {
		this.msgtypeList = msgtypeList;
	}

	@RequestMapping("/findDTMenuJsonList")
	public String findDTMenuJsonList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pvMap) {
		// 菜单管理中不需要分页
		FileDeal.parseSimpleForm(pvMap);
		menuList = this.systemWXService.getDTMenuList(pvMap);
		topMenuList = this.systemWXService.getTopDTMenuList(null);
		msgtypeList = this.systemWXService.getMsgtypeList(Constants.REQUEST_MSG);
		Map<String, Object> data = new HashMap();
		data.put("menuList", menuList);
		data.put("topMenuList", topMenuList);
		data.put("msgtypeList", msgtypeList);

//		request.setAttribute("result", );
		try {
			response.getWriter().write(this.parseJsonData(data));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	private String getMsgtype(String msgtypename) {
		Map<String, Object> key = new HashMap();
		key.put("key", msgtypename);
		List<Map<String, Object>> dataList = this.systemWXService
				.getMsgtypeList(key);
		if (null != dataList && dataList.size() > 0) {
			return MapUtils.getString(dataList.get(0), "id");
		}
		return null;
	}

	@RequestMapping("/saveDTMenu")
	public String saveDTMenu(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			String msgtypename = MapUtils.getString(paramCondition,
					"msgtypename");
			String msgtype = getMsgtype(msgtypename);
			if (null != msgtype && !"".equals(msgtype)) {
				paramCondition.put("msgtype", msgtype);
			}

			if (paramCondition.containsKey("menuid")
					&& null != paramCondition.get("menuid")
					&& !"".equals(paramCondition.get("menuid").toString())) {
				this.systemWXService.updateDTMenu(paramCondition);
			} else {
				paramCondition.put("menuid", StringUtil.getUUID());
				this.systemWXService.insertDTMenu(paramCondition);
			}
			flag = true;
		}
		try {
			response.getWriter().write(this.parseJsonData(flag));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	@RequestMapping("/delDTMenu")
	public String delDTMenu(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			this.systemWXService.deleteDTMenu(paramCondition);
			flag = true;
		}
		try {
			response.getWriter().write(this.parseJsonData(flag));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	public String parseJsonData(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	// 生成微信菜单
	@RequestMapping("/createWeixinMenu")
	public String createWeixinMenu(HttpServletRequest request) {
		// 只生成已经激活的菜单
		Map<String, Object> param = new HashMap<String, Object>();
		// param.put("ifactive", Constants.IFACTIVE_TRUE);
		System.out.println("\n组装生成菜单的数据\n");
		menuList = this.systemWXService.getDTMenuList(param);
		System.out.println("查询到的菜单数据=" + menuList.toString());
		// 生成微信菜单
		Menu menu = MenuManager.getMenu(menuList);
		System.out.println("菜单=" + menu.toString());
		boolean flag = new MenuManager().createMenu(menu);
		log.info("微信菜单创建结果=" + flag);
		return findMenuList(request,new HashMap<String, Object>());
	}

	private int currentPage = 1;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void initPage(Map<String, Object> paramMap, PageHelper pageHelper,Map<String, Object> pvMap) {
		int rowPage = pageHelper.getRowPage(); // 每页显示的记录
		if (currentPage < 1)
			currentPage = 1;
		if (rowPage < 1)
			rowPage = 1;
		int startnum = (currentPage - 1) * rowPage; // 分页查询开始的序号
		int rownum = rowPage; // 每页显示的条数
		pvMap.put("startnum", startnum);
		pvMap.put("rownum", rownum);
	}

	/*private Map<String, Object> pvMap = new HashMap<String, Object>();

	public Map<String, Object> getPvMap() {
		return pvMap;
	}

	public void setPvMap(Map<String, Object> pvMap) {
		this.pvMap = pvMap;
	}*/

}
