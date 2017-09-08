package com.lr.backer.controller.upload;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lr.backer.util.UploadFileUtil;

/**
 * @ClassName:UploadImgAction.java
 * @ClassPath:com.hk.backer.action
 * @Desciption:上传图片
 * @Author: robin
 * @Date: 2014-11-5 上午10:47:18
 *
 */
@Controller
@RequestMapping("/backer/upload/img")
public class UploadImgController {

	private static final Logger LOGGER = Logger
			.getLogger(UploadImgController.class);
	private static final long serialVersionUID = 1L;

//	private File imgurl;
//	private String imgurlFileName;
//	// 图文中的封面图片
//	private File coverupload;
//	private String coveruploadFileName;
//	private HttpServletResponse response;
//	private HttpServletRequest request;
	private String result;

	/*public File getImgurl() {
		return imgurl;
	}

	public void setImgurl(File imgurl) {
		this.imgurl = imgurl;
	}

	public String getImgurlFileName() {
		return imgurlFileName;
	}

	public void setImgurlFileName(String imgurlFileName) {
		this.imgurlFileName = imgurlFileName;
	}*/

	// 通过flash上传图片
	@RequestMapping("/uploadImgFlash")
	public String uploadImgFlash(HttpServletRequest request,HttpServletResponse response)  {
		// 上传图片
		try {
			Map.Entry<String, MultipartFile> fileEntry = getFile(request);
			String realPath = request.getRealPath("/");
			String path = "/upload/images/";
			String targetPath = UploadFileUtil.copyFile(request.getInputStream(),
					fileEntry.getKey(), realPath, path);
			// 返回图片地址
			request.setAttribute("imgPath", targetPath);
			System.out.println(targetPath);
			this.result = targetPath;
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	public static void main(String[] args) {
		String string = "/upload/images/9046290195232036392.png";
		string = string.replaceAll("/", "#");
		System.out.println(string);
	}

	@RequestMapping("/uploadImgurl")
	public String uploadImgurl(HttpServletRequest request,HttpServletResponse response) {
		// 上传图片
		String targetPath = "";
		Map.Entry<String, MultipartFile> fileEntry = getFile(request);
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)fileEntry.getValue() ;
//		if (null != imgurl) {
		if(commonsMultipartFile.getFileItem().getSize() > 2 * 1024 * 1024){
			// 超过 2M
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<script type=\"text/javascript\">");
			sBuilder.append("window.parent.$._imgUpload_.complete(\"sizeTooBig\")");
			sBuilder.append("</script>");
			try {
				response.getWriter().write(sBuilder.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return "showImg";
			return null;
        }
		
			String realPath = request.getRealPath("/");
			String path = "/upload/images/";
			targetPath = UploadFileUtil.copyFile(commonsMultipartFile.getFileItem(),commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			// request.setAttribute("imgPath", targetPath);
			// System.out.println(targetPath);
//		}
		String type = "";
		if (request.getContentLength() > 1204) {

		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<script type=\"text/javascript\">");
		sBuilder.append("window.parent.$._imgUpload_.complete(\"" + targetPath
				+ "\")");
		sBuilder.append("</script>");
		try {
			response.getWriter().write(sBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return "showImg";
		return null;
	}

	
	/**
	 * 上传图文中的封面图片
	 * @return
	 */
	@RequestMapping("/uploadCoverImg")
	public String uploadCoverImg(HttpServletRequest request,HttpServletResponse response) {
		// 上传图片
		String targetPath = "";
		Map.Entry<String, MultipartFile> fileEntry = getFile(request);
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)fileEntry.getValue() ;
//		if (null != coverupload) {

		if(commonsMultipartFile.getFileItem().getSize() > 64 * 1024){
			// 超过 64K
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<script type=\"text/javascript\">");
			sBuilder.append("window.parent.$.fn.imgUploadSetVal(\"sizeTooBig\")");
			sBuilder.append("</script>");
			try {
				response.getWriter().write(sBuilder.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return "showImg";
			return null;
        }
		
			String realPath = request.getRealPath("/");
			String path = "/upload/images/";
			targetPath = UploadFileUtil.copyFile(commonsMultipartFile.getFileItem(),commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			// request.setAttribute("imgPath", targetPath);
			// System.out.println(targetPath);
//		}
		String type = "";
		if (request.getContentLength() > 1204) {

		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<script type=\"text/javascript\">");
		sBuilder.append("window.parent.$.fn.imgUploadSetVal(\"" + targetPath
				+ "\")");
		sBuilder.append("</script>");
		try {
			response.getWriter().write(sBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return "showImg";
		return null;
	}

	@RequestMapping("/uploadImgurl2")
	public String uploadImgurl2(HttpServletRequest request) {
		// 上传图片
		Map.Entry<String, MultipartFile> fileEntry = getFile(request);
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)fileEntry.getValue() ;
//		if (null != imgurl) {
			String realPath = request.getRealPath("/");
			String path = "/upload/images/";
			String targetPath = UploadFileUtil.copyFile(commonsMultipartFile.getFileItem(), commonsMultipartFile.getFileItem().getName(),
					realPath, path);
			// 返回图片地址
			request.setAttribute("imgPath", targetPath);
			System.out.println(targetPath);
//		}
			return "/upload/showImg2";
	}

	// 素材管理中的图片上传
	// 这里的图片需要进行图片的压缩
	@RequestMapping("/uploadBannerImgurl")
	public String uploadBannerImgurl(HttpServletRequest request) {
		// 上传图片
		Map.Entry<String, MultipartFile> fileEntry = getFile(request);
		
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)fileEntry.getValue() ;
		
		
//		if (null != imgurl) {
			String realPath = request.getRealPath("/");
			String path = "/upload/images/";
			
			String targetPath = UploadFileUtil.copyCompressFile(commonsMultipartFile.getFileItem(),
					commonsMultipartFile.getFileItem().getName(), realPath, path);
			// 返回图片地址
			request.setAttribute("imgPath", targetPath);
			System.out.println(targetPath);
//		}
			return "/upload/showImg";
	}
	
	private Map.Entry<String, MultipartFile> getFile(HttpServletRequest request){
		return UploadFileUtil.getFile(request);
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/*public File getCoverupload() {
		return coverupload;
	}

	public void setCoverupload(File coverupload) {
		this.coverupload = coverupload;
	}

	public String getCoveruploadFileName() {
		return coveruploadFileName;
	}

	public void setCoveruploadFileName(String coveruploadFileName) {
		this.coveruploadFileName = coveruploadFileName;
	}*/

}