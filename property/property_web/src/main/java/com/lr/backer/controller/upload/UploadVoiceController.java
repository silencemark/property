package com.lr.backer.controller.upload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lr.backer.util.UploadFileUtil;
import com.lr.labor.weixin.backer.controller.BaseActionImpl;

/**
 * @ClassName:UploadVoiceAction.java
 * @ClassPath:com.hk.backer.action
 * @Desciption:上传语音
 * @Author: robin
 * @Date: 2015-1-21 下午09:37:38
 * 
 */
@Controller
@RequestMapping("/backer/upload/voice")
public class UploadVoiceController extends BaseActionImpl {

	private static final Logger LOGGER = Logger.getLogger(UploadVoiceController.class);
	private static final long serialVersionUID = 1L;

//	private File voidce;
//	private String voiceFileName;

//	public File getVoidce() {
//		return voidce;
//	}
//
//	public void setVoidce(File voidce) {
//		this.voidce = voidce;
//	}

//	public String getVoiceFileName() {
//		return voiceFileName;
//	}
//
//	public void setVoiceFileName(String voiceFileName) {
//		this.voiceFileName = voiceFileName;
//	}
	
	//通过flash上传语音
	@RequestMapping("/uploadVoiceFlash")
	public String uploadVoiceFlash(HttpServletRequest request,HttpServletResponse response) {
		// 上传图片
		try {

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
				request.setAttribute("voicePath", MapUtils.getString(dataMap, "url"));
				System.out.println(MapUtils.getString(dataMap, "url"));
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", "1");
			map.put("data", dataMap);
			//data.url ; data.time ; data.name ; data.size ;
//			this.result = parseJsonData(map);
			JSONObject jsonObject = JSONObject.fromObject(map) ;
			this.result = jsonObject.toString();
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	@RequestMapping("/uploadVoice")
	public String uploadVoice(HttpServletRequest request) {
		// 上传图片
		Map.Entry<String, MultipartFile> entryFile = UploadFileUtil.getFile(request);
//		if (null != voidce) {
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)entryFile.getValue() ;
			String realPath = request.getRealPath("/");
			String path = "/upload/voice/";
			String targetPath = UploadFileUtil.copyFile(commonsMultipartFile.getFileItem(),commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			request.setAttribute("voicePath", targetPath);
			System.out.println(targetPath);
//		}
			return "/upload/showVoice";
	}

	@RequestMapping("/uploadImgurl2")
	public String uploadImgurl2(HttpServletRequest request) {
		// 上传语音
		Map.Entry<String, MultipartFile> entryFile = UploadFileUtil.getFile(request);
//		if (null != voidce) {
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)entryFile.getValue() ;
			String realPath = request.getRealPath("/");
			String path = "/upload/voice/";
			String targetPath = UploadFileUtil.copyFile(commonsMultipartFile.getFileItem(),commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			request.setAttribute("voicePath", targetPath);
			System.out.println(targetPath);
//		}
		return "/upload/showVoice2";
	}
	
	//素材管理中的语音上传
	//这里的语音需要进行语音的压缩
	@RequestMapping("/uploadBannerImgurl")
	public String uploadBannerImgurl(HttpServletRequest request) {
		// 上传语音素材
		Map.Entry<String, MultipartFile> entryFile = UploadFileUtil.getFile(request);
//		if (null != voidce) {
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)entryFile.getValue() ;
			String realPath = request.getRealPath("/");
			String path = "/upload/voice/";
			String targetPath = UploadFileUtil.copyCompressFile(commonsMultipartFile.getFileItem(),commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			request.setAttribute("voicePath", targetPath);
			System.out.println(targetPath);
//		}
		return "/upload/showVoice";
	}

}