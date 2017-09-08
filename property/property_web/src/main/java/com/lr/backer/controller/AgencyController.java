package com.lr.backer.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.ExportExcelPoi;
import com.hoheng.util.StringUtil;
import com.lr.backer.service.AgencyService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.Md5Util;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.backer.vo.UploadUtil;
import com.lr.weixin.backer.service.MemberService;


@Controller
@RequestMapping("/system/agency")
public class AgencyController extends BaseController {
	
	@Resource AgencyService agencyService;
	@Resource SystemService systemService;
	@Resource MemberService memberService;
	
	
	/**
	 * 分销渠道列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/agencyManager")
	public String agencyManager(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		
		Map<String,Object> user = UserUtil.getUser(request);
			if(!this.isManager(request,user.get("userid")+"")){//如果不是管理员
	          map.put("parentuserid", user.get("userid")+"");
			}else{
				if(map.get("parentid") == null || map.get("parentid").equals("")){
					map.put("parentid", "0");
				}
			}
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =this.agencyService.getAgencyListNum(map);
		pageHelper.setTotalCount(num);
		List<Map<String,Object>>dataList = this.agencyService.getAgencyList(map);
		for(Map<String,Object> data : dataList){
			 Map<String,Object> imgMap = new HashMap<String,Object>();
			 imgMap.put("agencyid", data.get("agencyid"));
			 List<Map<String,Object>> agencyImgList = this.agencyService.getAgencyImgList(imgMap);
			 if(agencyImgList.size()>0){
				 for(Map<String,Object> data1 : agencyImgList){
					 data1.put("agencyImgShow", UploadUtil.downImg(String.valueOf(data1.get("url"))));	 
				 }
				 data.put("agencyImgList", agencyImgList);
			 }
			
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("page", pageHelper.paginate1().toString());
		model.addAttribute("map", map);
		return "/system/agency_list";
	
	
	}
	
	/**
	 * 初始化代理商详情页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/agencyDetail")
	public String agencyDetail(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<Map<String,Object>> dataList = null;
		Map<String,Object> pvMap = new HashMap<String, Object>();
		if(map.get("agencyid") !=null && !map.get("agencyid").equals("")){
			List<Map<String,Object>> data = this.agencyService.getAgencyList(map);
			if(data.size()>0){
			  model.addAttribute("data", data.get(0));
			}
			//所有的代理商
			 pvMap.put("ownagencyid", map.get("agencyid"));
			 pvMap.put("roletype", "1");
			 dataList = this.agencyService.getAgencyList(pvMap);
			 
			 Map<String,Object> imgMap = new HashMap<String,Object>();
			 imgMap.put("agencyid", map.get("agencyid"));
			 List<Map<String,Object>> agencyImgList = this.agencyService.getAgencyImgList(imgMap);
				 for(Map<String,Object> data1 : agencyImgList){
					 data1.put("agencyImgShow", UploadUtil.downImg(String.valueOf(data1.get("url"))));	 
				 }
		 
			 
			 model.addAttribute("agencyImgList", agencyImgList);	 
		}else{
			//所有的代理商
			 pvMap.put("roletype", "1");
			 dataList = this.agencyService.getAgencyList(pvMap);
		}
	    model.addAttribute("dataList", dataList);
		return "/system/agency_detail";
	}
	
	
	/**
	 * 编辑代理商信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAgency")
	@ResponseBody
	public boolean updateAgency(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		boolean flg = false;
		Map<String,Object> user = UserUtil.getUser(request);
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		map.put("updaterid", user.get("userid"));
		map.put("updatetime", tm);
		if(map.get("parentid") == null || map.get("parentid").equals("") || map.get("parentid").equals("-1")){
			map.put("parentid", "0");
		}
		
		if(map.get("agencyid") != null && !map.get("agencyid").equals("")){
			
			this.agencyService.updateAgency(map);
			flg=true;
			//修改
		}else{
			//新增
			Map<String,Object>  dt  = new HashMap<String, Object>();
			String password = Md5Util.getMD5(map.get("password")+"");
			dt.put("username", map.get("username"));
			dt.put("password", password);
			dt.put("realname", map.get("contacter"));
			dt.put("provinceid", map.get("provinceid"));
			dt.put("phone", map.get("phonenum"));
			this.systemService.editUser(dt);
			Map<String, Object> data = this.systemService.getUserInfo(dt);
		 
			List<Map<String,Object>> userRoleList = new ArrayList<Map<String,Object>>();
			Map<String, Object> rolMap = new HashMap<String, Object>();
			rolMap.put("userid", data.get("userid"));
			if(map.get("roletype") != null && map.get("roletype").equals("1")){
				rolMap.put("roleid",  Constants.USER_AGENCY_ROLE);	
			}else if(map.get("roletype") != null && map.get("roletype").equals("2")){
				rolMap.put("roleid", Constants.USER_SELL_ROLE);
			}
			userRoleList.add(rolMap);
			rolMap.put("userRoleList", userRoleList);
			this.systemService.saveUserRole(rolMap);
			
			map.put("userid", data.get("userid"));
			map.put("createtime", tm);
			map.put("createrid", user.get("userid"));
			map.put("agencyid", StringUtil.getUUID());
			map.put("delflag", "0");
			this.agencyService.insertAgency(map);
			
			flg = true;
		}
		if(map.get("agencyImg") != null && !map.get("agencyImg").equals("")){

			String [] imgArrays =  map.get("agencyImg").toString().split(",");
			List<Map<String, Object>> agencyImgArray = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> imgArray = new ArrayList<Map<String, Object>>();
			Map<String,Object> imgMap = null;
			Map<String,Object> agencyImgMap = null;
			for(int i=0;i<imgArrays.length;i++){
				imgMap=new HashMap<String, Object>();
				agencyImgMap = new HashMap<String, Object>();
				imgMap.put("imgid", StringUtil.getUUID());
				imgMap.put("name", "代理商资质图片");
				imgMap.put("url",imgArrays[i]);
				imgMap.put("suffix", imgArrays[i]);
				imgMap.put("createrid", user.get("userid"));
				imgMap.put("createtime", tm);
				imgMap.put("delflag", "0");
				
				agencyImgMap.put("agimgid", StringUtil.getUUID());
				agencyImgMap.put("agencyid", map.get("agencyid"));
				agencyImgMap.put("imgid", imgMap.get("imgid"));
				agencyImgMap.put("createrid", user.get("userid"));
				agencyImgMap.put("createtime", tm);
				agencyImgMap.put("delflag", "0");
				
				agencyImgArray.add(agencyImgMap);
				imgArray.add(imgMap);
			}
			if(imgArray.size()>0 || agencyImgArray.size()>0){
				Map<String,Object>  pvMap = new HashMap<String, Object>();
				pvMap.put("agencyImgArray", agencyImgArray);
				pvMap.put("imgArray", imgArray);
				this.memberService.insertimg(pvMap);
				this.agencyService.insertAgencyImg(pvMap);
			}
			
		}
		return flg;
	}
	
	/**
	 * 得到地区信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getArea")
	@ResponseBody
	public Map<String,Object> getArea(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<Map<String,Object>> dataList =  this.systemService.getAreaList(map);
		map.put("dataList", dataList);
		return map;
	}
	
	/**
	 * 删除代理商资质图片
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteAgencyImg")
	@ResponseBody
	public boolean deleteAgencyImg(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		 boolean flg = false;
		 this.agencyService.deleteAgencyImg(map);
		 flg =true;
		return flg;
	}
	
	/**
	 * 导出渠道信息记录
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportAgencyInfo")
	@ResponseBody
	public Map<String, Object>  exportMemberInstanceRecord(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<Map<String,Object>> dataList = agencyService.getAgencyList(map);
		String importurl=request.getSession().getServletContext().getRealPath("/upload/滴答叫人渠道用户信息.xls");
	 
		System.out.println(importurl);
		ExportExcelPoi<Map<String,Object>>  ex= new ExportExcelPoi<Map<String,Object>>();
		String[] headers = {"序号","渠道账号","渠道名称","渠道类型","渠道区域","上级渠道","提成比列","渠道地址","联系人","联系电话","创建时间"};
		List<Map<String,Object>> dataset= new ArrayList<Map<String,Object>>();
		Map<String, Object> dt = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flg=false;
		
		if(dataList.size()>0){
		  try{
			int i=0;
			for(Map<String,Object> data:dataList){
				i++;
				dt=new LinkedHashMap<String, Object>();
				dt.put("a", i);
				dt.put("b", data.get("username") == null?"":data.get("username"));
				dt.put("c", data.get("agencyname") == null?"":data.get("agencyname"));
				if(data.get("roletype").toString().equals("1")){
					dt.put("d","代理商");	
				}else{
					dt.put("d", "销售员");
				}
				Object provincename = data.get("provincename")==null?"":data.get("provincename");
				Object cityname = data.get("cityname")==null?"":data.get("cityname");
				Object districtname =  data.get("districtname")==null?"":data.get("districtname");
				dt.put("e",provincename+" "+cityname+" "+districtname);
				System.out.println(dt+"-------------------------------------------");
				if(data.get("parentid").toString().equals("0")){
					dt.put("f", "平台");
				}else{
					dt.put("f", data.get("parentname") == null?"":data.get("parentname"));
					
				}
				
				dt.put("g", data.get("commissionrate") == null?"":data.get("commissionrate"));
				
				dt.put("h", data.get("address") == null?"":data.get("address"));
				dt.put("i", data.get("contacter") == null?"":data.get("contacter"));
				dt.put("j", data.get("phonenum") == null?"":data.get("phonenum"));
				 
				if(data.get("createtime") != null && !data.get("createtime").equals("") ){
					dt.put("k",sf.format(sf.parse( data.get("createtime").toString())));	
				}else{
					dt.put("k","");
				}
			 
				dataset.add(dt);
			}
			    OutputStream out = new FileOutputStream(importurl);  
	            ex.exportExcel(sf1.format(System.currentTimeMillis())+"滴答叫人渠道用户信息",headers, dataset, out);
	            out.close();  
	            System.out.println("帐号excel导出成功！");
	            flg=true;
	            resultMap.put("msg", "导出成功");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else{
			resultMap.put("msg", "您没有数据可以导出！");
		}
		resultMap.put("flg", flg);
		return resultMap;
	}
	
	/**
	 * 下载excel
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadAgencyExcel")
	public  ResponseEntity<byte[]>  downloadExcel(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request,HttpServletResponse response){
		ExportExcelPoi<Map<String,Object>>  ex= new ExportExcelPoi<Map<String,Object>>();
	        
		try {
			return ex.download("滴答叫人渠道用户信息.xls", response,request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
