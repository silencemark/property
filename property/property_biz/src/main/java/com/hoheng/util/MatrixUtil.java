package com.hoheng.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException; 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat; 
import com.google.zxing.MultiFormatWriter; 
import com.google.zxing.common.BitMatrix; 

/**
 * 利用zxing开源工具生成二维码QRCode
 * 
 * @date 2012-10-26
 * @author xhw
 * 
 */
public class MatrixUtil {
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;
 
	public static void main(String[] args) {
		File file = new File("E://ewm");
		if  (!file .exists()  && !file .isDirectory()){        
		    file .mkdir();    
		}
		encode("123456789", new File("E:/","/ewm/1437121532367.jpg"));
	}
	
	/**
	 * 生成QRCode二维码<br>
	 * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
	 * static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
	 * 修改为UTF-8，否则中文编译后解析不了<br>
	 */
	public static boolean encode(String contents, File file) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 500, 500);
			writeToFile(bitMatrix, "png", file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean encode(String contents, File file,int width) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE,width,width);
			writeToFile(bitMatrix, "png", file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * * 生成二维码图片<br>
	 * 
	 * @param matrix
	 * @param format
	 *            图片格式
	 * @param file
	 *            生成二维码图片位置
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		ImageIO.write(image, format, file);
	}

	/**
	 * 生成二维码内容<br>
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	/**
	 * 生成二维码
	 * @param text  内容
	 * @param type  类型
	 * @param id    数据id
	 * @param request
	 * @return
	 */
	public static String encode(String text,String type,Object id,HttpServletRequest request){
		return encode(text, type, id, 800, request); 
	}
	
	/**
	 * 生成二维码
	 * @param text  内容
	 * @param type  类型
	 * @param id    数据id
	 * @param width 二维码宽度
	 * @param request
	 * @return
	 */
	public static String encode(String text,String type,Object id,int width,HttpServletRequest request){
		if(id==null){
			id= new Date().getTime()+"";
		}
		if(type==null){
			type=""+new Date().getTime();
		}
		String realPath = request.getSession().getServletContext().getRealPath("/upload");  
		realPath = realPath.replace("\\", "/");  
		File file =new File(realPath+"/ewm");    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory()){        
		    file .mkdir();    
		}
		file =new File(realPath+"/ewm/"+type);  
		//如果文件夹不存在则创建    
				if  (!file .exists()  && !file .isDirectory()){        
				    file .mkdir();    
				}
		//判断文件是否存在  存在就不创建
		String url = "/ewm/"+type+"/"+id+".jpg";
		String originalFilename = "/ewm/"+type+"/"+id+".jpg"; 
		boolean ok  = MatrixUtil.encode(text,new File(realPath, originalFilename),width);
		if(ok){
			return "/upload"+url; 
		}
		return "";
		/* file = new File(realPath+url);
		if(!file.exists()){
			String originalFilename = "/ewm/"+type+"/"+id+".jpg"; 
			boolean ok  = MatrixUtil.encode(text,new File(realPath, originalFilename),width);
			if(ok){
				return "/upload"+url; 
			}
			return ""; 
	    }else{
	    	return "/upload"+url;
	    }*/
	}

}