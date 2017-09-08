package com.lr.backer.controller.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.lr.backer.util.UserUtil;
import com.lr.backer.vo.UploadUtil;

@Controller
@RequestMapping("/upload")
public class UploadController {

	/**
	 * 用户唯一ID
	 * @return
	 */
	public String getUserId(HttpServletRequest request){
		Map<String, Object> data=UserUtil.getUser(request);
		return data.get("memberid")==null?"admin":data.get("memberid") + ""; 
	}
	
	/**
	 * 显示图片
	 * @param keyid
	 * @return
	 */
	@RequestMapping("/downimg/{keyid}")
	@ResponseBody
	public String downimg(@PathVariable String keyid){
		return UploadUtil.downImg(keyid);
	}
	
	/**
	 * 微信端显示图片
	 * @param keyid
	 * @return
	 */
	@RequestMapping("/downimg/wx/{keyid}")
	@ResponseBody
	public String downPublicImg(@PathVariable String keyid){
		return UploadUtil.downWxImg(keyid);  
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/headimg",method=RequestMethod.POST)    
	@ResponseBody 
	public Map<String, Object> headimg(HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		String filename = null;
		if(request.getParameter("filename") != null && !request.getParameter("filename").equals("")){
			filename = String.valueOf(request.getParameter("filename"));
		}else{
			filename = "myfiles";  
		}
		  
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 获得文件：
		List<MultipartFile> myfiles = multipartRequest.getFiles(filename);
		System.out.println(myfiles.get(0).getContentType()); 
		// 设置响应给前台内容的数据格式 
		//response.setContentType("application/octet-stream; charset=UTF-8");
		//response.setContentType("json");
		// 设置响应给前台内容的PrintWriter对象 
		Map<String, Object> map = new HashMap<String, Object>();
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				map.put("error", "请选择文件后上传");
				return map;
			} else {
				CommonsMultipartFile cf= (CommonsMultipartFile)myfile; 
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				File file= fi.getStoreLocation();
				 
				String newImg = System.currentTimeMillis()/1000l + "_" + getUserId(request);

			    boolean fag = UploadUtil.uploadImg(file, newImg);
			    fi.delete();
			    if(fag){
			    	String imgurl = UploadUtil.downImg(newImg);
			    	map.put("imgurl", imgurl);
			    	map.put("imgkey", newImg);
			    }else{
			    	map.put("error", "上传失败");
			    }	    
			    
//				System.out.println("文件名称: " + myfile.getName());
//				System.out.println("文件长度: " + myfile.getSize());
//				System.out.println("文件类型: " + myfile.getContentType());
			}
		}

		return map;
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/headimg1")  
	@ResponseBody 
	public String headimg1(HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		String filename = null;
		if(request.getParameter("filename") != null && !request.getParameter("filename").equals("")){
			filename = String.valueOf(request.getParameter("filename"));
		}else{
			filename = "myfiles"; 
		}
		  
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 获得文件：
		List<MultipartFile> myfiles = multipartRequest.getFiles(filename);
		System.out.println(myfiles.get(0).getContentType()); 
		// 设置响应给前台内容的数据格式 
		//response.setContentType("application/octet-stream; charset=UTF-8");
		// 设置响应给前台内容的PrintWriter对象 
		Map<String, Object> map = new HashMap<String, Object>();
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				map.put("error", "请选择文件后上传");
				return JSON.toJSONString(map);
			} else {
				CommonsMultipartFile cf= (CommonsMultipartFile)myfile; 
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				File file= fi.getStoreLocation();
				 
				String newImg = System.currentTimeMillis()/1000l + "_" + getUserId(request);

			    boolean fag = UploadUtil.uploadImg(file, newImg);
			    fi.delete();
			    if(fag){
			    	String imgurl = UploadUtil.downImg(newImg);
			    	map.put("imgurl", imgurl);
			    	map.put("imgkey", newImg);
			    }else{
			    	map.put("error", "上传失败");
			    }	    
			    
//				System.out.println("文件名称: " + myfile.getName());
//				System.out.println("文件长度: " + myfile.getSize());
//				System.out.println("文件类型: " + myfile.getContentType());
			}
		}

		return JSON.toJSONString(map);
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "/headimg2", method = RequestMethod.POST)   
	@ResponseBody 
	public String headimg2(@RequestParam("myfiles") MultipartFile myfile,HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
				Map<String,Object> map = new HashMap<String,Object>();
		
				CommonsMultipartFile cf= (CommonsMultipartFile)myfile; 
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				File file= fi.getStoreLocation();
				 
				String newImg = System.currentTimeMillis()/1000l + "_" + getUserId(request);

			    boolean fag = UploadUtil.uploadImg(file, newImg);
			    fi.delete();
			    if(fag){
			    	String imgurl = UploadUtil.downImg(newImg);
			    	map.put("imgurl", imgurl);
			    	map.put("imgkey", newImg);
			    }else{
			    	map.put("error", "上传失败");
			    }	    
			    
		return JSON.toJSONString(map);
	}
}
