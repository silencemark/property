package com.lr.backer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 


import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.SupplierService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.PageScroll;
import com.lr.backer.vo.UploadUtil;
/**
 * 供应商管理
 * @author 
 *
 */
@Controller
@RequestMapping("/weixin/supplier")
public class WxSupplierController extends BaseController{
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(TestController.class);
	
	@Resource SupplierService supplierService;
	
	@Resource SystemService systemService;
	
	@Resource IndexService indexService;
	/**
	 *  查询供应商信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getsupplierlist")
	public String getsupperlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("map", map);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		return "/phone/supplier/supplier_list";
	}
	/**
	 * 供应商列表分页查询
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getsupplierlistAjax")
	@ResponseBody
	public Map<String,Object> getsupplierlistAjax(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		PageScroll page = new PageScroll();
		int num = this.supplierService.getsuppernum(map);
		page.setTotalRecords(num);
		page.initPage(map);
		
		Map<String, Object> cityInfo=RedisUtil.getMap("cityInfo"+getUserId(request));
		Map<String, Object> membermap=new HashMap<String, Object>();
		membermap.put("memberid", getUserId(request));
		membermap=this.indexService.getMemberInfo(membermap);
		
		double lat=Double.parseDouble((membermap.get("latitude")==null?0:membermap.get("latitude"))+"");
		double lon=Double.parseDouble((membermap.get("longitude")==null?0:membermap.get("longitude"))+"");;
		if(cityInfo!= null && cityInfo.size()>0){
			lat=Double.valueOf(cityInfo.get("latitude")+"");
			lon=Double.valueOf(cityInfo.get("longitude")+"");
		}
		map.put("longitude", lon);
		map.put("latitude", lat);
		
		Map<String, Object> paramInfo=new HashMap<String, Object>();
		paramInfo.put("pkey", Constants.DISTANCE_KEY);
		paramInfo=this.systemService.getParams(paramInfo).get(0);
		int distance=Integer.parseInt(paramInfo.get("pvalue")+"");
		map.put("maxjuli", distance*1000);
		
		List<Map<String, Object>> supplierList = supplierService.getsupperlist(map);
		
		
		if(supplierList!= null && supplierList.size()>0){
			for(int i=0;i<supplierList.size();i++){
				if(Double.parseDouble(supplierList.get(i).get("juli")+"") > 1000){
					String amount = String.format("%.1f", new Double(supplierList.get(i).get("juli")+"").intValue()/(double)1000);
					supplierList.get(i).put("spacing", amount + "公里");
				}else{
					supplierList.get(i).put("spacing", new Double(supplierList.get(i).get("juli")+"").intValue()+"米");
				}
				String supplierlog = (String) supplierList.get(i).get("supplierlog");
				if(StringUtils.isEmpty(supplierlog)){
					supplierList.get(i).put("supplierlog", "/appcssjs/images/page/pic_bg.png");
				}else{
					supplierList.get(i).put("supplierlog", UploadUtil.downImg(supplierList.get(i).get("supplierlog")+""));
				}
			}
		}
		dataMap.put("dataList",supplierList);
		dataMap.put("page",page);
	    return dataMap;
	}
	
	
	
	
	
	@RequestMapping("/getsupplierdetail")
	public String getsupplierdetaile(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//阅读数
		map.put("addreadtime", 1);
		this.supplierService.updatesupper(map);
		
		List<Map<String, Object>> supplierList = supplierService.getsupperlist(map);
		if(supplierList!=null && supplierList.size()>0){
			List<Map<String, Object>> phonelist=this.supplierService.getsupplierimage(supplierList.get(0));
			for(Map<String, Object> datamap:phonelist){
				datamap.put("url",UploadUtil.downImg(datamap.get("url")+""));
			}
			model.addAttribute("phonelist", phonelist);
			model.addAttribute("suppliermap", supplierList.get(0));
		}
		//分享参数
		Map<String, Object> shareMap=initWeixinShareParam(request);
		model.addAttribute("shareMap", shareMap);
		model.addAttribute("isWeiXinFrom", HttpHeaderUtil.isWeiXinFrom(request)+"");
		
		
		return "/phone/supplier/supplier_detail";
	}
}
