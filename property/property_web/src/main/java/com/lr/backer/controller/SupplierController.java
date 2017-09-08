package com.lr.backer.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoheng.base.controller.BaseController;
import com.hoheng.util.ExportExcelPoi;
import com.hoheng.util.StringUtil;
import com.hoheng.util.baidujingwei;
import com.lr.backer.service.AgencyService;
import com.lr.backer.service.SupplierService;
import com.lr.backer.util.PageHelper;
import com.lr.backer.util.UserUtil;
import com.lr.backer.vo.UploadUtil;
/**
 * 供应商管理
 * @author 
 *
 */
@Controller
@RequestMapping("/system/supplier")
public class SupplierController extends BaseController {
	
	@SuppressWarnings("unused")
	private transient static Log log = LogFactory.getLog(TestController.class);
	
	@Resource SupplierService supplierService;
	@Resource AgencyService agencyService;
	/**
	 *  查询供应商信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getsupperlist")
	public String getsupperlist(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//初始化分页
	    Map<String,Object> user = UserUtil.getUser(request);
		if(!this.isManager(request,user.get("userid")+"") && !this.isInvestment(request,user.get("userid")+"")){//如果不是管理员或者招商
				map.put("sourcepid", user.get("userid")+"");
		}
		model.addAttribute("isInvestment",this.isInvestment(request,user.get("userid")+"")+"");
		PageHelper pageHelper = new PageHelper(request);
		pageHelper.initPage(map);
		int num =supplierService.getsuppernum(map);
		pageHelper.setTotalCount(num);
		List<Map<String, Object>> supplierList = supplierService.getsupperlist(map);
		model.addAttribute("page", pageHelper.paginate1().toString());
		if(!this.isManager(request,user.get("userid")+"") && this.isInvestment(request,user.get("userid")+"")){
			for(Map<String, Object> su:supplierList){
				if(su.containsKey("telephone") && !"".equals(su.get("telephone"))){
					String phone=su.get("telephone")+"";
					phone=phone.substring(0,3)+"****"+phone.substring(7,phone.length());
					su.put("telephone", phone);
				}
			}
		}
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("map", map);
		return "/system/supplier_list";
	}
	/**
	 *  初始化添加供应商信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initsupperinfo")
	public String initsupperinfo(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<Map<String, Object>> dataList = this.agencyService.getAgencyList(new HashMap<String, Object>());
		model.addAttribute("dataList", dataList);
		return "/system/add_supplier";
	}
	/**
	 *  添加供应商信息
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatesupperinfo")
	public String insertsupperinfo(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> user = UserUtil.getUser(request);
		String address =""+map.get("provincename")+map.get("cityname")+map.get("districtname")+map.get("address");
		System.out.println(address);
	    Map<String,Object> da= baidujingwei.getjingwei(address);
	    if(da==null){
	    	throw new RuntimeException("请输入正确地址！");
	    }
	    map.put("longitudelatitude", da.get("longitude")+","+da.get("latitude"));
	    map.put("lon", da.get("longitude"));
	    map.put("lat", da.get("latitude"));
		 if(map.get("supplierid")!="" && map.get("supplierid")!=null){
			map.put("updaterid", user.get("userid"));
			map.put("updatetime", new Date());
			
		 
			this.supplierService.updatesupper(map);
			if(map.get("dmimgurl")!=null && map.get("dmimgurl")!=""){
			//往图片表里面存放值
			String mdimglist=map.get("dmimgurl")+"";
			String mdimg[] = mdimglist.split(","); 
			for(int i =0; i <mdimg.length ; i++){ 
				if(mdimg[i]!=null && mdimg[i]!=""){
					//往timg中放图
					Map<String, Object> imgmap = new HashMap<String, Object>();
					imgmap.put("imgid", StringUtil.getUUID());
					imgmap.put("name", "店面图片");
					imgmap.put("url", mdimg[i]);
					imgmap.put("createtime", new Date());
					imgmap.put("createrid", user.get("userid"));
					imgmap.put("delflag", "0");
					this.supplierService.insertimg(imgmap);
					//往supplierimg 中放入图片
					Map<String, Object> datamap = new HashMap<String, Object>();
					datamap.put("supplierimgid", StringUtil.getUUID());
					datamap.put("supplierid", map.get("supplierid"));
					datamap.put("type","2");
					datamap.put("createtime", new Date());
					datamap.put("createrid", user.get("userid"));
					datamap.put("delflag", "0");
					datamap.put("imgid", imgmap.get("imgid"));
					this.supplierService.insertsupperimg(datamap);
				}
			}
			}
			if(map.get("cpimgurl")!=null && map.get("cpimgurl")!=""){
			String cpimglist=map.get("cpimgurl")+"";
			String cpimg[] = cpimglist.split(","); 
			for(int i =0; i <cpimg.length ; i++){ 
				if(cpimg[i]!=null && cpimg[i]!=""){
					//往timg中放图
					Map<String, Object> imgmap = new HashMap<String, Object>();
					imgmap.put("imgid", StringUtil.getUUID());
					imgmap.put("name", "产品图片");
					imgmap.put("url", cpimg[i]);
					imgmap.put("createtime", new Date());
					imgmap.put("createrid", user.get("userid"));
					imgmap.put("delflag", "0");
					this.supplierService.insertimg(imgmap);
					//往supplierimg 中放入图片
					Map<String, Object> datamap = new HashMap<String, Object>();
					datamap.put("supplierimgid", StringUtil.getUUID());
					datamap.put("supplierid", map.get("supplierid"));
					datamap.put("type","1");
					datamap.put("createtime", new Date());
					datamap.put("createrid", user.get("userid"));
					datamap.put("delflag", "0");
					datamap.put("imgid", imgmap.get("imgid"));
					this.supplierService.insertsupperimg(datamap);
				}
			}
			}
		}else{
			String mdimglist="";
			String mdimg[]=null;
			if(map.get("dmimgurl")!=null && map.get("dmimgurl")!=""){
				mdimglist=map.get("dmimgurl")+"";
				mdimg = mdimglist.split(","); 
		    }
			if(map.get("supplierlog") == null || map.get("supplierlog").equals("")){
				if(mdimg!=null && mdimg.length>0){
					map.put("supplierlog", mdimg[0]);
				}
			}
			map.put("supplierid", StringUtil.getUUID());
			map.put("createtime", new Date());
			map.put("createrid", user.get("userid"));
			map.put("delflag", "0");
			this.supplierService.insertsupper(map);
			//往图片表里面存放值
			if(map.get("dmimgurl")!=null && map.get("dmimgurl")!=""){
			for(int i =0; i <mdimg.length ; i++){ 
				if(mdimg[i]!=null && mdimg[i]!=""){
					//往timg中放图
					Map<String, Object> imgmap = new HashMap<String, Object>();
					imgmap.put("imgid", StringUtil.getUUID());
					imgmap.put("name", "店面图片");
					imgmap.put("url", mdimg[i]);
					imgmap.put("createtime", new Date());
					imgmap.put("createrid", user.get("userid"));
					imgmap.put("delflag", "0");
					this.supplierService.insertimg(imgmap);
					//往supplierimg 中放入图片
					Map<String, Object> datamap = new HashMap<String, Object>();
					datamap.put("supplierimgid", StringUtil.getUUID());
					datamap.put("supplierid", map.get("supplierid"));
					datamap.put("type","2");
					datamap.put("createtime", new Date());
					datamap.put("createrid", user.get("userid"));
					datamap.put("delflag", "0");
					datamap.put("imgid", imgmap.get("imgid"));
					this.supplierService.insertsupperimg(datamap);
				}
			}
			}
			if(map.get("cpimgurl")!=null && map.get("cpimgurl")!=""){
			String cpimglist=map.get("cpimgurl")+"";
			String cpimg[] = cpimglist.split(","); 
			for(int i =0; i <cpimg.length ; i++){ 
				if(cpimg[i]!=null && cpimg[i]!=""){
					//往timg中放图
					Map<String, Object> imgmap = new HashMap<String, Object>();
					imgmap.put("imgid", StringUtil.getUUID());
					imgmap.put("name", "产品图片");
					imgmap.put("url", cpimg[i]);
					imgmap.put("createtime", new Date());
					imgmap.put("createrid", user.get("userid"));
					imgmap.put("delflag", "0");
					this.supplierService.insertimg(imgmap);
					//往supplierimg 中放入图片
					Map<String, Object> datamap = new HashMap<String, Object>();
					datamap.put("supplierimgid", StringUtil.getUUID());
					datamap.put("supplierid", map.get("supplierid"));
					datamap.put("type","1");
					datamap.put("createtime", new Date());
					datamap.put("createrid", user.get("userid"));
					datamap.put("delflag", "0");
					datamap.put("imgid", imgmap.get("imgid"));
					this.supplierService.insertsupperimg(datamap);
				}
			}
			}
		}
		map = new HashMap<String, Object>();
		return  "redirect:/system/supplier/getsupperlist";
	}
	/**
	 * 初始化修改供应商信息
	 * @author 
	 */
	@RequestMapping("/initupdatesupperinfo")
	public String initupdatesupperinfo(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		//查询基础数据
		List<Map<String, Object>> supplierList = supplierService.getsupperlist(map);
		Map<String, Object> suppliermap = supplierList.get(0);
		//查询图片数据
		 
		List<Map<String, Object>> supplierimglist = this.supplierService.getsupperimg(map);
		for(Map<String,Object>data :supplierimglist){
			data.put("sup_url", UploadUtil.downImg(String.valueOf(data.get("url"))));
		}
		if(suppliermap.get("supplierlog") != null){
			suppliermap.put("supplierlog_show", UploadUtil.downImg(String.valueOf(suppliermap.get("supplierlog"))));	
		} 
		
		model.addAttribute("suppliermap", suppliermap);
		model.addAttribute("supplierimglist", supplierimglist);
		map.put("roletype", "1");
		List<Map<String, Object>> dataList = this.agencyService.getAgencyList(map);
		model.addAttribute("dataList", dataList);
		return "/system/add_supplier";
	}
	/**
	 * 删除供应商信息（假删除）
	 * @author 
	 */
	@RequestMapping("/detelesupper")
	public String detelesupper(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> user = UserUtil.getUser(request);
		map.put("updaterid", user.get("userid"));
		map.put("updatetime", new Date());
		this.supplierService.updatesupper(map);
		map = new HashMap<String, Object>();
		return  "redirect:/system/supplier/getsupperlist";
	}
	/**
	 * 删除图片
	 * @author 唐杰
	 */
	@RequestMapping("/detelesupperimg")
	@ResponseBody
	public void detelesupperimg(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		Map<String,Object> user = UserUtil.getUser(request);
		map.put("updaterid", user.get("userid"));
		map.put("updatetime", new Date());
		this.supplierService.updatesupplierimg(map);
	}
	
	
	
	/**
	 * 导出渠道信息记录
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportSupplierInfo")
	@ResponseBody
	public Map<String, Object>  exportMemberInstanceRecord(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request){
		List<Map<String,Object>> dataList =  supplierService.getsupperlist(map);
		String importurl=request.getSession().getServletContext().getRealPath("/upload/滴答叫人供应商信息.xls");
	 
		System.out.println(importurl);
		ExportExcelPoi<Map<String,Object>>  ex= new ExportExcelPoi<Map<String,Object>>();
		String[] headers = {"序号","供应商名称","所属代理商","所在地区","主营产品","营业时间","营业地址","联系电话","分享次数","阅读次数","创建人"};
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
				dt.put("b", data.get("suppliername") == null?"":data.get("suppliername"));
				dt.put("c", data.get("agencyname") == null?"平台":data.get("agencyname"));
			 
				Object provincename = data.get("provincename")==null?"":data.get("provincename");
				Object cityname = data.get("cityname")==null?"":data.get("cityname");
				Object districtname =  data.get("districtname")==null?"":data.get("districtname");
				dt.put("d",provincename+" "+cityname+" "+districtname);
				dt.put("e", data.get("products") == null?"":data.get("products"));
				dt.put("f", data.get("businesshours") == null?"":data.get("businesshours"));
				dt.put("g", data.get("address") == null?"":data.get("address"));
				dt.put("h", data.get("telephone") == null?"":data.get("telephone"));
				dt.put("i", data.get("sharetimes") == null?"0":data.get("sharetimes"));
				dt.put("j", data.get("readtimes") == null?"0":data.get("readtimes"));
				dt.put("k", data.get("createname") == null?"":data.get("createname"));
				dataset.add(dt);
			}
			    OutputStream out = new FileOutputStream(importurl);  
	            ex.exportExcel(sf1.format(System.currentTimeMillis())+"滴答叫人供应商信息",headers, dataset, out);
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
	@RequestMapping("/downloadSupplierExcel")
	public  ResponseEntity<byte[]>  downloadExcel(@RequestParam Map<String,Object> map,Model model,HttpServletRequest request,HttpServletResponse response){
		ExportExcelPoi<Map<String,Object>>  ex= new ExportExcelPoi<Map<String,Object>>();
	        
		try {
			return ex.download("滴答叫人供应商信息.xls", response,request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
