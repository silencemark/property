package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hoheng.util.StringUtil;
import com.lr.backer.service.DictionarieService;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;


@Controller
@RequestMapping("/system/dic")
public class DictionarieController {
	
	 
	@Resource DictionarieService dictionarieService;
	
	/**
	 * 字典类型列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDicTypeList")
	public String getDicTypeList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.dictionarieService.getDictTypeListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.dictionarieService.getDictTypeList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/dict/dict_type";
	}
	
	/**
	 * 编辑字典类型（新增和修改）
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/editDicType")
	public String editDicType(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> user = UserUtil.getUser(request);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		map.put("updatetime", d);
		map.put("updaterid", user.get("userid"));
		if(map.get("datatypeid")!=null && !map.get("datatypeid").equals("")){
			this.dictionarieService.updateDicType(map);
		}else{
			map.put("createtime",d);
			map.put("createrid", user.get("userid"));
			map.put("datatypeid", StringUtil.getUUID());
			this.dictionarieService.insertDicType(map);
		}
		return "redirect:/system/dic/getDicTypeList";
	}
	
	
	/**
	 * 得到字典详情列表   一级
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDicList")  
	public String getDicList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//初始化分页
		map.put("st", "www");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.dictionarieService.getDicListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.dictionarieService.getDicList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/dict/dict_info_list";
	}
	
	/**
	 * 得到字典详情列表   二级
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDicInfoList")  
	public String getDicListInfoList(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//初始化分页
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.dictionarieService.getDicListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.dictionarieService.getDicList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/dict/dict_info_list_one";
	}
	
	
	
	/**
	 * 修改字典详情信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/editDic")  
	public String editDic(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		if(map.get("dataid")!=null && !map.get("dataid").equals("")){
			this.dictionarieService.updateDic(map);
		}else{
			map.put("dataid", StringUtil.getUUID());
			this.dictionarieService.insertDic(map);
		}
		if(map.get("st") != null && !map.get("st").equals("")){
			return "redirect:/system/dic/getDicInfoList?datatypeid="+map.get("typeid")+"&parentid="+map.get("parentid");	
		}else{
			return "redirect:/system/dic/getDicList?datatypeid="+map.get("typeid");
		}
		
	}
	
	
	
	
}
