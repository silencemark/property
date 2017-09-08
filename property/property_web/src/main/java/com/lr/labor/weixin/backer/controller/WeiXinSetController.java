package com.lr.labor.weixin.backer.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
















import com.lr.backer.util.AESUtil;
import com.lr.backer.util.CollectionUtils;
import com.lr.backer.util.Constants;
import com.lr.backer.util.FileDeal;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UploadFileUtil;
import com.lr.backer.util.WeiXinSetUtil;
import com.lr.backer.vo.ImgTextMulti;
import com.lr.backer.vo.ImgTextSingle;
import com.lr.backer.vo.KeyWord;
import com.lr.backer.vo.NoticeSend;
import com.lr.backer.vo.ReplyForm;
import com.lr.labor.weixin.api.WeiXinGroupAPI;
import com.lr.labor.weixin.api.WeiXinMaterialAPI;
import com.lr.labor.weixin.api.WeiXinMessageAPI;
import com.lr.weixin.backer.service.BatteryService;
import com.lr.weixin.backer.service.SystemWXService;
import com.lr.weixin.backer.service.WeiXinSetService;
import com.sun.jmx.snmp.Timestamp;

@Controller
@RequestMapping("/ws/backer/wxset")
public class WeiXinSetController extends BaseActionImpl {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(WeiXinSetController.class);

	@Autowired
	private WeiXinSetService weiXinSetService;
	@Autowired
	private SystemWXService systemService;
	@Autowired
	private BatteryService batteryService ;

//	private Map<String, Object> pvMap = new HashMap<String, Object>();


	/**
	 * @function:对id进行加密
	 * @datetime:2014-12-30 上午10:07:29
	 * @Author: robin
	 * @param: @param value
	 * @return String
	 */
	public static String getSecretID(String value) {
		return WeiXinSetUtil.getSecretID(value);
	}

	/*public Map<String, Object> getPvMap() {
		return pvMap;
	}

	public void setPvMap(Map<String, Object> pvMap) {
		pvMap = pvMap;
	}*/

	private List<Map<String, Object>> dataList;

	public WeiXinSetService getWeiXinSetService() {
		return weiXinSetService;
	}

	public void setWeiXinSetService(WeiXinSetService weiXinSetService) {
		this.weiXinSetService = weiXinSetService;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}


	// 编辑单图文
	@RequestMapping("/dtImgTextSingle")
	public String dtImgTextSingle(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String imgtextid = getRequestParameterValue(request,"imgtextid");
		if (null != imgtextid && !"".equals(imgtextid)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("imgtextid", imgtextid);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getDTImgTextItemList(data);
			// 单图文的情况下，dataList的大小为1
			pvMap = dataList.get(0);
		} else {
			pvMap = new HashMap<String, Object>();
		}
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/dtImgTextSingle";
	}

	// 编辑多图文
	@RequestMapping("/dtImgTextMulti")
	public String dtImgTextMulti(HttpServletRequest request) {
		String imgtextid = getRequestParameterValue(request,"imgtextid");
		if (null != imgtextid && !"".equals(imgtextid)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("imgtextid", imgtextid);
			this.dataList = this.weiXinSetService.getDTImgTextItemList(data);
		} else {
			this.dataList = null;
		}
		request.setAttribute("dataList", dataList);
		return "/wx/backer/dtImgTextMulti";
	}

	// 保存单图文
	@RequestMapping("/saveDTImgTextSingle")
	public String saveDTImgTextSingle(HttpServletRequest request,ImgTextSingle imgTextSingle) {
		LOGGER.debug(imgTextSingle);
		
		boolean flag = false;
		Map<String, Object> paramCondition = new HashMap<String, Object>();
		if (null != imgTextSingle) {
//			FileDeal.parseSimpleForm(paramCondition);
			paramCondition.put("imgtexttype",imgTextSingle.getImgtexttype());
			paramCondition.put("imgtextid",imgTextSingle.getImgtextid());
			paramCondition.put("imgtextlistid",imgTextSingle.getImgtextlistid());
			paramCondition.put("title",imgTextSingle.getTitle());
			paramCondition.put("author",imgTextSingle.getAuthor());
			paramCondition.put("ifviewcontent",imgTextSingle.getIfviewcontent());
			paramCondition.put("imgurl",imgTextSingle.getImgurl());
			paramCondition.put("summary",imgTextSingle.getSummary());
			paramCondition.put("content",imgTextSingle.getContent());
			paramCondition.put("linkurl",imgTextSingle.getLinkurl());
			flag = this.weiXinSetService.saveGraphicSingle(paramCondition);
		}
		paramCondition = new HashMap<String, Object>();
		return findDTImgTextList(request,paramCondition);
	}

	/*private String multiGraphic;

	public String getMultiGraphic() {
		return multiGraphic;
	}

	public void setMultiGraphic(String multiGraphic) {
		this.multiGraphic = multiGraphic;
	}*/

	// 保存多图文
	@RequestMapping("/saveDTImgTextMulti")
	public String saveDTImgTextMulti(HttpServletRequest request,HttpServletResponse response,@RequestParam String multiGraphic) {
		LOGGER.debug(multiGraphic);
		boolean flag = false;
		if (null != multiGraphic && !"".equals(multiGraphic)) {
			flag = this.weiXinSetService.saveDTImgTextMulti(multiGraphic);
		}
		this.result = parseJsonData(flag);
//		request.setAttribute("result", result);
//		return "returnJson";
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 保存多图文
	@RequestMapping("/saveDTImgTextMultiForm")
	public String saveDTImgTextMultiForm(HttpServletRequest request,ImgTextMulti imgTextMulti) {
		// 获取多图文 
		String multiGraphic = "" ;
		if(null != imgTextMulti){
			multiGraphic = imgTextMulti.getMultiGraphic() ;
		}
		LOGGER.debug(multiGraphic);
		boolean flag = false;
		if (null != multiGraphic && !"".equals(multiGraphic)) {
			flag = this.weiXinSetService.saveDTImgTextMulti(multiGraphic);
		}
		// this.result = parseJsonData(flag);
		// return "returnJson";
		return findDTImgTextList(request,new HashMap<String, Object>());
	}

	// 素材管理 = 图文
	@RequestMapping("/findDTImgTextList")
	public String findDTImgTextList(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		PageHelper pageHelper = new PageHelper(request);
//		FileDeal.parseSimpleForm(pvMap);
//		String roleType = Constants.ROLE_TYPE_ADMIN;
//		paramCondition.putAll(pvMap);
		/*// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			paramCondition.put("roletype", roleType);
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
		} else {
			// 官方
		}*/
		paramCondition.put("roletype", Constants.ROLE_TYPE_ADMIN);

		int num = this.weiXinSetService.getDTImgTextListNum(paramCondition);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(paramCondition, pageHelper);

		dataList = this.weiXinSetService.getDTImgTextList(paramCondition);

		request.setAttribute("pager",
				pageHelper.paginate1().toString());
		request.setAttribute("dataList", dataList);
		request.setAttribute("paramCondition", paramCondition);
		return "/wx/backer/dtImgTextList";
	}

	@RequestMapping("/findDTImgTextJsonList")
//	@ResponseBody
	public String findDTImgTextJsonList(HttpServletRequest request,HttpServletResponse response) {

		/*if (null == UserSession.getInstance().getCurrentUser()) {
			return "toLogin";
		}*/
		/*String roleType = UserSession.getInstance().getCurrentUser()
				.getRoletype();*/

		try {
			Map<String, Object> data = new HashMap<String, Object>();
			String imgtextid = super.getRequestParameterValue(request,"imgtextid");
			if (null != imgtextid && !"".equals(imgtextid)) {
				data.put("imgtextid", imgtextid);
			}
			// 1表示取官方素材库，2表示取本地素材库
			/*String type = super.getRequestParameterValue(request,"type");
		if ("1".equals(type)) {
			// 官方
			data.put("roletype", Constants.ROLE_TYPE_ADMIN);
		} else if ("2".equals(type)) {
			// 客户
			data.put("roletype", Constants.ROLE_TYPE_CUSTOR);
			data.put("primaryid", UserSession.getInstance().getCurrentUser()
					.getId());
		}*/
			dataList = this.weiXinSetService.getDTImgTextList(data);
			// 将所有的时间转为时间戳
			if (null != dataList) {
				for (Map<String, Object> map : dataList) {
					if (null != map.get("createtime")) {
						Date createtime = (Date) map.get("createtime");
						map.put("createtime", createtime.getTime());
					}
				}
			}
			
			this.result = parseJsonData(dataList);
			LOGGER.debug("result::"+result);
			response.getWriter().write(result);
//			return result ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*request.setAttribute("result", result);
		request.setAttribute("dataList", dataList);*/
		return null ;
		
//		return "returnJson";
	}

	// 素材管理 = 语音
	@RequestMapping("/findDTAudioList")
	public String findDTAudioList(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		PageHelper pageHelper = new PageHelper(request);

		/*String roleType = UserSession.getInstance().getCurrentUser()
				.getRoletype();
		// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			paramCondition.put("roletype", roleType);
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
		} else {
			// 官方
			paramCondition.put("roletype", roleType);
		}*/

		int num = this.weiXinSetService.getDTAudioListNum(paramCondition);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(paramCondition, pageHelper);

		dataList = this.weiXinSetService.getDTAudioList(paramCondition);

		request.setAttribute("pager",
				pageHelper.paginate1().toString());
		request.setAttribute("dataList", dataList);
		request.setAttribute("paramCondition", paramCondition);
		return "/wx/backer/dtAudioList";
	}

	@RequestMapping("/findDTAudioJsonList")
	public String findDTAudioJsonList(HttpServletRequest request,HttpServletResponse response) {
		// String roleType =
		// UserSession.getInstance().getCurrentUser().getRoletype();
		Map<String, Object> data = new HashMap<String, Object>();
		String id = super.getRequestParameterValue(request,"id");
		if (null != id && !"".equals(id)) {
			data.put("id", id);
		}
		// 1表示取官方素材库，2表示取本地素材库
		/*String type = super.getRequestParameterValue(request,"type");
		if ("1".equals(type)) {
			// 官方
			data.put("roletype", Constants.ROLE_TYPE_ADMIN);
		} else if ("2".equals(type)) {
			// 客户
			data.put("roletype", Constants.ROLE_TYPE_CUSTOR);
			data.put("primaryid", UserSession.getInstance().getCurrentUser().getId());
		}*/
		dataList = this.weiXinSetService.getDTAudioList(data);
		// 将所有的时间转为时间戳
		if (null != dataList) {
			for (Map<String, Object> map : dataList) {
				if (null != map.get("createtime")) {
					Date createtime = (Date) map.get("createtime");
					map.put("createtime", createtime.getTime());
				}
			}
		}
		this.result = parseJsonData(dataList);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 保存素材语音
	@RequestMapping("/saveDTAudio")
	public String saveDTAudio(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null == paramCondition)
			paramCondition = new HashMap<String, Object>();

		FileDeal.parseSimpleForm(paramCondition);

		if (paramCondition.containsKey("id")
				&& null != paramCondition.get("id")
				&& !"".equals(paramCondition.get("id").toString())) {
			this.weiXinSetService.updateDTAudio(paramCondition);
		} else {
//			paramCondition.put("roletype", UserSession.getInstance()
//					.getCurrentUser().getRoletype());
//			paramCondition.put("primaryid", UserSession.getInstance()
//					.getCurrentUser().getId());
			this.weiXinSetService.insertDTAudio(paramCondition);
		}
//		flag = true;
//		this.result = parseJsonData(flag);
//		return "returnJson";
		paramCondition = new HashMap<String, Object>();
		request.setAttribute("paramCondition", paramCondition);
		return findDTAudioList(request,paramCondition) ;
	}

	// 删除素材语音
	@RequestMapping("/deleteDTAudio")
	public String deleteDTAudio(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null == paramCondition)
			paramCondition = new HashMap<String, Object>();

		FileDeal.parseSimpleForm(paramCondition);
		if (paramCondition.containsKey("id")
				&& null != paramCondition.get("id")
				&& !"".equals(paramCondition.get("id").toString())) {
			this.weiXinSetService.deleteDTAudio(paramCondition);
		}
		flag = true;
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 查看图文
	@RequestMapping("/showDTImgText")
	public String showDTImgText(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		// imgtextlistid
		String imgtextlistid = super.getRequestParameterValue(request,"imgtextlistid");
		// 对字符串进行解密
		try {
			String t = AESUtil.defaultDecrypt(imgtextlistid);
			if (null != t && t.length() > WeiXinSetUtil.SECRET_KEY.length()) {
				String id = t.substring(0, t.length() - WeiXinSetUtil.SECRET_KEY.length());
				LOGGER.debug("解析出的ID值=" + id);
				// 使用该值进行图文的展示
				// TODO 展示图文
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("imgtextlistid", id);
				pvMap = this.weiXinSetService.getDTImgText(data);
			} else {
				// 无权限访问
				pvMap = null;
				LOGGER.debug("您无权访问\timgtextlistid=" + imgtextlistid + "\t");
			}
		} catch (Exception e) {
			LOGGER.error("解密失败");
			e.printStackTrace();
			pvMap = null;
		}
		// id + WeiXinSetUtil.SECRET_KEY
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/showDTImgText";
	}

	// 查看图文
	@RequestMapping("/showDTImgTextRefuse")
	public String showDTImgTextRefuse(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		// imgtextlistid
		String imgtextlistid = super.getRequestParameterValue(request,"imgtextlistid");
		// 对字符串进行解密
		try {
			String t = AESUtil.defaultDecrypt(imgtextlistid);
			if (null != t && t.length() > WeiXinSetUtil.SECRET_KEY.length()) {
				String id = t.substring(0, t.length() - WeiXinSetUtil.SECRET_KEY.length());
				LOGGER.debug("解析出的ID值=" + id);
				// 使用该值进行图文的展示
				// TODO 展示图文
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("imgtextlistid", id);
				pvMap = this.weiXinSetService.getDTImgText(data);
			} else {
				// 无权限访问
				pvMap = null;
				LOGGER.debug("您无权访问\timgtextlistid=" + imgtextlistid + "\t");
			}
		} catch (Exception e) {
			LOGGER.debug("解密失败");
			e.printStackTrace();
			pvMap = null;
		}
		// id + SECRET_KEY
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/showDTImgTextRefuse";
	}

	// 保存图文
	@RequestMapping("/saveDTImgText")
	public String saveDTImgText(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);

			if (paramCondition.containsKey("imgtextid")
					&& null != paramCondition.get("imgtextid")
					&& !"".equals(paramCondition.get("imgtextid").toString())) {
				this.weiXinSetService.updateDTImgText(paramCondition);
			} else {
//				paramCondition.put("roletype", UserSession.getInstance()
//						.getCurrentUser().getRoletype());
//				paramCondition.put("primaryid", UserSession.getInstance()
//						.getCurrentUser().getId());
				this.weiXinSetService.insertDTImgText(paramCondition);
			}
			flag = true;
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 保存素材图片
	@RequestMapping("/delDTImgText")
	public String delDTImgText(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			this.weiXinSetService.deleteDTImgText(paramCondition);
			flag = true;
		}
		// return findDTImgList();
		// this.result = parseJsonData(flag);
		// return "returnJson";
		paramCondition = new HashMap<String, Object>();
		return findDTImgTextList(request,paramCondition);
	}

	// 删除图文
	@RequestMapping("/delDTImgTextItem")
	public String delDTImgTextItem(HttpServletRequest request) {
		String imgtextid = super.getRequestParameterValue(request,"imgtextid");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("imgtextid", imgtextid);
		this.weiXinSetService.deleteDTImgTextItem(data);

		return findDTImgTextList(request,new HashMap<String, Object>());
	}

	// 素材管理 = 图片
	@RequestMapping("/findDTImgList")
	public String findDTImgList(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		PageHelper pageHelper = new PageHelper(request);

		/*String roleType = UserSession.getInstance().getCurrentUser()
				.getRoletype();
		// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			paramCondition.put("roletype", roleType);
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
		} else {
			// 官方
			paramCondition.put("roletype", roleType);
		}*/

		int num = this.weiXinSetService.getDTImgListNum(paramCondition);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(paramCondition, pageHelper);

		dataList = this.weiXinSetService.getDTImgList(paramCondition);

		request.setAttribute("pager",
				pageHelper.paginate1().toString());
		request.setAttribute("paramCondition", paramCondition);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/dtImgList";
	}

	@RequestMapping("/findDTImgJsonList")
	public String findDTImgJsonList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
//		String roleType = UserSession.getInstance().getCurrentUser()
//				.getRoletype();
		String imgid = super.getRequestParameterValue(request,"imgid");
		if (null != imgid && !"".equals(imgid)) {
			paramCondition.put("imgid", imgid);
		}
		// 1表示取官方素材库，2表示取本地素材库
		/*String type = super.getRequestParameterValue(request,"type");
		if ("1".equals(type)) {
			// 官方
			paramCondition.put("roletype", Constants.ROLE_TYPE_ADMIN);
		} else if ("2".equals(type)) {
			// 客户
			paramCondition.put("roletype", Constants.ROLE_TYPE_CUSTOR);
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
		}*/
		dataList = this.weiXinSetService.getDTImgList(paramCondition);
		this.result = this.parseJsonData(dataList);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 更新图片名称
	@RequestMapping("/updateDTImgName")
	public String updateDTImgName(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		FileDeal.parseSimpleForm(paramCondition);
		if (null == paramCondition) {
			paramCondition = new HashMap<String, Object>();
		}
		this.weiXinSetService.updateDTImg(paramCondition);

		paramCondition = new HashMap<String, Object>();
		return findDTImgList(request,paramCondition);
	}

	// 保存素材图片
	@RequestMapping("/saveDTImg")
	public String saveDTImg(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);

			if (paramCondition.containsKey("imgid")
					&& null != paramCondition.get("imgid")
					&& !"".equals(paramCondition.get("imgid").toString())) {
				this.weiXinSetService.updateDTImg(paramCondition);
			} else {
//				paramCondition.put("roletype", UserSession.getInstance()
//						.getCurrentUser().getRoletype());
//				paramCondition.put("primaryid", UserSession.getInstance()
//						.getCurrentUser().getId());
				paramCondition.put("name", new Timestamp().getDateTime());
				this.weiXinSetService.insertDTImg(paramCondition);
			}
			flag = true;
		}
		// this.result = parseJsonData(flag);
		// return "returnJson";
		paramCondition = new HashMap<String, Object>();
		return findDTImgList(request,paramCondition);
	}

	// 保存素材图片
	@RequestMapping("/delDTImg")
	public String delDTImg(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			this.weiXinSetService.deleteDTImg(paramCondition);
			flag = true;
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	private List<Map<String, Object>> provinceList;

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	/**
	 * 查看粉丝集合
	 * 
	 * @return
	 */
	@RequestMapping("/findMember")
	public String findMember(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		PageHelper pageHelper = new PageHelper(request);
		FileDeal.parseSimpleForm(pvMap);
		if (null != pvMap) {
			String gtygroup = MapUtils.getString(pvMap, "gtygroup");
			if (null == gtygroup || "".equals(gtygroup)) {
				pvMap.put("gtygroup", Constants.DEFAULT_GROUP);
			}
		}

		/*String roleType = UserSession.getInstance().getCurrentUser()
				.getRoletype();
		// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			pvMap.put("roletype", roleType);
			pvMap.put("primaryid", UserSession.getInstance().getCurrentUser()
					.getId());
		}*/

		// 处理分页参数
		int num = this.weiXinSetService.getMemberListNum(pvMap);
		pageHelper.setTotalCount(num);
		initPage(pvMap, pageHelper);

		dataList = this.weiXinSetService.getMemberList(pvMap);

		request.setAttribute("pager",
				pageHelper.paginate1().toString());

		// 1. 获取省市的下拉列表
		this.provinceList = this.weiXinSetService.getProvinceList();
		// 2. 标签类型
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("ifactive", Constants.IFACTIVE_TRUE);
		// this.markList = batteryService.getMarkList(temp);
		// 3. 分组列表
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("grouptype", Constants.GROUP_MEMBER);
		this.groupList = this.weiXinSetService.getGTYGroupTJList(t);
		// 4. 判断当前选中的分组
		if (null != pvMap && pvMap.containsKey("gtygroup")) {
			String gtypgroup = MapUtils.getString(pvMap, "gtygroup");
			if (null != gtypgroup && !"".equals(gtypgroup)) {
				t.put("groupid", gtypgroup);
				List<Map<String, Object>> tempList = this.weiXinSetService
						.getGTYGroupTJList(t);
				if (null != tempList && tempList.size() > 0) {
					pvMap.put("selectGroup", tempList.get(0));
				}
			}
		}
		request.setAttribute("pvMap", pvMap);
		request.setAttribute("groupList", groupList);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/member";
	}

	/*private String agencyname;

	public String getAgencyname() {
		return agencyname;
	}

	public void setAgencyname(String agencyname) {
		this.agencyname = agencyname;
	}*/

	

	/**
	 * 导出粉丝集合
	 * 
	 * @return
	 */
	@RequestMapping("/exportMember")
	public String exportMember(HttpServletRequest request,HttpServletRequest response,@RequestParam Map<String, Object> pvMap) {
		FileDeal.parseSimpleForm(pvMap);
		if (null != pvMap) {
			String gtygroup = MapUtils.getString(pvMap, "gtygroup");
			if (null == gtygroup || "".equals(gtygroup)) {
				pvMap.put("gtygroup", Constants.DEFAULT_GROUP);
			}
		}

		/*String roleType = UserSession.getInstance().getCurrentUser()
				.getRoletype();
		// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			pvMap.put("roletype", roleType);
			pvMap.put("primaryid", UserSession.getInstance().getCurrentUser()
					.getId());
		}*/

		dataList = this.weiXinSetService.getMemberList(pvMap);

		// 导出excel的数据
		List<LinkedHashMap<String, Object>> exportDataList = getExportExcelList(dataList);

		// String path = "" ;//导出文件的路径
		// model.addAttribute(Constant.MESSAGE_DATA_KEY, path);

		/*
		String excelName = "粉丝数据.xls";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		try {
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ URLEncoder.encode(excelName, "UTF-8"));
			os = response.getOutputStream();
			workbook = ExportUtil.exportSimpleSheetWorkbook("粉丝数据",
					exportDataList);
			workbook.write(os);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} finally {
			// 写入文件
			workbook = null;
			if (null != os)
				try {
					os.close();
				} catch (IOException e) {
					LOGGER.error(Constants.LOGGER_EXCEPTION, e);
				}
		}*/
		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return null;
	}

	/**
	 * 导出粉丝集合
	 * 
	 * @return
	 */
	@RequestMapping("/exportAgencyInfoMember")
	public String exportAgencyInfoMember(HttpServletRequest request,HttpServletRequest response,@RequestParam Map<String, Object> pvMap) {
		FileDeal.parseSimpleForm(pvMap);

//		String roleType = UserSession.getInstance().getCurrentUser()
//				.getRoletype();
		// 客户
//		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			/*pvMap.put("roletype", roleType);
			pvMap.put("primaryid", UserSession.getInstance().getCurrentUser()
					.getId());*/
//			pvMap.put("agencyid", UserSession.getInstance().getCurrentUser()
//					.getId());
//		}
		dataList = this.weiXinSetService.getMemberList(pvMap);

		// 导出excel的数据
		List<LinkedHashMap<String, Object>> exportDataList = getExportExcelList(dataList);

		/*String excelName = "粉丝数据.xls";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		try {
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ URLEncoder.encode(excelName, "UTF-8"));
			os = response.getOutputStream();
			workbook = ExportUtil.exportSimpleSheetWorkbook("粉丝数据",
					exportDataList);
			workbook.write(os);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} finally {
			// 写入文件
			workbook = null;
			if (null != os)
				try {
					os.close();
				} catch (IOException e) {
					LOGGER.error(Constants.LOGGER_EXCEPTION, e);
				}
		}*/
		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return null;
	}

	private List<LinkedHashMap<String, Object>> getExportExcelList(
			List<Map<String, Object>> list) {
		// 查询需要导出的结果
		List<LinkedHashMap<String, Object>> exportDataList = new ArrayList<LinkedHashMap<String, Object>>();
		// 导出的excel的表头：昵称 性别 地区 手机 帮派 车型 轮胎品种
		LinkedHashMap<String, Object> excelHeadMap = new LinkedHashMap<String, Object>();
		excelHeadMap.put("nickname", "昵称");
		excelHeadMap.put("sex", "性别");
		excelHeadMap.put("province", "地区");
		excelHeadMap.put("phone", "手机");
		excelHeadMap.put("agencyname", "帮派");
//		excelHeadMap.put("cartypename", "车型");
//		excelHeadMap.put("tiretypename", "轮胎品种");
		exportDataList.add(excelHeadMap);
		if (null != list) {
			for (Map<String, Object> dataMap : list) {
				if (null != dataMap) {
					LinkedHashMap<String, Object> excelDataMap = new LinkedHashMap<String, Object>();
					excelDataMap.put("nickname", MapUtils.getString(dataMap,
							"nickname"));
					String sex = MapUtils.getString(dataMap, "sex");
					excelDataMap.put("sex", "1".equals(sex) ? "男" : "女");
					String province = null != MapUtils.getString(dataMap,
							"province") ? MapUtils.getString(dataMap,
							"province") : "";
					String city = null != MapUtils.getString(dataMap, "city") ? MapUtils
							.getString(dataMap, "city")
							: "";
					if (!"".equals(province) && !"".equals(city)) {
						excelDataMap.put("province", province + " - " + city);
					} else if (!"".equals(province)) {
						excelDataMap.put("province", province);
					} else if (!"".equals(city)) {
						excelDataMap.put("province", city);
					} else {
						excelDataMap.put("province", "");
					}

					excelDataMap.put("phone", MapUtils.getString(dataMap,
							"phone"));
					excelDataMap.put("agencyname", MapUtils.getString(dataMap,
							"agencyname"));
//					excelDataMap.put("cartypename", MapUtils.getString(dataMap,
//							"cartypename"));
//					excelDataMap.put("tiretypename", MapUtils.getString(
//							dataMap, "tiretypename"));
					exportDataList.add(excelDataMap);
				}
			}
		}
		return exportDataList;
	}

	@RequestMapping("/findMemberView")
	public String findMemberView(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		FileDeal.parseSimpleForm(pvMap);
		pvMap = this.weiXinSetService.getMemberView(pvMap);
		Integer activitycount = this.weiXinSetService
				.getMemberActivityNum(pvMap);
		pvMap.put("activitycount", activitycount);
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/memberView";
	}

	private List<Map<String, Object>> groupList;

	public List<Map<String, Object>> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Map<String, Object>> groupList) {
		this.groupList = groupList;
	}

	// 删除用户分组
	@RequestMapping("/deleteGTYGroup")
	public String deleteGTYGroup(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		// 删除后该组下的成员将会移动至未分组
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			if (null != paramCondition && paramCondition.containsKey("groupid")) {
				String groupid = MapUtils.getString(paramCondition, "groupid");
				LOGGER.debug(groupid);
				if (null != groupid) {
					// 1. 将当前组的粉丝修改为未分组
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("gtygroup", groupid);

					List<Map<String, Object>> memberList = this.weiXinSetService
							.getMemberList(dataMap);
					if (null != memberList && memberList.size() > 0) {
						for (Map<String, Object> map : memberList) {
							if (null != map && map.containsKey("openid")) {
								String openid = MapUtils.getString(map,
										"openid");
								WeiXinGroupAPI.updateMemberGroup(openid,
										Constants.DEFAULT_GROUP);
							}
						}
					}
					this.weiXinSetService.updateDefaultGroup(dataMap);

					// 2. 将当前分组删除
					dataMap.put("groupid", groupid);
					this.weiXinSetService.deleteGTYGroup(dataMap);
					flag = true;
				}

			}
			// 需要将分组信息，更新到微信端
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 单个分组
	@RequestMapping("/saveGTYMemberGroup")
	public String saveGTYMemberGroup(HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			if (paramCondition.containsKey("memberid")) {
				// 对某一个粉丝进行分组
				updateMemberGroup(paramCondition);
				flag = true;
			}

			// 需要将分组信息，更新到微信端
			// flag = true ;
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 批量分组
	@RequestMapping("/saveGTYMemberGroupMany")
	public String saveGTYMemberGroupMany(HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		FileDeal.parseSimpleForm(paramCondition);
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			if (null != paramCondition
					&& paramCondition.containsKey("memberids")) {
				// 批量分组
				String memberids = MapUtils.getString(paramCondition,
						"memberids");
				if (null != memberids) {
					String[] memberidArray = memberids.split(",");
					for (int i = 0, len = memberidArray.length; i < len; i++) {
						LOGGER.debug(memberidArray[i]);
						paramCondition.put("memberid", memberidArray[i]);
						this.weiXinSetService.updategtygroup(paramCondition);
						Map<String, Object> data = this.weiXinSetService
								.getMember(paramCondition);
						String openid = MapUtils.getString(data, "openid");
						WeiXinGroupAPI.updateMemberGroup(openid, MapUtils
								.getString(paramCondition, "gtygroup"));
					}
				}
			}
		}
		try {
			response.getWriter().write(flag+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	@RequestMapping("/updateMemberGroup")
	private void updateMemberGroup(Map<String, Object> param) {
		// 更新粉丝的分组
		Map<String, Object> data = this.weiXinSetService.getMember(param);
		if (null != data && data.containsKey("openid")) {
			String openid = MapUtils.getString(data, "openid");
			WeiXinGroupAPI.updateMemberGroup(openid, MapUtils.getString(param,
					"gtygroup"));
			data.put("openid", openid);
			data.put("gtygroup", MapUtils.getString(param, "gtygroup"));
			this.weiXinSetService.updateMember(data);
		}
	}

	// 保存固特异分组
	@RequestMapping("/saveGTYGroup")
	public String saveGTYGroup(HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			paramCondition.put("grouptype", Constants.GROUP_MEMBER);
			if (paramCondition.containsKey("groupid")
					&& null != paramCondition.get("groupid")
					&& !"".equals(paramCondition.get("groupid").toString())) {
				boolean updateflag = WeiXinGroupAPI.updateGroup(MapUtils
						.getString(paramCondition, "groupid"), MapUtils
						.getString(paramCondition, "groupname"));
				LOGGER.debug("分组修改状态=" + updateflag);
				if (updateflag) {
					this.weiXinSetService.updateGTYGroup(paramCondition);
				}
			} else {
				String id = WeiXinGroupAPI.addGroup(MapUtils.getString(
						paramCondition, "groupname"));
				paramCondition.put("groupid", id);
				this.weiXinSetService.insertGTYGroup(paramCondition);
			}
			// 需要将分组信息，更新到微信端
			flag = true;
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 初始化系统分组和粉丝
	@RequestMapping("/initGroupMember")
	public String initGroupMember(HttpServletResponse response) {
		boolean flag = weiXinSetService.initWeiXinGroup();
		boolean flag2 = weiXinSetService.initWeiXinMember();
		boolean flag3 = weiXinSetService.initWeiXinMemberGroup();
		this.result = parseJsonData(flag && flag3);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 消息记录 = 发送
	@RequestMapping("/findNoticeSend")
	public String findNoticeSend(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		// 1. 分组列表
		Map<String, Object> t = new HashMap<String, Object>();
		// 查询粉丝的分组
		t.put("grouptype", Constants.GROUP_MEMBER);
//		String roleType = UserSession.getInstance().getCurrentUser()
//				.getRoletype();
		String roleType = Constants.ROLE_TYPE_ADMIN ;

		/*if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			// 客户
			t.put("roletype", roleType);
			t.put("primaryid", UserSession.getInstance().getCurrentUser()
					.getId());
			this.groupList = this.weiXinSetService.getDTGroupList(t);
		} else {
			// 官方
		}*/
		this.groupList = this.weiXinSetService.getGTYGroupTJList(t);

		// 汇总本月剩余发送消息的条数
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("roletype", roleType);
		/*if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			data.put("primaryid", UserSession.getInstance().getCurrentUser().getId());
		}*/
		paramCondition.put("roletype", roleType);
		Integer canSendNum = this.weiXinSetService.getCanSendNum(data);
		if (null != canSendNum) {
			request.setAttribute("canSendNum",
					null != canSendNum ? canSendNum : "0");
		}
		
		if (Constants.ROLE_TYPE_ADMIN.equals(roleType)) {
			//查询经销商没月可发送的条数
			data.put("roletype", Constants.ROLE_TYPE_CUSTOR);
			Integer num = this.weiXinSetService.getAgencyCanSendNum(data);
			paramCondition.put("agencyCanSendNum", num);
		} 
		request.setAttribute("paramCondition", paramCondition);
		request.setAttribute("groupList", groupList);
		return "/wx/backer/noticeSend";
	}

	// 设置群发数量
	@RequestMapping("/setCanSendNum")
	public String setCanSendNum(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
//			String roletype = UserSession.getInstance().getCurrentUser()
//					.getRoletype();
			//更新经销商的本月可发送的条数
			paramCondition.put("roletype", Constants.ROLE_TYPE_ADMIN);
			weiXinSetService.updateSYNoticeSet(paramCondition);
		}
		return findNoticeSend(request,paramCondition);
	}

	// 发送消息
	@RequestMapping("/noticeSend")
	public String noticeSend(HttpServletRequest request,NoticeSend noticeSend) {
		/*if (Constants.ROLE_TYPE_ADMIN.equals(UserSession.getInstance()
				.getCurrentUser().getRoletype())) {*/
			// 官方进行群发
			return noticeSendAdmin(request,noticeSend);
	/*	}

		// 处理分页参数
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			// 点击发送消息时，只需要将消息保存到数据库中就可以了。
			// 经销商群发消息后，需要官方进行审核
			// 官方群发消息后，不需要审核

			String roleType = UserSession.getInstance().getCurrentUser()
					.getRoletype();
			// 点击发送消息时，只需要将消息保存到数据库中就可以了。
			// 点击发送后，需要固特异官网进行审核后，才可以真正发送。
			paramCondition.put("createtime", new Date());
			paramCondition.put("roletype", roleType);
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
			paramCondition.put("primaryid", UserSession.getInstance()
					.getCurrentUser().getId());
			String msgtype = getMsgtype(MapUtils.getString(paramCondition,
					"msgtype"));
			if (null != msgtype && !"".equals(msgtype)) {
				paramCondition.put("msgtype", msgtype);
			}
			// 是官方
			boolean flag = Constants.ROLE_TYPE_ADMIN.equals(roleType);
			// 经销商 ;未审核
			paramCondition.put("sendstatus", "0");
			this.weiXinSetService.insertSYNoticeSend(paramCondition);
		}
		paramCondition = new HashMap<String, Object>();
		return findNoticeSendHistory(request);*/
	}

	// 官方群发消息
	@RequestMapping("/noticeSendAdmin")
	public String noticeSendAdmin(HttpServletRequest request,NoticeSend noticeSend) {
		
		Map<String, Object> paramCondition = new HashMap<String, Object>();
		if(null != noticeSend){
			paramCondition.put("msgtype", noticeSend.getMsgtype());
			paramCondition.put("content", noticeSend.getContent());
		}
//		String roleType = UserSession.getInstance().getCurrentUser()
//				.getRoletype();
		String roleType = Constants.ROLE_TYPE_ADMIN;
		
		paramCondition.put("createtime", new Date());
		paramCondition.put("roletype", roleType);
//		paramCondition.put("primaryid", UserSession.getInstance()
//				.getCurrentUser().getId());
		String msgtype = getMsgtype(MapUtils.getString(paramCondition,
				"msgtype"));
		if (null != msgtype && !"".equals(msgtype)) {
			paramCondition.put("msgtype", msgtype);
		}
		// 官方 ; 审核通过
		paramCondition.put("sendstatus", "1");
		this.weiXinSetService.insertSYNoticeSend(paramCondition);

		// 进行消息群发
		paramCondition = this.weiXinSetService.getMaxSYNoticeSend();
		paramCondition.put("sendstatus", "1");
		// 当审核通过时，需要调用微信的api进行群发，然后更新数据库的状态
		paramCondition.put("sendtime", new Date());

		// 调用微信api进行群发
		String id = MapUtils.getString(paramCondition, "id");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		/*data.put("roletype", UserSession.getInstance().getCurrentUser()
				.getRoletype());*/
		List<Map<String, Object>> dataList = this.weiXinSetService
				.getSYNoticeSendAuditList(data);
		Map<String, Object> temp = FileDeal.getListFirst(dataList);
		if (null == temp || temp.isEmpty()) {
			paramCondition = new HashMap<String, Object>();
			return findMessageAudit(request,paramCondition);
		}
		// 进行消息推送，推送成功之后，会返回消息ID
		// 将消息ID保存到数据库中
		// 微信将消息推送完成后，会通过消息ID返回推送的结果。
		Map<String, Object> resultMap = sendMessage(temp,request);
		if (null != resultMap && !resultMap.isEmpty()) {
			LOGGER.debug(resultMap.toString());

			String msgid = MapUtils.getString(resultMap, "msgid");
			paramCondition.put("msgid", msgid);
		}
		// ==============
		this.weiXinSetService.updateSYNoticeSend(paramCondition);
		paramCondition = new HashMap<String, Object>();
		return findNoticeSendHistory(request,paramCondition);
	}

	// 历史消息记录 = 发送
	@RequestMapping("/findNoticeSendHistory")
	public String findNoticeSendHistory(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		PageHelper pageHelper = new PageHelper(request);

		if (null != paramCondition) {
			FileDeal.parseSimpleForm(paramCondition);

			// 经销商只能查看自己发送的记录
			// 官方只能查看自己发送的记录
//			paramCondition.put("roletype", UserSession.getInstance()
//					.getCurrentUser().getRoletype());
//			paramCondition.put("primaryid", UserSession.getInstance()
//					.getCurrentUser().getId());

			int num = this.weiXinSetService
					.getSYNoticeSendListNum(paramCondition);
			pageHelper.setTotalCount(num);
			// 处理分页参数
			initPage(paramCondition, pageHelper);

			dataList = this.weiXinSetService
					.getSYNoticeSendList(paramCondition);

			request.setAttribute("pager",
					pageHelper.paginate1().toString());
		}
		request.setAttribute("paramCondition", paramCondition);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/noticeSendHistory";
	}

	// 删除消息记录
	@RequestMapping("/deleteNoticeSend")
	public String deleteNoticeSend(HttpServletResponse response,@RequestParam Map<String, Object> paramCondition) {
		boolean flag = false;
		if (null != paramCondition && !paramCondition.isEmpty()) {
			FileDeal.parseSimpleForm(paramCondition);
			this.weiXinSetService.deleteSYNoticeSend(paramCondition);
			flag = true;
		}
		this.result = parseJsonData(flag);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	// 内容审核开始

	public String findContentList() {

		String forward = "contentList";

		return forward;

	}

	// 内容审核结束

	/**
	 * 自动回复开始
	 */
	@RequestMapping("/findReplyAttention")
	public String findReplyAttention(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String forward = "findReplyAttention";
		if (null != pvMap) {
			FileDeal.parseSimpleForm(pvMap);
			// 被关注自动回复
			pvMap.put("typeid", Constants.FOCUS_AFTER_KEY_REPLAY);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getDTTemplateList(pvMap);
			if (null != dataList && dataList.size() > 0) {
				pvMap = dataList.get(0);
			}
//			pvMap.put("roletype", UserSession.getInstance().getCurrentUser()
//					.getRoletype());
		}
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/wxset/replyAttention";

	}

	private String getMsgtype(String msgtypename) {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("key", msgtypename);
		List<Map<String, Object>> dataList = this.systemService
				.getMsgtypeList(key);
		if (null != dataList && dataList.size() > 0) {
			return MapUtils.getString(dataList.get(0), "id");
		}
		return null;
	}

	// 保存 被关注自动回复
	@RequestMapping("/saveReplyAttention")
	public String saveReplyAttention(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		if (null != pvMap) {
			FileDeal.parseSimpleForm(pvMap);
			// 被关注自动回复
			pvMap.put("typeid", Constants.FOCUS_AFTER_KEY_REPLAY);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getDTTemplateList(pvMap);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (null != dataList && dataList.size() > 0) {
				dataMap = dataList.get(0);
			}
			String msgtype = getMsgtype(MapUtils
					.getString(pvMap, "templettype"));
			if (null != msgtype && !"".equals(msgtype)) {
				dataMap.put("templettype", msgtype);
			}
			dataMap
					.put("templetname", MapUtils
							.getString(pvMap, "templetname"));
			this.weiXinSetService.updateDTTemplate(dataMap);
		}
		return findReplyAttention(request,pvMap);

	}

	@RequestMapping("/findReplyNoKeyword")
	public String findReplyNoKeyword(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String forward = "findReplyNoKeyword";
		if (null != pvMap) {
			FileDeal.parseSimpleForm(pvMap);
			// 被关注自动回复
			pvMap.put("typeid", Constants.MATCH_NO_KEY_REPLAY);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getDTTemplateList(pvMap);
			if (null != dataList && dataList.size() > 0) {
				pvMap = dataList.get(0);
			}
			pvMap.put("roletype", Constants.ROLE_TYPE_ADMIN);
		}
		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/wxset/replyNoKeyword";
	}

	// 保存 未匹配自动回复
	@RequestMapping("/saveReplyNoKeyword")
	public String saveReplyNoKeyword(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {

		if (null != pvMap) {
			FileDeal.parseSimpleForm(pvMap);
			// 未匹配自动回复
			pvMap.put("typeid", Constants.MATCH_NO_KEY_REPLAY);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getDTTemplateList(pvMap);
			Map<String, Object> dataMap = null;
			if (null != dataList && dataList.size() > 0) {
				dataMap = dataList.get(0);
			}
			String msgtype = getMsgtype(MapUtils
					.getString(pvMap, "templettype"));
			if (null != msgtype && !"".equals(msgtype)) {
				dataMap.put("templettype", msgtype);
			}
			dataMap
					.put("templetname", MapUtils
							.getString(pvMap, "templetname"));
			this.weiXinSetService.updateDTTemplate(dataMap);
		}
		return findReplyNoKeyword(request,pvMap);

	}

	// 关键字回复列表
	@RequestMapping("/findReplyKeyword")
	public String findReplyKeyword(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {

		String forward = "findReplyKeyword";
		if (null != pvMap) {
			FileDeal.parseSimpleForm(pvMap);
			this.dataList = this.weiXinSetService.getDTKeyRuleList(pvMap);
		}

		request.setAttribute("dataList", dataList);
		return "/wx/backer/wxset/replyKeyword";
	}

	// 删除自定义回复的规则
	@RequestMapping("/deleteReplyKeyword")
	public String deleteReplyKeyword(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		if (null != pvMap && !pvMap.isEmpty()) {
			FileDeal.parseSimpleForm(pvMap);
			this.weiXinSetService.deleteDTKeyRule(pvMap);
		}
		pvMap = new HashMap<String, Object>();
		return findReplyKeyword(request,pvMap);
	}

	// 新增关键字回复
	@RequestMapping("/initReplyKeywordAdd")
	public String initReplyKeywordAdd(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String forward = "initReplyKeywordAdd";
		if (null != pvMap && !pvMap.isEmpty()) {
			FileDeal.parseSimpleForm(pvMap);
			String ruleid = MapUtils.getString(pvMap, "ruleid");
			if (null != ruleid && !"".equals(ruleid)) {
				pvMap = this.weiXinSetService.getDTKeyRuleDetail(pvMap);
			}
			pvMap.put("type", "编辑");
		} else {
			pvMap.put("type", "新增");
		}
		pvMap.put("roletype", Constants.ROLE_TYPE_ADMIN);

		request.setAttribute("pvMap", pvMap);
		return "/wx/backer/wxset/replyKeywordAdd";
	}

	// 更改规则的状态
	@RequestMapping("/changeReplyKeyStatus")
	public String changeReplyKeyStatus(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		if (null != pvMap && !pvMap.isEmpty()) {
			FileDeal.parseSimpleForm(pvMap);
			String ruleid = MapUtils.getString(pvMap, "ruleid");
			if (null != ruleid && !"".equals(ruleid)) {
				this.weiXinSetService.changeReplyKeyStatus(pvMap);
			}
			pvMap = new HashMap<String, Object>();
		}
		return findReplyKeyword(request,pvMap);
	}

	@RequestMapping("/saveReplyKeyword")
	public String saveReplyKeyword(HttpServletRequest request,ReplyForm replyForm) {
		Map<String, Object> pvMap = new HashMap<String, Object>();
		if(null != replyForm){
			pvMap.put("ruleid", replyForm.getRuleid());
			pvMap.put("rulename", replyForm.getRulename());
			pvMap.put("replytype", replyForm.getReplytype());
			pvMap.put("replycontent", replyForm.getReplycontent());
			List<Map<String, Object>> textKeyList = new ArrayList<Map<String, Object>>();
			if(null != replyForm.getKeyWordList()){
				for (KeyWord keyWord : replyForm.getKeyWordList()) {
					Map<String, Object> keyMap = new HashMap<String, Object>();
					if(null != keyWord){
						keyMap.put("name", keyWord.getName());
						keyMap.put("matetype", keyWord.getMatetype());
						pvMap.put("textKeyList", textKeyList);
						textKeyList.add(keyMap);
					}
				}
			}
			//===============
		}

		// 2. 根据消息类型，获取对应的id
		String replytype = getMsgtype(MapUtils.getString(pvMap, "replytype"));
		if (null != replytype && !"".equals(replytype)) {
			pvMap.put("replytype", replytype);
		}
		pvMap.put("replycontent", MapUtils.getString(pvMap,"replycontent"));

		String ruleid = MapUtils.getString(pvMap, "ruleid");
		if (null != ruleid && !"".equals(ruleid)) {
			this.weiXinSetService.updateDTKeyRule(pvMap);
		} else {
			this.weiXinSetService.insertDTKeyRule(pvMap);
		}
		pvMap = new HashMap<String, Object>();
		return findReplyKeyword(request,pvMap);
	}

	/**
	 * 自动回复结束
	 */

	/**
	 * 群发信息审核
	 */
	@RequestMapping("/findMessageAudit")
	public String findMessageAudit(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		// 查询三个状态的数量
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);
		// 未审核
		dataMap.put("sendstatus", "0");
		Integer auditNoCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		request.getSession().setAttribute("auditNoCount",
				auditNoCount);
		// 已通过
		dataMap.put("sendstatus", "1");
		Integer auditSuccessCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		// 已拒绝
		dataMap.put("sendstatus", "2");
		Integer auditRefuseCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);

		request.setAttribute("auditNoCount",
				null == auditNoCount ? 0 : auditNoCount);
		request.setAttribute("auditSuccessCount",
				null == auditSuccessCount ? 0 : auditSuccessCount);
		request.setAttribute("auditRefuseCount",
				null == auditRefuseCount ? 0 : auditRefuseCount);

		// 查询待审核的数据
		PageHelper pageHelper = new PageHelper(request);
		FileDeal.parseSimpleForm(pvMap);
		pvMap.put("sendstatus", "0");
		pvMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);

		int num = this.weiXinSetService.getSYNoticeSendAuditSearchListNum(pvMap);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(pvMap, pageHelper);

		dataList = this.weiXinSetService.getSYNoticeSendAuditSearchList(pvMap);

		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/wxset/messageAudit";
	}

	/**
	 * 群发信息审核
	 */
	@RequestMapping("/findMessageAuditPass")
	public String findMessageAuditPass(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String forward = "findMessageAuditPass";

		// 查询三个状态的数量
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 未审核
		dataMap.put("sendstatus", "0");
		dataMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);
		Integer auditNoCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		request.getSession().setAttribute("auditNoCount",
				auditNoCount);
		// 已通过
		dataMap.put("sendstatus", "1");
		Integer auditSuccessCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		// 已拒绝
		dataMap.put("sendstatus", "2");
		Integer auditRefuseCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);

		request.setAttribute("auditNoCount",
				null == auditNoCount ? 0 : auditNoCount);
		request.setAttribute("auditSuccessCount",
				null == auditSuccessCount ? 0 : auditSuccessCount);
		request.setAttribute("auditRefuseCount",
				null == auditRefuseCount ? 0 : auditRefuseCount);

		// 查询审核通过的数据
		PageHelper pageHelper = new PageHelper(request);
		FileDeal.parseSimpleForm(pvMap);
		pvMap.put("sendstatus", "1");
		pvMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);

		int num = this.weiXinSetService.getSYNoticeSendAuditSearchListNum(pvMap);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(pvMap, pageHelper);

		dataList = this.weiXinSetService.getSYNoticeSendAuditSearchList(pvMap);

		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/wxset/messageAuditPass";
	}

	/**
	 * 群发信息审核
	 */
	@RequestMapping("/findMessageAuditRefuse")
	public String findMessageAuditRefuse(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		String forward = "findMessageAuditRefuse";

		// 查询三个状态的数量
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 未审核
		dataMap.put("sendstatus", "0");
		dataMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);
		Integer auditNoCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		request.getSession().setAttribute("auditNoCount",
				auditNoCount);
		// 已通过
		dataMap.put("sendstatus", "1");
		Integer auditSuccessCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);
		// 已拒绝
		dataMap.put("sendstatus", "2");
		Integer auditRefuseCount = this.weiXinSetService
				.getSYNoticeSendListNum(dataMap);

		request.setAttribute("auditNoCount",
				null == auditNoCount ? 0 : auditNoCount);
		request.setAttribute("auditSuccessCount",
				null == auditSuccessCount ? 0 : auditSuccessCount);
		request.setAttribute("auditRefuseCount",
				null == auditRefuseCount ? 0 : auditRefuseCount);

		// 查询审核拒绝的数据
		PageHelper pageHelper = new PageHelper(request);
		FileDeal.parseSimpleForm(pvMap);
		pvMap.put("sendstatus", "2");
		pvMap.put("roletype", Constants.ROLE_TYPE_CUSTOR);

		int num = this.weiXinSetService.getSYNoticeSendAuditSearchListNum(pvMap);
		pageHelper.setTotalCount(num);
		// 处理分页和插入数据库
		initPage(pvMap, pageHelper);

		dataList = this.weiXinSetService.getSYNoticeSendAuditSearchList(pvMap);

		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/wxset/messageAuditRefuse";
	}

	// 消息审核
	@RequestMapping("/noticeSendAudit")
	public String noticeSendAudit(HttpServletRequest request,@RequestParam Map<String, Object> paramCondition) {
		// 处理分页参数
		if (null == paramCondition) {
			paramCondition = new HashMap<String, Object>();
		}
		FileDeal.parseSimpleForm(paramCondition);
		String sendstatus = MapUtils.getString(paramCondition, "sendstatus");
		// 审核通过
		boolean flag = "1".equals(sendstatus);
		if (flag) {
			// 当审核通过时，需要调用微信的api进行群发，然后更新数据库的状态
			paramCondition.put("sendtime", new Date());

			// 调用微信api进行群发
			String id = MapUtils.getString(paramCondition, "id");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			List<Map<String, Object>> dataList = this.weiXinSetService
					.getSYNoticeSendAuditList(data);
			Map<String, Object> temp = FileDeal.getListFirst(dataList);
			if (null == temp || temp.isEmpty()) {
				paramCondition = new HashMap<String, Object>();
				return findMessageAudit(request,paramCondition);
			}
			// 进行消息推送，推送成功之后，会返回消息ID
			// 将消息ID保存到数据库中
			// 微信将消息推送完成后，会通过消息ID返回推送的结果。
			// 判断素材是否已经上传到微信端？
			// 如上传了，则可以直接使用；否则，需要上传素材，之后才可以调用微信的群发接口
			Map<String, Object> sendMap = temp ;
			String msgtypekey = MapUtils.getString(sendMap, "msgtypekey");
			String mediaid = MapUtils.getString(sendMap, "mediaid");
			String content = MapUtils.getString(sendMap, "content");
			// true 表示素材未上传，flase表示素材已上传
			// boolean flag = null == mediaid || "".equals(mediaid) ;
			List<Map<String, Object>> openidList = null;
			if (Constants.ROLE_TYPE_CUSTOR.equals(((Integer) sendMap
					.get("roletype")).toString())) {
				// 如果是经销商进行的信息群发，则根据openid进行群发
				Map<String, Object> tempMap = new HashMap<String, Object>();
			 
				openidList = this.weiXinSetService.getAgencyMemberList(tempMap);
			}
			
			if(null != openidList && openidList.size()>0){
				LOGGER.debug("经销商群发消息，按openid进行群发，微信要求最大群发的个数是10000个，所以需要对openid进行拆分！！");
				
				LOGGER.debug( "接受到的集合的大小＝"+openidList.size());
				// 1. 将集合按500大小进行拆分成若干个集合
				List<List<Map<String, Object>>> targetList = CollectionUtils.split(openidList,10000);
				LOGGER.debug( "拆分后的数组大小＝"+targetList.size());
				if (null != targetList && !targetList.isEmpty()) {
					for (List<Map<String, Object>> itemList : targetList) {
						LOGGER.debug( "遍历拆分后的集合：：：：：：：：：：：：：：：");
						
						Map<String, Object> resultMap = sendMessageAudit(sendMap, msgtypekey, content, itemList,request);
						LOGGER.debug(null!=resultMap?resultMap.toString():"");
						String msgid = MapUtils.getString(resultMap, "msgid");
						paramCondition.put("msgid", msgid);
						LOGGER.debug("微信群发的消息id="+msgid);
						// 当审核拒绝时，只需要更新数据库中的状态就可以
						// 当审核通过时，需要将调用微信的api进行信息的推送
						this.weiXinSetService.updateSYNoticeSend(paramCondition);
					}
				}
				
			}
			
			
			
			
			
			// ==============
		}else {
			// 当审核拒绝时，只需要更新数据库中的状态就可以
			// 当审核通过时，需要将调用微信的api进行信息的推送
			this.weiXinSetService.updateSYNoticeSend(paramCondition);
			
		}
		paramCondition = new HashMap<String, Object>();
		return findMessageAudit(request,paramCondition);
	}

	// 信息群发
	private Map<String, Object> sendMessage(Map<String, Object> sendMap,HttpServletRequest request) {

		// 判断素材是否已经上传到微信端？
		// 如上传了，则可以直接使用；否则，需要上传素材，之后才可以调用微信的群发接口
		String msgtypekey = MapUtils.getString(sendMap, "msgtypekey");
		String mediaid = MapUtils.getString(sendMap, "mediaid");
		String content = MapUtils.getString(sendMap, "content");
		// true 表示素材未上传，flase表示素材已上传
		// boolean flag = null == mediaid || "".equals(mediaid) ;
		List<Map<String, Object>> openidList = null;
		if (Constants.ROLE_TYPE_CUSTOR.equals(((Integer) sendMap
				.get("roletype")).toString())) {
			// 如果是经销商进行的信息群发，则根据openid进行群发
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("roletype", "2");
			tempMap.put("primaryid", (Integer) sendMap.get("primaryid"));
			openidList = this.weiXinSetService.getAgencyMemberList(tempMap);
		}

		Map<String, Object> resultMap = null;
		if ("article".equals(msgtypekey)) {
			Map<String, Object> temp = getSendArticleMessage(content,request);
			if (null != temp && !temp.isEmpty()) {
				// 群发图片
				resultMap = WeiXinMessageAPI.sendMessage(temp, msgtypekey,
						openidList);
			}
		} else if ("img".equals(msgtypekey)) {
			Map<String, Object> temp = getSendImgMessage(content,request);
			if (null != temp && !temp.isEmpty()) {
				// 群发图片
				resultMap = WeiXinMessageAPI.sendMessage(temp, msgtypekey,
						openidList);
			}
			// -----------
		} else if ("txt".equals(msgtypekey)) {
			// sendMap
			resultMap = WeiXinMessageAPI.sendMessage(sendMap, msgtypekey,
					openidList);
		}
		return resultMap;
	}
	
	
	// 信息群发 - 经销商群发审核后的消息发送
	private Map<String, Object> sendMessageAudit(Map<String, Object> sendMap,String msgtypekey,String content,List<Map<String, Object>> openidList,HttpServletRequest request ) {
		LOGGER.debug("经销商审核后的群发，这里是拆分后的集合大小。。。。。");
		Map<String, Object> resultMap = null;
		if ("article".equals(msgtypekey)) {
			Map<String, Object> temp = getSendArticleMessage(content,request);
			if (null != temp && !temp.isEmpty()) {
				// 群发图片
				resultMap = WeiXinMessageAPI.sendMessage(temp, msgtypekey,
						openidList);
			}
		} else if ("img".equals(msgtypekey)) {
			Map<String, Object> temp = getSendImgMessage(content,request);
			if (null != temp && !temp.isEmpty()) {
				// 群发图片
				resultMap = WeiXinMessageAPI.sendMessage(temp, msgtypekey,
						openidList);
			}
			// -----------
		} else if ("txt".equals(msgtypekey)) {
			// sendMap
			resultMap = WeiXinMessageAPI.sendMessage(sendMap, msgtypekey,
					openidList);
		}
		return resultMap;
	}

	// 发送图文信息
	private Map<String, Object> getSendArticleMessage(String content,HttpServletRequest request) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("imgtextid", content.substring(2));
		List<Map<String, Object>> graphicList = this.weiXinSetService
				.getDTImgTextItemList(temp);
		// 1. 上传每一个图文的缩略图
		if (null == graphicList || graphicList.isEmpty()
				|| graphicList.size() == 0) {
			return null;
		}
		for (Map<String, Object> map : graphicList) {
			// 验证缩略图是否已经上传？
			// 未超过三天，则直接使用之前上传的信息
			// 判断当前的媒体文件是否已过期
			// true 表示过期 ； false 表示未过期
			boolean uploadstatus = WeiXinMaterialAPI.isMaterialOverdue(map,
					"thumbwxtime");

			// 未上传缩略图 或 缩略图已过期
			if (uploadstatus) {
				// 上传缩略图
				String imgurl = MapUtils.getString(map, "imgurl");
				if (null == imgurl || "".equals(imgurl))
					break;
				String url = request.getRealPath("/") + imgurl;
				File file = new File(url);
				Map<String, Object> t = WeiXinMaterialAPI.uploadThumb(file);
				if (null == t) {
					continue;
				}
				map.put("thumbmediaid", MapUtils.getString(t, "thumbmediaid"));
				map.put("thumbwxtime", MapUtils.getString(t, "createat"));
				this.weiXinSetService.updateDTImgTextItem(map);
			}
		}

		// 微信的媒体文件，上传只能保留三天
		// 每一次使用媒体文件之前首先判断当前的媒体文件是否超过三天，
		// 超过三天则重新上传一次
		// 未超过三天，则直接使用之前上传的信息
		temp = new HashMap<String, Object>();
		temp.put("imgtextid", content.substring(2));
		List<Map<String, Object>> dataList = this.weiXinSetService
				.getDTImgTextList(temp);
		Map<String, Object> tMap = FileDeal.getListFirst(dataList);
		if (null == tMap || tMap.isEmpty()) {
			return null;
		}

		// 判断当前的媒体文件是否已过期
		// true 表示过期 ； false 表示未过期
		boolean uploadstatus = WeiXinMaterialAPI.isMaterialOverdue(tMap,
				"uploadwxtime");
		if (uploadstatus) {
			// 2. 上传图文
			graphicList = this.weiXinSetService.getDTImgTextItemList(temp);
			Map<String, Object> resultMap = WeiXinMaterialAPI
					.uploadGraphic(graphicList);
			// 将结果更新到数据库中
			temp.put("mediaid", MapUtils.getString(resultMap, "mediaid"));
			temp.put("uploadwxtime", MapUtils.getString(resultMap, "createat"));
			// 将上传图文返回的信息保存到数据库中
			this.weiXinSetService.updateDTImgText(temp);
		} else {
			// 已经上传过图文信息
			temp.put("imgtextid", content.substring(2));
			List<Map<String, Object>> tempList = this.weiXinSetService
					.getDTImgTextList(temp);
			temp = FileDeal.getListFirst(tempList);
		}
		return temp;
	}
	
	
	// 发送图文预览信息
	private Map<String, Object> getSendArticleMessagePreview(List<Map<String, Object>> graphicList,HttpServletRequest request) {
		// 1. 上传每一个图文的缩略图
		if (null == graphicList || graphicList.isEmpty()
				|| graphicList.size() == 0) {
			return null;
		}
		//2. 遍历每一个图文
		for (Map<String, Object> map : graphicList) {
			//2.1  上传缩略图
			String imgurl = MapUtils.getString(map, "imgurl");
			if (null == imgurl || "".equals(imgurl))
				break;
			String url = request.getRealPath("/") + imgurl;
			File file = new File(url);
			Map<String, Object> t = WeiXinMaterialAPI.uploadThumb(file);
			if (null == t) {
				continue;
			}
			map.put("thumbmediaid", MapUtils.getString(t, "thumbmediaid"));
			map.put("thumbwxtime", MapUtils.getString(t, "createat"));
			//修改消息中图片路径
			WeiXinMaterialAPI.uploadMessageImg(map,request.getRealPath("/"));
		}
		LOGGER.debug("缩略图上传结果="+graphicList.toString());
		//3. 上传每一个图文
		Map<String, Object> resultMap = WeiXinMaterialAPI.uploadGraphic(graphicList);
		resultMap.put("mediaid", MapUtils.getString(resultMap, "mediaid"));
		LOGGER.debug("上传图文的结果="+resultMap.toString());
		
		return resultMap;
	}

	private Map<String, Object> getSendImgMessage(String content,HttpServletRequest request) {
		Map<String, Object> temp = new HashMap<String, Object>();
		// 上传图文信息
		temp.put("imgid", content.substring(2));
		List<Map<String, Object>> imgList = this.weiXinSetService
				.getDTImgList(temp);
		temp = FileDeal.getListFirst(imgList);
		if (null == temp) {
			return null;
		}

		// 微信的媒体文件，上传只能保留三天
		// 每一次使用媒体文件之前首先判断当前的媒体文件是否超过三天，
		// 超过三天则重新上传一次
		// 未超过三天，则直接使用之前上传的信息
		// 判断当前的媒体文件是否已过期
		// true 表示过期 ； false 表示未过期
		boolean uploadstatus = WeiXinMaterialAPI.isMaterialOverdue(temp,
				"uploadwxtime");

		if (uploadstatus && (null != content && content.length() >= 2)) {

			String img = MapUtils.getString(temp, "img");
			if (null == img || "".equals(img)) {
				return null;
			}
			String url = request.getRealPath("/") + img;

			File file = new File(url);

			Map<String, Object> resultMap = WeiXinMaterialAPI.uploadImg(file);
			// 将结果更新到数据库中
			temp.put("mediaid", MapUtils.getString(resultMap, "mediaid"));
			temp.put("uploadwxtime", MapUtils.getString(resultMap, "createat"));
			// 将数据同步到数据库
			this.weiXinSetService.updateDTImg(temp);
		} else {
			temp.put("imgid", content.substring(2));
			temp = this.weiXinSetService.getDTImg(temp);
		}
		return temp;
	}

	// 通过flash上传语音
	@SuppressWarnings("deprecation")
	@RequestMapping("/uploadDTAudio")
	public String uploadDTAudio(HttpServletRequest request,HttpServletResponse response) throws IOException {
		boolean flag = false;
		// 上传图片
		String realPath = request.getRealPath("/");
		String path = "/upload/voice/";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(request instanceof MultipartHttpServletRequest ){
			MultipartHttpServletRequest  multiPartRequestWrapper = (MultipartHttpServletRequest)request ;
			
			dataMap = UploadFileUtil.copyAudioByFile(multiPartRequestWrapper, realPath, path);
			if(dataMap.containsKey("_size_out") && "true".equals(dataMap.get("_size_out").toString())){
				try {
					response.getWriter().write("sizeTooBig");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null ;
			}
			// 返回图片地址
			request.setAttribute("voicePath",
					MapUtils.getString(dataMap, "path"));
			LOGGER.debug(MapUtils.getString(dataMap, "path"));

//			dataMap.put("roletype", UserSession.getInstance().getCurrentUser()
//					.getRoletype());
//			dataMap.put("primaryid", UserSession.getInstance().getCurrentUser()
//					.getId());
			dataMap.put("createtime", new Date());
			this.weiXinSetService.insertDTAudio(dataMap);
			flag = true;
		}
		/*if (request instanceof MultiPartRequestWrapper) {
			MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;

			dataMap = UploadFileUtil.copyAudioByFile(multiPartRequestWrapper,
					realPath, path);
			
		}*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "1");
		map.put("data", flag);
		// data.url ; data.time ; data.name ; data.size ;
		// paramCondition = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(flag);
		this.result = jsonObject.toString();
		// return findDTAudioList();
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * @function:下载语音文件
	 * @datetime:2015-1-26 下午06:35:34
	 * @Author: robin
	 * @return String
	 */
	@RequestMapping("/downloadAudio")
	public String downloadAudio(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pvMap) {
		if (null == pvMap) {
			pvMap = new HashMap<String, Object>();
		}
		FileDeal.parseSimpleForm(pvMap);
		Map<String, Object> map = this.weiXinSetService.getDTAudio(pvMap);
		String filePath = MapUtils.getString(map, "path");
		String fileName = MapUtils.getString(map, "name");
		return download(request,response,fileName, filePath);
	}
	
	//预览
	@RequestMapping("/previewSimple")
	@ResponseBody
	public String previewSimple(HttpServletRequest request,HttpServletResponse response,ImgTextSingle imgTextSingle) {
		boolean flag = false ;
		LOGGER.debug("preview");
		Map<String, Object> paramCondition = new HashMap<String, Object>();
		if(null != imgTextSingle){
			paramCondition.put("imgtexttype",imgTextSingle.getImgtexttype());
			paramCondition.put("imgtextid",imgTextSingle.getImgtextid());
			paramCondition.put("imgtextlistid",imgTextSingle.getImgtextlistid());
			paramCondition.put("title",imgTextSingle.getTitle());
			paramCondition.put("author",imgTextSingle.getAuthor());
			paramCondition.put("ifviewcontent",imgTextSingle.getIfviewcontent());
			paramCondition.put("imgurl",imgTextSingle.getImgurl());
			paramCondition.put("summary",imgTextSingle.getSummary());
			paramCondition.put("content",imgTextSingle.getContent());
			paramCondition.put("linkurl",imgTextSingle.getLinkurl());
			paramCondition.put("openid",imgTextSingle.getOpenid());
		}
		
//		FileDeal.parseSimpleForm(paramCondition);
		try {
			//传递上来的数据是粉丝的昵称
			String nickname = MapUtils.getString(paramCondition, "openid");
			LOGGER.debug("粉丝的昵称="+nickname);
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("nickname", nickname);
			String openid =this.batteryService.getOpenidByNickname(tempMap);   
			LOGGER.debug("粉丝的openid="+openid);
//			String openid = MapUtils.getString(paramMap, "openid");
			
			// 判断素材是否已经上传到微信端？
			// 如上传了，则可以直接使用；否则，需要上传素材，之后才可以调用微信的群发接口
			String msgtypekey = "article";
			String mediaid = MapUtils.getString(paramCondition, "mediaid");
			String content = MapUtils.getString(paramCondition, "content");

			List<Map<String, Object>> graphicList = new ArrayList<Map<String,Object>>();
			graphicList.add(paramCondition);
			Map<String, Object> resultMap = null;
			//上传图文信息
			if ("article".equals(msgtypekey)) {
				//上传图文所需的相关素材
				Map<String, Object> temp = getSendArticleMessagePreview(graphicList,request);
				if (null != temp && !temp.isEmpty()) {
					// 群发图片
					resultMap = WeiXinMessageAPI.sendMessagePreview(temp, msgtypekey,openid);
				}
			}
			LOGGER.debug(resultMap);
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		this.result = parseJsonData(map);
//		try {
//			response.getWriter().write(result);
			return result ;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null ;
	}
	
	//获取多图文的集合
	private List<Map<String, Object>> getMultiJsonList(String json){
		//多图文标志
		String imgtexttype = "2";
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//"{'cover':{'title':'bbbb','author':'abadfasdf','coverimg':'/theme/grey/images/0.jpeg','show':true,'summary':'abadfasdf','article':'<p>ffaa<br/></p>','link':'ffff'},'sub':[{'title':'1111','author':'11111','coverimg':'http://localhost/upload/images/8278102221095109370.png','show':true,'summary':'','article':'<p>1111111</p>','link':'111111111'},{'title':'1111','author':'11111','coverimg':'http://localhost/upload/images/8278102221095109370.png','show':true,'summary':'','article':'<p>1111111</p>','link':'111111111'}]}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		//1. 获取主图文
		Map<String, Object> map1 = (Map<String, Object>)JSONObject.toBean(jsonObject.getJSONObject("cover"),Map.class);
		map1.put("imgtexttype", imgtexttype);
		list.add(map1);
		
		//2. 获取子图文
		JSONArray jsonArray = jsonObject.getJSONArray("sub");
		if(null!=jsonArray && jsonArray.size()>0){
			for (int i=0,len=jsonArray.size();i<len;i++){
				JSONObject json2 = jsonArray.getJSONObject(i);
				Map<String, Object> map2 = (Map<String, Object>)JSONObject.toBean(json2,Map.class);
				map2.put("imgtexttype", imgtexttype);
				list.add(map2);
			}
		}
		return list ;
	}
	
	//多图文预览
	@RequestMapping("/previewMulti")
	public String previewMulti(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("multiGraphic") String multiGraphic,
			@RequestParam("nickname") String nickname) {
		LOGGER.debug("----hah---");
		boolean flag = false ;
		LOGGER.debug("previewMulti");
//		LOG.info("previewMulti");
//		if(null==paramMap){
//			paramMap = new HashMap<String, Object>();
//		}
//		FileDeal.parseSimpleForm(paramMap);
		try {
			//获取多图文数据
			List<Map<String, Object>> graphicList = this.getMultiJsonList(multiGraphic);
			
			//传递上来的数据是粉丝的昵称
//			String nickname = MapUtils.getString(paramMap, "openid");
			LOGGER.debug("粉丝的昵称="+nickname);
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("nickname", nickname);
			String openid =this.batteryService.getOpenidByNickname(tempMap);
			LOGGER.debug("粉丝的openid="+openid);
//			String openid = MapUtils.getString(paramMap, "openid");
			
			
			// 判断素材是否已经上传到微信端？
			// 如上传了，则可以直接使用；否则，需要上传素材，之后才可以调用微信的群发接口
			String msgtypekey = "article";
//			String mediaid = MapUtils.getString(paramMap, "mediaid");
//			String content = MapUtils.getString(paramMap, "content");

			Map<String, Object> resultMap = null;
			//上传图文信息
			if ("article".equals(msgtypekey)) {
				//上传图文所需的相关素材
				Map<String, Object> temp = getSendArticleMessagePreview(graphicList,request);
				if (null != temp && !temp.isEmpty()) {
					// 群发图片
					resultMap = WeiXinMessageAPI.sendMessagePreview(temp, msgtypekey,openid);
				}
			}

			LOGGER.debug(resultMap);
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print("<script>parent.prevFlag('"+flag+"')</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*private Map<String, Object> paramMap;

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}*/

	
	
	/**
	 * 查看粉丝集合
	 * 
	 * @return
	 */
	@RequestMapping("/findMemberV2")
	public String findMemberV2(HttpServletRequest request,@RequestParam Map<String, Object> pvMap) {
		PageHelper pageHelper = new PageHelper(request);
		FileDeal.parseSimpleForm(pvMap);
		if (null != pvMap) {
			String gtygroup = MapUtils.getString(pvMap, "gtygroup");
			if (null == gtygroup || "".equals(gtygroup)) {
				pvMap.put("gtygroup", Constants.DEFAULT_GROUP);
			}
		}

		/*String roleType = UserSession.getInstance().getCurrentUser().getRoletype();
		// 客户
		if (Constants.ROLE_TYPE_CUSTOR.equals(roleType)) {
			pvMap.put("agencyid", UserSession.getInstance().getCurrentUser().getId());
		}*/

		// 处理分页参数
		int num = this.weiXinSetService.getMemberListNumV2(pvMap);
		pageHelper.setTotalCount(num);
		initPage(pvMap, pageHelper);

		dataList = this.weiXinSetService.getMemberListV2(pvMap);

		request.setAttribute("pager",
				pageHelper.paginate1().toString());

		// 1. 获取省市的下拉列表
		this.provinceList = this.weiXinSetService.getProvinceList();
		// 3. 分组列表
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("grouptype", Constants.GROUP_MEMBER);
		this.groupList = this.weiXinSetService.getGTYGroupTJList(t);
		// 4. 判断当前选中的分组
		if (null != pvMap && pvMap.containsKey("gtygroup")) {
			String gtypgroup = MapUtils.getString(pvMap, "gtygroup");
			if (null != gtypgroup && !"".equals(gtypgroup)) {
				t.put("groupid", gtypgroup);
				List<Map<String, Object>> tempList = this.weiXinSetService
						.getGTYGroupTJList(t);
				if (null != tempList && tempList.size() > 0) {
					pvMap.put("selectGroup", tempList.get(0));
				}
			}
		}

		request.setAttribute("pvMap", pvMap);
		request.setAttribute("dataList", dataList);
		return "/wx/backer/member";
	}
	
}