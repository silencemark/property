package com.lr.backer.vo;

import java.io.File;
import java.util.Map;

import com.lr.backer.redis.RedisUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.AuthConstant;

public class UploadUtil {
	public static String pc_style = "";
	public static String pcstyle_sy = "";
	private static String wx_style = "";
	private static String wxstyle_sy = "";
	public static String qn_domain = "http://7xqoy0.com1.z0.glb.clouddn.com/";
	/**
	 * 上传图片(file文件流)
	 * @param file
	 * @param newname
	 * @return
	 */
	public static boolean uploadImg(File file,String newname){
		UploadManager uploadManager = new UploadManager();
		try {
			Response res = uploadManager.put(file, newname, AuthConstant.getToken());
			if(res.isOK()){
				return true;
			}
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	/**
	 * 上传图片(路径)
	 * @return
	 */
	public static boolean uploadImg(String url,String newname){
		UploadManager uploadManager = new UploadManager();
		try {
			Response res = uploadManager.put(url, newname, AuthConstant.getToken());
			if(res.isOK()){
				return true;
			}
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	
	/**
	 * 下载图片
	 *  @return
	 */
	public static String downImg(String imgname){
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			}

			update_style();
			
			String url = qn_domain + imgname + "?" + pc_style; 
			try{
				return url; 
			}catch(Exception e){
				return imgname;
			}		
		}else{
			return null;
		}
		
	}
	
	/**
	 * 下载图片(微信端)(有水印)
	 *  @return
	 */
	public static String downImg_wxsy(String imgname){ 
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			} 

			update_style();
			
			String url = qn_domain + imgname + "?" + wxstyle_sy; 
			try{ 
				return url; 
			}catch(Exception e){
				return imgname;
			}		
		}else{
			return null;
		}
		
	}
	
	/**
	 * 下载图片(PC端)(有水印)
	 *  @return
	 */
	public static String downImg_pcsy(String imgname){ 
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			} 

			update_style();
			
			String url = qn_domain + imgname + "?" + pcstyle_sy; 
			try{
				return url; 
			}catch(Exception e){
				return imgname;
			}		
		}else{
			return null;
		}
		
	}
	
	/**
	 * 下载图片(PC端)(banner)
	 *  @return
	 */
	public static String downImg_PcBanner(String imgname){
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			}
			
			update_style();
			
			String url = qn_domain + imgname + "?" + "imageView2/2/w/1360/q/75";   
			try{ 
				return url; 
			}catch(Exception e){ 
				return imgname;
			}		
		}else{
			return null;
		}
		
	}
	/**
	 * 下载图片(PC端)(无水印)
	 *  @return
	 */
	public static String downPcImg(String imgname){
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			}
		//	Auth auth = AuthConstant.getAuth();
			update_style();
			
			String url = qn_domain + imgname+ "?"+pc_style;   
			try{
		//		String urlSigned = auth.privateDownloadUrl(url);
				return url; 
			}catch(Exception e){
				return imgname;
			}		 
		}else{
			return null;
		}
		
	}
	/**
	 * 下载图片(微信端)(无水印)
	 * @param imgname
	 * @param style
	 * @return
	 */
	public static String downWxImg(String imgname){
		if(imgname != null && !imgname.equals("")){
			if(imgname.indexOf("http:/") > -1){
				return imgname;
			}
			
			update_style();
			
			String url = qn_domain + imgname + "?"+wx_style;       

			try{ 
				return url;
			}catch(Exception e){
				return imgname;
				
			}		
		}else{
			return null;
		}
	}
	
	private static void update_style(){
		if(RedisUtil.exist("qiniu_change")){
			Map<String,Object> qiniuMap = RedisUtil.getMap("qiniu"); 
			qn_domain = String.valueOf(qiniuMap.get("domain")); 
			pc_style = String.valueOf(qiniuMap.get("pcstyle")); 
			pcstyle_sy = String.valueOf(qiniuMap.get("pcstyle_sy"));  
			wx_style = String.valueOf(qiniuMap.get("wxstyle")); 
			wxstyle_sy = String.valueOf(qiniuMap.get("wxstyle_sy")); 
			RedisUtil.remove("qiniu_change"); 
		}
	}
	
	public static void init_style(){
		Map<String,Object> qiniuMap = RedisUtil.getMap("qiniu"); 
		if(qiniuMap != null){
			qn_domain = String.valueOf(qiniuMap.get("domain")); 
			pc_style = String.valueOf(qiniuMap.get("pcstyle")); 
			pcstyle_sy = String.valueOf(qiniuMap.get("pcstyle_sy"));  
			wx_style = String.valueOf(qiniuMap.get("wxstyle")); 
			wxstyle_sy = String.valueOf(qiniuMap.get("wxstyle_sy")); 
		} 
		 
	}
}
