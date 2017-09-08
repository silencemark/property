package com.lr.backer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.fileupload.FileItem;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @ClassName:UploadFileUtil.java
 * @ClassPath:com.hk.common.util
 * @Desciption:上传文件工具类
 * @Author: robin
 * @Date: 2014-10-18 上午11:35:50
 *
 */
public class UploadFileUtil {

	public static Map.Entry<String, MultipartFile> getFile(HttpServletRequest request){
		MultipartHttpServletRequest multiPartRequestWrapper = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multiPartRequestWrapper.getFileMap();
    	Iterator<Map.Entry<String, MultipartFile>> iterator = fileMap.entrySet().iterator();
    	Map.Entry<String, MultipartFile>  fileEntry = null ;
    	while (iterator.hasNext()) {
    		fileEntry = iterator.next() ;
			break;
		}
		return fileEntry ;
	}
	
    /**
     * @function:获取文件后缀
     * @datetime:2014-10-18 上午11:39:57
     * @Author: robin
     * @param: @param fileName 文件名称
     * @return String 文件后缀名
     */
    private static String getFileExtension(String fileName){
        if(null!=fileName && !"".equals(fileName)){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "" ;
    }

    /**
     * @function:获取文件的扩展名
     * @datetime:2015-1-22 下午07:50:11
     * @Author: robin
     * @param: @param fileName
     * @param: @param extension 默认文件扩展名
     * @return String
     */
    private static String getFileExtension(String fileName,String defaultextension){
        if(null!=fileName && !"".equals(fileName)){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return defaultextension ;
    }

    /**
     * @function:上传文件
     * @datetime:2014-10-18 上午11:37:29
     * @Author: robin
     * @param: @param src 需要上传的文件
     * @param: @param fileName 文件名称
     * @param: @param url 文件需要上传的url地址
     * @return String 上传后的文件路径
     */
    public static String copyFile(FileItem src,String fileName,String webRootPath,String url) {
        int BUFFER_SIZE = 16 * 1024;
        InputStream in = null;
        OutputStream out = null;
        String extension = getFileExtension(fileName);

        String temp = url + new Random().nextLong() + extension ;
        try {
        	in = src.getInputStream();
//            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new FileOutputStream(webRootPath+temp);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }


    /**
     * @function:上传文件
     * @datetime:2014-10-18 上午11:37:29
     * @Author: robin
     * @param: @param src 需要上传的文件
     * @param: @param fileName 文件名称
     * @param: @param url 文件需要上传的url地址
     * @return String 上传后的文件路径
     */
    public static String copyFile(InputStream in,String fileName,String webRootPath,String url) {
        int BUFFER_SIZE = 16 * 1024;
        OutputStream out = null;
        String extension = "";
        if(null==fileName || "".equals(fileName)){
            extension = ".png";
        }else {
            extension = getFileExtension(fileName);
        }

        String temp = url + new Random().nextLong() + extension ;
        try {
            out = new FileOutputStream(webRootPath+temp);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }

    /**
     * @function:上传文件
     * @datetime:2014-10-18 上午11:37:29
     * @Author: robin
     * @param: @param src 需要上传的文件
     * @param: @param fileName 文件名称
     * @param: @param url 文件需要上传的url地址
     * @return String 上传后的文件路径
     */
    public static String copyAudioFile(InputStream in,String fileName,String webRootPath,String url) {
        int BUFFER_SIZE = 16 * 1024;
        OutputStream out = null;
        String extension = getFileExtension(fileName, ".mp3");

        String temp = url + new Random().nextLong() + extension ;
        try {
            out = new FileOutputStream(webRootPath+temp);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }


    /**
     * @function:上传文件
     * @datetime:2014-10-18 上午11:37:29
     * @Author: robin
     * @param: @param src 需要上传的文件
     * @param: @param fileName 文件名称
     * @param: @param url 文件需要上传的url地址
     * @return String 上传后的文件路径
     */
    public static Map<String, Object> copyAudioByFile(MultipartHttpServletRequest  multiPartRequestWrapper,String webRootPath,String url) {
    	Map<String, MultipartFile> fileMap = multiPartRequestWrapper.getFileMap();
    	Iterator<Map.Entry<String, MultipartFile>> iterator = fileMap.entrySet().iterator();
    	Map.Entry<String, MultipartFile>  fileEntry = null ;
    	while (iterator.hasNext()) {
    		fileEntry = iterator.next() ;
			break;
		}
    	CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)fileEntry.getValue();
        FileItem fileItem = commonsMultipartFile.getFileItem() ; 
        OutputStream out = null;
        int BUFFER_SIZE = 16 * 1024;
        Map<String, Object> map = new HashMap<String, Object>();
        if(fileItem.getSize() > 2 * 1024 * 1024){
        	// 语音超过 2M
        	map.put("_size_out", "true");
        	return map ;
        }
        
        
        String fileName = fileItem.getName();
//        MultipartFile multipartFile = fileEntry.getValue() ;
        InputStream inputStream = null ;
        try {
//            fileName = fileEntry.getKey() ;
            if(fileName.contains(".") && fileName.lastIndexOf(".")>=0){
                map.put("name", fileName.substring(0,fileName.lastIndexOf(".")));
            }
            map.put("size", Integer.valueOf(fileItem.getSize()+""));

            String extension = getFileExtension(fileName,".mp3");
            String tempurl = url + new Random().nextLong() + extension ;

            out = new FileOutputStream(webRootPath+tempurl);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            inputStream = fileItem.getInputStream() ;
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            map.put("path", tempurl);

            map.put("time", Integer.valueOf(getMp3Time(new File(webRootPath+tempurl))+""));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                	inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static long getMp3Time(File file){
        MP3File f;
        try {
            f = (MP3File)AudioFileIO.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
            return audioHeader.getTrackLength();
        } catch (CannotReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TagException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0 ;
    }

    private static long getMp3Time(InputStream inputstream){
        long total = 0;
        try {
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(inputstream);
            Map props = aff.properties();
            if (props.containsKey("duration"))
                total = (long) Math.round((((Long) props.get("duration"))
                        .longValue())/1000);
            System.out.println(total);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total ;
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream(new File("D:\\1.mp3"));
            copyAudioFile(inputStream, "", "", "");
            System.out.println(getMp3Time(new File("D:\\1.mp3")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 压缩图片的宽高
    private static final int IMG_COMPRESS_WIDTH = 400;
    private static final int IMG_COMPRESS_HEIGHT = 400;


    /**
     * @function:上传图片后，压缩图片
     * @datetime:2014-11-21 下午12:43:02
     * @Author: robin
     * @param: @param src
     * @param: @param fileName
     * @param: @param webRootPath
     * @param: @param url
     * @return String
     */
    public static String copyCompressFile(FileItem src,String fileName,String webRootPath,String url) {
        InputStream in = null;
        OutputStream out = null;
        String extension = getFileExtension(fileName);

        String temp = url + new Random().nextLong() + extension ;
        try {
            temp = parsePNGImage(temp);
            //对图片进行压缩
            ImgCompress imgCom = new ImgCompress(src.getInputStream());
            imgCom.resizeFix(IMG_COMPRESS_WIDTH, IMG_COMPRESS_HEIGHT, webRootPath + temp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }


    /**
     * @function:上传图片后，压缩图片
     * @datetime:2014-11-21 下午12:43:02
     * @Author: robin
     * @param: @param src
     * @param: @param fileName
     * @param: @param webRootPath
     * @param: @param url
     * @return String
     */
    public static String copyResolutionFile(FileItem src,String fileName,String webRootPath,String url,int width,int height) {
        InputStream in = null;
        OutputStream out = null;
        String extension = getFileExtension(fileName);

        String temp = url + new Random().nextLong() + extension ;
        try {
            temp = parsePNGImage(temp);
            //对图片进行压缩
            ImgCompress imgCom = new ImgCompress(src.getInputStream());
            imgCom.resize(width, height, webRootPath + temp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }

    private static String parsePNGImage(String sourcePath) {
        return sourcePath.substring(0, sourcePath.lastIndexOf(".")) + ".png";
    }

}